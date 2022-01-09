package quiz_management_system;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    private int nQuestions;
    public Date quizDate;
    public LocalTime openTime;
    private ArrayList<Question> questionBank;
    private long totalSec;

    public Quiz()
    {
        questionBank = new ArrayList<>();
        JFrame window = new CreateQuizWindow();
    }

    public Quiz(Teacher author)
    {
        this.creator = author;
        questionBank = new ArrayList<>();
        JFrame window = new CreateQuizWindow();
    }

    public Quiz(String quizID, String quizTitle, Teacher creator, int nQuestions)
    {
        this.quizID = quizID;
        this.quizTitle = quizTitle;
        this.creator = creator;
        this.nQuestions = nQuestions;

        this.questionBank = new ArrayList<>();
        questionBank.add(new Question(1, "What is this planet?", 1.5));
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

    public void setQuizID(String quizID)
    {
        this.quizID = quizID;
    }

    public ArrayList<Question> getQuestionBank()
    {
        return questionBank;
    }

    public String getQuizTitle()
    {
        return quizTitle;
    }

    public long getTotalSec()
    {
        return totalSec;
    }

    public void setTotalSec(long TotalSec)
    {
        this.totalSec = TotalSec;
    }

    public int getNQuestions()
    {
        return nQuestions;
    }

    public Date quizDate()
    {
        return quizDate;
    }

    /**
     * @deprecated Not used with GUI
     */
    public void displayQuizPropertiesConsole()
    {
        System.out.println("-----------Displaying quiz properties------------");
        System.out.println("Quiz ID: " + quizID);
        System.out.println("Quiz Title: " + quizTitle);
        System.out.println("Number of questions: " + nQuestions);
        System.out.println("Size of question bank: " + questionBank.size());
    }

    /**
     * @param author the creator of the quiz
     * @deprecated Not Used with GUI
     */
    public void createQuizConsole(Teacher author)
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

        public Question(int questionID, String prompt, double grade)
        {
            this.questionID = questionID;
            this.prompt = prompt;
            this.grade = grade;
            this.mcq = new Choice(new String[]{"Earth", "Mars", "Venus", "Jupiter"}, 1);
        }

        public Question()
        {
            JFrame window = new CreateQuestionBank();
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

        class CreateQuestionBank extends JFrame implements ActionListener
        {
            private int currentQuestionIndex = 0;

            JPanel Back,
                    Title,
                    down;
            Font myFont;
            JLabel Title_label,
                    prompt_label,
                    grade_label,
                    choice_label,
                    c1,
                    c2,
                    c3,
                    c4,
                    index_label,
                    currentQuestionLabel;
            JButton right_b,
                    left_b,
            submit,
                    Back_button;
            Icon icon_r,
                    icon_l;
            JTextField Q_t,
                    G_t,
                    c1_t,
                    c2_t,
                    c3_t,
                    c4_t,
                    I_t;
            Border brdr;

            {
                myFont = new Font(Font.MONOSPACED, Font.BOLD, 30);
                Title_label = new JLabel("Question Creator");
                Back = new JPanel();
                Title = new JPanel();
                brdr = BorderFactory.createLineBorder(new Color(222, 184, 150));
                down = new JPanel();
                currentQuestionLabel = new JLabel("Question: " + (currentQuestionIndex + 1));
                prompt_label = new JLabel("Enter prompt:");
                grade_label = new JLabel("Enter grade: ");
                choice_label = new JLabel("Enter choices: ");
                index_label = new JLabel("Enter the index of right answer: ");
                Back_button = new JButton("Back");
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
                submit = new JButton("Submit");
            }

            public CreateQuestionBank()
            {
                //button
                Back_button.setBounds(20, 470, 100, 30);
                Back_button.setBackground(new Color(222, 184, 150));
                Back_button.addActionListener(this);
                add(Back_button);

                submit.setFocusable(false);
                submit.setBounds(430, 470, 100, 30);
                submit.setBorder(BorderFactory.createEtchedBorder());
                submit.setBackground(new Color(222, 184, 150));
                add(submit);
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
                currentQuestionLabel.setBounds(5, 60, 200, 30);
                prompt_label.setBounds(5, 80, 100, 30);
                grade_label.setBounds(5, 150, 100, 30);
                choice_label.setBounds(5, 180, 100, 30);
                index_label.setBounds(5, 380, 200, 30);
                c1.setBounds(5, 220, 10, 10);
                c2.setBounds(5, 260, 10, 10);
                c3.setBounds(5, 300, 10, 10);
                c4.setBounds(5, 340, 10, 10);
                add(currentQuestionLabel);
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

                setTitle("Create Question Bank");
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
                    currentQuestionIndex++;
                    if (currentQuestionIndex > 0)
                    {
                        left_b.setEnabled(true);
                    }
                    currentQuestionLabel.setText("Question: " + (currentQuestionIndex + 1));

                }
                if (e.getSource() == left_b)
                {
                    currentQuestionIndex--;
                    if (currentQuestionIndex == 0)
                    {
                        left_b.setEnabled(false);
                    }
                    currentQuestionLabel.setText("Question: " + (currentQuestionIndex + 1));

                }
                if (e.getSource() == Back_button){
                        setVisible(false);
                        JFrame window = new CreateQuizWindow();
                }
                //TODO set String quizID to add to the quizzes
            }
        }

        public class Choice implements Serializable
        {
            private int nChoices;
            private String[] choices;
            private int answerKeyIndex;

            private Choice()
            {
                this.nChoices = 4;
                choices = new String[4];
            }

            public Choice(String[] choices, int answerKeyIndex)
            {
                this.choices = choices;
                this.answerKeyIndex = answerKeyIndex;
            }

            public String[] getChoices()
            {
                return choices;
            }

            public int getAnswerKeyIndex()
            {
                return answerKeyIndex;
            }

            public void setAnswerKeyIndex(int answerKeyIndex)
            {
                this.answerKeyIndex = answerKeyIndex;
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

    class CreateQuizWindow extends JFrame implements ActionListener
    {
        JPanel title_panel, background;
        Font myFont;
        JLabel Title, label1, label2, label3, label4;
        JButton button, Back_button;
        JTextField quizTitle_text, quizID_text, duration_text, nQuestions_text;
        Border brdr;

        {
            title_panel = new JPanel();
            brdr = BorderFactory.createLineBorder(new Color(222, 184, 150));
            background = new JPanel();
            myFont = new Font(Font.MONOSPACED, Font.BOLD, 30);
            Title = new JLabel("Create Quiz");
            button = new JButton("Create Questions");
            quizTitle_text = new JTextField();
            quizID_text = new JTextField();
            duration_text = new JTextField();
            nQuestions_text = new JTextField();
            Back_button = new JButton("leave");

        }

        public CreateQuizWindow()
        {
            //button
            Back_button.setBounds(20, 470, 100, 30);
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
            //submit button
            button.setFont(new Font("Sans Serif", Font.PLAIN, 25));
            button.setFocusable(false);
            button.setBounds(70 , 260, 400, 40);
            button.addActionListener(e -> System.out.println("Saved"));
            button.setBorder(BorderFactory.createEtchedBorder());
            button.setBackground(new Color(222, 184, 150));
            add(button);
            //labels
            label1 = new JLabel("Title: ");
            label1.setBounds(70, 100, 60, 30);
            label2 = new JLabel("Quiz Code: ");
            label2.setBounds(70, 140, 1000, 30);
            label3 = new JLabel("Duration: ");
            label3.setBounds(70, 180, 60, 30);
            label4 = new JLabel("Number of Questions: ");
            label4.setBounds(70, 220, 140, 30);
            add(label1);
            add(label2);
            add(label3);
            add(label4);
            //text fields
            quizTitle_text.setBounds(270, 100, 200, 30);
            quizID_text.setBounds(270, 140, 200, 30);
            duration_text.setBounds(270, 180, 200, 30);
            nQuestions_text.setBounds(270, 220, 200, 30);
            add(quizTitle_text);
            add(quizID_text);
            add(duration_text);
            add(nQuestions_text);
            //Background
            background.setBackground(Color.WHITE);
            add(background, BorderLayout.CENTER);

            setTitle("Create New Quiz");
            setSize(550, 550);
            setLocationRelativeTo(null); // to not have it open at the corner
            setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            setVisible(true);
        }

        @Override
        public void actionPerformed(ActionEvent e)
        {
            if (e.getSource() == button)
            {
                quizTitle = quizTitle_text.getText();

                nQuestions = Integer.parseInt(nQuestions_text.getText());
                setVisible(false);
                Question x = new Question();
            }
            if (e.getSource() == Back_button){
                int reply = JOptionPane.showConfirmDialog(null, "Are you sure you want to close?", "Close?",  JOptionPane.YES_NO_OPTION);
                if (reply == JOptionPane.YES_OPTION)
                {
                    setVisible(false);
                    //JFrame window = new ;
                }
            }
        }
    }
}