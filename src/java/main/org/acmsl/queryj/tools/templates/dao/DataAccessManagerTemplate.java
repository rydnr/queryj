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
 * Description: Is able to generate DataAccessManager facade class according
 *              to database metadata.
 *
 */
package org.acmsl.queryj.tools.templates.dao;

/*
 * Importing some ACM-SL classes.
 */
import org.acmsl.commons.utils.EnglishGrammarUtils;
import org.acmsl.commons.utils.StringUtils;

/*
 * Importing Ant classes.
 */
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;

/*
 * Importing some JDK classes.
 */
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Is able to generate DataAccessManager facade class according to database
 * metadata.
 * @author <a href="mailto:chous@acm-sl.org"
 *         >Jose San Leandro</a>
 */
public class DataAccessManagerTemplate
    extends  AbstractDataAccessManagerTemplate
    implements  DataAccessManagerTemplateDefaults
{
    /**
     * Builds a DataAccessManagerTemplate using given information.
     * @param packageName the package name.
     * @param repository the repository.
     * @param project the project, for logging purposes.
     * @param task the task, for logging purposes.
     */
    public DataAccessManagerTemplate(
        final String packageName,
        final String repository,
        final Project project,
        final Task task)
    {
        super(
            DEFAULT_HEADER,
            DEFAULT_PACKAGE_DECLARATION,
            packageName,
            repository,
            DEFAULT_PROJECT_IMPORTS_JAVADOC,
            DEFAULT_PROJECT_IMPORTS,
            DEFAULT_JDK_IMPORTS,
            DEFAULT_JAVADOC,
            DEFAULT_CLASS_DEFINITION,
            DEFAULT_CLASS_START,
            DEFAULT_DAO_REFERENCE,
            DEFAULT_SINGLETON_BODY,
            DEFAULT_DAO_METHODS,
            DEFAULT_CLASS_END,
            project,
            task);
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
                getJdkImports(),
                getJavadoc(),
                getClassDefinition(),
                getClassStart(),
                getDAOReference(),
                getSingletonBody(),
                getDAOMethods(),
                getClassEnd(),
                getTables(),
                StringUtils.getInstance(),
                EnglishGrammarUtils.getInstance());
    }

    /**
     * Retrieves the source code of the generated table repository.
     * @param header the header.
     * @param packageDeclaration the package declaration.
     * @param packageName the package name.
     * @param repository the repository.
     * @param projectImportsJavadoc the project imports javadoc.
     * @param projectImports the project imports.
     * @param jdkImports the JDK imports.
     * @param javadoc the class Javadoc.
     * @param classDefinition the class definition.
     * @param classStart the class start.
     * @param daoReference the DAO reference.
     * @param singletonBody the singleton body.
     * @param daoMethods the DAO methods.
     * @param classEnd the class end.
     * @param tables the tables.
     * @param stringUtils the <code>StringUtils</code> instance.
     * @param englishGrammarUtils the <code>EnglishGrammarUtils</code>
     * instance.
     * @return such source code.
     * @precondition tables != null
     * @precondition stringUtils != null
     * @precondition englishGrammarUtils != null
     */
    protected String generateOutput(
        final String header,
        final String packageDeclaration,
        final String packageName,
        final String repository,
        final String projectImportsJavadoc,
        final String projectImports,
        final String jdkImports,
        final String javadoc,
        final String classDefinition,
        final String classStart,
        final String daoReference,
        final String singletonBody,
        final String daoMethods,
        final String classEnd,
        final List tables,
        final StringUtils stringUtils,
        final EnglishGrammarUtils englishGrammarUtils)
    {
        StringBuffer t_sbResult = new StringBuffer();

        Object[] t_aRepository =
            new Object[]{
                stringUtils.normalize(repository, '_')};

        Object[] t_aPackageName = new Object[]{packageName};

        MessageFormat t_Formatter = new MessageFormat(header);
        t_sbResult.append(t_Formatter.format(t_aRepository));

        t_Formatter = new MessageFormat(packageDeclaration);
        t_sbResult.append(t_Formatter.format(t_aPackageName));

        StringBuffer t_sbDAOReferences = new StringBuffer();
        MessageFormat t_DAOReferenceFormatter =
            new MessageFormat(daoReference);

        StringBuffer t_sbDAOMethods = new StringBuffer();
        MessageFormat t_DAOMethodFormatter =
            new MessageFormat(daoMethods);

        Iterator t_itTables = tables.iterator();

        if  (t_itTables.hasNext()) 
        {
            t_sbResult.append(projectImportsJavadoc);
        }

        MessageFormat t_ImportFormatter =
            new MessageFormat(projectImports);

        while  (t_itTables.hasNext()) 
        {
            String t_strTable = (String) t_itTables.next();

            if  (t_strTable != null)
            {
                String t_strFormattedTable =
                    stringUtils.capitalize(
                        englishGrammarUtils.getSingular(
                            t_strTable.toLowerCase()),
                        '_');

                Object[] t_aTable =
                    new Object[]
                    {
                        t_strFormattedTable
                    };

                t_sbResult.append(
                    t_ImportFormatter.format(
                        new Object[]
                        {
                            packageName,
                            t_strFormattedTable
                        }));

                t_sbDAOReferences.append(
                    t_DAOReferenceFormatter.format(
                        t_aTable));

                t_sbDAOMethods.append(
                    t_DAOMethodFormatter.format(
                        t_aTable));
            }
        }

        t_sbResult.append(jdkImports);

        t_Formatter = new MessageFormat(javadoc);
        t_sbResult.append(t_Formatter.format(t_aRepository));

        t_Formatter = new MessageFormat(classDefinition);
        t_sbResult.append(t_Formatter.format(t_aRepository));

        t_sbResult.append(classStart);

        t_sbResult.append(singletonBody);

        t_sbResult.append(t_sbDAOReferences);

        t_sbResult.append(t_sbDAOMethods);

        t_sbResult.append(classEnd);

        return t_sbResult.toString();
    }
}
