package quiz_management_system;

import quiz_management_system.UserType.User;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
    static JPanel Back, up;
    static JLabel password_label, username_label, img;
    static Icon icon;
    static JTextField username_text;
    static JButton actionLogin;
    static JPasswordField password_text;

    static
    {
        Back = new JPanel();
        up = new JPanel();
        username_label = new JLabel("Username: ");
        password_label = new JLabel("Password: ");
        username_text = new JTextField(20);
        password_text = new JPasswordField(20);
        actionLogin = new JButton("Log in");
        icon = new ImageIcon("Q1.PNG");
        img = new JLabel(icon);

    }

    public LoginWindow()
    {
        //labels
        username_label.setBounds(100, 280, 100, 30);
        password_label.setBounds(100, 340, 100, 30);
        add(username_label);
        add(password_label);

        //Text field
        username_text.setBounds(180, 285, 200, 30);
        password_text.setBounds(180, 345, 200, 30);
        add(username_text);
        add(password_text);

        actionLogin.setBackground(new Color(222, 184, 150));
        actionLogin.setBounds(100, 400, 280, 30);
        add(actionLogin);
        //image
        up.add(img);
        up.setBackground(Color.WHITE);
        add(up, BorderLayout.NORTH);
        //BackGround
        Back.setBackground(Color.WHITE);
        add(Back, BorderLayout.CENTER);

        ActionLogin actionlogin = new ActionLogin();
        actionLogin.addActionListener(actionlogin);

        //Login Button activation
        DL(username_text);
        if (username_text.getText().isEmpty())
        {
            actionLogin.setEnabled(false);
        }
    }

    public static void constructWindow()
    {
        window = new LoginWindow();
        window.setTitle("Quiz Management System Login");
        window.setSize(550, 550);
        window.setLocationRelativeTo(null); // to not have it open at the corner
        window.setResizable(false);
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

            Quiz_Management_System.setActiveUser(newUser);
            window.setVisible(false);
            Quiz_Management_System.getActiveUser().listMenu();
        }
    }

    public void DL(JTextField x)
    {
        x.getDocument().addDocumentListener(new DocumentListener()
        {
            public void changedUpdate(DocumentEvent e)
            {
                changed();
            }

            public void removeUpdate(DocumentEvent e)
            {
                changed();
            }

            public void insertUpdate(DocumentEvent e)
            {
                changed();
            }

            public void changed()
            {
                actionLogin.setEnabled(!x.getText().equals(""));
            }
        });
    }
}
