/*
                        QueryJ

    Copyright (C) 2002  Jose San Leandro Armendariz
                        jsanleandro@yahoo.es
                        chousz@yahoo.com

    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    version 2 of the License, or any later version.

    This library is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General Public License for more details.

    You should have received a copy of the GNU General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA

    Thanks to ACM S.L. for distributing this library under the GPL license.
    Contact info: jsanleandro@yahoo.es
    Postal Address: c/Playa de Lagoa, 1
                    Urb. Valdecabanas
                    Boadilla del monte
                    28660 Madrid
                    Spain

 ******************************************************************************
 *
 * Filename: $RCSfile$
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Is able to create xml DAO implementations for each
 *              table in the persistence model.
 *
 * Last modified by: $Author$ at $Date$
 *
 * File version: $Revision$
 *
 * Project version: $Name$
 *
 * $Id$
 *
 */
package org.acmsl.queryj.tools.templates.dao.xml;

/*
 * Importing some project-specific classes.
 */
import org.acmsl.queryj.tools.DatabaseMetaDataManager;
import org.acmsl.queryj.tools.MetaDataUtils;
import org.acmsl.queryj.tools.PackageUtils;
import org.acmsl.queryj.tools.templates.TableTemplate;

/*
 * Importing some ACM-SL classes.
 */
import org.acmsl.commons.utils.EnglishGrammarUtils;
import org.acmsl.commons.utils.StringUtils;
import org.acmsl.commons.utils.StringValidator;

/*
 * Importing some JDK classes.
 */
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/*
 * Importing Apache Commons Logging classes.
 */
import org.apache.commons.logging.LogFactory;

/**
 * Is able to create xml DAO implementations for each
 * table in the persistence model.
 * @author <a href="mailto:jsanleandro@yahoo.es"
           >Jose San Leandro</a>
 * @version $Revision$
 */
public abstract class XMLDAOTemplate
{
    /**
     * The default header.
     */
    public static final String DEFAULT_HEADER =
          "/*\n"
        + "                        QueryJ\n"
        + "\n"
        + "    Copyright (C) 2002  Jose San Leandro Armendariz\n"
        + "                        jsanleandro@yahoo.es\n"
        + "                        chousz@yahoo.com\n"
        + "\n"
        + "    This library is free software; you can redistribute it and/or\n"
        + "    modify it under the terms of the GNU General Public\n"
        + "    License as published by the Free Software Foundation; either\n"
        + "    version 2 of the License, or any later "
        + "version.\n"
        + "\n"
        + "    This library is distributed in the hope that it will be "
        + "useful,\n"
        + "    but WITHOUT ANY WARRANTY; without even the implied warranty "
        + "of\n"
        + "    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the "
        + "GNU\n"
        + "    General Public License for more details.\n"
        + "\n"
        + "    You should have received a copy of the GNU General Public\n"
        + "    License along with this library; if not, write to the Free "
        + "Software\n"
        + "    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  "
        + "02111-1307  USA\n"
        + "\n"
        + "    Thanks to ACM S.L. for distributing this library under the GPL "
        + "license.\n"
        + "    Contact info: jsanleandro@yahoo.es\n"
        + "    Postal Address: c/Playa de Lagoa, 1\n"
        + "                    Urb. Valdecabanas\n"
        + "                    Boadilla del monte\n"
        + "                    28660 Madrid\n"
        + "                    Spain\n"
        + "\n"
        + " *****************************************************************"
        + "*************\n"
        + " *\n"
        + " * Filename: $" + "RCSfile: $\n"
        + " *\n"
        + " * Author: QueryJ\n"
        + " *\n"
        + " * Description: XML-based DAO layer responsible of simulating a persistence\n"
        + " *              layer for {0} structures.\n"
        + " *\n"
        + " * Last modified by: $" + "Author: $ at $" + "Date: $\n"
        + " *\n"
        + " * File version: $" + "Revision: $\n"
        + " *\n"
        + " * Project version: $" + "Name: $\n"
        + " *\n"
        + " * $" + "Id: $\n"
        + " *\n"
        + " */\n";

    /**
     * The package declaration.
     */
    public static final String PACKAGE_DECLARATION = "package {0};\n\n"; // package

    /**
     * The project imports.
     */
    public static final String DEFAULT_PROJECT_IMPORTS =
          "/*\n"
        + " * Importing project-specific classes.\n"
        + " */\n"
         // XML DAO package
        + "import {0}.XML{2}ValueObjectFactory;\n"
         // JDBC DAO package
        + "import {1}.{2}ValueObject;\n"
         // ValueObject DAO package - table name
        + "import {3}.{2}ValueObjectFactory;\n"
         // ValueObject factory DAO package - table name
        + "import {4}.{2}DAO;\n"
         // DAO interface package - table name
        + "import {5}.DataAccessManager;\n"
         // data access manager package
        + "{6}";
         // foreign DAO imports

    /**
     * Foreign DAO imports.
     */
    public static final String DEFAULT_FOREIGN_DAO_IMPORTS =
        "import {0}.{1}DAO;\n";

    /**
     * The ACM-SL imports.
     */
    public static final String ACMSL_IMPORTS =
          "\n/*\n"
        + " * Importing some ACM-SL classes.\n"
        + " */\n"
        + "import org.acmsl.queryj.dao.TransactionToken;\n\n"
        + "\n"
        + "/*\n"
        + " * Importing ACM-SL Commons classes.\n"
        + " */\n"
        + "import org.acmsl.commons.patterns.dao.DataAccessException;\n"
        + "import org.acmsl.commons.utils.io.IOUtils;\n\n";

    /**
     * The JDK imports.
     */
    public static final String JDK_IMPORTS =
          "/*\n"
        + " * Importing some JDK classes.\n"
        + " */\n"
        + "import java.io.InputStream;\n"
        + "import java.io.OutputStream;\n"
        + "import java.io.PrintStream;\n"
        + "import java.util.ArrayList;\n"
        + "import java.math.BigDecimal;\n"
        + "import java.util.Calendar;\n"
        + "import java.util.Collection;\n"
        + "import java.util.Date;\n"
        + "import java.util.Hashtable;\n"
        + "import java.util.Iterator;\n"
        + "import java.util.Map;\n\n";

    /**
     * The extra imports.
     */
    public static final String EXTRA_IMPORTS =
          "/*\n"
        + " * Importing Undigester classes.\n"
        + " */\n"
        + "import org.acmsl.undigester.GetNameRule;\n"
        + "import org.acmsl.undigester.GetNextRule;\n"
        + "import org.acmsl.undigester.GetPropertyRule;\n"
        + "import org.acmsl.undigester.Undigester;\n\n"
        + "import org.acmsl.undigester.UndigesterException;\n\n"
        + "/*\n"
        + " * Importing Digester classes.\n"
        + " */\n"
        + "import org.apache.commons.digester.Digester;\n\n"
        + "/*\n"
        + " * Importing Jakarta Commons Logging classes\n"
        + " */\n"
        + "import org.apache.commons.logging.LogFactory;\n\n";
    
    /**
     * The default class Javadoc.
     */
    public static final String DEFAULT_JAVADOC =
          "/**\n"
        + " * XML-based DAO class responsible of simulating a persistence layer \n"
        + " * for {0} structures.\n"
        + " * @author <a href=\"http://maven.acm-sl.org/queryj\">QueryJ</a>\n"
        + " * @version $" + "Revision: $\n"
        + " */\n";

    /**
     * The class definition.
     */
    public static final String CLASS_DEFINITION =
          "public abstract class XML{0}DAO\n"
        + "    implements  {0}DAO\n";
        // table name

    /**
     * The class start.
     */
    public static final String DEFAULT_CLASS_START =
          "'{'\n"
        + "    /**\n"
        + "     * A lock to synchronize writes and reads.\n"
        + "     */\n"
        + "    protected static final Object LOCK = new Object();\n\n"
        + "    /**\n"
        + "     * The storage of {0} information.\n"
        + "     */\n"
        + "    private static Map m__m{1}Map;\n\n"
        + "    /**\n"
        + "     * The ordered pointers to {0} entities.\n"
        + "     */\n"
        + "    private static Collection m__c{1}Collection;\n\n"
        + "    /**\n"
        + "     * The input stream.\n"
        + "     */\n"
        + "    private InputStream m__isInput;\n\n"
        + "    /**\n"
        + "     * The output stream.\n"
        + "     */\n"
        + "    private OutputStream m__osOutput;\n\n";

    /**
     * The class constructor.
     */
    public static final String CLASS_CONSTRUCTOR =
          "    /**\n"
        + "     * Creates a XML{0}DAO with given input stream.\n"
        // table name
        + "     * @param input the input stream.\n"
        + "     * @param output (<b>optional</b>) where to save the changes to.\n"
        + "     * @precondition input != null\n"
        + "     */\n"
        + "    public XML{0}DAO(final InputStream input, final OutputStream output)\n"
        + "    '{'\n"
        + "        immutableSetInput(input);\n"
        + "        immutableSetOutput(output);\n"
        + "    '}'\n\n";

    /**
     * The class internal methods.
     */
    public static final String DEFAULT_CLASS_INTERNAL_METHODS =
          "    /**\n"
        + "     * Specifies the map with {0} information\n"
        + "     * @param map the new map.\n"
        + "     */\n"
        + "    protected static void set{0}Map(final Map map)\n"
        + "    '{'\n"
        + "        m__m{0}Map = map;\n"
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Retrieves the map with {0} information\n"
        + "     * return such map.\n"
        + "     */\n"
        + "    protected static Map get{0}Map()\n"
        + "    '{'\n"
        + "        return m__m{0}Map;\n"
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Specifies the {0} collection\n"
        + "     * @param collection the new collection.\n"
        + "     */\n"
        + "    protected static void set{0}Collection(final Collection collection)\n"
        + "    '{'\n"
        + "        m__c{0}Collection = collection;\n"
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Retrieves the {0} collection\n"
        + "     * return such collection.\n"
        + "     */\n"
        + "    protected static Collection get{0}Collection()\n"
        + "    '{'\n"
        + "        return m__c{0}Collection;\n"
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Builds a key using given information."
        + "{1}\n"
         // BUILD_KEY_PK_JAVADOC
        + "     * @return such key.\n"
        + "     */\n"
        + "    protected Object buildKey("
        + "{2})\n"
         // BUILD_KEY_PK_DECLARATION
        + "    '{'\n"
        + "        return \".:\" + {3} + \":.\";\n"
         // BUILD_KEY_PK_VALUES
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Specifies the input stream.\n"
        + "     * @param input the input stream.\n"
        + "     */\n"
        + "    private void immutableSetInput(final InputStream input)\n"
        + "    '{'\n"
        + "        m__isInput = input;\n"
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Specifies the input stream.\n"
        + "     * @param input the input stream.\n"
        + "     */\n"
        + "    protected void setInput(final InputStream input)\n"
        + "    '{'\n"
        + "        immutableSetInput(input);\n"
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Retrieves the input stream.\n"
        + "     * @return such stream.\n"
        + "     */\n"
        + "    protected InputStream getInput()\n"
        + "    '{'\n"
        + "        return m__isInput;\n"
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Specifies the output stream.\n"
        + "     * @param input the output stream.\n"
        + "     */\n"
        + "    private void immutableSetOutput(final OutputStream output)\n"
        + "    '{'\n"
        + "        m__osOutput = output;\n"
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Specifies the output stream.\n"
        + "     * @param output the output stream.\n"
        + "     */\n"
        + "    protected void setOutput(final OutputStream output)\n"
        + "    '{'\n"
        + "        immutableSetOutput(output);\n"
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Retrieves the output stream.\n"
        + "     * @return such stream.\n"
        + "     */\n"
        + "    protected OutputStream getOutput()\n"
        + "    '{'\n"
        + "        return m__osOutput;\n"
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Loads the information from the XML resource.\n"
        + "     * @return the {0} information.\n"
        + "     * @throws DataAccessException if the data can not be parsed.\n"
        + "     */\n"
        + "    protected Map load()\n"
        + "      throws  DataAccessException\n"
        + "    '{'\n"
        + "        return load(configureDigester(), getInput());\n"
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Loads the information from the XML resource.\n"
        + "     * @param digester the Digester instance.\n"
        + "     * @param stream the input stream.\n"
        + "     * @return the {0} information.\n"
        + "     * @throws DataAccessException if the data can not be parsed.\n"
        + "     * @precondition (stream != null)\n"
        + "     */\n"
        + "    protected synchronized Map load(\n"
        + "        final Digester digester,\n"
        + "        final InputStream input)\n"
        + "      throws  DataAccessException\n"
        + "    '{'\n"
        + "        Map result = null;\n\n"
        + "        if  (digester != null)\n"
        + "        '{'\n"
        + "            Collection collection = new ArrayList();\n\n"
        + "            digester.push(collection);\n\n"
        + "            try\n"
        + "            '{'\n"
        + "                if  (input != null)\n"
        + "                '{'\n"
        + "                    digester.parse(input);\n\n"
        + "                    result = new Hashtable();\n"
        + "                    process{0}(collection, result);\n"
        + "                    set{0}Collection(collection);\n"
        + "                    set{0}Map(result);\n"
        + "                '}'\n"
        + "            '}'\n"
        + "            catch (final Exception exception)\n"
        + "            '{'\n"
        + "                LogFactory.getLog(getClass()).error(\n"
        + "                    \"Cannot read XML {0} information.\",\n"
        + "                    exception);\n\n"
        + "                throw\n"
        + "                    new DataAccessException(\n"
        + "                        \"Cannot.read.xml.{0}.information\",\n"
        + "                        exception,\n"
        + "                        this);\n"
        + "            '}'\n"
        + "        '}'\n\n"
        + "        return result;\n"
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Creates and configures a digester instance.\n"
        + "     * @return a configured digester instance.\n"
        + "     */\n"
        + "    protected Digester configureDigester()\n"
        + "    '{'\n"
        + "        Digester result = new Digester();\n\n"
        + "        result.addFactoryCreate(\n"
        + "            \"{4}-list/{4}\",\n"
    // uncapitalized value object name, in singular
        + "            XML{0}ValueObjectFactory.getXMLInstance());\n"
    // value object package + name
        + "        result.addSetNext(\n"
        + "            \"{4}-list/{4}\",\n"
        + "            \"add\",\n"
        + "            \"{5}.{0}ValueObject\");\n\n"
        + "        return result;\n"
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Processes given {0} information.\n"
        + "     * @param collection the {0} collection.\n"
        + "     * @param map the {0} map.\n"
        + "     * @precondition collection != null\n"
        + "     * @precondition map != null\n"
        + "     */\n"
        + "    protected synchronized void process{0}(\n"
        + "        final Collection collection, final Map map)\n"
        + "    '{'\n"
        + "        Iterator t_Iterator = collection.iterator();\n\n"
        + "        if  (t_Iterator != null)\n"
        + "        '{'\n"
        + "            while  (t_Iterator.hasNext())\n"
        + "            '{'\n"
        + "                {0}ValueObject t_ValueObject =\n"
        + "                    ({0}ValueObject) t_Iterator.next();\n"
        + "                if  (t_ValueObject != null)\n"
        + "                '{'\n"
        + "                    map.put(buildKey({6}), t_ValueObject);\n"
         // PROCESS_PK_ATTRIBUTES
        + "                '}'\n"
        + "            '}'\n"
        + "        '}'\n"
        + "    '}'\n\n";

