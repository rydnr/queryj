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
 * Description: Defines the default subtemplates to generate DAO sources.
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
 * Defines the default subtemplates to generate DAO sources.
 * @author <a href="mailto:jsanleandro@yahoo.es"
           >Jose San Leandro</a>
 * @version $Revision$
 */
public interface DAOTemplateDefaults
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
        + " * Description: DAO layer responsible of retrieving\n"
        + "                \"{2}\" structures from "
         // Table name
        + "{0} {1} persistence layers.\n"
         // engine name - driver version
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
    public static final String DEFAULT_PACKAGE_DECLARATION =
        "package {0};\n\n"; // package

    /**
     * The project imports.
     * @param 0 JDBC operations package.
     * @param 1 Capitalized value object name.
     * @param 2 custom result set extractor imports
     * @param 3 value object package
     * @param 4 base DAO package
     * @param 5 rdb package
     * @param 6 table repository package
     * @param 7 table repository name.
     * @param 8 DataAccessManager package.
     */
    public static final String DEFAULT_PROJECT_IMPORTS =
          "/*\n"
        + " * Importing project-specific classes.\n"
        + " */\n"
        + "import {0}.{1}AttributesStatementSetter;\n"
         // Specific JDBC operations package - table name
        + "import {0}.{1}PkStatementSetter;\n"
         // Specific JDBC operations package - table name
        + "import {0}.{1}ResultSetExtractor;\n"
         // Specific JDBC operations package - table name
        + "{2}"
         // Custom resultset extractors' imports
        + "import {3}.{1}ValueObject;\n"
         // ValueObject package - table name
        + "import {4}.{1}DAO;\n"
         // DAO interface package - table name
        + "import {5}.QueryPreparedStatementCreator;\n"
         // RDB base package - table name
        + "import {6}.{7}TableRepository;\n"
         // Table repository package - Table repository name
        + "import {8}.DataAccessManager;\n"
         // data access manager package
        + "{9}";
         // foreign DAO imports

    /**
     * The custom result set extractors.
     */
    public static final String DEFAULT_CUSTOM_RESULT_SET_EXTRACTOR_IMPORT =
        "import {0}.{1}Extractor;\n";
         // Specific JDBC operations package - custom result id

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
        + " * Importing some ACM-SL classes.\n"
        + " */\n"
        + "import org.acmsl.queryj.DeleteQuery;\n"
        + "import org.acmsl.queryj.InsertQuery;\n"
        + "import org.acmsl.queryj.Query;\n"
        + "import org.acmsl.queryj.QueryFactory;\n"
        + "import org.acmsl.queryj.SelectQuery;\n"
        + "import org.acmsl.queryj.UpdateQuery;\n\n";

    /**
     * Additional imports.
     */
    public static final String DEFAULT_ADDITIONAL_IMPORTS =
          "\n/*\n"
        + " * Importing Spring classes.\n"
        + " */\n"
        + "import org.springframework.dao.DataAccessException;\n"
        + "import org.springframework.jdbc.core.JdbcTemplate;\n"
        + "import org.springframework.jdbc.core.PreparedStatementCreatorFactory;\n"
        + "import org.springframework.jdbc.core.ResultSetExtractor;\n"
        + "import org.springframework.jdbc.core.SqlParameter;\n"
        + "import org.springframework.jdbc.object.SqlQuery;\n";
    
    /**
     * The JDK imports.
     */
    public static final String DEFAULT_JDK_IMPORTS =
          "/*\n"
        + " * Importing some JDK classes.\n"
        + " */\n"
        + "import java.math.BigDecimal;\n"
        + "import java.sql.SQLException;\n"
        + "import java.sql.Types;\n"
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
     */
    public static final String DEFAULT_JAVADOC =
          "/**\n"
        + " * DAO class responsible of retrieving\n"
        + " * <code>{2}</code> structures from\n"
         // Table name
        + " * {0} {1} persistence layers.\n"
         // engine name - driver version
        + " * @author <a href=\"http://maven.acm-sl.org/queryj\">QueryJ</a>\n"
        + " */\n";

    /**
     * The class definition.
     */
    public static final String DEFAULT_CLASS_DEFINITION =
          "public class {0}{1}DAO\n"
        + "    extends     JdbcTemplate\n"
        + "    implements  {1}DAO\n";
        // engine name - table name

    /**
     * The class start.
     */
    public static final String DEFAULT_CLASS_START =
        "{\n";

    /**
     * The class' constants.
     */
    public static final String DEFAULT_CLASS_CONSTANTS =
          "    // <constants>\n\n"
        + "    /**\n"
        + "     * The result set extractor for <i>{0}</i>\n"
        + "     * value objects.\n"
        + "     */\n"
        + "    public static final ResultSetExtractor "
        + "{1}_EXTRACTOR =\n"
        + "        new {2}ResultSetExtractor();\n\n"
        + "{3}"
         // result set extractor constant
        + "    // </constants>\n\n";

    /**
     * The custom result set extractor constant.
     */
    public static final String DEFAULT_CUSTOM_RESULT_SET_EXTRACTOR_CONSTANT =
          "    /**\n"
        + "     * The result set extractor for <i>{0}</i>\n"
        + "     */\n"
        + "    public static final ResultSetExtractor "
        + "{1}_EXTRACTOR =\n"
        + "        new {2}Extractor();\n\n";

    /**
     * The class constructor.
     */
    public static final String DEFAULT_CLASS_CONSTRUCTOR =
          "    /**\n"
        + "     * Builds a <code>{0}{1}DAO</code> instance\n"
        // engine name - table name
        + "     * with given data source.\n"
        + "     * @param dataSource the required data source.\n"
        + "     * @precondition dataSource != null\n"
        + "     */\n"
        + "    public {0}{1}DAO(final DataSource dataSource)\n"
        + "    '{'\n"
        + "        super(dataSource);\n"
        + "    '}'\n\n";
        // engine name - table name

    // <helper subtemplates>

    /**
     * The primary keys javadoc.
     */
    public static final String DEFAULT_PK_JAVADOC =
        "\n     * @param {0} the <i>{1}</i> value to filter.";
         // java pk - pk

    /**
     * The attribute javadoc.
     */
    public static final String DEFAULT_ATTRIBUTE_JAVADOC =
        "\n     * @param {0} the <i>{1}</i> information.";
         // java attribute - attribute

    /**
     * The attribute declaration.
     */
    public static final String DEFAULT_ATTRIBUTE_DECLARATION =
        "\n        final {0} {1}";
         // attribute type - java attribute

    /**
     * The attribute filter.
     */
    public static final String DEFAULT_ATTRIBUTE_FILTER =
        "        result.where({0}TableRepository.{1}.{2}.equals());\n";
    // table repository name - table name - field name

    /**
     * The statement setter call.
     */
    public static final String DEFAULT_STATEMENT_SETTER_CALL =
        "\n                        {0}";
        // java pk

    // </helper subtemplates>

    /**
     * The find by primary key method.
     */
    public static final String DEFAULT_FIND_BY_PRIMARY_KEY_METHOD =
          "     // <find by primary key>\n\n"
        + "    /**\n"
        + "     * Builds the query for finding the <code>{0}</code>\n"
        + "     * information searching by its primary key.\n"
        + "     * @return the <code>SelectQuery</code> instance.\n"
        + "     */\n"
        + "    protected Query buildFindByPrimaryKeyQuery()\n"
        + "    '{'\n"
        + "        return buildFindByPrimaryKeyQuery("
        +              "QueryFactory.getInstance());\n"
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Creates the query for finding the <code>{0}</code>\n"
        + "     * information searching by its primary key.\n"
        + "     * @param queryFactory the <code>QueryFactory</code> "
        + "instance.\n"
        + "     * @return the <code>SelectQuery</code> instance.\n"
        + "     * @precondition queryFactory != null\n"
        + "     */\n"
        + "    protected Query buildFindByPrimaryKeyQuery("
        +          "final QueryFactory queryFactory)\n"
        + "    '{'\n"
        + "        SelectQuery result = queryFactory.createSelectQuery();\n\n"
        + "        result.select({1}TableRepository.{2}.getAll());\n\n"
         // table repository name - table instance name
        + "        result.from({1}TableRepository.{2});\n\n"
         // table repository name - table instance name
        + "{3}\n"
         // PK_FILTER
        + "        return result;\n"
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Retrieves <code>{4}</code> information\n"
        + "     * filtering by its primary key."
        + "{5}\n"
         // PK_JAVADOC
        + "     * @throws DataAccessException if the operation fails.\n"
        + "     */\n"
        + "    public {6}ValueObject findByPrimaryKey("
         // Capitalized Value Object name
        + "{7})\n"
        + "      throws DataAccessException\n"
         // PK_DECLARATION
        + "    '{'\n"
        + "        Query t_Query = buildFindByPrimaryKeyQuery();\n\n"
        + "        return\n"
        + "            ({6}ValueObject)\n"
         // Capitalized Value Object name
        + "                query(\n"
        + "                    new QueryPreparedStatementCreator(t_Query),\n"
        + "                    new {6}PkStatementSetter({8}),\n"
         // Capitalized Value Object name
        + "                    {9}_EXTRACTOR);\n"
         // Upper case table name
        + "    '}'\n\n"
        + "    // </find by primary key>\n\n";

    /**
     * The insert method.
     */
    public static final String DEFAULT_INSERT_METHOD =
          "    // <insert>\n\n"
        + "    /**\n"
        + "     * Builds the query required to <i>insert</i> a concrete\n"
        + "     * <code>{0}</code> instance.\n"
        + "     * @return the <code>InsertQuery</code> instance.\n"
        + "     */\n"
        + "    protected Query buildInsertQuery()\n"
        + "    '{'\n"
        + "        return buildInsertQuery(QueryFactory.getInstance());\n"
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Builds the query required to <i>insert</i> a concrete\n"
        + "     * <code>{0}</code> instance.\n"
        + "     * @param queryFactory the <code>QueryFactory</code> "
        + "instance.\n"
        + "     * @return the <code>InsertQuery</code> instance.\n"
        + "     * @precondition queryFactory != null\n"
        + "     */\n"
        + "    protected Query buildInsertQuery("
        +          "final QueryFactory queryFactory)\n"
        + "    '{'\n"
        + "        InsertQuery result = queryFactory.createInsertQuery();\n\n"
        + "        result.insertInto({1}TableRepository.{2});\n"
        + "{3}\n\n"
        + "        return result;\n"
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Persists <code>{0}</code> information."
         // table name
        + "{4}\n"
         // attributes javadoc
        + "     * @throws DataAccessException if the operation fails.\n"
        + "     */\n"
        + "    public void insert("
        + "{5})\n"
         // attributes declaration
        + "      throws DataAccessException\n"
        + "    '{'\n"
        + "        Query t_Query = buildInsertQuery();\n\n"
        + "        update(\n"
        + "            new QueryPreparedStatementCreator(t_Query),\n"
        + "            new {0}AttributesStatementSetter({6}));\n"
        + "    '}'\n\n"
        + "    // </insert>\n\n";

    /**
     * The insert parameters specification.
     */
    public static final String DEFAULT_INSERT_PARAMETERS_SPECIFICATION =
        "\n        result.value({0}TableRepository.{1}.{2});";
    // repository - table - field

    /**
     * The update method.
     */
    public static final String DEFAULT_UPDATE_METHOD =
          "    // <update>\n\n"
        + "    /**\n"
        + "     * Builds the query required to <i>update</i> a concrete\n"
        + "     * <code>{0}</code> entity, determined by its primary key.\n"
        + "     * @return the <code>UpdateQuery</code> instance.\n"
        + "     */\n"
        + "    protected Query buildUpdateQuery()\n"
        + "    '{'\n"
        + "        return buildUpdateQuery(QueryFactory.getInstance());\n"
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Builds the query required to <i>update</i> a concrete\n"
        + "     * <code>{0}</code> entity, determined by its primary key.\n"
        + "     * @param queryFactory the <code>QueryFactory</code> "
        + "instance.\n"
        + "     * @return the <code>UpdateQuery</code> instance.\n"
        + "     * @precondition queryFactory != null\n"
        + "     */\n"
        + "    protected Query buildUpdateQuery("
        +          "final QueryFactory queryFactory)\n"
        + "    '{'\n"
        + "        UpdateQuery result = queryFactory.createUpdateQuery();\n\n"
        + "        result.update({1}TableRepository.{2});\n\n"
        + "{3}\n"
         // update parameters specification
        + "{4}\n"
         // pk filter
        + "        return result;\n"
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Updates <code>{0}</code> information."
         // table name
        + "{5}\n"
         // attributes javadoc
        + "     * @throws DataAccessException if the operation fails.\n"
        + "     */\n"
        + "    public void update("
        + "{6})\n"
         // attributes declaration
        + "      throws DataAccessException\n"
        + "    '{'\n"
        + "        Query t_Query = buildUpdateQuery();\n\n"
        + "        update(\n"
        + "            new QueryPreparedStatementCreator(t_Query),\n"
        + "            new {0}AttributesStatementSetter({7}));\n"
        + "    '}'\n\n"
        + "    // </update>\n\n";

    /**
     * The update parameters specification.
     */
    public static final String DEFAULT_UPDATE_PARAMETERS_SPECIFICATION =
        "        result.set({0}TableRepository.{1}.{2});\n";
    // table repository name - table name - field name

    /**
     * The delete method.
     */
    public static final String DEFAULT_DELETE_METHOD =
          "    // <delete>\n\n"
        + "    /**\n"
        + "     * Builds the query required to <i>delete</i> a concrete\n"
        + "     * <code>{0}</code> entity, determined by its primary key.\n"
        + "     * @return the <code>DeleteQuery</code> instance.\n"
        + "     */\n"
        + "    protected Query buildDeleteQuery()\n"
        + "    '{'\n"
        + "        return buildDeleteQuery(QueryFactory.getInstance());\n"
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Builds the query required to <i>delete</i> a concrete\n"
        + "     * <code>{0}</code> entity, determined by its primary key.\n"
        + "     * @param queryFactory the <code>QueryFactory</code> "
        + "instance.\n"
        + "     * @return the <code>DeleteQuery</code> instance.\n"
        + "     * @precondition queryFactory != null\n"
        + "     */\n"
        + "    protected Query buildDeleteQuery("
        +          "final QueryFactory queryFactory)\n"
        + "    '{'\n"
        + "        DeleteQuery result = queryFactory.createDeleteQuery();\n\n"
        + "        result.deleteFrom({1}TableRepository.{2});\n\n"
        + "{3}\n"
         // delete filter
        + "        return result;\n"
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Deletes <code>{0}</code> information."
         // table name
        + "{4}\n"
         // pk javadoc
        + "     * @throws DataAccessException if the operation fails.\n"
        + "     */\n"
        + "    public void delete("
        + "{5})\n"
         // pk declaration
        + "      throws DataAccessException\n"
        + "    '{'\n"
        + "        Query t_Query = buildDeleteQuery();\n\n"
        + "        update(\n"
        + "            new QueryPreparedStatementCreator(t_Query),\n"
        + "            new {0}PkStatementSetter({6}));\n"
        + "    '}'\n\n"
        + "    // </delete>\n\n";

    /**
     * The custom select template.
     * @param 0 the sql id.
     * @param 1 the sql description.
     * @param 2 the parameter Javadoc.
     * @param 3 the result type.
     * @param 4 the sql method name.
     * @param 5 the parameter declaration.
     * @param 6 the sql sentence.
     * @param 7 the parameter type specification.
     * @param 8 the parameter values.
     * @param 9 the extractor constant.
     */
    public static final String DEFAULT_CUSTOM_SELECT =
          "    /**\n"
        + "     * Custom select <i>{0}</i>.\n"
        + "     * {1}"
         // sql id - sql description
        + "{2}\n"
         // CUSTOM_SELECT_PARAMETER_JAVADOC
        + "     * @return the <i>{3}</i> information retrieved.\n"
        + "     * @throws DataAccessException if the operation fails.\n"
        + "     */\n"
        + "    public {3} {4}("
         // result class - sql name
        + "{5})\n"
         // CUSTOM_SELECT_PARAMETER_DECLARATION
        + "      throws DataAccessException\n"
        + "    '{'\n"
        + "        {3} result = null;\n\n"
         // java table name
        + "        PreparedStatementCreatorFactory "
        +              "t_PreparedStatementCreatorFactory =\n"
        +     "             new PreparedStatementCreatorFactory(\n"
        +     "                 \"{6}\");\n\n"
        + "        /*\n"
        + "        t_PreparedStatementCreatorFactory.setResultSetType(..);\n"
        + "        t_PreparedStatementCreatorFactory.setUpdatableResults(..);\n"
        + "        t_PreparedStatementCreatorFactory.setReturnGeneratedKeys(..);\n"
        + "        t_PreparedStatementCreatorFactory.setGeneratedKeysColumnNames(..);\n"
        + "        */\n\n"
        + "{7}\n"
        + "        Object[] t_aParams =\n"
        + "            new Object[]\n"
        + "            '{'"
        + "{8}\n"
        + "            '}';\n\n"
        + "        result =\n"
        + "            ({3})\n"
        + "                query(\n"
        + "                    t_PreparedStatementCreatorFactory\n"
        + "                        .newPreparedStatementCreator(t_aParams),\n"
        + "                    t_PreparedStatementCreatorFactory\n"
        + "                        .newPreparedStatementSetter(t_aParams),\n"
        + "                    {9}_EXTRACTOR);\n\n"
        + "        return result;\n"
        + "    '}'\n\n";

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
     * The custom select parameter type specification.
     * @param 0 the SQL parameter type.
     */
    public static final String DEFAULT_CUSTOM_SELECT_PARAMETER_TYPE_SPECIFICATION =
          "        t_PreparedStatementCreatorFactory.addParameter(\n"
        + "            new SqlParameter(Types.{0}));\n";

    /**
     * The custom select parameter values.
     * @param 0 the parameter type.
     * @param 1 the parameter name.
     */
    public static final String DEFAULT_CUSTOM_SELECT_PARAMETER_VALUES =
        "\n                new {0}({1})";

    /**
     * The custom select result properties.
     */
    public static final String DEFAULT_CUSTOM_SELECT_RESULT_PROPERTIES =
        "\n                        t_rsResults.get{0}({1})";

    /**
     * The custom update or insert template.
     * @param 0 sql id.
     * @param 1 sql description.
     * @param 2 the parameter Javadoc.
     * @param 3 the sql method name.
     * @param 4 the parameter declaration.
     * @param 5 the sql sentence.
     * @param 6 the parameter type specification.
     * @param 7 the parameter values.
     */
    public static final String DEFAULT_CUSTOM_UPDATE_OR_INSERT =
          "    /**\n"
        + "     * Custom select <i>{0}</i>.\n"
        + "     * {1}"
         // sql id - sql description
        + "{2}\n"
         // CUSTOM_SELECT_PARAMETER_JAVADOC
        + "     * @throws DataAccessException if operation fails.\n"
        + "     */\n"
        + "    public void {3}("
         // sql name
        + "{4})\n"
         // CUSTOM_UPDATE_OR_INSERT_PARAMETER_DECLARATION
        + "      throws DataAccessException\n"
        + "    '{'\n"
        + "        PreparedStatementCreatorFactory "
        +              "t_PreparedStatementCreatorFactory =\n"
        +     "             new PreparedStatementCreatorFactory(\n"
        +     "                 \"{5}\");\n\n"
        + "        /*\n"
        + "        t_PreparedStatementCreatorFactory.setResultSetType(..);\n"
        + "        t_PreparedStatementCreatorFactory.setUpdatableResults(..);\n"
        + "        t_PreparedStatementCreatorFactory.setReturnGeneratedKeys(..);\n"
        + "        t_PreparedStatementCreatorFactory.setGeneratedKeysColumnNames(..);\n"
        + "        */\n\n"
        + "{6}\n"
        + "        Object[] t_aParams =\n"
        + "            new Object[]\n"
        + "            '{'"
        + "{7}\n"
        + "            '}';\n\n"
        + "        update(\n"
        + "            t_PreparedStatementCreatorFactory\n"
        + "                .newPreparedStatementCreator(t_aParams),\n"
        + "            t_PreparedStatementCreatorFactory\n"
        + "                .newPreparedStatementSetter(t_aParams));\n"
        + "    '}'\n\n";

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
     * The custom update or insert parameter type specification.
     * @param 0 the SQL parameter type.
     */
    public static final String DEFAULT_CUSTOM_UPDATE_OR_INSERT_PARAMETER_TYPE_SPECIFICATION =
        DEFAULT_CUSTOM_SELECT_PARAMETER_TYPE_SPECIFICATION;

    /**
     * The custom update or insert parameter values.
     */
    public static final String DEFAULT_CUSTOM_UPDATE_OR_INSERT_PARAMETER_VALUES =
        DEFAULT_CUSTOM_SELECT_PARAMETER_VALUES;

    /**
     * The custom select for update template.
     * @param 0 the sql id.
     * @param 1 the sql description.
     * @param 2 parameter Javadoc.
     * @param 3 return Javadoc.
     * @param 4 the result type.
     * @param 5 the method name.
     * @param 6 the parameter declaration.
     * @param 7 the parameter specification,
     * @param 8 the custom select for update subtemplate.
     * @param 9 the conditional return.
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
        + "     * @throws DataAccessException if the access to the information fails.\n"
        + "     */\n"
        + "    public {4} {5}("
         // result class - method name
        + "{6})\n"
         // parameter declaration
        + "      throws DataAccessException\n"
        + "    '{'\n"
        + "        {9}"
        + "        {5}(\n"
         // method name
        + "{7},\n"
         // parameter specification
        + "                getDataSource());\n"
        + "    '}'\n\n"
        + "    /**\n"
        + "     * <i>{0}</i>:\n"
         // sql id
        + "     * {1}"
         // sql description
        + "{2}\n"
         // parameter Javadoc
        + "     * @param dataSource the data source.\n"
        + "{3}"
        + "     * @throws DataAccessException if the access to the information fails.\n"
        + "     * @precondition dataSource != null\n"
        + "     */\n"
        + "    protected {4} {5}("
         // result class - method name
        + "{6},\n"
         // parameter declaration
        + "        final DataSource dataSource)\n"
        + "      throws DataAccessException\n"
        + "    '{'\n"
        + "{8}"
         // custom select for update subtemplate
        + "    '}'\n\n";

    /**
     * The custom select for update template with no return.
     * @param 0 the query object name.
     * @param 1 the sql query.
     */
    public static final String DEFAULT_CUSTOM_SELECT_FOR_UPDATE_WITH_NO_RETURN =
          "        SqlQuery t_Query =\n"
        + "            new SqlQuery(\n"
        + "                dataSource,\n"
        + "                \"{1}\");\n\n"
        + "        t_Query.execute();\n";

    /**
     * The custom select for update template, returning 1 or more instances.
     * @param 0 the query object name.
     * @param 1 the sql query.
     * @param 2 the result type.
     * @param 3 the subtemplate.
     */
    public static final String DEFAULT_CUSTOM_SELECT_FOR_UPDATE_WITH_RETURN =
          "        {2} result = null;\n\n"
         // result type
        + "        SqlQuery t_Query =\n"
        + "            new SqlQuery(\n"
        + "                dataSource,\n"
        + "                \"{1}\");\n\n"
        + "        List t_lResult = t_Query.execute();\n\n"
        + "{3}\n"
        + "        return result;\n";

    /**
     * The custom select for update, returning a single instance.
     */
    public static final String DEFAULT_CUSTOM_SELECT_FOR_UPDATE_WITH_SINGLE_RETURN =
          "        if  (   (t_lResult != null)\n"
        + "             && (t_lResult.size() > 0))\n"
        + "        {\n"
        + "            result = t_lResult.get(0);\n"
        + "        }\n";

    /**
     * The custom select for update, returning multiple instances.
     */
    public static final String DEFAULT_CUSTOM_SELECT_FOR_UPDATE_WITH_MULTIPLE_RETURN =
        "        result = t_lResult;\n";

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
     * The custom select-for-update parameter specification.
     * @param 0 parameter name
     */
    public static final String DEFAULT_CUSTOM_SELECT_FOR_UPDATE_PARAMETER_SPECIFICATION =
        "                {0}";

    /**
     * The conditional return.
     */
    public static final String DEFAULT_CUSTOM_SELECT_FOR_UPDATE_CONDITIONAL_RETURN =
          "        return\n"
        + "    ";

    /**
     * The default class end.
     */
    public static final String DEFAULT_CLASS_END = "}\n";
}
