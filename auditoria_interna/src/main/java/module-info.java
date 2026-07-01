module com.auditoria {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.auditoria to javafx.fxml;
    opens com.auditoria.controller to javafx.fxml;
    exports com.auditoria;


}
