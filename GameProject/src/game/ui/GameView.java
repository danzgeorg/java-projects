package game.ui;

import city.cs.engine.UserView;
import game.character.Character;
import game.levels.GameLevel;
import game.levels.Level1;
import game.levels.Level2;
import game.levels.Level3;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * <p>
 * custom view class for rendering the game world and UI elements and an animated scrolling background
 * </p>
 * it handles:
 * <ul>
 *   <li> drawing the level specific background images that scroll vertically in a continuous loop
 *   <li> rendering UI elements including score display and the health bar
 *   <li> displaying level specific info
 * </ul>
 * <p>
 * the view automatically selects the appropriate background image based on the
 * current level type (Level1, Level2, or Level3) and displays health information
 * with colour coding based on the character's current health percentage
 * </p>
 * <p>
 * the background animation implements a vertical scrolling effect using
 * two copies of the background image that are drawn with a small overlap to prevent
 * any visible seams during the transition
 * </p>
 */
public class GameView extends UserView implements ActionListener {

    private Image background;
    private final GameLevel level;
    private Timer animationTimer;

    // animation scrolling
    private float backgroundY = 0;
    private final float ANIMATION_SPEED = 0.5f;
    private int backgroundHeight;

    // dash message
    private boolean showDashMessage = false;
    private Timer messageTimer;
    private final int MESSAGE_DURATION = 2000;

    // double jump message
    private boolean showDoubleJumpMessage = false;
    private Timer doubleJumpMessageTimer;
    private final int DOUBLEJUMP_MESSAGE_DURATION = 2000;

    /**
     * creates a new GameView for the specified level with the given dimensions
     * <p>
     * this constructor:
     * <ol>
     *   <li> automatically selects and loads the appropriate background image based on the level type
     *   <li> initialises the background animation system
     *   <li> starts the animation timer to begin the scrolling effect
     * </ol>
     * </p>
     *
     * @param level the game level to be displayed in this view
     * @param width the width of the view in pixels
     * @param height the height of the view in pixels
     */
    public GameView(GameLevel level, int width, int height) {
        super(level, width, height);
        this.level = level;

        // set the appropriate background based on the level type
        if (level instanceof Level1) {
            background = new ImageIcon("java-project-2025-danzgeorg/data/textures/lvl1background.png").getImage();
        } else if (level instanceof Level2) {
            background = new ImageIcon("java-project-2025-danzgeorg/data/textures/lvl2background.png").getImage();
        } else if (level instanceof Level3) {
            background = new ImageIcon("java-project-2025-danzgeorg/data/textures/lvl3background.jpg").getImage();
        }

        // initialise animation settings after image is loaded
        if (background != null) {
            // create a MediaTracker to ensure image is fully loaded
            MediaTracker tracker = new MediaTracker(this);
            tracker.addImage(background, 0);
            try {
                tracker.waitForID(0);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            backgroundHeight = background.getHeight(this);
            if (backgroundHeight <= 0) {
                // fallback if height cannot be determined
                backgroundHeight = height;
            }
        } else {
            // fallback if image failed to load
            backgroundHeight = height;
        }

        // start animation timer (16ms ≈ 60fps)
        animationTimer = new Timer(16, this);
        animationTimer.start();

        if (level instanceof Level2) {
            showDashMessage = true;
            messageTimer = new Timer(MESSAGE_DURATION, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    showDashMessage = false;
                    repaint(); // repaint to remove the message
                }
            });
            messageTimer.setRepeats(false);
            messageTimer.start();
        }

