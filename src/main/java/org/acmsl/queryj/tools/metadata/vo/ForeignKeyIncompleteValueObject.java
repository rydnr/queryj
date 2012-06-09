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
 * Filename: ForeignKeyIncompleteValueObject.java
 *
 * Author: Jose San Leandro Armendariz (chous)
 *
 * Description: 
 *
 * Date: 6/9/12
 * Time: 9:10 AM
 *
 */
package org.acmsl.queryj.tools.metadata.vo;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/*
 * Importing some JDK classes.
 */
import java.util.List;

/**
 * A mutable implementation of {@link ForeignKey}.
 * @author <a href="mailto:chous@acm-sl.org">chous</a>
 * @since 2012/06/09
 */
public class ForeignKeyIncompleteValueObject
    extends AbstractForeignKey
{
    /**
     * Creates a {@link ForeignKeyIncompleteValueObject} with given information.
     * @param fkName the name of the foreign key.
     * @param sourceTableName the source table.
     * @param targetTableName the target table.
     */
    public ForeignKeyIncompleteValueObject(
        @NotNull final String fkName,
        @NotNull final String sourceTableName,
        @NotNull final String targetTableName)
    {
        super(fkName, sourceTableName, targetTableName);
    }

    /**
     * Specifies the attributes.
     * @param attributes the attributes.
     */
    @Override
    public void setAttributes(@NotNull final List<Attribute> attributes)
    {
        super.setAttributes(attributes);
    }

    /**
     * Specifies whether the foreign key can take null values.
     *
     * @param allowsNull whether it allows null.
     */
    @Override
    public void isNullable(final boolean allowsNull)
    {
        super.isNullable(allowsNull);
    }
}
