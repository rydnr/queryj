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
 * Filename: BasePerCustomResultTemplateContext.java
 *
 * Author: Jose San Leandro Armendariz (chous)
 *
 * Description: Context information required by templates customized for each
 *              Result.
 *
 * Date: 5/20/12
 * Time: 6:44 AM
 *
 */
package org.acmsl.queryj.api;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.customsql.CustomSqlProvider;
import org.acmsl.queryj.customsql.Result;
import org.acmsl.queryj.metadata.DecoratorFactory;
import org.acmsl.queryj.metadata.MetadataManager;

/*
 * Importing some Apache Commons Lang builder classes.
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
import org.jetbrains.annotations.Nullable;

/**
 * Context information required by templates customized for each {@link Result}
 * @author <a href="mailto:chous@acm-sl.org">chous</a>
 * @since 2012/05/20
 */
@ThreadSafe
public class PerCustomResultTemplateContext
    extends AbstractTemplateContext
{

    private static final long serialVersionUID = 5193168262427622240L;
    /**
     * The result.
     */
    private Result m__Result;

    /**
     * Creates a {@link PerCustomResultTemplateContext} with given information.
     * @param metadataManager the {@link MetadataManager} instance.
     * @param customSqlProvider the {@link CustomSqlProvider} instance.
     * @param header the header.
     * @param decoratorFactory the {@link DecoratorFactory} instance.
     * @param packageName the package name.
     * @param basePackageName the base package name.
     * @param repositoryName the repository name.
     * @param implementMarkerInterfaces whether to implement marker interfaces or not.
     * @param jmx whether to include JMX support.
     * @param jndiLocation the JNDI location.
     * @param disableGenerationTimestamps whether to disable generation timestamps.
     * @param disableNotNullAnnotations whether to disable NotNull annotations.
     * @param disableCheckthreadAnnotations whether to disable checkthread.org annotations or not.
     * @param result the {@link Result} instance.
     */
    public PerCustomResultTemplateContext(
        @NotNull final MetadataManager metadataManager,
        @NotNull final CustomSqlProvider customSqlProvider,
        @Nullable final String header,
        @NotNull final DecoratorFactory decoratorFactory,
        @NotNull final String packageName,
        @NotNull final String basePackageName,
        @NotNull final String repositoryName,
        final boolean implementMarkerInterfaces,
        final boolean jmx,
        @NotNull final String jndiLocation,
        final boolean disableGenerationTimestamps,
        final boolean disableNotNullAnnotations,
        final boolean disableCheckthreadAnnotations,
        @NotNull final Result result)
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
            jndiLocation,
            disableGenerationTimestamps,
            disableNotNullAnnotations,
            disableCheckthreadAnnotations);

        immutableSetResult(result);
    }

    /**
     * Specifies the result.
     * @param result the custom result.
     */
    protected final void immutableSetResult(@NotNull final Result result)
    {
        m__Result = result;
    }

    /**
     * Specifies the result.
     * @param result the custom result.
     */
    @SuppressWarnings("unused")
    protected void setResult(@NotNull final Result result)
    {
        immutableSetResult(result);
    }

    /**
     * Retrieves the result.
     * @return such information.
     */
    @NotNull
    public Result getResult()
    {
        return m__Result;
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    public String getTemplateName()
    {
        return getTemplateName(getResult());
    }

    /**
     * Retrieves the template name.
     * @param customResult the {@link Result} instance.
     * @return the template name.
     */
    @NotNull
    public String getTemplateName(@NotNull final Result customResult)
    {
        String result = customResult.getClassValue();

        if (result == null)
        {
            result = customResult.getId();
        }

        return result;
    }

    @Override
    public int hashCode()
    {
        return new HashCodeBuilder().appendSuper(super.hashCode()).append(this.m__Result).toHashCode();
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
        final PerCustomResultTemplateContext other = (PerCustomResultTemplateContext) obj;
        return new EqualsBuilder().appendSuper(super.equals(obj)).append(this.m__Result, other.m__Result).isEquals();
    }
}
