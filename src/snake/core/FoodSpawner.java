package snake.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FoodSpawner {
    private final Random random = new Random();

    public Food spawn(Snake snake, int width, int height) {
        List<Position> freeCells = new ArrayList<>();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Position position = new Position(x, y);
                if (!snake.getBody().contains(position)) {
                    freeCells.add(position);
                }
            }
        }

        if (freeCells.isEmpty()) {
            return null;
        }

        Position picked = freeCells.get(random.nextInt(freeCells.size()));
        return new Food(picked);
    }
}
