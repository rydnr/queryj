/*
                        queryj

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
 * Filename: AntUtils.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: 
 *
 * Date: 2014/08/20
 * Time: 08:35
 *
 */
package org.acmsl.queryj.tools.ant;

/*
 * Importing QueryJ Core classes.
 */
import org.acmsl.queryj.QueryJCommand;
import org.acmsl.queryj.QueryJCommandWrapper;
import org.acmsl.queryj.api.exceptions.MissingClasspathException;
import org.acmsl.queryj.api.exceptions.MissingExternallyManagedFieldsException;
import org.acmsl.queryj.api.exceptions.MissingTablesException;
import org.acmsl.queryj.api.exceptions.QueryJBuildException;
import org.acmsl.queryj.metadata.MetadataManager;
import org.acmsl.queryj.metadata.MetadataTypeManager;
import org.acmsl.queryj.metadata.vo.Attribute;
import org.acmsl.queryj.metadata.vo.Field;
import org.acmsl.queryj.metadata.vo.Table;
import org.acmsl.queryj.metadata.vo.TableIncompleteValueObject;
import org.acmsl.queryj.tools.handlers.ParameterValidationHandler;

/*
 * Importing Ant classes.
 */
import org.apache.tools.ant.types.Path;

/*
 * Importing NotNull annotations.
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
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 *
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2014/08/20 08:35
 */
@ThreadSafe
public class AntUtils
{
    /**
     * Retrieves the explicitly-defined table names.
     * @param parameters the parameters.
     * @return the array of table names.
     */
    @SuppressWarnings("unused")
    @NotNull
    protected List<Table<String, Attribute<String>, List<Attribute<String>>>> retrieveExplicitTableNames(
        @NotNull final QueryJCommand parameters)
    {
        List<Table<String, Attribute<String>, List<Attribute<String>>>> result = null;

        @Nullable final AntTablesElement t_TablesElement =
            retrieveTablesElement(parameters);

        @Nullable final Iterator<AntTableElement> t_itTableElements;

        @Nullable final Collection<AntTableElement> t_cTableElements;

        if  (t_TablesElement != null)
        {
            t_cTableElements = t_TablesElement.getTables();

            if  (   (t_cTableElements != null)
                    && (t_cTableElements.size() > 0))
            {
                result =
                    new ArrayList<>(
                        t_cTableElements.size());

                t_itTableElements = t_cTableElements.iterator();

                while  (t_itTableElements.hasNext())
                {
                    @NotNull final AntTableElement t_Table = t_itTableElements.next();

                    result.add(convertToTable(t_Table));
                }

//                storeTables(result, parameters);
            }
        }

        if (result == null)
        {
            result = new ArrayList<>(0);
        }

        return result;
    }

    /**
     * Converts to a {@link Table}.
     * @param table the {@link AntTableElement} instance to convert.
     * @return the converted table.
     */
    @NotNull
    protected Table<String, Attribute<String>, List<Attribute<String>>> convertToTable(
        @NotNull final AntTableElement table)
    {
        @NotNull final TableIncompleteValueObject result =
            new TableIncompleteValueObject(table.getName(), null);

        @Nullable final List<AntFieldElement> t_lFields = table.getFields();

        if (t_lFields != null)
        {
            @NotNull final List<Attribute<String>> t_lAttributes =
                new ArrayList<>(t_lFields.size());

            for (@Nullable final AntFieldElement t_Field : t_lFields)
            {
                if (t_Field != null)
                {
                    t_lAttributes.add(t_Field);
                }
            }

            result.setAttributes(t_lAttributes);
        }

        // TODO: add foreign keys

        return result;
    }

    /**
     * Checks whether there are at least one field in any explicitly-defined table.
     * @param explicitTables the explicitly-defined table information.
     * @return <code>true</code> in such case.
     */
    @SuppressWarnings("unused")
    protected boolean areExplicitTableFieldsEmpty(@Nullable final AntTablesElement explicitTables)
    {
        boolean result = true;

        if (explicitTables != null)
        {
            @Nullable final Iterator<AntTableElement> t_itTableElements;

            @Nullable final Collection<AntTableElement> t_cTableElements = explicitTables.getTables();

            @Nullable Collection<AntFieldElement> t_cFieldElements;

            t_itTableElements = t_cTableElements.iterator();

            while  (t_itTableElements.hasNext())
            {
                @NotNull final AntTableElement t_Table =
                    t_itTableElements.next();

                t_cFieldElements = t_Table.getFields();

                if  (   (t_cFieldElements != null)
                        && (t_cFieldElements.size() > 0))
                {
                    result = false;
                    break;
                }
            }
        }

        return result;
    }

