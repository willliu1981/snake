package snake.app;

import snake.core.FoodSpawner;
import snake.core.GameConfig;
import snake.core.GameEngine;
import snake.core.GameState;
import snake.ui.SwingRendererPanel;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class SwingMain {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GameEngine engine = new GameEngine(new FoodSpawner());
            engine.initialize();

            SwingRendererPanel panel = new SwingRendererPanel(engine);
            JFrame frame = new JFrame("Snake Swing");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setContentPane(panel);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setResizable(false);
            frame.setVisible(true);

            Timer timer = new Timer(GameConfig.TICK_MILLIS, event -> {
                GameState state = engine.getGameState();
                if (state != null && !state.isGameOver()) {
                    engine.update();
                }
                panel.repaint();
            });
            timer.start();
        });
    }
}
