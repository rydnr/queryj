/*
                        QueryJ

    Copyright (C) 2002  Jose San Leandro Armendariz
                        jsanleandro@yahoo.es
                        chousz@yahoo.com

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
    Contact info: jsanleandro@yahoo.es
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
 * Description: Is able to generate keyword repository templates according to
 *              keyword definition.
 *
 * Last modified by: $Author$ at $Date$
 *
 * File version: $Revision$
 *
 * Project version: $Name$
 *
 * $Id$
 *
 */
package org.acmsl.queryj.tools.templates;

/*
 * Importing some project-specific classes.
 */
import org.acmsl.queryj.tools.templates.KeywordRepositoryTemplate;
import org.acmsl.queryj.tools.templates.KeywordRepositoryTemplateFactory;

/*
 * Importing some ACM-SL classes.
 */
import org.acmsl.commons.utils.io.FileUtils;
import org.acmsl.commons.utils.StringUtils;

/*
 * Importing some JDK classes.
 */
import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;

/**
 * Is able to generate keyword repositories template according to
 * keyword definition.
 * @author <a href="mailto:jsanleandro@yahoo.es"
           >Jose San Leandro</a>
 * @version $Revision$
 */
public class KeywordRepositoryTemplateGenerator
    implements  KeywordRepositoryTemplateFactory
{
    /**
     * Singleton implemented as a weak reference.
     */
    private static WeakReference singleton;

    /**
     * Protected constructor to avoid accidental instantiation.
     */
    protected KeywordRepositoryTemplateGenerator() {};

    /**
     * Specifies a new weak reference.
     * @param generator the generator instance to use.
     */
    protected static void setReference(
        KeywordRepositoryTemplateGenerator generator)
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
     * Retrieves a KeywordRepositoryTemplateGenerator instance.
     * @return such instance.
     */
    public static KeywordRepositoryTemplateGenerator getInstance()
    {
        KeywordRepositoryTemplateGenerator result = null;

        WeakReference reference = getReference();

        if  (reference != null) 
        {
            result = (KeywordRepositoryTemplateGenerator) reference.get();
        }

        if  (result == null) 
        {
            result = new KeywordRepositoryTemplateGenerator() {};

            setReference(result);
        }

        return result;
    }

    /**
     * Generates a keyword repository template.
     * @param packageName the package name.
     * @param repository the repository.
     * @return such template.
     * @throws IOException if the file cannot be created.
     */
    public KeywordRepositoryTemplate createKeywordRepositoryTemplate(
        String packageName,
        String repository)
    {
        KeywordRepositoryTemplate result = null;

        if  (   (packageName != null)
             && (repository  != null))
        {
            result =
                new KeywordRepositoryTemplate(packageName, repository) {};
        }

        return result;
    }

    /**
     * Writes a keyword repository template to disk.
     * @param keywordRepositoryTemplate the keyword repository to write.
     * @param outputDir the output folder.
     * @throws IOException if the file cannot be created.
     */
    public void write(
            KeywordRepositoryTemplate keywordRepositoryTemplate,
            File                      outputDir)
        throws  IOException
    {
        if  (   (keywordRepositoryTemplate != null)
             && (outputDir                 != null))
        {
            StringUtils t_StringUtils = StringUtils.getInstance();
            FileUtils t_FileUtils = FileUtils.getInstance();

            if  (   (t_StringUtils != null)
                 && (t_FileUtils   != null))
            {
                outputDir.mkdirs();

                String t_strNormalizedRepository =
                    t_StringUtils.normalize(
                        keywordRepositoryTemplate.getRepository(), '_');

                t_FileUtils.writeFile(
                      outputDir.getAbsolutePath()
                    + File.separator
                    + t_strNormalizedRepository
                    + "KeywordRepository.java",
                    keywordRepositoryTemplate.toString());
            }
        }
    }
}
