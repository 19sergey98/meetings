package meetings;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.logging.Logger;

public class App {
    //static String datePattern =
    // static SimpleDateFormat ft = new SimpleDateFormat (datePattern);

    ArrayList<Room> rooms;
    ArrayList<Participant> users;

    private static Logger log = Logger.getLogger(App.class.getName());


    public App() {
        rooms = new ArrayList<>();
        users = new ArrayList<>();
    }

    public static void main(String[] args) {
        App mainApp = new App();
        //  Block of code to try
        /**MeetingTime testMeetingTime =new MeetingTime(2018,11,4,6,0,1.5);
         testMeetingTime.print();
         System.out.println(testMeetingTime.getLengthInMillis()+" mls");
         System.out.println(testMeetingTime.getLength()+" hrs");*/
        int controller = 0;
        String tempRoomName = "";
        Scanner sc = new Scanner(System.in);


        String[] mainLevelOptions = {
                "Create a room",
                "Remove the room ",
                "Show all room names"
        };

        while (controller != 10) {
            try {
                System.out.println();
                for (int i = 0; i < mainLevelOptions.length; i++)
                    System.out.println("Press " + i + " to " + mainLevelOptions[i]);

                controller = sc.nextInt();

                switch (controller) {
                    case 0:
                        // Create a room
                        System.out.println("Type room name");
                        tempRoomName = sc.next();
                        if (mainApp.getRoom(tempRoomName) == null) {
                            mainApp.addRoom(tempRoomName);
                            System.out.println("Room named " + tempRoomName +" has been created");
                        } else
                            System.out.println("There is a room named " + tempRoomName);
                        break; // optional

                    case 1:
                        // remove the room
                        System.out.println("Type room name");
                        tempRoomName = sc.next();
                        if (mainApp.getRoom(tempRoomName) == null) {
                            System.out.println("There is no room named " + tempRoomName);
                        } else{
                            mainApp.removeRoom(tempRoomName);
                            System.out.println("Room named " + tempRoomName + " has been removed");
                        }
                        break; // optional

                    case 2:
                        // Statements
                        mainApp.printAllRooms();
                        break; // optional

                    // You can have any number of case statements.
                    default: // Optional
                        controller = 10;
                        // Statements
                }
            } catch (MeetingInitException e) {
                //  Block of code to handle errors
                e.printStackTrace();
            }/*catch (ParseException e) {
            System.out.println("Unparseable using " + ft);
        }*/ catch (InputMismatchException e) {
                //  Block of code to handle errors
                System.out.println("Incorrect data format");
                //sc.close();
                //sc = new Scanner(System.in);
                //e.printStackTrace();
            } finally {
                //System.out.println("The 'try catch' is finished.");
            }
        }
    }
    //adding test
            /*
            MeetingTime testMeetingTime =new MeetingTime(2018,11,4,6,0,1.5);
            Room testRoom= new Room(1);
            testRoom.addAvailableTime(testMeetingTime);
            testRoom.printAvailableTime();
            System.out.println();
            testRoom.addAvailableTime(new MeetingTime(2018,11,4,9,0,1.5));
            testRoom.printAvailableTime();
            System.out.println();
            testRoom.addAvailableTime(new MeetingTime(2018,11,4,10,0,1));
            testRoom.printAvailableTime();
            System.out.println();
            testRoom.addAvailableTime(new MeetingTime(2018,11,4,12,0,1));
            testRoom.printAvailableTime();
            System.out.println();
            testRoom.addAvailableTime(new MeetingTime(2018,11,4,6,0,1));
            testRoom.printAvailableTime();
            System.out.println();
            testRoom.addAvailableTime(new MeetingTime(2018,11,4,6,0,5.5));
            testRoom.printAvailableTime();
            System.out.println();
            testRoom.addAvailableTime(new MeetingTime(2018,11,4,11,0,3));
            testRoom.printAvailableTime();
            System.out.println();
            testRoom.addMeeting(new Meeting(2018,11,4,13,0,1));
            testRoom.printAvailableTime();
            System.out.println();
            testRoom.addMeeting(new Meeting(2018,11,4,8,0,1));
            testRoom.printAvailableTime();
            System.out.println();
            testRoom.addMeeting(new Meeting(2018,11,4,6,0,1));
            testRoom.printAvailableTime();
            System.out.println();
            testRoom.addMeeting(new Meeting(2018,11,4,7,0,1));
            testRoom.printAvailableTime();
            System.out.println();
            testRoom.addMeeting(new Meeting(2018,11,4,10,0,1));
            testRoom.printAvailableTime();
            System.out.println();
            testRoom.addMeeting(new Meeting(2018,11,4,11,0,1));
            testRoom.printAvailableTime();
            System.out.println();
            testRoom.addAvailableTime(new MeetingTime(2018,11,4,15,0,1));
            testRoom.printAvailableTime();
               */


    //System.out.println( "Hello World!" );


    public void createMeetingTime(MeetingTime curMeetingTime) {
        System.out.println("Enter meeting start date");
        System.out.println("Format:");
        //new MeetingTime;
        new MeetingTime(2018, 11, 4, 6, 0, -2);
        log.info("Meeting time created "+ curMeetingTime);

    }

    public Room getRoom(String roomName) {
        for (int i = 0; i < this.rooms.size(); i++)
            if (this.rooms.get(i).getName().equals(roomName))
                return this.rooms.get(i);

        return null;
    }

    public void removeRoom(String roomName) {
        if (getRoom(roomName) != null){
            rooms.remove(getRoom(roomName));
            log.info("removed room "+roomName+" ");

        }
    }

    public void addRoom(String roomName){
        this.rooms.add(new Room(roomName));
        log.info("added room "+roomName);
    }

    public void addUser(String userName){
        this.users.add(new Participant(userName));
        log.info("added user "+userName);
    }

    public Participant getUser(String userName) {
        for (int i = 0; i < this.users.size(); i++)
            if (this.users.get(i).getName().equals(userName))
                return this.users.get(i);
        return null;
    }

    public void removeUser(String userName) {
        if (getRoom(userName) != null){
            users.remove(getRoom(userName));
            log.info("removed user "+userName+" ");
        }
    }

    public void printAllRooms(){
        for(int i = 0; i< this.rooms.size(); i++)
            this.rooms.get(i).print();
    }

    //public static boolean checkValueRange(int )
}
