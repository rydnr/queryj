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
package org.acmsl.queryj.templates;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.customsql.CustomSqlProvider;
import org.acmsl.queryj.metadata.DecoratorFactory;
import org.acmsl.queryj.metadata.MetadataManager;
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

/**
 * Context information required by templates customized for each {@link ForeignKey}.
 * @author <a href="mailto:chous@acm-sl.org">chous</a>
 * @since 2012/05/20
 */
public class BasePerForeignKeyTemplateContext
    extends AbstractTemplateContext
{
    private static final long serialVersionUID = 1350908613901423440L;

    /**
     * The result.
     */
    private ForeignKey m__ForeignKey;

    /**
     * Creates a {@link BasePerForeignKeyTemplateContext} with given information.
     * @param metadataManager the {@link MetadataManager} instance.
     * @param customSqlProvider the {@link CustomSqlProvider} instance.
     * @param header the header.
     * @param decoratorFactory the {@link DecoratorFactory} instance.
     * @param packageName the package name.
     * @param basePackageName the base package name.
     * @param repositoryName the repository name.
     * @param implementMarkerInterfaces whether to implement marker interfaces or not.
     * @param jndiLocation the JNDI path of the {@link javax.sql.DataSource}.
     * @param foreignKey the {@link ForeignKey} instance.
     */
    public BasePerForeignKeyTemplateContext(
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
        @NotNull final ForeignKey foreignKey)
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

        immutableSetForeignKey(foreignKey);
    }

    /**
     * Specifies the foreign key.
     * @param foreignKey the foreign key.
     */
    protected final void immutableSetForeignKey(@NotNull final ForeignKey foreignKey)
    {
        m__ForeignKey = foreignKey;
    }

    /**
     * Specifies the foreign key.
     * @param foreignKey the foreign key.
     */
    @SuppressWarnings("unused")
    protected void setForeignKey(@NotNull final ForeignKey foreignKey)
    {
        immutableSetForeignKey(foreignKey);
    }

    /**
     * Retrieves the foreign key.
     * @return such information.
     */
    @NotNull
    public ForeignKey getForeignKey()
    {
        return m__ForeignKey;
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
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
    protected String getTemplateName(@NotNull final ForeignKey foreignKey)
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

    @Override
    public int hashCode()
    {
        return new HashCodeBuilder().appendSuper(super.hashCode()).append(this.m__ForeignKey).toHashCode();
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
        final BasePerForeignKeyTemplateContext other = (BasePerForeignKeyTemplateContext) obj;
        return new EqualsBuilder().appendSuper(super.equals(obj)).append(this.m__ForeignKey, other.m__ForeignKey)
            .isEquals();
    }
}