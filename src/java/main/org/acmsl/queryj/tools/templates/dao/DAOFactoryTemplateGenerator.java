/*
                        QueryJ

    Copyright (C) 2002-2005  Jose San Leandro Armendariz
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
 * Filename: $RCSfile: $
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Is able to generate DAO factories according to
 *              database metadata.
 *
 */
package org.acmsl.queryj.tools.templates.dao;

/*
 * Importing some project-specific classes.
 */
import org.acmsl.queryj.QueryJException;
import org.acmsl.queryj.tools.metadata.CachingDecoratorFactory;
import org.acmsl.queryj.tools.metadata.DecoratorFactory;
import org.acmsl.queryj.tools.templates.dao.DAOFactoryTemplate;
import org.acmsl.queryj.tools.templates.dao.DAOFactoryTemplateFactory;
import org.acmsl.queryj.tools.templates.TableTemplate;
import org.acmsl.queryj.tools.templates.TemplateMappingManager;

/*
 * Importing some ACM-SL classes.
 */
import org.acmsl.commons.utils.EnglishGrammarUtils;
import org.acmsl.commons.utils.io.FileUtils;
import org.acmsl.commons.utils.StringUtils;

/*
 * Importing some JDK classes.
 */
import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;

/**
 * Is able to generate DAO factories according to database
 * metadata.
 * @author <a href="mailto:chous@acm-sl.org"
 *         >Jose San Leandro</a>
 */
public class DAOFactoryTemplateGenerator
  implements DAOFactoryTemplateFactory
{
    /**
     * Singleton implemented as a weak reference.
     */
    private static WeakReference singleton;

    /**
     * Protected constructor to avoid accidental instantiation.
     */
    protected DAOFactoryTemplateGenerator() {};

    /**
     * Specifies a new weak reference.
     * @param generator the generator instance to use.
     */
    protected static void setReference(
        final DAOFactoryTemplateGenerator generator)
    {
        singleton = new WeakReference(generator);
    }

    /**
     * Retrieves the weak reference.
     * @return such reference.
     */
    protected static WeakReference getReference()
    {
        return singleton;
    }

    /**
     * Retrieves a DAOFactoryTemplateGenerator instance.
     * @return such instance.
     */
    public static DAOFactoryTemplateGenerator getInstance()
    {
        DAOFactoryTemplateGenerator result = null;

        WeakReference reference = getReference();

        if  (reference != null) 
        {
            result = (DAOFactoryTemplateGenerator) reference.get();
        }

        if  (result == null) 
        {
            result = new DAOFactoryTemplateGenerator();

            setReference(result);
        }

        return result;
    }

    /**
     * Generates a DAO factory template.
     * @param tableTemplate the table template.
     * @param packageName the package name.
     * @param engineName the engine name.
     * @param basePackageName the base package name.
     * @param jndiDataSource the JNDI location of the data source.
     * @param header the header.
     * @return a template.
     * @throws QueryJException if the input values are invalid.
     * @precondition tableTemplate != null
     * @precondition packageName != null
     * @precondition engineName != null
     */
    public DAOFactoryTemplate createDAOFactoryTemplate(
        final TableTemplate tableTemplate,
        final String packageName,
        final String engineName,
        final String basePackageName,
        final String jndiDataSource,
        final String header)
      throws  QueryJException
    {
        return
            new DAOFactoryTemplate(
                header,
                getDecoratorFactory(),
                tableTemplate,
                packageName,
                engineName,
                basePackageName,
                jndiDataSource);
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
     * Writes a DAO factory template to disk.
     * @param template the DAO factory template to write.
     * @param outputDir the output folder.
     * @throws IOException if the file cannot be created.
     * @precondition template != null
     * @precondition outputDir != null
     */
    public void write(
        final DAOFactoryTemplate template,
        final File outputDir)
        throws  IOException
    {
        write(
            template,
            outputDir,
            StringUtils.getInstance(),
            EnglishGrammarUtils.getInstance(),
            FileUtils.getInstance());
    }

    /**
     * Writes a DAO factory template to disk.
     * @param template the DAO factory template to write.
     * @param outputDir the output folder.
     * @param stringUtils the <code>StringUtils</code> instance.
     * @param englishGrammarUtils the <code>EnglishGrammarUtils</code>
     * instance.
     * @param fileUtils the <code>FileUtils</code> instance.
     * @throws IOException if the file cannot be created.
     * @precondition template != null
     * @precondition outputDir != null
     * @precondition stringUtils != null
     * @precondition englishGrammarUtils != null
     * @precondition fileUtils != null
     */
    protected void write(
        final DAOFactoryTemplate template,
        final File outputDir,
        final StringUtils stringUtils,
        final EnglishGrammarUtils englishGrammarUtils,
        final FileUtils fileUtils)
      throws  IOException
    {
        outputDir.mkdirs();

        fileUtils.writeFile(
            outputDir.getAbsolutePath()
            + File.separator
            + template.getEngineName()
            + stringUtils.capitalize(
                englishGrammarUtils.getSingular(
                    template
                        .getTableTemplate().getTableName()
                            .toLowerCase()),
                '_')
            + "DAOFactory.java",
            template.generate());
    }
}
