package meetings;

import java.util.ArrayList;

public class Meeting{

    String name;
    MeetingTime meetingTime;
    ArrayList<Participant> participants;

    public void setName(String name) {
        this.name = name;
    }

    public Meeting(int year, int month, int date, int hour, int minute, double meetingLen) {
        this.meetingTime = new MeetingTime(year,  month,  date,  hour, minute,  meetingLen);
    }

    public MeetingTime getMeetingTime() {
        return  this.meetingTime;
    }

    public void printAllParcipants(){
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
        this.printAllParcipants();
    }
}
