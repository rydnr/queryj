//;-*- mode: java -*-
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
 * Filename: CustomSqlProviderRetrievalHandler.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Retrieves the CustomSqlProvider instance.
 *
 */
package org.acmsl.queryj.customsql.handlers;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.tools.QueryJBuildException;
import org.acmsl.queryj.customsql.CustomSqlProvider;
import org.acmsl.queryj.customsql.xml.SqlXmlParser;
import org.acmsl.queryj.customsql.xml.SqlXmlParserFactory;
import org.acmsl.queryj.tools.handlers.AbstractQueryJCommandHandler;
import org.acmsl.queryj.tools.handlers.ParameterValidationHandler;

/*
 * Importing some ACM-SL classes.
 */

/*
 * Importing some Commons-Logging classes.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing some JDK classes.
 */
import java.io.File;
import java.util.Map;

/**
 * Retrieves the CustomSqlProvider instance.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
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
     * @throws QueryJBuildException if the build process cannot be performed.
     * @precondition parameters != null
     */
    protected boolean handle(@NotNull final Map parameters)
        throws  QueryJBuildException
    {
        boolean result = true;

        CustomSqlProvider t_CustomSqlProvider = buildCustomSqlProvider(parameters);

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
     * @throws QueryJBuildException if some problem occurs.
     * @precondition parameters != null
     */
    @Nullable
    protected CustomSqlProvider buildCustomSqlProvider(@NotNull final Map parameters)
        throws  QueryJBuildException
    {
        return
            buildCustomSqlProvider(
                retrieveCustomSqlModel(parameters),
                retrieveCustomSqlModelXmlFile(parameters),
                SqlXmlParserFactory.getInstance());
    }

    /**
     * Retrieves the custom sql model from the attribute map.
     * @param parameters the parameters.
     * @return such model.
     * @precondition parameters != null
     */
    @NotNull
    protected String retrieveCustomSqlModel(@NotNull final Map parameters)
    {
        return 
            (String)
                parameters.get(ParameterValidationHandler.CUSTOM_SQL_MODEL);
    }

    /**
     * Retrieves the custom sql model XML file from the attribute map.
     * @param parameters the parameters.
     * @return such reference.
     * @precondition parameters != null
     */
    @NotNull
    protected File retrieveCustomSqlModelXmlFile(@NotNull final Map parameters)
    {
        return 
            (File) parameters.get(ParameterValidationHandler.SQL_XML_FILE);
    }

    /**
     * Builds the custom sql provider from given information.
     * @param model the model.
     * @param xmlFile the XML file.
     * @param factory the <code>SqlXmlParserFactory</code> instance.
     * @return such provider..
     * @throws QueryJBuildException if some problem occurs.
     * @precondition model != null
     * @precondition sqlXmlParserFactory != null
     */
    @Nullable
    protected CustomSqlProvider buildCustomSqlProvider(
        @Nullable final String model,
        @Nullable final File xmlFile,
        @NotNull final SqlXmlParserFactory factory)
      throws  QueryJBuildException
    {
        @Nullable CustomSqlProvider result = null;

        if  (model != null)
        {
            if  (!ParameterValidationHandler.CUSTOM_SQL_MODEL_XML.equals(
                     model))
            {
                throw
                    new QueryJBuildException(
                          "Custom queries can only be provided via sql.xml "
                        + "so far.");
            }
        }

        if  (xmlFile != null)
        {
            @Nullable SqlXmlParser t_Parser = factory.createSqlXmlParser(xmlFile);

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
    @SuppressWarnings("unchecked")
    protected void storeCustomSqlProvider(
        @NotNull final CustomSqlProvider provider,
        @NotNull final Map parameters)
    {
        parameters.put(CUSTOM_SQL_PROVIDER, provider);
    }
}
