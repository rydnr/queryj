/*
                        QueryJ

    Copyright (C) 2002  Jose San Leandro Armendariz
                        jsanleandro@yahoo.es
                        chousz@yahoo.com

    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    version 2 of the License, or (at your option) any later version.

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
                    Urb. Valdecabañas
                    Boadilla del monte
                    28660 Madrid
                    Spain

 ******************************************************************************
 *
 * Filename: $RCSfile$
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Is able to generate configuration properties file.
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
package org.acmsl.queryj.tools.templates.dao;

/*
 * Importing some project-specific  classes.
 */
import org.acmsl.queryj.tools.templates.dao.ConfigurationPropertiesTemplate;
import org.acmsl.queryj.tools.templates.dao
    .ConfigurationPropertiesTemplateFactory;

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
 * Is able to generate configuration properties files.
 * @author <a href="mailto:jsanleandro@yahoo.es"
           >Jose San Leandro</a>
 * @version $Revision$
 */
public class ConfigurationPropertiesTemplateGenerator
    implements  ConfigurationPropertiesTemplateFactory
{
    /**
     * Singleton implemented as a weak reference.
     */
    private static WeakReference singleton;

    /**
     * Protected constructor to avoid accidental instantiation.
     */
    protected ConfigurationPropertiesTemplateGenerator() {};

    /**
     * Specifies a new weak reference.
     * @param generator the generator instance to use.
     */
    protected static void setReference(
        ConfigurationPropertiesTemplateGenerator generator)
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
     * Retrieves a ConfigurationPropertiesTemplateGenerator instance.
     * @return such instance.
     */
    public static ConfigurationPropertiesTemplateGenerator getInstance()
    {
        ConfigurationPropertiesTemplateGenerator result = null;

        WeakReference reference = getReference();

        if  (reference != null) 
        {
            result = (ConfigurationPropertiesTemplateGenerator) reference.get();
        }

        if  (result == null) 
        {
            result = new ConfigurationPropertiesTemplateGenerator() {};

            setReference(result);
        }

        return result;
    }

    /**
     * Creates a ConfigurationProperties template instance.
     * @param repository the repository.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param basePackageName the base package name.
     * @return such template.
     */
    public ConfigurationPropertiesTemplate createConfigurationPropertiesTemplate(
        String repository,
        String engineName,
        String engineVersion,
        String basePackageName)
    {
        ConfigurationPropertiesTemplate result = null;

        if  (   (repository      != null)
             && (engineName      != null)
             && (basePackageName != null))
        {

            result =
                new ConfigurationPropertiesTemplate(
                    repository,
                    engineName,
                    engineVersion,
                    basePackageName) {};
        }

        return result;
    }

    /**
     * Writes a ConfigurationProperties to disk.
     * @param configurationPropertiesTemplate the template to write.
     * @param outputDir the output folder.
     * @throws IOException if the file cannot be created.
     */
    public void write(
            ConfigurationPropertiesTemplate configurationPropertiesTemplate,
            File                            outputDir)
        throws  IOException
    {
        if  (   (configurationPropertiesTemplate != null)
             && (outputDir                       != null))
        {
            StringUtils t_StringUtils = StringUtils.getInstance();
            FileUtils t_FileUtils = FileUtils.getInstance();

            if  (   (t_StringUtils != null)
                 && (t_FileUtils   != null))
            {
                outputDir.mkdirs();

                t_FileUtils.writeFile(
                      outputDir.getAbsolutePath()
                    + File.separator
                    + (  configurationPropertiesTemplate.getRepository()
                       + ".properties").toLowerCase(),
                    configurationPropertiesTemplate.toString());
            }
        }
    }
}
