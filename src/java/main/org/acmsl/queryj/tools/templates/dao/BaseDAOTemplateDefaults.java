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
 * Description: Contains the default subtemplates for building base DAO
 *              interfaces.
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
import org.acmsl.queryj.tools.templates.JavaTemplateDefaults;

/**
 * Contains the default subtemplates for building base DAO interfaces.
 * @author <a href="mailto:jsanleandro@yahoo.es"
 *         >Jose San Leandro</a>
 * @version $Revision$
 */
public interface BaseDAOTemplateDefaults
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
        + " * Description: DAO abstract layer responsible of retrieving\n"
        + " *              \"{0}\" structures from persistence layers.\n"
         // Table name
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
     * @param 0 the value object package name.
     * @param 1 the capitalized table name.
     */
    public static final String DEFAULT_PROJECT_IMPORTS =
          "/*\n"
        + " * Importing project classes.\n"
        + " */\n"
        + "import {0}.{1}ValueObject;\n\n";


    /**
     * The ACM-SL imports.
     */
    public static final String ACMSL_IMPORTS = "";

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
        + "import java.util.List;\n\n";

    /**
     * The default class Javadoc.
     */
    public static final String DEFAULT_JAVADOC =
          "/**\n"
        + " * DAO abstract class responsible of requesting services\n"
        + " * to manage <i>{0}</i> structures from persistence layers.\n"
         // Table name
        + " * @author <a href=\"http://maven.acm-sl.org/queryj\">QueryJ</a>\n"
        + " * @version $" + "Revision: $\n"
        + " */\n";

    /**
     * The class definition.
     */
    public static final String CLASS_DEFINITION =
        "public interface {0}DAO\n";
        // table name

    /**
     * The class start.
     */
    public static final String DEFAULT_CLASS_START =
        "{\n";

    /**
     * The constant records, if the table is static.
     * @param 0 the description.
     * @param 1 the capitalized value object name.
     * @param 2 the constant record properties specification.
     */
    public static final String DEFAULT_CONSTANT_RECORD =
          "    /**\n"
        + "     * The {0} constant.\n"
        + "     */\n"
        + "    public static final {1}ValueObject {0} =\n"
        + "        new {1}ValueObject({2});\n\n";

    /**
     * The constant records, if the table is static.
     * @param 2 the capitalized value object name.
     * @param 1 the constant records.
     */
    public static final String DEFAULT_CONSTANT_ARRAY =
          "    /**\n"
        + "     * The whole constants.\n"
        + "     */\n"
        + "    public static final {0}ValueObject[] _ALL_QUERYJ_CONSTANTS_ =\n"
        + "        new {1}ValueObject[]\n"
        + "        '{'\n"
        + "{1}"
        + "        '};\n\n";

    /**
     * The constant record entry, if the table is static.
     * @param 0 the description.
     */
    public static final String DEFAULT_CONSTANT_ARRAY_ENTRY =
        "\n            {0}";

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
        + "     */\n"
        + "    public {3}ValueObject findBy{4}("
        + "{5});\n\n";

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
        + "     * Loads <i>{0}</i> information from the persistence layer filtering\n"
         // table name
        + "     * by its primary keys."
        + "{1}\n"
         // FIND_BY_PRIMARY_KEY_PK_JAVADOC
        + "     * @return the information extracted from the persistence layer.\n"
        + "     */\n"
        + "    public {2}ValueObject findByPrimaryKey("
         // java table name
        + "{3});\n\n";
         // FIND_BY_PRIMARY_KEY_PK_DECLARATION

    /**
     * The find-by-primary-key method's primary keys javadoc.
     * @param 0 the field name (Java valid).
     * @param 1 the field name.
     */
    public static final String DEFAULT_FIND_BY_PRIMARY_KEY_PK_JAVADOC =
        DEFAULT_FIND_BY_STATIC_FIELD_JAVADOC;

    /**
     * The find-by-primary-key method's primary keys declaration.
     * @param 0 the field type.
     * @param 1 the field name (Java valid).
     */
    public static final String DEFAULT_FIND_BY_PRIMARY_KEY_PK_DECLARATION =
        DEFAULT_FIND_BY_STATIC_FIELD_DECLARATION;

    /**
     * The store method.
     */
    public static final String DEFAULT_INSERT_METHOD =
          "    /**\n"
        + "     * Persists {0} information."
         // table name
        + "{1}"
         // (optional) pk javadoc
        + "{2}\n"
         // insert parameters javadoc
        + "     */\n"
        + "    public void insert("
        + "{3}"
         // (optional) pk declaration
        + "{4});\n\n";
         // insert parameters declaration

    /**
     * The insert parameters javadoc.
     */
    public static final String DEFAULT_INSERT_PARAMETERS_JAVADOC =
        "\n     * @param {0} the <i>{1}</i> information.";
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
        + "     * Updates <i>{0}</i> information\n"
        + "     * in the persistence layer."
         // table name
        + "{1}"
         // (optional) pk javadoc
        + "{2}\n"
         // update parameters javadoc
        + "     */\n"
        + "    public void update("
        + "{3}"
         // (optional) pk declaration
        + "{4});\n\n";
         // update parameters declaration

    /**
     * The update parameters javadoc.
     */
    public static final String DEFAULT_UPDATE_PARAMETERS_JAVADOC =
        "\n     * @param {0} the <i>{1}</i> information.";
    // field name - field Name

    /**
     * The update parameters declaration.
     */
    public static final String DEFAULT_UPDATE_PARAMETERS_DECLARATION =
        ",\n        final {0} {1}";
    // field type - field name

    /**
     * The delete method.
     * @param 0 the value object name.
     * @param 1 the pk javadoc
     * @param 2 the pk declaration.
     */
    public static final String DEFAULT_DELETE_METHOD =
          "    /**\n"
        + "     * Deletes {0} information from the persistence layer filtering\n"
        + "     * by its primary keys."
        + "{1}\n"
        + "     */\n"
        + "    public void delete("
        + "{2});\n\n";

    /**
     * The delete method's primary keys javadoc.
     * @param 0 the pk java name
     * @param 1 the pk name.
     */
    public static final String DEFAULT_DELETE_PK_JAVADOC =
        "\n     * @param {0} the <i>{1}</i> value to filter.";

    /**
     * The delete method's primary keys declaration.
     * @param pk java type
     * @param pk name.
     */
    public static final String DEFAULT_DELETE_PK_DECLARATION =
        "\n        final {0} {1}";

    /**
     * The delete by fk method.
     * @param 0 the value object name.
     * @param 1 the fk javadoc
     * @param 2 the fk value object name.
     * @param 3 the fk declaration.
     */
    public static final String DEFAULT_DELETE_BY_FK_METHOD =
          "    /**\n"
        + "     * Deletes {0} information from the persistence layer filtering\n"
        + "     * by given foreign keys."
        + "{1}\n"
        + "     */\n"
        + "    public void deleteBy{2}("
        + "{3});\n\n";

    /**
     * The delete method's primary keys javadoc.
     * @param 0 the fk java name
     * @param 1 the fk name.
     */
    public static final String DEFAULT_DELETE_FK_JAVADOC =
        DEFAULT_DELETE_PK_JAVADOC;

    /**
     * The delete method's foreign keys declaration.
     * @param fk java type
     * @param fk name.
     */
    public static final String DEFAULT_DELETE_FK_DECLARATION =
        "\n        final {0} {1}";

    /**
     * The custom select template.
     * @param 0 the sql id.
     * @param 1 the sql description.
     * @param 2 the parameter Javadoc.
     * @param 3 the result type.
     * @param 4 the sql method name.
     * @param 5 the parameter declaration.
     */
    public static final String DEFAULT_CUSTOM_SELECT =
          "    /**\n"
        + "     * Custom select <i>{0}</i>.\n"
        + "     * {1}"
         // sql id - sql description
        + "{2}\n"
         // CUSTOM_SELECT_PARAMETER_JAVADOC
        + "     * @return the <i>{3}</i> information retrieved.\n"
        + "     */\n"
        + "    public {3} {4}("
         // result class - sql name
        + "{5});\n";
         // CUSTOM_SELECT_PARAMETER_DECLARATION

    /**
     * The custom select parameter javadoc.
     */
    public static final String DEFAULT_CUSTOM_SELECT_PARAMETER_JAVADOC =
        "\n     * @param {0} the value to filter.";
         // parameter name

    /**
     * The custom select parameter declaration.
     */
    public static final String DEFAULT_CUSTOM_SELECT_PARAMETER_DECLARATION =
        "\n        final {0} {1}";
         // parameter type - parameter name

    /**
     * The custom update or insert template.
     * @param 0 sql id.
     * @param 1 sql description.
     * @param 2 the parameter Javadoc.
     * @param 3 the sql method name.
     * @param 4 the parameter declaration.
     */
    public static final String DEFAULT_CUSTOM_UPDATE_OR_INSERT =
          "    /**\n"
        + "     * Custom select <i>{0}</i>.\n"
        + "     * {1}"
         // sql id - sql description
        + "{2}\n"
         // CUSTOM_SELECT_PARAMETER_JAVADOC
        + "     */\n"
        + "    public void {3}("
         // sql name
        + "{4});\n";
         // CUSTOM_UPDATE_OR_INSERT_PARAMETER_DECLARATION

    /**
     * The custom update or insert parameter javadoc.
     */
    public static final String DEFAULT_CUSTOM_UPDATE_OR_INSERT_PARAMETER_JAVADOC =
        "\n     * @param {0} such information.";
         // parameter name

    /**
     * The custom update or insert parameter declaration.
     */
    public static final String DEFAULT_CUSTOM_UPDATE_OR_INSERT_PARAMETER_DECLARATION =
        DEFAULT_CUSTOM_SELECT_PARAMETER_DECLARATION;

    /**
     * The custom select for update template.
     * @param 0 the sql id.
     * @param 1 the sql description.
     * @param 2 parameter Javadoc.
     * @param 3 return Javadoc.
     * @param 4 the result type.
     * @param 5 the method name.
     * @param 6 the parameter declaration.
     */
    public static final String DEFAULT_CUSTOM_SELECT_FOR_UPDATE =
          "    /**\n"
        + "     * <i>{0}</i>:\n"
         // sql id
        + "     * {1}"
         // sql description
        + "{2}\n"
         // parameter Javadoc
        + "{3}"
         // return Javadoc
        + "     */\n"
        + "    public {4} {5}("
         // result class - method name
        + "{6});\n";
         // parameter declaration

    /**
     * The custom select-for-update parameter javadoc.
     */
    public static final String DEFAULT_CUSTOM_SELECT_FOR_UPDATE_PARAMETER_JAVADOC =
        DEFAULT_CUSTOM_SELECT_PARAMETER_JAVADOC;

    /**
     * The custom select-for-update return javadoc.
     */
    public static final String DEFAULT_CUSTOM_SELECT_FOR_UPDATE_RETURN_JAVADOC =
          "     * @return the information extracted from the persistence layer\n"
        + "     * and/or processed.\n";

    /**
     * The custom select-for-update parameter declaration.
     */
    public static final String DEFAULT_CUSTOM_SELECT_FOR_UPDATE_PARAMETER_DECLARATION =
        DEFAULT_CUSTOM_SELECT_PARAMETER_DECLARATION;

    /**
     * The default class end.
     */
    public static final String DEFAULT_CLASS_END = "}";
}
