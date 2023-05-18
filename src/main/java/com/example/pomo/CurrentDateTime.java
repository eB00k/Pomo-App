package com.example.pomo;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

public class CurrentDateTime {
    public static String getCurrentDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        System.out.println();
        return formatter.format(date);
    }
}