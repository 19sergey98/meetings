package meetings;

import java.io.Serializable;

public class MeetingInitException  extends ExceptionInInitializerError implements Serializable {

    public MeetingInitException(String message) {
        super(message);
    }
}
