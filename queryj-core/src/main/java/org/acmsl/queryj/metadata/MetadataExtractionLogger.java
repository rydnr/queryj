/*
                        QueryJ

    Copyright (C) 2002-today  Jose San Leandro Armendariz
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
    Contact info: jose.sanleandro@acm-sl.com

 ******************************************************************************
 *
 * Filename: MetadataExtractionLogger.java
 *
 * Author: Jose San Leandro Armendariz (chous)
 *
 * Description: 
 *
 * Date: 6/8/12
 * Time: 4:44 PM
 *
 */
package org.acmsl.queryj.metadata;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.metadata.vo.Attribute;
import org.acmsl.queryj.metadata.vo.ForeignKey;

/*
 * Importing some ACM-SL Commons classes.
 */
import org.acmsl.commons.logging.UniqueLogFactory;

/*
 * Importing some Apache Commons-Logging classes.
 */
import org.apache.commons.logging.Log;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing some JDK classes.
 */
import java.util.List;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * Basic implementation of {@link MetadataExtractionListener} that
 * logs all events it gets.
 * @author <a href="mailto:chous@acm-sl.org">chous</a>
 * @since 2012/06/08
 */
@ThreadSafe
public class MetadataExtractionLogger
    implements MetadataExtractionListener
{
    @Nullable
    final static Log LOG = UniqueLogFactory.getLog(MetadataExtractionLogger.class);

    private static final long serialVersionUID = 8177528056282777278L;

    /**
     * Logs a debug message.
     * @param message the message to log as debug.
     */
    protected void debug(@NotNull final String message)
    {
        @Nullable final Log t_Log = LOG;

        if (t_Log != null)
        {
            t_Log.debug(message);
        }
    }

    /**
     * Notifies all table name extraction have been started.
     */
    @Override
    public void tableNamesExtractionStarted()
    {
        debug("table names extraction started");
    }

    /**
     * Notifies all table names have been extracted.
     */
    @Override
    public void tableNamesExtracted(final int count)
    {
        debug("A total of " + count + " tables have been extracted");
    }

    /**
     * Notifies the table extraction has been started.
     *
     * @param table the table.
     */
    @Override
    public void tableExtractionStarted(@NotNull final String table)
    {
        debug("table " + table + " extraction started");
    }

    /**
     * Notifies the table extraction has been completed.
     *
     * @param table the table.
     */
    @Override
    public void tableExtracted(@NotNull final String table)
    {
        debug("Table " + table + " extracted");
    }

    /**
     * Notifies table metadata extraction has been started.
     */
    @Override
    public void tableMetadataExtractionStarted()
    {
        debug("table metadata extraction started");
    }

    /**
     * Notifies table metadata extraction has been started.
     */
    @Override
    public void tableMetadataExtracted()
    {
        debug("table metadata extracted");
    }

    /**
     * Notifies the table comment extraction has been started.
     *
     * @param table the table.
     */
    @Override
    public void tableCommentExtractionStarted(@NotNull final String table)
    {
        debug("comments for table " + table + " extracted");
    }

    /**
     * Notifies a table comment has been extracted.
     *
     * @param table   the table name.
     * @param comment the comment.
     */
    @Override
    public void tableCommentExtracted(@NotNull final String table, @NotNull final String comment)
    {
        debug("comment for table " + table + " is " + comment);
    }

    /**
     * Notifies column name extraction has been started.
     *
     * @param table the table.
     */
    @Override
    public void columnNamesExtractionStarted(@NotNull final String table)
    {
        debug("column names extraction started");
    }

    /**
     * Notifies all column names have been extracted.
     *
     * @param table the table name.
     * @param count the count.
     */
    @Override
    public void columnNamesExtracted(@NotNull final String table, final int count)
    {
        debug("columns (" + count + ") for " + table + " extracted");
    }

    /**
     * Notifies column comment extraction has been started.
     *
     * @param table the table.
     */
    @Override
    public void columnCommentsExtractionStarted(@NotNull final String table)
    {
        debug("Comments for " + table + " columns extraction started");
    }

    /**
     * Notifies a column comment has been extracted.
     *
     * @param table   the table name.
     * @param column  the column name.
     * @param comment the column comment.
     */
    @Override
    public void columnCommentExtracted(
        @NotNull final String table,
        @NotNull final String column,
        @NotNull final String comment)
    {
        debug("Comment for " + table + "." + column + " is " + comment);
    }

    /**
     * Notifies all column comments have been extracted.
     *
     * @param table the table name.
     */
    @Override
    public void columnCommentsExtracted(@NotNull final String table)
    {
        debug("Columns extracted for table " + table);
    }

    /**
     * Notifies column type extraction has been started.
     *
     * @param table the table.
     */
    @Override
    public void columnTypesExtractionStarted(@NotNull final String table)
    {
        debug("column types for " + table + " extracted");
    }

    /**
     * Notifies all column types have been extracted.
     *
     * @param table the table name.
     */
    @Override
    public void columnTypesExtracted(@NotNull final String table)
    {
        debug("Column types for " + table + " extracted");
    }

    /**
     * Notifies column nullable setting extraction has been extracted.
     *
     * @param table the table.
     */
    @Override
    public void columnNullablesExtractionStarted(@NotNull final String table)
    {
        debug("Extraction of Nullable information for table " + table + " extracted");
    }

    /**
     * Notifies all column nullable settings have been extracted.
     *
     * @param table the table name.
     */
    @Override
    public void columnNullablesExtracted(@NotNull final String table)
    {
        debug("Extraction of nullable information for table " + table + " completed");
    }

    /**
     * Notifies a primary key has been extracted.
     *
     * @param table      the table name.
     * @param primaryKey the primary key.
     */
    @Override
    public void primaryKeyExtracted(@NotNull final String table, @NotNull final List<Attribute> primaryKey)
    {
        debug("Primary key for table " + table + " extracted");
    }

    /**
     * Notifies a primary key extraction has been started.
     */
    @Override
    public void primaryKeyExtractionStarted()
    {
        debug("Primary key extraction started");
    }

    /**
     * Notifies all primary keys have been extracted.
     */
    @Override
    public void allPrimaryKeysExtracted()
    {
        debug("Primary keys extracted");
    }

    /**
     * Notifies a foreign key extraction has been started.
     */
    @Override
    public void foreignKeyExtractionStarted()
    {
        debug("Foreign key extraction started");
    }

    /**
     * Notifies a foreign key has been extracted.
     * @param foreignKey  the foreign key.
     */
    @Override
    public void foreignKeyExtracted(@NotNull final ForeignKey foreignKey)
    {
        debug(
              "Foreign key ("
            + foreignKey.getSourceTableName() + " -> " + foreignKey.getTargetTableName()
            + ") extracted");
    }

    /**
     * Notifies all foreign keys have been extracted.
     */
    @Override
    public void allForeignKeysExtracted()
    {
        debug("All foreign keys extracted");
    }
}
