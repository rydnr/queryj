/*
                        QueryJ

    Copyright (C) 2002  Jose San Leandro Armendáriz
                        jsanleandro@yahoo.es
                        chousz@yahoo.com

    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    version 2 of the License, or (at your option) any later version.

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
                    Urb. Valdecabañas
                    Boadilla del monte
                    28660 Madrid
                    Spain

 ******************************************************************************
 *
 * Filename: $RCSfile$
 *
 * Author: Jose San Leandro Armendáriz
 *
 * Description: Is able to generate DataAccessManager facade class according
 *              to database metadata.
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
 * Is able to generate DataAccessManager facade class according to database
 * metadata.
 * @author <a href="mailto:jsanleandro@yahoo.es"
           >Jose San Leandro</a>
 * @version $Revision$
 */
public abstract class DataAccessManagerTemplate
{
    /**
     * The default header.
     */
    public static final String DEFAULT_HEADER =
          "/*\n"
        + "                        QueryJ\n"
        + "\n"
        + "    Copyright (C) 2002  Jose San Leandro Armendáriz\n"
        + "                        jsanleandro@yahoo.es\n"
        + "                        chousz@yahoo.com\n"
        + "\n"
        + "    This library is free software; you can redistribute it and/or\n"
        + "    modify it under the terms of the GNU General Public\n"
        + "    License as published by the Free Software Foundation; either\n"
        + "    version 2 of the License, or (at your option) any later "
        + "version.\n"
        + "\n"
        + "    This library is distributed in the hope that it will be "
        + "useful,\n"
        + "    but WITHOUT ANY WARRANTY; without even the implied warranty "
        + "of\n"
        + "    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the "
        + "GNU\n"
        + "    General Public License for more details.\n"
        + "\n"
        + "    You should have received a copy of the GNU General Public\n"
        + "    License along with this library; if not, write to the Free "
        + "Software\n"
        + "    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  "
        + "02111-1307  USA\n"
        + "\n"
        + "    Thanks to ACM S.L. for distributing this library under the GPL "
        + "license.\n"
        + "    Contact info: jsanleandro@yahoo.es\n"
        + "    Postal Address: c/Playa de Lagoa, 1\n"
        + "                    Urb. Valdecabañas\n"
        + "                    Boadilla del monte\n"
        + "                    28660 Madrid\n"
        + "                    Spain\n"
        + "\n"
        + " *****************************************************************"
        + "*************\n"
        + " *\n"
        + " * Filename: $" + "RCSfile: $\n"
        + " *\n"
        + " * Author: QueryJ\n"
        + " *\n"
        + " * Description: Facade to facilitate access to all DAOs in\n"
        + " *              {0} model.\n"
        + " *\n"
        + " * Last modified by: $" + "Author: $ at $" + "Date: $\n"
        + " *\n"
        + " * File version: $" + "Revision: $\n"
        + " *\n"
        + " * Project version: $" + "Name: $\n"
        + " *\n"
        + " * $" + "Id: $\n"
        + " */\n";

    /**
     * The package declaration.
     */
    public static final String DEFAULT_PACKAGE_DECLARATION =
        "package {0};\n\n"; // package

    /**
     * The project imports.
     */
    public static final String DEFAULT_PROJECT_IMPORTS_JAVADOC =
          "/*\n"
        + " * Importing project-specific classes.\n"
        + " */\n";

    /**
     * The project imports.
     */
    public static final String DEFAULT_PROJECT_IMPORTS =
          "import {0}.{1}DAO;\n" // package - table
        + "import {0}.{1}DAOFactory;\n";

    /**
     * The JDK imports.
     */
    public static final String DEFAULT_JDK_IMPORTS =
          "/*\n"
        + " * Importing some JDK classes.\n"
        + " */\n"
        + "import java.lang.ref.WeakReference;\n\n";

    /**
     * The default class Javadoc.
     */
    public static final String DEFAULT_JAVADOC =
          "/**\n"
        + " * Facade to facilitate access to all DAOs in {0} model.\n" // repository
        + " * @author <a href=\"http://queryj.sourceforge.net\">QueryJ</a>\n"
        + " * @version $" + "Revision: $\n"
        + " */\n";

    /**
     * The class definition.
     */
    public static final String DEFAULT_CLASS_DEFINITION =
         "public abstract class DataAccessManager\n"; // repository

    /**
     * The class start.
     */
    public static final String DEFAULT_CLASS_START =
          "{\n"
        + "    /**\n"
        + "     * Singleton implemented as a weak reference.\n"
        + "     */\n"
        + "    private static WeakReference singleton;\n\n";

