package com.example.tfgapp;

import android.os.Build;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Scanner;

public class EnvReader {

    private final HashMap<String, String> envVariables;


    public EnvReader(String fileName) throws IOException, VersionError {
        envVariables = new HashMap<>();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Path path = Paths.get(fileName);
            Scanner scanner = new Scanner(path);
            while(scanner.hasNextLine()){
                String line = scanner.nextLine();
                String[] splitLine = line.split("=");
                envVariables.put(splitLine[0],splitLine[1]);
            }
        }else{
            throw new VersionError(("" + Build.VERSION_CODES.O));
        }
    }

    public EnvReader(File file) throws FileNotFoundException, VersionError {
        envVariables = new HashMap<>();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Scanner scanner = new Scanner(file);
            while(scanner.hasNextLine()){
                String line = scanner.nextLine();
                String[] splitLine = line.split("=");
                envVariables.put(splitLine[0],splitLine[1]);
            }
        }else{
            throw new VersionError(("" + Build.VERSION_CODES.O));
        }
    }

    public String getValue(String key){
        return envVariables.get(key);
    }


}
