package com.bigbig.event;

import java.util.EventObject;


public class HomeEvent extends EventObject {
    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public HomeEvent(Object source) {
        super(source);
    }
}