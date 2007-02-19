//;-*- mode: java -*-
/*
                        QueryJ

    Copyright (C) 2001-2007  Jose San Leandro Armendariz
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
 * Filename: MetadataExtractionListener.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Receives notifications on metadata extraction.
 *
 */
package org.acmsl.queryj.tools.metadata;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.tools.metadata.MetadataManager;
import org.acmsl.queryj.tools.SqlTypeResolver;

/*
 * Importing some ACM-SL Commons classes.
 */
import org.acmsl.commons.logging.UniqueLogFactory;
import org.acmsl.commons.utils.StringUtils;

/**
 * Importing some Apache Commons Logging classes.
 */
import org.apache.commons.logging.Log;

/*
 * Importing some JDK classes.
 */
import java.util.Arrays;
import java.util.Collection;

/**
 * Receives notifications on metadata extraction.
 * @author <a href="mailto:chous@acm-sl.org"
           >Jose San Leandro</a>
 */
public class MetadataExtractionLoggingListener
    implements  MetadataExtractionListener
{
    /**
     * The log constant (the aim of this class).
     */
    protected static final Log LOG = 
        UniqueLogFactory.getLog(MetadataExtractionLoggingListener.class);

    /**
     * Singleton implemented to avoid double-checked locking.
     */
    protected static class MetadataExtractionLoggingListenerSingletonContainer
    {
        /**
         * The actual singleton.
         */
        public static final MetadataExtractionListener SINGLETON =
            new MetadataExtractionLoggingListener();
    }

    /**
     * Retrieves the <code>MetadataExtractionLoggingListener</code> instance.
     * @return such instance.
     */
    public static MetadataExtractionListener getInstance()
    {
        return MetadataExtractionLoggingListenerSingletonContainer.SINGLETON;
    }

    /**
     * Notifies all table name extraction have been started.
     * @param count the table count.
     */
    public void tableNamesExtractionStarted()
    {
        logInfo("Extracting table names...");
    }

    /**
     * Notifies a table name has been extracted.
     * @param table the table name.
     */
    public void tableNameExtracted(final String table)
    {
        logDebug("  Extracting " + table + " name.");
    }

    /**
     * Notifies all table names have been extracted.
     * @param count the table count.
     */
    public void tableNamesExtracted(final int count)
    {
        logInfo(count + " tables found.");
    }

    /**
     * Notifies the table extraction has been started.
     * @param table the table.
     */
    public void tableExtractionStarted(final String table)
    {
        logInfo("  Extracting " + table + "...");
    }

    /**
     * Notifies the table extraction has been completed.
     * @param table the table.
     */
    public void tableExtracted(final String table)
    {
        logInfo("  Done.");
    }

    /**
     * Notifies the table comment extraction has been started.
     * @param table the table.
     */
    public void tableCommentExtractionStarted(final String table)
    {
        logDebug("    Extracting " + table + " comment...");
    }

    /**
     * Notifies table metadata extraction has been started.
     */
    public void tableMetadataExtractionStarted()
    {
        logInfo("Extracting table metadata...");
    }

    /**
     * Notifies a table comment has been extracted.
     * @param table the table name.
     * @param comment the comment.
     */
    public void tableCommentExtracted(
        final String table, final String comment)
    {
        logDebug("    Comment for " + table + " extracted: " + comment);
    }

    /**
     * Notifies all table comments have been extracted.
     */
    public void tableCommentsExtracted()
    {
        logInfo("  Done.");
    }

    /**
     * Notifies column name extraction has been started.
     * @param table the table.
     */
    public void columnNamesExtractionStarted(final String table)
    {
        logInfo("    Extracting " + table + " column names...");
    }

    /**
     * Notifies a column name has been extracted.
     * @param table the table name.
     * @param column the column name.
     */
    public void columnNameExtracted(
        final String table, final String column)
    {
        logDebug("    " + table + " column extracted: " + column);
    }


    /**
     * Notifies all column names have been extracted.
     * @param table the table name.
     * @param count the count.
     */
    public void columnNamesExtracted(final String table, final int count)
    {
        logDebug("    " + count + " columns found for " + table + ".");
    }

    /**
     * Notifies column comment extraction has been started.
     * @param table the table.
     */
    public void columnCommentsExtractionStarted(final String table)
    {
        logInfo("    Extracting " + table + " column comments...");
    }

    /**
     * Notifies a column comment has been extracted.
     * @param table the table name.
     * @param column the column name.
     * @param comment the column comment.
     */
    public void columnCommentExtracted(
        final String table, final String column, final String comment)
    {
        logDebug("    Comment for " + table + "." + column + ": " + comment);
    }

    /**
     * Notifies all column comments have been extracted.
     * @param table the table name.
     */
    public void columnCommentsExtracted(final String table)
    {
        logDebug("    Column comments for " + table + " extracted.");
    }

    /**
     * Notifies column type extraction has been started.
     */
    public void columnTypesExtractionStarted(final String table)
    {
        logInfo("    Extracting " + table + " column types...");
    }

    /**
     * Notifies a column type has been extracted.
     * @param table the table name.
     * @param column the column name.
     * @param type the column type.
     */
    public void columnTypeExtracted(
        final String table, final String column, final int type)
    {
        columnTypeExtracted(
            table, column, type, SqlTypeResolver.getInstance());
    }

    /**
     * Notifies a column type has been extracted.
     * @param table the table name.
     * @param column the column name.
     * @param type the column type.
     * @param sqlTypeResolver the <code>SqlTypeResolver</code> instance.
     * @precondition sqlTypeResolver != null
     */
    protected void columnTypeExtracted(
        final String table,
        final String column,
        final int type,
        final SqlTypeResolver sqlTypeResolver)
    {
        logDebug(
              "    Type for " + table + "." + column + ": "
            + type + "/" + sqlTypeResolver.resolve(type));
    }

    /**
     * Notifies all column types have been extracted.
     * @param table the table name.
     */
    public void columnTypesExtracted(final String table)
    {
        logDebug("    Column types for " + table + " extracted.");
    }

    /**
     * Notifies column nullable setting extraction has been extracted.
     * @param table the table.
     */
    public void columnNullablesExtractionStarted(final String table)
    {
        logInfo("    Extracting " + table + " column nullable settings...");
    }

    /**
     * Notifies a column nullable setting has been extracted.
     * @param table the table name.
     * @param column the column name.
     * @param nullable whether the column allows null.
     */
    public void columnNullableExtracted(
        final String table, final String column, final boolean nullable)
    {
        logDebug(
            "    Does " + table + "." + column + " allow nulls? " + nullable);
    }

    /**
     * Notifies all column nullable settings have been extracted.
     * @param table the table name.
     */
    public void columnNullablesExtracted(final String table)
    {
        logDebug("    Column types for " + table + " extracted.");
    }

    /**
     * Notifies a primary key extraction has been started.
     */
    public void primaryKeyExtractionStarted()
    {
        logInfo("Extracting primary keys...");
    }

    /**
     * Notifies a primary key has been extracted.
     * @param table the table name.
     * @param primaryKey the primary key.
     */
    public void primaryKeyExtracted(
        final String table, final String[] primaryKey)
    {
        logDebug(
              "  Primary key for " + table + ": ["
            + concatenate(primaryKey, ", ", StringUtils.getInstance()) + "]");
    }

    /**
     * Notifies all primary keys have been extracted.
     * @param table the table name.
     */
    public void allPrimaryKeysExtracted()
    {
        logInfo("Done.");
    }

    /**
     * Notifies a foreign key extraction has been started.
     */
    public void foreignKeyExtractionStarted()
    {
        logInfo("Extracting foreign keys...");
    }

    /**
     * Notifies a foreign key has been extracted.
     * @param table the table name.
     * @param targetTable the target table.
     * @param foreignKey the foreign key.
     */
    public void foreignKeyExtracted(
        final String table,
        final String targetTable,
        final String[] foreignKey)
    {
        logDebug(
              "  Foreign key from " + table + " ["
            + concatenate(foreignKey, ", ", StringUtils.getInstance())
            + "] to " + targetTable + " extracted.");
    }

    /**
     * Notifies all foreign keys for a given table have been extracted.
     * @param table the table name.
     */
    public void foreignKeysExtracted(final String table)
    {
        logDebug("  Foreign keys for " + table + " extracted.");
    }

    /**
     * Notifies all foreign keys have been extracted.
     */
    public void allForeignKeysExtracted()
    {
        logInfo("Done.");
    }

    /**
     * Notifies the proccedures extraction has been started.
     */
    public void procedureExtractionStarted()
    {
        logInfo("Extracting procedures...");
    }

    /**
     * Notifies a proccedure metadata has been extracted.
     * @param name the procedure name.
     * @param type the type.
     * @param comment the comment.
     */
    public void procedureMetadataExtracted(
        final String name, final int type, final String comment)
    {
        logDebug(
              "  Procedure metadata extracted."
            + " Name: " + name
            + " Type: " + type
            + " Comment: " + comment);
    }

    /**
     * Notifies a procedure parameter metadata has been extracted.
     * @param columnName the column name.
     * @param columnType the column type.
     * @param comment the comment.
     * @param dataType the data type.
     * @param length the length.
     * @param nullable the nullable attribute.
     */
    public void procedureParameterMetadataExtracted(
        final String columnName,
        final int columnType,
        final String comment,
        final int dataType,
        final int length,
        final int nullable)
    {
        logDebug(
              "    Procedure parameter metadata extracted."
            + " Column name: " + columnName
            + " Column type: " + columnType
            + " Comment: " + comment
            + " Data type: " + dataType
            + " Length: " + length
            + " Nullable: " + nullable);
    }

    /**
     * Notifies a proccedure has been extracted.
     * @param name the procedure name.
     * @param parameters the parameters.
     */
    public void procedureExtracted(
        final String name, final ProcedureParameterMetadata[] parameters)
    {
        logDebug("  Procedure " + name + " extracted.");
    }

    /**
     * Notifies all procedures have been extracted.
     */
    public void allProceduresExtracted()
    {
        logInfo("Done.");
    }

    /**
     * Logs given message as <i>debug</i>.
     * @param message the message.
     */
    protected void logDebug(final String message)
    {
        if  (   (LOG != null)
             && (LOG.isDebugEnabled()))
        {
            LOG.debug(message);
        }
    }

    /**
     * Logs given message as <i>info</i>.
     * @param message the message.
     */
    protected void logInfo(final String message)
    {
        if  (   (LOG != null)
             && (LOG.isInfoEnabled()))
        {
            LOG.info(message);
        }
    }

    /**
     * Concatenates given array using a concrete separator.
     * @param array the array.
     * @param separator the separator.
     * @param stringUtils the <code>StringUtils</code> instance.
     * @return the result of the concat operation.
     * @precondition array != null
     * @precondiiton separator != null
     * @precondition stringUtils != null
     */
    protected String concatenate(
        final String[] array,
        final String separator,
        final StringUtils stringUtils)
    {
        Collection t_cAux = Arrays.asList(array);

        return stringUtils.concatenate(t_cAux, separator);
    }
}
