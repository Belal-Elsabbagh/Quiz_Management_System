package quiz_management_system;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.io.Serial;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.*;

public class Quiz implements Serializable
{
    @Serial
    private static final long serialVersionUID = 1L;

    private String quizID;
    private String quizTitle;
    private Teacher creator;
    private int nAttempts, nQuestions;
    public Date quizDate;
    public LocalTime openTime;
    private ArrayList<Question> questionBank;
    private long totalSec;

    public Quiz()
    {
        questionBank = new ArrayList<>();
    }


    public LocalTime openTime()
    {
        return openTime;
    }

    /**
     * @param qID the required quizID to search
     * @return the found quiz (null if not found)
     */
    public static Quiz searchByID(String qID)
    {
        for (Quiz i : DataHandler.quizData)
        {
            if (i.getQuizID().equals(qID))
            {
                return i;
            }
        }
        return null;
    }

    public String getQuizID()
    {
        return quizID;
    }

    public String getQuizTitle()
    {
        return quizTitle;
    }

    public int getNQuestions()
    {
        return nQuestions;
    }

    public Date quizDate()
    {
        return quizDate;
    }

    public long getTotalSec()
    {
        return totalSec;
    }

    public void setTotalSec(long TotalSec)
    {
        this.totalSec = TotalSec;
    }

    public void setQuizID(String quizID)
    {
        this.quizID = quizID;
    }

    public void displayQuizProperties()
    {
        System.out.println("-----------Displaying quiz properties------------");
        System.out.println("Quiz ID: " + quizID);
        System.out.println("Quiz Title: " + quizTitle);
        System.out.println("Number of questions: " + nQuestions);
        System.out.println("Number of attempts: " + nAttempts);
        System.out.println("Size of question bank: " + questionBank.size());
    }

    @Override
    public String toString()
    {
        return "Quiz{" + "quizID=" + quizID + ", quizTitle=" + quizTitle + ", nAttempts=" + nAttempts + ", nQuestions=" + nQuestions + ", questionBank.size()=" + questionBank.size() + '}';
    }

    /**
     * @param author the creator of the quiz
     */
    public void createQuiz(Teacher author)
    {
        Scanner sc = new Scanner(System.in);
        creator = author;

        System.out.println("-----------Creating a new quiz-------------------");
        System.out.println("Enter quiz title: ");
        quizTitle = sc.nextLine();
        System.out.println("Enter number of questions: ");
        while (!sc.hasNextInt())
        {
            System.err.println("INVALID INPUT.");
            sc.next();
        }
        nQuestions = sc.nextInt();
        System.out.println("Enter number of attempts: ");
        while (!sc.hasNextInt())
        {
            System.err.println("INVALID INPUT.");
            sc.next();
        }
        nAttempts = sc.nextInt();

        createQuestionBank();
        quizTime();

        System.out.println("Quiz created successfully.");
    }

    public void createQuestionBank()
    {
        questionBank = new ArrayList<>();
        Scanner sc = new Scanner(System.in);

        System.out.println("-----------Question Bank Creator-----------------");
        int i = 0;

        while (!sc.hasNextInt())
        {
            Question tempQ = new Question();
            tempQ.createQuestionConsole();
            tempQ.questionID = i + 1;
            questionBank.add(tempQ);
            System.out.println("stop?");
            while (!sc.hasNextInt())
            {
                System.err.println("INVALID INPUT.");
                sc.next();
            }
            i = sc.nextInt();
        }
        System.out.println("Question Bank created successfully.");
    }

    /**
     * @return a shuffled set of questions in an array
     */
    public Question[] generateQuizModel()
    {
        Question[] newModel;
        newModel = new Question[nQuestions];

        ArrayList<Question> shuffled = new ArrayList<>(questionBank);
        Collections.shuffle(shuffled);

        for (int i = 0; i < nQuestions; i++)
        {
            newModel[i] = shuffled.get(i);
        }
        return newModel;
    }

