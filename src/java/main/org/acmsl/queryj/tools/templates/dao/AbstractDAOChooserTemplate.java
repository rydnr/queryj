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
 * Description: Contains the required subtemplates used to generate DAOChooser
 *              class according to database metadata.
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
package org.acmsl.queryj.tools.templates.dao;

/*
 * Importing project-specific classes.
 */
import org.acmsl.queryj.tools.templates.AbstractTemplate;

/*
 * Importing Ant classes.
 */
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;

/*
 * Importing some JDK classes.
 */
import java.util.ArrayList;
import java.util.List;

/**
 * Contains the required subtemplates to generate DAOChooser class
 * according to database metadata.
 * @author <a href="mailto:jsanleandro@yahoo.es"
 *         >Jose San Leandro</a>
 * @version $Revision$
 */
public abstract class AbstractDAOChooserTemplate
    extends  AbstractTemplate
{
    /**
     * The header.
     */
    private String m__strHeader;

    /**
     * The package declaration.
     */
    private String m__strPackageDeclaration;

    /**
     * The package name.
     */
    private String m__strPackageName;

    /**
     * The repository.
     */
    private String m__strRepository;

    /**
     * The project import Javadoc.
     */
    private String m__strProjectImportsJavadoc;

    /**
     * The project import statements.
     */
    private String m__strProjectImports;

    /**
     * The JDK import statements.
     */
    private String m__strJDKImports;

    /**
     * The Logging import statements.
     */
    private String m__strLoggingImports;

    /**
     * The class Javadoc.
     */
    private String m__strJavadoc;

    /**
     * The class definition.
     */
    private String m__strClassDefinition;

    /**
     * The class start.
     */
    private String m__strClassStart;

    /**
     * The properties keys.
     */
    private String m__strPropertiesKeys;

    /**
     * The properties reference
     */
    private String m__strPropertiesReference;

    /**
     * The singleton body.
     */
    private String m__strSingletonBody;

    /**
     * The Properties accessors.
     */
    private String m__strPropertiesAccessors;

    /**
     * The helper methods.
     */
    private String m__strHelperMethods;

    /**
     * The getDAOFactory methods.
     */
    private String m__strGetDAOFactoryMethods;

    /**
     * The class end.
     */
    private String m__strClassEnd;

    /**
     * The table list.
     */
    private List m__lTables;

    /**
     * Builds an <code>AbstractDAOChooserTemplate</code> using given
     * information.
     * @param header the header.
     * @param packageDeclaration the package declaration.
     * @param packageName the package name.
     * @param repository the repository.
     * @param jdkImports the JDK imports.
     * @param loggingImports the logging imports.
     * @param javadoc the class Javadoc.
     * @param classDefinition the class definition.
     * @param classStart the class start.
     * @param propertiesKeys the properties' keys template.
     * @param propertiesReference the properties reference template.
     * @param singletonBody the singleton body.
     * @param propertiesAccessors the properties accessors template.
     * @param helperMethods the helper methods.
     * @param getDAOFactoryMethods the getDAOFactory methods.
     * @param classEnd the class end.
     * @param project the project, for logging purposes.
     * @param task the task, for logging purposes.
     */
    public AbstractDAOChooserTemplate(
        final String header,
        final String packageDeclaration,
        final String packageName,
        final String repository,
        final String jdkImports,
        final String loggingImports,
        final String javadoc,
        final String classDefinition,
        final String classStart,
        final String propertiesKeys,
        final String propertiesReference,
        final String singletonBody,
        final String propertiesAccessors,
        final String helperMethods,
        final String getDAOFactoryMethods,
        final String classEnd,
        final Project project,
        final Task task)
    {
        super(project, task);
        immutableSetHeader(header);
        immutableSetPackageDeclaration(packageDeclaration);
        immutableSetPackageName(packageName);
        immutableSetRepository(repository);
        immutableSetJdkImports(jdkImports);
        immutableSetLoggingImports(loggingImports);
        immutableSetJavadoc(javadoc);
        immutableSetClassDefinition(classDefinition);
        immutableSetClassStart(classStart);
        immutableSetPropertiesKeys(propertiesKeys);
        immutableSetPropertiesReference(propertiesReference);
        immutableSetSingletonBody(singletonBody);
        immutableSetPropertiesAccessors(propertiesAccessors);
        immutableSetHelperMethods(helperMethods);
        immutableSetGetDAOFactoryMethods(getDAOFactoryMethods);
        immutableSetClassEnd(classEnd);
        immutableSetTables(new ArrayList());
    }

    /**
     * Specifies the header.
     * @param header the new header.
     */
    private void immutableSetHeader(final String header)
    {
        m__strHeader = header;
    }

    /**
     * Specifies the header.
     * @param header the new header.
     */
    protected void setHeader(final String header)
    {
        immutableSetHeader(header);
    }

    /**
     * Retrieves the header.
     * @return such information.
     */
    public String getHeader() 
    {
        return m__strHeader;
    }

    /**
     * Specifies the package declaration.
     * @param packageDeclaration the new package declaration.
     */
    private void immutableSetPackageDeclaration(final String packageDeclaration)
    {
        m__strPackageDeclaration = packageDeclaration;
    }

    /**
     * Specifies the package declaration.
     * @param packageDeclaration the new package declaration.
     */
    protected void setPackageDeclaration(final String packageDeclaration)
    {
        immutableSetPackageDeclaration(packageDeclaration);
    }

    /**
     * Retrieves the package declaration.
     * @return such information.
     */
    public String getPackageDeclaration() 
    {
        return m__strPackageDeclaration;
    }

    /**
     * Specifies the package name.
     * @param packageName the new package name.
     */
    private void immutableSetPackageName(final String packageName)
    {
        m__strPackageName = packageName;
    }

    /**
     * Specifies the package name.
     * @param packageName the new package name.
     */
    protected void setPackageName(final String packageName)
    {
        immutableSetPackageName(packageName);
    }

    /**
     * Retrieves the package name.
     * @return such information.
     */
    public String getPackageName() 
    {
        return m__strPackageName;
    }

    /**
     * Specifies the repository.
     * @param repository the new repository.
     */
    private void immutableSetRepository(final String repository)
    {
        m__strRepository = repository;
    }

    /**
     * Specifies the repository.
     * @param repository the new repository.
     */
    protected void setRepository(final String repository)
    {
        immutableSetRepository(repository);
    }

    /**
     * Retrieves the repository.
     * @return such information.
     */
    public String getRepository() 
    {
        return m__strRepository;
    }

    /**
     * Specifies the JDK imports.
     * @param jdkImports the new JDK imports.
     */
    private void immutableSetJdkImports(final String jdkImports)
    {
        m__strJDKImports = jdkImports;
    }

    /**
     * Specifies the JDK imports.
     * @param jdkImports the new JDK imports.
     */
    protected void setJdkImports(final String jdkImports)
    {
        immutableSetJdkImports(jdkImports);
    }

    /**
     * Retrieves the JDK imports.
     * @return such information.
     */
    public String getJdkImports() 
    {
        return m__strJDKImports;
    }

    /**
     * Specifies the logging imports.
     * @param loggingImports the new logging imports.
     */
    private void immutableSetLoggingImports(final String loggingImports)
    {
        m__strLoggingImports = loggingImports;
    }

    /**
     * Specifies the logging imports.
     * @param loggingImports the new logging imports.
     */
    protected void setLoggingImports(final String loggingImports)
    {
        immutableSetLoggingImports(loggingImports);
    }

    /**
     * Retrieves the logging imports.
     * @return such information.
     */
    public String getLoggingImports() 
    {
        return m__strLoggingImports;
    }

    /**
     * Specifies the javadoc.
     * @param javadoc the new javadoc.
     */
    private void immutableSetJavadoc(final String javadoc)
    {
        m__strJavadoc = javadoc;
    }

    /**
     * Specifies the javadoc.
     * @param javadoc the new javadoc.
     */
    protected void setJavadoc(final String javadoc)
    {
        immutableSetJavadoc(javadoc);
    }

    /**
     * Retrieves the javadoc.
     * @return such information.
     */
    public String getJavadoc() 
    {
        return m__strJavadoc;
    }

    /**
     * Specifies the class definition.
     * @param classDefinition the new class definition.
     */
    private void immutableSetClassDefinition(final String classDefinition)
    {
        m__strClassDefinition = classDefinition;
    }

    /**
     * Specifies the class definition.
     * @param classDefinition the new class definition.
     */
    protected void setClassDefinition(final String classDefinition)
    {
        immutableSetClassDefinition(classDefinition);
    }

    /**
     * Retrieves the class definition.
     * @return such information.
     */
    public String getClassDefinition() 
    {
        return m__strClassDefinition;
    }

    /**
     * Specifies the class start.
     * @param classStart the new class start.
     */
    private void immutableSetClassStart(final String classStart)
    {
        m__strClassStart = classStart;
    }

    /**
     * Specifies the class start.
     * @param classStart the new class start.
     */
    protected void setClassStart(final String classStart)
    {
        immutableSetClassStart(classStart);
    }

    /**
     * Retrieves the class start.
     * @return such information.
     */
    public String getClassStart() 
    {
        return m__strClassStart;
    }

    /**
     * Specifies the properties keys.
     * @param propertiesKeys the new properties keys.
     */
    private void immutableSetPropertiesKeys(final String propertiesKeys)
    {
        m__strPropertiesKeys = propertiesKeys;
    }

    /**
     * Specifies the properties keys.
     * @param propertiesKeys the new properties keys.
     */
    protected void setPropertiesKeys(final String propertiesKeys)
    {
        immutableSetPropertiesKeys(propertiesKeys);
    }

    /**
     * Retrieves the properties keys.
     * @return such information.
     */
    public String getPropertiesKeys() 
    {
        return m__strPropertiesKeys;
    }

    /**
     * Specifies the properties reference.
     * @param propertiesReference the new properties reference.
     */
    private void immutableSetPropertiesReference(final String propertiesReference)
    {
        m__strPropertiesReference = propertiesReference;
    }

    /**
     * Specifies the properties reference.
     * @param propertiesReference the new properties reference.
     */
    protected void setPropertiesReference(final String propertiesReference)
    {
        immutableSetPropertiesReference(propertiesReference);
    }

    /**
     * Retrieves the properties reference.
     * @return such information.
     */
    public String getPropertiesReference() 
    {
        return m__strPropertiesReference;
    }

    /**
     * Specifies the singleton body.
     * @param singletonBody the new singleton body.
     */
    private void immutableSetSingletonBody(final String singletonBody)
    {
        m__strSingletonBody = singletonBody;
    }

    /**
     * Specifies the singleton body.
     * @param singletonBody the new singleton body.
     */
    protected void setSingletonBody(final String singletonBody)
    {
        immutableSetSingletonBody(singletonBody);
    }

    /**
     * Retrieves the singleton body.
     * @return such information.
     */
    public String getSingletonBody() 
    {
        return m__strSingletonBody;
    }

    /**
     * Specifies the properties accessors.
     * @param propertiesAccessors the new properties accessors.
     */
    private void immutableSetPropertiesAccessors(final String propertiesAccessors)
    {
        m__strPropertiesAccessors = propertiesAccessors;
    }

    /**
     * Specifies the properties accessors.
     * @param propertiesAccessors the new properties accessors.
     */
    protected void setPropertiesAccessors(final String propertiesAccessors)
    {
        immutableSetPropertiesAccessors(propertiesAccessors);
    }

    /**
     * Retrieves the properties accessors.
     * @return such information.
     */
    public String getPropertiesAccessors() 
    {
        return m__strPropertiesAccessors;
    }

    /**
     * Specifies the helper methods.
     * @param helperMethods the new helper methods.
     */
    private void immutableSetHelperMethods(final String helperMethods)
    {
        m__strHelperMethods = helperMethods;
    }

    /**
     * Specifies the helper methods.
     * @param helperMethods the new helper methods.
     */
    protected void setHelperMethods(final String helperMethods)
    {
        immutableSetHelperMethods(helperMethods);
    }

    /**
     * Retrieves the helper methods.
     * @return such information.
     */
    public String getHelperMethods() 
    {
        return m__strHelperMethods;
    }

    /**
     * Specifies the getDAOFactory methods.
     * @param getDAOFactoryMethods the new getDAOFactory methods.
     */
    private void immutableSetGetDAOFactoryMethods(final String getDAOFactoryMethods)
    {
        m__strGetDAOFactoryMethods = getDAOFactoryMethods;
    }

    /**
     * Specifies the getDAOFactory methods.
     * @param getDAOFactoryMethods the new getDAOFactory methods.
     */
    protected void setGetDAOFactoryMethods(final String getDAOFactoryMethods)
    {
        immutableSetGetDAOFactoryMethods(getDAOFactoryMethods);
    }

    /**
     * Retrieves the getDAOFactory methods.
     * @return such information.
     */
    public String getGetDAOFactoryMethods() 
    {
        return m__strGetDAOFactoryMethods;
    }

    /**
     * Specifies the class end.
     * @param classEnd the new class end.
     */
    private void immutableSetClassEnd(final String classEnd)
    {
        m__strClassEnd = classEnd;
    }

    /**
     * Specifies the class end.
     * @param classEnd the new class end.
     */
    protected void setClassEnd(final String classEnd)
    {
        immutableSetClassEnd(classEnd);
    }

    /**
     * Retrieves the class end.
     * @return such information.
     */
    public String getClassEnd() 
    {
        return m__strClassEnd;
    }

    /**
     * Specifies the tables.
     * @param tables the tables.
     */
    private void immutableSetTables(final List tables)
    {
        m__lTables = tables;
    }

    /**
     * Specifies the tables.
     * @param tables the tables.
     */
    protected void setTables(final List tables)
    {
        immutableSetTables(tables);
    }

    /**
     * Retrieves the tables.
     * @return such collection.
     */
    public List getTables()
    {
        return m__lTables;
    }

    /**
     * Adds a new table.
     * @param table the new table.
     */
    public void addTable(final String table)
    {
        List t_lTables = getTables();

        if  (t_lTables != null) 
        {
            t_lTables.add(table);
        }
    }

    /**
     * Builds the header for logging purposes.
     * @return such header.
     */
    protected String buildHeader()
    {
        return "Generating DAOChooser.";
    }
}
