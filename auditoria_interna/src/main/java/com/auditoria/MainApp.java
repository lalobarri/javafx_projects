package com.auditoria;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

/**
 * Clase principal de arranque adaptada a la estructura nativa de navegación de JavaFX.
 */
public class MainApp extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        // Se inicializa la escena cargando la vista principal de auditoría
        // Se aumenta el tamaño (1100x700) para garantizar una interfaz cómoda para el auditor
        Parent root = loadFXML("MainView");
        scene = new Scene(root, 1100, 700);
        
        // Inyección opcional y segura de la hoja de estilos CSS si existe en la misma ruta
        URL cssUrl = MainApp.class.getResource("MainView.css"); // O "styles.css" según lo nombres
        if (cssUrl != null) {
            scene.getStylesheets().add(cssUrl.toExternalForm());
        }

        stage.setTitle("Módulo de Auditoría de Sistemas de Gestión - ISO 9001 / ISO 21001");
        stage.setMinWidth(950);
        stage.setMinHeight(600);
        stage.setScene(scene);
        stage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        // Busca el archivo .fxml en el mismo paquete/directorio donde reside la clase App
        URL fxmlUrl = MainApp.class.getResource(fxml + ".fxml");
        if (fxmlUrl == null) {
            throw new IOException("No se pudo encontrar el archivo: " + fxml + ".fxml en el classpath.");
        }
        FXMLLoader fxmlLoader = new FXMLLoader(fxmlUrl);
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }
}
