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
 * Filename: TemplateDefDebugVisitor.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Visitor for "debug" rule.
 *
 * Date: 2014/05/12
 * Time: 05:14
 *
 */
package org.acmsl.queryj.templates.packaging.antlr;

/*
 * Importing QueryJ Template Packaging classes.
 */
import org.acmsl.queryj.templates.packaging.antlr.TemplateDefParser.DebugRuleContext;

/*
 * Importing JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * Visitor for "debug" rule.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2014/05/12 05:14
 */
@ThreadSafe
public class TemplateDefDebugVisitor
    extends TemplateDefBaseVisitor<Boolean>
{
    /**
     * Whether the template def allows debugging or not.
     */
    private boolean m__bDebug;

    /**
     * Specifies whether the template def is marked as debugged.
     * @param debug if the template def is being debugged.
     */
    protected final void immutableSetDebug(final boolean debug)
    {
        this.m__bDebug = debug;
    }

    /**
     * Specifies whether the template def is marked as debugged.
     * @param debug if the template def is being debugged.
     */
    @SuppressWarnings("unused")
    protected void setDebug(final boolean debug)
    {
        immutableSetDebug(debug);
    }

    /**
     * Retrieves whether the template def is marked as debugged.
     * @return such behavior.
     */
    public boolean isDebug()
    {
        return this.m__bDebug;
    }

    /**
     * {@inheritDoc}
     * The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.
     */
    @Override
    @Nullable
    public Boolean visitDebugRule(@NotNull final DebugRuleContext ctx)
    {
        setDebug(true);

        return super.visitDebugRule(ctx);
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    public String toString()
    {
        return
            "{ \"class\": \"" + TemplateDefDebugVisitor.class.getSimpleName() + '"'
            + ", \"debug\": " + m__bDebug + " }";
    }
}
