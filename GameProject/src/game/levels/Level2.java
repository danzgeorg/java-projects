package game.levels;

import city.cs.engine.*;
import game.SoundManager;
import game.collisions.GenericCollisionListener;
import game.platform.Platform;
import game.enemies.Enemy;
import org.jbox2d.common.Vec2;

public class Level2 extends GameLevel {
    private Enemy enemy1, enemy2, enemy3;
    private final int PICKUPS_REQUIRED = 5;

    public Level2(int initialScore, int initialHealth) {
        super(2, initialScore, initialHealth);
        SoundManager.getInstance().stopAllSounds();
        SoundManager.getInstance().loop(SoundManager.LEVEL2MUSIC);

        // define the pickup positions for this level (more challenging positions)
        PickupPositions.add(new Vec2(-18, -4f));
        PickupPositions.add(new Vec2(10, 7.5f));
        PickupPositions.add(new Vec2(-22, 10f));
        PickupPositions.add(new Vec2(18, -5f));
        PickupPositions.add(new Vec2(0, 13f));

        // position the character
        character.setPosition(new Vec2(5f, -8.5f));

        // populate the level with platforms, enemies, etc.
        populate();

        // spawn the first pickup immediately
        spawnNextPickup();

        System.out.println("Level 2 started! Collect 5 pickups to complete the level.");
    }

    // for backward compatibility
    public Level2(int initialScore) {
        this(initialScore, 100); // default to full health
    }

    @Override
    public void populate() {
        // make the ground
        Shape groundShape = new BoxShape(25, 0.5f);
        StaticBody ground = new StaticBody(this, groundShape);
        ground.setPosition(new Vec2(0f, -11.5f));

        // add enemies (more enemies in level 2)
        enemy1 = new Enemy(this, new Vec2(15, -10f),2);
        enemy2 = new Enemy(this, new Vec2(-15, -10f),2);
        enemy3 = new Enemy(this, new Vec2(0, 4f),2);

        // create a unified collision listener
        GenericCollisionListener gcl = new GenericCollisionListener(character, this);
        character.addCollisionListener(gcl);
        enemy1.addCollisionListener(gcl);
        enemy2.addCollisionListener(gcl);
        enemy3.addCollisionListener(gcl);

        // make more complex platforms for level 2
        new Platform(this, new Vec2(-18, -6f), 5, 0.5f, false);
        new Platform(this, new Vec2(18, -7f), 5, 0.5f, false);
        new Platform(this, new Vec2(10, 6f), 3, 0.5f, false);
        new Platform(this, new Vec2(-12, 0f), 3, 0.5f, false);
        new Platform(this, new Vec2(0, 10f), 4, 0.5f, false);
        new Platform(this, new Vec2(-22, 8f), 3, 0.5f, false);
        new Platform(this, new Vec2(-12.5f, 5.5f), 0.5f, 1, false);

        // add some vertical platforms
        new Platform(this, new Vec2(-6f, 4f), 0.5f, 7f, true);
        new Platform(this, new Vec2(6f, 4f), 0.5f, 7f, true);
    }

    @Override
    public boolean isComplete() {
        // level is complete when the player has collected all required pickups
        return character.getScore() >= PICKUPS_REQUIRED;
    }
}