    /**
     * Process explicit schema.
     * @param parameters the parameter map.
     * @param metadataManager the {@link org.acmsl.queryj.metadata.MetadataManager} instance.
     * @param metadataTypeManager the {@link org.acmsl.queryj.metadata.MetadataTypeManager} instance.
     * @param explicitTables the {@link AntTablesElement} instance.
     * @param tableMap the table map.
     * @param tableNameMap the table name map.
     * @param fieldMap a map to store field information.
     * @param fieldFkMap the field fk map.
     * @param fieldNameMap the field name map.
     * @param attributeMap the attribute map.
     */
    protected void processExplicitSchema(
        @SuppressWarnings("unused") @NotNull final QueryJCommand parameters,
        @NotNull final MetadataManager metadataManager,
        @NotNull final MetadataTypeManager metadataTypeManager,
        @Nullable final AntTablesElement explicitTables,
        @SuppressWarnings("unused") @NotNull final Map<String, List<AntTableElement>> tableMap,
        @NotNull final Map<String, List<String>> tableNameMap,
        @SuppressWarnings("unused") @NotNull final Map<String, List<AntFieldElement>> fieldMap,
        @NotNull final Map<String, List<AntFieldFkElement>> fieldFkMap,
        @NotNull final Map<String, String> fieldNameMap,
        @NotNull final Map<String, List<Attribute<String>>> attributeMap)
    {
        @Nullable List<AntTableElement> t_cTableElements = null;

        if (explicitTables != null)
        {
            t_cTableElements = explicitTables.getTables();
        }

        @Nullable Iterator<AntTableElement> t_itTableElements = null;

        @Nullable List<AntFieldElement> t_cFieldElements;

        List<String> t_lTables;

        if  (t_cTableElements != null)
        {
            t_itTableElements = t_cTableElements.iterator();
        }

        if  (t_itTableElements != null)
        {
            while  (t_itTableElements.hasNext())
            {
                @NotNull final AntTableElement t_Table =
                    t_itTableElements.next();

                t_lTables = tableNameMap.get(buildTableKey());

                if  (t_lTables == null)
                {
                    t_lTables = new ArrayList<>();
                    tableNameMap.put(buildTableKey(), t_lTables);
                }

                t_lTables.add(t_Table.getName());

                t_cFieldElements = t_Table.getFields();

                if  (   (t_cFieldElements  != null)
                        && (t_cFieldElements.size() > 0))
                {
                    @SuppressWarnings("unchecked")
                    @Nullable List<Attribute<String>> t_lFields =
                        attributeMap.get(buildTableFieldsKey(t_Table.getName()));

                    if (t_lFields == null)
                    {
                        t_lFields = new ArrayList<>(4);

                        attributeMap.put(buildTableFieldsKey(t_Table.getName()), t_lFields);
                    }

                    @Nullable List<Attribute<String>> t_lPks =
                        attributeMap.get(buildPkKey(t_Table.getName()));

                    if (t_lPks == null)
                    {
                        t_lPks = new ArrayList<>(1);
                        attributeMap.put(buildPkKey(t_Table.getName()), t_lPks);
                    }

                    for (@Nullable final AntFieldElement t_Field : t_cFieldElements)
                    {
                        if (t_Field != null)
                        {
                            t_lFields.add(t_Field);

                            if (t_Field.isPk())
                            {
                                t_lPks.add(t_Field);

                                fieldNameMap.put(
                                    buildPkKey(t_Table.getName(), t_Field.getName()),
                                    t_Field.getName());
                            }

                            @Nullable List<AntFieldFkElement> t_lFieldFks = null;
                            /*
                                t_Field.getFieldFks();
                            */
                            if (t_lFieldFks == null)
                            {
                                t_lFieldFks = new ArrayList<>(0);
                            }
                            fieldFkMap.put(
                                buildFkKey(t_Table.getName(), t_Field.getName()),
                                t_lFieldFks);

                            metadataManager.getColumnDAO().insert(
                                t_Table.getName(),
                                t_Field.getName(),
                                metadataTypeManager.getJavaType(
                                    t_Field.getType()));
                        }
                    }
                }

                @Nullable final List<Attribute<String>> t_lFields =
                    attributeMap.get(buildTableFieldsKey(t_Table.getName()));

                if (t_lFields != null)
                {
                    metadataManager.getPrimaryKeyDAO().insert(
                        t_Table.getName(), t_lFields);
                }
            }
        }
    }

    /**
     * Builds the table key.
     * @return the map key.
     */
    @NotNull
    protected String buildTableKey()
    {
        return "'@'@'table";
    }