    public void quizTime()
    {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter Date (day-month-year):");
        SimpleDateFormat dateIn = new SimpleDateFormat("dd-MM-yyyy");

        String takeDate = sc.nextLine();

        try
        {
            Date date = dateIn.parse(takeDate);
            System.out.println("The quiz will be on: " + new SimpleDateFormat("dd-MM-yyyy").format(date));
            quizDate = dateIn.parse(takeDate);

        }
        catch (ParseException e)
        {
            System.out.println("Invalid input");
        }
        System.out.println("Enter quiz time (hr:min:sec):");
        System.out.println("as in the format 00:00:00");
        String time = sc.next();
        try
        {
            LocalTime Time = LocalTime.parse(time);
            System.out.println("The quiz time: " + Time);
            openTime = LocalTime.parse(time);

        }
        catch (Exception e)
        {
            System.out.println("Invalid input");
        }
        System.out.println("Enter quiz duration:");
        System.out.println("Hour:");
        int hour;
        hour = sc.nextInt();
        System.out.println("Min:");
        int min;
        min = sc.nextInt();
        System.out.println("Sec:");
        int sec;
        sec = sc.nextInt();
        System.out.println("The quiz duration will be:" + hour + ":" + min + ":" + sec);
        totalSec = ((long) hour * 60 * 60) + (min * 60) + sec;
    }

    public class Question implements Serializable
    {
        private int questionID;
        private String prompt;
        private double grade;
        private Choice mcq;

        /**
         * @deprecated not used with GUI
         */
        public void createQuestionConsole()
        {
            Scanner sc = new Scanner(System.in);
            System.out.println("-----------Creating a new question-----------");
            System.out.println("Enter prompt: ");
            prompt = sc.next();

            sc = new Scanner(System.in);
            System.out.println("Enter grade: ");
            while (!sc.hasNextDouble())
            {
                System.err.println("INVALID INPUT.");
                sc.next();
            }
            grade = sc.nextDouble();

            mcq = new Choice();
            mcq.createMCQConsole();

            System.out.println("Question added successfully.");
        }

        public String getPrompt()
        {
            return prompt;
        }

        public Choice getMCQ()
        {
            return mcq;
        }

        public double getGrade()
        {
            return grade;
        }

        /**
         * @deprecated not used with GUI
         */
        public void displayQuestionConsole()
        {
            System.out.println(prompt);
            for (int i = 0; i < mcq.nChoices; i++)
            {
                System.out.println((i + 1) + ". " + mcq.choices[i]);
            }
        }

        public boolean checkAnswer(short inAnswer)
        {
            return inAnswer == mcq.answerKeyIndex;
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

        class Choice implements Serializable
        {
            private int nChoices;
            private String[] choices;
            private int answerKeyIndex;

            private Choice()
            {
                this.nChoices = 4;
                choices = new String[4];
            }

            public String[] getChoices()
            {
                return choices;
            }

            public String getChoiceAt(int index)
            {
                return choices[index];
            }

            /**
             * @deprecated not used with GUI
             */
            public void createMCQConsole()
            {

                Scanner sc = new Scanner(System.in);

                System.out.println("-----------Creating MCQ------------------");

                System.out.println("Enter number of choices: ");
                while (!sc.hasNextShort())
                {
                    System.err.println("INVALID INPUT.");
                    sc.next();
                }
                nChoices = sc.nextShort();

                choices = new String[nChoices];

                for (int i = 0; i < nChoices; i++)
                {
                    System.out.println("Enter choice " + i + ": ");
                    choices[i] = sc.next();
                }

                answerKeyIndex = nChoices;
                while (answerKeyIndex > nChoices - 1)
                {
                    System.out.println("Enter the index of right answer (count starts at 0): ");
                    while (!sc.hasNextShort())
                    {
                        System.err.println("INVALID INPUT.");
                        sc.next();
                    }
                    answerKeyIndex = sc.nextShort();
                    if (answerKeyIndex > nChoices - 1)
                    {
                        System.out.println("Error: The number of choice doesn't exist");
                    }
                }
                System.out.println("Choices added successfully.");
            }
        }
    }
}
