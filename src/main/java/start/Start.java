package start;


import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.itextpdf.text.DocumentException;
import ControlParser.FileParser;

/**
 * This class represents the main function
 * @author Vlad Cofaru
 *
 */
public class Start {
	protected static final Logger LOGGER = Logger.getLogger(Start.class.getName());

	public static void main(String[] args) throws SQLException, IOException, DocumentException {

		FileParser FP = new FileParser();

		try {
			//FP.readData("input.txt.txt");
			FP.readData(args[0]);


		} catch (Exception ex) {
			LOGGER.log(Level.INFO, ex.getMessage());
		}
	}

}
