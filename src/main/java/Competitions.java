public class Competitions {

    int IdOfCompetition;
    String Name;
    String Location;
    String Data;

    public Competitions(int idOfCompetition, String name, String location, String data) {
        IdOfCompetition = idOfCompetition;
        Name = name;
        Location = location;
        Data = data;
    }

    public Competitions(String name, String location, String data) {
        Name = name;
        Location = location;
        Data = data;
    }

    public int getIdOfCompetition() {
        return IdOfCompetition;
    }

    public void setIdOfCompetition(int idOfCompetition) {
        IdOfCompetition = idOfCompetition;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getData() {
        return Data;
    }

    public void setData(String data) {
        Data = data;
    }

    @Override
    public String toString() {
        return "Competitions{" +
                "IdOfCompetition=" + IdOfCompetition +
                ", Name='" + Name + '\'' +
                ", Location='" + Location + '\'' +
                ", Data='" + Data + '\'' +
                '}';
    }
}
