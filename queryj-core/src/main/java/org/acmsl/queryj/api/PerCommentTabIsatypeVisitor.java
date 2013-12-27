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
 * Filename: PerCommentTabIsatypeVisitor.java
 *
 * Author: Jose San Leandro Armendariz (chous)
 *
 * Description: 
 *
 * Date: 7/6/13
 * Time: 3:15 PM
 *
 */
package org.acmsl.queryj.api;

/*
 * Importing project classes.
 */
import org.acmsl.queryj.tools.antlr.PerCommentBaseVisitor;
import org.acmsl.queryj.tools.antlr.PerCommentParser.IdentContext;
import org.acmsl.queryj.tools.antlr.PerCommentParser.TabIsatypeContext;

/*
 * Impoting JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Visits tabIsa rules in PerComment.g4 grammar.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro</a>
 * @since 2013/07/06
 */
public class PerCommentTabIsatypeVisitor
    extends PerCommentBaseVisitor<String>
{
    /**
     * Visits the parser tree within the <pre>tabStatic</pre> rule.
     * @param context the parse context.
     * @return the name of the parent ISA table.
     */
    @Override
    public String visitTabIsatype(@NotNull final TabIsatypeContext context)
    {
        String result = "";

        @Nullable final IdentContext identContext = context.ident();

        if (identContext != null)
        {
            result = identContext.getText();
        }

        return result;
    }
}
