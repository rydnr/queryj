//;-*- mode: java -*-
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

 *****************************************************************************
 *
 * Filename: AbstractTableTemplate.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Is able to create Table sources for each
 *              table in the persistence model.
 *
 */
package org.acmsl.queryj.templates;

/*
 * Importing StringTemplate classes.
 */
import org.antlr.stringtemplate.StringTemplateGroup;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing some JDK classes.
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * Is able to create Table sources for each
 * table in the persistence model.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
@ThreadSafe
@SuppressWarnings("unused")
public abstract class AbstractTableTemplate
    extends  AbstractBasePerTableTemplate<BasePerTableTemplateContext>
{
    private static final long serialVersionUID = -1114015521191407454L;
    /**
     * The field list.
     */
    private List<String> m__lFields;

    /**
     * The field types.
     */
    private Map<String,String> m__mFieldTypes;

    /**
     * Builds a <code>TableTemplate</code> using given information.
     * @param context the {@link BasePerTableTemplateContext} instance.
     */
    public AbstractTableTemplate(@NotNull final BasePerTableTemplateContext context)
    {
        super(context);

        immutableSetFields(new ArrayList<String>());
        immutableSetFieldTypes(new HashMap<String,String>());
    }

    /**
     * Retrieves the string template group.
     * @return such instance.
     */
    @Nullable
    @Override
    public StringTemplateGroup retrieveGroup()
    {
        return retrieveGroup("/org/acmsl/queryj/sql/Table.stg");
    }

    /**
     * Specifies the fields.
     * @param fields the fields.
     */
    protected final void immutableSetFields(@NotNull final List<String> fields)
    {
        m__lFields = fields;
    }

    /**
     * Specifies the fields.
     * @param fields the fields.
     */
    @SuppressWarnings("unused")
    protected void setFields(@NotNull final List<String> fields)
    {
        immutableSetFields(fields);
    }

    /**
     * Retrieves the fields.
     * @return such collection.
     */
    @NotNull
    public List<String> getFields()
    {
        return m__lFields;
    }

    /**
     * Adds a new field.
     * @param field the new field.
     */
    @SuppressWarnings("unused")
    public void addField(@NotNull final String field)
    {
        @NotNull final List<String> t_lFields = getFields();

        t_lFields.add(field);
    }

    /**
     * Specifies the field types.
     * @param fieldTypes the field type.
     */
    protected final void immutableSetFieldTypes(@NotNull final Map<String,String> fieldTypes)
    {
        m__mFieldTypes = fieldTypes;
    }

    /**
     * Specifies the field types.
     * @param fieldTypes the field type.
     */
    @SuppressWarnings("unused")
    protected void setFieldTypes(final Map<String,String> fieldTypes)
    {
        immutableSetFieldTypes(fieldTypes);
    }

    /**
     * Retrieves the field types.
     * @return such collection.
     */
    @NotNull
    public Map<String,String> getFieldTypes()
    {
        return m__mFieldTypes;
    }

    /**
     * Adds a new field type.
     * @param field the field.
     * @param type the field type.
     */
    @SuppressWarnings("unused")
    public void addFieldType(@NotNull final String field, @NotNull final String type)
    {
        @NotNull final Map<String,String> t_mFieldTypes = getFieldTypes();

        t_mFieldTypes.put(field, type);
    }

    /**
     * Retrieves the type of given field.
     * @param field the field.
     * @return the field type.
     */
    @NotNull
    @SuppressWarnings("unused")
    public String getFieldType(@NotNull final String field)
    {
        @Nullable String result;

        @NotNull final Map<String,String> t_mFieldTypes = getFieldTypes();

        result = t_mFieldTypes.get(field);

        if (result == null)
        {
            result = "Field";
        }

        return result;
    }

    /**
     * Retrieves the template name.
     * @return such information.
     */
    @NotNull
    public String getTemplateName()
    {
        return "Table";
    }

    @NotNull
    @Override
    public String toString()
    {
        return "TableTemplate{ fields=" + m__lFields + ", fieldTypes=" + m__mFieldTypes + " }";
    }
}
