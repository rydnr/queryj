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
    Contact info: chous@acm-sl.org
    Postal Address: c/Playa de Lagoa, 1
                    Urb. Valdecabanas
                    Boadilla del monte
                    28660 Madrid
                    Spain

 *****************************************************************************
 *
 * Filename: CustomResultSetExtractorAttributeDecorator.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Customizes CachingAttributeDecorator for
 * CustomResultSetExtractor templates.
 *
 */
package org.acmsl.queryj.tools.templates.dao;

/*
 * Importing project classes.
 */
import org.acmsl.queryj.tools.metadata.CachingAttributeDecorator;
import org.acmsl.queryj.tools.metadata.MetadataManager;
import org.acmsl.queryj.tools.metadata.MetadataTypeManager;
import org.acmsl.queryj.tools.metadata.vo.Attribute;

/**
 * Customizes CachingAttributeDecorator for <code>CustomResultSetExtractor</code>
 * templates.
 * @author <a href="mailto:chous@acm-sl.org"
 *         >Jose San Leandro</a>
 */
public class CustomResultSetExtractorAttributeDecorator
    extends  CachingAttributeDecorator
{
    /**
     * The actual object type.
     */
    private String m__strCachedActualObjectType;

    /**
     * Creates a <code>CustomResultSetExtractorAttributeDecorator</code> with the
     * <code>Attribute</code> to decorate.
     * @param attribute the attribute.
     * @param metadataManager the metadata manager.
     * @precondition attribute != null
     * @precondition metadataManager != null
     */
    public CustomResultSetExtractorAttributeDecorator(
        final Attribute attribute,
        final MetadataManager metadataManager)
    {
        super(attribute, metadataManager);
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
                    getAttribute(), getMetadataManager());
            setCachedActualObjectType(result);
        }

        return result;
    }

    /**
     * Retrieves the actual object type.
     * @param attribute the attribute.
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @return such information.
     * @precondition attribute != null
     * @precondition metadataManager != null
     */
    protected String retrieveActualObjectType(
        final Attribute attribute, final MetadataManager metadataManager)
    {
        return
            retrieveActualObjectType(
                attribute,
                metadataManager,
                metadataManager.getMetadataTypeManager());
    }
    
    /**
     * Retrieves the actual object type.
     * @param attribute the attribute.
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @param metadataTypeManager the <code>MetadataTypeManager</code>
     * instance.
     * @return such information.
     * @precondition attribute != null
     * @precondition metadataManager != null
     * @precondition metadataTypeManager != null
     */
    protected String retrieveActualObjectType(
        final Attribute attribute,
        final MetadataManager metadataManager,
        final MetadataTypeManager metadataTypeManager)
    {
        String result = null;

        int t_iType =
            metadataManager.getColumnType(
                attribute.getTableName(),
                attribute.getName());
        
        result =
            metadataTypeManager.getObjectType(t_iType, false);
        
        return result;
    }
}
