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
 * Description: Defines the default subtemplates to generate
 +              FkStatementSetter templates.
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
 * Defines the default subtemplates to generate FkStatementSetter
 * templates.
 * @author <a href="mailto:jsanleandro@yahoo.es"
 *         >Jose San Leandro</a>
 * @version $Revision$
 */
public interface FkStatementSetterTemplateDefaults
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
        + " * Description: Specifies the PreparedStatement values required\n"
        + " *              to perform any {0} operation expecting the\n"
        + " *              complete fk set.\n"
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
         // Custom resultset setters' imports
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
        + "import org.acmsl.queryj.Query;\n\n";

    /**
     * Additional imports.
     */
    public static final String DEFAULT_ADDITIONAL_IMPORTS =
          "\n/*\n"
        + " * Importing Spring classes.\n"
        + " */\n"
        + "import org.springframework.dao.DataAccessException;\n"
        + "import org.springframework.jdbc.core.PreparedStatementSetter;\n";
    
    /**
     * The JDK imports.
     */
    public static final String DEFAULT_JDK_IMPORTS =
          "/*\n"
        + " * Importing some JDK classes.\n"
        + " */\n"
        + "import java.math.BigDecimal;\n"
        + "import java.sql.PreparedStatement;\n"
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
     * @param 1 the foreign key.
     */
    public static final String DEFAULT_JAVADOC =
          "/**\n"
        + " * Specifies the <code>PreparedStatement</code> values required\n"
        + " * to perform any <i>{0}</i> operation using just {1}\n"
        + " * foreign key.\n"
        + " * @author <a href=\"http://maven.acm-sl.org/queryj\">QueryJ</a>\n"
        + " */\n";

    /**
     * The class definition.
     * @param 0 the value object name.
     * @param 1 the capitalized referred table.
     */
    public static final String DEFAULT_CLASS_DEFINITION =
          "public class {0}By{1}StatementSetter\n"
        + "    implements  PreparedStatementSetter\n";

    /**
     * The class start.
     * @param 0 the parameter accessors.
     */
    public static final String DEFAULT_CLASS_START =
          "'{'\n"
        + "{0}";

    /**
     * The class constructor.
     * @param 0 the value object name.
     * @param 1 the capitalized referred table.
     * @param 2 the parameters javadoc.
     * @param 2 the parameters declaration.
     * @param 3 the parameter setter call.
     */
    public static final String DEFAULT_CLASS_CONSTRUCTOR =
          "    /**\n"
        + "     * Creates a <code>{0}By{1}StatementSetter</code> instance.\n"
        + "{2}"
        + "     */\n"
        + "    public {0}By{1}StatementSetter({3})\n"
        + "    '{'\n"
        + "{4}"
        + "    '}'\n\n";

    /**
     * The parameter declaration.
     * @param 0 the parameter type.
     * @param 1 the parameter name.
     */
    public static final String DEFAULT_PARAMETER_SETTER_CALL =
        "        immutableSet{0}({1});\n";

    /**
     * The parameter accessors.
     * @param 0 the property name.
     * @param 1 the parameter type.
     * @param 2 the parameter name.
     * @param 3 the capitalized parameter name.
     */
    public static final String DEFAULT_PARAMETER_ACCESSOR =
          "    /**\n"
        + "     * The <i>{0}</i> information.\n"
        + "     */\n"
        + "    private {1} {2};\n\n"
        + "    /**\n"
        + "     * Specifies the <i>{0}</i> value.\n"
        + "     * @param {2} such value.\n"
        + "     */\n"
        + "    protected final void immutableSet{3}(final {1} {2})\n"
        + "    '{'\n"
        + "        this.{2} = {2};\n"
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Specifies the <i>{0}</i> value.\n"
        + "     * @param {2} such value.\n"
        + "     */\n"
        + "    protected void set{3}(final {1} {2})\n"
        + "    '{'\n"
        + "        immutableSet{3}({2});\n"
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Retrieves the <i>{0}</i> value.\n"
        + "     * @return such value.\n"
        + "     */\n"
        + "    protected {1} get{3}()\n"
        + "    '{'\n"
        + "        return {2};\n"
        + "    '}'\n\n";
       
    /**
     * The <code>setValues</code> methods.
     * @param 0 the parameter getters call.
     * @param 1 the parameter javadoc.
     * @param 2 the parameter declaration.
     * @param 3 the parameter specification.
     */
    public static final String DEFAULT_SET_VALUES_METHOD =
          "     // <set values>\n\n"
        + "    /**\n"
        + "     * Specifies the values on given <code>PreparedStatement</code>\n"
        + "     * @param preparedStatement the prepared statement.\n"
        + "     * @throws SQLException intercepted by <i>Spring</i>.\n"
        + "     * @precondition preparedStatement != null\n"
        + "     * @precondition preparedStatement instanceof Query\n"
        + "     */\n"
        + "    public void setValues(final PreparedStatement preparedStatement)\n"
        + "        throws  SQLException\n"
        + "    '{'\n"
        + "        setValues(\n"
        + "            (Query) preparedStatement"
        + "{0});\n"
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Specifies the values on given <code>PreparedStatement</code>\n"
        + "     * @param preparedStatement the prepared statement.\n"
        + "{1}"
        + "     * @throws SQLException intercepted by <i>Spring</i>.\n"
        + "     * @precondition query != null\n"
        + "     */\n"
        + "    protected void setValues(\n"
        + "        final Query query,"
        + "{2})\n"
        + "      throws  SQLException\n"
        + "    '{'\n"
        + "{3}"
        + "    '}'\n";

    /**
     * The parameter declaration.
     * @param 1 the capitalized parameter name.
     */
    public static final String DEFAULT_PARAMETER_GETTER_CALL =
        ",\n            get{0}()";

    /**
     * The parameter javadoc.
     * @param 0 the parameter name.
     * @param 1 the property name.
     */
    public static final String DEFAULT_PARAMETER_JAVADOC =
        "     * @param {0} the <i>{1}</i> value.\n";

    /**
     * The parameter declaration.
     * @param 0 the parameter type.
     * @param 1 the parameter name.
     */
    public static final String DEFAULT_PARAMETER_DECLARATION =
        "\n        final {0} {1}";

    /**
     * The parameter specification.
     * @param 0 the parameter type.
     * @param 1 the table repository name.
     * @param 2 the table name.
     * @param 3 the property name.
     * @param 4 the parameter name.
     */
    public static final String DEFAULT_PARAMETER_SPECIFICATION =
          "        query.set{0}(\n"
        + "            {1}TableRepository.{2}.{3}.equals(),\n"
        + "            {4});\n\n";

    /**
     * The default class end.
     */
    public static final String DEFAULT_CLASS_END = "}\n";
}
