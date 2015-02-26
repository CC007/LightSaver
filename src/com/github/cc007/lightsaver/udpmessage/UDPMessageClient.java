/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.cc007.lightsaver.udpmessage;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Random;

/**
 *
 * @author Rik
 */
public abstract class UDPMessageClient extends Thread {

    private static final String SERVER_ADDRESS = "localhost";

    protected UDPMessage m;
    protected boolean send;

    private byte[] mBuffer;
    private DatagramSocket s = null;

    public UDPMessageClient() {
        this.send = true;
    }

    public UDPMessageClient(String name) {
        this();
        setName(name);
    }

    protected void doBefore() {
    }

    protected abstract UDPMessage createMessage();

    protected abstract byte[] writeToBuffer();

    protected abstract int getMessageSize();

    protected void doAfter() {
    }

    @Override
    public void run() {
        while (true) {
            try {
                // things to be done before creating the message
                doBefore();
                
                if (send) {
                    // create the message
                    m = createMessage();

                    //write to buffer
                    mBuffer = writeToBuffer();

                    // connection part
                    s = new DatagramSocket();
                    InetAddress lightDetectorServer = InetAddress.getByName(SERVER_ADDRESS);

                    // send message
                    DatagramPacket ldmPacket = new DatagramPacket(mBuffer, getMessageSize(), lightDetectorServer, UDPMessageServer.SERVER_PORT);
                    s.send(ldmPacket);
                }
                
                // things to be done after sending the message
                doAfter();
            } catch (SocketException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
