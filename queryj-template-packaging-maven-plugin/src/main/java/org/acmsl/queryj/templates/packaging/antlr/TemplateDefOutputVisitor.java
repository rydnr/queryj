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
 * Filename: TemplateDefOutputVisitor.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: ANTLR4 visitor to retrieve the output definition.
 *
 * Date: 2013/08/13
 * Time: 17:33
 *
 */
package org.acmsl.queryj.templates.packaging.antlr;

/*
 * Importing ANTLR-generated classes.
 */
import org.acmsl.queryj.templates.packaging.antlr.TemplateDefParser.OutputRuleContext;

/*
 * Importing JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.NotThreadSafe;

/**
 * ANTLR4 visitor to retrieve the output definition.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2013/08/13 17:33
 */
@NotThreadSafe
public class TemplateDefOutputVisitor
    extends TemplateDefBaseVisitor<String>
{
    /**
     * The output value.
     */
    private String m__strOutput;

    /**
     * Specifies the output.
     * @param output the output value.
     */
    protected final void immutableSetOutput(@NotNull final String output)
    {
        this.m__strOutput = output;
    }

    /**
     * Specifies the output.
     * @param output the output value.
     */
    protected void setOutput(@NotNull final String output)
    {
        immutableSetOutput(output);
    }

    /**
     * Retrieves the output.
     * @return such information.
     */
    @NotNull
    public String getOutput()
    {
        return this.m__strOutput;
    }

    /**
     * {@inheritDoc}
     * <p/>
     * The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.
     */
    @Override
    public String visitOutputRule(@NotNull final OutputRuleContext ctx)
    {
        setOutput(ctx.getChild(2).getText());

        return super.visitOutputRule(ctx);
    }

    @Override
    public String toString()
    {
        return "{ 'class': 'TemplateDefOutputVisitor', " +
               " 'output': '" + m__strOutput + "' }";
    }
}
