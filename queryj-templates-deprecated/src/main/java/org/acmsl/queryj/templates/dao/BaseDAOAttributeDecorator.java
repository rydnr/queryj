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
 * Filename: BaseDAOAttributeDecorator.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Decorates attributes for BaseDAO template.
 *
 */
package org.acmsl.queryj.templates.dao;

/*
 * Importing project classes.
 */
import org.acmsl.queryj.metadata.CachingAttributeDecorator;
import org.acmsl.queryj.metadata.MetadataManager;
import org.acmsl.queryj.metadata.vo.Attribute;
import org.jetbrains.annotations.NotNull;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;
import org.jetbrains.annotations.Nullable;

/**
 * Decorates attributes for BaseDAO template.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
@ThreadSafe
public class BaseDAOAttributeDecorator
    extends  CachingAttributeDecorator
{

    private static final long serialVersionUID = 5261181161201022631L;

    /**
     * Creates a <code>BaseDAOAttributeDecorator</code> with the
     * <code>Attribute</code> to decorate.
     * @param attribute the attribute.
     * @param metadataManager the metadata manager.
     */
    public BaseDAOAttributeDecorator(
        @NotNull final Attribute attribute,
        @NotNull final MetadataManager metadataManager)
    {
        super(attribute, metadataManager);
    }

    /**
     * Retrieves the Java type of the attribute, which would be
     * only a primitive Java type if the attribute type matches,
     * and the column allows nulls.
     * @return such information.
     */
    @NotNull
    @Override
    public String getType()
    {
        @Nullable String result = getCachedType();

        if  (result == null)
        {
            result = retrieveType();
            setCachedType(result);
        }

        return result;
    }
}
