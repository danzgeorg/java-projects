package game.levels;

import city.cs.engine.*;
import game.SoundManager;
import game.collisions.GenericCollisionListener;
import game.platform.Platform;
import game.enemies.Enemy;
import org.jbox2d.common.Vec2;

public class Level1 extends GameLevel {
    private Enemy enemy1, enemy2;
    private final int PICKUPS_REQUIRED = 3;

    public Level1(int initialScore, int initialHealth) {
        super(1, initialScore, initialHealth);
        SoundManager.getInstance().stopAllSounds();
        SoundManager.getInstance().loop(SoundManager.LEVEL1MUSIC);

        // define the pickup positions for this level
        PickupPositions.add(new Vec2(-15, -10f));
        PickupPositions.add(new Vec2(-5, 6f));
        PickupPositions.add(new Vec2(-24, 12f));

        // position the character
        character.setPosition(new Vec2(0f, -8.5f));

        // populate the level with platforms, enemies, etc.
        populate();

        // spawn the first pickup immediately
        spawnNextPickup();

        System.out.println("Level 1 started! Collect 3 pickups to complete the level.");
    }

    // for backward compatibility
    public Level1(int initialScore) {
        this(initialScore, 100); // default to full health
    }

    @Override
    public void populate() {
        // make the ground
        Shape groundShape = new BoxShape(25, 0.5f);
        StaticBody ground = new StaticBody(this, groundShape);
        ground.setPosition(new Vec2(0f, -11.5f));

        // add enemies
        enemy1 = new Enemy(this, new Vec2(5, -10f),1);
        enemy2 = new Enemy(this, new Vec2(-9,-10f),1);

        // create a unified collision listener
        GenericCollisionListener gcl = new GenericCollisionListener(character, this);

        // add the collision listener only to the character
        // this way, pickups will only be detected when the character collides with them
        character.addCollisionListener(gcl);

        // add the same listener to enemies for enemy-enemy interactions
        enemy1.addCollisionListener(gcl);
        enemy2.addCollisionListener(gcl);

        // make the platforms
        new Platform(this, new Vec2(-10, -6f), 3, 0.5f, false);
        new Platform(this, new Vec2(-6.5f, -1.5f), 0.5f, 5f, false);
        new Platform(this, new Vec2(-2f, 4f), 0.5f, 5f, true);
        new Platform(this, new Vec2(-15, 10f), 10, 0.5f, false);
    }

    @Override
    public boolean isComplete() {
        // level is complete when the player has collected all required pickups
        return character.getScore() >= PICKUPS_REQUIRED;
    }
}