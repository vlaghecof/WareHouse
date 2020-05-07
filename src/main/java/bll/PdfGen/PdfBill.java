package bll.PdfGen;


import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import model.OrderItem;
import model.Products;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.lang.annotation.Documented;
import java.util.List;

/**
 * This class generates the Pdf bill that each customer is receiving after placing an order
 */
public class PdfBill {
    private int index=1;

    /**
     * This class generates a bill with the command that a person has placed , or it sends an error message in case the
     * order could not pe brocessed
     * @param order the order that the client is placing
     * @param list  the list of products that the client has already placed
     * @param totalPrice  the total price of the order
     * @param Succesfull_bill an indicator if the order could be or nor processed
     */
    public PdfBill(OrderItem order,List<OrderItem>list,double totalPrice ,boolean Succesfull_bill)
    {
        Document document = new Document();
        String bill ;
                try
                {
                     bill =  String.format("Bill_%s",order.getClientName());

                    if(Succesfull_bill) {
                        Font font = FontFactory.getFont(FontFactory.COURIER, 35, BaseColor.BLACK);
                        PdfWriter.getInstance(document, new FileOutputStream(bill+".pdf"));
                        document.open();
                        document.add(new Chunk("Bill", font));
                        document.add(new Paragraph("\n"));

                        document.add(new Paragraph("Client : " + order.getClientName() + ".\n\n"));
                        document.add(new Paragraph("Has ordered the following products : \n\n"));

                        com.itextpdf.text.List orderedList = new com.itextpdf.text.List(com.itextpdf.text.List.ORDERED);
                        list.forEach((item) -> orderedList.add(new ListItem(item.getProductName())));

                        document.add(orderedList);

                        document.add(new Paragraph("\n"));
                        document.add(new Paragraph("With the total cost of : " + totalPrice));

                    }
                    else
                    {
                        bill =  String.format("Bill_%s_error",order.getClientName());
                        PdfWriter.getInstance(document, new FileOutputStream(bill+".pdf"));
                        document.open();
                        Font font = FontFactory.getFont(FontFactory.COURIER, 20, BaseColor.RED);
                        document.add(new Paragraph("\n"));
                        document.add(new Chunk("We are sorry ,but the order ' "+order.getClientName()+" "+order.getProductName()+" " +order.getQuantity()+ "' could not be made ", font));
                    }

                    document.close();

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (DocumentException e) {
                    e.printStackTrace();
                }


    }


}
