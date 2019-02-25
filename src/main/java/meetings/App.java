package meetings;

import java.io.*;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.logging.Logger;

public class App implements Serializable {
    ArrayList<Room> rooms;
    ArrayList<Participant> participants;

    private static Logger log = Logger.getLogger(App.class.getName());

    public App() {
        rooms = new ArrayList<>();
        participants = new ArrayList<>();
    }

    public static void main(String[] args) {
        App mainApp = new App();

        int controller = 0;
        ArrayList<Participant> tempParticipantsPool = new ArrayList<>();
        String tempRoomName = "";
        String tempUserName = "";
        String tempMeetingName = "";
        int secondController = 0;
        double tempMeetingLen = 1.0;
        Scanner sc = new Scanner(System.in);
        int[] tempMeetingParams = {};

        String[] mainLevelOptions = {
                "Create new room",
                "Remove the room ",
                "Show all rooms",
                "Create new user",
                "Remove the user",
                "Show all users",
                "Add available time for room",
                "Show planned meetings for the user",
                "Create new meeting",
                "Show all meetings in room",
                "Show all meetings",
                "Remove the meeting",
                "Add participant to the meeting",
                "Remove participant from the meeting",
                "Change meeting date",
                "Save session",
                "Load session",
                "Exit"
        };

        while (controller != 20) {
            try {
                System.out.println();
                for (int i = 0; i < mainLevelOptions.length; i++)
                    System.out.println("Press " + i + " to " + mainLevelOptions[i]);
                while (!sc.hasNextInt()) {
                    sc.next();
                    System.out.println("Enter correct number");
                }
                controller = sc.nextInt();


                switch (controller) {
                    case 0:
                        // Create a room
                        System.out.println("Type room name");
                        tempRoomName = sc.next();
                        if (mainApp.getRoom(tempRoomName) == null) {
                            mainApp.addRoom(tempRoomName);
                            System.out.println("Room named " + tempRoomName + " has been created");
                        } else
                            System.out.println("There is a room named " + tempRoomName);
                        break;
                    case 1:
                        // remove the room
                        System.out.println("Type room name");
                        tempRoomName = sc.next();
                        if (mainApp.getRoom(tempRoomName) == null) {
                            System.out.println("There is no room named " + tempRoomName);
                        } else {
                            mainApp.removeRoom(tempRoomName);
                            System.out.println("Room named " + tempRoomName + " has been removed");
                        }
                        break;
                    case 2:
                        //print all rooms
                        mainApp.printAllRooms();
                        break;
                    case 3:
                        // Create new user
                        System.out.println("Type user name");
                        tempUserName = sc.next();
                        if (mainApp.getParticipant(tempUserName) == null) {
                            mainApp.addUser(tempUserName);
                            System.out.println("User named " + tempUserName + " has been created");
                        } else
                            System.out.println("There is a user named " + tempUserName);
                        break;
                    case 4:
                        // remove the user
                        System.out.println("Type user name");
                        tempUserName = sc.next();
                        if (mainApp.getParticipant(tempUserName) == null) {
                            System.out.println("There is no user named " + tempUserName);
                        } else {
                            mainApp.removeUser(tempUserName);
                            System.out.println("User named " + tempUserName + " has been removed");
                        }
                        break;
                    case 5:
                        // print all users
                        mainApp.printAllUsers();
                        break;
                    case 6:
                        // set available time
                        int temp = 0;
                        System.out.println("Type room name");
                        tempRoomName = sc.next();
                        if (mainApp.getRoom(tempRoomName) == null) {
                            System.out.println("There is no room named " + tempRoomName);
                        } else {
                            //set meeting time
                            System.out.println("Press 0 set default time");
                            System.out.println("Press 1 set time");
                            temp = mainApp.getIntFromConsole(sc);
                            if (temp == 0) {
                                mainApp.getRoom(tempRoomName).addAvailableTime(new MeetingTime(2019, 0, 1, 8, 0, 8760));
                            } else {
                                tempMeetingLen = mainApp.getMeetingLenFromConsole(sc);
                                tempMeetingParams = mainApp.getMeetingTimeFromConsole(sc);
                                mainApp.getRoom(tempRoomName).addAvailableTime(new MeetingTime(tempMeetingParams[0], tempMeetingParams[1], tempMeetingParams[2],
                                        tempMeetingParams[3], tempMeetingParams[4], tempMeetingLen));
                            }
                        }
                        break;

                    case 7:
                        // print user's schedule
                        System.out.println("Type user name");
                        tempUserName = sc.next();
                        if (mainApp.getParticipant(tempUserName) == null) {
                            System.out.println("There is no user named " + tempUserName);
                        } else {
                            mainApp.getParticipant(tempUserName).printSchedule();
                        }
                        break;
                    case 8:
                        // create new Meeting
                        tempParticipantsPool.clear();
                        System.out.println("Type meeting name");
                        tempMeetingName = sc.next();
                        while (mainApp.isMeeting(tempMeetingName)) {
                            System.out.println("There is meeting named " + tempMeetingName);
                            System.out.println("Enter different name");
                            tempMeetingName = sc.next();
                        }

                        tempMeetingLen = mainApp.getMeetingLenFromConsole(sc);
                        int counter = 0;

                        secondController = 10;

                        while (secondController == 10) {
                            System.out.println("Press 0 to add participant");
                            System.out.println("Press 1 to carry on");
                            secondController = mainApp.getIntFromConsole(sc);

                            if (secondController == 0) {
                                System.out.println("Enter user name");
                                tempUserName = sc.next();
                                secondController = 10;
                                if (mainApp.getParticipant(tempUserName) == null)
                                    System.out.println("no user named " + tempUserName);
                                else
                                    tempParticipantsPool.add(mainApp.getParticipant(tempUserName));
                            } else
                                secondController = 1;
                        }

                        System.out.println("Press 0 to get time and place automatically");
                        System.out.println("Press 1 to get time and place manually");

                        secondController = mainApp.getIntFromConsole(sc);
                        if (secondController == 0) {
                            log.info("automatic time setting picked");
                            MeetingTime aaa = mainApp.setMostCloseMeeting(tempParticipantsPool, new GregorianCalendar(), tempMeetingLen, tempMeetingName);
                            if (aaa != null) {
                                System.out.println("Meeting has been set to:");
                                aaa.print();
                            } else {
                                System.out.println("No chance to set meeting in 90 days");
                            }
                        } else {
                            tempMeetingParams = mainApp.getMeetingTimeFromConsole(sc);
                            log.info("manual time setting picked");
                            //check is busy
                            int ccc = 0;
                            MeetingTime myTime = new MeetingTime(tempMeetingParams[0], tempMeetingParams[1], tempMeetingParams[2],
                                    tempMeetingParams[3], tempMeetingParams[4], tempMeetingLen);

                            while (ccc == 0) {
                                if (mainApp.areUsersBusy(tempParticipantsPool, myTime)) {
                                    System.out.println("Users are busy in that time");
                                    tempMeetingParams = mainApp.getMeetingTimeFromConsole(sc);
                                    myTime = new MeetingTime(tempMeetingParams[0], tempMeetingParams[1], tempMeetingParams[2],
                                            tempMeetingParams[3], tempMeetingParams[4], tempMeetingLen);

                                } else {
                                    Room rm = mainApp.getAvailableRoom(myTime);
                                    if (rm != null) {
                                        ccc = 1;
                                        //set name and time
                                        Meeting myMeeting = new Meeting(tempMeetingName, tempMeetingParams[0], tempMeetingParams[1], tempMeetingParams[2],
                                                tempMeetingParams[3], tempMeetingParams[4], tempMeetingLen);
                                        //set participants
                                        myMeeting.addParticipantsList(tempParticipantsPool);
                                        //add meeting
                                        rm.addMeeting(myMeeting);
                                    } else {
                                        System.out.println("No available room");
                                        tempMeetingParams = mainApp.getMeetingTimeFromConsole(sc);
                                        myTime = new MeetingTime(tempMeetingParams[0], tempMeetingParams[1], tempMeetingParams[2],
                                                tempMeetingParams[3], tempMeetingParams[4], tempMeetingLen);
                                    }
                                }
                            }
                        }
                        break;
                    case 9:
                        // print the room
                        System.out.println("Type room name");
                        tempRoomName = sc.next();
                        if (mainApp.getRoom(tempRoomName) == null) {
                            System.out.println("There is no room named " + tempRoomName);
                        } else {
                            mainApp.printRoom(tempRoomName);
                        }
                        break;
                    case 10:
                        // print all meetings
                        System.out.println("All meetings:");
                        mainApp.printAllMeetings();
                        break;
                    case 11:
                        // remove the meeting
                        System.out.println("Type meeting name:");
                        tempMeetingName = sc.next();
                        if (mainApp.isMeeting(tempMeetingName)) {
                            mainApp.removeMeeting(tempMeetingName);
                        } else {
                            System.out.println("There is no meeting named " + tempMeetingName);
                            log.info("Tried to remove not existed meeting " + tempMeetingName);
                        }
                        break;
                    case 12:
                        // add user to meeting
                        System.out.println("Type user name");
                        tempUserName = sc.next();
                        if (mainApp.getParticipant(tempUserName) == null)
                            mainApp.addUser(tempUserName);

                        System.out.println("Type meeting name");
                        tempMeetingName = sc.next();
                        while (!mainApp.isMeeting(tempMeetingName)) {
                            System.out.println("There is no meeting named" + tempMeetingName);
                            System.out.println("Type meeting name");
                            tempMeetingName = sc.next();
                        }
                        Room tempRoom = mainApp.getRoomWithMeeting(tempMeetingName);
                        Participant tempParticipant = mainApp.getParticipant(tempUserName);

                        if (tempParticipant.isBusy(tempRoom.getMeeting(tempMeetingName).getMeetingTime()))
                            System.out.println("User not available that time");
                        else
                            tempRoom.addParticipantToMeeting(tempMeetingName, tempParticipant);

                        break;
                    case 13:
                        // remove participant from the meeting
                        System.out.println("Type user name");
                        tempUserName = sc.next();
                        if (mainApp.getParticipant(tempUserName) == null)
                            mainApp.addUser(tempUserName);

                        System.out.println("Type meeting name");
                        tempMeetingName = sc.next();
                        while (!mainApp.isMeeting(tempMeetingName)) {
                            System.out.println("There is no meeting named" + tempMeetingName);
                            System.out.println("Type meeting name");
                            tempMeetingName = sc.next();
                        }
                        Room tRoom = mainApp.getRoomWithMeeting(tempMeetingName);
                        Meeting tMeeting = tRoom.getMeeting(tempMeetingName);

                        if (tMeeting != null) {
                            tRoom.removeParticipantFromMeeting(tempMeetingName, tempUserName);
                        } else
                            System.out.println("no meeting in room");
                        break;
                    case 14:
                        // change date
                        System.out.println("Type meeting name");
                        tempMeetingName = sc.next();
                        while (!mainApp.isMeeting(tempMeetingName)) {
                            System.out.println("There is no meeting named" + tempMeetingName);
                            System.out.println("Type meeting name");
                            tempMeetingName = sc.next();
                        }
                        Room ttRoom = mainApp.getRoomWithMeeting(tempMeetingName);

                        tempMeetingParams = mainApp.getMeetingTimeFromConsole(sc);
                        if (ttRoom.changeMeetingDate(tempMeetingName, tempMeetingParams[0],
                                tempMeetingParams[1], tempMeetingParams[2], tempMeetingParams[3], tempMeetingParams[4])) {
                            System.out.println("Date changed");
                        } else
                            System.out.println("Date can't be changed");

                        break;
                    case 15:
                        //save session basic
                        System.out.println("Press 0 to save to default file named session.out ");
                        System.out.println("Press 1 to save to your own file ");

                        int cc = mainApp.getIntFromConsole(sc);
                        if (cc == 0) {
                            mainApp.saveObject("session.out");
                        } else {
                            System.out.println("Type file name");
                            mainApp.saveObject(sc.next());
                        }

                        break;

                    case 16:
                        //load session
                        System.out.println("Press 0 to load from default file named session.out ");
                        System.out.println("Press 1 to load from your own file ");
                        if (mainApp.getIntFromConsole(sc) == 0) {
                            mainApp = mainApp.loadObject("session.out");
                        } else {
                            System.out.println("Type file name");
                            mainApp = mainApp.loadObject(sc.next());
                        }
                        break;

                    default:
                        controller = 20;//set to exit value
                }

            } catch (ClassNotFoundException e) {
                System.out.println("Error while file loading object");
            } catch (IOException e) {
                //  Block of code to handle errors
                System.out.println("Error while writing to file");
                e.printStackTrace();

            } catch (InputMismatchException e) {
                //  Block of code to handle errors
                System.out.println("Incorrect data format");

            } catch (MeetingInitException e) {
                //  Block of code to handle errors
                e.printStackTrace();
            } finally {
                //System.out.println("The 'try catch' is finished.");
            }
        }
    }

