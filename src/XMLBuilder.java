import org.w3c.dom.*;
import org.xml.sax.SAXException;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

/**
 * Created by Anatoly.Cherkasov on 21.04.14.
 */

public class XMLBuilder {

    Info info = new Info();

    public XMLBuilder() {

    }

    public XMLBuilder( Info info ) {
        this.info = info;
    }

    /* Сгенерим xml на основе info и запишем его в файл */
    public void genStandartXML () {

        try {

            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            // root elements
            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("Report");
            doc.appendChild(rootElement);

            /* version */
            Element version = doc.createElement("version");
            version.appendChild(doc.createTextNode("001.00"));
            rootElement.appendChild(version);

            // Tables
            Element tablesElement = doc.createElement("Tables");
            rootElement.appendChild(tablesElement);

            // Строим на основе коллекции
            for (TableInfo tbl : info.tableInfoArrayList) {
                // Table
                Element tableElement = doc.createElement("Table");
                tablesElement.appendChild(tableElement);

                /* Эта часть строит информацю  о таблице */
                /* Name */
                Element sTblTableName = doc.createElement("sTableName");
                sTblTableName.appendChild(doc.createTextNode(tbl.name));
                tableElement.appendChild(sTblTableName);

                /* Status */
                Element sTblStatus = doc.createElement("sStatus");
                if (tbl.status != null)
                    sTblStatus.appendChild(doc.createTextNode(tbl.status));
                tableElement.appendChild(sTblStatus);

                /* Partitioned */
                Element sTblPartitioned = doc.createElement("sPartitioned");
                if (tbl.partitioned != null)
                    sTblPartitioned.appendChild(doc.createTextNode(tbl.partitioned));
                tableElement.appendChild(sTblPartitioned);

                /* Temporary */
                Element sTblTemporary = doc.createElement("sTemporary");
                if (tbl.temporary != null)
                    sTblTemporary.appendChild(doc.createTextNode(tbl.temporary));
                tableElement.appendChild(sTblTemporary);

                /* Compression */
                Element sTblCompression = doc.createElement("sCompression");
                if (tbl.compression != null)
                    sTblCompression.appendChild(doc.createTextNode(tbl.compression));
                tableElement.appendChild(sTblCompression);

                /* logging */
                Element sTblLogging = doc.createElement("sLogging");
                if (tbl.logging != null)
                    sTblLogging.appendChild(doc.createTextNode(tbl.logging));
                tableElement.appendChild(sTblLogging);

                /* cache */
                Element sTblCache = doc.createElement("sCache");
                if (tbl.cache != null)
                    sTblCache.appendChild(doc.createTextNode(tbl.cache));
                tableElement.appendChild(sTblCache);

                /* table_lock */
                Element sTblTableLock = doc.createElement("sTableLock");
                if (tbl.table_lock != null)
                    sTblTableLock.appendChild(doc.createTextNode(tbl.table_lock));
                tableElement.appendChild(sTblTableLock);

                Element columnsElement = doc.createElement("Columns");
                tableElement.appendChild(columnsElement);

                /* Эта часть строит информацю о колонках таблицы */
                for (ColumnInfo col : tbl.columnInfoArrayList) {
                    Element columnElement = doc.createElement("Column");
                    columnsElement.appendChild(columnElement);

                    // column_name
                    Element sColName = doc.createElement("sColumnName");
                    if (col.name != null)
                        sColName.appendChild(doc.createTextNode(col.name));
                    columnElement.appendChild(sColName);

                    // data_type
                    Element sColDataType = doc.createElement("sDataType");
                    if (col.data_type != null)
                        sColDataType.appendChild(doc.createTextNode(col.data_type));
                    columnElement.appendChild(sColDataType);

                    // data_length
                    Element sColDataLength = doc.createElement("iDataLength");
                    if (col.data_length != null)
                        sColDataLength.appendChild(doc.createTextNode(col.data_length));
                    columnElement.appendChild(sColDataLength);

                    // data_precision
                    Element sColDataPrecision = doc.createElement("iDataPrecision");
                    if (col.data_precision != null)
                        sColDataPrecision.appendChild(doc.createTextNode(col.data_precision));
                    columnElement.appendChild(sColDataPrecision);


                    // data_scale
                    Element sColDataScale = doc.createElement("iDataScale");
                    if (col.data_scale != null)
                        sColDataScale.appendChild(doc.createTextNode(col.data_scale));
                    columnElement.appendChild(sColDataScale);

                    // nullable
                    Element sColNullable = doc.createElement("sNullable");
                    if (col.nullable != null)
                        sColNullable.appendChild(doc.createTextNode(col.nullable));
                    columnElement.appendChild(sColNullable);


                    // default_length
                    Element sColDefaultLength = doc.createElement("iDefaultLength");
                    if (col.default_length != null)
                        sColDefaultLength.appendChild(doc.createTextNode(col.default_length));
                    columnElement.appendChild(sColDefaultLength);
                }
            }

            // write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            // Output to file
            StreamResult result = new StreamResult(new File("D:\\file.xml"));

            // Output to console
            // StreamResult result = new StreamResult(System.out);

            transformer.transform(source, result);

            System.out.println("File saved!");

        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (TransformerException tfe) {
            tfe.printStackTrace();
        }
    }

    /* Заполним info на основе пришедшей строки */
    public void parseStandartXml (String fileName)
    {
        Document doc = null;
        try {
            File fXmlFile = new File(fileName);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.parse(fXmlFile);
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }

        doc.getDocumentElement().normalize();

        // System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

        NodeList tablesList = doc.getElementsByTagName("Table");

        // System.out.println("----------------------------");

        for (int x = 0; x < tablesList.getLength(); x++) {

            Node tbl = tablesList.item(x);

            // System.out.println("\nCurrent Element :" + tbl.getNodeName());

            if (tbl.getNodeType() == Node.ELEMENT_NODE) {

                //System.out.println(" ");
                //System.out.println("------------ tbl ----------------");
                Element eTbl = (Element) tbl;
                /*
                System.out.println("sTableName : " + eTbl.getElementsByTagName("sTableName").item(0).getTextContent());
                System.out.println("sStatus : " + eTbl.getElementsByTagName("sStatus").item(0).getTextContent());
                System.out.println("sPartitioned : " + eTbl.getElementsByTagName("sPartitioned").item(0).getTextContent());
                System.out.println("sTemporary : " + eTbl.getElementsByTagName("sTemporary").item(0).getTextContent());
                System.out.println("sCompression : " + eTbl.getElementsByTagName("sCompression").item(0).getTextContent());
                System.out.println("sLogging : " + eTbl.getElementsByTagName("sLogging").item(0).getTextContent());
                System.out.println("sCache : " + eTbl.getElementsByTagName("sCache").item(0).getTextContent());
                System.out.println("sTableLock : " + eTbl.getElementsByTagName("sTableLock").item(0).getTextContent());
                */
                NodeList columnsList = eTbl.getElementsByTagName("Column");

                // Информация о колонках таблицы
                ArrayList<ColumnInfo> columnInfoArrayList = new ArrayList<ColumnInfo>();
                //
                for (int y = 0; y < columnsList.getLength(); y++) {

                    Node col = columnsList.item(y);

                    // System.out.println("\nCurrent Element :" + col.getNodeName());

                    if (col.getNodeType() == Node.ELEMENT_NODE) {

                        //System.out.println(" ");
                        //System.out.println("------------ col ----------------");
                        Element eCol = (Element) col;
                        /*
                        System.out.println("sColumnName : " + eCol.getElementsByTagName("sColumnName").item(0).getTextContent());
                        System.out.println("sDataType : " + eCol.getElementsByTagName("sDataType").item(0).getTextContent());
                        System.out.println("iDataLength : " + eCol.getElementsByTagName("iDataLength").item(0).getTextContent());
                        System.out.println("iDataPrecision : " + eCol.getElementsByTagName("iDataPrecision").item(0).getTextContent());
                        System.out.println("iDataScale : " + eCol.getElementsByTagName("iDataScale").item(0).getTextContent());
                        System.out.println("sNullable : " + eCol.getElementsByTagName("sNullable").item(0).getTextContent());
                        System.out.println("iDefaultLength : " + eCol.getElementsByTagName("iDefaultLength").item(0).getTextContent());
                        */
                        columnInfoArrayList.add(new ColumnInfo(eCol.getElementsByTagName("sColumnName").item(0).getTextContent(),
                                                               eCol.getElementsByTagName("sDataType").item(0).getTextContent(),
                                                               eCol.getElementsByTagName("iDataLength").item(0).getTextContent(),
                                                               eCol.getElementsByTagName("iDataPrecision").item(0).getTextContent(),
                                                               eCol.getElementsByTagName("iDataScale").item(0).getTextContent(),
                                                               eCol.getElementsByTagName("sNullable").item(0).getTextContent(),
                                                               eCol.getElementsByTagName("iDefaultLength").item(0).getTextContent()));
                    }
                }

                TableInfo ti = new TableInfo();
                ti.setTableInfo(eTbl.getElementsByTagName("sTableName").item(0).getTextContent(),
                                eTbl.getElementsByTagName("sStatus").item(0).getTextContent(),
                                eTbl.getElementsByTagName("sPartitioned").item(0).getTextContent(),
                                eTbl.getElementsByTagName("sTemporary").item(0).getTextContent(),
                                eTbl.getElementsByTagName("sCompression").item(0).getTextContent(),
                                eTbl.getElementsByTagName("sLogging").item(0).getTextContent(),
                                eTbl.getElementsByTagName("sCache").item(0).getTextContent(),
                                eTbl.getElementsByTagName("sTableLock").item(0).getTextContent(),
                                columnInfoArrayList);

                info.tableInfoArrayList.add(ti);
            }
        }
    }
}
