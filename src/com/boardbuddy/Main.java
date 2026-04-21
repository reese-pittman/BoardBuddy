package com.boardbuddy;

import com.boardbuddy.service.InputXml;
import com.boardbuddy.service.LoginBackend;

/**
 * Main Program
 */
public class Main {

    

    /**
     * Testing main
     * @param args
     */
    public static void main(String[] args) {

        InputXml.parse("reviews.xml", "Reviews", -1); // return value can be ignored
        InputXml.parse("users.xml", "Users", -1);

        // Start the chain to login -> dashboard
        // Unused is okay
        @SuppressWarnings("unused")
        LoginBackend startLogin = new LoginBackend();

        

    }

}
