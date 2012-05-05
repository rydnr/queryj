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
 * Filename: DAOListenerImplTemplateGenerator.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Is able to generate DAO listeners implementations from
 * templates.
 *
 */
package org.acmsl.queryj.tools.templates.dao;

/*
 * Importing some project-specific classes.
 */
import org.acmsl.commons.patterns.Singleton;
import org.acmsl.queryj.tools.customsql.CustomSqlProvider;
import org.acmsl.queryj.tools.metadata.DecorationUtils;
import org.acmsl.queryj.tools.metadata.MetadataManager;
import org.acmsl.queryj.tools.metadata.MetadataTypeManager;
import org.acmsl.queryj.tools.templates.AbstractTemplateGenerator;
import org.acmsl.queryj.tools.templates.BasePerRepositoryTemplateFactory;
import org.acmsl.queryj.tools.templates.BasePerRepositoryTemplateGenerator;

/*
 * Importing some ACM-SL classes.
 */

/*
 * Importing some jetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/*
 * Importing some JDK classes.
 */
import java.util.List;

/**
 * Is able to generate DAO listeners from templates.
 * @author <a href="mailto:chous@acm-sl.org"
           >Jose San Leandro</a>
 */
public class DAOListenerImplTemplateGenerator
    extends AbstractTemplateGenerator<DAOListenerImplTemplate>
    implements  BasePerRepositoryTemplateGenerator<DAOListenerImplTemplate>,
                BasePerRepositoryTemplateFactory<DAOListenerImplTemplate>,
                Singleton
{
    /**
     * Singleton implemented to avoid the double-checked locking.
     */
    private static class DAOListenerImplTemplateGeneratorSingletonContainer
    {
        /**
         * The actual singleton.
         */
        public static final DAOListenerImplTemplateGenerator SINGLETON =
            new DAOListenerImplTemplateGenerator();
    }

    /**
     * Default constructor.
     */
    public DAOListenerImplTemplateGenerator() {}

    /**
     * Retrieves a DAOListenerTemplateGenerator instance.
     * @return such instance.
     */
    @NotNull
    public static DAOListenerImplTemplateGenerator getInstance()
    {
        return DAOListenerImplTemplateGeneratorSingletonContainer.SINGLETON;
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    @SuppressWarnings("unused")
    public DAOListenerImplTemplate createTemplate(
        @NotNull final MetadataManager metadataManager,
        @NotNull final MetadataTypeManager metadataTypeManager,
        @NotNull final CustomSqlProvider customSqlProvider,
        @NotNull final String projectPackage,
        @NotNull final String packageName,
        @NotNull final String repository,
        @NotNull final String engineName,
        @NotNull final String header,
        final boolean jmx,
        @NotNull final List<String> tableNames,
        @NotNull final String jndiLocation)
    {
        return
            new DAOListenerImplTemplate(
                metadataManager,
                metadataTypeManager,
                customSqlProvider,
                header,
                jmx,
                getDecoratorFactory(),
                packageName,
                projectPackage,
                repository,
                engineName,
                tableNames);
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    public String retrieveTemplateFileName(@NotNull final DAOListenerImplTemplate template)
    {
        return retrieveTemplateFileName(template, DecorationUtils.getInstance());
    }

    /**
     * Retrieves given template's file name.
     * @param template the template.
     * @param decorationUtils the {@link DecorationUtils} instance.
     * @return such name.
     */
    @NotNull
    protected String retrieveTemplateFileName(
        @NotNull final DAOListenerImplTemplate  template, @NotNull final DecorationUtils decorationUtils)
    {
        return
              decorationUtils.capitalize(template.getRepositoryName())
            + "DAOListenerImpl.java";
    }
}
