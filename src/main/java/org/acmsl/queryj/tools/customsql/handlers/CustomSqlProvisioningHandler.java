//;-*- mode: java -*-
/*
                        QueryJ

    Copyright (C) 2002-2007  Jose San Leandro Armendariz
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
 * Filename: CustomSqlProvisioningHandler.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Adds additional custom-sql information to avoid
 *              the need to manually specify elements derived from the model.
 *
 */
package org.acmsl.queryj.tools.customsql.handlers;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.tools.AntCommand;
import org.acmsl.queryj.tools.customsql.CustomSqlProvider;
import org.acmsl.queryj.tools.customsql.xml.SqlXmlParser;
import org.acmsl.queryj.tools.customsql.xml.SqlXmlParserFactory;
import org.acmsl.queryj.tools.metadata.MetadataManager;
import org.acmsl.queryj.tools.metadata.MetadataTypeManager;
import org.acmsl.queryj.tools.handlers.AbstractAntCommandHandler;
import org.acmsl.queryj.tools.handlers.ParameterValidationHandler;
import org.acmsl.queryj.QueryJException;

/*
 * Importing some ACM-SL classes.
 */
import org.acmsl.commons.logging.UniqueLogFactory;
import org.acmsl.commons.patterns.Command;

/*
 * Importing some Ant classes.
 */
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.util.ClasspathUtils;

/*
 * Importing some Commons-Logging classes.
 */
import org.apache.commons.logging.Log;

/*
 * Importing some JDK classes.
 */
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

/**
 * Adds additional custom-sql information to avoid
 * the need to manually specify elements derived from the model.
 * @author <a href="mailto:chous@acm-sl.org"
           >Jose San Leandro</a>
 */
public class CustomSqlProvisioningHandler
    extends  AbstractAntCommandHandler
{
    /**
     * The relative weight depending on the number of tables.
     */
    public static final double RELATIVE_SIZE_WEIGHT =
        (MAX_WEIGHT - MIN_WEIGHT) / 100.0d;

    /**
     * Creates a <code>CustomSqlProvisioningHandler</code> instance.
     */
    public CustomSqlProvisioningHandler() {};

    /**
     * Handles given command.
     * @param command the command to handle.
     * @return <code>true</code> if the chain should be stopped.
     * @throws BuildException if the build process cannot be performed.
     * @precondition command != null
     */
    public boolean handle(final AntCommand command)
        throws  BuildException
    {
        return handle(command.getAttributeMap());
    }

    /**
     * Handles given parameters.
     * @param parameters the parameters.
     * @return <code>true</code> if the chain should be stopped.
     * @throws BuildException if the build process cannot be performed.
     * @precondition parameters != null
     */
    protected boolean handle(final Map parameters)
        throws  BuildException
    {
        return
            handle(
                retrieveCustomSqlProvider(parameters),
                retrieveMetadataManager(parameters));
    }

    /**
     * Handles given parameters.
     * @param customSqlProvider the <code>CustomSqlProvider</code> instance.
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @return <code>true</code> if the chain should be stopped.
     * @throws BuildException if the build process cannot be performed.
     * @precondition customSqlProvider != null
     * @precondition metadataManager != null
     */
    protected boolean handle(
        final CustomSqlProvider customSqlProvider,
        final MetadataManager metadataManager)
      throws  BuildException
    {
        return
            handle(
                customSqlProvider,
                metadataManager,
                metadataManager.getMetadataTypeManager());
    }

    /**
     * Handles given parameters.
     * @param customSqlProvider the <code>CustomSqlProvider</code> instance.
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @param metadataTypeManager the <code>MetadataTypeManager</code> instance.
     * @return <code>true</code> if the chain should be stopped.
     * @throws BuildException if the build process cannot be performed.
     * @precondition customSqlProvider != null
     * @precondition metadataManager != null
     * @precondition metadataTypeManager != null
     */
    protected boolean handle(
        final CustomSqlProvider customSqlProvider,
        final MetadataManager metadataManager,
        final MetadataTypeManager metadataTypeManager)
      throws  BuildException
    {
        boolean result = false;

        String[] t_astrTableNames = metadataManager.getTableNames();

        int t_iTableCount =
            (t_astrTableNames != null) ? t_astrTableNames.length : 0;

        String t_strTableName;
        String t_strResultName;
        String[] t_astrAttributeNames;
        String t_strAttributeName;
        String t_strPropertyName;
        int t_iAttributeCount;
        Collection t_cResultData;

        for  (int t_iTableIndex = 0;
                  t_iTableIndex < t_iTableCount;
                  t_iTableIndex++)
        {
            t_strTableName = t_astrTableNames[t_iTableIndex];

            t_astrAttributeNames =
                metadataManager.getAllColumnNames(t_strTableName);

            t_iAttributeCount =
                (t_astrAttributeNames != null)
                ?  t_astrAttributeNames.length
                :  0;

            t_cResultData = new ArrayList();

            for  (int t_iAttributeIndex = 0;
                      t_iAttributeIndex < t_iAttributeCount;
                      t_iAttributeIndex++)
            {
                t_strAttributeName =
                    t_astrAttributeNames[t_iAttributeIndex];

                t_strPropertyName =
                    buildPropertyName(t_strAttributeName);

                customSqlProvider.addProperty(
                    t_strPropertyName,
                    t_strTableName,
                    metadataTypeManager.getFieldType(
                        metadataManager.getColumnType(
                            t_strTableName,
                            t_strAttributeName),
                        metadataManager.allowsNull(
                            t_strTableName,
                            t_strAttributeName),
                            false));

                t_cResultData.add(t_strPropertyName);
            }
        }

        return result;
    }

    /**
     * Builds the result name.
     * @param name the attribute name.
     * @return such result.
     */
    protected String buildResultName(final String name)
    {
        // TODO
        return name;
    }

    /**
     * Builds the property name.
     * @param name the attribute name.
     * @return such property.
     */
    protected String buildPropertyName(final String name)
    {
        // TODO
        return name;
    }

    /**
     * Retrieves the relative weight of this handler.
     * @param parameters the parameters.
     * @return a value between <code>MIN_WEIGHT</code>
     * and <code>MAX_WEIGHT</code>.
     */
    public double getRelativeWeight(final Map parameters)
    {
        return getRelativeWeight(retrieveMetadataManager(parameters));
    }

    /**
     * Retrieves the relative weight of this handler.
     * @param metadataManager the metadata manager..
     * @return a value between <code>MIN_WEIGHT</code>
     * and <code>MAX_WEIGHT</code>.
     * @precondition metadataManager != null
     */
    public double getRelativeWeight(final MetadataManager metadataManager)
    {
        double result = MIN_WEIGHT;

        String[] t_astrTableNames =
            (metadataManager != null)
            ? metadataManager.getTableNames() : null;

        int t_iTableCount =
            (t_astrTableNames != null) ? t_astrTableNames.length : 0;

        result +=
              (MAX_WEIGHT - MIN_WEIGHT)
            * (  (t_astrTableNames != null)
               ? (double) t_astrTableNames.length : 0.0d)
            * RELATIVE_SIZE_WEIGHT;

        result = Math.min(MAX_WEIGHT, result);

        return result;
    }
}
