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

    ArrayList<Quiz> createdQuizzes;

    public Teacher(String username, String password, Access teacher)
    {
        super(username, password, teacher);
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

        //TODO set String quizID to add to the quizzes

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
        //background
        JPanel Back = new JPanel();

        JList<String> quizList;
        JScrollPane scrollPane;
        JTextField qID_review = new JTextField(15);

        public TeacherWindow()
        {
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

            Back.setBackground(Color.WHITE);
            add(Back, BorderLayout.CENTER);
        }

        @Override
        public void actionPerformed(ActionEvent e)
        {
            if (e.getSource() == actionReview)
            {

                JFrame window;
                try
                {
                    window = new ReviewQuizGrades(Quiz.searchByID(qID_review.getText()));
                } catch (NullPointerException nullPointerException)
                {
                    JOptionPane.showMessageDialog(null, "No Quiz Found!", "Warning", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                setVisible(false);
                window.setTitle("Review Quiz Grades");
                window.setSize(400, 500);
                window.setLocationRelativeTo(null); // to not have it open at the corner
                window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                window.setVisible(true);
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
    static class ReviewQuizGrades extends JFrame
    {
        JPanel title_panel = new JPanel(),
                background = new JPanel();
        Font myFont = new Font(Font.MONOSPACED, Font.BOLD, 30);
        JLabel Title = new JLabel("Review Quiz Grades "),
                BestGrade_label = new JLabel("Best Grade : "),
                WorstGrade_label = new JLabel("Worst Grade : "),
                AverageGrade_label = new JLabel("Average Grade : "),
                label4 = new JLabel(" "),
                label5 = new JLabel(" "),
                label6 = new JLabel(" ");
        Border brdr = BorderFactory.createLineBorder(new Color(222, 184, 150));
        String[] columnNames = {"Student", "Grade"};
        Object[][] data = {{"Kathy", 5}, {"John", 4}};
        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);

        public ReviewQuizGrades(Quiz newQuiz)
        {
            Title.setFont(myFont);
            title_panel.add(Title);
            add(title_panel, BorderLayout.PAGE_START);
            Title.setForeground(Color.BLACK);
            title_panel.setBackground(Color.white);
            title_panel.setBorder(brdr);

            //labels
            Title.setBounds(10, 100, 60, 30);
            BestGrade_label.setBounds(10, 140, 100, 30);
            WorstGrade_label.setBounds(10, 180, 200, 30);
            AverageGrade_label.setBounds(10, 220, 200, 30);
            label5.setBounds(10, 260, 60, 30);
            label6.setBounds(10, 300, 60, 30);
            add(BestGrade_label);
            add(WorstGrade_label);
            add(AverageGrade_label);
            add(label4);
            add(label5);
            add(label6);

            //Background
            background.setBackground(Color.WHITE);
            add(background, BorderLayout.CENTER);

            //Table
            constructData(newQuiz);
            table.setFillsViewportHeight(true);
            table.setBounds(100, 300, 300, 500);
            table.setBackground(new Color(222, 184, 150));
            add(scrollPane);
        }

        public void constructData(Quiz newQuiz)
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
    }
}
