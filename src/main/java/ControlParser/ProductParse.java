package ControlParser;


import dao.ProductsDAO;

import model.Products;

/**
 * This class is used to parse the input from the file and generate a Product with that information
 * @author Vlad Cofaru
 *
 */
public class ProductParse {


    public ProductsDAO productsDAO = new ProductsDAO();




    public Products generateProducts (String data )
    {
        String productData[] =  data.split(",");
        Products product = new Products(productData[0].strip(),Integer.parseInt(productData[1].strip()),Float.parseFloat(productData[2].strip()));
        return product;
    }
    public Products generatProdsByName(String data)
    {
        String productData[] =  data.split(",");
        Products product = new Products(productData[0].strip());
        return product;
    }

}
