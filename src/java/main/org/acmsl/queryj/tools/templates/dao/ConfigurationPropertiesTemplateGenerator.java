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
        final ConfigurationPropertiesTemplateGenerator generator)
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
     * @param project the project, for logging purposes.
     * @param task the task, for logging purposes.
     * @return such template.
     * @precondition repository != null
     * @precondition engineName != null
     * @precondition basePackageName != null
     */
    public ConfigurationPropertiesTemplate createConfigurationPropertiesTemplate(
        final String repository,
        final String engineName,
        final String engineVersion,
        final String basePackageName,
        final Project project,
        final Task task)
    {
        return
            new ConfigurationPropertiesTemplate(
                repository,
                engineName,
                engineVersion,
                basePackageName,
                project,
                task);
    }

    /**
     * Writes a ConfigurationProperties to disk.
     * @param configurationPropertiesTemplate the template to write.
     * @param outputDir the output folder.
     * @throws IOException if the file cannot be created.
     * @precondition configurationPropertiesTemplate != null
     * @precondition outputDir != null
     */
    public void write(
        final ConfigurationPropertiesTemplate configurationPropertiesTemplate,
        final File outputDir)
      throws  IOException
    {
        write(
            configurationPropertiesTemplate,
            configurationPropertiesTemplate.getRepository(),
            outputDir,
            DAOChooserTemplateUtils.getInstance(),
            FileUtils.getInstance());
    }

    /**
     * Writes a <code>ConfigurationProperties</code> to disk.
     * @param configurationPropertiesTemplate the template to write.
     * @param repository the template repository.
     * @param outputDir the output folder.
     * @param daoChooserTemplateUtils the <code>DAOChooserTemplateUtils</code>
     * instance.
     * @param fileUtils the <code>FileUtils</code> instance.
     * @throws IOException if the file cannot be created.
     * @precondition configurationPropertiesTemplate != null
     * @precondition repository != null
     * @precondition outputDir != null
     * @precondition daoChooserTemplateUtils != null
     * @precondition fileUtils != null
     */
    protected void write(
        final ConfigurationPropertiesTemplate configurationPropertiesTemplate,
        final String repository,
        final File outputDir,
        final DAOChooserTemplateUtils daoChooserTemplateUtils,
        final FileUtils fileUtils)
      throws  IOException
    {
        outputDir.mkdirs();

        fileUtils.writeFile(
              outputDir.getAbsolutePath()
            + File.separator
            + daoChooserTemplateUtils.retrievePropertiesFileName(
                  repository.toLowerCase()),
              configurationPropertiesTemplate.generate());
    }
}
