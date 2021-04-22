package model;

public class Car {

    private int id;
    private String name;
    private int companyID;

    public Car(int id, String name, int companyID) {
        this.id = id;
        this.name = name;
        this.companyID = companyID;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public int getCompanyID() {
        return companyID;
    }

    @Override
    public String toString() {
        return name;
    }
}
