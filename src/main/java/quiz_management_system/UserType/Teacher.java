package quiz_management_system.UserType;

import quiz_management_system.DataHandler;
import quiz_management_system.Quiz;
import quiz_management_system.Quiz_Management_System;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.io.Serial;
import java.util.ArrayList;
import java.util.Scanner;

public class Teacher extends User
{
    @Serial
    private static final long serialVersionUID = 1L;

    ArrayList<Quiz> createdQuizzes;

    public Teacher(String username, String password, Access teacher)
    {
        super(username, password);
        createdQuizzes = new ArrayList<>();
    }

    @Override
    public void listMenu()
    {
        JFrame window;

        window = new TeacherWindow();
        window.setTitle("Logged in as " + Quiz_Management_System.getActiveUser().getUsername());
        window.setSize(640, 480);
        window.setLocationRelativeTo(null); // to not have it open at the corner
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setVisible(true);
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
                listQuizzes();
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

    public void listQuizzes()
    {
        for (Quiz i : createdQuizzes)
        {
            i.displayQuizProperties();
        }
    }

    public void createNewQuiz()
    {
        Quiz newQuiz = new Quiz();
        newQuiz.createQuiz(this);

        newQuiz.setQuizID(DataHandler.quizData.size());

        DataHandler.quizData.add(newQuiz);
        createdQuizzes.add(newQuiz);
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
        int inID = sc.nextInt();

        Quiz newQuiz = Quiz.searchByID(inID);
        if (newQuiz == null)
        {
            System.err.println("No Quiz Found.");
        }
    }

    public void reviewStudentGrades(Quiz newQuiz)
    {
        for (User i : DataHandler.userData)
        {
            if (i instanceof Student)
            {
                for (Student.Attempt j : ((Student) i).getAttemptHistory())
                {
                    if (j.getQuiz().equals(newQuiz))
                    {
                        // TODO properly display the students with their respective grades of the quiz.
                    }
                }
            }
        }
    }

    /**
     * @author marma
     */
    class TeacherWindow extends JFrame
    {
        public TeacherWindow()
        {
            //Title
            JPanel Title = new JPanel();
            Border brdr = BorderFactory.createLineBorder(new Color(222, 184, 150));
            Font myFont = new Font(Font.MONOSPACED, Font.BOLD, 30);
            JLabel Title_label = new JLabel("Welcome " + Quiz_Management_System.getActiveUser().getUsername()); // + username
            Title.add(Title_label);
            add(Title, BorderLayout.PAGE_START);
            Title_label.setFont(myFont);
            Title_label.setForeground(Color.BLACK);
            Title.setBackground(Color.WHITE);
            Title.setBorder(brdr);
            //List
            JPanel l = new JPanel();
            JLabel lb = new JLabel("Created Quizzes: ");
            lb.setBounds(5, 70, 100, 30);
            String[] data = {"1", "2"};
            JList list = new JList(data);
            list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
            list.setLayoutOrientation(JList.HORIZONTAL_WRAP);
            l.add(list);
            list.setPreferredSize(new Dimension(250, 400));
            l.setBackground(new Color(239, 222, 205));
            l.setBounds(5, 100, 280, 420);
            add(l);
            add(lb);
            JScrollPane lScroll = new JScrollPane();
            lScroll.setPreferredSize(new Dimension(250, 80));

            //buttons
            JButton b1 = new JButton("Create quiz");
            b1.setBounds(300, 100, 200, 30);
            b1.setBackground(new Color(222, 184, 150));
            add(b1);
            JButton b2 = new JButton("Review quiz grades");
            b2.setBounds(300, 150, 200, 30);
            b2.setBackground(new Color(222, 184, 150));
            add(b2);
            //background
            JPanel Back = new JPanel();
            Back.setBackground(Color.WHITE);
            add(Back, BorderLayout.CENTER);
        }
    }

    /**
     * @author Habiba1234567
     */
    static class ReviewQuizGrades extends JFrame
    {
        static JFrame window;
        static JPanel title_panel, background;
        static Font myFont;
        static JLabel Title, label1, label2, label3, label4, label5, label6;
        static Border brdr;
        static JTable table;
        static String[] columnNames = {"Student", "Grade"};
        static Object[][] data = {
                {"Kathy", 5}, {"John", 4}};
        static JScrollPane scrollpane;

        static
        {
            title_panel = new JPanel();
            brdr = BorderFactory.createLineBorder(new Color(222, 184, 150));
            background = new JPanel();
            myFont = new Font(Font.MONOSPACED, Font.BOLD, 30);
            Title = new JLabel("Review Quiz Grades ");
            label1 = new JLabel("Best Grade : ");
            label2 = new JLabel("Worst Grade : ");
            label3 = new JLabel("Average Grade : ");
            label4 = new JLabel(" ");
            label5 = new JLabel(" ");
            label6 = new JLabel(" ");
            table = new JTable(data, columnNames);
            scrollpane = new JScrollPane(table);
            table.setFillsViewportHeight(true);
        }

        public ReviewQuizGrades()
        {
            Title.setFont(myFont);
            title_panel.add(Title);
            add(title_panel, BorderLayout.PAGE_START);
            Title.setForeground(Color.BLACK);
            title_panel.setBackground(Color.white);
            title_panel.setBorder(brdr);

            //labels
            label1 = new JLabel("Title: ");
            label1.setBounds(10, 100, 60, 30);
            label2 = new JLabel("Best Grade : ");
            label2.setBounds(10, 140, 100, 30);
            label3 = new JLabel("Worst Grade : ");
            label3.setBounds(10, 180, 200, 30);
            label4 = new JLabel("Average Grade : ");
            label4.setBounds(10, 220, 200, 30);
            label5 = new JLabel(" ");
            label5.setBounds(10, 260, 60, 30);
            label6 = new JLabel(" ");
            label6.setBounds(10, 300, 60, 30);
            add(label1);
            add(label2);
            add(label3);
            add(label4);
            add(label5);
            add(label6);

            //Background
            background.setBackground(Color.WHITE);
            add(background, BorderLayout.CENTER);

            //Table
            table.setBounds(100, 300, 300, 500);
            table.setBackground(new Color(222, 184, 150));
            add(table);

        }

        public static void constructWindow()
        {
            window = new ReviewQuizGrades();
            //  window.setTitle("Logged in as " + Quiz_Management_System.getActiveUser().getUsername());
            window.setSize(400, 200);
            window.setLocationRelativeTo(null); // to not have it open at the corner
            window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            window.setVisible(true);
        }
    }
}
