package com.group7.application;

import com.group7.ConfigManager;

public class GUIStarter {

    public static void main(final String[] args) {
        //ConfigManager.createFile();
        ConfigManager.readFile();
        HelloApplication.main(args);
    }
}