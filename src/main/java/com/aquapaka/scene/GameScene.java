package com.aquapaka.scene;

import com.aquapaka.FlappyBoat;
import com.aquapaka.model.Background;
import com.aquapaka.model.Boat;
import com.aquapaka.model.Enemy;
import com.aquapaka.model.Entity;
import com.aquapaka.state.GameState;
import com.aquapaka.ui.TextFactory;
import javafx.animation.AnimationTimer;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.util.List;
import java.util.Random;

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

        // create playing ui pane
        StackPane playingUiPane = new StackPane();
        playingUiPane.setMinWidth(gamePane.getWidth());
        playingUiPane.setMinHeight(gamePane.getHeight());
        Text instructionText = TextFactory.getText("INSTRUCTION: Press SPACE to Jump, try to dodge bullets and enemies", 32);
        instructionText.setTranslateY(-200);
        playingUiPane.getChildren().addAll(instructionText);

        for (Node node : playingUiPane.getChildren()) {
            if(node instanceof Text) {
                ((Text) node).setTextAlignment(TextAlignment.CENTER);
                playingUiPane.setAlignment(Pos.CENTER);
            }
        }

        // Create ui pane
        StackPane gameOverUiPane = new StackPane();
        gameOverUiPane.setMinWidth(gamePane.getWidth());
        gameOverUiPane.setMinHeight(gamePane.getHeight());
        Text gameOverText = TextFactory.getText("Game Over", 94);
        gameOverText.setTranslateY(-40);
        Text scoreText = TextFactory.getText("Score: 0", 48);
        scoreText.setTranslateY(40);
        gameOverUiPane.getChildren().addAll(gameOverText, scoreText);

        for (Node node : gameOverUiPane.getChildren()) {
            if(node instanceof Text) {
                ((Text) node).setTextAlignment(TextAlignment.CENTER);
                gameOverUiPane.setAlignment(Pos.CENTER);
            }
        }
        gameOverUiPane.setVisible(false);

        boat = new Boat(120, 300,   0, 0, 0, GRAVITY);

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

                    // Generate enemies
                    Random randomPosY = new Random(System.currentTimeMillis());
                    gamePane.getChildren().add(new Enemy(FlappyBoat.WINDOW_WIDTH, randomPosY.nextInt(FlappyBoat.WINDOW_HEIGHT), 0, 0, -0.05, 0));

                    // Move ui to front so enemy will not overlap ui
                    playingUiPane.toFront();
                    gameOverUiPane.toFront();
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
                        instructionText.setVisible(false);
                        boat.jump();
                    }
                    break;
                }

                case ENTER -> {
                    if(FlappyBoat.gameState == GameState.GAME_OVER) {
                        FlappyBoat.gameState = GameState.WAITING_FOR_START;
                    }
                }
            }
        });

        gamePane.getChildren().addAll(background1, background2, boat, playingUiPane, gameOverUiPane);
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
