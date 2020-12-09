import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.stream.XMLStreamException;
import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        if ((args.length != 2 && args.length != 3) || args[0].length() != 2 || (!args[0].equals("-o") && !args[0].equals("-u"))) {
            System.err.println("USE THESE INPUT ARGUMENTS: -o/-u InputFileName.xml [OutputFileName.xml].");
            System.exit(1);
        }
        boolean obfuscate = args[0].equals("-o");

        try {
            XMLHandler handler = (args.length == 2) ? new XMLHandler(obfuscate) : new XMLHandler(obfuscate, args[2]);
            SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
            SAXParser parser = saxParserFactory.newSAXParser();
            parser.parse(new File(args[1]), handler);
        } catch (ParserConfigurationException | SAXException | IOException | XMLStreamException e) {
            e.printStackTrace();
        }
    }


}
