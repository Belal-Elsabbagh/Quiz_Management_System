package quiz_management_system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serial;
import java.io.Serializable;
import java.text.ParseException;

public class User implements Serializable, Interactive
{
    @Serial
    private static final long serialVersionUID = 1L;

    private int userID;
    private int accessLevel;
    private String username, password;

    public User(String username, String password)
    {
        this.username = username;
        this.password = password;
        this.accessLevel = -1;
    }

    public User(String username, String password, int accessLevel)
    {
        this.username = username;
        this.password = password;
        this.accessLevel = accessLevel;
    }

    public void setUserID(int userID)
    {
        this.userID = userID;
    }

    public static User login(String inUsername, String inPassword)
    {
        User newUser = new User(inUsername, inPassword);
        User searchResult = DataHandler.hasUser(newUser);

        return searchResult;
    }

    public String getUsername()
    {
        return username;
    }

    public int getUserID()
    {
        return userID;
    }

    public String getPassword()
    {
        return password;
    }

    @Override
    public String toString()
    {
        return "userID: " + userID + "\tusername: " + username + "\tpassword: " + password + "\naccessLevel: " + accessLevel;
    }

    @Override
    public int listMenu() throws ParseException
    {
        new LoginWindow();
        return 0;
    }

    enum Access
    {
        NONE, STUDENT, TEACHER, ADMIN
    }

    private class LoginWindow implements ActionListener
    {
        private static JLabel password1, label;
        private static JTextField username;
        private static JButton button;
        private static JPasswordField Password;

        public LoginWindow()
        {
            JPanel panel = new JPanel();
            panel.setLayout(null);

            JFrame frame = new JFrame();
            frame.setTitle("LOGIN PAGE");
            frame.setLocationRelativeTo(null);
            frame.add(panel);
            frame.setSize(400, 200);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            label = new JLabel("Username");
            label.setBounds(100, 8, 70, 20);
            panel.add(label);

            username = new JTextField();
            username.setBounds(100, 27, 193, 28);
            panel.add(username);

            password1 = new JLabel("Password");
            password1.setBounds(100, 55, 70, 20);
            panel.add(password1);

            Password = new JPasswordField();
            Password.setBounds(100, 75, 193, 28);
            panel.add(Password);

            button = new JButton("Login");
            button.setBounds(100, 110, 90, 25);
            button.setForeground(Color.WHITE);
            button.setBackground(Color.BLACK);
            button.addActionListener((ActionListener) new LoginWindow());
            panel.add(button);
        }

        @Override
        public void actionPerformed(ActionEvent e)
        {
            String Username = username.getText();
            String Password1 = String.valueOf(Password.getPassword());

            User newUser = login(Username, Password1);
            if (newUser == null)
                JOptionPane.showMessageDialog(null, "Username or Password mismatch");
            JOptionPane.showMessageDialog(null, "Login Successful");
        }
    }
}
