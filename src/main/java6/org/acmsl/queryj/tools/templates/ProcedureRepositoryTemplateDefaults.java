/*
                        QueryJ

    Copyright (C) 2002-2007  Jose San Leandro Armendariz
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
 * Filename: ProcedureRepositoryTemplateDefaults.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Defines the default subtemplates used to generate procedure
 *              repositories according to database metadata.
 *
 */
package org.acmsl.queryj.tools.templates;

/**
 * Defines the default subtemplates used to generate procedure
 * repositories according to database metadata.
 * @author <a href="mailto:chous@acm-sl.org"
 *         >Jose San Leandro</a>
 */
public interface ProcedureRepositoryTemplateDefaults
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
        + " * Description: Contains all procedures belonging to\n"
        + " *              \"{0}\" repository.\n"
        + " */\n";

    /**
     * The package declaration.
     */
    public static final String PACKAGE_DECLARATION = "package {0};\n\n"; // package

    /**
     * The project imports.
     */
    public static final String DEFAULT_PROJECT_IMPORTS_JAVADOC =
          "/*\n"
        + " * Importing project-specific classes.\n"
        + " */\n";

    /**
     * The project imports.
     */
    public static final String PROJECT_IMPORTS = "\n";

    /**
     * The ACM-SL imports.
     */
    public static final String ACMSL_IMPORTS =
          "/*\n"
        + " * Importing some ACM-SL classes.\n"
        + " */\n"
        + "import org.acmsl.queryj.QueryJException;\n\n";

    /**
     * The JDK imports.
     */
    public static final String JDK_IMPORTS =
          "/*\n"
        + " * Importing some JDK classes.\n"
        + " */\n"
        + "import java.math.BigDecimal;\n"
        + "import java.sql.CallableStatement;\n"
        + "import java.sql.Connection;\n"
        + "import java.sql.ResultSet;\n"
        + "import java.sql.SQLException;\n"
        + "import java.sql.Timestamp;\n"
        + "import java.sql.Types;\n"
        + "import java.util.Calendar;\n\n";
    
    /**
     * The default class Javadoc.
     */
    public static final String DEFAULT_JAVADOC =
          "/**\n"
        + " * Contains all procedures belonging to\n"
        + " * <i>{0}</i> repository.\n" // repository
        + " * @author <a href=\"http://maven.acm-sl.org/queryj\">QueryJ</a>\n"
        + " */\n";

    /**
     * The class definition.
     */
    public static final String CLASS_DEFINITION =
         "public abstract class {0}\n"; // repository

    /**
     * The class start.
     */
    public static final String DEFAULT_CLASS_START = "{\n";

    /**
     * The default procedure Javadoc.
     */
    public static final String DEFAULT_PROCEDURE_JAVADOC =
          "    /**\n"
        + "     * (Taken from procedure comment)\n"
        + "     * <pre>\n"
        + "     * {0}\n" // procedure comment.
        + "     * </pre>\n"
        + "     * @param connection the JDBC connection.\n"
        + "{1}" // parameters javadoc
        + "     * @return the associated value object.\n"
        + "     * @precondition connection != null\n"
        + "     */\n";

    /**
     * The procedure's parameter javadoc.
     */
    public static final String DEFAULT_PROCEDURE_PARAMETER_JAVADOC =
        "     * @param {0} (taken from procedure comment) {1}\n";
         // parameter name - parameter comment.

    /**
     * The procedure body.
     */
    public static final String DEFAULT_PROCEDURE_BODY =
          "    public {0} {1}(\n"
         // procedure value object - procedure java name
        + "            Connection connection"
        + "{2})\n"
         // - procedure parameters declaration
        + "        throws  QueryJException\n"
        + "    '{'\n"
        + "        {0} result = {3};\n\n"
        + "        try\n"
        + "        '{'\n"
        + "            // Retrieve data source\n"
        + "            Connection t_Connection = connection;//getConnection();\n\n"
        + "            CallableStatement t_CallableStatement =\n"
        + "                t_Connection.prepareCall(\n"
        + "{4});\n\n" // procedure sentence
        + "{5}\n" // out parameters registration
        + "{6}\n" // in parameters specification
        + "            t_CallableStatement.execute();\n\n"
        + "            ResultSet t_rsResult = t_CallableStatement.getResultSet();\n\n"
        + "            if  (t_rsResult != null)\n"
        + "            '{'\n"
        + "{7}" // out parameters retrieval
        + "                result = t_rsResult.{10};\n"
        + "{8}\n" // value object construction
        + "                t_rsResult.close();\n"
        + "            '}'\n"
        + "        '}'\n"
        + "        catch  (final SQLException sqlException)\n"
        + "        '{'\n"
        + "            throw\n"
        + "                new QueryJException(\n"
        + "                    \"Error executing {11} procedure\",\n"
        + "                    sqlException);\n"
        + "        '}'\n\n"
        + "        return result;\n"
        + "    '}'\n\n";

    /**
     * The parameter declaration.
     */
    public static final String DEFAULT_PROCEDURE_PARAMETER_DECLARATION =
        "\n            final {0} {1}";

    /**
     * The procedure sentence.
     */
    public static final String DEFAULT_PROCEDURE_SENTENCE =
        "                    \"'{'? = {0}({1})'}'\"";
        // procedure name - procedure parameter bindings

    /**
     * OUT parameter registration.
     */
    public static final String DEFAULT_OUT_PARAMETER_REGISTRATION =
          "                t_CallableStatement.registerOutParameter(\n"
        + "                    {0},\n"
        + "                    Types.{1});\n";
    
    /**
     * IN parameter specification.
     */
    public static final String DEFAULT_IN_PARAMETER_SPECIFICATION =
        "            t_CallableStatement.{0};\n";
        // parameter type - parameter index - Java parameter name

    /**
     * OUT parameter retrieval.
     */
    public static final String DEFAULT_OUT_PARAMETER_RETRIEVAL =
          "                {0} t_{1} = t_rsResult.get{3}({2});\n";
          // parameter type - parameter name - parameter index - object parameter type

    /**
     * The value object construction.
     */
    public static final String DEFAULT_VALUE_OBJECT_CONSTRUCTION =
          "                {0} t_{1} = t_rsResult.get{2}({3});\n";
          // parameter type - Java parameter name - parameter name

    /**
     * The default class end.
     */
    public static final String DEFAULT_CLASS_END = "}";
}
