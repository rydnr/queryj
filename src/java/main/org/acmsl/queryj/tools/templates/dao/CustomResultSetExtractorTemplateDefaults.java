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
 * Description: Defines the default subtemplates to generate custom
 *              resultset extractors.
 */
package org.acmsl.queryj.tools.templates.dao;

/*
 * Importing some project-specific classes.
 */
import org.acmsl.queryj.tools.templates.JavaTemplateDefaults;

/**
 * Defines the default subtemplates to generate custom result set extractors.
 * @author <a href="mailto:jsanleandro@yahoo.es"
 *         >Jose San Leandro</a>
 */
public interface CustomResultSetExtractorTemplateDefaults
    extends  JavaTemplateDefaults
{
    /**
     * The default header.
     * @param 0 the table name.
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
        + " * Description: Extracts {0} objects from result sets.\n"
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
     * @param 0 package name.
     */
    public static final String DEFAULT_PACKAGE_DECLARATION =
        "package {0};\n\n";

    /**
     * The project imports.
     * @param 0 value object package
     * @param 1 Capitalized value object name.
     * @param 2 table repository package
     * @param 3 table repository name.
     */
    public static final String DEFAULT_PROJECT_IMPORTS =
          "/*\n"
        + " * Importing project-specific classes.\n"
        + " */\n"
         // Custom resultset extractors' imports
        + "import {0}.{1}ValueObject;\n"
         // ValueObject package - Value object name
        + "import {0}.{1}ValueObjectFactory;\n"
         // ValueObject package - Value object name
        + "import {2}.{3}TableRepository;\n";

    /**
     * The ACM-SL imports.
     */
    public static final String DEFAULT_ACMSL_IMPORTS =
          "\n/*\n"
        + " * Importing some ACM-SL classes.\n"
        + " */\n"
        + "import org.acmsl.queryj.QueryResultSet;\n\n";

    /**
     * Additional imports.
     */
    public static final String DEFAULT_ADDITIONAL_IMPORTS =
          "\n/*\n"
        + " * Importing Spring classes.\n"
        + " */\n"
        + "import org.springframework.dao.DataAccessException;\n"
        + "import org.springframework.jdbc.core.ResultSetExtractor;\n";
    
    /**
     * The JDK imports.
     */
    public static final String DEFAULT_JDK_IMPORTS =
          "/*\n"
        + " * Importing some JDK classes.\n"
        + " */\n"
        + "import java.math.BigDecimal;\n"
        + "import java.sql.ResultSet;\n"
        + "import java.sql.SQLException;\n"
        + "import java.util.ArrayList;\n"
        + "import java.util.Calendar;\n"
        + "import java.util.Collection;\n"
        + "import java.util.Date;\n\n";

    /**
     * The JDK extension imports.
     */
    public static final String DEFAULT_JDK_EXTENSION_IMPORTS =
          "/*\n"
        + " * Importing some JDK extension classes\n"
        + " */\n"
        + "import javax.sql.DataSource;\n\n";

    /**
     * The logging imports.
     */
    public static final String DEFAULT_LOGGING_IMPORTS =
          "/*\n"
        + " * Importing Jakarta Commons Logging classes\n"
        + " */\n"
        + "import org.apache.commons.logging.LogFactory;\n\n";
    
    /**
     * The default class Javadoc.
     * @param 0 the value object name.
     */
    public static final String DEFAULT_JAVADOC =
          "/**\n"
        + " * Extracts <i>{0}</i> value objects from result sets.\n"
         // value object name
        + " * @author <a href=\"http://maven.acm-sl.org/queryj\">QueryJ</a>\n"
        + " */\n";

    /**
     * The class definition.
     * @param 0 the value object name.
     */
    public static final String DEFAULT_CLASS_DEFINITION =
          "public class {0}Extractor\n"
        + "    implements  ResultSetExtractor\n";

    /**
     * The class start.
     */
    public static final String DEFAULT_CLASS_START =
        "{\n";

    /**
     * The <code>extractData</code> method, for single result.
     * @param 0 the value object name.
     * @param 1 the value object properties specification.
     */
    public static final String DEFAULT_EXTRACT_DATA_METHOD_WITH_SINGLE_RESULT =
          "     // <extract data>\n\n"
        + "    /**\n"
        + "     * Extracts <i>{0}</i> information from given result set.\n"
        + "     * @param resultSet the result set.\n"
        + "     * @return the <code>{0}ValueObject</code> or <code>null</code>\n"
        + "     * if the operation returned no data.\n"
        + "     * @throws SQLException intercepted by <i>Spring</i>.\n"
        + "     * @throws DataAccessException with information about any\n"
        + "     * custom exception.\n"
        + "     * @precondition resultSet != null\n"
        + "     */\n"
        + "    public Object extractData(final ResultSet resultSet)\n"
        + "        throws  SQLException,\n"
        + "                DataAccessException\n"
        + "    '{'\n"
        + "        {0} result = null;\n\n"
        + "        if  (resultSet.next())\n"
        + "        '{'\n"
        + "            result =\n"
        + "                new {0}("
        + "{1});\n"
        + "        '}'\n\n"
        + "        return result;\n"
        + "    '}'\n";

    /**
     * The <code>extractData</code> methods, for multiple results
     * @param 0 the value object name.
     * @param 1 the value object properties specification.
     */
    public static final String DEFAULT_EXTRACT_DATA_METHOD_WITH_MULTIPLE_RESULTS =
          "     // <extract data>\n\n"
        + "    /**\n"
        + "     * Extracts <i>{0}</i> information from given result set.\n"
        + "     * @param resultSet the result set.\n"
        + "     * @return the list of retrieved <code>{0}</code> instances.\n"
        + "     * @throws SQLException intercepted by <i>Spring</i>.\n"
        + "     * @throws DataAccessException with information about any\n"
        + "     * custom exception.\n"
        + "     * @precondition resultSet != null\n"
        + "     */\n"
        + "    public Object extractData(final ResultSet resultSet)\n"
        + "        throws  SQLException,\n"
        + "                DataAccessException\n"
        + "    '{'\n"
        + "        Collection result = new ArrayList();\n\n"
        + "        while  (resultSet.next())\n"
        + "        '{'\n"
        + "            result.add(\n"
        + "                new {0}("
        + "{1}));\n"
        + "        '}'\n\n"
        + "        return result;\n"
        + "    '}'\n";

    /**
     * The value object properties specification.
     * @param 0 the property type.
     * @param 1 the property name.
     */
    public static final String DEFAULT_VALUE_OBJECT_PROPERTIES_SPECIFICATION =
          "\n                    resultSet.get{0}(\n"
        + "                        \"{1}\")";

    /**
     * The default class end.
     */
    public static final String DEFAULT_CLASS_END = "}\n";
}
