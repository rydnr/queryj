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
 * Description: Is able to create abstract base JDBC DAO.
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
import org.acmsl.queryj.tools.DatabaseMetaDataManager;
import org.acmsl.queryj.tools.templates.TableTemplate;

/*
 * Importing some ACM-SL classes.
 */
import org.acmsl.commons.utils.StringUtils;

/*
 * Importing some JDK classes.
 */
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/*
 * Importing Apache Commons Logging classes.
 */
import org.apache.commons.logging.LogFactory;

/**
 * Is able to create abstract base JDBC DAO.
 * @author <a href="mailto:jsanleandro@yahoo.es"
           >Jose San Leandro</a>
 * @version $Revision$
 */
public abstract class JdbcDAOTemplate
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
        + " * Description: Abstract DAO with common methods to all\n"
        + " *              JDBC-based DAO implementations.\n"
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
     * The ACM-SL imports.
     */
    public static final String DEFAULT_ACMSL_IMPORTS =
          "/*\n"
        + " * Importing some ACM-SL classes.\n"
        + " */\n"
        + "import org.acmsl.queryj.dao.DataSourceTransactionToken;\n"
        + "import org.acmsl.queryj.dao.TransactionToken;\n"
        + "import org.acmsl.queryj.dao.TransactionTokenFactory;\n\n";

    /**
     * The JDK imports.
     */
    public static final String DEFAULT_JDK_IMPORTS =
          "/*\n"
        + " * Importing some JDK classes.\n"
        + " */\n"
        + "import java.sql.Connection;\n"
        + "import java.sql.PreparedStatement;\n"
        + "import java.sql.ResultSet;\n"
        + "import java.sql.SQLException;\n"
        + "import java.sql.Statement;\n\n";

    /**
     * The JDK extension imports.
     */
    public static final String DEFAULT_JDK_EXTENSION_IMPORTS =
          "/*\n"
        + " * Importing some JDK extension classes\n"
        + " */\n"
        + "import javax.sql.DataSource;\n"
        + "//import javax.transaction.NotSupportedException;\n"
        + "//import javax.transaction.RollbackException;\n"
        + "//import javax.transaction.InvalidTransactionException;\n\n";

    /**
     * The logging imports.
     */
    public static final String DEFAULT_LOGGING_IMPORTS =
          "/*\n"
        + " * Importing Jakarta Commons Loggig classes\n"
        + " */\n"
        + "import org.apache.commons.logging.LogFactory;\n\n";
    
    /**
     * The default class Javadoc.
     */
    public static final String DEFAULT_JAVADOC =
          "/**\n"
        + " * Abstract DAO with common methods to all\n"
        + " * JDBC-based DAO implementations.\n"
        + " * @author <a href=\"http://maven.acm-sl.org/queryj\">QueryJ</a>\n"
        + " * @version $" + "Revision: $\n"
        + " */\n";

    /**
     * The class definition.
     */
    public static final String DEFAULT_CLASS_DEFINITION =
        "public abstract class JdbcDAO\n";

    /**
     * The class start.
     */
    public static final String DEFAULT_CLASS_START =
          "{\n"
        + "    /**\n"
        + "     * The data source.\n"
        + "     */\n"
        + "    private DataSource m__DataSource;\n\n";

    /**
     * The class constructor.
     */
    public static final String DEFAULT_CLASS_CONSTRUCTOR =
          "    /**\n"
        + "     * Builds a JdbcDAO\n"
        // engine name - table name
        + "     * with given data source.\n"
        + "     * @param dataSource the required data source.\n"
        + "     */\n"
        + "    public JdbcDAO(DataSource dataSource)\n"
        + "    {\n"
        + "        inmutableSetDataSource(dataSource);\n"
        + "    }\n\n";
        // engine name - table name

    /**
     * The attributes' accessors.
     */
    public static final String DEFAULT_ATTRIBUTE_ACCESSORS =
          "    /**\n"
        + "     * Specifies the data source.\n"
        + "     * @param dataSource the data source to use.\n"
        + "     */\n"
        + "    private void inmutableSetDataSource(DataSource dataSource)\n"
        + "    {\n"
        + "        m__DataSource = dataSource;\n"
        + "    }\n\n"
        + "    /**\n"
        + "     * Specifies the data source.\n"
        + "     * @param dataSource the data source to use.\n"
        + "     */\n"
        + "    protected void setDataSource(DataSource dataSource)\n"
        + "    {\n"
        + "        inmutableSetDataSource(dataSource);\n"
        + "    }\n\n"
        + "    /**\n"
        + "     * Retrieves the data source.\n"
        + "     * @return such data source.\n"
        + "     */\n"
        + "    public DataSource getDataSource()\n"
        + "    {\n"
        + "        return m__DataSource;\n"
        + "    }\n\n";

    /**
     * The connection retrieval methods.
     */
    public static final String DEFAULT_CONNECTION_RETRIEVAL_METHODS =
          "    /**\n"
        + "     * Tries the retrieve a connection from wherever it can.\n"
        + "     * @return the connection.\n"
        + "     * @exception SQLException if the connection cannot be\n"
        + "     * retrieved.\n"
        + "     */\n"
        + "    protected Connection getConnection()\n"
        + "        throws  SQLException\n"
        + "    {\n"
        + "        return getConnection(null);\n"
        + "    }\n\n"
        + "    /**\n"
        + "     * Tries the retrieve a connection from given transaction token\n"
        + "     * or somewhere else if such token is <code>null</code>.\n"
        + "     * @param transactionToken the transaction runtime information.\n"
        + "     * @return the connection.\n"
        + "     * @exception SQLException if the connection cannot be\n"
        + "     * retrieved.\n"
        + "     */\n"
        + "    protected Connection getConnection(final TransactionToken transactionToken)\n"
        + "        throws  SQLException\n"
        + "    {\n"
        + "        Connection result = null;\n\n"
        + "        DataSource t_DataSource = null;\n\n"
        + "        if  (   (transactionToken != null)\n"
        + "             && (transactionToken instanceof DataSourceTransactionToken))\n"
        + "        {\n"
        + "            t_DataSource =\n"
        + "                ((DataSourceTransactionToken) transactionToken)\n"
        + "                    .getDataSource();\n\n"
        + "        }\n\n"
        + "        if  (t_DataSource == null)\n"
        + "        {\n"
        + "            t_DataSource = getDataSource();\n"
        + "        }\n\n"
        + "        if  (t_DataSource != null)\n"
        + "        {\n"
        + "            result = t_DataSource.getConnection();\n"
        + "        }\n\n"
        + "        return result;\n"
        + "    }\n\n";

    /**
     * The commit methods.
     */
    public static final String DEFAULT_COMMIT_METHODS =
          "    /**\n"
        + "     * Tries to commit the operation.\n"
        + "     * Note: Use this method ONLY if the connection pool\n"
        + "     *       has auto-commit behaviour disabled.\n"
        + "     * @param connection the connection to commit.\n"
        + "     * @exception SQLException if while committing or closing\n"
        + "     * the connection an exception occurs.\n"
        + "     */\n"
        + "    protected void commit(final Connection connection)\n"
        + "        throws  SQLException\n"
        + "    {\n"
        + "        commit(connection, null);\n"
        + "    }\n\n"
        + "    /**\n"
        + "     * Tries to commit the operation.\n"
        + "     * Note: Use this method ONLY if the connection pool\n"
        + "     *       has auto-commit behaviour disabled.\n"
        + "     * @param transactionToken the transaction token.\n"
        + "     * @exception SQLException if while committing or closing\n"
        + "     * the connection an exception occurs.\n"
        + "     */\n"
        + "    protected void commit(final TransactionToken transactionToken)\n"
        + "        throws  SQLException\n"
        + "    {\n"
        + "        commit(null, transactionToken);\n"
        + "    }\n\n"
        + "    /**\n"
        + "     * Tries to commit the operation.\n"
        + "     * Note: Use this method ONLY if the connection pool\n"
        + "     *       has auto-commit behaviour disabled.\n"
        + "     * @param connection the connection to commit.\n"
        + "     * @param transactionToken the transaction token.\n"
        + "     * @exception SQLException if while committing or closing\n"
        + "     * the connection an exception occurs.\n"
        + "     */\n"
        + "    protected void commit(\n"
        + "            final Connection       connection,\n"
        + "            final TransactionToken transactionToken)\n"
        + "        throws  SQLException\n"
        + "    {\n"
        + "        // By default, we won't try to restore the autocommit setting.\n"
        + "        commit(connection, transactionToken, false, true);\n"
        + "    }\n\n"
        + "    /**\n"
        + "     * Tries to commit the operation.\n"
        + "     * @param connection the connection to commit.\n"
        + "     * @param transactionToken the transaction token.\n"
        + "     * @param restoreAutoCommit to restore the previous auto-commit\n"
        + "     * flag value.\n"
        + "     * @param closeConnection <code>true</code> to try to close the connection.\n"
        + "     * @exception SQLException if while committing or closing\n"
        + "     * the connection an exception occurs.\n"
        + "     */\n"
        + "    protected void commit(\n"
        + "            final Connection       connection,\n"
        + "            final TransactionToken transactionToken,\n"
        + "            final boolean          restoreAutoCommit,\n"
        + "            final boolean          closeConnection)\n"
        + "        throws  SQLException\n"
        + "    {\n"
        + "        SQLException t_AutoCommitException = null;\n\n"
        + "        Connection t_OwnConnection = null;\n\n"
        + "        DataSource t_DataSource = null;\n\n"
        + "        // by default, we suppose auto-commit is set to false.\n"
        + "        boolean t_bAutoCommit = false;\n\n"
        + "        if  (   (transactionToken != null)\n"
        + "             && (transactionToken instanceof DataSourceTransactionToken))\n"
        + "        {\n"
        + "            t_DataSource =\n"
        + "                ((DataSourceTransactionToken) transactionToken)\n"
        + "                    .getDataSource();\n\n"
        + "        }\n\n"
        + "        if  (t_DataSource == null)\n"
        + "        {\n"
        + "            t_DataSource = getDataSource();\n"
        + "        }\n\n"
        + "        t_OwnConnection =\n"
        + "            (   (t_DataSource == null)\n"
        + "             ?  connection\n"
        + "             :  t_DataSource.getConnection());\n\n"
        + "        if  (t_OwnConnection != null)\n"
        + "        {\n"
        + "            try\n"
        + "            {\n"
        + "                // We only commit the operations not belonging to\n"
        + "                // an outer transaction.\n"
        + "                if  (   (transactionToken == null)\n"
        + "                     || (!transactionToken.isTransactionAlive()))\n"
        + "                {\n"
        + "                    try\n"
        + "                    {\n"
        + "                        t_bAutoCommit = t_OwnConnection.getAutoCommit();\n\n"
        + "                    }\n"
        + "                    catch  (SQLException sqlException)\n"
        + "                    {\n"
        + "                        t_AutoCommitException = sqlException;\n"
        + "                    }\n\n"
        + "                    t_OwnConnection.commit();\n\n"
        + "                    if  (transactionToken != null)\n"
        + "                    {\n"
        + "                        transactionToken.release();\n"
        + "                    }\n"
        + "                    else\n"
        + "                    {\n"
        + "                        if  (closeConnection)\n"
        + "                        {\n"
        + "                            closeConnection(\n"
        + "                                t_OwnConnection,\n"
        + "                                transactionToken,\n"
        + "                                restoreAutoCommit,\n"
        + "                                t_bAutoCommit);\n"
        + "                        }\n"
        + "                    }\n"
        + "                }\n"
        + "            }\n"
        + "            catch  (final SQLException sqlException)\n"
        + "            {\n"
        + "                if  (t_AutoCommitException != null)\n"
        + "                {\n"
        + "                    sqlException.initCause(t_AutoCommitException);\n"
        + "                }\n\n"
        + "                throw sqlException;\n"
        + "            }\n"
        + "        }\n"
        + "    }\n\n";
        
    /**
     * The rollback methods.
     */
    public static final String DEFAULT_ROLLBACK_METHODS =
          "    /**\n"
        + "     * Tries to rollback the operation.\n"
        + "     * Note: Use this method ONLY if the connection pool has auto\n"
        + "     *       commit behaviour disabled.\n"
        + "     * @param connection the connection to rollback.\n"
        + "     * @throws SQLException if while performing the rollback or\n"
        + "     * closing the connection an exception occurs.\n"
        + "     */\n"
        + "    protected void rollback(final Connection connection)\n"
        + "        throws  SQLException\n"
        + "    {\n"
        + "        rollback(connection, null);\n"
        + "    }\n\n"
        + "    /**\n"
        + "     * Tries to rollback the operation.\n"
        + "     * Note: Use this method ONLY if the connection pool has auto\n"
        + "     *       commit behaviour disabled.\n"
        + "     * @param transactionToken the transaction token.\n"
        + "     * @throws SQLException if while performing the rollback or\n"
        + "     * closing the connection an exception occurs.\n"
        + "     */\n"
        + "    protected void rollback(final TransactionToken transactionToken)\n"
        + "        throws  SQLException\n"
        + "    {\n"
        + "        rollback(null, transactionToken);\n"
        + "    }\n\n"
        + "    /**\n"
        + "     * Tries to rollback the operation.\n"
        + "     * Note: Use this method ONLY if the connection pool has auto\n"
        + "     *       commit behaviour disabled.\n"
        + "     * @param connection the connection to rollback.\n"
        + "     * @param transactionToken the transaction token.\n"
        + "     * @throws SQLException if while performing the rollback or\n"
        + "     * closing the connection an exception occurs.\n"
        + "     */\n"
        + "    protected void rollback(\n"
        + "            final Connection       connection,\n"
        + "            final TransactionToken transactionToken)\n"
        + "        throws  SQLException\n"
        + "    {\n"
        + "        // By default, we won't try to restore the autocommit setting.\n"
        + "        rollback(connection, transactionToken, false, true);\n"
        + "    }\n\n"
        + "    /**\n"
        + "     * Tries to rollback the operation.\n"
        + "     * @param connection the connection to rollback.\n"
        + "     * @param transactionToken the transaction token.\n"
        + "     * @param restoreAutoCommit to restore the previous auto-commit\n"
        + "     * flag value.\n"
        + "     * @param closeConnection <code>true</code> to try to close the connection.\n"
        + "     * @throws SQLException if the rollback or while closing the connection an\n"
        + "     * exception occurs.\n"
        + "     */\n"
        + "    protected void rollback(\n"
        + "            final Connection        connection,\n"
        + "            final TransactionToken  transactionToken,\n"
        + "            final boolean           restoreAutoCommit,\n"
        + "            final boolean           closeConnection)\n"
        + "        throws  SQLException\n"
        + "    {\n"
        + "        SQLException t_AutoCommitException = null;\n\n"
        + "        Connection t_OwnConnection = null;\n\n"
        + "        DataSource t_DataSource = null;\n\n"
        + "        // by default, we suppose auto-commit is set to false.\n"
        + "        boolean t_bAutoCommit = false;\n\n"
        + "        if  (   (transactionToken != null)\n"
        + "             && (transactionToken instanceof DataSourceTransactionToken))\n"
        + "        {\n"
        + "            t_DataSource =\n"
        + "                ((DataSourceTransactionToken) transactionToken)\n"
        + "                    .getDataSource();\n\n"
        + "        }\n\n"
        + "        if  (t_DataSource == null)\n"
        + "        {\n"
        + "            t_DataSource = getDataSource();\n"
        + "        }\n\n"
        + "        t_OwnConnection =\n"
        + "            (   (t_DataSource == null)\n"
        + "             ?  connection\n"
        + "             :  t_DataSource.getConnection());\n\n"
        + "        if  (t_OwnConnection != null)\n"
        + "        {\n"
        + "            t_bAutoCommit = t_OwnConnection.getAutoCommit();\n\n"
        + "            try\n"
        + "            {\n"
        + "                // We only rollback the operations not belonging to\n"
        + "                // an outer transaction.\n"
        + "                if  (   (transactionToken == null)\n"
        + "                     || (!transactionToken.isTransactionAlive()))\n"
        + "                {\n"
        + "                    try\n"
        + "                    {\n"
        + "                        t_bAutoCommit = t_OwnConnection.getAutoCommit();\n\n"
        + "                    }\n"
        + "                    catch  (SQLException sqlException)\n"
        + "                    {\n"
        + "                        t_AutoCommitException = sqlException;\n"
        + "                    }\n\n"
        + "                    t_OwnConnection.rollback();\n\n"
        + "                    if  (transactionToken != null)\n"
        + "                    {\n"
        + "                        transactionToken.release();\n"
        + "                    }\n"
        + "                    else\n"
        + "                    {\n"
        + "                        if  (closeConnection)\n"
        + "                        {\n"
        + "                            closeConnection(\n"
        + "                                t_OwnConnection,\n"
        + "                                transactionToken,\n"
        + "                                restoreAutoCommit,\n"
        + "                                t_bAutoCommit);\n"
        + "                        }\n"
        + "                    }\n"
        + "                }\n"
        + "                else\n"
        + "                {\n"
        + "                    if  (transactionToken != null)\n"
        + "                    {\n"
        + "                        transactionToken.setRollbackPending(true);\n"
        + "                    }\n"
        + "                }\n"
        + "            }\n"
        + "            catch  (SQLException sqlException)\n"
        + "            {\n"
        + "                if  (t_AutoCommitException != null) \n"
        + "                {\n"
        + "                    sqlException.initCause(sqlException);\n"
        + "                }\n\n"
        + "                throw sqlException;\n"
        + "            }\n"
        + "        }\n"
        + "    }\n\n";

    /**
     * The connection closing methods.
     */
    public static final String DEFAULT_CONNECTION_CLOSING_METHODS =
          "    /**\n"
        + "     * Tries to close the database connection.\n"
        + "     * Note: Use this method ONLY if the connection pool has auto\n"
        + "     * commit behaviour disabled.\n"
        + "     * @param connection the database connection.\n"
        + "     * @param transactionToken the transaction token.\n"
        + "     * @throws SQLException if while closing the connection an exception\n"
        + "     * occurs.\n"
        + "     */\n"
        + "    protected void closeConnection(\n"
        + "            final Connection        connection,\n"
        + "            final TransactionToken  transactionToken)\n"
        + "        throws  SQLException\n"
        + "    {\n"
        + "        // By default, we won't try to restore the autocommit setting.\n"
        + "        closeConnection(connection, transactionToken, false, false);\n"
        + "    }\n\n"
        + "    /**\n"
        + "     * Tries to close the database connection.\n"
        + "     * @param connection the database connection.\n"
        + "     * @param transactionToken the transaction token.\n"
        + "     * @param restoreAutoCommit <true> to restore the autocommit setting.\n"
        + "     * @param oldAutoCommit the old autocommit behaviour.\n"
        + "     * @throws SQLException if while closing the connection an exception\n"
        + "               occurs.\n"
        + "     */\n"
        + "    protected void closeConnection(\n"
        + "            final Connection        connection,\n"
        + "            final TransactionToken  transactionToken,\n"
        + "            final boolean           restoreAutoCommit,\n"
        + "            final boolean           oldAutoCommit)\n"
        + "        throws  SQLException\n"
        + "    {\n"
        + "        SQLException t_AutoCommitException = null;\n\n"
        + "        try \n"
        + "        {\n"
        + "            if  (   (transactionToken == null)\n"
        + "                 || (!(transactionToken instanceof DataSourceTransactionToken)))\n"
        + "            {\n"
        + "                if  (   (connection != null)\n"
        + "                     && (!connection.isClosed()))\n"
        + "                {\n"
        + "                    if  (restoreAutoCommit)\n"
        + "                    {\n"
        + "                        try \n"
        + "                        {\n"
        + "                            connection.setAutoCommit(oldAutoCommit);\n"
        + "                        }\n"
        + "                        catch  (SQLException sqlException)\n"
        + "                        {\n"
        + "                            t_AutoCommitException = sqlException;\n"
        + "                        }\n"
        + "                    }\n\n"
        + "                    connection.close();\n"
        + "                }\n"
        + "            }\n"
        + "        }\n"
        + "        catch  (SQLException sqlException)\n"
        + "        {\n"
        + "            if  (t_AutoCommitException != null) \n"
        + "            {\n"
        + "                sqlException.initCause(t_AutoCommitException);\n"
        + "            }\n\n"
        + "            throw sqlException;\n"
        + "        }\n"
        + "    }\n\n";

    /**
     * The transaction token factory methods.
     */
    public static final String DEFAULT_TRANSACTION_TOKEN_FACTORY_METHODS =
          "    /**\n"
        + "     * Creates a transaction token.\n"
        + "     * @return the transaction token.\n"
        + "     */\n"
        + "    public TransactionToken createTransactionToken()\n"
        + "    {\n"
        + "        return\n"
        + "            TransactionTokenFactory.createTransactionToken(getDataSource());\n"
        + "    }\n\n";

    /**
     * The default class end.
     */
    public static final String DEFAULT_CLASS_END = "}\n";

    /**
     * The header.
     */
    private String m__strHeader;

    /**
     * The package declaration.
     */
    private String m__strPackageDeclaration;

    /**
     * The package name.
     */
    private String m__strPackageName;

    /**
     * The ACM-SL import statements.
     */
    private String m__strAcmslImports;

    /**
     * The JDK import statements.
     */
    private String m__strJdkImports;

    /**
     * The JDK extension import statements.
     */
    private String m__strJdkExtensionImports;

    /**
     * The Logging import statements.
     */
    private String m__strLoggingImports;

    /**
     * The class Javadoc.
     */
    private String m__strJavadoc;

    /**
     * The class definition.
     */
    private String m__strClassDefinition;

    /**
     * The class start.
     */
    private String m__strClassStart;

    /**
     * The class constructor.
     */
    private String m__strClassConstructor;

    /**
     * The attribute accessors.
     */
    private String m__strAttributeAccessors;

    /**
     * The connection retrieval methods.
     */
    private String m__strConnectionRetrievalMethods;

    /**
     * The commit methods.
     */
    private String m__strCommitMethods;

    /**
     * The rollback methods.
     */
    private String m__strRollbackMethods;

    /**
     * The connection closing methods.
     */
    private String m__strConnectionClosingMethods;

    /**
     * The transaction token factory methods.
     */
    private String m__strTransactionTokenFactoryMethods;

    /**
     * The class end.
     */
    private String m__strClassEnd;

    /**
     * Builds a JdbcDAOTemplate using given information.
     * @param header the header.
     * @param packageDeclaration the package declaration.
     * @param packageName the package name.
     * @param acmslImports the ACM-SL imports.
     * @param jdkImports the JDK imports.
     * @param jdkExtensionImports the JDK extension imports.
     * @param loggingImports the commons-logging imports.
     * @param javadoc the class Javadoc.
     * @param classDefinition the class definition.
     * @param classStart the class start.
     * @param classConstructor the class constructor.
     * @param attributeAccessors the attribute accessors.
     * @param connectionRetrievalMethods the connection retrieval methods.
     * @param commitMethods the commit methods.
     * @param rollbackMethods the rollback methods.
     * @param connectionClosingMethods the connection closing methods.
     * @param transactionTokenFactoryMethods the transaction token factory
     *        methods.
     * @param classEnd the class end.
     */
    public JdbcDAOTemplate(
        final String  header,
        final String  packageDeclaration,
        final String  packageName,
        final String  acmslImports,
        final String  jdkImports,
        final String  jdkExtensionImports,
        final String  loggingImports,
        final String  javadoc,
        final String  classDefinition,
        final String  classStart,
        final String  classConstructor,
        final String  attributeAccessors,
        final String  connectionRetrievalMethods,
        final String  commitMethods,
        final String  rollbackMethods,
        final String  connectionClosingMethods,
        final String  transactionTokenFactoryMethods,
        final String  classEnd)
    {
        inmutableSetHeader(header);
        inmutableSetPackageDeclaration(packageDeclaration);
        inmutableSetPackageName(packageName);
        inmutableSetAcmslImports(acmslImports);
        inmutableSetJdkImports(jdkImports);
        inmutableSetJdkExtensionImports(jdkExtensionImports);
        inmutableSetLoggingImports(loggingImports);
        inmutableSetJavadoc(javadoc);
        inmutableSetClassDefinition(classDefinition);
        inmutableSetClassStart(classStart);
        inmutableSetClassConstructor(classConstructor);
        inmutableSetAttributeAccessors(attributeAccessors);
        inmutableSetConnectionRetrievalMethods(
            connectionRetrievalMethods);
        inmutableSetCommitMethods(commitMethods);
        inmutableSetRollbackMethods(rollbackMethods);
        inmutableSetConnectionClosingMethods(connectionClosingMethods);
        inmutableSetTransactionTokenFactoryMethods(
            transactionTokenFactoryMethods);
        inmutableSetClassEnd(classEnd);
    }

    /**
     * Builds a JdbcDAOTemplate using given information.
     * @param packageName the package name.
     */
    public JdbcDAOTemplate(String packageName)
    {
        this(
            DEFAULT_HEADER,
            DEFAULT_PACKAGE_DECLARATION,
            packageName,
            DEFAULT_ACMSL_IMPORTS,
            DEFAULT_JDK_IMPORTS,
            DEFAULT_JDK_EXTENSION_IMPORTS,
            DEFAULT_LOGGING_IMPORTS,
            DEFAULT_JAVADOC,
            DEFAULT_CLASS_DEFINITION,
            DEFAULT_CLASS_START,
            DEFAULT_CLASS_CONSTRUCTOR,
            DEFAULT_ATTRIBUTE_ACCESSORS,
            DEFAULT_CONNECTION_RETRIEVAL_METHODS,
            DEFAULT_COMMIT_METHODS,
            DEFAULT_ROLLBACK_METHODS,
            DEFAULT_CONNECTION_CLOSING_METHODS,
            DEFAULT_TRANSACTION_TOKEN_FACTORY_METHODS,
            DEFAULT_CLASS_END);
    }

    /**
     * Specifies the header.
     * @param header the new header.
     */
    private void inmutableSetHeader(final String header)
    {
        m__strHeader = header;
    }

    /**
     * Specifies the header.
     * @param header the new header.
     */
    protected void setHeader(final String header)
    {
        inmutableSetHeader(header);
    }

    /**
     * Retrieves the header.
     * @return such information.
     */
    public String getHeader() 
    {
        return m__strHeader;
    }

    /**
     * Specifies the package declaration.
     * @param packageDeclaration the new package declaration.
     */
    private void inmutableSetPackageDeclaration(final String packageDeclaration)
    {
        m__strPackageDeclaration = packageDeclaration;
    }

    /**
     * Specifies the package declaration.
     * @param packageDeclaration the new package declaration.
     */
    protected void setPackageDeclaration(final String packageDeclaration)
    {
        inmutableSetPackageDeclaration(packageDeclaration);
    }

    /**
     * Retrieves the package declaration.
     * @return such information.
     */
    public String getPackageDeclaration() 
    {
        return m__strPackageDeclaration;
    }

    /**
     * Specifies the package name.
     * @param packageName the new package name.
     */
    private void inmutableSetPackageName(final String packageName)
    {
        m__strPackageName = packageName;
    }

    /**
     * Specifies the package name.
     * @param packageName the new package name.
     */
    protected void setPackageName(final String packageName)
    {
        inmutableSetPackageName(packageName);
    }

    /**
     * Retrieves the package name.
     * @return such information.
     */
    public String getPackageName() 
    {
        return m__strPackageName;
    }

    /**
     * Specifies the ACM-SL imports.
     * @param acmslImports the new ACM-SL imports.
     */
    private void inmutableSetAcmslImports(final String acmslImports)
    {
        m__strAcmslImports = acmslImports;
    }

    /**
     * Specifies the ACM-SL imports.
     * @param acmslImports the new ACM-SL imports.
     */
    protected void setAcmslImports(final String acmslImports)
    {
        inmutableSetAcmslImports(acmslImports);
    }

    /**
     * Retrieves the ACM-SL imports.
     * @return such information.
     */
    public String getAcmslImports() 
    {
        return m__strAcmslImports;
    }

    /**
     * Specifies the JDK imports.
     * @param jdkImports the new JDK imports.
     */
    private void inmutableSetJdkImports(final String jdkImports)
    {
        m__strJdkImports = jdkImports;
    }

    /**
     * Specifies the JDK imports.
     * @param jdkImports the new JDK imports.
     */
    protected void setJdkImports(final String jdkImports)
    {
        inmutableSetJdkImports(jdkImports);
    }

    /**
     * Retrieves the JDK imports.
     * @return such information.
     */
    public String getJdkImports() 
    {
        return m__strJdkImports;
    }

    /**
     * Specifies the JDK extension imports.
     * @param jdkExtensionImports the new JDK extension imports.
     */
    private void inmutableSetJdkExtensionImports(final String jdkExtensionImports)
    {
        m__strJdkExtensionImports = jdkExtensionImports;
    }

    /**
     * Specifies the JDK extension imports.
     * @param jdkExtensionImports the new JDK extension imports.
     */
    protected void setJdkExtensionImports(final String jdkExtensionImports)
    {
        inmutableSetJdkExtensionImports(jdkExtensionImports);
    }

    /**
     * Retrieves the JDK extension imports.
     * @return such information.
     */
    public String getJdkExtensionImports() 
    {
        return m__strJdkExtensionImports;
    }


    /**
     * Specifies the logging imports.
     * @param loggingImports the new logging imports.
     */
    private void inmutableSetLoggingImports(final String loggingImports)
    {
        m__strLoggingImports = loggingImports;
    }

    /**
     * Specifies the logging imports.
     * @param loggingImports the new logging imports.
     */
    protected void setLoggingImports(final String loggingImports)
    {
        inmutableSetLoggingImports(loggingImports);
    }

    /**
     * Retrieves the logging imports.
     * @return such information.
     */
    public String getLoggingImports() 
    {
        return m__strLoggingImports;
    }

    /**
     * Specifies the javadoc.
     * @param javadoc the new javadoc.
     */
    private void inmutableSetJavadoc(final String javadoc)
    {
        m__strJavadoc = javadoc;
    }

    /**
     * Specifies the javadoc.
     * @param javadoc the new javadoc.
     */
    protected void setJavadoc(final String javadoc)
    {
        inmutableSetJavadoc(javadoc);
    }

    /**
     * Retrieves the javadoc.
     * @return such information.
     */
    public String getJavadoc() 
    {
        return m__strJavadoc;
    }

    /**
     * Specifies the class definition.
     * @param classDefinition the new class definition.
     */
    private void inmutableSetClassDefinition(final String classDefinition)
    {
        m__strClassDefinition = classDefinition;
    }

    /**
     * Specifies the class definition.
     * @param classDefinition the new class definition.
     */
    protected void setClassDefinition(final String classDefinition)
    {
        inmutableSetClassDefinition(classDefinition);
    }

    /**
     * Retrieves the class definition.
     * @return such information.
     */
    public String getClassDefinition() 
    {
        return m__strClassDefinition;
    }

    /**
     * Specifies the class start.
     * @param classStart the new class start.
     */
    private void inmutableSetClassStart(final String classStart)
    {
        m__strClassStart = classStart;
    }

    /**
     * Specifies the class start.
     * @param classStart the new class start.
     */
    protected void setClassStart(final String classStart)
    {
        inmutableSetClassStart(classStart);
    }

    /**
     * Retrieves the class start.
     * @return such information.
     */
    public String getClassStart() 
    {
        return m__strClassStart;
    }

    /**
     * Specifies the class constructor
     * @param constructor such source code.
     */
    private void inmutableSetClassConstructor(final String constructor)
    {
        m__strClassConstructor = constructor;
    }

    /**
     * Specifies the class constructor
     * @param constructor such source code.
     */
    protected void setClassConstructor(final String constructor)
    {
        inmutableSetClassConstructor(constructor);
    }

    /**
     * Retrieves the class constructor.
     * @return such source code.
     */
    public String getClassConstructor()
    {
        return m__strClassConstructor;
    }

    /**
     * Specifies the attribute accessors.
     * @param attributeAccessors the attribute accessors.
     */
    private void inmutableSetAttributeAccessors(final String attributeAccessors)
    {
        m__strAttributeAccessors = attributeAccessors;
    }

    /**
     * Specifies the attribute accessors.
     * @param attributeAccessors the attribute accessors.
     */
    protected void setAttributeAccessors(final String attributeAccessors)
    {
        inmutableSetAttributeAccessors(attributeAccessors);
    }

    /**
     * Retrieves the attribute accessors.
     * @return such methods.
     */
    public String getAttributeAccessors()
    {
        return m__strAttributeAccessors;
    }

    /**
     * Specifies the connection retrieval methods,
     * @param connectionRetrievalMethods the connection retrieval
     * methods.
     */
    private void inmutableSetConnectionRetrievalMethods(
        String connectionRetrievalMethods)
    {
        m__strConnectionRetrievalMethods = connectionRetrievalMethods;
    }

    /**
     * Specifies the connection retrieval methods,
     * @param connectionRetrievalMethods the connection retrieval
     * methods.
     */
    protected void setConnectionRetrievalMethods(
        String connectionRetrievalMethods)
    {
        inmutableSetConnectionRetrievalMethods(
            connectionRetrievalMethods);
    }

    /**
     * Retrieves the connection retrieval methods.
     * @return such methods.
     */
    public String getConnectionRetrievalMethods()
    {
        return m__strConnectionRetrievalMethods;
    }

    /**
     * Specifies the commit methods.
     * @param commitMethods the commit methods.
     */
    private void inmutableSetCommitMethods(final String commitMethods)
    {
        m__strCommitMethods = commitMethods;
    }

    /**
     * Specifies the commit methods.
     * @param commitMethods the commit methods.
     */
    protected void setCommitMethods(final String commitMethods)
    {
        inmutableSetCommitMethods(commitMethods);
    }

    /**
     * Retrieves the commit methods.
     * @return such methods.
     */
    public String getCommitMethods()
    {
        return m__strCommitMethods;
    }

    /**
     * Specifies the rollback methods.
     * @param rollbackMethods the rollback methods.
     */
    private void inmutableSetRollbackMethods(final String rollbackMethods)
    {
        m__strRollbackMethods = rollbackMethods;
    }

    /**
     * Specifies the rollback methods.
     * @param rollbackMethods the rollback methods.
     */
    protected void setRollbackMethods(final String rollbackMethods)
    {
        inmutableSetRollbackMethods(rollbackMethods);
    }

    /**
     * Retrieves the rollback methods.
     * @return such methods.
     */
    public String getRollbackMethods()
    {
        return m__strRollbackMethods;
    }

    /**
     * Specifies the connection closing methods,
     * @param connectionClosingMethods the connection closing
     * methods.
     */
    private void inmutableSetConnectionClosingMethods(
        String connectionClosingMethods)
    {
        m__strConnectionClosingMethods = connectionClosingMethods;
    }

    /**
     * Specifies the connection closing methods,
     * @param connectionClosingMethods the connection closing
     * methods.
     */
    protected void setConnectionClosingMethods(
        String connectionClosingMethods)
    {
        inmutableSetConnectionClosingMethods(
            connectionClosingMethods);
    }

    /**
     * Retrieves the connection closing methods.
     * @return such methods.
     */
    public String getConnectionClosingMethods()
    {
        return m__strConnectionClosingMethods;
    }

    /**
     * Specifies the transaction token factory methods.
     * @param transactionTokenFactoryMethods such methods.
     */
    private void inmutableSetTransactionTokenFactoryMethods(
        String transactionTokenFactoryMethods)
    {
        m__strTransactionTokenFactoryMethods = transactionTokenFactoryMethods;
    }

    /**
     * Specifies the transaction token factory methods.
     * @param transactionTokenFactoryMethods such methods.
     */
    protected void setTransactionTokenFactoryMethods(
        String transactionTokenFactoryMethods)
    {
        inmutableSetTransactionTokenFactoryMethods(
            transactionTokenFactoryMethods);
    }

    /**
     * Retrieves the transaction token factory methods.
     * @return such methods.
     */
    public String getTransactionTokenFactoryMethods()
    {
        return m__strTransactionTokenFactoryMethods;
    }

    /**
     * Specifies the class end.
     * @param classEnd the new class end.
     */
    private void inmutableSetClassEnd(final String classEnd)
    {
        m__strClassEnd = classEnd;
    }

    /**
     * Specifies the class end.
     * @param classEnd the new class end.
     */
    protected void setClassEnd(final String classEnd)
    {
        inmutableSetClassEnd(classEnd);
    }

    /**
     * Retrieves the class end.
     * @return such information.
     */
    public String getClassEnd() 
    {
        return m__strClassEnd;
    }

    /**
     * Retrieves the source code generated by this template.
     * @return such code.
     */
    public String toString()
    {
        StringBuffer t_sbResult = new StringBuffer();

        StringUtils t_StringUtils = StringUtils.getInstance();

        if  (t_StringUtils != null)
        {
            t_sbResult.append(getHeader());

            MessageFormat t_PackageDeclarationFormatter =
                new MessageFormat(getPackageDeclaration());

            t_sbResult.append(
                t_PackageDeclarationFormatter.format(
                    new Object[]{getPackageName()}));

            t_sbResult.append(getAcmslImports());
            t_sbResult.append(getJdkImports());
            t_sbResult.append(getJdkExtensionImports());
            t_sbResult.append(getLoggingImports());

            t_sbResult.append(getJavadoc());

            t_sbResult.append(getClassDefinition());

            t_sbResult.append(getClassStart());

            t_sbResult.append(getClassConstructor());

            t_sbResult.append(getAttributeAccessors());
            t_sbResult.append(getConnectionRetrievalMethods());
            t_sbResult.append(getCommitMethods());
            t_sbResult.append(getRollbackMethods());
            t_sbResult.append(getConnectionClosingMethods());
            t_sbResult.append(getTransactionTokenFactoryMethods());

            t_sbResult.append(getClassEnd());
        }

        return t_sbResult.toString();
    }
}
