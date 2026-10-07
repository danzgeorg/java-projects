package game.pickups;

import city.cs.engine.*;
import game.levels.Level2;
import game.levels.Level3;
import org.jbox2d.common.Vec2;

/**
 * <p>
 * pickup items are static bodies that the player can collect to increase score.
 * </p>
 * the appearance of the pickup varies based on the current game level:
 * <ol>
 *   <li>Level 1: Banana</li>
 *   <li>Level 2: Apple</li>
 *   <li>Level 3: Melon/Strawberry</li>
 * </ol>
 *
 * all pickups share the same collision shape but display different images depending on the level
 *
 */

public class Pickup extends StaticBody {
    private static final Shape PickupShape = new BoxShape(1, 2);
    private static final BodyImage BananaImage = new BodyImage
            ("java-project-2025-danzgeorg/data/items/banana-pickup.png", 2f);
    private static final BodyImage AppleImage = new BodyImage
            ("java-project-2025-danzgeorg/data/items/apple-pickup.png", 2f);
    private static final BodyImage StrawberryImage = new BodyImage
            ("java-project-2025-danzgeorg/data/items/melon-pickup.png", 1.25f);

    public Pickup(World w, Vec2 position) {
        super(w, PickupShape);
        if (w instanceof Level3) {
            addImage(StrawberryImage);
        } else if (w instanceof Level2) {
            addImage(AppleImage);
        } else {
            addImage(BananaImage);
        }

        setPosition(position);
    }
}