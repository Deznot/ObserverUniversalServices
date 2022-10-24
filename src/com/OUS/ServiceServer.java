package com.OUS;
import java.rmi.*;

public interface ServiceServer extends Remote {
    Object[] getServicesList() throws RemoteException;
    Service getService(Object serviceKey) throws RemoteException;
}