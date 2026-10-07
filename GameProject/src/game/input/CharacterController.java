package game.input;

import game.SoundManager;
import game.character.Character;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class CharacterController implements KeyListener {

    private final game.character.Character character;

    public CharacterController(Character character) {
        this.character = character;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        // handle movement based on key presses
        if (code == KeyEvent.VK_W || code == KeyEvent.VK_SPACE) {
            character.jump(10);
            // jump
            game.SoundManager.getInstance().play(SoundManager.JUMP);
        } else if (code == KeyEvent.VK_S) {
            // move downward
            character.startMovingDown();
        } else if (code == KeyEvent.VK_A) {
            // move left
            character.startWalking(-4);
            character.turnLeft();
        } else if (code == KeyEvent.VK_D) {
            // move right
            character.startWalking(4);
            character.turnRight();
        } else if (code == KeyEvent.VK_Q || code == KeyEvent.VK_SHIFT) {
            // try dash
            if (character.canDash()) {
                character.dash();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        // stop movement when keys are released
        if (code == KeyEvent.VK_A || code == KeyEvent.VK_D) {
            character.stopWalking();
        }

        // stop downward movement when S is released
        if (code == KeyEvent.VK_S) {
            character.stopMovingDown();
        }
    }
}