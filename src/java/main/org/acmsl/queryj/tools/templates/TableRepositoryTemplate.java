/*
                        QueryJ

    Copyright (C) 2002  Jose San Leandro Armendariz
                        jsanleandro@yahoo.es
                        chousz@yahoo.com

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
    Contact info: jsanleandro@yahoo.es
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
 * Description: Is able to generate table repositories according to database
 *              metadata.
 *
 * Last modified by: $Author$ at $Date$
 *
 * File version: $Revision$
 *
 * Project version: $Name$
 *
 * $Id$
 *
 */
package org.acmsl.queryj.tools.templates;

/*
 * Importing some ACM-SL classes.
 */
import org.acmsl.commons.utils.StringUtils;

/*
 * Importing some JDK classes.
 */
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Is able to generate Table repositories according to database metadata.
 * @author <a href="mailto:jsanleandro@yahoo.es"
 *         >Jose San Leandro</a>
 * @version $Revision$
 */
public class TableRepositoryTemplate
    extends  AbstractTableRepositoryTemplate
    implements  TableRepositoryTemplateDefaults
{
    /**
     * Builds a <code>TableRepositoryTemplate</code> using given information.
     * @param packageName the package name.
     * @param repository the repository.
     */
    public TableRepositoryTemplate(
        final String packageName, final String repository)
    {
        super(
            DEFAULT_HEADER,
            DEFAULT_PACKAGE_DECLARATION,
            packageName,
            repository,
            DEFAULT_PROJECT_IMPORTS_JAVADOC,
            DEFAULT_PROJECT_IMPORTS,
            DEFAULT_ACMSL_IMPORTS,
            DEFAULT_JAVADOC,
            DEFAULT_CLASS_DEFINITION,
            DEFAULT_CLASS_START,
            DEFAULT_TABLE_JAVADOC,
            DEFAULT_TABLE_DEFINITION,
            DEFAULT_CLASS_END);
    }

    /**
     * Retrieves the source code of the generated table repository.
     * @return such source code.
     */
    public String toString()
    {
        return
            toString(
                TableRepositoryUtils.getInstance(), StringUtils.getInstance());
    }

    /**
     * Retrieves the source code of the generated table repository.
     * @param tableRepositoryUtils the <code>TableRepositoryUtils</code>
     * instance.
     * @param stringUtils the <code>StringUtils</code> instance.
     * @return such source code.
     * @precondition tableRepositoryUtils != null
     * @precondition stringUtils != null
     */
    protected String toString(
        final TableRepositoryUtils tableRepositoryUtils,
        final StringUtils stringUtils)
    {
        StringBuffer t_sbResult = new StringBuffer();

        Object[] t_aRepository =
            new Object[]{
                stringUtils.normalize(getRepository(), '_')};

        Object[] t_aPackageName = new Object[]{getPackageName()};

        MessageFormat t_Formatter = new MessageFormat(getHeader());
        t_sbResult.append(t_Formatter.format(t_aRepository));

        t_Formatter = new MessageFormat(getPackageDeclaration());
        t_sbResult.append(t_Formatter.format(t_aPackageName));

        List t_lTables = getTables();

        if  (t_lTables != null) 
        {
            Iterator t_itTables = t_lTables.iterator();

            if  (t_itTables.hasNext()) 
            {
                t_sbResult.append(getProjectImportsJavadoc());
            }

            MessageFormat t_ImportFormatter =
                new MessageFormat(getProjectImports());

            while  (t_itTables.hasNext()) 
            {
                String t_strTable = (String) t_itTables.next();

                if  (t_strTable != null)
                {
                    t_sbResult.append(
                        t_ImportFormatter.format(
                            new Object[]{
                                getPackageName(),
                                stringUtils.capitalize(
                                    t_strTable.toLowerCase(),
                                    '_')}));
                }
            }
        }

        t_sbResult.append(getAcmslImports());

        t_Formatter = new MessageFormat(getJavadoc());
        t_sbResult.append(t_Formatter.format(t_aRepository));

        t_Formatter = new MessageFormat(getClassDefinition());
        t_sbResult.append(
            t_Formatter.format(
                new Object[]
                {
                    tableRepositoryUtils.retrieveTableRepositoryClassName(
                        getRepository())
                }));

        t_sbResult.append(getClassStart());

        if  (t_lTables != null)
        {
            Iterator t_itTables = t_lTables.iterator();

            MessageFormat t_JavadocFormatter =
                new MessageFormat(getTableJavadoc());

            MessageFormat t_DefinitionFormatter =
                new MessageFormat(getTableDefinition());

            while  (t_itTables.hasNext()) 
            {
                String t_strTable = (String) t_itTables.next();

                if  (t_strTable != null)
                {
                    t_sbResult.append(
                        t_JavadocFormatter.format(
                            new Object[]{t_strTable}));

                    t_sbResult.append(
                        t_DefinitionFormatter.format(
                            new Object[]{
                                t_strTable.toUpperCase(),
                                stringUtils.capitalize(
                                    t_strTable.toLowerCase(),
                                    '_')}));
                }
            }
        }

        t_sbResult.append(getClassEnd());

        return t_sbResult.toString();
    }
}
