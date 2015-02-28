/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.cc007.lightsaver.message;

/**
 *
 * @author Rik
 */
public interface MessageTypes {
    // declare the max size of all message types below
    public static final int MAX_MSG_SIZE = 12;
    
    // put here the type representation
    public static final int LIGHT_DETECTOR_MSG = 1000;
    
    public static final int PASSAGE_DETECTOR_MSG = 2000;
}
