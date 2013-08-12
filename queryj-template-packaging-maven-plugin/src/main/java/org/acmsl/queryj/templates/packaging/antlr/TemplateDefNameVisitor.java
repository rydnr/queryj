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
 * Description: 
 *
 * Date: 2013/08/12
 * Time: 14:42
 *
 */
package org.acmsl.queryj.templates.packaging.antlr;
/**
 * Importing JetBrains annotations.
 */

import org.antlr.v4.runtime.tree.ParseTree;
import org.checkthread.annotations.NotThreadSafe;
import org.jetbrains.annotations.NotNull;

/**
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
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
    public String visitName(@NotNull final TemplateDefParser.NameContext context)
    {
        setName(context.getText());

        return super.visitName(context);
    }
}
