package com.aquapaka;

import com.aquapaka.model.Boat;
import com.aquapaka.scene.GameScene;
import com.aquapaka.scene.MenuScene;
import com.aquapaka.state.GameState;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;

/**
 * Hello world!
 *
 */
public class FlappyBoat extends Application {
    private static final String iconUrl = "/image/Player_Boat.png";
    public static final int WINDOW_WIDTH = 1280;
    public static final int WINDOW_HEIGHT = 720;
    public static GameState gameState;

    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        GameScene gameScene = new GameScene(WINDOW_WIDTH, WINDOW_HEIGHT);
        MenuScene menuScene = new MenuScene(stage, gameScene, WINDOW_WIDTH, WINDOW_HEIGHT);

        stage.setScene(menuScene);
        gameState = GameState.IN_MENU;

        stage.setResizable(false);
        stage.setTitle("Flappy Boat");
        stage.getIcons().add(new Image(iconUrl));
        stage.show();
    }
}
