public class Citizen {

    private String citizenId;
    private String name;

    public Citizen(String citizenId, String name) {
        this.citizenId = citizenId;
        this.name = name;
    }

    public String getCitizenId() {
        return citizenId;
    }

    public String getName() {
        return name;
    }
}
