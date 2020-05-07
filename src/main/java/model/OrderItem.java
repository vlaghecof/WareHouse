package model;

/**
 * This class is responsible for modelling the orders that a customer can make
 */
public class OrderItem {

    private int id;
    private String clientName;
    private String productName;
    private int quantity ;

    /** The constructor for this class
     * @param clientName
     * @param productName
     * @param quantity
     */
    public OrderItem(String clientName, String productName, int quantity) {
        this.clientName = clientName;
        this.productName = productName;
        this.quantity = quantity;
    }

    public OrderItem() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "id=" + id +
                ", clientName='" + clientName + '\'' +
                ", productName='" + productName + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}
