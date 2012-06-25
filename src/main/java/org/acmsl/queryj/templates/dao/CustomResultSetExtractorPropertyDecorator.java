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
 * Filename: CustomResultSetExtractorPropertyDecorator.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Property decorator specific for CustomResultSetExtractor
 *              template.
 *
 */
package org.acmsl.queryj.templates.dao;

/*
 * Importing project-specific classes.
 */
import org.acmsl.queryj.customsql.Property;
import org.acmsl.queryj.metadata.CachingPropertyDecorator;
import org.acmsl.queryj.metadata.MetadataManager;
import org.acmsl.queryj.metadata.MetadataTypeManager;
import org.acmsl.queryj.metadata.MetadataTypeUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Property decorator specific for CustomResultSetExtractor template.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public class CustomResultSetExtractorPropertyDecorator
    extends  CachingPropertyDecorator
{
    private static final long serialVersionUID = 3352072015467667634L;
    /**
     * The cached Java type, capitalized.
     */
    private String m__strCachedTypeCapitalized;

    /**
     * Creates a {@link CustomResultSetExtractorPropertyDecorator} to
     * decorate given property.
     * @param property the property to decorate.
     * @precondition property != null
     * @precondition metadataManager != null
     */
    public CustomResultSetExtractorPropertyDecorator(
        @NotNull final Property property, @NotNull final MetadataManager metadataManager)
    {
        super(property, metadataManager);
    }

    /**
     * Specifies the cached Java type, capitalized.
     * @param type such type.
     */
    protected final void immutableSetCachedTypeCapitalized(final String type)
    {
        m__strCachedTypeCapitalized = type;
    }

    /**
     * Specifies the cached Java type, capitalized.
     * @param type such type.
     */
    @SuppressWarnings("unused")
    protected void setCachedTypeCapitalized(final String type)
    {
        immutableSetCachedTypeCapitalized(type);
    }

    /**
     * Retrieves the cached Java type, capitalized.
     * @return such type.
     */
    @SuppressWarnings("unused")
    public String getCachedTypeCapitalized()
    {
        return m__strCachedTypeCapitalized;
    }

    /**
     * Retrieves the Java type of the property.
     * @return such information.
     */
    public String getTypeCapitalized()
    {
        String result = getCachedTypeCapitalized();

        if  (result == null)
        {
            result = uppercase(getType());
            setCachedTypeCapitalized(result);
        }

        return result;
    }

    /**
     * Retrieves the Java type of the property.
     * @return such information.
     */
    @NotNull
    public String getType()
    {
        String result = getCachedType();

        if  (result == null)
        {
            result = retrieveType();
            setCachedType(result);
        }

        return result;
    }

    /**
     * Retrieves the Java type of the property.
     * @return such information.
     */
    protected String retrieveType()
    {
        return retrieveType(getType(), getMetadataManager(), isNullable());
    }

    /**
     * Retrieves the Java type of the property.
     * @param type the declared type.
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @param isNullable whether it allows nulls.
     * @return such information.
     * @precondition metadataManager != null
     */
    protected String retrieveType(
        final String type,
        @NotNull final MetadataManager metadataManager,
        final boolean isNullable)
    {
        return
            retrieveType(
                type,
                metadataManager.getMetadataTypeManager(),
                isNullable,
                MetadataTypeUtils.getInstance());
    }

    /**
     * Retrieves the type of the property.
     * @param type the declared type.
     * @param metadataTypeManager the <code>MetadataTypeManager</code> instance.
     * @param isNullable whether it allows nulls.
     * @param metadataTypeUtils the <code>MetadataTypeUtils</code> instance.
     * @return such information.
     * @precondition metadataTypeManager != null
     * @precondition metadataTypeUtils != null
     */
    @SuppressWarnings("unused")
    protected String retrieveType(
        final String type,
        final MetadataTypeManager metadataTypeManager,
        final boolean isNullable,
        final MetadataTypeUtils metadataTypeUtils)
    {
        return type;
    }

    /**
     * Retrieves the object type.
     * @return such information.
     */
    @NotNull
    public String getObjectType()
    {
        @Nullable String result = getCachedObjectType();

        if  (result == null)
        {
            result = getObjectType(getType(), getMetadataTypeManager());
            setCachedObjectType(result);
        }

        return result;
    }

    /**
     * Retrieves the attribute's object type.
     * @param type the attribute type.
     * @param metadataTypeManager the metadata type manager.
     * @return such type.
     * @precondition metadataTypeManager != null
     */
    @Nullable
    protected String getObjectType(
        final String type, @NotNull final MetadataTypeManager metadataTypeManager)
    {
        return
            metadataTypeManager.getStatementSetterFieldType(
                metadataTypeManager.getJavaType(type));
    }
}
