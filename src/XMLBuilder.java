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


    public XMLBuilder() {}

    /**
     * Вспомогательная функция
     * Преобразуем doc в String
     */
    private String getStringFromDoc(Document doc)    {

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
    }

    /**
     * Сгенерим xml на основе info
     * @param info
     * @return
     */
    public String genStandartXML (Info info) throws ParserConfigurationException {
        try {

            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            // root element
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

            // Строим на основе коллекции таблиц
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
            System.out.println("XMLBuilder.genStandartXML: ошибка при генерации xml");
            pce.printStackTrace();
            throw pce;
        }
    }

    /**
     * Создадим объект Info на основе переданного XML
     * @param strXML
     * @return
     */
    public Info parseStandartXml (String strXML) throws Exception {
        Info info = new Info();
        Document doc = null;
        try {
            DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            InputSource is = new InputSource();
            is.setCharacterStream(new StringReader(strXML));

            doc = db.parse(is);
        } catch (Exception e) {
            System.out.println("XMLBuilder.parseStandartXml: ошибка при разборе xml");
            e.printStackTrace();
            throw e;
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
        return info;
    }


    public String compareStandartXML (Info baseInfo, Info fileInfo) throws ParserConfigurationException {
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

        // Перебираем все таблицы из файла
        for (TableInfo fileTbl : fileInfo.tableInfoArrayList) {

            // Сюда (baseTbl) мы закинем тот объект который надо удалить из массива
            // Этот объект-таблицу мы постараемся найти в списке, который пришел к нам из базы
            TableInfo baseTbl = baseInfo.getTableInfoByName(fileTbl.name);

            // Если что то нашли то с этим объектом надо продолжить работу
            if (baseTbl != null) {
                tablesElement.appendChild(generateComparedTable (doc, fileTbl, baseTbl));
                baseInfo.tableInfoArrayList.remove(baseTbl);
            }
            // Если ничего не нашли то занесем в результат как таблицу только из файла
            // Со всеми ее колонками
            else {
                tablesElement.appendChild(generateTableJustInFile(doc, fileTbl));
            }
        }

        // К этому моменту таблицы в файле закончились
        // Все таблицы которые пришли из базы и остались в списке надо занести как таблицы только в базе
        for ( TableInfo baseTbl : baseInfo.tableInfoArrayList ) {
            tablesElement.appendChild(generateTableJustInBase(doc, baseTbl));
        }

        // Вернем результат
        return getStringFromDoc(doc);

        } catch (ParserConfigurationException e) {
            System.out.println("XMLBuilder.compareStandartXML: ошибка при сравнении info / построении результирующего xml");
            e.printStackTrace();
            throw e;
        }
    }


    private Element generateTableJustInFile(Document doc, TableInfo tbl) throws ParserConfigurationException {

        /** Пример того что должно получится на выходе
         * <Table place="file">
         *  <sTableName>CMS_WF_STATUS</sTableName>
         *  <sOwner>
         *      <file>CRM_DAILY</file>
         *      <base></base>
         *      <state>diff</state>
         *  </sOwner>
         *  <sStatus>
         *      <file>VALID</file>
         *      <base></base>
         *      <state>diff</state>
         *  </sStatus>
         *  <sPartitioned>
         *      <file>NO</file>
         *      <base></base>
         *      <state>diff</state>
         *  </sPartitioned>
         *  <sTemporary>
         *      <file>N</file>
         *      <base></base>
         *      <state>diff</state>
         *  </sTemporary>
         *  <sCompression>
         *      <file>DISABLED</file>
         *      <base></base>
         *      <state>diff</state>
         *  </sCompression>
         *  <sLogging>
         *      <file>YES</file>
         *      <base></base>
         *      <state>diff</state>
         *  </sLogging>
         *  <sCache>
         *      <file>N</file>
         *      <base></base>
         *      <state>diff</state>
         *  </sCache>
         *  <sTableLock>
         *      <file>ENABLED</file>
         *      <base></base>
         *      <state>diff</state>
         *  </sTableLock>
         *  <Columns>
         *      <Column></Column>
         *  </Columns>
         * </Table>
         */


        Element tableElement = doc.createElement("Table");
        tableElement.setAttribute("place", "file");
        // Эта часть строит информацю  о таблице
        // Name
        Element sTblTableName = doc.createElement("sTableName");
        sTblTableName.appendChild(doc.createTextNode(tbl.name));
        tableElement.appendChild(sTblTableName);

        // owner
        Element sTblOwner = doc.createElement("sOwner");
        tableElement.appendChild(sTblOwner);
        Element sTblOwnerFile = doc.createElement("file");
        sTblOwnerFile.appendChild(doc.createTextNode(tbl.owner));
        sTblOwner.appendChild(sTblOwnerFile);
        sTblOwner.appendChild(doc.createElement("base"));
        Element sTblOwnerState = doc.createElement("state");
        sTblOwnerState.appendChild(doc.createTextNode("diff"));
        sTblOwner.appendChild(sTblOwnerState);

        // Status
        Element sTblStatus = doc.createElement("sStatus");
        tableElement.appendChild(sTblStatus);
        Element sTblStatusFile = doc.createElement("file");
        if (tbl.status != null)
            sTblStatusFile.appendChild(doc.createTextNode(tbl.status));
        sTblStatus.appendChild(sTblStatusFile);
        sTblStatus.appendChild(doc.createElement("base"));
        Element sTblStatusState = doc.createElement("state");
        sTblStatusState.appendChild(doc.createTextNode("diff"));
        sTblStatus.appendChild(sTblStatusState);

        // Partitioned
        Element sTblPartitioned = doc.createElement("sPartitioned");
        tableElement.appendChild(sTblPartitioned);
        Element sTblPartitionedFile = doc.createElement("file");
        if (tbl.partitioned != null)
            sTblPartitionedFile.appendChild(doc.createTextNode(tbl.partitioned));
        sTblPartitioned.appendChild(sTblPartitionedFile);
        sTblPartitioned.appendChild(doc.createElement("base"));
        Element sTblPartitionedState = doc.createElement("state");
        sTblPartitionedState.appendChild(doc.createTextNode("diff"));
        sTblPartitioned.appendChild(sTblPartitionedState);

        // Temporary
        Element sTblTemporary = doc.createElement("sTemporary");
        tableElement.appendChild(sTblTemporary);
        Element sTblTemporaryFile = doc.createElement("file");
        if (tbl.temporary != null)
            sTblTemporaryFile.appendChild(doc.createTextNode(tbl.temporary));
        sTblTemporary.appendChild(sTblTemporaryFile);
        sTblTemporary.appendChild(doc.createElement("base"));
        Element sTblTemporaryState = doc.createElement("state");
        sTblTemporaryState.appendChild(doc.createTextNode("diff"));
        sTblTemporary.appendChild(sTblTemporaryState);

        // Compression
        Element sTblCompression = doc.createElement("sCompression");
        tableElement.appendChild(sTblCompression);
        Element sTblCompressionFile = doc.createElement("file");
        if (tbl.compression != null)
            sTblCompressionFile.appendChild(doc.createTextNode(tbl.compression));
        sTblCompression.appendChild(sTblCompressionFile);
        sTblCompression.appendChild(doc.createElement("base"));
        Element sTblCompressionState = doc.createElement("state");
        sTblCompressionState.appendChild(doc.createTextNode("diff"));
        sTblCompression.appendChild(sTblCompressionState);

        // logging
        Element sTblLogging = doc.createElement("sLogging");
        tableElement.appendChild(sTblLogging);
        Element sTblLoggingFile = doc.createElement("file");
        if (tbl.logging != null)
            sTblLoggingFile.appendChild(doc.createTextNode(tbl.logging));
        sTblLogging.appendChild(sTblLoggingFile);
        sTblLogging.appendChild(doc.createElement("base"));
        Element sTblLoggingState = doc.createElement("state");
        sTblLoggingState.appendChild(doc.createTextNode("diff"));
        sTblLogging.appendChild(sTblLoggingState);

        // cache
        Element sTblCache = doc.createElement("sCache");
        tableElement.appendChild(sTblCache);
        Element sTblCacheFile = doc.createElement("file");
        if (tbl.cache != null)
            sTblCacheFile.appendChild(doc.createTextNode(tbl.cache));
        sTblCache.appendChild(sTblCacheFile);
        sTblCache.appendChild(doc.createElement("base"));
        Element sTblCacheState = doc.createElement("state");
        sTblCacheState.appendChild(doc.createTextNode("diff"));
        sTblCache.appendChild(sTblCacheState);

        // table_lock
        Element sTblTableLock = doc.createElement("sTableLock");
        tableElement.appendChild(sTblTableLock);
        Element sTblTableLockFile = doc.createElement("file");
        if (tbl.table_lock != null)
            sTblTableLockFile.appendChild(doc.createTextNode(tbl.table_lock));
        sTblTableLock.appendChild(sTblTableLockFile);
        sTblTableLock.appendChild(doc.createElement("base"));
        Element sTblTableLockState = doc.createElement("state");
        sTblTableLockState.appendChild(doc.createTextNode("diff"));
        sTblTableLock.appendChild(sTblTableLockState);

        // Теперь сюда надо добавить информацию по колонкам этой таблицы.
        //
        Element columnsElement = doc.createElement("Columns");
        tableElement.appendChild(columnsElement);

        for (ColumnInfo col : tbl.columnInfoArrayList) {
            // Эта часть строит информацю о колонках
            columnsElement.appendChild(generateColumnJustInFile(doc, col));
        }

        return tableElement;
    }

    private Element generateTableJustInBase(Document doc, TableInfo tbl) throws ParserConfigurationException {

        /** Пример того что должно получится на выходе
         * <Table place="base">
         *  <sTableName>CMS_WF_STATUS</sTableName>
         *  <sOwner>
         *      <file></file>
         *      <base>CRM_DAILY</base>
         *      <state>diff</state>
         *  </sOwner>
         *  <sStatus>
         *      <file></file>
         *      <base>VALID</base>
         *      <state>diff</state>
         *  </sStatus>
         *  <sPartitioned>
         *      <file></file>
         *      <base>NO</base>
         *      <state>diff</state>
         *  </sPartitioned>
         *  <sTemporary>
         *      <file></file>
         *      <base>N</base>
         *      <state>diff</state>
         *  </sTemporary>
         *  <sCompression>
         *      <file></file>
         *      <base>DISABLED</base>
         *      <state>diff</state>
         *  </sCompression>
         *  <sLogging>
         *      <file></file>
         *      <base>YES</base>
         *      <state>diff</state>
         *  </sLogging>
         *  <sCache>
         *      <file></file>
         *      <base>N</base>
         *      <state>diff</state>
         *  </sCache>
         *  <sTableLock>
         *      <file></file>
         *      <base>ENABLED</base>
         *      <state>diff</state>
         *  </sTableLock>
         *  <Columns>
         *      <Column></Column>
         *  </Columns>
         * </Table>
         */


        Element tableElement = doc.createElement("Table");
        tableElement.setAttribute("place", "base");
        // Эта часть строит информацю  о таблице
        // Name
        Element sTblTableName = doc.createElement("sTableName");
        sTblTableName.appendChild(doc.createTextNode(tbl.name));
        tableElement.appendChild(sTblTableName);

        // owner
        Element sTblOwner = doc.createElement("sOwner");
        tableElement.appendChild(sTblOwner);
        Element sTblOwnerFile = doc.createElement("base");
        sTblOwnerFile.appendChild(doc.createTextNode(tbl.owner));
        sTblOwner.appendChild(sTblOwnerFile);
        sTblOwner.appendChild(doc.createElement("file"));
        Element sTblOwnerState = doc.createElement("state");
        sTblOwnerState.appendChild(doc.createTextNode("diff"));
        sTblOwner.appendChild(sTblOwnerState);

        // Status
        Element sTblStatus = doc.createElement("sStatus");
        tableElement.appendChild(sTblStatus);
        Element sTblStatusFile = doc.createElement("base");
        if (tbl.status != null)
            sTblStatusFile.appendChild(doc.createTextNode(tbl.status));
        sTblStatus.appendChild(sTblStatusFile);
        sTblStatus.appendChild(doc.createElement("file"));
        Element sTblStatusState = doc.createElement("state");
        sTblStatusState.appendChild(doc.createTextNode("diff"));
        sTblStatus.appendChild(sTblStatusState);

        // Partitioned
        Element sTblPartitioned = doc.createElement("sPartitioned");
        tableElement.appendChild(sTblPartitioned);
        Element sTblPartitionedFile = doc.createElement("base");
        if (tbl.partitioned != null)
            sTblPartitionedFile.appendChild(doc.createTextNode(tbl.partitioned));
        sTblPartitioned.appendChild(sTblPartitionedFile);
        sTblPartitioned.appendChild(doc.createElement("file"));
        Element sTblPartitionedState = doc.createElement("state");
        sTblPartitionedState.appendChild(doc.createTextNode("diff"));
        sTblPartitioned.appendChild(sTblPartitionedState);

        // Temporary
        Element sTblTemporary = doc.createElement("sTemporary");
        tableElement.appendChild(sTblTemporary);
        Element sTblTemporaryFile = doc.createElement("base");
        if (tbl.temporary != null)
            sTblTemporaryFile.appendChild(doc.createTextNode(tbl.temporary));
        sTblTemporary.appendChild(sTblTemporaryFile);
        sTblTemporary.appendChild(doc.createElement("file"));
        Element sTblTemporaryState = doc.createElement("state");
        sTblTemporaryState.appendChild(doc.createTextNode("diff"));
        sTblTemporary.appendChild(sTblTemporaryState);

        // Compression
        Element sTblCompression = doc.createElement("sCompression");
        tableElement.appendChild(sTblCompression);
        Element sTblCompressionFile = doc.createElement("base");
        if (tbl.compression != null)
            sTblCompressionFile.appendChild(doc.createTextNode(tbl.compression));
        sTblCompression.appendChild(sTblCompressionFile);
        sTblCompression.appendChild(doc.createElement("file"));
        Element sTblCompressionState = doc.createElement("state");
        sTblCompressionState.appendChild(doc.createTextNode("diff"));
        sTblCompression.appendChild(sTblCompressionState);

        // logging
        Element sTblLogging = doc.createElement("sLogging");
        tableElement.appendChild(sTblLogging);
        Element sTblLoggingFile = doc.createElement("base");
        if (tbl.logging != null)
            sTblLoggingFile.appendChild(doc.createTextNode(tbl.logging));
        sTblLogging.appendChild(sTblLoggingFile);
        sTblLogging.appendChild(doc.createElement("file"));
        Element sTblLoggingState = doc.createElement("state");
        sTblLoggingState.appendChild(doc.createTextNode("diff"));
        sTblLogging.appendChild(sTblLoggingState);

        // cache
        Element sTblCache = doc.createElement("sCache");
        tableElement.appendChild(sTblCache);
        Element sTblCacheFile = doc.createElement("base");
        if (tbl.cache != null)
            sTblCacheFile.appendChild(doc.createTextNode(tbl.cache));
        sTblCache.appendChild(sTblCacheFile);
        sTblCache.appendChild(doc.createElement("file"));
        Element sTblCacheState = doc.createElement("state");
        sTblCacheState.appendChild(doc.createTextNode("diff"));
        sTblCache.appendChild(sTblCacheState);

        // table_lock
        Element sTblTableLock = doc.createElement("sTableLock");
        tableElement.appendChild(sTblTableLock);
        Element sTblTableLockFile = doc.createElement("base");
        if (tbl.table_lock != null)
            sTblTableLockFile.appendChild(doc.createTextNode(tbl.table_lock));
        sTblTableLock.appendChild(sTblTableLockFile);
        sTblTableLock.appendChild(doc.createElement("file"));
        Element sTblTableLockState = doc.createElement("state");
        sTblTableLockState.appendChild(doc.createTextNode("diff"));
        sTblTableLock.appendChild(sTblTableLockState);

        // Теперь сюда надо добавить информацию по колонкам этой таблицы.
        //
        Element columnsElement = doc.createElement("Columns");
        tableElement.appendChild(columnsElement);

        for (ColumnInfo col : tbl.columnInfoArrayList) {
            // Эта часть строит информацю о колонках
            columnsElement.appendChild(generateColumnJustInBase(doc, col));
        }
        return tableElement;
    }

    private Element generateComparedTable(Document doc, TableInfo fileTbl, TableInfo baseTbl) throws ParserConfigurationException {
        /** Пример того что должно получится на выходе
         * <Table place="both">
         *  <sTableName>CMS_WF_STATUS</sTableName>
         *  <sOwner>
         *      <file></file>
         *      <base>CRM_DAILY</base>
         *      <state>diff</state>
         *  </sOwner>
         *  <sStatus>
         *      <file></file>
         *      <base>VALID</base>
         *      <state>diff</state>
         *  </sStatus>
         *  <sPartitioned>
         *      <file></file>
         *      <base>NO</base>
         *      <state>diff</state>
         *  </sPartitioned>
         *  <sTemporary>
         *      <file></file>
         *      <base>N</base>
         *      <state>diff</state>
         *  </sTemporary>
         *  <sCompression>
         *      <file></file>
         *      <base>DISABLED</base>
         *      <state>diff</state>
         *  </sCompression>
         *  <sLogging>
         *      <file></file>
         *      <base>YES</base>
         *      <state>diff</state>
         *  </sLogging>
         *  <sCache>
         *      <file></file>
         *      <base>N</base>
         *      <state>diff</state>
         *  </sCache>
         *  <sTableLock>
         *      <file></file>
         *      <base>ENABLED</base>
         *      <state>diff</state>
         *  </sTableLock>
         *  <Columns>
         *      <Column></Column>
         *  </Columns>
         * </Table>
         */


        Element tableElement = doc.createElement("Table");
        tableElement.setAttribute("place", "both");
        // Эта часть строит информацю  о таблице
        // Name
        Element sTblTableName = doc.createElement("sTableName");
        sTblTableName.appendChild(doc.createTextNode(fileTbl.name));
        tableElement.appendChild(sTblTableName);

        /* owner */
        Element sTblOwner = doc.createElement("sOwner");
        tableElement.appendChild(sTblOwner);
        Element sTblOwnerFile = doc.createElement("file");
        sTblOwnerFile.appendChild(doc.createTextNode(fileTbl.owner));
        sTblOwner.appendChild(sTblOwnerFile);
        Element sTblOwnerBase = doc.createElement("base");
        sTblOwnerBase.appendChild(doc.createTextNode(baseTbl.owner));
        sTblOwner.appendChild(sTblOwnerBase);
        Element sTblOwnerState = doc.createElement("state");
//        if ((baseTbl.owner == null && fileTbl.owner == null) || ( baseTbl.owner.equals( fileTbl.owner) ))
            sTblOwnerState.appendChild(doc.createTextNode("same"));
//        else
//            sTblOwnerState.appendChild(doc.createTextNode("diff"));
        sTblOwner.appendChild(sTblOwnerState);

        // Status
        Element sTblStatus = doc.createElement("sStatus");
        tableElement.appendChild(sTblStatus);
        Element sTblStatusFile = doc.createElement("file");
        if (fileTbl.status != null)
            sTblStatusFile.appendChild(doc.createTextNode(fileTbl.status));
        sTblStatus.appendChild(sTblStatusFile);
        Element sTblStatusBase = doc.createElement("base");
        if (baseTbl.status != null)
            sTblStatusBase.appendChild(doc.createTextNode(baseTbl.status));
        sTblStatus.appendChild(sTblStatusBase);
        Element sTblStatusState = doc.createElement("state");
        if ((baseTbl.status == null && fileTbl.status == null) || (baseTbl.status.equals( fileTbl.status )))
            sTblStatusState.appendChild(doc.createTextNode("same"));
        else
            sTblStatusState.appendChild(doc.createTextNode("diff"));
        sTblStatus.appendChild(sTblStatusState);

        // Partitioned
        Element sTblPartitioned = doc.createElement("sPartitioned");
        tableElement.appendChild(sTblPartitioned);
        Element sTblPartitionedFile = doc.createElement("file");
        if (fileTbl.partitioned != null)
            sTblPartitionedFile.appendChild(doc.createTextNode(fileTbl.partitioned));
        sTblPartitioned.appendChild(sTblPartitionedFile);
        Element sTblPartitionedBase = doc.createElement("base");
        if (baseTbl.partitioned != null)
            sTblPartitionedBase.appendChild(doc.createTextNode(baseTbl.partitioned));
        sTblPartitioned.appendChild(sTblPartitionedBase);
        Element sTblPartitionedState = doc.createElement("state");
        if ((baseTbl.partitioned == null && fileTbl.partitioned == null) || ( baseTbl.partitioned.equals( fileTbl.partitioned ))) {
            sTblPartitionedState.appendChild(doc.createTextNode("same"));
        }
        else {
            sTblPartitionedState.appendChild(doc.createTextNode("diff"));
        }
        sTblPartitioned.appendChild(sTblPartitionedState);

        // Temporary
        Element sTblTemporary = doc.createElement("sTemporary");
        tableElement.appendChild(sTblTemporary);
        Element sTblTemporaryFile = doc.createElement("file");
        if (fileTbl.temporary != null)
            sTblTemporaryFile.appendChild(doc.createTextNode(fileTbl.temporary));
        sTblTemporary.appendChild(sTblTemporaryFile);
        Element sTblTemporaryBase = doc.createElement("base");
        if (baseTbl.temporary != null)
            sTblTemporaryBase.appendChild(doc.createTextNode(baseTbl.temporary));
        sTblTemporary.appendChild(sTblTemporaryBase);
        Element sTblTemporaryState = doc.createElement("state");
        if ((baseTbl.temporary == null && fileTbl.temporary == null) || ( baseTbl.temporary.equals( fileTbl.temporary ))) {
            sTblTemporaryState.appendChild(doc.createTextNode("same"));
        }
        else {
            sTblTemporaryState.appendChild(doc.createTextNode("diff"));
        }
        sTblTemporary.appendChild(sTblTemporaryState);

        // Compression
        Element sTblCompression = doc.createElement("sCompression");
        tableElement.appendChild(sTblCompression);
        Element sTblCompressionFile = doc.createElement("file");
        if (fileTbl.compression != null)
            sTblCompressionFile.appendChild(doc.createTextNode(fileTbl.compression));
        sTblCompression.appendChild(sTblCompressionFile);
        Element sTblCompressionBase = doc.createElement("base");
        if (baseTbl.compression != null)
            sTblCompressionBase.appendChild(doc.createTextNode(baseTbl.compression));
        sTblCompression.appendChild(sTblCompressionBase);
        Element sTblCompressionState = doc.createElement("state");
        if ( (baseTbl.compression == null && fileTbl.compression == null) || ( baseTbl.compression.equals( fileTbl.compression) )) {
            sTblCompressionState.appendChild(doc.createTextNode("same"));
        }
        else {
            sTblCompressionState.appendChild(doc.createTextNode("diff"));
        }
        sTblCompression.appendChild(sTblCompressionState);

        // logging
        Element sTblLogging = doc.createElement("sLogging");
        tableElement.appendChild(sTblLogging);
        Element sTblLoggingFile = doc.createElement("file");
        if (fileTbl.logging != null)
            sTblLoggingFile.appendChild(doc.createTextNode(fileTbl.logging));
        sTblLogging.appendChild(sTblLoggingFile);
        Element sTblLoggingBase = doc.createElement("base");
        if (baseTbl.logging != null)
            sTblLoggingBase.appendChild(doc.createTextNode(baseTbl.logging));
        sTblLogging.appendChild(sTblLoggingBase);
        Element sTblLoggingState = doc.createElement("state");
        if ( (baseTbl.logging == null && fileTbl.logging == null) || (baseTbl.logging.equals( fileTbl.logging )) ) {
            sTblLoggingState.appendChild(doc.createTextNode("same"));
        }
        else {
            sTblLoggingState.appendChild(doc.createTextNode("diff"));
        }
        sTblLogging.appendChild(sTblLoggingState);

        // cache
        Element sTblCache = doc.createElement("sCache");
        tableElement.appendChild(sTblCache);
        Element sTblCacheFile = doc.createElement("file");
        if (fileTbl.cache != null)
            sTblCacheFile.appendChild(doc.createTextNode(fileTbl.cache));
        sTblCache.appendChild(sTblCacheFile);
        Element sTblCacheBase = doc.createElement("base");
        if (baseTbl.cache != null)
            sTblCacheBase.appendChild(doc.createTextNode(baseTbl.cache));
        sTblCache.appendChild(sTblCacheBase);
        Element sTblCacheState = doc.createElement("state");
        if ((baseTbl.cache == null && fileTbl.cache == null) || (baseTbl.cache.equals( fileTbl.cache ))) {
            sTblCacheState.appendChild(doc.createTextNode("same"));
        }
        else {
            sTblCacheState.appendChild(doc.createTextNode("diff"));
        }
        sTblCache.appendChild(sTblCacheState);

        // table_lock
        Element sTblTableLock = doc.createElement("sTableLock");
        tableElement.appendChild(sTblTableLock);
        Element sTblTableLockFile = doc.createElement("file");
        if (fileTbl.table_lock != null)
            sTblTableLockFile.appendChild(doc.createTextNode(fileTbl.table_lock));
        sTblTableLock.appendChild(sTblTableLockFile);
        Element sTblTableLockBase = doc.createElement("base");
        if (baseTbl.table_lock != null)
            sTblTableLockBase.appendChild(doc.createTextNode(baseTbl.table_lock));
        sTblTableLock.appendChild(sTblTableLockBase);
        Element sTblTableLockState = doc.createElement("state");
        if ((baseTbl.table_lock == null && fileTbl.table_lock == null) || (baseTbl.table_lock.equals( fileTbl.table_lock ))) {
            sTblTableLockState.appendChild(doc.createTextNode("same"));
        }
        else {
            sTblTableLockState.appendChild(doc.createTextNode("diff"));
        }
        sTblTableLock.appendChild(sTblTableLockState);


        // Теперь сюда надо добавить информацию по колонкам этой таблицы.
        // Число колонок может не совпасть в таблицах разных источников
        //
        Element columnsElement = doc.createElement("Columns");
        tableElement.appendChild(columnsElement);


        // Переберем все колонки из таблицы, которая пришла из файла
        for (ColumnInfo fileCol : fileTbl.columnInfoArrayList) {

            // Сюда (baseCol) мы закинем тот объект который надо удалить из массива
            // Этот объект-колонку мы постараемся найти в списке, который пришел к нам из списка колонок таблицы из базы
            ColumnInfo baseCol = baseTbl.getColumnInfoByName(fileCol.name);

            // Если что то нашли то с этим объектом надо продолжить работу
            if (baseCol != null) {
                columnsElement.appendChild(generateComparedColumn(doc, fileCol, baseCol));
                baseTbl.columnInfoArrayList.remove(baseCol);
            }
            // Если ничего не нашли то занесем в результат как таблицу только из файла
            // Со всеми ее колонками
            else {
                columnsElement.appendChild(generateColumnJustInFile(doc,  fileCol));
            }
        }

        // К этому моменту колонки из таблицы в файле закончились
        // Все колонки которые пришли из таблицы базы и остались в списке надо занести как колонки только в базе
        for ( ColumnInfo baseCol : baseTbl.columnInfoArrayList ) {
            columnsElement.appendChild(generateColumnJustInBase(doc, baseCol));
        }

        return tableElement;
    }

    private Element generateColumnJustInFile(Document doc, ColumnInfo col) throws ParserConfigurationException {
        /** Пример того что должно получится на выходе
         * <Column place="base">
         *  <sTableName>CMS_WF_STATUS</sTableName>
         *  <sColumnName>ID</sColumnName>
         *  <sOwner>
         *      <file>CRM_DAILY</file>
         *      <base></base>
         *      <state>diff</state>
         *  </sOwner>
         *  <sDataType>
         *      <file>xxx</file>
         *      <base></base>
         *      <state>diff</state>
         *  </sDataType>
         *  <iDataLength>
         *      <file>xxx</file>
         *      <base></base>
         *      <state>diff</state>
         *  </iDataLength>
         *  <iDataPrecision>
         *      <file>xxx</file>
         *      <base></base>
         *      <state>diff</state>
         *  </iDataPrecision>
         *  <iDataScale>
         *      <file>xxx</file>
         *      <base></base>
         *      <state>diff</state>
         *  </iDataScale>
         *  <sNullable>
         *      <file>xxx</file>
         *      <base></base>
         *      <state>diff</state>
         *  </sNullable>
         *  <iDefaultLength>
         *      <file>xxx</file>
         *      <base></base>
         *      <state>diff</state>
         *  </iDefaultLength>
         * </Column>
         */

        //
        // Эта часть строит информацю о колонках
        Element columnElement = doc.createElement("Column");
        columnElement.setAttribute("place", "file");
        //

        // Table_name
        Element sColTableName = doc.createElement("sTableName");
        sColTableName.appendChild(doc.createTextNode(col.table_name));
        columnElement.appendChild(sColTableName);

        // column_name
        Element sColName = doc.createElement("sColumnName");
        sColName.appendChild(doc.createTextNode(col.name));
        columnElement.appendChild(sColName);

        // owner
        Element sColOwner = doc.createElement("sOwner");
        columnElement.appendChild(sColOwner);
        Element sColOwnerFile = doc.createElement("file");
        sColOwnerFile.appendChild(doc.createTextNode(col.owner));
        sColOwner.appendChild(sColOwnerFile);
        sColOwner.appendChild(doc.createElement("base"));
        Element sColOwnerState = doc.createElement("state");
        sColOwnerState.appendChild(doc.createTextNode("diff"));
        sColOwner.appendChild(sColOwnerState);

        // data_type
        Element sColDataType = doc.createElement("sDataType");
        columnElement.appendChild(sColDataType);
        Element sColDataTypeFile = doc.createElement("file");
        sColDataTypeFile.appendChild(doc.createTextNode(col.data_type));
        sColDataType.appendChild(sColDataTypeFile);
        sColDataType.appendChild(doc.createElement("base"));
        Element sColDataTypeState = doc.createElement("state");
        sColDataTypeState.appendChild(doc.createTextNode("diff"));
        sColDataType.appendChild(sColDataTypeState);

        // data_length
        Element sColDataLength = doc.createElement("iDataLength");
        columnElement.appendChild(sColDataLength);
        Element sColDataLengthFile = doc.createElement("file");
        if (col.data_length != null)
            sColDataLengthFile.appendChild(doc.createTextNode(col.data_length));
        sColDataLength.appendChild(sColDataLengthFile);
        sColDataLength.appendChild(doc.createElement("base"));
        Element sColDataLengthState = doc.createElement("state");
        sColDataLengthState.appendChild(doc.createTextNode("diff"));
        sColDataLength.appendChild(sColDataLengthState);

        // data_precision
        Element sColDataPrecision = doc.createElement("iDataPrecision");
        columnElement.appendChild(sColDataPrecision);
        Element sColDataPrecisionFile = doc.createElement("file");
        if (col.data_precision != null)
            sColDataPrecisionFile.appendChild(doc.createTextNode(col.data_precision));
        sColDataPrecision.appendChild(sColDataPrecisionFile);
        sColDataPrecision.appendChild(doc.createElement("base"));
        Element sColDataPrecisionState = doc.createElement("state");
        sColDataPrecisionState.appendChild(doc.createTextNode("diff"));
        sColDataPrecision.appendChild(sColDataPrecisionState);

        // data_scale
        Element sColDataScale = doc.createElement("iDataScale");
        columnElement.appendChild(sColDataScale);
        Element sColDataScaleFile = doc.createElement("file");
        if (col.data_scale != null)
            sColDataScaleFile.appendChild(doc.createTextNode(col.data_scale));
        sColDataScale.appendChild(sColDataScaleFile);
        sColDataScale.appendChild(doc.createElement("base"));
        Element sColDataScaleState = doc.createElement("state");
        sColDataScaleState.appendChild(doc.createTextNode("diff"));
        sColDataScale.appendChild(sColDataScaleState);

        // nullable
        Element sColNullable = doc.createElement("sNullable");
        columnElement.appendChild(sColNullable);
        Element sColNullableFile = doc.createElement("file");
        if (col.nullable != null)
            sColNullableFile.appendChild(doc.createTextNode(col.nullable));
        sColNullable.appendChild(sColNullableFile);
        Element sColNullableNew = doc.createElement("base");
        sColNullable.appendChild(sColNullableNew);
        Element sColNullableState = doc.createElement("state");
        sColNullableState.appendChild(doc.createTextNode("diff"));
        sColNullable.appendChild(sColNullableState);

        // default_length
        Element sColDefaultLength = doc.createElement("iDefaultLength");
        columnElement.appendChild(sColDefaultLength);
        Element sColDefaultLengthFile = doc.createElement("file");
        if (col.default_length != null)
            sColDefaultLengthFile.appendChild(doc.createTextNode(col.default_length));
        sColDefaultLength.appendChild(sColDefaultLengthFile);
        Element sColDefaultLengthNew = doc.createElement("base");
        sColDefaultLength.appendChild(sColDefaultLengthNew);
        Element sColDefaultLengthState = doc.createElement("state");
        sColDefaultLengthState.appendChild(doc.createTextNode("diff"));
        sColDefaultLength.appendChild(sColDefaultLengthState);

        return columnElement;
    }

    private Element generateColumnJustInBase(Document doc, ColumnInfo col) throws ParserConfigurationException {
        /** Пример того что должно получится на выходе
         * <Column place="base">
         *  <sTableName>CMS_WF_STATUS</sTableName>
         *  <sColumnName>ID</sColumnName>
         *  <sOwner>
         *      <file>CRM_DAILY</file>
         *      <base></base>
         *      <state>diff</state>
         *  </sOwner>
         *  <sDataType>
         *      <file>xxx</file>
         *      <base></base>
         *      <state>diff</state>
         *  </sDataType>
         *  <iDataLength>
         *      <file>xxx</file>
         *      <base></base>
         *      <state>diff</state>
         *  </iDataLength>
         *  <iDataPrecision>
         *      <file>xxx</file>
         *      <base></base>
         *      <state>diff</state>
         *  </iDataPrecision>
         *  <iDataScale>
         *      <file>xxx</file>
         *      <base></base>
         *      <state>diff</state>
         *  </iDataScale>
         *  <sNullable>
         *      <file>xxx</file>
         *      <base></base>
         *      <state>diff</state>
         *  </sNullable>
         *  <iDefaultLength>
         *      <file>xxx</file>
         *      <base></base>
         *      <state>diff</state>
         *  </iDefaultLength>
         * </Column>
         */

        //
        // Эта часть строит информацю о колонках
        Element columnElement = doc.createElement("Column");
        columnElement.setAttribute("place", "base");
        //

        // Table_name
        Element sColTableName = doc.createElement("sTableName");
        sColTableName.appendChild(doc.createTextNode(col.table_name));
        columnElement.appendChild(sColTableName);

        // column_name
        Element sColName = doc.createElement("sColumnName");
        sColName.appendChild(doc.createTextNode(col.name));
        columnElement.appendChild(sColName);

        // owner
        Element sColOwner = doc.createElement("sOwner");
        columnElement.appendChild(sColOwner);
        Element sColOwnerBase = doc.createElement("base");
        sColOwnerBase.appendChild(doc.createTextNode(col.owner));
        sColOwner.appendChild(sColOwnerBase);
        sColOwner.appendChild(doc.createElement("file"));
        Element sColOwnerState = doc.createElement("state");
        sColOwnerState.appendChild(doc.createTextNode("diff"));
        sColOwner.appendChild(sColOwnerState);

        // data_type
        Element sColDataType = doc.createElement("sDataType");
        columnElement.appendChild(sColDataType);
        Element sColDataTypeBase = doc.createElement("base");
        sColDataTypeBase.appendChild(doc.createTextNode(col.data_type));
        sColDataType.appendChild(sColDataTypeBase);
        sColDataType.appendChild(doc.createElement("file"));
        Element sColDataTypeState = doc.createElement("state");
        sColDataTypeState.appendChild(doc.createTextNode("diff"));
        sColDataType.appendChild(sColDataTypeState);

        // data_length
        Element sColDataLength = doc.createElement("iDataLength");
        columnElement.appendChild(sColDataLength);
        Element sColDataLengthBase = doc.createElement("base");
        if (col.data_length != null)
            sColDataLengthBase.appendChild(doc.createTextNode(col.data_length));
        sColDataLength.appendChild(sColDataLengthBase);
        sColDataLength.appendChild(doc.createElement("file"));
        Element sColDataLengthState = doc.createElement("state");
        sColDataLengthState.appendChild(doc.createTextNode("diff"));
        sColDataLength.appendChild(sColDataLengthState);

        // data_precision
        Element sColDataPrecision = doc.createElement("iDataPrecision");
        columnElement.appendChild(sColDataPrecision);
        Element sColDataPrecisionBase = doc.createElement("base");
        if (col.data_precision != null)
            sColDataPrecisionBase.appendChild(doc.createTextNode(col.data_precision));
        sColDataPrecision.appendChild(sColDataPrecisionBase);
        sColDataPrecision.appendChild(doc.createElement("file"));
        Element sColDataPrecisionState = doc.createElement("state");
        sColDataPrecisionState.appendChild(doc.createTextNode("diff"));
        sColDataPrecision.appendChild(sColDataPrecisionState);

        // data_scale
        Element sColDataScale = doc.createElement("iDataScale");
        columnElement.appendChild(sColDataScale);
        Element sColDataScaleBase = doc.createElement("base");
        if (col.data_scale != null)
            sColDataScaleBase.appendChild(doc.createTextNode(col.data_scale));
        sColDataScale.appendChild(sColDataScaleBase);
        sColDataScale.appendChild(doc.createElement("file"));
        Element sColDataScaleState = doc.createElement("state");
        sColDataScaleState.appendChild(doc.createTextNode("diff"));
        sColDataScale.appendChild(sColDataScaleState);

        // nullable
        Element sColNullable = doc.createElement("sNullable");
        columnElement.appendChild(sColNullable);
        Element sColNullableBase = doc.createElement("base");
        if (col.nullable != null)
            sColNullableBase.appendChild(doc.createTextNode(col.nullable));
        sColNullable.appendChild(sColNullableBase);
        Element sColNullableNew = doc.createElement("file");
        sColNullable.appendChild(sColNullableNew);
        Element sColNullableState = doc.createElement("state");
        sColNullableState.appendChild(doc.createTextNode("diff"));
        sColNullable.appendChild(sColNullableState);

        // default_length
        Element sColDefaultLength = doc.createElement("iDefaultLength");
        columnElement.appendChild(sColDefaultLength);
        Element sColDefaultLengthBase = doc.createElement("base");
        if (col.default_length != null)
            sColDefaultLengthBase.appendChild(doc.createTextNode(col.default_length));
        sColDefaultLength.appendChild(sColDefaultLengthBase);
        Element sColDefaultLengthNew = doc.createElement("file");
        sColDefaultLength.appendChild(sColDefaultLengthNew);
        Element sColDefaultLengthState = doc.createElement("state");
        sColDefaultLengthState.appendChild(doc.createTextNode("diff"));
        sColDefaultLength.appendChild(sColDefaultLengthState);

        return columnElement;
    }

    private Element generateComparedColumn(Document doc, ColumnInfo fileCol, ColumnInfo baseCol ) throws ParserConfigurationException {
        /** Пример того что должно получится на выходе
         * <Column place="both">
         *  <sTableName>CMS_WF_STATUS</sTableName>
         *  <sColumnName>ID</sColumnName>
         *  <sOwner>
         *      <file>CRM_DAILY</file>
         *      <base></base>
         *      <state>diff</state>
         *  </sOwner>
         *  <sDataType>
         *      <file>xxx</file>
         *      <base></base>
         *      <state>diff</state>
         *  </sDataType>
         *  <iDataLength>
         *      <file>xxx</file>
         *      <base></base>
         *      <state>diff</state>
         *  </iDataLength>
         *  <iDataPrecision>
         *      <file>xxx</file>
         *      <base></base>
         *      <state>diff</state>
         *  </iDataPrecision>
         *  <iDataScale>
         *      <file>xxx</file>
         *      <base></base>
         *      <state>diff</state>
         *  </iDataScale>
         *  <sNullable>
         *      <file>xxx</file>
         *      <base></base>
         *      <state>diff</state>
         *  </sNullable>
         *  <iDefaultLength>
         *      <file>xxx</file>
         *      <base></base>
         *      <state>diff</state>
         *  </iDefaultLength>
         * </Column>
         */

        //
        // Эта часть строит информацю о колонках
        Element columnElement = doc.createElement("Column");
        columnElement.setAttribute("place", "both");
        //

        // Table_name
        Element sColTableName = doc.createElement("sTableName");
        sColTableName.appendChild(doc.createTextNode(fileCol.table_name));
        columnElement.appendChild(sColTableName);

        // column_name
        Element sColName = doc.createElement("sColumnName");
        sColName.appendChild(doc.createTextNode(fileCol.name));
        columnElement.appendChild(sColName);

        // owner
        Element sColOwner = doc.createElement("sOwner");
        columnElement.appendChild(sColOwner);
        Element sColOwnerBase = doc.createElement("base");
        sColOwnerBase.appendChild(doc.createTextNode(baseCol.owner));
        sColOwner.appendChild(sColOwnerBase);
        Element sColOwnerFile = doc.createElement("file");
        sColOwnerFile.appendChild(doc.createTextNode(fileCol.owner));
        sColOwner.appendChild(sColOwnerFile);

        Element sColOwnerState = doc.createElement("state");
//        if ((baseCol.owner == null && fileCol.owner == null) || (baseCol.owner.equalsIgnoreCase( fileCol.owner ))) {
            sColOwnerState.appendChild(doc.createTextNode("same"));
//        }
//        else {
//            sColOwnerState.appendChild(doc.createTextNode("diff"));
//        }
        sColOwner.appendChild(sColOwnerState);

        // data_type
        Element sColDataType = doc.createElement("sDataType");
        columnElement.appendChild(sColDataType);
        Element sColDataTypeBase = doc.createElement("base");
        sColDataTypeBase.appendChild(doc.createTextNode(baseCol.data_type));
        sColDataType.appendChild(sColDataTypeBase);
        Element sColDataTypeFile = doc.createElement("file");
        sColDataTypeFile.appendChild(doc.createTextNode(fileCol.data_type));
        sColDataType.appendChild(sColDataTypeFile);
        Element sColDataTypeState = doc.createElement("state");
        if ((baseCol.data_type == null && fileCol.data_type == null) || (baseCol.data_type.equalsIgnoreCase( fileCol.data_type ))) {
            sColDataTypeState.appendChild(doc.createTextNode("same"));
        }
        else {
            sColDataTypeState.appendChild(doc.createTextNode("diff"));
        }
        sColDataType.appendChild(sColDataTypeState);

        // data_length
        Element sColDataLength = doc.createElement("iDataLength");
        columnElement.appendChild(sColDataLength);
        Element sColDataLengthBase = doc.createElement("base");
        if (baseCol.data_length != null)
            sColDataLengthBase.appendChild(doc.createTextNode(baseCol.data_length));
        sColDataLength.appendChild(sColDataLengthBase);
        Element sColDataLengthFile = doc.createElement("file");
        if (baseCol.data_length != null)
            sColDataLengthFile.appendChild(doc.createTextNode(fileCol.data_length));
        sColDataLength.appendChild(sColDataLengthFile);
        Element sColDataLengthState = doc.createElement("state");
        if ((baseCol.data_length == null && fileCol.data_length == null) || (baseCol.data_length.equalsIgnoreCase( fileCol.data_length ))) {
            sColDataLengthState.appendChild(doc.createTextNode("same"));
        }
        else {
            sColDataLengthState.appendChild(doc.createTextNode("diff"));
        }
        sColDataLength.appendChild(sColDataLengthState);

        // data_precision
        Element sColDataPrecision = doc.createElement("iDataPrecision");
        columnElement.appendChild(sColDataPrecision);
        Element sColDataPrecisionBase = doc.createElement("base");
        if (baseCol.data_precision != null)
            sColDataPrecisionBase.appendChild(doc.createTextNode(baseCol.data_precision));
        sColDataPrecision.appendChild(sColDataPrecisionBase);
        Element sColDataPrecisionFile = doc.createElement("file");
        if (fileCol.data_precision != null)
            sColDataPrecisionFile.appendChild(doc.createTextNode(fileCol.data_precision));
        sColDataPrecision.appendChild(sColDataPrecisionFile);
        Element sColDataPrecisionState = doc.createElement("state");
        if ((baseCol.data_precision == null && fileCol.data_precision == null) || (baseCol.data_precision.equalsIgnoreCase( fileCol.data_precision ))) {
            sColDataPrecisionState.appendChild(doc.createTextNode("same"));
        }
        else {
            sColDataPrecisionState.appendChild(doc.createTextNode("diff"));
        }
        sColDataPrecision.appendChild(sColDataPrecisionState);

        // data_scale
        Element sColDataScale = doc.createElement("iDataScale");
        columnElement.appendChild(sColDataScale);
        Element sColDataScaleBase = doc.createElement("base");
        if (baseCol.data_scale != null)
            sColDataScaleBase.appendChild(doc.createTextNode(baseCol.data_scale));
        sColDataScale.appendChild(sColDataScaleBase);
        Element sColDataScaleFile = doc.createElement("file");
        if (fileCol.data_scale != null)
            sColDataScaleFile.appendChild(doc.createTextNode(fileCol.data_scale));
        sColDataScale.appendChild(sColDataScaleFile);
        Element sColDataScaleState = doc.createElement("state");
        if ((baseCol.data_scale == null && fileCol.data_scale == null) || (baseCol.data_scale.equalsIgnoreCase( fileCol.data_scale ))) {
            sColDataScaleState.appendChild(doc.createTextNode("same"));
        }
        else {
            sColDataScaleState.appendChild(doc.createTextNode("diff"));
        }
        sColDataScale.appendChild(sColDataScaleState);

        // nullable
        Element sColNullable = doc.createElement("sNullable");
        columnElement.appendChild(sColNullable);
        Element sColNullableBase = doc.createElement("base");
        if (baseCol.nullable != null)
            sColNullableBase.appendChild(doc.createTextNode(baseCol.nullable));
        sColNullable.appendChild(sColNullableBase);
        Element sColNullableFile = doc.createElement("file");
        if (fileCol.nullable != null)
            sColNullableFile.appendChild(doc.createTextNode(fileCol.nullable));
        sColNullable.appendChild(sColNullableFile);
        Element sColNullableState = doc.createElement("state");
        if ((baseCol.nullable == null && fileCol.nullable == null) || (baseCol.nullable.equalsIgnoreCase( fileCol.nullable ))) {
            sColNullableState.appendChild(doc.createTextNode("same"));
        }
        else {
            sColNullableState.appendChild(doc.createTextNode("diff"));
        }
        sColNullable.appendChild(sColNullableState);

        // default_length
        Element sColDefaultLength = doc.createElement("iDefaultLength");
        columnElement.appendChild(sColDefaultLength);
        Element sColDefaultLengthBase = doc.createElement("base");
        if (baseCol.default_length != null)
            sColDefaultLengthBase.appendChild(doc.createTextNode(baseCol.default_length));
        sColDefaultLength.appendChild(sColDefaultLengthBase);
        Element sColDefaultLengthFile = doc.createElement("file");
        if (fileCol.default_length != null)
            sColDefaultLengthFile.appendChild(doc.createTextNode(fileCol.default_length));
        sColDefaultLength.appendChild(sColDefaultLengthFile);
        Element sColDefaultLengthState = doc.createElement("state");
        if ((baseCol.default_length == null && fileCol.default_length == null) || (baseCol.default_length.equalsIgnoreCase( fileCol.default_length ))) {
            sColDefaultLengthState.appendChild(doc.createTextNode("same"));
        }
        else {
            sColDefaultLengthState.appendChild(doc.createTextNode("diff"));
        }
        sColDefaultLength.appendChild(sColDefaultLengthState);

        return columnElement;
    }

}
