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
 * Filename: DAOListenerTemplateWritingHandler.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Writes the DAO listener.
 *
 */
package org.acmsl.queryj.templates.dao.handlers;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.tools.PackageUtils;
import org.acmsl.queryj.templates.BasePerRepositoryTemplateContext;
import org.acmsl.queryj.templates.dao.DAOListenerTemplate;
import org.acmsl.queryj.templates.handlers.BasePerRepositoryTemplateWritingHandler;
import org.acmsl.queryj.templates.dao.DAOListenerTemplateGenerator;
import org.acmsl.queryj.templates.TemplateMappingManager;

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
 * Writes the DAO listener.
 * @author <a href="mailto:chous@acm-sl.org"
           >Jose San Leandro</a>
 */
public class DAOListenerTemplateWritingHandler
    extends  BasePerRepositoryTemplateWritingHandler
                 <DAOListenerTemplate, DAOListenerTemplateGenerator, BasePerRepositoryTemplateContext>
{
    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    protected DAOListenerTemplateGenerator retrieveTemplateGenerator()
    {
        return DAOListenerTemplateGenerator.getInstance();
    }

    /**
     * {@inheritDoc}
     */
    @Nullable
    @Override
    @SuppressWarnings("unchecked")
    protected DAOListenerTemplate retrieveTemplate(@NotNull final Map parameters)
    {
        return
            (DAOListenerTemplate)
                parameters.get(
                    TemplateMappingManager.DAO_LISTENER_TEMPLATE);
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    protected File retrieveOutputDir(
        @NotNull final File projectFolder,
        @NotNull final String projectPackage,
        final boolean useSubfolders,
        @NotNull final String engineName,
        @NotNull final Map parameters,
        @NotNull final PackageUtils packageUtils)
    {
        return
            packageUtils.retrieveDAOListenerFolder(
                projectFolder,
                projectPackage,
                engineName,
                useSubfolders);
    }
}