    /**
     * The build-key method's primary keys javadoc.
     */
    public static final String DEFAULT_BUILD_KEY_PK_JAVADOC =
        "\n     * @param {0} the {1}.";
         // java pk - pk

    /**
     * The build-key method's primary keys declaration.
     */
    public static final String DEFAULT_BUILD_KEY_PK_DECLARATION =
        "\n        final {0} {1}";
         // pk type - java pk

    /**
     * The build-key method's pk values.
     */
    public static final String DEFAULT_BUILD_KEY_PK_VALUES =
          "{0}";
         // value

    /**
     * The process method's pk attributes.
     */
    public static final String DEFAULT_PROCESS_PK_ATTRIBUTES =
          "t_ValueObject.get{0}()";
         // value

    /**
     * The find by primary key method.
     */
    public static final String DEFAULT_FIND_BY_PRIMARY_KEY_METHOD =
          "    /**\n"
        + "     * Loads {0} information from the simulated persistence\n"
         // table name
        + "     * layer filtering by its primary keys."
        + "{1}\n"
         // FIND_BY_PRIMARY_KEY_PK_JAVADOC
        + "     * @param transactionToken <i>not used in this implementation</i>.\n"
        + "     * @return the information extracted from the simulated\n"
        + "     * persistence layer.\n"
        + "     * @throws DataAccessException if the access to the information fails.\n"
        + "     */\n"
        + "    public {2}ValueObject findByPrimaryKey(\n"
         // java table name
        + "{3}"
         // FIND_BY_PRIMARY_KEY_PK_DECLARATION
        + "        final TransactionToken transactionToken)\n"
        + "      throws  DataAccessException\n"
        + "    '{'\n"
        + "        {2}ValueObject result = null;\n\n"
         // java table name
        + "        Map t_m{2}Map = null;\n\n"
        + "        synchronized (LOCK)\n"
        + "        '{'\n"
        + "            t_m{2}Map = get{2}Map();\n\n"
        + "            if  (t_m{2}Map == null)\n"
        + "            '{'\n"
        + "                t_m{2}Map = load();\n"
        + "            '}'\n\n"
        + "            if  (t_m{2}Map == null)\n"
        + "            '{'\n"
        + "                t_m{2}Map = new Hashtable();\n"
        + "                set{2}Map(t_m{2}Map);\n"
        + "            '}'\n"
        + "        '}'\n\n"
        + "        result =\n"
        + "            ({2}ValueObject)\n"
        + "                t_m{2}Map.get(\n"
        + "                    buildKey({4}));\n\n"
         // FIND_BY_PRIMARY_KEY_PK_FILTER_VALUES
        + "        return result;\n"
        + "    '}'\n\n";

    /**
     * The find-by-primary-key method's primary keys javadoc.
     */
    public static final String DEFAULT_FIND_BY_PRIMARY_KEY_PK_JAVADOC =
        "\n     * @param {0} the {1} value to filter.";
         // java pk - pk

    /**
     * The find-by-primary-key method's primary keys declaration.
     */
    public static final String DEFAULT_FIND_BY_PRIMARY_KEY_PK_DECLARATION =
        "        {0} {1},\n";
         // pk type - java pk

    /**
     * The find-by-primary-key method's pk filter values.
     */
    public static final String DEFAULT_FIND_BY_PRIMARY_KEY_PK_FILTER_VALUES =
          "{0}";
         // value

    /**
     * The build-value-object method.
     */
    public static final String BUILD_VALUE_OBJECT_METHOD =
          "    /**\n"
        + "     * Fills up a {0} with the information provided."
         // table name
        + "{2}"
         // (optional) pk javadoc
        + "{3}"
         // insert parameters javadoc
        + "\n     * @return the {1}ValueObject."
        + "\n     */\n"
        + "    protected {1}ValueObject build{1}({4})\n"
         // build value object  parameters declaration
        + "    '{'\n"
        + "        {1}ValueObject result = null;\n\n"
        + "        {1}ValueObjectFactory t_{1}Factory =\n"
        + "            {1}ValueObjectFactory.getInstance();\n\n"
        + "        if  (t_{1}Factory != null) \n"
        + "        '{'\n"
        + "            result =\n"
        + "                t_{1}Factory.create{1}(\n"
        + "                    {5});\n"
         // BUILD_VALUE_OBJECT_VALUE_RETRIEVAL
        + "        '}'\n\n"
        + "        return result;\n"
        + "    '}'\n\n";

    /**
     * The store method.
     */
    public static final String DEFAULT_INSERT_METHOD =
          "    /**\n"
        + "     * Persists given {0} information."
         // table name
        + "{2}"
         // (optional) pk javadoc
        + "{3}\n"
         // insert parameters javadoc
        + "     * @param transactionToken <i>not used in this implementation</i>.\n"
        + "     * @throws DataAccessException if the access to the information fails.\n"
        + "     */\n"
        + "    public void insert("
        + "{4}\n"
         // insert parameters declaration
        + "        final TransactionToken transactionToken)\n"
        + "      throws  DataAccessException\n"
        + "    '{'\n"
        + "        Map t_m{1}Map = null;\n\n"
        + "        synchronized (LOCK)\n"
        + "        '{'\n"
        + "            t_m{1}Map = get{1}Map();\n\n"
        + "            if  (t_m{1}Map == null)\n"
        + "            '{'\n"
        + "                t_m{1}Map = load();\n"
        + "            '}'\n\n"
        + "            if  (t_m{1}Map == null)\n"
        + "            '{'\n"
        + "                t_m{1}Map = new Hashtable();\n"
        + "                set{1}Map(t_m{1}Map);\n"
        + "            '}'\n"
        + "        '}'\n\n"
        + "        t_m{1}Map.put(\n"
        + "            buildKey({5}),\n"
         // FIND_BY_PRIMARY_KEY_PK_FILTER_VALUES
        + "            build{1}(\n"
        + "                {6}));\n"
         // BUILD_VALUE_OBJECT_VALUE_RETRIEVAL
        + "    '}'\n\n";

    /**
     * The insert parameters javadoc.
     */
    public static final String DEFAULT_INSERT_PARAMETERS_JAVADOC =
        "\n     * @param {0} the {1} information.";
    // field name - field Name

    /**
     * The insert parameters declaration.
     */
    public static final String DEFAULT_INSERT_PARAMETERS_DECLARATION =
        "\n        {0} {1}";
    // field type - field name

    /**
     * The update method.
     */
    public static final String DEFAULT_UPDATE_METHOD =
          "    /**\n"
        + "     * Simulates given {0} information is updated\n"
        + "     * in the persistence layer."
         // table name
        + "{1}"
         // (optional) pk javadoc
        + "{2}\n"
         // update parameters javadoc
        + "     * @param transactionToken <i>not used in this implementation</i>.\n"
        + "     * @throws DataAccessException if the access to the information fails.\n"
        + "     */\n"
        + "    public void update(\n"
        + "{3}"
         // pk declaration
        + "{4}"
         // update parameters declaration
        + "        final TransactionToken transactionToken)\n"
        + "      throws  DataAccessException\n"
        + "    '{'\n"
        + "        {0}ValueObject t_{0}ValueObject =\n"
        + "            findByPrimaryKey(\n"
        + "                {5},\n"
        + "                transactionToken);\n\n"
         // pk values
        + "        if  (t_{0}ValueObject != null)\n"
        + "        '{'\n"
        + "            t_{0}ValueObject =\n"
        + "                build{0}(\n"
        + "                    {6});\n"
        + "        '}'\n\n"
        + "        synchronized (LOCK)\n"
        + "        '{'\n"
        + "            Map t_m{0}Map = get{0}Map();\n\n"
        + "            if  (t_m{0}Map == null)\n"
        + "            '{'\n"
        + "                t_m{0}Map = load();\n"
        + "            '}'\n\n"
        + "            if  (t_m{0}Map == null)\n"
        + "            '{'\n"
        + "                t_m{0}Map = new Hashtable();\n"
        + "                set{0}Map(t_m{0}Map);\n"
        + "            '}'\n"
        + "            if  (t_m{0}Map != null)\n"
        + "            '{'\n"
        + "                t_m{0}Map.put(\n"
        + "                    buildKey({5}),\n"
        + "                    t_{0}ValueObject);\n"
        + "            '}'\n"
        + "        '}'\n"
        + "    '}'\n\n";

    /**
     * The update parameters javadoc.
     */
    public static final String DEFAULT_UPDATE_PARAMETERS_JAVADOC =
        "\n     * @param {0} the {1} information.";
    // field name - field Name

    /**
     * The update parameters declaration.
     */
    public static final String DEFAULT_UPDATE_PARAMETERS_DECLARATION =
        "        final {0} {1},\n";
    // field type - field name

