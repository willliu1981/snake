package snake.core;

public class GameState {
    private final Snake snake;
    private Position foodPosition;
    private int score;
    private boolean gameOver;

    public GameState(Snake snake, Position foodPosition, int score, boolean gameOver) {
        this.snake = snake;
        this.foodPosition = foodPosition;
        this.score = score;
        this.gameOver = gameOver;
    }

    public Snake getSnake() {
        return snake;
    }

    public Position getFoodPosition() {
        return foodPosition;
    }

    public void setFoodPosition(Position foodPosition) {
        this.foodPosition = foodPosition;
    }

    public int getScore() {
        return score;
    }

    public void incrementScore() {
        score++;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }
}
