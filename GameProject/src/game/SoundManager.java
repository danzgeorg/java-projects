package game;

import city.cs.engine.SoundClip;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * singleton class responsible for managing all game sounds and music
 * <ul>
 *   <li> provides functionality to load, play, loop, and stop sound effects and music
 *   <li> implements volume control and sound enabling/disabling features
 * </ul>
 * <p>
 * this class follows the singleton design pattern to ensure only one instance exists throughout the application
 * </p>
 */

public class SoundManager {
    private static SoundManager instance;
    private Map<String, SoundClip> sounds;
    private float masterVolume = 0.1f;
    private boolean soundEnabled = true;

    // constants for sound names

    // character sounds
    public static final String JUMP = "jump";
    public static final String DAMAGE = "damage";
    public static final String DEATH = "death";
    public static final String DASH = "dash";

    // enemy sounds
    public static final String ENEMY_DEATH = "enemy_death";

    // pickup sounds
    public static final String PICKUP = "pickup";

    // level sounds & music
    public static final String LEVEL_COMPLETE = "level_complete";
    public static final String GAME_COMPLETE = "game_complete";
    public static final String LEVEL1MUSIC = "level1music";
    public static final String LEVEL2MUSIC = "level2music";
    public static final String LEVEL3MUSIC = "level3music";

    // UI sounds & music
    public static final String MAIN_MENU_MUSIC = "music";

    /**
     * <ul>
     *   <li> gets the singleton instance of SoundManager
     *   <li> creates the instance if it doesn't already exist
     * </ul>
     *
     * @return the SoundManager instance
     */
    public static SoundManager getInstance() {
        if (instance == null) {
            instance = new SoundManager();
        }
        return instance;
    }

    /**
     * <ul>
     *   <li> private constructor to prevent instantiation outside the class
     *   <li> initialises the sounds map and loads all sound resources
     * </ul>
     */

    private SoundManager() {
        sounds = new HashMap<>();
        loadAllSounds();
    }

    private void loadAllSounds() {
        try {
            loadCharacterSounds();
            loadPickupSound();
            loadLevelSounds();
            loadUISounds();

            System.out.println("All sounds loaded successfully");
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println("Error loading sounds: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void loadUISounds() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        sounds.put(MAIN_MENU_MUSIC, new SoundClip
                ("java-project-2025-danzgeorg/data/sounds/UI/main_menu_music.wav"));
    }

    private void loadLevelSounds() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        sounds.put(LEVEL_COMPLETE, new SoundClip("java-project-2025-danzgeorg/data/sounds/misc/level_complete.wav"));
        sounds.put(GAME_COMPLETE, new SoundClip("java-project-2025-danzgeorg/data/sounds/misc/game_complete.wav"));
        sounds.put(LEVEL1MUSIC, new SoundClip("java-project-2025-danzgeorg/data/sounds/misc/level1music.wav"));
        sounds.put(LEVEL2MUSIC, new SoundClip("java-project-2025-danzgeorg/data/sounds/misc/level2music.wav"));
        sounds.put(LEVEL3MUSIC, new SoundClip("java-project-2025-danzgeorg/data/sounds/misc/level3music.wav"));
    }


    private void loadPickupSound() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        sounds.put(PICKUP, new SoundClip("java-project-2025-danzgeorg/data/sounds/pickup/pickup.wav"));
    }

    private void loadCharacterSounds() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        sounds.put(JUMP, new SoundClip("java-project-2025-danzgeorg/data/sounds/character/jump.wav"));
        sounds.put(DAMAGE, new SoundClip("java-project-2025-danzgeorg/data/sounds/character/damage.wav"));
        sounds.put(DEATH, new SoundClip("java-project-2025-danzgeorg/data/sounds/character/death.wav"));
        sounds.put(DASH, new SoundClip("java-project-2025-danzgeorg/data/sounds/character/dash.wav"));
    }

    /**
     * plays a sound effect once
     *
     * @param soundName the name of the sound to play
     */

    public void play(String soundName) {
        if (!soundEnabled) return;

        SoundClip sound = sounds.get(soundName);
        if (sound != null) {
            // set the volume before playing
            sound.setVolume(masterVolume);
            sound.play();
        } else {
            System.out.println("Warning: Sound '" + soundName + "' not found");
        }
    }

    /**
     * plays a sound in a continuous loop
     *
     * @param soundName the name of the sound to loop
     */

    public void loop(String soundName) {
        if (!soundEnabled) return;

        SoundClip sound = sounds.get(soundName);
        if (sound != null) {
            // Set the volume before looping
            sound.setVolume(masterVolume);
            sound.loop();
        } else {
            System.out.println("Warning: Sound '" + soundName + "' not found");
        }
    }

    /**
     * stops playing a specific sound
     *
     * @param soundName the name of the sound to stop
     */
    public void stop(String soundName) {
        SoundClip sound = sounds.get(soundName);
        if (sound != null) {
            sound.stop();
        }
    }

    /**
     * <ul>
     *   <li> stops all currently playing sounds
     *   <li> this method is useful when transitioning between game states or when pausing/muting the game
     * </ul>
     */

    public void stopAllSounds() {
        for (SoundClip sound : sounds.values()) {
            sound.stop();
        }
    }

    public boolean isSoundEnabled() {
        return soundEnabled;
    }

    public void setSoundEnabled(boolean enabled) {
        this.soundEnabled = enabled;
        if (!enabled) {
            // Stop all sounds when muting
            stopAllSounds();
        }
    }

    /**
     * <ul>
     *   <li> sets the master volume level for all sounds
     *   <li> the volume is clamped between 0.0 (silent) and 1.0 (maximum volume)
     * </ul>
     *
     * @param volume the volume level between 0.0 and 1.0
     */

    public void setMasterVolume(float volume) {
        this.masterVolume = Math.max(0.0f, Math.min(1.0f, volume));
    }
}