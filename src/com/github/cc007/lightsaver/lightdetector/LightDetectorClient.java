package com.github.cc007.lightsaver.lightdetector;

import com.github.cc007.lightsaver.udpmessage.UDPMessageClient;
import com.github.cc007.lightsaver.udpmessage.UDPMessageTypes;
import com.github.cc007.lightsaver.udpmessage.UDPMessage;
import java.nio.ByteBuffer;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LightDetectorClient extends UDPMessageClient {

    protected int clientId;

    private static int detectLight() {
        Random r = new Random(System.currentTimeMillis());
        return r.nextInt(100); //TODO really detect light
    }

    @Override
    protected void init() {
        super.init();
        Random r = new Random(System.currentTimeMillis());
        this.clientId = r.nextInt(1000); //TODO make client id unique
    }

    @Override
    protected UDPMessage createMessage() {
        return new LightDetectorMessage(UDPMessageTypes.LIGHT_DETECTOR_MSG, clientId, detectLight());
    }

    @Override
    protected byte[] writeToBuffer() {
        return ByteBuffer.allocate(12).putInt(0, m.getMsgType()).putInt(4, ((LightDetectorMessage) m).getClientId()).putInt(8, ((LightDetectorMessage) m).getValue()).array();
    }

    @Override
    protected int getMessageSize() {
        return 12;
    }

    @Override
    protected void doAfter() {
        try {
            //wait 5 seconds
            Thread.sleep(5000);
        } catch (InterruptedException ex) {
            Logger.getLogger(LightDetectorClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
