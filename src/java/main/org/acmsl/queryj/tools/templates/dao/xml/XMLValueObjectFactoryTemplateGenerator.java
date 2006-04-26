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
 * Description: Is able to generate XML value object factories according to
 *              database metadata.
 *
 */
package org.acmsl.queryj.tools.templates.dao.xml;

/*
 * Importing some project-specific classes.
 */
import org.acmsl.queryj.QueryJException;
import org.acmsl.queryj.tools.metadata.CachingDecoratorFactory;
import org.acmsl.queryj.tools.metadata.DecoratorFactory;
import org.acmsl.queryj.tools.metadata.MetadataManager;
import org.acmsl.queryj.tools.templates.TableTemplate;
import org.acmsl.queryj.tools.templates.TemplateMappingManager;
import org.acmsl.queryj.tools.templates.dao.xml.XMLValueObjectFactoryTemplate;
import org.acmsl.queryj.tools.templates.dao.xml
    .XMLValueObjectFactoryTemplateFactory;

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
 * Is able to generate XML value object factories according to database
 * metadata.
 * @author <a href="mailto:chous@acm-sl.org"
 * >Jose San Leandro</a>
 * @version $Revision$ at $Date$ by $Author$
 */
public class XMLValueObjectFactoryTemplateGenerator
    implements  XMLValueObjectFactoryTemplateFactory
{
    /**
     * Singleton implemented as a weak reference.
     */
    private static WeakReference singleton;

    /**
     * Protected constructor to avoid accidental instantiation.
     */
    protected XMLValueObjectFactoryTemplateGenerator() {};

    /**
     * Specifies a new weak reference.
     * @param generator the generator instance to use.
     */
    protected static void setReference(
        XMLValueObjectFactoryTemplateGenerator generator)
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
     * Retrieves a XMLValueObjectFactoryTemplateGenerator instance.
     * @return such instance.
     */
    public static XMLValueObjectFactoryTemplateGenerator getInstance()
    {
        XMLValueObjectFactoryTemplateGenerator result = null;

        WeakReference reference = getReference();

        if  (reference != null) 
        {
            result = (XMLValueObjectFactoryTemplateGenerator) reference.get();
        }

        if  (result == null) 
        {
            result = new XMLValueObjectFactoryTemplateGenerator();

            setReference(result);
        }

        return result;
    }

    /**
     * Generates a value object factory template.
     * @param packageName the package name.
     * @param valueObjectPackageName the value object package name.
     * @param tableTemplate the table template.
     * @param metadataManager the metadata manager.
     * @param header the header.
     * @return a template.
     * @throws QueryJException if the factory class is invalid.
     * @precondition packageName != null
     * @precondition valueObjectPackageName != null
     * @precondition tableTemplate != null
     * @precondition metadataManager != null
     */
    public XMLValueObjectFactoryTemplate createXMLValueObjectFactoryTemplate(
        final String packageName,
        final String valueObjectPackageName,
        final TableTemplate tableTemplate,
        final MetadataManager metadataManager,
        final String header)
      throws  QueryJException
    {
        return
            new XMLValueObjectFactoryTemplate(
                packageName,
                valueObjectPackageName,
                tableTemplate,
                metadataManager,
                header,
                getDecoratorFactory());
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
     * Writes a value object factory template to disk.
     * @param template the value object factory template to write.
     * @param outputDir the output folder.
     * @throws IOException if the file cannot be created.
     * @precondition template != null
     * @precondition outputDir != null
     */
    public void write(
        final XMLValueObjectFactoryTemplate template,
        final File outputDir)
      throws  IOException
    {
        write(
            template,
            outputDir,
            EnglishGrammarUtils.getInstance(),
            StringUtils.getInstance(),
            FileUtils.getInstance());
    }

    /**
     * Writes a value object factory template to disk.
     * @param template the value object factory template to write.
     * @param outputDir the output folder.
     * @param englishGrammarUtils the <code>EnglishGrammarUtils</code> instance.
     * @param stringUtils the <code>StringUtils</code> instance.
     * @param fileUtils the <code>FileUtils</code> instance.
     * @throws IOException if the file cannot be created.
     * @precondition template != null
     * @precondition outputDir != null
     * @precondition englishGrammarUtils != null
     * @precondition stringUtils != null
     * @precondition fileUtils != null
     */
    protected void write(
        final XMLValueObjectFactoryTemplate template,
        final File outputDir,
        final EnglishGrammarUtils englishGrammarUtils,
        final StringUtils stringUtils,
        final FileUtils fileUtils)
      throws  IOException
    {
        outputDir.mkdirs();

        fileUtils.writeFile(
              outputDir.getAbsolutePath()
            + File.separator
            + "XML"
            + stringUtils.capitalize(
                englishGrammarUtils.getSingular(
                    template.getTableTemplate().getTableName().toLowerCase()),
                '_')
            + "ValueObjectFactory.java",
            template.generate());
    }
}
