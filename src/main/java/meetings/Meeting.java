package meetings;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.logging.Logger;

public class Meeting implements Serializable {

    String name;
    MeetingTime meetingTime;
    ArrayList<Participant> participants;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Meeting m = (Meeting) o;
        return this.meetingTime.equals(m.meetingTime) &&
                this.name.equals(m.name);
    }

    @Override
    public String toString() {
        return "Meeting{" +
                "name=" + this.name + '}';
    }

    public String getName(){
        return this.name;
    }

    private static Logger log = Logger.getLogger(Meeting.class.getName());

    public void setName(String name) {
        this.name = name;
    }

    public Meeting(int year, int month, int date, int hour, int minute, double meetingLen) {
        this.meetingTime = new MeetingTime(year,  month,  date,  hour, minute,  meetingLen);
        this.participants = new ArrayList<>();
        log.info("Created meeting  " );
    }

    public Meeting(String name, int year, int month, int date, int hour, int minute, double meetingLen) {
        this.meetingTime = new MeetingTime(year,  month,  date,  hour, minute,  meetingLen);
        this.participants = new ArrayList<>();
        this.name = name;
        log.info("Created meeting  "+name+" " );
    }

    public Meeting(String name, MeetingTime mt){
        this.meetingTime = mt;
        this.participants = new ArrayList<>();
        this.name = name;
        log.info("Created meeting  "+name+" " );
    }

    public MeetingTime getMeetingTime() {
        return  this.meetingTime;
    }

    public boolean isParticipant(String participantName){
        for(int i=0; i< this.participants.size();i++)
            if(this.participants.get(i).getName().equals(participantName))
                return true;
        return false;
    }

    public void removeParticipant(String participantName){
        Participant tempParticipant = this.getParticipant(participantName);

        if(tempParticipant==null){
            log.info("tried to remove not existed participant named + "+participantName+" from meeting"+this);
        }
        else {
            //remove from schedule
            this.getParticipant(participantName).removeMeeting(this);
            //remove from participants
            this.participants.remove(this.getParticipant(participantName));
            log.info("removed participant");
        }
    }

    public Participant getParticipant(String participantName) {
        for (int i = 0; i < this.participants.size(); i++) {
            if (this.participants.get(i).getName().equals(participantName))
                return this.participants.get(i);
        }
        return null;
    }

    public void changeDate(int year, int month, int date, int hour, int minute){
        long len = this.meetingTime.getLengthInMillis();
        GregorianCalendar newStartDate = new GregorianCalendar( year,  month,  date,  hour,  minute);
        GregorianCalendar newFinishDate = new GregorianCalendar( year,  month,  date,  hour,  minute);
        newFinishDate.setTimeInMillis(newStartDate.getTimeInMillis()+len);
        this.meetingTime.setStartDate(newStartDate);
        log.info("set new start date "+newStartDate.toString()+"for "+this);
        this.meetingTime.setFinishDate(newFinishDate);
        log.info("set new finish date "+newFinishDate.toString()+"for "+this);
    }

    public void changeDate(MeetingTime newMeetingTime){
        GregorianCalendar newStartDate = new GregorianCalendar();
        newStartDate.setTimeInMillis(newMeetingTime.getStartDate().getTimeInMillis());

        GregorianCalendar newFinishDate = new GregorianCalendar();
        newFinishDate.setTimeInMillis(newMeetingTime.getFinishDate().getTimeInMillis());

        this.meetingTime.setStartDate(newStartDate);
        log.info("set new start date "+newStartDate.toString()+"for "+this);

        this.meetingTime.setFinishDate(newFinishDate);
        log.info("set new finish date "+newFinishDate.toString()+"for "+this);
    }

    //true-added false-user can't be add
    public boolean addParticipant(Participant tempParticipant){
        //is free
        if(!tempParticipant.isBusy(this.meetingTime)){
            //add to meeting's participants
            this.participants.add(tempParticipant);
            log.info("User named "+tempParticipant.getName()+"added to the meeting " );
            //add to participant's schedule
            tempParticipant.addMeeting(this);
            log.info("User named "+tempParticipant.getName()+"got meeting "+this+" is his schedule" );
            return true;
        }
        log.info("User named "+tempParticipant.getName()+" can't be add to the meeting " );
        return false;
    }

    public void addParticipantsList(ArrayList<Participant> tempList){
        for(int i=0; i< tempList.size(); i++)
            this.addParticipant(tempList.get(i));
    }

    public void printAllParticipants() {
        if (this.participants.size() < 1)
            System.out.println("No participants!");
        else
            System.out.println("Participants :");
        {

            for (int i = 0; i < this.participants.size(); i++) {
                System.out.println(this.participants.get(i).getName());
            }
        }
    }

    public void print(){
        System.out.println("Meeting - "+this.name);
        System.out.println("Time:");
        this.meetingTime.print();
        this.printAllParticipants();
    }
}
