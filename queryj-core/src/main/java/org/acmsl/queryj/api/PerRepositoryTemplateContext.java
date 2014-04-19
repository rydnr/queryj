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
package org.acmsl.queryj.api;

/*
 * Importing project classes.
 */
import org.acmsl.queryj.customsql.CustomSqlProvider;
import org.acmsl.queryj.metadata.DecoratorFactory;
import org.acmsl.queryj.metadata.MetadataManager;

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
 * Importing some JDK classes.
 */
import java.util.ArrayList;
import java.util.List;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * Context used by per-repository templates.
 * @author <a href="mailto:chous@acm-sl.org">chous</a>
 * @since 2.0
 * Created: 2012/05/20
 */
@ThreadSafe
public class PerRepositoryTemplateContext
    extends AbstractQueryJTemplateContext
{
    /**
     * The serial version id.
     */
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
     * @param disableGenerationTimestamps whether to disable generation timestamps.
     * @param disableNotNullAnnotations whether to disable NotNull annotations.
     * @param disableCheckthreadAnnotations whether to disable checkthread.org annotations or not.
     */
    public PerRepositoryTemplateContext(
        @NotNull final MetadataManager metadataManager,
        @NotNull final CustomSqlProvider customSqlProvider,
        @Nullable final String header,
        @NotNull final DecoratorFactory decoratorFactory,
        @NotNull final String packageName,
        @NotNull final String basePackageName,
        @NotNull final String repositoryName,
        final boolean implementMarkerInterfaces,
        final boolean jmx,
        @NotNull final List<String> tableNames,
        @NotNull final String jndiLocation,
        final boolean disableGenerationTimestamps,
        final boolean disableNotNullAnnotations,
        final boolean disableCheckthreadAnnotations,
        @NotNull final String fileName)
    {
        // TODO
        super(null, null, null);

        immutableSetTableNames(tableNames);
    }

    /**
     * Specifies the table names.
     * @param tableNames such list.
     */
    protected final void immutableSetTableNames(@NotNull final List<String> tableNames)
    {
        m__lTableNames = tableNames;
    }

    /**
     * Specifies the table names.
     * @param tableNames such list.
     */
    @SuppressWarnings("unused")
    protected void setTableNames(@NotNull final List<String> tableNames)
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
        return new ArrayList<String>(immutableGetTableNames());
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    public String getTemplateName()
    {
        return getRepositoryName();
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
        final PerRepositoryTemplateContext other = (PerRepositoryTemplateContext) obj;
        return new EqualsBuilder().appendSuper(super.equals(obj)).append(this.m__lTableNames, other.m__lTableNames)
            .isEquals();
    }

    @NotNull
    @Override
    public String toString()
    {
        return "{ 'class': 'PerRepositoryTemplateContext'," +
               " 'tableNames': [" + m__lTableNames + "], " +
               " parent: " + super.toString() +
               '}';
    }
}
