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
    public static final String DEFAULT_PACKAGE_DECLARATION = "package {0};\n\n"; // package

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
        + " * @version $" + "Revision: $\n"
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
        + "    public static final ResultSetExtractor {1}_RESULT_SET_EXTRACTOR =\n"
        + "        new {2}ResultSetExtractor();\n\n"
        + "    // </constants>\n\n";

    /**
     * The class constructor.
     */
    public static final String DEFAULT_CLASS_CONSTRUCTOR =
          "    /**\n"
        + "     * Builds a {0}{1}DAO\n"
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

    /**
     * The find by primary key method.
     */
    public static final String DEFAULT_FIND_BY_PRIMARY_KEY_METHOD =
          "     //<find by primary key>\n\n"
        + "    /**\n"
        + "     * Builds the query for <i>FindByPrimaryKey</i>.\n"
        + "     * @return the <code>SelectQuery</code> instance.\n"
        + "     */\n"
        + "    protected Query buildFindByPrimaryKeyQuery()\n"
        + "    '{'\n"
        + "        return buildFindByPrimaryKeyQuery(QueryFactory.getInstance());\n"
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Creates the query for <i>FindByPrimaryKey</i>.\n"
        + "     * @param queryFactory the <code>QueryFactory</code> instance.\n"
        + "     * @return the <code>SelectQuery</code> instance.\n"
        + "     * @precondition queryFactory != null\n"
        + "     */\n"
        + "    protected Query buildFindByPrimaryKeyQuery(final QueryFactory queryFactory)\n"
        + "    '{'\n"
        + "        SelectQuery result = queryFactory.createSelectQuery();\n\n"
        + "        result.select({0}.{1}.getAll());\n\n"
         // table repository name - table instance name
        + "        result.from({0}.{1});\n\n"
         // table repository name - table instance name
        + "{2}"
         // FIND_BY_PRIMARY_KEY_PK_FILTER
        + "        return result;\n"
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Retrieves <code>{3}</code> information filtering by its primary key."
        + "{4}\n"
         // FIND_BY_PRIMARY_KEY_PK_JAVADOC
        + "     */\n"
        + "    public {5} findByPrimaryKey("
        + "{6})\n"
        + "    '{'\n"
        + "        Query t_Query = buildFindByPrimaryKeyQuery();\n\n"
        + "        return\n"
        + "            (User)\n"
        + "                query(\n"
        + "                    new QueryPreparedStatementCreator(t_Query),\n"
        + "                    new UserPkStatementSetter({7}),\n"
        + "                    {8}_RESULT_SET_EXTRACTOR);\n"
        + "    '}'\n\n";

    /**
     * The find-by-primary-key method's primary keys javadoc.
     */
    public static final String DEFAULT_FIND_BY_PRIMARY_KEY_PK_JAVADOC =
        "\n     * @param {0} the <i>{1}</i> value to filter.";
         // java pk - pk

    /**
     * The find-by-primary-key method's primary keys declaration.
     */
    public static final String DEFAULT_FIND_BY_PRIMARY_KEY_PK_DECLARATION =
        "\n        final {0} {1}";
         // pk type - java pk

    /**
     * The primary keys filter subtemplate.
     */
    public static final String DEFAULT_PK_FILTER =
        "        result.where({0}.{1}.{2}.equals());\n\n";
         // table repository name - table instance name - primary key

    /**
     * The find-by-primary-key statement setter call.
     */
    public static final String DEFAULT_PK_STATEMENT_SETTER_CALL =
        "\n                        {0}";
        // java pk

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
        + "     * @param transactionToken the transaction boundary.\n"
        + "     * @throws DataAccessException if the access to the information fails.\n"
        + "     */\n"
        + "    public void insert("
        + "{3}\n"
         // insert parameters declaration
        + "        final TransactionToken transactionToken)\n"
        + "      throws DataAccessException\n"
        + "    '{'\n"
        + "        Connection        t_Connection        = null;\n"
        + "        PreparedStatement t_PreparedStatement = null;\n\n"
        + "        try\n"
        + "        '{'\n"
        + "            t_Connection = getConnection(transactionToken);\n\n"
        + "            QueryFactory t_QueryFactory = QueryFactory.getInstance();\n\n"
        + "            {4}KeywordRepository t_KeywordRepository =\n"
        + "                {4}KeywordRepository.getInstance();\n\n"
        + "            if  (   (t_Connection        != null)\n"
        + "                 && (t_QueryFactory      != null)\n"
        + "                 && (t_KeywordRepository != null))\n"
        + "            '{'\n"
        + "                InsertQuery t_Query = t_QueryFactory.createInsertQuery();\n\n"
        + "                t_Query.insertInto({4}.{5});\n\n"
         // table repository name - table name
        + "{6}\n"
         // insert parameters specification
        + "                t_PreparedStatement = t_Query.prepareStatement(t_Connection);\n"
        + "                t_PreparedStatement.executeUpdate();\n"
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
        + "                if  (t_PreparedStatement != null)\n"
        + "                '{'\n"
        + "                    t_PreparedStatement.close();\n"
        + "                '}'\n"
        + "                if  (t_Connection != null)\n"
        + "                '{'\n"
        + "                    closeConnection(t_Connection, transactionToken);\n"
        + "                '}'\n"
        + "            '}'\n"
        + "            catch  (final Exception exception)\n"
        + "            '{'\n"
        + "                LogFactory.getLog(getClass()).error(exception);\n"
        + "            '}'\n"
        + "        '}'\n"
        + "    '}'\n";

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
     * The insert parameters specification.
     */
    public static final String DEFAULT_INSERT_PARAMETERS_SPECIFICATION =
        "                t_Query.value({0}.{1}.{2}, {3});\n";
    // table repository name - table name - field name - field value

    /**
     * The insert keyword-based parameters specification.
     */
    public static final String DEFAULT_INSERT_KEYWORD_PARAMETERS_SPECIFICATION =
          "                t_Query.value(\n"
        + "                    {0}.{1}.{2},\n"
        + "                    t_KeywordRepository.{3}());\n";
    // table repository name - table name - field name - field value

    /**
     * The update method.
     */
    public static final String DEFAULT_UPDATE_METHOD =
          "    /**\n"
        + "     * Updates {0} information\n"
        + "     * in the persistence layer."
         // table name
        + "{1}"
         // (optional) pk javadoc
        + "{2}\n"
         // update parameters javadoc
        + "     * @param transactionToken the transaction boundary.\n"
        + "     * @throws DataAccessException if the access to the information fails.\n"
        + "     */\n"
        + "    public void update("
        + "{3}"
         // (optional) pk declaration
        + "{4}"
         // update parameters declaration
        + "\n        final TransactionToken transactionToken)\n"
        + "      throws DataAccessException\n"
        + "    '{'\n"
        + "        Connection        t_Connection        = null;\n"
        + "        PreparedStatement t_PreparedStatement = null;\n\n"
        + "        try\n"
        + "        '{'\n"
        + "            t_Connection = getConnection(transactionToken);\n\n"
        + "            QueryFactory t_QueryFactory = QueryFactory.getInstance();\n\n"
        + "            if  (   (t_Connection   != null)\n"
        + "                 && (t_QueryFactory != null))\n"
        + "            '{'\n"
        + "                UpdateQuery t_Query = t_QueryFactory.createUpdateQuery();\n\n"
        + "                t_Query.update({5}.{6});\n\n"
         // table repository name - table name
        + "{7}"
         // update parameters specification
        + "{8}"
         // pk values specificacion
        + "                t_PreparedStatement = t_Query.prepareStatement(t_Connection);\n"
        + "                t_PreparedStatement.executeUpdate();\n"
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
        + "                if  (t_PreparedStatement != null)\n"
        + "                '{'\n"
        + "                    t_PreparedStatement.close();\n"
        + "                '}'\n"
        + "                if  (t_Connection != null)\n"
        + "                '{'\n"
        + "                    closeConnection(t_Connection, transactionToken);\n"
        + "                '}'\n"
        + "            '}'\n"
        + "            catch  (final Exception exception)\n"
        + "            '{'\n"
        + "                LogFactory.getLog(getClass()).error(exception);\n"
        + "            '}'\n"
        + "        '}'\n"
        + "    '}'\n";

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
     * The update parameters specification.
     */
    public static final String DEFAULT_UPDATE_PARAMETERS_SPECIFICATION =
        "                t_Query.set({0}.{1}.{2}, {3});\n";
    // table repository name - table name - field name - field value

    /**
     * The update filter.
     */
    public static final String DEFAULT_UPDATE_FILTER =
        "                t_Query.where({0}.{1}.{2}.equals({3}));\n";
    // table repository name - table name - field name - field value

    /**
     * The delete method.
     */
    public static final String DEFAULT_DELETE_METHOD =
          "    /**\n"
        + "     * Deletes {0} information from the persistence layer filtering\n"
         // table name
        + "     * by its primary keys."
        + "{1}\n"
         // DELETE_PK_JAVADOC
        + "     * @param transactionToken needed to use an open connection and\n"
        + "     * see previously uncommited inserts/updates/deletes.\n"
        + "     * @return <code>true</code> if the information has been deleted\n"
        + "     * successfully.\n"
        + "     * @throws DataAccessException if the access to the information fails.\n"
        + "     */\n"
        + "    {7} boolean delete{8}("
         // java table name
        + "{2}"
         // DELETE_PK_DECLARATION
        + "\n        final TransactionToken transactionToken)\n"
        + "      throws DataAccessException\n"
        + "    '{'\n"
        + "        return\n"
        + "            delete{8}("
        + "{9}\n"
         // DELETE_PK_VALUES
        + "                transactionToken,\n"
        + "                QueryFactory.getInstance());\n"
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Deletes {0} information from the persistence layer filtering\n"
         // table name
        + "     * by its primary keys."
        + "{1}\n"
         // DELETE_PK_JAVADOC
        + "     * @param transactionToken needed to use an open connection and\n"
        + "     * see previously uncommited inserts/updates/deletes.\n"
        + "     * @param queryFactory the QueryFactory instance.\n"
        + "     * @return <code>true</code> if the information has been deleted\n"
        + "     * successfully.\n"
        + "     * @throws DataAccessException if the access to the information fails.\n"
        + "     * @precondition queryFactory != null\n"
        + "     */\n"
        + "    {7} boolean delete{8}("
         // java table name
        + "{2}\n"
         // DELETE_PK_DECLARATION
        + "        final TransactionToken transactionToken,\n"
        + "        final QueryFactory queryFactory)\n"
        + "      throws DataAccessException\n"
        + "    '{'\n"
        + "        return\n"
        + "            delete{8}("
        + "{9}\n"
         // DELETE_PK_VALUES
        + "                transactionToken,\n"
        + "                queryFactory.createDeleteQuery());\n"
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Deletes {0} information from the persistence layer filtering\n"
         // table name
        + "     * by its primary keys."
        + "{1}\n"
         // DELETE_PK_JAVADOC
        + "     * @param transactionToken needed to use an open connection and\n"
        + "     * see previously uncommited inserts/updates/deletes.\n"
        + "     * @param query the delete query.\n"
        + "     * @return <code>true</code> if the information has been deleted\n"
        + "     * successfully.\n"
        + "     * @throws DataAccessException if the access to the information fails.\n"
        + "     * @precondition query != null\n"
        + "     */\n"
        + "    {7} boolean delete{8}("
         // java table name
        + "{2}\n"
         // DELETE_PK_DECLARATION
        + "        final TransactionToken transactionToken,\n"
        + "        final DeleteQuery query)\n"
        + "      throws DataAccessException\n"
        + "    '{'\n"
        + "        boolean result = false;\n\n"
        + "        Connection        t_Connection        = null;\n"
        + "        PreparedStatement t_PreparedStatement = null;\n\n"
        + "        try\n"
        + "        '{'\n"
        + "            t_Connection = getConnection(transactionToken);\n\n"
        + "            if  (t_Connection != null)\n"
        + "            '{'\n"
        + "                query.deleteFrom({3}.{4});\n\n"
         // table repository name - table name
        + "{5}"

         // DELETE_PK_FILTER_DECLARATION
        + "                t_PreparedStatement = query.prepareStatement(t_Connection);\n\n"
        + "{6}"
         // DELETE_PK_FILTER_VALUES
        + "                t_PreparedStatement = query.prepareStatement(t_Connection);\n"
        + "                t_PreparedStatement.executeUpdate();\n\n"
        + "                result = true;\n"
        + "            '}'\n"
        + "        '}'\n"
        + "        catch   (SQLException sqlException)\n"
        + "        '{'\n"
        + "            LogFactory.getLog(getClass()).fatal(sqlException);\n"
        + "        '}'\n"
        + "        catch   (Exception exception)\n"
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
        + "                if  (t_Connection != null)\n"
        + "                '{'\n"
        + "                    closeConnection(t_Connection, transactionToken);\n"
        + "                '}'\n"
        + "            '}'\n"
        + "            catch  (Exception exception)\n"
        + "            '{'\n"
        + "                LogFactory.getLog(getClass()).error(exception);\n"
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
        "        final {0}              {1},\n";
         // pk type - java pk

    /**
     * The delete method's primary keys declaration.
     */
    public static final String DEFAULT_DELETE_PK_VALUES =
        "\n                {0},";

    /**
     * The delete method's filter declaration.
     */
    public static final String DEFAULT_DELETE_FILTER_DECLARATION =
        "                t_Query.where({0}.{1}.{2}.equals());\n\n";
         // table repository name - table name - pk field name

    /**
     * The delete method's filter values.
     */
    public static final String DEFAULT_DELETE_FILTER_VALUES =
          "                t_Query.set{0}(\n"
         // pk field type
        + "                    {1}.{2}.{3}.equals(),\n"
         // table repository name - table name - pk field name
        + "                    {4});\n\n";
         // java pk

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
        + "     * @param transactionToken needed to use an open connection and\n"
        + "     * see previously uncommited inserts/updates/deletes.\n"
        + "     * @return <code>true</code> if the information has been deleted\n"
        + "     * successfully.\n"
        + "     * @throws DataAccessException if the access to the information fails.\n"
        + "     */\n"
        + "    public boolean delete("
        + "{2}"
         // DELETE_PK_DECLARATION
        + "\n        final TransactionToken transactionToken)\n"
        + "      throws DataAccessException\n"
        + "    '{'\n"
        + "        return\n"
        + "            delete(\n"
        + "                findByPrimaryKey("
        + "{3}"
        + "\n                    transactionToken),\n"
        + "                transactionToken);\n"
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Deletes given {0} information from the persistence layer.\n"
         // table name
        + "     * @param {4} the information to delete.\n"
        + "     * @param transactionToken needed to use an open connection and\n"
        + "     * see previously uncommited inserts/updates/deletes.\n"
        + "     * @return <code>true</code> if the information has been deleted\n"
        + "     * successfully.\n"
        + "     * @precondition {4} != null\n"
        + "     */\n"
        + "    public boolean delete(\n"
        + "        final {0}ValueObject {4},\n"
        + "        final TransactionToken  transactionToken)\n"
        + "    '{'\n"
        + "        boolean result = false;\n\n"
        + "        TransactionToken  t_DeleteTransactionToken = transactionToken;\n"
        + "        boolean           t_bInnerTransaction      = false;\n\n"
        + "        if  (t_DeleteTransactionToken == null)\n"
        + "        '{'\n"
        + "            t_DeleteTransactionToken = createTransactionToken();\n"
        + "        '}'\n\n"
        + "        if  (   (t_DeleteTransactionToken != null)\n"
        + "             && (!t_DeleteTransactionToken.isTransactionAlive()))\n"
        + "        '{'\n"
        + "            t_DeleteTransactionToken.beginTransaction();\n"
        + "            t_bInnerTransaction = true;\n"
        + "        '}'\n\n"
        + "        try\n"
        + "        '{'\n"
        + "            DataAccessManager t_DataAccessManager = DataAccessManager.getInstance();\n"
        + "            if  (t_DataAccessManager != null)\n"
        + "            '{'\n"
        + "                result = true;\n\n"
        + "{5}\n"
        + "                if  (result)\n"
        + "                '{'\n"
        + "                    result =\n"
        + "                        delete{0}(\n"
        + "                            {4}.get{6}(),\n"
        + "                            t_DeleteTransactionToken);\n"
        + "                '}'\n"
        + "            '}'\n"
        + "        '}'\n"
        + "        catch  (final Exception exception)\n"
        + "        '{'\n"
        + "            LogFactory.getLog(getClass()).error(exception);\n"
        + "            result = false;\n"
        + "            if  (t_DeleteTransactionToken != null)\n"
        + "            '{'\n"
        + "                t_DeleteTransactionToken.setRollbackPending(true);\n"
        + "            '}'\n"
        + "        '}'\n"
        + "        finally\n"
        + "        '{'\n"
        + "            try\n"
        + "            '{'\n"
        + "                if  (   (t_bInnerTransaction)\n"
        + "                     && (t_DeleteTransactionToken != null))\n"
        + "                '{'\n"
        + "                    if  (   (t_DeleteTransactionToken.isRollbackPending())\n"
        + "                         || (!result))\n"
        + "                    '{'\n"
        + "                        rollback(t_DeleteTransactionToken);\n"
        + "                    '}'\n"
        + "                    else\n"
        + "                    '{'\n"
        + "                        commit(t_DeleteTransactionToken);\n"
        + "                    '}'\n"
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
     * The delete with fk method's primary keys javadoc.
     */
    public static final String DEFAULT_DELETE_WITH_FK_PK_JAVADOC =
        "\n     * @param {0} the {1} value to filter.";
         // java pk - pk

    /**
     * The delete with fk method's primary keys declaration.
     */
    public static final String DEFAULT_DELETE_WITH_FK_PK_DECLARATION =
        "        final {0}              {1},\n";
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
     * The custom select template.
     */
    public static final String DEFAULT_CUSTOM_SELECT =
          "    /**\n"
        + "     * Retrieves {0} entities\n"
         // result class
        + "     * from the persistence layer matching given criteria.\n"
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
