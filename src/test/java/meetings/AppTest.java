package meetings;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }

    @Test
    public void addUser(){
        App testApp = new App();

        testApp.addUser("ua");
        testApp.addUser("ub");
        testApp.addUser("uc");

        Participant u1 = new Participant("ua");
        Participant u2 = new Participant("ub");
        Participant u3 = new Participant("uc");

        ArrayList<Participant> testList = new ArrayList<>();
        testList.add(u1);
        testList.add(u2);
        testList.add(u3);
        Assert.assertEquals(testApp.getAllUsers(),testList);
    }

    @Test
    public void removeUsers(){
        App testApp = new App();

        testApp.addUser("ua");
        testApp.addUser("ub");
        testApp.addUser("uc");

        testApp.removeUser("uc");

        Participant u1 = new Participant("ua");
        Participant u2 = new Participant("ub");

        ArrayList<Participant> testList = new ArrayList<>();
        testList.add(u1);
        testList.add(u2);

        Assert.assertEquals(testApp.getAllUsers(),testList);

    }

    @Test
    public void addRooms(){
        App testApp = new App();

        testApp.addRoom("ra");
        testApp.addRoom("rb");
        testApp.addRoom("rc");


        Room r1 = new Room("ra");
        Room r2 = new Room("rb");
        Room r3 = new Room("rc");

        ArrayList<Room> testList = new ArrayList<>();
        testList.add(r1);
        testList.add(r2);
        testList.add(r3);

        Assert.assertEquals(testApp.getAllRooms(),testList);
    }

    @Test
    public void removeRoom(){
        App testApp = new App();

        testApp.addRoom("ra");
        testApp.addRoom("rb");
        testApp.addRoom("rc");

        testApp.removeRoom("rb");

        Room r1 = new Room("ra");
        Room r2 = new Room("rc");

        ArrayList<Room> testList = new ArrayList<>();
        testList.add(r1);
        testList.add(r2);

        Assert.assertEquals(testApp.getAllRooms(),testList);

    }

    @Test
    public void addAvailableTime(){
        App testApp = new App();

        testApp.addRoom("ua");

        testApp.getRoom("ua").addAvailableTime(new MeetingTime(2019,10,2,11,0,3));
        testApp.getRoom("ua").addAvailableTime(new MeetingTime(2019,10,28,11,0,42));
        testApp.getRoom("ua").addAvailableTime(new MeetingTime(2019,10,2,12,0,1));//already covered

        ArrayList<MeetingTime> testList = new ArrayList<>();
        testList.add(new MeetingTime(2019,10,2,11,0,3));
        testList.add(new MeetingTime(2019,10,28,11,0,42));

        Assert.assertEquals(testApp.getRoom("ua").availableTime,testList);
    }

    @Test
    public void fillAvailableTime(){
        App testApp = new App();

        testApp.addRoom("ua");

        testApp.getRoom("ua").addAvailableTime(new MeetingTime(2019,10,2,11,0,3));

        //fill time from 12 to 13 that means now have av 11to12 and 13to14
        testApp.getRoom("ua").addMeeting(new Meeting("test",new MeetingTime(2019,10,2,12,0,1)));

        ArrayList<MeetingTime> testList = new ArrayList<>();
        testList.add(new MeetingTime(2019,10,2,11,0,1));
        testList.add(new MeetingTime(2019,10,2,13,0,1));

        Assert.assertEquals(testApp.getRoom("ua").availableTime,testList);
    }

    @Test
    public void changeMeetingTime(){
        App testApp = new App();

        testApp.addRoom("ua");

        testApp.getRoom("ua").addAvailableTime(new MeetingTime(2019,10,2,11,0,3));

        //initial time from 11 to 12
        testApp.getRoom("ua").addMeeting(new Meeting("test",new MeetingTime(2019,10,2,11,0,1)));

        //final time 12 to 13 means free  11 to 12 and 13 to 14
        testApp.getRoom("ua").changeMeetingDate("test",2019,10,2,12,0);

        ArrayList<MeetingTime> testList = new ArrayList<>();
        testList.add(new MeetingTime(2019,10,2,11,0,1));
        testList.add(new MeetingTime(2019,10,2,13,0,1));

        Assert.assertEquals(testApp.getRoom("ua").availableTime,testList);
    }



}
