package com.example.pomo.model;

public interface CountDownObserver {
    void update(int seconds);
    void timeIsUp();
}
