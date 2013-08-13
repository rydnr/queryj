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
 * Filename: TemplateDefTypeVisitor.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: ANTLR4 visitor to retrieve the type definition.
 *
 * Date: 2013/08/13
 * Time: 17:23
 *
 */
package org.acmsl.queryj.templates.packaging.antlr;

/*
 * Importing ANTLR-generated classes.
 */
import org.acmsl.queryj.templates.packaging.antlr.TemplateDefParser.TypeRuleContext;

/*
 * Importing JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.NotThreadSafe;

/**
 * ANTLR4 visitor to retrieve the type definition.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2013/08/13 17:23
 */
@NotThreadSafe
public class TemplateDefTypeVisitor
    extends TemplateDefBaseVisitor<String>
{
    /**
     * The type of the template def.
     */
    @NotNull
    private String m__strType;

    /**
     * Specifies the type.
     * @param type the type.
     */
    protected final void immutableSetType(@NotNull final String type)
    {
        this.m__strType = type;
    }

    /**
     * Specifies the type.
     * @param type the type.
     */
    protected void setType(@NotNull final String type)
    {
        immutableSetType(type);
    }

    /**
     * Retrieves the type.
     * @return such type.
     */
    public String getType()
    {
        return this.m__strType;
    }


    /**
     * {@inheritDoc}
     * <p/>
     * The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.
     */
    @Override
    public String visitTypeRule(@org.antlr.v4.runtime.misc.NotNull final TypeRuleContext context)
    {
        setType(context.getChild(2).getText());
        return super.visitTypeRule(context);
    }

    @NotNull
    @Override
    public String toString()
    {
        return "{ 'class': 'TemplateDefTypeVisitor', " +
               " 'type': '" + m__strType + "' }";
    }
}
