package com.tejasbhovad.upixelapp;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;


import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class Application extends javafx.application.Application {
    public static String imagePath;
    public static int appState = 0;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("UI.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setMinWidth(600);
        stage.setMinHeight(400);
        stage.centerOnScreen();
        stage.setTitle("Upixel");
        final Toolkit defaultToolkit = Toolkit.getDefaultToolkit();
        final URL imageResource = Main.class.getClassLoader().getResource("logo.png");
        final java.awt.Image image = defaultToolkit.getImage(imageResource);
        final Taskbar taskbar = Taskbar.getTaskbar();

        try {
            //set icon for mac os (and other systems which do support this method)
            taskbar.setIconImage(image);
        } catch (final UnsupportedOperationException e) {
            System.out.println("The os does not support: 'taskbar.setIconImage'");
        } catch (final SecurityException e) {
            System.out.println("There was a security exception for: 'taskbar.setIconImage'");
        }
        stage.getIcons().add(new Image(Objects.requireNonNull(Application.class.getResourceAsStream("/logo.png"))));
        stage.setScene(scene);
        stage.setResizable(false);

        stage.show();





    }

    public static void main(String[] args) {
        launch();
    }
}