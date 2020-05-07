package ControlParser;

import dao.ClientDAO;
import dao.OrderItemDAO;
import model.Client;
import model.OrderItem;

/**
 *
 * This class is used to parse the String information read from the input text and generate a client with the specific
 * data
 *@Author Vlad Cofaru
 */
public class ClientParse {

    public ClientDAO clientDao = new ClientDAO();
    public Client client ;
    public OrderItemDAO orderItemDAO = new OrderItemDAO();


    /**
     * Used to generate a client from the data line
     * @param data line read from the file
     * @return an client
     */
    public Client generateClient(String data)
    {
              String clientData[] = data.split(",");
              Client client = new  Client(clientData[0],clientData[1]);
            return client;

    }


    /**
     * Used to generate an order from the data line
     * @param data line read form the input file
     * @return an order object
     */
    public OrderItem makeOrder(String data)
    {
        String orderData[] = data.split(",");
        OrderItem orderItem = new OrderItem(orderData[0].strip(),orderData[1],Integer.parseInt(orderData[2].strip()));
        return orderItem;
    }


}
