package snake.core;

public class GameState {
    private final Snake snake;
    private Food food;
    private int score;
    private boolean gameOver;
    private boolean won;

    public GameState(Snake snake, Food food) {
        this.snake = snake;
        this.food = food;
        this.score = 0;
        this.gameOver = false;
        this.won = false;
    }

    public Snake getSnake() {
        return snake;
    }

    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
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

    public boolean isWon() {
        return won;
    }

    public void setWon(boolean won) {
        this.won = won;
    }
}
