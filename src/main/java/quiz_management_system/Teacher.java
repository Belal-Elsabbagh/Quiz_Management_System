package quiz_management_system;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serial;
import java.util.ArrayList;
import java.util.Scanner;

public class Teacher extends User
{
    @Serial
    private static final long serialVersionUID = 1L;

    private ArrayList<Quiz> createdQuizzes;

    public Teacher(String username, String password, Access teacher)
    {
        super(username, password, teacher);
        createdQuizzes = new ArrayList<>();
    }

    public ArrayList<Quiz> getCreatedQuizzes()
    {
        return createdQuizzes;
    }

    public void setCreatedQuizzes(ArrayList<Quiz> createdQuizzes)
    {
        this.createdQuizzes = createdQuizzes;
    }

    @Override
    public void listMenu()
    {
        new TeacherWindow();
    }

    /**
     * @deprecated
     */
    public int listMenuConsole()
    {
        System.out.println("----------Teacher Operations Main Menu-----------");
        System.out.println("1. List my quizzes.");
        System.out.println("2. Create new quiz.");
        System.out.println("3. review Quiz Grades");
        Scanner sc = new Scanner(System.in);
        short n;
        n = sc.nextShort();
        switch (n)
        {
            case -1:
                return 0;
            case 1:
            {
                listQuizzesConsole();
                break;
            }
            case 2:
            {
                createNewQuiz();
                break;
            }
            case 3:
            {
                reviewStudentGradesConsole();
                break;
            }
        }
        return 1;
    }

    /**
     * @deprecated
     */
    public void listQuizzesConsole()
    {
        for (Quiz i : createdQuizzes)
        {
            i.displayQuizPropertiesConsole();
        }
    }

    public void createNewQuiz()
    {
        Quiz newQuiz = new Quiz(this);
    }

    /**
     * @deprecated not used with GUI
     */
    public void reviewStudentGradesConsole()
    {
        Scanner sc = new Scanner(System.in);

        System.out.println("please enter quiz ID : ");
        while (!sc.hasNextInt())
        {
            System.err.println("INVALID INPUT.");
            sc.next();
        }
        String inID = sc.next();

        Quiz newQuiz = Quiz.searchByID(inID);
        if (newQuiz == null)
        {
            System.err.println("No Quiz Found.");
        }
    }

    /**
     * @author marma
     */
    class TeacherWindow extends JFrame implements ActionListener
    {
        // Title
        JPanel Title = new JPanel();
        Border brdr = BorderFactory.createLineBorder(new Color(222, 184, 150));
        Font myFont = new Font(Font.MONOSPACED, Font.BOLD, 30);
        JLabel Title_label = new JLabel("Welcome " + Quiz_Management_System.getActiveUser().getUsername()); // + username
        // List
        JPanel listArea = new JPanel();
        JLabel lb = new JLabel("Created Quizzes: ");
        // Buttons
        JButton actionCreate = new JButton("Create quiz");
        JButton actionReview = new JButton("Review quiz grades");
        JButton Back_button = new JButton("Log out");
        //background
        JPanel Back = new JPanel();
        //chat
        JPanel chat_panel = new JPanel();
        JButton actionCloseChat = new JButton("x");
        JButton actionOpenChat = new JButton(new ImageIcon("t1.jpeg"));

        JList<String> quizList;
        JScrollPane scrollPane;
        JTextField qID_review = new JTextField(15);

