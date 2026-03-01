package snake.app;

import snake.core.FoodSpawner;
import snake.core.GameEngine;
import snake.ui.ConsoleInputHandler;
import snake.ui.ConsoleRenderer;

public class Main {
    public static void main(String[] args) {
        GameEngine engine = new GameEngine(new FoodSpawner());
        ConsoleRenderer renderer = new ConsoleRenderer();
        ConsoleInputHandler inputHandler = new ConsoleInputHandler();

        new GameLoop(engine, renderer, inputHandler).run();
    }
}
