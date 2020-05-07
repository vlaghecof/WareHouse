
package bll.PdfGen;

        import com.itextpdf.text.Document;
        import com.itextpdf.text.DocumentException;
        import com.itextpdf.text.pdf.PdfPTable;
        import com.itextpdf.text.pdf.PdfWriter;
        import dao.OrderItemDAO;
        import model.Client;
        import model.OrderItem;


        import java.io.FileNotFoundException;
        import java.io.FileOutputStream;
        import java.io.IOException;
        import java.lang.reflect.ParameterizedType;
        import java.util.List;

/**
 * This class generates the the pdf report of all the orders
 */
public class PdfGeneratorOrders {

    Document document ;
    List<OrderItem> list;
    OrderItemDAO orderItem = new OrderItemDAO();


    private void addHeader(PdfPTable table) {

        table.addCell("id");
        table.addCell("ClientName");
        table.addCell("ProductName");
        table.addCell("quantity");
    }

    private void addRows(PdfPTable table) {

        for(int i=0;i<list.size();i++)
        {
            table.addCell(""+ list.get(i).getId());
            table.addCell(""+ list.get(i).getClientName());
            table.addCell(""+ list.get(i).getProductName());
            table.addCell(""+ list.get(i).getQuantity());

        }


    }

    /**
     * this functions generates the report of all the orders that have been placed
     * @param file  where to write the pdf file
     * @param columnNumber how many columns the table will have
     * @throws DocumentException
     * @throws IOException
     */
    public PdfGeneratorOrders (String file, int columnNumber) throws DocumentException, IOException {

        this.list = orderItem.findAll();


        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(file+".pdf"));
        document.open();

        PdfPTable table = new PdfPTable(columnNumber);
        addHeader(table);
        addRows(table);
        document.add(table);
        document.close();



    }





}