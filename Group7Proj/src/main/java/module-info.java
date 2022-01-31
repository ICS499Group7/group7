module com.testproject {

    requires javafx.graphics;
    requires java.sql;
    requires mysql.connector.java;

    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;

    opens com.testproject to javafx.fxml;
    exports com.testproject;
}