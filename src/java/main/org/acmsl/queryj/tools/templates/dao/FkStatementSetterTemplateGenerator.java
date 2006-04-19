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
import org.acmsl.queryj.tools.metadata.DecoratorFactory;
import org.acmsl.queryj.tools.metadata.CachingDecoratorFactory;
import org.acmsl.queryj.tools.metadata.MetadataManager;
import org.acmsl.queryj.tools.metadata.vo.ForeignKey;
import org.acmsl.queryj.tools.templates.BasePerForeignKeyTemplate;
import org.acmsl.queryj.tools.templates.dao.FkStatementSetterTemplate;
import org.acmsl.queryj.tools.templates.dao.FkStatementSetterTemplateFactory;
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
     * Creates a FkStatementSetter template.
     * @param foreignKey the foreign key.
     * @param metadataManager the database metadata manager.
     * @param packageName the package name.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param quote the identifier quote string.
     * @param basePackageName the base package name.
     * @param repositoryName the repository name.
     * @return a template.
     * @throws QueryJException if the factory class is invalid.
     * @precondition foreignKey != null
     * @precondition metadataManager != null
     * @precondition packageName != null
     * @precondition repositoryName != null
     */
    public BasePerForeignKeyTemplate createTemplate(
        final ForeignKey foreignKey,
        final MetadataManager metadataManager,
        final String packageName,
        final String engineName,
        final String engineVersion,
        final String quote,
        final String basePackageName,
        final String repositoryName)
      throws  QueryJException
    {
        return
            new FkStatementSetterTemplate(
                foreignKey,
                metadataManager,
                packageName,
                engineName,
                engineVersion,
                quote,
                basePackageName,
                repositoryName);
    }

    /**
     * Retrieves the decorator factory for each template.
     * @return such instance.
     */
    public DecoratorFactory getDecoratorFactory()
    {
        return CachingDecoratorFactory.getInstance();
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
            template.getForeignKey());
    }

    /**
     * Writes a FkStatementSetter template to disk.
     * @param template the template to write.
     * @param outputDir the output folder.
     * @param foreignKey the foreign key.
     * @throws IOException if the file cannot be created.
     * @precondition template != null
     * @precondition outputDir != null
     * @precondition foreignKey != null
     */
    public void write(
        final FkStatementSetterTemplate template,
        final File outputDir,
        final ForeignKey foreignKey)
      throws  IOException
    {
        write(
            template,
            outputDir,
            foreignKey.getSourceTableName(),
            foreignKey.getTargetTableName(),
            StringUtils.getInstance(),
            EnglishGrammarUtils.getInstance(),
            FileUtils.getInstance());
    }

    /**
     * Writes a FkStatementSetterCreator template to disk.
     * @param template the template to write.
     * @param sourceTableName the source table name.
     * @param targetTableName the target table name.
     * @param outputDir the output folder.
     * @param stringUtils the <code>StringUtils</code> instance.
     * @param englishGrammarUtils the <code>EnglishGrammarUtils</code>
     * instance.
     * @param fileUtils the <code>FileUtils</code> instance.
     * @throws IOException if the file cannot be created.
     * @precondition template != null
     * @precondition sourceTableName != null
     * @precondition targetTableName != null
     * @precondition outputDir != null
     * @precondition stringUtils != null
     * @precondition englishGrammarUtils != null
     * @precondition fileUtils != null
     */
    protected void write(
        final FkStatementSetterTemplate template,
        final File outputDir,
        final String sourceTableName,
        final String targetTableName,
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
                    sourceTableName.toLowerCase()),
                '_')
            + "By"
            + stringUtils.capitalize(
                englishGrammarUtils.getSingular(
                    targetTableName.toLowerCase()),
                '_')
            + "StatementSetter.java",
            template.generate());
    }
}