    /**
     * The DAO reference.
     */
    public static final String DEFAULT_DAO_REFERENCE =
         "    /**\n"
       + "     * The {0} DAO reference (cached whereas Manager instance is not\n"
       + "     * garbage collected).\n"
       + "     */\n"
       + "    private {0}DAO m__{0}DAO;\n\n";

    /**
     * The class singleton logic.
     */
    public static final String DEFAULT_SINGLETON_BODY =
          "    /**\n"
        + "     * Protected constructor to avoid accidental instantiation.\n"
        + "     * @param alias the table alias.\n"
        + "     */\n"
        + "    protected DataAccessManager() {};\n\n"
        + "    /**\n"
        + "     * Specifies a new weak reference.\n"
        + "     * @param manager the new manager instance.\n"
        + "     */\n"
        + "    protected static void setReference(DataAccessManager manager)\n" // table
        + "    {\n"
        + "        singleton = new WeakReference(manager);\n"
        + "    }\n\n"
        + "    /**\n"
        + "     * Retrieves the weak reference.\n"
        + "     * @return such reference.\n"
        + "     */\n"
        + "    protected static WeakReference getReference()\n"
        + "    {\n"
        + "        return singleton;\n"
        + "    }\n\n"
        + "    /**\n"
        + "     * Retrieves a DataAccessManager instance.\n"
        + "     * @return such instance.\n"
        + "     */\n"
        + "    public static DataAccessManager getInstance()\n"
        + "    {\n"
        + "        DataAccessManager result = null;\n\n"
        + "        WeakReference reference = getReference();\n\n"
        + "        if  (reference != null) \n"
        + "        {\n"
        + "            result = (DataAccessManager) reference.get();\n"
        + "        }\n\n"
        + "        if  (result == null) \n"
        + "        {\n"
        + "            result = new DataAccessManager() {};\n\n"
        + "            setReference(result);\n"
        + "        }\n\n"
        + "        return result;\n"
        + "    }\n\n";

    /**
     * The DAO methods.
     */
    public static final String DEFAULT_DAO_METHODS =
         "    /**\n"
       + "     * Specifies the new {0} DAO reference to keep.\n"
       + "     * @param dao the new DAO.\n"
       + "     */\n"
       + "    protected void set{0}DAOReference({0}DAO dao)\n"
       + "    '{'\n"
       + "        m__{0}DAO = dao;\n"
       + "    '}'\n\n"
       + "    /**\n"
       + "     * Retrieves the existing {0} DAO reference.\n"
       + "     * @return a DAO instance for accessing {0} information.\n"
       + "     */\n"
       + "    protected {0}DAO get{0}DAOReference()\n"
       + "    '{'\n"
       + "        return m__{0}DAO;\n"
       + "    '}'\n\n"
       + "    /**\n"
       + "     * Retrieves a {0} DAO.\n"
       + "     * @return a DAO instance for accessing {0} information.\n"
       + "     */\n"
       + "    public {0}DAO get{0}DAO()\n"
       + "    '{'\n"
       + "        {0}DAO result = get{0}DAOReference();\n\n"
       + "        if  (result == null)\n"
       + "        '{'\n"
       + "            {0}DAOFactory t_{0}DAOFactory =\n"
       + "                {0}DAOFactory.getInstance();\n\n"
       + "            if  (t_{0}DAOFactory != null)\n"
       + "            '{'\n"
       + "                result = t_{0}DAOFactory.create{0}DAO();\n"
       + "                set{0}DAOReference(result);\n"
       + "            '}'\n"
       + "        '}'\n\n"
       + "        return result;\n"
       + "    '}'\n\n";

    /**
     * The default class end.
     */
    public static final String DEFAULT_CLASS_END = "}\n";

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
     * The DAO reference
     */
    private String m__strDAOReference;

    /**
     * The singleton body.
     */
    private String m__strSingletonBody;

    /**
     * The DAO methods.
     */
    private String m__strDAOMethods;

    /**
     * The class end.
     */
    private String m__strClassEnd;

    /**
     * The table list.
     */
    private List m__lTables;

