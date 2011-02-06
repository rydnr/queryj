//;-*- mode: java -*-
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
 * Filename: CustomValueObjectTemplateGenerator.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Is able to generate custom ValueObject templates.
 *
 */
package org.acmsl.queryj.tools.templates.valueobject;

/*
 * Importing some project-specific classes.
 */
import org.acmsl.queryj.tools.customsql.CustomSqlProvider;
import org.acmsl.queryj.tools.customsql.Result;
import org.acmsl.queryj.tools.metadata.DecoratorFactory;
import org.acmsl.queryj.tools.metadata.MetadataManager;
import org.acmsl.queryj.tools.PackageUtils;
import org.acmsl.queryj.tools.templates.BasePerCustomResultTemplate;
import org.acmsl.queryj.tools.templates.BasePerCustomResultTemplateFactory;
import org.acmsl.queryj.tools.templates.BasePerCustomResultTemplateGenerator;
import org.acmsl.queryj.tools.templates.valueobject.ValueObjectTemplate;

/*
 * Importing some ACM-SL classes.
 */
import org.acmsl.commons.patterns.Singleton;
import org.acmsl.commons.utils.EnglishGrammarUtils;
import org.acmsl.commons.utils.io.FileUtils;
import org.acmsl.commons.utils.StringUtils;

/*
 * Importing some JDK classes.
 */
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * Is able to generate custom ValueObject templates.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public class CustomValueObjectTemplateGenerator
    implements  BasePerCustomResultTemplateFactory,
                BasePerCustomResultTemplateGenerator,
                Singleton
{
    /**
     * Singleton implemented to avoid the double-checked locking.
     */
    private static class CustomValueObjectTemplateGeneratorSingletonContainer
    {
        /**
         * The actual singleton.
         */
        public static final CustomValueObjectTemplateGenerator SINGLETON =
            new CustomValueObjectTemplateGenerator();
    }

    /**
     * Protected constructor to avoid accidental instantiation.
     */
    protected CustomValueObjectTemplateGenerator() {};

    /**
     * Retrieves a <code>CustomValueObjectTemplateGenerator</code> instance.
     * @return such instance.
     */
    public static CustomValueObjectTemplateGenerator getInstance()
    {
        return CustomValueObjectTemplateGeneratorSingletonContainer.SINGLETON;
    }

    /**
     * Generates a CustomValueObject template.
     * @param customResult the custom result.
     * @param customSqlProvider the custom sql provider.
     * @param metadataManager the database metadata manager.
     * @param packageName the package name.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param basePackageName the base package name.
     * @param repositoryName the repository name.
     * @param header the header.
     * @return the new template.
     * @precondition resultElement != null
     * @precondition customSqlProvider != null
     * @precondition metadataManager != null
     * @precondition packageName != null
     * @precondition basePackageName != null
     * @precondition repositoryName != null
     */
    public BasePerCustomResultTemplate createTemplate(
        final Result customResult,
        final CustomSqlProvider customSqlProvider,
        final MetadataManager metadataManager,
        final String packageName,
        final String engineName,
        final String engineVersion,
        final String basePackageName,
        final String repositoryName,
        final String header)
    {
        BasePerCustomResultTemplate result = null;

        if  (!isStandard(
                 extractClassName(customResult.getClassValue()),
                 metadataManager))
        {
            result =
                new CustomValueObjectTemplate(
                    customResult,
                    customSqlProvider,
                    metadataManager,
                    header,
                    getDecoratorFactory(),
                    packageName,
                    engineName,
                    engineVersion,
                    basePackageName,
                    repositoryName);
        }

        return result;
    }

    /**
     * Retrieves the decorator factory.
     * @return such instance.
     */
    public DecoratorFactory getDecoratorFactory()
    {
        return CustomValueObjectDecoratorFactory.getInstance();
    }

    /**
     * Writes a custom resultset extractor template to disk.
     * @param template the template to write.
     * @param outputDir the output folder.
     * @param charset the file encoding.
     * @throws IOException if the file cannot be created.
     */
    public void write(
        final BasePerCustomResultTemplate template,
        final File outputDir,
        final Charset charset)
      throws  IOException
    {
        write(
            template,
            outputDir,
            charset,
            StringUtils.getInstance(),
            EnglishGrammarUtils.getInstance(),
            FileUtils.getInstance());
    }

    /**
     * Writes a ValueObjectCreator template to disk.
     * @param template the template to write.
     * @param outputDir the output folder.
     * @param charset the file encoding.
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
        final BasePerCustomResultTemplate template,
        final File outputDir,
        final Charset charset,
        final StringUtils stringUtils,
        final EnglishGrammarUtils englishGrammarUtils,
        final FileUtils fileUtils)
      throws  IOException
    {
        outputDir.mkdirs();

        fileUtils.writeFile(
              outputDir.getAbsolutePath()
            + File.separator
            + extractClassName(template.getResult().getClassValue())
            + ".java",
            template.generate(),
            charset);
    }

    /**
     * Checks whether the given class name corresponds to
     * a standard value object or not.
     * @param className the class name.
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @return <code>true</code> in such case.
     * @precondition className != null
     * @precondition metadataManager != null
     */
    protected boolean isStandard(
        final String className, final MetadataManager metadataManager)
    {
        return
            isStandard(
                className,
                metadataManager,
                ValueObjectTemplateGenerator.getInstance());
    }

    /**
     * Checks whether the given class name corresponds to
     * a standard value object or not.
     * @param className the class name.
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @param valueObjectTemplateGenerator the
     * <code>ValueObjectTemplateGenerator</code> instance.
     * @return <code>true</code> in such case.
     * @precondition className != null
     * @precondition metadataManager != null
     * @precondition valueObjectTemplateGenerator != null
     */
    protected boolean isStandard(
        final String className,
        final MetadataManager metadataManager,
        final ValueObjectTemplateGenerator valueObjectTemplateGenerator)
    {
        boolean result = false;

        String[] t_astrTableNames = metadataManager.getTableNames();

        int t_iCount =
            (t_astrTableNames != null) ? t_astrTableNames.length : 0;

        String t_strTableName;
        String t_strVoClassName;

        for  (int t_iIndex = 0; t_iIndex < t_iCount; t_iIndex++)
        {
            t_strTableName = t_astrTableNames[t_iIndex];

            if  (t_strTableName != null)
            {
                t_strVoClassName =
                    valueObjectTemplateGenerator.getVoClassName(
                        t_strTableName);

                if  (   (t_strVoClassName != null)
                     && (   (t_strVoClassName.equals(className)))
                         || ((t_strVoClassName + "ValueObject").equals(
                                 className))
                         || ((className + "ValueObject").equals(
                                 t_strVoClassName)))
                {
                    result = true;
                    break;
                }
            }
        }

        return result;
    }

    /**
     * Extracts the class name of given fully-qualified class.
     * @param fqcn such information.
     * @return the class name.
     * @precondition fqcn != null
     */
    public String extractClassName(final String fqdn)
    {
        return extractClassName(fqdn, PackageUtils.getInstance());
    }

    /**
     * Extracts the class name of given fully-qualified class.
     * @param fqcn such information.
     * @param packageUtils the <code>PackageUtils</code> instance.
     * @return the class name.
     * @precondition fqcn != null
     * @precondition packageUtils != null
     */
    protected String extractClassName(
        final String fqdn, final PackageUtils packageUtils)
    {
        return packageUtils.extractClassName(fqdn);
    }
}
