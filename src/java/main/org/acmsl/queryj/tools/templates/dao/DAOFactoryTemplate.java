/*
                        QueryJ

    Copyright (C) 2002-2005  Jose San Leandro Armendariz
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
 * Filename: $RCSfile$
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Is able to generate DAO factories according to
 *              database metadata.
 *
 */
package org.acmsl.queryj.tools.templates.dao;

/*
 * Importing some project-specific classes.
 */
import org.acmsl.queryj.tools.DatabaseMetaDataManager;
import org.acmsl.queryj.tools.PackageUtils;
import org.acmsl.queryj.tools.MetaDataUtils;
import org.acmsl.queryj.tools.templates.TableTemplate;

/*
 * Importing some ACM-SL classes.
 */
import org.acmsl.commons.utils.EnglishGrammarUtils;
import org.acmsl.commons.utils.StringUtils;

/*
 * Importing some JDK classes.
 */
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Is able to generate DAO factories according to
 * database metadata.
 * @author <a href="mailto:chous@acm-sl.org"
 *         >Jose San Leandro</a>
 */
public class DAOFactoryTemplate
    extends  AbstractDAOFactoryTemplate
    implements  DAOFactoryTemplateDefaults
{
    /**
     * Builds a <code>DAOFactoryTemplate</code> using given information.
     * @param tableTemplate the table template.
     * @param metaDataManager the metadata manager.
     * @param packageName the package name.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param quote the identifier quote string.
     * @param basePackageName the base package name.
     * @param jndiDataSource the JNDI location of the data source.
     */
    public DAOFactoryTemplate(
        final TableTemplate tableTemplate,
        final DatabaseMetaDataManager metaDataManager,
        final String packageName,
        final String engineName,
        final String engineVersion,
        final String quote,
        final String basePackageName,
        final String jndiDataSource)
    {
        super(
            tableTemplate,
            metaDataManager,
            packageName,
            engineName,
            engineVersion,
            quote,
            basePackageName,
            jndiDataSource);
    }

    /**
     * Retrieves the source code of the generated field tableName.
     * @return such source code.
     */
    protected String generateOutput()
    {
        return
            generateOutput(
                getTableTemplate(),
                getMetaDataManager(),
                getPackageName(),
                getEngineName(),
                getEngineVersion(),
                getQuote(),
                getBasePackageName(),
                getJNDIDataSource(),
                TemplateUtils.getInstance(),
                StringUtils.getInstance(),
                EnglishGrammarUtils.getInstance(),
                MetaDataUtils.getInstance(),
                PackageUtils.getInstance());
    }

    /**
     * Retrieves the source code of the generated field tableName.
     * @param tableTemplate the table template.
     * @param metaDataManager the metadata manager.
     * @param packageName the package name.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param quote the identifier quote string.
     * @param basePackageName the base package name.
     * @param jndiDataSource the JNDI location of the data source.
     * @param stringUtils the <code>StringUtils</code> instance.
     * @param englishGrammarUtils the <code>EnglishGrammarUtils</code>
     * instance.
     * @param metaDataUtils the <code>MetaDataUtils</code> instance.
     * @param packageUtils the <code>PackageUtils</code> instance.
     * @return such source code.
     * @precondition tableTemplate != null
     * @precondition metaDataManager != null
     * @precondition stringUtils != null
     * @precondition englishGrammarUtils != null
     * @precondition metaDataUtils != null
     * @precondition packageUtils != null
     */
    protected String generateOutput(
        final TableTemplate tableTemplate,
        final DatabaseMetaDataManager metaDataManager,
        final String packageName,
        final String engineName,
        final String engineVersion,
        final String quote,
        final String basePackageName,
        final String jndiDataSource,
        final StringUtils stringUtils,
        final EnglishGrammarUtils englishGrammarUtils,
        final MetaDataUtils metaDataUtils,
        final PackageUtils packageUtils)
    {
        StringBuffer t_sbResult = new StringBuffer();

        String t_strTableName = tableTemplate.getTableName();

        String t_strBaseDAOPackage =
            packageUtils.retrieveBaseDAOPackage(basePackageName);

        String t_strValueObjectName =
            stringUtils.capitalize(
                englishGrammarUtils.getSingular(
                    t_strTableName.toLowerCase()),
                '_');

        String t_strBaseDAOFactoryPackage =
            packageUtils.retrieveBaseDAOFactoryPackage(
                basePackageName);

        String t_strDAOPackage =
            packageUtils.retrieveDAOPackage(
                basePackageName, engineName);
        
        List t_lFields = tableTemplate.getFields();

        return t_sbResult.toString();
    }
}
