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
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

public class TimerController {
    @FXML private VBox container;
    @FXML private Label clockLabel;
    @FXML private Label treeAmount;
    @FXML private ProgressBar clockProgressBar;
    @FXML private Button toggleBtn;
    @FXML private Button pomodoroBtn;
    @FXML private Button shortBreakBtn;
    @FXML private Button longBreakBtn;

    private CountDown countdown;
    private PomodoroClock clock;
    private Map<Button, TimeMode> buttonToMode;

    private static int treeCount;
   private static TimeMode currentMode = TimeMode.POMODORO;

    @FXML
    public void initialize() throws IOException {
        clock = new PomodoroClock(this, clockLabel, clockProgressBar, TimeMode.POMODORO);
        countdown = new CountDown(TimeMode.POMODORO, clock);
        setTree();
        initializeButtonToMode();
    }

    private void initializeButtonToMode() {
        buttonToMode = new HashMap<>();
        buttonToMode.put(pomodoroBtn, TimeMode.POMODORO);
        buttonToMode.put(shortBreakBtn, TimeMode.SHORT_BREAK);
        buttonToMode.put(longBreakBtn, TimeMode.LONG_BREAK);
        System.out.println("Time Mode: " + TimeMode.POMODORO);
    }

    public void toggleBtnClicked() throws IOException {
        if (countdown.isRunning())
            stop();
        else
            activate();
    }

    private void stop() throws IOException {
        countdown.stop();
        if(toggleBtn.getText() == "STOP") {
            gaveUp();
        }
        updateToggleBtn("RESUME");
    }

    private void gaveUp() throws IOException {
        boolean result = AlertBox.showAlert("Confirmation", "Are you sure to Give Up?");
        if(result){
            addRecord(false);
            reset();
        };
    }

    private void updateToggleBtn(String text) {
        toggleBtn.setText(text);
    }

    private void activate() {
        if (countdown.isTimeUp())
            reset();
        start();
    }

    private void reset() {
        removeTimeIsUpStyles();
        countdown.reset();
    }

    private void removeTimeIsUpStyles() {
        container.getStyleClass().remove("time-is-up-background");
        toggleBtn.getStyleClass().remove("time-is-up-color");
    }

    private void start() {
        countdown.start();
        updateToggleBtn("STOP");
    }

    public void modeBtnClicked(ActionEvent event) {
        Button button = (Button) event.getSource();
        TimeMode mode = buttonToMode.get(button);
        changeMode(mode);
        highlightModeButton(button);
    }

    private void changeMode(TimeMode mode) {
        currentMode = mode;
        countdown.setMode(mode);
        clock.setMode(mode);
        removeTimeIsUpStyles();
//        start();
    }

    private void highlightModeButton(Button button) {
        removeModeButtonHighlighting();
        button.getStyleClass().add("highlight-btn");
    }

    private void removeModeButtonHighlighting() {
        pomodoroBtn.getStyleClass().remove("highlight-btn");
        shortBreakBtn.getStyleClass().remove("highlight-btn");
        longBreakBtn.getStyleClass().remove("highlight-btn");
    }

    public void timeIsUp() throws IOException {
        addTimeIsUpStyles();
        playSound();
        if(currentMode == TimeMode.POMODORO) {
            addTree();
            addRecord(true); // true -> meaning session successfully finished
        }
        updateToggleBtn("RESET");
    }

    private void addTimeIsUpStyles() {
        container.getStyleClass().add("time-is-up-background");
        toggleBtn.getStyleClass().add("time-is-up-color");
    }

    private void playSound() {
        Media sound = new Media(this.getClass().getResource("sound.wav").toString());
        MediaPlayer player = new MediaPlayer(sound);
        player.setVolume(Settings.volume);
        player.play();
    }

    public void settingsIconClicked() throws IOException {
        stop();
        App.setRoot("settings");
    }

    public void reportsIconClicked() throws IOException {
        stop();
        App.setRoot("list");
    }

    public void addTree() throws IOException {
        treeCount++;
        treeAmount.setText(String.valueOf(treeCount));
        BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\Users\\dastan.akatov\\Downloads\\pomodoro-javafx-master\\Pomo\\src\\main\\resources\\DB\\output.txt"));
        writer.write(String.valueOf(treeCount));
        writer.close();
    }

    public void setTree() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\dastan.akatov\\Downloads\\pomodoro-javafx-master\\Pomo\\src\\main\\resources\\DB\\output.txt"));
        int number = Integer.parseInt(reader.readLine().trim());
        reader.close();
        treeCount = number;
        treeAmount.setText(String.valueOf(treeCount));
    }

    public void addRecord(boolean status) throws IOException {
        String currentDate = CurrentDateTime.getCurrentDate();
        String lengthOfSession = currentMode.getMinutes() + (currentMode.getMinutes() == 1 ? " min": "mins");
        String actionStatus = status? "Completed": "Failed";
        writeTableData(lengthOfSession, currentDate, actionStatus);
        readTableData();
    }

    public void readTableData() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(Settings.filePathTable));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] rowData = line.split(",");
            String lengthOfSession = rowData[0];
            String currentDateTime = rowData[1];
            String status = rowData[2];

            // Process the data as needed
            System.out.println("Length of Session: " + lengthOfSession);
            System.out.println("Current DateTime: " + currentDateTime);
            System.out.println("Status: " + status);
            System.out.println("----------------------");
            }
        reader.close();
    }


    public void writeTableData(String data1, String data2, String data3) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(Settings.filePathTable, true));
        String line = data1 + "," + data2 + "," + data3;
        writer.write(line);
        writer.newLine();
        System.out.println(line + "\nTable data has been written to the file.");
        writer.close();
    }
}
