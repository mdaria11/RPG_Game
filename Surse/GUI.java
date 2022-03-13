package com.company;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI implements ActionListener
{
    private JTextField nameinput;
    private JTextField passwordinput;
    private JLabel success;

    public void start()
    {
        JPanel panel= new JPanel();
        JFrame frame= new JFrame();
        frame.setSize(350, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Login Page");
        frame.add(panel);

        panel.setLayout(null);
        JLabel welcometext= new JLabel("Welcome!");
        welcometext.setBounds(130, 20, 80, 25);
        panel.add(welcometext);

        JLabel nametext= new JLabel("Username");
        nametext.setBounds(50, 50, 80, 25);
        panel.add(nametext);
        nameinput= new JTextField(20);
        nameinput.setBounds(120, 50, 165, 25);
        panel.add(nameinput);

        JLabel passwordtext= new JLabel("Password");
        passwordtext.setBounds(50, 80, 80, 25);
        panel.add(passwordtext);
        passwordinput= new JTextField(20);
        passwordinput.setBounds(120, 80, 165, 25);
        panel.add(passwordinput);

        JButton loginbutton= new JButton("Login");
        loginbutton. setBounds(120, 110, 80, 25);
        loginbutton.addActionListener(this);
        panel.add(loginbutton);
        success= new JLabel("");
        success.setBounds(120, 140, 130, 25);
        panel.add(success);

        frame.setVisible(true);
    }

    public void actionPerformed(ActionEvent e)
    {
        String name= nameinput.getText();
        String password=passwordinput.getText();

        for(Account account : Game.getGame().gameaccounts)
        {
            if(account.getName().equals(name))
            {
                if(account.getCredentials().getPassword().equals(password))
                {
                    success.setText("Success!");
                    return;
                }
                else
                {
                    success.setText("Incorrect password!");
                    return;
                }
            }
        }

        success.setText("Account not found!");
    }
}
