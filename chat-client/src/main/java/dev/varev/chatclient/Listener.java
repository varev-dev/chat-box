package dev.varev.chatclient;

import dev.varev.chatshared.MessageDTO;

import java.io.IOException;
import java.io.ObjectInputStream;

public class Listener extends Thread {
    private ObjectInputStream input;

    public Listener(ObjectInputStream input) {
        this.input = input;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Object response = input.readObject();

                if (response instanceof MessageDTO message) {
                    System.out.println(message);
                } else {
                    // todo: log unknown response
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
