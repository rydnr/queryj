//;-*- mode: java -*-
/*
                        QueryJ

    Copyright (C) 2002-today  Jose San Leandro Armendariz
                              chous@acm-sl.org

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
    Contact info: jose.sanleandro@acm-sl.com

 ******************************************************************************
 *
 * Filename: XMLDAOTemplateDefaults.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Defines the default subtemplates used to create xml DAO
 *              implementations for each table in the persistence model.
 *
 */
package org.acmsl.queryj.tools.templates.dao.xml;

/*
 * Importing some project-specific classes.
 */
import org.acmsl.queryj.tools.templates.JavaTemplateDefaults;

/**
 * Defines the default subtemplates used to create xml DAO implementations
 * for each table in the persistence model.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public interface XMLDAOTemplateDefaults
    extends  JavaTemplateDefaults
{
    /**
     * The default header.
     */
    public static final String DEFAULT_HEADER =
          HEADER
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
        + " */\n";

    /**
     * The package declaration.
     */
    public static final String DEFAULT_PACKAGE_DECLARATION =
        "package {0};\n\n"; // package

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
    public static final String DEFAULT_ACMSL_IMPORTS =
          "\n/*\n"
        + " * Importing ACM-SL Commons classes.\n"
        + " */\n"
        + "import org.acmsl.commons.patterns.dao.DataAccessException;\n"
        + "import org.acmsl.commons.utils.io.IOUtils;\n\n";

    /**
     * The JDK imports.
     */
    public static final String DEFAULT_JDK_IMPORTS =
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
    public static final String DEFAULT_EXTRA_IMPORTS =
          "/*\n"
        + " * Importing Undigester classes.\n"
        + " */\n"
        + "import org.acmsl.undigester.GetNameRule;\n"
        + "import org.acmsl.undigester.GetNextRule;\n"
        + "import org.acmsl.undigester.GetPropertyRule;\n"
        + "import org.acmsl.undigester.Undigester;\n"
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
        + " */\n";

    /**
     * The class definition.
     */
    public static final String DEFAULT_CLASS_DEFINITION =
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
    public static final String DEFAULT_CLASS_CONSTRUCTOR =
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
        + "     */\n"
        + "    protected Map load()\n"
        + "    '{'\n"
        + "        return load(configureDigester(), getInput());\n"
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Loads the information from the XML resource.\n"
        + "     * @param digester the Digester instance.\n"
        + "     * @param stream the input stream.\n"
        + "     * @return the {0} information.\n"
        + "     * @precondition (stream != null)\n"
        + "     */\n"
        + "    protected synchronized Map load(\n"
        + "        final Digester digester,\n"
        + "        final InputStream input)\n"
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
        + "            catch  (final Exception exception)\n"
        + "            '{'\n"
        + "                try\n"
        + "                '{'\n"
        + "                    LogFactory.getLog(getClass()).error(\n"
        + "                        \"Cannot read XML {0} information.\",\n"
        + "                        exception);\n\n"
        + "                '}'\n"
        + "                catch  (final Throwable throwable)\n"
        + "                '{'\n"
        + "                    // class-loading problem.\n"
        + "                '}'\n\n"
        + "                //throw\n"
        + "                //    new DataAccessException(\n"
        + "                //        \"Cannot.read.xml.{0}.information\",\n"
        + "                //        exception);\n"
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
        + "                    ({0}ValueObject) t_Iterator.next();\n\n"
        + "                if  (t_ValueObject != null)\n"
        + "                '{'\n"
        + "                    map.put(\n"
        + "                        buildKey("
        + "{6}),\n"
        + "                        t_ValueObject);\n"
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
          "\n                            t_ValueObject.get{0}()";
         // value

    /**
     * The find by static field method.
     * @param 0 the table name.
     * @param 1 the static field.
     * @param 2 the static field javadoc.
     * @param 3 the capitalized table name.
     * @param 4 the capitalized static column.
     * @param 5 the static field declaration.
     */
    public static final String DEFAULT_FIND_BY_STATIC_FIELD_METHOD =
          "    /**\n"
        + "     * Loads <i>{0}</i> information from the persistence layer filtering\n"
        + "     * by {1}."
        + "{2}\n"
        + "     * @return the information extracted from the persistence layer.\n"
        + "     * @precondition {1} != null\n"
        + "     */\n"
        + "    public {3}ValueObject findConstantBy{4}("
        + "{5})\n"
        + "    '{'\n"
        + "        {3}ValueObject result = null;\n\n"
        + "        for  (int t_iIndex = 0; t_iIndex < _ALL_QUERYJ_CONSTANTS_.length; t_iIndex++)\n"
        + "        '{'\n"
        + "            {3}ValueObject t_CurrentItem = _ALL_QUERYJ_CONSTANTS_[t_iIndex];\n\n"
        + "            if  (   (t_CurrentItem != null)\n"
        + "                 && ({1}.equals(t_CurrentItem.get{4}())))\n"
        + "            '{'\n"
        + "                result = t_CurrentItem;\n"
        + "            '}'\n"
        + "        '}'\n\n"
        + "        return result;\n"
        + "    '}'\n\n";

    /**
     * The find-by-static-field method's field javadoc.
     * @param 0 the field name (Java valid).
     * @param 1 the field name.
     */
    public static final String DEFAULT_FIND_BY_STATIC_FIELD_JAVADOC =
        "\n     * @param {0} the <i>{1}</i> value to filter.";

    /**
     * The find-by-primary-key method's primary keys declaration.
     * @param 0 the field type.
     * @param 1 the field name (Java valid).
     */
    public static final String DEFAULT_FIND_BY_STATIC_FIELD_DECLARATION =
        "\n        final {0} {1}";

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
        + "     * @return the information extracted from the simulated\n"
        + "     * persistence layer.\n"
        + "     */\n"
        + "    public {2}ValueObject findByPrimaryKey("
         // java table name
        + "{3})\n"
         // FIND_BY_PRIMARY_KEY_PK_DECLARATION
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
        "\n        final {0} {1}";
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
    public static final String DEFAULT_BUILD_VALUE_OBJECT_METHOD =
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
        + "                t_{1}Factory.create{1}({5});\n"
         // BUILD_VALUE_OBJECT_VALUE_RETRIEVAL
        + "        '}'\n\n"
        + "        return result;\n"
        + "    '}'\n\n";

    /**
     * The build value object value retrieval.
     */
    public static final String DEFAULT_BUILD_VALUE_OBJECT_VALUE_RETRIEVAL =
        "\n                    {0}";

    /**
     * The store method.
     */
    public static final String DEFAULT_INSERT_METHOD =
          "    /**\n"
        + "     * Persists given {0}\n"
        + "     * information, retrieving the new instance.\n"
         // table name
        + "{2}"
         // (optional) pk javadoc
        + "{3}\n"
         // insert parameters javadoc
        + "     */\n"
        + "    public {1}ValueObject create("
        + "{4})\n"
         // insert parameters declaration
        + "    '{'\n"
        + "        return null;\n"
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Persists given {0} information."
         // table name
        + "{2}"
         // (optional) pk javadoc
        + "{3}\n"
         // insert parameters javadoc
        + "     */\n"
        + "    public void insert("
        + "{4})\n"
         // insert parameters declaration
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
        + "            build{1}({6}));\n"
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
        "\n        final {0} {1}";
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
        + "     */\n"
        + "    public void update("
        + "{3}"
         // pk declaration
        + "{4})\n"
         // update parameters declaration
        + "    '{'\n"
        + "        {0}ValueObject t_{0}ValueObject =\n"
        + "            findByPrimaryKey(\n"
        + "                {5});\n\n"
         // pk values
        + "        if  (t_{0}ValueObject != null)\n"
        + "        '{'\n"
        + "            t_{0}ValueObject =\n"
        + "                build{0}({6});\n"
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
        "\n        final {0} {1}";
    // field type - field name

    /**
     * The delete method.
     */
    public static final String DEFAULT_DELETE_METHOD_SUBTEMPLATE =
          "    /**\n"
        + "     * Deletes {0} information from the persistence layer filtering\n"
         // table name
        + "     * by its primary keys."
        + "{1}\n"
         // DELETE_PK_JAVADOC
        + "     */\n"
        + "    protected void deleteNoFk("
         // java table name
        + "{4})\n"
         // DELETE_PK_DECLARATION
        + "    '{'\n"
        + "        synchronized (LOCK)\n"
        + "        '{'\n"
        + "            Map t_m{5}Map = get{5}Map();\n\n"
        + "            if  (t_m{5}Map == null)\n"
        + "            '{'\n"
        + "                t_m{5}Map = load();\n"
        + "            '}'\n\n"
        + "            if  (t_m{5}Map == null)\n"
        + "            '{'\n"
        + "                t_m{5}Map = new Hashtable();\n"
        + "                set{5}Map(t_m{5}Map);\n"
        + "            '}'\n"
        + "            if  (t_m{5}Map != null)\n"
        + "            '{'\n"
        + "                t_m{5}Map.remove(\n"
        + "                    buildKey({6}));\n\n"
        + "            '}'\n"
        + "        '}'\n\n"
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
        "\n        final {0} {1}";
         // pk type - java pk

    /**
     * The delete no fk method.
     */
    public static final String DEFAULT_DELETE_NO_FK_METHOD =
          "    /**\n"
        + "     * Deletes {0} information from the persistence layer filtering\n"
         // table name
        + "     * by its primary keys."
        + "{1}\n"
         // DELETE_PK_JAVADOC
        + "     */\n"
        + "    public void delete("
        + "{4})\n"
         // DELETE_PK_DECLARATION
        + "    '{'\n"
        + "        deleteNoFk({7});\n"
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
        "\n        final {0} {1}";
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
        + "                        t_{0}DAO.delete(\n"
        + "                            {1}.get{2}());\n"
        + "                    '}'\n"
        + "                '}'\n";

    /**
     * The delete with fk methods' FK values.
     */
    public static final String DEFAULT_DELETE_WITH_FK_PK_VALUES =
        "\n                    {0}";

    /**
     * The delete by fk method.
     * @param 0 the value object name.
     * @param 1 the fk javadoc
     * @param 2 the fk value object name.
     * @param 3 the fk declaration.
     * @param 4 the fk attribute filter.
     * @param 5 the fk statement setter call.
     * @param 6 the repository name.
     * @param 7 the table name.
     */
    public static final String DEFAULT_DELETE_BY_FK_METHOD =
          "    /**\n"
        + "     * Deletes {0} information from the persistence layer filtering\n"
        + "     * by given foreign keys."
        + "{11}\n"
        + "     */\n"
        + "    public void deleteBy{14}("
        + "{13})\n"
        + "    '{'\n"
        + "        throw new IllegalArgumentException(\"Not implemented\");\n"
        + "    '}'\n";

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
        + "            try\n"
        + "            '{'\n"
        + "                LogFactory.getLog(getClass()).warn(\n"
        + "                    \"No {0} information to serialize.\");\n"
        + "            '}'\n"
        + "            catch  (final Throwable throwable)\n"
        + "            '{'\n"
        + "                // class-loading problem.\n"
        + "            '}'\n\n"
        + "        '}'\n"
        + "        else if  (output == null)\n"
        + "        '{'\n"
        + "            try\n"
        + "            '{'\n"
        + "                LogFactory.getLog(getClass()).warn(\n"
        + "                    \"No output stream to serialize the information through.\");\n"
        + "            '}'\n"
        + "            catch  (final Throwable throwable)\n"
        + "            '{'\n"
        + "                // class-loading problem.\n"
        + "            '}'\n\n"
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
        + "                try\n"
        + "                '{'\n"
        + "                    LogFactory.getLog(getClass()).fatal(\n"
        + "                          \"Cannot serialize {0} information. This could be \"\n"
        + "                        + \"a bug in QueryJ-based generated code. \"\n"
        + "                        + \"Please provide us information about this issue\"\n"
        + "                        + \"though http://bugzilla.acm-sl.org\",\n"
        + "                        undigesterException);\n"
        + "                '}'\n"
        + "                catch  (final Throwable throwable)\n"
        + "                '{'\n"
        + "                    // class-loading problem.\n"
        + "                '}'\n\n"
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
}
