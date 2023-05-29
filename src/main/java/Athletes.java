public class Athletes {
    int IdOfAthlete;
    String SureName;
    String Name;
    int Age;
    String NameOfTrainer;
    String TypeOfCompet;

    public Athletes(int idOfAthlete, String sureName, String name, int age, String nameOfTrainer, String typeOfCompet) {
        IdOfAthlete = idOfAthlete;
        SureName = sureName;
        Name = name;
        Age = age;
        NameOfTrainer = nameOfTrainer;
        TypeOfCompet = typeOfCompet;
    }

    public Athletes(String sureName, String name, int age, String nameOfTrainer, String typeOfCompet) {
        SureName = sureName;
        Name = name;
        Age = age;
        NameOfTrainer = nameOfTrainer;
        TypeOfCompet = typeOfCompet;
    }

    public int getIdOfAthlete() {
        return IdOfAthlete;
    }

    public void setIdOfAthlete(int idOfAthlete) {
        IdOfAthlete = idOfAthlete;
    }

    public String getSureName() {
        return SureName;
    }

    public void setSureName(String sureName) {
        SureName = sureName;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getAge() {
        return Age;
    }

    public void setAge(int age) {
        Age = age;
    }

    public String getNameOfTrainer() {
        return NameOfTrainer;
    }

    public void setNameOfTrainer(String nameOfTrainer) {
        NameOfTrainer = nameOfTrainer;
    }

    public String getTypeOfCompet() {
        return TypeOfCompet;
    }

    public void setTypeOfCompet(String typeOfCompet) {
        TypeOfCompet = typeOfCompet;
    }


    @Override
    public String toString() {
        return "Athletes{" +
                "IdOfAthlete=" + IdOfAthlete +
                ", SureName='" + SureName + '\'' +
                ", Name='" + Name + '\'' +
                ", Age=" + Age +
                ", NameOfTrainer='" + NameOfTrainer + '\'' +
                ", TypeOfCompet='" + TypeOfCompet + '\'' +
                '}';
    }
}
