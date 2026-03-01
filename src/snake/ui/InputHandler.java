package snake.ui;

import snake.core.Direction;

public interface InputHandler {
    Direction pollDirection();

    boolean shouldQuit();
}