    public int[] getMeetingTimeFromConsole(Scanner myc) {
        int[] tempRez = {2019, 2, 18, 10, 0};
        System.out.println("Meeting time :");
        String[] lines = {"Type a year(ex. 2019)",
                "Type the month from 0-11 (0-jan 11-dec)",
                "Type the day", "Type the hour", "Type the minute"};
        int[] boundaries = {2019, 2050, 0, 11, 1, 31, 0, 23, 0, 59};
        for (int i = 0; i < 5; i++) {
            System.out.println(lines[i]);
            while ((!myc.hasNextInt())) {
                myc.next();
                System.out.println("Enter correct number");
            }
            tempRez[i] = myc.nextInt();
            if (tempRez[i] < boundaries[2 * i] || tempRez[i] > boundaries[2 * i + 1]) {
                System.out.println("Enter correct number");
                i--;
            }
        }
        log.info("get temp rez with " + tempRez[0] + " " + tempRez[1] + " " + tempRez[2] + " " + tempRez[3] + " " + tempRez[4]);
        return tempRez;
    }

    public double getMeetingLenFromConsole(Scanner myc) {
        System.out.println("Enter length in hrs");
        double tempLen = 1.0;
        while (!myc.hasNextDouble()) {
            // sc.reset();
            myc.next();
            System.out.println("Enter correct number");
        }
        tempLen = myc.nextDouble();
        log.info("get temp let from console " + tempLen);
        return tempLen;
    }

