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
 * Description: Defines the default subtemplates to create abstract base
 *              JDBC DAO.
 *
<<<<<<< JdbcDAOTemplateDefaults.java
=======
 * Last modified by: $Author$ at $Date$
 *
 * File version: $Revision$
 *
 * Project version: $Name$
 *
 * $Id$
 *
>>>>>>> 1.3
 */
package org.acmsl.queryj.tools.templates.dao;

/*
 * Importing some project-specific classes.
 */
import org.acmsl.queryj.tools.templates.JavaTemplateDefaults;

/**
 * Defines the default subtemplates to create abstract base JDBC DAO.
 * @author <a href="mailto:jsanleandro@yahoo.es"
 *         >Jose San Leandro</a>
<<<<<<< JdbcDAOTemplateDefaults.java
=======
 * @version $Revision$
>>>>>>> 1.3
 */
public interface JdbcDAOTemplateDefaults
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
}
