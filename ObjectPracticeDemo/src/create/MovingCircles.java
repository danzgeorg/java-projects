package create;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MovingCircles extends JPanel implements ActionListener{

    ArrayList<Circle> circles;

    public MovingCircles(){
        circles = new ArrayList<>();
        Timer t = new Timer(100, this);
        t.start();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(600,400);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (Circle c : circles)
            c.moveBy(10, 0);
        this.repaint();

    }

    public void addCircle(Circle c){
        circles.add(c);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        for (Circle c : circles)
            c.draw(g);

    }
}
