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
import org.acmsl.queryj.tools.QueryJBuildException;

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
import org.jetbrains.annotations.Nullable;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/*
 * Importing some JDK classes.
 */
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Common logic for template generators.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
@ThreadSafe
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
    @ThreadSafe
    protected AbstractTemplateGenerator(final boolean caching, final int threadCount)
    {
        immutableSetCaching(caching);
        immutableSetThreadCount(threadCount);
    }

    /**
     * Specifies whether to cache templates or not.
     * @param flag such setting.
     */
    @ThreadSafe
    protected final void immutableSetCaching(final boolean flag)
    {
        m__bCaching = flag;
    }

    /**
     * Specifies whether to cache templates or not.
     * @param flag such setting.
     */
    @ThreadSafe
    @SuppressWarnings("unused")
    protected void setCaching(final boolean flag)
    {
        immutableSetCaching(flag);
    }

    /**
     * Retrieves whether to cache templates or not.
     * @return such setting.
     */
    @ThreadSafe
    public boolean isCaching()
    {
        return m__bCaching;
    }

    /**
     * Specifies the thread count.
     * @param count such value.
     */
    @ThreadSafe
    protected final void immutableSetThreadCount(final int count)
    {
        m__iThreadCount = count;
    }

    /**
     * Specifies the thread count.
     * @param count such value.
     */
    @ThreadSafe
    @SuppressWarnings("unused")
    protected void setThreadCount(final int count)
    {
        immutableSetThreadCount(count);
    }

    /**
     * Retrieves the thread count.
     * @return such value.
     */
    @ThreadSafe
    @SuppressWarnings("unused")
    public int getThreadCount()
    {
        return m__iThreadCount;
    }

    /**
     * Retrieves the decorator factory.
     * @return such instance.
     */
    @ThreadSafe
    @NotNull
    public DecoratorFactory getDecoratorFactory()
    {
        return CachingDecoratorFactory.getInstance();
    }

    /**
     * Writes a table template to disk.
     * @param template the table template to write.
     * @param outputDir the output folder.
     * @param rootFolder the root folder.
     * @param charset the file encoding.
     * @throws IOException if the file cannot be created.
     * @throws QueryJBuildException if the generation process fails.
     */
    @ThreadSafe
    @Override
    public void write(
        @NotNull final N template,
        @NotNull final File outputDir,
        @NotNull final File rootFolder,
        @NotNull final Charset charset)
        throws  IOException,
                QueryJBuildException
    {
        write(
            isCaching(),
            template,
            retrieveTemplateFileName(template.getTemplateContext()),
            outputDir,
            rootFolder,
            charset,
            FileUtils.getInstance());
    }

    /**
     * Writes a table template to disk.
     * @param caching whether to use caching or not.
     * @param template the table template to write.
     * @param fileName the template's file name.
     * @param outputDir the output folder.
     * @param rootFolder the root folder.
     * @param charset the file encoding.
     * @param fileUtils the {@link FileUtils} instance.
     * @throws IOException if the file cannot be created.
     * @throws QueryJBuildException if the generation process fails.
     */
    @ThreadSafe
    protected void write(
        final boolean caching,
        @NotNull final N template,
        @NotNull final String fileName,
        @NotNull final File outputDir,
        @NotNull final File rootFolder,
        @NotNull final Charset charset,
        @NotNull final FileUtils fileUtils)
        throws  IOException,
                QueryJBuildException
    {
        generate(
            template,
            caching,
            fileName,
            outputDir,
            rootFolder,
            charset,
            fileUtils,
            UniqueLogFactory.getLog(AbstractTemplateGenerator.class));
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
    @ThreadSafe
    protected void generate(
        @NotNull final N template,
        final boolean caching,
        @NotNull final String fileName,
        @NotNull final File outputDir,
        @NotNull final File rootFolder,
        @NotNull final Charset charset,
        @NotNull final FileUtils fileUtils,
        @NotNull final Log log)
        throws IOException,
        QueryJBuildException
    {
        @NotNull final String relevantContent = template.generate(true);

        @NotNull final String newHash = computeHash(relevantContent, charset);

        @Nullable final String oldHash = retrieveHash(fileName, outputDir, rootFolder, fileUtils);

        if (   (oldHash == null)
            || (!newHash.equals(oldHash)))
        {
            String t_strOutputFile =
                outputDir.getAbsolutePath()
                + File.separator
                + fileName;

            if (caching)
            {
                serializeTemplate(
                    template,
                    getOutputDir(outputDir, rootFolder).getAbsolutePath() + File.separator + "." + fileName + ".ser");
            }

            String t_strFileContents = template.generate(false);

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

                writeHash(newHash, fileName, outputDir, rootFolder, charset, fileUtils);
            }
            else
            {
                log.debug(
                    "Not writing " + t_strOutputFile + " since the generated content is empty");
            }
        }
    }

    /**
     * Tries to read the hash from disk.
     * @param fileName the file name.
     * @param outputDir the output folder.
     * @param fileUtils the {@link FileUtils} instance.
     * @return the hash, if found.
     */
    @ThreadSafe
    @Nullable
    protected String retrieveHash(
        @NotNull final String fileName,
        @NotNull final File outputDir,
        @NotNull final File rootFolder,
        @NotNull final FileUtils fileUtils)
    {
        @Nullable String result =
            fileUtils.readFileIfPossible(buildHashFile(fileName, outputDir, rootFolder));

        if (   (result != null)
            && (result.endsWith("\n")))
        {
            result = result.substring(0, result.length() - 1);
        }

        return result;
    }

    /**
     * Builds the hash file.
     * @param fileName the file name.
     * @param outputDir the output folder.
     * @param rootFolder the root folder.
     * @return the hash file.
     */
    @ThreadSafe
    @NotNull
    protected File buildHashFile(
        @NotNull final String fileName,
        @NotNull final File outputDir,
        @NotNull final File rootFolder)
    {
        return
            new File(
                getOutputDir(outputDir, rootFolder).getAbsolutePath() + File.separator + "." + fileName + ".sha256");
    }

    /**
     * Serializes given template.
     * @param template the template.
     * @param outputFilePath the output file path.
     */
    @ThreadSafe
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
     * Retrieves the actual base folder.
     * @param outputDir the output dir.
     * @param rootFolder the root folder.
     * @return the actual folder.
     */
    @ThreadSafe
    @NotNull
    protected File getOutputDir(@NotNull final File outputDir, @NotNull final File rootFolder)
    {
        File result = outputDir;

        String rootPath = rootFolder.getAbsolutePath();
        String outputPath = outputDir.getAbsolutePath();

        if (outputPath.startsWith(rootPath))
        {
            outputPath = rootPath + File.separator + ".queryj" + outputPath.substring(rootPath.length());

            result = new File(outputPath);

            if (   (!result.exists())
                && (!result.mkdirs()))
            {
                result = outputDir;
            }
        }

        return result;
    }

    /**
     * Writes the hash value to disk, to avoid unnecessary generation.
     * @param hashValue the content to write
     * @param fileName the file name.
     * @param outputDir the output dir.
     * @param charset the charset.
     * @param fileUtils the {@link FileUtils} instance.
     */
    @ThreadSafe
    protected void writeHash(
        @NotNull final String hashValue,
        @NotNull final String fileName,
        @NotNull final File outputDir,
        @NotNull final File rootFolder,
        @NotNull final Charset charset,
        @NotNull final FileUtils fileUtils)
    {
        fileUtils.writeFileIfPossible(
            buildHashFile(fileName, outputDir, rootFolder), hashValue, charset);
    }

    /**
     * Computes the hash of given content.
     * @param relevantContent the content.
     * @return the hash.
     */
    @ThreadSafe
    protected String computeHash(
        @NotNull final String relevantContent, @NotNull final Charset charset)
        throws QueryJBuildException
    {
        byte[] content = relevantContent.getBytes(charset);

        byte[] buffer = new byte[content.length];

        try
        {
            MessageDigest md = MessageDigest.getInstance("SHA-256");

            md.update(content, 0, buffer.length);

            buffer = md.digest();
        }
        catch (@NotNull final NoSuchAlgorithmException e)
        {
            throw new QueryJBuildException("SHA-256 not supported", e);
        }

        return toHex(buffer);
    }

    /**
     * Converts given bytes to hexadecimal.                            v
     * @param buffer the buffer to convert.
     * @return the hexadecimal representation.
     */
    @ThreadSafe
    @NotNull
    protected String toHex(@NotNull final byte[] buffer)
    {
        @NotNull StringBuilder result = new StringBuilder(buffer.length);

        for (byte currentByte : buffer)
        {
            result.append(Integer.toHexString(0xFF & currentByte));
        }

        return result.toString();
    }
}
