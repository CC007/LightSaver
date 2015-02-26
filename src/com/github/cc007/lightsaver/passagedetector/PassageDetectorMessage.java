/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.cc007.lightsaver.passagedetector;

import com.github.cc007.lightsaver.udpmessage.UDPMessage;

/**
 *
 * @author Rik
 */
public class PassageDetectorMessage extends UDPMessage {

    private final int clientId;
    private final boolean detected;

    public PassageDetectorMessage(int msgType, int clientId, boolean detected) {
        super(msgType);
        this.clientId = clientId;
        this.detected = detected;
    }

    public int getClientId() {
        return clientId;
    }

    public boolean isDetected() {
        return detected;
    }


}
