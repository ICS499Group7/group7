package com.group7;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class ConfigManager {
    public static void createFile() {
        try {
            FileWriter testFile = new FileWriter("config.txt");

            testFile.write("Database Config");

            testFile.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void readFile() {
        try{
            File file = new File("config.txt");
            Scanner scanner = new Scanner(file);


            scanner.close();

        } catch(IOException e) {
            e.printStackTrace();
        }
    }


}