    /**
     * The delete method.
     */
    public static final String DEFAULT_DELETE_METHOD =
          "    /**\n"
        + "     * Deletes {0} information from the persistence layer filtering\n"
         // table name
        + "     * by its primary keys."
        + "{2}\n"
         // DELETE_PK_JAVADOC
        + "     * @param transactionToken the transaction token.\n"
        + "     * @return <code>true</code> if the information has been deleted\n"
        + "     * successfully.\n"
        + "     * @throws DataAccessException if the access to the information fails.\n"
        + "     */\n"
        + "    {4} boolean delete{5}(\n"
         // java table name
        + "{3}"
         // DELETE_PK_DECLARATION
        + "        final TransactionToken transactionToken)\n"
        + "      throws  DataAccessException\n"
        + "    '{'\n"
        + "        boolean result = false;\n\n"
        + "        synchronized (LOCK)\n"
        + "        '{'\n"
        + "            Map t_m{1}Map = get{1}Map();\n\n"
        + "            if  (t_m{1}Map == null)\n"
        + "            '{'\n"
        + "                t_m{1}Map = load();\n"
        + "            '}'\n\n"
        + "            if  (t_m{1}Map == null)\n"
        + "            '{'\n"
        + "                t_m{1}Map = new Hashtable();\n"
        + "                set{1}Map(t_m{1}Map);\n"
        + "            '}'\n"
        + "            if  (t_m{1}Map != null)\n"
        + "            '{'\n"
        + "                t_m{1}Map.remove(\n"
        + "                    buildKey({6}));\n\n"
        + "                result = true;\n"
        + "            '}'\n"
        + "        '}'\n\n"
        + "        return result;\n"
        + "    '}'\n\n";

    /**
     * The delete method's primary keys javadoc.
     */
    public static final String DEFAULT_DELETE_PK_JAVADOC =
        "\n     * @param {0} the {1} value to filter.";
         // java pk - pk

    /**
     * The delete method's primary keys declaration.
     */
    public static final String DEFAULT_DELETE_PK_DECLARATION =
        "        final {0} {1},\n";
         // pk type - java pk

    /**
     * The delete with fk method.
     */
    public static final String DEFAULT_DELETE_WITH_FK_METHOD =
          "    /**\n"
        + "     * Deletes {0} information from the persistence layer filtering\n"
         // table name
        + "     * by its primary keys."
        + "{1}\n"
         // DELETE_PK_JAVADOC
        + "     * @param transactionToken <i>not used in this implementation</i>.\n"
        + "     * @return <code>true</code> if the information has been deleted\n"
        + "     * successfully.\n"
        + "     * @throws DataAccessException if the access to the information fails.\n"
        + "     */\n"
        + "    public boolean delete(\n"
        + "{2}"
         // DELETE_PK_DECLARATION
        + "        final TransactionToken  transactionToken)\n"
        + "      throws  DataAccessException\n"
        + "    '{'\n"
        + "        return\n"
        + "        // delete with fk, comparing for xml implementations.\n"
        + "        return result;\n"
        + "    '}'\n\n";

    /**
     * The delete with fk method's primary keys javadoc.
     */
    public static final String DEFAULT_DELETE_WITH_FK_PK_JAVADOC =
        "\n     * @param {0} the {1} value to filter.";
         // java pk - pk

    /**
     * The delete with fk method's primary keys declaration.
     */
    public static final String DEFAULT_DELETE_WITH_FK_PK_DECLARATION =
        "        final {0} {1},\n";
         // pk type - java pk

    /**
     * The delete with fk methods' FK DAO delete request.
     */
    public static final String DEFAULT_DELETE_WITH_FK_DAO_DELETE_REQUEST =
          "                if  (result)\n"
        + "                '{'\n"
        + "                    {0}DAO t_{0}DAO = t_DataAccessManager.get{0}DAO();\n\n"
        + "                    if  (t_{0}DAO != null)\n"
        + "                    '{'\n"
        + "                        result =\n"
        + "                            t_{0}DAO.delete(\n"
        + "                                {1}.get{2}(),\n"
        + "                                t_DeleteTransactionToken);\n"
        + "                    '}'\n"
        + "                    else\n"
        + "                    '{'\n"
        + "                        result = false;\n"
        + "                    '}'\n"
        + "                '}'\n"
        + "                else\n"
        + "                '{'\n"
        + "                    result = false;\n"
        + "                '}'\n";

    /**
     * The delete with fk methods' FK values.
     */
    public static final String DEFAULT_DELETE_WITH_FK_PK_VALUES =
        "\n                    {0},";

    /**
     * The persist method.
     */
    public static final String DEFAULT_PERSIST_METHOD =
          "    /**\n"
        + "     * Persists in-memory contents to the output stream\n"
        + "     * associated to this instance.\n"
        + "     */\n"
        + "    public void persist()\n"
        + "    '{'\n"
        + "        persist(\n"
        + "            get{0}Collection(), getOutput(), IOUtils.getInstance());\n"
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Persists in-memory contents to the output stream\n"
        + "     * associated to this instance.\n"
        + "     * @param collection the collection to serialize.\n"
        + "     * @param output where to write the contents to.\n"
        + "     * @param ioUtils the IOUtils instance.\n"
        + "     * @precondition ioUtils != null\n"
        + "     */\n"
        + "    protected void persist(\n"
        + "        final Collection collection,\n"
        + "        final OutputStream output,\n"
        + "        final IOUtils ioUtils)\n"
        + "    '{'\n"
        + "        if  (collection == null)\n"
        + "        '{'\n"
        + "            LogFactory.getLog(getClass()).warn(\n"
        + "                \"No {0} information to serialize.\");\n"
        + "        '}'\n"
        + "        else if  (output == null)\n"
        + "        '{'\n"
        + "            LogFactory.getLog(getClass()).warn(\n"
        + "                \"No output stream to serialize the information through.\");\n"
        + "        '}'\n"
        + "        else\n"
        + "        '{'\n"
        + "            Undigester t_Undigester = new Undigester(collection);\n"
        + "            configureUndigester(t_Undigester, collection);\n"
        + "            try\n"
        + "            '{'\n"
        + "                ioUtils.write(t_Undigester.unparse(), output);\n"
        + "            '}'\n"
        + "            catch  (final UndigesterException undigesterException)\n"
        + "            '{'\n"
        + "                LogFactory.getLog(getClass()).fatal(\n"
        + "                      \"Cannot serialize {0} information. This could be \"\n"
        + "                    + \"a bug in QueryJ-based generated code. \"\n"
        + "                    + \"Please provide us information about this issue\"\n"
        + "                    + \"though http://bugzilla.acm-sl.org\",\n"
        + "                    undigesterException);\n"
        + "            '}'\n"
        + "        '}'\n"
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Configures given Undigester instance.\n"
        + "     * @param undigester the Undigester instance.\n"
        + "     * @param collection the {0} collection, required to build\n"
        + "     * Undigester patterns.\n"
        + "     * @precondition undigester != null\n"
        + "     * @precondition collection != null\n"
        + "     */\n"
        + "    protected void configureUndigester(\n"
        + "        final Undigester undigester, final Collection collection)\n"
        + "    '{'\n"
        + "        undigester.addRule(\n"
        + "            new GetNameRule(\n"
        + "                collection.getClass().getName(),\n"
        + "                \"{1}-list\",\n"
        + "                null));\n\n"
        + "        GetNextRule t_ItemRule =\n"
        + "            new GetNextRule(\n"
        + "                collection.getClass().getName(),\n"
        + "                \"toArray\",\n"
        + "                new Object[]\n"
        + "                '{'\n"
        + "                    new {0}ValueObject[0]\n"
        + "                '}',\n"
        + "                (GetNextRule) null);\n"
        + "        undigester.addRule(t_ItemRule);\n\n"
        + "        undigester.addRule(\n"
        + "            new GetNameRule(\n"
        + "                  collection.getClass().getName()\n"
        + "                + \"/\" + {0}ValueObject.class.getName(),\n"
        + "                \"{1}\",\n"
        + "                t_ItemRule));\n\n"
        + "{2}"
        + "    '}'\n";


    /**
     * The property rules.
     */
    public static final String DEFAULT_UNDIGESTER_PROPERTY_RULES =
          "        undigester.addRule(\n"
        + "            new GetPropertyRule(\n"
        + "                  collection.getClass().getName()\n"
        + "                + \"/\" + {0}ValueObject.class.getName(),\n"
        + "                \"{1}\",\n"
        + "                t_ItemRule));\n\n";

    /**
     * The default class end.
     */
    public static final String DEFAULT_CLASS_END = "}\n";

    /**
     * The table template.
     */
    private TableTemplate m__TableTemplate;

    /**
     * The database metadata manager.
     */
    private DatabaseMetaDataManager m__MetaDataManager;

    /**
     * The header.
     */
    private String m__strHeader;

    /**
     * The package declaration.
     */
    private String m__strPackageDeclaration;

    /**
     * The package name.
     */
    private String m__strPackageName;

    /**
     * The base package name.
     */
    private String m__strBasePackageName;

    /**
     * The repository name.
     */
    private String m__strRepositoryName;

    /**
     * The project imports.
     */
    private String m__strProjectImports;

    /**
     * The foreign DAO imports.
     */
    private String m__strForeignDAOImports;

    /**
     * The ACM-SL import statements.
     */
    private String m__strAcmslImports;

    /**
     * The JDK import statements.
     */
    private String m__strJdkImports;

    /**
     * The extra import statements.
     */
    private String m__strExtraImports;

    /**
     * The class Javadoc.
     */
    private String m__strJavadoc;

    /**
     * The class definition.
     */
    private String m__strClassDefinition;

    /**
     * The class start.
     */
    private String m__strClassStart;

    /**
     * The class constructor.
     */
    private String m__strClassConstructor;

    /**
     * The class' internal methods.
     */
    private String m__strClassInternalMethods;

    /**
     * The build-key pk javadoc.
     */
    private String m__strBuildKeyPkJavadoc;

    /**
     * The build-key pk declaration.
     */
    private String m__strBuildKeyPkDeclaration;

    /**
     * The build-key pk values.
     */
    private String m__strBuildKeyPkValues;

    /**
     * The process method's pk attributes.
     */
    private String m__strProcessPkAttributes;

    /**
     * The find-by-primary-key method.
     */
    private String m__strFindByPrimaryKeyMethod;

    /**
     * The find-by-primary-key pk javadoc.
     */
    private String m__strFindByPrimaryKeyPkJavadoc;

    /**
     * The find-by-primary-key pk declaration.
     */
    private String m__strFindByPrimaryKeyPkDeclaration;

    /**
     * The find-by-primary-key pk filter values.
     */
    private String m__strFindByPrimaryKeyPkFilterValues;

    /**
     * The build-value-object method.
     */
    private String m__strBuildValueObjectMethod;

    /**
     * The insert method.
     */
    private String m__strInsertMethod;

    /**
     * The insert parameters Javadoc.
     */
    private String m__strInsertParametersJavadoc;

    /**
     * The insert parameters declaration.
     */
    private String m__strInsertParametersDeclaration;

    /**
     * The update method.
     */
    private String m__strUpdateMethod;

    /**
     * The update parameters Javadoc.
     */
    private String m__strUpdateParametersJavadoc;

    /**
     * The update parameters declaration.
     */
    private String m__strUpdateParametersDeclaration;

    /**
     * The delete method.
     */
    private String m__strDeleteMethod;

    /**
     * The delete PK javadoc.
     */
    private String m__strDeletePkJavadoc;

    /**
     * The delete PK declaration.
     */
    private String m__strDeletePkDeclaration;

    /**
     * The delete PK filter declaration.
     */
    private String m__strDeleteFilterDeclaration;

    /**
     * The delete with FK method.
     */
    private String m__strDeleteWithFkMethod;

    /**
     * The delete with FK PK javadoc.
     */
    private String m__strDeleteWithFkPkJavadoc;

    /**
     * The delete with FK PK declaration.
     */
    private String m__strDeleteWithFkPkDeclaration;

    /**
     * The delete with FK PK DAO delete request..
     */
    private String m__strDeleteWithFkDAODeleteRequest;

    /**
     * The delete with FK DAO FK values.
     */
    private String m__strDeleteWithFkPkValues;

    /**
     * The persist method.
     */
    private String m__strPersistMethod;

    /**
     * The Undigester property rules.
     */
    private String m__strUndigesterPropertyRules;

    /**
     * The class end.
     */
    private String m__strClassEnd;

