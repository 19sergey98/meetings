package meetings;

import java.io.Serializable;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Logger;

public class MeetingTime implements Serializable {

    GregorianCalendar startDate;

    private static Logger log = Logger.getLogger(MeetingTime.class.getName());

    public void setStartDate(GregorianCalendar startDate) {
        this.startDate = startDate;
        log.info("Start date set");
    }

    GregorianCalendar finishDate;

    public void setFinishDate(GregorianCalendar finishDate) {
        this.finishDate = finishDate;
        log.info("Finish date set");
    }

    static long oneHour = 60 * 60 * 1000L;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MeetingTime mt = (MeetingTime) o;
        return this.startDate.equals(mt.getStartDate()) && this.finishDate.equals(mt.getFinishDate());
    }

    @Override
    public String toString() {
        return "Meeting time{" +
                "start: " + this.startDate.getTime() +" finish: "+this.finishDate.getTime()+ '}';
    }

    public MeetingTime() {
        this.startDate = new GregorianCalendar();
        this.finishDate = new GregorianCalendar();
    }

    public MeetingTime(long longLen) {
        this.startDate = new GregorianCalendar();
        this.finishDate = new GregorianCalendar();
        this.finishDate.setTimeInMillis(this.startDate.getTimeInMillis() + longLen);
        log.info("Meeting time " + this + " created today with len" + longLen + " millis");
    }

    public void shiftMeetingTime(long longShift) {
        this.startDate.setTimeInMillis(this.startDate.getTimeInMillis() + longShift);
        this.finishDate.setTimeInMillis(this.finishDate.getTimeInMillis() + longShift);
        log.info("Meeting time " + this + " shifted by" + longShift + " millis");
    }

    /**
     * default meeting length is 1 hour
     */
    public MeetingTime(int year, int month, int date, int hour, int minute) {
        this.startDate = new GregorianCalendar(year, month, date, hour, minute);
        long original = startDate.getTimeInMillis();
        this.finishDate = new GregorianCalendar(year, month, date, hour, minute);
        finishDate.setTimeInMillis(original + MeetingTime.oneHour);
    }

    public MeetingTime(int year, int month, int date, int hour, int minute, double meetingLen) {
        if (meetingLen <= 0)
            throw new MeetingInitException("Incorrect meeting time. Gotta be more than 0");

        this.startDate = new GregorianCalendar(year, month, date, hour, minute);
        this.finishDate = new GregorianCalendar(year, month, date, hour, minute);
        long original = startDate.getTimeInMillis();
        finishDate.setTimeInMillis(original + (long) (MeetingTime.oneHour * meetingLen));

        log.info("Meeting time created " + this);
    }

    public long getLengthInMillis() {
        long begin = startDate.getTimeInMillis();
        long end = finishDate.getTimeInMillis();
        return end - begin;
    }

    public double getLength() {
        float tm = this.getLengthInMillis();
        return tm / MeetingTime.oneHour;
    }

    public GregorianCalendar getStartDate() {
        return startDate;
    }

    public GregorianCalendar getFinishDate() {
        return finishDate;
    }

    public void print() {
        System.out.println("Start Date: " + startDate.getTime());
        System.out.println("Finish Date: " + finishDate.getTime());
    }
}

