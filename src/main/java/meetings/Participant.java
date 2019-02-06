package meetings;

import java.util.logging.Logger;

public class Participant {

    String name;

    private static Logger log = Logger.getLogger(Participant.class.getName());

    public Participant(String name) {
        this.name = name;
        log.info("Participant created "+this);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        log.info("name  set for " +this);
    }
}
