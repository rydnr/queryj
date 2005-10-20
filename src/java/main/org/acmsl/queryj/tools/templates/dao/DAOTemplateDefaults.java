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
 * Filename: $RCSfile$
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Defines the default subtemplates to generate DAO sources.
 */
package org.acmsl.queryj.tools.templates.dao;

/*
 * Importing some project-specific classes.
 */
import org.acmsl.queryj.tools.templates.JavaTemplateDefaults;

/**
 * Defines the default subtemplates to generate DAO sources.
 * @author <a href="mailto:chous@acm-sl.org"
 * >Jose San Leandro</a>
 * @version $Revision$ at $Date$ by $Author$
 */
public interface DAOTemplateDefaults
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
        + " * Description: DAO layer responsible of retrieving\n"
        + "                \"{2}\" structures from "
         // Table name
        + "{0} {1} persistence layers.\n"
         // engine name - driver version
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
     * @param 9 the foreign key statement setter imports.
     * @param 10 the foreign DAO imports.
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
        + "import {3}.{1}ValueObjectFactory;\n"
         // ValueObject package - table name
        + "import {4}.{1}DAO;\n"
         // DAO interface package - table name
        + "import {5}.QueryPreparedStatementCreator;\n"
         // RDB base package - table name
        + "import {6}.{7}TableRepository;\n"
         // Table repository package - Table repository name
        + "import {8}.DataAccessManager;\n"
         // data access manager package
        + "{9}"
         // foreign key statement setter imports.
        + "{10}";
         // foreign DAO imports

    /**
     * The custom result set extractors.
     */
    public static final String DEFAULT_CUSTOM_RESULT_SET_EXTRACTOR_IMPORT =
        "import {0}.{1}Extractor;\n";
         // Specific JDBC operations package - custom result id

    /**
     * The foreign key statement setter imports.
     * @param 0 statement setter package.
     * @param 1 capitalized value object name.
     * @param 2 capitalized referred table name.
     */
    public static final String
        DEFAULT_FOREIGN_KEY_STATEMENT_SETTER_IMPORT =
            "import {0}.{1}By{2}StatementSetter;\n";

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
        + "import org.acmsl.queryj.dao.QueryjJdbcTemplate;\n"
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
        + "import org.springframework.jdbc.core.PreparedStatementCreator;\n"
        + "import org.springframework.jdbc.core.PreparedStatementCreatorFactory;\n"
        + "import org.springframework.jdbc.core.PreparedStatementSetter;\n"
        + "import org.springframework.jdbc.core.ResultSetExtractor;\n"
        + "import org.springframework.jdbc.core.SqlParameter;\n"
        + "import org.springframework.jdbc.datasource.DataSourceTransactionManager;\n"
        + "import org.springframework.jdbc.object.SqlQuery;\n"
        + "import org.springframework.transaction.support.TransactionCallback;\n"
        + "import org.springframework.transaction.support.TransactionTemplate;\n"
        + "import org.springframework.transaction.TransactionException;\n"
        + "import org.springframework.transaction.TransactionStatus;\n\n";
    
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
        + "import java.sql.Types;\n"
        + "import java.util.Calendar;\n"
        + "import java.util.Date;\n"
        + "import java.util.HashMap;\n"
        + "import java.util.List;\n"
        + "import java.util.Map;\n\n";

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
        + "    extends     QueryjJdbcTemplate\n"
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
        + "    /**\n"
        + "     * The result set extractor for <i>{0}</i>\n"
        + "     * primary keys (see inner class).\n"
        + "     */\n"
        + "    public static final ResultSetExtractor "
        + "{1}_PK_EXTRACTOR =\n"
        + "        new {2}PkResultSetExtractor();\n\n"
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
        + "        // Enabling transactions temporarily.\n"
        + "        DataSourceTransactionManager t_TransactionManager =\n"
        + "            new DataSourceTransactionManager(getDataSource());\n\n"
        + "        TransactionTemplate t_TransactionTemplate =\n"
        + "            new TransactionTemplate(t_TransactionManager);\n\n"
        + "        return\n"
        + "            ({6}ValueObject)\n"
         // Capitalized Value Object name
        + "                t_TransactionTemplate.execute(\n"
        + "                    new QueryTransactionCallback(\n"
        + "                        new QueryPreparedStatementCreator(t_Query),\n"
        + "                        new {6}PkStatementSetter({8}),\n"
         // Capitalized Value Object name
        + "                        {9}_EXTRACTOR,\n"
         // Upper case table name
        + "                        this));\n"
        + "        /*\n"
        + "                query(\n"
        + "                    new QueryPreparedStatementCreator(t_Query),\n"
        + "                    new {6}PkStatementSetter({8}),\n"
         // Capitalized Value Object name
        + "                    {9}_EXTRACTOR);\n"
         // Upper case table name
        + "        */\n"
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
        + "{3}\n"
        + "{7}\n"
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
        + "        // Enabling transactions temporarily.\n"
        + "        DataSourceTransactionManager t_TransactionManager =\n"
        + "            new DataSourceTransactionManager(getDataSource());\n\n"
        + "        TransactionTemplate t_TransactionTemplate =\n"
        + "            new TransactionTemplate(t_TransactionManager);\n\n"
        + "        t_TransactionTemplate.execute(\n"
        + "            new UpdateTransactionCallback(\n"
        + "                new QueryPreparedStatementCreator(t_Query),\n"
        + "                new {0}AttributesStatementSetter({6},\n"
        + "                    false),\n"
        + "                this));\n"
        + "        /*\n"
        + "        update(\n"
        + "            new QueryPreparedStatementCreator(t_Query),\n"
        + "            new {0}AttributesStatementSetter({6},\n"
        + "                false));\n\n"
        + "        */\n\n"
        + "    '}'\n\n"
        + "    // </insert>\n\n";

    /**
     * The insert parameters specification.
     * @param 0 the repository name.
     * @param 1 the table name.
     * @param 2 the field name.
     */
    public static final String DEFAULT_INSERT_PARAMETERS_SPECIFICATION =
        "\n        result.value({0}TableRepository.{1}.{2});";

    /**
     * The externally-managed  insert fields specification.
     * @param 0 the repository name.
     * @param 1 the capitalized table name.
     * @param 2 the field name.
     * @param 3 the keyword.
     */
    public static final String
        DEFAULT_EXTERNALLY_MANAGED_INSERT_PARAMETERS_SPECIFICATION =
            "\n        result.value({0}TableRepository.{1}.{2}, \"{3}\", false);";

    /**
     * The create method.
     * @param 0 the capitalized value object name.
     * @param 1 the table repository name.
     * @param 2 the table name.
     * @param 3 the create parameters specification.
     * @param 4 the attributes Javadoc.
     * @param 5 the attributes declaration.
     * @param 6 the statement setter call.
     * @param 7 the attributes passing.
     * @param 8 the pk queries.
     * @param 9 the attributes specification.
     * @param 10 the externally-managed field's value retrieval.
     */
    public static final String DEFAULT_CREATE_METHOD =
          "    // <create>\n\n"
        + "    /**\n"
        + "     * Builds the query required to <i>create</i> a concrete\n"
        + "     * <code>{0}ValueObject</code> instance.\n"
        + "     * @return the <code>InsertQuery</code> instance.\n"
        + "     */\n"
        + "    protected Query buildCreateQuery()\n"
        + "    '{'\n"
        + "        return buildCreateQuery(QueryFactory.getInstance());\n"
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Builds the query required to <i>create</i> a concrete\n"
        + "     * <code>{0}</code> instance.\n"
        + "     * @param queryFactory the <code>QueryFactory</code> "
        + "instance.\n"
        + "     * @return the <code>InsertQuery</code> instance.\n"
        + "     * @precondition queryFactory != null\n"
        + "     */\n"
        + "    protected Query buildCreateQuery("
        +          "final QueryFactory queryFactory)\n"
        + "    '{'\n"
        + "        InsertQuery result = queryFactory.createInsertQuery();\n\n"
        + "        result.insertInto({1}TableRepository.{2});\n"
        + "{3}\n"
        + "        return result;\n"
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Inserts and retrieves the newly-inserted\n"
        + "     * <code>{0}</code> instance."
         // table name
        + "{4}\n"
         // attributes javadoc
        + "     * @throws DataAccessException if the operation fails.\n"
        + "     */\n"
        + "    public {0}ValueObject create("
        + "{5})\n"
         // attributes declaration
        + "      throws DataAccessException\n"
        + "    '{'\n"
        + "        return\n"
        + "            create({7}\n"
        + "                getDataSource(),\n"
        + "                {0}ValueObjectFactory.getInstance());\n"
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Inserts and retrieves the newly-inserted\n"
        + "     * <code>{0}</code> instance."
         // table name
        + "{4}\n"
         // attributes javadoc
        + "     * @param dataSource the data source.\n"
        + "     * @param factory the <code>{0}ValueObjectFactory</code> instance.\n"
        + "     * @throws DataAccessException if the operation fails.\n"
        + "     */\n"
        + "    public {0}ValueObject create("
        + "{5},\n"
        + "        final DataSource dataSource,\n"
        + "        final {0}ValueObjectFactory factory)\n"
         // attributes declaration
        + "      throws DataAccessException\n"
        + "    '{'\n"
        + "        {0}ValueObject result = null;\n\n"
        + "        // Enabling transactions temporarily.\n"
        + "        DataSourceTransactionManager t_TransactionManager =\n"
        + "            new DataSourceTransactionManager(dataSource);\n\n"
        + "        TransactionTemplate t_TransactionTemplate =\n"
        + "            new TransactionTemplate(t_TransactionManager);\n\n"
        + "        Map t_mMap =\n"
        + "            (Map)\n"
        + "                t_TransactionTemplate.execute(\n"
        + "                    new QueryTransactionCallback(\n"
        + "                        \"{8}\",\n"
        + "                        {11}_PK_EXTRACTOR,\n"
         // Upper case table name
        + "                        this));\n"
        + "        /*\n"
        + "        Map t_mMap =\n"
        + "            (Map)\n"
        + "                query(\n"
        + "                    \"{8}\",\n"
        + "                    {11}_PK_EXTRACTOR);\n\n"
        + "         */\n"
        + "{10}\n"
        + "        Query t_Query = buildCreateQuery();\n\n"
        + "        t_TransactionTemplate.execute(\n"
        + "            new UpdateTransactionCallback(\n"
        + "                new QueryPreparedStatementCreator(t_Query),\n"
        + "                new {0}AttributesStatementSetter({6},\n"
        + "                    true),\n"
        + "                this));\n"
        + "        /*\n"
        + "        update(\n"
        + "            new QueryPreparedStatementCreator(t_Query),\n"
        + "            new {0}AttributesStatementSetter({6},\n"
        + "                true));\n\n"
        + "        */\n\n"
        + "        result =\n"
        + "            factory.create{0}ValueObject("
        + "{9});\n\n"
        + "        return result;\n"
        + "    '}'\n\n"
        + "    // </create>\n\n";

    /**
     * The attributes passing subtemplate.
     * @param 0 the attribute name (Java version).
     */
    public static final String DEFAULT_ATTRIBUTES_PASSING_SUBTEMPLATE =
        "\n                {0},";

    /**
     * The attributes specification subtemplate.
     * @param 0 the attribute name (Java version).
     */
    public static final String DEFAULT_ATTRIBUTES_SPECIFICATION_SUBTEMPLATE =
        "\n                {0}";

    /**
     * The externally-managed field value retrieval.
     * @param 0 the attribute name (Java version).
     * @param 1 the attribute type.
     */
    public static final String DEFAULT_CREATE_EXTERNALLY_MANAGED_FIELD_VALUE_RETRIEVAL =
          "        {0} {1} =\n"
        + "           (({2}) t_mMap.get(\"{1}\")).{0}Value();\n";

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
        + "        // Enabling transactions temporarily.\n"
        + "        DataSourceTransactionManager t_TransactionManager =\n"
        + "            new DataSourceTransactionManager(getDataSource());\n\n"
        + "        TransactionTemplate t_TransactionTemplate =\n"
        + "            new TransactionTemplate(t_TransactionManager);\n\n"
        + "        t_TransactionTemplate.execute(\n"
        + "            new UpdateTransactionCallback(\n"
        + "                new QueryPreparedStatementCreator(t_Query),\n"
        + "                new {0}AttributesStatementSetter({7},\n"
        + "                    true),\n"
        + "                this));\n"
        + "        /*\n"
        + "        update(\n"
        + "            new QueryPreparedStatementCreator(t_Query),\n"
        + "            new {0}AttributesStatementSetter({7},\n"
        + "                true));\n\n"
        + "        */\n"
        + "    '}'\n\n"
        + "    // </update>\n\n";

    /**
     * The update parameters specification.
     */
    public static final String DEFAULT_UPDATE_PARAMETERS_SPECIFICATION =
        "        result.set({0}TableRepository.{1}.{2});\n";
    // table repository name - table name - field name

    /**
     * The delete method subtemplate.
     * @param 0 the value object name.
     * @param 1 the table repository name.
     * @param 2 the table name
     * @param 3 the pk filter.
     * @param 4 the pk javadoc.
     * @param 5 the pk declaration.
     * @param 6 the pk statement setter call.
     */
    public static final String DEFAULT_DELETE_METHOD_SUBTEMPLATE =
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
        + "        return result;\n"
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Deletes <code>{0}</code> information."
        + "{4}\n"
        + "     */\n"
        + "    protected void deleteNoFk("
        + "{5})\n"
        + "    '{'\n"
        + "        Query t_Query = buildDeleteQuery();\n\n"
        + "        // Enabling transactions temporarily.\n"
        + "        DataSourceTransactionManager t_TransactionManager =\n"
        + "            new DataSourceTransactionManager(getDataSource());\n\n"
        + "        TransactionTemplate t_TransactionTemplate =\n"
        + "            new TransactionTemplate(t_TransactionManager);\n\n"
        + "        t_TransactionTemplate.execute(\n"
        + "            new UpdateTransactionCallback(\n"
        + "                new QueryPreparedStatementCreator(t_Query),\n"
        + "                new {0}PkStatementSetter({6}),\n"
        + "                this));\n"
        + "        /*\n"
        + "        update(\n"
        + "            new QueryPreparedStatementCreator(t_Query),\n"
        + "            new {0}PkStatementSetter({6}));\n"
        + "        */\n"
        + "    '}'\n\n"
        + "    // </delete>\n\n";

    /**
     * The delete method with no fks.
     * @param 0 the value object name.
     * @param 1 the table repository name.
     * @param 2 the table name
     * @param 3 the pk filter.
     * @param 4 the pk javadoc.
     * @param 5 the pk declaration.
     * @param 6 the pk statement setter call.
     */
    public static final String DEFAULT_DELETE_METHOD_NO_FK =
          DEFAULT_DELETE_METHOD_SUBTEMPLATE
        + "    /**\n"
        + "     * Deletes <code>{0}</code> information."
        + "{4}\n"
        + "     */\n"
        + "    public void delete("
        + "{5})\n"
        + "    '{'\n"
        + "        deleteNoFk({6});\n"
        + "    '}'\n\n"
        + "    // </delete>\n\n";

    /**
     * The delete method with fks.
     * @param 0 the value object name.
     * @param 1 the table repository name.
     * @param 2 the table name
     * @param 3 the pk filter.
     * @param 4 the pk javadoc.
     * @param 5 the pk declaration.
     * @param 6 the pk statement setter call.
     * @param 7 the foreign DAO delete call.
     */
    public static final String DEFAULT_DELETE_METHOD_FK =
          DEFAULT_DELETE_METHOD_SUBTEMPLATE
        + "    /**\n"
        + "     * Deletes <code>{0}</code> information."
        + "{4}\n"
        + "     */\n"
        + "    public void delete("
        + "{5})\n"
        + "    '{'\n"
        + "        delete({6}, DataAccessManager.getInstance());\n"
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Deletes <code>{0}</code> information."
        + "{4}\n"
        + "     * @param dataAccessManager the <code>DataAccessManager</code>"
        + " instance.\n"
        + "     * @precondition dataAccessManager != null\n"
        + "     */\n"
        + "    protected void delete("
        + "{5},\n"
        + "        final DataAccessManager dataAccessManager)\n"
        + "    '{'\n"
        + "{7}\n"
        + "        deleteNoFk({6});\n"
        + "    '}'\n\n"
        + "    // </delete>\n\n";

    /**
     * The foreign DAO delete call.
     * @param 0 the foreign DAO name.
     * @param 1 the foreign key.
     */
    public static final String DEFAULT_FOREIGN_DAO_DELETE_CALL =
          "        {0}DAO t_{0}DAO = dataAccessManager.get{0}DAO();\n\n"
        + "        if  (t_{0}DAO != null)\n"
        + "        '{'\n"
        + "            t_{0}DAO.deleteBy{1}({2});\n"
        + "        '}'\n";

    /**
     * The foreign DAO update call.
     * @param 0 the foreign DAO name.
     * @param 1 the foreign key.
     */
    public static final String DEFAULT_FOREIGN_DAO_UPDATE_CALL =
          "        {0}DAO t_{0}DAO = dataAccessManager.get{0}DAO();\n\n"
        + "        if  (t_{0}DAO != null)\n"
        + "        '{'\n"
        + "            t_{0}DAO.discardRelationTo{1}({2});\n"
        + "        '}'\n";

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
        + "     * Builds the query required to <i>delete</i> a concrete\n"
        + "     * <code>{0}</code> entity, determined by a concrete foreign key.\n"
        + "     * @return the <code>DeleteQuery</code> instance.\n"
        + "     */\n"
        + "    protected Query buildDeleteBy{2}Query()\n"
        + "    '{'\n"
        + "        return buildDeleteBy{2}Query(QueryFactory.getInstance());\n"
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Builds the query required to <i>delete</i> a concrete\n"
        + "     * <code>{0}</code> entity, determined by a concrete foreign key.\n"
        + "     * @param queryFactory the <code>QueryFactory</code> "
        + "instance.\n"
        + "     * @return the <code>DeleteQuery</code> instance.\n"
        + "     * @precondition queryFactory != null\n"
        + "     */\n"
        + "    protected Query buildDeleteBy{2}Query("
        +          "final QueryFactory queryFactory)\n"
        + "    '{'\n"
        + "        DeleteQuery result = queryFactory.createDeleteQuery();\n\n"
        + "        result.deleteFrom({6}TableRepository.{7});\n\n"
        + "{4}\n"
        + "        return result;\n"
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Deletes {0} information from the persistence layer filtering\n"
        + "     * by given foreign keys."
        + "{1}\n"
        + "     */\n"
        + "    public void deleteBy{2}("
        + "{3})\n"
        + "    '{'\n"
        + "        Query t_Query = buildDeleteBy{2}Query();\n\n"
        + "        // Enabling transactions temporarily.\n"
        + "        DataSourceTransactionManager t_TransactionManager =\n"
        + "            new DataSourceTransactionManager(getDataSource());\n\n"
        + "        TransactionTemplate t_TransactionTemplate =\n"
        + "            new TransactionTemplate(t_TransactionManager);\n\n"
        + "        t_TransactionTemplate.execute(\n"
        + "            new UpdateTransactionCallback(\n"
        + "                new QueryPreparedStatementCreator(t_Query),\n"
        + "                new {0}By{2}StatementSetter({5}),\n"
        + "                this));\n"
        + "        /*\n"
        + "        update(\n"
        + "            new QueryPreparedStatementCreator(t_Query),\n"
        + "            new {0}By{2}StatementSetter({5}));\n"
        + "        */\n"
        + "    '}'\n\n"
        + "    // </delete>\n\n";

    /**
     * The delete method's primary keys javadoc.
     * @param 0 the fk java name
     * @param 1 the fk name.
     */
    public static final String DEFAULT_DELETE_FK_JAVADOC =
        "\n     * @param {0} the <i>{1}</i> value to filter.";

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
        + "        // Enabling transactions temporarily.\n"
        + "        DataSourceTransactionManager t_TransactionManager =\n"
        + "            new DataSourceTransactionManager(getDataSource());\n\n"
        + "        TransactionTemplate t_TransactionTemplate =\n"
        + "            new TransactionTemplate(t_TransactionManager);\n\n"
        + "        result =\n"
        + "            ({3})\n"
        + "                t_TransactionTemplate.execute(\n"
        + "                    new QueryTransactionCallback(\n"
        + "                    t_PreparedStatementCreatorFactory\n"
        + "                        .newPreparedStatementCreator(t_aParams),\n"
        + "                    null,\n"
        + "                    // calls setXXX twice\n"
        + "                    // t_PreparedStatementCreatorFactory\n"
        + "                    //    .newPreparedStatementSetter(t_aParams),\n"
        + "                    {9}_EXTRACTOR,\n"
        + "                        this));\n"
        + "        /*\n"
        + "        result =\n"
        + "            ({3})\n"
        + "                query(\n"
        + "                    t_PreparedStatementCreatorFactory\n"
        + "                        .newPreparedStatementCreator(t_aParams),\n"
        + "                    null,\n"
        + "                    // calls setXXX twice\n"
        + "                    // t_PreparedStatementCreatorFactory\n"
        + "                    //    .newPreparedStatementSetter(t_aParams),\n"
        + "                    {9}_EXTRACTOR);\n\n"
        + "         */\n"
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
        + "        // Enabling transactions temporarily.\n"
        + "        DataSourceTransactionManager t_TransactionManager =\n"
        + "            new DataSourceTransactionManager(getDataSource());\n\n"
        + "        TransactionTemplate t_TransactionTemplate =\n"
        + "            new TransactionTemplate(t_TransactionManager);\n\n"
        + "        t_TransactionTemplate.execute(\n"
        + "            new UpdateTransactionCallback(\n"
        + "                t_PreparedStatementCreatorFactory\n"
        + "                    .newPreparedStatementCreator(t_aParams),\n"
        + "                t_PreparedStatementCreatorFactory\n"
        + "                    .newPreparedStatementSetter(t_aParams),\n"
        + "                this));\n"
        + "        /*\n"
        + "        update(\n"
        + "            t_PreparedStatementCreatorFactory\n"
        + "                .newPreparedStatementCreator(t_aParams),\n"
        + "            t_PreparedStatementCreatorFactory\n"
        + "                .newPreparedStatementSetter(t_aParams));\n"
        + "        */\n"
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
     * The PK ResultSet extractor subtemplate.
     * @param 0 the capitalized value object name.
     * @param 1 the pk extractor parameter retrieval.
     */
    public static final String DEFAULT_PK_RESULTSET_EXTRACTOR =
          "    /**\n"
        + "     * Extracts <i>g_subscription</i> value objects from result sets.\n"
        + "     * @author <a href=\"http://maven.acm-sl.org/queryj\">QueryJ</a>\n"
        + "     */\n"
        + "    public static class {0}PkResultSetExtractor\n"
        + "        implements  ResultSetExtractor\n"
        + "    '{'\n"
        + "        // <extract data>\n"
        + "        /**\n"
        + "         * Extracts <i>{0}</i> information from given result set.\n"
        + "         * @param resultSet the result set.\n"
        + "         * @return the <code>{0}ValueObject</code> or <code>null</code>\n"
        + "         * if the operation returned no data.\n"
        + "         * @throws SQLException intercepted by <i>Spring</i>.\n"
        + "         * @throws DataAccessException with information about any\n"
        + "         * custom exception.\n"
        + "         * @precondition resultSet != null\n"
        + "         */\n"
        + "        public Object extractData(final ResultSet resultSet)\n"
        + "            throws  SQLException,\n"
        + "                    DataAccessException\n"
        + "        '{'\n"
        + "            Map result = new HashMap();\n\n"
        + "            if  (resultSet.next())\n"
        + "            '{'\n"
        + "                int index = 1;\n\n"
        + "{1}"
        + "            '}'\n\n"
        + "            return result;\n"
        + "        '}'\n"
        + "        // </extract data>\n"
        + "    '}'\n";

    /**
     * The pk extractor simple parameter retrieval.
     * @param 0 the attribute name (Java version).
     * @param 1 the getter method.
     * @param 2 the Java type.
     */
    public static final String DEFAULT_PK_EXTRACTOR_SIMPLE_PARAMETER_RETRIEVAL =
          "                result.put(\n"
        + "                    \"{0}\",\n"
        + "                    new {2}(resultSet.{1}(index++)));\n";
        
    /**
     * The pk extractor parameter retrieval.
     * @param 0 the attribute name (Java version).
     * @param 1 the getter method.
     */
    public static final String DEFAULT_PK_EXTRACTOR_PARAMETER_RETRIEVAL =
          "                result.put(\n"
        + "                    \"{0}\",\n"
        + "                    resultSet.{1}(index++));\n";
        
    /**
     * The default class end.
     * @param 0 the database vendor.
     * @param 1 the table name.
     */
    public static final String DEFAULT_CLASS_END =
          "    /**\n"
        + "     * Executes the update method on JdbcTemplate class.\n"
        + "     * @param creator the <code>PreparedStatementCreator</code> instance.\n"
        + "     * @param setter the <code>PreparedStatementSetter</code> instance.\n"
        + "     */\n"
        + "    public int update(\n"
        + "        final PreparedStatementCreator creator,\n"
        + "        final PreparedStatementSetter setter)\n"
        + "    '{'\n"
        + "        return super.update(creator, setter);\n"
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Executes the query method on JdbcTemplate class.\n"
        + "     * @param creator the <code>PreparedStatementCreator</code> instance.\n"
        + "     * @param setter the <code>PreparedStatementSetter</code> instance.\n"
        + "     * @param extractor the <code>ResultSetExtractor</code> instance.\n"
        + "     */\n"
        + "    public Object query(\n"
        + "        final PreparedStatementCreator creator,\n"
        + "        final PreparedStatementSetter setter,\n"
        + "        final ResultSetExtractor extractor)\n"
        + "    '{'\n"
        + "        return super.query(creator, setter, extractor);\n"
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Transaction callback implementation to perform\n"
        + "     * <code>JdbcTemplate.update(PreparedStatementCreator, PreparedStatementSetter)</code>.\n"
        + "     */\n"
        + "    protected static class AbstractTransactionCallback\n"
        + "        implements TransactionCallback\n"
        + "    '{'\n"
        + "        /**\n"
        + "         * The <code>PreparedStatementCreator</code>.\n"
        + "         */\n"
        + "        private PreparedStatementCreator m__PreparedStatementCreator;\n\n"
        + "        /**\n"
        + "         * The <code>PreparedStatementSetter</code>.\n"
        + "         */\n"
        + "        private PreparedStatementSetter m__PreparedStatementSetter;\n\n"
        + "        /**\n"
        + "         * The <code>{0}{1}DAO</code>.\n"
        + "         */\n"
        + "        private {0}{1}DAO m__{0}{1}DAO;\n\n"
        + "        /**\n"
        + "         * Creates a transaction callback.\n"
        + "         * @param creator the <code>PreparedStatementCreator</code> instance.\n"
        + "         * @param setter the <code>PreparedStatementSetter</code> instance.\n"
        + "         * @param dao the <code>{0}{1}DAO</code> instance.\n"
        + "         */\n"
        + "        public AbstractTransactionCallback(\n"
        + "            final PreparedStatementCreator creator,\n"
        + "            final PreparedStatementSetter setter,\n"
        + "            final {0}{1}DAO dao)\n"
        + "        '{'\n"
        + "            immutableSetPreparedStatementCreator(creator);\n"
        + "            immutableSetPreparedStatementSetter(setter);\n"
        + "            immutableSet{0}{1}DAO(dao);\n"
        + "        '}'\n\n"
        + "        /**\n"
        + "         * Specifies the <code>PreparedStatementCreator</code> instance.\n"
        + "         * @param creator such instance.\n"
        + "         */\n"
        + "        private void immutableSetPreparedStatementCreator(\n"
        + "            final PreparedStatementCreator creator)\n"
        + "        '{'\n"
        + "            m__PreparedStatementCreator = creator;\n"
        + "        '}'\n\n"
        + "        /**\n"
        + "         * Specifies the <code>PreparedStatementCreator</code> instance.\n"
        + "         * @param creator such instance.\n"
        + "         */\n"
        + "        protected void setPreparedStatementCreator(\n"
        + "            final PreparedStatementCreator creator)\n"
        + "        '{'\n"
        + "            immutableSetPreparedStatementCreator(creator);\n"
        + "        '}'\n\n"
        + "        /**\n"
        + "         * Retrieves the <code>PreparedStatementCreator</code> instance.\n"
        + "         * @return such instance.\n"
        + "         */\n"
        + "        public PreparedStatementCreator getPreparedStatementCreator()\n"
        + "        '{'\n"
        + "            return m__PreparedStatementCreator;\n"
        + "        '}'\n\n"
        + "        /**\n"
        + "         * Specifies the <code>PreparedStatementSetter</code> instance.\n"
        + "         * @param setter such instance.\n"
        + "         */\n"
        + "        private void immutableSetPreparedStatementSetter(\n"
        + "            final PreparedStatementSetter setter)\n"
        + "        '{'\n"
        + "            m__PreparedStatementSetter = setter;\n"
        + "        '}'\n\n"
        + "        /**\n"
        + "         * Specifies the <code>PreparedStatementSetter</code> instance.\n"
        + "         * @param setter such instance.\n"
        + "         */\n"
        + "        protected void setPreparedStatementSetter(\n"
        + "            final PreparedStatementSetter setter)\n"
        + "        '{'\n"
        + "            immutableSetPreparedStatementSetter(setter);\n"
        + "        '}'\n\n"
        + "        /**\n"
        + "         * Retrieves the <code>PreparedStatementSetter</code> instance.\n"
        + "         * @return such instance.\n"
        + "         */\n"
        + "        public PreparedStatementSetter getPreparedStatementSetter()\n"
        + "        '{'\n"
        + "            return m__PreparedStatementSetter;\n"
        + "        '}'\n\n"
        + "        /**\n"
        + "         * Specifies the <code>{0}DAO</code> instance.\n"
        + "         * @param dao the DAO instance.\n"
        + "         */\n"
        + "        private void immutableSet{0}{1}DAO(final {0}{1}DAO dao)\n"
        + "        '{'\n"
        + "            m__{0}{1}DAO = dao;\n"
        + "        '}'\n\n"
        + "        /**\n"
        + "         * Specifies the <code>{0}{1}DAO</code> instance.\n"
        + "         * @param dao the DAO instance.\n"
        + "         */\n"
        + "        protected void set{0}{1}DAO(final {0}{1}DAO dao)\n"
        + "        '{'\n"
        + "            immutableSet{0}{1}DAO(dao);\n"
        + "        '}'\n\n"
        + "        /**\n"
        + "         * Retrieves the <code>{0}{1}DAO</code> instance.\n"
        + "         * @return such DAO.\n"
        + "         */\n"
        + "        public {0}{1}DAO get{0}{1}DAO()\n"
        + "        '{'\n"
        + "            return m__{0}{1}DAO;\n"
        + "        '}'\n\n"
        + "        /**\n"
        + "         * Executes the transactional operation.\n"
        + "         * @param status the transaction status.\n"
        + "         */\n"
        + "        public Object doInTransaction(final TransactionStatus status)\n"
        + "        '{'\n"
        + "            return\n"
        + "                doInTransaction(\n"
        + "                    status,\n"
        + "                    getPreparedStatementCreator(),\n"
        + "                    getPreparedStatementSetter(),\n"
        + "                    get{0}{1}DAO());\n"
        + "        '}'\n"
        + "        /**\n"
        + "         * Executes the transactional operation.\n"
        + "         * @param status the transaction status.\n"
        + "         * @param creator the <code>PreparedStatementCreator</code> instance.\n"
        + "         * @param setter the <code>PreparedStatementSetter</code> instance.\n"
        + "         * @param dao the <code>{0}{1}DAO</code> instance.\n"
        + "         */\n"
        + "        protected Object doInTransaction(\n"
        + "            final TransactionStatus status,\n"
        + "            final PreparedStatementCreator creator,\n"
        + "            final PreparedStatementSetter setter,\n"
        + "            final {0}{1}DAO dao)\n"
        + "        '{'\n"
        + "            return null;\n"
        + "        '}'\n\n"
        + "    '}'\n"
        + "    /**\n"
        + "     * Transaction callback implementation to perform\n"
        + "     * <code>JdbcTemplate.update(PreparedStatementCreator, PreparedStatementSetter)</code>.\n"
        + "     */\n"
        + "    protected static class UpdateTransactionCallback\n"
        + "        extends AbstractTransactionCallback\n"
        + "    '{'\n"
        + "        /**\n"
        + "         * Creates a transaction callback.\n"
        + "         * @param creator the <code>PreparedStatementCreator</code> instance.\n"
        + "         * @param setter the <code>PreparedStatementSetter</code> instance.\n"
        + "         * @param dao the <code>{0}{1}DAO</code> instance.\n"
        + "         */\n"
        + "        public UpdateTransactionCallback(\n"
        + "            final PreparedStatementCreator creator,\n"
        + "            final PreparedStatementSetter setter,\n"
        + "            final {0}{1}DAO dao)\n"
        + "        '{'\n"
        + "            super(creator, setter, dao);\n"
        + "        '}'\n\n"
        + "        /**\n"
        + "         * Executes the transactional operation.\n"
        + "         * @param status the transaction status.\n"
        + "         * @param creator the <code>PreparedStatementCreator</code> instance.\n"
        + "         * @param setter the <code>PreparedStatementSetter</code> instance.\n"
        + "         * @param dao the <code>{0}{1}DAO</code> instance.\n"
        + "         */\n"
        + "        protected Object doInTransaction(\n"
        + "            final TransactionStatus status,\n"
        + "            final PreparedStatementCreator creator,\n"
        + "            final PreparedStatementSetter setter,\n"
        + "            final {0}{1}DAO dao)\n"
        + "        '{'\n"
        + "            return new Integer(dao.update(creator, setter));\n"
        + "        '}'\n"
        + "    '}'\n"
        + "    /**\n"
        + "     * Transaction callback implementation to perform\n"
        + "     * <code>JdbcTemplate.query(PreparedStatementCreator, PreparedStatementSetter)</code>.\n"
        + "     */\n"
        + "    protected static class QueryTransactionCallback\n"
        + "        extends AbstractTransactionCallback\n"
        + "    '{'\n"
        + "        /**\n"
        + "         * The optional SQL.\n"
        + "         */\n"
        + "        private String m__strSQL;\n\n"
        + "        /**\n"
        + "         * The <code>ResultSetExtractor</code>.\n"
        + "         */\n"
        + "        private ResultSetExtractor m__ResultSetExtractor;\n\n"
        + "        /**\n"
        + "         * Creates a transaction callback.\n"
        + "         * @param sql the SQL sentence.\n"
        + "         * @param extractor the <code>ResultSetExtractor</code> instance.\n"
        + "         * @param dao the <code>{0}{1}DAO</code> instance.\n"
        + "         */\n"
        + "        public QueryTransactionCallback(\n"
        + "            final String sql,\n"
        + "            final ResultSetExtractor extractor,\n"
        + "            final {0}{1}DAO dao)\n"
        + "        '{'\n"
        + "            super(null, null, dao);\n"
        + "            immutableSetSQL(sql);\n"
        + "            immutableSetResultSetExtractor(extractor);\n"
        + "        '}'\n\n"
        + "        /**\n"
        + "         * Creates a transaction callback.\n"
        + "         * @param creator the <code>PreparedStatementCreator</code> instance.\n"
        + "         * @param setter the <code>PreparedStatementSetter</code> instance.\n"
        + "         * @param extractor the <code>ResultSetExtractor</code> instance.\n"
        + "         * @param dao the <code>{0}{1}DAO</code> instance.\n"
        + "         */\n"
        + "        public QueryTransactionCallback(\n"
        + "            final PreparedStatementCreator creator,\n"
        + "            final PreparedStatementSetter setter,\n"
        + "            final ResultSetExtractor extractor,\n"
        + "            final {0}{1}DAO dao)\n"
        + "        '{'\n"
        + "            super(creator, setter, dao);\n"
        + "            immutableSetResultSetExtractor(extractor);\n"
        + "        '}'\n\n"
        + "        /**\n"
        + "         * Specifies the SQL sentence.\n"
        + "         * @param sql such sentence.\n"
        + "         */\n"
        + "        private void immutableSetSQL(final String sql)\n"
        + "        '{'\n"
        + "            m__strSQL = sql;\n"
        + "        '}'\n\n"
        + "        /**\n"
        + "         * Specifies the SQL sentence.\n"
        + "         * @param sql such sentence.\n"
        + "         */\n"
        + "        protected void setSQL(final String sql)\n"
        + "        '{'\n"
        + "            immutableSetSQL(sql);\n"
        + "        '}'\n\n"
        + "        /**\n"
        + "         * Retrieves the SQL sentence.\n"
        + "         * @return such sentence.\n"
        + "         */\n"
        + "        public String getSQL()\n"
        + "        '{'\n"
        + "            return m__strSQL;\n"
        + "        '}'\n\n"
        + "        /**\n"
        + "         * Specifies the <code>ResultSetExtractor</code> instance.\n"
        + "         * @param extractor such instance.\n"
        + "         */\n"
        + "        private void immutableSetResultSetExtractor(\n"
        + "            final ResultSetExtractor extractor)\n"
        + "        '{'\n"
        + "            m__ResultSetExtractor = extractor;\n"
        + "        '}'\n\n"
        + "        /**\n"
        + "         * Specifies the <code>ResultSetExtractor</code> instance.\n"
        + "         * @param extractor such instance.\n"
        + "         */\n"
        + "        protected void setResultSetExtractor(\n"
        + "            final ResultSetExtractor extractor)\n"
        + "        '{'\n"
        + "            immutableSetResultSetExtractor(extractor);\n"
        + "        '}'\n\n"
        + "        /**\n"
        + "         * Retrieves the <code>ResultSetExtractor</code> instance.\n"
        + "         * @return such instance.\n"
        + "         */\n"
        + "        public ResultSetExtractor getResultSetExtractor()\n"
        + "        '{'\n"
        + "            return m__ResultSetExtractor;\n"
        + "        '}'\n\n"
        + "        /**\n"
        + "         * Executes the transactional operation.\n"
        + "         * @param status the transaction status.\n"
        + "         * @param creator the <code>PreparedStatementCreator</code> instance.\n"
        + "         * @param setter the <code>PreparedStatementSetter</code> instance.\n"
        + "         * @param dao the <code>{0}{1}DAO</code> instance.\n"
        + "         */\n"
        + "        protected Object doInTransaction(\n"
        + "            final TransactionStatus status,\n"
        + "            final PreparedStatementCreator creator,\n"
        + "            final PreparedStatementSetter setter,\n"
        + "            final {0}{1}DAO dao)\n"
        + "        '{'\n"
        + "            return\n"
        + "                doInTransaction(\n"
        + "                    status,\n"
        + "                    creator,\n"
        + "                    setter,\n"
        + "                    getSQL(),\n"
        + "                    getResultSetExtractor(),\n"
        + "                    dao);\n"
        + "        '}'\n"
        + "        /**\n"
        + "         * Executes the transactional operation.\n"
        + "         * @param status the transaction status.\n"
        + "         * @param creator the <code>PreparedStatementCreator</code> instance.\n"
        + "         * @param setter the <code>PreparedStatementSetter</code> instance.\n"
        + "         * @param sql the sql (optional).\n"
        + "         * @param extractor the <code>ResultSetExtractor</code> instance.\n"
        + "         * @param dao the <code>{0}{1}DAO</code> instance.\n"
        + "         */\n"
        + "        protected Object doInTransaction(\n"
        + "            final TransactionStatus status,\n"
        + "            final PreparedStatementCreator creator,\n"
        + "            final PreparedStatementSetter setter,\n"
        + "            final String sql,\n"
        + "            final ResultSetExtractor extractor,\n"
        + "            final {0}{1}DAO dao)\n"
        + "        '{'\n"
        + "            Object result = null;\n\n"
        + "            if  (sql == null)\n"
        + "            '{'\n"
        + "                result = dao.query(creator, setter, extractor);\n"
        + "            '}'\n"
        + "            else\n"
        + "            '{'\n"
        + "                result = dao.query(sql, extractor);\n"
        + "            '}'\n\n"
        + "            return result;\n"
        + "        '}'\n"
        + "    '}'\n"
        + "'}'\n";
}
