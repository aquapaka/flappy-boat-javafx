package com.aquapaka.scene;

import com.aquapaka.FlappyBoat;
import com.aquapaka.model.Background;
import com.aquapaka.state.GameState;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MenuScene extends Scene {
    private final Stage stage;
    private final GameScene gameScene;

    public MenuScene(Stage stage, GameScene gameScene, double v, double v1) {
        super(new Pane(), v, v1);
        this.stage = stage;
        this.gameScene = gameScene;
        initMenuPane();
    }

    private void initMenuPane() {
        Pane menuPane = (Pane) getRoot();
        ImageView background = new Background();

        setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case S -> {
                    stage.setScene(gameScene);
                    FlappyBoat.gameState = GameState.WAITING_FOR_START;
                    break;
                }
            }
        });

        menuPane.getChildren().addAll(background);
    }
}
