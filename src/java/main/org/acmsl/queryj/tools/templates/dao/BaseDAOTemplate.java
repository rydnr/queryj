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
 * Description: Is able to create DAO interfaces for each table in the
 *              persistence model.
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
 * Importing some project-specific classes.
 */
import org.acmsl.queryj.tools.DatabaseMetaDataManager;
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

/*
 * Importing Apache Commons Logging classes.
 */
import org.apache.commons.logging.LogFactory;

/**
 * Is able to create DAO interfaces for each table in the
 * persistence model.
 * @author <a href="mailto:jsanleandro@yahoo.es"
           >Jose San Leandro</a>
 * @version $Revision$
 */
public abstract class BaseDAOTemplate
{
    /**
     * The default header.
     */
    public static final String DEFAULT_HEADER =
          "/*\n"
        + "                        QueryJ\n"
        + "\n"
        + "    Copyright (C) 2002  Jose San Leandro Armendariz\n"
        + "                        jsanleandro@yahoo.es\n"
        + "                        chousz@yahoo.com\n"
        + "\n"
        + "    This library is free software; you can redistribute it and/or\n"
        + "    modify it under the terms of the GNU General Public\n"
        + "    License as published by the Free Software Foundation; either\n"
        + "    version 2 of the License, or any later "
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
        + "                    Urb. Valdecabanas\n"
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
        + " * Description: DAO abstract layer responsible of retrieving\n"
        + "                {0} structures from persistence layers.\n"
         // Table name
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
    public static final String PACKAGE_DECLARATION = "package {0};\n\n"; // package

    /**
     * The ACM-SL imports.
     */
    public static final String ACMSL_IMPORTS =
          "/*\n"
        + " * Importing QueryJ classes.\n"
        + " */\n"
        + "import org.acmsl.queryj.dao.TransactionToken;\n\n"
        + "/*\n"
        + " * Importing ACM-SL Commons classes.\n"
        + " */\n"
        + "import org.acmsl.commons.patterns.dao.DAO;\n"
        + "import org.acmsl.commons.patterns.dao.DataAccessException;\n\n";

    /**
     * The JDK imports.
     */
    public static final String JDK_IMPORTS =
          "/*\n"
        + " * Importing some JDK classes.\n"
        + " */\n"
        + "import java.math.BigDecimal;\n"
        + "import java.util.Calendar;\n"
        + "import java.util.Date;\n\n";

    /**
     * The default class Javadoc.
     */
    public static final String DEFAULT_JAVADOC =
          "/**\n"
        + " *DAO abstract class responsible of retrieving\n"
        + " {0} structures from persistence layers.\n"
         // Table name
        + " * @author <a href=\"http://maven.acm-sl.org/queryj\">QueryJ</a>\n"
        + " * @version $" + "Revision: $\n"
        + " */\n";

    /**
     * The class definition.
     */
    public static final String CLASS_DEFINITION =
          "public interface {0}DAO\n"
        + "    extends  DAO\n";
        // table name

    /**
     * The class start.
     */
    public static final String DEFAULT_CLASS_START =
        "{\n";

    /**
     * The find by primary key method.
     */
    public static final String DEFAULT_FIND_BY_PRIMARY_KEY_METHOD =
          "    /**\n"
        + "     * Loads {0} information from the persistence layer filtering\n"
         // table name
        + "     * by its primary keys."
        + "{1}\n"
         // FIND_BY_PRIMARY_KEY_PK_JAVADOC
        + "     * @param transactionToken needed to use an open connection and\n"
        + "     * see previously uncommited inserts/updates/deletes.\n"
        + "     * @return the information extracted from the persistence layer.\n"
        + "     * @throws DataAccessException if the access to the information fails.\n"
        + "     */\n"
        + "    public {2}ValueObject findByPrimaryKey(\n"
         // java table name
        + "{3}"
         // FIND_BY_PRIMARY_KEY_PK_DECLARATION
        + "        TransactionToken  transactionToken)\n"
        + "      throws DataAccessException;\n\n";

    /**
     * The find-by-primary-key method's primary keys javadoc.
     */
    public static final String DEFAULT_FIND_BY_PRIMARY_KEY_PK_JAVADOC =
        "\n     * @param {0} the {1} value to filter.";
         // java pk - pk

    /**
     * The find-by-primary-key method's primary keys declaration.
     */
    public static final String DEFAULT_FIND_BY_PRIMARY_KEY_PK_DECLARATION =
        "        {0}              {1},\n";
         // pk type - java pk

