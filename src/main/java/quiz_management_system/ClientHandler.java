package quiz_management_system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler extends JFrame implements Runnable , ActionListener
{

    public static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String clientUsername;

    JTextArea l1 = new JTextArea(100, 50);
    JButton send = new JButton("send");
    JPanel chat_panel = new JPanel();
    JTextField t1 = new JTextField();
    JButton actionCloseChat = new JButton("x");
    public ClientHandler(){
        send.setBounds(420, 480, 100, 30);
        send.setBackground(new Color(222, 184, 150));
        chat_panel.setBackground(new Color(239, 222, 205));
        chat_panel.setLayout(null);
        t1.setBounds(5, 480, 400, 30);
        t1.addActionListener(this);
        chat_panel.setBounds(1, 1, 550, 550);
        l1.setLineWrap(true);
        l1.setEditable(false);
        l1.setBounds(5, 35, 400, 440);
        actionCloseChat.setBounds(1, 1, 60, 30);
        actionCloseChat.setBackground(new Color(239, 222, 205));
        actionCloseChat.addActionListener(this);
        chat_panel.add(actionCloseChat);
        chat_panel.add(l1);
        chat_panel.add(t1);
        chat_panel.add(send);
        add(chat_panel);

        setTitle("Chat");
        setSize(550, 550);
        setResizable(false);
        setLocationRelativeTo(null); // to not have it open at the corner
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public ClientHandler(Socket socket)
    {
        try
        {
            this.socket = socket;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.clientUsername = bufferedReader.readLine();
            clientHandlers.add(this);
            broadcastMessage(clientUsername + " has entered the chat!");
        } catch (IOException e)
        {
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }


    @Override
    public void run()
    {
        String messageFromClient = t1.getText();
        while (socket.isConnected())
        {
            try
            {
                messageFromClient = bufferedReader.readLine();
                l1.append(messageFromClient);
                broadcastMessage(messageFromClient);
            } catch (IOException e)
            {
                closeEverything(socket, bufferedReader, bufferedWriter);
                break;
            }
        }
    }

    public void broadcastMessage(String messageToSend)
    {
        for (ClientHandler clientHandler : clientHandlers)
        {
            try
            {
                if (!clientHandler.clientUsername.equals(clientUsername))
                {
                    clientHandler.bufferedWriter.write(messageToSend);
                    clientHandler.bufferedWriter.newLine();
                    clientHandler.bufferedWriter.flush();
                }
            } catch (IOException e)
            {
                closeEverything(socket, bufferedReader, bufferedWriter);
            }
        }
    }

    public void removeClientHandler()
    {
        clientHandlers.remove(this);
        broadcastMessage(clientUsername + "has left");
    }

    public void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter)
    {
        removeClientHandler();
        try
        {
            if (bufferedReader != null)
            {
                bufferedReader.close();
            }
            if (bufferedWriter != null)
            {
                bufferedWriter.close();
            }
            if (socket != null)
            {
                socket.close();
            }
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == actionCloseChat){
            setVisible(false);
            Quiz_Management_System.getActiveUser().listMenu();
        }
        if(e.getSource() == send){
            run();
        }
    }
}