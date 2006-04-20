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
 * Description: Is able to generate table repositories according to database
 *              metadata.
 *
 */
package org.acmsl.queryj.tools.templates;

/*
 * Importing some project-specific classes.
 */
import org.acmsl.queryj.tools.metadata.DecoratorFactory;

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
 * @author <a href="mailto:chous@acm-sl.org"
 *         >Jose San Leandro</a>
 */
public class TableRepositoryTemplate
    extends  AbstractTableRepositoryTemplate
    implements  TableRepositoryTemplateDefaults
{
    /**
     * Builds a <code>TableRepositoryTemplate</code> using given information.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @param packageName the package name.
     * @param repository the repository.
     */
    public TableRepositoryTemplate(
        final DecoratorFactory decoratorFactory,
        final String packageName,
        final String repository)
    {
        super(
            decoratorFactory,
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
     * Builds the header for logging purposes.
     * @return such header.
     */
    protected String buildHeader()
    {
        return
            buildHeader(
                getRepository(),
                TableRepositoryTemplateUtils.getInstance());
    }

    /**
     * Builds the header for logging purposes.
     * @param repository the table repository.
     * @param tableRepositoryTemplateUtils the
     * <code>TableRepositoryTemplateUtils</code> instance.
     * @return such header.
     * @precondition repository != null
     * @precondition tableRepositoryTemplateUtils != null
     */
    protected String buildHeader(
        final String repository,
        final TableRepositoryTemplateUtils tableRepositoryTemplateUtils)
    {
        return
              "Generating "
            + tableRepositoryTemplateUtils.retrieveTableRepositoryClassName(
                  repository)
            + ".";
    }

    /**
     * Retrieves the source code of the generated table repository.
     * @return such source code.
     */
    protected String generateOutput()
    {
        return
            generateOutput(
                getHeader(),
                getPackageDeclaration(),
                getPackageName(),
                getRepository(),
                getProjectImportsJavadoc(),
                getProjectImports(),
                getAcmslImports(),
                getJavadoc(),
                getClassDefinition(),
                getClassStart(),
                getTableJavadoc(),
                getTableDefinition(),
                getClassEnd(),
                getTables(),
                TableRepositoryTemplateUtils.getInstance(),
                StringUtils.getInstance());
    }

    /**
     * Retrieves the source code of the generated table repository.
     * @param header the header.
     * @param packageDeclaration the package declaration.
     * @param packageName the package name.
     * @param repository the repository.
     * @param importsJavadoc the project imports javadoc.
     * @param projectImports the project imports.
     * @param acmslImports the ACM-SL imports.
     * @param javadoc the class Javadoc.
     * @param classDefinition the class definition.
     * @param classStart the class start.
     * @param tableJavadoc the table Javadoc.
     * @param tableDefinition the table definition.
     * @param classEnd the class end.
     * @param tables the tables.
     * @param tableRepositoryTemplateUtils the
     * <code>TableRepositoryTemplateUtils</code> instance.
     * @param stringUtils the <code>StringUtils</code> instance.
     * @return such source code.
     * @precondition tables != null
     * @precondition tableRepositoryUtils != null
     * @precondition stringUtils != null
     */
    protected String generateOutput(
        final String header,
        final String packageDeclaration,
        final String packageName,
        final String repository,
        final String importsJavadoc,
        final String projectImports,
        final String acmslImports,
        final String javadoc,
        final String classDefinition,
        final String classStart,
        final String tableJavadoc,
        final String tableDefinition,
        final String classEnd,
        final List tables,
        final TableRepositoryTemplateUtils tableRepositoryTemplateUtils,
        final StringUtils stringUtils)
    {
        StringBuffer t_sbResult = new StringBuffer();

        Object[] t_aRepository =
            new Object[]
            {
                stringUtils.capitalize(repository)
            };

        Object[] t_aPackageName = new Object[]{packageName};

        MessageFormat t_Formatter = new MessageFormat(header);
        t_sbResult.append(t_Formatter.format(t_aRepository));

        t_Formatter = new MessageFormat(packageDeclaration);
        t_sbResult.append(t_Formatter.format(t_aPackageName));

        Iterator t_itTables = tables.iterator();

        if  (t_itTables.hasNext()) 
        {
            t_sbResult.append(importsJavadoc);
        }

        MessageFormat t_ImportFormatter =
            new MessageFormat(projectImports);

        while  (t_itTables.hasNext()) 
        {
            String t_strTable = (String) t_itTables.next();

            if  (t_strTable != null)
            {
                t_sbResult.append(
                    t_ImportFormatter.format(
                        new Object[]{
                            packageName,
                            stringUtils.capitalize(
                                t_strTable.toLowerCase(),
                                '_')}));
            }
        }

        t_sbResult.append(acmslImports);

        t_Formatter = new MessageFormat(javadoc);
        t_sbResult.append(t_Formatter.format(t_aRepository));

        t_Formatter = new MessageFormat(classDefinition);
        t_sbResult.append(
            t_Formatter.format(
                new Object[]
                {
                    tableRepositoryTemplateUtils
                        .retrieveTableRepositoryClassName(
                            repository)
                }));

        t_sbResult.append(classStart);

        t_itTables = tables.iterator();

        MessageFormat t_JavadocFormatter =
            new MessageFormat(tableJavadoc);

        MessageFormat t_DefinitionFormatter =
            new MessageFormat(tableDefinition);

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
                        new Object[]
                        {
                            t_strTable.toUpperCase(),
                            stringUtils.capitalize(
                                t_strTable.toLowerCase(),
                                '_')
                        }));
            }
        }

        t_sbResult.append(classEnd);

        return t_sbResult.toString();
    }
}
