package quiz_management_system;

import quiz_management_system.Quiz.*;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
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
        super(username, password, student);
        attemptHistory = new ArrayList<>();
    }

    /**
     * @deprecated
     */
    public static void consoleLogin()
    {
        Scanner sc = new Scanner(System.in);
        String inUsername, inPassword;
        System.out.println("*****Quiz Management System*****");
        System.out.println("Enter username:");
        inUsername = sc.next();
        System.out.println("Enter password:");
        inPassword = sc.next();
        Quiz_Management_System.setActiveUser(login(inUsername, inPassword));
        Quiz_Management_System.getActiveUser().listMenu();
    }

    public ArrayList<Attempt> getAttemptHistory()
    {
        return attemptHistory;
    }

    public Attempt getAttempt(String qID)
    {
        for (Attempt i : attemptHistory)
        {
            if (i.getQuiz().getQuizID().equals(qID))
                return i;
        }
        return null;
    }

    @Override
    public void listMenu()
    {
        JFrame window = new StudentWindow();
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
                newQuiz.displayQuizPropertiesConsole();
                startAttempt(newQuiz);
            }
            case 2:
                viewAttemptHistoryConsole();
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

    public void createNewAttempt(Quiz x)
    {
        new Attempt(x);
    }

    public void startAttempt(Quiz newQuiz)
    {
        Attempt newAttempt = new Attempt(newQuiz);
    }

    /**
     * @deprecated not used with GUI.
     */
    public void viewAttemptHistoryConsole()
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

        JPanel chat_panel = new JPanel();
        JButton actionCloseChat = new JButton("x");
        JButton actionOpenChat = new JButton(new ImageIcon("t1.jpeg"));

        JTextField qID = new JTextField(20);

        public StudentWindow()
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

            setTitle("Logged in as " + Quiz_Management_System.getActiveUser().getUsername());
            setSize(550, 550);
            setResizable(false);
            setLocationRelativeTo(null); // to not have it open at the corner
            setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            setVisible(true);
        }

        private void constructData()
        {
            DefaultTableModel data = new DefaultTableModel();
            String[] headers = {"ID", "Quiz", "Result"};
            data.setColumnIdentifiers(headers);
            for (Attempt i : attemptHistory)
            {
                Object[] row = new Object[]{i.getQuiz().getQuizTitle(), i.getResult()};
                data.addRow(row);
            }
            attemptTable = new JTable(data);
        }

        @Override
        public void actionPerformed(ActionEvent e)
        {
            if (e.getSource() == actionReview)
            {
                Attempt toReview = getAttempt(qID.getText());
                if (toReview == null)
                {
                    JOptionPane.showMessageDialog(null, "Quiz not found.");
                    return;
                }

                //TODO Load attempt object from table and create the window from it
                toReview.openReview();
                setVisible(false);
            }
            else if (e.getSource() == actionAttempt)
            {
                Quiz newA;
                try
                {
                    newA = Quiz.searchByID(qID.getText());
                } catch (NullPointerException a)
                {
                    JOptionPane.showMessageDialog(null, "Quiz not found", "Error", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                new Attempt(newA);
            }
            else if (e.getSource() == actionOpenChat)
            {
                chat_panel.setVisible(true);
            }
            else if (e.getSource() == actionCloseChat)
            {
                chat_panel.setVisible(false);
            }
        }
    }

    public class Attempt
    {
        private Quiz quiz;
        private Question[] model;
        private int[] answerIndexes;
        private double result;

        public Attempt(Quiz newQuiz)
        {
            quiz = newQuiz;
            model = newQuiz.generateQuizModel();
            answerIndexes = new int[quiz.getNQuestions()];
            model = quiz.generateQuizModel();
            result = 0;
            new DoAttemptWindow();
        }

        public void openReview()
        {
            new ReviewAttemptWindow();
        }

        public int[] getAnswerIndex()
        {
            return answerIndexes;
        }

        public void setAnswerIndex(int[] answerIndex)
        {
            this.answerIndexes = answerIndex;
        }

        public double getResult()
        {
            return result;
        }

        public void setResult(double result)
        {
            this.result = result;
        }

        public Quiz getQuiz()
        {
            return quiz;
        }

        public Question[] getModel()
        {
            return model;
        }

        public void setModel(Question[] model)
        {
            this.model = model;
        }

        private void addThisAttemptToHistory()
        {
            attemptHistory.add(this);
        }

        /**
         * @deprecated not used with GUI
         */
        public void doAttemptConsole()
        {
            Scanner sc = new Scanner(in);
            Duration d = new Duration();
            d.run();
            out.println("Starting Quiz...Enter answer index after the prompt appears.");
            answerIndexes = new int[quiz.getNQuestions()];
            for (int i = 0; i < quiz.getNQuestions(); i++)
            {
                model[i].displayQuestionConsole();
                answerIndexes[i] = sc.nextInt();

                if (model[i].checkAnswer((short) answerIndexes[i]))
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
                model[i].displayQuestionConsole();
                out.println("Your Answer: " + answerIndexes[i]);
                if (model[i].checkAnswer((short) answerIndexes[i]))
                    out.println("Correct");
            }
        }

        /**
         * @author marma
         */
        class DoAttemptWindow extends JFrame implements ActionListener, AttemptWindow
        {
            JPanel Back = new JPanel(),
                    Title = new JPanel(),
                    down = new JPanel();
            Font myFont = new Font(Font.MONOSPACED, Font.BOLD, 30);
            JButton right_b = new JButton(new ImageIcon("UR.PNG")),
                    left_b = new JButton(new ImageIcon("UL.PNG")),
                    submit = new JButton("Submit");
            Border brdr = BorderFactory.createLineBorder(new Color(222, 184, 150));
            JRadioButton c1_r = new JRadioButton("Choice 1"),   // get the text written in choice 1
                    c2_r = new JRadioButton("Choice 2"),   // get the text written in choice 2
                    c3_r = new JRadioButton("Choice 3"),   // get the text written in choice 3
                    c4_r = new JRadioButton("Choice 4");   // get the text written in choice 4
            ButtonGroup choices = new ButtonGroup();
            private int currentQuestionIndex = 0;
            JLabel Title_label = new JLabel(quiz.getQuizTitle()),
                    prompt_label = new JLabel(model[0].getPrompt()),
                    choice_label = new JLabel("choices: "),
                    c1 = new JLabel(model[currentQuestionIndex].getMCQ().getChoices()[0]),
                    c2 = new JLabel(model[currentQuestionIndex].getMCQ().getChoices()[1]),
                    c3 = new JLabel(model[currentQuestionIndex].getMCQ().getChoices()[2]),
                    c4 = new JLabel(model[currentQuestionIndex].getMCQ().getChoices()[3]),
                    currentQuestionLabel = new JLabel("Question: " + (currentQuestionIndex + 1) + "/" + model.length),
                    grade = new JLabel(" Grade: " + model[currentQuestionIndex].getGrade()),
                    timer = new JLabel("Timer");

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
                right_b.setBackground(Color.WHITE);
                if (currentQuestionIndex == quiz.getNQuestions() - 1)
                {
                    right_b.setEnabled(false);
                }
                left_b.setBackground(Color.WHITE);
                left_b.setEnabled(false);
                down.add(left_b, BorderLayout.EAST);
                down.add(right_b, BorderLayout.PAGE_END);
                down.setBackground(Color.WHITE);
                add(down, BorderLayout.PAGE_END);
                //labels
                currentQuestionLabel.setBounds(5, 60, 200, 30);
                prompt_label.setBounds(5, 100, 500, 30);
                choice_label.setBounds(5, 140, 100, 30);
                c1.setBounds(40, 180, 100, 30);
                c2.setBounds(40, 220, 100, 30);
                c3.setBounds(40, 260, 100, 30);
                c4.setBounds(40, 300, 100, 30);
                grade.setBorder(brdr);
                grade.setBounds(400, 60, 100, 30);
                timer.setBorder(brdr);
                timer.setBounds(450, 60, 50, 30);
                add(currentQuestionLabel);
                add(prompt_label);
                add(choice_label);
                add(c1);
                add(c2);
                add(c3);
                add(c4);
                add(grade);
                add(timer);
                //radio buttons
                c1_r.setBackground(Color.WHITE);
                c2_r.setBackground(Color.WHITE);
                c3_r.setBackground(Color.WHITE);
                c4_r.setBackground(Color.WHITE);
                choices.add(c1_r);
                choices.add(c2_r);
                choices.add(c3_r);
                choices.add(c4_r);
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

                setTitle(quiz.getQuizTitle());
                setSize(550, 550);
                setResizable(false);
                setLocationRelativeTo(null); // to not have it open at the corner
                setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                setVisible(true);
            }

            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (e.getSource() == right_b)
                {
                    saveChoice();
                    goRight();
                }
                else if (e.getSource() == left_b)
                {
                    saveChoice();
                    goLeft();
                }
                else if (e.getSource() == submit)
                {
                    addThisAttemptToHistory();
                }
            }

            private void saveChoice()
            {
                if (c1_r.isSelected())
                    answerIndexes[currentQuestionIndex] = 1;
                else if (c2_r.isSelected())
                    answerIndexes[currentQuestionIndex] = 2;
                else if (c3_r.isSelected())
                    answerIndexes[currentQuestionIndex] = 3;
                else if (c4_r.isSelected())
                    answerIndexes[currentQuestionIndex] = 4;
            }

            @Override
            public void goRight()
            {
                currentQuestionIndex++;
                if (currentQuestionIndex == quiz.getNQuestions() - 1)
                {
                    right_b.setEnabled(false);
                }
                else
                {
                    right_b.setEnabled(true);
                }
                currentQuestionLabel.setText("Question: " + (currentQuestionIndex + 1) + "/" + model.length);
                grade.setText(String.valueOf(model[currentQuestionIndex].getGrade()));
                c1.setText(model[currentQuestionIndex].getMCQ().getChoices()[0]);
                c2.setText(model[currentQuestionIndex].getMCQ().getChoices()[1]);
                c3.setText(model[currentQuestionIndex].getMCQ().getChoices()[2]);
                c4.setText(model[currentQuestionIndex].getMCQ().getChoices()[3]);
            }

            @Override
            public void goLeft()
            {
                currentQuestionIndex--;
                if (currentQuestionIndex == 0)
                {
                    left_b.setEnabled(false);
                }
                else
                {
                    left_b.setEnabled(true);
                }
                currentQuestionLabel.setText("Question: " + (currentQuestionIndex + 1) + "/" + model.length);
                grade.setText(String.valueOf(model[currentQuestionIndex].getGrade()));
                c1.setText(model[currentQuestionIndex].getMCQ().getChoices()[0]);
                c2.setText(model[currentQuestionIndex].getMCQ().getChoices()[1]);
                c3.setText(model[currentQuestionIndex].getMCQ().getChoices()[2]);
                c4.setText(model[currentQuestionIndex].getMCQ().getChoices()[3]);
            }
        }

        /**
         * @author marma
         */
        class ReviewAttemptWindow extends JFrame implements ActionListener, AttemptWindow
        {
            JPanel Back = new JPanel(),
                    Title = new JPanel(),
                    down = new JPanel();
            Font myFont = new Font(Font.MONOSPACED, Font.BOLD, 30);
            JButton right_b = new JButton(new ImageIcon("UR.PNG")),
                    left_b = new JButton(new ImageIcon("UL.PNG")),
                    submit = new JButton("Close");
            Border brdr = BorderFactory.createLineBorder(new Color(222, 184, 150));
            JRadioButton c1_r = new JRadioButton("Choice 1."),
                    c2_r = new JRadioButton("Choice 1."),
                    c3_r = new JRadioButton("Choice 1."),
                    c4_r = new JRadioButton("Choice 1.");
            ButtonGroup choices = new ButtonGroup();
            private int currentQuestionIndex = 0;
            JLabel Title_label = new JLabel("Quiz Title"),
                    prompt_label = new JLabel("((((Question here))))"),
                    choice_label = new JLabel("Choices: "),
                    c1 = new JLabel(model[currentQuestionIndex].getMCQ().getChoices()[0]),
                    c2 = new JLabel(model[currentQuestionIndex].getMCQ().getChoices()[1]),
                    c3 = new JLabel(model[currentQuestionIndex].getMCQ().getChoices()[2]),
                    c4 = new JLabel(model[currentQuestionIndex].getMCQ().getChoices()[3]),
                    currentQuestionLabel = new JLabel("Question: " + (currentQuestionIndex + 1) + "/" + model.length),
                    Q = new JLabel("Question number: " + (currentQuestionIndex + 1)),
                    grade = new JLabel(" Grade: " + model[currentQuestionIndex].getGrade()),
                    answer = new JLabel(" The right answer is " + 1);

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
                c1_r.setEnabled(false);
                c2_r.setBackground(Color.WHITE);
                c2_r.setEnabled(false);
                c3_r.setBackground(Color.WHITE);
                c3_r.setEnabled(false);
                c4_r.setBackground(Color.WHITE);
                c4_r.setEnabled(false);
                choices.add(c1_r);
                choices.add(c2_r);
                choices.add(c3_r);
                choices.add(c4_r);
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

                setTitle("Review Quiz (" + quiz.getQuizTitle() + ") Grades");
                setSize(400, 500);
                setResizable(false);
                setLocationRelativeTo(null); // to not have it open at the corner
                setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                setVisible(true);
            }

            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (e.getSource() == right_b)
                {
                    goRight();
                }
                else if (e.getSource() == left_b)
                {
                    goLeft();
                }
                //TODO Change the submit button in Reviewing
                else if (e.getSource() == submit)
                {
                    addThisAttemptToHistory();
                }
            }

            @Override
            public void goRight()
            {
                currentQuestionIndex++;

                answer.setText(String.valueOf(model[currentQuestionIndex].getMCQ().getAnswerKeyIndex() + 1));
                currentQuestionLabel.setText("Question: " + (currentQuestionIndex + 1) + "/" + model.length);
                grade.setText(String.valueOf(model[currentQuestionIndex].getGrade()));
                c1.setText(model[currentQuestionIndex].getMCQ().getChoices()[0]);
                c2.setText(model[currentQuestionIndex].getMCQ().getChoices()[1]);
                c3.setText(model[currentQuestionIndex].getMCQ().getChoices()[2]);
                c4.setText(model[currentQuestionIndex].getMCQ().getChoices()[3]);
            }

            @Override
            public void goLeft()
            {
                currentQuestionIndex--;

                answer.setText(String.valueOf(model[currentQuestionIndex].getMCQ().getAnswerKeyIndex() + 1));
                currentQuestionLabel.setText("Question: " + (currentQuestionIndex + 1) + "/" + model.length);
                grade.setText(String.valueOf(model[currentQuestionIndex].getGrade()));
                c1.setText(model[currentQuestionIndex].getMCQ().getChoices()[0]);
                c2.setText(model[currentQuestionIndex].getMCQ().getChoices()[1]);
                c3.setText(model[currentQuestionIndex].getMCQ().getChoices()[2]);
                c4.setText(model[currentQuestionIndex].getMCQ().getChoices()[3]);
            }
        }
    }
}