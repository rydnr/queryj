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
    Contact info: chous@acm-sl.org
    Postal Address: c/Playa de Lagoa, 1
                    Urb. Valdecabanas
                    Boadilla del monte
                    28660 Madrid
                    Spain

 ******************************************************************************
 *
 * Filename: JdbcTemplateTemplateWritingHandler.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Writes JdbcTemplate templates.
 *
 */
package org.acmsl.queryj.templates.dao.handlers;

/*
 * Importing some project classes.
 */

import org.acmsl.queryj.templates.BasePerRepositoryTemplateContext;
import org.acmsl.queryj.templates.TemplateMappingManager;
import org.acmsl.queryj.templates.dao.JdbcTemplateTemplate;
import org.acmsl.queryj.templates.dao.JdbcTemplateTemplateGenerator;
import org.acmsl.queryj.templates.handlers.BasePerRepositoryTemplateWritingHandler;
import org.acmsl.queryj.tools.PackageUtils;


/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing some JDK classes.
 */
import java.io.File;
import java.util.Map;

/**
 * Writes {@link org.acmsl.queryj.templates.dao.JdbcTemplateTemplate} templates.
 * @author <a href="mailto:chous@acm-sl.org"
           >Jose San Leandro</a>
 * @since 2012/07/01 (recovered)
 */
public class JdbcTemplateTemplateWritingHandler
    extends  BasePerRepositoryTemplateWritingHandler
                 <JdbcTemplateTemplate,
                     JdbcTemplateTemplateGenerator,
                     BasePerRepositoryTemplateContext>
{
    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    protected JdbcTemplateTemplateGenerator retrieveTemplateGenerator()
    {
        return JdbcTemplateTemplateGenerator.getInstance();
    }

    /**
     * {@inheritDoc}
     */
    @Nullable
    @Override
    @SuppressWarnings("unchecked")
    protected JdbcTemplateTemplate retrieveTemplate(
        @NotNull final Map parameters)
    {
        return
            (JdbcTemplateTemplate)
                parameters.get(
                    TemplateMappingManager.JDBC_TEMPLATE_TEMPLATE);
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    protected File retrieveOutputDir(
        @NotNull final File projectFolder,
        @NotNull final String projectPackage,
        final boolean useSubFolders,
        @NotNull final String engineName,
        @NotNull final Map parameters,
        @NotNull final PackageUtils packageUtils)
    {
        return
            packageUtils.retrieveJdbcTemplateFolder(
                projectFolder,
                projectPackage,
                useSubFolders);
    }
}
