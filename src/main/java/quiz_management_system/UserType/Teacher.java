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
        TeacherWindow.constructWindow();
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

    static class TeacherWindow extends JFrame
    {
        static JFrame window;

        static
        {

        }

        public TeacherWindow()
        {

        }

        public static void constructWindow()
        {
            window = new TeacherWindow();
            window.setTitle("Logged in as " + Quiz_Management_System.getActiveUser().getUsername());
            window.setSize(400, 200);
            window.setLocationRelativeTo(null); // to not have it open at the corner
            window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            window.setVisible(true);
        }

    }

    static class CreateQuestionBank extends JFrame
    {

        static JFrame window;
        static JPanel Back, Title, down;
        static Font myFont;
        static JLabel Title_label, prompt_label, grade_label, choice_label, c1, c2, c3, c4, index_label, Q;
        static JButton right_b, left_b;
        static Icon icon_r, icon_l;
        static JTextField Q_t, G_t, c1_t, c2_t, c3_t, c4_t, I_t;
        static Border brdr;

        static
        {
            myFont = new Font(Font.MONOSPACED, Font.BOLD, 30);
            Title_label = new JLabel("Question Creator");
            Back = new JPanel();
            Title = new JPanel();
            brdr = BorderFactory.createLineBorder(new Color(222, 184, 150));
            down = new JPanel();
            Q = new JLabel("Question number: " + 1);
            prompt_label = new JLabel("Enter prompt:");
            grade_label = new JLabel("Enter grade: ");
            choice_label = new JLabel("Enter choices: ");
            index_label = new JLabel("Enter the index of right answer: ");
            c1 = new JLabel("1.");
            c2 = new JLabel("2.");
            c3 = new JLabel("3.");
            c4 = new JLabel("4.");
            right_b = new JButton();
            left_b = new JButton();
            icon_r = new ImageIcon("UR.PNG");
            icon_l = new ImageIcon("UL.PNG");
            Q_t = new JTextField();
            G_t = new JTextField();
            c1_t = new JTextField();
            c2_t = new JTextField();
            c3_t = new JTextField();
            c4_t = new JTextField();
            I_t = new JTextField();
        }

        public CreateQuestionBank()
        {
            //Title
            Title.add(Title_label);
            add(Title, BorderLayout.PAGE_START);
            Title_label.setFont(myFont);
            Title_label.setForeground(Color.BLACK);
            Title.setBackground(Color.WHITE);
            Title.setBorder(brdr);
            // right/left buttons
            right_b.setIcon(icon_r);
            left_b.setIcon(icon_l);
            right_b.setBackground(Color.WHITE);
            left_b.setBackground(Color.WHITE);
            down.add(left_b, BorderLayout.EAST);
            down.add(right_b, BorderLayout.PAGE_END);
            down.setBackground(Color.WHITE);
            add(down, BorderLayout.PAGE_END);
            //labels
            Q.setBounds(5, 60, 200, 30);
            prompt_label.setBounds(5, 80, 100, 30);
            grade_label.setBounds(5, 150, 100, 30);
            choice_label.setBounds(5, 180, 100, 30);
            index_label.setBounds(5, 380, 200, 30);
            c1.setBounds(5, 220, 10, 10);
            c2.setBounds(5, 260, 10, 10);
            c3.setBounds(5, 300, 10, 10);
            c4.setBounds(5, 340, 10, 10);
            add(Q);
            add(prompt_label);
            add(grade_label);
            add(choice_label);
            add(index_label);
            add(c1);
            add(c2);
            add(c3);
            add(c4);
            //text fields
            Q_t.setBounds(5, 110, 330, 30);
            G_t.setBounds(100, 150, 60, 30);
            c1_t.setBounds(30, 210, 300, 30);
            c2_t.setBounds(30, 250, 300, 30);
            c3_t.setBounds(30, 290, 300, 30);
            c4_t.setBounds(30, 330, 300, 30);
            I_t.setBounds(210, 380, 60, 30);
            add(Q_t);
            add(G_t);
            add(c1_t);
            add(c2_t);
            add(c3_t);
            add(c4_t);
            add(I_t);
            //background
            Back.setBackground(Color.WHITE);
            add(Back, BorderLayout.CENTER);
        }

        public static void constructWindow()
        {
            window = new CreateQuestionBank();
            window.setTitle("Create Question Bank");
            window.setSize(550, 550);
            window.setResizable(false);
            window.setLocationRelativeTo(null); // to not have it open at the corner
            window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            window.setVisible(true);
        }
    }

    static class ReviewQuizWindow extends JFrame
    {
        static JFrame window;

        static
        {

        }

        public ReviewQuizWindow()
        {

        }

        public static void constructWindow()
        {
            window = new ReviewQuizWindow();
            window.setTitle("Logged in as " + Quiz_Management_System.getActiveUser().getUsername());
            window.setSize(400, 200);
            window.setLocationRelativeTo(null); // to not have it open at the corner
            window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            window.setVisible(true);
        }
    }
}
