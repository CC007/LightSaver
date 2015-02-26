package com.github.cc007.lightsaver.udpmessage;

import com.github.cc007.lightsaver.lightdetector.LightDetectorMessage;
import com.github.cc007.lightsaver.passagedetector.PassageDetectorMessage;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.nio.ByteBuffer;

public class UDPMessageServer {

    public static final int SERVER_PORT = 7376;

    private static DatagramSocket s = null;
    private static UDPMessage m;

    public static void main(String[] args) {
        try {
            // connection part
            s = new DatagramSocket(SERVER_PORT);

            //allocate the buffer array
            while (true) {
                // receive message
                byte[] mBuffer = new byte[UDPMessageTypes.MAX_MSG_SIZE];
                DatagramPacket mPacket = new DatagramPacket(mBuffer, mBuffer.length);
                s.receive(mPacket);

                // construct message object from message
                switch (ByteBuffer.wrap(mBuffer).getInt(0)) {
                    case UDPMessageTypes.LIGHT_DETECTOR_MSG:
                        // it's a Light detector value message
                        m = new LightDetectorMessage(UDPMessageTypes.LIGHT_DETECTOR_MSG, ByteBuffer.wrap(mBuffer).getInt(4), ByteBuffer.wrap(mBuffer).getInt(8));

                        //print the info
                        System.out.println("Value from client " + ((LightDetectorMessage)m).getClientId() + ": " + ((LightDetectorMessage)m).getValue());
                        break;
                    case UDPMessageTypes.PASSAGE_DETECTOR_MSG:
                        // it's a Passage detector value message
                        m = new PassageDetectorMessage(UDPMessageTypes.LIGHT_DETECTOR_MSG, ByteBuffer.wrap(mBuffer).getInt(4));

                        //print the info
                        System.out.println("Detected passage from client " + ((PassageDetectorMessage)m).getClientId());
                        break;
                    default:
                        System.err.println("Unknown message type found: " + ByteBuffer.wrap(mBuffer).getInt(0));
                }

            }
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (s != null) {
                s.close();
            }
        }
    }

}
