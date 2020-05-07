package model;

/**
 * This class is responsible for modeling a Client
 * @author Vlad Cofaru
 *
 */
public class Client {

    private int id;
    private String name;
    private String city;

    public Client() {

    }

    public Client(String name) {
        this.name = name;
        this.city=null;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", city='" + city + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Client(String name, String city) {
        this.name = name;
        this.city = city;
    }

    /**
     * The constructor for this class
     * @param id
     * @param name
     * @param city
     */
    public Client(int id, String name, String city) {
        this.id = id;
        this.name = name;
        this.city = city;
    }
}
