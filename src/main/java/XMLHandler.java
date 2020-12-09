import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.FileWriter;
import java.io.IOException;

public class XMLHandler extends DefaultHandler {

    private final boolean obfuscate;
    private static FileWriter fileWriter;
    private static XMLStreamWriter xmlStreamWriter;

    public XMLHandler(boolean obfuscate) throws IOException, XMLStreamException {
        this.obfuscate = obfuscate;
        fileWriter = new FileWriter("output.xml");
        XMLOutputFactory xmlOutputFactory = XMLOutputFactory.newInstance();
        xmlStreamWriter = xmlOutputFactory.createXMLStreamWriter(fileWriter);
    }

    public XMLHandler(boolean obfuscate, String outputFileName) throws IOException, XMLStreamException {
        this.obfuscate = obfuscate;
        fileWriter = new FileWriter(outputFileName);
        XMLOutputFactory xmlOutputFactory = XMLOutputFactory.newInstance();
        xmlStreamWriter = xmlOutputFactory.createXMLStreamWriter(fileWriter);
    }

    @Override
    public void startDocument() {
        try {
            xmlStreamWriter.writeStartDocument();
            xmlStreamWriter.writeCharacters("\n");
        } catch (XMLStreamException exception) {
            System.out.println("CANNOT READ START OF XML FILE.");
            exception.printStackTrace();
        }
    }

    @Override
    public void endDocument() {
        try {
            xmlStreamWriter.writeEndDocument();
            xmlStreamWriter.flush();
            xmlStreamWriter.close();
            fileWriter.close();
            System.out.println("PROCESS COMPLETED.");
        } catch (XMLStreamException | IOException exception) {
            System.out.println("CANNOT READ END OF XML FILE.");
            exception.printStackTrace();
        }
    }

    @Override
    public void startElement(String uri, String localName,
                             String qName, Attributes atts) {
        try {
            xmlStreamWriter.writeStartElement(qName);
            if (atts != null) {
                for (int i = 0, len = atts.getLength(); i < len; i++) {
                    if (obfuscate)
                        xmlStreamWriter.writeAttribute(atts.getQName(i), Obfuscator.obfuscate(atts.getValue(i)));
                    else
                        xmlStreamWriter.writeAttribute(atts.getQName(i), Obfuscator.unobfuscate(atts.getValue(i)));
                }
            }
        } catch (XMLStreamException exception) {
            System.out.println("CANNOT READ ELEMENT: " + qName);
            exception.printStackTrace();
        }
    }

    @Override
    public void endElement(String uri, String localName,
                           String qName) {
        try {
            xmlStreamWriter.writeEndElement();
        } catch (XMLStreamException exception) {
            System.out.println("CANNOT READ ELEMENT: " + qName);
            exception.printStackTrace();
        }
    }

    @Override
    public void characters(char[] ch, int start, int len) {
        String str = new String(ch, start, len);
        String trimmedStr = str.trim();
        try {
            if (!trimmedStr.isEmpty()) {
                if (obfuscate)
                    xmlStreamWriter.writeCharacters(Obfuscator.obfuscate(trimmedStr));
                else
                    xmlStreamWriter.writeCharacters(Obfuscator.unobfuscate(trimmedStr));
            } else {
                xmlStreamWriter.writeCharacters(str);
            }
        } catch (XMLStreamException exception) {
            System.out.println("CANNOT READ CHARACTERS: " + str);
            exception.printStackTrace();
        }
    }
}
