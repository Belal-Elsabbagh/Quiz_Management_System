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

    public User()
    {
    }

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

    public static void updateUser()
    {
        DataHandler.userData.set(DataHandler.userData.indexOf(Quiz_Management_System.getLastReference()), Quiz_Management_System.getActiveUser());
    }

    public static User searchByID(int inID)
    {
        for (User i : DataHandler.userData)
        {
            if (i.getUserID() == inID)
                return i;
        }
        return null;
    }

    @Override
    public void listMenu()
    {
        new LoginWindow();
    }

    enum Access
    {
        NONE, STUDENT, TEACHER, ADMIN
    }

    class LoginWindow extends JFrame implements ActionListener
    {
        JPanel Back, up;
        JLabel password_label, username_label, logo;
        JTextField username_text;
        JPasswordField password_text;
        JButton actionLogin;

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

            actionLogin.addActionListener(this);

            //Login Button activation
            DL(username_text);
            if (username_text.getText().isEmpty())
            {
                actionLogin.setEnabled(false);
            }

            setTitle("Quiz Management System Login");
            setSize(550, 550);
            setLocationRelativeTo(null); // to not have it open at the corner
            setResizable(false);
            setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            setVisible(true);
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
            Quiz_Management_System.setLastReference(newUser);
            setVisible(false);
            Quiz_Management_System.getActiveUser().listMenu();
        }
    }
}