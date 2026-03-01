package snake.core;

import java.util.ArrayDeque;
import java.util.Deque;

public class GameEngine {
    private final FoodSpawner foodSpawner;
    private GameState gameState;
    private Direction queuedDirection;

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
        Position foodPosition = foodSpawner.spawn(snake);
        gameState = new GameState(snake, foodPosition, 0, false);
        queuedDirection = null;
    }

    public void queueDirection(Direction direction) {
        queuedDirection = direction;
    }

    public void update() {
        if (gameState == null || gameState.isGameOver()) {
            return;
        }

        Snake snake = gameState.getSnake();
        if (queuedDirection != null) {
            snake.setDirection(queuedDirection);
            queuedDirection = null;
        }

        Position nextHead = getNextHead(snake.getHead(), snake.getCurrentDirection());
        if (nextHead == null) {
            gameState.setGameOver(true);
            return;
        }

        Position foodPosition = gameState.getFoodPosition();
        boolean grow = foodPosition != null && nextHead.equals(foodPosition);
        if (isCollision(nextHead, snake, grow)) {
            gameState.setGameOver(true);
            return;
        }

        snake.addHead(nextHead);
        if (!grow) {
            snake.removeTail();
        }

        if (grow) {
            gameState.incrementScore();
            gameState.setFoodPosition(foodSpawner.spawn(snake));
        }
    }

    public GameState getGameState() {
        return gameState;
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
