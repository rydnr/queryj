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
 * Filename: BasePerRepositoryTemplateContext.java
 *
 * Author: Jose San Leandro Armendariz (chous)
 *
 * Description: Context used by per-repository templates.
 *
 * Date: 5/20/12
 * Time: 6:06 AM
 *
 */
package org.acmsl.queryj.tools.templates;

/*
 * Importing project classes.
 */
import org.acmsl.queryj.tools.customsql.CustomSqlProvider;
import org.acmsl.queryj.tools.metadata.DecoratorFactory;
import org.acmsl.queryj.tools.metadata.MetadataManager;

/*
 * Importing some JetBrains annotations.
 */
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.jetbrains.annotations.NotNull;

/*
 * Importing some JDK classes.
 */
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Context used by per-repository templates.
 * @author <a href="mailto:chous@acm-sl.org">chous</a>
 * @since 2012/05/20
 */
public class BasePerRepositoryTemplateContext
    extends AbstractTemplateContext
{
    private static final long serialVersionUID = 3832140019368840537L;

    /**
     * The table names.
     */
    private List<String> m__lTableNames;

    /**
     * Creates the template context with required information.
     * @param metadataManager the {@link MetadataManager} instance.
     * @param customSqlProvider the {@link CustomSqlProvider} instance.
     * @param header the header.
     * @param decoratorFactory the {@link DecoratorFactory} instance.
     * @param packageName the package name.
     * @param basePackageName the base package name.
     * @param repositoryName the repository name.
     * @param implementMarkerInterfaces whether to implement marker interfaces.
     * @param jmx whether to include JMX support.
     * @param tableNames the table names.
     * @param jndiLocation the JNDI location of the data source.
     */
    public BasePerRepositoryTemplateContext(
        @NotNull final MetadataManager metadataManager,
        @NotNull final CustomSqlProvider customSqlProvider,
        @NotNull final String header,
        @NotNull final DecoratorFactory decoratorFactory,
        @NotNull final String packageName,
        @NotNull final String basePackageName,
        @NotNull final String repositoryName,
        final boolean implementMarkerInterfaces,
        final boolean jmx,
        @NotNull List<String> tableNames,
        @NotNull final String jndiLocation)
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

        immutableSetTableNames(tableNames);
    }

    /**
     * Specifies the table names.
     * @param tableNames such list.
     */
    protected final void immutableSetTableNames(@NotNull List<String> tableNames)
    {
        m__lTableNames = tableNames;
    }

    /**
     * Specifies the table names.
     * @param tableNames such list.
     */
    @SuppressWarnings("unused")
    protected void setTableNames(@NotNull List<String> tableNames)
    {
        immutableSetTableNames(tableNames);
    }

    /**
     * Retrieves the table names.
     * @return such list.
     */
    @NotNull
    protected final List<String> immutableGetTableNames()
    {
        return m__lTableNames;
    }

    /**
     * Retrieves the table names.
     * @return such list.
     */
    @NotNull
    public List<String> getTableNames()
    {
        @NotNull List<String> t_lOriginal = immutableGetTableNames();

        List<String> result = new ArrayList<String>(t_lOriginal);

        Collections.copy(result, t_lOriginal);

        return result;
    }

    @Override
    public int hashCode()
    {
        return new HashCodeBuilder().appendSuper(super.hashCode()).append(this.m__lTableNames).toHashCode();
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
        final BasePerRepositoryTemplateContext other = (BasePerRepositoryTemplateContext) obj;
        return new EqualsBuilder().appendSuper(super.equals(obj)).append(this.m__lTableNames, other.m__lTableNames)
            .isEquals();
    }
}
