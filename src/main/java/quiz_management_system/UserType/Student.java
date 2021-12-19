package quiz_management_system.UserType;

import quiz_management_system.Duration;
import quiz_management_system.Interactive;
import quiz_management_system.Quiz;
import quiz_management_system.Quiz.Question;
import quiz_management_system.Quiz_Management_System;

import javax.swing.*;
import java.awt.*;
import java.io.Serial;
import java.util.ArrayList;
import java.util.Scanner;

import static java.lang.System.*;

/**
 * @author belsa
 */
public class Student extends User implements Interactive
{
    @Serial
    private static final long serialVersionUID = 1L;

    private ArrayList<Attempt> attemptHistory;

    public Student(String username, String password, Access student)
    {
        super(username, password);
    }

    public ArrayList<Attempt> getAttemptHistory()
    {
        return attemptHistory;
    }

    @Override
    public void listMenu()
    {
        StudentWindow.constructWindow();
    }

    /**
     * @deprecated
     */
    public int listMenuConsole()
    {
        out.println("*****Logged in as " + Quiz_Management_System.getActiveUser().getUsername() + "*****");
        out.println("1. Attempt quiz.");
        out.println("2. View attempt history.");
        out.println("3. Review Attempt.");
        out.println("Enter -1 to quit");
        Scanner sc = new Scanner(in);
        short n;
        n = sc.nextShort();
        switch (n)
        {
            case -1:
                return -1;
            case 1:
            {
                out.println("Enter Quiz ID: ");
                while (!sc.hasNextInt())
                {
                    err.println("INVALID INPUT.");
                    sc.next();
                }
                Quiz newQuiz = Quiz.searchByID(sc.nextInt());
                if (newQuiz == null)
                {
                    err.println("No Quiz Found.");
                }
                out.println("Starting Quiz: ");
                newQuiz.displayQuizProperties();
                startAttempt(newQuiz);
            }
            case 2:
                viewAttemptHistory();
            case 3:
            {
                out.println("Enter Quiz ID: ");
                while (!sc.hasNextInt())
                {
                    err.println("INVALID INPUT.");
                    sc.next();
                }
                Quiz newQuiz = Quiz.searchByID(sc.nextInt());
                if (newQuiz == null)
                {
                    err.println("No Quiz Found.");
                    return 0;
                }
                Attempt toReview = new Attempt(newQuiz);
                toReview.reviewAttempt();
            }
        }
        return 1;
    }

    public void startAttempt(Quiz newQuiz)
    {
        Attempt newAttempt = new Attempt(newQuiz);
        newAttempt.doAttempt();
        attemptHistory.add(newAttempt);
    }

    public void viewAttemptHistory()
    {
        attemptHistory.forEach(i ->
        {
            double sum = 0;
            for (Question j : i.getModel())
            {
                sum += j.getGrade();
            }
            out.println("Quiz: " + i.quiz.getQuizTitle());
            out.println("Your result: " + i.result + "/" + sum);
        });
    }

    static class StudentWindow extends JFrame
    {
        static JFrame window;

        private static String[] THeader;
        private static Object[][] data;
        static JTable AttemptHistoryTable;
        static JLabel enterQuizID_label;
        static JTextField enterQuizID_text;
        static JButton actionAttempt;
        static JPanel doNewQuiz;

        static
        {
            doNewQuiz = new JPanel(new GridLayout(3, 1));
            data = new Object[2][10];
            enterQuizID_label = new JLabel("Enter Quiz ID");
            enterQuizID_text = new JTextField(10);
            actionAttempt = new JButton("Attempt Quiz");
            THeader = new String[]{"Quiz", "Grade"};
        }

        public StudentWindow()
        {
            AttemptHistoryTable = new JTable(data, THeader);

            setLayout(new FlowLayout(FlowLayout.LEFT));

            doNewQuiz.add(enterQuizID_label);
            doNewQuiz.add(enterQuizID_text);
            doNewQuiz.add(actionAttempt);

            add(AttemptHistoryTable);
            add(doNewQuiz);
        }

        public static void constructWindow()
        {
            window = new StudentWindow();
            window.setTitle("Logged in as " + Quiz_Management_System.getActiveUser().getUsername());
            window.setSize(640, 480);
            window.setLocationRelativeTo(null); // to not have it open at the corner
            window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            window.setVisible(true);
        }
    }

    protected class Attempt
    {
        private final Quiz quiz;
        private Question[] model;
        private int[] answerIndex;
        private double result;

        public Attempt(Quiz newQuiz)
        {
            quiz = newQuiz;
            model = new Question[quiz.getnQuestions()];
            model = quiz.generateQuizModel();
            result = 0;
        }

        public double getResult()
        {
            return result;
        }

        public Quiz getQuiz()
        {
            return quiz;
        }

        public Question[] getModel()
        {
            return model;
        }

        public void doAttempt()
        {
            Scanner sc = new Scanner(in);
            Duration d = new Duration();
            d.run();
            out.println("Starting Quiz...Enter answer index after the prompt appears.");
            answerIndex = new int[quiz.getnQuestions()];
            for(int i = 0; i < quiz.getnQuestions(); i++)
            {
                model[i].displayQuestion();
                answerIndex[i] = sc.nextInt();

                if(model[i].checkAnswer((short) answerIndex[i]))
                    result += model[i].getGrade();

            }
        }

        public void reviewAttempt()
        {
            for (int i = 0; i < quiz.getnQuestions(); i++)
            {
                model[i].displayQuestion();
                out.println("Your Answer: " + answerIndex[i]);
                if (model[i].checkAnswer((short) answerIndex[i]))
                    out.println("Correct");
            }
        }

        static class DoAttemptWindow extends JFrame
        {
            static JFrame window;

            static
            {

            }

            public DoAttemptWindow()
            {

            }

            public static void constructWindow()
            {
                window = new DoAttemptWindow();
                window.setTitle("Logged in as " + Quiz_Management_System.getActiveUser().getUsername());
                window.setSize(400, 200);
                window.setLocationRelativeTo(null); // to not have it open at the corner
                window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                window.setVisible(true);
            }
        }

        static class ReviewAttemptWindow extends JFrame
        {
            static JFrame window;

            static
            {

            }

            public ReviewAttemptWindow()
            {

            }

            public static void constructWindow()
            {
                window = new ReviewAttemptWindow();
                window.setTitle("Logged in as " + Quiz_Management_System.getActiveUser().getUsername());
                window.setSize(400, 200);
                window.setLocationRelativeTo(null); // to not have it open at the corner
                window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                window.setVisible(true);
            }
        }
    }
}

