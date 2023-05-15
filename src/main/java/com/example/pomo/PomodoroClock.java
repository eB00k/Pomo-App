package com.example.pomo;

import com.example.pomo.model.CountDownObserver;
import com.example.pomo.model.TimeMode;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

import java.io.IOException;

/*
 * Observers CountDown instance
 * Takes care of displaying progress
 * through Label and ProgressBar
 */
class PomodoroClock implements CountDownObserver {
    private final TimerController controller;
    private final Label clockLabel;
    private final ProgressBar progressBar;
    private TimeMode mode;

    public PomodoroClock(TimerController controller,
                         Label clockLabel,
                         ProgressBar progressBar,
                         TimeMode mode) {
        this.controller = controller;
        this.clockLabel = clockLabel;
        this.progressBar = progressBar;
        setMode(mode);
    }

    @Override
    public void update(int seconds) {
        Platform.runLater(() -> {
            clockLabel.setText(secondsToString(seconds));
            updateProgressBar(seconds);
        });
    }

    @Override
    public void timeIsUp() {
        Platform.runLater(() -> {
            try {
                controller.timeIsUp();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private String secondsToString(int seconds) {
        int minutes = seconds / 60;
        seconds = seconds % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

    private void updateProgressBar(int seconds) {
        progressBar.setProgress((float) seconds / mode.getSeconds());
    }

    public void setMode(TimeMode mode) {
        this.mode = mode;
        clockLabel.setText(secondsToString(mode.getSeconds()));
        progressBar.setProgress(1);
    }
}
