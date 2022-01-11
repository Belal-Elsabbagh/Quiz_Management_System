package quiz_management_system;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
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
        new AdminWindow();
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
                Delete_button = new JButton("Delete"),
                Back_button = new JButton("Log out");
        JLabel Title = new JLabel("Admin"),
                name_label = new JLabel("Enter User Name"),
                pass_label = new JLabel("Enter Password");
        Border brdr = BorderFactory.createLineBorder(new Color(222, 184, 150));
        Font myFont = new Font(Font.MONOSPACED, Font.BOLD, 30);
        JScrollPane scrollPane;
        JTable table;
        JTextField name_Text = new JTextField(),
                password_Text = new JTextField();
        Access[] accessString = {Access.STUDENT, Access.TEACHER, Access.ADMIN};
        JComboBox<Access> accessCombo = new JComboBox<>(accessString);

        public AdminWindow()
        {
            Title.setFont(myFont);
            Title_panel.add(Title);
            Title.setForeground(Color.BLACK);
            Title_panel.setBackground(Color.white);
            Title_panel.setBorder(brdr);
            add(Title_panel, BorderLayout.PAGE_START);

            update_button.setBounds(350, 420, 180, 30);
            update_button.addActionListener(this);
            update_button.setFocusable(false);
            update_button.setFont(new Font("Comic Sans", Font.BOLD, 16));
            update_button.setBackground(new Color(222, 184, 150));
            update_button.addActionListener(this);
            add(update_button);

            Add_button.setBounds(350, 250, 180, 30);
            Add_button.addActionListener(this);
            Add_button.setFocusable(false);
            Add_button.setFont(new Font("Comic Sans", Font.BOLD, 16));
            Add_button.setBackground(new Color(222, 184, 150));
            Add_button.addActionListener(this);
            add(Add_button);

            Delete_button.setBounds(350, 290, 180, 30);
            Delete_button.addActionListener(this);
            Delete_button.setFocusable(false);
            Delete_button.setFont(new Font("Comic Sans", Font.BOLD, 16));
            Delete_button.setBackground(new Color(222, 184, 150));
            add(Delete_button);

            Back_button.setBounds(430, 470, 100, 30);
            Back_button.setBackground(new Color(222, 184, 150));
            Back_button.addActionListener(this);
            add(Back_button);

            name_Text.setBounds(350, 90, 180, 30);
            add(name_Text);

            password_Text.setBounds(350, 150, 180, 30);
            add(password_Text);

            name_label.setBounds(400, 60, 180, 30);
            add(name_label);

            pass_label.setBounds(400, 120, 180, 30);
            add(pass_label);

            accessCombo.setBounds(350, 200, 180, 30);
            accessCombo.setBackground(new Color(239, 222, 205));
            add(accessCombo);

            constructData();
            table.setFillsViewportHeight(true);
            table.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
            table.setBounds(0, 50, 350, 400);

            table.setBackground(new Color(239, 222, 205));
            scrollPane = new JScrollPane(table);
            scrollPane.setBounds(0, 50, 350, 400);
            add(scrollPane);

            Background_panel.setBackground(Color.WHITE);
            Background_panel.setBounds(0, 0, 550, 550);
            add(Background_panel);

            setTitle("Logged in as " + Quiz_Management_System.getActiveUser().getUsername());
            setSize(550, 550);
            setLocationRelativeTo(null); // to not have it open at the corner
            setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            setVisible(true);
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
            if (e.getSource() == Back_button)
            {
                int reply = JOptionPane.showConfirmDialog(null, "Are you sure you want to close?", "Close?", JOptionPane.YES_NO_OPTION);
                if (reply == JOptionPane.YES_OPTION)
                {
                    setVisible(false);
                    JFrame window = new LoginWindow();
                }

            }
            if (e.getSource() == update_button)
            {
                new UpdateWindow();
            }
            if (e.getSource() == Add_button)
            {
                Access newAccess = (Access) accessCombo.getSelectedItem();
                if (newAccess == Access.ADMIN)
                    DataHandler.userData.add(new Admin(name_Text.getText(), password_Text.getText(), newAccess));
                else if (newAccess == Access.TEACHER)
                    DataHandler.userData.add(new Teacher(name_Text.getText(), password_Text.getText(), newAccess));
                else if (newAccess == Access.STUDENT)
                    DataHandler.userData.add(new Student(name_Text.getText(), password_Text.getText(), newAccess));

                DataHandler.save();
                constructData();
            }
            if (e.getSource() == Delete_button)
            {
                int reply = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this user?", "Close?", JOptionPane.YES_NO_OPTION);
                if (reply != JOptionPane.YES_OPTION)
                    return;
                DataHandler.userData.remove(table.getSelectedRow());
                DataHandler.save();
            }
        }

        /**
         * @author marma
         */
        public class UpdateWindow extends JFrame implements ActionListener
        {
            JPanel title_panel, background;
            Font myFont;
            JLabel Title, label1, label2;
            JButton Back_button, save;
            JTextField username_text,
                    password_text;
            Border brdr;
            Access[] accessString = {Access.STUDENT, Access.TEACHER, Access.ADMIN};
            JComboBox<Access> accessCombo = new JComboBox(accessString);

            {
                title_panel = new JPanel();
                brdr = BorderFactory.createLineBorder(new Color(222, 184, 150));
                background = new JPanel();
                myFont = new Font(Font.MONOSPACED, Font.BOLD, 30);
                Title = new JLabel("EDIT");
                username_text = new JTextField(DataHandler.userData.get(table.getSelectedRow()).getUsername());
                password_text = new JTextField(DataHandler.userData.get(table.getSelectedRow()).getPassword());
                accessCombo.setSelectedItem(DataHandler.userData.get(table.getSelectedRow()).getAccessLevel());
                Back_button = new JButton("Cancel");
                save = new JButton("Save");
            }

            public UpdateWindow()
            {
                //button
                Back_button.setBounds(380, 280, 90, 30);
                Back_button.setBackground(new Color(222, 184, 150));
                Back_button.addActionListener(this);
                add(Back_button);

                save.setBounds(270, 280, 90, 30);
                save.setBackground(new Color(222, 184, 150));
                save.addActionListener(this);
                add(save);
                //Title
                Title.setFont(myFont);
                title_panel.add(Title);
                add(title_panel, BorderLayout.PAGE_START);
                Title.setForeground(Color.BLACK);
                title_panel.setBackground(Color.white);
                title_panel.setBorder(brdr);
                //submit button

                //labels
                label1 = new JLabel("New User Name: ");
                label1.setBounds(70, 100, 150, 30);
                label2 = new JLabel("New Password: ");
                label2.setBounds(70, 140, 100, 30);

                add(label1);
                add(label2);

                username_text.getDocument().addDocumentListener(new DocumentListener()
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
                        Add_button.setEnabled(!username_text.getText().equals(""));
                    }
                });
                if (username_text.getText().isEmpty())
                {
                    Add_button.setEnabled(false);
                }

                //text fields
                username_text.setBounds(270, 100, 200, 30);
                password_text.setBounds(270, 140, 200, 30);

                add(username_text);
                add(password_text);

                accessCombo.setBounds(270, 180, 200, 30);
                accessCombo.setBackground(new Color(239, 222, 205));
                add(accessCombo);
                //Background
                background.setBackground(Color.WHITE);
                add(background, BorderLayout.CENTER);

                setTitle("Edit");
                setSize(550, 550);
                setLocationRelativeTo(null); // to not have it open at the corner
                setResizable(false);
                setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                setVisible(true);
            }

            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (e.getSource() == save)
                {

                }
                if (e.getSource() == Back_button)
                {
                    int reply = JOptionPane.showConfirmDialog(null, "Are you sure you want to cancel?", "Close?", JOptionPane.YES_NO_OPTION);
                    if (reply == JOptionPane.YES_OPTION)
                    {
                        setVisible(false);
                    }
                }
            }
        }
    }
}
