package com.github.cc007.lightsaver.message.tcp;

import com.github.cc007.lightsaver.detector.door.DoorDetectorMessage;
import com.github.cc007.lightsaver.message.Message;
import com.github.cc007.lightsaver.message.MessageTypes;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TCPMessageServer {

    public static int SERVER_PORT = 7374;

    public static void main(String[] args) {
        TCPMessageServer server = new TCPMessageServer();
        server.listen();
    }

    public void listen() {
        try {
            ServerSocket serverSocket = new ServerSocket(SERVER_PORT);
            System.out.println("Server is listening...");
            while (true) {
                Socket clientSocket = serverSocket.accept();
                //start a thread that waits for a client message coming in
                ConnectionThread ct = new ConnectionThread(clientSocket);
                ct.start();
            }
        } catch (IOException ex) {
            Logger.getLogger(TCPMessageServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    class ConnectionThread extends Thread {

        private DataInputStream in;
        private DataOutputStream out;
        private Socket clientSocket;

        private Message m;

        public ConnectionThread(Socket aSocket) {
            try {
                clientSocket = aSocket;
                in = new DataInputStream(clientSocket.getInputStream());
                out = new DataOutputStream(clientSocket.getOutputStream());
            } catch (IOException ex) {
                Logger.getLogger(TCPMessageServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        @Override
        public void run() {
            try {
                int type = in.readInt();
                switch (type) {
                    case MessageTypes.DOOR_DETECTOR_MSG:
                        m = new DoorDetectorMessage(type, in.readInt(), in.readBoolean());
                        System.out.println("The state of door " + ((DoorDetectorMessage)m).getClientId() + " changed its state to: " + (((DoorDetectorMessage)m).isOpen()?"open":"closed"));
                        break;
                    default:
                        System.err.println("Unknown message type found: " + type);
                }
            } catch (IOException ex) {
                Logger.getLogger(TCPMessageServer.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    clientSocket.close();
                } catch (IOException ex) {
                    Logger.getLogger(TCPMessageServer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
