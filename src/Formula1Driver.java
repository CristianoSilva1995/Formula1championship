import java.util.Comparator;

public class Formula1Driver extends Driver{
    int pointsEarned;
    int firstPositionTimes;
    int secondPositionTimes;
    int thirdPositionTimes;


    public Formula1Driver(String firstName, String lastName, String team, String country) {
        super(firstName, lastName, team, country);
    }
    public Formula1Driver(String firstName, String lastName, String team, String country, String pointsEarned) {
        super(firstName, lastName, team, country);
        this.pointsEarned = Integer.parseInt(pointsEarned);
    }

    public Formula1Driver(String firstName, String lastName, String team, String country, String pointsEarned, String firstPositionTimes, String secondPositionTimes, String thirdPositionTimes) {
        super(firstName, lastName, team, country);
        this.pointsEarned = Integer.parseInt(pointsEarned);
        this.firstPositionTimes = Integer.parseInt(firstPositionTimes);
        this.secondPositionTimes = Integer.parseInt(secondPositionTimes);
        this.thirdPositionTimes = Integer.parseInt(thirdPositionTimes);
    }

    public void updatePoints(int pointsEarned){
        this.pointsEarned += pointsEarned;
    }

    public void updateTeam(String newTeamName){
        super.setTeam(newTeamName);
    }

    public void updateName(String newFirstName, String newLastName){
        super.setFirstName(newFirstName);
        super.setLastName(newLastName);
    }

    public int getPointsEarned(){
        return pointsEarned;
    }

    public void resetPoints(){
        this.pointsEarned = 0;
    }

    public void setFirstPositionTimes(){
        firstPositionTimes++;
    }

    public void setSecondPositionTimes(){
        secondPositionTimes++;
    }

    public void setThirdPositionTimes(){
        thirdPositionTimes++;
    }

    public int getFirstPositionTimes(){
        return firstPositionTimes;
    }

    public int getSecondPositionTimes(){
        return secondPositionTimes;
    }

    public int getThirdPositionTimes(){
        return thirdPositionTimes;
    }
    public void displayStatistics(){
        System.out.println("  First Name: " + super.getFirstName() +
                "\n  Last Name: " + super.getLastName() +
                "\n  Team: " + super.getTeam() +
                "\n  Country: " + super.getCountry() +
                "\n  Points: " + getPointsEarned()+
                "\n  First Position Times: " + getFirstPositionTimes()+
                "\n  Second Position Times: " + getSecondPositionTimes() +
                "\n  Third Position Times: " + getThirdPositionTimes());
    }
    public void displayLeadershipBoard(){
        System.out.println("  First Name: " +  super.getFirstName() +
                "\n  Last Name: " + super.getLastName() +
                "\n  Team: " + super.getTeam() +
                "\n  Country: " + super.getCountry() +
                "\n  Points: " + pointsEarned +
                "\n");
    }

    public static Comparator<Formula1Driver> formula1DriverDescComparator = new Comparator<Formula1Driver>() {
        @Override
        public int compare(Formula1Driver o1, Formula1Driver o2) {
            int driverPoints1 = o1.getPointsEarned();
            int driverPoints2 = o2.getPointsEarned();

            return driverPoints2 - driverPoints1;
        }
    };

    public static Comparator<Formula1Driver> formula1DriverAscComparator = new Comparator<Formula1Driver>() {
        @Override
        public int compare(Formula1Driver o1, Formula1Driver o2) {
            int driverPoints1 = o1.getPointsEarned();
            int driverPoints2 = o2.getPointsEarned();

            return driverPoints1 - driverPoints2;
        }
    };

    public static Comparator<Formula1Driver> formula1Driver1stComparator = new Comparator<Formula1Driver>() {
        @Override
        public int compare(Formula1Driver o1, Formula1Driver o2) {
            int driverPoints1 = o1.getFirstPositionTimes();
            int driverPoints2 = o2.getFirstPositionTimes();

            return driverPoints2 - driverPoints1;
        }
    };

}
