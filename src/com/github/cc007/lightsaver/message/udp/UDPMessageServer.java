package com.github.cc007.lightsaver.message.udp;

import com.github.cc007.lightsaver.message.MessageTypes;
import com.github.cc007.lightsaver.message.Message;
import com.github.cc007.lightsaver.detector.light.LightDetectorMessage;
import com.github.cc007.lightsaver.detector.motion.MotionDetectorMessage;
import com.github.cc007.lightsaver.detector.passage.PassageDetectorMessage;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UDPMessageServer {

    public static final int SERVER_PORT = 7376;

    private static DatagramSocket s = null;
    private static Message m;

    public static void main(String[] args) {
        try {
            // connection part
            s = new DatagramSocket(SERVER_PORT);

            //allocate the buffer array
            while (true) {
                // receive message
                byte[] mBuffer = new byte[MessageTypes.MAX_UDP_MSG_SIZE];
                DatagramPacket mPacket = new DatagramPacket(mBuffer, mBuffer.length);
                s.receive(mPacket);

                // construct message object from message
                switch (ByteBuffer.wrap(mBuffer).getInt(0)) {
                    case MessageTypes.LIGHT_DETECTOR_MSG:
                        // it's a Light detector value message
                        m = new LightDetectorMessage(MessageTypes.LIGHT_DETECTOR_MSG, ByteBuffer.wrap(mBuffer).getInt(4), ByteBuffer.wrap(mBuffer).getInt(8));

                        //print the info
                        System.out.println("Value from client " + ((LightDetectorMessage) m).getClientId() + ": " + ((LightDetectorMessage) m).getValue());
                        break;
                    case MessageTypes.PASSAGE_DETECTOR_MSG:
                        // it's a Passage detector value message
                        m = new PassageDetectorMessage(MessageTypes.LIGHT_DETECTOR_MSG, ByteBuffer.wrap(mBuffer).getInt(4));

                        //print the info
                        System.out.println("Detected passage from client " + ((PassageDetectorMessage) m).getClientId());
                        break;
                    case MessageTypes.MOTION_DETECTOR_MSG:
                        // it's a Passage detector value message
                        m = new MotionDetectorMessage(MessageTypes.MOTION_DETECTOR_MSG, ByteBuffer.wrap(mBuffer).getInt(4));

                        //print the info
                        System.out.println("Detected motion from client " + ((PassageDetectorMessage) m).getClientId());
                        break;
                    default:
                        System.err.println("Unknown message type found: " + ByteBuffer.wrap(mBuffer).getInt(0));
                }

            }
        } catch (SocketException ex) {
            Logger.getLogger(UDPMessageServer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(UDPMessageServer.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (s != null) {
                s.close();
            }
        }
    }

}