    /**
     * Builds a XMLDAOTemplate using given information.
     * @param tableTemplate the table template.
     * @param metaDataManager the database metadata manager.
     * @param header the header.
     * @param packageDeclaration the package declaration.
     * @param packageName the package name.
     * @param basePackageName the base package name.
     * @param repositoryName the repository name.
     * @param projectImports the project imports.
     * @param foreignDAOImports the foreign DAO imports.
     * @param acmslImports the ACM-SL imports.
     * @param jdkImports the JDK imports.
     * @param extraImports the extra imports.
     * @param javadoc the class Javadoc.
     * @param classDefinition the class definition.
     * @param classStart the class start.
     * @param classConstructor the class constructor.
     * @param classInternalMethods the class' internal methods.
     * @param buildKeyPkJavadoc the <i>buildKey</i> key pk javadoc.
     * @param buildKeyPkDeclaration the <i>buildKey</i> pk declaration.
     * @param buildKeyPkValues the <i>buildKey</i>  values.
     * @param processPkAttributes the <i>process</i> pk attributes.
     * @param findByPrimaryKeyMethod the find by primary key method.
     * @param findByPrimaryKeyPkJavadoc the find by primary key pk javadoc.
     * @param findByPrimaryKeyPkDeclaration the find by primary key pk
     *        declaration.
     * @param findByPrimaryKeyPkFilterValues the find by primary key pk
     *        filter values.
     * @param buildValueObjectMethod the build value object method.
     * @param insertMethod the insert method.
     * @param insertParametersJavadoc the javadoc of the insert method's parameters.
     * @param insertParametersDeclaration the declaration of the insert method's parameters.
     * @param updateMethod the update method.
     * @param updateParametersJavadoc the javadoc of the update method's parameters.
     * @param updateParametersDeclaration the declaration of the update method's parameters.
     * @param deleteMethod the delete method.
     * @param deletePkJavadoc the delete PK javadoc.
     * @param deletePkDeclaration the delete PK declaration.
     * @param deleteFilterDeclaration the delete filter declaration.
     * @param deleteWithFkMethod the delete method.
     * @param deleteWithFkPkJavadoc the delete with FK PK javadoc.
     * @param deleteWithFkPkDeclaration the delete with FK PK declaration.
     * @param deleteWithFkDAODeleteRequest the delete with FK DAO delete request.
     * @param deleteWithFkPkValues the delete with FK PK values.
     * @param persistMethod the persist method.
     * @param undigesterPropertyRules the Undigester property rules.
     * @param classEnd the class end.
     */
    public XMLDAOTemplate(
        final TableTemplate           tableTemplate,
        final DatabaseMetaDataManager metaDataManager,
        final String                  header,
        final String                  packageDeclaration,
        final String                  packageName,
        final String                  basePackageName,
        final String                  repositoryName,
        final String                  projectImports,
        final String                  foreignDAOImports,
        final String                  acmslImports,
        final String                  jdkImports,
        final String                  extraImports,
        final String                  javadoc,
        final String                  classDefinition,
        final String                  classStart,
        final String                  classConstructor,
        final String                  classInternalMethods,
        final String                  buildKeyPkJavadoc,
        final String                  buildKeyPkDeclaration,
        final String                  buildKeyPkValues,
        final String                  processPkAttributes,
        final String                  findByPrimaryKeyMethod,
        final String                  findByPrimaryKeyPkJavadoc,
        final String                  findByPrimaryKeyPkDeclaration,
        final String                  findByPrimaryKeyPkFilterValues,
        final String                  buildValueObjectMethod,
        final String                  insertMethod,
        final String                  insertParametersJavadoc,
        final String                  insertParametersDeclaration,
        final String                  updateMethod,
        final String                  updateParametersJavadoc,
        final String                  updateParametersDeclaration,
        final String                  deleteMethod,
        final String                  deletePkJavadoc,
        final String                  deletePkDeclaration,
        final String                  deleteWithFkMethod,
        final String                  deleteWithFkPkJavadoc,
        final String                  deleteWithFkPkDeclaration,
        final String                  deleteWithFkDAODeleteRequest,
        final String                  deleteWithFkPkValues,
        final String                  persistMethod,
        final String                  undigesterPropertyRules,
        final String                  classEnd)
    {
        immutableSetTableTemplate(
            tableTemplate);

        immutableSetMetaDataManager(
            metaDataManager);

        immutableSetHeader(
            header);

        immutableSetPackageDeclaration(
            packageDeclaration);

        immutableSetPackageName(
            packageName);

        immutableSetBasePackageName(
            basePackageName);

        immutableSetRepositoryName(
            repositoryName);

        immutableSetProjectImports(
            projectImports);

        immutableSetForeignDAOImports(
            foreignDAOImports);

        immutableSetAcmslImports(
            acmslImports);

        immutableSetJdkImports(
            jdkImports);

        immutableSetExtraImports(
            extraImports);

        immutableSetJavadoc(
            javadoc);

        immutableSetClassDefinition(
            classDefinition);

        immutableSetClassStart(
            classStart);

        immutableSetClassConstructor(
            classConstructor);

        immutableSetClassInternalMethods(
            classInternalMethods);

        immutableSetBuildKeyPkJavadoc(
            buildKeyPkJavadoc);

        immutableSetBuildKeyPkDeclaration(
            buildKeyPkDeclaration);

        immutableSetBuildKeyPkValues(
            buildKeyPkValues);

        immutableSetProcessPkAttributes(
            processPkAttributes);

        immutableSetFindByPrimaryKeyMethod(
            findByPrimaryKeyMethod);

        immutableSetFindByPrimaryKeyPkJavadoc(
            findByPrimaryKeyPkJavadoc);

        immutableSetFindByPrimaryKeyPkDeclaration(
            findByPrimaryKeyPkDeclaration);

        immutableSetFindByPrimaryKeyPkFilterValues(
            findByPrimaryKeyPkFilterValues);

        immutableSetBuildValueObjectMethod(
            buildValueObjectMethod);

        immutableSetInsertMethod(
            insertMethod);

        immutableSetInsertParametersJavadoc(
            insertParametersJavadoc);

        immutableSetInsertParametersDeclaration(
            insertParametersDeclaration);

        immutableSetUpdateMethod(
            updateMethod);

        immutableSetUpdateParametersJavadoc(
            updateParametersJavadoc);

        immutableSetUpdateParametersDeclaration(
            updateParametersDeclaration);

        immutableSetDeleteMethod(
            deleteMethod);

        immutableSetDeletePkJavadoc(
            deletePkJavadoc);

        immutableSetDeletePkDeclaration(
            deletePkDeclaration);

        immutableSetDeleteWithFkMethod(
            deleteWithFkMethod);

        immutableSetDeleteWithFkPkJavadoc(
            deleteWithFkPkJavadoc);

        immutableSetDeleteWithFkPkDeclaration(
            deleteWithFkPkDeclaration);

        immutableSetDeleteWithFkDAODeleteRequest(
            deleteWithFkDAODeleteRequest);

        immutableSetDeleteWithFkPkValues(
            deleteWithFkPkValues);

        immutableSetPersistMethod(
            persistMethod);

        immutableSetUndigesterPropertyRules(
            undigesterPropertyRules);

        immutableSetClassEnd(
            classEnd);
    }

    /**
     * Builds a XMLDAOTemplate using given information.
     * @param tableTemplate the table template.
     * @param metaDataManager the database metadata manager.
     * @param packageName the package name.
     * @param basePackageName the base package name.
     * @param repositoryName the repository name.
     */
    public XMLDAOTemplate(
        final TableTemplate           tableTemplate,
        final DatabaseMetaDataManager metaDataManager,
        final String                  packageName,
        final String                  basePackageName,
        final String                  repositoryName)
    {
        this(
            tableTemplate,
            metaDataManager,
            DEFAULT_HEADER,
            PACKAGE_DECLARATION,
            packageName,
            basePackageName,
            repositoryName,
            DEFAULT_PROJECT_IMPORTS,
            DEFAULT_FOREIGN_DAO_IMPORTS,
            ACMSL_IMPORTS,
            JDK_IMPORTS,
            EXTRA_IMPORTS,
            DEFAULT_JAVADOC,
            CLASS_DEFINITION,
            DEFAULT_CLASS_START,
            CLASS_CONSTRUCTOR,
            DEFAULT_CLASS_INTERNAL_METHODS,
            DEFAULT_BUILD_KEY_PK_JAVADOC,
            DEFAULT_BUILD_KEY_PK_DECLARATION,
            DEFAULT_BUILD_KEY_PK_VALUES,
            DEFAULT_PROCESS_PK_ATTRIBUTES,
            DEFAULT_FIND_BY_PRIMARY_KEY_METHOD,
            DEFAULT_FIND_BY_PRIMARY_KEY_PK_JAVADOC,
            DEFAULT_FIND_BY_PRIMARY_KEY_PK_DECLARATION,
            DEFAULT_FIND_BY_PRIMARY_KEY_PK_FILTER_VALUES,
            BUILD_VALUE_OBJECT_METHOD,
            DEFAULT_INSERT_METHOD,
            DEFAULT_INSERT_PARAMETERS_JAVADOC,
            DEFAULT_INSERT_PARAMETERS_DECLARATION,
            DEFAULT_UPDATE_METHOD,
            DEFAULT_UPDATE_PARAMETERS_JAVADOC,
            DEFAULT_UPDATE_PARAMETERS_DECLARATION,
            DEFAULT_DELETE_METHOD,
            DEFAULT_DELETE_PK_JAVADOC,
            DEFAULT_DELETE_PK_DECLARATION,
            DEFAULT_DELETE_WITH_FK_METHOD,
            DEFAULT_DELETE_WITH_FK_PK_JAVADOC,
            DEFAULT_DELETE_WITH_FK_PK_DECLARATION,
            DEFAULT_DELETE_WITH_FK_DAO_DELETE_REQUEST,
            DEFAULT_DELETE_WITH_FK_PK_VALUES,
            DEFAULT_PERSIST_METHOD,
            DEFAULT_UNDIGESTER_PROPERTY_RULES,
            DEFAULT_CLASS_END);
    }

    /**
     * Specifies the table template.
     * @param tableTemplate the table template.
     */
    private void immutableSetTableTemplate(final TableTemplate tableTemplate)
    {
        m__TableTemplate = tableTemplate;
    }

    /**
     * Specifies the table template.
     * @param tableTemplate the table template.
     */
    protected void setTableTemplate(final TableTemplate tableTemplate)
    {
        immutableSetTableTemplate(tableTemplate);
    }

    /**
     * Retrieves the table template.
     * @return such template.
     */
    public TableTemplate getTableTemplate()
    {
        return m__TableTemplate;
    }


    /**
     * Specifies the metadata manager.
     * @param metaDataManager the metadata manager.
     */
    private void immutableSetMetaDataManager(
        final DatabaseMetaDataManager metaDataManager)
    {
        m__MetaDataManager = metaDataManager;
    }

    /**
     * Specifies the metadata manager.
     * @param metaDataManager the metadata manager.
     */
    protected void setMetaDataManager(
        final DatabaseMetaDataManager metaDataManager)
    {
        immutableSetMetaDataManager(metaDataManager);
    }

    /**
     * Retrieves the metadata manager.
     * @return such manager.
     */
    public DatabaseMetaDataManager getMetaDataManager()
    {
        return m__MetaDataManager;
    }

    /**
     * Specifies the header.
     * @param header the new header.
     */
    private void immutableSetHeader(final String header)
    {
        m__strHeader = header;
    }

    /**
     * Specifies the header.
     * @param header the new header.
     */
    protected void setHeader(final String header)
    {
        immutableSetHeader(header);
    }

    /**
     * Retrieves the header.
     * @return such information.
     */
    public String getHeader() 
    {
        return m__strHeader;
    }

    /**
     * Specifies the package declaration.
     * @param packageDeclaration the new package declaration.
     */
    private void immutableSetPackageDeclaration(final String packageDeclaration)
    {
        m__strPackageDeclaration = packageDeclaration;
    }

    /**
     * Specifies the package declaration.
     * @param packageDeclaration the new package declaration.
     */
    protected void setPackageDeclaration(final String packageDeclaration)
    {
        immutableSetPackageDeclaration(packageDeclaration);
    }

    /**
     * Retrieves the package declaration.
     * @return such information.
     */
    public String getPackageDeclaration() 
    {
        return m__strPackageDeclaration;
    }

