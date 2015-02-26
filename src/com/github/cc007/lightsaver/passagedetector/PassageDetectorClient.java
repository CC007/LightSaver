package com.github.cc007.lightsaver.passagedetector;

import com.github.cc007.lightsaver.udpmessage.UDPMessageClient;
import com.github.cc007.lightsaver.udpmessage.UDPMessageTypes;
import com.github.cc007.lightsaver.udpmessage.UDPMessage;
import java.nio.ByteBuffer;
import java.util.Random;

public class PassageDetectorClient extends UDPMessageClient {

    protected int clientId;

    @Override
    protected void doBefore() {
        super.doBefore();
        send = detectPassage();
    }

    public PassageDetectorClient(int clientId) {
        super("Passage detector #" + Integer.toString(clientId));
        this.clientId = clientId;
    }

    private static boolean detectPassage() {
        Random r = new Random(System.currentTimeMillis());
        return r.nextBoolean(); //TODO really detect passage
    }

    @Override
    protected UDPMessage createMessage() {
        return new PassageDetectorMessage(UDPMessageTypes.PASSAGE_DETECTOR_MSG, clientId);
    }

    @Override
    protected byte[] writeToBuffer() {
        return ByteBuffer.allocate(9).putInt(0, m.getMsgType()).putInt(4, ((PassageDetectorMessage) m).getClientId()).array();
    }

    @Override
    protected int getMessageSize() {
        return 8;
    }

    @Override
    protected void doAfter() {
        try {
            //wait 5 seconds
            Thread.sleep(1000);
        } catch (InterruptedException e) {
                e.printStackTrace();
        }
    }

}
