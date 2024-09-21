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
 * Filename: ResultSetExtractorAttributeDecorator.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Decorates attributes for ResultSetExtractor templates.
 *
 */
package org.acmsl.queryj.templates.dao;

/*
 * Importing project classes.
 */
import org.acmsl.queryj.metadata.CachingAttributeDecorator;
import org.acmsl.queryj.metadata.MetadataManager;
import org.acmsl.queryj.metadata.MetadataTypeManager;
import org.acmsl.queryj.metadata.vo.Attribute;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * Decorates attributes for ResultSetExtractor templates.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
@ThreadSafe
public class ResultSetExtractorAttributeDecorator
    extends  CachingAttributeDecorator
{

    private static final long serialVersionUID = 5940951263282955769L;

    /**
     * Creates a <code>ResultSetExtractorAttributeDecorator</code> with the
     * <code>Attribute</code> to decorate.
     * @param attribute the attribute.
     * @param metadataManager the metadata manager.
     */
    public ResultSetExtractorAttributeDecorator(
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

    /**
     * Retrieves the object type.
     * @return such information.
     */
    @NotNull
    @Override
    public String getObjectType()
    {
        @Nullable String result = getCachedObjectType();

        if  (result == null)
        {
            result = getObjectType(getTypeId(), getMetadataTypeManager());
            setCachedObjectType(result);
        }

        return result;
    }

    /**
     * Retrieves the attribute's object type.
     * @param type the attribute type.
     * @param metadataTypeManager the metadata type manager.
     * @return such type.
     */
    @NotNull
    @Override
    protected String getObjectType(
        final int type, @NotNull final MetadataTypeManager metadataTypeManager)
    {
        return metadataTypeManager.getStatementSetterFieldType(type);
    }
}
