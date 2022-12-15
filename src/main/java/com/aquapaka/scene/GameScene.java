package com.aquapaka.scene;

import com.aquapaka.FlappyBoat;
import com.aquapaka.model.*;
import com.aquapaka.state.GameState;
import com.aquapaka.ui.TextFactory;
import javafx.animation.AnimationTimer;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.util.List;
import java.util.Random;

public class GameScene extends Scene {
    private static final long CLEAN_ENTITY_TIME_INTERVAL = 5000;
    private static final long SPAWN_ENEMY_TIME_INTERVAL = 3000;
    private static final double BOAT_START_SPEED = 2;
    private static final double GRAVITY = 0.05;
    private static final int BOAT_SPAWN_POS_X = 120;
    private static final int BOAT_SPAWN_POS_Y = 300;
    private final Random random = new Random(System.currentTimeMillis());
    private Boat boat;
    private double currentBoatFlySpeed;
    private Background background1;
    private Background background2;
    private StackPane playingUiPane;
    private StackPane gameOverUiPane;
    private Text instructionText;
    private Text playingScoreText;
    private Text gameOverScoreText;
    private long startTime;
    private int score;

    public GameScene(double v, double v1) {
        super(new Pane(), v, v1);
        initGameScene();
    }

    private void initPlayingUiPane() {
        Pane gamePane = (Pane) getRoot();

        playingUiPane = new StackPane();
        playingUiPane.setMinWidth(gamePane.getWidth());
        playingUiPane.setMinHeight(gamePane.getHeight());
        instructionText = TextFactory.getText("INSTRUCTION: Press SPACE to Jump, try to dodge bullets and enemies", 32);
        instructionText.setTranslateY(-200);
        playingScoreText = TextFactory.getText("", 48);
        playingScoreText.setTranslateY(-340);
        playingUiPane.getChildren().addAll(instructionText, playingScoreText);

        for (Node node : playingUiPane.getChildren()) {
            if(node instanceof Text) {
                ((Text) node).setTextAlignment(TextAlignment.CENTER);
                playingUiPane.setAlignment(Pos.CENTER);
            }
        }
    }

    private void initGameOverUiPane() {
        Pane gamePane = (Pane) getRoot();

        // Create game over ui pane
        gameOverUiPane = new StackPane();
        gameOverUiPane.setMinWidth(gamePane.getWidth());
        gameOverUiPane.setMinHeight(gamePane.getHeight());
        Text gameOverText = TextFactory.getText("Game Over", 94);
        gameOverText.setTranslateY(-100);
        gameOverScoreText = TextFactory.getText("Score: 0", 48);
        gameOverScoreText.setTranslateY(0);
        Text tryAgainText = TextFactory.getText("Press ENTER to try again", 48);
        tryAgainText.setTranslateY(100);
        gameOverUiPane.getChildren().addAll(gameOverText, gameOverScoreText, tryAgainText);

        for (Node node : gameOverUiPane.getChildren()) {
            if(node instanceof Text) {
                ((Text) node).setTextAlignment(TextAlignment.CENTER);
                gameOverUiPane.setAlignment(Pos.CENTER);
            }
        }
        gameOverUiPane.setVisible(false);
    }

    private void initBackground() {
        // Create 2 background next to each other
        background1 = new Background();
        background2 = new Background();
        double backgroundWidth = background1.getImage().getWidth();
        background1.setVelocityX(-BOAT_START_SPEED);
        background2.setVelocityX(-BOAT_START_SPEED);
        background2.setX(background1.getX() + backgroundWidth);
    }

