//;-*- mode: java -*-
/*
                        QueryJ

    Copyright (C) 2002-2006  Jose San Leandro Armendariz
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
    Contact info: chous@acm-sl.org
    Postal Address: c/Playa de Lagoa, 1
                    Urb. Valdecabanas
                    Boadilla del monte
                    28660 Madrid
                    Spain

 ******************************************************************************
 *
 * Filename: $RCSfile: $
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Retrieves the CustomSqlProvider instance.
 *
 */
package org.acmsl.queryj.tools.customsql.handlers;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.tools.QueryJBuildException;
import org.acmsl.queryj.tools.QueryJCommand;
import org.acmsl.queryj.tools.customsql.CustomSqlProvider;
import org.acmsl.queryj.tools.customsql.xml.SqlXmlParser;
import org.acmsl.queryj.tools.customsql.xml.SqlXmlParserFactory;
import org.acmsl.queryj.tools.handlers.AbstractQueryJCommandHandler;
import org.acmsl.queryj.tools.handlers.ParameterValidationHandler;
import org.acmsl.queryj.QueryJException;

/*
 * Importing some ACM-SL classes.
 */
import org.acmsl.commons.logging.UniqueLogFactory;
import org.acmsl.commons.patterns.Command;

/*
 * Importing some Commons-Logging classes.
 */
import org.apache.commons.logging.Log;

/*
 * Importing some JDK classes.
 */
import java.io.File;
import java.util.Map;

/**
 * Retrieves the CustomSqlProvider instance.
 * @author <a href="mailto:chous@acm-sl.org"
           >Jose San Leandro</a>
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
    public CustomSqlProviderRetrievalHandler() {};

    /**
     * Handles given parameters.
     * @param parameters the parameters.
     * @return <code>true</code> if the chain should be stopped.
     * @throws QueryJBuildException if the build process cannot be performed.
     * @precondition parameters != null
     */
    protected boolean handle(final Map parameters)
        throws  QueryJBuildException
    {
        storeCustomSqlProvider(
            buildCustomSqlProvider(parameters),
            parameters);

        return false;
    }

    /**
     * Builds the custom SQL provider.
     * @param parameters the parameter map.
     * @return such provider.
     * @throws QueryJBuildException if some problem occurs.
     * @precondition parameters != null
     */
    protected CustomSqlProvider buildCustomSqlProvider(final Map parameters)
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
    protected String retrieveCustomSqlModel(final Map parameters)
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
    protected File retrieveCustomSqlModelXmlFile(final Map parameters)
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
    protected CustomSqlProvider buildCustomSqlProvider(
        final String model,
        final File xmlFile,
        final SqlXmlParserFactory factory)
      throws  QueryJBuildException
    {
        CustomSqlProvider result = null;

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
            SqlXmlParser t_Parser = factory.createSqlXmlParser(xmlFile);

            /*
              t_Parser.setClassLoader(
                  org.apache.tools.ant.util.ClasspathUtils.getDelegate(
                      task.getClassLoader());
            */

            t_Parser.parse();

            result = t_Parser;
        }

        return result;
    }

    /**
     * Stores the CustomSqlProvider instance.
     * @param provider the provider to store.
     * @param parameters the parameter map.
     * @precondition provider != null
     * @precondition parameters != null
     */
    protected void storeCustomSqlProvider(
        final CustomSqlProvider provider,
        final Map parameters)
    {
        parameters.put(CUSTOM_SQL_PROVIDER, provider);
    }
}
