module com.group7 {

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

    opens com.group7 to javafx.fxml;
    exports com.group7;
    exports com.group7.controllers;
    opens com.group7.controllers to javafx.fxml;
    exports com.group7.model;
    opens com.group7.model to javafx.fxml;
    exports com.group7.application;
    opens com.group7.application to javafx.fxml;
    exports com.group7.View;
    opens com.group7.View to javafx.fxml;
}