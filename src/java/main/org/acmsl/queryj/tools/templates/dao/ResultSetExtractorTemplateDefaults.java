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
 * Description: Defines the default subtemplates to generate resultset
 *              extractors.
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
package org.acmsl.queryj.tools.templates.dao;

/*
 * Importing some project-specific classes.
 */

/**
 * Defines the default subtemplates to generate resultset extractors.
 * @author <a href="mailto:jsanleandro@yahoo.es"
           >Jose San Leandro</a>
 * @version $Revision$
 */
public interface ResultSetExtractorTemplateDefaults
{
    /**
     * The default header.
     * @param 0 the table name.
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
        + "import java.util.Calendar;\n"
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
          "public class {0}ResultSetExtractor\n"
        + "    implements  ResultSetExtractor\n";

    /**
     * The class start.
     */
    public static final String DEFAULT_CLASS_START =
        "{\n";

    /**
     * The <code>extractData</code> methods.
     * @param 0 the value object name.
     * @param 1 the uncapitalized value object name.
     * @param 2 the value object properties specification.
     */
    public static final String DEFAULT_EXTRACT_DATA_METHOD =
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
        + "     * @precondition resultSet instanceof QueryResultSet\n"
        + "     */\n"
        + "    public Object extractData(final ResultSet resultSet)\n"
        + "        throws  SQLException,\n"
        + "                DataAccessException\n"
        + "    '{'\n"
        + "        return\n"
        + "            extractData(\n"
        + "                (QueryResultSet) resultSet,\n"
        + "                {0}ValueObjectFactory.getInstance());\n"
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Extracts <i>{0}</i> information from given result set.\n"
        + "     * @param resultSet the result set.\n"
        + "     * @param {1}Factory the <code>{0}ValueObjectFactory</code> instance.\n"
        + "     * @return the <code>{0}ValueObject</code> or <code>null</code>\n"
        + "     * if the operation returned no data.\n"
        + "     * @throws SQLException intercepted by <i>Spring</i>.\n"
        + "     * @throws DataAccessException with information about any\n"
        + "     * custom exception.\n"
        + "     * @precondition resultSet != null\n"
        + "     * @precondition {1}Factory != null\n"
        + "     */\n"
        + "    protected {0}ValueObject extractData(\n"
        + "        final QueryResultSet resultSet,\n"
        + "        final {0}ValueObjectFactory {1}Factory)\n"
        + "      throws  SQLException,\n"
        + "              DataAccessException\n"
        + "    '{'\n"
        + "        {0}ValueObject result = null;\n\n"
        + "        if  (resultSet.next())\n"
        + "        '{'\n"
        + "            result =\n"
        + "                {1}Factory.create{0}ValueObject({2});\n"
        + "        '}'\n\n"
        + "        return result;\n"
        + "    '}'\n";

    /**
     * The value object properties specification.
     * @param 0 the property type.
     * @param 1 the table repository name.
     * @param 2 the table name.
     * @param 3 the property name.
     */
    public static final String DEFAULT_VALUE_OBJECT_PROPERTIES_SPECIFICATION =
          "\n                    resultSet.get{0}(\n"
        + "                        {1}TableRepository.{2}.{3})";

    /**
     * The default class end.
     */
    public static final String DEFAULT_CLASS_END = "}\n";
}
