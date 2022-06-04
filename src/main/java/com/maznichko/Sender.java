package com.maznichko;

/**
 * This class send notify to other places
 */
public interface Sender  {
    /**
     * send notify
     */
    void send(String header,String body,String to);
}
