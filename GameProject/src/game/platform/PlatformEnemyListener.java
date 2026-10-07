package game.platform;

import city.cs.engine.CollisionEvent;
import city.cs.engine.CollisionListener;
import game.enemies.Enemy;
import org.jbox2d.common.Vec2;

/**
 * <p>
 * a collision listener that handles interactions between platforms and enemies
 * </p>
 * this listener is responsible for:
 * <ul>
 *   <li> detecting when enemies collide with platform sides
 *   <li> triggering appropriate direction changes in enemy movement
 * </ul>
 *
 * the listener implements collision detection logic that determines whether the enemy should change direction based on:
 * <ul>
 *   <li>the relative positions of the enemy and platform
 *   <li>The current movement direction of the enemy
 * </ul>
 *
 * <p>
 * to prevent rapid direction changes when enemies remain in contact with platforms, this listener implements a
 * cooldown period between direction change events.
 * </p>
 */

public class PlatformEnemyListener implements CollisionListener {
    private long lastCollisionTime = 0;
    private static final long COLLISION_COOLDOWN = 500; // 500ms cooldown

    /**
     * <p>
     * handles collision events between platforms and enemies.
     * </p>
     * <p>
     * automatically called by the physics engine when a collision occurs involving a body to which this listener is
     * attached.
     * </p>
     * specifically handles collisions with enemies and determines whether to change the enemy's direction based on:
     * <ul>
     *   <li> whether the enemy is moving toward the platform (side collision)
     *   <li> whether sufficient time has passed since the last direction change (cooldown check)
     * </ul>
     * <p>
     * direction changes only occur when an enemy collides with a platform while moving toward it, which indicates a
     * side collision rather than a top/bottom collision.
     * </p>
     *
     * @param e the CollisionEvent containing information about the collision, including the bodies involved and
     * contact points
     */

    @Override
    public void collide(CollisionEvent e) {
        // check if collision is with an Enemy
        if (e.getOtherBody() instanceof Enemy) {
            // get enemy and platform
            Enemy enemy = (Enemy) e.getOtherBody();
            Platform platform = (Platform) e.getReportingBody();

            // basic collision for side hits - only change direction if:
            // 1. were moving right and the platform is to our right
            // 2. were moving left and the platform is to our left
            Vec2 enemyPos = enemy.getPosition();
            Vec2 platformPos = platform.getPosition();

            boolean enemyToLeft = enemyPos.x < platformPos.x;
            boolean movingTowardsPlatform = (enemyToLeft && enemy.isMovingRight()) ||
                    (!enemyToLeft && !enemy.isMovingRight());

            // apply cooldown to prevent multiple triggers
            long currentTime = System.currentTimeMillis();
            if (currentTime - lastCollisionTime < COLLISION_COOLDOWN) {
                return;
            }

            // only change direction if enemy is moving toward the platform (side collision)
            if (movingTowardsPlatform) {
                System.out.println("Enemy collided with platform side - changing direction");
                enemy.changeDirection();
                lastCollisionTime = currentTime;
            }
        }
    }
}