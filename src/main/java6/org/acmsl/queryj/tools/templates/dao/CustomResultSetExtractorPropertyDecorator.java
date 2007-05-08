/*
                        QueryJ

    Copyright (C) 2002-2007  Jose San Leandro Armendariz
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
 * Filename: CustomResultSetExtractorPropertyDecorator.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Property decorator specific for CustomResultSetExtractor
 *              template.
 *
 */
package org.acmsl.queryj.tools.templates.dao;

/*
 * Importing project-specific classes.
 */
import org.acmsl.queryj.tools.customsql.CustomResultUtils;
import org.acmsl.queryj.tools.customsql.CustomSqlProvider;
import org.acmsl.queryj.tools.customsql.Property;
import org.acmsl.queryj.tools.customsql.Result;
import org.acmsl.queryj.tools.metadata.CachingPropertyDecorator;
import org.acmsl.queryj.tools.metadata.MetadataManager;
import org.acmsl.queryj.tools.metadata.MetadataTypeManager;
import org.acmsl.queryj.tools.metadata.MetadataTypeUtils;

/**
 * Property decorator specific for CustomResultSetExtractor template.
 * @author <a href="mailto:chous@acm-sl.org"
 *         >Jose San Leandro</a>
 */
public class CustomResultSetExtractorPropertyDecorator
    extends  DAOPropertyDecorator
{
    /**
     * The cached Java type, capitalized.
     */
    private String m__strCachedJavaTypeCapitalized;

    /**
     * The actual object type.
     */
    private String m__strCachedActualObjectType;

    /**
     * Creates a <code>CustomResultSetExtractorPropertyDecorator</code> to
     * decorate given property.
     * @param property the property to decorate.
     * @param result the associated result element.
     * @param customSqlProvider the <code>CustomSqlProvider</code> instance.
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @precondition property != null
     * @precondition result != null
     * @precondition customSqlProvider != null
     * @precondition metadataManager != null
     */
    public CustomResultSetExtractorPropertyDecorator(
        final Property property,
        final Result result,
        final CustomSqlProvider customSqlProvider,
        final MetadataManager metadataManager)
    {
        super(property, result, customSqlProvider, metadataManager);
    }

    /**
     * Specifies the cached Java type, capitalized.
     * @param type such type.
     */
    protected final void immutableSetCachedJavaTypeCapitalized(final String type)
    {
        m__strCachedJavaTypeCapitalized = type;
    }

    /**
     * Specifies the cached Java type, capitalized.
     * @param type such type.
     */
    protected void setCachedJavaTypeCapitalized(final String type)
    {
        immutableSetCachedJavaTypeCapitalized(type);
    }

    /**
     * Retrieves the cached Java type, capitalized.
     * @return such type.
     */
    public String getCachedJavaTypeCapitalized()
    {
        return m__strCachedJavaTypeCapitalized;
    }

    /**
     * Retrieves the Java type of the property.
     * @return such information.
     */
    public String getJavaTypeCapitalized()
    {
        String result = getCachedJavaType();

        if  (result == null)
        {
            result = uppercase(getJavaType());
            setCachedJavaType(result);
        }

        return result;
    }

    /**
     * Retrieves the Java type of the property.
     * @return such information.
     */
    public String getJavaType()
    {
        String result = getCachedJavaType();

        if  (result == null)
        {
            result = retrieveJavaType();
            setCachedJavaType(result);
        }

        return result;
    }

    /**
     * Retrieves the Java type of the property.
     * @return such information.
     */
    protected String retrieveJavaType()
    {
        return retrieveJavaType(getType(), getMetadataManager(), isNullable());
    }

    /**
     * Retrieves the Java type of the property.
     * @param type the declared type.
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @param allowsNull whether it allows nulls.
     * @return such information.
     * @precondition metadataManager != null
     */
    protected String retrieveJavaType(
        final String type,
        final MetadataManager metadataManager,
        final boolean allowsNull)
    {
        return
            retrieveJavaType(
                type,
                metadataManager.getMetadataTypeManager(),
                allowsNull,
                MetadataTypeUtils.getInstance());
    }

    /**
     * Retrieves the Java type of the property.
     * @param type the declared type.
     * @param metadataTypeManager the <code>MetadataTypeManager</code> instance.
     * @param allowsNull whether it allows nulls.
     * @param metadataTypeUtils the <code>MetadataTypeUtils</code> instance.
     * @return such information.
     * @precondition metadataTypeManager != null
     * @precondition metadataTypeUtils != null
     */
    protected String retrieveJavaType(
        final String type,
        final MetadataTypeManager metadataTypeManager,
        final boolean allowsNull,
        final MetadataTypeUtils metadataTypeUtils)
    {
        return type;
    }

    /**
     * Specifies the cached actual object type.
     * @param type such information.
     */
    protected final void immutableSetCachedActualObjectType(
        final String type)
    {
        m__strCachedActualObjectType = type;
    }

    /**
     * Specifies the cached actual object type.
     * @param type such information.
     */
    protected void setCachedActualObjectType(
        final String type)
    {
        immutableSetCachedActualObjectType(type);
    }

    /**
     * Retrieves the actual object type.
     * @return such value.
     */
    public String getCachedActualObjectType()
    {
        return m__strCachedActualObjectType;
    }

    /**
     * Retrieves the actual object type.
     * @return such value.
     */
    public String getActualObjectType()
    {
        String result = getCachedActualObjectType();

        if  (result == null)
        {
            result =
                retrieveActualObjectType(
                    getProperty(),
                    getResult(),
                    getCustomSqlProvider(),
                    CustomResultUtils.getInstance(),
                    getMetadataManager());

            setCachedActualObjectType(result);
        }

        return result;
    }

    /**
     * Retrieves the actual object type.
     * @param property the property.
     * @param result the result.
     * @param customSqlProvider the <code>CustomSqlProvider</code> instance.
     * @param customResultUtils the <code>CustomResultUtils</code>
     * instance.
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @return such information.
     * @precondition property != null
     * @precondition result != null
     * @precondition customSqlProvider != null
     * @procondition customResultUtils != null
     * @precondition metadataManager != null
     */
    protected String retrieveActualObjectType(
        final Property property,
        final Result result,
        final CustomSqlProvider customSqlProvider,
        final CustomResultUtils customResultUtils,
        final MetadataManager metadataManager)
    {
        return
            retrieveActualObjectType(
                property,
                result,
                customSqlProvider,
                customResultUtils,
                metadataManager,
                metadataManager.getMetadataTypeManager());
    }
    
    /**
     * Retrieves the actual object type.
     * @param property the property.
     * @param sqlResult the result.
     * @param customSqlProvider the <code>CustomSqlProvider</code> instance.
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @param metadataTypeManager the <code>MetadataTypeManager</code>
     * instance.
     * @return such information.
     * @precondition property != null
     * @precondition sqlResult != null
     * @precondition customSqlProvider != null
     * @precondition customResultUtils != null
     * @precondition metadataManager != null
     * @precondition metadataTypeManager != null
     */
    protected String retrieveActualObjectType(
        final Property property,
        final Result sqlResult,
        final CustomSqlProvider customSqlProvider,
        final CustomResultUtils customResultUtils,
        final MetadataManager metadataManager,
        final MetadataTypeManager metadataTypeManager)
    {
        String result = null;

        if  (property.isImplicit())
        {
            result =
                metadataTypeManager.getObjectType(
                    property.getType(),
                    property.isNullable());
        }
        else
        {
            int t_iType =
                metadataManager.getColumnType(
                    customResultUtils.retrieveTable(
                        sqlResult, customSqlProvider, metadataManager),
                    property.getName());
        
            result =
                metadataTypeManager.getObjectType(t_iType, false);
        }
        
        return result;
    }
}
