package quiz_management_system.GUI.TeacherGUI;

import quiz_management_system.Quiz_Management_System;

import javax.swing.*;
import java.awt.*;

/**
 * GUI Class Description
 * <p>
 * The main class has static default GUI elements, extends JFrame and implements the Windows Interface
 * which has a function for calling the window when needed.
 * <p>
 * The static block initializes the elements with the properties that we need and loads them into the system.
 * <p>
 * When the class constructor is called, the JFrame constructor is called then the elements are loaded in the window
 * and the action listeners are paired with the objects.
 * <p>
 * Event handling is done by nested class(es).
 */

public class CreateQuizWindow extends JFrame
{
    static JFrame window;

    static JPanel p1, p2, p3 , p4;
    static
    {
        p1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        p2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        p3 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        p4 = new JPanel(new FlowLayout(FlowLayout.LEFT));

    }

    public CreateQuizWindow()
    {

    }

    public static void constructWindow()
    {
        window = new CreateQuizWindow();
        window.setTitle("Logged in as " + Quiz_Management_System.getActiveUser().getUsername());
        window.setSize(400, 200);
        window.setLocationRelativeTo(null); // to not have it open at the corner
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setVisible(true);
    }

}
