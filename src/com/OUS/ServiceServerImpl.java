package com.OUS;

import java.rmi.*;
import java.rmi.server.*;
import java.util.*;
import com.OUS.ServiceServer;
import java.rmi.registry.*;

public class ServiceServerImpl extends UnicastRemoteObject implements ServiceServer {
    HashMap<String,Service> serviceList;
    public ServiceServerImpl() throws RemoteException {
        setUpServices();
    }
    private void setUpServices() {
        serviceList = new HashMap<String,Service>();
        serviceList.put("Dice Rolling Service", new DiceService());
        serviceList.put("Day of the Week Service", new DayOfTheWeekService());
        serviceList.put("Visual Mysic Service", new MiniMusicService());
    }

    public Object[] getServicesList() {
        System.out.println("in remote");
        return serviceList.keySet().toArray();
    }

    public Service getService(Object serviceKey) throws RemoteException {
        Service theSerivce = (Service) serviceList.get(serviceKey);
        return theSerivce;
    }

    public static void main(String[] args) {
        final String UNIQUE_BINDING_NAME = "ServiceServer";
        try {
            Registry registry = LocateRegistry.createRegistry(8010);
            registry.rebind(UNIQUE_BINDING_NAME, new ServiceServerImpl());
            System.out.println("export and binding of objects has been completed");
        }catch(Exception ex) {
            System.out.println("ServiceServer Error...");
            ex.printStackTrace();
        }
    }
}