    /**
     * Builds a DataAccessManagerTemplate using given information.
     * @param header the header.
     * @param packageDeclaration the package declaration.
     * @param packageName the package name.
     * @param repository the repository.
     * @param importsJavadoc the project imports javadoc.
     * @param projectImports the project imports.
     * @param jdkImports the JDK imports.
     * @param javadoc the class Javadoc.
     * @param classDefinition the class definition.
     * @param classStart the class start.
     * @param daoReference the DAO reference.
     * @param singletonBody the singleton body.
     * @param daoMethods the DAO methods.
     * @param classEnd the class end.
     */
    public DataAccessManagerTemplate(
        String header,
        String packageDeclaration,
        String packageName,
        String repository,
        String importsJavadoc,
        String projectImports,
        String jdkImports,
        String javadoc,
        String classDefinition,
        String classStart,
        String daoReference,
        String singletonBody,
        String daoMethods,
        String classEnd)
    {
        inmutableSetHeader(header);
        inmutableSetPackageDeclaration(packageDeclaration);
        inmutableSetPackageName(packageName);
        inmutableSetRepository(repository);
        inmutableSetProjectImportsJavadoc(importsJavadoc);
        inmutableSetProjectImports(projectImports);
        inmutableSetJdkImports(jdkImports);
        inmutableSetJavadoc(javadoc);
        inmutableSetClassDefinition(classDefinition);
        inmutableSetClassStart(classStart);
        inmutableSetDAOReference(daoReference);
        inmutableSetSingletonBody(singletonBody);
        inmutableSetDAOMethods(daoMethods);
        inmutableSetClassEnd(classEnd);
        inmutableSetTables(new ArrayList());
    }

    /**
     * Builds a DataAccessManagerTemplate using given information.
     * @param packageName the package name.
     * @param repository the repository.
     */
    public DataAccessManagerTemplate(
        String packageName,
        String repository)
    {
        this(
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
            DEFAULT_CLASS_END);
    }


    /**
     * Specifies the header.
     * @param header the new header.
     */
    private void inmutableSetHeader(String header)
    {
        m__strHeader = header;
    }

