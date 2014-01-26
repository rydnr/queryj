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
 * Filename: TableIncompleteValueObject.java
 *
 * Author: Jose San Leandro Armendariz (chous)
 *
 * Description: Represents an incomplete Table: only with partial information.
 *
 * Date: 6/6/12
 * Time: 10:08 AM
 *
 */
package org.acmsl.queryj.metadata.vo;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

import java.util.List;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;
import org.jetbrains.annotations.Nullable;

/**
 * Represents an incomplete {@link Table}: only with partial information.
 * @author <a href="mailto:chous@acm-sl.org">chous</a>
 * @since 2012/06/06
 */
@ThreadSafe
public class TableIncompleteValueObject
    extends AbstractTable<String, Attribute<String>, List<Attribute<String>>>
{

    private static final long serialVersionUID = -9077871583769430337L;

    /**
     * Creates a {@link TableIncompleteValueObject}.
     * @param name the table name.
     * @param comment the table comment.
     */
    public TableIncompleteValueObject(
        @NotNull final String name, @Nullable final String comment)
    {
        super(name, comment);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setParentTable(
        @NotNull final Table<String, Attribute<String>, List<Attribute<String>>> parent)
    {
        super.setParentTable(parent);
    }

    /**
     * Specifies the attributes.
     * @param attrs the attributes.
     */
    @Override
    public void setAttributes(@NotNull final List<Attribute<String>> attrs)
    {
        super.setAttributes(attrs);
    }

    /**
     * Specifies the primary key attributes.
     *
     * @param attrs the primary key attributes.
     */
    @Override
    public void setPrimaryKey(@NotNull final List<Attribute<String>> attrs)
    {
        super.setPrimaryKey(attrs);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setForeignKeys(@NotNull final List<ForeignKey<String>> foreignKeys)
    {
        super.setForeignKeys(foreignKeys);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setStaticAttribute(@NotNull final Attribute<String> staticAttribute)
    {
        super.setStaticAttribute(staticAttribute);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setVoDecorated(final boolean flag)
    {
        super.setVoDecorated(flag);
    }
}
