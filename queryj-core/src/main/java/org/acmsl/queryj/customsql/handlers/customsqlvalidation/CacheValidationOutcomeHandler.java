/*
                        QueryJ Core

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
 * Filename: CacheValidationOutcomeHandler.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: If all validations succeed for the current Sql, the outcome
 *              is cached to avoid unnecessary validation in future
 *              executions of QueryJ.
 *
 * Date: 2014/03/16
 * Time: 15:09
 *
 */
package org.acmsl.queryj.customsql.handlers.customsqlvalidation;

/*
 * Importing ACM SL Java Commons classes.
 */
import org.acmsl.commons.utils.io.FileUtils;

/*
 * Importing QueryJ Core classes.
 */
import org.acmsl.queryj.QueryJCommand;
import org.acmsl.queryj.QueryJCommandWrapper;
import org.acmsl.queryj.api.exceptions.QueryJBuildException;
import org.acmsl.queryj.customsql.CustomSqlProvider;
import org.acmsl.queryj.customsql.Sql;
import org.acmsl.queryj.customsql.handlers.CustomSqlCacheWritingHandler;
import org.acmsl.queryj.tools.handlers.AbstractQueryJCommandHandler;

/*
 * Importing JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/*
 * Importing JDK classes.
 */
import java.io.File;
import java.nio.charset.Charset;

/**
 * If all validations succeed for the current {@link Sql}, the
 * outcome is cached to avoid unnecessary validation in future
 * executions of QueryJ.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2014/03/16 15:09
 */
@ThreadSafe
public class CacheValidationOutcomeHandler
    extends AbstractQueryJCommandHandler
{
    /**
     * Asks the handler to process the command. The idea is that each
     * command handler decides if such command is suitable of being
     * processed, and if so perform the concrete actions the command
     * represents.
     *
     * @param command the command to process (or not).
     * @return <code>true</code> if the handler actually process the command,
     *         or maybe because it's not desirable to continue the chain.
     */
    @Override
    public boolean handle(@NotNull final QueryJCommand command)
        throws QueryJBuildException
    {
        @NotNull final Sql<String> t_Sql = new RetrieveQueryHandler().retrieveCurrentSql(command);

        final boolean t_bResultSetGettersCheck =
            new CheckResultSetGettersWorkForDefinedPropertiesHandler().getValidationOutcome(t_Sql, command);

        if (t_bResultSetGettersCheck)
        {
            cacheOutcomeToDisk(t_Sql, command);
        }

        return false;
    }

    /**
     * Writes the validation outcome to disk.
     * @param sql the {@link Sql}.
     * @param command the command.
     * @throws QueryJBuildException if the operation fails.
     */
    protected void cacheOutcomeToDisk(
        @NotNull final Sql<String> sql,
        @NotNull final QueryJCommand command)
        throws QueryJBuildException
    {
        @NotNull final File outputFolder = retrieveOutputFolderForSqlHashes(command);

        @NotNull final CustomSqlProvider customSqlProvider = retrieveCustomSqlProvider(command);

        @NotNull final Charset charset = retrieveCharset(command);

        @NotNull final String hash = customSqlProvider.getHash(sql, charset.displayName());

        writeHash(outputFolder, hash, charset);
    }

    /**
     * Writes the hash to disk.
     * @param outputFolder the output folder.
     * @param hash the hash.
     * @param charset the charset.
     */
    protected void writeHash(
        @NotNull final File outputFolder,
        @NotNull final String hash,
        @NotNull final Charset charset)
    {
        @NotNull final String path = hashPath(outputFolder.getAbsolutePath(), hash);

        if (!outputFolder.exists())
        {
            new File(outputFolder.getAbsolutePath()).mkdirs();
        }

        if (!existsAlready(path))
        {
            FileUtils.getInstance().writeFileIfPossible(path, "", charset);
        }
    }

    /**
     * Retrieves the folder where to store the hashes.
     * @param command the command.
     * @return such folder.
     */
    @NotNull
    public File retrieveOutputFolderForSqlHashes(@NotNull final QueryJCommand command)
    {
        @NotNull final File result;

        @Nullable final File aux =
            new QueryJCommandWrapper<File>(command).getSetting(
                CustomSqlCacheWritingHandler.CUSTOM_SQL_OUTPUT_FOLDER_FOR_HASHES);

        if (aux == null)
        {
            @NotNull final File outputFolder = retrieveProjectOutputDir(command);
            result = new File(outputFolder + File.separator + ".sql");
        }
        else
        {
            result = aux;
        }

        return result;
    }

    /**
     * Retrieves the file name associated to given hash, under the given folder.
     * @param folder the parent folder.
     * @param hash the hash.
     * @return the absolute file name.
     */
    public String hashPath(@NotNull final String folder, @NotNull final String hash)
    {
        return folder + File.separator + hash;
    }

    /**
     * Checks whether the path exists already.
     * @param path the path.
     * @return {@code true} in such case.
     */
    public boolean existsAlready(@NotNull final String path)
    {
        return new File(path).exists();
    }
}
