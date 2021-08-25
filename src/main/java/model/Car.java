package model;

public class Car {

    private final int id;
    private final String name;
    private final int companyID;
    private boolean isRented;

    public Car(int id, String name, int companyID, boolean isRented) {
        this.id = id;
        this.name = name;
        this.companyID = companyID;
        this.isRented = isRented;
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

    public boolean isRented() {
        return isRented;
    }

    public void setRented(boolean rented) {
        isRented = rented;
    }

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", companyID=" + companyID +
                ", isRented=" + isRented +
                '}';
    }
}