    /**
     * The store method.
     */
    public static final String DEFAULT_INSERT_METHOD =
          "    /**\n"
        + "     * Persists {0} information."
         // table name
        + "{1}"
         // (optional) pk javadoc
        + "{2}\n"
         // insert parameters javadoc
        + "     * @param transactionToken the transaction boundary.\n"
        + "     * @throws DataAccessException if the access to the information fails.\n"
        + "     */\n"
        + "    public void insert("
        + "{3}"
         // (optional) pk declaration
        + "{4}\n"
         // insert parameters declaration
        + "        TransactionToken transactionToken)\n"
        + "      throws DataAccessException;\n\n";

    /**
     * The insert parameters javadoc.
     */
    public static final String DEFAULT_INSERT_PARAMETERS_JAVADOC =
        "\n     * @param {0} the {1} information.";
    // field name - field Name

    /**
     * The insert parameters declaration.
     */
    public static final String DEFAULT_INSERT_PARAMETERS_DECLARATION =
        "\n        {0} {1}";
    // field type - field name

    /**
     * The update method.
     */
    public static final String DEFAULT_UPDATE_METHOD =
          "    /**\n"
        + "     * Updates {0} information\n"
        + "     * in the persistence layer."
         // table name
        + "{1}"
         // (optional) pk javadoc
        + "{2}\n"
         // update parameters javadoc
        + "     * @param transactionToken the transaction boundary.\n"
        + "     * @throws DataAccessException if the access to the information fails.\n"
        + "     */\n"
        + "    public void update(\n"
        + "{3}"
         // (optional) pk declaration
        + "{4}\n"
         // update parameters declaration
        + "        TransactionToken transactionToken)\n"
        + "      throws DataAccessException;\n\n";

    /**
     * The update parameters javadoc.
     */
    public static final String DEFAULT_UPDATE_PARAMETERS_JAVADOC =
        "\n     * @param {0} the {1} information.";
    // field name - field Name

    /**
     * The update parameters declaration.
     */
    public static final String DEFAULT_UPDATE_PARAMETERS_DECLARATION =
        "\n        {0} {1}";
    // field type - field name

    /**
     * The delete method.
     */
    public static final String DEFAULT_DELETE_METHOD =
          "    /**\n"
        + "     * Deletes {0} information from the persistence layer filtering\n"
         // table name
        + "     * by its primary keys."
        + "{1}\n"
         // DELETE_PK_JAVADOC
        + "     * @param transactionToken needed to use an open connection and\n"
        + "     * see previously uncommited inserts/updates/deletes.\n"
        + "     * @return <code>true</code> if the information has been deleted\n"
        + "     * successfully.\n"
        + "     * @throws DataAccessException if the access to the information fails.\n"
        + "     */\n"
        + "    public boolean delete(\n"
         // java table name
        + "{2}"
         // DELETE_PK_DECLARATION
        + "        TransactionToken  transactionToken)\n"
        + "      throws DataAccessException;\n";

    /**
     * The delete method's primary keys javadoc.
     */
    public static final String DEFAULT_DELETE_PK_JAVADOC =
        "\n     * @param {0} the {1} value to filter.";
         // java pk - pk

    /**
     * The delete method's primary keys declaration.
     */
    public static final String DEFAULT_DELETE_PK_DECLARATION =
        "        {0}              {1},\n";
         // pk type - java pk

    /**
     * The default class end.
     */
    public static final String DEFAULT_CLASS_END = "}\n";

    /**
     * The table template.
     */
    private TableTemplate m__TableTemplate;

    /**
     * The database metadata manager.
     */
    private DatabaseMetaDataManager m__MetaDataManager;

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
     * The engine name.
     */
    private String m__strEngineName;

    /**
     * The engine's version.
     */
    private String m__strEngineVersion;

    /**
     * The quote.
     */
    private String m__strQuote;

    /**
     * The ACM-SL import statements.
     */
    private String m__strAcmslImports;

    /**
     * The JDK import statements.
     */
    private String m__strJdkImports;

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
     * The find-by-primary-key method.
     */
    private String m__strFindByPrimaryKeyMethod;

    /**
     * The find-by-primary-key pk javadoc.
     */
    private String m__strFindByPrimaryKeyPkJavadoc;

    /**
     * The find-by-primary-key pk declaration.
     */
    private String m__strFindByPrimaryKeyPkDeclaration;

    /**
     * The insert method.
     */
    private String m__strInsertMethod;

