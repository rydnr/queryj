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
package org.acmsl.queryj.tools.templates;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.tools.customsql.CustomSqlProvider;
import org.acmsl.queryj.tools.customsql.Sql;
import org.acmsl.queryj.tools.metadata.DecoratorFactory;
import org.acmsl.queryj.tools.metadata.MetadataManager;

/*
 * Importing some JetBrains annotations.
 */
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.jetbrains.annotations.NotNull;

/**
 * Context information required by templates customized for each {@link Sql}.
 * @author <a href="mailto:chous@acm-sl.org">chous</a>
 * @since 2012/05/20
 */
public class BasePerCustomSqlTemplateContext
    extends AbstractTemplateContext
{

    private static final long serialVersionUID = -1302836113779801668L;
    /**
     * The Sql.
     */
    private Sql m__Sql;

    /**
     * Creates a {@link BasePerCustomSqlTemplateContext} with given information.
     * @param metadataManager the {@link MetadataManager} instance.
     * @param customSqlProvider the {@link CustomSqlProvider} instance.
     * @param header the header.
     * @param decoratorFactory the {@link DecoratorFactory} instance.
     * @param packageName the package name.
     * @param basePackageName the base package name.
     * @param repositoryName the repository name.
     * @param implementMarkerInterfaces whether to implement marker interfaces or not.
     * @param jmx whether to include support for JMX or not.
     * @param sql the {@link Sql} instance.
     */
    public BasePerCustomSqlTemplateContext(
        @NotNull final MetadataManager metadataManager,
        @NotNull final CustomSqlProvider customSqlProvider,
        @NotNull final String header,
        @NotNull final DecoratorFactory decoratorFactory,
        @NotNull final String packageName,
        @NotNull final String basePackageName,
        @NotNull final String repositoryName,
        final boolean implementMarkerInterfaces,
        final boolean jmx,
        @NotNull final String jndiLocation,
        @NotNull final Sql sql)
    {
        super(
            metadataManager,
            customSqlProvider,
            header,
            decoratorFactory,
            packageName,
            basePackageName,
            repositoryName,
            implementMarkerInterfaces,
            jmx,
            jndiLocation);

        immutableSetSql(sql);
    }

    /**
     * Specifies the {@link Sql}.
     * @param sql the Sql.
     */
    protected final void immutableSetSql(@NotNull final Sql sql)
    {
        m__Sql = sql;
    }

    /**
     * Specifies the Sql.
     * @param sql the sql.
     */
    @SuppressWarnings("unused")
    protected void setSql(@NotNull final Sql sql)
    {
        immutableSetSql(sql);
    }

    /**
     * Retrieves the Sql.
     * @return such information.
     */
    @NotNull
    public Sql getSql()
    {
        return m__Sql;
    }

    @Override
    public int hashCode()
    {
        return new HashCodeBuilder().appendSuper(super.hashCode()).append(this.m__Sql).toHashCode();
    }

    @Override
    public boolean equals(final Object obj)
    {
        if (obj == null)
        {
            return false;
        }
        if (getClass() != obj.getClass())
        {
            return false;
        }
        final BasePerCustomSqlTemplateContext other = (BasePerCustomSqlTemplateContext) obj;
        return new EqualsBuilder().appendSuper(super.equals(obj)).append(this.m__Sql, other.m__Sql).isEquals();
    }
}
