/*
/*
                        QueryJ Template Packaging

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
 * Filename: TemplateDefDisabledVisitor.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: ANTLR4 visitor to retrieve whether the template def is
 *              disabled or not.
 *
 * Date: 2013/12/07
 * Time: 11:06
 *
 */
package org.acmsl.queryj.templates.packaging.antlr;

/*
 * Importing project classes.
 */
import org.acmsl.queryj.templates.packaging.antlr.TemplateDefParser.DisabledRuleContext;

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
 * ANTLR4 visitor to retrieve whether the template def is disabled or not.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2013/12/07 11:06
 */
@ThreadSafe
public class TemplateDefDisabledVisitor
    extends TemplateDefBaseVisitor<Boolean>
{
    /**
     * Whether the template def is disabled or not.
     */
    private boolean m__bDisabled;

    /**
     * Specifies whether the template def is disabled.
     * @param disabled if the template def is disabled.
     */
    protected final void immutableSetDisabled(final boolean disabled)
    {
        this.m__bDisabled = disabled;
    }

    /**
     * Specifies whether the template def is disabled.
     * @param disabled if the template def is disabled.
     */
    @SuppressWarnings("unused")
    protected void setDisabled(final boolean disabled)
    {
        immutableSetDisabled(disabled);
    }

    /**
     * Retrieves whether the template def is disabled.
     * @return such information.
     */
    public boolean isDisabled()
    {
        return this.m__bDisabled;
    }

    /**
     * {@inheritDoc}
     * The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.
     */
    @Override
    @Nullable
    public Boolean visitDisabledRule(@NotNull final DisabledRuleContext ctx)
    {
        setDisabled(true);

        return super.visitDisabledRule(ctx);
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    public String toString()
    {
        return
              "{ \"class\": \"" + TemplateDefDisabledVisitor.class.getName() + '"'
            + ", \"disabled\": " + m__bDisabled + " }";
    }
}
