package quiz_management_system.UserType;

import quiz_management_system.Duration;
import quiz_management_system.Interactive;
import quiz_management_system.Quiz;
import quiz_management_system.Quiz.Question;
import quiz_management_system.Quiz_Management_System;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
        window.setResizable(false);
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
                Quiz newQuiz = Quiz.searchByID(sc.next());
                if (newQuiz == null)
                {
                    err.println("No Quiz Found.");
                    return 0;
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
                Quiz newQuiz = Quiz.searchByID(sc.next());
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

    class StudentWindow extends JFrame implements ActionListener
    {
        // Title
        JPanel Title = new JPanel();
        Border brdr = BorderFactory.createLineBorder(new Color(222, 184, 150));
        Font myFont = new Font(Font.MONOSPACED, Font.BOLD, 30);
        JLabel Title_label = new JLabel("Welcome " + Quiz_Management_System.getActiveUser().getUsername()); // + username
        // Table
        JTable attemptTable;
        JPanel l = new JPanel();
        JLabel lb = new JLabel("Attempt History");
        // Buttons
        JButton actionAttempt = new JButton("Attempt Now");
        JButton actionReview = new JButton("Review quiz grades");
        //background
        JPanel Back = new JPanel();

        JTextField qID = new JTextField(20);

        public StudentWindow()
        {
            Title.add(Title_label);
            add(Title, BorderLayout.PAGE_START);
            Title_label.setFont(myFont);
            Title_label.setForeground(Color.BLACK);
            Title.setBackground(Color.WHITE);
            Title.setBorder(brdr);

            lb.setBounds(5, 70, 100, 30);

            String[] headers = {"ID", "Quiz", "Result"};
            attemptTable = new JTable(loadData(), headers);
            attemptTable.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
            //attemptTable.setPreferredSize(new Dimension(250, 400));
            JScrollPane lScroll = new JScrollPane(attemptTable);
            lScroll.setPreferredSize(new Dimension(250, 80));
            l.add(lScroll);
            l.setBackground(new Color(239, 222, 205));
            l.setBounds(5, 100, 280, 420);
            add(l);
            add(lb);

            qID.setBounds(300, 100, 200, 30);
            qID.setBackground(new Color(222, 184, 150));
            add(qID);

            //buttons
            actionAttempt.setBounds(300, 150, 200, 30);
            actionAttempt.setBackground(new Color(222, 184, 150));
            actionAttempt.addActionListener(this);
            add(actionAttempt);

            Back.setBackground(Color.WHITE);
            add(Back, BorderLayout.CENTER);
        }

        private Object[][] loadData()
        {
            int n = 0;
            Object[][] data;
            if (attemptHistory != null)
            {
                data = new String[3][attemptHistory.size()];
                int j = 0;
                for (Attempt i : attemptHistory)
                {
                    data[0][j] = i.getQuiz().getQuizID();
                    data[1][j] = i.getQuiz().getQuizTitle();
                    data[2][3] = i.getResult();
                    j++;
                }
                return data;
            }
            data = new Object[3][1];
            return data;
        }

        @Override
        public void actionPerformed(ActionEvent e)
        {
            if (e.getSource() == actionReview)
            {
                if (Quiz.searchByID(qID.getText()) == null)
                {
                    JOptionPane.showMessageDialog(null, "Quiz not found.");
                    return;
                }

                //TODO Load attempt object from table and create the window from it
                setVisible(false);
/* FIXME
                JFrame window;
                window = new Attempt.ReviewAttemptWindow();
                window.setTitle("Review Quiz Grades");
                window.setSize(400, 500);
                window.setResizable(false);
                window.setLocationRelativeTo(null); // to not have it open at the corner
                window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                window.setVisible(true);
                */
            }
        }
    }

    public class Attempt
    {
        private final Quiz quiz;
        private Question[] model;
        private int[] answerIndex;
        private double result;

        public Attempt(Quiz newQuiz)
        {
            quiz = newQuiz;
            model = new Question[quiz.getNQuestions()];
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
            answerIndex = new int[quiz.getNQuestions()];
            for (int i = 0; i < quiz.getNQuestions(); i++)
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
            for (int i = 0; i < quiz.getNQuestions(); i++)
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
            JFrame window;
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
                window = new DoAttemptWindow();
                window.setTitle(quiz.getQuizTitle());
                window.setSize(550, 550);
                window.setResizable(false);
                window.setLocationRelativeTo(null); // to not have it open at the corner
                window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                window.setVisible(true);
            }
        }

        /**
         * @author marma
         */
        class ReviewAttemptWindow extends JFrame
        {
            JPanel Back = new JPanel(),
                    Title = new JPanel(),
                    down = new JPanel();
            Font myFont = new Font(Font.MONOSPACED, Font.BOLD, 30);
            JLabel Title_label = new JLabel("Quiz Title"),
                    prompt_label = new JLabel("((((Question here))))"),
                    choice_label = new JLabel("Choices: "),
                    choice1 = new JLabel("choice 1"),
                    choice2 = new JLabel("choice 2"), choice3 = new JLabel("choice 3"),
                    choice4 = new JLabel("choice 4"),
                    Q = new JLabel("Question number: " + 1),
                    grade = new JLabel(" Grade: " + 1.5),
                    answer = new JLabel(" The right answer is " + 1);
            JButton right_b = new JButton(new ImageIcon("UR.PNG")),
                    left_b = new JButton(new ImageIcon("UL.PNG")),
                    submit = new JButton("Close");
            Border brdr = BorderFactory.createLineBorder(new Color(222, 184, 150));
            JRadioButton c1_r = new JRadioButton("Choice 1."),
                    c2_r = new JRadioButton("Choice 1."),
                    c3_r = new JRadioButton("Choice 1."),
                    c4_r = new JRadioButton("Choice 1.");

            public ReviewAttemptWindow()
            {
                //Title
                Title.add(Title_label);
                add(Title, BorderLayout.PAGE_START);
                Title_label.setFont(myFont);
                Title_label.setForeground(Color.BLACK);
                Title.setBackground(Color.WHITE);
                Title.setBorder(brdr);
                // right/left buttons
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
                choice1.setBounds(40, 180, 100, 30);
                choice2.setBounds(40, 220, 100, 30);
                choice3.setBounds(40, 260, 100, 30);
                choice4.setBounds(40, 300, 100, 30);
                grade.setBorder(brdr);
                grade.setBounds(400, 60, 100, 30);
                answer.setBorder(brdr);
                answer.setBounds(0, 350, 535, 30);
                add(Q);
                add(prompt_label);
                add(choice_label);
                add(choice1);
                add(choice2);
                add(choice3);
                add(choice4);
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
        }
    }
}