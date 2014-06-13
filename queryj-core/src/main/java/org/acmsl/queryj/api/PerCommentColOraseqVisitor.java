/*
                        QueryJ Core

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
 * Filename: PerCommentColOraseqVisitor.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Visits colOraseq rules in PerComment.g4 grammar.
 *
 * Date: 2014/06/13
 * Time: 19:57
 *
 */
package org.acmsl.queryj.api;

/*
 * Importing QueryJ Core classes.
 */
import org.acmsl.queryj.tools.antlr.PerCommentBaseVisitor;
import org.acmsl.queryj.tools.antlr.PerCommentParser.ColOraseqContext;

/*
 * Importing ANTLR classes.
 */
import org.antlr.v4.runtime.tree.ParseTree;

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
 * Visits colOraseq rules in PerComment.g4 grammar.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2014/06/13 19:57
 */
@ThreadSafe
public class PerCommentColOraseqVisitor
    extends PerCommentBaseVisitor<String>
{
    /**
     * Visits the parser tree within the <pre>colReadonly</pre> rule.
     * @param context the parse context.
     * @return {@code true} if the comment declares the column is read-only.
     */
    @Nullable
    @Override
    public String visitColOraseq(@NotNull final ColOraseqContext context)
    {
        @Nullable final String result;

        if (context.getChildCount() > 1)
        {
            @Nullable final ParseTree secondChild = context.getChild(1);

            if (secondChild == null)
            {
                result = null;
            }
            else
            {
                @Nullable final String text = secondChild.getText();

                if (text == null)
                {
                    result = null;
                }
                else
                {
                    result = text.toString().trim();
                }
            }
        }
        else
        {
            result = null;
        }

        return result;
    }
}

