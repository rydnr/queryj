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
 * Description: Is able to generate dataAccessContext-local.xml templates.
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
import org.acmsl.queryj.tools.templates.dao.DataAccessContextLocalTemplate;
import org.acmsl.queryj.tools.templates.dao
    .DataAccessContextLocalTemplateFactory;

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
 * Is able to generate dataAccessContext-local.xml templates.
 * @author <a href="mailto:jsanleandro@yahoo.es"
 *         >Jose San Leandro</a>
 * @version $Revision$
 */
public class DataAccessContextLocalTemplateGenerator
    implements  DataAccessContextLocalTemplateFactory
{
    /**
     * Singleton implemented as a weak reference.
     */
    private static WeakReference singleton;

    /**
     * Protected constructor to avoid accidental instantiation.
     */
    protected DataAccessContextLocalTemplateGenerator() {};

    /**
     * Specifies a new weak reference.
     * @param generator the generator instance to use.
     */
    protected static void setReference(
        final DataAccessContextLocalTemplateGenerator generator)
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
     * Retrieves a DataAccessContextLocalTemplateGenerator instance.
     * @return such instance.
     */
    public static DataAccessContextLocalTemplateGenerator getInstance()
    {
        DataAccessContextLocalTemplateGenerator result = null;

        WeakReference reference = getReference();

        if  (reference != null) 
        {
            result = (DataAccessContextLocalTemplateGenerator) reference.get();
        }

        if  (result == null) 
        {
            result = new DataAccessContextLocalTemplateGenerator() {};

            setReference(result);
        }

        return result;
    }

    /**
     * Creates a DataAccessContextLocal template instance.
     * @param jndiLocation the JNDI location.
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
    public DataAccessContextLocalTemplate createDataAccessContextLocalTemplate(
        final String jndiLocation,
        final String engineName,
        final String engineVersion,
        final String basePackageName,
        final Project project,
        final Task task)
    {
        return
            new DataAccessContextLocalTemplate(
                jndiLocation,
                engineName,
                engineVersion,
                basePackageName,
                project,
                task);
    }

    /**
     * Writes a dataAccessContext-local.xml to disk.
     * @param template the template to write.
     * @param outputDir the output folder.
     * @throws IOException if the file cannot be created.
     * @precondition template != null
     * @precondition outputDir != null
     */
    public void write(
        final DataAccessContextLocalTemplate template,
        final File outputDir)
      throws  IOException
    {
        write(
            template,
            outputDir,
            FileUtils.getInstance());
    }

    /**
     * Writes a <code>dataAccessContext-local.xml</code> to disk.
     * @param template the template to write.
     * @param outputDir the output folder.
     * @param fileUtils the <code>FileUtils</code> instance.
     * @throws IOException if the file cannot be created.
     * @precondition template != null
     * @precondition outputDir != null
     * @precondition fileUtils != null
     */
    protected void write(
        final DataAccessContextLocalTemplate template,
        final File outputDir,
        final FileUtils fileUtils)
      throws  IOException
    {
        outputDir.mkdirs();

        fileUtils.writeFile(
              outputDir.getAbsolutePath()
            + File.separator
            + "dataAccessContext-local.xml.sample",
            template.generate());
    }
}
