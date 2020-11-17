package application.ship;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class ShipGame extends Application {

    //переменные
    private static final Random RAND = new Random();
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private static final int PLAYER_SIZE = 60;

    static final Image PLAYER_IMG = new Image("file:C:/Users/Umaru/Desktop/11037/универ/4 курс/java/ShipMVC/img/B2.img");
    static final Image BOTS_IMG = new Image("file:/img/4.img");

    final int MAX_BOTS = 10, MAX_SHOTS = MAX_BOTS * 2;
    boolean gameOver = false;
    private GraphicsContext gc;

    Ship player;
    List<Shot> shots;
    List<Bot> Bots;

    private double mouseX;
    private double mouseY;

    //start
    public void start(Stage stage) throws Exception {
        Canvas canvas = new Canvas(WIDTH,HEIGHT);
        gc = canvas.getGraphicsContext2D();
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(100), e ->run(gc)));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
        canvas.setCursor(Cursor.MOVE);
        canvas.setOnMouseMoved(e -> mouseX = e.getX());
        //canvas.setOnMouseMoved(c -> mouseY = c.getY());
        canvas.setOnMouseClicked(e ->{
            if(shots.size() < MAX_SHOTS) shots.add(player.shoot());
            if(gameOver) {
                gameOver = false;
                setup();
            }
        });
        setup();
        stage.setScene(new Scene(new StackPane(canvas)));
        stage.setTitle("ShipShip");
        stage.show();
    }

    //setup the game
    private void setup() {
        shots = new ArrayList<>();
        Bots = new ArrayList<>();
        player = new Ship(WIDTH / 2, HEIGHT - PLAYER_SIZE, PLAYER_SIZE, PLAYER_IMG);
        IntStream.range(0, MAX_BOTS).mapToObj(i -> this.newBot()).forEach(Bots::add);
    }

    //run Graphic
    private void run(GraphicsContext gc) {
        gc.setFill(Color.BLACK);
        gc.fillRect(0,0,WIDTH,HEIGHT);
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setFont(Font.font(20));

        if(gameOver) {
            gc.setFont(Font.font(35));
            gc.setFill(Color.ORANGE);
            gc.fillText("GAME OVER!", WIDTH /2, HEIGHT / 2);
        }

        player.draw();
        player.x = (int) mouseX;
        //player.y = (int) mouseY;

        Bots.stream();

        for (int i = shots.size() - 1; i>=0; i--) {
            Shot shot = shots.get(i);
            if(shot.y < 0 || shot.toRemove) {
                shots.remove(i);
                continue;
            }
            shot.update();
            shot.draw();

            for(Bot bot : Bots) {
                if(shot.colide(bot)){
                    shot.toRemove = true;
                }
            }
        }

        for(int i =Bots.size() - 1; i>=0; i--) {
            if(Bots.get(i).destroyed) {
                Bots.set(i,newBot());
            }
        }

        gameOver = player.destroyed;
    }

    //player
    public class Ship {
        int x, y, size;
        Image img;
        boolean destroyed;

        //constructor
        public Ship(int posX, int posY, int size, Image image) {
            this.x = posX;
            this.y = posY;
            this.size = size;
            this.img = image;
        }

        public Shot shoot() {
            return new Shot(x + size / 2 - Shot.size / 2, y - Shot.size);
        }

        public boolean colide(Ship other) {
            int d = distance(this.x + size / 2, this.y + size / 2,
                other.x + other.size / 2, other.y + other.size / 2);
            return d < other.size / 2 + this.size / 2;

        }

        public void draw(){
            //gc.setFill(Color.LIGHTBLUE);
            //gc.fillRect(x*size,y*size,size,size);
            gc.drawImage(img,x,y,size,size);
        }
    }

    //bots
    public class Bot extends Ship {
        int speed = 5;

        public Bot(int posX, int posY, int size, Image image) {
            super(posX,posY,size,image);
        }
    }

    //shot
    public class Shot {
        public boolean toRemove;

        int x, y, speed = 10;
        static final int size = 6;
        public Shot(int posX, int posY) {
            this.x = posX;
            this.y = posY;
        }

        public void update() {
            y -= speed;
        }

        public void draw() {
            gc.setFill(Color.DARKORANGE);
            gc.fillOval(x,y,size,speed);
        }

        public boolean colide(Ship ship) {
            int distance = distance(this.x + size / 2, this.y + size / 2,
                    ship.x + ship.size / 2, ship.y + ship.size / 2);
            return distance < ship.size / 2 + size / 2;
        }
    }

    Bot newBot() {
        return new Bot(50 + RAND.nextInt(WIDTH - 100), 0, PLAYER_SIZE,
                BOTS_IMG);
    }

    int distance(int x1, int y1, int x2, int y2) {
        return (int) Math.sqrt(Math.pow((x1 - x2), 2) + Math.pow((y1 - y2), 2));
    }

    private static void main(String[] args) {
        launch(args);
    }
}
