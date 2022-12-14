package com.OUS;

import com.OUS.Service;
import javax.swing.*;
import javax.swing.plaf.DimensionUIResource;
import org.w3c.dom.css.RGBColor;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.ImageObserver;
import java.io.*;
import java.util.*;

public class DiceService implements Service {
    JLabel label;
    JComboBox numOfDice;
    MyDrawPanel drawPanel;
    ArrayList<myRectangle> rectList = new ArrayList<myRectangle>();
    JPanel mainPanel;

    public JPanel getGuiPanel(){
        mainPanel = new JPanel();
        JPanel panel = new JPanel();
        JButton button = new JButton("Roll 'em ");
        String[] choices = {"1","2","3","4","5","7","8","9"};
        mainPanel.setLayout(new BorderLayout());
        drawPanel = new MyDrawPanel();
        numOfDice = new JComboBox<String>(choices);
        label = new JLabel("dice values here");
        button.addActionListener(new RollEmListener());
        panel.add(numOfDice);
        panel.add(button);
        panel.add(label);
        mainPanel.add(BorderLayout.NORTH, panel);
        mainPanel.add(BorderLayout.CENTER, drawPanel);
        return mainPanel;
    }

    public class RollEmListener implements ActionListener {
        public void actionPerformed(ActionEvent ev) {
            //throw the dice
            String diceOutput = "";
            String selection = (String) numOfDice.getSelectedItem();
            int numOfDiceToRoll = Integer.parseInt(selection);
            rectList.removeAll(rectList);
            drawPanel.repaint();

            int x = 0, y = 15, dx = 10, w = 100, h = 100, dw = 0, dh = 0;
            for (int i = 0; i < numOfDiceToRoll; i++) {
                int r = (int) ((Math.random() * 6) + 1);
                if (i == 0) {
                    x = 5;
                }else if ((x+205+dx) > drawPanel.getWidth()) {
                    //set up coordinates move to new line
                    x = 5;
                    y += 110;
                } else {
                    //calculate coordinates
                    x += 100 + dx;
                }
                Dice dice = new Dice(x, y, w, h, r);
                rectList.add(dice);
                diceOutput += (" " + r);
            }
            label.setText(diceOutput);
            drawPanel.repaint();
        }
    }

    public class MyDrawPanel extends JPanel {
        public void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setColor(getBackground());
            g2d.fillRect(0,0, this.getWidth(), this.getHeight());
            for (int i = 0; i < rectList.size(); i++) {
                rectList.get(i).draw(g2d);
            }
        } 
    }

    public interface GraphShape{
        public int getWidth();
        public int getHeight();
        public void draw(Graphics g);
    }

    public class myRectangle implements GraphShape{
        int x;
        int y;
        int w;
        int h;
        myRectangle(int x, int y, int w, int h) {
            this.x = x;
            this.y = y;
            this.w = w;
            this.h = h;
        }
        public int getWidth() {
            return w;
        }
        public int getHeight() {
            return h;
        }
        public void draw(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setFont(new Font("SabsSerif", Font.PLAIN, 10));
            g2.setStroke(new BasicStroke(2.0f));
            g2.setColor(Color.YELLOW);
            g2.fill(new Rectangle(x, y, w, h));
            g2.setColor(Color.black);
            g2.draw(new Rectangle(x, y, w, h));
        }
    }

    public class Dice extends myRectangle {
        int num;
        Dice(int x, int y, int w, int h, int num) {
            super(x,y,w,h);
            this.num = num;
        }

        @Override
        public void draw(Graphics g) {
            super.draw(g);
            g.setColor(Color.GRAY);
            if (num > 1)  // upper left dot
                g.fillOval(x+10, y+10, 20, 20);
            if (num > 3)  // upper right dot
                g.fillOval(x+70, y+10, 20, 20);
            if (num == 6) // middle left dot
                g.fillOval(x+10, y+38, 20, 20);
            if (num % 2 == 1) // middle dot (for odd-numbered)
                g.fillOval(x+38, y+38, 20, 20);
            if (num == 6) // middle right dot
                g.fillOval(x+70, y+38, 20, 20);
            if (num > 3)  // bottom left dot
                g.fillOval(x+10, y+70, 20, 20);
            if (num > 1)  // bottom right dot
                g.fillOval(x+70, y+70, 20,20);
        }
    }
}
