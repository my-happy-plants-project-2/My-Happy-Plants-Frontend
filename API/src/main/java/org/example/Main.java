package org.example;
import org.example.config.AppConfig;

public class Main {
    public static void main(String[] args) {
        AppConfig.configureApp().start(8080);
    }
}