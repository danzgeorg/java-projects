package game;

import game.input.CharacterController;
import game.levels.GameLevel;
import game.levels.Level1;
import game.levels.Level2;
import game.levels.Level3;
import game.ui.GameView;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


/**
 * <p>
 * main game class that serves as the entry point and manages level transition
 * </p>
 * responsible for:
 * <ul>
 *   <li> initialising the game environment
 *   <li> managing the main game window
 *   <li> creating and transitioning between game levels
 *   <li> handling level progression and completion
 *   <li> maintaining game state between levels (e.g. score)
 * </ul>
 * <p>
 * consists of multiple levels (currently 3) that the player progresses through sequentially. Each level has its own
 * objectives, but the player's score is carried over between levels
 * </p>
 */

public class Game {

    private static GameLevel currentLevel;
    private static Game gameInstance;
    private static JFrame frame;
    private static GameView view;
    private static int currentLevelNumber = 3; // start at level 1
    private static final int MAX_LEVELS = 3;
    private static boolean transitionInProgress = false;

    /** initialise a new Game. */
    public Game() {
        gameInstance = this;
        SoundManager.getInstance().setMasterVolume(0.1f);
        SoundManager.getInstance().stopAllSounds();

        // load and play background music
        try {
            // initialise background music
            SoundManager.getInstance().loop(SoundManager.MAIN_MENU_MUSIC);
        } catch (Exception e) {
            System.out.println("Error initializing sound: " + e);
        }
        // create the first level with a starting score of 0 and full health
        startLevel(currentLevelNumber, 0, 100);
    }

    /**
     * <p>
     * starts or restarts a specific game level
     * </p>
     * handles:
     * <ul>
     *   <li> stopping the previous level and cleaning up resources
     *   <li> creating the appropriate level based on level number
     *   <li> setting up the game view and controller
     *   <li> configuring the game window
     *   <li> starting the new level
     * </ul>
     * <p>
     * i've taken more care to clean up resources between level transition to prevent any memory leaks and stop any
     * freezing issues
     * </p>
     *
     * @param levelNumber the number of the level to start (1, 2, or 3)
     * @param score the score to carry over from the previous level (0 for new game)
     * @param health the health to carry over from the previous level (100 for new game)
     */

    private void startLevel(int levelNumber, int score, int health) {
        // stop the current level if it exists
        if (currentLevel != null) {
            // clean up more aggressively
            try {
                System.out.println("Stopping current level and cleaning up resources...");
                currentLevel.stopTimer();
                currentLevel.stop();

                // allow some time for resources to be released
                Thread.sleep(100);

                // remove references to encourage garbage collection
                currentLevel = null;
                System.gc(); // request garbage collection

                // allow some time for garbage collection
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // if the frame exists, remove the old view
            if (frame != null && view != null) {
                frame.remove(view);
                view = null;
            }
            if (view != null) {
                view.stopAnimationTimer();
            }
        }

        System.out.println("Creating new level: " + levelNumber);

        // create the new level with both score and health
        if (levelNumber == 1) {
            currentLevel = new Level1(score, health);
        } else if (levelNumber == 2) {
            currentLevel = new Level2(score, health);
        } else if (levelNumber == 3) {
            currentLevel = new Level3(score, health);
        } else {
            System.out.println("Game completed! Thanks for playing!");
            System.exit(0);
            return;
        }

        // create or update the view
        view = new GameView(currentLevel, 1920, 1080); // using a smaller size to reduce memory usage

        // create controller for character movement
        CharacterController controller = new CharacterController(currentLevel.getCharacter());
        view.addKeyListener(controller);

        // create or update the frame
        if (frame == null) {
            frame = new JFrame("Pixel Adventure - Level " + levelNumber);

            // enable the frame to quit the application when the x button is pressed
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            // add window listener to stop the timer when closing the game
            frame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    if (currentLevel != null) {
                        currentLevel.stopTimer();
                    }
                    if (view != null) {
                        view.stopAnimationTimer();
                    }
                    super.windowClosing(e);
                }
            });

            frame.setLocationByPlatform(true);
            frame.setResizable(true);
        } else {
            frame.setTitle("Pixel Adventure - Level " + levelNumber);
        }

        // add the view to the frame
        frame.add(view);
        frame.pack();
        frame.setVisible(true);

        // start the level
        currentLevel.start();
        view.requestFocus();

        System.out.println("Level " + levelNumber + " started successfully");

        // reset transition flag after a short delay to ensure everything is loaded
        Timer transitionTimer = new Timer(500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                transitionInProgress = false;
                System.out.println("Level transition completed, game is ready");
            }
        });
        transitionTimer.setRepeats(false);
        transitionTimer.start();
    }

    /**
     * <p>
     * advances the game to the next level
     * </p>
     * called when a level is completed. it:
     * <ul>
     *   <li> preserves the player's current score and health
     *   <li> increments the level number
     *   <li> initiates a smooth transition to the next level
     *   <li> handles game completion if all levels are completed
     * </ul>
     *
     * <p>
     * i've included safeguards to prevent multiple simultaneous level transitions and ensure all transitions occur on
     * the Event Dispatch Thread for thread safety
     * </p>
     *
     * @param score the player's current score to carry to the next level
     * @param health the player's current health to carry to the next level
     */

    public static void goToNextLevel(int score, int health) {
        if (currentLevel != null && !transitionInProgress) {
            // set flag to prevent multiple transitions
            transitionInProgress = true;

            System.out.println("Level transition initiated");

            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    // create a timer for the transition to allow time for resources to be released
                    Timer transitionTimer = new Timer(500, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            System.out.println("Advancing to next level...");
                            currentLevelNumber++;

                            if (currentLevelNumber <= MAX_LEVELS) {
                                System.out.println("Starting level " + currentLevelNumber);
                                gameInstance.startLevel(currentLevelNumber, score, health);
                            } else {
                                System.out.println("Congratulations! You've completed all levels!");
                                game.SoundManager.getInstance().play(game.SoundManager.GAME_COMPLETE);
                                System.exit(0);
                            }
                        }
                    });
                    transitionTimer.setRepeats(false);
                    transitionTimer.start();
                }
            });
        } else {
            System.out.println("Cannot transition to next level: transition already in progress or no current level");
        }
    }

    /**
     * legacy method for compatibility - forwards to the new method with health parameter
     */
    public static void goToNextLevel() {
        if (currentLevel != null) {
            goToNextLevel(currentLevel.getCharacter().getScore(), currentLevel.getCharacter().getHealth());
        }
    }

    /** Run the game. */
    public static void main(String[] args) {
        // use SwingUtilities to ensure the game starts on the edt (event dispatch thread)
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Game();
            }
        });
    }
}