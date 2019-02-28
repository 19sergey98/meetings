package meetings;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Logger;

public class Participant implements Serializable {

    String name;

    ArrayList<Meeting> schedule;

    private static Logger log = Logger.getLogger(Participant.class.getName());

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Participant par = (Participant) o;
        return this.name.equals(par.getName());
    }
    @Override
    public String toString() {
        return "Participant{" +
                "name=" + this.name + '}';
    }
    public Participant(String name) {
        schedule = new ArrayList<>();
        this.name = name;
        log.info("Participant created " + this);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        log.info("name  set for " + this);
    }

    public void print() {
        System.out.println("User - " + this.name);
    }

    public void printSchedule() {
        if (this.schedule.size() < 1)
            System.out.println("there is no meetings planed for " + this.name);
        else {
            System.out.println("meetings planed for " + this.name);
            for (int i = 0; i < this.schedule.size(); i++)
                this.schedule.get(i).print();
        }
    }

    public void addMeeting(Meeting tempMeeting) {
        this.schedule.add(tempMeeting);
        log.info("Meeting " + tempMeeting.name + " add to user named " + this.name + " schedule");
    }

    public void removeMeeting(Meeting tempMeeting) {
        this.schedule.remove(tempMeeting);
        log.info("Meeting " + tempMeeting.name + " removed from user named " + this.name + " schedule");
    }

    public ArrayList<MeetingTime> getBusyTime() {
        ArrayList<MeetingTime> tempMT = new ArrayList<>();
        for (int i = 0; i < this.schedule.size(); i++) {
            tempMT.add(schedule.get(i).meetingTime);
        }

        return tempMT;
    }

    //check!!!
    public boolean isBusy(MeetingTime potMeetingTime) {
        ArrayList<MeetingTime> tempMT = new ArrayList<>();
        log.info("Run busy-ness check for user " + this.name + " for time " + potMeetingTime);
        for (int i = 0; i < this.schedule.size(); i++) {
            if (
                    (
                            potMeetingTime.getStartDate().getTimeInMillis() < this.schedule.get(i).getMeetingTime().getFinishDate().getTimeInMillis()
                                    &&
                                    potMeetingTime.getStartDate().getTimeInMillis() >= this.schedule.get(i).getMeetingTime().getStartDate().getTimeInMillis()
                    )
                            ||
                            (
                                    potMeetingTime.getFinishDate().getTimeInMillis() <= this.schedule.get(i).getMeetingTime().getFinishDate().getTimeInMillis()
                                            &&
                                            potMeetingTime.getFinishDate().getTimeInMillis() > this.schedule.get(i).getMeetingTime().getStartDate().getTimeInMillis()
                            )

            ) {
                log.info("Busy-ness check for user " + this.name + " is true ");
                return true;
            }
        }
        log.info("Busy-ness check for user " + this.name + " is false ");
        return false;
    }

}