    private void initGameScene() {
        initPlayingUiPane();
        initGameOverUiPane();
        initBackground();

        boat = new Boat(BOAT_SPAWN_POS_X, BOAT_SPAWN_POS_Y,   0, 10, 0, GRAVITY);
        currentBoatFlySpeed = BOAT_START_SPEED;

        Pane gamePane = (Pane) getRoot();
        gamePane.getChildren().addAll(background1, background2, boat, playingUiPane, gameOverUiPane);

        AnimationTimer timer = new AnimationTimer() {
            private long prevSpawnTime;
            private long spawnTimeInteral = SPAWN_ENEMY_TIME_INTERVAL;
            private long prevCleanTime;

            @Override
            public void handle(long now) {
                if(FlappyBoat.gameState == GameState.PLAYING || FlappyBoat.gameState == GameState.GAME_OVER) {
                    update();
                    addScore(0);

                    // Loop position of the background that leaved the screen
                    double backgroundWidth = background1.getImage().getWidth();
                    if(background1.getX() + backgroundWidth < 0) {
                        background1.setX(background2.getX() + backgroundWidth);
                    }
                    if(background2.getX() + backgroundWidth < 0) {
                        background2.setX(background1.getX() + backgroundWidth);
                    }

                    long nowTime = System.currentTimeMillis();
                    // Update game time
                    long gameTime = (nowTime - startTime) / 1000;

                    // Generate enemy after certain time
                    if(nowTime - prevSpawnTime > spawnTimeInteral) {
                        spawnEnemy();
                        spawnTimeInteral = random.nextLong(Math.max(SPAWN_ENEMY_TIME_INTERVAL - gameTime * 50, 500));
                        prevSpawnTime = nowTime;
                    }

                    // Remove out of screen entities after certain time
                    if(nowTime - prevCleanTime > CLEAN_ENTITY_TIME_INTERVAL) {
                        cleanEntities();
                        prevCleanTime = nowTime;
                    }

                    // Increase boat fly speed over time
                    currentBoatFlySpeed += 0.0001;
                    // Update background speed to match boat fly speed
                    background1.setVelocityX(-currentBoatFlySpeed);
                    background2.setVelocityX(-currentBoatFlySpeed);

                    // Check game over conditions
                    for(Node node : gamePane.getChildren()) {
                        if(!boat.isDead()) {
                            // Game over if player touch enemy
                            if(!(node instanceof Enemy enemy)) continue;
                            if(enemy.getBoundsInParent().intersects(boat.getBoundsInParent())) {
                                gameOver();
                            }

                            // Game over if player jump too high
                            if(boat.getY() < -100) {
                                gameOver();
                            }

                            // Game over if player fall below screen
                            if(boat.getY() > FlappyBoat.WINDOW_HEIGHT) {
                                gameOver();
                            }
                        }

                        // Mark enemy is dead if it touches player bullet
                        for(Node node2 : gamePane.getChildren()) {
                            if(!(node instanceof Enemy enemy)) continue;
                            if(!(node2 instanceof Bullet bullet)) continue;
                            if(!(bullet.getOwner() instanceof Boat)) continue;

                            if(enemy.getBoundsInParent().intersects(bullet.getBoundsInParent())) {
                                enemy.setDead(true);
                                bullet.setDead(true);
                                addScore(50);
                            }
                        }
                    }

                    // Remove all dead bullet and enemy
                    gamePane.getChildren().removeIf(node -> {
                        if(node instanceof Enemy || node instanceof Bullet) {
                            if(((Entity) node).isDead()) return true;
                        }
                        return false;
                    });
                }
            }
        };
        timer.start();

        // Setup event handlers
        setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case SPACE -> {
                    if(FlappyBoat.gameState == GameState.WAITING_FOR_START) {
                        FlappyBoat.gameState = GameState.PLAYING;
                        instructionText.setVisible(false);
                        playingScoreText.setVisible(true);
                        startTime = System.currentTimeMillis();
                        boat.jump();
                        boat.shoot((Pane) getRoot());
                    }

                    if(FlappyBoat.gameState == GameState.PLAYING) {
                        boat.jump();
                        boat.shoot((Pane) getRoot());
                    }
                }

                case ENTER -> {
                    if(FlappyBoat.gameState == GameState.GAME_OVER) {
                        resetGameScene();
                    }
                }
            }
        });
    }

    private void update() {
        Pane gamePane = (Pane) getRoot();
        List<Node> nodes = gamePane.getChildren();

        for(Node node : nodes) {
            if(node instanceof Entity entity) {
                entity.update();
            }
        }
    }

    private void addScore(int addScore) {
        score += addScore;
        playingScoreText.textProperty().set(String.format("%09d", score));
    }

    private void spawnEnemy() {
        int spawnLocationX = FlappyBoat.WINDOW_WIDTH;
        int spawnLocationY = random.nextInt(FlappyBoat.WINDOW_HEIGHT);
        double enemyVelocityX = 0 + random.nextDouble(5);

        ((Pane)getRoot()).getChildren().add(new Enemy(spawnLocationX, spawnLocationY, - enemyVelocityX - currentBoatFlySpeed, 0, -0.01, 0));

        // Move ui to front so enemy will not overlap ui
        playingUiPane.toFront();
        gameOverUiPane.toFront();
    }

    private void cleanEntities() {
        ((Pane)getRoot()).getChildren().removeIf(node -> {
            if (!(node instanceof Entity entity)) return false;
            if (node instanceof Background || node instanceof Boat) return false;
            return entity.getX() < -200 || entity.getX() > FlappyBoat.WINDOW_WIDTH + 200 || entity.getY() < -200 || entity.getY() > FlappyBoat.WINDOW_HEIGHT + 200;
        });
    }

    private void gameOver() {
        FlappyBoat.gameState = GameState.GAME_OVER;
        boat.setDead(true);
        boat.setVelocityX(-1);
        boat.setVelocityY(-2);
        playingScoreText.setVisible(false);
        gameOverScoreText.textProperty().set(String.format("Score: %d", score));
        gameOverUiPane.setVisible(true);
    }

    private void resetGameScene() {
        FlappyBoat.gameState = GameState.WAITING_FOR_START;
        score = 0;
        currentBoatFlySpeed = BOAT_START_SPEED;
        playingScoreText.setVisible(false);
        instructionText.setVisible(true);
        gameOverUiPane.setVisible(false);
        boat.setX(BOAT_SPAWN_POS_X);
        boat.setY(BOAT_SPAWN_POS_Y);
        boat.setDead(false);
        boat.setVelocityX(0);
        boat.setVelocityY(10);
        boat.setRotate(0);
        ((Pane)getRoot()).getChildren().removeIf(node -> node instanceof Enemy);
    }
}
