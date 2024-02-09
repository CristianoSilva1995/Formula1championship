import java.io.*;
import java.util.*;

public class Formula1ChampionshipManager implements ChampionshipManager{
    final int NUMBER_OF_CARS = 10;

    static final int MAX_FIELDS_PER_DRIVER = 8;
    static final int INDEX_FIRST_NAME = 0;
    static final int INDEX_LAST_NAME = 1;
    static final int INDEX_TEAM = 2;
    static final int INDEX_COUNTRY = 3;
    static final int INDEX_POINTS = 4;
    static final int INDEX_FIRST_POS = 5;
    static final int INDEX_SECOND_POS = 6;
    static final int INDEX_THIRD_POS = 7;

    static ArrayList<Formula1Driver> drivers = new ArrayList<>();

    @Override
    public void race() {
        final int[] POINTS_SYSTEM = {25, 18, 15, 12, 10, 8, 6, 4, 2, 1};
        ArrayList<Integer> driversPointsAttributed = new ArrayList<>();
        int carFinished = 0;
        while (carFinished < 10){
            int driverCrossFinishLine = randomPosition(NUMBER_OF_CARS);
            if(!(driversPointsAttributed.contains(driverCrossFinishLine))){
                if(carFinished == 0){ drivers.get(driverCrossFinishLine).setFirstPositionTimes();}
                else if (carFinished == 1) { drivers.get(driverCrossFinishLine).setSecondPositionTimes();}
                else if (carFinished == 2) {drivers.get(driverCrossFinishLine).setThirdPositionTimes();}
                driversPointsAttributed.add(driverCrossFinishLine);
                drivers.get(driverCrossFinishLine).updatePoints(POINTS_SYSTEM[carFinished]);
                //output table position name points
                System.out.println((carFinished + 1) + " - " + drivers.get(driverCrossFinishLine).getFirstName() + " " + drivers.get(driverCrossFinishLine).getLastName() + "" +
                        " => " + POINTS_SYSTEM[carFinished] + " points");
                carFinished++;
            }
        }
    }

    @Override
    public void menu() {
        System.out.print("""
                  \s
                   Welcome to Formula 1 Championship
                ---------------------------------------
                   1 - Race
                   2 - Display Leadership Board
                   3 - Driver statistics
                   4 - Register a Driver
                   5 - Delete a Driver
                   6 - Update Driver (Change Driver Name)
                   7 - Update Driver (Change Team)
                   8 - Store data
                   9 - Load data
                   10 - Load Interface (GUI)\s
                   11 - Start new Season

                   0 - Exit Application
                ---------------------------------------
                   Option:\s""");
    }

    @Override
    public int randomPosition(int maxRange) {
        Random rand = new Random();
        return rand.nextInt(maxRange);
    }


