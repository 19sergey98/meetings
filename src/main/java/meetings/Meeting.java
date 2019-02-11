package meetings;

import java.util.ArrayList;
import java.util.logging.Logger;

public class Meeting{

    String name;
    MeetingTime meetingTime;
    ArrayList<Participant> participants;

    public String getName(){
        return this.name;
    }

    private static Logger log = Logger.getLogger(Meeting.class.getName());

    public void setName(String name) {
        this.name = name;
    }

    public Meeting(int year, int month, int date, int hour, int minute, double meetingLen) {
        this.meetingTime = new MeetingTime(year,  month,  date,  hour, minute,  meetingLen);
        log.info("Created meeting  " );
    }

    public Meeting(String name, int year, int month, int date, int hour, int minute, double meetingLen) {
        this.meetingTime = new MeetingTime(year,  month,  date,  hour, minute,  meetingLen);
        log.info("Created meeting  "+name+" " );
    }

    public MeetingTime getMeetingTime() {
        return  this.meetingTime;
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
        }
        log.info("User named "+tempParticipant.getName()+" can't be add to the meeting " );
        return false;
    }

    public void printAllParticipants(){
        if(this.participants.size()<1)
            System.out.println("No participants!");
        else
            System.out.println("participants :");

        for(int i = 0; i < this.participants.size();i++){
            this.participants.get(i).getName();
        }
    }

    public void print(){
        System.out.println("Meeting- "+this.name);
        this.printAllParticipants();
    }
}
