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
           >Jose San Leandro</a>
 * @version $Revision$
 */
public abstract class TableRepositoryTemplate
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
        + " * Description: Contains all tables belonging to {0} repository.\n"
        + " *\n"
        + " * Last modified by: $" + "Author: $ at $" + "Date: $\n"
        + " *\n"
        + " * File version: $" + "Revision: $\n"
        + " *\n"
        + " * Project version: $" + "Name: $\n"
        + " *\n"
        + " * $" + "Id: $\n"
        + " *\n"
        + " */\n";

    /**
     * The package declaration.
     */
    public static final String PACKAGE_DECLARATION = "package {0};\n"; // package

    /**
     * The project imports.
     */
    public static final String DEFAULT_PROJECT_IMPORTS_JAVADOC =
          "\n"
        + "/*\n"
        + " * Importing project-specific classes.\n"
        + " */\n";

    /**
     * The project imports.
     */
    public static final String PROJECT_IMPORTS =
        "import {0}.{1}Table;\n"; // package.table

    /**
     * The ACM-SL imports.
     */
    public static final String ACMSL_IMPORTS =
          "\n"
        + "/*\n"
        + " * Importing some ACM-SL classes.\n"
        + " */\n"
        + "import org.acmsl.queryj.Table;\n";

    /**
     * The default class Javadoc.
     */
    public static final String DEFAULT_JAVADOC =
          "\n"
        + "/**\n"
        + " * Contains all tables belonging to {0} repository.\n" // repository
        + " * @author <a href=\"http://queryj.sourceforge.net\">QueryJ</a>\n"
        + " * @version $" + "Revision: $\n"
        + " */\n";

    /**
     * The class definition.
     */
    public static final String CLASS_DEFINITION =
         "public interface {0}\n"; // repository

    /**
     * The class start.
     */
    public static final String DEFAULT_CLASS_START = "{";

    /**
     * The default table Javadoc.
     */
    public static final String DEFAULT_TABLE_JAVADOC =
          "\n"
        + "    /**\n"
        + "     * The {0} table.\n" // table
        + "     */\n";

    /**
     * The table definition.
     */
    public static final String TABLE_DEFINITION =
        "    public static final {1}Table {0} = {1}Table.getInstance();\n";

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
     * The ACM-SL import statements.
     */
    private String m__strAcmslImports;

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
     * The table Javadoc.
     */
    private String m__strTableJavadoc;

    /**
     * The table definition.
     */
    private String m__strTableDefinition;

    /**
     * The class end.
     */
    private String m__strClassEnd;

    /**
     * The table list.
     */
    private List m__lTables;

    /**
     * Builds a TableRepositoryTemplate using given information.
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
     */
    public TableRepositoryTemplate(
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
        final String classEnd)
    {
        setHeader(header);
        setPackageDeclaration(packageDeclaration);
        setPackageName(packageName);
        setRepository(repository);
        setProjectImportsJavadoc(importsJavadoc);
        setProjectImports(projectImports);
        setAcmslImports(acmslImports);
        setJavadoc(javadoc);
        setClassDefinition(classDefinition);
        setClassStart(classStart);
        setTableJavadoc(tableJavadoc);
        setTableDefinition(tableDefinition);
        setClassEnd(classEnd);
        setTables(new ArrayList());
    }

    /**
     * Builds a TableRepositoryTemplate using given information.
     * @param packageName the package name.
     * @param repository the repository.
     */
    public TableRepositoryTemplate(
        final String packageName,
        final String repository)
    {
        this(
            DEFAULT_HEADER,
            PACKAGE_DECLARATION,
            packageName,
            repository,
            DEFAULT_PROJECT_IMPORTS_JAVADOC,
            PROJECT_IMPORTS,
            ACMSL_IMPORTS,
            DEFAULT_JAVADOC,
            CLASS_DEFINITION,
            DEFAULT_CLASS_START,
            DEFAULT_TABLE_JAVADOC,
            TABLE_DEFINITION,
            DEFAULT_CLASS_END);
    }

    /**
     * Specifies the header.
     * @param header the new header.
     */
    protected void setHeader(final String header)
    {
        m__strHeader = header;
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
    protected void setPackageDeclaration(final String packageDeclaration)
    {
        m__strPackageDeclaration = packageDeclaration;
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
    protected void setPackageName(final String packageName)
    {
        m__strPackageName = packageName;
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
    protected void setRepository(final String repository)
    {
        m__strRepository = repository;
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
     * @param importJavadoc the new project imports Javadoc.
     */
    protected void setProjectImportsJavadoc(final String importJavadoc)
    {
        m__strProjectImportsJavadoc = importJavadoc;
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
    protected void setProjectImports(final String projectImports)
    {
        m__strProjectImports = projectImports;
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
     * Specifies the ACM-SL imports.
     * @param acmslImports the new ACM-SL imports.
     */
    protected void setAcmslImports(final String acmslImports)
    {
        m__strAcmslImports = acmslImports;
    }

    /**
     * Retrieves the ACM-SL imports.
     * @return such information.
     */
    public String getAcmslImports() 
    {
        return m__strAcmslImports;
    }

    /**
     * Specifies the javadoc.
     * @param javadoc the new javadoc.
     */
    protected void setJavadoc(final String javadoc)
    {
        m__strJavadoc = javadoc;
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
    protected void setClassDefinition(final String classDefinition)
    {
        m__strClassDefinition = classDefinition;
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
    protected void setClassStart(final String classStart)
    {
        m__strClassStart = classStart;
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
     * Specifies the table Javadoc.
     * @param tableJavadoc the new table Javadoc.
     */
    protected void setTableJavadoc(final String tableJavadoc)
    {
        m__strTableJavadoc = tableJavadoc;
    }

    /**
     * Retrieves the table Javadoc.
     * @return such information.
     */
    public String getTableJavadoc() 
    {
        return m__strTableJavadoc;
    }

    /**
     * Specifies the table definition.
     * @param tableDefinition the new table definition.
     */
    protected void setTableDefinition(final String tableDefinition)
    {
        m__strTableDefinition = tableDefinition;
    }

    /**
     * Retrieves the table definition.
     * @return such information.
     */
    public String getTableDefinition() 
    {
        return m__strTableDefinition;
    }

    /**
     * Specifies the class end.
     * @param classEnd the new class end.
     */
    protected void setClassEnd(final String classEnd)
    {
        m__strClassEnd = classEnd;
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
    protected void setTables(final List tables)
    {
        m__lTables = tables;
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
                                    t_StringUtils.capitalize(
                                        t_strTable.toLowerCase(),
                                        '_')}));
                    }
                }
            }

            t_sbResult.append(getAcmslImports());

            t_Formatter = new MessageFormat(getJavadoc());
            t_sbResult.append(t_Formatter.format(t_aRepository));

            t_Formatter = new MessageFormat(getClassDefinition());
            t_sbResult.append(t_Formatter.format(t_aRepository));

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
                                    t_StringUtils.capitalize(
                                        t_strTable.toLowerCase(),
                                        '_')}));
                    }
                }
            }

            t_sbResult.append(getClassEnd());
        }

        return t_sbResult.toString();
    }
}
