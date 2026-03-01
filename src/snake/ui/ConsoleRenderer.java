package snake.ui;

import snake.core.Food;
import snake.core.GameConfig;
import snake.core.GameState;
import snake.core.Position;
import snake.core.Snake;

import java.util.HashSet;
import java.util.Set;

public class ConsoleRenderer implements Renderer {
    public void render(GameState state) {
        if (state == null) {
            return;
        }

        StringBuilder frame = new StringBuilder();
        frame.append("\u001b[H\u001b[2J");
        frame.append("Snake | Score: ").append(state.getScore()).append(System.lineSeparator());

        Snake snake = state.getSnake();
        Set<Position> bodyPositions = new HashSet<>(snake.getBody());
        Position head = snake.getHead();
        Position foodPosition = state.getFoodPosition();

        frame.append('+');
        for (int x = 0; x < GameConfig.GRID_WIDTH; x++) {
            frame.append('-');
        }
        frame.append('+').append(System.lineSeparator());

        for (int y = 0; y < GameConfig.GRID_HEIGHT; y++) {
            frame.append('|');
            for (int x = 0; x < GameConfig.GRID_WIDTH; x++) {
                Position current = new Position(x, y);
                char cell = ' ';
                if (foodPosition != null && current.equals(foodPosition)) {
                    cell = '*';
                }
                if (bodyPositions.contains(current)) {
                    cell = current.equals(head) ? '@' : 'o';
                }
                frame.append(cell);
            }
            frame.append('|').append(System.lineSeparator());
        }

        frame.append('+');
        for (int x = 0; x < GameConfig.GRID_WIDTH; x++) {
            frame.append('-');
        }
        frame.append('+').append(System.lineSeparator());
        frame.append("Controls: W/A/S/D or Arrow Keys, Q to quit").append(System.lineSeparator());

        if (state.isGameOver()) {
            frame.append(state.isGameOver() ? "You won!" : "Game over!").append(System.lineSeparator());
        }

        System.out.print(frame);
        System.out.flush();
    }
}
