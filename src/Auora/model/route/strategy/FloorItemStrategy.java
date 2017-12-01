package Auora.model.route.strategy;

import Auora.model.route.RouteStrategy;
import Auora.util.RSTile;

public class FloorItemStrategy extends RouteStrategy {

    /**
     * Entity position x.
     */
    private int x;
    /**
     * Entity position y.
     */
    private int y;

    public FloorItemStrategy(RSTile itemTile) {
        this.x = itemTile.getX();
        this.y = itemTile.getY();
    }

    //meaning only stops under
    @Override
    public boolean canExit(int currentX, int currentY, int sizeXY, int[][] clip, int clipBaseX, int clipBaseY) {
        return currentX == x && currentY == y;//RouteStrategy.checkFilledRectangularInteract(clip, currentX - clipBaseX, currentY - clipBaseY, sizeXY, sizeXY, x - clipBaseX, y - clipBaseY, 1, 1, 0);
    }

    @Override
    public int getApproxDestinationX() {
        return x;
    }

    @Override
    public int getApproxDestinationY() {
        return y;
    }

    @Override
    public int getApproxDestinationSizeX() {
        return 1;
    }

    @Override
    public int getApproxDestinationSizeY() {
        return 1;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof FloorItemStrategy))
            return false;
        FloorItemStrategy strategy = (FloorItemStrategy) other;
        return x == strategy.x && y == strategy.y;
    }

}
