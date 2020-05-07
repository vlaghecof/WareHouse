package model;

/**
 * This class models the products that are currently in the storage
 */
public class Products {
    private int id;
    private String name;
    private  int quantity;
    private double price;

    public Products() {
    }


    public Products(String name) {
        this.name = name;

    }

    /**
     * This function is the constructor for a product
     * @param name
     * @param quantity
     * @param price
     */
    public Products(String name, int quantity, double price) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    public Products(int id, String name, int quantity, double price) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    @Override
    public String toString() {
        return "Products{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
