package quiz_management_system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;

public class Client extends JFrame implements ActionListener, Runnable
{
    String name;
    JTextField name_text = new JTextField(20),
            message_text = new JTextField(30);
    JTextArea chat = new JTextArea();
    JPanel name_area = new JPanel(new FlowLayout(FlowLayout.LEFT)),
            message_area = new JPanel(new FlowLayout(FlowLayout.LEFT));
    JButton setName = new JButton("Set Name"),
            send = new JButton("Send");

    Socket clientSocket;
    DataInputStream dataIn;
    DataOutputStream dataOut;

    public Client()
    {
        setLayout(new GridLayout(3, 1));
        try
        {
            this.clientSocket = new Socket("10.14.3.45", 6969);
            this.dataIn = new DataInputStream(clientSocket.getInputStream());
            this.dataOut = new DataOutputStream(clientSocket.getOutputStream());
        } catch (IOException e)
        {
            e.printStackTrace();
        }

        name_area.add(name_text);
        name_area.add(setName);
        message_area.add(message_text);
        message_area.add(send);
        add(name_area);
        add(chat);
        add(message_area);

        send.addActionListener(this);
        setName.addActionListener(this);

        setTitle("Client");
        setLocationRelativeTo(null);
        setSize(500, 500);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == send)
        {
            try
            {
                dataOut.writeUTF(name + " wrote " + message_text.getText() + "\n");
                dataOut.flush();
            } catch (IOException ex)
            {
                ex.printStackTrace();
                return;
            }
            message_text.setText("");
        }
        else if (e.getSource() == setName)
        {
            name = name_text.getText();
        }
    }

    public static void main(String[] args)
    {
        JFrame window = new Client();


    }

    @Override
    public void run()
    {
        while (true)
        {
            try
            {
                //dataIn = new DataInputStream(clientSocket.getInputStream());
                BufferedReader bf = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                String newString = bf.readLine();
                chat.append(newString + "\n");
                System.out.println(newString);
            } catch (IOException e)
            {
                e.printStackTrace();
            }

        }
    }
}
