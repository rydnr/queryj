/*
                        QueryJ Test

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
 * Filename: ForeignKeyTestHelper.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Helper class for per-foreign key Cucumber tests.
 *
 * Date: 2014/04/15
 * Time: 21:11
 *
 */
package org.acmsl.queryj.test;

/*
 * Importing Cucumber classes.
 */
import cucumber.api.DataTable;

/*
 * Importing QueryJ Core classes.
 */
import org.acmsl.queryj.api.exceptions.AttributeNotAvailableException;
import org.acmsl.queryj.metadata.vo.Attribute;
import org.acmsl.queryj.metadata.vo.ForeignKey;
import org.acmsl.queryj.metadata.vo.ForeignKeyValueObject;
import org.acmsl.queryj.metadata.vo.Table;

/*
 * Importing JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/*
 * Importing JDK classes.
 */
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Helper class for per-foreign key Cucumber tests.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2014/04/15 21:11
 */
@ThreadSafe
public class ForeignKeyTestHelper
{
    /**
     * Singleton implementation to avoid double-locking check.
     */
    protected static final class ForeignKeyTestHelperSingletonContainer
    {
        /**
         * The actual singleton.
         */
        public static final ForeignKeyTestHelper SINGLETON = new ForeignKeyTestHelper();
    }

    /**
     * Retrieves the singleton instance.
     * @return such instance.
     */
    @NotNull
    public static ForeignKeyTestHelper getInstance()
    {
        return ForeignKeyTestHelperSingletonContainer.SINGLETON;
    }

    /**
     * Defines a foreign key based on the Cucumber information given.
     * @param fkInfo the Cucumber table.
     * @param foreignKeys the list to fill with the foreign keys.
     * @param tables the table information (needed to provide additional
     * metadata about the foreign key attributes.
     */
    public void defineInputForeignKey(
        @NotNull final DataTable fkInfo,
        @NotNull final List<ForeignKey<String>> foreignKeys,
        @NotNull final Map<String, Table<String, Attribute<String>, List<Attribute<String>>>> tables)
    {
        @NotNull final List<Map<String, String>> fkEntries = fkInfo.asMaps(String.class, String.class);

        @Nullable ForeignKey<String> foreignKey;

        for (@NotNull final Map<String, String> fkEntry : fkEntries)
        {
            foreignKey = convertToForeignKey(fkEntry, tables);

            if (foreignKey != null)
            {
                foreignKeys.add(foreignKey);
            }
        }
    }

    /**
     * Converts given foreign key described in a Cucumber feature, to a {@link ForeignKey}.
     * @param fkEntry the Cucumber information.
     * @param tables the table information (needed to provide additional
     * metadata about the foreign key attributes.
     * @return the {@link ForeignKey}.
     */
    @Nullable
    protected ForeignKey<String> convertToForeignKey(
        @NotNull final Map<String, String> fkEntry,
        @NotNull final Map<String, Table<String, Attribute<String>, List<Attribute<String>>>> tables)
    {
        @Nullable final ForeignKey<String> result;

        @Nullable final String sourceTable = fkEntry.get("source");
        @Nullable final String sourceColumns = fkEntry.get("column(s)");

        @Nullable final String targetTable = fkEntry.get("target");
        @Nullable final boolean allowsNull = Boolean.valueOf(fkEntry.get("allows null"));
        @NotNull final List<Attribute<String>> columns = fromCsv(sourceColumns, sourceTable, tables);

        if (   (sourceTable != null)
            && (columns.size() > 0)
            && (targetTable != null))
        {
            result =
                new ForeignKeyValueObject(
                    sourceTable, columns, targetTable, allowsNull);
        }
        else
        {
            result = null;
        }

        return result;
    }

    /**
     * Parses given list of columns to a list of {@link Attribute}s.
     * @param sourceColumns the column names.
     * @param sourceTable the source table.
     * @param tables the table information (needed to provide additional
     * metadata about the foreign key attributes.
     * @return the attribute list.
     */
    @NotNull
    protected List<Attribute<String>> fromCsv(
        @Nullable final String sourceColumns,
        @Nullable final String sourceTable,
        @NotNull final Map<String, Table<String, Attribute<String>, List<Attribute<String>>>> tables)
    {
        @NotNull final List<Attribute<String>> result = new ArrayList<>();

        if (sourceTable != null)
        {
            @NotNull final StringTokenizer tokenizer =
                new StringTokenizer(sourceColumns, ",");

            while (tokenizer.hasMoreTokens())
            {
                @NotNull final String attributeName = tokenizer.nextToken();

                @Nullable final Table<String, Attribute<String>, List<Attribute<String>>> table =
                    tables.get(sourceTable);

                @Nullable final Attribute<String> attribute =
                    table == null
                    ? createLazyAttribute(attributeName, sourceTable, tables)
                    : findAttribute(attributeName, table);

                if (attribute != null)
                {
                    result.add(attribute);
                }
            }
        }

        return result;
    }

    protected Attribute<String> createLazyAttribute(
        @NotNull final String attributeName,
        @NotNull final String sourceTable,
        @NotNull final Map<String, Table<String, Attribute<String>, List<Attribute<String>>>> tables)