    /**
     * Specifies the package name.
     * @param packageName the new package name.
     */
    private void immutableSetPackageName(final String packageName)
    {
        m__strPackageName = packageName;
    }

    /**
     * Specifies the package name.
     * @param packageName the new package name.
     */
    protected void setPackageName(final String packageName)
    {
        immutableSetPackageName(packageName);
    }

    /**
     * Retrieves the package name.
     * @return such information.
     */
    public String getPackageName() 
    {
        return m__strPackageName;
    }

    /**
     * Specifies the base package name.
     * @param basePackageName the new base package name.
     */
    private void immutableSetBasePackageName(final String basePackageName)
    {
        m__strBasePackageName = basePackageName;
    }

    /**
     * Specifies the base package name.
     * @param basePackageName the new base package name.
     */
    protected void setBasePackageName(final String basePackageName)
    {
        immutableSetBasePackageName(basePackageName);
    }

    /**
     * Retrieves the base package name.
     * @return such information.
     */
    public String getBasePackageName() 
    {
        return m__strBasePackageName;
    }

    /**
     * Specifies the repository name.
     * @param repositoryName the new repository name.
     */
    private void immutableSetRepositoryName(final String repositoryName)
    {
        m__strRepositoryName = repositoryName;
    }

    /**
     * Specifies the repository name.
     * @param repositoryName the new repository name.
     */
    protected void setRepositoryName(final String repositoryName)
    {
        immutableSetRepositoryName(repositoryName);
    }

    /**
     * Retrieves the repository name.
     * @return such information.
     */
    public String getRepositoryName()
    {
        return m__strRepositoryName;
    }

    /**
     * Specifies the project imports.
     * @param projectImports the new project imports.
     */
    private void immutableSetProjectImports(final String projectImports)
    {
        m__strProjectImports = projectImports;
    }

    /**
     * Specifies the project imports.
     * @param projectImports the new project imports.
     */
    protected void setProjectImports(final String projectImports)
    {
        immutableSetProjectImports(projectImports);
    }

    /**
     * Retrieves the project imports.
     * @return such information.
     */
    public String getProjectImports() 
    {
        return m__strProjectImports;
    }

    /**
     * Specifies the foreign DAO imports.
     * @param foreignDAOImports the new foreign DAO imports.
     */
    private void immutableSetForeignDAOImports(final String foreignDAOImports)
    {
        m__strForeignDAOImports = foreignDAOImports;
    }

    /**
     * Specifies the foreign DAO imports.
     * @param foreignDAOImports the new foreign DAO imports.
     */
    protected void setForeignDAOImports(final String foreignDAOImports)
    {
        immutableSetForeignDAOImports(foreignDAOImports);
    }

    /**
     * Retrieves the foreign DAO imports.
     * @return such information.
     */
    public String getForeignDAOImports() 
    {
        return m__strForeignDAOImports;
    }

    /**
     * Specifies the ACM-SL imports.
     * @param acmslImports the new ACM-SL imports.
     */
    private void immutableSetAcmslImports(final String acmslImports)
    {
        m__strAcmslImports = acmslImports;
    }

    /**
     * Specifies the ACM-SL imports.
     * @param acmslImports the new ACM-SL imports.
     */
    protected void setAcmslImports(final String acmslImports)
    {
        immutableSetAcmslImports(acmslImports);
    }

    /**
     * Retrieves the ACM-SL imports.
     * @return such information.
     */
    public String getAcmslImports() 
    {
        return m__strAcmslImports;
    }

    /**
     * Specifies the JDK imports.
     * @param jdkImports the new JDK imports.
     */
    private void immutableSetJdkImports(final String jdkImports)
    {
        m__strJdkImports = jdkImports;
    }

    /**
     * Specifies the JDK imports.
     * @param jdkImports the new JDK imports.
     */
    protected void setJdkImports(final String jdkImports)
    {
        immutableSetJdkImports(jdkImports);
    }

    /**
     * Retrieves the JDK imports.
     * @return such information.
     */
    public String getJdkImports() 
    {
        return m__strJdkImports;
    }

    /**
     * Specifies the extra imports.
     * @param extraImports the new extra imports.
     */
    private void immutableSetExtraImports(final String extraImports)
    {
        m__strExtraImports = extraImports;
    }

    /**
     * Specifies the extra imports.
     * @param extraImports the new extra imports.
     */
    protected void setExtraImports(final String extraImports)
    {
        immutableSetExtraImports(extraImports);
    }

    /**
     * Retrieves the extra imports.
     * @return such information.
     */
    public String getExtraImports() 
    {
        return m__strExtraImports;
    }

    /**
     * Specifies the javadoc.
     * @param javadoc the new javadoc.
     */
    private void immutableSetJavadoc(final String javadoc)
    {
        m__strJavadoc = javadoc;
    }

    /**
     * Specifies the javadoc.
     * @param javadoc the new javadoc.
     */
    protected void setJavadoc(final String javadoc)
    {
        immutableSetJavadoc(javadoc);
    }

    /**
     * Retrieves the javadoc.
     * @return such information.
     */
    public String getJavadoc() 
    {
        return m__strJavadoc;
    }

    /**
     * Specifies the class definition.
     * @param classDefinition the new class definition.
     */
    private void immutableSetClassDefinition(final String classDefinition)
    {
        m__strClassDefinition = classDefinition;
    }

    /**
     * Specifies the class definition.
     * @param classDefinition the new class definition.
     */
    protected void setClassDefinition(final String classDefinition)
    {
        immutableSetClassDefinition(classDefinition);
    }

    /**
     * Retrieves the class definition.
     * @return such information.
     */
    public String getClassDefinition() 
    {
        return m__strClassDefinition;
    }

    /**
     * Specifies the class start.
     * @param classStart the new class start.
     */
    private void immutableSetClassStart(final String classStart)
    {
        m__strClassStart = classStart;
    }

    /**
     * Specifies the class start.
     * @param classStart the new class start.
     */
    protected void setClassStart(final String classStart)
    {
        immutableSetClassStart(classStart);
    }

    /**
     * Retrieves the class start.
     * @return such information.
     */
    public String getClassStart() 
    {
        return m__strClassStart;
    }

    /**
     * Specifies the class constructor
     * @param constructor such source code.
     */
    private void immutableSetClassConstructor(final String constructor)
    {
        m__strClassConstructor = constructor;
    }

    /**
     * Specifies the class constructor
     * @param constructor such source code.
     */
    protected void setClassConstructor(final String constructor)
    {
        immutableSetClassConstructor(constructor);
    }

    /**
     * Retrieves the class constructor.
     * @return such source code.
     */
    public String getClassConstructor()
    {
        return m__strClassConstructor;
    }

    /**
     * Specifies the class' internal methods.
     * @param internalMethods such source code.
     */
    private void immutableSetClassInternalMethods(final String internalMethods)
    {
        m__strClassInternalMethods = internalMethods;
    }

    /**
     * Specifies the class' internal methods.
     * @param internalMethods such source code.
     */
    protected void setClassInternalMethods(final String internalMethods)
    {
        immutableSetClassInternalMethods(internalMethods);
    }

    /**
     * Retrieves the class' internal methods.
     * @return such source code.
     */
    public String getClassInternalMethods()
    {
        return m__strClassInternalMethods;
    }

    /**
     * Specifies the build-key pk Javadoc.
     * @param buildKeyPkJavadoc such Javadoc.
     */
    private void immutableSetBuildKeyPkJavadoc(
        String buildKeyPkJavadoc)
    {
        m__strBuildKeyPkJavadoc = buildKeyPkJavadoc;
    }

    /**
     * Specifies the build-key pk Javadoc.
     * @param buildKeyPkJavadoc such Javadoc.
     */
    protected void setBuildKeyPkJavadoc(
        String buildKeyPkJavadoc)
    {
        immutableSetBuildKeyPkJavadoc(
            buildKeyPkJavadoc);
    }

    /**
     * Retrieves the build-key pk Javadoc.
     * @return such Javadoc.
     */
    public String getBuildKeyPkJavadoc()
    {
        return m__strBuildKeyPkJavadoc;
    }
    
    /**
     * Specifies the build-key pk declaration.
     * @param buildKeyPkDeclaration such declaration.
     */
    private void immutableSetBuildKeyPkDeclaration(
        String buildKeyPkDeclaration)
    {
        m__strBuildKeyPkDeclaration =
            buildKeyPkDeclaration;
    }

    /**
     * Specifies the build-key pk declaration.
     * @param buildKeyPkdeclaration such declaration.
     */
    protected void setBuildKeyPkdeclaration(
        String buildKeyPkDeclaration)
    {
        immutableSetBuildKeyPkDeclaration(
            buildKeyPkDeclaration);
    }

    /**
     * Retrieves the build-key pk declaration.
     * @return such declaration.
     */
    public String getBuildKeyPkDeclaration()
    {
        return m__strBuildKeyPkDeclaration;
    }
    
    /**
     * Specifies the build-key pk values.
     * @param buildKeyPkValues such values.
     */
    private void immutableSetBuildKeyPkValues(
        String buildKeyPkValues)
    {
        m__strBuildKeyPkValues = buildKeyPkValues;
    }

    /**
     * Specifies the build-key pk values.
     * @param buildKeyPkValues such values.
     */
    protected void setBuildKeyPkValues(
        String buildKeyPkValues)
    {
        immutableSetBuildKeyPkValues(
            buildKeyPkValues);
    }

    /**
     * Retrieves the build-key pk values.
     * @return such values.
     */
    public String getBuildKeyPkValues()
    {
        return m__strBuildKeyPkValues;
    }

    /**
     * Specifies the process pk attributes.
     * @param processPkAttributes such attributes.
     */
    private void immutableSetProcessPkAttributes(
        String processPkAttributes)
    {
        m__strProcessPkAttributes = processPkAttributes;
    }

    /**
     * Specifies the process pk attributes.
     * @param processPkAttributes such attributes.
     */
    protected void setProcessPkAttributes(
        String processPkAttributes)
    {
        immutableSetProcessPkAttributes(
            processPkAttributes);
    }

    /**
     * Retrieves the process pk attributes.
     * @return such attributes.
     */
    public String getProcessPkAttributes()
    {
        return m__strProcessPkAttributes;
    }

    /**
     * Specifies the find-by-primary-key method.
     * @param findByPrimaryKeyMethod such method.
     */
    private void immutableSetFindByPrimaryKeyMethod(
        String findByPrimaryKeyMethod)
    {
        m__strFindByPrimaryKeyMethod = findByPrimaryKeyMethod;
    }

    /**
     * Specifies the find-by-primary-key method.
     * @param findByPrimaryKeyMethod such method.
     */
    protected void setFindByPrimaryKeyMethod(
        String findByPrimaryKeyMethod)
    {
        immutableSetFindByPrimaryKeyMethod(
            findByPrimaryKeyMethod);
    }

    /**
     * Retrieves the find-by-primary-key method.
     * @return such method.
     */
    public String getFindByPrimaryKeyMethod()
    {
        return m__strFindByPrimaryKeyMethod;
    }

    /**
     * Specifies the find-by-primary-key pk Javadoc.
     * @param findByPrimaryKeyPkJavadoc such Javadoc.
     */
    private void immutableSetFindByPrimaryKeyPkJavadoc(
        String findByPrimaryKeyPkJavadoc)
    {
        m__strFindByPrimaryKeyPkJavadoc = findByPrimaryKeyPkJavadoc;
    }

    /**
     * Specifies the find-by-primary-key pk Javadoc.
     * @param findByPrimaryKeyPkJavadoc such Javadoc.
     */
    protected void setFindByPrimaryKeyPkJavadoc(
        String findByPrimaryKeyPkJavadoc)
    {
        immutableSetFindByPrimaryKeyPkJavadoc(
            findByPrimaryKeyPkJavadoc);
    }

    /**
     * Retrieves the find-by-primary-key pk Javadoc.
     * @return such Javadoc.
     */
    public String getFindByPrimaryKeyPkJavadoc()
    {
        return m__strFindByPrimaryKeyPkJavadoc;
    }
    
