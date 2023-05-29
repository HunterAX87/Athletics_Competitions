public class Schedules {

    int IdOfScheduele;
    int IdOfCompettition;
    String Type;
    String Sector;
    String Time;

    public Schedules(int idOfScheduele, int idOfCompettition, String type, String sector, String time) {
        IdOfScheduele = idOfScheduele;
        IdOfCompettition = idOfCompettition;
        Type = type;
        Sector = sector;
        Time = time;
    }

    public Schedules(int idOfCompettition, String type, String sector, String time) {
        IdOfCompettition = idOfCompettition;
        Type = type;
        Sector = sector;
        Time = time;
    }

    public int getIdOfScheduele() {
        return IdOfScheduele;
    }

    public void setIdOfScheduele(int idOfScheduele) {
        IdOfScheduele = idOfScheduele;
    }

    public int getIdOfCompettition() {
        return IdOfCompettition;
    }

    public void setIdOfCompettition(int idOfCompettition) {
        IdOfCompettition = idOfCompettition;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getSector() {
        return Sector;
    }

    public void setSector(String sector) {
        Sector = sector;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    @Override
    public String toString() {
        return "Schedules{" +
                "IdOfScheduele=" + IdOfScheduele +
                ", IdOfCompettition=" + IdOfCompettition +
                ", Type='" + Type + '\'' +
                ", Sector='" + Sector + '\'' +
                ", Time='" + Time + '\'' +
                '}';
    }
}