    public int getIntFromConsole(Scanner myc) {
        int t;
        while (!myc.hasNextInt()) {
            // sc.reset();
            myc.next();
            System.out.println("Enter correct number");
        }
        t = myc.nextInt();
        log.info("get temp int from console " + t);
        return t;
    }

    public void createMeetingTime(MeetingTime curMeetingTime) {
        System.out.println("Enter meeting start date");
        System.out.println("Format:");
        new MeetingTime(2018, 11, 4, 6, 0, -2);
        log.info("Meeting time created " + curMeetingTime);
    }

    public Room getRoom(String roomName) {
        for (int i = 0; i < this.rooms.size(); i++)
            if (this.rooms.get(i).getName().equals(roomName))
                return this.rooms.get(i);
        return null;
    }

    public Room getRoomWithMeeting(String meetingName) {
        for (int i = 0; i < this.rooms.size(); i++)
            if (this.rooms.get(i).isMeeting(meetingName))
                return this.rooms.get(i);
        return null;
    }

    public void removeRoom(String roomName) {
        if (getRoom(roomName) != null) {
            rooms.remove(getRoom(roomName));
            log.info("removed room " + roomName + " ");
        }
    }

    public void addRoom(String roomName) {
        this.rooms.add(new Room(roomName));
        log.info("added room " + roomName);
    }

