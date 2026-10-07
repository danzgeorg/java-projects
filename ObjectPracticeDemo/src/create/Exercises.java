package create;

import javax.swing.*;

public class Exercises {

    public static void doExercises(){

        JFrame frame = new JFrame("Circles");

        MovingCircles circles = new MovingCircles();

        circles.addCircle(new Circle(100,100,50));

        frame.add(circles);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);


    }
}
