package com.aquapaka;

import com.aquapaka.model.Boat;
import com.aquapaka.model.Entity;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.List;

/**
 * Hello world!
 *
 */
public class FlappyBoat extends Application {
    private static final String BACKGROUND_URL = "/image/Background.png";
    private static final int WINDOW_WIDTH = 1280;
    private static final int WINDOW_HEIGHT = 720;
    private static final double GRAVITY = 0.05;
    private final Pane menuPane = new Pane();
    private final Pane gamePane = new Pane();
    private final Pane uiPane = new Pane();
    private Boat boat;


    private void initMenuPane() {

    }

    private void initGamePane() {
        // Create background image
        Image backgroundImage = new Image(BACKGROUND_URL);
        backgroundImage = new Image(BACKGROUND_URL, backgroundImage.getWidth()*6, 0, true, false);
        ImageView background = new ImageView(backgroundImage);
        // Center background image
        background.setX(-(backgroundImage.getWidth()-WINDOW_WIDTH)/2);
        background.setY(-(backgroundImage.getHeight()-WINDOW_HEIGHT)/2);

        boat = new Boat(120, 300, 0, 0, 0, GRAVITY);

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();
            }
        };
        timer.start();

        gamePane.getChildren().addAll(background, boat);
    }

    private void update() {
        List<Node> nodes = gamePane.getChildren();

        for(Node node : nodes) {
            if(node instanceof Entity) {
                ((Entity) node).update();
            }
        }
    }

    public static void main( String[] args )
    {
        System.out.println( "Starting application..." );
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Scene menuScene = new Scene(menuPane, WINDOW_WIDTH, WINDOW_HEIGHT);
        Scene gameScene = new Scene(gamePane, WINDOW_WIDTH, WINDOW_HEIGHT);

        initMenuPane();
        initGamePane();
        stage.setScene(gameScene);

        gameScene.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case SPACE -> {
                    boat.jump();
                    break;
                }
            }
        });

        stage.setResizable(false);
        stage.show();
    }
}
