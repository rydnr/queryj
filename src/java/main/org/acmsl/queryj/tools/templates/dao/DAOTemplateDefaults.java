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
        + "import {2}.{1};\n"
         // ValueObject package - table name
        + "import {3}.{1}DAO;\n"
         // DAO interface package - table name
        + "import {4}.QueryPreparedStatementCreator;\n"
         // RDB base package - table name
        + "import {5}.{6};\n"
         // Table repository package - Table repository name
        + "import {7}.DataAccessManager;\n"
         // data access manager package
        + "{8}";
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
        + "import org.springframework.jdbc.core.ResultSetExtractor;\n";
    
    /**
     * The JDK imports.
     */
    public static final String DEFAULT_JDK_IMPORTS =
          "/*\n"
        + " * Importing some JDK classes.\n"
        + " */\n"
        + "import java.math.BigDecimal;\n"
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
        + "{1}_RESULT_SET_EXTRACTOR =\n"
        + "        new {2}ResultSetExtractor();\n\n"
        + "    // </constants>\n\n";

    /**
     * The class constructor.
     */
    public static final String DEFAULT_CLASS_CONSTRUCTOR =
          "    /**\n"
        + "     * Builds a <code>{0}{1}DAO</code> instance\n"
        // engine name - table name
        + "     * with given data source.\n"
        + "     * @param dataSource the required data source.\n"
        + "     * @precondition dateSource != null\n"
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
        "        result.where({0}.{1}.{2}.equals());\n";
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
        + "        result.select({1}.{2}.getAll());\n\n"
         // table repository name - table instance name
        + "        result.from({1}.{2});\n\n"
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
        + "    public {6} findByPrimaryKey("
         // Capitalized Value Object name
        + "{7})\n"
        + "      throws DataAccessException\n"
         // PK_DECLARATION
        + "    '{'\n"
        + "        Query t_Query = buildFindByPrimaryKeyQuery();\n\n"
        + "        return\n"
        + "            ({6})\n"
         // Capitalized Value Object name
        + "                query(\n"
        + "                    new QueryPreparedStatementCreator(t_Query),\n"
        + "                    new {6}PkStatementSetter({8}),\n"
         // Capitalized Value Object name
        + "                    {9}_RESULT_SET_EXTRACTOR);\n"
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
        + "        result.insertInto({1}.{2});\n"
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
        "\n        result.value({0}.{1}.{2});";
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
        + "        result.update({1}.{2});\n\n"
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
        "        result.set({0}.{1}.{2});\n";
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
        + "        result.deleteFrom({1}.{2});\n\n"
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
     */
    public static final String DEFAULT_CUSTOM_SELECT =
          "    /**\n"
        + "     * Retrieves {0} entities\n"
         // result class
        + "     * from the persistence layer matching given criteria."
        + "{1}\n"
         // CUSTOM_SELECT_PARAMETER_JAVADOC
        + "     * @param transactionToken needed to use an open connection and\n"
        + "     * see previously uncommited inserts/updates/deletes.\n"
        + "     * @return the information extracted from the persistence layer.\n"
        + "     * @throws DataAccessException if the access to the information fails.\n"
        + "     */\n"
        + "    public {0} {2}("
         // result class - sql name
        + "{3}\n"
         // CUSTOM_SELECT_PARAMETER_DECLARATION
        + "        final TransactionToken transactionToken)\n"
        + "      throws DataAccessException\n"
        + "    '{'\n"
        + "        {0} result = null;\n\n"
         // java table name
        + "        Connection        t_Connection        = null;\n"
        + "        PreparedStatement t_PreparedStatement = null;\n"
        + "        ResultSet         t_rsResults         = null;\n\n"
        + "        try\n"
        + "        '{'\n"
        + "            t_Connection = getConnection(transactionToken);\n\n"
        + "            String t_strQuery =\n"
        + "                \"{4}\";\n\n"
        + "            t_PreparedStatement = t_Connection.prepareStatement(t_strQuery);\n"
        + "{5}\n\n"
         // CUSTOM_SELECT_PARAMETER_VALUES
        + "            t_rsResults = t_PreparedStatement.executeQuery();\n\n"
        + "            if  (   (t_rsResults != null)\n"
        + "                 && (t_rsResults.next()))\n"
        + "            '{'\n"
        + "                {0}Factory t_Factory =\n"
        + "                    {0}Factory.getInstance();\n\n"
        + "                result =\n"
        + "                    t_Factory.create{6}("
        + "{7});\n"
         // CUSTOM_SELECT_RESULT_PROPERTIES
        + "            '}'\n"
        + "        '}'\n"
        + "        catch  (final SQLException sqlException)\n"
        + "        '{'\n"
        + "            LogFactory.getLog(getClass()).fatal(sqlException);\n"
        + "        '}'\n"
        + "        catch  (final Exception exception)\n"
        + "        '{'\n"
        + "            LogFactory.getLog(getClass()).error(exception);\n"
        + "        '}'\n"
        + "        finally\n"
        + "        '{'\n"
        + "            try\n"
        + "            '{'\n"
        + "                if  (t_rsResults != null)\n"
        + "                '{'\n"
        + "                    t_rsResults.close();\n"
        + "                '}'\n"
        + "            '}'\n"
        + "            catch  (final Exception exception)\n"
        + "            '{'\n"
        + "                LogFactory.getLog(getClass()).error(exception);\n"
        + "            '}'\n"
        + "            try\n"
        + "            '{'\n"
        + "                if  (t_PreparedStatement != null)\n"
        + "                '{'\n"
        + "                    t_PreparedStatement.close();\n"
        + "                '}'\n"
        + "            '}'\n"
        + "            catch  (final Exception exception)\n"
        + "            '{'\n"
        + "                LogFactory.getLog(getClass()).error(exception);\n"
        + "            '}'\n"
        + "            try\n"
        + "            '{'\n"
        + "                if  (t_Connection != null)\n"
        + "                '{'\n"
        + "                    closeConnection(t_Connection, transactionToken);\n"
        + "                '}'\n"
        + "            '}'\n"
        + "            catch  (final Exception exception)\n"
        + "            '{'\n"
        + "                LogFactory.getLog(getClass()).error(exception);\n"
        + "            '}'\n"
        + "        '}'\n\n"
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
        "\n        final {0} {1},";
         // parameter type - parameter name

    /**
     * The custom select parameter values.
     */
    public static final String DEFAULT_CUSTOM_SELECT_PARAMETER_VALUES =
        "\n            t_PreparedStatement.set{0}({1}, {2});";

    /**
     * The custom select result properties.
     */
    public static final String DEFAULT_CUSTOM_SELECT_RESULT_PROPERTIES =
        "\n                        t_rsResults.get{0}({1})";

    /**
     * The custom update or insert template.
     */
    public static final String DEFAULT_CUSTOM_UPDATE_OR_INSERT =
          "    /**\n"
        + "     * Performs the <i>{0}</i> operation\n"
         // query name
        + "     * in the persistence layer with the provided data."
        + "{1}\n"
         // CUSTOM_SELECT_PARAMETER_JAVADOC
        + "     * @param transactionToken needed to use an open connection and\n"
        + "     * see previously uncommited inserts/updates/deletes.\n"
        + "     * @return the information extracted from the persistence layer.\n"
        + "     * @throws DataAccessException if the access to the information fails.\n"
        + "     */\n"
        + "    public void {2}("
         // sql name
        + "{3}\n"
         // CUSTOM_UPDATE_OR_INSERT_PARAMETER_DECLARATION
        + "        final TransactionToken transactionToken)\n"
        + "      throws DataAccessException\n"
        + "    '{'\n"
         // java table name
        + "        Connection        t_Connection        = null;\n"
        + "        PreparedStatement t_PreparedStatement = null;\n\n"
        + "        try\n"
        + "        '{'\n"
        + "            t_Connection = getConnection(transactionToken);\n\n"
        + "            StringBuffer t_sbQuery = new StringBuffer();\n\n"
        + "{4}\n"
        + "            t_PreparedStatement =\n"
        + "                t_Connection.prepareStatement(t_sbQuery.toString());\n"
        + "{5}\n\n"
         // CUSTOM_UPDATE_OR_INSERT_PARAMETER_VALUES
        + "            t_PreparedStatement.executeUpdate();\n\n"
        + "        '}'\n"
        + "        catch  (final SQLException sqlException)\n"
        + "        '{'\n"
        + "            LogFactory.getLog(getClass()).fatal(sqlException);\n"
        + "        '}'\n"
        + "        catch  (final Exception exception)\n"
        + "        '{'\n"
        + "            LogFactory.getLog(getClass()).error(exception);\n"
        + "        '}'\n"
        + "        finally\n"
        + "        '{'\n"
        + "            try\n"
        + "            '{'\n"
        + "                if  (t_PreparedStatement != null)\n"
        + "                '{'\n"
        + "                    t_PreparedStatement.close();\n"
        + "                '}'\n"
        + "            '}'\n"
        + "            catch  (final Exception exception)\n"
        + "            '{'\n"
        + "                LogFactory.getLog(getClass()).error(exception);\n"
        + "            '}'\n"
        + "            try\n"
        + "            '{'\n"
        + "                if  (t_Connection != null)\n"
        + "                '{'\n"
        + "                    closeConnection(t_Connection, transactionToken);\n"
        + "                '}'\n"
        + "            '}'\n"
        + "            catch  (final Exception exception)\n"
        + "            '{'\n"
        + "                LogFactory.getLog(getClass()).error(exception);\n"
        + "            '}'\n"
        + "        '}'\n\n"
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
        "\n        final {0} {1},";
         // parameter type - parameter name

    /**
     * The custom update or insert parameter values.
     */
    public static final String DEFAULT_CUSTOM_UPDATE_OR_INSERT_PARAMETER_VALUES =
        "\n            t_PreparedStatement.set{0}({1}, {2});";

    /**
     * The custom update or insert query line.
     */
    public static final String DEFAULT_CUSTOM_UPDATE_OR_INSERT_QUERY_LINE =
        "            t_sbQuery.append({0}\"{1} \");";

    /**
     * The custom select for update template, with return.
     */
    public static final String DEFAULT_CUSTOM_SELECT_FOR_UPDATE_WITH_RETURN =
          "    /**\n"
        + "     * Performs the <i>{0}</i> operation.\n"
         // sql id
        + "{1}\n"
         // CUSTOM_SELECT_PARAMETER_JAVADOC
        + "     * @param transactionToken needed to use an open connection and\n"
        + "     * see previously uncommited inserts/updates/deletes.\n"
        + "     * @return the information extracted from the persistence layer\n"
        + "     * and/or processed.\n"
        + "     * @throws DataAccessException if the access to the information fails.\n"
        + "     */\n"
        + "    public {0} {2}("
         // result class - sql name
        + "{3}\n"
         // CUSTOM_SELECT_PARAMETER_DECLARATION
        + "        final TransactionToken transactionToken)\n"
        + "      throws DataAccessException\n"
        + "    '{'\n"
        + "        {0} result = null;\n\n"
         // java table name
        + "        Connection        t_Connection        = null;\n"
        + "        PreparedStatement t_PreparedStatement = null;\n"
        + "        ResultSet         t_rsResults         = null;\n\n"
        + "        try\n"
        + "        '{'\n"
        + "            t_Connection = getConnection(transactionToken);\n\n"
        + "            String t_strQuery =\n"
        + "                \"{4}\";\n\n"
        + "            t_PreparedStatement = t_Connection.prepareStatement(t_strQuery);\n"
        + "{5}\n\n"
         // CUSTOM_SELECT_PARAMETER_VALUES
        + "            t_rsResults = t_PreparedStatement.executeQuery();\n\n"
        + "            if  (   (t_rsResults != null)\n"
        + "                 && (t_rsResults.next()))\n"
        + "            '{'\n"
        + "                {0}Factory t_Factory =\n"
        + "                    {0}Factory.getInstance();\n\n"
        + "                result =\n"
        + "                    t_Factory.create{6}("
        + "{7});\n"
         // CUSTOM_SELECT_RESULT_PROPERTIES
        + "            '}'\n"
        + "        '}'\n"
        + "        catch  (final SQLException sqlException)\n"
        + "        '{'\n"
        + "            LogFactory.getLog(getClass()).fatal(sqlException);\n"
        + "        '}'\n"
        + "        catch  (final Exception exception)\n"
        + "        '{'\n"
        + "            LogFactory.getLog(getClass()).error(exception);\n"
        + "        '}'\n"
        + "        finally\n"
        + "        '{'\n"
        + "            try\n"
        + "            '{'\n"
        + "                if  (t_rsResults != null)\n"
        + "                '{'\n"
        + "                    t_rsResults.close();\n"
        + "                '}'\n"
        + "            '}'\n"
        + "            catch  (final Exception exception)\n"
        + "            '{'\n"
        + "                LogFactory.getLog(getClass()).error(exception);\n"
        + "            '}'\n"
        + "            try\n"
        + "            '{'\n"
        + "                if  (t_PreparedStatement != null)\n"
        + "                '{'\n"
        + "                    t_PreparedStatement.close();\n"
        + "                '}'\n"
        + "            '}'\n"
        + "            catch  (final Exception exception)\n"
        + "            '{'\n"
        + "                LogFactory.getLog(getClass()).error(exception);\n"
        + "            '}'\n"
        + "            try\n"
        + "            '{'\n"
        + "                if  (t_Connection != null)\n"
        + "                '{'\n"
        + "                    closeConnection(t_Connection, transactionToken);\n"
        + "                '}'\n"
        + "            '}'\n"
        + "            catch  (final Exception exception)\n"
        + "            '{'\n"
        + "                LogFactory.getLog(getClass()).error(exception);\n"
        + "            '}'\n"
        + "        '}'\n\n"
        + "        return result;\n"
        + "    '}'\n\n";

    /**
     * The custom select for update template.
     */
    public static final String DEFAULT_CUSTOM_SELECT_FOR_UPDATE =
        DEFAULT_CUSTOM_SELECT_FOR_UPDATE_WITH_RETURN;

    /**
     * The custom select-for-update parameter javadoc.
     */
    public static final String DEFAULT_CUSTOM_SELECT_FOR_UPDATE_PARAMETER_JAVADOC =
        DEFAULT_CUSTOM_SELECT_PARAMETER_JAVADOC;

    /**
     * The custom select-for-update parameter declaration.
     */
    public static final String DEFAULT_CUSTOM_SELECT_FOR_UPDATE_PARAMETER_DECLARATION =
        DEFAULT_CUSTOM_SELECT_PARAMETER_DECLARATION;

    /**
     * The custom select-for-update parameter values.
     */
    public static final String DEFAULT_CUSTOM_SELECT_FOR_UPDATE_PARAMETER_VALUES =
        DEFAULT_CUSTOM_SELECT_PARAMETER_VALUES;

    /**
     * The custom select result properties.
     */
    public static final String DEFAULT_CUSTOM_SELECT_FOR_UPDATE_RESULT_PROPERTIES =
        DEFAULT_CUSTOM_SELECT_RESULT_PROPERTIES;

    /**
     * The default class end.
     */
    public static final String DEFAULT_CLASS_END = "}\n";
}
