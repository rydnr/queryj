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
 * Filename: TemplateDefMetadataVisitor.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Visits metadata rules.
 *
 * Date: 2014/05/25
 * Time: 12:01
 *
 */
package org.acmsl.queryj.templates.packaging.antlr;

/*
 * Importing JetBrains annotations.
 */
import org.acmsl.queryj.templates.packaging.antlr.TemplateDefParser.MetadataRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;
import org.jetbrains.annotations.NotNull;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/*
 * Importing JDK classes.
 */
import java.util.HashMap;
import java.util.Map;

/**
 * Visits metadata rules.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2014/05/25 12:01
 */
@ThreadSafe
public class TemplateDefMetadataVisitor
    extends TemplateDefBaseVisitor<Map<String, String>>
{
    /**
     * The metadata.
     */
    private Map<String, String> m__mMetadata;

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override
    public Map<String, String> visitMetadataRule(
        @NotNull final TemplateDefParser.MetadataRuleContext ctx)
    {
        @NotNull final Map<String, String> result;

        @NotNull final ParseTree metadata = ctx.getChild(3);

        result = new HashMap<>(metadata.getChildCount() / 2);

        for (int index = 0; index < metadata.getChildCount() - 2; index += 2)
        {
            result.put(metadata.getChild(index).getText(), metadata.getChild(index+2).getText());
        }

        setMetadata(result);

        return visitChildren(ctx);
    }

    /**
     * Specifies the metadata.
     * @param metadata such metadata.
     */
    protected final void immutableSetMetadata(@NotNull final Map<String, String> metadata)
    {
        this.m__mMetadata = metadata;
    }

    /**
     * Specifies the metadata.
     * @param metadata such metadata.
     */
    protected void setMetadata(@NotNull final Map<String, String> metadata)
    {
        immutableSetMetadata(metadata);
    }

    /**
     * Retrieves the metadata.
     * @return such metadata.
     */
    @NotNull
    public Map<String, String> getMetadata()
    {
        return m__mMetadata;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @NotNull
    public String toString()
    {
        return
              "{ \"metadata\": " + this.m__mMetadata
            + ", \"class\": \"TemplateDefMetadataVisitor\""
            + ", \"package\": \"org.acmsl.queryj.templates.packaging.antlr\" }";
    }
}
