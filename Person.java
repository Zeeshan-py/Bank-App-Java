public class Person {
    private String name;
    private int CNIC;
    private int phoneNumber;

    public Person(){
        this.name = "";
        this.CNIC = 0;
        this.phoneNumber = 0;
    }

    public Person(String name, int CNIC, int phoneNumber) {
        this.name = name;
        this.CNIC = CNIC;
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public int getCNIC() {
        return CNIC;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setCNIC(int CNIC) {
        this.CNIC = CNIC;
    }
    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "Name: " + name + "\nCNIC: " + CNIC + "\nPhone Number: " + phoneNumber;
    }
}