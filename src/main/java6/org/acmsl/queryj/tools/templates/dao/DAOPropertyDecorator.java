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
 * Filename: DAOPropertyDecorator.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Property decorator specific for DAO template.
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
public class DAOPropertyDecorator
    extends  CachingPropertyDecorator
{
    /**
     * The result.
     */
    private Result m__Result;
    
    /**
     * The custom sql provider.
     */
    private CustomSqlProvider m__CustomSqlProvider;
    
    /**
     * The actual Java type.
     */
    private String m__strCachedActualJavaType;

    /**
     * Creates a <code>DAOPropertyDecorator</code> to
     * decorate given property.
     * @param property the property to decorate.
     * @param result the associated result element.
     * @param customSqlProvider the <code>CustomSqlProvider</code> instance.
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @precondition property != null
     * @precondition metadataManager != null
     */
    public DAOPropertyDecorator(
        final Property property,
        final Result result,
        final CustomSqlProvider customSqlProvider,
        final MetadataManager metadataManager)
    {
        super(property, metadataManager);
        immutableSetResult(result);
        immutableSetCustomSqlProvider(customSqlProvider);
    }

    /**
     * Specifies the result element.
     * @param result the result.
     */
    protected final void immutableSetResult(final Result result)
    {
        m__Result = result;
    }
        
    /**
     * Specifies the result element.
     * @param result the result.
     */
    protected void setResult(final Result result)
    {
        immutableSetResult(result);
    }

    /**
     * Retrieves the result.
     * @return such information.
     */
    public Result getResult()
    {
        return m__Result;
    }

    /**
     * Specifies the custom sql provider.
     * @param provider such provider.
     */
    protected final void immutableSetCustomSqlProvider(
        final CustomSqlProvider provider)
    {
        m__CustomSqlProvider = provider;
    }
    
    /**
     * Specifies the custom sql provider.
     * @param provider such provider.
     */
    protected void setCustomSqlProvider(final CustomSqlProvider provider)
    {
        immutableSetCustomSqlProvider(provider);
    }

    /**
     * Retrieves the custom sql provider.
     * @return such instance.
     */
    public CustomSqlProvider getCustomSqlProvider()
    {
        return m__CustomSqlProvider;
    }
    
    /**
     * Specifies the cached actual java type.
     * @param type such information.
     */
    protected final void immutableSetCachedActualJavaType(
        final String type)
    {
        m__strCachedActualJavaType = type;
    }

    /**
     * Specifies the cached actual java type.
     * @param type such information.
     */
    protected void setCachedActualJavaType(
        final String type)
    {
        immutableSetCachedActualJavaType(type);
    }

    /**
     * Retrieves the actual java type.
     * @return such value.
     */
    public String getCachedActualJavaType()
    {
        return m__strCachedActualJavaType;
    }

    /**
     * Retrieves the actual java type.
     * @return such value.
     */
    public String getActualJavaType()
    {
        String result = getCachedActualJavaType();

        if  (result == null)
        {
            result =
                retrieveActualJavaType(
                    getProperty(),
                    getResult(),
                    getCustomSqlProvider(),
                    CustomResultUtils.getInstance(),
                    getMetadataManager());

            setCachedActualJavaType(result);
        }

        return result;
    }

    /**
p     * Retrieves the actual java type.
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
    protected String retrieveActualJavaType(
        final Property property,
        final Result result,
        final CustomSqlProvider customSqlProvider,
        final CustomResultUtils customResultUtils,
        final MetadataManager metadataManager)
    {
        return
            retrieveActualJavaType(
                property,
                result,
                customSqlProvider,
                customResultUtils,
                metadataManager,
                metadataManager.getMetadataTypeManager());
    }
    
    /**
     * Retrieves the actual java type.
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
    protected String retrieveActualJavaType(
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
                metadataTypeManager.getQueryJFieldType(
                    metadataTypeManager.getJavaType(
                        sqlResult.getClassValue()),
                    false);
        }
        else
        {
            int t_iType =
                metadataManager.getColumnType(
                    customResultUtils.retrieveTable(
                        sqlResult, customSqlProvider, metadataManager),
                    property.getName());
        
            result =
                metadataTypeManager.getNativeType(t_iType, getAllowsNull(), false);
        }
        
        return result;
    }
}