    /**
     * The insert parameters Javadoc.
     */
    private String m__strInsertParametersJavadoc;

    /**
     * The insert parameters declaration.
     */
    private String m__strInsertParametersDeclaration;

    /**
     * The update method.
     */
    private String m__strUpdateMethod;

    /**
     * The update parameters Javadoc.
     */
    private String m__strUpdateParametersJavadoc;

    /**
     * The update parameters declaration.
     */
    private String m__strUpdateParametersDeclaration;

    /**
     * The delete method.
     */
    private String m__strDeleteMethod;

    /**
     * The delete PK javadoc.
     */
    private String m__strDeletePkJavadoc;

    /**
     * The delete PK declaration.
     */
    private String m__strDeletePkDeclaration;

    /**
     * The class end.
     */
    private String m__strClassEnd;

    /**
     * Builds a BaseDAOTemplate using given information.
     * @param tableTemplate the table template.
     * @param metaDataManager the database metadata manager.
     * @param header the header.
     * @param packageDeclaration the package declaration.
     * @param packageName the package name.
     * @param acmslImports the ACM-SL imports.
     * @param jdkImports the JDK imports.
     * @param javadoc the class Javadoc.
     * @param classDefinition the class definition.
     * @param classStart the class start.
     * @param findByPrimaryKeyMethod the find by primary key method.
     * @param findByPrimaryKeyPkJavadoc the find by primary key pk javadoc.
     * @param findByPrimaryKeyPkDeclaration the find by primary key pk
     *        declaration.
     * @param insertMethod the insert method.
     * @param insertParametersJavadoc the javadoc of the insert method's parameters.
     * @param insertParametersDeclaration the declaration of the insert method's parameters.
     * @param updateMethod the update method.
     * @param updateParametersJavadoc the javadoc of the update method's parameters.
     * @param updateParametersDeclaration the declaration of the update method's parameters.
     * @param deleteMethod the delete method.
     * @param deletePkJavadoc the delete PK javadoc.
     * @param deletePkDeclaration the delete PK declaration.
     * @param classEnd the class end.
     */
    public BaseDAOTemplate(
        TableTemplate           tableTemplate,
        DatabaseMetaDataManager metaDataManager,
        String                  header,
        String                  packageDeclaration,
        String                  packageName,
        String                  acmslImports,
        String                  jdkImports,
        String                  javadoc,
        String                  classDefinition,
        String                  classStart,
        String                  findByPrimaryKeyMethod,
        String                  findByPrimaryKeyPkJavadoc,
        String                  findByPrimaryKeyPkDeclaration,
        String                  insertMethod,
        String                  insertParametersJavadoc,
        String                  insertParametersDeclaration,
        String                  updateMethod,
        String                  updateParametersJavadoc,
        String                  updateParametersDeclaration,
        String                  deleteMethod,
        String                  deletePkJavadoc,
        String                  deletePkDeclaration,
        String                  classEnd)
    {
        inmutableSetTableTemplate(tableTemplate);
        inmutableSetMetaDataManager(metaDataManager);
        inmutableSetHeader(header);
        inmutableSetPackageDeclaration(packageDeclaration);
        inmutableSetPackageName(packageName);
        inmutableSetAcmslImports(acmslImports);
        inmutableSetJdkImports(jdkImports);
        inmutableSetJavadoc(javadoc);
        inmutableSetClassDefinition(classDefinition);
        inmutableSetClassStart(classStart);
        inmutableSetFindByPrimaryKeyMethod(findByPrimaryKeyMethod);
        inmutableSetFindByPrimaryKeyPkJavadoc(findByPrimaryKeyPkJavadoc);
        inmutableSetFindByPrimaryKeyPkDeclaration(findByPrimaryKeyPkDeclaration);
        inmutableSetInsertMethod(insertMethod);
        inmutableSetInsertParametersJavadoc(insertParametersJavadoc);
        inmutableSetInsertParametersDeclaration(insertParametersDeclaration);
        inmutableSetUpdateMethod(updateMethod);
        inmutableSetUpdateParametersJavadoc(updateParametersJavadoc);
        inmutableSetUpdateParametersDeclaration(updateParametersDeclaration);
        inmutableSetDeleteMethod(deleteMethod);
        inmutableSetDeletePkJavadoc(deletePkJavadoc);
        inmutableSetDeletePkDeclaration(deletePkDeclaration);
        inmutableSetClassEnd(classEnd);
    }

