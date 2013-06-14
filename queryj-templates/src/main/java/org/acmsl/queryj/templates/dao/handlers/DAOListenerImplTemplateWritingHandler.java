/*
                        QueryJ

    Copyright (C) 2002-2007  Jose San Leandro Armendariz
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
 * Filename: DAOListenerImplTemplateWritingHandler.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Writes the DAO listener implementation.
 *
 */
package org.acmsl.queryj.templates.dao.handlers;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.api.PerRepositoryTemplateContext;
import org.acmsl.queryj.tools.PackageUtils;
import org.acmsl.queryj.templates.dao.DAOListenerImplTemplate;
import org.acmsl.queryj.api.handlers.BasePerRepositoryTemplateWritingHandler;
import org.acmsl.queryj.templates.dao.DAOListenerImplTemplateGenerator;
import org.acmsl.queryj.api.TemplateMappingManager;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing some JDK classes.
 */
import java.io.File;
import java.util.List;
import java.util.Map;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * Writes the DAO listener implementation.
 * @author <a href="mailto:chous@acm-sl.org"
           >Jose San Leandro</a>
 */
@ThreadSafe
public class DAOListenerImplTemplateWritingHandler
    extends  BasePerRepositoryTemplateWritingHandler
                 <DAOListenerImplTemplate, DAOListenerImplTemplateGenerator, PerRepositoryTemplateContext>
{
    /**
     * {@inheritDoc}
     */
    @NotNull
    protected DAOListenerImplTemplateGenerator retrieveTemplateGenerator(final boolean caching, final int threadCount)
    {
        return new DAOListenerImplTemplateGenerator(caching, threadCount);
    }

    /**
     * {@inheritDoc}
     */
    @Nullable
    @Override
    @SuppressWarnings("unchecked")
    protected List<DAOListenerImplTemplate> retrieveTemplates(@NotNull final Map parameters)
    {
        return
            (List<DAOListenerImplTemplate>)
                parameters.get(
                    TemplateMappingManager.DAO_LISTENER_IMPL_TEMPLATE);
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    protected File retrieveOutputDir(
        @NotNull final File projectFolder,
        @NotNull final String projectPackage,
        final boolean useSubfolders,
        @NotNull final String engineName,
        @NotNull final Map parameters,
        @NotNull final PackageUtils packageUtils)
    {
        return
            packageUtils.retrieveDAOListenerImplFolder(
                projectFolder,
                projectPackage,
                engineName,
                useSubfolders);
    }
}
