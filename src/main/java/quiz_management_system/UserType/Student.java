package quiz_management_system.UserType;

import quiz_management_system.Duration;
import quiz_management_system.Interactive;
import quiz_management_system.Quiz;
import quiz_management_system.Quiz.Question;
import quiz_management_system.Quiz_Management_System;

import javax.swing.*;
import javax.swing.border.Border;
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
        JFrame window;

        window = new StudentWindow();
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
                toReview.reviewAttemptConsole();
            }
        }
        return 1;
    }

    public void startAttempt(Quiz newQuiz)
    {
        Attempt newAttempt = new Attempt(newQuiz);
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

    class StudentWindow extends JFrame
    {
        private String[] THeader = new String[]{"Quiz", "Grade"};
        private Object[][] data = new Object[2][10];
        JTable AttemptHistoryTable;
        JLabel enterQuizID_label = new JLabel("Enter Quiz ID");
        JTextField enterQuizID_text = new JTextField(10);
        JButton actionAttempt = new JButton("Attempt Quiz");
        JPanel doNewQuiz = new JPanel(new GridLayout(3, 1));

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

        public void closeWindow()
        {
            setVisible(false);
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

            JFrame window;
            window = new DoAttemptWindow();
            window.setTitle(newQuiz.getQuizTitle());
            window.setSize(550, 550);
            window.setResizable(false);
            window.setLocationRelativeTo(null); // to not have it open at the corner
            window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            window.setVisible(true);
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

                if (model[i].checkAnswer((short) answerIndex[i]))
                    result += model[i].getGrade();

            }
        }

        /**
         * @deprecated
         */
        public void reviewAttemptConsole()
        {
            for (int i = 0; i < quiz.getnQuestions(); i++)
            {
                model[i].displayQuestion();
                out.println("Your Answer: " + answerIndex[i]);
                if (model[i].checkAnswer((short) answerIndex[i]))
                    out.println("Correct");
            }
        }

        /**
         * @author marma
         */
        class DoAttemptWindow extends JFrame
        {
            JPanel Back = new JPanel(),
                    Title = new JPanel(),
                    down = new JPanel();
            Font myFont = new Font(Font.MONOSPACED, Font.BOLD, 30);
            JLabel Title_label = new JLabel("Quiz Title"),
                    prompt_label = new JLabel("((((Question here))))"),
                    choice_label = new JLabel("choices: "),
                    c1,
                    c2,
                    c3,
                    c4,
                    Q = new JLabel("Question number: " + 1),
                    timer = new JLabel(" Timer");
            JButton right_b = new JButton(), left_b = new JButton(), submit = new JButton("Submit");
            ;
            Icon icon_r = new ImageIcon("UR.PNG"),
                    icon_l = new ImageIcon("UL.PNG");
            Border brdr = BorderFactory.createLineBorder(new Color(222, 184, 150));
            JRadioButton c1_r = new JRadioButton("Choice 1"),   //get the text written in choice 1
                    c2_r = new JRadioButton("Choice 2"),   //get the text written in choice 2
                    c3_r = new JRadioButton("Choice 3"),   //get the text written in choice 3
                    c4_r = new JRadioButton("Choice 4");   //get the text written in choice 4

            public DoAttemptWindow()
            {
                //Title
                Title.add(Title_label);
                add(Title, BorderLayout.PAGE_START);
                Title_label.setFont(myFont);
                Title_label.setForeground(Color.BLACK);
                Title.setBackground(Color.WHITE);
                Title.setBorder(brdr);
                // rigth/left buttons
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
                prompt_label.setBounds(5, 100, 500, 30);
                choice_label.setBounds(5, 140, 100, 30);
                c1.setBounds(40, 180, 100, 30);
                c2.setBounds(40, 220, 100, 30);
                c3.setBounds(40, 260, 100, 30);
                c4.setBounds(40, 300, 100, 30);
                timer.setBorder(brdr);
                timer.setBounds(450, 60, 50, 30);
                add(Q);
                add(prompt_label);
                add(choice_label);
                add(c1);
                add(c2);
                add(c3);
                add(c4);
                add(timer);
                //radio buttons
                c1_r.setBackground(Color.WHITE);
                c2_r.setBackground(Color.WHITE);
                c3_r.setBackground(Color.WHITE);
                c4_r.setBackground(Color.WHITE);
                ButtonGroup g = new ButtonGroup();
                g.add(c1_r);
                g.add(c2_r);
                g.add(c3_r);
                g.add(c4_r);
                c1_r.setBounds(5, 180, 20, 30);
                c2_r.setBounds(5, 220, 20, 30);
                c3_r.setBounds(5, 260, 20, 30);
                c4_r.setBounds(5, 300, 20, 30);
                add(c1_r);
                add(c2_r);
                add(c3_r);
                add(c4_r);
                //button
                submit.setFont(new Font("Sans Serif", Font.PLAIN, 25));
                submit.setFocusable(false);
                submit.setBounds(430, 460, 100, 40);
                submit.setBorder(BorderFactory.createEtchedBorder());
                submit.setBackground(new Color(222, 184, 150));
                add(submit);
                //background
                Back.setBackground(Color.WHITE);
                add(Back, BorderLayout.CENTER);
            }

            public void constructWindow()
            {

            }
        }

        /**
         * @author marma
         */
        static class ReviewAttemptWindow extends JFrame
        {
            static JFrame window;
            static JPanel Back, Title, down;
            static Font myFont;
            static JLabel Title_label, prompt_label, choice_label, c1, c2, c3, c4, Q, grade, answer;
            static JButton right_b, left_b, submit;
            static Icon icon_r, icon_l;
            static Border brdr;
            static JRadioButton c1_r, c2_r, c3_r, c4_r, I_r;

            static
            {
                myFont = new Font(Font.MONOSPACED, Font.BOLD, 30);
                Title_label = new JLabel("Quiz Title");
                Back = new JPanel();
                Title = new JPanel();
                brdr = BorderFactory.createLineBorder(new Color(222, 184, 150));
                down = new JPanel();
                Q = new JLabel("Question number: " + 1);
                prompt_label = new JLabel("((((Question here))))");  // getQuizTitle function
                choice_label = new JLabel("choices: ");
                c1 = new JLabel("choice 1");     //get the text written in choice 1
                c2 = new JLabel("choice 2");     //get the text written in choice 2
                c3 = new JLabel("choice 3");     //get the text written in choice 3
                c4 = new JLabel("choice 4");     //get the text written in choice 4
                grade = new JLabel(" Grade: " + 100);
                submit = new JButton("Close");
                answer = new JLabel(" The right answer is " + 1);   //index of right answer
                right_b = new JButton();
                left_b = new JButton();
                icon_r = new ImageIcon("D:\\UR.PNG");
                icon_l = new ImageIcon("D:\\UL.PNG");
                c1_r = new JRadioButton();
                c2_r = new JRadioButton();
                c3_r = new JRadioButton();
                c4_r = new JRadioButton();
            }

            public ReviewAttemptWindow()
            {
                //Title
                Title.add(Title_label);
                add(Title, BorderLayout.PAGE_START);
                Title_label.setFont(myFont);
                Title_label.setForeground(Color.BLACK);
                Title.setBackground(Color.WHITE);
                Title.setBorder(brdr);
                // rigth/left buttons
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
                prompt_label.setBounds(5, 100, 500, 30);
                choice_label.setBounds(5, 140, 100, 30);
                c1.setBounds(40, 180, 100, 30);
                c2.setBounds(40, 220, 100, 30);
                c3.setBounds(40, 260, 100, 30);
                c4.setBounds(40, 300, 100, 30);
                grade.setBorder(brdr);
                grade.setBounds(400, 60, 100, 30);
                answer.setBorder(brdr);
                answer.setBounds(0, 350, 535, 30);
                add(Q);
                add(prompt_label);
                add(choice_label);
                add(c1);
                add(c2);
                add(c3);
                add(c4);
                add(grade);
                add(answer);
                //radio buttons
                c1_r.setBackground(Color.WHITE);
                c2_r.setBackground(Color.WHITE);
                c3_r.setBackground(Color.WHITE);
                c4_r.setBackground(Color.WHITE);
                ButtonGroup g = new ButtonGroup();
                g.add(c1_r);
                g.add(c2_r);
                g.add(c3_r);
                g.add(c4_r);
                c1_r.setBounds(5, 180, 20, 30);
                c2_r.setBounds(5, 220, 20, 30);
                c3_r.setBounds(5, 260, 20, 30);
                c4_r.setBounds(5, 300, 20, 30);
                add(c1_r);
                add(c2_r);
                add(c3_r);
                add(c4_r);
                //button
                submit.setFont(new Font("Sans Serif", Font.PLAIN, 25));
                submit.setFocusable(false);
                submit.setBounds(430, 460, 100, 40);
                submit.setBorder(BorderFactory.createEtchedBorder());
                submit.setBackground(new Color(222, 184, 150));
                add(submit);
                //background
                Back.setBackground(Color.WHITE);
                add(Back, BorderLayout.CENTER);
            }

            public static void constructWindow()
            {
                window = new ReviewAttemptWindow();
                //window.setTitle("Logged in as " + Quiz_Management_System.getActiveUser().getUsername());
                window.setSize(550, 550);
                window.setResizable(false);
                window.setLocationRelativeTo(null); // to not have it open at the corner
                window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                window.setVisible(true);
            }
        }
    }
}