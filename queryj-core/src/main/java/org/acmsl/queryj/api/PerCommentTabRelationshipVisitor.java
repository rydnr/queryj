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
 * Filename: PerCommentTabRelationshipVisitor.java
 *
 * Author: Jose San Leandro Armendariz (chous)
 *
 * Description: Visits tabRelationship rules in PerComment.g4 grammar.
 *
 * Date: 7/6/13
 * Time: 5:12 PM
 *
 */
package org.acmsl.queryj.api;

/*
 * Importing project classes.
 */
import org.acmsl.queryj.tools.antlr.PerCommentBaseVisitor;
import org.acmsl.queryj.tools.antlr.PerCommentParser.TabRelationshipContext;
import org.acmsl.queryj.tools.antlr.PerCommentParser.TextOrIdContext;

/*
 * Importing ANTLR classes.
 */
import org.antlr.v4.runtime.tree.TerminalNode;

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
 * Visits tabRelationship rules in PerComment.g4 grammar.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro</a>
 * @since 2013/07/06
 */
public class PerCommentTabRelationshipVisitor
    extends PerCommentBaseVisitor<List<List<String>>>
{
    /**
     * Visits the parser tree within the <pre>tabRelationship</pre> rule.
     * @param context the parse context.
     * @return the list of relationship columns.
     */
    @Override
    public List<List<String>> visitTabRelationship(@NotNull final TabRelationshipContext context)
    {
        @NotNull final List<List<String>> result = new ArrayList<List<String>>();

        @NotNull final List<TextOrIdContext> textOrIdContexts = context.textOrId();

        @NotNull final List<TerminalNode> ids = context.ID();

        for (int index = 0; index < textOrIdContexts.size() ; index++)
        {
            @Nullable final List<String> currentDuple = new ArrayList<String>(2);

            @Nullable final TextOrIdContext textOrIdContext = textOrIdContexts.get(index);
            @Nullable final TerminalNode id = ids.get(index);

            if (textOrIdContext != null)
            {
                currentDuple.add(textOrIdContext.getText());
            }
            if (id != null)
            {
                currentDuple.add(id.getText());
            }

            result.add(currentDuple);
        }

        return result;
    }
}
