package ControlParser;

import bll.PdfGen.PdfBill;
import bll.PdfGen.PdfGeneratorClient;
import bll.PdfGen.PdfGeneratorOrders;
import bll.PdfGen.PdfGeneratorProduct;
import com.itextpdf.text.DocumentException;
import dao.*;
import model.OrderItem;
import model.Products;
import model.TotalOrder;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 *
 * This class reads from the input file line by line and executes the proper instructions
 * @author Vlad Cofaru
 */
public class FileParser {

    ClientParse clientParse= new ClientParse();
    ProductParse productParse= new ProductParse();

    String line,instruction, data;
    ProductsDAO productsDAO= new ProductsDAO();
    TotalOrderDAO totalOrderDAO  = new TotalOrderDAO();
    OrderItemDAO orderItemDAO= new OrderItemDAO();


    public void executeInsert(Products prod)
    {
        Products aux = productsDAO.findByName(prod.getName());
        System.out.println(aux);

    }


    /**
     * This class is responsible for executing the proper instructions depending on the line read from the file ,
     *
     * @param line the line read from the file
     * @throws IOException
     * @throws DocumentException
     */
    public void executeInstruction(String line) throws IOException, DocumentException { String instruction = null, data = null;

        String commands[]=line.split(":");
        if(line.contains("Report"))
        {
            instruction=line;
            data=null;
        }
        else {
            instruction = commands[0];
            data = commands[1];

        }

        switch (instruction){
            case"Insert client":
                clientParse.clientDao.insert(clientParse.generateClient(data));
                System.out.println("sa introdus "+data + " in clienti");
                break;
            case"Insert product":
                //productParse.porductDao.insert(productParse.generateProduct(data));
                Products prod = productParse.generateProducts(data);
                Products aux = productsDAO.findByName(prod.getName());
                if(aux!=null)
                {
                    prod.setQuantity(prod.getQuantity()+aux.getQuantity());
                    productParse.productsDAO.update(prod);
                }
                else
                {
                    productParse.productsDAO.insert(prod);
                }
                System.out.println("sa introdus "+data + " in produse");
                break;
            case"Delete client":
                    clientParse.clientDao.delete(clientParse.generateClient(data));
                    System.out.println("sa Sters "+data + " din clienti");
                    break;
            case"Delete Product":
              //productParse.porductDao.delete(productParse.generatProdByName(data));
                productParse.productsDAO.delete(productParse.generatProdsByName(data));
                System.out.println("sa Sters "+data + " din produse");
                break;

            case "Order":

                OrderItem order = clientParse.makeOrder(data);
                aux=productsDAO.findByName(order.getProductName());
              if (aux!=null)
              {
                  if(aux.getQuantity()>order.getQuantity()) {
                      aux.setQuantity(aux.getQuantity() - order.getQuantity());
                      productsDAO.update(aux);
                      clientParse.orderItemDAO.insert(order);
                      double totalPrice = (double) order.getQuantity() * aux.getPrice();
                      TotalOrder totalOrder = totalOrderDAO.findByName(order.getClientName());
                      if (totalOrder != null) {
                          totalOrder.setTotalAmmount(totalOrder.getTotalAmmount() + totalPrice);
                          totalOrderDAO.update(totalOrder);
                      } else
                      {       totalOrder =new TotalOrder(order.getClientName(), totalPrice);
                              totalOrderDAO.insert(totalOrder);
                      }
                      ///make bill
                      PdfBill pdfBill = new PdfBill(order,orderItemDAO.FindAllProducts(order.getClientName()),totalOrder.getTotalAmmount(),true);
                  }
                  else
                  {
                      PdfBill pdfBill = new PdfBill(order,null,0,false);

                  }
              }
              else
              {
                  PdfBill pdfBill = new PdfBill(order,null,0,false);
              }

            case"Report client":

                PdfGeneratorClient clientGen= new PdfGeneratorClient("client",3);
                break;
            case"Report order":
                PdfGeneratorOrders generatorOrders= new PdfGeneratorOrders("order",4);
                break;
            case"Report product":
               PdfGeneratorProduct pdfGeneratorProduct = new PdfGeneratorProduct("products",4);
               break;
            default:
                System.out.println("sa intrat pe dafault");
        }

    }

    /**
     * The functions the opens the file and starts reading line by line from the file and sends the information to the
     * executin function
     * @param path  the path towards the input file
     */
    public void readData(String path){
        try {
            File file = new File(path);
            Scanner myReader = new Scanner(file);
            while (myReader.hasNextLine()) {
                line = myReader.nextLine();
                executeInstruction(line);
                System.out.println(line);
            }
            myReader.close();
        } catch (IOException | DocumentException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }


}



