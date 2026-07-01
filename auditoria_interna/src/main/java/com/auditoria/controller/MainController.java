package com.auditoria.controller;

import com.auditoria.model.EstadoEvaluacion;
import com.auditoria.model.Requisito;
import com.auditoria.repository.CSVRequisitoLoader;
import com.auditoria.repository.ReporteGeneratorService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import java.io.File;
import java.io.InputStream;
import java.util.List;

public class MainController {

    @FXML private ListView<Requisito> listViewRequisitos;
    @FXML private Label lblIdRequisito;
    @FXML private Label lblPreguntas;
    @FXML private Label lblDocumentos;
    @FXML private RadioButton radioCumple;
    @FXML private RadioButton radioNoCumple;
    @FXML private RadioButton radioObservacion;
    @FXML private TextArea txtObservaciones;
    @FXML private ComboBox<String> comboExportFormat;
    
    private ToggleGroup groupDictamen;
    private final ObservableList<Requisito> requisitosData = FXCollections.observableArrayList();
    private Requisito requisitoSeleccionado;

    @FXML
    public void initialize() {
        configurarToggleGroup();
        configurarFormatosExportacion();
        cargarDatosIniciales();
        configurarMapeoListView();
    }

    private void configurarToggleGroup() {
        groupDictamen = new ToggleGroup();
        radioCumple.setToggleGroup(groupDictamen);
        radioNoCumple.setToggleGroup(groupDictamen);
        radioObservacion.setToggleGroup(groupDictamen);

        // Listener reactivo para guardar cambios de estado inmediatamente
        groupDictamen.selectedToggleProperty().addListener((obs, oldToggle, newToggle) -> {
            if (requisitoSeleccionado != null && newToggle != null) {
                if (newToggle == radioCumple) requisitoSeleccionado.setEstado(EstadoEvaluacion.CUMPLE);
                if (newToggle == radioNoCumple) requisitoSeleccionado.setEstado(EstadoEvaluacion.NO_CUMPLE);
                if (newToggle == radioObservacion) requisitoSeleccionado.setEstado(EstadoEvaluacion.OBSERVACION);
            }
        });
    }

    private void configurarFormatosExportacion() {
        comboExportFormat.setItems(FXCollections.observableArrayList("WORD (*.docx)", "PDF (*.pdf)", "CSV (*.csv)"));
        comboExportFormat.getSelectionModel().selectFirst();
    }

  private void cargarDatosIniciales() {
    try {
        // Llamamos directamente al proveedor de datos (pasamos null ya que el archivo no es necesario)
        List<Requisito> cargados = com.auditoria.repository.CSVRequisitoLoader.cargarRequisitosDesdeCSV(null);
        
        requisitosData.clear();
        requisitosData.addAll(cargados);
        listViewRequisitos.setItems(requisitosData);
        
        // Seleccionar automáticamente la primera cláusula (4.1) para rellenar la pantalla al arrancar
        if (!requisitosData.isEmpty()) {
            listViewRequisitos.getSelectionModel().selectFirst();
        }
    } catch (Exception e) {
        mostrarAlerta("Error de Carga", "No se pudieron inicializar los requisitos en memoria: " + e.getMessage());
        e.printStackTrace();
    }
}

    private void configurarMapeoListView() {
        // Formato para mostrar solo el ID del requisito en la lista lateral
        listViewRequisitos.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Requisito item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) { setText(null); } 
                else { setText("Cláusula " + item.getId()); }
            }
        });

        // Evento de cambio de selección entre un requisito y otro
        listViewRequisitos.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            desvincularRequisitoAnterior(oldVal);
            vincularNuevoRequisito(newVal);
        });
        
        if(!requisitosData.isEmpty()) {
            listViewRequisitos.getSelectionModel().selectFirst();
        }
    }

    private void desvincularRequisitoAnterior(Requisito anterior) {
        if (anterior != null) {
            // Romper el binding bidireccional de la caja de texto para evitar fugas de memoria
            txtObservaciones.textProperty().unbindBidirectional(anterior.observacionesTextProperty());
        }
    }

    private void vincularNuevoRequisito(Requisito nuevo) {
        if (nuevo == null) return;
        requisitoSeleccionado = nuevo;

        // Cargar metadatos fijos de la norma
        lblIdRequisito.setText(nuevo.getId());
        lblPreguntas.setText(nuevo.getPreguntasClave());
        lblDocumentos.setText(nuevo.getDocumentosSolicitar());

        // Sincronizar el estado del Radio Button
        switch (nuevo.getEstado()) {
            case CUMPLE: radioCumple.setSelected(true);
            case NO_CUMPLE: radioNoCumple.setSelected(true);
            case OBSERVACION: radioObservacion.setSelected(true);
            default: groupDictamen.selectToggle(null);
        }

        // Vincular bidireccionalmente el texto de observaciones
        txtObservaciones.textProperty().bindBidirectional(nuevo.observacionesTextProperty());
    }

    @FXML
    private void handleExportar() {
        String formato = comboExportFormat.getValue();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Guardar Reporte de Auditoría");
        
        if (formato.contains("CSV")) {
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archivo CSV", "*.csv"));
        } else if (formato.contains("WORD")) {
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Documento de Word", "*.docx"));
        } else {
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Documento PDF", "*.pdf"));
        }

        File destino = fileChooser.showSaveDialog(listViewRequisitos.getScene().getWindow());
        if (destino != null) {
            try {
                ReporteGeneratorService.exportarReporte(requisitosData, formato, destino);
                mostrarAlerta("Éxito", "El dictamen definitivo de auditoría ha sido generado correctamente.");
            } catch (Exception e) {
                mostrarAlerta("Error de Exportación", "Ocurrió un error al compilar el formato seleccionado: " + e.getMessage());
            }
        }
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}

