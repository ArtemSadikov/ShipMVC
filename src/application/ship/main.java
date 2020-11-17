package application.ship;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class main extends Application {
    static int speed = 5;
    static int width = 50;
    static int height = 30;
    static int cornersize = 25;
    static List<Corner> ship = new ArrayList<>();
    static Dir direction = Dir.left;
    static boolean gameOver = false;
    static Random rand = new Random();

    public enum Dir {
        left, right, up, down
    }

    public static class Corner{
        int x;
        int y;

        public Corner(int x, int y){
            this.x = x;
            this.y = y;
        }
    }

    public void start(Stage primaryStage) {
        try{
            VBox root = new VBox();
            Canvas c = new Canvas(width*cornersize, height*cornersize);
            GraphicsContext gc = c.getGraphicsContext2D();
            root.getChildren().add(c);

            new AnimationTimer(){
                long lastTick = 0;

                public void handle(long now){
                    if(lastTick == 0){
                        lastTick = now;
                        tick(gc);
                        return;
                    }

                    if(now - lastTick > 1000000000/speed){
                        lastTick = now;
                        tick(gc);
                    }
                }
            }.start();

            Scene scene = new Scene(root, width*cornersize, height*cornersize);

            //controler
            scene.addEventFilter(KeyEvent.KEY_PRESSED, key ->{
                if (key.getCode() == KeyCode.W) {
                    direction = Dir.up;
                }
                if (key.getCode() == KeyCode.A) {
                    direction = Dir.left;
                }
                if (key.getCode() == KeyCode.S) {
                    direction = Dir.down;
                }
                if (key.getCode() == KeyCode.D) {
                    direction = Dir.right;
                }

            });

            // add start ship
            ship.add(new Corner(width/2, height/2));
            ship.add(new Corner(width/2,height/2));

            primaryStage.setScene(scene);
            primaryStage.setTitle("SHIPS GAME");
            primaryStage.show();
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    //tick
    public static void tick(GraphicsContext gc) {
        if(gameOver){
            gc.setFill(Color.RED);
            gc.setFont(new Font("", 50));
            gc.fillText("GAME OVER", 100, 250);
            return;
        }

        for(int i = ship.size() -1; i >=1; i--) {
            ship.get(i).x = ship.get(i-1).x;
            ship.get(i).y = ship.get(i-1).y;
        }

        switch (direction) {
            case up:
                ship.get(0).y--;
                if(ship.get(0).y < 0){
                    gameOver = true;
                }
                break;
            case down:
                ship.get(0).y++;
                if(ship.get(0).y > height){
                    gameOver = true;
                }
                break;
            case left:
                ship.get(0).x--;
                if(ship.get(0).x < 0){
                    gameOver = true;
                }
                break;
            case right:
                ship.get(0).x++;
                if(ship.get(0).x > width){
                    gameOver = true;
                }
                break;
        }

        //fill background
        gc.setFill(Color.LIGHTBLUE);
        gc.fillRect(0,0,width*cornersize,height*cornersize);

        //ship
        for (Corner c: ship) {
            gc.setFill(Color.DARKORANGE);
            gc.fillRect(c.x*cornersize,c.y*cornersize, cornersize, cornersize);
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
