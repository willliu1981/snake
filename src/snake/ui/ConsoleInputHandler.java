package snake.ui;

import snake.core.Direction;

import java.io.IOException;
import java.io.InputStream;

public class ConsoleInputHandler implements InputHandler {
    private final InputStream inputStream;
    private boolean quitRequested;
    private int pendingEscapeByte = -1;

    public ConsoleInputHandler() {
        this.inputStream = System.in;
    }

    public Direction pollDirection() {
        Direction latestDirection = null;
        try {
            while (inputStream.available() > 0 || pendingEscapeByte != -1) {
                int value;
                if (pendingEscapeByte != -1) {
                    value = pendingEscapeByte;
                    pendingEscapeByte = -1;
                } else {
                    value = inputStream.read();
                }
                if (value < 0) {
                    break;
                }

                Direction parsed = parseInput(value);
                if (parsed != null) {
                    latestDirection = parsed;
                }
            }
        } catch (IOException e) {
            quitRequested = true;
        }
        return latestDirection;
    }

    public boolean shouldQuit() {
        return quitRequested;
    }

    private Direction parseInput(int value) throws IOException {
        char c = Character.toLowerCase((char) value);
        switch (c) {
            case 'w':
                return Direction.UP;
            case 's':
                return Direction.DOWN;
            case 'a':
                return Direction.LEFT;
            case 'd':
                return Direction.RIGHT;
            case 'q':
                quitRequested = true;
                return null;
            default:
                break;
        }

        if (value != 27) {
            return null;
        }

        if (inputStream.available() < 2) {
            return null;
        }

        int bracket = inputStream.read();
        int arrow = inputStream.read();
        if (bracket != 91) {
            if (arrow >= 0) {
                pendingEscapeByte = arrow;
            }
            return null;
        }

        switch (arrow) {
            case 65:
                return Direction.UP;
            case 66:
                return Direction.DOWN;
            case 67:
                return Direction.RIGHT;
            case 68:
                return Direction.LEFT;
            default:
                return null;
        }
    }
}
