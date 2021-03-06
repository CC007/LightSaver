/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.cc007.lightsaver.light;

import com.github.cc007.lightsaver.detector.light.LightDetectorMessage;
import com.github.cc007.lightsaver.detector.motion.MotionDetectorMessage;
import com.github.cc007.lightsaver.detector.passage.PassageDetectorMessage;
import com.github.cc007.lightsaver.message.Message;
import com.github.cc007.lightsaver.message.MessageTypes;
import com.github.cc007.lightsaver.message.udp.UDPMessageProtocol;
import java.nio.ByteBuffer;

/**
 *
 * @author Rik
 */
public class DetectorUDPMessageProtocol implements UDPMessageProtocol {

    @Override
    public void processInput(byte[] mBuffer, Message m) {
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
                System.out.println("Detected motion from client " + ((MotionDetectorMessage) m).getClientId());
                break;
            default:
                System.err.println("Unknown message type found: " + ByteBuffer.wrap(mBuffer).getInt(0));
        }
    }

}
