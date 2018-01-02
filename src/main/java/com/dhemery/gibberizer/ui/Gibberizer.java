package com.dhemery.gibberizer.ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Gibberizer extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Gibberizer");
        Scene scene = new Scene(new GibberizerPane());
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String args[]) {
        launch(Gibberizer.class, args);
    }
}
