package quiz_management_system.GUI;

import quiz_management_system.UserType.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginWindow
{
    static JLabel password = new JLabel("Password"), username = new JLabel("Username");
    static JTextField username_text = new JTextField(21);
    static JButton actionLogin = new JButton("Log in");
    static JPasswordField password_text = new JPasswordField(30);

    static JFrame window;

    public LoginWindow()
    {
        window = new LoginMenuConstructor();
        window.setTitle("Quiz Management System Login");
        window.setSize(640, 480);
        window.setLocationRelativeTo(null); // to not have it open at the corner
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setVisible(true);
    }
}

class LoginMenuConstructor extends JFrame
{
    public LoginMenuConstructor()
    {
        setLayout(new GridLayout(2, 2, 10, 10));

        add(LoginWindow.username);
        add(LoginWindow.username_text);
        add(LoginWindow.password);
        add(LoginWindow.password_text);
        add(LoginWindow.actionLogin);

        ActionLogin actionLogin = new ActionLogin();
        LoginWindow.actionLogin.addActionListener(actionLogin);
    }
}

class ActionLogin implements ActionListener
{
    @Override
    public void actionPerformed(ActionEvent e)
    {
        String Username = LoginWindow.username_text.getText();
        String Password1 = String.valueOf(LoginWindow.password_text.getPassword());

        User newUser = User.login(Username, Password1);
        if (newUser == null)
        {
            JOptionPane.showMessageDialog(null, "Username or Password mismatch");
            return;
        }
        JOptionPane.showMessageDialog(null, "Login Successful");
    }
}
