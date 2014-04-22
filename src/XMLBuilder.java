import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

/**
 * Created by Anatoly.Cherkasov on 21.04.14.
 */

public class XMLBuilder {

    Info info;

    public XMLBuilder( Info info ) {
        this.info = info;
    }

    public void genStandartXML () {

        try {

            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();


            // root elements
            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("Table");
            doc.appendChild(rootElement);

            /* Эта часть строит информацю  о таблице */
            /* Name */
            Element sTblTableName = doc.createElement("sTableName");
            sTblTableName.appendChild(doc.createTextNode(info.tableInfo.name));
            rootElement.appendChild(sTblTableName);

            /* Status */
            Element sTblStatus = doc.createElement("sStatus");
            sTblStatus.appendChild(doc.createTextNode(info.tableInfo.status));
            rootElement.appendChild(sTblStatus);

            /* Partitioned */
            Element sTblPartitioned = doc.createElement("sPartitioned");
            sTblPartitioned.appendChild(doc.createTextNode(info.tableInfo.partitioned));
            rootElement.appendChild(sTblPartitioned);

            /* Temporary */
            Element sTblTemporary = doc.createElement("sTemporary");
            sTblTemporary.appendChild(doc.createTextNode(info.tableInfo.temporary));
            rootElement.appendChild(sTblTemporary);

            /* Compression */
            Element sTblCompression = doc.createElement("sCompression");
            sTblCompression.appendChild(doc.createTextNode(info.tableInfo.compression));
            rootElement.appendChild(sTblCompression);

            /* logging */
            Element sTblLogging = doc.createElement("sLogging");
            sTblLogging.appendChild(doc.createTextNode(info.tableInfo.logging));
            rootElement.appendChild(sTblLogging);

            /* cache */
            Element sTblCache = doc.createElement("sCache");
            sTblCache.appendChild(doc.createTextNode(info.tableInfo.cache));
            rootElement.appendChild(sTblCache);

            /* table_lock */
            Element sTblTableLock = doc.createElement("sTableLock");
            sTblTableLock.appendChild(doc.createTextNode(info.tableInfo.table_lock));
            rootElement.appendChild(sTblTableLock);

            Element columnsElement = doc.createElement("Columns");
            rootElement.appendChild(columnsElement);

            /* Эта часть строит информацю о колонках таблицы */

            for (ColumnInfo ci : info.columnInfoArrayList) {
                Element columnElement = doc.createElement("Column");
                columnsElement.appendChild(columnElement);

                // column_name
                Element sColName = doc.createElement("sColumnName");
                if (ci.name != null)
                    sColName.appendChild(doc.createTextNode(ci.name));
                columnElement.appendChild(sColName);

                // data_type
                Element sColDataType = doc.createElement("sDataType");
                if (ci.data_type != null)
                    sColDataType.appendChild(doc.createTextNode(ci.data_type));
                columnElement.appendChild(sColDataType);

                // data_length
                Element sColDataLength = doc.createElement("iDataLength");
                if (ci.data_length != null)
                    sColDataLength.appendChild(doc.createTextNode(ci.data_length));
                columnElement.appendChild(sColDataLength);

                // data_precision
                Element sColDataPrecision = doc.createElement("iDataPrecision");
                if (ci.data_precision != null)
                    sColDataPrecision.appendChild(doc.createTextNode(ci.data_precision));
                columnElement.appendChild(sColDataPrecision);


                // data_scale
                Element sColDataScale = doc.createElement("iDataScale");
                if (ci.data_scale != null)
                    sColDataScale.appendChild(doc.createTextNode(ci.data_scale));
                columnElement.appendChild(sColDataScale);

                // nullable
                Element sColNullable = doc.createElement("sNullable");
                if (ci.nullable != null)
                    sColNullable.appendChild(doc.createTextNode(ci.nullable));
                columnElement.appendChild(sColNullable);


                // default_length
                Element sColDefaultLength = doc.createElement("iDefaultLength");
                if (ci.default_length != null)
                    sColDefaultLength.appendChild(doc.createTextNode(ci.default_length));
                columnElement.appendChild(sColDefaultLength);


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
}
