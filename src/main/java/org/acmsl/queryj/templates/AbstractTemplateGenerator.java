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
 * Filename: AbstractTemplateGenerator.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Common logic for template generators.
 *
 */
package org.acmsl.queryj.templates;

/*
 * Importing some project-specific classes.
 */
import org.acmsl.queryj.metadata.CachingDecoratorFactory;
import org.acmsl.queryj.metadata.DecoratorFactory;

/*
 * Importing some ACM-SL classes.
 */
import org.acmsl.commons.logging.UniqueLogFactory;
import org.acmsl.commons.utils.io.FileUtils;

/*
 * Importing some Apache Commons-Logging classes.
 */
import org.apache.commons.logging.Log;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/*
 * Importing some JDK classes.
 */
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.charset.Charset;

/**
 * Common logic for template generators.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public abstract class AbstractTemplateGenerator<N extends Template<C>, C extends TemplateContext>
    implements TemplateGenerator<N, C>
{
    /**
     * Whether to enable template caching.
     */
    private boolean m__bCaching = true;

    /**
     * The thread count.
     */
    private int m__iThreadCount = 1;

    /**
     * Creates an {@link AbstractTemplateGenerator} with given settings.
     * @param caching whether to support caching or not.
     * @param threadCount the number of threads to use.
     */
    protected AbstractTemplateGenerator(final boolean caching, final int threadCount)
    {
        immutableSetCaching(caching);
        immutableSetThreadCount(threadCount);
    }

    /**
     * Specifies whether to cache templates or not.
     * @param flag such setting.
     */
    protected final void immutableSetCaching(final boolean flag)
    {
        m__bCaching = flag;
    }

    /**
     * Specifies whether to cache templates or not.
     * @param flag such setting.
     */
    @SuppressWarnings("unused")
    protected void setCaching(final boolean flag)
    {
        immutableSetCaching(flag);
    }

    /**
     * Retrieves whether to cache templates or not.
     * @return such setting.
     */
    public boolean isCaching()
    {
        return m__bCaching;
    }

    /**
     * Specifies the thread count.
     * @param count such value.
     */
    protected final void immutableSetThreadCount(final int count)
    {
        m__iThreadCount = count;
    }

    /**
     * Specifies the thread count.
     * @param count such value.
     */
    @SuppressWarnings("unused")
    protected void setThreadCount(final int count)
    {
        immutableSetThreadCount(count);
    }

    /**
     * Retrieves the thread count.
     * @return such value.
     */
    @SuppressWarnings("unused")
    public int getThreadCount()
    {
        return m__iThreadCount;
    }

    /**
     * Serializes given template.
     * @param template the template.
     * @param outputFilePath the output file path.
     */
    protected void serializeTemplate(@NotNull final N template, @NotNull final String outputFilePath)
    {
        try
        {
            @NotNull final File outputFile = new File(outputFilePath);
            @NotNull final File baseFolder = outputFile.getParentFile();

            if (!baseFolder.exists())
            {
                if (!baseFolder.mkdirs())
                {
                    throw new IOException(baseFolder + " does not exist and cannot be created");
                }
            }

            if (!baseFolder.canWrite())
            {
                throw new IOException(baseFolder + " is not writable");
            }

            ObjectOutputStream t_osCache =
                new ObjectOutputStream(new FileOutputStream(new File(outputFilePath)));

            t_osCache.writeObject(template);
        }
        catch (@NotNull IOException cannotSerialize)
        {
            @NotNull final Log t_Log =
                UniqueLogFactory.getLog(
                    AbstractTemplateGenerator.class);

            t_Log.warn(
                "Cannot serialize template " + outputFilePath + " (" + cannotSerialize + ")",
                cannotSerialize);
        }
    }

    /**
     * Writes a table template to disk.
     * @param template the table template to write.
     * @param outputDir the output folder.
     * @param charset the file encoding.
     * @throws IOException if the file cannot be created.
     */
    public void write(
        @NotNull final N template,
        @NotNull final File outputDir,
        @NotNull final Charset charset)
        throws  IOException
    {
        write(
            isCaching(),
            template,
            retrieveTemplateFileName(template.getTemplateContext()),
            outputDir,
            charset,
            FileUtils.getInstance());
    }

    /**
     * Writes a table template to disk.
     * @param caching whether to use caching or not.
     * @param template the table template to write.
     * @param fileName the template's file name.
     * @param outputDir the output folder.
     * @param charset the file encoding.
     * @param fileUtils the {@link FileUtils} instance.
     * @throws IOException if the file cannot be created.
     */
    protected void write(
        final boolean caching,
        @NotNull final N template,
        @NotNull final String fileName,
        @NotNull final File outputDir,
        @NotNull final Charset charset,
        @NotNull final FileUtils fileUtils)
        throws  IOException
    {
        Log t_Log =
            UniqueLogFactory.getLog(
                AbstractTemplateGenerator.class);

        String t_strOutputFile =
            outputDir.getAbsolutePath()
                + File.separator
                + fileName;

        if (caching)
        {
            serializeTemplate(template, t_strOutputFile + ".ser");
        }
        String t_strFileContents = template.generate();

        if  (!"".equals(t_strFileContents))
        {
            boolean folderCreated = outputDir.mkdirs();

            if (   (!folderCreated)
                && (!outputDir.exists()))
            {
                throw
                    new IOException("Cannot create output dir: " + outputDir);
            }
            else
            {
                if (t_Log != null)
                {
                    t_Log.debug(
                        "Writing " + (t_strFileContents.length() * 2) + " bytes (" + charset + "): "
                        + t_strOutputFile);
                }

                fileUtils.writeFile(
                    t_strOutputFile,
                    t_strFileContents,
                    charset);
            }
        }
        else
        {
            if (t_Log != null)
            {
                t_Log.debug(
                    "Not writing " + t_strOutputFile + " since the generated content is empty");
            }
        }
    }

    /**
     * Retrieves the decorator factory.
     * @return such instance.
     */
    @NotNull
    public DecoratorFactory getDecoratorFactory()
    {
        return CachingDecoratorFactory.getInstance();
    }

}
