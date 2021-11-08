package quiz_management_system;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;
import java.util.Scanner;

public class Quiz
{

    private int quizID;
    private String quizTitle;
    private Teacher creator;
    private int nAttempts, nQuestions;
//    private Calendar openTime, duration; //still uncertain of the data type.
    private ArrayList<Question> questionBank;
    private int qBankSize;

    public String getQuizTitle()
    {
        return quizTitle;
    }

    public void setQuizTitle(String quizTitle)
    {
        this.quizTitle = quizTitle;
    }

    public int getnAttempts()
    {
        return nAttempts;
    }

    public void setnAttempts(int nAttempts)
    {
        this.nAttempts = nAttempts;
    }

    public int getnQuestions()
    {
        return nQuestions;
    }

    public void setnQuestions(int nQuestions)
    {
        this.nQuestions = nQuestions;
    }

    public Teacher getCreator()
    {
        return creator;
    }

    public void setCreator(Teacher creator)
    {
        this.creator = creator;
    }

    public void displayQuizProperties()
    {
        System.out.println("-----------Displaying quiz properties------------");
        System.out.println("Quiz ID: " + quizID);
        System.out.println("Quiz Title: " + quizTitle);
        System.out.println("Number of questions: " + nQuestions);
        System.out.println("Number of attempts: " + nAttempts);
        System.out.println("Size of question bank: " + qBankSize);
    }

    @Override
    public String toString()
    {
        return "Quiz{" + "quizID=" + quizID + ", quizTitle=" + quizTitle + ", nAttempts=" + nAttempts + ", nQuestions=" + nQuestions + ", qBankSize=" + qBankSize + '}';
    }

    public void createQuiz(Teacher author)
    {
        Scanner sc = new Scanner(System.in);
        creator = author;

        System.out.println("-----------Creating a new quiz-------------------");
        System.out.println("Enter quiz title: ");
        quizTitle = sc.nextLine();

        try
        {
            System.out.println("Enter number of questions: ");
            nQuestions = sc.nextInt();

            System.out.println("Enter number of attempts: ");
            nAttempts = sc.nextInt();

            while (qBankSize < nQuestions)
            {
                System.out.println("Enter size of question bank: ");
                qBankSize = sc.nextInt();
                if (qBankSize < nQuestions)
                {
                    System.out.println("Error: Number of question bank must be equal or more than number of questions");
                } else
                {
                    continue;
                }
            }
        } catch (Exception e)
        {
            System.out.println("Error: Invailed input");
            return;
        }
        createQuestionBank();

        System.out.println("Quiz created sucessfully.");
    }

    public void createQuestionBank()
    {
        questionBank = new ArrayList(qBankSize);

        System.out.println("-----------Question Bank Creator-----------------");
        for (Question tempQ : questionBank)
        {
            tempQ.createQuestion();
        }
        System.out.println("Question Bank created successfully.");
    }

    public Question[] generateQuizModel()
    {
        Question[] newModel;
        newModel = new Question[nQuestions];

        Random generator = new Random();
        int randIndex;
        for (int i = 0; i < nQuestions; i++)
        {
            randIndex = generator.nextInt(questionBank.size());

            newModel[i] = questionBank.get(randIndex);
        }
        return newModel;
    }

    public class Question
    {

        private int questionID;
        private String prompt;
        private double grade;
        protected Choice mcq;

        public void createQuestion()
        {
            Scanner sc = new Scanner(System.in);
            System.out.println("-----------Creating a new question-----------");
            System.out.println("Enter prompt: ");
            prompt = sc.next();

            try
            {
                sc = new Scanner(System.in);
                System.out.println("Enter grade: ");
                grade = sc.nextDouble();
            } catch (Exception e)
            {
                System.out.println("Error: Invailed input");
                return;
            }

            mcq = new Choice();
            mcq.createMCQ();

            System.out.println("Question added successfully.");

        }

        public int getQuestionID()
        {
            return questionID;
        }

        public String getPrompt()
        {
            return prompt;
        }

        public double getGrade()
        {
            return grade;
        }

        public Choice getMcq()
        {
            return mcq;
        }

        public void displayQuestion()
        {
            System.out.println(prompt);
            for (int i = 0; i < mcq.nChoices; i++)
            {
                System.out.println((i + 1) + ". " + mcq.choices[i]);
            }
        }

        public boolean checkAnswer(short inAnswer)
        {
            boolean result = false;
            if (inAnswer == mcq.getAnswerKeyIndex())
            {
                result = true;
            }
            return result;
        }
    }

    private class Choice
    {

        private int nChoices;
        private String[] choices;
        private int answerKeyIndex;

        private Choice()
        {
            this.nChoices = 0;
            choices = new String[2];
        }

        public int getnChoices()
        {
            return nChoices;
        }

        public int getAnswerKeyIndex()
        {
            return answerKeyIndex;
        }

        public void createMCQ()
        {

            Scanner sc = new Scanner(System.in);

            System.out.println("-----------Creating MCQ------------------");
            try
            {
                System.out.println("Enter number of choices: ");
                nChoices = sc.nextShort();

            } catch (Exception e)
            {
                System.out.println("Error: Invailed input");
                return;
            }
            choices = new String[nChoices];

            for (int i = 0; i < nChoices; i++)
            {
                System.out.println("Enter choice " + i + ": ");
                choices[i] = sc.next();
            }

            answerKeyIndex = nChoices;
            try
            {
                while (answerKeyIndex > nChoices - 1)
                {
                    System.out.println("Enter the index of right answer (count starts at 0): ");
                    answerKeyIndex = sc.nextShort();
                    if (answerKeyIndex > nChoices - 1)
                    {
                        System.out.println("Error: The number of choice doesn't exist");
                    } else
                    {
                        continue;
                    }
                }
            } catch (Exception e)
            {
                System.out.println("Error: Invailed input");
                return;
            }
            System.out.println("Choices added successfully.");
        }

        public boolean checkAnswer(short inAnswer)
        {
            boolean result = false;
            if (inAnswer == answerKeyIndex)
            {
                result = true;
            }
            return result;
        }
    }
}