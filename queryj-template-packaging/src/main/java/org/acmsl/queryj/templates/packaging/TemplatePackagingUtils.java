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
 * Filename: TemplatePackagingUtils.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Utility class for template packaging.
 *
 * Date: 2014/06/03
 * Time: 01:17
 *
 */
package org.acmsl.queryj.templates.packaging;

/*
 * Importing JetBrains annotations.
 */
import org.acmsl.commons.patterns.Singleton;
import org.acmsl.queryj.metadata.DecoratedString;
import org.jetbrains.annotations.NotNull;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.regex.Pattern;

/**
 * Utility class for template packaging.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2014/06/03 01:17
 */
@ThreadSafe
public class TemplatePackagingUtils
    implements Singleton
{
    /**
     * The regex to match .stg extensions.
     */
    @NotNull
    private static final Pattern STG_EXT = Pattern.compile("\\.stg$");

    /**
     * The regex to match .stg.def extensions.
     */
    @NotNull
    private static final Pattern STG_DEF_EXT = Pattern.compile("\\.stg\\.def$");

    /**
     * Singleton implemented to avoid the double check locking.
     */
    private static final class TemplatePackagingUtilsSingletonContainer
    {
        /**
         * The singleton.
         */
        public static final TemplatePackagingUtils SINGLETON = new TemplatePackagingUtils();
    }

    /**
     * Default constructor.
     */
    public TemplatePackagingUtils() {}

    /**
     * Retrieves the singleton instance.
     * @return such instance.
     */
    @NotNull
    public static TemplatePackagingUtils getInstance()
    {
        return TemplatePackagingUtilsSingletonContainer.SINGLETON;
    }

    /**
     * Builds the final file name.
     * @param templateDef the {@link TemplateDef} instance.
     * @param templateName the template name.
     * @return such file name.
     */
    @NotNull
    public String buildFilename(
        @NotNull final TemplateDef<String> templateDef,
        @NotNull final String templateName)
    {
        return buildFilename(templateDef, templateName, ".java");
    }

    /**
     * Builds the final file name.
     * @param templateDef the {@link TemplateDef} instance.
     * @param templateName the template name.
     * @param extension the filename extension.
     * @return such file name.
     */
    @NotNull
    public String buildFilename(
        @NotNull final TemplateDef<String> templateDef,
        @NotNull final String templateName,
        @NotNull final String extension)
    {
        @NotNull final String result;

        @NotNull final String templateDefPart;

        @Nullable final File defFile = templateDef.getFile();

        if (defFile == null)
        {
            templateDefPart = STG_EXT.matcher(templateDef.getName()).replaceAll("");
        }
        else
        {
            templateDefPart = STG_DEF_EXT.matcher(defFile.getName()).replaceAll("");
        }

        result =
            new DecoratedString(templateDefPart) //.getCapitalized()
            + templateName
            + extension;

        return result;
    }
}
