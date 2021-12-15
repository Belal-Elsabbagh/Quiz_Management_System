package quiz_management_system.GUI;

import quiz_management_system.UserType.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginWindow extends JFrame implements Windows
{
    static JLabel password = new JLabel("Password"), username = new JLabel("Username");
    static JTextField username_text = new JTextField(21);
    static JButton actionLogin = new JButton("Log in");
    static JPasswordField password_text = new JPasswordField(30);

    static JFrame window;

    public LoginWindow()
    {
        setLayout(new GridLayout(2, 2, 10, 10));

        add(username);
        add(username_text);
        add(password);
        add(password_text);
        add(actionLogin);

        ActionLogin actionlogin = new ActionLogin();
        actionLogin.addActionListener(actionlogin);
    }


    @Override
    public void constructWindow()
    {
        window = new LoginWindow();
        window.setTitle("Quiz Management System Login");
        window.setSize(640, 480);
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
