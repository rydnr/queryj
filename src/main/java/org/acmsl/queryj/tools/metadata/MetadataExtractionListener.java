/*
                        QueryJ

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
package org.acmsl.queryj.tools.metadata;

/*
 * Importing some ACM-SL Commons classes.
 */
//import org.acmsl.commons.patterns.Listener;

/**
 * Receives notifications on metadata extraction.
 * @author <a href="mailto:chous@acm-sl.org"
           >Jose San Leandro</a>
 */
public interface MetadataExtractionListener
//    extends  Listener
{
    /**
     * Notifies all table name extraction have been started.
     */
    public void tableNamesExtractionStarted();

    /**
     * Notifies a table name has been extracted.
     * @param table the table name.
     */
    public void tableNameExtracted(final String table);

    /**
     * Notifies all table names have been extracted.
     */
    public void tableNamesExtracted(final int count);

    /**
     * Notifies the table extraction has been started.
     * @param table the table.
     */
    public void tableExtractionStarted(final String table);

    /**
     * Notifies the table extraction has been completed.
     * @param table the table.
     */
    public void tableExtracted(final String table);

    /**
     * Notifies table metadata extraction has been started.
     */
    public void tableMetadataExtractionStarted();

    /**
     * Notifies the table comment extraction has been started.
     * @param table the table.
     */
    public void tableCommentExtractionStarted(final String table);

    /**
     * Notifies a table comment has been extracted.
     * @param table the table name.
     * @param comment the comment.
     */
    public void tableCommentExtracted(
        final String table, final String comment);

    /**
     * Notifies all table comments have been extracted.
     */
    public void tableCommentsExtracted();

    /**
     * Notifies column name extraction has been started.
     * @param table the table.
     */
    public void columnNamesExtractionStarted(final String table);

    /**
     * Notifies a column name has been extracted.
     * @param table the table name.
     * @param column the column name.
     */
    public void columnNameExtracted(
        final String table, final String column);

    /**
     * Notifies all column names have been extracted.
     * @param table the table name.
     * @param count the count.
     */
    public void columnNamesExtracted(final String table, final int count);

    /**
     * Notifies column comment extraction has been started.
     * @param table the table.
     */
    public void columnCommentsExtractionStarted(final String table);

    /**
     * Notifies a column comment has been extracted.
     * @param table the table name.
     * @param column the column name.
     * @param comment the column comment.
     */
    public void columnCommentExtracted(
        final String table, final String column, final String comment);

    /**
     * Notifies all column comments have been extracted.
     * @param table the table name.
     */
    public void columnCommentsExtracted(final String table);

    /**
     * Notifies column type extraction has been started.
     * @param table the table.
     */
    public void columnTypesExtractionStarted(final String table);

    /**
     * Notifies a column type has been extracted.
     * @param table the table name.
     * @param column the column name.
     * @param type the column type.
     */
    public void columnTypeExtracted(
        final String table, final String column, final int type);

    /**
     * Notifies all column types have been extracted.
     * @param table the table name.
     */
    public void columnTypesExtracted(final String table);

    /**
     * Notifies column nullable setting extraction has been extracted.
     * @param table the table.
     */
    public void columnNullablesExtractionStarted(final String table);

    /**
     * Notifies a column nullable setting has been extracted.
     * @param table the table name.
     * @param column the column name.
     * @param nullable whether the column allows null.
     */
    public void columnNullableExtracted(
        final String table, final String column, final boolean nullable);

    /**
     * Notifies all column nullable settings have been extracted.
     * @param table the table name.
     */
    public void columnNullablesExtracted(final String table);

    /**
     * Notifies a primary key extraction has been started.
     */
    public void primaryKeyExtractionStarted();

    /**
     * Notifies a primary key has been extracted.
     * @param table the table name.
     * @param primaryKey the primary key.
     */
    public void primaryKeyExtracted(
        final String table, final String[] primaryKey);

    /**
     * Notifies all primary keys have been extracted.
     */
    public void allPrimaryKeysExtracted();

    /**
     * Notifies a foreign key extraction has been started.
     */
    public void foreignKeyExtractionStarted();

    /**
     * Notifies a foreign key has been extracted.
     * @param table the table name.
     * @param targetTable the target table.
     * @param foreignKey the foreign key.
     */
    public void foreignKeyExtracted(
        final String table,
        final String targetTable,
        final String[] foreignKey);

    /**
     * Notifies all foreign keys for a given table have been extracted.
     * @param table the table name.
     */
    public void foreignKeysExtracted(final String table);

    /**
     * Notifies all foreign keys have been extracted.
     */
    public void allForeignKeysExtracted();

    /**
     * Notifies the procedures extraction has been started.
     */
    public void procedureExtractionStarted();

    /**
     * Notifies a procedure metadata has been extracted.
     * @param name the procedure name.
     * @param type the type.
     * @param comment the comment.
     */
    public void procedureMetadataExtracted(
        final String name, final int type, final String comment);

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
        final int nullable);

    /**
     * Notifies a procedure has been extracted.
     * @param name the procedure name.
     * @param parameters the parameters.
     */
    public void procedureExtracted(
        final String name, final ProcedureParameterMetadata[] parameters);

    /**
     * Notifies all procedures have been extracted.
     */
    public void allProceduresExtracted();
}