    public void addUser(String userName) {
        this.participants.add(new Participant(userName));
        log.info("added user " + userName);
    }

    public Participant getParticipant(String userName) {
        for (int i = 0; i < this.participants.size(); i++)
            if (this.participants.get(i).getName().equals(userName))
                return this.participants.get(i);
        return null;
    }

    public void removeUser(String userName) {
        if (getParticipant(userName) != null) {
            participants.remove(getParticipant(userName));
            log.info("removed user " + userName + " ");
        }
    }

    public boolean areUsersBusy(ArrayList<Participant> pool, MeetingTime tMeeting) {
        for (int i = 0; i < pool.size(); i++) {
            if (pool.get(i).isBusy(tMeeting)) {
                return true;
            }
        }
        return false;
    }

    public Room getAvailableRoom(MeetingTime tM) {
        for (int i = 0; i < this.rooms.size(); i++) {
            if (this.rooms.get(i).isTimeAavailable(tM)) {
                log.info("get room" + this.rooms.get(i) + " for the time" + tM.toString());
                return this.rooms.get(i);
            }
        }

        log.info("no room get for the time" + tM.toString());
        return null;
    }

    public void removeMeeting(String mName) {
        Room tRoom = this.getRoomWithMeeting(mName);
        if (tRoom != null) {
            tRoom.removeMeeting(mName);
            log.info("removed meeting " + mName + " ");
        }
    }

