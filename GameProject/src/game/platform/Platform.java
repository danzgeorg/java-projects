package game.platform;

import city.cs.engine.BoxShape;
import city.cs.engine.StaticBody;
import city.cs.engine.World;
import org.jbox2d.common.Vec2;

/**
 * platforms are:
 * <ol>
 *   <li> immovable objects that form the level structure
 *   <li> provide surfaces for the player character and enemies to stand on or collide with
 *   <li> can be oriented either horizontally or vertically
 * </ol>
 * each platform:
 * <ol>
 * <li> tracks its own dimensions and orientation to assist with collision detection
 * <li> vertical platforms are rotated 90 degrees during creation.
 * </ol>
 * platforms include collision handling specifically for enemy interactions
 */

public class Platform extends StaticBody {
    // store platform dimensions for collision detection
    private final float width;
    private final float height;
    private final boolean isVertical;

    public Platform(World w, Vec2 position, float width, float height, boolean isVertical) {
        super(w, new BoxShape(width, height)); // create the platform with specified dimensions
        this.width = width;
        this.height = height;
        this.isVertical = isVertical;

        setPosition(position);

        // rotate the platform if its vertical
        if (isVertical) {
            setAngle((float) Math.toRadians(90));
        }

        // add collision listener to handle enemy collisions from the platforms side
        addCollisionListener(new PlatformEnemyListener());
    }

    // getter methods for platform dimensions
    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public boolean isVertical() {
        return isVertical;
    }
}