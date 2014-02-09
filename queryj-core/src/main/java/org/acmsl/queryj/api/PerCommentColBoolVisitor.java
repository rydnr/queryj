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
 * Filename: PerCommentColBoolVisitor.java
 *
 * Author: Jose San Leandro Armendariz (chous)
 *
 * Description: Visits colBool rules in PerComment.g4 grammar.
 *
 * Date: 7/6/13
 * Time: 5:42 PM
 *
 */
package org.acmsl.queryj.api;

/*
 * Importing project classes.
 */
import org.acmsl.queryj.tools.antlr.PerCommentBaseVisitor;
import org.acmsl.queryj.tools.antlr.PerCommentParser.ColBoolContext;
import org.acmsl.queryj.tools.antlr.PerCommentParser.IdentContext;

/*
 * Importing JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing JDK classes.
 */
import java.util.ArrayList;
import java.util.List;

/**
 * Visits colBool rules in PerComment.g4 grammar.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro</a>
 * @since 2013/07/06
 */
public class PerCommentColBoolVisitor
    extends PerCommentBaseVisitor<List<String>>
{
    /**
     * Visits the parser tree within the <pre>tabRelationship</pre> rule.
     * @param context the parse context.
     * @return the list of boolean definitions. At position 0, the value representing {@code true}; 1 -> {@code false};
     * 2 -> {@code null}.
     */
    @NotNull
    @Override
    public List<String> visitColBool(@NotNull final ColBoolContext context)
    {
        @NotNull final List<String> result = new ArrayList<>(3);

        @NotNull final List<IdentContext> identContexts = context.ident();

        for (@Nullable final IdentContext ident : identContexts)
        {
            if (ident != null)
            {
                result.add(ident.getText().trim());
            }
        }

        return result;
    }
}