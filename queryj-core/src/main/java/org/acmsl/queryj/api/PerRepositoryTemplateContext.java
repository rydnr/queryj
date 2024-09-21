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
 * Importing QueryJ Core classes.
 */
import org.acmsl.queryj.QueryJCommand;

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
     * @param repositoryName the repository name.
     * @param tableNames the table names.
     * @param debug whether debugging is enabled.
     * @param command the command.
     */
    public PerRepositoryTemplateContext(
        @NotNull final String repositoryName,
        @NotNull final List<String> tableNames,
        final boolean debug,
        @NotNull final QueryJCommand command)
    {
        super(repositoryName, debug, command);

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
        return new ArrayList<>(immutableGetTableNames());
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

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode()
    {
        return new HashCodeBuilder().appendSuper(super.hashCode()).append(this.m__lTableNames).toHashCode();
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
        final PerRepositoryTemplateContext other = (PerRepositoryTemplateContext) obj;
        return new EqualsBuilder().appendSuper(super.equals(obj)).append(this.m__lTableNames, other.m__lTableNames)
            .isEquals();
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    public String toString()
    {
        return
              "{ \"class\": \"" + PerRepositoryTemplateContext.class.getSimpleName() + '"'
            + ", \"tableNames'\": [" + m__lTableNames + "], "
            + ", \"parent\": " + super.toString()
            + ", \"package\": \"org.acmsl.queryj.api\" }";
    }
}
