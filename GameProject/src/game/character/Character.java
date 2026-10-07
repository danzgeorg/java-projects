package game.character;
import city.cs.engine.*;
import game.levels.Level2;
import game.levels.Level3;
import game.SoundManager;
import org.jbox2d.common.Vec2;

import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * represents the player character in the game
 * <ul>
 *   <li> the character can move left and right, jump, collect pickups, and interact with enemies
 *   <li> different character sprites are used for each level of the game
 *   <li> the character has health and can take damage from enemies
 *   <li> it also keeps track of the player's score and collected pickups
 * </ul>
 */

public class Character extends Walker implements StepListener {
    private static final Shape characterShape = new BoxShape(0.5f,1);

    // level 1 character
    private static final BodyImage RightCharacterLevel1Image = new BodyImage("java-project-2025-danzgeorg/data/characters/rightPinkMan.png",2f);
    private static final BodyImage LeftCharacterLevel1Image = new BodyImage("java-project-2025-danzgeorg/data/characters/leftPinkMan.png", 2f);

    // level 2 character
    private static final BodyImage LeftCharacterLevel2Image = new BodyImage("java-project-2025-danzgeorg/data/characters/leftBlueMan.png", 2f);
    private static final BodyImage RightCharacterLevel2Image = new BodyImage("java-project-2025-danzgeorg/data/characters/rightBlueMan.png", 2f);

    // level 3 character
    private static final BodyImage LeftCharacterLevel3Image = new BodyImage("java-project-2025-danzgeorg/data/characters/leftFrogMan.png", 2f);
    private static final BodyImage RightCharacterLevel3Image = new BodyImage("java-project-2025-danzgeorg/data/characters/rightFrogMan.png", 2f);

    private boolean facingRight = true;

    private static final float FALL_LIMIT = -50f; // the limit of the game view

    private int score = 0;
    private int health = 100; // starting health
    private final int MAX_HEALTH = 100;

    // dash fields
    private boolean canDash = false;
    private boolean isDashing = false;
    private boolean dashCooldown = false;
    private final float DASH_POWER = 10;
    private final int DASH_DURATION = 200;
    private final int DASH_COOLDOWN = 800;
    private Timer dashTimer;
    private Timer cooldownTimer;

    // downward movement
    private boolean movingDown = false;
    private final float DOWN_SPEED = -7f;

    // double jump
    private boolean canDoubleJump = false;
    private boolean hasJumped = false;
    private boolean hasDoubleJumped = false;
    private final float DOUBLE_JUMP_POWER = 10f;

    private int pickupCount;

    public int getPickupCount() {
        return pickupCount;
    }

    public void setPickupCount(int pickupCount) {
        this.pickupCount = pickupCount;
    }

    /**
     * creates a new character in the specified world with the given score and level
     * sets the appropriate character image based on the level
     *
     * @param world the game world in which the character exists
     * @param score the initial score for the character
     * @param level the current level (1, 2, or 3)
     */
    public Character(World world, int score, int level) {
        super(world, characterShape);

        if (level == 3) {
            addImage(RightCharacterLevel3Image);
        } else if (level == 2) {
            addImage(RightCharacterLevel2Image);
        } else {
            addImage(RightCharacterLevel1Image);
        }

        this.score = score;
        world.addStepListener(this);

        // dashing only for level 2 and above
        if (level >= 2) {
            canDash = true;
            isDashing = false;
            dashCooldown = false;

            // initialise dash duration timer
            dashTimer = new Timer(DASH_DURATION, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    endDash();
                }
            });
            dashTimer.setRepeats(false);

