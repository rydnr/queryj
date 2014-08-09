/*
                        QueryJ Template Maven Plugin

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
 * Filename: TemplatePackagingParameterValidationHandler.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Validates the parameters.
 *
 * Date: 2013/08/11
 * Time: 08:48
 *
 */
package org.acmsl.queryj.templates.packaging.handlers;

/*
 * Importing QueryJ Template Packaging classes.
 */
import org.acmsl.queryj.templates.packaging.TemplatePackagingSettings;
import org.acmsl.queryj.templates.packaging.exceptions.MissingSourcesException;

/*
 * Importing QueryJ-Core classes.
 */
import org.acmsl.queryj.QueryJCommand;
import org.acmsl.queryj.QueryJCommandWrapper;
import org.acmsl.queryj.api.exceptions.QueryJBuildException;
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
import java.util.List;

/**
 * Validates the parameters.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created 2013/08/11 08:48
 */
@ThreadSafe
public class TemplatePackagingParameterValidationHandler
    extends AbstractQueryJCommandHandler
    implements TemplatePackagingSettings
{
    /**
     * Creates a {@link TemplatePackagingParameterValidationHandler} instance.
     */
    public TemplatePackagingParameterValidationHandler() {}

    /**
     * Handles given command.
     * @param command the command to handle.
     * @return <code>true</code> if the chain should be stopped.
     */
    @Override
    public boolean handle(@NotNull final QueryJCommand command)
        throws QueryJBuildException
    {
        validateParameters(new QueryJCommandWrapper<File>(command).getListSetting(SOURCES));

        return false;
    }

    /**
     * Validates the parameters.
     * @param sources the source folders.
     * @throws QueryJBuildException if the parameters fail validation.
     */
    protected void validateParameters(@Nullable final List<File> sources)
        throws  QueryJBuildException
    {
        if  (   (sources == null)
             || (sources.size() == 0))
        {
            throw new MissingSourcesException();
        }
    }
}
