/*
                        QueryJ Core

    Copyright (C) 2001-today  Jose San Leandro Armendariz
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
package org.acmsl.queryj.metadata;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.metadata.vo.Attribute;
import org.acmsl.queryj.metadata.vo.ForeignKey;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/*
 * Importing some JDK classes.
 */
import java.io.Serializable;
import java.util.List;

/**
 * Receives notifications on metadata extraction.
 * @author <a href="mailto:chous@acm-sl.org"
           >Jose San Leandro</a>
 */
public interface MetadataExtractionListener
    extends Serializable
{
    /**
     * Notifies all table name extraction have been started.
     */
    @SuppressWarnings("unused")
    public void tableNamesExtractionStarted();

    /**
     * Notifies all table names have been extracted.
     * @param count the count.
     */
    public void tableNamesExtracted(final int count);

    /**
     * Notifies the table extraction has been started.
     * @param table the table.
     */
    @SuppressWarnings("unused")
    public void tableExtractionStarted(@NotNull final String table);

    /**
     * Notifies the table extraction has been completed.
     * @param table the table.
     */
    @SuppressWarnings("unused")
    public void tableExtracted(@NotNull final String table);

    /**
     * Notifies table metadata extraction has been started.
     */
    public void tableMetadataExtractionStarted();

    /**
     * Notifies table metadata extraction has been started.
     */
    public void tableMetadataExtracted();

    /**
     * Notifies the table comment extraction has been started.
     * @param table the table.
     */
    @SuppressWarnings("unused")
    public void tableCommentExtractionStarted(@NotNull final String table);

    /**
     * Notifies a table comment has been extracted.
     * @param table the table name.
     * @param comment the comment.
     */
    @SuppressWarnings("unused")
    public void tableCommentExtracted(
        @NotNull final String table, @NotNull final String comment);

    /**
     * Notifies column name extraction has been started.
     * @param table the table.
     */
    @SuppressWarnings("unused")
    public void columnNamesExtractionStarted(@NotNull final String table);

    /**
     * Notifies all column names have been extracted.
     * @param table the table name.
     * @param count the count.
     */
    @SuppressWarnings("unused")
    public void columnNamesExtracted(@NotNull final String table, final int count);

    /**
     * Notifies column comment extraction has been started.
     * @param table the table.
     */
    @SuppressWarnings("unused")
    public void columnCommentsExtractionStarted(@NotNull final String table);

    /**
     * Notifies a column comment has been extracted.
     * @param table the table name.
     * @param column the column name.
     * @param comment the column comment.
     */
    @SuppressWarnings("unused")
    public void columnCommentExtracted(
        @NotNull final String table, @NotNull final String column, @NotNull final String comment);

    /**
     * Notifies all column comments have been extracted.
     * @param table the table name.
     */
    @SuppressWarnings("unused")
    public void columnCommentsExtracted(@NotNull final String table);

    /**
     * Notifies column type extraction has been started.
     * @param table the table.
     */
    @SuppressWarnings("unused")
    public void columnTypesExtractionStarted(@NotNull final String table);

    /**
     * Notifies all column types have been extracted.
     * @param table the table name.
     */
    @SuppressWarnings("unused")
    public void columnTypesExtracted(@NotNull final String table);

    /**
     * Notifies column nullable setting extraction has been extracted.
     * @param table the table.
     */
    @SuppressWarnings("unused")
    public void columnNullablesExtractionStarted(@NotNull final String table);

    /**
     * Notifies all column nullable settings have been extracted.
     * @param table the table name.
     */
    @SuppressWarnings("unused")
    public void columnNullablesExtracted(@NotNull final String table);

    /**
     * Notifies a primary key extraction has been started.
     */
    @SuppressWarnings("unused")
    public void primaryKeyExtractionStarted();

    /**
     * Notifies a primary key has been extracted.
     * @param table the table name.
     * @param primaryKey the primary key.
     */
    @SuppressWarnings("unused")
    public void primaryKeyExtracted(
        @NotNull final String table, @NotNull final List<Attribute<String>> primaryKey);

    /**
     * Notifies all primary keys have been extracted.
     */
    @SuppressWarnings("unused")
    public void allPrimaryKeysExtracted();

    /**
     * Notifies a foreign key extraction has been started.
     */
    @SuppressWarnings("unused")
    public void foreignKeyExtractionStarted();

    /**
     * Notifies a foreign key has been extracted.
     * @param foreignKey the foreign key.
     */
    @SuppressWarnings("unused")
    public void foreignKeyExtracted(@NotNull final ForeignKey<String> foreignKey);

    /**
     * Notifies all foreign keys have been extracted.
     */
    @SuppressWarnings("unused")
    public void allForeignKeysExtracted();
}