    /**
     * Specifies the header.
     * @param header the new header.
     */
    protected void setHeader(String header)
    {
        inmutableSetHeader(header);
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
    private void inmutableSetPackageDeclaration(String packageDeclaration)
    {
        m__strPackageDeclaration = packageDeclaration;
    }

    /**
     * Specifies the package declaration.
     * @param packageDeclaration the new package declaration.
     */
    protected void setPackageDeclaration(String packageDeclaration)
    {
        inmutableSetPackageDeclaration(packageDeclaration);
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
    private void inmutableSetPackageName(String packageName)
    {
        m__strPackageName = packageName;
    }

    /**
     * Specifies the package name.
     * @param packageName the new package name.
     */
    protected void setPackageName(String packageName)
    {
        inmutableSetPackageName(packageName);
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
    private void inmutableSetRepository(String repository)
    {
        m__strRepository = repository;
    }

    /**
     * Specifies the repository.
     * @param repository the new repository.
     */
    protected void setRepository(String repository)
    {
        inmutableSetRepository(repository);
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
     * Specifies the project imports Javadoc.
     * @param projectImports the new project imports Javadoc.
     */
    private void inmutableSetProjectImportsJavadoc(String importJavadoc)
    {
        m__strProjectImportsJavadoc = importJavadoc;
    }

    /**
     * Specifies the project imports Javadoc.
     * @param projectImports the new project imports Javadoc.
     */
    protected void setProjectImportsJavadoc(String importJavadoc)
    {
        inmutableSetProjectImportsJavadoc(importJavadoc);
    }

    /**
     * Retrieves the project imports Javadoc.
     * @return such information.
     */
    public String getProjectImportsJavadoc() 
    {
        return m__strProjectImportsJavadoc;
    }

    /**
     * Specifies the project imports.
     * @param projectImports the new project imports.
     */
    private void inmutableSetProjectImports(String projectImports)
    {
        m__strProjectImports = projectImports;
    }

    /**
     * Specifies the project imports.
     * @param projectImports the new project imports.
     */
    protected void setProjectImports(String projectImports)
    {
        inmutableSetProjectImports(projectImports);
    }

    /**
     * Retrieves the project imports.
     * @return such information.
     */
    public String getProjectImports() 
    {
        return m__strProjectImports;
    }

    /**
     * Specifies the JDK imports.
     * @param jdkImports the new JDK imports.
     */
    private void inmutableSetJdkImports(String jdkImports)
    {
        m__strJDKImports = jdkImports;
    }

    /**
     * Specifies the JDK imports.
     * @param jdkImports the new JDK imports.
     */
    protected void setJdkImports(String jdkImports)
    {
        inmutableSetJdkImports(jdkImports);
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
     * Specifies the javadoc.
     * @param javadoc the new javadoc.
     */
    private void inmutableSetJavadoc(String javadoc)
    {
        m__strJavadoc = javadoc;
    }

    /**
     * Specifies the javadoc.
     * @param javadoc the new javadoc.
     */
    protected void setJavadoc(String javadoc)
    {
        inmutableSetJavadoc(javadoc);
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
    private void inmutableSetClassDefinition(String classDefinition)
    {
        m__strClassDefinition = classDefinition;
    }

    /**
     * Specifies the class definition.
     * @param classDefinition the new class definition.
     */
    protected void setClassDefinition(String classDefinition)
    {
        inmutableSetClassDefinition(classDefinition);
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
    private void inmutableSetClassStart(String classStart)
    {
        m__strClassStart = classStart;
    }

    /**
     * Specifies the class start.
     * @param classStart the new class start.
     */
    protected void setClassStart(String classStart)
    {
        inmutableSetClassStart(classStart);
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
     * Specifies the DAO reference.
     * @param dAOReference the new DAO reference.
     */
    private void inmutableSetDAOReference(String daoReference)
    {
        m__strDAOReference = daoReference;
    }

    /**
     * Specifies the DAO reference.
     * @param daoReference the new DAO reference.
     */
    protected void setDAOReference(String daoReference)
    {
        inmutableSetDAOReference(daoReference);
    }

    /**
     * Retrieves the DAO reference.
     * @return such information.
     */
    public String getDAOReference() 
    {
        return m__strDAOReference;
    }

    /**
     * Specifies the singleton body.
     * @param singletonBody the new singleton body.
     */
    private void inmutableSetSingletonBody(String singletonBody)
    {
        m__strSingletonBody = singletonBody;
    }

    /**
     * Specifies the singleton body.
     * @param singletonBody the new singleton body.
     */
    protected void setSingletonBody(String singletonBody)
    {
        inmutableSetSingletonBody(singletonBody);
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
     * Specifies the DAO methods.
     * @param dAOMethods the new DAO methods.
     */
    private void inmutableSetDAOMethods(String daoMethods)
    {
        m__strDAOMethods = daoMethods;
    }

    /**
     * Specifies the DAO methods.
     * @param daoMethods the new DAO methods.
     */
    protected void setDAOMethods(String daoMethods)
    {
        inmutableSetDAOMethods(daoMethods);
    }

    /**
     * Retrieves the DAO methods.
     * @return such information.
     */
    public String getDAOMethods() 
    {
        return m__strDAOMethods;
    }

    /**
     * Specifies the class end.
     * @param classEnd the new class end.
     */
    private void inmutableSetClassEnd(String classEnd)
    {
        m__strClassEnd = classEnd;
    }

    /**
     * Specifies the class end.
     * @param classEnd the new class end.
     */
    protected void setClassEnd(String classEnd)
    {
        inmutableSetClassEnd(classEnd);
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
    private void inmutableSetTables(List tables)
    {
        m__lTables = tables;
    }

    /**
     * Specifies the tables.
     * @param tables the tables.
     */
    protected void setTables(List tables)
    {
        inmutableSetTables(tables);
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
    public void addTable(String table)
    {
        List t_lTables = getTables();

        if  (t_lTables != null) 
        {
            t_lTables.add(table);
        }
    }

    /**
     * Retrieves the source code of the generated table repository.
     * @return such source code.
     */
    public String toString()
    {
        StringBuffer t_sbResult = new StringBuffer();

        StringUtils t_StringUtils = StringUtils.getInstance();

        if  (t_StringUtils != null) 
        {
            Object[] t_aRepository =
                new Object[]{
                    t_StringUtils.normalize(getRepository(), '_')};

            Object[] t_aPackageName = new Object[]{getPackageName()};

            MessageFormat t_Formatter = new MessageFormat(getHeader());
            t_sbResult.append(t_Formatter.format(t_aRepository));

            t_Formatter = new MessageFormat(getPackageDeclaration());
            t_sbResult.append(t_Formatter.format(t_aPackageName));

            StringBuffer t_sbDAOReferences = new StringBuffer();
            MessageFormat t_DAOReferenceFormatter =
                new MessageFormat(getDAOReference());

            StringBuffer t_sbDAOMethods = new StringBuffer();
            MessageFormat t_DAOMethodFormatter =
                new MessageFormat(getDAOMethods());

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
                        String t_strFormattedTable =
                            t_StringUtils.capitalize(
                                t_strTable.toLowerCase(),
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
                                    getPackageName(),
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
            }

            t_sbResult.append(getJdkImports());

            t_Formatter = new MessageFormat(getJavadoc());
            t_sbResult.append(t_Formatter.format(t_aRepository));

            t_Formatter = new MessageFormat(getClassDefinition());
            t_sbResult.append(t_Formatter.format(t_aRepository));

            t_sbResult.append(getClassStart());

            t_sbResult.append(getSingletonBody());

            t_sbResult.append(t_sbDAOReferences);

            t_sbResult.append(t_sbDAOMethods);

            t_sbResult.append(getClassEnd());
        }

        return t_sbResult.toString();
    }
}
