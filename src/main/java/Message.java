/**
 * Created by qadirhaqq on 11/19/17.
 */
public class Message {
    int messageId;
    boolean objectFound;
    Cell objectLocation;
    public Message(int id, boolean objectFound, Cell location) {
        this.messageId  = id;
        this.objectFound = objectFound;
        this.objectLocation = location;
    }
}
