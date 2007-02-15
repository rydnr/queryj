//;-*- mode: java -*-
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
    Contact info: jose.sanleandro@acm-sl.com
    Postal Address: c/Playa de Lagoa, 1
                    Urb. Valdecabanas
                    Boadilla del monte
                    28660 Madrid
                    Spain

 *****************************************************************************
 *
 * Filename: CachingTableDecorator.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Adds a simple caching mechanism while decorating 'Table'
 *              instances.
 */
package org.acmsl.queryj.tools.metadata;

/*
 * Importing project classes.
 */
import org.acmsl.queryj.tools.metadata.TableDecorator;
import org.acmsl.queryj.tools.metadata.vo.Table;

/*
 * Importing some JDK classes.
 */
import java.util.ArrayList;
import java.util.List;

/**
 * Adds a simple caching mechanism while decorating <code>Table</code>
 * instances.
 * @author <a href="mailto:chous@acm-sl.org"
 *         >Jose San Leandro</a>
 */
public class CachingTableDecorator
    extends AbstractTableDecorator
{
    /**
     * The cached uppercased name.
     */
    private String m__strCachedNameUppercased;

    /**
     * The cached capitalized name.
     */
    private String m__strCachedNameCapitalized;

    /**
     * The cached lowercased name.
     */
    private String m__strCachedNameLowercased;

    /**
     * The cached uncapitalized name.
     */
    private String m__strCachedUncapitalizedName;

    /**
     * The cached VO name.
     */
    private String m__strCachedVoName;

    /**
     * The cached normalized lowercased name.
     */
    private String m__strCachedNameNormalizedLowercased;

    /**
     * The cached normalized lowercased singular name.
     */
    private String m__strCachedSingularNameNormalizedLowercased;

    /**
     * The cached normalized name.
     */
    private String m__strCachedNameNormalized;

    /**
     * The cached capitalized singular name.
     */
    private String m__strCachedSingularNameCapitalized;

    /**
     * The cached non-parent attributes.
     */
    private List m__lCachedNonParentAttributes;

    /**
     * The cached parent table.
     */
    private Table m__CachedParentTable;

    /**
     * The cached all attributes.
     */
    private List m__lCachedAllAttributes;

    /**
     * The cached attributes.
     */
    private List m__lCachedAttributes;

    /**
     * Creates a <code>CachingTableDecorator</code> with the
     * <code>Table</code> to decorate.
     * @param table the table.
     * @param metadataManager the metadata manager.
     * @precondition table != null
     * @precondition metadataManager != null
     */
    public CachingTableDecorator(
        final Table table, final MetadataManager metadataManager)
    {
        super(table, metadataManager);
    }

    /**
     * Creates a <code>CachingTableDecorator</code> with the following
     * information.
     * @param name the name.
     * @param attributes the table attributes.
     * @param parentTable the parent table.
     * @param metadataManager the metadata manager.
     * @precondition name != null
     * @precondition metadataManager != null
     */
    public CachingTableDecorator(
        final String name,
        final List attributes,
        final Table parentTable,
        final MetadataManager metadataManager)
    {
        super(name, attributes, parentTable, metadataManager);
    }

    /**
     * Creates a <code>CachingTableDecorator</code> instance.
     * @param table the table name.
     * @param attributes the attributes.
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @param childAttributes the child attributes.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @precondition decoratorFactory != null
     */
    public CachingTableDecorator(
        final String table,
        final List attributes,
        final MetadataManager metadataManager,
        final List childAttributes,
        final DecoratorFactory decoratorFactory)
    {
        super(table, attributes, metadataManager, childAttributes, decoratorFactory);
    }

    /**
     * Creates a <code>CachingTableDecorator</code> instance.
     * <code>Table</code> to decorate.
     * @param table the table.
     * @param metadataManager the metadata manager.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @precondition table != null
     * @precondition metadataManager != null
     * @precondition decoratorFactory != null
     */
    protected CachingTableDecorator(
        final String table,
        final MetadataManager metadataManager,
        final DecoratorFactory decoratorFactory)
    {
        super(table, metadataManager, decoratorFactory);
    }

    /**
     * Creates an <code>CachingTableDecorator</code> with the
     * <code>Table</code> to decorate.
     * @param table the table.
     * @param metadataManager the metadata manager.
     * @param decoratorFactory the decorator factory.
     * @precondition table != null
     * @precondition metadataManager != null
     * @precondition decoratorFactory != null
     */
    public CachingTableDecorator(
        final Table table,
        final MetadataManager metadataManager,
        final DecoratorFactory decoratorFactory)
    {
        super(table, metadataManager, decoratorFactory);
    }

    /**
     * Creates a <code>CachingTableDecorator</code> instance.
     * <code>Table</code> to decorate.
     * @param table the table.
     * @param metadataManager the metadata manager.
     * @param childAttributes the child attributes.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @precondition table != null
     * @precondition metadataManager != null
     * @precondition decoratorFactory != null
     */
    protected CachingTableDecorator(
        final String table,
        final MetadataManager metadataManager,
        final List childAttributes,
        final DecoratorFactory decoratorFactory)
    {
        super(table, metadataManager, childAttributes, decoratorFactory);
    }

    /**
     * Specifies the cached all attributes.
     * @param attributes the attributes.
     */
    protected final void immutableSetCachedAllAttributes(final List attributes)
    {
        m__lCachedAllAttributes = attributes;
    }

    /**
     * Specifies the cached all attributes.
     * @param attributes the attributes.
     */
    protected void setCachedAllAttributes(final List attributes)
    {
        immutableSetCachedAllAttributes(attributes);
    }

    /**
     * Retrieves the cached all attributes.
     * @return such attributes.
     */
    protected List getCachedAllAttributes()
    {
        return m__lCachedAllAttributes;
    }

    /**
     * Retrieves the attributes.
     * @return such information.
     */
    public List getAllAttributes()
    {
        List result = getCachedAllAttributes();

        if  (result == null)
        {
            result = super.getAllAttributes();
            setCachedAllAttributes(result);
        }

        return result;
    }

    /**
     * Specifies the cached attributes.
     * @param attributes the attributes.
     */
    protected final void immutableSetCachedAttributes(final List attributes)
    {
        m__lCachedAttributes = attributes;
    }

    /**
     * Specifies the cached attributes.
     * @param attributes the attributes.
     */
    protected void setCachedAttributes(final List attributes)
    {
        immutableSetCachedAttributes(attributes);
    }

    /**
     * Retrieves the cached attributes.
     * @return such attributes.
     */
    protected List getCachedAttributes()
    {
        return m__lCachedAttributes;
    }

    /**
     * Retrieves the attributes.
     * @return such information.
     */
    public List getAttributes()
    {
        List result = getCachedAttributes();

        if  (result == null)
        {
            result = super.getAttributes();
            setCachedAttributes(result);
        }

        return result;
    }

    /**
     * Specifies the cached parent table.
     * @param parent such table.
     */
    protected final void immutableSetCachedParentTable(final Table parent)
    {
        m__CachedParentTable = parent;
    }

    /**
     * Specifies the cached parent table.
     * @param parent such table.
     */
    protected void setCachedParentTable(final Table parent)
    {
        immutableSetCachedParentTable(parent);
    }

    /**
     * Retrieves the cached parent table.
     * @return such table.
     */
    public Table getCachedParentTable()
    {
        return m__CachedParentTable;
    }

    /**
     * Retrieves the parent table.
     * @return such information.
     */
    public Table getParentTable()
    {
        Table result = getCachedParentTable();

        if  (result == null)
        {
            result = super.getParentTable();
            setCachedParentTable(result);
        }

        return result;
    }


    /**
     * Specifies the cached, non-parent attributes.
     * @param attrs such attributes.
     */
    protected final void immutableSetCachedNonParentAttributes(final List attrs)
    {
        m__lCachedNonParentAttributes = attrs;
    }

    /**
     * Specifies the cached, non-parent attributes.
     * @param attrs such attributes.
     */
    protected void setCachedNonParentAttributes(final List attrs)
    {
        immutableSetCachedNonParentAttributes(attrs);
    }

    /**
     * Retrieves the cached, non-parent attributes.
     * @return such information.
     */
    protected List getCachedNonParentAttributes()
    {
        return m__lCachedNonParentAttributes;
    }

    /**
     * Retrieves the non-parent attributes.
     * @return such attributes.
     */
    public List getNonParentAttributes()
    {
        List result = getCachedNonParentAttributes();

        if  (result == null)
        {
            result = super.getNonParentAttributes();
            setCachedNonParentAttributes(result);
        }

        return result;
    }

    /**
     * Specifies the cached uppercased name.
     * @param value the value to cache.
     */
    protected final void immutableSetCachedNameUppercased(
        final String value)
    {
        m__strCachedNameUppercased = value;
    }
    
    /**
     * Specifies the cached uppercased name.
     * @param value the value to cache.
     */
    protected void setCachedNameUppercased(final String value)
    {
        immutableSetCachedNameUppercased(value);
    }

    /**
     * Retrieves the cached uppercased name.
     * @return such value.
     */
    public String getCachedNameUppercased()
    {
        return m__strCachedNameUppercased;
    }

    /**
     * Retrieves the name, in upper case.
     * @return such value.
     */
    public String getNameUppercased()
    {
        String result = getCachedNameUppercased();
        
        if  (result == null)
        {
            result = super.getNameUppercased();
            setCachedNameUppercased(result);
        }
        
        return result;
    }

    /**
     * Specifies the cached capitalized name.
     * @param value the value to cache.
     */
    protected final void immutableSetCachedNameCapitalized(
        final String value)
    {
        m__strCachedNameCapitalized = value;
    }
    
    /**
     * Specifies the cached capitalized name.
     * @param value the value to cache.
     */
    protected void setCachedNameCapitalized(final String value)
    {
        immutableSetCachedNameCapitalized(value);
    }

    /**
     * Retrieves the cached capitalized name.
     * @return such value.
     */
    public String getCachedNameCapitalized()
    {
        return m__strCachedNameCapitalized;
    }

    /**
     * Retrieves the name, in upper case.
     * @return such value.
     */
    public String getNameCapitalized()
    {
        String result = getCachedNameCapitalized();
        
        if  (result == null)
        {
            result = super.getNameCapitalized();
            setCachedNameCapitalized(result);
        }
        
        return result;
    }

    /**
     * Specifies the cached lowercased name.
     * @param value the value to cache.
     */
    protected final void immutableSetCachedNameLowercased(
        final String value)
    {
        m__strCachedNameLowercased = value;
    }
    
    /**
     * Specifies the cached lowercased name.
     * @param value the value to cache.
     */
    protected void setCachedNameLowercased(final String value)
    {
        immutableSetCachedNameLowercased(value);
    }

    /**
     * Retrieves the cached lowercased name.
     * @return such value.
     */
    public String getCachedNameLowercased()
    {
        return m__strCachedNameLowercased;
    }

    /**
     * Retrieves the name, in lower case.
     * @return such value.
     */
    public String getNameLowercased()
    {
        String result = getCachedNameLowercased();
        
        if  (result == null)
        {
            result = super.getNameLowercased();
            setCachedNameLowercased(result);
        }
        
        return result;
    }

    /**
     * Specifies the cached uncapitalized name.
     * @param value the value to cache.
     */
    protected final void immutableSetCachedUncapitalizedName(
        final String value)
    {
        m__strCachedUncapitalizedName = value;
    }
    
    /**
     * Specifies the cached uncapitalized name.
     * @param value the value to cache.
     */
    protected void setCachedUncapitalizedName(final String value)
    {
        immutableSetCachedUncapitalizedName(value);
    }

    /**
     * Retrieves the cached uncapitalized name.
     * @return such value.
     */
    public String getCachedUncapitalizedName()
    {
        return m__strCachedUncapitalizedName;
    }
    
    /**
     * Retrieves the table name, uncapitalized.
     * @return such value.
     */
    public String getUncapitalizedName()
    {
        String result = getCachedUncapitalizedName();
        
        if  (result == null)
        {
            result = super.getUncapitalizedName();
            setCachedUncapitalizedName(result);
        }
        
        return result;
    }

    /**
     * Specifies the cached VO name.
     * @param value the value to cache.
     */
    protected final void immutableSetCachedVoName(
        final String value)
    {
        m__strCachedVoName = value;
    }
    
    /**
     * Specifies the cached VO name.
     * @param value the value to cache.
     */
    protected void setCachedVoName(final String value)
    {
        immutableSetCachedVoName(value);
    }

    /**
     * Retrieves the cached VO name.
     * @return such value.
     */
    public String getCachedVoName()
    {
        return m__strCachedVoName;
    }

    /**
     * Retrieves the value-object name associated to the table name.
     * @return such name.
     */
    public String getVoName()
    {
        String result = getCachedVoName();
        
        if  (result == null)
        {
            result = super.getVoName();
            setCachedVoName(result);
        }
        
        return result;
    }

    /**
     * Specifies the cached normalized lowercased name.
     * @param value the value to cache.
     */
    protected final void immutableSetCachedNameNormalizedLowercased(
        final String value)
    {
        m__strCachedNameNormalizedLowercased = value;
    }
    
    /**
     * Specifies the cached normalized lowercased name.
     * @param value the value to cache.
     */
    protected void setCachedNameNormalizedLowercased(final String value)
    {
        immutableSetCachedNameNormalizedLowercased(value);
    }

    /**
     * Retrieves the cached normalized lowercased name.
     * @return such value.
     */
    public String getCachedNameNormalizedLowercased()
    {
        return m__strCachedNameNormalizedLowercased;
    }

    /**
     * Retrieves the table's name in lower-case, once normalized.
     * @return such information.
     */
    public String getNameNormalizedLowercased()
    {
        String result = getCachedNameNormalizedLowercased();
        
        if  (result == null)
        {
            result = super.getNameNormalizedLowercased();
            setCachedNameNormalizedLowercased(result);
        }
        
        return result;
    }

    /**
     * Specifies the cached normalized lowercased singular name.
     * @param value the value to cache.
     */
    protected final void immutableSetCachedSingularNameNormalizedLowercased(
        final String value)
    {
        m__strCachedSingularNameNormalizedLowercased = value;
    }
    
    /**
     * Specifies the cached normalized lowercased singular name.
     * @param value the value to cache.
     */
    protected void setCachedSingularNameNormalizedLowercased(final String value)
    {
        immutableSetCachedSingularNameNormalizedLowercased(value);
    }

    /**
     * Retrieves the cached normalized lowercased singular name.
     * @return such value.
     */
    public String getCachedSingularNameNormalizedLowercased()
    {
        return m__strCachedSingularNameNormalizedLowercased;
    }

    /**
     * Retrieves the table's name in lower-case, once normalized.
     * @return such information.
     */
    public String getSingularNameNormalizedLowercased()
    {
        String result = getCachedSingularNameNormalizedLowercased();
        
        if  (result == null)
        {
            result = super.getSingularNameNormalizedLowercased();
            setCachedSingularNameNormalizedLowercased(result);
        }
        
        return result;
    }

    /**
     * Specifies the cached normalized name.
     * @param value the value to cache.
     */
    protected final void immutableSetCachedNameNormalized(
        final String value)
    {
        m__strCachedNameNormalized = value;
    }
    
    /**
     * Specifies the cached normalized name.
     * @param value the value to cache.
     */
    protected void setCachedNameNormalized(final String value)
    {
        immutableSetCachedNameNormalized(value);
    }

    /**
     * Retrieves the cached normalized name.
     * @return such value.
     */
    public String getCachedNameNormalized()
    {
        return m__strCachedNameNormalized;
    }

    /**
     * Retrieves the name, in lower case.
     * @return such value.
     */
    public String getNameNormalized()
    {
        String result = getCachedNameNormalized();
        
        if  (result == null)
        {
            result = super.getNameNormalized();
            setCachedNameNormalized(result);
        }
        
        return result;
    }

    /**
     * Specifies the cached capitalized singular name.
     * @param value the value to cache.
     */
    protected final void immutableSetCachedSingularNameCapitalized(
        final String value)
    {
        m__strCachedSingularNameCapitalized = value;
    }
    
    /**
     * Specifies the cached capitalized singular name.
     * @param value the value to cache.
     */
    protected void setCachedSingularNameCapitalized(final String value)
    {
        immutableSetCachedSingularNameCapitalized(value);
    }

    /**
     * Retrieves the cached capitalized singular name.
     * @return such value.
     */
    public String getCachedSingularNameCapitalized()
    {
        return m__strCachedSingularNameCapitalized;
    }

    /**
     * Retrieves the capitalized table name in singular form.
     * @return such information.
     */
    public String getSingularNameCapitalized()
    {
        String result = getCachedSingularNameCapitalized();
        
        if  (result == null)
        {
            result = super.getSingularNameCapitalized();
            setCachedSingularNameCapitalized(result);
        }
        
        return result;
    }

    /**
     * Creates a table decorator.
     * @param name the table name.
     * @param attributes the attributes.
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @return such decorator.
     * @precondition name != null
     * @precondition attributes != null
     * @precondition metadataManager != null
     * @precondition decoratorFactory != null
     */
    protected TableDecorator createTableDecorator(
        final String parentTable,
        final List attributes,
        final MetadataManager metadataManager,
        final DecoratorFactory decoratorFactory)
    {
        return
            new CachingTableDecorator(
                parentTable,
                attributes,
                metadataManager,
                null,
                decoratorFactory);
    }

    /**
     * Creates a table decorator.
     * @param name the table name.
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @return such decorator.
     * @precondition name != null
     * @precondition metadataManager != null
     * @precondition decoratorFactory != null
     */
    protected TableDecorator createTableDecorator(
        final Table parentTable,
        final MetadataManager metadataManager,
        final DecoratorFactory decoratorFactory)
    {
        return
            new CachingTableDecorator(
                parentTable,
                metadataManager,
                decoratorFactory);
    }
}
