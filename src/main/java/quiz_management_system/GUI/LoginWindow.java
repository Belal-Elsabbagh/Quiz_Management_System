package quiz_management_system.GUI;

import quiz_management_system.UserType.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;

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

public class LoginWindow extends JFrame
{
    static JFrame window;

    static JPanel content, user, pass;
    static JLabel password_label, username_label;
    static JTextField username_text;
    static JButton actionLogin;
    static JPasswordField password_text;

    static
    {
        username_label = new JLabel("Username");
        password_label = new JLabel("Password");
        username_text = new JTextField(20);
        password_text = new JPasswordField(20);
        actionLogin = new JButton("Log in");
        user = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pass = new JPanel(new FlowLayout(FlowLayout.LEFT));
        content = new JPanel(new GridLayout(2,1));
    }

    public LoginWindow()
    {
        user.add(username_label);
        user.add(username_text);
        pass.add(password_label);
        pass.add(password_text);

        content.add(user);
        content.add(pass);

        add(content, BorderLayout.CENTER);
        add(actionLogin, BorderLayout.SOUTH);


        ActionLogin actionlogin = new ActionLogin();
        actionLogin.addActionListener(actionlogin);
    }

    public static void constructWindow()
    {
        window = new LoginWindow();
        window.setTitle("Quiz Management System Login");
        window.setSize(400, 200);
        window.setLocationRelativeTo(null); // to not have it open at the corner
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setVisible(true);
    }

    static class ActionLogin implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            String Username = username_text.getText();
            String Password1 = String.valueOf(password_text.getPassword());

            User newUser = User.login(Username, Password1);
            if (newUser == null)
            {
                JOptionPane.showMessageDialog(null, "Username or Password mismatch");
                return;
            }
            JOptionPane.showMessageDialog(null, "Login Successful");

        }
    }
}
