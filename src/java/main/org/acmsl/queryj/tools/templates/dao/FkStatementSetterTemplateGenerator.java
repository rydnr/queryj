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
 * Filename: $RCSfile$
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Is able to generate FkStatementSetter templates.
 *
 */
package org.acmsl.queryj.tools.templates.dao;

/*
 * Importing some project-specific classes.
 */
import org.acmsl.queryj.QueryJException;
import org.acmsl.queryj.tools.DatabaseMetaDataManager;
import org.acmsl.queryj.tools.templates.dao.FkStatementSetterTemplate;
import org.acmsl.queryj.tools.templates.dao.FkStatementSetterTemplateFactory;
import org.acmsl.queryj.tools.templates.TableTemplate;
import org.acmsl.queryj.tools.templates.TemplateMappingManager;

/*
 * Importing some ACM-SL classes.
 */
import org.acmsl.commons.utils.EnglishGrammarUtils;
import org.acmsl.commons.utils.io.FileUtils;
import org.acmsl.commons.utils.StringUtils;

/*
 * Importing Ant classes.
 */
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;

/*
 * Importing some JDK classes.
 */
import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;

/**
 * Is able to generate FkStatementSetter templates.
 * @author <a href="mailto:chous@acm-sl.org"
 *         >Jose San Leandro</a>
 */
public class FkStatementSetterTemplateGenerator
    implements  FkStatementSetterTemplateFactory
{
    /**
     * Singleton implemented as a weak reference.
     */
    private static WeakReference singleton;

    /**
     * Protected constructor to avoid accidental instantiation.
     */
    protected FkStatementSetterTemplateGenerator() {};

    /**
     * Specifies a new weak reference.
     * @param generator the generator instance to use.
     */
    protected static void setReference(
        final FkStatementSetterTemplateGenerator generator)
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
     * Retrieves a FkStatementSetterTemplateGenerator instance.
     * @return such instance.
     */
    public static FkStatementSetterTemplateGenerator getInstance()
    {
        FkStatementSetterTemplateGenerator result = null;

        WeakReference reference = getReference();

        if  (reference != null) 
        {
            result =
                (FkStatementSetterTemplateGenerator) reference.get();
        }

        if  (result == null) 
        {
            result = new FkStatementSetterTemplateGenerator();

            setReference(result);
        }

        return result;
    }

    /**
     * Generates a FkStatementSetter template.
     * @param tableTemplate the table template.
     * @param foreignKey the foreign key.
     * @param metaDataManager the metadata manager.
     * @param packageName the package name.
     * @param basePackageName the base package name.
     * @param repositoryName the name of the repository.
     * @param project the project, for logging purposes.
     * @param task the task, for logging purposes.
     * @return a template.
     * @throws QueryJException if the factory class is invalid.
     * @precondition tableTemplate != null
     * @precondition foreignKey != null
     * @precondition metaDataManager != null
     * @precondition packageName != null
     * @precondition basePackageName != null
     * @precondition repositoryName != null
     */
    public FkStatementSetterTemplate createFkStatementSetterTemplate(
        final TableTemplate tableTemplate,
        final String foreignKey,
        final DatabaseMetaDataManager metaDataManager,
        final String packageName,
        final String basePackageName,
        final String repositoryName,
        final Project project,
        final Task task)
      throws  QueryJException
    {
        return
            new FkStatementSetterTemplate(
                tableTemplate,
                foreignKey,
                metaDataManager,
                packageName,
                basePackageName,
                repositoryName,
                project,
                task);
    }

    /**
     * Writes a FkStatementSetter template to disk.
     * @param template the template to write.
     * @param outputDir the output folder.
     * @throws IOException if the file cannot be created.
     * @precondition template != null
     * @precondition outputDir != null
     */
    public void write(
        final FkStatementSetterTemplate template,
        final File outputDir)
      throws  IOException
    {
        write(
            template,
            outputDir,
            template.getTableTemplate());
    }

    /**
     * Writes a FkStatementSetter template to disk.
     * @param template the template to write.
     * @param outputDir the output folder.
     * @param tableTemplate the table template.
     * @throws IOException if the file cannot be created.
     * @precondition template != null
     * @precondition outputDir != null
     * @precondition tableTemplate != null
     */
    public void write(
        final FkStatementSetterTemplate template,
        final File outputDir,
        final TableTemplate tableTemplate)
      throws  IOException
    {
        write(
            template,
            outputDir,
            tableTemplate.getTableName(),
            template.getForeignKey(),
            template.getMetaDataManager(),
            StringUtils.getInstance(),
            EnglishGrammarUtils.getInstance(),
            FileUtils.getInstance());
    }

    /**
     * Writes a FkStatementSetterCreator template to disk.
     * @param template the template to write.
     * @param tableName the table name.
     * @param foreignKey the foreign key.
     * @param metaDataManager the database metadata manager.
     * @param outputDir the output folder.
     * @param stringUtils the <code>StringUtils</code> instance.
     * @param englishGrammarUtils the <code>EnglishGrammarUtils</code>
     * instance.
     * @param fileUtils the <code>FileUtils</code> instance.
     * @throws IOException if the file cannot be created.
     * @precondition template != null
     * @precondition tableName != null
     * @precondition foreignKey != null
     * @precondition metaDataManager != null
     * @precondition outputDir != null
     * @precondition stringUtils != null
     * @precondition englishGrammarUtils != null
     * @precondition fileUtils != null
     */
    protected void write(
        final FkStatementSetterTemplate template,
        final File outputDir,
        final String tableName,
        final String foreignKey,
        final DatabaseMetaDataManager metaDataManager,
        final StringUtils stringUtils,
        final EnglishGrammarUtils englishGrammarUtils,
        final FileUtils fileUtils)
      throws  IOException
    {
        outputDir.mkdirs();

        fileUtils.writeFile(
              outputDir.getAbsolutePath()
            + File.separator
            + stringUtils.capitalize(
                englishGrammarUtils.getSingular(
                    tableName.toLowerCase()),
                '_')
            + "By"
            + stringUtils.capitalize(
                englishGrammarUtils.getSingular(
                    metaDataManager.getReferredTable(
                        tableName,
                        foreignKey).toLowerCase()),
                '_')
            + "StatementSetter.java",
            template.generate());
    }
}
