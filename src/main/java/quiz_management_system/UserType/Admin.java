package quiz_management_system.UserType;

import quiz_management_system.DataHandler;
import quiz_management_system.Quiz_Management_System;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serial;
import java.io.Serializable;
import java.util.Scanner;

import static java.lang.System.in;
import static java.lang.System.out;

/**
 * @author belsa
 */
public class Admin extends User implements Serializable
{
    @Serial
    private static final long serialVersionUID = 1L;

    public Admin(String username, String password)
    {
        super(username, password, 3);
    }

    @Override
    public void listMenu()
    {
        AdminWindow.constructWindow();
    }

    /**
     * @deprecated
     */
    public int listMenuConsole()
    {
        Scanner sc = new Scanner(in);
        out.println("*****Logged in as " + Quiz_Management_System.getActiveUser().getUsername() + "*****");
        out.println("1. View all users.\n2. Create new User.\n3. Edit user.\n4. Remove user.\nEnter -1 to quit\n");
        short n;
        n = sc.nextShort();
        switch (n)
        {
            case -1:
                return -1;
            case 1:
            {
                listUsers();
            }
            case 2:
            {
                createUserConsole();
            }
            case 3:
            {

            }
        }
        return 1;
    }

    public void listUsers()
    {
        for (User i : DataHandler.userData)
            out.println(i.toString());
    }


    /**
     * @deprecated not used with GUI
     */
    public void createUserConsole()
    {
        Scanner sc = new Scanner(System.in);
        String inUsername, inPassword = null;
        int aLevel;
        User newUser;
        System.out.println("-----------Creating new user------------");


        boolean status = false;
        do
        {
            System.out.println("Enter username:");
            inUsername = sc.next();

            if (User.searchByUsername(inUsername, Access.NONE) != null)
            {
                System.err.println("Username already exists.");
                continue;
            }
            System.out.println("Enter password:");
            inPassword = sc.next();
            status = true;
        } while (!status);


        System.out.println("Set Access Level.\n 1 for student\n2 for teacher\n3 for Admin:");
        aLevel = sc.nextInt();
        // TODO Set an ID for the user
        if(aLevel == 1)
        {
            newUser = new Student(inUsername, inPassword, Access.STUDENT);
            DataHandler.userData.add(newUser);
        }
        else if (aLevel == 2)
        {
            newUser = new Teacher(inUsername, inPassword, Access.TEACHER);
            newUser.setUserID(2 * 1000 + DataHandler.userData.size() + 1);
            DataHandler.userData.add(newUser);
        }
        else if (aLevel == 3)
        {
            newUser = new Teacher(inUsername, inPassword, Access.ADMIN);
            newUser.setUserID(2 * 1000 + DataHandler.userData.size() + 1);
            DataHandler.userData.add(newUser);
        }
    }

    static class AdminWindow extends JFrame implements ActionListener
    {
        String[] columnNames = {"First Name",
                "Last Name",
                "ID",
                "Type"};
        Object[][] data = {
                {"Kathy", "Smith", "2100", "Teacher"},
                {"John", "Doe", "2500", "Teacher"},
                {"Sue", "Black", "1003", "Student"},
                {"Jane", "White", "1220", "Student"},
                {"Joe", "Brown", "2004", "Teacher"}
        };
        static JFrame window;
        static JPanel Background_panel = new JPanel();
        static JPanel Title_panel = new JPanel();
        static JPanel Table_panel = new JPanel();
        static JButton update_button = new JButton();
        static JButton Save_button = new JButton();
        static JButton Delete_button = new JButton();
        static JLabel Title = new JLabel("Admin");
        static Border brdr = BorderFactory.createLineBorder(new Color(222, 184, 150));
        static Font myFont = new Font(Font.MONOSPACED, Font.BOLD, 30);
        static JScrollPane scrollpane;

        JTable table = new JTable(data, columnNames);

        public AdminWindow()
        {

            Title.setFont(myFont);
            Title_panel.add(Title);
            add(Title_panel, BorderLayout.PAGE_START);
            Title.setForeground(Color.BLACK);
            Title_panel.setBackground(Color.white);
            Title_panel.setBorder(brdr);


            update_button.setBounds(400, 100, 100, 50);
            update_button.addActionListener(this);
            update_button.setText("Update");
            update_button.setFocusable(false);
            update_button.setFont(new Font("Comic Sans", Font.BOLD, 16));
            update_button.setBackground(new Color(222, 184, 150));
            add(update_button);

            Save_button.setBounds(400, 200, 100, 50);
            Save_button.addActionListener(this);
            Save_button.setText("Add");
            Save_button.setFocusable(false);
            Save_button.setFont(new Font("Comic Sans", Font.BOLD, 16));
            Save_button.setBackground(new Color(222, 184, 150));
            add(Save_button);

            Delete_button.setBounds(400, 300, 100, 50);
            Delete_button.addActionListener(this);
            Delete_button.setText("Delete");
            Delete_button.setFocusable(false);
            Delete_button.setFont(new Font("Comic Sans", Font.BOLD, 16));
            Delete_button.setBackground(new Color(222, 184, 150));
            add(Delete_button);

            scrollpane = new JScrollPane(table);
            table.setFillsViewportHeight(true);
            JTable table = new JTable(data, columnNames);
            table.setBounds(0, 50, 350, 100);
            table.setBackground(new Color(239, 222, 205));
            add(table);

//        JScrollPane scrollPane = new JScrollPane(table);
//        table.setFillsViewportHeight(true);
//        add(scrollPane);

            Background_panel.setBackground(Color.WHITE);
            Background_panel.setBounds(0, 0, 550, 550);
            add(Background_panel);
        }

        public static void constructWindow()
        {
            window = new AdminWindow();
            window.setTitle("Logged in as " + Quiz_Management_System.getActiveUser().getUsername());
            window.setSize(550, 550);
            window.setLocationRelativeTo(null); // to not have it open at the corner
            window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            window.setVisible(true);
        }

        @Override
        public void actionPerformed(ActionEvent e)
        {
            if (e.getSource() == update_button)
            {
                System.out.println("Data is updated");
            }
            if (e.getSource() == Save_button)
            {
                System.out.println("Data is saved");
            }
            if (e.getSource() == Delete_button)
            {
                System.out.println("Data is deleted");
            }
        }
    }
}
