package snake.core;

import java.util.ArrayDeque;
import java.util.Deque;

public class GameEngine {
    private final FoodSpawner foodSpawner;
    private GameState state;

    public GameEngine(FoodSpawner foodSpawner) {
        this.foodSpawner = foodSpawner;
    }

    public void initialize() {
        int startX = GameConfig.GRID_WIDTH / 2;
        int startY = GameConfig.GRID_HEIGHT / 2;

        Deque<Position> initialBody = new ArrayDeque<>();
        for (int i = 0; i < GameConfig.INITIAL_SNAKE_LENGTH; i++) {
            initialBody.addLast(new Position(startX - i, startY));
        }

        Snake snake = new Snake(initialBody, Direction.RIGHT);
        Food food = foodSpawner.spawn(snake, GameConfig.GRID_WIDTH, GameConfig.GRID_HEIGHT);
        state = new GameState(snake, food);
    }

    public GameState getState() {
        return state;
    }

    public void update(Direction inputDirection) {
        if (state == null || state.isGameOver()) {
            return;
        }

        Snake snake = state.getSnake();
        snake.setDirection(inputDirection);

        Position nextHead = getNextHead(snake.getHead(), snake.getCurrentDirection());
        if (nextHead == null) {
            state.setGameOver(true);
            return;
        }

        boolean grow = state.getFood() != null && nextHead.equals(state.getFood().getPosition());
        if (isCollision(nextHead, snake, grow)) {
            state.setGameOver(true);
            return;
        }

        snake.move(grow);

        if (grow) {
            state.incrementScore();
            Food nextFood = foodSpawner.spawn(snake, GameConfig.GRID_WIDTH, GameConfig.GRID_HEIGHT);
            state.setFood(nextFood);
            if (nextFood == null) {
                state.setWon(true);
                state.setGameOver(true);
            }
        }
    }

    private Position getNextHead(Position head, Direction direction) {
        if (head == null || direction == null) {
            return null;
        }

        int x = head.getX();
        int y = head.getY();
        switch (direction) {
            case UP:
                return new Position(x, y - 1);
            case DOWN:
                return new Position(x, y + 1);
            case LEFT:
                return new Position(x - 1, y);
            case RIGHT:
                return new Position(x + 1, y);
            default:
                return null;
        }
    }

    private boolean isCollision(Position nextHead, Snake snake, boolean grow) {
        int x = nextHead.getX();
        int y = nextHead.getY();

        if (x < 0 || x >= GameConfig.GRID_WIDTH || y < 0 || y >= GameConfig.GRID_HEIGHT) {
            return true;
        }

        Deque<Position> body = new ArrayDeque<>(snake.getBody());
        if (!grow && !body.isEmpty()) {
            body.removeLast();
        }

        return body.contains(nextHead);
    }
}
