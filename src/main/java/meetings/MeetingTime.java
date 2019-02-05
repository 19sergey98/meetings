package meetings;

import java.util.Date;
import java.util.GregorianCalendar;

public class MeetingTime {

    GregorianCalendar startDate;

    public void setStartDate(GregorianCalendar startDate) {
        this.startDate = startDate;
    }

    GregorianCalendar finishDate;

    public void setFinishDate(GregorianCalendar finishDate) {
        this.finishDate = finishDate;
    }

    static long oneHour = 60*60*1000L;

    public MeetingTime() {
        this.startDate = new GregorianCalendar();
        this.finishDate = new GregorianCalendar();
    }

    /**default meeting length is 1 hour*/
    public MeetingTime(int year, int month, int date, int hour, int minute) {
        this.startDate = new GregorianCalendar(year, month, date, hour, minute);
        long original = startDate.getTimeInMillis();
        this.finishDate = new GregorianCalendar(year, month, date, hour, minute);
        finishDate.setTimeInMillis(original+MeetingTime.oneHour);
    }

    public MeetingTime(int year, int month, int date, int hour, int minute, double meetingLen) {
        if(meetingLen<=0)
            throw new MeetingInitException("Incorrect meeting time. Gotta be more than 0");

        this.startDate = new GregorianCalendar(year, month, date, hour, minute);
        this.finishDate = new GregorianCalendar(year, month, date, hour, minute);
        long original = startDate.getTimeInMillis();
        finishDate.setTimeInMillis(original+(long)(MeetingTime.oneHour*meetingLen));
    }

    public long getLengthInMillis(){
        long begin = startDate.getTimeInMillis();
        long end = finishDate.getTimeInMillis();
        return end-begin;
    }

    public double getLength(){
        float tm = this.getLengthInMillis();
        return tm/MeetingTime.oneHour;
    }

    public GregorianCalendar getStartDate(){
        return startDate;
    }

    public GregorianCalendar getFinishDate(){
        return finishDate;
    }

    public void print()
    {
        System.out.println("Start Date: "+startDate.getTime());
        System.out.println("Finish Date: "+finishDate.getTime());
    }
}