    /**
     * Builds a BaseDAOTemplate using given information.
     * @param tableTemplate the table template.
     * @param metaDataManager the database metadata manager.
     * @param packageName the package name.
     */
    public BaseDAOTemplate(
        TableTemplate           tableTemplate,
        DatabaseMetaDataManager metaDataManager,
        String                  packageName)
    {
        this(
            tableTemplate,
            metaDataManager,
            DEFAULT_HEADER,
            PACKAGE_DECLARATION,
            packageName,
            ACMSL_IMPORTS,
            JDK_IMPORTS,
            DEFAULT_JAVADOC,
            CLASS_DEFINITION,
            DEFAULT_CLASS_START,
            DEFAULT_FIND_BY_PRIMARY_KEY_METHOD,
            DEFAULT_FIND_BY_PRIMARY_KEY_PK_JAVADOC,
            DEFAULT_FIND_BY_PRIMARY_KEY_PK_DECLARATION,
            DEFAULT_INSERT_METHOD,
            DEFAULT_INSERT_PARAMETERS_JAVADOC,
            DEFAULT_INSERT_PARAMETERS_DECLARATION,
            DEFAULT_UPDATE_METHOD,
            DEFAULT_UPDATE_PARAMETERS_JAVADOC,
            DEFAULT_UPDATE_PARAMETERS_DECLARATION,
            DEFAULT_DELETE_METHOD,
            DEFAULT_DELETE_PK_JAVADOC,
            DEFAULT_DELETE_PK_DECLARATION,
            DEFAULT_CLASS_END);
    }

    /**
     * Specifies the table template.
     * @param tableTemplate the table template.
     */
    private void inmutableSetTableTemplate(TableTemplate tableTemplate)
    {
        m__TableTemplate = tableTemplate;
    }

    /**
     * Specifies the table template.
     * @param tableTemplate the table template.
     */
    protected void setTableTemplate(TableTemplate tableTemplate)
    {
        inmutableSetTableTemplate(tableTemplate);
    }

    /**
     * Retrieves the table template.
     * @return such template.
     */
    public TableTemplate getTableTemplate()
    {
        return m__TableTemplate;
    }


    /**
     * Specifies the metadata manager.
     * @param metaDataManager the metadata manager.
     */
    private void inmutableSetMetaDataManager(DatabaseMetaDataManager metaDataManager)
    {
        m__MetaDataManager = metaDataManager;
    }

    /**
     * Specifies the metadata manager.
     * @param metaDataManager the metadata manager.
     */
    protected void setMetaDataManager(DatabaseMetaDataManager metaDataManager)
    {
        inmutableSetMetaDataManager(metaDataManager);
    }