    /**
     * Specifies the find-by-primary-key pk declaration.
     * @param findByPrimaryKeyPkDeclaration such declaration.
     */
    private void immutableSetFindByPrimaryKeyPkDeclaration(
        String findByPrimaryKeyPkDeclaration)
    {
        m__strFindByPrimaryKeyPkDeclaration =
            findByPrimaryKeyPkDeclaration;
    }

    /**
     * Specifies the find-by-primary-key pk declaration.
     * @param findByPrimaryKeyPkdeclaration such declaration.
     */
    protected void setFindByPrimaryKeyPkdeclaration(
        String findByPrimaryKeyPkDeclaration)
    {
        immutableSetFindByPrimaryKeyPkDeclaration(
            findByPrimaryKeyPkDeclaration);
    }

    /**
     * Retrieves the find-by-primary-key pk declaration.
     * @return such declaration.
     */
    public String getFindByPrimaryKeyPkDeclaration()
    {
        return m__strFindByPrimaryKeyPkDeclaration;
    }

    
    /**
     * Specifies the find-by-primary-key pk filter values.
     * @param findByPrimaryKeyPkFilterValues such filter values.
     */
    private void immutableSetFindByPrimaryKeyPkFilterValues(
        String findByPrimaryKeyPkFilterValues)
    {
        m__strFindByPrimaryKeyPkFilterValues =
            findByPrimaryKeyPkFilterValues;
    }

    /**
     * Specifies the find-by-primary-key pk filter values.
     * @param findByPrimaryKeyPkfilterValues such filter values.
     */
    protected void setFindByPrimaryKeyPkfilterValues(
        String findByPrimaryKeyPkFilterValues)
    {
        immutableSetFindByPrimaryKeyPkFilterValues(
            findByPrimaryKeyPkFilterValues);
    }

    /**
     * Retrieves the find-by-primary-key pk filter values.
     * @return such filter values.
     */
    public String getFindByPrimaryKeyPkFilterValues()
    {
        return m__strFindByPrimaryKeyPkFilterValues;
    }

    /**
     * Specifies the build-value-object method.
     * @param buildValueObjectMethod such method.
     */
    private void immutableSetBuildValueObjectMethod(
        String buildValueObjectMethod)
    {
        m__strBuildValueObjectMethod = buildValueObjectMethod;
    }

    /**
     * Specifies the build-value-object method.
     * @param buildValueObjectMethod such method.
     */
    protected void setBuildValueObjectMethod(
        String buildValueObjectMethod)
    {
        immutableSetBuildValueObjectMethod(
            buildValueObjectMethod);
    }

    /**
     * Retrieves the build-value-object method.
     * @return such method.
     */
    public String getBuildValueObjectMethod()
    {
        return m__strBuildValueObjectMethod;
    }

    /**
     * Specifies the insert method.
     * @param insertMethod such method.
     */
    private void immutableSetInsertMethod(final String insertMethod)
    {
        m__strInsertMethod = insertMethod;
    }

    /**
     * Specifies the insert method.
     * @param insertMethod such method.
     */
    protected void setInsertMethod(final String insertMethod)
    {
        immutableSetInsertMethod(insertMethod);
    }

    /**
     * Retrieves the insert method.
     * @return such method.
     */
    public String getInsertMethod()
    {
        return m__strInsertMethod;
    }

    /**
     * Specifies the insert parameters Javadoc.
     * @param javadoc such javadoc.
     */
    private void immutableSetInsertParametersJavadoc(final String javadoc)
    {
        m__strInsertParametersJavadoc = javadoc;
    }

    /**
     * Specifies the insert parameters Javadoc.
     * @param javadoc such javadoc.
     */
    protected void setInsertParametersJavadoc(final String javadoc)
    {
        immutableSetInsertParametersJavadoc(javadoc);
    }

    /**
     * Retrieves the insert parameters javadoc.
     * @return such information.
     */
    public String getInsertParametersJavadoc()
    {
        return m__strInsertParametersJavadoc;
    }

    /**
     * Specifies the insert parameters Declaration.
     * @param declaration such declaration.
     */
    private void immutableSetInsertParametersDeclaration(final String declaration)
    {
        m__strInsertParametersDeclaration = declaration;
    }

    /**
     * Specifies the insert parameters Declaration.
     * @param declaration such declaration.
     */
    protected void setInsertParametersDeclaration(final String declaration)
    {
        immutableSetInsertParametersDeclaration(declaration);
    }

    /**
     * Retrieves the insert parameters declaration.
     * @return such information.
     */
    public String getInsertParametersDeclaration()
    {
        return m__strInsertParametersDeclaration;
    }

    /**
     * Specifies the update method.
     * @param updateMethod such method.
     */
    private void immutableSetUpdateMethod(final String updateMethod)
    {
        m__strUpdateMethod = updateMethod;
    }

    /**
     * Specifies the update method.
     * @param updateMethod such method.
     */
    protected void setUpdateMethod(final String updateMethod)
    {
        immutableSetUpdateMethod(updateMethod);
    }

    /**
     * Retrieves the update method.
     * @return such method.
     */
    public String getUpdateMethod()
    {
        return m__strUpdateMethod;
    }

    /**
     * Specifies the update parameters Javadoc.
     * @param javadoc such javadoc.
     */
    private void immutableSetUpdateParametersJavadoc(final String javadoc)
    {
        m__strUpdateParametersJavadoc = javadoc;
    }

    /**
     * Specifies the update parameters Javadoc.
     * @param javadoc such javadoc.
     */
    protected void setUpdateParametersJavadoc(final String javadoc)
    {
        immutableSetUpdateParametersJavadoc(javadoc);
    }

    /**
     * Retrieves the update parameters javadoc.
     * @return such information.
     */
    public String getUpdateParametersJavadoc()
    {
        return m__strUpdateParametersJavadoc;
    }

    /**
     * Specifies the update parameters Declaration.
     * @param declaration such declaration.
     */
    private void immutableSetUpdateParametersDeclaration(final String declaration)
    {
        m__strUpdateParametersDeclaration = declaration;
    }

    /**
     * Specifies the update parameters Declaration.
     * @param declaration such declaration.
     */
    protected void setUpdateParametersDeclaration(final String declaration)
    {
        immutableSetUpdateParametersDeclaration(declaration);
    }

    /**
     * Retrieves the update parameters declaration.
     * @return such information.
     */
    public String getUpdateParametersDeclaration()
    {
        return m__strUpdateParametersDeclaration;
    }

    /**
     * Specifies the delete method.
     * @param deleteMethod such method.
     */
    private void immutableSetDeleteMethod(
        String deleteMethod)
    {
        m__strDeleteMethod = deleteMethod;
    }

    /**
     * Specifies the delete method.
     * @param deleteMethod such method.
     */
    protected void setDeleteMethod(
        String deleteMethod)
    {
        immutableSetDeleteMethod(
            deleteMethod);
    }

    /**
     * Retrieves the delete method.
     * @return such method.
     */
    public String getDeleteMethod()
    {
        return m__strDeleteMethod;
    }

    /**
     * Specifies the delete pk Javadoc.
     * @param deletePkJavadoc such Javadoc.
     */
    private void immutableSetDeletePkJavadoc(
        String deletePkJavadoc)
    {
        m__strDeletePkJavadoc = deletePkJavadoc;
    }

    /**
     * Specifies the delete pk Javadoc.
     * @param deletePkJavadoc such Javadoc.
     */
    protected void setDeletePkJavadoc(
        String deletePkJavadoc)
    {
        immutableSetDeletePkJavadoc(
            deletePkJavadoc);
    }

    /**
     * Retrieves the delete pk Javadoc.
     * @return such Javadoc.
     */
    public String getDeletePkJavadoc()
    {
        return m__strDeletePkJavadoc;
    }
    
    /**
     * Specifies the delete pk declaration.
     * @param deletePkDeclaration such declaration.
     */
    private void immutableSetDeletePkDeclaration(
        String deletePkDeclaration)
    {
        m__strDeletePkDeclaration =
            deletePkDeclaration;
    }

    /**
     * Specifies the delete pk declaration.
     * @param deletePkdeclaration such declaration.
     */
    protected void setDeletePkdeclaration(
        String deletePkDeclaration)
    {
        immutableSetDeletePkDeclaration(
            deletePkDeclaration);
    }

    /**
     * Retrieves the delete pk declaration.
     * @return such declaration.
     */
    public String getDeletePkDeclaration()
    {
        return m__strDeletePkDeclaration;
    }

    /**
     * Specifies the delete with FK method.
     * @param deleteWithFkMethod such method.
     */
    private void immutableSetDeleteWithFkMethod(
        String deleteWithFkMethod)
    {
        m__strDeleteWithFkMethod = deleteWithFkMethod;
    }

    /**
     * Specifies the delete with FK method.
     * @param deleteWithFkMethod such method.
     */
    protected void setDeleteWithFkMethod(
        String deleteWithFkMethod)
    {
        immutableSetDeleteWithFkMethod(
            deleteWithFkMethod);
    }

    /**
     * Retrieves the delete with FK method.
     * @return such method.
     */
    public String getDeleteWithFkMethod()
    {
        return m__strDeleteWithFkMethod;
    }

    /**
     * Specifies the delete with FK PK Javadoc.
     * @param deleteWithFkPkJavadoc such Javadoc.
     */
    private void immutableSetDeleteWithFkPkJavadoc(
        String deleteWithFkPkJavadoc)
    {
        m__strDeleteWithFkPkJavadoc = deleteWithFkPkJavadoc;
    }

    /**
     * Specifies the delete with FK PK Javadoc.
     * @param deleteWithFkPkJavadoc such Javadoc.
     */
    protected void setDeleteWithFkPkJavadoc(
        String deleteWithFkPkJavadoc)
    {
        immutableSetDeleteWithFkPkJavadoc(
            deleteWithFkPkJavadoc);
    }

    /**
     * Retrieves the delete with FK PK Javadoc.
     * @return such Javadoc.
     */
    public String getDeleteWithFkPkJavadoc()
    {
        return m__strDeleteWithFkPkJavadoc;
    }
    
    /**
     * Specifies the delete with FK PK declaration.
     * @param deleteWithFkPkDeclaration such declaration.
     */
    private void immutableSetDeleteWithFkPkDeclaration(
        String deleteWithFkPkDeclaration)
    {
        m__strDeleteWithFkPkDeclaration =
            deleteWithFkPkDeclaration;
    }

    /**
     * Specifies the delete with FK PK declaration.
     * @param deleteWithFkPkdeclaration such declaration.
     */
    protected void setDeleteWithFkPkdeclaration(
        String deleteWithFkPkDeclaration)
    {
        immutableSetDeleteWithFkPkDeclaration(
            deleteWithFkPkDeclaration);
    }

    /**
     * Retrieves the delete with FK PK declaration.
     * @return such declaration.
     */
    public String getDeleteWithFkPkDeclaration()
    {
        return m__strDeleteWithFkPkDeclaration;
    }

    /**
     * Specifies the delete with FK DAO delete request.
     * @param deleteWithFkDAODeleteRequest such request.
     */
    private void immutableSetDeleteWithFkDAODeleteRequest(
        String deleteWithFkDAODeleteRequest)
    {
        m__strDeleteWithFkDAODeleteRequest =
            deleteWithFkDAODeleteRequest;
    }

    /**
     * Specifies the delete with FK DAO delete request.
     * @param deleteWithFkDAODeleteRequest such request.
     */
    protected void setDeleteWithFkDAODeleteRequest(
        String deleteWithFkDAODeleteRequest)
    {
        immutableSetDeleteWithFkDAODeleteRequest(
            deleteWithFkDAODeleteRequest);
    }

    /**
     * Retrieves the delete with FK DAO delete request.
     * @return such request.
     */
    public String getDeleteWithFkDAODeleteRequest()
    {
        return m__strDeleteWithFkDAODeleteRequest;
    }

    /**
     * Specifies the delete with FK PK values.
     * @param deleteWithFkPkValues such values.
     */
    private void immutableSetDeleteWithFkPkValues(
        String deleteWithFkPkValues)
    {
        m__strDeleteWithFkPkValues =
            deleteWithFkPkValues;
    }

    /**
     * Specifies the delete with FK PK values.
     * @param deleteWithFkPkValues such values.
     */
    protected void setDeleteWithFkPkValues(
        String deleteWithFkPkValues)
    {
        immutableSetDeleteWithFkPkValues(
            deleteWithFkPkValues);
    }

