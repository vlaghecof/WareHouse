package bll.PdfGen;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import dao.ProductsDAO;
import model.Client;

import model.Products;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.util.List;


/**
 * This class is responsible for generating the pfd report of all the products in the database
 */
public class PdfGeneratorProduct{

    Document document ;
    List<Products> list;
    ProductsDAO productsDAO= new ProductsDAO();


    private void addHeader(PdfPTable table) {

            table.addCell("id");
            table.addCell("Name");
            table.addCell("Quantity");
            table.addCell("Price");
    }

    private void addRows(PdfPTable table) {

        for(int i=0;i<list.size();i++)
        {
            table.addCell(""+ list.get(i).getId());
            table.addCell(""+ list.get(i).getName());
            table.addCell(""+ list.get(i).getQuantity());
            table.addCell(""+ list.get(i).getPrice());

        }


    }

    /**
     * It genearates ta table  with all the products in the databse
     * @param file  where to write the pdf file
     * @param columnNumber how many columns the table will have
     * @throws DocumentException
     * @throws IOException
     */
    public PdfGeneratorProduct(String file, int columnNumber) throws DocumentException, IOException {

        this.list = productsDAO.findAll();


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

