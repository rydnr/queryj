/*
                        QueryJ Template Packaging

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
 * Filename: TemplateDefType.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Allowed values for template def types.
 *
 * Date: 2013/08/14
 * Time: 09:03
 *
 */
package org.acmsl.queryj.templates.packaging;

/*
 * Importing ACM-SL Commons classes.
 */
import org.acmsl.commons.utils.EnumUtils;

/*
 * Importing checkthread.org annotations.
 */
import org.acmsl.queryj.metadata.DecoratedString;
import org.checkthread.annotations.ThreadSafe;

/*
 * Importing Jetbrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/**
 * Allowed values for template def types.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2013/08/14 09:03
 */
@ThreadSafe
public enum TemplateDefType
{
    /**
     * Per table.
     */
    PER_TABLE("per-table"),
    /**
     * Per repository.
     */
    PER_REPOSITORY("per-repository"),
    /**
     * Per custom result.
     */
    PER_CUSTOM_RESULT("per-custom-result"),
    /**
     * Per foreign key.
     */
    PER_FOREIGN_KEY("per-foreign-key"),
    /**
     * Per custom SQL.
     */
    PER_CUSTOM_SQL("per-custom-sql");

    /**
     * The type.
     */
    @NotNull
    private final String m__strType;

    /**
     * Creates a new type.
     * @param type the name of the type.
     */
    TemplateDefType(/* @NotNull */ final String type)
    {
        this.m__strType = type;
    }

    /**
     * Retrieves the name.
     * @return such name.
     */
    @NotNull
    public String getType()
    {
        return this.m__strType;
    }

    /**
     * Retrieves the template def type for given value.
     * @param type the value.
     * @return the enum.
     */
    @NotNull
    public static TemplateDefType getEnumFromString(@NotNull final String type)
    {
        return EnumUtils.getInstance().getEnumFromString(TemplateDefType.class, type);
    }

    /**
     * Checks whether this type identifies per-table template defs.
     * @return {@code true} in such case.
     */
    public boolean isPerTable()
    {
        return PER_TABLE.getType().equals(getType());
    }

    /**
     * Checks whether this type identifies per-repository template defs.
     * @return {@code true} in such case.
     */
    public boolean isPerRepository()
    {
        return PER_REPOSITORY.getType().equals(getType());
    }

    /**
     * Checks whether this type identifies per-sql template defs.
     * @return {@code true} in such case.
     */
    public boolean isPerSql()
    {
        return PER_CUSTOM_SQL.getType().equals(getType());
    }

    /**
     * Checks whether this type identifies per-foreign key template defs.
     * @return {@code true} in such case.
     */
    public boolean isPerForeignKey()
    {
        return PER_FOREIGN_KEY.getType().equals(getType());
    }

    /**
     * Checks whether this type identifies per-custom result template defs.
     * @return {@code true} in such case.
     */
    public boolean isPerCustomResult()
    {
        return PER_CUSTOM_RESULT.getType().equals(getType());
    }

    /**
     * Retrieves a capitalized version.
     * @return the type, capitalized.
     */
    @NotNull
    public String getCapitalized()
    {
        return new DecoratedString(getType()).getCapitalized().getValue();
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    public String toString()
    {
        return
            "{ \"class\": \"" + TemplateDefType.class.getSimpleName() + '"'
            + ", \"type\": \"" + this.m__strType + '"'
            + ", \"package\": \"" + TemplateDefType.class.getPackage().getName()
            + "\" }";
    }
}
