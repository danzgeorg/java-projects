package game.enemies;

import city.cs.engine.*;
import game.collisions.PlatformCollisionListener;
import game.levels.Level2;
import game.levels.Level3;
import org.jbox2d.common.Vec2;

/**
 * represents an enemy character in the game that moves horizontally and changes direction when colliding with
 * platforms or reaching world boundaries
 * <p>
 * the enemy appearance and movement speed depend on the game level
 * </p>
 * <ol>
 *   <li> appearance for each level:
 *       <ul>
 *           <li> level 1 as snails
 *           <li> level 2 as chickens
 *           <li> level 3 as pigs.
 *       </ul>
 *   <li> enemies in level 3 move at a faster speed than those in Levels 1 and 2.
 * </ol>
 */

public class Enemy extends DynamicBody implements StepListener {
    private static final Shape enemyShape = new BoxShape(1, 1);

    // level 1 enemy
    private static final BodyImage leftEnemyLevel1 = new BodyImage("java-project-2025-danzgeorg/data/enemies/leftSnail.png", 2f);
    private static final BodyImage rightEnemyLevel1 = new BodyImage("java-project-2025-danzgeorg/data/enemies/rightSnail.png", 2f);

    // level 2 enemy
    private static final BodyImage leftEnemyLevel2 = new BodyImage("java-project-2025-danzgeorg/data/enemies/leftChicken.png", 2f);
    private static final BodyImage rightEnemyLevel2 = new BodyImage("java-project-2025-danzgeorg/data/enemies/rightChicken.png", 2f);

    // level 3 enemy
    private static final BodyImage leftEnemyLevel3 = new BodyImage("java-project-2025-danzgeorg/data/enemies/leftPig.png", 2f);
    private static final BodyImage rightEnemyLevel3 = new BodyImage("java-project-2025-danzgeorg/data/enemies/rightPig.png", 2f);

    private boolean movingRight = true; // direction of movement
    private float moveSpeed = 3f; // speed at which the enemy moves
    private boolean justChangedDirection = false; // flag to track if direction was recently changed
    private long lastDirectionChangeTime = 0; // track when direction was last changed

    /**
     * creates a new enemy at the specified position for the given level
     * the enemy appearance and speed are determined by the level parameter:
     * <ul>
     *   <li> level 1: snail with normal speed
     *   <li> level 2: chicken with normal speed
     *   <li> level 3: pig with increased speed
     * </ul>
     *
     * @param world the game world in which to create the enemy
     * @param position the initial position of the enemy
     * @param level the game level (1, 2, or 3) which determines enemy appearance and behavior
     */

    public Enemy(World world, Vec2 position, int level) {
        super(world, enemyShape);
        if (level == 3) {
            addImage(rightEnemyLevel3);
            // enemies in level 3 move faster
            moveSpeed = 4f;
        } else if (level == 2) {
            addImage(rightEnemyLevel2);
        } else {
            addImage(rightEnemyLevel1);
        }

        setPosition(position);
        world.addStepListener(this);
        setGravityScale(0);

        // add a collision listener to detect platform collisions
        addCollisionListener(new PlatformCollisionListener(this));

        startMoving();
    }

    private void startMoving() {
        if (movingRight) {
            setLinearVelocity(new Vec2(moveSpeed, 0)); // move right
        } else {
            setLinearVelocity(new Vec2(-moveSpeed, 0)); // move left
        }
    }

    /**
     * changes the enemys movement direction and updates its appearance accordingly
     * <ol>
     *   <li> implements a cooldown to prevent rapid direction changes
     *   <li> flips the movement direction
     *   <li> updates the enemys image to face the new direction
     *   <li> restarts movement in the new direction
     * </ol>
     * <p>
     * the appropriate image is selected based on both the direction and the current level
     * </p>
     */

    public void changeDirection() {
        // add a cooldown to prevent rapid direction changes
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastDirectionChangeTime < 500) {
            // dont change direction if it was changed less than 500ms ago
            return;
        }

        if (!justChangedDirection) {
            movingRight = !movingRight; // flip the direction
            justChangedDirection = true;
            lastDirectionChangeTime = currentTime;
        }

        // flip image based on the direction
        removeAllImages();

        if (getWorld() instanceof Level3) {
            if (movingRight) {
                addImage(rightEnemyLevel3);
            } else {
                addImage(leftEnemyLevel3);
            }
        } else if (getWorld() instanceof Level2) {
            if (movingRight) {
                addImage(rightEnemyLevel2);
            } else {
                addImage(leftEnemyLevel2);
            }
        } else {
            if (movingRight) {
                addImage(rightEnemyLevel1);
            } else {
                addImage(leftEnemyLevel1);
            }
        }

        System.out.println("enemy flipped to face " + (movingRight ? "right" : "left"));
        startMoving();
    }

    /**
     * called before each physics step to check and update enemy behavior
     * monitors if the enemy has reached the world boundaries and changes direction if so
     *
     * @param stepEvent the step event provided by the physics engine
     */

    @Override
    public void preStep(StepEvent stepEvent) {
        // check if the enemy has reached the boundaries
        if (getPosition().x > 20) { // right boundary
            changeDirection();
        } else if (getPosition().x < -20) { // left boundary
            changeDirection();
        }
    }

    /**
     * called after each physics step to finalize enemy updates
     * <ol>
     * <li> resets the direction change flag
     * <li> ensures the enemy maintains proper velocity
     * </ol>
     *
     * @param stepEvent the step event provided by the physics engine
     */

    @Override
    public void postStep(StepEvent stepEvent) {
        justChangedDirection = false; // reset the direction change flag after a step

        // make sure velocity is maintained
        Vec2 currentVelocity = getLinearVelocity();
        if (Math.abs(currentVelocity.x) < 0.1f) {
            // if almost stopped, restart movement
            startMoving();
        }
    }

    // getter for the platform collision detection
    public boolean isMovingRight() {
        return movingRight;
    }
}