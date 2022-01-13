package Model.Snake;

import Controller.Snake.SnakeInput;
import Controller.Updatable;
import Model.Direction;
import Model.Matrix;
import Model.Position;
import View.RenderGrid;
import javafx.scene.paint.Color;
import java.util.LinkedList;
import java.util.Queue;

public class SnakeGame implements Updatable<RenderGrid> {
    private RenderGrid renderGrid;
    private Matrix squares;
    private double seconds;
    private final int ticksPerSecond = 6;
    private Queue<Position> snake;
    private Position headPosition;
    private int width;
    private int height;
    private RandomBits randomBits;
    private SnakeInput snakeInput;
    private Direction direction;

    //Color
    private final Color background = Color.rgb(0,0,0);
    private final Color headColor = Color.rgb(255, 0, 0);
    private final Color body = Color.rgb(255, 255, 255);
    private final Color bits = Color.rgb(255,255,0);

    public SnakeGame(double canvasHeight){
        this(32, 18, canvasHeight);
    }

    public SnakeGame(int width, int height, double canvasHeight){
        this.width = width;
        this.height = height;
        if ((double) width / height != 16.0 / 9) {
            Exception e = new RuntimeException("Dimensions not allowed. Has to be 16:9");
            e.printStackTrace();
        }
        squares = new Matrix(width, height, canvasHeight);
        squares.setAllColor(background);
        direction = Direction.NORTH;
        headPosition = new Position(width / 2, height / 3);
        snake = new LinkedList<>();
        //snake = new LinkedList<>(List.of(squares.getNeighbour(headPosition.x, headPosition.y, DirectionController.getOpposite(direction))));
        randomBits = new RandomBits(width, height, 3, snake.toArray(Position[]::new), headPosition);
        snakeInput = new SnakeInput(direction);
        renderGrid = tick();
    }

    @Override
    public void update(double seconds) {
        this.seconds += seconds;
        double tickTime = 1.0 / ticksPerSecond;
        if(this.seconds >= tickTime){
            this.seconds -= tickTime;
            renderGrid = tick();
        }
    }

    @Override
    public RenderGrid getRenderObject() {
        return renderGrid;
    }

    @Override
    public boolean stopCondition() {
        //Collision with itself
        for(Position position : snake){
            if(headPosition.x == position.x && headPosition.y == position.y) return true;
        }
        return false;
    }

    private RenderGrid tick(){
        Direction newDirection = snakeInput.getDirection();
        if(newDirection != null) direction = newDirection;
        Position lastPart = snake.peek();
        if(lastPart == null) lastPart = headPosition;
        snake.add(new Position(headPosition.x, headPosition.y));
        headPosition = squares.getNeighbour(headPosition.x, headPosition.y, direction);
        if(!randomBits.isSnakeEating(snake.toArray(Position[]::new), headPosition)) snake.poll();

        //Colors
        //Bits
        for(Position position : randomBits.getBits()){
            squares.setColor(position.x, position.y, bits);
        }
        //Snake
        squares.setColor(lastPart.x, lastPart.y, background);
        squares.setColor(headPosition.x, headPosition.y, headColor);
        for(Position position : snake)
        {
            squares.setColor(position.x, position.y, body);
        }


        return new RenderGrid(squares);
    }
}
