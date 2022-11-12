package com.tejasbhovad.upixelapp;

import com.tejasbhovad.upixelapp.applets.upscaleImage;
import javafx.fxml.FXML;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.tejasbhovad.upixelapp.Application.appState;

public class appController {
    public javafx.scene.control.Label selectionLabel;

    //private static Label selection;
    @FXML
    public void onUploadClick() {
        try {
            FileChooser fc = new FileChooser();
            // if we want to open fixed location
            String home = System.getProperty("user.home");
            String initDir = home + "/Downloads/";
            fc.setInitialDirectory(new File(initDir));

            // if we want to fixed file extension
            fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files", "*.*"), new FileChooser.ExtensionFilter("JPG", "*.jpg"), new FileChooser.ExtensionFilter("PNG", "*.png")

            );
            File selectedFile = fc.showOpenDialog(null);

            if (selectedFile != null) {
                Application.imagePath = (selectedFile.getAbsolutePath());
                //System.out.println(Application.imagePath);
                upscaleImage.startUpscale();
                appState = 1;
                //String home = System.getProperty("user.home");
                Path path = Paths.get(Application.imagePath);
                Path fileTMP = path.getFileName();
                String fileName = fileTMP.toString();
                selectionLabel.setText(fileName+" Uploaded");

            } else {
                System.out.println("File is not valid!");
            }


        } catch (Exception InvocationTargetException) {
            System.out.println("File not selected");
        }
    }

    public void onDownloadClick() throws IOException {
        upscaleImage.writePNG();

        appState = 0;
    }


}
