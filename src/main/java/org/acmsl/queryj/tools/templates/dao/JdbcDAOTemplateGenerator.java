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
 * Filename: JdbcDAOTemplateGenerator.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Is able to generate JdbcDAO implementations.
 *
 */
package org.acmsl.queryj.tools.templates.dao;

/*
 * Importing some project-specific classes.
 */
import org.acmsl.queryj.tools.templates.AbstractTemplateGenerator;

/*
 * Importing some ACM-SL classes.
 */
import org.acmsl.commons.patterns.Singleton;

/*
 * Importing some JetBrains annotations.
 */
import org.acmsl.queryj.tools.templates.BasePerRepositoryTemplateContext;
import org.jetbrains.annotations.NotNull;

/**
 * Is able to generate JdbcDAO implementations.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public class JdbcDAOTemplateGenerator
    extends AbstractTemplateGenerator<JdbcDAOTemplate, BasePerRepositoryTemplateContext>
    implements  JdbcDAOTemplateFactory,
                Singleton
{
    /**
     * Singleton implemented to avoid the double-checked locking.
     */
    private static class JdbcDAOTemplateGeneratorSingletonContainer
    {
        /**
         * The actual singleton.
         */
        public static final JdbcDAOTemplateGenerator SINGLETON =
            new JdbcDAOTemplateGenerator();
    }

    /**
     * Protected constructor to avoid accidental instantiation.
     */
    protected JdbcDAOTemplateGenerator() {}

    /**
     * Retrieves a {@link JdbcDAOTemplateGenerator} instance.
     * @return such instance.
     */
    @NotNull
    public static JdbcDAOTemplateGenerator getInstance()
    {
        return JdbcDAOTemplateGeneratorSingletonContainer.SINGLETON;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @NotNull
    public JdbcDAOTemplate createJdbcDAOTemplate(
        @NotNull final String packageName, @NotNull final String header)
    {
        return new JdbcDAOTemplate(header, getDecoratorFactory(), packageName);
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    public String retrieveTemplateFileName(@NotNull final BasePerRepositoryTemplateContext context)
    {
        return "JdbcDAO.java";
    }
}
