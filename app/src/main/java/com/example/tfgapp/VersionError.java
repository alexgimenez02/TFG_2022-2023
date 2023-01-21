package com.example.tfgapp;

public class VersionError extends Throwable {
    VersionError(String version){
        super();
        System.err.println("Current build sdf version is smaller than " + version);
    }
}
