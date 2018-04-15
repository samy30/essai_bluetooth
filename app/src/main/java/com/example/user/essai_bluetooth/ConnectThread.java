package com.example.user.essai_bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;


import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

/**
 * Created by user on 14/04/2018.
 */

public class ConnectThread extends Thread {

    private final BluetoothSocket mmSocket;
    private final BluetoothDevice mmDevice;
    private BluetoothAdapter mmAdapter ;
    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");

    public ConnectThread(BluetoothDevice device,BluetoothAdapter adapter) {
        BluetoothSocket tmp =null ;

        mmDevice = device;
        mmAdapter=adapter;
        try {
            tmp = mmDevice.createRfcommSocketToServiceRecord(MY_UUID);
        } catch (IOException e) { }
        mmSocket=tmp;
    }

    public void run() {
        mmAdapter.cancelDiscovery();
        try {
            mmSocket.connect();
        } catch (IOException connectException) {
            try {
                mmSocket.close();
            } catch (IOException closeException) { }
            return;
        }
        envoyermessage();
    }

    public void cancel() {
        try {
            mmSocket.close();
        } catch (IOException e) { }
    }

    public void envoyermessage(){
        OutputStream os;
        try{os=mmSocket.getOutputStream();
            DataOutputStream dos = new DataOutputStream(os);
            dos.writeUTF("Bonjour");}
        catch(IOException e){System.out.println("erreur lors de l envoi");};
    }
}
