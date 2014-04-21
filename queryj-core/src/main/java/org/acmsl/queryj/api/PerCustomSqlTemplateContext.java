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
 * Filename: BasePerCustomSqlTemplateContext.java
 *
 * Author: Jose San Leandro Armendariz (chous)
 *
 * Description: Context information required by templates customized for each
 *              Sql.
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
import org.acmsl.queryj.customsql.Sql;

/*
 * Importing some Apache Commons-Lang classes.
 */
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

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
 * Context information required by templates customized for each {@link Sql}.
 * @author <a href="mailto:chous@acm-sl.org">chous</a>
 * @since 2012/05/20
 */
@ThreadSafe
public class PerCustomSqlTemplateContext
    extends AbstractQueryJTemplateContext
{
    /**
     * The serial version id.
     */
    private static final long serialVersionUID = -1302836113779801668L;

    /**
     * The Sql.
     */
    private Sql<String> m__Sql;

    /**
     * Creates a {@code PerCustomSqlTemplateContext} with given information.
     * @param sql the {@link Sql} instance.
     * @param command the {@link QueryJCommand} instance.
     */
    public PerCustomSqlTemplateContext(@NotNull final Sql<String> sql, @NotNull final QueryJCommand command)
    {
        super(sql.getId(), command);

        immutableSetSql(sql);
    }

    /**
     * Specifies the {@link Sql}.
     * @param sql the Sql.
     */
    protected final void immutableSetSql(@NotNull final Sql<String> sql)
    {
        m__Sql = sql;
    }

    /**
     * Specifies the Sql.
     * @param sql the sql.
     */
    @SuppressWarnings("unused")
    protected void setSql(@NotNull final Sql<String> sql)
    {
        immutableSetSql(sql);
    }

    /**
     * Retrieves the Sql.
     * @return such information.
     */
    @NotNull
    public Sql<String> getSql()
    {
        return m__Sql;
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    public String getTemplateName()
    {
        return getSql().getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode()
    {
        return new HashCodeBuilder().appendSuper(super.hashCode()).append(this.m__Sql).toHashCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(@Nullable final Object obj)
    {
        if (obj == null)
        {
            return false;
        }
        if (getClass() != obj.getClass())
        {
            return false;
        }
        final PerCustomSqlTemplateContext other = (PerCustomSqlTemplateContext) obj;
        return new EqualsBuilder().appendSuper(super.equals(obj)).append(this.m__Sql, other.m__Sql).isEquals();
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    public String toString()
    {
        return
              "{ \"class\": \"" + PerCustomSqlTemplateContext.class.getSimpleName() + '"'
            + ", \"sql\": " + m__Sql
            + ", \"package\": \"org.acmsl.queryj.api\" }";
    }
}