    /**
     * Builds the table fields key.
     * @param key the key.
     * @return the map key.
     */
    @NotNull
    protected String buildTableFieldsKey(@NotNull final Object key)
    {
        return ".98.table'@'@'fields`p" + key;
    }

    /**
     * Builds a pk key using given object.
     * @param firstKey the first object key.
     * @return the map key.
     */
    @NotNull
    protected String buildPkKey(@NotNull final Object firstKey)
    {
        return ".|\\|.pk" + firstKey;
    }

    /**
     * Builds a pk key using given object.
     * @param firstKey the first object key.
     * @param secondKey the second object key.
     * @return the map key.
     */
    @NotNull
    protected String buildPkKey(
        @NotNull final String firstKey, @NotNull final String secondKey)
    {
        return buildPkKey(firstKey) + "-.,.,-" + secondKey;
    }

    /**
     * Builds a fk key using given object.
     * @param firstKey the first object key.
     * @return the map key.
     */
    @NotNull
    protected String buildFkKey(@NotNull final String firstKey)
    {
        return "==fk''" + firstKey;
    }

    /**
     * Builds a fk key using given object.
     * @param firstKey the first object key.
     * @param secondKey the second object key.
     * @return the map key.
     */
    @NotNull
    protected String buildFkKey(
        @NotNull final String firstKey, @NotNull final String secondKey)
    {
        return buildFkKey(firstKey) + ".,.," + secondKey;
    }

    /**
     * Retrieves the tables XML element stored in the
     * attribute map.
     * @param parameters the parameter map.
     * @return the table information.
     */
    @Nullable
    protected AntTablesElement retrieveTablesElement(@NotNull final QueryJCommand parameters)
    {
        return
            new QueryJCommandWrapper<AntTablesElement>(parameters)
                .getSetting(ParameterValidationHandler.EXPLICIT_TABLES);
    }

    /**
     * Retrieves the names of the user-defined tables.
     * @param parameters the command parameters.
     * @param tablesElement the &lt;tables&gt; element.
     * @return such table names.
     */
    @NotNull
    @SuppressWarnings("unused")
    protected List<Table<String, Attribute<String>, List<Attribute<String>>>> extractTables(
        @NotNull final QueryJCommand parameters, @Nullable final AntTablesElement tablesElement)
    {
        List<Table<String, Attribute<String>, List<Attribute<String>>>> result = null;

        if (tablesElement != null)
        {
            result = extractTables(tablesElement.getTables());
        }

        if (result == null)
        {
            result = new ArrayList<>(0);
        }

        return result;
    }

    /**
     * Retrieves the fields of the user-defined tables.
     * @param tables the table definitions.
     * @return such fields.
     */
    @NotNull
    protected List<Table<String, Attribute<String>, List<Attribute<String>>>> extractTables(
        @Nullable final List<AntTableElement> tables)
    {
        @Nullable final List<Table<String, Attribute<String>, List<Attribute<String>>>> result;

        final int t_iLength = (tables != null) ? tables.size() : 0;

        result = new ArrayList<>(t_iLength);

        if (tables != null)
        {
            for (@Nullable final AntTableElement t_Table : tables)
            {
                if  (t_Table != null)
                {
                    result.add(convertToTable(t_Table));
                }
            }
        }

        return result;
    }

    /**
     * Retrieves whether the tables should be extracted on demand.
     * @param tablesElement the tables element.
     * @return the result of such analysis..
     */
    @SuppressWarnings("unused")
    protected boolean lazyTableExtraction(@Nullable final AntTablesElement tablesElement)
    {
        boolean result = false;

        if  (tablesElement != null)
        {
            result = lazyTableExtraction(tablesElement.getTables());
        }

        return result;
    }

    /**
     * Retrieves whether the tables should be extracted on demand.
     * @param tables the table definitions.
     * @return the result of such analysis.
     */
    protected boolean lazyTableExtraction(@Nullable final Collection<AntTableElement> tables)
    {
        boolean result = false;

        if (tables != null)
        {
            for (@Nullable final AntTableElement t_Table : tables)
            {
                if  (t_Table != null)
                {
                    @Nullable final Collection<AntFieldElement> t_cFields = t_Table.getFields();

                    if  (   (t_cFields != null)
                            && (t_cFields.size()> 0))
                    {
                        result = true;
                        break;
                    }
                }
            }
        }

        return result;
    }

