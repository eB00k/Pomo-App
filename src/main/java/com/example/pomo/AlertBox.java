package com.example.pomo;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class AlertBox {

    public static boolean status; // if true then user gave up session

    public static boolean showAlert(String title, String message) {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(250);

        Label label = new Label();
        label.setText(message);

        Button closeButton = new Button("Cancel");
        Button gaveUpButton = new Button("Give Up");

        closeButton.setOnAction(e -> {
            status = false;
            window.close();
        });
        gaveUpButton.setOnAction(e -> {
            status = true;
            window.close();
        });

        HBox buttonBox = new HBox(10);
        buttonBox.getChildren().addAll(closeButton, gaveUpButton);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setFillHeight(true);
        HBox.setHgrow(closeButton, Priority.ALWAYS);
        HBox.setHgrow(gaveUpButton, Priority.ALWAYS);

        BorderPane layout = new BorderPane();
        layout.setTop(label);
        BorderPane.setAlignment(label, Pos.CENTER);
        BorderPane.setMargin(label, new javafx.geometry.Insets(10));

        layout.setBottom(buttonBox);
        BorderPane.setAlignment(buttonBox, Pos.CENTER);
        BorderPane.setMargin(buttonBox, new javafx.geometry.Insets(10));

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.centerOnScreen();
        window.showAndWait();

        return status;
    }
}
