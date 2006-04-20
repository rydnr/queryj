//;-*- mode: java -*-
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

 *****************************************************************************
 *
 * Filename: $RCSfile$
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Is able to create Table sources for each
 *              table in the persistence model.
 *
 */
package org.acmsl.queryj.tools.templates;

/*
 * Importing some project-specific classes.
 */
import org.acmsl.queryj.tools.customsql.CustomSqlProvider;
import org.acmsl.queryj.tools.metadata.DecoratorFactory;
import org.acmsl.queryj.tools.metadata.MetadataManager;
import org.acmsl.queryj.tools.templates.BasePerTableTemplate;

/*
 * Importing StringTemplate classes.
 */
import org.antlr.stringtemplate.StringTemplateGroup;

/*
 * Importing some JDK classes.
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Is able to create Table sources for each
 * table in the persistence model.
 * @author <a href="mailto:chous@acm-sl.org"
 *         >Jose San Leandro</a>
 */
public class TableTemplate
    extends  BasePerTableTemplate
{
    /**
     * The field list.
     */
    private List m__lFields;

    /**
     * The field types.
     */
    private Map m__mFieldTypes;

    /**
     * Builds a <code>TableTemplate</code> using given information.
     * @param tableName the table name.
     * @param metadataManager the database metadata manager.
     * @param customSqlProvider the CustomSqlProvider instance.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @param packageName the package name.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param quote the identifier quote string.
     * @param basePackageName the base package name.
     * @param repositoryName the repository name.
     */
    public TableTemplate(
        final String tableName,
        final MetadataManager metadataManager,
        final CustomSqlProvider customSqlProvider,
        final DecoratorFactory decoratorFactory,
        final String packageName,
        final String engineName,
        final String engineVersion,
        final String quote,
        final String basePackageName,
        final String repositoryName)
    {
        super(
            tableName,
            metadataManager,
            customSqlProvider,
            decoratorFactory,
            packageName,
            engineName,
            engineVersion,
            quote,
            basePackageName,
            repositoryName);

        immutableSetFields(new ArrayList());
        immutableSetFieldTypes(new HashMap());
    }

    /**
     * Retrieves the string template group.
     * @return such instance.
     */
    protected StringTemplateGroup retrieveGroup()
    {
        return retrieveGroup("/org/acmsl/queryj/sql/Table.stg");
    }

    /**
     * Specifies the fields.
     * @param fields the fields.
     */
    private void immutableSetFields(final List fields)
    {
        m__lFields = fields;
    }

    /**
     * Specifies the fields.
     * @param fields the fields.
     */
    protected void setFields(final List fields)
    {
        immutableSetFields(fields);
    }

    /**
     * Retrieves the fields.
     * @return such collection.
     */
    public List getFields()
    {
        return m__lFields;
    }

    /**
     * Adds a new field.
     * @param field the new field.
     */
    public void addField(final String field)
    {
        List t_lFields = getFields();

        if  (t_lFields != null) 
        {
            t_lFields.add(field);
        }
    }

    /**
     * Specifies the field types.
     * @param fieldTypes the field type.
     */
    private void immutableSetFieldTypes(final Map fieldTypes)
    {
        m__mFieldTypes = fieldTypes;
    }

    /**
     * Specifies the field types.
     * @param fieldTypes the field type.
     */
    protected void setFieldTypes(final Map fieldTypes)
    {
        immutableSetFieldTypes(fieldTypes);
    }

    /**
     * Retrieves the field types.
     * @return such collection.
     */
    public Map getFieldTypes()
    {
        return m__mFieldTypes;
    }

    /**
     * Adds a new field type.
     * @param field the field.
     * @param type the field type.
     */
    public void addFieldType(final String field, String type)
    {
        Map t_mFieldTypes = getFieldTypes();

        if  (t_mFieldTypes != null) 
        {
            t_mFieldTypes.put(field, type);
        }
    }

    /**
     * Retrieves the type of given field.
     * @param field the field.
     * @return the field type.
     */
    public String getFieldType(final String field)
    {
        String result = "Field";

        Map t_mFieldTypes = getFieldTypes();

        if  (t_mFieldTypes != null) 
        {
            result = (String) t_mFieldTypes.get(field);
        }

        return result;
    }

    /**
     * Retrieves the template name.
     * @return such information.
     */
    public String getTemplateName()
    {
        return "Table";
    }
}