    /**
     * Extracts the procedures.
     * @param tables the tables.
     * @param extractedMap the already-extracted information.
     * @param metadataManager the database metadata manager.
     * @param tableKey the key to store the tables.
     */
    @SuppressWarnings("unused")
    protected void extractForeignKeys(
        @NotNull final Collection<String> tables,
        @NotNull final Map<String, Collection<String>> extractedMap,
        @NotNull final MetadataManager metadataManager,
        @NotNull final Object tableKey)
    {
        for (@Nullable final String t_strTableName : tables)
        {
            if (t_strTableName != null)
            {
                @NotNull final Collection<String> t_cFields =
                    extractedMap.get(buildTableFieldsKey(t_strTableName));

                for (@Nullable final String t_strFieldName : t_cFields)
                {
                    if (t_strFieldName != null)
                    {
                        @NotNull final Collection<String> t_cFieldFks =
                            extractedMap.get(buildFkKey(t_strTableName, t_strFieldName));

                        @Nullable final Iterator<String> t_itFieldFks = null;

//                        if  (t_cFieldFks != null)
//                        {
//                            t_itFieldFks =
//                                t_cFieldFks.iterator();
//                        }
//
//                        if  (t_itFieldFks != null)
//                        {
//                            while  (t_itFieldFks.hasNext())
//                            {
//                                  @NotNull AntFieldFkElement t_FieldFk =
//                                      (AntFieldFkElement)
//                                          t_itFieldFks.next();
//
//                                  if  (t_FieldFk != null)
//                                  {
                        // TODO
//                                        metadataManager.addForeignKey(
//                                            t_strTableName,
//                                            new String[] {t_strFieldName},
//                                            t_FieldFk.getTable(),
//                                            new String[]
//                                            {
//                                                t_FieldFk.getField()
//                                            });
//                                    }
//                                }
//                            }
                    }
                }
            }
        }
    }

    /**
     * Validates the parameters explicitly coming from Ant.
     * @param tables the table information.
     * @param externallyManagedFields the externally-managed fields
     * information.
     * @param classpath the classpath.
     * such as the header contents.
     * @throws org.acmsl.queryj.api.exceptions.QueryJBuildException if any parameter fails to validate.
     */
    protected void validateAntParameters(
        @Nullable final AntTablesElement tables,
        @Nullable final AntExternallyManagedFieldsElement externallyManagedFields,
        @Nullable final Path classpath)
        throws QueryJBuildException
    {
        if  (classpath == null)
        {
            throw new MissingClasspathException();
        }

        if  (   (tables != null)
                && (   (tables.getTables() == null)
                       || (tables.getTables().size() == 0)))
        {
            throw new MissingTablesException();
        }

        if  (   (externallyManagedFields != null)
                && (externallyManagedFields.getFields().size() == 0))
        {
            throw new MissingExternallyManagedFieldsException();
        }
    }

    /**
     * Validates the parameters.
     * @param command the parameter map.
     * @param usingAnt whether QueryJ is executed within Ant.
     * @throws QueryJBuildException if any parameter fails to validate.
     */
    public void validateParameters(
        @NotNull final QueryJCommand command, final boolean usingAnt)
        throws  QueryJBuildException
    {
        /*
        validateParameters(
            command.getStringSetting(QueryJSettings.JDBC_DRIVER),
            command.getStringSetting(QueryJSettings.JDBC_URL),
            command.getStringSetting(QueryJSettings.JDBC_USERNAME),
            command.getStringSetting(QueryJSettings.JDBC_SCHEMA),
            command.getStringSetting(QueryJSettings.REPOSITORY),
            command.getStringSetting(QueryJSettings.PACKAGE_NAME),
            command.getFileSetting(QueryJSettings.OUTPUT_DIR),
            command.getFileSetting(QueryJSettings.HEADER_FILE),
            command.getStringSetting(QueryJSettings.JNDI_DATASOURCE),
            command.getFileSetting(QueryJSettings.SQL_XML_FILE),
            command.getFileSetting(QueryJSettings.GRAMMAR_FOLDER),
            command.getStringSetting(QueryJSettings.GRAMMAR_NAME),
            command.getStringSetting(QueryJSettings.GRAMMAR_SUFFIX),
            command.getStringSetting(QueryJSettings.ENCODING),
            command.getIntSetting(QueryJSettings.THREAD_COUNT, Runtime.getRuntime().availableProcessors()),
            command);
        */
        if  (usingAnt)
        {
            validateAntParameters(
                new QueryJCommandWrapper<AntTablesElement>(command).getSetting(ParameterValidationHandler.TABLES),
                new QueryJCommandWrapper<AntExternallyManagedFieldsElement>(command).getSetting(
                    ParameterValidationHandler.EXTERNALLY_MANAGED_FIELDS),
                new QueryJCommandWrapper<Path>(command).getSetting(ParameterValidationHandler.CLASSPATH));
        }
    }
}
