/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.cc007.lightsaver.udpmessage;

import java.io.Serializable;

/**
 *
 * @author Rik
 */
public abstract class UDPMessage implements Serializable {

    private final int msgType;

    public UDPMessage(int msgType) {
        this.msgType = msgType;
    }

    public int getMsgType() {
        return msgType;
    }

}
