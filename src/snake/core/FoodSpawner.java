package snake.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FoodSpawner {
    private final Random random = new Random();

    public Position spawn(Snake snake) {
        List<Position> freeCells = new ArrayList<>();
        for (int y = 0; y < GameConfig.GRID_HEIGHT; y++) {
            for (int x = 0; x < GameConfig.GRID_WIDTH; x++) {
                Position position = new Position(x, y);
                if (!snake.getBody().contains(position)) {
                    freeCells.add(position);
                }
            }
        }

        if (freeCells.isEmpty()) {
            return null;
        }

        return freeCells.get(random.nextInt(freeCells.size()));
    }
}
