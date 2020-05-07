package bll.PdfGen;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import dao.ClientDAO;
import model.Client;


import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * This class generates the pdf format of all the clients currently in the database
 */
public class PdfGeneratorClient {

    Document document ;
    List< Client > list;

    ClientDAO clientDAO=new ClientDAO();


    private void addHeader(PdfPTable table) {
                table.addCell("id" );
                table.addCell("Name" );
                table.addCell("City" );
    }

    private void addRows(PdfPTable table) {

for(int i=0;i<list.size();i++)
{
    table.addCell(""+ list.get(i).getId());
    table.addCell(""+ list.get(i).getName());
    table.addCell(""+ list.get(i).getCity());
}


    }

    /**
     * This function generates the pdf format of all the clients currently in the database
     *
     * @param file  where to write the pdf file
     * @param columnNumber how many columns the table will have
     * @throws DocumentException
     * @throws IOException
     */
    public PdfGeneratorClient(String file, int columnNumber) throws DocumentException, IOException {

         this.list = clientDAO.findAll();


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
