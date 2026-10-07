package create;

import java.awt.*;

public class Circle {
    int x;
    int y;
    int r;

    Circle(int x, int y, int r){
        this.x = x;
        this.y = y;
        this.r = r;
    }

    void moveBy(int dx, int dy){
        x += dx;
        y += dy;
    }

    void draw(Graphics g){
        g.setColor(Color.black);
        g.fillOval(x-r/2, y-r/2, r,r);

    }

}
