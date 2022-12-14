package com.OUS;

import com.OUS.Service;
import com.OUS.ServiceServer;
import java.awt.*;
import javax.swing.*;
import java.rmi.*;
import java.awt.event.*;
import java.rmi.registry.*;


public class ServiceBrowser {
    JPanel mainPanel;
    JComboBox serviceList;
    ServiceServer server;

    public void buildGUI() {
        JFrame frame = new JFrame("RMI Browser");
        frame.pack();
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        Object[] services = getServiceList();

        serviceList = new JComboBox<Object>(services);
        frame.getContentPane().add(BorderLayout.NORTH, serviceList);
        frame.getContentPane().add(BorderLayout.CENTER, mainPanel);
        //frame.pack();
        serviceList.addActionListener(new MyListListener());
        frame.setSize(550,550);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    void loadService(Object serviceSelection) {
        try {
            Service svc = server.getService(serviceSelection);
            mainPanel.removeAll();
            mainPanel.add(svc.getGuiPanel());
            mainPanel.revalidate();
            mainPanel.repaint();
        }catch(Exception ex) {
            System.out.println("loadService error");
            ex.printStackTrace();
        }
    }

    Object[] getServiceList() {
        Object obj = null;
        Object[] services = null;
        try {
            Registry registry = LocateRegistry.getRegistry("127.0.0.1", 8010);
            //Get the reference of exported object from RMI Registry
            obj = registry.lookup("ServiceServer");
        }catch(Exception ex) {
            System.out.println("getService error get obj");
            ex.printStackTrace();
        }
        server = (ServiceServer) obj;

        try{
            services = server.getServicesList();
        }catch(Exception ex){
            System.out.println("getService error get services");
            ex.printStackTrace();
        }
        return services;
    }

    class MyListListener implements ActionListener {
        public void actionPerformed(ActionEvent ev) {
            Object selection = serviceList.getSelectedItem();
            loadService(selection);
        }
    }

    public static void main(String[] args) {
        new ServiceBrowser().buildGUI();
    }
}