        public TeacherWindow()
        {
            JButton b = new JButton("send");
            b.setBounds(153, 480, 80, 30);
            b.setBackground(new Color(222, 184, 150));
            chat_panel.setBackground(new Color(239, 222, 205));
            JTextField t1 = new JTextField();
            chat_panel.setLayout(null);
            t1.setBounds(1, 480, 150, 30);
            chat_panel.setBounds(300, 1, 250, 550);
            JTextArea l1 = new JTextArea(100, 50);
            l1.setLineWrap(true);
            l1.setEditable(false);
            l1.setBounds(5, 35, 220, 440);
            actionCloseChat.setBounds(1, 1, 60, 30);
            actionCloseChat.setBackground(new Color(239, 222, 205));
            actionCloseChat.addActionListener(this);
            chat_panel.add(actionCloseChat);
            chat_panel.add(l1);
            chat_panel.add(t1);
            chat_panel.add(b);
            add(chat_panel);
            chat_panel.setVisible(false);
            actionOpenChat.setBounds(3, 1, 53, 53);
            actionOpenChat.addActionListener(this);
            add(actionOpenChat);

            Title.add(Title_label);
            add(Title, BorderLayout.PAGE_START);
            Title_label.setFont(myFont);
            Title_label.setForeground(Color.BLACK);
            Title.setBackground(Color.WHITE);
            Title.setBorder(brdr);

            lb.setBounds(5, 70, 100, 30);

            constructData();
            quizList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
            quizList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
            listArea.add(quizList);
            quizList.setPreferredSize(new Dimension(250, 400));

            quizList.setBackground(new Color(239, 222, 205));
            scrollPane = new JScrollPane(quizList);
            scrollPane.setBounds(0, 50, 250, 400);
            add(scrollPane);
            listArea.setBackground(new Color(239, 222, 205));
            add(listArea);
            add(lb);

            //buttons
            actionCreate.setBounds(300, 100, 200, 30);
            actionCreate.setBackground(new Color(222, 184, 150));
            actionCreate.addActionListener(this);
            add(actionCreate);
            qID_review.setBounds(300, 150, 200, 30);
            add(qID_review);
            actionReview.setBounds(300, 200, 200, 30);
            actionReview.setBackground(new Color(222, 184, 150));
            actionReview.addActionListener(this);
            add(actionReview);

            Back_button.setBounds(430, 470, 100, 30);
            Back_button.setBackground(new Color(222, 184, 150));
            Back_button.addActionListener(this);
            add(Back_button);

            Back.setBackground(Color.WHITE);
            add(Back, BorderLayout.CENTER);

            setTitle("Logged in as " + Quiz_Management_System.getActiveUser().getUsername());
            setSize(550, 550);
            setResizable(false);
            setLocationRelativeTo(null); // to not have it open at the corner
            setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            setVisible(true);
        }

        @Override
        public void actionPerformed(ActionEvent e)
        {
            if (e.getSource() == actionReview)
            {
                try
                {
                    new ReviewQuizGrades(Quiz.searchByID(qID_review.getText()));
                } catch (NullPointerException nullPointerException)
                {
                    JOptionPane.showMessageDialog(null, "No Quiz Found!", "Warning", JOptionPane.WARNING_MESSAGE);
                    return;
                }
            }
            if (e.getSource() == actionCreate)
            {
                new Quiz();
            }
            if (e.getSource() == actionOpenChat)
            {
                chat_panel.setVisible(true);
            }
            if (e.getSource() == actionCloseChat)
            {
                chat_panel.setVisible(false);
            }
            if (e.getSource() == Back_button)
            {
                int reply = JOptionPane.showConfirmDialog(null, "Are you sure you want to close?", "Close?", JOptionPane.YES_NO_OPTION);
                if (reply == JOptionPane.YES_OPTION)
                {
                    setVisible(false);
                    new LoginWindow();
                }
            }
        }

        private void constructData()
        {
            DefaultListModel<String> data = new DefaultListModel<>();
            createdQuizzes.forEach((i) -> data.addElement(i.getQuizTitle()));
            quizList = new JList<>(data);
        }
    }

    /**
     * @author Habiba1234567
     */
    class ReviewQuizGrades extends JFrame implements ActionListener
    {
        int max = 100,
                min = 0,
                avg = 50,
                h1 = min * 2,
                h2 = avg * 2,
                h3 = max * 2;
/*
        int [] arr = { 35 , 90 , 78};
        public void sort() {
            int temp;
            for (int i = 0; i < arr.length; i++)
            {
                for (int j = i + 1; j < arr.length; j++)
                {
                    if (arr[i] > arr[j])
                    {
                        temp = arr[i];
                        arr[i] = arr[j];
                        arr[j] = temp;
                    }
                }
            }
            min = arr[0];
        }
        public void Average() {
            double sum = 0;
            int i=0;
            while(i < arr.length) {
                sum += arr[i];
                i++;
            }
            avg = (sum / arr.length);

        }
 */

        ArrayList<Student> students = new ArrayList<>();

        JPanel title_panel = new JPanel(),
                table_panel = new JPanel(),
                background = new JPanel();
        Font myFont = new Font(Font.MONOSPACED, Font.BOLD, 30);
        JLabel Title = new JLabel("Review Quiz Grades "),
                BestGrade_label = new JLabel("Best Grade"),
                WorstGrade_label = new JLabel("Worst Grade"),
                AverageGrade_label = new JLabel("Average Grade"),
                zero = new JLabel("0"),
                fifty =  new JLabel("50"),
                hundred = new JLabel("100"),
                g1 = new JLabel("min: " + min),
                g2 =  new JLabel("Avg:" + avg),
                g3 = new JLabel("Max: " + max),
                label4 = new JLabel(" "),
                label5 = new JLabel(" "),
                label6 = new JLabel(" ");
        Border brdr = BorderFactory.createLineBorder(new Color(222, 184, 150));
        String[] columnNames = {"Student", "Grade"};
        Object[][] data = {{"Kathy", 5}, {"John", 4}};
        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        JButton Back_button = new JButton("Back");