    {
        return
            new Attribute<String>()
            {
                @NotNull
                protected Attribute<String> resolveAttribute()
                {
                    @Nullable final Attribute<String> result = findAttribute(attributeName, tables.get(sourceTable));

                    if (result == null)
                    {
                        throw new AttributeNotAvailableException(attributeName, sourceTable);
                    }

                    return result;
                }

                @NotNull
                @Override
                public String getName()
                {
                    return attributeName;
                }

                @Override
                public int getTypeId()
                {
                    final int result;

                    @NotNull final Attribute<String> attribute = resolveAttribute();

                    result = attribute.getTypeId();

                    return result;
                }

                @NotNull
                @Override
                public String getType()
                {
                    @NotNull final String result;

                    @NotNull final Attribute<String> attribute = resolveAttribute();

                    result = attribute.getType();

                    return result;
                }

                @NotNull
                @Override
                public String getTableName()
                {
                    return sourceTable;
                }

                @Nullable
                @Override
                public String getComment()
                {
                    @Nullable final String result;

                    @NotNull final Attribute<String> attribute = resolveAttribute();

                    result = attribute.getComment();

                    return result;
                }

                @Override
                public boolean isExternallyManaged()
                {
                    final boolean result;

                    @NotNull final Attribute<String> attribute = resolveAttribute();

                    result = attribute.isExternallyManaged();

                    return result;
                }

                @Nullable
                @Override
                public String getKeyword()
                {
                    @Nullable final String result;

                    @NotNull final Attribute<String> attribute = resolveAttribute();

                    result = attribute.getKeyword();

                    return result;
                }

                @Nullable
                @Override
                public String getRetrievalQuery()
                {
                    @Nullable final String result;

                    @NotNull final Attribute<String> attribute = resolveAttribute();

                    result = attribute.getRetrievalQuery();

                    return result;
                }

                @Override
                public boolean isNullable()
                {
                    final boolean result;

                    @NotNull final Attribute<String> attribute = resolveAttribute();

                    result = attribute.isNullable();

                    return result;
                }

                @Nullable
                @Override
                public String getValue()
                {
                    @Nullable final String result;

                    @NotNull final Attribute<String> attribute = resolveAttribute();

                    result = attribute.getValue();

                    return result;
                }

                @Override
                public boolean isReadOnly()
                {
                    final boolean result;

                    @NotNull final Attribute<String> attribute = resolveAttribute();

                    result = attribute.isReadOnly();

                    return result;
}

                @Override
                public boolean isBoolean()
                {
                    final boolean result;

                    @NotNull final Attribute<String> attribute = resolveAttribute();

                    result = attribute.isBoolean();

                    return result;
                }

                @Nullable
                @Override
                public String getBooleanTrue()
                {
                    @Nullable final String result;

                    @NotNull final Attribute<String> attribute = resolveAttribute();

                    result = attribute.getBooleanTrue();

                    return result;
                }

                @Nullable
                @Override
                public String getBooleanFalse()
                {
                    @Nullable final String result;

                    @NotNull final Attribute<String> attribute = resolveAttribute();

                    result = attribute.getBooleanFalse();

                    return result;
                }

                @Nullable
                @Override
                public String getBooleanNull()
                {
                    @Nullable final String result;

                    @NotNull final Attribute<String> attribute = resolveAttribute();

                    result = attribute.getBooleanNull();

                    return result;
                }

                @Override
                public int getOrdinalPosition()
                {
                    final int result;

                    @NotNull final Attribute<String> attribute = resolveAttribute();

                    result = attribute.getOrdinalPosition();

                    return result;
                }

                @Override
                public int getLength()
                {
                    final int result;

                    @NotNull final Attribute<String> attribute = resolveAttribute();

                    result = attribute.getLength();

                    return result;
                }

                @Override
                public int getPrecision()
                {
                    final int result;

                    @NotNull final Attribute<String> attribute = resolveAttribute();

                    result = attribute.getPrecision();

                    return result;
                }

                @Nullable
                @Override
                public String getSequence()
                {
                    @Nullable final String result;

                    @NotNull final Attribute<String> attribute = resolveAttribute();

                    result = attribute.getSequence();

                    return result;
                }

                @Override
                public int compareTo(final Attribute<String> o)
                {
                    final int result;

                    @NotNull final Attribute<String> attribute = resolveAttribute();

                    result = attribute.compareTo(o);

                    return result;
                }
            };
    }

    /**
     * Retrieves the attribute matching given name.
     * @param name the name.
     * @param table the table.
     * @return such attribute, or {@code null} if not found.
     */
    @Nullable
    protected Attribute<String> findAttribute(
        @NotNull final String name,
        @Nullable final Table<String, Attribute<String>, List<Attribute<String>>> table)
    {
        @Nullable Attribute<String> result = null;

        if (table != null)
        {
            for (@Nullable final Attribute<String> attribute : table.getAttributes())
            {
                if (   (attribute != null)
                    && (attribute.getName().equals(name)))
                {
                    result = attribute;
                    break;
                }
            }
        }

        return result;
    }
}
