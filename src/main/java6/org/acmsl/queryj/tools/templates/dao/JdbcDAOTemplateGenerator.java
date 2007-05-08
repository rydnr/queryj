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
import org.acmsl.queryj.QueryJException;
import org.acmsl.queryj.tools.metadata.CachingDecoratorFactory;
import org.acmsl.queryj.tools.metadata.DecoratorFactory;
import org.acmsl.queryj.tools.templates.dao.JdbcDAOTemplate;
import org.acmsl.queryj.tools.templates.dao.JdbcDAOTemplateFactory;
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
 * Is able to generate JdbcDAO implementations.
 * @author <a href="mailto:chous@acm-sl.org"
           >Jose San Leandro</a>
 */
public class JdbcDAOTemplateGenerator
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
    protected JdbcDAOTemplateGenerator() {};

    /**
     * Retrieves a <code>JdbcDAOTemplateGenerator</code> instance.
     * @return such instance.
     */
    public static JdbcDAOTemplateGenerator getInstance()
    {
        return JdbcDAOTemplateGeneratorSingletonContainer.SINGLETON;
    }

    /**
     * Generates a JDBC DAO template.
     * @param packageName the package name.
     * @param header the header.
     * @return a template.
     * @precondition packageName != null
     */
    public JdbcDAOTemplate createJdbcDAOTemplate(
        final String packageName, final String header)
      throws  QueryJException
    {
        return new JdbcDAOTemplate(header, getDecoratorFactory(), packageName);
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
     * Writes a JDBC DAO template to disk.
     * @param jdbcDAOTemplate the JDBC DAO template to write.
     * @param outputDir the output folder.
     * @throws IOException if the file cannot be created.
     * @precondition jdbcDAOTemplate != null
     * @precondition outputDir != null
     */
    public void write(
        final JdbcDAOTemplate jdbcDAOTemplate,
        final File outputDir)
      throws  IOException
    {
        write(
            jdbcDAOTemplate,
            outputDir,
            StringUtils.getInstance(),
            FileUtils.getInstance());
    }

    /**
     * Writes a JDBC DAO template to disk.
     * @param jdbcDAOTemplate the JDBC DAO template to write.
     * @param outputDir the output folder.
     * @param stringUtils the <code>StringUtils</code> instance.
     * @param fileUtils the <code>FileUtils</code> instance.
     * @throws IOException if the file cannot be created.
     * @precondition jdbcDAOTemplate != null
     * @precondition outputDir != null
     * @precondition stringUtils != null
     * @precondition fileUtils != null
     */
    protected void write(
        final JdbcDAOTemplate jdbcDAOTemplate,
        final File outputDir,
        final StringUtils stringUtils,
        final FileUtils fileUtils)
      throws  IOException
    {
        outputDir.mkdirs();

        fileUtils.writeFile(
              outputDir.getAbsolutePath()
            + File.separator
            + "JdbcDAO.java",
            jdbcDAOTemplate.generate());
    }
}
