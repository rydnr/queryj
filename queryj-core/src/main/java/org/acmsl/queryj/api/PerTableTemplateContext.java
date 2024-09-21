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
 * Filename: BasePerTableTemplateContext.java
 *
 * Author: Jose San Leandro Armendariz (chous)
 *
 * Description: Context used by per-table templates.
 *
 * Date: 5/20/12
 * Time: 8:05 AM
 *
 */
package org.acmsl.queryj.api;

/*
 * Importing QueryJ Core classes.
 */
import org.acmsl.queryj.QueryJCommand;
import org.acmsl.queryj.api.exceptions.StaticValuesNotAvailableException;
import org.acmsl.queryj.api.exceptions.TableNameNotAvailableException;
import org.acmsl.queryj.metadata.MetadataManager;
import org.acmsl.queryj.metadata.vo.Attribute;
import org.acmsl.queryj.metadata.vo.Row;
import org.acmsl.queryj.metadata.vo.Table;

/*
 * Importing Apache Commons Lang classes.
 */
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/*
 * Importing JetBrains annotations.
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
 * Context used by per-table templates.
 * @author <a href="mailto:chous@acm-sl.org">chous</a>
 * @since 3.0
 * Created: 2012/05/20
 */
@ThreadSafe
public class PerTableTemplateContext
    extends AbstractQueryJTemplateContext
{
    /**
     * The serial version id.
     */
    private static final long serialVersionUID = -7439946925532182308L;

    /**
     * Creates a {@code PerTableTemplateContext} with given information.
     * @param tableName the table name.
     * @param staticValues the static values.
     * @param debug whether debugging is enabled.
     * @param command the {@link QueryJCommand}.
     */
    public PerTableTemplateContext(
        @NotNull final String tableName,
        @NotNull final List<Row<String>> staticValues,
        final boolean debug,
        @NotNull final QueryJCommand command)
    {
        super(tableName, debug, command);

        immutableSetValue(buildTableNameKey(), tableName, command);
        immutableSetValue(buildStaticValuesKey(), staticValues, command);
    }

    /**
     * Retrieves the key for the table name.
     * @return such key.
     */
    @NotNull
    protected String buildTableNameKey()
    {
        return "tableName@" + hashCode();
    }

    /**
     * Retrieves the table name.
     * @return such name.
     */
    @NotNull
    public String getTableName()
    {
        return getValue(buildTableNameKey(), getCommand(), new TableNameNotAvailableException());
    }

    /**
     * Retrieves the static values' key.
     * @return such key.
     */
    @NotNull
    protected String buildStaticValuesKey()
    {
        return "staticValues@" + hashCode();
    }

    /**
     * Retrieves the static values.
     * @return such values.
     */
    @SuppressWarnings("unused")
    @NotNull
    public List<Row<String>> getStaticValues()
    {
        return
            new ArrayList<Row<String>>(
                (List<Row<String>>)
                    super.<Row<String>>getListValue(
                        buildStaticValuesKey(), getCommand(), new StaticValuesNotAvailableException()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @NotNull
    public String getTemplateName()
    {
        return getTemplateName(getTableName(), getMetadataManager());
    }

    /**
     * Retrieves a name based on the table name and its attributes.
     * @param tableName the table name.
     * @param metadataManager the {@link MetadataManager} instance.
     * @return such information.
     */
    @NotNull
    protected String getTemplateName(
        @NotNull final String tableName, @NotNull final MetadataManager metadataManager)
    {
        @NotNull final StringBuilder result = new StringBuilder(tableName);

        @Nullable final Table<String, Attribute<String>, List<Attribute<String>>> t_Table =
            metadataManager.getTableDAO().findByName(tableName);

        if (t_Table != null)
        {
            result.append("(");
            result.append(toCsv(t_Table.getAttributes()));
            result.append(")");
        }

        return result.toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode()
    {
        return
            new HashCodeBuilder()
                .appendSuper(super.hashCode())
                .append(PerTableTemplateContext.class.getName())
                .append(this.getCommand())
                .toHashCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(@Nullable final Object obj)
    {
        final boolean result;

        if (obj == null)
        {
            result = false;
        }
        else if (getClass() != obj.getClass())
        {
            result = false;
        }
        else if (obj instanceof PerTableTemplateContext)
        {
            final PerTableTemplateContext other = (PerTableTemplateContext) obj;

            result =
                new EqualsBuilder()
                    .appendSuper(super.equals(obj))
                    .append(this.getCommand(), other.getCommand())
                    .isEquals();
        }
        else
        {
            result = false;
        }

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    public String toString()
    {
        return
              "{ \"class\": \"" + PerTableTemplateContext.class.getSimpleName() + '"'
            + ", \"package\": \"org.acmsl.queryj.api\""
            + ", \"command\": " + getCommand().hashCode()
            + " }";
    }
}
