package com.auditoria.model;

import javafx.beans.property.*;

/**
 * Clase que representa el Modelo de un Requisito de la norma.
 * Utiliza propiedades de JavaFX para permitir la reactividad con la Vista (MVC).
 */
public class Requisito {
    
    // Propiedades reactivas de JavaFX
    private final StringProperty id = new SimpleStringProperty();
    private final StringProperty preguntasClave = new SimpleStringProperty();
    private final StringProperty documentosSolicitar = new SimpleStringProperty();
    private final ObjectProperty<EstadoEvaluacion> estado = new SimpleObjectProperty<>(EstadoEvaluacion.PENDIENTE);
    private final StringProperty observacionesText = new SimpleStringProperty("");

    /**
     * Constructor principal para inicializar un requisito con los datos del CSV.
     */
    public Requisito(String id, String preguntasClave, String documentosSolicitar) {
        this.id.set(id);
        this.preguntasClave.set(preguntasClave);
        this.documentosSolicitar.set(documentosSolicitar);
    }

    // =========================================================================
    // MÉTODOS PROPERTY (Esenciales para el Data Binding automático en la UI)
    // =========================================================================
    
    public StringProperty idProperty() { 
        return id; 
    }

    public StringProperty preguntasClaveProperty() { 
        return preguntasClave; 
    }

    public StringProperty documentosSolicitarProperty() { 
        return documentosSolicitar; 
    }

    public ObjectProperty<EstadoEvaluacion> estadoProperty() { 
        return estado; 
    }

    public StringProperty observacionesTextProperty() { 
        return observacionesText; 
    }

    // =========================================================================
    // GETTERS Y SETTERS ESTÁNDAR (Para lógica de negocio y persistencia/exportación)
    // =========================================================================

    public String getId() { 
        return id.get(); 
    }
    
    public void setId(String id) { 
        this.id.set(id); 
    }

    public String getPreguntasClave() { 
        return preguntasClave.get(); 
    }
    
    public void setPreguntasClave(String preguntasClave) { 
        this.preguntasClave.set(preguntasClave); 
    }

    public String getDocumentosSolicitar() { 
        return documentosSolicitar.get(); 
    }
    
    public void setDocumentosSolicitar(String documentosSolicitar) { 
        this.documentosSolicitar.set(documentosSolicitar); 
    }

    public EstadoEvaluacion getEstado() { 
        return estado.get(); 
    }
    
    public void setEstado(EstadoEvaluacion estado) { 
        this.estado.set(estado); 
    }

    public String getObservacionesText() { 
        return observacionesText.get(); 
    }
    
    public void setObservacionesText(String observacionesText) { 
        this.observacionesText.set(observacionesText); 
    }
}
