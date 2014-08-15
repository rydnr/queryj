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
 * Filename: PerForeignKeyTemplatesFeatureTemplate.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Used to generate sources using PerForeignKeyTemplatesFeature.stg.
 *
 * Date: 2014/04/16
 * Time: 13:09
 *
 */
package org.acmsl.queryj.templates.packaging;

/*
 * Importing JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/*
 * Importing StringTemplate 4 classes.
 */
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupString;

/**
 * Used to generate sources using PerForeignKeyTemplatesFeature.stg.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2014/04/16 13:09
 */
@ThreadSafe
public class PerForeignKeyTemplatesFeatureTemplate
    extends AbstractTemplatePackagingTemplate<GlobalTemplateContext>
{
    /**
     * The serial version id.
     */
    private static final long serialVersionUID = 106295972351691642L;

    /**
     * Builds a PerForeignKeyTemplatesFeature using given context.
     * @param context the {@link org.acmsl.queryj.templates.packaging.GlobalTemplateContext}.
     */
    public PerForeignKeyTemplatesFeatureTemplate(@NotNull final GlobalTemplateContext context)
    {
        super(context);
    }

    /**
     * Retrieves the template in given group.
     * @param group the StringTemplate group.
     * @return the template.
     */
    @Nullable
    @Override
    protected ST retrieveTemplate(@Nullable final STGroup group)
    {
        @Nullable final ST result = super.retrieveTemplate(group);

        if (group != null)
        {
            for (@NotNull final TemplateDef<String> def : getTemplateContext().getTemplateDefs())
            {
                @NotNull final String ruleBody =
                    def.getFilenameRule()
                    + "(engine, fileName) ::= <<\n" + def.getFilenameBuilder() + ">>\n";

                group.importTemplates(new STGroupString(def.getName() + "_filename", ruleBody));
            }
        }

        return result;
    }

    /**
     * Retrieves the template name.
     * @return "PerForeignKeyTemplatesFeature";
     */
    @NotNull
    @Override
    public String getTemplateName()
    {
        return Literals.PER_FOREIGN_KEY_TEMPLATES_FEATURE;
    }
}
