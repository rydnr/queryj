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
 * Filename: AbstractBasePerForeignKeyTemplate.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Logic-less container for all templates to be processed once
 *              per custom Foreign Key.
 *
 */
package org.acmsl.queryj.api;

/*
 * Importing some project-specific classes.
 */
import org.acmsl.queryj.api.exceptions.InvalidPerForeignKeyTemplateException;
import org.acmsl.queryj.api.exceptions.InvalidTemplateException;
import org.acmsl.queryj.metadata.vo.ForeignKey;

/*
 * Importing some ACM-SL classes.
 */
import org.acmsl.commons.utils.StringUtils;

/*
 * Importing some JetBrains annotations.
 */
import org.antlr.stringtemplate.StringTemplate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing some JDK classes.
 */
import java.util.Collection;

/**
 * Logic-less container for all templates to be processed once per custom Foreign Key.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
@SuppressWarnings("unused")
public abstract class AbstractBasePerForeignKeyTemplate<C extends PerForeignKeyTemplateContext>
    extends  AbstractTemplate<C>
    implements PerForeignKeyTemplate<C>
{
    /**
     * Builds a <code>AbstractBasePerForeignKeyTemplate</code> using
     * given information.
     * @param context the {@link PerForeignKeyTemplateContext} instance.
     */
    public AbstractBasePerForeignKeyTemplate(@NotNull final C context)
    {
        super(context);
    }

    /**
     * Builds the header for logging purposes.
     * @return such header.
     */
    @NotNull
    protected String buildHeader()
    {
        return buildHeader(getTemplateName(), getTemplateContext());
    }

    /**
     * Builds the header for logging purposes.
     * @param templateName the template name.
     * @param context the {@link PerForeignKeyTemplateContext} instance.
     * @return such header.
     */
    @NotNull
    protected String buildHeader(
        final String templateName, @NotNull final C context)
    {
        return buildHeader(templateName, context.getForeignKey());
    }

    /**
     * Builds the header for logging purposes.
     * @param templateName the template name.
     * @param foreignKey the foreign key.
     * @return such header.
     */
    @NotNull
    protected String buildHeader(
        final String templateName, @NotNull final ForeignKey foreignKey)
    {
        return
              "Generating " + templateName + " for "
            + foreignKey.getSourceTableName()
            + "(" + concat(foreignKey.getAttributes(), ",") + ")"
            + "->"
            + foreignKey.getTargetTableName();
    }

    /**
     * Concatenates given list.
     * @param list the list.
     * @param separator the separator.
     * @return the concatenation of the elements in the list.
     */
    @NotNull
    protected String concat(@NotNull final Collection<?> list, @NotNull final String separator)
    {
        return concat(list, separator, StringUtils.getInstance());
    }

    /**
     * Concatenates given list.
     * @param list the list.
     * @param separator the separator.
     * @param stringUtils the <code>StringUtils</code> instance.
     * @return the concatenation of the elements in the list.
     */
    @NotNull
    protected String concat(
        @NotNull final Collection<?> list,
        @NotNull final String separator,
        @Nullable final StringUtils stringUtils)
    {
        @NotNull final String result;
        if (stringUtils != null)
        {
            result = stringUtils.concatenate(list, separator);
        }
        else
        {
            @NotNull final StringBuilder builder = new StringBuilder();
            for (@Nullable final Object item : list)
            {
                if (item != null)
                {
                    builder.append(item);
                }
            }

            result = builder.toString();
        }

        return result;
    }

    /**
     * Builds a context-specific exception.
     * @param context the context.
     * @param template the {@link StringTemplate} instance.
     * @return the specific {@link InvalidTemplateException} for the template.
     */
    @Override
    @NotNull
    public InvalidTemplateException buildInvalidTemplateException(
        @NotNull final C context,
        @NotNull final StringTemplate template,
        @NotNull final Throwable actualException)
    {
        return
            new InvalidPerForeignKeyTemplateException(
                getTemplateName(),
                context.getForeignKey(),
                context.getRepositoryName(),
                actualException);
    }}
