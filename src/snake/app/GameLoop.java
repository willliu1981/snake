package snake.app;

import snake.core.Direction;
import snake.core.GameConfig;
import snake.core.GameEngine;
import snake.core.GameState;
import snake.ui.InputHandler;
import snake.ui.Renderer;

public class GameLoop {
    private final GameEngine engine;
    private final Renderer renderer;
    private final InputHandler inputHandler;

    public GameLoop(GameEngine engine, Renderer renderer, InputHandler inputHandler) {
        this.engine = engine;
        this.renderer = renderer;
        this.inputHandler = inputHandler;
    }

    public void run() {
        engine.initialize();
        GameState state = engine.getState();
        renderer.render(state);

        while (!state.isGameOver() && !inputHandler.shouldQuit()) {
            long tickStart = System.currentTimeMillis();

            Direction inputDirection = inputHandler.pollDirection();
            engine.update(inputDirection);
            renderer.render(state);

            long elapsed = System.currentTimeMillis() - tickStart;
            long sleepMillis = GameConfig.TICK_MILLIS - elapsed;
            if (sleepMillis > 0) {
                try {
                    Thread.sleep(sleepMillis);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }
    }
}
