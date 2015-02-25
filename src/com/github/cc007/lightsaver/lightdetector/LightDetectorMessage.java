/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.cc007.lightsaver.lightdetector;

import com.github.cc007.lightsaver.udpmessage.UDPMessage;

/**
 *
 * @author Rik
 */
public class LightDetectorMessage extends UDPMessage {

    private final int clientId;
    private final int value;

    public LightDetectorMessage(int msgType, int clientId, int value) {
        super(msgType);
        this.clientId = clientId;
        this.value = value;
    }

    public int getClientId() {
        return clientId;
    }

    public int getValue() {
        return value;
    }

}
