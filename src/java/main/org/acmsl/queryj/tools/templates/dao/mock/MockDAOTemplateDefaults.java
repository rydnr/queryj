/*
                        QueryJ

    Copyright (C) 2002-2005  Jose San Leandro Armendariz
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
    Contact info: chous@acm-sl.org
    Postal Address: c/Playa de Lagoa, 1
                    Urb. Valdecabanas
                    Boadilla del monte
                    28660 Madrid
                    Spain

 ******************************************************************************
 *
 * Filename: $RCSfile: $
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Contains the default subtemplates to create mock DAO
 *              implementations for each table in the persistence model.
 *
 */
package org.acmsl.queryj.tools.templates.dao.mock;

/**
 * Contains the default subtemplates to create mock DAO implementations for
 * each table in the persistence model.
 * @author <a href="mailto:chous@acm-sl.org"
           >Jose San Leandro</a>
 */
public interface MockDAOTemplateDefaults
{
    /**
     * The default header.
     */
    public static final String DEFAULT_HEADER =
          "/*\n"
        + "                        QueryJ\n"
        + "\n"
        + "    Copyright (C) 2002-2005  Jose San Leandro Armendariz\n"
        + "                        chous@acm-sl.org\n"
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
        + "    Contact info: chous@acm-sl.org\n"
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
        + " * Description: DAO layer responsible of simulating a persistence\n"
        + "                layer for {2} structures.\n"
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
        + "import org.acmsl.queryj.dao.TransactionToken;\n\n";

    /**
     * The JDK imports.
     */
    public static final String JDK_IMPORTS =
          "/*\n"
        + " * Importing some JDK classes.\n"
        + " */\n"
        + "import java.math.BigDecimal;\n"
        + "import java.util.Calendar;\n"
        + "import java.util.Date;\n"
        + "import java.util.Hashtable;\n"
        + "import java.util.Map;\n\n";

    /**
     * The logging imports.
     */
    public static final String LOGGING_IMPORTS =
          "/*\n"
        + " * Importing Jakarta Commons Logging classes\n"
        + " */\n"
        + "import org.apache.commons.logging.LogFactory;\n\n";
    
    /**
     * The default class Javadoc.
     */
    public static final String DEFAULT_JAVADOC =
          "/**\n"
        + " * DAO class responsible of simulating a persistence layer \n"
        + " * for {0} structures.\n"
        + " * @author <a href=\"http://maven.acm-sl.org/queryj\">QueryJ</a>\n"
        + " */\n";

    /**
     * The class definition.
     */
    public static final String CLASS_DEFINITION =
          "public abstract class Mock{0}DAO\n"
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
        + "    private static Map m__m{1}Map;\n\n";

    /**
     * The class constructor.
     */
    public static final String CLASS_CONSTRUCTOR =
          "    /**\n"
        + "     * Builds a {0}DAO.\n"
        // table name
        + "     */\n"
        + "    public Mock{0}DAO() '{}';\n\n";

    /**
     * The class internal methods.
     */
    public static final String DEFAULT_CLASS_INTERNAL_METHODS =
          "    /**\n"
        + "     * Specifies the map used to simulate the persistence\n"
        + "     * layer.\n"
        + "     * @param map the new map.\n"
        + "     */\n"
        + "    protected static void set{0}Map(final Map map)\n"
        + "    '{'\n"
        + "        m__m{0}Map = map;\n"
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Retrieves the map used to simulate the persistence\n"
        + "     * layer.\n"
        + "     * return such map.\n"
        + "     */\n"
        + "    protected static Map get{0}Map()\n"
        + "    '{'\n"
        + "        return m__m{0}Map;\n"
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
        "\n        {0} {1}";
         // pk type - java pk

    /**
     * The build-key method's pk values.
     */
    public static final String DEFAULT_BUILD_KEY_PK_VALUES =
          "{0}";
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
    public static final String BUILD_VALUE_OBJECT_METHOD =
          "    /**\n"
        + "     * Fills up a {0} with the information provided."
         // table name
        + "{2}"
         // (optional) pk javadoc
        + "{3}"
         // insert parameters javadoc
        + "\n     * @return a {1}ValueObject filled up with the"
        + "\n     * simulated persisted contents."
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
        + "     * Simulates given {0} information is persisted."
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
        + "     */\n"
        + "    public void update(\n"
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
        + "                build{0}(\n"
        + "                    {6});\n"
        + "        '}'\n\n"
        + "        synchronized (LOCK)\n"
        + "        '{'\n"
        + "            Map t_m{0}Map = get{0}Map();\n\n"
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
        "        {0} {1},\n";
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
        + "     * @return <code>true</code> if the information has been deleted\n"
        + "     * successfully.\n"
        + "     */\n"
        + "    {4} boolean delete{5}(\n"
         // java table name
        + "{3})\n"
         // DELETE_PK_DECLARATION
        + "    '{'\n"
        + "        boolean result = false;\n\n"
        + "        synchronized (LOCK)\n"
        + "        '{'\n"
        + "            Map t_m{1}Map = get{1}Map();\n\n"
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
        "        {0}              {1},\n";
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
        + "     * @return <code>true</code> if the information has been deleted\n"
        + "     * successfully.\n"
        + "     */\n"
        + "    public boolean delete(\n"
        + "{2})\n"
         // DELETE_PK_DECLARATION
        + "    '{'\n"
        + "        return\n"
        + "        // delete with fk, comparing for mock implementations.\n"
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
        "        {0}              {1},\n";
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
        + "                                {1}.get{2}());\n"
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
     * The default class end.
     */
    public static final String DEFAULT_CLASS_END = "}\n";
}
