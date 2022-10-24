package com.OUS;

import java.rmi.*;
import java.rmi.server.*;
import java.util.*;

import javax.xml.transform.SourceLocator;

import com.OUS.ServiceServer;

public class ServiceServerImpl extends UnicastRemoteObject implements ServiceServer {
    HashMap servicesList;
    public ServiceServerImpl() throws RemoteException {
        setUpServices();
    }
    private void setUpServices() {
        servicesList = new HashMap<String,Service>();
        serviceList.put("Dice Rolling Service", new DiceService());
        serviceList.put("Day of the Week Service", new DatOfTheWeekService());
        serviceList.put("Visual Mysic Service", new MiniMusicService());
    }

    public Object[] getServicesList() {
        System.out.println("in remote");
        return serviceList.keySet().toArray();
    }

    public Service getService(Object serviceKey) throws RemoteException {
        Service theSerivce = (Service) servicesList.get(serviceKey);
        return theSerivce;
    }

    public static void main(String[] args) {
        try {
            Naming.rebind("ServiceServer", new ServiceServerImpl());
        }catch(Exception ex) {
            System.out.println(ex.printStackTrace());
        }
    }
}
