package game.collisions;

import city.cs.engine.CollisionEvent;
import city.cs.engine.CollisionListener;
import game.enemies.Enemy;
import game.platform.Platform;
import org.jbox2d.common.Vec2;

/**
 * collision listener to detect when an enemy collides with platforms
 * this class is responsible for:
 * <ul>
 *   <li> detecting collisions between enemies and platforms
 *   <li> determining if the enemy should change direction based on collision
 *   <li> implementing a cooldown to prevent multiple rapid direction changes
 *   <li> only changing enemy direction when the enemy is moving toward the platform
 * </ul>
 */

public class PlatformCollisionListener implements CollisionListener {
    private final Enemy enemy;
    private long lastCollisionTime = 0;
    private final long COLLISION_COOLDOWN = 500; // 500ms cooldown


    public PlatformCollisionListener(Enemy enemy) {
        this.enemy = enemy;
    }

    /**
     * handles collision events between the enemy and other bodies
     * changes the enemy's direction when it collides with a platform, but only if:
     * <ol>
     *   <li> the collision is with a Platform
     *   <li> sufficient time has passed since the last direction change (cooldown)
     *   <li> the enemy has significant horizontal velocity
     *   <li> the enemy is moving toward the platform (to ignore collisions from above/below)
     * </ol>
     *
     * @param e the collision event containing information about the bodies involved
     */

    @Override
    public void collide(CollisionEvent e) {
        // only process if collision is with a Platform
        if (e.getOtherBody() instanceof Platform) {
            // implement cooldown to prevent multiple direction changes
            long currentTime = System.currentTimeMillis();
            if (currentTime - lastCollisionTime < COLLISION_COOLDOWN) {
                return; // skip this collision if we changed direction recently
            }

            // get position of both bodies
            Vec2 enemyPos = enemy.getPosition();
            Vec2 platformPos = e.getOtherBody().getPosition();

            // get enemy velocity to determine movement direction
            Vec2 enemyVel = enemy.getLinearVelocity();

            // only change direction if we have horizontal velocity (not just sitting on platform)
            if (Math.abs(enemyVel.x) > 0.5f) {
                // determine if enemy is to the left or right of platform
                boolean enemyToLeft = enemyPos.x < platformPos.x;
                boolean movingTowardsPlatform = (enemyToLeft && enemyVel.x > 0) || (!enemyToLeft && enemyVel.x < 0);

                // only change direction if moving towards the platform
                if (movingTowardsPlatform) {
                    System.out.println("Enemy hit platform - changing direction");
                    enemy.changeDirection();
                    lastCollisionTime = currentTime;
                }
            }
        }
    }
}