package com.OUS;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.text.*;

public class DayOfTheWeekService implements Service {
    JLabel outputLabel;
    JComboBox month;
    JTextField day;
    JTextField year;

    public JPanel getGuiPanel() {
        JPanel panel = new JPanel();
        JButton button = new JButton("Do it!");
        button.addActionListener(new DoItListener());
        outputLabel = new JLabel("data appears here");
        DateFormatSymbols dataStuff = new DateFormatSymbols();
        month = new JComboBox<String>(dataStuff.getMonths());
        day = new JTextField(8);
        year = new JTextField(8);
        JPanel inputPanel = new JPanel(new GridLayout(3,2));
        inputPanel.add(new JLabel("Month"));
        inputPanel.add(month);
        inputPanel.add(new JLabel("Day"));
        inputPanel.add(day);
        inputPanel.add(new JLabel("Year"));
        inputPanel.add(year);
        panel.add(inputPanel);
        panel.add(button);
        panel.add(outputLabel);
        return panel;
    }

    public class DoItListener implements ActionListener {
        public void actionPerformed(ActionEvent ev) {
            int monthNum = month.getSelectedIndex();
            System.out.println("Month" + monthNum);
            int dayNum = Integer.parseInt(day.getText());
            System.out.println("day" + dayNum);
            int yearNum = Integer.parseInt(year.getText());
            System.out.println("year" + yearNum);

            Calendar calendar = new GregorianCalendar(yearNum, monthNum, dayNum);
            System.out.println(calendar.getTime());
            Date date = calendar.getTime();
            String dayOfWeek = (new SimpleDateFormat("EEEE")).format(date);
            outputLabel.setText(dayOfWeek);
        }
    }

    public static void main(String[] args) {
        DayOfTheWeekService d = new DayOfTheWeekService();

        JFrame frame = new JFrame("test");
        frame.add(d.getGuiPanel());
        frame.setSize(500,500);
        frame.setVisible(true);
    }
    
}
