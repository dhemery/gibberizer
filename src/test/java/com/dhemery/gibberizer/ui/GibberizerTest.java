package com.dhemery.gibberizer.ui;

import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import static com.google.common.truth.Truth.assertThat;

public class GibberizerTest extends ApplicationTest {
    Scene scene;
    @Override
    public void start(Stage stage) throws Exception {
        scene = new Scene(new GibberizerPane(), 800, 600);
        stage.setScene(scene);
        stage.show();
    }

    @Test
    public void foo() {
        assertThat(window(scene).isShowing()).isTrue();
    }
}
