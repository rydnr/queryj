/*
                        QueryJ Core

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
 * Filename: BasePerCustomForeignKeyTemplateContext.java
 *
 * Author: Jose San Leandro Armendariz (chous)
 *
 * Description: Context information required by templates customized for each
 *              ForeignKey.
 *
 * Date: 5/20/12
 * Time: 6:44 AM
 *
 */
package org.acmsl.queryj.api;

/*
 * Importing QueryJ Core classes.
 */
import org.acmsl.queryj.QueryJCommand;
import org.acmsl.queryj.api.exceptions.ForeignKeyNotAvailableException;
import org.acmsl.queryj.metadata.vo.ForeignKey;

/*
 * Importing some Apache Commons-Lang classes.
 */
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * Context information required by templates customized for each {@link ForeignKey}.
 * @author <a href="mailto:chous@acm-sl.org">chous</a>
 * @since 2012/05/20
 */
@ThreadSafe
public class PerForeignKeyTemplateContext
    extends AbstractQueryJTemplateContext
{
    /**
     * The serial version id.
     */
    private static final long serialVersionUID = 1350908613901423440L;

    /**
     * Creates a {@link PerForeignKeyTemplateContext} with given information.
     * @param fileName the file name.
     * @param packageName the package name.
     * @param foreignKey the {@link ForeignKey} instance.
     * @param command the {@link QueryJCommand} instance.
     */
    public PerForeignKeyTemplateContext(
        @NotNull final String fileName,
        @NotNull final String packageName,
        @NotNull final ForeignKey<String> foreignKey,
        @NotNull final QueryJCommand command)
    {
        super("fk" + foreignKey.getFkName(), command);

        immutableSetValue(buildFileNameKey(), fileName, command);
        immutableSetValue(buildPackageNameKey(), packageName, command);
        immutableSetValue(buildForeignKeyKey(), foreignKey, command);
    }

    /**
     * Retrieves the key to access the foreign key.
     * @return such information.
     */
    @NotNull
    public String buildForeignKeyKey()
    {
        return "foreignKey@" + hashCode();
    }

    /**
     * Retrieves the foreign key.
     * @return such {@link ForeignKey} instance.
     */
    @NotNull
    public ForeignKey<String> getForeignKey()
    {
        return getValue(buildForeignKeyKey(), getCommand(), new ForeignKeyNotAvailableException());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @NotNull
    public String getTemplateName()
    {
        return getTemplateName(getForeignKey());
    }

    /**
     * Retrieves the template name, based on given foreign key.
     * @param foreignKey the foreign key.
     * @return the template name.
     */
    @NotNull
    protected String getTemplateName(@NotNull final ForeignKey<String> foreignKey)
    {
        String result = foreignKey.getFkName();

        if (result == null)
        {
            result =
                "fk." + foreignKey.getSourceTableName()
                + " (" + toCsv(foreignKey.getAttributes()) + ")->" + foreignKey.getTargetTableName();
        }

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode()
    {
        return new HashCodeBuilder().appendSuper(super.hashCode()).append(PerForeignKeyTemplate.class).toHashCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object obj)
    {
        return
               (obj != null)
            && (getClass() == obj.getClass())
            && (new EqualsBuilder().appendSuper(super.equals(obj)).isEquals());
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    public String toString()
    {
        return
              "{ \"class\": \"PerForeignKeyTemplateContext\""
            + ", \"super\": " + super.toString()
            + ", \"package\": \"org.acmsl.queryj.api\""
            + " }";
    }
}
