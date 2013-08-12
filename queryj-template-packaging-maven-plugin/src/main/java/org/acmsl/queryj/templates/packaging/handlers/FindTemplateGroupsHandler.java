/*
                        queryj

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
 * Filename: FindTemplateGroupsHandler.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: 
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
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created 2013/08/11 20:22
 */
@ThreadSafe
public class FindTemplateGroupsHandler
    extends AbstractQueryJCommandHandler
    implements TemplatePackagingSettings
{
    /**
     * The STG filename filter.
     */
    public static final FilenameFilter STG_FILENAME_FILTER =
        new StgFilenameFilter();

    /**
     * The folder filter.
     */
    public static final FileFilter DIRECTORY_FILTER =
        new DirectoryFilter();

    /**
     * Creates a {@link FindTemplateGroupsHandler} instance.
     */
    public FindTemplateGroupsHandler() {}

    /**
     * Handles given command.
     * @param command the command to handle.
     * @return <code>true</code> if the chain should be stopped.
     * @throws org.acmsl.queryj.api.exceptions.QueryJBuildException if the build process cannot be performed.
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
     * @throws QueryJBuildException if the build process cannot be performed.
     */
    protected boolean handle(@NotNull final QueryJCommand command, @Nullable final Log log)
        throws  QueryJBuildException
    {
        @NotNull final Set<File> t_lBaseFolders = expandFolders(retrieveSourceFolders(command));

        @NotNull final List<File> t_lTotalStgFiles = new ArrayList<File>();

        for (@NotNull final File t_BaseFolder: t_lBaseFolders)
        {
            @NotNull final List<File> t_lStgFiles = findStgFiles(t_BaseFolder);

            if (log != null)
            {
                if (t_lStgFiles.size() > 0)
                {
                    log.error(
                        "Found " + t_lStgFiles.size() + " template group files in " + t_BaseFolder.getAbsolutePath());
                }
            }

            t_lTotalStgFiles.addAll(t_lStgFiles);
        }

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
        @NotNull final Set<File> result = new TreeSet<File>();

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
        @NotNull final Set<File> result = new TreeSet<File>();

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
            // TODO
            throw new RuntimeException("No source folders");
        }
        else
        {
            result = aux;
        }

        return result;
    }

    /**
     * Retrieves the list of STGs inside given folder.
     * @param baseFolder the folder to look for template groups.
     * @return the list of STG files.
     */
    @NotNull
    public List<File> findStgFiles(@NotNull final File baseFolder)
    {
        return Arrays.asList(baseFolder.listFiles(STG_FILENAME_FILTER));
    }

    /**
     * Checks whether a concrete filename is a stg.
     * @author <a href="mailto:queryj@ventura24.es">Jose San Leandro</a>
     */
    protected static class StgFilenameFilter
        implements  FilenameFilter
    {
        /**
         * Checks whether given file is a jsp.
         * @param dir the directory.
         * @param name the file name.
         * @return <code>true</code> in such case.
         */
        @Override
        public boolean accept(@NotNull final File dir, @NotNull final String name)
        {
            return name.endsWith(".stg");
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
