package com.aquapaka.scene;

import com.aquapaka.FlappyBoat;
import com.aquapaka.model.Background;
import com.aquapaka.model.Boat;
import com.aquapaka.model.Entity;
import com.aquapaka.state.GameState;
import javafx.animation.AnimationTimer;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

import java.util.List;

public class GameScene extends Scene {
    private static final double START_SPEED = 1;
    private static final double GRAVITY = 0.05;
    private Boat boat;

    public GameScene(double v, double v1) {
        super(new Pane(), v, v1);
        initGameScene();
    }

    private void initGameScene() {
        Pane gamePane = (Pane) getRoot();
        double boatSpeed = START_SPEED;

        // Create 2 background next to each other
        Background background1 = new Background();
        Background background2 = new Background();
        double backgroundWidth = background1.getImage().getWidth();
        background1.setVelocityX(-boatSpeed);
        background2.setVelocityX(-boatSpeed);
        background2.setX(background1.getX() + backgroundWidth);

        // Create ui pane
        Pane gameOverUiPane = new Pane();
        gameOverUiPane.setVisible(false);

        boat = new Boat(120, 300, 0, 0, 0, GRAVITY);

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if(FlappyBoat.gameState == GameState.PLAYING || FlappyBoat.gameState == GameState.GAME_OVER) {
                    update();

                    // Loop position of the background that leaved the screen
                    if(background1.getX() + backgroundWidth < 0) {
                        background1.setX(background2.getX() + backgroundWidth);
                    }
                    if(background2.getX() + backgroundWidth < 0) {
                        background2.setX(background1.getX() + backgroundWidth);
                    }

                    // Game over if player fall below screen
                    if(boat.getY() > FlappyBoat.WINDOW_HEIGHT) {
                        FlappyBoat.gameState = GameState.GAME_OVER;
                        gameOverUiPane.setVisible(true);
                    }
                }
            }
        };
        timer.start();

        // Setup event handlers
        setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case SPACE -> {
                    if(FlappyBoat.gameState == GameState.WAITING_FOR_START || FlappyBoat.gameState == GameState.PLAYING) {
                        FlappyBoat.gameState = GameState.PLAYING;
                        boat.jump();
                    }
                    break;
                }
            }
        });

        gamePane.getChildren().addAll(background1, background2, boat, gameOverUiPane);
    }

    private void update() {
        Pane gamePane = (Pane) getRoot();
        List<Node> nodes = gamePane.getChildren();

        for(Node node : nodes) {
            if(node instanceof Entity) {
                ((Entity) node).update();
            }
        }
    }
}
