package game.levels;

import city.cs.engine.*;
import game.SoundManager;
import game.collisions.GenericCollisionListener;
import game.platform.Platform;
import game.enemies.Enemy;
import org.jbox2d.common.Vec2;

public class Level3 extends GameLevel {
    private Enemy enemy1, enemy2, enemy3, enemy4, enemy5;
    private final int PICKUPS_REQUIRED = 8;

    public Level3(int initialScore, int initialHealth) {
        super(3, initialScore, initialHealth);
        SoundManager.getInstance().stopAllSounds();
        SoundManager.getInstance().loop(SoundManager.LEVEL3MUSIC);

        // define the pickup positions for this level (challenging positions)
        PickupPositions.add(new Vec2(-17, 14f));   // top left
        PickupPositions.add(new Vec2(22, 14f));    // top right
        PickupPositions.add(new Vec2(0, 17f));     // center top
        PickupPositions.add(new Vec2(-15, 5.5f));    // middle left
        PickupPositions.add(new Vec2(15, 5.5f));     // middle right
        PickupPositions.add(new Vec2(-8, -2f));    // lower left
        PickupPositions.add(new Vec2(8, -2f));     // lower right
        PickupPositions.add(new Vec2(0, 7f));     // Pickup 8: Center

        // position the character
        character.setPosition(new Vec2(0f, -8.5f));

        // populate the level with platforms, enemies, etc.
        populate();

        // spawn the first pickup immediately
        spawnNextPickup();

        System.out.println("Level 3 started! Collect 8 pickups to complete the level.");
    }

    // for backward compatibility
    public Level3(int initialScore) {
        this(initialScore, 100); // default to full health
    }

    @Override
    public void populate() {
        // make the ground
        Shape groundShape = new BoxShape(25, 0.5f);
        StaticBody ground = new StaticBody(this, groundShape);
        ground.setPosition(new Vec2(0f, -11.5f));

        // add enemies (5 enemies for level 3)
        enemy1 = new Enemy(this, new Vec2(15, -10f), 3);
        enemy2 = new Enemy(this, new Vec2(-15, -10f), 3);
        enemy3 = new Enemy(this, new Vec2(-15, 4.5f), 3);
        enemy4 = new Enemy(this, new Vec2(-12, 12f), 3);
        enemy5 = new Enemy(this, new Vec2(12, 12f), 3);

        // create a unified collision listener
        GenericCollisionListener gcl = new GenericCollisionListener(character, this);

        // add collision listeners to character and enemies
        character.addCollisionListener(gcl);
        enemy1.addCollisionListener(gcl);
        enemy2.addCollisionListener(gcl);
        enemy3.addCollisionListener(gcl);
        enemy4.addCollisionListener(gcl);
        enemy5.addCollisionListener(gcl);

        // create a complex platform structure for level 3
        new Platform(this, new Vec2(-18, -6f), 5, 0.5f, false);
        new Platform(this, new Vec2(18, -6f), 5, 0.5f, false);
        new Platform(this, new Vec2(-15, 3f), 4, 0.5f, false);
        new Platform(this, new Vec2(15, 3f), 4, 0.5f, false);
        new Platform(this, new Vec2(0, 0f), 3, 0.5f, false);
        new Platform(this, new Vec2(-15, 11f), 6, 0.5f, false);
        new Platform(this, new Vec2(15, 11f), 6, 0.5f, false);
        new Platform(this, new Vec2(0, 15f), 4, 0.5f, false);
        new Platform(this, new Vec2(-8, 6f), 2, 0.5f, false);
        new Platform(this, new Vec2(0, 6f), 2, 0.5f, false);
        new Platform(this, new Vec2(8, 6f), 2, 0.5f, false);
        new Platform(this, new Vec2(-22, 4.5f), 0.5f, 8f, true);
        new Platform(this, new Vec2(22, 4.5f), 0.5f, 8f, true);
        new Platform(this, new Vec2(-8, -4.5f), 0.5f, 4f, true);
        new Platform(this, new Vec2(8, -4.5f), 0.5f, 4f, true);
        new Platform(this, new Vec2(-4, 11f), 0.5f, 4f, true);
        new Platform(this, new Vec2(4, 11f), 0.5f, 4f, true);
    }

    @Override
    public boolean isComplete() {
        // level is complete when the player has collected all required pickups
        return character.getScore() >= PICKUPS_REQUIRED;
    }
}