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
 * Filename: QueryPreparedStatementCreatorTemplateGenerator.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Is able to generate QueryPreparedStatementCreator
 *              templates.
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
import org.jetbrains.annotations.NotNull;

/**
 * Is able to generate QueryPreparedStatementCreator templates.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public class QueryPreparedStatementCreatorTemplateGenerator
    extends AbstractTemplateGenerator<QueryPreparedStatementCreatorTemplate>
    implements  QueryPreparedStatementCreatorTemplateFactory,
                Singleton
{
    /**
     * Singleton implemented to avoid the double-checked locking.
     */
    private static class QueryPreparedStatementCreatorTemplateGeneratorSingletonContainer
    {
        /**
         * The actual singleton.
         */
        public static final QueryPreparedStatementCreatorTemplateGenerator SINGLETON =
            new QueryPreparedStatementCreatorTemplateGenerator();
    }

    /**
     * Protected constructor to avoid accidental instantiation.
     */
    protected QueryPreparedStatementCreatorTemplateGenerator() {}

    /**
     * Retrieves a {@link QueryPreparedStatementCreatorTemplateGenerator}
     * instance.
     * @return such instance.
     */
    @NotNull
    public static QueryPreparedStatementCreatorTemplateGenerator getInstance()
    {
        return QueryPreparedStatementCreatorTemplateGeneratorSingletonContainer.SINGLETON;
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    public QueryPreparedStatementCreatorTemplate createQueryPreparedStatementCreatorTemplate(
        @NotNull final String packageName, @NotNull final String header)
    {
        return
            new QueryPreparedStatementCreatorTemplate(
                header, getDecoratorFactory(), packageName);
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    public String retrieveTemplateFileName(@NotNull final QueryPreparedStatementCreatorTemplate template)
    {
        return "QueryPreparedStatementCreator.java";
    }
}
