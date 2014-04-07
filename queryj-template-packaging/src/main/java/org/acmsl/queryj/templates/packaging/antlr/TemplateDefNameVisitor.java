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
 * Filename: TemplateDefNameVisitor.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: ANTLR4 visitor to retrieve the name definition.
 *
 * Date: 2013/08/12
 * Time: 14:42
 *
 */
package org.acmsl.queryj.templates.packaging.antlr;

/*
 * Importing ANTLR-generated classes..
 */
import org.acmsl.queryj.templates.packaging.antlr.TemplateDefParser.NameRuleContext;

/*
 * Importing JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.NotThreadSafe;

/**
 * ANTLR4 visitor to retrieve the name definition.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2013/08/12 14:42
 */
@NotThreadSafe
public class TemplateDefNameVisitor
    extends TemplateDefBaseVisitor<String>
{
    /**
     * The name of the template def.
     */
    @NotNull
    private String m__strName;

    /**
     * Specifies the name.
     * @param name the name.
     */
    protected final void immutableSetName(@NotNull final String name)
    {
        this.m__strName = name;
    }

    /**
     * Specifies the name.
     * @param name the name.
     */
    protected void setName(@NotNull final String name)
    {
        immutableSetName(name);
    }

    /**
     * Retrieves the name.
     * @return such name.
     */
    public String getName()
    {
        return this.m__strName;
    }

    /**
     * Visits the name rule.
     * @param context the context.
     * @return the defined attribute for 'name'.
     */
    @Override
    public String visitNameRule(@NotNull final NameRuleContext context)
    {
        setName(context.getChild(2).getText());

        return super.visitNameRule(context);
    }

    @NotNull
    @Override
    public String toString()
    {
        return "{ 'class': 'TemplateDefNameVisitor', " +
               "'name': '" + m__strName + "' }";
    }
}