    //meetingTime-set null=no chance to set in 90days
    public MeetingTime setMostCloseMeeting(ArrayList<Participant> pool, GregorianCalendar startTime, double lenInHrs, String meetingName) {
        GregorianCalendar today = new GregorianCalendar();
        today = startTime;
        long potStartInMillis = today.getTimeInMillis();
        long rezStartInMillis = potStartInMillis;

        long lenInMillis = (long) (MeetingTime.oneHour * lenInHrs);
        long shift = MeetingTime.oneHour / 6;
        long limit = today.getTimeInMillis() + MeetingTime.oneHour * 24 * 90;

        Room tempRoom = null;
        MeetingTime myMT = new MeetingTime(lenInMillis);

        while (myMT.getStartDate().getTimeInMillis() < limit) {
            if (!this.areUsersBusy(pool, myMT)) {
                tempRoom = this.getAvailableRoom(myMT);
                if (tempRoom != null) {
                    Meeting myM = new Meeting(meetingName, myMT);
                    myM.addParticipantsList(pool);
                    tempRoom.addMeeting(myM);
                    return myMT;
                }
            }
            myMT.shiftMeetingTime(shift);
        }
        return null;
    }

    public ArrayList<String> getAllMeetingsNames() {
        ArrayList<String> tempList = new ArrayList<>();
        for (int i = 0; i < this.rooms.size(); i++) {
            for (int j = 0; j < this.rooms.get(i).meetings.size(); j++)
                tempList.add(this.rooms.get(i).meetings.get(j).getName());
        }
        return tempList;
    }

    public boolean isMeeting(String tempName) {
        ArrayList<String> tempList = this.getAllMeetingsNames();
        for (int i = 0; i < tempList.size(); i++) {
            if (tempList.get(i).equals(tempName))
                return true;
        }
        return false;
    }

    public void saveObject(String fileName) throws IOException {
        FileOutputStream fos = new FileOutputStream(fileName);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(this);
        oos.flush();
        oos.close();
    }

    public App loadObject(String fileName) throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(fileName);
        ObjectInputStream oin = new ObjectInputStream(fis);
        return (App) oin.readObject();
    }

    public void printRoom(String roomName) {
        this.getRoom(roomName).print();
    }

    public void printAllRooms() {
        if (this.rooms.size() < 1)
            System.out.println("No rooms yet");

        for (int i = 0; i < this.rooms.size(); i++)
            this.rooms.get(i).print();
    }

    public void printAllUsers() {
        for (int i = 0; i < this.participants.size(); i++)
            this.participants.get(i).print();
    }

    public void printAllMeetings() {
        for (int i = 0; i < this.rooms.size(); i++)
            for (int j = 0; j < this.rooms.get(i).meetings.size(); j++)
                this.rooms.get(i).meetings.get(j).print();
    }
}
