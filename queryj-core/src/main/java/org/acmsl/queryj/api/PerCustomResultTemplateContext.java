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
import org.acmsl.queryj.Literals;
import org.acmsl.queryj.QueryJCommand;
import org.acmsl.queryj.api.exceptions.MissingPropertiesException;
import org.acmsl.queryj.api.exceptions.MissingResultException;
import org.acmsl.queryj.customsql.Property;
import org.acmsl.queryj.customsql.Result;

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

/*
 * Importing JDK classes.
 */
import java.util.List;

/**
 * Context information required by templates customized for each {@link Result}
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 2.0
 * Created: 2012/05/20
 */
@ThreadSafe
public class PerCustomResultTemplateContext
    extends AbstractQueryJTemplateContext
{
    /**
     * The serial version id.
     */
    private static final long serialVersionUID = 5193168262427622240L;

    /**
     * Creates a {@link PerCustomResultTemplateContext} with given information.
     * @param result the {@link Result} instance.
     * @param properties the properties.
     * @param command the command.
     */
    public PerCustomResultTemplateContext(
        @NotNull final Result<String> result,
        @NotNull final List<Property<String>> properties,
        @NotNull final QueryJCommand command)
    {
        super(result.getId(), command);

        immutableSetValue(buildResultKey(), result, command);
        immutableSetValue(buildPropertiesKey(), properties, command);
    }

    /**
     * Retrieves the result key.
     * @return "result".
     */
    @NotNull
    public String buildResultKey()
    {
        return Literals.RESULT;
    }

    /**
     * Retrieves the result.
     * @return such information.
     */
    @NotNull
    public Result<String> getResult()
    {
        return getValue(buildResultKey(), getCommand(), new MissingResultException());
    }

    /**
     * Retrieves the properties key.
     * @return "properties".
     */
    @NotNull
    protected String buildPropertiesKey()
    {
        return "properties.for." + getPk();
    }

    /**
     * Retrieves the properties.
     * @return the list of properties.
     */
    @NotNull
    public List<Property<String>> getProperties()
    {
        return getListValue(buildPropertiesKey(), getCommand(), new MissingPropertiesException());
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
    public String getTemplateName(@NotNull final Result<String> customResult)
    {
        String result = customResult.getClassValue();

        if (result == null)
        {
            result = customResult.getId();
        }

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode()
    {
        return new HashCodeBuilder().appendSuper(super.hashCode()).toHashCode();
    }

    /**
     * {@inheritDoc}
     */
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
        return new EqualsBuilder().appendSuper(super.equals(obj)).isEquals();
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    public String toString()
    {
        return
              "{ \"class\": \"" + PerCustomResultTemplateContext.class.getSimpleName() + '"'
            + ", \"result\": " + getResult()
            + ", \"properties\": " + getProperties()
            + ", \"package\": \"org.acmsl.queryj.api\" }";
    }
}
