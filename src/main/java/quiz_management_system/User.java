package quiz_management_system;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serial;
import java.io.Serializable;

public class User implements Serializable, Interactive
{
    @Serial
    private static final long serialVersionUID = 1L;

    private int userID;
    private String username, password;
    private Access accessLevel;

    public User(String username, String password)
    {
        this.username = username;
        this.password = password;
        this.accessLevel = Access.NONE;
    }

    public User(String username, String password, Access accessLevel)
    {
        this.username = username;
        this.password = password;
        this.accessLevel = accessLevel;
    }

    public static User login(String inUsername, String inPassword)
    {
        User newUser = searchByUsername(inUsername);

        if (newUser == null || !newUser.getPassword().equals(inPassword))
            return null;

        return newUser;
    }

    public static User searchByUsername(String inUsername)
    {
        for (User i : DataHandler.userData)
        {
            if (i.getUsername().equals(inUsername))
                return i;
        }
        return null;
    }

    public Access getAccessLevel()
    {
        return accessLevel;
    }

    public String getUsername()
    {
        return username;
    }

    public int getUserID()
    {
        return userID;
    }

    public void setUserID(int userID)
    {
        this.userID = userID;
    }

    public String getPassword()
    {
        return password;
    }

    @Override
    public void listMenu()
    {
        LoginWindow.constructWindow();
    }

    public enum Access
    {
        NONE, STUDENT, TEACHER, ADMIN
    }

    static class LoginWindow extends JFrame
    {
        static JFrame window;
        static JPanel Back, up;
        static JLabel password_label, username_label, logo;
        static JTextField username_text;
        static JPasswordField password_text;
        static JButton actionLogin;

        static
        {
            Back = new JPanel();
            up = new JPanel();
            username_label = new JLabel("Username: ");
            password_label = new JLabel("Password: ");
            username_text = new JTextField(20);
            password_text = new JPasswordField(20);
            actionLogin = new JButton("Log in");
            logo = new JLabel(new ImageIcon("Q1.PNG"));
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
            up.add(logo);
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
}