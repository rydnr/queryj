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
 * Filename: CachingForeignKeyDecorator.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Adds a simple caching mechanism while decorating 'ForeignKey'
 * instances.
 *
 */
package org.acmsl.queryj.metadata;

/*
 * Importing project classes.
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
import java.util.List;

/**
 * Adds a simple caching mechanism while decorating <code>ForeignKey</code>
 * instances.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
@SuppressWarnings("unused")
public class CachingForeignKeyDecorator
    extends  AbstractForeignKeyDecorator
{
    /**
     * The cached uncapitalized source table name.
     */
    private String m__strCachedSourceTableNameUncapitalized;

    /**
     * The cached source VO name.
     */
    private String m__strCachedSourceVoName;

    /**
     * The cached target VO name.
     */
    private String m__strCachedTargetVoName;

    /**
     * Creates a <code>CachingForeignKeyDecorator</code> with the
     * <code>ForeignKey</code> information to decorate.
     * @param foreignKey the foreign key.
     * @param metadataManager the {@link MetadataManager} instance.
     * @param decoratorFactory the {@link DecoratorFactory} implementation.
     */
    @SuppressWarnings("unused")
    public CachingForeignKeyDecorator(
        @NotNull final ForeignKey foreignKey,
        @NotNull final MetadataManager metadataManager,
        @NotNull final DecoratorFactory decoratorFactory)
    {
        super(foreignKey, metadataManager, decoratorFactory);
    }

    /**
     * Creates a <code>CachingForeignKeyDecorator</code> with the following
     * information.
     * @param sourceTableName the source table name.
     * @param attributes the attributes.
     * @param targetTableName the target table name.
     * @param allowsNull whether the foreign key allows null values.
     * @param metadataManager the {@link MetadataManager} instance.
     * @param decoratorFactory the {@link DecoratorFactory} implementation.
     */
    @SuppressWarnings("unused")
     protected CachingForeignKeyDecorator(
        @NotNull final String sourceTableName,
        @NotNull final List<Attribute> attributes,
        @NotNull final String targetTableName,
        final boolean allowsNull,
        @NotNull final MetadataManager metadataManager,
        @NotNull final DecoratorFactory decoratorFactory)
    {
        super(sourceTableName, attributes, targetTableName, allowsNull, metadataManager, decoratorFactory);
    }

    /**
     * Specifies the cached uncapitalized source table name.
     * @param value the value to cache.
     */
    protected final void immutableSetCachedSourceTableNameUncapitalized(
        final String value)
    {
        m__strCachedSourceTableNameUncapitalized = value;
    }
    
    /**
     * Specifies the cached uncapitalized source table name.
     * @param value the value to cache.
     */
    protected void setCachedSourceTableNameUncapitalized(final String value)
    {
        immutableSetCachedSourceTableNameUncapitalized(value);
    }

    /**
     * Retrieves the cached uncapitalized source table name.
     * @return such value.
     */
    public String getCachedSourceTableNameUncapitalized()
    {
        return m__strCachedSourceTableNameUncapitalized;
    }

    /**
     * Retrieves the source table name, uncapitalized.
     * @return such value.
     */
    @NotNull
    public String getSourceTableNameUncapitalized()
    {
        String result = getCachedSourceTableNameUncapitalized();
        
        if  (result == null)
        {
            result = super.getSourceTableNameUncapitalized();
            setCachedSourceTableNameUncapitalized(result);
        }
        
        return result;
    }

    /**
     * Specifies the cached source VO name.
     * @param value the value to cache.
     */
    protected final void immutableSetCachedSourceVoName(
        final String value)
    {
        m__strCachedSourceVoName = value;
    }
    
    /**
     * Specifies the cached source VO name.
     * @param value the value to cache.
     */
    protected void setCachedSourceVoName(final String value)
    {
        immutableSetCachedSourceVoName(value);
    }

    /**
     * Retrieves the cached source VO name.
     * @return such value.
     */
    public String getCachedSourceVoName()
    {
        return m__strCachedSourceVoName;
    }

    /**
     * Retrieves the source value-object name.
     * @return such value.
     */
    @NotNull
    public String getSourceVoName()
    {
        String result = getCachedSourceVoName();
        
        if  (result == null)
        {
            result = super.getSourceVoName();
            setCachedSourceVoName(result);
        }
        
        return result;
    }

    /**
     * Specifies the cached target VO name.
     * @param value the value to cache.
     */
    protected final void immutableSetCachedTargetVoName(
        final String value)
    {
        m__strCachedTargetVoName = value;
    }
    
    /**
     * Specifies the cached target VO name.
     * @param value the value to cache.
     */
    protected void setCachedTargetVoName(final String value)
    {
        immutableSetCachedTargetVoName(value);
    }

    /**
     * Retrieves the cached target VO name.
     * @return such value.
     */
    public String getCachedTargetVoName()
    {
        return m__strCachedTargetVoName;
    }
    /**
     * Retrieves the target value-object name.
     * @return such value.
     */
    @NotNull
    public String getTargetVoName()
    {
        String result = getCachedTargetVoName();
        
        if  (result == null)
        {
            result = super.getTargetVoName();
            setCachedTargetVoName(result);
        }
        
        return result;
    }
}