        if (level instanceof Level3) {
            showDoubleJumpMessage = true;
            doubleJumpMessageTimer = new Timer(DOUBLEJUMP_MESSAGE_DURATION, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    showDoubleJumpMessage = false;
                    repaint(); // repaint to remove the message
                }
            });
            doubleJumpMessageTimer.setRepeats(false);
            doubleJumpMessageTimer.start();
        }
    }

    /**
     * draws the scrolling background for the current level
     * <p>
     * it does this by:
     * <ol>
     *   <li> drawing two copies of the background image, one below the other
     *   <li> moving both images downward based on the current backgroundY position
     *   <li> including a 1 pixel overlap between images to prevent seams
     *   <li> resetting the background position when a full cycle is complete
     * </ol>
     * </p>
     * <p>
     * the scrolling is achieved by managing the transition between the two copies of the background image,
     * and making sure no gap appears during the reset
     * </p>
     *
     * @param g the Graphics2D context used for drawing
     */
    @Override
    protected void paintBackground(Graphics2D g) {
        if (background == null) {
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, getWidth(), getHeight());
            return;
        }

        // calculate the integer position for rendering to avoid fractional pixel issues
        int yPos = (int)backgroundY;

        // draw two copies of the background with 1px overlap to eliminate white lines
        g.drawImage(background, 0, yPos, getWidth(), backgroundHeight, this);
        g.drawImage(background, 0, yPos - backgroundHeight + 1, getWidth(), backgroundHeight, this);

        // if weve scrolled past the background height, reset position
        // the -1 ensures we don't get a gap during transition
        if (backgroundY >= backgroundHeight - 1) {
            backgroundY = 0;
        }
    }

    /**
     * renders UI elements and game information on top of the game world.
     * <p>
     * automatically called by the game engine's rendering system after all game objects are drawn, ensuring UI
     * elements appear on top of everything else
     * </p>
     * the method renders:
     * <ul>
     *   <li> the player's current score
     *   <li> a health bar that changes colour based on the character's health %
     *   <li> health information as text (current/maximum)
     *   <li> level specific objectives and info
     * </ul>
     * the health bar is colour coded:
     * <ul>
     *   <li> green: health > 70%
     *   <li> yellow: health between 30% and 70%
     *   <li> red: health < 30%
     * </ul>
     *
     * @param g The Graphics2D context used for drawing
     */

    @Override
    protected void paintForeground(Graphics2D g) {
        // get the character
        Character character = level.getCharacter();

        // display level information
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Score: " + character.getScore(), 20, 30);

        // display health
        int health = character.getHealth();
        int maxHealth = character.getMaxHealth();

        // draw health bar background
        g.setColor(Color.DARK_GRAY);
        g.fillRect(20, 40, 200, 20);

        // calculate health bar width based on current health percentage
        int healthBarWidth = (int)((health / (float)maxHealth) * 200);

        // choose color based on health percentage
        if (health > 70) {
            g.setColor(Color.GREEN);
        } else if (health > 30) {
            g.setColor(Color.YELLOW);
        } else {
            g.setColor(Color.RED);
        }

        // draw health bar
        g.fillRect(20, 40, healthBarWidth, 20);
        g.setColor(Color.BLACK);
        g.drawString("HP: " + health + "/" + maxHealth, 85, 57);

        // display level specific objectives
        if (level instanceof Level1) {
            g.drawString("Level 1: Collect 3 bananas", 20, 80);
        } else if (level instanceof Level2) {
            g.drawString("Level 2: Collect 5 apples", 20, 80);
        } else if (level instanceof Level3) {
            g.drawString("Level 3: Collect 8 pickups", 20, 80);
        }

        if (showDashMessage) {
            // create a semi transparent black background for better readability
            g.setColor(new Color(0, 0, 0, 180));
            int messageWidth = 400;
            int messageHeight = 50;
            int messageX = (getWidth() - messageWidth) / 2;
            int messageY = (getHeight() - messageHeight) / 2;
            g.fillRoundRect(messageX, messageY, messageWidth, messageHeight, 15, 15);

            // draw the message text in white
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 20));
            g.drawString("Press Q or SHIFT to Dash!", messageX + 90, messageY + 30);
        }

        if (showDoubleJumpMessage) {
            // create a semi transparent black background for better readability
            g.setColor(new Color(0, 0, 0, 180));
            int messageWidth = 500;
            int messageHeight = 50;
            int messageX = (getWidth() - messageWidth) / 2;
            int messageY = (getHeight() - messageHeight) / 2 + 60; // Below dash message
            g.fillRoundRect(messageX, messageY, messageWidth, messageHeight, 15, 15);

            // draw the message text in white
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 20));
            g.drawString("Press SPACE in mid-air to Double Jump!", messageX + 75, messageY + 30);
        }

    }

    /**
     * handles the animation timer events to update the background position
     * <p>
     * this method is called automatically by the animation timer at regular intervals
     * (approximately 60 times per second, or 60fps) to:
     * <ol>
     *   <li> update the background's vertical position by the animation speed
     *   <li> request a repaint of the view to display the updated animation
     * </ol>
     * </p>
     * <p>
     * using a float for the background position allows for smoother scrolling
     * </p>
     *
     * @param e the ActionEvent triggered by the animation timer
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        // update background position with fractional movement
        backgroundY += ANIMATION_SPEED;

        // request repaint to show the animation
        repaint();
    }

    /**
     * stops the animation timer to prevent memory leaks and CPU usage.
     * <p>
     * this method should be called when:
     * <ul>
     *   <li> transitioning between game levels
     *   <li> closing the game
     *   <li> when the view is no longer visible or needed
     * </ul>
     * </p>
     * <p>
     * stopping the timer ensures that the animation doesnt continue to run in the background, which could cause
     * performance issues
     * </p>
     */
    public void stopAnimationTimer() {
        if (animationTimer != null && animationTimer.isRunning()) {
            animationTimer.stop();
        }

        // stop the message timer to prevent memory leaks
        if (messageTimer != null && messageTimer.isRunning()) {
            messageTimer.stop();
            messageTimer = null; // for garbage collection
        }

        if (doubleJumpMessageTimer != null && doubleJumpMessageTimer.isRunning()) {
            doubleJumpMessageTimer.stop();
            doubleJumpMessageTimer = null; // for garbage collection
        }
    }
}