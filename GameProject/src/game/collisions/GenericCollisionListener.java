package game.collisions;


import city.cs.engine.CollisionEvent;
import city.cs.engine.CollisionListener;
import game.SoundManager;
import game.character.Character;
import game.enemies.Enemy;
import game.levels.GameLevel;
import game.pickups.Pickup;
import game.platform.Platform;
import org.jbox2d.common.Vec2;

/**
 * generic collision listener that handles all collision events in the game
 * processes various types of interactions:
 * <ul>
 *   <li> character-pickup collisions (collecting items)
 *   <li> character-enemy collisions (both jumping on enemies and taking damage)
 *   <li> enemy-enemy collisions (changing directions)
 *   <li> enemy-platform collisions (changing directions)
 *   <li> enemy-pickup collisions (changing directions)
 * </ul>
 * the listener implements checks against duplicate collisions and has error handling
 */

public class GenericCollisionListener implements CollisionListener {
    private final game.character.Character character;
    private final GameLevel level;
    private int collisionCounter = 0;

    // flag to track pickups to prevent duplicate processing
    private boolean processingPickup = false;

    public GenericCollisionListener(game.character.Character character, GameLevel level) {
        this.character = character;
        this.level = level;
    }

    /**
     * handles all collision events in the game by identifying the types of bodies involved and the appropriate logic
     * <br></br>
     * different collision types are handled:
     * <ul>
     *   <li> character collecting pickups
     *   <li> character jumping on or being hit by enemies
     *   <li> enemies colliding with other enemies
     *   <li> enemies colliding with platforms
     *   <li> enemies colliding with pickups
     * </ul>
     *
     * @param e the collision event containing information about the bodies involved
     */

    @Override
    public void collide(CollisionEvent e) {
        collisionCounter++;

        // basic collision logging
        System.out.println("Collision " + collisionCounter);
        System.out.println("  Reporting body: " + e.getReportingBody().getClass().getSimpleName());
        System.out.println("  Other body: " + e.getOtherBody().getClass().getSimpleName());

        try {
            // handle pickups - only if the character is directly involved
            if (!processingPickup && e.getOtherBody() instanceof Pickup && e.getReportingBody() instanceof Character) {
                processingPickup = true;
                try {
                    System.out.println("Processing pickup");
                    character.setPickupCount(character.getPickupCount() + 1);
                    character.setScore(character.getScore() + 1);
                    System.out.println("Pickup collected! Score: " + character.getScore());
                    game.SoundManager.getInstance().play(SoundManager.PICKUP);

                    // destroy the pickup first
                    e.getOtherBody().destroy();

                    // then update level state
                    level.decrementPickupCount();
                } finally {
                    processingPickup = false;
                }
            }

            // handle enemy collisions with the character
            else if (e.getReportingBody() instanceof Character playerChar && e.getOtherBody() instanceof Enemy enemy) {
                // check if this is a collision from above (player jumping on enemy)

                // check if this is a collision from above (player jumping on enemy)
                Vec2 playerPos = playerChar.getPosition();
                Vec2 enemyPos = enemy.getPosition();
                Vec2 playerVel = playerChar.getLinearVelocity();

                // calculate if player is above enemy and moving downward
                boolean playerAboveEnemy = playerPos.y > enemyPos.y;
                boolean playerMovingDown = playerVel.y < 0;

                // define a vertical threshold to consider as "above"
                float verticalThreshold = 0.8f; // how much higher the player needs to be
                boolean isHighEnough = playerPos.y > enemyPos.y + verticalThreshold;

                if (playerAboveEnemy && playerMovingDown && isHighEnough) {
                    // player jumped on enemy from above
                    System.out.println("Player jumped on enemy from above!");

                    // player takes damage even when jumping on enemy (reduced damage compared to side hit)
                    boolean stillAlive = playerChar.takeDamage(10);
                    SoundManager.getInstance().play(SoundManager.DEATH);

                    if (!stillAlive) {
                        // player has run out of health
                        System.out.println("Game over! You ran out of health!");
                        SoundManager.getInstance().play(SoundManager.DEATH);
                        level.stopTimer(); // stop the timer before exiting
                        System.exit(0);
                    }

                    // give the player a bounce upward (still bounce even when taking damage)
                    playerChar.setLinearVelocity(new Vec2(playerVel.x, 8f)); // Bounce upward

                    enemy.destroy();
                } else {
                    boolean stillAlive = playerChar.takeDamage(20); // damage for side collision
                    SoundManager.getInstance().play(SoundManager.DAMAGE);

                    if (!stillAlive) {
                        // player has run out of health
                        System.out.println("Game over! You ran out of health!");
                        SoundManager.getInstance().play(SoundManager.ENEMY_DEATH);
                        level.stopTimer(); // stop the timer before exiting
                        System.exit(0);
                    } else {
                        // player still has health, push them back a bit and make the enemy change direction
                        Vec2 pushDirection = playerChar.getPosition().sub(enemy.getPosition());
                        pushDirection.normalize();
                        playerChar.applyImpulse(pushDirection.mul(8f)); // push player away from enemy

                        // make the enemy turn around
                        enemy.changeDirection();
                    }
                }
            }

            // handle enemy collisions with other enemies
            else if (e.getReportingBody() instanceof Enemy && e.getOtherBody() instanceof Enemy) {
                System.out.println("Enemy-Enemy collision detected!");

                // change direction of both enemies
                ((Enemy) e.getReportingBody()).changeDirection();
                ((Enemy) e.getOtherBody()).changeDirection();
            }

            // handle enemy collisions with platforms
            else if (e.getReportingBody() instanceof Enemy && e.getOtherBody() instanceof Platform) {
                System.out.println("Enemy-Platform collision detected!");
                ((Enemy) e.getReportingBody()).changeDirection();
            }
            else if (e.getOtherBody() instanceof Enemy && e.getReportingBody() instanceof Platform) {
                System.out.println("Platform-Enemy collision detected!");
                ((Enemy) e.getOtherBody()).changeDirection();
            }

            // handle enemy collisions with pickups
            else if (e.getReportingBody() instanceof Enemy && e.getOtherBody() instanceof Pickup) {
                System.out.println("Enemy changing direction due to collision with Pickup");
                ((Enemy) e.getReportingBody()).changeDirection();
            }
            else if (e.getOtherBody() instanceof Enemy && e.getReportingBody() instanceof Pickup) {
                System.out.println("Enemy changing direction due to collision with Pickup");
                ((Enemy) e.getOtherBody()).changeDirection();
            }
        } catch (Exception ex) {
            // log any exceptions during collision handling to help with debugging
            System.out.println("Error in collision handling: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}