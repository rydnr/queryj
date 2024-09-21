/*
                        QueryJ Template Packaging

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
 * Filename: FindTemplateDefsHandler.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Finds all template def files in "sources" folders.
 *
 * Date: 2013/08/11
 * Time: 20:22
 *
 */
package org.acmsl.queryj.templates.packaging.handlers;

/*
 * Importing QueryJ-Core classes.
 */
import org.acmsl.queryj.QueryJCommand;
import org.acmsl.queryj.QueryJCommandWrapper;
import org.acmsl.queryj.api.exceptions.QueryJBuildException;
import org.acmsl.queryj.templates.packaging.TemplatePackagingSettings;
import org.acmsl.queryj.templates.packaging.exceptions.MissingSourceFoldersAtRuntimeException;
import org.acmsl.queryj.tools.handlers.AbstractQueryJCommandHandler;

/**
 * Importing Apache Commons Logging classes.
 */
import org.apache.commons.logging.Log;

/**
 * Importing JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/*
 * Importing JDK classes.
 */
import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Finds all template def files in "sources" folders.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created 2013/08/11 20:22
 */
@ThreadSafe
public class FindTemplateDefsHandler
    extends AbstractQueryJCommandHandler
    implements TemplatePackagingSettings
{
    /**
     * The DEF filename filter.
     */
    public static final FilenameFilter DEF_FILENAME_FILTER =
        new ExtensionFilenameFilter(".def");

    /**
     * The folder filter.
     */
    public static final FileFilter DIRECTORY_FILTER =
        new DirectoryFilter();

    /**
     * Creates a {@code FindTemplateDefsHandler} instance.
     */
    public FindTemplateDefsHandler() {}

    /**
     * Handles given command.
     * @param command the command to handle.
     * @return <code>true</code> if the chain should be stopped.
     */
    @Override
    public boolean handle(@NotNull final QueryJCommand command)
        throws QueryJBuildException
    {
        return handle(command, command.getLog());
    }

    /**
     * Handles given parameters.
     * @param command the command to handle.
     * @param log the log.
     * @return <code>true</code> if the chain should be stopped.
     * @throws QueryJBuildException if the template defs are unavailable.
     */
    protected boolean handle(@NotNull final QueryJCommand command, @Nullable final Log log)
        throws  QueryJBuildException
    {
        @NotNull final Set<File> t_lBaseFolders = expandFolders(retrieveSourceFolders(command));

        @NotNull final List<File> t_lTotalDefFiles = new ArrayList<>();

        for (@NotNull final File t_BaseFolder: t_lBaseFolders)
        {
            @NotNull final List<File> t_lDefFiles = findDefFiles(t_BaseFolder);

            if (log != null)
            {
                if (t_lDefFiles.size() > 0)
                {
                    log.info(
                        "Found " + t_lDefFiles.size() + " template def files in " + t_BaseFolder.getAbsolutePath());
                }
            }

            t_lTotalDefFiles.addAll(t_lDefFiles);
        }

        new QueryJCommandWrapper<List<File>>(command).setSetting(DEF_FILES, t_lTotalDefFiles);

        return false;
    }

    /**
     * Recursively finds all sub folders contained within given folders.
     * @param folders the folders.
     * @return the list of all sub folders.
     */
    @NotNull
    protected Set<File> expandFolders(@NotNull final List<File> folders)
    {
        @NotNull final Set<File> result = new TreeSet<>();

        for (@NotNull final File t_Folder : folders)
        {
            result.addAll(expandFolders(t_Folder));
        }

        return result;
    }

    /**
     * Recursively finds all sub folders contained within given folder.
     * @param folder the folder.
     * @return the list of all sub folders.
     */
    @NotNull
    protected Set<File> expandFolders(@NotNull final File folder)
    {
        @NotNull final Set<File> result = new TreeSet<>();

        if (folder.isDirectory())
        {
            result.add(folder);

            @Nullable final File[] t_aFolders = folder.listFiles(DIRECTORY_FILTER);

            if (t_aFolders != null)
            {
                for (@Nullable final File t_Folder : t_aFolders)
                {
                    if (t_Folder != null)
                    {
                        result.addAll(expandFolders(t_Folder));
                    }
                }
            }
        }

        return result;
    }

    /**
     * Retrieves the source folders from given command.
     * @param command the command to handle.
     * @return the source folders.
     */
    protected List<File> retrieveSourceFolders(@NotNull final QueryJCommand command)
    {
        @NotNull final List<File> result;

        @Nullable final List<File> aux = new QueryJCommandWrapper<File>(command).getListSetting(SOURCES);

        if (aux == null)
        {
            throw new MissingSourceFoldersAtRuntimeException();
        }
        else
        {
            result = aux;
        }

        return result;
    }

    /**
     * Retrieves the list of template def files inside given folder.
     * @param baseFolder the folder to look for template groups.
     * @return the list of def files.
     */
    @NotNull
    public List<File> findDefFiles(@NotNull final File baseFolder)
    {
        return Arrays.asList(baseFolder.listFiles(DEF_FILENAME_FILTER));
    }

    /**
     * Checks whether a concrete filename is a stg.
     * @author <a href="mailto:queryj@ventura24.es">Jose San Leandro</a>
     */
    protected static class ExtensionFilenameFilter
        implements  FilenameFilter
    {
        /**
         * The extension.
         */
        @NotNull private String m__strExtension;

        /**
         * Creates a new extension filter.
         * @param extension the extension.
         */
        public ExtensionFilenameFilter(@NotNull final String extension)
        {
            immutableSetExtension(extension);
        }

        /**
         * Specifies the extension.
         * @param ext the extension.
         */
        protected final void immutableSetExtension(@NotNull final String ext)
        {
            this.m__strExtension = ext;
        }

        /**
         * Specifies the extension.
         * @param ext the extension.
         */
        @SuppressWarnings("unused")
        protected void setExtension(@NotNull final String ext)
        {
            immutableSetExtension(ext);
        }

        /**
         * Retrieves the extension.
         * @return the extension.
         */
        @NotNull
        public String getExtension()
        {
            return this.m__strExtension;
        }

        /**
         * Checks whether given file ends with a given extension.
         * @param dir the directory.
         * @param name the file name.
         * @return <code>true</code> in such case.
         */
        @Override
        public boolean accept(@NotNull final File dir, @NotNull final String name)
        {
            return accept(name, getExtension());
        }

        /**
         * Checks whether given file ends with a given extension.
         * @param name the file name.
         * @param extension the extension.
         * @return <code>true</code> in such case.
         */
        protected boolean accept(@NotNull final String name, @NotNull final String extension)
        {
            return name.endsWith(extension);
        }

        /**
         * {@inheritDoc}
         */
        @NotNull
        @Override
        public String toString()
        {
            return
                  "{ \"class\": \"" + ExtensionFilenameFilter.class.getName() + '"'
                + ", \"extension\": \"" + m__strExtension + "\" }";
        }
    }

    /**
     * Checks whether a concrete filename is a stg.
     * @author <a href="mailto:queryj@ventura24.es">Jose San Leandro</a>
     */
    protected static class DirectoryFilter
        implements FileFilter
    {
        /**
         * Checks whether given file is a folder itself.
         * @param file the file.
         * @return <code>true</code> in such case.
         */
        @Override
        public boolean accept(@NotNull final File file)
        {
            return file.isDirectory();
        }
    }
}
