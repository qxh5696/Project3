package com.rit.edu.cs.copads.p3;

/**
 * Created by qadirhaqq on 11/19/17.
 */

public class Message {
    int messageId;
    boolean objectFound;
    Cell objectLocation;

    /**
     * com.rit.edu.cs.copads.p3.Message Object for each robot
     *
     * @param id          - Id of the robot
     * @param objectFound - Object found boolean message
     * @param location    - x,y coordinate message if object is found
     */
    public Message(int id, boolean objectFound, Cell location) {
        this.messageId = id;
        this.objectFound = objectFound;
        this.objectLocation = location;
    }
}
