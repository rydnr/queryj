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
 * Description: Is able to generate table repositories according to database
 *              metadata.
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
import org.acmsl.queryj.tools.templates.TableRepositoryTemplate;
import org.acmsl.queryj.tools.templates.TableRepositoryTemplateFactory;

/*
 * Importing some ACM-SL classes.
 */
import org.acmsl.commons.utils.io.FileUtils;
import org.acmsl.commons.utils.StringUtils;

/*
 * Importing some Ant classes.
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
 * Is able to generate Table repositories according to database metadata.
 * @author <a href="mailto:jsanleandro@yahoo.es"
           >Jose San Leandro</a>
 * @version $Revision$
 */
public class TableRepositoryTemplateGenerator
    implements  TableRepositoryTemplateFactory
{
    /**
     * Singleton implemented as a weak reference.
     */
    private static WeakReference singleton;

    /**
     * Protected constructor to avoid accidental instantiation.
     */
    protected TableRepositoryTemplateGenerator() {};

    /**
     * Specifies a new weak reference.
     * @param generator the generator instance to use.
     */
    protected static void setReference(
        final TableRepositoryTemplateGenerator generator)
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
     * Retrieves a TableRepositoryTemplateGenerator instance.
     * @return such instance.
     */
    public static TableRepositoryTemplateGenerator getInstance()
    {
        TableRepositoryTemplateGenerator result = null;

        WeakReference reference = getReference();

        if  (reference != null) 
        {
            result = (TableRepositoryTemplateGenerator) reference.get();
        }

        if  (result == null) 
        {
            result = new TableRepositoryTemplateGenerator();

            setReference(result);
        }

        return result;
    }

    /**
     * Generates a table repository template.
     * @param packageName the package name.
     * @param repository the repository.
     * @param project the project, for logging purposes.
     * @param task the task, for logging purposes.
     * @return such template.
     * @precondition packageName != null
     * @precondition repository != null
     */
    public TableRepositoryTemplate createTableRepositoryTemplate(
        final String packageName,
        final String repository,
        final Project project,
        final Task task)
    {
        return new TableRepositoryTemplate(packageName, repository, project, task);
    }

    /**
     * Writes a table repository template to disk.
     * @param tableRepositoryTemplate the table repository template to write.
     * @param outputDir the output folder.
     * @param project the project, for logging purposes.
     * @param task the task, for logging purposes.
     * @throws IOException if the file cannot be created.
     */
    public void write(
        final TableRepositoryTemplate tableRepositoryTemplate,
        final File outputDir,
        final Project project,
        final Task task)
      throws  IOException
    {
        write(
            tableRepositoryTemplate,
            outputDir,
            project,
            task,
            FileUtils.getInstance(),
            TableRepositoryTemplateUtils.getInstance());
    }
            
    /**
     * Writes a table repository template to disk.
     * @param tableRepositoryTemplate the table repository template to write.
     * @param outputDir the output folder.
     * @param project the project, for logging purposes.
     * @param task the task, for logging purposes.
     * @param fileUtils the <code>FileUtils</code> instance.
     * @param tableRepositoryTemplateUtils the
     * <code>TableRepositoryTemplateUtils</code> instance.
     * @throws IOException if the file cannot be created.
     * @precondition tableRepositoryTemplate != null
     * @precondition outputDir != null
     * @precondition fileUtils != null
     * @precondition tableRepositoryTemplateUtils != null
     */
    public void write(
        final TableRepositoryTemplate tableRepositoryTemplate,
        final File outputDir,
        final Project project,
        final Task task,
        final FileUtils fileUtils,
        final TableRepositoryTemplateUtils tableRepositoryTemplateUtils)
      throws  IOException
    {
        outputDir.mkdirs();

        fileUtils.writeFile(
              outputDir.getAbsolutePath()
            + File.separator
            + tableRepositoryTemplateUtils.retrieveTableRepositoryClassName(
                  tableRepositoryTemplate.getRepository())
            + ".java",
            tableRepositoryTemplate.generate());
    }
}
