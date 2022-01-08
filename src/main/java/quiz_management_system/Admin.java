package quiz_management_system;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
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

    public Admin(String username, String password, Access admin)
    {
        super(username, password, admin);
    }

    @Override
    public void listMenu()
    {
        JFrame window = new AdminWindow();
        window.setTitle("Logged in as " + Quiz_Management_System.getActiveUser().getUsername());
        window.setSize(550, 550);
        window.setLocationRelativeTo(null); // to not have it open at the corner
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setVisible(true);
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
                listUsersConsole();
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

    /**
     * @deprecated Not used with GUI
     */
    public void listUsersConsole()
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
        User newUser = null;
        System.out.println("-----------Creating new user------------");

        boolean status = false;
        do
        {
            System.out.println("Enter username:");
            inUsername = sc.next();

            if (User.searchByUsername(inUsername) != null)
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
        if (aLevel == 1)
            newUser = new Student(inUsername, inPassword, Access.STUDENT);
        else if (aLevel == 2)
            newUser = new Teacher(inUsername, inPassword, Access.TEACHER);
        else if (aLevel == 3)
            newUser = new Admin(inUsername, inPassword, Access.ADMIN);

        if (newUser != null)
        {
            newUser.setUserID(newUser.hashCode());
            DataHandler.userData.add(newUser);
        }
    }

    /**
     * @author Lojain45Mostafa
     */
    class AdminWindow extends JFrame implements ActionListener
    {
        JPanel Background_panel = new JPanel(),
                Title_panel = new JPanel();
        JButton update_button = new JButton("Update"),
                Add_button = new JButton("Add"),
                Delete_button = new JButton("Delete");
        JLabel Title = new JLabel("Admin");
        Border brdr = BorderFactory.createLineBorder(new Color(222, 184, 150));
        Font myFont = new Font(Font.MONOSPACED, Font.BOLD, 30);
        JScrollPane scrollPane;
        JTable table;

        public AdminWindow()
        {
            Title.setFont(myFont);
            Title_panel.add(Title);
            Title.setForeground(Color.BLACK);
            Title_panel.setBackground(Color.white);
            Title_panel.setBorder(brdr);
            add(Title_panel, BorderLayout.PAGE_START);

            update_button.setBounds(400, 100, 100, 50);
            update_button.addActionListener(this);
            update_button.setFocusable(false);
            update_button.setFont(new Font("Comic Sans", Font.BOLD, 16));
            update_button.setBackground(new Color(222, 184, 150));
            add(update_button);

            Add_button.setBounds(400, 200, 100, 50);
            Add_button.addActionListener(this);
            Add_button.setFocusable(false);
            Add_button.setFont(new Font("Comic Sans", Font.BOLD, 16));
            Add_button.setBackground(new Color(222, 184, 150));
            add(Add_button);

            Delete_button.setBounds(400, 300, 100, 50);
            Delete_button.addActionListener(this);
            Delete_button.setFocusable(false);
            Delete_button.setFont(new Font("Comic Sans", Font.BOLD, 16));
            Delete_button.setBackground(new Color(222, 184, 150));
            add(Delete_button);

            constructData();
            table.setFillsViewportHeight(true);
            table.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
            table.setBounds(0, 50, 350, 100);

            table.setBackground(new Color(239, 222, 205));
            scrollPane = new JScrollPane(table);
            scrollPane.setBounds(0, 50, 350, 100);
            add(scrollPane);

            Background_panel.setBackground(Color.WHITE);
            Background_panel.setBounds(0, 0, 550, 550);
            add(Background_panel);
        }

        private void constructData()
        {
            DefaultTableModel data = new DefaultTableModel();
            data.setColumnIdentifiers(new String[]{"User ID", "Username", "Position"});
            for (User i : DataHandler.userData)
            {
                Object[] row = new Object[]{i.getUserID(), i.getUsername(), i.getAccessLevel()};
                data.addRow(row);
            }
            table = new JTable(data);
        }

        @Override
        public void actionPerformed(ActionEvent e)
        {
            if (e.getSource() == update_button)
            {
                System.out.println("Data is updated");
                return;
            }
            if (e.getSource() == Add_button)
            {
                System.out.println("Data is saved");
                return;
            }
            if (e.getSource() == Delete_button)
            {
                System.out.println("Data is deleted");
            }
        }
    }
}
