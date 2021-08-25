package events.clientevents;

public class SendHitedSquareCoordinatesCE extends ClientEvent{
    private final int x;
    private final int y;

    public SendHitedSquareCoordinatesCE(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