    /**
     * Retrieves the delete with FK PK values.
     * @return such values.
     */
    public String getDeleteWithFkPkValues()
    {
        return m__strDeleteWithFkPkValues;
    }

    /**
     * Specifies the persist method.
     * @param persistMethod such method.
     */
    private void immutableSetPersistMethod(final String persistMethod)
    {
        m__strPersistMethod = persistMethod;
    }

    /**
     * Specifies the persist method.
     * @param persistMethod such method.
     */
    protected void setPersistMethod(final String persistMethod)
    {
        immutableSetPersistMethod(persistMethod);
    }

    /**
     * Retrieves the persist method.
     * @return such method.
     */
    public String getPersistMethod()
    {
        return m__strPersistMethod;
    }

    /**
     * Specifies the Undigester's property rules.
     * @param undigesterPropertyRules such rules.
     */
    private void immutableSetUndigesterPropertyRules(
        final String undigesterPropertyRules)
    {
        m__strUndigesterPropertyRules = undigesterPropertyRules;
    }

    /**
     * Specifies the Undigester's property rules.
     * @param undigesterPropertyRules such rules.
     */
    protected void setUndigesterPropertyRules(
        final String undigesterPropertyRules)
    {
        immutableSetUndigesterPropertyRules(undigesterPropertyRules);
    }

    /**
     * Retrieves the Undigester's property rules.
     * @return such rules.
     */
    public String getUndigesterPropertyRules()
    {
        return m__strUndigesterPropertyRules;
    }

    /**
     * Specifies the class end.
     * @param classEnd the new class end.
     */
    private void immutableSetClassEnd(final String classEnd)
    {
        m__strClassEnd = classEnd;
    }

    /**
     * Specifies the class end.
     * @param classEnd the new class end.
     */
    protected void setClassEnd(final String classEnd)
    {
        immutableSetClassEnd(classEnd);
    }

    /**
     * Retrieves the class end.
     * @return such information.
     */
    public String getClassEnd() 
    {
        return m__strClassEnd;
    }

