public abstract class Driver {
    private String firstName;
    private String lastName;
    private String team;
    private String country;

    public Driver(String firstName, String lastName, String team, String country){
        this.firstName = firstName;
        this.lastName = lastName;
        this.team = team;
        this.country = country;
    }

    public void displayDrivers(){
        System.out.println("First Name: " + getFirstName() +
                "\nLast Name: " + getLastName() +
                "\nTeam : " + getTeam() +
                "\nCountry: " + getCountry() +
                "\n");
    }

    public String getFirstName(){
        return firstName;
    }

    public String getLastName(){
        return lastName;
    }

    public String getTeam(){
        return team;
    }

    public String getCountry(){
        return country;
    }

    public void setFirstName (String firstName){
        this.firstName = firstName;
    }

    public void setLastName (String lastName){
        this.lastName= lastName;
    }

    public void setTeam (String team){
        this.team = team;
    }

    public void setCountry(String country){
        this.country = country;
    }
}
