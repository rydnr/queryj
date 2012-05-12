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
 * Filename: VOAttributeDecorator.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Decorates attributes for ValueObject-related templates.
 *
 */
package org.acmsl.queryj.tools.templates.valueobject;

/*
 * Importing project classes.
 */
import org.acmsl.queryj.tools.metadata.CachingAttributeDecorator;
import org.acmsl.queryj.tools.metadata.MetadataManager;
import org.acmsl.queryj.tools.metadata.MetadataTypeManager;
import org.acmsl.queryj.tools.metadata.MetadataTypeUtils;
import org.acmsl.queryj.tools.metadata.vo.Attribute;

/**
 * Decorates attributes for ValueObject-related templates.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public class VOAttributeDecorator
    extends  CachingAttributeDecorator
{
    /**
     * Creates a <code>VOAttributeDecorator</code> with the
     * <code>Attribute</code> to decorate.
     * @param attribute the attribute.
     * @param metadataManager the metadata manager.
     * @precondition attribute != null
     * @precondition metadataManager != null
     */
    public VOAttributeDecorator(
        final Attribute attribute,
        final MetadataManager metadataManager)
    {
        super(attribute, metadataManager);
    }

    /**
     * Retrieves the Java type of the attribute, which would be
     * only a primitive Java type if the attribute type matches,
     * and the column allows nulls.
     * @return such information.
     */
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
}
