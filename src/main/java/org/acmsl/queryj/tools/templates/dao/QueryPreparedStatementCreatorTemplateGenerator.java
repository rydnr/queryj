//;-*- mode: java -*-
/*
                        QueryJ

    Copyright (C) 2002-2006  Jose San Leandro Armendariz
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
 * Importing some project classes.
 */
import org.acmsl.queryj.tools.SingularPluralFormConverter;
import org.acmsl.queryj.QueryJException;
import org.acmsl.queryj.tools.metadata.CachingDecoratorFactory;
import org.acmsl.queryj.tools.metadata.DecoratorFactory;
import org.acmsl.queryj.tools.templates.dao.QueryPreparedStatementCreatorTemplate;
import org.acmsl.queryj.tools.templates.dao.QueryPreparedStatementCreatorTemplateFactory;
import org.acmsl.queryj.tools.templates.TemplateMappingManager;

/*
 * Importing some ACM-SL classes.
 */
import org.acmsl.commons.patterns.Singleton;
import org.acmsl.commons.utils.io.FileUtils;
import org.acmsl.commons.utils.StringUtils;

/*
 * Importing some JDK classes.
 */
import java.io.File;
import java.io.IOException;

/**
 * Is able to generate QueryPreparedStatementCreator templates.
 * @author <a href="mailto:chous@acm-sl.org"
 *         >Jose San Leandro</a>
 */
public class QueryPreparedStatementCreatorTemplateGenerator
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
    protected QueryPreparedStatementCreatorTemplateGenerator() {};

    /**
     * Retrieves a <code>QueryPreparedStatementCreatorTemplateGenerator</code>
     * instance.
     * @return such instance.
     */
    public static QueryPreparedStatementCreatorTemplateGenerator getInstance()
    {
        return QueryPreparedStatementCreatorTemplateGeneratorSingletonContainer.SINGLETON;
    }

    /**
     * Generates a QueryPreparedStatementCreator template.
     * @param packageName the package name.
     * @param header the header.
     * @return a template.
     * @throws QueryJException if the factory class is invalid.
     * @precondition packageName != null
     */
    public QueryPreparedStatementCreatorTemplate createQueryPreparedStatementCreatorTemplate(
        final String packageName, final String header)
      throws  QueryJException
    {
        return
            new QueryPreparedStatementCreatorTemplate(
                header, getDecoratorFactory(), packageName);
    }

    /**
     * Retrieves the decorator factory.
     * @return such instance.
     */
    public DecoratorFactory getDecoratorFactory()
    {
        return CachingDecoratorFactory.getInstance();
    }

    /**
     * Writes a QueryPreparedStatementCreator template to disk.
     * @param template the template to write.
     * @param outputDir the output folder.
     * @throws IOException if the file cannot be created.
     * @precondition template != null
     * @precondition outputDir != null
     */
    public void write(
        final QueryPreparedStatementCreatorTemplate template,
        final File outputDir)
      throws  IOException
    {
        write(
            template,
            outputDir, 
            FileUtils.getInstance());
    }

    /**
     * Writes a QueryPreparedStatementCreator template to disk.
     * @param template the template to write.
     * @param outputDir the output folder.
     * @param fileUtils the <code>FileUtils</code> instance.
     * @throws IOException if the file cannot be created.
     * @precondition template != null
     * @precondition outputDir != null
     * @precondition fileUtils != null
     */
    protected void write(
        final QueryPreparedStatementCreatorTemplate template,
        final File outputDir,
        final FileUtils fileUtils)
      throws  IOException
    {
        outputDir.mkdirs();

        fileUtils.writeFile(
              outputDir.getAbsolutePath()
            + File.separator
            + "QueryPreparedStatementCreator.java",
            template.generate());
    }
}