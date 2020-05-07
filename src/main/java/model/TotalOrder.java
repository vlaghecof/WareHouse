package model;

/**
 * This class holds infomation about a customer and the total ammount it spend
 */
public class TotalOrder  {

    private  int id ;
    private   String name;
    private  double totalAmmount;

    public TotalOrder(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "TotalOrder{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", totalAmmount=" + totalAmmount +
                '}';
    }

    public TotalOrder() {

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

    public double getTotalAmmount() {
        return totalAmmount;
    }

    public void setTotalAmmount(double totalAmmount) {
        this.totalAmmount = totalAmmount;
    }

    /**
     * The constructor of this class
     * @param name
     * @param totalAmmount
     */
    public TotalOrder(String name, double totalAmmount) {
        this.name = name;
        this.totalAmmount = totalAmmount;
    }
}
