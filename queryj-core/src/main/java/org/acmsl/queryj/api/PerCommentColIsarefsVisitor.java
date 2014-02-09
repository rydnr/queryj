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
 * Filename: PerCommentColIsarefsVisitor.java
 *
 * Author: Jose San Leandro Armendariz (chous)
 *
 * Description: Visits colIsarefs rules in PerComment.g4 grammar.
 *
 * Date: 7/6/13
 * Time: 5:36 PM
 *
 */
package org.acmsl.queryj.api;

/*
 * Importing project classes.
 */
import org.acmsl.queryj.tools.antlr.PerCommentBaseVisitor;
import org.acmsl.queryj.tools.antlr.PerCommentParser.ColIsarefsContext;
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
 * Visits colIsarefs rules in PerComment.g4 grammar.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro</a>
 * @since 2013/07/06
 */
public class PerCommentColIsarefsVisitor
    extends PerCommentBaseVisitor<List<List<String>>>
{
    /**
     * Visits the parser tree within the <pre>colIsarefs</pre> rule.
     * @param context the parse context.
     * @return the list of ISA column references.
     */
    @NotNull
    @Override
    public List<List<String>> visitColIsarefs(@NotNull final ColIsarefsContext context)
    {
        @NotNull final List<List<String>> result = new ArrayList<>(3);

        @NotNull final List<IdentContext> ids = context.ident();

        @NotNull List<String> currentDuple = new ArrayList<>(2);

        for (int index = 0; index < ids.size() ; index++)
        {
            @Nullable final IdentContext id = ids.get(index);

            if (id != null)
            {
                currentDuple.add(id.getText().trim());
            }

            if ((index % 2) != 0)
            {
                result.add(currentDuple);

                if (index < ids.size() - 1)
                {
                    currentDuple = new ArrayList<>(2);
                }
            }
        }

        return result;
    }
}