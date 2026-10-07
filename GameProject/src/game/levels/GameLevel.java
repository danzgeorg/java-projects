package game.levels;

import city.cs.engine.*;
import game.Game;
import game.pickups.Pickup;
import game.character.Character;
import game.enemies.Enemy;
import org.jbox2d.common.Vec2;
import javax.swing.Timer;
import javax.swing.SwingUtilities;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * abstract class for all game levels in the game
 * </p>
 * this class provides shared functionality for all levels including:
 * <ul>
 *   <li>character management</li>
 *   <li>pickup spawning mechanisms</li>
 *   <li>level completion logic</li>
 *   <li>basic physics world management</li>
 * </ul>
 * <p>
 * each new game level extends this class and implements the abstract methods to define behaviors and layouts
 * </p>
 * the level operates with:
 * <ul>
 *   <li>a timer based pickup spawning system</li>
 *   <li>new pickups appear sequentially at predefined positions</li>
 *   <li>level completion is triggered when all pickups have been collected. </li>
 * </ul>
 * </p>
 */

public abstract class GameLevel extends World implements ActionListener {
    // character controlled by the player
    protected game.character.Character character;

    // timer for spawning pickupw
    protected Timer PickupSpawnTimer;
    // timer for level completion
    protected Timer completionTimer;

    // pickup tracking
    protected int totalPickups = 0;
    protected List<Vec2> PickupPositions = new ArrayList<>();
    protected int currentPickupIndex = 0;
    protected boolean waitingForCollection = false;

    // flag to track if level completion is in progress
    private boolean completionInProgress = false;

    /**
     * constructor for the base GameLevel:
     * <ul>
     *   <li> creates the character
     *   <li> sets up the timer
     * </ul>
     * note: the timers are initiliased but not started
     */
    public GameLevel(int levelNumber) {
        super();

        // create the character
        character = new game.character.Character(this, 0, levelNumber);

        // initialise the timer but dont start it yet
        PickupSpawnTimer = new Timer(2000, this);
        PickupSpawnTimer.setRepeats(false); // only fire once per start

        // initialise the completion timer but dont start it
        completionTimer = new Timer(500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processLevelCompletion();
            }
        });
        completionTimer.setRepeats(false);
    }

    /**
     * constructor that accepts both score and health to carry between levels
     */
    public GameLevel(int levelNumber, int score, int health) {
        super();

        // create the character with the provided score and health
        character = new game.character.Character(this, score, levelNumber);
        character.setHealth(health); // Set the carried-over health value

        // initialise the timer but dont start it yet
        PickupSpawnTimer = new Timer(2000, this);
        PickupSpawnTimer.setRepeats(false); // only fire once per start

        // initialise the completion timer but dont start it
        completionTimer = new Timer(500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processLevelCompletion();
            }
        });
        completionTimer.setRepeats(false);
    }

    public Character getCharacter() {
        return character;
    }

    /**
     * creates a new pickup at the next predefined position in the PickupPositions list.
     */
    protected void spawnNextPickup() {
        if (currentPickupIndex < PickupPositions.size()) {
            Vec2 position = PickupPositions.get(currentPickupIndex);
            Pickup pickup = new Pickup(this, position);
            totalPickups++;
            waitingForCollection = true;
            System.out.println("Spawned pickup at position: " + position);

            // move to the next banana position for next time
            currentPickupIndex++;
        } else {
            System.out.println("All pickups have been spawned!");
        }
    }

    /**
     * decrements the pickup count and triggers next spawn if needed
     */
    public void decrementPickupCount() {
        totalPickups--;

        // when a banana is collected, start the timer to spawn the next one
        if (totalPickups == 0 && currentPickupIndex < PickupPositions.size()) {
            System.out.println("Pickup collected! Next pickup will spawn in 2 seconds...");
            waitingForCollection = false;
            PickupSpawnTimer.restart();
        } else if (totalPickups == 0 && !completionInProgress) {
            // check completion if no more bananas to spawn
            startCompletionCheck();
        }
    }

    /**
     * starts the level completion check with a timer
     */
    protected void startCompletionCheck() {
        if (!completionInProgress) {
            System.out.println("Starting completion check...");
            completionInProgress = true;

            // stop all current physics
            for (Body body : this.getDynamicBodies()) {
                if (body instanceof Enemy) {
                    ((Enemy) body).setLinearVelocity(new Vec2(0, 0));
                }
            }

            // restart the timer if its already running
            if (completionTimer.isRunning()) {
                completionTimer.restart();
            } else {
                completionTimer.start();
            }
        }
    }

    /**
     * called by the completion timer and checks if the level is actually complete using the isComplete() method
     * <ul>
     *   <li> if the level is complete, it stops all timers and transitions to the next level after a small delay.
     *   <li> if the level is not complete despite the completion check being triggered, it resets the completion flag.
     * </ul>
     */
    private void processLevelCompletion() {
        // check completion condition
        if (isComplete()) {
            System.out.println("Level completed! Transitioning to next level...");
            stopTimer(); // Stop all timers
            game.SoundManager.getInstance().play(game.SoundManager.LEVEL_COMPLETE);

            // use SwingUtilities.invokeLater to ensure UI updates happen on edt (event dispatch thread
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    Timer delayTimer = new Timer(200, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            // Pass both score AND health to next level
                            Game.goToNextLevel(character.getScore(), character.getHealth());
                        }
                    });
                    delayTimer.setRepeats(false);
                    delayTimer.start();
                }
            });
        } else {
            // reset flag if level is somehow not complete
            System.out.println("Level not complete despite completion check. Resetting flag.");
            completionInProgress = false;
        }
    }

    public void stopTimer() {
        if (PickupSpawnTimer != null && PickupSpawnTimer.isRunning()) {
            PickupSpawnTimer.stop();
            System.out.println("Pickup spawn timer stopped.");
        }

        if (completionTimer != null && completionTimer.isRunning()) {
            completionTimer.stop();
            System.out.println("Completion timer stopped.");
        }

        // stop character timers to prevent memory leaks
        if (character != null) {
            character.stopAllTimers();
            System.out.println("Character timers stopped.");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // this handler is only for the banana spawn timer
        spawnNextPickup();
    }

    /**
     * defines what it means for a level to be complete
     * @return true if the level is complete, false otherwise
     */
    public abstract boolean isComplete();

    /**
     * populates the level with platforms, enemies, and other elements
     */
    public abstract void populate();
}