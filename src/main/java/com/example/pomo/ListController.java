package com.example.pomo;

import com.example.pomo.model.CountDown;
import com.example.pomo.model.TimeMode;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class ListController {

    @FXML private ProgressBar clockProgressBar;

    @FXML private VBox container;

    @FXML
    private TableView<TableRowData> tableView;

    @FXML
    private TableColumn<TableRowData, String> lengthOfSessionColumn;

    @FXML
    private TableColumn<TableRowData, String> currentDateTimeColumn;

    @FXML
    private TableColumn<TableRowData, String> statusColumn;

    @FXML private Label treeAmount;
    private static int treeCount;

    public void initialize() throws IOException {
        setTree();
        // Initialize the table columns
        lengthOfSessionColumn.setCellValueFactory(new PropertyValueFactory<>("lengthOfSession"));
        currentDateTimeColumn.setCellValueFactory(new PropertyValueFactory<>("currentDateTime"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        // Read table data from the file and populate the TableView
        BufferedReader reader = new BufferedReader(new FileReader(String.valueOf(Settings.filePathTable)));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] values = line.split(",");
            if (values.length == 3) {
                String lengthOfSession = values[0];
                String currentDateTime = values[1];
                String status = values[2];
                tableView.getItems().add(new TableRowData(lengthOfSession, currentDateTime, status));
            }
        }
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
