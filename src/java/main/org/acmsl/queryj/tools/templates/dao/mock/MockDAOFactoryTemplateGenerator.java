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
 * Description: Is able to generate Mock DAO factories.
 *
 */
package org.acmsl.queryj.tools.templates.dao.mock;

/*
 * Importing some project-specific classes.
 */
import org.acmsl.queryj.QueryJException;
import org.acmsl.queryj.tools.templates.dao.mock.MockDAOFactoryTemplate;
import org.acmsl.queryj.tools.templates.dao.mock.MockDAOFactoryTemplateFactory;
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
 * Is able to generate Mock DAO factories.
 * @author <a href="mailto:chous@acm-sl.org"
           >Jose San Leandro</a>
 */
public class MockDAOFactoryTemplateGenerator
    implements  MockDAOFactoryTemplateFactory
{
    /**
     * Singleton implemented as a weak reference.
     */
    private static WeakReference singleton;

    /**
     * Protected constructor to avoid accidental instantiation.
     */
    protected MockDAOFactoryTemplateGenerator() {};

    /**
     * Specifies a new weak reference.
     * @param generator the generator instance to use.
     */
    protected static void setReference(
        final MockDAOFactoryTemplateGenerator generator)
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
     * Retrieves a MockDAOFactoryTemplateGenerator instance.
     * @return such instance.
     */
    public static MockDAOFactoryTemplateGenerator getInstance()
    {
        MockDAOFactoryTemplateGenerator result = null;

        WeakReference t_Reference = getReference();

        if  (t_Reference != null) 
        {
            result = (MockDAOFactoryTemplateGenerator) t_Reference.get();
        }

        if  (result == null) 
        {
            result = new MockDAOFactoryTemplateGenerator() {};

            setReference(result);
        }

        return result;
    }

    /**
     * Generates a Mock DAO factory template.
     * @param tableTemplate the table template.
     * @param packageName the package name.
     * @param basePackageName the base package name.
     * @return a template.
     * @throws QueryJException if the input values are invalid.
     */
    public MockDAOFactoryTemplate createMockDAOFactoryTemplate(
        final TableTemplate tableTemplate,
        final String packageName,
        final String basePackageName)
      throws  QueryJException
    {
        MockDAOFactoryTemplate result = null;

        if  (   (tableTemplate   != null)
             && (packageName     != null)
             && (basePackageName != null))
        {
            result =
                new MockDAOFactoryTemplate(
                    tableTemplate,
                    packageName,
                    basePackageName);
        }

        return result;
    }

    /**
     * Writes a Mock DAO factory template to disk.
     * @param template the Mock DAO factory template to write.
     * @param outputDir the output folder.
     * @throws IOException if the file cannot be created.
     */
    public void write(
        final MockDAOFactoryTemplate template,
        final File outputDir)
      throws  IOException
    {
        if  (   (template  != null)
             && (outputDir != null))
        {
            EnglishGrammarUtils t_EnglishGrammarUtils =
                EnglishGrammarUtils.getInstance();
            StringUtils t_StringUtils = StringUtils.getInstance();
            FileUtils t_FileUtils = FileUtils.getInstance();

            if  (   (t_StringUtils != null)
                 && (t_FileUtils   != null))
            {
                outputDir.mkdirs();

                t_FileUtils.writeFile(
                      outputDir.getAbsolutePath()
                    + File.separator
                    + "Mock"
                    + t_StringUtils.capitalize(
                          t_EnglishGrammarUtils.getSingular(
                              template
                                  .getTableTemplate().getTableName()
                                      .toLowerCase()),
                          '_')
                    + "DAOFactory.java",
                    template.toString());
            }
        }
    }
}