            // initialise dash cooldown timer
            cooldownTimer = new Timer(DASH_COOLDOWN, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    dashCooldown = false;
                }
            });
            cooldownTimer.setRepeats(false);
        }

        // enable double jump only for level 3
        if (level >= 3) {
            canDoubleJump = true;
        }
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    // health getters and setters
    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        // ensure health stays within bounds (0-100)
        this.health = Math.max(0, Math.min(MAX_HEALTH, health));
    }

    public int getMaxHealth() {
        return MAX_HEALTH;
    }

    /**
     * reduce health by the specified amount
     * @param damage amount of health to reduce
     * @return true if still alive, false if health reached 0
     */
    public boolean takeDamage(int damage) {
        health = Math.max(0, health - damage);
        return health > 0; // Return true if still alive
    }

    /**
     * changes the character's direction and image to face left
     * only changes the image if the character is currently facing right
     */
    public void turnLeft() {
        if (facingRight) {
            removeAllImages();

            if (getWorld() instanceof Level3) {
                addImage(LeftCharacterLevel3Image);
            } else if (getWorld() instanceof Level2) {
                addImage(LeftCharacterLevel2Image);
            } else {
                addImage(LeftCharacterLevel1Image);
            }
            facingRight = false;
        }
    }

    /**
     * changes the character's direction and image to face right
     * only changes the image if the character is currently facing left
     */
    public void turnRight() {
        if (!facingRight) {
            removeAllImages();

            if (getWorld() instanceof Level3) {
                addImage(RightCharacterLevel3Image);
            } else if (getWorld() instanceof Level2) {
                addImage(RightCharacterLevel2Image);
            } else {
                addImage(RightCharacterLevel1Image);
            }
            facingRight = true;
        }
    }

    /**
     * override the startWalking method to prevent it from canceling dashes
     */
    @Override
    public void startWalking(float speed) {
        // don't change velocity while dashing
        if (isDashing) {
            // just update the facing direction based on attempted movement
            if (speed < 0) {
                turnLeft();
            } else if (speed > 0) {
                turnRight();
            }
            return;
        }

        // normal walking behavior when not dashing
        super.startWalking(speed);
    }

    /**
     * starts moving the character downward at a constant speed
     */
    public void startMovingDown() {
        if (isDashing) {
            return; // Don't allow downward movement during dash
        }

        movingDown = true;
    }

    /**
     * stops downward movement
     */
    public void stopMovingDown() {
        movingDown = false;
    }

    /**
     * dash in the current facing direction if conditions allow
     * the dash gives horizontal movement and has a cooldown period
     */
    public void dash() {
        // check if dashing is allowed for this level and not on cooldown
        if (!canDash || isDashing || dashCooldown) {
            return;
        }

        // start the dash
        isDashing = true;
        dashCooldown = true;

        // calculate dash force in the direction the character is facing
        float dashDirection = facingRight ? DASH_POWER : -DASH_POWER;

        // apply impulse in the facing direction
        applyImpulse(new Vec2(dashDirection, 2.0f));

        // ensure any current walking is canceled during the dash
        super.stopWalking();

        SoundManager.getInstance().play(SoundManager.DASH);

        // stop any existing timers first to avoid potential conflicts
        if (dashTimer.isRunning()) {
            dashTimer.stop();
        }

        // start dash timer
        dashTimer.start();

    }

    /**
     * ends the dash state and starts the cooldown timer
     * called automatically when the dash duration ends
     */
    private void endDash() {
        isDashing = false;
        cooldownTimer.start();
    }

    /**
     * checks if the character is able to dash
     * dashing is only in levels 2 and above
     *
     * @return true if dashing is enabled for this level
     */
    public boolean canDash() {
        return canDash;
    }

    /**
     * stops all timers to prevent memory leaks during level transitions
     * call this method from the Character's destroy() method or when changing levels
     */
    public void stopAllTimers() {
        if (dashTimer != null && dashTimer.isRunning()) {
            dashTimer.stop();
        }

        if (cooldownTimer != null && cooldownTimer.isRunning()) {
            cooldownTimer.stop();
        }
    }

    /**
     * override the jump method to implement double jump
     */
    @Override
    public void jump(float speed) {
        Vec2 v = getLinearVelocity();
        boolean onGround = Math.abs(v.y) < 0.01f;

        if (onGround) {
            // reset jump state when on the ground
            hasJumped = false;
            hasDoubleJumped = false;

            // perform a normal jump
            super.jump(speed);
            hasJumped = true;
        }
        else if (canDoubleJump && hasJumped && !hasDoubleJumped) {
            // if in the air after first jump, in level 3, and haven't used double jump yet
            setLinearVelocity(new Vec2(v.x, DOUBLE_JUMP_POWER));
            hasDoubleJumped = true;
            SoundManager.getInstance().play(SoundManager.JUMP);
        }
    }

    /**
     * <ul>
     *   <li> called before each physics step in the game engine
     *   <li> checks if the character has fallen below the fall limit
     *   <li> ends the game if the character falls out of bounds
     * </ul>
     *
     * @param stepEvent the step event from the physics engine
     */
    @Override
    public void preStep(StepEvent stepEvent) {
        // check if the y coordinate is below the fall limit
        if (this.getPosition().y < FALL_LIMIT) {
            System.out.println("Game Over! The player fell out of bounds.");
            System.exit(0);
        }

        // maintain dash velocity while dashing
        if (isDashing) {
            Vec2 currentVel = getLinearVelocity();
            float dashDirection = facingRight ? DASH_POWER : -DASH_POWER;

            // only control horizontal velocity, preserve vertical velocity for jumps
            setLinearVelocity(new Vec2(dashDirection, currentVel.y));
        }
        // handle downward movement (when not dashing)
        else if (movingDown) {
            Vec2 currentVel = getLinearVelocity();
            // keep horizontal velocity, set vertical to DOWN_SPEED
            setLinearVelocity(new Vec2(currentVel.x, DOWN_SPEED));
        }
    }

    @Override
    public void postStep(StepEvent stepEvent) {
    }

    /**
     * override the destroy method to ensure timers are stopped when the character is destroyed
     */
    @Override
    public void destroy() {
        stopAllTimers();
        super.destroy();
    }
}