    /**
     * Retrieves the metadata manager.
     * @return such manager.
     */
    public DatabaseMetaDataManager getMetaDataManager()
    {
        return m__MetaDataManager;
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
     * Specifies the ACM-SL imports.
     * @param acmslImports the new ACM-SL imports.
     */
    private void inmutableSetAcmslImports(String acmslImports)
    {
        m__strAcmslImports = acmslImports;
    }

    /**
     * Specifies the ACM-SL imports.
     * @param acmslImports the new ACM-SL imports.
     */
    protected void setAcmslImports(String acmslImports)
    {
        inmutableSetAcmslImports(acmslImports);
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
     * Specifies the JDK imports.
     * @param jdkImports the new JDK imports.
     */
    private void inmutableSetJdkImports(String jdkImports)
    {
        m__strJdkImports = jdkImports;
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
        return m__strJdkImports;
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
     * Specifies the find-by-primary-key method.
     * @param findByPrimaryKeyMethod such method.
     */
    private void inmutableSetFindByPrimaryKeyMethod(
        String findByPrimaryKeyMethod)
    {
        m__strFindByPrimaryKeyMethod = findByPrimaryKeyMethod;
    }

    /**
     * Specifies the find-by-primary-key method.
     * @param findByPrimaryKeyMethod such method.
     */
    protected void setFindByPrimaryKeyMethod(
        String findByPrimaryKeyMethod)
    {
        inmutableSetFindByPrimaryKeyMethod(
            findByPrimaryKeyMethod);
    }

    /**
     * Retrieves the find-by-primary-key method.
     * @return such method.
     */
    public String getFindByPrimaryKeyMethod()
    {
        return m__strFindByPrimaryKeyMethod;
    }

    /**
     * Specifies the find-by-primary-key pk Javadoc.
     * @param findByPrimaryKeyPkJavadoc such Javadoc.
     */
    private void inmutableSetFindByPrimaryKeyPkJavadoc(
        String findByPrimaryKeyPkJavadoc)
    {
        m__strFindByPrimaryKeyPkJavadoc = findByPrimaryKeyPkJavadoc;
    }

    /**
     * Specifies the find-by-primary-key pk Javadoc.
     * @param findByPrimaryKeyPkJavadoc such Javadoc.
     */
    protected void setFindByPrimaryKeyPkJavadoc(
        String findByPrimaryKeyPkJavadoc)
    {
        inmutableSetFindByPrimaryKeyPkJavadoc(
            findByPrimaryKeyPkJavadoc);
    }

    /**
     * Retrieves the find-by-primary-key pk Javadoc.
     * @return such Javadoc.
     */
    public String getFindByPrimaryKeyPkJavadoc()
    {
        return m__strFindByPrimaryKeyPkJavadoc;
    }
    
    /**
     * Specifies the find-by-primary-key pk declaration.
     * @param findByPrimaryKeyPkDeclaration such declaration.
     */
    private void inmutableSetFindByPrimaryKeyPkDeclaration(
        String findByPrimaryKeyPkDeclaration)
    {
        m__strFindByPrimaryKeyPkDeclaration =
            findByPrimaryKeyPkDeclaration;
    }

    /**
     * Specifies the find-by-primary-key pk declaration.
     * @param findByPrimaryKeyPkDeclaration such declaration.
     */
    protected void setFindByPrimaryKeyPkdeclaration(
        String findByPrimaryKeyPkDeclaration)
    {
        inmutableSetFindByPrimaryKeyPkDeclaration(
            findByPrimaryKeyPkDeclaration);
    }

    /**
     * Retrieves the find-by-primary-key pk declaration.
     * @return such declaration.
     */
    public String getFindByPrimaryKeyPkDeclaration()
    {
        return m__strFindByPrimaryKeyPkDeclaration;
    }

    /**
     * Specifies the insert method.
     * @param insertMethod such method.
     */
    private void inmutableSetInsertMethod(String insertMethod)
    {
        m__strInsertMethod = insertMethod;
    }

    /**
     * Specifies the insert method.
     * @param insertMethod such method.
     */
    protected void setInsertMethod(String insertMethod)
    {
        inmutableSetInsertMethod(insertMethod);
    }

    /**
     * Retrieves the insert method.
     * @return such method.
     */
    public String getInsertMethod()
    {
        return m__strInsertMethod;
    }

    /**
     * Specifies the insert parameters Javadoc.
     * @param javadoc such javadoc.
     */
    private void inmutableSetInsertParametersJavadoc(String javadoc)
    {
        m__strInsertParametersJavadoc = javadoc;
    }

    /**
     * Specifies the insert parameters Javadoc.
     * @param javadoc such javadoc.
     */
    protected void setInsertParametersJavadoc(String javadoc)
    {
        inmutableSetInsertParametersJavadoc(javadoc);
    }

    /**
     * Retrieves the insert parameters javadoc.
     * @return such information.
     */
    public String getInsertParametersJavadoc()
    {
        return m__strInsertParametersJavadoc;
    }

    /**
     * Specifies the insert parameters Declaration.
     * @param declaration such declaration.
     */
    private void inmutableSetInsertParametersDeclaration(String declaration)
    {
        m__strInsertParametersDeclaration = declaration;
    }

    /**
     * Specifies the insert parameters Declaration.
     * @param declaration such declaration.
     */
    protected void setInsertParametersDeclaration(String declaration)
    {
        inmutableSetInsertParametersDeclaration(declaration);
    }

    /**
     * Retrieves the insert parameters declaration.
     * @return such information.
     */
    public String getInsertParametersDeclaration()
    {
        return m__strInsertParametersDeclaration;
    }

    /**
     * Specifies the update method.
     * @param updateMethod such method.
     */
    private void inmutableSetUpdateMethod(String updateMethod)
    {
        m__strUpdateMethod = updateMethod;
    }

    /**
     * Specifies the update method.
     * @param updateMethod such method.
     */
    protected void setUpdateMethod(String updateMethod)
    {
        inmutableSetUpdateMethod(updateMethod);
    }

    /**
     * Retrieves the update method.
     * @return such method.
     */
    public String getUpdateMethod()
    {
        return m__strUpdateMethod;
    }

    /**
     * Specifies the update parameters Javadoc.
     * @param javadoc such javadoc.
     */
    private void inmutableSetUpdateParametersJavadoc(String javadoc)
    {
        m__strUpdateParametersJavadoc = javadoc;
    }

    /**
     * Specifies the update parameters Javadoc.
     * @param javadoc such javadoc.
     */
    protected void setUpdateParametersJavadoc(String javadoc)
    {
        inmutableSetUpdateParametersJavadoc(javadoc);
    }

    /**
     * Retrieves the update parameters javadoc.
     * @return such information.
     */
    public String getUpdateParametersJavadoc()
    {
        return m__strUpdateParametersJavadoc;
    }

    /**
     * Specifies the update parameters Declaration.
     * @param declaration such declaration.
     */
    private void inmutableSetUpdateParametersDeclaration(String declaration)
    {
        m__strUpdateParametersDeclaration = declaration;
    }

    /**
     * Specifies the update parameters Declaration.
     * @param declaration such declaration.
     */
    protected void setUpdateParametersDeclaration(String declaration)
    {
        inmutableSetUpdateParametersDeclaration(declaration);
    }

    /**
     * Retrieves the update parameters declaration.
     * @return such information.
     */
    public String getUpdateParametersDeclaration()
    {
        return m__strUpdateParametersDeclaration;
    }

    /**
     * Specifies the delete method.
     * @param deleteMethod such method.
     */
    private void inmutableSetDeleteMethod(
        String deleteMethod)
    {
        m__strDeleteMethod = deleteMethod;
    }

    /**
     * Specifies the delete method.
     * @param deleteMethod such method.
     */
    protected void setDeleteMethod(
        String deleteMethod)
    {
        inmutableSetDeleteMethod(
            deleteMethod);
    }

    /**
     * Retrieves the delete method.
     * @return such method.
     */
    public String getDeleteMethod()
    {
        return m__strDeleteMethod;
    }

    /**
     * Specifies the delete pk Javadoc.
     * @param deletePkJavadoc such Javadoc.
     */
    private void inmutableSetDeletePkJavadoc(
        String deletePkJavadoc)
    {
        m__strDeletePkJavadoc = deletePkJavadoc;
    }

    /**
     * Specifies the delete pk Javadoc.
     * @param deletePkJavadoc such Javadoc.
     */
    protected void setDeletePkJavadoc(
        String deletePkJavadoc)
    {
        inmutableSetDeletePkJavadoc(
            deletePkJavadoc);
    }

    /**
     * Retrieves the delete pk Javadoc.
     * @return such Javadoc.
     */
    public String getDeletePkJavadoc()
    {
        return m__strDeletePkJavadoc;
    }
    
    /**
     * Specifies the delete pk declaration.
     * @param deletePkDeclaration such declaration.
     */
    private void inmutableSetDeletePkDeclaration(
        String deletePkDeclaration)
    {
        m__strDeletePkDeclaration =
            deletePkDeclaration;
    }

    /**
     * Specifies the delete pk declaration.
     * @param deletePkDeclaration such declaration.
     */
    protected void setDeletePkdeclaration(
        String deletePkDeclaration)
    {
        inmutableSetDeletePkDeclaration(
            deletePkDeclaration);
    }

    /**
     * Retrieves the delete pk declaration.
     * @return such declaration.
     */
    public String getDeletePkDeclaration()
    {
        return m__strDeletePkDeclaration;
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
     * Retrieves the source code generated by this template.
     * @return such code.
     */
    public String toString()
    {
        StringBuffer t_sbResult = new StringBuffer();

        EnglishGrammarUtils t_EnglishGrammarUtils =
            EnglishGrammarUtils.getInstance();
        StringUtils t_StringUtils = StringUtils.getInstance();

        MetaDataUtils t_MetaDataUtils = MetaDataUtils.getInstance();

        TableTemplate t_TableTemplate = getTableTemplate();

        DatabaseMetaDataManager t_MetaDataManager = getMetaDataManager();

        if  (   (t_TableTemplate   != null)
             && (t_MetaDataManager != null)
             && (t_MetaDataUtils   != null)
             && (t_StringUtils     != null))
        {
            MessageFormat t_HeaderFormatter = new MessageFormat(getHeader());

            t_sbResult.append(
                t_HeaderFormatter.format(
                    new Object[]
                    {
                        t_TableTemplate.getTableName()
                    }));

            MessageFormat t_PackageDeclarationFormatter =
                new MessageFormat(getPackageDeclaration());

            t_sbResult.append(
                t_PackageDeclarationFormatter.format(
                    new Object[]{getPackageName()}));

            t_sbResult.append(getAcmslImports());
            t_sbResult.append(getJdkImports());

            MessageFormat t_JavadocFormatter = new MessageFormat(getJavadoc());

            t_sbResult.append(
                t_JavadocFormatter.format(
                    new Object[]
                    {
                        t_TableTemplate.getTableName()
                    }));

            MessageFormat t_ClassDefinitionFormatter =
                new MessageFormat(getClassDefinition());

            t_sbResult.append(
                t_ClassDefinitionFormatter.format(
                    new Object[]
                    {
                        t_StringUtils.capitalize(
                            t_EnglishGrammarUtils.getSingular(
                                t_TableTemplate.getTableName()
                                    .toLowerCase()),
                            '_')
                    }));

            t_sbResult.append(getClassStart());

            MessageFormat t_FindByPrimaryKeyFormatter =
                new MessageFormat(getFindByPrimaryKeyMethod());

            MessageFormat t_FindByPrimaryKeyPkJavadocFormatter =
                new MessageFormat(getFindByPrimaryKeyPkJavadoc());

            MessageFormat t_FindByPrimaryKeyPkDeclarationFormatter =
                new MessageFormat(getFindByPrimaryKeyPkDeclaration());

            MessageFormat t_InsertMethodFormatter =
                new MessageFormat(getInsertMethod());

            MessageFormat t_InsertParametersJavadocFormatter =
                new MessageFormat(getInsertParametersJavadoc());

            MessageFormat t_InsertParametersDeclarationFormatter =
                new MessageFormat(getInsertParametersDeclaration());

            MessageFormat t_UpdateMethodFormatter =
                new MessageFormat(getUpdateMethod());

            MessageFormat t_UpdateParametersJavadocFormatter =
                new MessageFormat(getUpdateParametersJavadoc());

            MessageFormat t_UpdateParametersDeclarationFormatter =
                new MessageFormat(getUpdateParametersDeclaration());

            String[] t_astrPrimaryKeys =
                t_MetaDataManager.getPrimaryKeys(t_TableTemplate.getTableName());

            MessageFormat t_DeleteMethodFormatter =
                new MessageFormat(getDeleteMethod());

            StringBuffer t_sbPkJavadoc = new StringBuffer();
            StringBuffer t_sbPkDeclaration = new StringBuffer();
            StringBuffer t_sbDeleteMethod = new StringBuffer();
            StringBuffer t_sbInsertPkJavadoc = new StringBuffer();
            StringBuffer t_sbInsertPkDeclaration = new StringBuffer();

            if  (t_astrPrimaryKeys != null)
            {
                StringBuffer t_sbSelectFields = new StringBuffer();
                StringBuffer t_sbFilterDeclaration = new StringBuffer();
                StringBuffer t_sbFilterValues = new StringBuffer();

                for  (int t_iPkIndex = 0;
                          t_iPkIndex < t_astrPrimaryKeys.length;
                          t_iPkIndex++)
                {
                    String t_strPkJavadoc =
                        t_FindByPrimaryKeyPkJavadocFormatter.format(
                            new Object[]
                            {
                                t_astrPrimaryKeys[t_iPkIndex].toLowerCase(),
                                t_astrPrimaryKeys[t_iPkIndex]
                            });

                    String t_strPkDeclaration =
                        t_FindByPrimaryKeyPkDeclarationFormatter.format(
                            new Object[]
                            {
                                t_MetaDataUtils.getNativeType(
                                    t_MetaDataManager.getColumnType(
                                        t_TableTemplate.getTableName(),
                                        t_astrPrimaryKeys[t_iPkIndex])),
                                t_astrPrimaryKeys[t_iPkIndex].toLowerCase()
                            });

                    t_sbPkJavadoc.append(t_strPkJavadoc);
                    t_sbPkDeclaration.append(t_strPkDeclaration);

                    if  (!t_MetaDataManager.isManagedExternally(
                             t_TableTemplate.getTableName(),
                             t_astrPrimaryKeys[t_iPkIndex]))
                    {
                        t_sbInsertPkJavadoc.append(t_strPkJavadoc);
                        t_sbInsertPkDeclaration.append(t_strPkDeclaration);
                    }
                }

                t_sbResult.append(
                    t_FindByPrimaryKeyFormatter.format(
                        new Object[]
                        {
                            t_TableTemplate.getTableName(),
                            t_sbPkJavadoc,
                            t_StringUtils.capitalize(
                                t_EnglishGrammarUtils.getSingular(
                                    t_TableTemplate.getTableName()
                                        .toLowerCase()),
                                '_'),
                            t_sbPkDeclaration
                        }));

                t_sbDeleteMethod.append(
                    t_DeleteMethodFormatter.format(
                        new Object[]
                        {
                            t_TableTemplate.getTableName(),
                            t_sbPkJavadoc,
                            t_sbPkDeclaration,
                            "Phoenix"
                        }));
            }

            String[] t_astrColumnNames =
                t_MetaDataManager.getColumnNames(t_TableTemplate.getTableName());

            if  (t_astrColumnNames != null)
            {
                StringBuffer t_sbBuildValueObjectRetrieval     =
                    new StringBuffer();
                StringBuffer t_sbInsertParametersJavadoc       =
                    new StringBuffer();
                StringBuffer t_sbInsertParametersDeclaration   =
                    new StringBuffer();
                StringBuffer t_sbInsertParametersSpecification =
                    new StringBuffer();
                StringBuffer t_sbUpdateParametersJavadoc       =
                    new StringBuffer();
                StringBuffer t_sbUpdateParametersDeclaration   =
                    new StringBuffer();
                StringBuffer t_sbUpdateParametersSpecification =
                    new StringBuffer();

                for  (int t_iColumnIndex = 0;
                          t_iColumnIndex < t_astrColumnNames.length;
                          t_iColumnIndex++)
                {
                    if  (!t_MetaDataManager.isManagedExternally(
                             t_TableTemplate.getTableName(),
                             t_astrColumnNames[t_iColumnIndex]))
                    {
                        if  (!t_MetaDataManager.isPrimaryKey(
                                 t_TableTemplate.getTableName(),
                                 t_astrColumnNames[t_iColumnIndex]))
                        {
                            t_sbInsertParametersJavadoc.append(
                                t_InsertParametersJavadocFormatter.format(
                                    new Object[]
                                    {
                                        t_astrColumnNames[t_iColumnIndex]
                                            .toLowerCase(),
                                        t_astrColumnNames[t_iColumnIndex]
                                    }));

                            t_sbUpdateParametersJavadoc.append(
                                t_UpdateParametersJavadocFormatter.format(
                                new Object[]
                                {
                                    t_astrColumnNames[t_iColumnIndex].toLowerCase(),
                                    t_astrColumnNames[t_iColumnIndex]
                                }));

                            t_sbInsertParametersDeclaration.append(
                                t_InsertParametersDeclarationFormatter.format(
                                    new Object[]
                                    {
                                        t_MetaDataUtils.getNativeType(
                                            t_MetaDataManager.getColumnType(
                                                t_TableTemplate.getTableName(),
                                                t_astrColumnNames[
                                                    t_iColumnIndex])),
                                        t_astrColumnNames[t_iColumnIndex]
                                            .toLowerCase()
                                    }));

                            t_sbInsertParametersDeclaration.append(",");

                            t_sbUpdateParametersDeclaration.append(
                                t_UpdateParametersDeclarationFormatter.format(
                                    new Object[]
                                    {
                                        t_MetaDataUtils.getNativeType(
                                            t_MetaDataManager.getColumnType(
                                                t_TableTemplate.getTableName(),
                                                t_astrColumnNames[
                                                    t_iColumnIndex])),
                                        t_astrColumnNames[t_iColumnIndex]
                                            .toLowerCase()
                                    }));

                            t_sbUpdateParametersDeclaration.append(",");
                        }
                    }
                }

                t_sbResult.append(
                    t_InsertMethodFormatter.format(
                        new Object[]
                        {
                            t_StringUtils.capitalize(
                                t_EnglishGrammarUtils.getSingular(
                                    t_TableTemplate.getTableName()
                                        .toLowerCase()),
                                '_'),
                            t_sbInsertPkJavadoc.toString(),
                            t_sbInsertParametersJavadoc,
                            t_sbInsertPkDeclaration,
                            t_sbInsertParametersDeclaration
                        }));

                t_sbResult.append(
                    t_UpdateMethodFormatter.format(
                        new Object[]
                        {
                            t_StringUtils.capitalize(
                                t_EnglishGrammarUtils.getSingular(
                                    t_TableTemplate.getTableName()
                                        .toLowerCase()),
                                '_'),
                            t_sbPkJavadoc.toString(),
                            t_sbUpdateParametersJavadoc,
                            t_sbPkDeclaration,
                            t_sbUpdateParametersDeclaration
                        }));

            }

            t_sbResult.append(t_sbDeleteMethod);
        }

        t_sbResult.append(getClassEnd());

        return t_sbResult.toString();
    }
}
