package snake.core;

import java.util.ArrayDeque;
import java.util.Deque;

public class Snake {
    private final Deque<Position> body;
    private Direction currentDirection;

    public Snake(Deque<Position> initialBody, Direction initialDirection) {
        this.body = new ArrayDeque<>(initialBody);
        this.currentDirection = initialDirection;
    }

    public Deque<Position> getBody() {
        return new ArrayDeque<>(body);
    }

    public Direction getCurrentDirection() {
        return currentDirection;
    }

    public void setDirection(Direction next) {
        if (next == null) {
            return;
        }
        if (!next.isOpposite(currentDirection)) {
            this.currentDirection = next;
        }
    }

    public Position getHead() {
        return body.peekFirst();
    }

    public void addHead(Position position) {
        body.addFirst(position);
    }

    public Position removeTail() {
        return body.pollLast();
    }

    public void move(boolean grow) {
        Position head = getHead();
        if (head == null || currentDirection == null) {
            return;
        }

        int nextX = head.getX();
        int nextY = head.getY();

        switch (currentDirection) {
            case UP:
                nextY--;
                break;
            case DOWN:
                nextY++;
                break;
            case LEFT:
                nextX--;
                break;
            case RIGHT:
                nextX++;
                break;
            default:
                return;
        }

        addHead(new Position(nextX, nextY));
        if (!grow) {
            removeTail();
        }
    }
}
