package com.example.pomo;

import com.example.pomo.model.CountDown;
import com.example.pomo.model.TimeMode;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class ListController {

    @FXML private ProgressBar clockProgressBar;

    @FXML private VBox container;

    @FXML private Label treeAmount;
    private static int treeCount;

    @FXML
    public void initialize() throws IOException {
        setTree();
    }

    public void backIconClicked() throws IOException {
        App.setRoot("timer");
    }

    public void setTree() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\dastan.akatov\\Downloads\\pomodoro-javafx-master\\Pomo\\src\\main\\resources\\DB\\output.txt"));
        int number = Integer.parseInt(reader.readLine().trim());
        reader.close();
        treeCount = number;
        treeAmount.setText(String.valueOf(treeCount));
    }
}
