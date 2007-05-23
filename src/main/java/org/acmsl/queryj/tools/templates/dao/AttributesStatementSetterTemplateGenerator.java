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
 * Filename: AttributesStatementSetterTemplateGenerator.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Is able to generate AttributesStatementSetter templates.
 *
 */
package org.acmsl.queryj.tools.templates.dao;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.tools.SingularPluralFormConverter;
import org.acmsl.queryj.QueryJException;
import org.acmsl.queryj.tools.customsql.CustomSqlProvider;
import org.acmsl.queryj.tools.metadata.CachingDecoratorFactory;
import org.acmsl.queryj.tools.metadata.DecoratorFactory;
import org.acmsl.queryj.tools.metadata.MetadataManager;
import org.acmsl.queryj.tools.templates.dao.AttributesStatementSetterTemplate;
import org.acmsl.queryj.tools.templates.BasePerTableTemplate;
import org.acmsl.queryj.tools.templates.BasePerTableTemplateFactory;
import org.acmsl.queryj.tools.templates.BasePerTableTemplateGenerator;

/*
 * Importing some ACM-SL classes.
 */
import org.acmsl.commons.utils.EnglishGrammarUtils;
import org.acmsl.commons.patterns.Singleton;
import org.acmsl.commons.utils.io.FileUtils;
import org.acmsl.commons.utils.StringUtils;

/*
 * Importing some JDK classes.
 */
import java.io.File;
import java.io.IOException;

/**
 * Is able to generate AttributesStatementSetter templates.
 * @author <a href="mailto:chous@acm-sl.org"
 *         >Jose San Leandro</a>
 */
public class AttributesStatementSetterTemplateGenerator
    implements  BasePerTableTemplateFactory,
                BasePerTableTemplateGenerator,
                Singleton
{
    /**
     * Singleton implemented to avoid the double-checked locking.
     */
    private static class AttributesStatementSetterTemplateGeneratorSingletonContainer
    {
        /**
         * The actual singleton.
         */
        public static final AttributesStatementSetterTemplateGenerator SINGLETON =
            new AttributesStatementSetterTemplateGenerator();
    }

    /**
     * Protected constructor to avoid accidental instantiation.
     */
    protected AttributesStatementSetterTemplateGenerator() {};

    /**
     * Retrieves a AttributesStatementSetterTemplateGenerator instance.
     * @return such instance.
     */
    public static AttributesStatementSetterTemplateGenerator getInstance()
    {
        return AttributesStatementSetterTemplateGeneratorSingletonContainer.SINGLETON;
    }

    /**
     * Generates a template.
     * @param tableName the table name.
     * @param metadataManager the metadata manager.
     * @param customSqlProvider the CustomSqlProvider instance.
     * @param packageName the package name.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param quote the identifier quote string.
     * @param basePackageName the base package name.
     * @param repositoryName the name of the repository.
     * @param jmx whether to support JMX.
     * @param header the optional header.
     * @param jndiLocation the location of the datasource in JNDI.
     * @return a template.
     * @throws QueryJException if the factory class is invalid.
     * @precondition tableName != null
     * @precondition metadataManager != null
     * @precondition packageName != null
     * @precondition engineName != null
     * @precondition engineVersion != null
     * @precondition quote != null
     * @precondition basePackageName != null
     * @precondition repositoryName != null
     */
    public BasePerTableTemplate createTemplate(
        final String tableName,
        final MetadataManager metadataManager,
        final CustomSqlProvider customSqlProvider,
        final String packageName,
        final String engineName,
        final String engineVersion,
        final String quote,
        final String basePackageName,
        final String repositoryName,
        final boolean jmx,
        final String header,
        final String jndiLocation)
     throws  QueryJException
    {
        return
            new AttributesStatementSetterTemplate(
                tableName,
                metadataManager,
                customSqlProvider,
                header,
                getDecoratorFactory(),
                packageName,
                engineName,
                engineVersion,
                quote,
                basePackageName,
                repositoryName,
                jmx,
                jndiLocation);
    }

    /**
     * Retrieves the decorator factory.
     * @return such instance.
     */
    public DecoratorFactory getDecoratorFactory()
    {
        return AttributeStatementSetterDecoratorFactory.getInstance();
    }

    /**
     * Writes the template to disk.
     * @param template the template to write.
     * @param outputDir the output folder.
     * @throws IOException if the file cannot be created.
     * @precondition template instanceof DAOTemplate
     * @precondition outputDir != null
     */
    public void write(
        final BasePerTableTemplate template, final File outputDir)
      throws  IOException
    {
        write(
            template,
            outputDir, 
            StringUtils.getInstance(),
            SingularPluralFormConverter.getInstance(),
            FileUtils.getInstance());
    }

    /**
     * Writes a DAO template to disk.
     * @param template the template to write.
     * @param outputDir the output folder.
     * @param stringUtils the <code>StringUtils</code> instance.
     * @param singularPluralFormConverter the <code>SingularPluralFormConverter</code>
     * instance.
     * @param fileUtils the <code>FileUtils</code> instance.
     * @throws IOException if the file cannot be created.
     * @precondition template instanceof DAOTemplate
     * @precondition outputDir != null
     * @precondition stringUtils != null
     * @precondition singularPluralFormConverter != null
     * @precondition fileUtils != null
     */
    protected void write(
        final BasePerTableTemplate template,
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
            + stringUtils.capitalize(
                singularPluralFormConverter.getSingular(
                    template.getTableName().toLowerCase()),
                '_')
            + "AttributesStatementSetter.java",
            template.generate());
    }
}