        public ReviewQuizGrades(Quiz newQuiz)
        {
            //button
            Back_button.setBounds(430, 475, 100, 30);
            Back_button.setBackground(new Color(222, 184, 150));
            Back_button.addActionListener(this);
            add(Back_button);
            //Title
            Title.setFont(myFont);
            title_panel.add(Title);
            add(title_panel, BorderLayout.PAGE_START);
            Title.setForeground(Color.BLACK);
            title_panel.setBackground(Color.white);
            title_panel.setBorder(brdr);

            //labels
            Title.setBounds(10, 100, 60, 30);
            BestGrade_label.setBounds(340, 480, 200, 30);
            WorstGrade_label.setBounds(140, 480, 100, 30);
            AverageGrade_label.setBounds(235, 480, 200, 30);
            label5.setBounds(10, 260, 60, 30);
            label6.setBounds(10, 300, 60, 30);
            zero.setBounds(80, 450, 60, 30);
            fifty.setBounds(70, 350, 60, 30);
            hundred.setBounds(65, 260, 60, 30);
            g1.setBounds(140, 440 - h1 , 60, 30);
            g2.setBounds(240, 440 - h2, 60, 30);
            g3.setBounds(340, 440 - h3, 60, 30);
            add(BestGrade_label);
            add(WorstGrade_label);
            add(AverageGrade_label);
            add(label4);
            add(label5);
            add(label6);
            add(zero);
            add(fifty);
            add(hundred);
            add(g1);
            add(g2);
            add(g3);
            //Table
            constructTable(newQuiz);
            table_panel.setBounds(0, 60, 550, 180);
            table_panel.setBackground(Color.WHITE);
            table_panel.setLayout(null);
            table.setBounds(0, 0, 550, 180);
            table.setBackground(new Color(222, 184, 150));
            table_panel.add(table);
            table.add(scrollPane);
            add(table_panel);
            table.setFillsViewportHeight(true);

            //Background
            background.setBackground(Color.WHITE);
            add(background, BorderLayout.CENTER);



            setTitle("Review Quiz Grades");
            setSize(550, 550);
            setResizable(false);
            setLocationRelativeTo(null); // to not have it open at the corner
            setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            setVisible(true);
        }

        @Override
        public void paint(Graphics g) {
            super.paintComponents(g);

            g.setColor(Color.red);
            g.fillRect(150, 500 -h1, 50,  0 + h1);

            g.setColor(Color.YELLOW);
            g.fillRect(250, 500 - h2 , 50,  0 + h2);

            g.setColor(Color.green);
            g.fillRect(350, 500 - h3, 50, 0 + h3);

            g.setColor(Color.BLACK);
            g.fillRect(100, 500, 350, 2);
            g.setColor(Color.BLACK);
            g.fillRect(100, 300, 2, 200);
        }

        private void getStudents(Quiz newQuiz)
        {
            for (User i : DataHandler.userData)
            {
                if (!(i instanceof Student))
                    continue;

                for (Student.Attempt j : ((Student) i).getAttemptHistory())
                {
                    if (j.getQuiz().equals(newQuiz))
                    {
                        students.add((Student) i);
                    }
                }
            }
        }

        public void constructTable(Quiz newQuiz)
        {
            DefaultTableModel data = new DefaultTableModel(0, 3);
            data.setColumnIdentifiers(new String[]{"Student ID", "Student Name", "Grade"});
            for (User i : DataHandler.userData)
            {
                if (!(i instanceof Student))
                    continue;

                for (Student.Attempt j : ((Student) i).getAttemptHistory())
                {
                    if (j.getQuiz().equals(newQuiz))
                    {
                        Object[] row = new Object[]{i.getUserID(), i.getUsername(), j.getResult()};
                        data.addRow(row);
                    }
                }
            }
        }

        @Override
        public void actionPerformed(ActionEvent e)
        {
            if (e.getSource() == Back_button)
            {
                setVisible(false);
                Quiz_Management_System.getActiveUser().listMenu();
            }
        }
    }
}
