/*
                        queryj

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
 * Filename: TemplateDefFilenameBuilderVisitor.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: ANTLR4 visitor to retrieve the filename builder definition.
 *
 * Date: 2013/08/13
 * Time: 17:42
 *
 */
package org.acmsl.queryj.templates.packaging.antlr;

/*
 * Importing ANTLR4-generated classes.
 */
import org.acmsl.queryj.templates.packaging.antlr.TemplateDefParser.FilenameBuilderRuleContext;

/*
 * Importing JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.NotThreadSafe;

/**
 * ANTLR4 visitor to retrieve the filename builder definition.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2013/08/13 17:42
 */
@NotThreadSafe
public class TemplateDefFilenameBuilderVisitor
    extends TemplateDefBaseVisitor<String>
{
    /**
     * The filename builder.
     */
    private String m__strFilenameBuilder;

    /**
     * Specifies the filename builder.
     * @param builder the builder.
     */
    protected final void immutablesetFilenameBuilder(final String builder)
    {
        this.m__strFilenameBuilder = builder;
    }

    /**
     * Specifies the filename builder.
     * @param builder the builder.
     */
    protected void setFilenameBuilder(final String builder)
    {
        immutablesetFilenameBuilder(builder);
    }

    /**
     * Retrieves the filename builder.
     * @return such information.
     */
    public String getFilenameBuilder()
    {
        return this.m__strFilenameBuilder;
    }

    /**
     * {@inheritDoc}
     * <p/>
     * The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.
     */
    @Override
    public String visitFilenameBuilderRule(@NotNull final FilenameBuilderRuleContext ctx)
    {
        setFilenameBuilder(ctx.getChild(3).getText());

        return super.visitFilenameBuilderRule(ctx);
    }

    @Override
    public String toString()
    {
        return "{ 'class': 'TemplateDefFilenameBuilderVisitor', " +
               "'filenameBuilder': '" + m__strFilenameBuilder + "' }";
    }
}
