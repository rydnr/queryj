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
 * Filename: CustomValueObjectImplTemplateGenerator.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Is able to generate custom ValueObjectImpl templates.
 *
 */
package org.acmsl.queryj.tools.templates.valueobject;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.tools.SingularPluralFormConverter;
import org.acmsl.queryj.QueryJException;
import org.acmsl.queryj.tools.customsql.CustomSqlProvider;
import org.acmsl.queryj.tools.customsql.Result;
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
import org.acmsl.commons.patterns.Singleton;
import org.acmsl.commons.utils.EnglishGrammarUtils;
import org.acmsl.commons.utils.io.FileUtils;
import org.acmsl.commons.utils.StringUtils;

/*
 * Importing some JDK classes.
 */
import java.io.File;
import java.io.IOException;

/**
 * Is able to generate custom ValueObjectImpl templates.
 * @author <a href="mailto:chous@acm-sl.org"
 *         >Jose San Leandro</a>
 */
public class CustomValueObjectImplTemplateGenerator
    extends  CustomValueObjectTemplateGenerator
    implements  Singleton
{
    /**
     * Singleton implemented to avoid the double-checked locking.
     */
    private static class CustomValueObjectImplTemplateGeneratorSingletonContainer
    {
        /**
         * The actual singleton.
         */
        public static final CustomValueObjectImplTemplateGenerator SINGLETON =
            new CustomValueObjectImplTemplateGenerator();
    }

    /**
     * Protected constructor to avoid accidental instantiation.
     */
    protected CustomValueObjectImplTemplateGenerator() {};

    /**
     * Retrieves a <code>CustomValueObjectTemplateGenerator</code> instance.
     * @return such instance.
     */
    public static CustomValueObjectTemplateGenerator getInstance()
    {
        return CustomValueObjectImplTemplateGeneratorSingletonContainer.SINGLETON;
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
                new CustomValueObjectImplTemplate(
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
     * Writes a ValueObjectCreator template to disk.
     * @param template the template to write.
     * @param outputDir the output folder.
     * @param stringUtils the <code>StringUtils</code> instance.
     * @param singularPluralFormConverter the <code>SingularPluralFormConverter</code>
     * instance.
     * @param fileUtils the <code>FileUtils</code> instance.
     * @throws IOException if the file cannot be created.
     * @precondition template != null
     * @precondition outputDir != null
     * @precondition stringUtils != null
     * @precondition singularPluralFormConverter != null
     * @precondition fileUtils != null
     */
    protected void write(
        final BasePerCustomResultTemplate template,
        final File outputDir,
        final StringUtils stringUtils,
        final EnglishGrammarUtils singularPluralFormConverter,
        final FileUtils fileUtils)
      throws  IOException
    {
        outputDir.mkdirs();

        fileUtils.writeFile(
              outputDir.getAbsolutePath()
            + File.separator
            + extractClassName(template.getResult().getClassValue())
            + "ValueObject.java",
            template.generate());
    }
}
