package snake.ui;

import snake.core.Direction;
import snake.core.GameConfig;
import snake.core.GameEngine;
import snake.core.GameState;
import snake.core.Position;
import snake.core.Snake;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.util.HashSet;
import java.util.Set;

public class SwingRendererPanel extends JPanel {
    public static final int CELL_SIZE = 24;

    private final GameEngine engine;

    public SwingRendererPanel(GameEngine engine) {
        this.engine = engine;
        int width = GameConfig.GRID_WIDTH * CELL_SIZE;
        int height = GameConfig.GRID_HEIGHT * CELL_SIZE;
        setPreferredSize(new Dimension(width, height));
        setBackground(Color.WHITE);
        setupKeyBindings();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2d.setFont(new Font("Monospaced", Font.BOLD, 16));

        GameState state = engine.getGameState();
        if (state == null) {
            g2d.dispose();
            return;
        }

        Snake snake = state.getSnake();
        Position head = snake.getHead();
        Set<Position> body = new HashSet<>(snake.getBody());
        Position food = state.getFoodPosition();

        for (int y = 0; y < GameConfig.GRID_HEIGHT; y++) {
            for (int x = 0; x < GameConfig.GRID_WIDTH; x++) {
                int px = x * CELL_SIZE;
                int py = y * CELL_SIZE;

                g2d.setColor(new Color(240, 240, 240));
                g2d.fillRect(px, py, CELL_SIZE, CELL_SIZE);
                g2d.setColor(new Color(210, 210, 210));
                g2d.drawRect(px, py, CELL_SIZE, CELL_SIZE);

                Position cell = new Position(x, y);
                char symbol = 0;
                if (food != null && food.equals(cell)) {
                    symbol = '*';
                }
                if (body.contains(cell)) {
                    symbol = cell.equals(head) ? '@' : 'o';
                }

                if (symbol != 0) {
                    g2d.setColor(Color.BLACK);
                    String text = String.valueOf(symbol);
                    int tx = px + (CELL_SIZE / 2) - 5;
                    int ty = py + (CELL_SIZE / 2) + 6;
                    g2d.drawString(text, tx, ty);
                }
            }
        }

        g2d.dispose();
    }

    private void setupKeyBindings() {
        InputMap inputMap = getInputMap(WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = getActionMap();

        bind(inputMap, actionMap, "UP", Direction.UP);
        bind(inputMap, actionMap, "DOWN", Direction.DOWN);
        bind(inputMap, actionMap, "LEFT", Direction.LEFT);
        bind(inputMap, actionMap, "RIGHT", Direction.RIGHT);
        bind(inputMap, actionMap, "W", Direction.UP);
        bind(inputMap, actionMap, "S", Direction.DOWN);
        bind(inputMap, actionMap, "A", Direction.LEFT);
        bind(inputMap, actionMap, "D", Direction.RIGHT);
    }

    private void bind(InputMap inputMap, ActionMap actionMap, String key, Direction direction) {
        String actionKey = "dir_" + key;
        inputMap.put(KeyStroke.getKeyStroke(key), actionKey);
        actionMap.put(actionKey, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                engine.queueDirection(direction);
            }
        });
    }
}
