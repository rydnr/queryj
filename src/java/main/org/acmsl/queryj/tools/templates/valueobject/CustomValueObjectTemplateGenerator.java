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
 * Description: Is able to generate custom ValueObject templates.
 *
 */
package org.acmsl.queryj.tools.templates.valueobject;

/*
 * Importing some project-specific classes.
 */
import org.acmsl.queryj.QueryJException;
import org.acmsl.queryj.tools.customsql.CustomSqlProvider;
import org.acmsl.queryj.tools.customsql.Result;
import org.acmsl.queryj.tools.metadata.CachingDecoratorFactory;
import org.acmsl.queryj.tools.metadata.DecoratorFactory;
import org.acmsl.queryj.tools.metadata.MetadataManager;
import org.acmsl.queryj.tools.PackageUtils;
import org.acmsl.queryj.tools.templates.valueobject.ValueObjectTemplate;
import org.acmsl.queryj.tools.templates.BasePerCustomResultTemplate;
import org.acmsl.queryj.tools.templates.BasePerCustomResultTemplateFactory;
import org.acmsl.queryj.tools.templates.BasePerCustomResultTemplateGenerator;

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
 * Is able to generate custom ValueObject templates.
 * @author <a href="mailto:chous@acm-sl.org"
 *         >Jose San Leandro</a>
 */
public class CustomValueObjectTemplateGenerator
    implements  BasePerCustomResultTemplateFactory,
                BasePerCustomResultTemplateGenerator
{
    /**
     * Singleton implemented as a weak reference.
     */
    private static WeakReference singleton;

    /**
     * Protected constructor to avoid accidental instantiation.
     */
    protected CustomValueObjectTemplateGenerator() {};

    /**
     * Specifies a new weak reference.
     * @param generator the generator instance to use.
     */
    protected static void setReference(
        final CustomValueObjectTemplateGenerator generator)
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
     * Retrieves a CustomValueObjectTemplateGenerator instance.
     * @return such instance.
     */
    public static CustomValueObjectTemplateGenerator getInstance()
    {
        CustomValueObjectTemplateGenerator result = null;

        WeakReference reference = getReference();

        if  (reference != null)
        {
            result =
                (CustomValueObjectTemplateGenerator) reference.get();
        }

        if  (result == null)
        {
            result = new CustomValueObjectTemplateGenerator();

            setReference(result);
        }

        return result;
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
     * @throws QueryJException if the factory class is invalid.
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
      throws  QueryJException
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
     * @throws IOException if the file cannot be created.
     */
    public void write(
        final BasePerCustomResultTemplate template, final File outputDir)
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
     * Writes a ValueObjectCreator template to disk.
     * @param template the template to write.
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
        final BasePerCustomResultTemplate template,
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
            + extractClassName(template.getResult().getClassValue())
            + ".java",
            template.generate());
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
