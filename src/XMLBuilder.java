import org.w3c.dom.*;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.*;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import javax.xml.transform.*;
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

    public XMLBuilder() {}

    public XMLBuilder( Info info ) {
        this.info = info;
    }

    /* Преобразуем doc в String */
    private String getStringFromDoc(Document doc)    {
        /*
        try {
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = null;

            transformer = tf.newTransformer();
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

            StringWriter writer = new StringWriter();
            transformer.transform(new DOMSource(doc), new StreamResult(writer));
            return writer.getBuffer().toString().replaceAll("\n|\r", "");

        } catch (TransformerException e) {
            e.printStackTrace();
        }

        return null;
        */


        DOMImplementationLS domImplementation = (DOMImplementationLS) doc.getImplementation();
        LSSerializer lsSerializer = domImplementation.createLSSerializer();
        return lsSerializer.writeToString(doc);


    }

    /* Сгенерим xml на основе info и запишем его в файл */
    public String genStandartXML () {
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

                /* owner */
                Element sTblOwner = doc.createElement("sOwner");
                sTblOwner.appendChild(doc.createTextNode(tbl.owner));
                tableElement.appendChild(sTblOwner);

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

                    /* Table_name */
                    Element sColTableName = doc.createElement("sTableName");
                    if (col.table_name != null)
                        sColTableName.appendChild(doc.createTextNode(col.table_name));
                    columnElement.appendChild(sColTableName);

                    /* owner */
                    Element sColOwner = doc.createElement("sOwner");
                    if (col.owner != null)
                        sColOwner.appendChild(doc.createTextNode(col.owner));
                    columnElement.appendChild(sColOwner);

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
            // Вернем результат
            return getStringFromDoc(doc);
        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
            return null;
        }
    }

    /* Заполним info на основе пришедшей строки */
    public void parseStandartXml (String strXML)
    {
        Document doc = null;
        try {
            DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            InputSource is = new InputSource();
            is.setCharacterStream(new StringReader(strXML));

            doc = db.parse(is);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        doc.getDocumentElement().normalize();

        NodeList tablesList = doc.getElementsByTagName("Table");

        for (int x = 0; x < tablesList.getLength(); x++) {

            Node tbl = tablesList.item(x);

            if (tbl.getNodeType() == Node.ELEMENT_NODE) {

                Element eTbl = (Element) tbl;
                NodeList columnsList = eTbl.getElementsByTagName("Column");

                // Информация о колонках таблицы
                ArrayList<ColumnInfo> columnInfoArrayList = new ArrayList<ColumnInfo>();
                //
                for (int y = 0; y < columnsList.getLength(); y++) {

                    Node col = columnsList.item(y);

                    if (col.getNodeType() == Node.ELEMENT_NODE) {

                        Element eCol = (Element) col;

                        columnInfoArrayList.add(new ColumnInfo(
                                (eCol.getElementsByTagName("sOwner").item(0).getTextContent().isEmpty() ? null : eCol.getElementsByTagName("sOwner").item(0).getTextContent()),
                                (eCol.getElementsByTagName("sTableName").item(0).getTextContent().isEmpty() ? null : eCol.getElementsByTagName("sTableName").item(0).getTextContent()),
                                (eCol.getElementsByTagName("sColumnName").item(0).getTextContent().isEmpty() ? null : eCol.getElementsByTagName("sColumnName").item(0).getTextContent()),
                                (eCol.getElementsByTagName("sDataType").item(0).getTextContent().isEmpty() ? null : eCol.getElementsByTagName("sDataType").item(0).getTextContent()),
                                (eCol.getElementsByTagName("iDataLength").item(0).getTextContent().isEmpty() ? null : eCol.getElementsByTagName("iDataLength").item(0).getTextContent()),
                                (eCol.getElementsByTagName("iDataPrecision").item(0).getTextContent().isEmpty() ? null : eCol.getElementsByTagName("iDataPrecision").item(0).getTextContent()),
                                (eCol.getElementsByTagName("iDataScale").item(0).getTextContent().isEmpty() ? null : eCol.getElementsByTagName("iDataScale").item(0).getTextContent()),
                                (eCol.getElementsByTagName("sNullable").item(0).getTextContent().isEmpty() ? null : eCol.getElementsByTagName("sNullable").item(0).getTextContent()),
                                (eCol.getElementsByTagName("iDefaultLength").item(0).getTextContent().isEmpty() ? null : eCol.getElementsByTagName("iDefaultLength").item(0).getTextContent())));
                    }
                }

                TableInfo ti = new TableInfo();
                ti.setTableInfo(  (eTbl.getElementsByTagName("sOwner").item(0).getTextContent().isEmpty() ? null : eTbl.getElementsByTagName("sOwner").item(0).getTextContent() ),
                                  (eTbl.getElementsByTagName("sTableName").item(0).getTextContent().isEmpty() ? null : eTbl.getElementsByTagName("sTableName").item(0).getTextContent() ),
                                  (eTbl.getElementsByTagName("sStatus").item(0).getTextContent().isEmpty() ? null : eTbl.getElementsByTagName("sStatus").item(0).getTextContent() ),
                                  (eTbl.getElementsByTagName("sPartitioned").item(0).getTextContent().isEmpty() ? null : eTbl.getElementsByTagName("sPartitioned").item(0).getTextContent() ),
                                  (eTbl.getElementsByTagName("sTemporary").item(0).getTextContent().isEmpty() ? null : eTbl.getElementsByTagName("sTemporary").item(0).getTextContent() ),
                                  (eTbl.getElementsByTagName("sCompression").item(0).getTextContent().isEmpty() ? null : eTbl.getElementsByTagName("sCompression").item(0).getTextContent() ),
                                  (eTbl.getElementsByTagName("sLogging").item(0).getTextContent().isEmpty() ? null : eTbl.getElementsByTagName("sLogging").item(0).getTextContent() ),
                                  (eTbl.getElementsByTagName("sCache").item(0).getTextContent().isEmpty() ? null : eTbl.getElementsByTagName("sCache").item(0).getTextContent() ),
                                  (eTbl.getElementsByTagName("sTableLock").item(0).getTextContent().isEmpty() ? null : eTbl.getElementsByTagName("sTableLock").item(0).getTextContent() ),
                                columnInfoArrayList);

                info.tableInfoArrayList.add(ti);
            }
        }
    }

    public String compareStandartXML (Info baseInfo) {
        /* Теперь начнем сравннеи потаблично */

        try {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

        // root elements
        Document doc = docBuilder.newDocument();
        Element rootElement = doc.createElement("CompareReport");
        doc.appendChild(rootElement);

            /* version */
        Element version = doc.createElement("version");
        version.appendChild(doc.createTextNode("001.00"));
        rootElement.appendChild(version);

        // Tables
        Element tablesElement = doc.createElement("Tables");
        rootElement.appendChild(tablesElement);

        for (TableInfo fileTbl : info.tableInfoArrayList) {

            // Признак того что объект найден
            boolean tblFind = false;
            // Сюда мы закинем тот объект который надо удлаить из массива
            TableInfo tblToDelete = new TableInfo();

            for ( TableInfo baseTbl : baseInfo.tableInfoArrayList ) {
                // Сравниваем таблицы поименно
                // Если нашли таблицу с тем же именем то добавим находку в результат
                if ((fileTbl.name.equals(baseTbl.name)) && (!tblFind) ) {
                    tblFind = true;
                    tblToDelete = baseTbl;

                    Element tableElement = doc.createElement("Table");
                    tableElement.setAttribute("place", "both");
                    tablesElement.appendChild(tableElement);
                    //
                    /* Эта часть строит информацю  о таблице */
                    /* Name */
                    Element sTblTableName = doc.createElement("sTableName");
                    sTblTableName.appendChild(doc.createTextNode(fileTbl.name));
                    tableElement.appendChild(sTblTableName);

                    /* owner */
                    Element sTblOwner = doc.createElement("sOwner");
                    tableElement.appendChild(sTblOwner);

                    Element sTblOwnerOld = doc.createElement("old");
                    sTblOwnerOld.appendChild(doc.createTextNode(fileTbl.owner));
                    sTblOwner.appendChild(sTblOwnerOld);

                    Element sTblOwnerNew = doc.createElement("new");
                    sTblOwnerNew.appendChild(doc.createTextNode(baseTbl.owner));
                    sTblOwner.appendChild(sTblOwnerNew);

                    Element sTblOwnerState = doc.createElement("state");
                    if ((baseTbl.owner == null && fileTbl.owner == null) || ( baseTbl.owner.equals( fileTbl.owner) )) {
                        sTblOwnerState.appendChild(doc.createTextNode("same"));
                    }
                    else {
                        sTblOwnerState.appendChild(doc.createTextNode("diff"));
                    }
                    sTblOwner.appendChild(sTblOwnerState);

                    /* Status */
                    Element sTblStatus = doc.createElement("sStatus");
                    tableElement.appendChild(sTblStatus);

                    Element sTblStatusOld = doc.createElement("old");
                    if (fileTbl.status != null)
                        sTblStatusOld.appendChild(doc.createTextNode(fileTbl.status));
                    sTblStatus.appendChild(sTblStatusOld);

                    Element sTblStatusNew = doc.createElement("new");
                    if (baseTbl.status != null)
                        sTblStatusNew.appendChild(doc.createTextNode(baseTbl.status));
                    sTblStatus.appendChild(sTblStatusNew);

                    Element sTblStatusState = doc.createElement("state");
                    if ((baseTbl.status == null && fileTbl.status == null) || (baseTbl.status.equals( fileTbl.status ))) {
                        sTblStatusState.appendChild(doc.createTextNode("same"));
                    }
                    else {
                        sTblStatusState.appendChild(doc.createTextNode("diff"));
                    }
                    sTblStatus.appendChild(sTblStatusState);

                    /* Partitioned */
                    Element sTblPartitioned = doc.createElement("sPartitioned");
                    tableElement.appendChild(sTblPartitioned);

                    Element sTblPartitionedOld = doc.createElement("old");
                    if (fileTbl.partitioned != null)
                        sTblPartitionedOld.appendChild(doc.createTextNode(fileTbl.partitioned));
                    sTblPartitioned.appendChild(sTblPartitionedOld);

                    Element sTblPartitionedNew = doc.createElement("new");
                    if (baseTbl.partitioned != null)
                        sTblPartitionedNew.appendChild(doc.createTextNode(baseTbl.partitioned));
                    sTblPartitioned.appendChild(sTblPartitionedNew);

                    Element sTblPartitionedState = doc.createElement("state");
                    if ((baseTbl.partitioned == null && fileTbl.partitioned == null) || ( baseTbl.partitioned.equals( fileTbl.partitioned ))) {
                        sTblPartitionedState.appendChild(doc.createTextNode("same"));
                    }
                    else {
                        sTblPartitionedState.appendChild(doc.createTextNode("diff"));
                    }
                    sTblPartitioned.appendChild(sTblPartitionedState);

                    /* Temporary */
                    Element sTblTemporary = doc.createElement("sTemporary");
                    tableElement.appendChild(sTblTemporary);

                    Element sTblTemporaryOld = doc.createElement("old");
                    if (fileTbl.temporary != null)
                        sTblTemporaryOld.appendChild(doc.createTextNode(fileTbl.temporary));
                    sTblTemporary.appendChild(sTblTemporaryOld);

                    Element sTblTemporaryNew = doc.createElement("new");
                    if (baseTbl.temporary != null)
                        sTblTemporaryNew.appendChild(doc.createTextNode(baseTbl.temporary));
                    sTblTemporary.appendChild(sTblTemporaryNew);

                    Element sTblTemporaryState = doc.createElement("state");
                    if ((baseTbl.temporary == null && fileTbl.temporary == null) || ( baseTbl.temporary.equals( fileTbl.temporary ))) {
                        sTblTemporaryState.appendChild(doc.createTextNode("same"));
                    }
                    else {
                        sTblTemporaryState.appendChild(doc.createTextNode("diff"));
                    }
                    sTblTemporary.appendChild(sTblTemporaryState);

                    /* Compression */
                    Element sTblCompression = doc.createElement("sCompression");
                    tableElement.appendChild(sTblCompression);

                    Element sTblCompressionOld = doc.createElement("old");
                    if (fileTbl.compression != null)
                        sTblCompressionOld.appendChild(doc.createTextNode(fileTbl.compression));
                    sTblCompression.appendChild(sTblCompressionOld);

                    Element sTblCompressionNew = doc.createElement("new");
                    if (baseTbl.compression != null)
                        sTblCompressionNew.appendChild(doc.createTextNode(baseTbl.compression));
                    sTblCompression.appendChild(sTblCompressionNew);

                    Element sTblCompressionState = doc.createElement("state");
                    if ( (baseTbl.compression == null && fileTbl.compression == null) || ( baseTbl.compression.equals( fileTbl.compression) )) {
                        sTblCompressionState.appendChild(doc.createTextNode("same"));
                    }
                    else {
                        sTblCompressionState.appendChild(doc.createTextNode("diff"));
                    }
                    sTblCompression.appendChild(sTblCompressionState);

                    /* logging */
                    Element sTblLogging = doc.createElement("sLogging");
                    tableElement.appendChild(sTblLogging);

                    Element sTblLoggingOld = doc.createElement("old");
                    if (fileTbl.logging != null)
                        sTblLoggingOld.appendChild(doc.createTextNode(fileTbl.logging));
                    sTblLogging.appendChild(sTblLoggingOld);

                    Element sTblLoggingNew = doc.createElement("new");
                    if (baseTbl.logging != null)
                        sTblLoggingNew.appendChild(doc.createTextNode(baseTbl.logging));
                    sTblLogging.appendChild(sTblLoggingNew);

                    Element sTblLoggingState = doc.createElement("state");
                    if ( (baseTbl.logging == null && fileTbl.logging == null) || (baseTbl.logging.equals( fileTbl.logging )) ) {
                        sTblLoggingState.appendChild(doc.createTextNode("same"));
                    }
                    else {
                        sTblLoggingState.appendChild(doc.createTextNode("diff"));
                    }
                    sTblLogging.appendChild(sTblLoggingState);

                    /* cache */
                    Element sTblCache = doc.createElement("sCache");
                    tableElement.appendChild(sTblCache);

                    Element sTblCacheOld = doc.createElement("old");
                    if (fileTbl.cache != null)
                        sTblCacheOld.appendChild(doc.createTextNode(fileTbl.cache));
                    sTblCache.appendChild(sTblCacheOld);

                    Element sTblCacheNew = doc.createElement("new");
                    if (baseTbl.cache != null)
                        sTblCacheNew.appendChild(doc.createTextNode(baseTbl.cache));
                    sTblCache.appendChild(sTblCacheNew);

                    Element sTblCacheState = doc.createElement("state");
                    if ((baseTbl.cache == null && fileTbl.cache == null) || (baseTbl.cache.equals( fileTbl.cache ))) {
                        sTblCacheState.appendChild(doc.createTextNode("same"));
                    }
                    else {
                        sTblCacheState.appendChild(doc.createTextNode("diff"));
                    }
                    sTblCache.appendChild(sTblCacheState);

                    /* table_lock */
                    Element sTblTableLock = doc.createElement("sTableLock");
                    tableElement.appendChild(sTblTableLock);

                    Element sTblTableLockOld = doc.createElement("old");
                    if (fileTbl.table_lock != null)
                        sTblTableLockOld.appendChild(doc.createTextNode(fileTbl.table_lock));
                    sTblTableLock.appendChild(sTblTableLockOld);

                    Element sTblTableLockNew = doc.createElement("new");
                    if (baseTbl.table_lock != null)
                        sTblTableLockNew.appendChild(doc.createTextNode(baseTbl.table_lock));
                    sTblTableLock.appendChild(sTblTableLockNew);

                    Element sTblTableLockState = doc.createElement("state");
                    if ((baseTbl.table_lock == null && fileTbl.table_lock == null) || (baseTbl.table_lock.equals( fileTbl.table_lock ))) {
                        sTblTableLockState.appendChild(doc.createTextNode("same"));
                    }
                    else {
                        sTblTableLockState.appendChild(doc.createTextNode("diff"));
                    }
                    sTblTableLock.appendChild(sTblTableLockState);

                    Element columnsElement = doc.createElement("Columns");
                    tableElement.appendChild(columnsElement);

                    /* Дальше спускаемся ниже для сравнения колонок таблицы и их свойств */
                    /* Сравниваем так. Перебираем все колонки из baseTbl. Для каждой колонки перебираем все колонки текущей fileTbl*/

                    for ( ColumnInfo fileCol : fileTbl.columnInfoArrayList ) {

                        // Признак того что объект найден
                        boolean colFind = false;
                        // Сюда мы закинем тот объект который надо удлаить из массива
                        ColumnInfo colToDelete = new ColumnInfo();

                        for (ColumnInfo baseCol : baseTbl.columnInfoArrayList) {
                            // Если нашли соответствующую колонку
                            // то отсравниваем ее параметры а потом удалим
                            if (baseCol.name.equals( fileCol.name) && !colFind) {
                                colFind = true;
                                colToDelete = baseCol;

                                Element columnElement = doc.createElement("Column");
                                columnElement.setAttribute("place", "both");
                                columnsElement.appendChild(columnElement);
                                //
                                /* Эта часть строит информацю  о колонке */
                                /* Table_name */
                                Element sColTableName = doc.createElement("sTableName");
                                if (baseCol.name != null)
                                    sColTableName.appendChild(doc.createTextNode(baseCol.name));
                                columnElement.appendChild(sColTableName);

                                // column_name
                                Element sColName = doc.createElement("sColumnName");
                                if (baseCol.name != null)
                                    sColName.appendChild(doc.createTextNode(baseCol.name));
                                columnElement.appendChild(sColName);

                                // owner
                                Element sColOwner = doc.createElement("sOwner");
                                columnElement.appendChild(sColOwner);

                                Element sColOwnerOld = doc.createElement("old");
                                if (fileCol.owner != null)
                                    sColOwnerOld.appendChild(doc.createTextNode(fileCol.owner));
                                sColOwner.appendChild(sColOwnerOld);

                                Element sColOwnerNew = doc.createElement("new");
                                if (baseCol.owner != null)
                                    sColOwnerNew.appendChild(doc.createTextNode(baseCol.owner));
                                sColOwner.appendChild(sColOwnerNew);

                                Element sColOwnerState = doc.createElement("state");
                                if ((baseCol.owner == null && fileCol.owner == null) || (baseCol.owner.equals( fileCol.owner ))) {
                                    sColOwnerState.appendChild(doc.createTextNode("same"));
                                }
                                else {
                                    sColOwnerState.appendChild(doc.createTextNode("diff"));
                                }
                                sColOwner.appendChild(sColOwnerState);

                                // data_type
                                Element sColDataType = doc.createElement("sDataType");
                                columnElement.appendChild(sColDataType);

                                Element sColDataTypeOld = doc.createElement("old");
                                if (fileCol.data_type != null)
                                    sColDataTypeOld.appendChild(doc.createTextNode(fileCol.data_type));
                                sColDataType.appendChild(sColDataTypeOld);

                                Element sColDataTypeNew = doc.createElement("new");
                                if (baseCol.data_type != null)
                                    sColDataTypeNew.appendChild(doc.createTextNode(baseCol.data_type));
                                sColDataType.appendChild(sColDataTypeNew);

                                Element sColDataTypeState = doc.createElement("state");
                                if ((baseCol.data_type == null && fileCol.data_type == null) || (baseCol.data_type.equals( fileCol.data_type ))) {
                                    sColDataTypeState.appendChild(doc.createTextNode("same"));
                                }
                                else {
                                    sColDataTypeState.appendChild(doc.createTextNode("diff"));
                                }
                                sColDataType.appendChild(sColDataTypeState);

                                // data_length
                                Element sColDataLength = doc.createElement("iDataLength");
                                columnElement.appendChild(sColDataLength);

                                Element sColDataLengthOld = doc.createElement("old");
                                if (fileCol.data_length != null)
                                    sColDataLengthOld.appendChild(doc.createTextNode(fileCol.data_length));
                                sColDataLength.appendChild(sColDataLengthOld);

                                Element sColDataLengthNew = doc.createElement("new");
                                if (baseCol.data_length != null)
                                    sColDataLengthNew.appendChild(doc.createTextNode(baseCol.data_length));
                                sColDataLength.appendChild(sColDataLengthNew);

                                Element sColDataLengthState = doc.createElement("state");
                                if ((baseCol.data_length == null && fileCol.data_length == null) || (baseCol.data_length.equals( fileCol.data_length ))) {
                                    sColDataLengthState.appendChild(doc.createTextNode("same"));
                                }
                                else {
                                    sColDataLengthState.appendChild(doc.createTextNode("diff"));
                                }
                                sColDataLength.appendChild(sColDataLengthState);

                                // data_precision
                                Element sColDataPrecision = doc.createElement("iDataPrecision");
                                columnElement.appendChild(sColDataPrecision);

                                Element sColDataPrecisionOld = doc.createElement("old");
                                if (fileCol.data_precision != null)
                                    sColDataPrecisionOld.appendChild(doc.createTextNode(fileCol.data_precision));
                                sColDataPrecision.appendChild(sColDataPrecisionOld);

                                Element sColDataPrecisionNew = doc.createElement("new");
                                if (baseCol.data_precision != null)
                                    sColDataPrecisionNew.appendChild(doc.createTextNode(baseCol.data_precision));
                                sColDataPrecision.appendChild(sColDataPrecisionNew);

                                Element sColDataPrecisionState = doc.createElement("state");
                                if ((baseCol.data_precision == null && fileCol.data_precision == null) || (baseCol.data_precision.equals( fileCol.data_precision ))) {
                                    sColDataPrecisionState.appendChild(doc.createTextNode("same"));
                                }
                                else {
                                    sColDataPrecisionState.appendChild(doc.createTextNode("diff"));
                                }
                                sColDataPrecision.appendChild(sColDataPrecisionState);

                                // data_scale
                                Element sColDataScale = doc.createElement("iDataScale");
                                columnElement.appendChild(sColDataScale);

                                Element sColDataScaleOld = doc.createElement("old");
                                if (fileCol.data_scale != null)
                                    sColDataScaleOld.appendChild(doc.createTextNode(fileCol.data_scale));
                                sColDataScale.appendChild(sColDataScaleOld);

                                Element sColDataScalenNew = doc.createElement("new");
                                if (baseCol.data_scale != null)
                                    sColDataScalenNew.appendChild(doc.createTextNode(baseCol.data_scale));
                                sColDataScale.appendChild(sColDataScalenNew);

                                Element sColDataScaleState = doc.createElement("state");
                                if ((baseCol.data_scale == null && fileCol.data_scale == null) || (baseCol.data_scale.equals( fileCol.data_scale ))) {
                                    sColDataScaleState.appendChild(doc.createTextNode("same"));
                                }
                                else {
                                    sColDataScaleState.appendChild(doc.createTextNode("diff"));
                                }
                                sColDataScale.appendChild(sColDataScaleState);

                                // nullable
                                Element sColNullable = doc.createElement("sNullable");
                                columnElement.appendChild(sColNullable);

                                Element sColNullableOld = doc.createElement("old");
                                if (fileCol.nullable != null)
                                    sColNullableOld.appendChild(doc.createTextNode(fileCol.nullable));
                                sColNullable.appendChild(sColNullableOld);

                                Element sColNullableNew = doc.createElement("new");
                                if (baseCol.nullable != null)
                                    sColNullableNew.appendChild(doc.createTextNode(baseCol.nullable));
                                sColNullable.appendChild(sColNullableNew);

                                Element sColNullableState = doc.createElement("state");
                                if ((baseCol.nullable == null && fileCol.nullable == null) || (baseCol.nullable.equals( fileCol.nullable ))) {
                                    sColNullableState.appendChild(doc.createTextNode("same"));
                                }
                                else {
                                    sColNullableState.appendChild(doc.createTextNode("diff"));
                                }
                                sColNullable.appendChild(sColNullableState);

                                // default_length
                                Element sColDefaultLength = doc.createElement("iDefaultLength");
                                columnElement.appendChild(sColDefaultLength);

                                Element sColDefaultLengthOld = doc.createElement("old");
                                if (fileCol.default_length != null)
                                    sColDefaultLengthOld.appendChild(doc.createTextNode(fileCol.default_length));
                                sColDefaultLength.appendChild(sColDefaultLengthOld);

                                Element sColDefaultLengthNew = doc.createElement("new");
                                if (baseCol.default_length != null)
                                    sColDefaultLengthNew.appendChild(doc.createTextNode(baseCol.default_length));
                                sColDefaultLength.appendChild(sColDefaultLengthNew);

                                Element sColDefaultLengthState = doc.createElement("state");
                                if ((baseCol.default_length == null && fileCol.default_length == null) || (baseCol.default_length.equals( fileCol.default_length ))) {
                                    sColDefaultLengthState.appendChild(doc.createTextNode("same"));
                                }
                                else {
                                    sColDefaultLengthState.appendChild(doc.createTextNode("diff"));
                                }
                                sColDefaultLength.appendChild(sColDefaultLengthState);
                            }
                        }

                        /* После того как отсравнивали колонки и сравнение закончилось нужно выкинуть таблицу из списка сравниваемых */
                        baseTbl.columnInfoArrayList.remove(colToDelete);

                        /* Перебрали все колонки из структуры с которой сравниваем. К этому моменту уже должны были с чем нить сравнить */
                        /* Если не нашли то напишем что такой таблицы нет в базе */
                        if (!tblFind) {
                            Element columnElement = doc.createElement("Column");
                            columnElement.setAttribute("place", "file");
                            columnsElement.appendChild(columnElement);
                            //
                                /* Эта часть строит информацю  о колонке */
                                /* Table_name */
                            Element sColTableName = doc.createElement("sTableName");
                            if (fileCol.name != null)
                                sColTableName.appendChild(doc.createTextNode(fileCol.name));
                            columnElement.appendChild(sColTableName);

                            // column_name
                            Element sColName = doc.createElement("sColumnName");
                            if (fileCol.name != null)
                                sColName.appendChild(doc.createTextNode(fileCol.name));
                            columnElement.appendChild(sColName);

                            // owner
                            Element sColOwner = doc.createElement("sOwner");
                            columnElement.appendChild(sColOwner);

                            Element sColOwnerOld = doc.createElement("old");
                            if (fileCol.owner != null)
                                sColOwnerOld.appendChild(doc.createTextNode(fileCol.owner));
                            sColOwner.appendChild(sColOwnerOld);

                            Element sColOwnerNew = doc.createElement("new");
                            sColOwner.appendChild(sColOwnerNew);

                            Element sColOwnerState = doc.createElement("state");
                            sColOwnerState.appendChild(doc.createTextNode("diff"));
                            sColOwner.appendChild(sColOwnerState);

                            // data_type
                            Element sColDataType = doc.createElement("sDataType");
                            columnElement.appendChild(sColDataType);

                            Element sColDataTypeOld = doc.createElement("old");
                            if (fileCol.data_type != null)
                                sColDataTypeOld.appendChild(doc.createTextNode(fileCol.data_type));
                            sColDataType.appendChild(sColDataTypeOld);

                            Element sColDataTypeNew = doc.createElement("new");
                            sColDataType.appendChild(sColDataTypeNew);

                            Element sColDataTypeState = doc.createElement("state");
                            sColDataTypeState.appendChild(doc.createTextNode("diff"));
                            sColDataType.appendChild(sColDataTypeState);

                            // data_length
                            Element sColDataLength = doc.createElement("iDataLength");
                            columnElement.appendChild(sColDataLength);

                            Element sColDataLengthOld = doc.createElement("old");
                            if (fileCol.data_length != null)
                                sColDataLengthOld.appendChild(doc.createTextNode(fileCol.data_length));
                            sColDataLength.appendChild(sColDataLengthOld);

                            Element sColDataLengthNew = doc.createElement("new");
                            sColDataLength.appendChild(sColDataLengthNew);

                            Element sColDataLengthState = doc.createElement("state");
                            sColDataLengthState.appendChild(doc.createTextNode("diff"));
                            sColDataLength.appendChild(sColDataLengthState);

                            // data_precision
                            Element sColDataPrecision = doc.createElement("iDataPrecision");
                            columnElement.appendChild(sColDataPrecision);

                            Element sColDataPrecisionOld = doc.createElement("old");
                            if (fileCol.data_precision != null)
                                sColDataPrecisionOld.appendChild(doc.createTextNode(fileCol.data_precision));
                            sColDataPrecision.appendChild(sColDataPrecisionOld);

                            Element sColDataPrecisionNew = doc.createElement("new");
                            sColDataPrecision.appendChild(sColDataPrecisionNew);

                            Element sColDataPrecisionState = doc.createElement("state");
                            sColDataPrecisionState.appendChild(doc.createTextNode("diff"));
                            sColDataPrecision.appendChild(sColDataPrecisionState);

                            // data_scale
                            Element sColDataScale = doc.createElement("iDataScale");
                            columnElement.appendChild(sColDataScale);

                            Element sColDataScaleOld = doc.createElement("old");
                            if (fileCol.data_scale != null)
                                sColDataScaleOld.appendChild(doc.createTextNode(fileCol.data_scale));
                            sColDataScale.appendChild(sColDataScaleOld);

                            Element sColDataScalenNew = doc.createElement("new");
                            sColDataScale.appendChild(sColDataScalenNew);

                            Element sColDataScaleState = doc.createElement("state");
                            sColDataScaleState.appendChild(doc.createTextNode("diff"));
                            sColDataScale.appendChild(sColDataScaleState);

                            // nullable
                            Element sColNullable = doc.createElement("sNullable");
                            columnElement.appendChild(sColNullable);

                            Element sColNullableOld = doc.createElement("old");
                            if (fileCol.nullable != null)
                                sColNullableOld.appendChild(doc.createTextNode(fileCol.nullable));
                            sColNullable.appendChild(sColNullableOld);

                            Element sColNullableNew = doc.createElement("new");
                            sColNullable.appendChild(sColNullableNew);

                            Element sColNullableState = doc.createElement("state");
                            sColNullableState.appendChild(doc.createTextNode("diff"));
                            sColNullable.appendChild(sColNullableState);

                            // default_length
                            Element sColDefaultLength = doc.createElement("iDefaultLength");
                            columnElement.appendChild(sColDefaultLength);

                            Element sColDefaultLengthOld = doc.createElement("old");
                            if (fileCol.default_length != null)
                                sColDefaultLengthOld.appendChild(doc.createTextNode(fileCol.default_length));
                            sColDefaultLength.appendChild(sColDefaultLengthOld);

                            Element sColDefaultLengthNew = doc.createElement("new");
                            sColDefaultLength.appendChild(sColDefaultLengthNew);

                            Element sColDefaultLengthState = doc.createElement("state");
                            sColDefaultLengthState.appendChild(doc.createTextNode("diff"));
                            sColDefaultLength.appendChild(sColDefaultLengthState);
                        }

                    }

                   /* Если мы попали сюда то все колонки из файла мы уже отсравнивали
                    * Посмотрим осталось ли что то в списке колонок из базы
                    * Если осталось то добавим в результирующий файл с отметкой что есть только в базе */

                    for (ColumnInfo baseCol : baseTbl.columnInfoArrayList ) {
                        Element columnElement = doc.createElement("Column");
                        columnElement.setAttribute("place", "base");
                        columnsElement.appendChild(columnElement);
                        //
                        /* Эта часть строит информацю  о колонке */
                        /* Table_name */
                        Element sColTableName = doc.createElement("sTableName");
                        if (baseCol.name != null)
                            sColTableName.appendChild(doc.createTextNode(baseCol.name));
                        columnElement.appendChild(sColTableName);

                        // column_name
                        Element sColName = doc.createElement("sColumnName");
                        if (baseCol.name != null)
                            sColName.appendChild(doc.createTextNode(baseCol.name));
                        columnElement.appendChild(sColName);

                        /* owner */
                        Element sColOwner = doc.createElement("sOwner");
                        columnElement.appendChild(sColOwner);

                        Element sColOwnerOld = doc.createElement("old");
                        sColOwner.appendChild(sColOwnerOld);

                        Element sColOwnerNew = doc.createElement("new");
                        if (baseCol.owner != null)
                            sColOwnerNew.appendChild(doc.createTextNode(baseCol.owner));
                        sColOwner.appendChild(sColOwnerNew);

                        Element sColOwnerState = doc.createElement("state");
                        sColOwnerState.appendChild(doc.createTextNode("diff"));
                        sColOwner.appendChild(sColOwnerState);

                        // data_type
                        Element sColDataType = doc.createElement("sDataType");
                        columnElement.appendChild(sColDataType);

                        Element sColDataTypeOld = doc.createElement("old");
                        sColDataType.appendChild(sColDataTypeOld);

                        Element sColDataTypeNew = doc.createElement("new");
                        if (baseCol.data_type != null)
                            sColDataTypeNew.appendChild(doc.createTextNode(baseCol.data_type));
                        sColDataType.appendChild(sColDataTypeNew);

                        Element sColDataTypeState = doc.createElement("state");
                        sColDataTypeState.appendChild(doc.createTextNode("diff"));
                        sColDataType.appendChild(sColDataTypeState);

                        // data_length
                        Element sColDataLength = doc.createElement("iDataLength");
                        columnElement.appendChild(sColDataLength);

                        Element sColDataLengthOld = doc.createElement("old");
                        sColDataLength.appendChild(sColDataLengthOld);

                        Element sColDataLengthNew = doc.createElement("new");
                        if (baseCol.data_length != null)
                            sColDataLengthNew.appendChild(doc.createTextNode(baseCol.data_length));
                        sColDataLength.appendChild(sColDataLengthNew);

                        Element sColDataLengthState = doc.createElement("state");
                        sColDataLengthState.appendChild(doc.createTextNode("diff"));
                        sColDataLength.appendChild(sColDataLengthState);

                        // data_precision
                        Element sColDataPrecision = doc.createElement("iDataPrecision");
                        columnElement.appendChild(sColDataPrecision);

                        Element sColDataPrecisionOld = doc.createElement("old");
                        sColDataPrecision.appendChild(sColDataPrecisionOld);

                        Element sColDataPrecisionNew = doc.createElement("new");
                        if (baseCol.data_precision != null)
                            sColDataPrecisionNew.appendChild(doc.createTextNode(baseCol.data_precision));
                        sColDataPrecision.appendChild(sColDataPrecisionNew);

                        Element sColDataPrecisionState = doc.createElement("state");
                        sColDataPrecisionState.appendChild(doc.createTextNode("diff"));
                        sColDataPrecision.appendChild(sColDataPrecisionState);

                        // data_scale
                        Element sColDataScale = doc.createElement("iDataScale");
                        columnElement.appendChild(sColDataScale);

                        Element sColDataScaleOld = doc.createElement("old");
                        sColDataScale.appendChild(sColDataScaleOld);

                        Element sColDataScalenNew = doc.createElement("new");
                        if (baseCol.data_scale != null)
                            sColDataScalenNew.appendChild(doc.createTextNode(baseCol.data_scale));
                        sColDataScale.appendChild(sColDataScalenNew);

                        Element sColDataScaleState = doc.createElement("state");
                        sColDataScaleState.appendChild(doc.createTextNode("diff"));
                        sColDataScale.appendChild(sColDataScaleState);

                        // nullable
                        Element sColNullable = doc.createElement("sNullable");
                        columnElement.appendChild(sColNullable);

                        Element sColNullableOld = doc.createElement("old");
                        sColNullable.appendChild(sColNullableOld);

                        Element sColNullableNew = doc.createElement("new");
                        if (baseCol.nullable != null)
                            sColNullableNew.appendChild(doc.createTextNode(baseCol.nullable));
                        sColNullable.appendChild(sColNullableNew);

                        Element sColNullableState = doc.createElement("state");
                        sColNullableState.appendChild(doc.createTextNode("diff"));
                        sColNullable.appendChild(sColNullableState);

                        // default_length
                        Element sColDefaultLength = doc.createElement("iDefaultLength");
                        columnElement.appendChild(sColDefaultLength);

                        Element sColDefaultLengthOld = doc.createElement("old");
                        sColDefaultLength.appendChild(sColDefaultLengthOld);

                        Element sColDefaultLengthNew = doc.createElement("new");
                        if (baseCol.default_length != null)
                            sColDefaultLengthNew.appendChild(doc.createTextNode(baseCol.default_length));
                        sColDefaultLength.appendChild(sColDefaultLengthNew);

                        Element sColDefaultLengthState = doc.createElement("state");
                        sColDefaultLengthState.appendChild(doc.createTextNode("diff"));
                        sColDefaultLength.appendChild(sColDefaultLengthState);
                    }
                }
            }

            /* После того как отсравнивали колонки и сравнение закончилось нужно выкинуть таблицу из списка сравниваемых */
            baseInfo.tableInfoArrayList.remove(tblToDelete);


            /* Перебрали все таблицы из структуры с которой сравниваем. К этому моменту уже должны были с ем нить сравнить */
            /* Если не нашли то напишем что такой таблицы нет в базе */
            if (!tblFind) {
                Element tableElement = doc.createElement("Table");
                tableElement.setAttribute("place", "file");
                tablesElement.appendChild(tableElement);
                //
                /* Эта часть строит информацю  о таблице */
                /* Name */
                Element sTblTableName = doc.createElement("sTableName");
                sTblTableName.appendChild(doc.createTextNode(fileTbl.name));
                tableElement.appendChild(sTblTableName);

                /* owner */
                Element sTblOwner = doc.createElement("sOwner");
                tableElement.appendChild(sTblOwner);

                Element sTblOwnerOld = doc.createElement("old");
                if (fileTbl.owner != null)
                    sTblOwnerOld.appendChild(doc.createTextNode(fileTbl.owner));
                sTblOwner.appendChild(sTblOwnerOld);

                Element sTblOwnerNew = doc.createElement("new");
                sTblOwner.appendChild(sTblOwnerNew);

                Element sTblOwnerState = doc.createElement("state");
                sTblOwnerState.appendChild(doc.createTextNode("diff"));
                sTblOwner.appendChild(sTblOwnerState);

                /* Status */
                Element sTblStatus = doc.createElement("sStatus");
                tableElement.appendChild(sTblStatus);

                Element sTblStatusOld = doc.createElement("old");
                if (fileTbl.status != null)
                    sTblStatusOld.appendChild(doc.createTextNode(fileTbl.status));
                sTblStatus.appendChild(sTblStatusOld);

                Element sTblStatusNew = doc.createElement("new");
                sTblStatus.appendChild(sTblStatusNew);

                Element sTblStatusState = doc.createElement("state");
                sTblStatusState.appendChild(doc.createTextNode("diff"));
                sTblStatus.appendChild(sTblStatusState);

                /* Partitioned */
                Element sTblPartitioned = doc.createElement("sPartitioned");
                tableElement.appendChild(sTblPartitioned);

                Element sTblPartitionedOld = doc.createElement("old");
                if (fileTbl.partitioned != null)
                    sTblPartitionedOld.appendChild(doc.createTextNode(fileTbl.partitioned));
                sTblPartitioned.appendChild(sTblPartitionedOld);

                Element sTblPartitionedNew = doc.createElement("new");
                sTblPartitioned.appendChild(sTblPartitionedNew);

                Element sTblPartitionedState = doc.createElement("state");
                sTblPartitionedState.appendChild(doc.createTextNode("diff"));
                sTblPartitioned.appendChild(sTblPartitionedState);

                /* Temporary */
                Element sTblTemporary = doc.createElement("sTemporary");
                tableElement.appendChild(sTblTemporary);

                Element sTblTemporaryOld = doc.createElement("old");
                if (fileTbl.temporary != null)
                    sTblTemporaryOld.appendChild(doc.createTextNode(fileTbl.temporary));
                sTblTemporary.appendChild(sTblTemporaryOld);

                Element sTblTemporaryNew = doc.createElement("new");
                sTblTemporary.appendChild(sTblTemporaryNew);

                Element sTblTemporaryState = doc.createElement("state");
                sTblTemporaryState.appendChild(doc.createTextNode("diff"));
                sTblTemporary.appendChild(sTblTemporaryState);

                    /* Compression */
                Element sTblCompression = doc.createElement("sCompression");
                tableElement.appendChild(sTblCompression);

                Element sTblCompressionOld = doc.createElement("old");
                if (fileTbl.compression != null)
                    sTblCompressionOld.appendChild(doc.createTextNode(fileTbl.compression));
                sTblCompression.appendChild(sTblCompressionOld);

                Element sTblCompressionNew = doc.createElement("new");
                sTblCompression.appendChild(sTblCompressionNew);

                Element sTblCompressionState = doc.createElement("state");
                sTblCompressionState.appendChild(doc.createTextNode("diff"));
                sTblCompression.appendChild(sTblCompressionState);

                /* logging */
                Element sTblLogging = doc.createElement("sLogging");
                tableElement.appendChild(sTblLogging);

                Element sTblLoggingOld = doc.createElement("old");
                if (fileTbl.logging != null)
                    sTblLoggingOld.appendChild(doc.createTextNode(fileTbl.logging));
                sTblLogging.appendChild(sTblLoggingOld);

                Element sTblLoggingNew = doc.createElement("new");
                sTblLogging.appendChild(sTblLoggingNew);

                Element sTblLoggingState = doc.createElement("state");
                sTblLoggingState.appendChild(doc.createTextNode("diff"));
                sTblLogging.appendChild(sTblLoggingState);

                /* cache */
                Element sTblCache = doc.createElement("sCache");
                tableElement.appendChild(sTblCache);

                Element sTblCacheOld = doc.createElement("old");
                if (fileTbl.cache != null)
                    sTblCacheOld.appendChild(doc.createTextNode(fileTbl.cache));
                sTblCache.appendChild(sTblCacheOld);

                Element sTblCacheNew = doc.createElement("new");
                sTblCache.appendChild(sTblCacheNew);

                Element sTblCacheState = doc.createElement("state");
                sTblCacheState.appendChild(doc.createTextNode("diff"));
                sTblCache.appendChild(sTblCacheState);

                /* table_lock */
                Element sTblTableLock = doc.createElement("sTableLock");
                tableElement.appendChild(sTblTableLock);

                Element sTblTableLockOld = doc.createElement("old");
                if (fileTbl.table_lock != null)
                    sTblTableLockOld.appendChild(doc.createTextNode(fileTbl.table_lock));
                sTblTableLock.appendChild(sTblTableLockOld);

                Element sTblTableLockNew = doc.createElement("new");
                sTblTableLock.appendChild(sTblTableLockNew);

                Element sTblTableLockState = doc.createElement("state");
                sTblTableLockState.appendChild(doc.createTextNode("diff"));
                sTblTableLock.appendChild(sTblTableLockState);
            }
        }

        /* Если мы попали сюда то все таблицы из файла мы уже отсравнивали
        * Посмотрим осталось ли что то в списке таблиц из базы
        * Если осталось то добавим в результирующий файл с отметкой что есть только в базе */

        for (TableInfo baseTbl : baseInfo.tableInfoArrayList ) {
            Element tableElement = doc.createElement("Table");
            tableElement.setAttribute("place", "base");
            tablesElement.appendChild(tableElement);
            //
            /* Эта часть строит информацю  о таблице */
            /* Name */
            Element sTblTableName = doc.createElement("sTableName");
            sTblTableName.appendChild(doc.createTextNode(baseTbl.name));
            tableElement.appendChild(sTblTableName);

                    /* owner */
            Element sTblOwner = doc.createElement("sOwner");
            tableElement.appendChild(sTblOwner);

            Element sTblOwnerOld = doc.createElement("old");
            sTblOwner.appendChild(sTblOwnerOld);

            Element sTblOwnerNew = doc.createElement("new");
            if (baseTbl.owner != null)
                sTblOwnerNew.appendChild(doc.createTextNode(baseTbl.owner));
            sTblOwner.appendChild(sTblOwnerNew);

            Element sTblOwnerState = doc.createElement("state");
            sTblOwnerState.appendChild(doc.createTextNode("diff"));
            sTblOwner.appendChild(sTblOwnerState);

            /* Status */
            Element sTblStatus = doc.createElement("sStatus");
            tableElement.appendChild(sTblStatus);

            Element sTblStatusOld = doc.createElement("old");
            sTblStatus.appendChild(sTblStatusOld);

            Element sTblStatusNew = doc.createElement("new");
            if (baseTbl.status != null)
                sTblStatusNew.appendChild(doc.createTextNode(baseTbl.status));
            sTblStatus.appendChild(sTblStatusNew);

            Element sTblStatusState = doc.createElement("state");
            sTblStatusState.appendChild(doc.createTextNode("diff"));
            sTblStatus.appendChild(sTblStatusState);

            /* Partitioned */
            Element sTblPartitioned = doc.createElement("sPartitioned");
            tableElement.appendChild(sTblPartitioned);

            Element sTblPartitionedOld = doc.createElement("old");
            sTblPartitioned.appendChild(sTblPartitionedOld);

            Element sTblPartitionedNew = doc.createElement("new");
            if (baseTbl.partitioned != null)
                sTblPartitionedNew.appendChild(doc.createTextNode(baseTbl.partitioned));
            sTblPartitioned.appendChild(sTblPartitionedNew);

            Element sTblPartitionedState = doc.createElement("state");
            sTblPartitionedState.appendChild(doc.createTextNode("diff"));
            sTblPartitioned.appendChild(sTblPartitionedState);

            /* Temporary */
            Element sTblTemporary = doc.createElement("sTemporary");
            tableElement.appendChild(sTblTemporary);

            Element sTblTemporaryOld = doc.createElement("old");
            sTblTemporary.appendChild(sTblTemporaryOld);

            Element sTblTemporaryNew = doc.createElement("new");
            if (baseTbl.temporary != null)
                sTblTemporaryNew.appendChild(doc.createTextNode(baseTbl.temporary));
            sTblTemporary.appendChild(sTblTemporaryNew);

            Element sTblTemporaryState = doc.createElement("state");
            sTblTemporaryState.appendChild(doc.createTextNode("diff"));
            sTblTemporary.appendChild(sTblTemporaryState);

            /* Compression */
            Element sTblCompression = doc.createElement("sCompression");
            tableElement.appendChild(sTblCompression);

            Element sTblCompressionOld = doc.createElement("old");
            sTblCompression.appendChild(sTblCompressionOld);

            Element sTblCompressionNew = doc.createElement("new");
            if (baseTbl.compression != null)
                sTblCompressionNew.appendChild(doc.createTextNode(baseTbl.compression));
            sTblCompression.appendChild(sTblCompressionNew);

            Element sTblCompressionState = doc.createElement("state");
            sTblCompressionState.appendChild(doc.createTextNode("diff"));
            sTblCompression.appendChild(sTblCompressionState);

                    /* logging */
            Element sTblLogging = doc.createElement("sLogging");
            tableElement.appendChild(sTblLogging);

            Element sTblLoggingOld = doc.createElement("old");
            sTblLogging.appendChild(sTblLoggingOld);

            Element sTblLoggingNew = doc.createElement("new");
            if (baseTbl.logging != null)
                sTblLoggingNew.appendChild(doc.createTextNode(baseTbl.logging));
            sTblLogging.appendChild(sTblLoggingNew);

            Element sTblLoggingState = doc.createElement("state");
            sTblLoggingState.appendChild(doc.createTextNode("diff"));
            sTblLogging.appendChild(sTblLoggingState);

            /* cache */
            Element sTblCache = doc.createElement("sCache");
            tableElement.appendChild(sTblCache);

            Element sTblCacheOld = doc.createElement("old");
            sTblCache.appendChild(sTblCacheOld);

            Element sTblCacheNew = doc.createElement("new");
            if (baseTbl.cache != null)
                sTblCacheNew.appendChild(doc.createTextNode(baseTbl.cache));
            sTblCache.appendChild(sTblCacheNew);

            Element sTblCacheState = doc.createElement("state");
            sTblCacheState.appendChild(doc.createTextNode("diff"));
            sTblCache.appendChild(sTblCacheState);

            /* table_lock */
            Element sTblTableLock = doc.createElement("sTableLock");
            tableElement.appendChild(sTblTableLock);

            Element sTblTableLockOld = doc.createElement("old");
            sTblTableLock.appendChild(sTblTableLockOld);

            Element sTblTableLockNew = doc.createElement("new");
            if (baseTbl.table_lock != null)
                sTblTableLockNew.appendChild(doc.createTextNode(baseTbl.table_lock));
            sTblTableLock.appendChild(sTblTableLockNew);

            Element sTblTableLockState = doc.createElement("state");
            sTblTableLockState.appendChild(doc.createTextNode("diff"));
            sTblTableLock.appendChild(sTblTableLockState);
        }

        // Вернем результат
        return getStringFromDoc(doc);

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
            return null;
        }
    }
}
