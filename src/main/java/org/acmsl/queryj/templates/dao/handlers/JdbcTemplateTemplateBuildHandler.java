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
 * Filename: JdbcTemplateTemplateBuildHandler.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Builds a JdbcTemplateTemplate instances.
 *
 */
package org.acmsl.queryj.templates.dao.handlers;

/*
 * Importing some project classes.
 */

import org.acmsl.queryj.customsql.CustomSqlProvider;
import org.acmsl.queryj.templates.BasePerRepositoryTemplateContext;
import org.acmsl.queryj.templates.TemplateMappingManager;
import org.acmsl.queryj.templates.dao.JdbcTemplateTemplate;
import org.acmsl.queryj.templates.dao.JdbcTemplateTemplateGenerator;
import org.acmsl.queryj.templates.handlers.BasePerRepositoryTemplateBuildHandler;
import org.acmsl.queryj.tools.PackageUtils;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/*
 * Importing some JDK classes.
 */
import java.util.Map;

/**
 * Builds a {@link org.acmsl.queryj.templates.dao.JdbcTemplateTemplate}
 * instances.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro</a>
 * @since 2012/07/01 (recovered)
 */
public class JdbcTemplateTemplateBuildHandler
    extends  BasePerRepositoryTemplateBuildHandler
                 <JdbcTemplateTemplate,
                     JdbcTemplateTemplateGenerator, BasePerRepositoryTemplateContext>
{
    /**
     * Checks whether template generation is enabled for this kind of template.
     * @return <code>true</code> in such case.
     */
    protected boolean isGenerationEnabled(
        @NotNull final CustomSqlProvider customSqlProvider, @NotNull final Map parameters)
    {
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    protected JdbcTemplateTemplateGenerator retrieveTemplateFactory()
    {
        return JdbcTemplateTemplateGenerator.getInstance();
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    protected String retrievePackage(
        @NotNull final String engineName,
        @NotNull final String projectPackage,
        @NotNull final PackageUtils packageUtils)
    {
        return packageUtils.retrieveJdbcTemplatePackage(projectPackage);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("unchecked")
    protected void storeTemplate(
        @NotNull final JdbcTemplateTemplate template, @NotNull final Map parameters)
    {
        parameters.put(
            TemplateMappingManager.JDBC_TEMPLATE_TEMPLATE,
            template);
    }

}
