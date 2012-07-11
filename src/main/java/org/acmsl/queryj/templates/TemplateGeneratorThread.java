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
 * Filename: TemplateGeneratorThread.java
 *
 * Author: Jose San Leandro Armendariz (chous)
 *
 * Description: Thread to make generators concurrent.
 *
 * Date: 7/10/12
 * Time: 8:42 PM
 *
 */
package org.acmsl.queryj.templates;

/*
 * Importing some ACM-SL Commons classes.
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
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Thread to make generators concurrent.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro</a>
 * @since 2012/07/10
 */
public class TemplateGeneratorThread<T extends Template<C>, C extends TemplateContext>
    extends Thread
{
    /**
     * The template.
     */
    private T template;

    /**
     * Whether caching is enabled.
     */
    private boolean caching;

    /**
     * The filename.
     */
    private String fileName;

    /**
     * The output folder.
     */
    private File outputDir;

    /**
     * The charset.
     */
    private Charset charset;

    /**
     * The {@link FileUtils} instance.
     */
    private FileUtils fileUtils;

    /**
     * The countdown latch.
     */
    private CountDownLatch latch;

    /**
     * The count of threads alive.
     */
    private AtomicInteger threadsCreated;

    /**
     * Creates a {@link TemplateGeneratorThread} with given information.
     * @param template the template.
     * @param caching whether to enable caching or not.
     * @param fileName the file name.
     * @param outputFolder the output folder.
     * @param charset the {@link Charset} instance.
     * @param fileUtils the {@link FileUtils} instance.
     * @param latch the {@link CountDownLatch latch}.
     * @param threadsCreated the number of threads created so far.
     */
    public TemplateGeneratorThread(
        @NotNull final T template,
        final boolean caching,
        @NotNull final String fileName,
        @NotNull final File outputFolder,
        @NotNull final Charset charset,
        @NotNull final FileUtils fileUtils,
        @NotNull final CountDownLatch latch,
        @NotNull final AtomicInteger threadsCreated)
    {
        immutableSetTemplate(template);
        immutableSetCaching(caching);
        immutableSetFileName(fileName);
        immutableSetOutputFolder(outputFolder);
        immutableSetCharset(charset);
        immutableSetFileUtils(fileUtils);
        immutableSetLatch(latch);
        immutableSetThreadsCreated(threadsCreated);
    }

    /**
     * Specifies the template.
     * @param template the {@link Template} instance to use.
     */
    protected final void immutableSetTemplate(@NotNull final T template)
    {
        this.template = template;
    }

    /**
     * Specifies the template.
     * @param template the {@link Template} instance to use.
     */
    @SuppressWarnings("unused")
    protected void setTemplate(@NotNull final T template)
    {
        immutableSetTemplate(template);
    }

    /**
     * Retrieves the template.
     * @return such template.
     */
    protected T getTemplate()
    {
        return template;
    }

    /**
     * Retrieves whether to use caching or not.
     * @param flag such condition.
     */
    protected final void immutableSetCaching(final boolean flag)
    {
        this.caching = flag;
    }

    /**
     * Retrieves whether to use caching or not.
     * @param flag such condition.
     */
    @SuppressWarnings("unused")
    protected void setCaching(final boolean flag)
    {
        immutableSetCaching(flag);
    }

    /**
     * Retrieves if template caching is enabled.
     * @return such condition.
     */
    protected boolean isCaching()
    {
        return this.caching;
    }

    /**
     * Specifies the file name.
     * @param fileName the file name.
     */
    protected final void immutableSetFileName(@NotNull final String fileName)
    {
        this.fileName = fileName;
    }

    /**
     * Specifies the file name.
     * @param fileName the file name.
     */
    @SuppressWarnings("unused")
    protected void setFileName(@NotNull final String fileName)
    {
        immutableSetFileName(fileName);
    }

    /**
     * Retrieves the file name.
     * @return such information.
     */
    @NotNull
    protected String getFileName()
    {
        return this.fileName;
    }

    /**
     * Specifies the output folder.
     * @param outputFolder the output folder.
     */
    protected final void immutableSetOutputFolder(@NotNull final File outputFolder)
    {
        this.outputDir = outputFolder;
    }

    /**
     * Specifies the output folder.
     * @param outputFolder the output folder.
     */
    @SuppressWarnings("unused")
    protected void setOutputFolder(@NotNull final File outputFolder)
    {
        immutableSetOutputFolder(outputFolder);
    }

    /**
     * Retrieves the output folder.
     * @return such information.
     */
    @NotNull
    public File getOutputFolder()
    {
        return this.outputDir;
    }

    /**
     * Specifies the charset.
     * @param charset the {@link Charset} to use.
     */
    protected final void immutableSetCharset(@NotNull final Charset charset)
    {
        this.charset = charset;
    }

    /**
     * Specifies the charset.
     * @param charset the {@link Charset} to use.
     */
    @SuppressWarnings("unused")
    protected void setCharset(@NotNull final Charset charset)
    {
        immutableSetCharset(charset);
    }

    /**
     * Retrieves the charset.
     * @return the {@link Charset} in use.
     */
    protected Charset getCharset()
    {
        return this.charset;
    }

    /**
     * Specifies the {@link FileUtils} instance.
     * @param fileUtils such instance.
     */
    protected final void immutableSetFileUtils(@NotNull final FileUtils fileUtils)
    {
        this.fileUtils = fileUtils;
    }

    /**
     * Specifies the {@link FileUtils} instance.
     * @param fileUtils such instance.
     */
    @SuppressWarnings("unused")
    protected void setFileUtils(@NotNull final FileUtils fileUtils)
    {
        immutableSetFileUtils(fileUtils);
    }

    /**
     * Retrieves the {@link FileUtils} instance.
     * @return such instance.
     */
    @NotNull
    protected FileUtils getFileUtils()
    {
        return this.fileUtils;
    }
    /**
     * Specifies the count-down latch.
     * @param latch such {@link CountDownLatch latch}.
     */
    protected final void immutableSetLatch(final CountDownLatch latch)
    {
        this.latch = latch;
    }

    /**
     * Specifies the count-down latch.
     * @param latch such {@link CountDownLatch latch}.
     */
    @SuppressWarnings("unused")
    protected void setLatch(final CountDownLatch latch)
    {
        immutableSetLatch(latch);
    }

    /**
     * Retrieves the count-down latch.
     * @return such {@link CountDownLatch latch}.
     */
    protected CountDownLatch getLatch()
    {
        return this.latch;
    }

    /**
     * Specifies the number of threads created (reference).
     * @param threadsCreated such reference.
     */
    protected final void immutableSetThreadsCreated(@NotNull final AtomicInteger threadsCreated)
    {
        this.threadsCreated = threadsCreated;
    }

    /**
     * Specifies the number of threads created (reference).
     * @param threadsCreated such reference.
     */
    @SuppressWarnings("unused")
    protected void setThreadsCreated(@NotNull final AtomicInteger threadsCreated)
    {
        immutableSetThreadsCreated(threadsCreated);
    }

    /**
     * Retrieves the number of threads created (reference).
     * @return such reference.
     */
    protected AtomicInteger getThreadsCreated()
    {
        return this.threadsCreated;
    }

    /**
     * Serializes given template.
     * @param template the template.
     * @param outputFilePath the output file path.
     */
    protected void serializeTemplate(@NotNull final T template, @NotNull final String outputFilePath)
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
     * Performs the generation process.
     * @param template the template.
     * @param caching whether template caching is enabled.
     * @param fileName the file name.
     * @param outputDir the output folder.
     * @param charset the {@link Charset} to use.
     * @param fileUtils the {@link FileUtils} instance.
     * @param log the {@link Log} instance.
     * @throws IOException if the output dir cannot be created.
     */
    protected void generate(
        @NotNull final T template,
        final boolean caching,
        @NotNull final String fileName,
        @NotNull final File outputDir,
        @NotNull final Charset charset,
        @NotNull final FileUtils fileUtils,
        @NotNull final Log log)
      throws IOException
    {
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
                log.debug(
                    "Writing " + (t_strFileContents.length() * 2) + " bytes (" + charset + "): "
                    + t_strOutputFile);
            }

            fileUtils.writeFile(
                t_strOutputFile,
                t_strFileContents,
                charset);
        }
        else
        {
            log.debug(
                "Not writing " + t_strOutputFile + " since the generated content is empty");
        }
    }

    public void run()
    {
        runGenerator(
            getTemplate(),
            isCaching(),
            getFileName(),
            getOutputFolder(),
            getCharset(),
            getFileUtils(),
            getLatch(),
            getThreadsCreated(),
            UniqueLogFactory.getLog(TemplateGeneratorThread.class));
    }

    /**
     * Runs the template generation process.
     * @param template the template..
     * @param caching whether template caching is enabled.
     * @param fileName the file name.
     * @param outputDir the output folder.
     * @param charset the {@link Charset} to use.
     * @param fileUtils the {@link FileUtils} instance.
     * @param latch the {@link CountDownLatch latch}.
     * @param threadsCreated the number of the threads created
     * @param log the {@link Log} instance.
     */
    protected void runGenerator(
        @NotNull final T template,
        final boolean caching,
        @NotNull final String fileName,
        @NotNull final File outputDir,
        @NotNull final Charset charset,
        @NotNull final FileUtils fileUtils,
        @NotNull CountDownLatch latch,
        @NotNull AtomicInteger threadsCreated,
        @NotNull final Log log)
    {
        try
        {
            generate(
                template,
                caching,
                fileName,
                outputDir,
                charset,
                fileUtils,
                log);

            latch.countDown();
        }
        catch (@NotNull final IOException ioException)
        {
            log.warn(ioException);
        }
    }
}