    /**
     * Retrieves the source code generated by this template.
     * @return such code.
     */
    public String toString()
    {
        StringBuffer t_sbResult = new StringBuffer();

        StringUtils t_StringUtils = StringUtils.getInstance();

        EnglishGrammarUtils t_EnglishGrammarUtils =
            EnglishGrammarUtils.getInstance();

        StringValidator t_StringValidator = StringValidator.getInstance();

        MetaDataUtils t_MetaDataUtils = MetaDataUtils.getInstance();

        TableTemplate t_TableTemplate = getTableTemplate();

        DatabaseMetaDataManager t_MetaDataManager = getMetaDataManager();

        PackageUtils t_PackageUtils = PackageUtils.getInstance();

        if  (   (t_TableTemplate   != null)
             && (t_MetaDataManager != null)
             && (t_MetaDataUtils   != null)
             && (t_StringUtils     != null)
             && (t_PackageUtils    != null))
        {
            String t_strRepositoryName =
                t_StringUtils.capitalize(
                    getRepositoryName(),
                    '_');

            MessageFormat t_HeaderFormatter = new MessageFormat(getHeader());

            MessageFormat t_PackageDeclarationFormatter =
                new MessageFormat(getPackageDeclaration());

            MessageFormat t_ProjectImportFormatter =
                new MessageFormat(getProjectImports());

            MessageFormat t_ForeignDAOImportsFormatter =
                new MessageFormat(getForeignDAOImports());

            MessageFormat t_JavadocFormatter = new MessageFormat(getJavadoc());

            MessageFormat t_ClassStartFormatter =
                new MessageFormat(getClassStart());

            MessageFormat t_ClassDefinitionFormatter =
                new MessageFormat(getClassDefinition());

            MessageFormat t_ClassConstructorFormatter =
                new MessageFormat(getClassConstructor());

            MessageFormat t_ClassInternalMethodsFormatter =
                new MessageFormat(getClassInternalMethods());

            MessageFormat t_BuildKeyPkDeclarationFormatter =
                new MessageFormat(getBuildKeyPkDeclaration());

            MessageFormat t_ProcessPkAttributesFormatter =
                new MessageFormat(getProcessPkAttributes());

            MessageFormat t_FindByPrimaryKeyFormatter =
                new MessageFormat(getFindByPrimaryKeyMethod());

            MessageFormat t_FindByPrimaryKeyPkJavadocFormatter =
                new MessageFormat(getFindByPrimaryKeyPkJavadoc());

            MessageFormat t_FindByPrimaryKeyPkDeclarationFormatter =
                new MessageFormat(getFindByPrimaryKeyPkDeclaration());

            MessageFormat t_FindByPrimaryKeyPkFilterValuesFormatter =
                new MessageFormat(getFindByPrimaryKeyPkFilterValues());

            MessageFormat t_BuildValueObjectMethodFormatter =
                new MessageFormat(getBuildValueObjectMethod());

            MessageFormat t_InsertMethodFormatter =
                new MessageFormat(getInsertMethod());

            MessageFormat t_InsertParametersJavadocFormatter =
                new MessageFormat(getInsertParametersJavadoc());

            MessageFormat t_InsertParametersDeclarationFormatter =
                new MessageFormat(getInsertParametersDeclaration());

            MessageFormat t_UpdateMethodFormatter =
                new MessageFormat(getUpdateMethod());

            MessageFormat t_UpdateParametersJavadocFormatter =
                new MessageFormat(getUpdateParametersJavadoc());

            MessageFormat t_UpdateParametersDeclarationFormatter =
                new MessageFormat(getUpdateParametersDeclaration());

            MessageFormat t_DeleteMethodFormatter =
                new MessageFormat(getDeleteMethod());

            MessageFormat t_DeleteWithFkMethodFormatter =
                new MessageFormat(getDeleteWithFkMethod());

            MessageFormat t_DeleteWithFkPkJavadocFormatter =
                new MessageFormat(getDeleteWithFkPkJavadoc());

            MessageFormat t_DeleteWithFkPkDeclarationFormatter =
                new MessageFormat(getDeleteWithFkPkDeclaration());

            MessageFormat t_DeleteWithFkDAODeleteRequestFormatter =
                new MessageFormat(getDeleteWithFkDAODeleteRequest());

            MessageFormat t_DeleteWithFkPkValuesFormatter =
                new MessageFormat(getDeleteWithFkPkValues());

            MessageFormat t_PersistMethodFormatter =
                new MessageFormat(getPersistMethod());

            MessageFormat t_UndigesterPropertyRulesFormatter =
                new MessageFormat(getUndigesterPropertyRules());

            StringBuffer t_sbForeignDAOImports = new StringBuffer();
            StringBuffer t_sbPkJavadoc = new StringBuffer();
            StringBuffer t_sbPkDeclaration = new StringBuffer();
            StringBuffer t_sbBuildKeyPkDeclaration = new StringBuffer();
            StringBuffer t_sbBuildKeyValues = new StringBuffer();
            StringBuffer t_sbProcessPkAttributes = new StringBuffer();
            StringBuffer t_sbPkFilterValues = new StringBuffer();
            StringBuffer t_sbUpdateFilter = new StringBuffer();
            StringBuffer t_sbDeleteMethod = new StringBuffer();
            StringBuffer t_sbSelectFields = new StringBuffer();
            StringBuffer t_sbFilterDeclaration = new StringBuffer();
            StringBuffer t_sbDeleteWithFkPkValues = new StringBuffer();
            StringBuffer t_sbDeleteWithFkPkValuesDeleteRequest = new StringBuffer();

            StringBuffer t_sbDeleteWithFkMethod = new StringBuffer();
            StringBuffer t_sbDeleteWithFkPkJavadoc = new StringBuffer();
            StringBuffer t_sbDeleteWithFkPkDeclaration = new StringBuffer();
            StringBuffer t_sbDeleteWithFkDAODeleteRequest = new StringBuffer();
            StringBuffer t_sbDeleteWithFkDAOFkValues = new StringBuffer();

            StringBuffer t_sbBuildValueObjectParametersDeclaration =
                new StringBuffer();

            StringBuffer t_sbBuildValueObjectRetrieval     = new StringBuffer();
            StringBuffer t_sbInsertParametersJavadoc       = new StringBuffer();
            StringBuffer t_sbInsertParametersDeclaration   = new StringBuffer();
            StringBuffer t_sbUpdateParametersJavadoc       = new StringBuffer();
            StringBuffer t_sbUpdateParametersDeclaration   = new StringBuffer();
            StringBuffer t_sbUpdateParametersSpecification = new StringBuffer();

            StringBuffer t_sbPersistMethod = new StringBuffer();
            StringBuffer t_sbUndigesterPropertyRules = new StringBuffer();

            String t_strDeleteMethodModifier = "public";
            String t_strDeleteMethodSuffix = "";

            boolean t_bForeignKeys = false;

            String[] t_astrReferredTables =
                t_MetaDataManager.getReferredTables(
                    t_TableTemplate.getTableName());

            if  (t_astrReferredTables != null)
            {
                for  (int t_iRefTableIndex = 0;
                          t_iRefTableIndex < t_astrReferredTables.length;
                          t_iRefTableIndex++)
                {
                    t_bForeignKeys = true;

                    String t_strReferredTableName =
                        t_StringUtils.capitalize(
                            t_EnglishGrammarUtils.getSingular(
                                t_astrReferredTables[t_iRefTableIndex]),
                            '_');

                    String t_strFkName =
                        t_MetaDataManager.getReferredKey(
                            t_TableTemplate.getTableName(),
                            t_astrReferredTables[t_iRefTableIndex]);

                    t_sbDeleteWithFkDAODeleteRequest.append(
                        t_DeleteWithFkDAODeleteRequestFormatter.format(
                            new Object[]
                            {
                                t_strReferredTableName,
                                t_TableTemplate.getTableName().toLowerCase(),
                                t_StringUtils.capitalize(
                                    t_MetaDataManager.getForeignKey(
                                        t_TableTemplate.getTableName(),
                                        t_astrReferredTables[t_iRefTableIndex])
                                    .toLowerCase(),
                                    '_')
                            }));

                    t_sbForeignDAOImports.append(
                        t_ForeignDAOImportsFormatter.format(
                            new Object[]
                            {
                                t_PackageUtils.retrieveBaseDAOPackage(
                                    getBasePackageName()),
                                t_strReferredTableName
                                
                            }));
                }
            }

            t_sbResult.append(
                t_HeaderFormatter.format(
                    new Object[]
                    {
                        t_TableTemplate.getTableName()
                    }));

            t_sbResult.append(
                t_PackageDeclarationFormatter.format(
                    new Object[]{getPackageName()}));

            t_sbResult.append(
                t_ProjectImportFormatter.format(
                    new Object[]
                    {
                        getPackageName(),
                        t_PackageUtils.retrieveValueObjectPackage(
                            getBasePackageName()),
                        t_StringUtils.capitalize(
                            t_EnglishGrammarUtils.getSingular(
                                t_TableTemplate.getTableName().toLowerCase()),
                            '_'),
                        t_PackageUtils.retrieveBaseDAOPackage(
                            getBasePackageName()),
                        t_PackageUtils.retrieveBaseDAOFactoryPackage(
                            getBasePackageName()),
                        t_PackageUtils.retrieveDataAccessManagerPackage(
                            getBasePackageName()),
                        t_sbForeignDAOImports
                    }));

            t_sbResult.append(getAcmslImports());
            t_sbResult.append(getJdkImports());
            t_sbResult.append(getExtraImports());

            t_sbResult.append(
                t_JavadocFormatter.format(
                    new Object[]
                    {
                        t_TableTemplate.getTableName()
                    }));

            t_sbResult.append(
                t_ClassDefinitionFormatter.format(
                    new Object[]
                    {
                        t_StringUtils.capitalize(
                            t_EnglishGrammarUtils.getSingular(
                                t_TableTemplate.getTableName().toLowerCase()),
                            '_')
                    }));

            t_sbResult.append(
                t_ClassStartFormatter.format(
                    new Object[]
                    {
                        t_TableTemplate.getTableName().toLowerCase(),
                        t_StringUtils.capitalize(
                            t_EnglishGrammarUtils.getSingular(
                                t_TableTemplate.getTableName().toLowerCase()),
                            '_')
                    }));


            t_sbResult.append(
                t_ClassConstructorFormatter.format(
                    new Object[]
                    {
                        t_StringUtils.capitalize(
                            t_EnglishGrammarUtils.getSingular(
                                t_TableTemplate.getTableName().toLowerCase()),
                            '_')
                    }));

            String[] t_astrPrimaryKeys =
                t_MetaDataManager.getPrimaryKeys(t_TableTemplate.getTableName());

            if  (t_astrPrimaryKeys != null)
            {
                for  (int t_iPkIndex = 0;
                          t_iPkIndex < t_astrPrimaryKeys.length;
                          t_iPkIndex++)
                {
                    t_sbPkJavadoc.append(
                        t_FindByPrimaryKeyPkJavadocFormatter.format(
                            new Object[]
                            {
                                t_astrPrimaryKeys[t_iPkIndex].toLowerCase(),
                                t_astrPrimaryKeys[t_iPkIndex]
                            }));

                    t_sbPkDeclaration.append(
                        t_FindByPrimaryKeyPkDeclarationFormatter.format(
                            new Object[]
                            {
                                t_MetaDataUtils.getNativeType(
                                    t_MetaDataManager.getColumnType(
                                        t_TableTemplate.getTableName(),
                                        t_astrPrimaryKeys[t_iPkIndex])),
                                t_astrPrimaryKeys[t_iPkIndex].toLowerCase()
                            }));

                    t_sbBuildKeyPkDeclaration.append(
                        t_BuildKeyPkDeclarationFormatter.format(
                            new Object[]
                            {
                                t_MetaDataUtils.getNativeType(
                                    t_MetaDataManager.getColumnType(
                                        t_TableTemplate.getTableName(),
                                        t_astrPrimaryKeys[t_iPkIndex])),
                                t_astrPrimaryKeys[t_iPkIndex].toLowerCase()
                            }));

                    String t_strPks =
                        t_FindByPrimaryKeyPkFilterValuesFormatter.format(
                            new Object[]
                            {
                                t_astrPrimaryKeys[t_iPkIndex].toLowerCase()
                            });

                    t_sbPkFilterValues.append(t_strPks);
                    t_sbBuildKeyValues.append(t_strPks);
                    t_sbProcessPkAttributes.append(
                        t_ProcessPkAttributesFormatter.format(
                            new Object[]
                            {
                                t_StringUtils.capitalize(
                                    t_astrPrimaryKeys[t_iPkIndex].toLowerCase(),
                                    '_')
                            }));

                    if  (t_iPkIndex < t_astrPrimaryKeys.length - 1)
                    {
                        t_sbPkFilterValues.append(", ");
                        t_sbBuildKeyPkDeclaration.append(", ");
                        t_sbBuildKeyValues.append(" + ");
                    }

                    t_sbDeleteWithFkPkValues.append(
                        t_DeleteWithFkPkValuesFormatter.format(
                            new Object[]
                            {
                                t_astrPrimaryKeys[t_iPkIndex].toLowerCase()
                            }));

                    t_sbDeleteWithFkPkValuesDeleteRequest.append(
                        t_StringUtils.capitalize(
                            t_astrPrimaryKeys[t_iPkIndex].toLowerCase(),
                            '_'));
                }
            }

            String[] t_astrColumnNames =
                t_MetaDataManager.getColumnNames(t_TableTemplate.getTableName());

            if  (t_astrColumnNames != null)
            {
                for  (int t_iColumnIndex = 0;
                          t_iColumnIndex < t_astrColumnNames.length;
                          t_iColumnIndex++)
                {
                    t_sbBuildValueObjectRetrieval.append(
                        t_astrColumnNames[t_iColumnIndex].toLowerCase());

                    t_sbUndigesterPropertyRules.append(
                        t_UndigesterPropertyRulesFormatter.format(
                            new Object[]
                            {
                                t_StringUtils.capitalize(
                                    t_EnglishGrammarUtils.getSingular(
                                        t_TableTemplate.getTableName()
                                            .toLowerCase()),
                                    '_'),
                                t_StringUtils.unCapitalizeStart(
                                    t_StringUtils.capitalize(
                                        t_astrColumnNames[t_iColumnIndex].toLowerCase(),
                                        '_')),
                            }));

                    if  (!t_MetaDataManager.isManagedExternally(
                             t_TableTemplate.getTableName(),
                             t_astrColumnNames[t_iColumnIndex]))
                    {
                        String t_strParameterDeclaration =
                            t_InsertParametersDeclarationFormatter.format(
                                new Object[]
                                {
                                    t_MetaDataUtils.getNativeType(
                                        t_MetaDataManager.getColumnType(
                                            t_TableTemplate.getTableName(),
                                            t_astrColumnNames[t_iColumnIndex])),
                                    t_astrColumnNames[t_iColumnIndex].toLowerCase()
                                });

                        t_sbBuildValueObjectParametersDeclaration.append(
                            t_strParameterDeclaration);

                        if  (t_iColumnIndex < t_astrColumnNames.length - 1)
                        {
                            t_sbBuildValueObjectParametersDeclaration.append(", ");
                        }

                        t_sbInsertParametersDeclaration.append(
                            t_strParameterDeclaration);
                        t_sbInsertParametersDeclaration.append(", ");
                    }
                    else 
                    {
                        String t_strValue =
                            t_MetaDataManager.getKeyword(
                                t_TableTemplate.getTableName(),
                                t_astrColumnNames[t_iColumnIndex]);

                        if  (t_StringValidator.isEmpty(t_strValue))
                        {
                            t_strValue =
                                t_astrColumnNames[t_iColumnIndex].toLowerCase();
                        }
                        else 
                        {
                            t_strValue =
                                t_StringUtils.normalize(t_strValue, '_');

                        }
                    }

                    if  (!t_MetaDataManager.isPrimaryKey(
                             t_TableTemplate.getTableName(),
                             t_astrColumnNames[t_iColumnIndex]))
                    {
                        t_sbInsertParametersJavadoc.append(
                            t_InsertParametersJavadocFormatter.format(
                                new Object[]
                                {
                                    t_astrColumnNames[t_iColumnIndex].toLowerCase(),
                                    t_astrColumnNames[t_iColumnIndex]
                                }));

                        t_sbUpdateParametersJavadoc.append(
                            t_UpdateParametersJavadocFormatter.format(
                                new Object[]
                                {
                                    t_astrColumnNames[t_iColumnIndex].toLowerCase(),
                                    t_astrColumnNames[t_iColumnIndex]
                                }));

                        t_sbUpdateParametersDeclaration.append(
                            t_UpdateParametersDeclarationFormatter.format(
                                new Object[]
                                {
                                    t_MetaDataUtils.getNativeType(
                                        t_MetaDataManager.getColumnType(
                                            t_TableTemplate.getTableName(),
                                            t_astrColumnNames[t_iColumnIndex])),
                                    t_astrColumnNames[t_iColumnIndex].toLowerCase()
                                }));
                    }

                    if  (t_iColumnIndex < t_astrColumnNames.length - 1)
                    {
                        t_sbBuildValueObjectRetrieval.append(", ");
                    }
                }

                t_sbResult.append(
                    t_ClassInternalMethodsFormatter.format(
                        new Object[]
                        {
                            t_StringUtils.capitalize(
                                t_EnglishGrammarUtils.getSingular(
                                    t_TableTemplate.getTableName()
                                        .toLowerCase()),
                                '_'),
                            t_sbPkJavadoc,
                            t_sbBuildKeyPkDeclaration,
                            t_sbBuildKeyValues,
                            t_EnglishGrammarUtils.getSingular(
                                t_StringUtils.unCapitalize(
                                    t_TableTemplate.getTableName(), "-")),
                            t_PackageUtils.retrieveValueObjectPackage(
                                getBasePackageName()),
                            t_sbProcessPkAttributes
                        }));

                t_sbResult.append(
                    t_FindByPrimaryKeyFormatter.format(
                        new Object[]
                        {
                            t_TableTemplate.getTableName(),
                            t_sbPkJavadoc,
                            t_StringUtils.capitalize(
                                t_EnglishGrammarUtils.getSingular(
                                    t_TableTemplate.getTableName()
                                        .toLowerCase()),
                                '_'),
                            t_sbPkDeclaration,
                            t_sbPkFilterValues
                        }));

                t_sbResult.append(
                    t_BuildValueObjectMethodFormatter.format(
                        new Object[]
                        {
                            t_TableTemplate.getTableName().toUpperCase(),
                            t_StringUtils.capitalize(
                                t_EnglishGrammarUtils.getSingular(
                                    t_TableTemplate.getTableName()
                                        .toLowerCase()),
                                '_'),
                            t_sbPkJavadoc,
                            t_sbInsertParametersJavadoc,
                            t_sbBuildValueObjectParametersDeclaration,
                            t_sbBuildValueObjectRetrieval
                        }));

                t_sbResult.append(
                    t_InsertMethodFormatter.format(
                        new Object[]
                        {
                            t_TableTemplate.getTableName().toUpperCase(),
                            t_StringUtils.capitalize(
                                t_EnglishGrammarUtils.getSingular(
                                    t_TableTemplate.getTableName()
                                        .toLowerCase()),
                                '_'),
                            t_sbPkJavadoc,
                            t_sbInsertParametersJavadoc,
                            t_sbInsertParametersDeclaration,
                            t_sbPkFilterValues,
                            t_sbBuildValueObjectRetrieval
                        }));

                t_sbResult.append(
                    t_UpdateMethodFormatter.format(
                        new Object[]
                        {
                            t_StringUtils.capitalize(
                                t_EnglishGrammarUtils.getSingular(
                                    t_TableTemplate.getTableName()
                                        .toLowerCase()),
                                '_'),
                            t_sbPkJavadoc.toString(),
                            t_sbUpdateParametersJavadoc,
                            t_sbPkDeclaration,
                            t_sbUpdateParametersDeclaration,
                            t_sbPkFilterValues,
                            t_sbBuildValueObjectRetrieval
                        }));


                if  (t_bForeignKeys)
                {
                    t_strDeleteMethodModifier = "protected";
                    t_strDeleteMethodSuffix = 
                        t_StringUtils.capitalize(
                            t_TableTemplate.getTableName(),
                            '_');

                    t_sbDeleteWithFkMethod.append(
                        t_DeleteWithFkMethodFormatter.format(
                            new Object[]
                            {
                                t_StringUtils.capitalize(
                                    t_EnglishGrammarUtils.getSingular(
                                        t_TableTemplate.getTableName()),
                                    '_'),
                                t_sbPkJavadoc,
                                t_sbPkDeclaration,
                                t_sbDeleteWithFkPkValues,
                                t_TableTemplate.getTableName().toLowerCase(),
                                t_sbDeleteWithFkDAODeleteRequest,
                                t_sbDeleteWithFkPkValuesDeleteRequest
                            }));

                    t_sbResult.append(t_sbDeleteWithFkMethod);
                }
                
                t_sbDeleteMethod.append(
                    t_DeleteMethodFormatter.format(
                        new Object[]
                        {
                            t_TableTemplate.getTableName(),
                            t_StringUtils.capitalize(
                                t_EnglishGrammarUtils.getSingular(
                                    t_TableTemplate.getTableName()
                                        .toLowerCase()),
                                '_'),
                            t_sbPkJavadoc,
                            t_sbPkDeclaration,
                            t_strDeleteMethodModifier,
                            t_strDeleteMethodSuffix,
                            t_sbPkFilterValues
                        }));

                t_sbResult.append(t_sbDeleteMethod);

                t_sbPersistMethod.append(
                    t_PersistMethodFormatter.format(
                        new Object[]
                        {
                            t_StringUtils.capitalize(
                                t_EnglishGrammarUtils.getSingular(
                                    t_TableTemplate.getTableName()
                                        .toLowerCase()),
                                '_'),
                            t_EnglishGrammarUtils.getSingular(
                                t_TableTemplate.getTableName()
                                    .toLowerCase()),
                            t_sbUndigesterPropertyRules
                        }));

                t_sbResult.append(t_sbPersistMethod);
            }
        }

        t_sbResult.append(getClassEnd());

        return t_sbResult.toString();
    }
}
