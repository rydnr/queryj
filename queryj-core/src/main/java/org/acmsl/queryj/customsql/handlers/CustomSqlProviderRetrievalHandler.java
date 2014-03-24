//;-*- mode: java -*-
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
 * Filename: CustomSqlProviderRetrievalHandler.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Retrieves the CustomSqlProvider instance.
 *
 */
package org.acmsl.queryj.customsql.handlers;

/*
 * Importing QueryJ Core classes.
 */
import org.acmsl.queryj.QueryJCommand;
import org.acmsl.queryj.QueryJCommandWrapper;
import org.acmsl.queryj.api.exceptions.QueryJBuildException;
import org.acmsl.queryj.customsql.CustomSqlProvider;
import org.acmsl.queryj.customsql.exceptions.MissingCustomSqlFileAtRuntimeException;
import org.acmsl.queryj.customsql.xml.SqlXmlParser;
import org.acmsl.queryj.customsql.xml.SqlXmlParserFactory;
import org.acmsl.queryj.tools.handlers.AbstractQueryJCommandHandler;
import org.acmsl.queryj.tools.handlers.ParameterValidationHandler;

/*
 * Importing JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing some JDK classes.
 */
import java.io.File;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * Retrieves the CustomSqlProvider instance.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
@ThreadSafe
public class CustomSqlProviderRetrievalHandler
    extends  AbstractQueryJCommandHandler
{
    /**
     * The custom-sql provider key.
     */
    public static final String CUSTOM_SQL_PROVIDER = "..custom-sql.provider..";

    /**
     * Creates a <code>CustomSqlProviderRetrievalHandler</code> instance.
     */
    public CustomSqlProviderRetrievalHandler() {}

    /**
     * Handles given parameters.
     *
     *
     * @param parameters the parameters.
     * @return <code>true</code> if the chain should be stopped.
     */
    @Override
    public boolean handle(@NotNull final QueryJCommand parameters)
        throws  QueryJBuildException
    {
        boolean result = true;

        @Nullable final CustomSqlProvider t_CustomSqlProvider = buildCustomSqlProvider(parameters);

        if (t_CustomSqlProvider != null)
        {
            storeCustomSqlProvider(t_CustomSqlProvider, parameters);

            result = false;
        }

        return result;
    }

    /**
     * Builds the custom SQL provider.
     * @param parameters the parameter map.
     * @return such provider.
     */
    @Nullable
    protected CustomSqlProvider buildCustomSqlProvider(@NotNull final QueryJCommand parameters)
        throws  QueryJBuildException
    {
        return
            buildCustomSqlProvider(
                retrieveCustomSqlModelXmlFile(parameters),
                SqlXmlParserFactory.getInstance());
    }

    /**
     * Retrieves the custom sql model XML file from the attribute map.
     * @param parameters the parameters.
     * @return such reference.
     */
    @NotNull
    protected File retrieveCustomSqlModelXmlFile(@NotNull final QueryJCommand parameters)
    {
        @NotNull final File result;

        @Nullable final File aux = parameters.getFileSetting(ParameterValidationHandler.SQL_XML_FILE);

        if (aux == null)
        {
            throw new MissingCustomSqlFileAtRuntimeException();
        }
        else
        {
            result = aux;
        }

        return result;
    }

    /**
     * Builds the custom sql provider from given information.
     * @param xmlFile the XML file.
     * @param factory the <code>SqlXmlParserFactory</code> instance.
     * @return such provider.
     */
    @Nullable
    protected CustomSqlProvider buildCustomSqlProvider(
        @Nullable final File xmlFile,
        @NotNull final SqlXmlParserFactory factory)
      throws  QueryJBuildException
    {
        @Nullable CustomSqlProvider result = null;

        if  (xmlFile != null)
        {
            @Nullable final SqlXmlParser t_Parser = factory.createSqlXmlParser(xmlFile);

            /*
              t_Parser.setClassLoader(
                  org.apache.tools.ant.util.ClasspathUtils.getDelegate(
                      task.getClassLoader());
            */

            if (t_Parser != null)
            {
                t_Parser.parse();
            }

            result = t_Parser;
        }

        return result;
    }

    /**
     * Stores the CustomSqlProvider instance.
     * @param provider the provider to store.
     * @param parameters the parameter map.
     */
    protected void storeCustomSqlProvider(
        @NotNull final CustomSqlProvider provider,
        @NotNull final QueryJCommand parameters)
    {
        new QueryJCommandWrapper<CustomSqlProvider>(parameters).setSetting(CUSTOM_SQL_PROVIDER, provider);
    }
}
