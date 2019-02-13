package meetings;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.logging.Logger;

public class Room {

    String name;

    ArrayList<MeetingTime> availableTime;
    ArrayList<Meeting> meetings;

    private static Logger log = Logger.getLogger(Room.class.getName());

    public Room(int id) {
        this.name = "room"+id;
        this.availableTime = new ArrayList<MeetingTime>();
        this.meetings = new ArrayList<Meeting>();
    }

    public Room(String name) {
        this.name = name;
        this.availableTime = new ArrayList<MeetingTime>();
        this.meetings = new ArrayList<Meeting>();
        log.info("Created Room "+name+" "+ this);
    }

    public void addAvailableTime(MeetingTime timeToAdd){
        long maxFinishDateInMillis;
        long minStartDateInMillis;

        this.availableTime.add(0,timeToAdd);

        //sort by start time
        for (int i = 0; i < this.availableTime.size()-1; i++){
            if(this.availableTime.get(i).getStartDate().getTimeInMillis() > this.availableTime.get(i+1).getStartDate().getTimeInMillis())
                this.swapAvailableTime(i,i+1);
        }

        //merging
        for (int i = 0; i < this.availableTime.size()-1; i++){
            if(this.availableTime.get(i).getFinishDate().getTimeInMillis() >= this.availableTime.get(i+1).getStartDate().getTimeInMillis()){
                minStartDateInMillis = Math.min(this.availableTime.get(i).getStartDate().getTimeInMillis(),
                        this.availableTime.get(i+1).getStartDate().getTimeInMillis());
                maxFinishDateInMillis = Math.max(this.availableTime.get(i).getFinishDate().getTimeInMillis(),
                        this.availableTime.get(i+1).getFinishDate().getTimeInMillis());

                this.availableTime.get(i).getStartDate().setTimeInMillis(minStartDateInMillis);
                this.availableTime.get(i).getFinishDate().setTimeInMillis(maxFinishDateInMillis);
                this.availableTime.remove(i+1);
                i--;
            }
            log.info("Added available time for room "+this.name+" "+ this);

        }


       /* if (this.availableTime.size() < 1) {
            this.availableTime.add(timeToAdd);
        }*/


        //check nearby intervals

    }

    public boolean isTimeAavailable(MeetingTime timeToCheck){

         long startInMillis = timeToCheck.getStartDate().getTimeInMillis();
         long finishInMillis = timeToCheck.getFinishDate().getTimeInMillis();

        for(int i = 0; i < this.availableTime.size();i++){

            long curStartInMillis = this.availableTime.get(i).getStartDate().getTimeInMillis();
            long curFinishInMillis =this.availableTime.get(i).getFinishDate().getTimeInMillis();

            if (startInMillis >= curStartInMillis && finishInMillis <= curFinishInMillis) {
                 return true;
            }
        }
        return false;
    }

    public void swapAvailableTime(int i, int j)
    {
        MeetingTime tempMT = this.availableTime.get(j);
        this.availableTime.set(j,this.availableTime.get(i));
        this.availableTime.set(i,tempMT);
    }

    public Meeting getMeeting(String meetingName){
        for(int i = 0; i< this.meetings.size(); i++)
            if(this.meetings.get(i).getName().equals(meetingName)){
                log.info("Got meeting " + this.meetings.get(i)+" named "+meetingName);
                return this.meetings.get(i);
            }
        return null;
    }

    public void addMeeting(Meeting tempMeeting){
        for(int i = 0; i< this.availableTime.size(); i++){
            //there is time
            if( (tempMeeting.getMeetingTime().getStartDate().getTimeInMillis() >= this.availableTime.get(i).getStartDate().getTimeInMillis())
                    && tempMeeting.getMeetingTime().getFinishDate().getTimeInMillis()<= this.availableTime.get(i).getFinishDate().getTimeInMillis()) {
                //add to meeting list
                this.meetings.add(tempMeeting);
                //adjust available time

                long tempFinishTimeInMillis = this.availableTime.get(i).getFinishDate().getTimeInMillis();
                long tempStartTimeInMillis = tempMeeting.getMeetingTime().getFinishDate().getTimeInMillis();

                this.availableTime.get(i).getFinishDate().setTimeInMillis(tempMeeting.getMeetingTime().getStartDate().getTimeInMillis());

                this.availableTime.get(i).getFinishDate().setTimeInMillis(tempMeeting.getMeetingTime().getStartDate().getTimeInMillis());
                MeetingTime tempMeetingTime = new MeetingTime();
                tempMeetingTime.getStartDate().setTimeInMillis(tempStartTimeInMillis);
                tempMeetingTime.getFinishDate().setTimeInMillis(tempFinishTimeInMillis);
                this.availableTime.add(i+1,tempMeetingTime);

                i = this.availableTime.size();
                log.info("Added Meeting for room "+this.name+" "+ this);

            }

        }

        for(int j = 0; j< this.availableTime.size(); j++){
            if(this.availableTime.get(j).getStartDate().getTimeInMillis() == this.availableTime.get(j).getFinishDate().getTimeInMillis()){
                this.availableTime.remove(j);
                j--;
            }
        }

    }

    public void removeMeeting(String meetingName){
        Meeting tempMeeting= this.getMeeting(meetingName);
        //remove meeting from all participant schedules
        for(int i =0; i< tempMeeting.participants.size(); i++) {
            tempMeeting.participants.get(i).schedule.remove(tempMeeting);
            log.info("Meeting "+tempMeeting.name+" has been removed from "+tempMeeting.participants.get(i).getName()+ "'s schedule");
        }

        //add freed time to available
        this.addAvailableTime(tempMeeting.getMeetingTime());
        log.info("Freed time added to room "+this.name);

        //remove from meetings
        this.meetings.remove(tempMeeting);
        log.info("Freed time added to room "+this.name);

    }
/*
    public long getMostCloseStartTimeInMillis(long start, long length){
        long avstart =0;
        long avfinish =0;

        for(int i = 0; i< this.availableTime.size(); i++){
            avstart=this.availableTime.get(i).getStartDate().getTimeInMillis();
            avfinish=this.availableTime.get(i).getFinishDate().getTimeInMillis();

            if(start>avstart)
              if(start-avfinish>=length)
                  return start;
            else
                if(avstart-avfinish>=length)
                    return avstart;
        }
        return -1;
    }
*/
    public String getName(){
        return name;
    }


    public void print(){
        System.out.println("Room name - "+ this.getName());
        this.printAvailableTime();
        System.out.println("Meetings:");
        this.printAllMeetings();
        System.out.println();
    }

    public void printAvailableTime(){
        if(this.availableTime.size()<1)
            System.out.println("No available time!");
        else{
            System.out.println("Available time:");
            for(int i = 0; i < this.availableTime.size();i++){
                this.availableTime.get(i).print();
            }
        }

    }

    public void printAllMeetings(){
        if(this.meetings.size()<1)
            System.out.println("No meetings planned!");
        else
            System.out.println("Planned meetings:");

        for(int i = 0; i < this.meetings.size();i++){
            this.meetings.get(i).print();
        }
    }

}