    public static void main(String[] args){
        Formula1ChampionshipManager accessPoint = new Formula1ChampionshipManager();
        final String INTEGER_ERROR_MESSAGE = "Invalid input. Please insert a valid integer!";
        Scanner scan = new Scanner(System.in);
        int menu_option = -1;

        ArrayList <Formula1Driver> copyDriversToOrder;
        copyDriversToOrder = drivers;

        accessPoint.initialiseApp();

        do{
            accessPoint.menu();
            if(scan.hasNextInt()){
                menu_option = scan.nextInt();
            }else{
                System.out.println(INTEGER_ERROR_MESSAGE);
                scan.next();
            }

            switch (menu_option){
                case 1 :
                    System.out.println("Lets race!");
                    accessPoint.race();
                    break;
                case 2 :
                    Collections.sort(copyDriversToOrder, Formula1Driver.formula1DriverDescComparator);
                    System.out.println("\n----- Leadership Board -----" );
                    int driverCounter = 1;
                    for(Formula1Driver f : copyDriversToOrder){
                        System.out.println("\nDriver " + driverCounter);
                        f.displayLeadershipBoard();
                        driverCounter++;
                    }
                    break;
                case 3 :
                    System.out.println();
                    accessPoint.displayArray();
                    System.out.print("Select Driver ID: ");
                    int driverID;
                    if(scan.hasNextInt()){
                        driverID = scan.nextInt();
                        driverID--;
                        drivers.get(driverID).displayStatistics();
                    }else {
                        System.out.println(INTEGER_ERROR_MESSAGE);
                        scan.next();
                    }
                    break;
                case 4 :
                    System.out.println("\n------ Register a Driver -------");
                    System.out.print("How many drivers would you like to add? ");
                    int nDriversToAdd = scan.nextInt();
                    scan.nextLine();
                    for(int i = 0; i < nDriversToAdd; i++){
                        System.out.println("\nDriver " + (i+1) + "\n");
                        System.out.print("  First Name: ");
                        String firstName = scan.nextLine();
                        System.out.print("  Last Name: ");
                        String lastName = scan.nextLine();
                        String team;
                        do{
                            System.out.print("  Team: ");
                            String tempTeam = scan.nextLine();
                            if(accessPoint.verifyIfTeamExists(tempTeam)){
                                System.out.println("\nTeam (" + tempTeam + ") has been already registered.\nPlease enter a new team.");
                            }else {
                                team = tempTeam;
                                break;
                            }
                        }while (true);
                        System.out.print("  Country: ");
                        String country = scan.nextLine();
                        drivers.add(new Formula1Driver(firstName, lastName, team, country));
                    }
                    break;
                case 5 :
                    System.out.println("---- Delete a Driver ----");
                    accessPoint.displayArray();

                    do{
                        System.out.print("Driver ID: ");
                        if(scan.hasNextInt()){
                            driverID = scan.nextInt();
                            driverID--;
                            break;
                        } else {
                            System.out.println("\n  " + INTEGER_ERROR_MESSAGE);
                            scan.next();
                        }
                    }while(true);
                    if(accessPoint.deleteDriver(driverID)){
                        System.out.println("Driver, was removed successfully!");
                    }
                    break;
                case 6 :
                    System.out.println("---- Update Driver Name ----");
                    accessPoint.displayArray();
                    System.out.print("\n  Driver ID: ");
                    driverID = scan.nextInt();
                    driverID--;
                    scan.nextLine();
                    System.out.print("  First Name: ");
                    String newFirstName = scan.nextLine();
                    System.out.print("  Last Name: ");
                    String newLastName = scan.nextLine();
                    if(accessPoint.updateDriver(driverID, newFirstName, newLastName)){
                        System.out.println("Driver, " + drivers.get(driverID).getFirstName() + " " + drivers.get(driverID).getLastName() +
                                " was updated successfully");
                    }else {
                        System.out.println("There was a team match, insert a different team name.");
                    }
                    break;
                case 7 :
                    //add team in display

                    System.out.println("---- Change Team ----");
                    accessPoint.displayArray();
                    System.out.print("\n  Driver ID: ");
                    driverID = scan.nextInt();
                    driverID--;
                    scan.nextLine();
                    System.out.print("  New Team Name: ");
                    String newTeam = scan.nextLine();
                    if(accessPoint.updateDriver(driverID, newTeam)){
                        System.out.println("Driver, " + drivers.get(driverID).getFirstName() + " " + drivers.get(driverID).getLastName() +
                                " was updated successfully");
                    }else {
                        System.out.println("There was a team match, insert a different team name.");
                    }
                    break;
                case 8 :
                    System.out.println("\n------------------------------------------");
                    if(accessPoint.saveInFile()){
                        System.out.println("  Information was stored successfully!");
                    }else {
                        System.out.println(" An error occurred while saving the data!");
                    }
                    System.out.println("------------------------------------------");
                    break;
                case 9 :
                    System.out.println("\n------------------------------------------");
                    if(accessPoint.loadFromFile()){
                        System.out.println("  Information was loaded successfully!");
                    }else {
                        System.out.println(" An error occurred while loading the data!");
                    }
                    System.out.println("------------------------------------------");
                    break;
                case 10 :
                    GUI.createTable();
                    break;
                case 11 :
                    System.out.println("\n---------------------------------------");
                    System.out.println("       New season has started!");
                    System.out.println("---------------------------------------");
                    accessPoint.startNewSeason();
                    break;
            }
        }while(menu_option != 0);
    }
    public void displayArray(){
        int counter = 1;
        for(Formula1Driver f : drivers){
            System.out.println("Driver " + counter +
                    "\n   First Name: " + f.getFirstName() +
                    "\n   Last Name:  " + f.getLastName() +
                    "\n   Team: " + f.getTeam());
            System.out.println();
            counter++;
        }
    }

    public void startNewSeason(){
        for(Formula1Driver f : drivers){
            f.resetPoints();
        }
    }

    public boolean verifyIfTeamExists(String teamName){
        for(Formula1Driver f : drivers){
            if(f.getTeam().equals(teamName)){
                return true;
            }
        }
        return false;
    }

    public boolean deleteDriver(int index){
        drivers.remove(index);
        System.out.println(index);
        return true;
    }

    public boolean updateDriver(int index, String newTeam){
        if(!(verifyIfTeamExists(newTeam))){
            drivers.get(index).updateTeam(newTeam);
            return true;
        }
        return false;
    }

    public boolean updateDriver(int index, String newFirstName, String newLastName){
        drivers.get(index).updateName(newFirstName, newLastName);
        return true;

    }

    public boolean saveInFile(){
        String fileName = "data/dataFormula1.txt";
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
            for(Formula1Driver driverObj : drivers){
                writer.append(driverObj.getFirstName() + "/" + driverObj.getLastName() + "/" + driverObj.getTeam() + "/"
                        + driverObj.getCountry() + "/" + driverObj.getPointsEarned() + "/" + driverObj.getFirstPositionTimes() +
                        "/" + driverObj.getSecondPositionTimes() + "/" + driverObj.getThirdPositionTimes());
                writer.append("\n");
            }
            writer.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean loadFromFile(){
        File file = new File("data/dataFormula1.txt");
        try {
            Scanner reader = new Scanner(file);

            String[] data = new String[MAX_FIELDS_PER_DRIVER];

            while (reader.hasNext()){

                String getLine = reader.nextLine();
                data = getLine.split("/");
                drivers.add(new Formula1Driver(data[INDEX_FIRST_NAME],data[INDEX_LAST_NAME], data[INDEX_TEAM], data[INDEX_COUNTRY], data[INDEX_POINTS], data[INDEX_FIRST_POS], data[INDEX_SECOND_POS], data[INDEX_THIRD_POS]));
            }
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void initialiseApp(){
        loadFromFile();
    }
}
