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
 * Description: Is able to generate procedure repositories according to
 *              database metadata.
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
 * Importing some project-specific classes.
 */
import org.acmsl.queryj.tools.DatabaseMetaDataManager;
import org.acmsl.queryj.tools.MetaDataUtils;
import org.acmsl.queryj.tools.ProcedureMetaData;
import org.acmsl.queryj.tools.ProcedureParameterMetaData;

/*
 * Importing some ACM-SL classes.
 */
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
 * Is able to generate procedure repositories according to
 * database metadata.
 * @author <a href="mailto:jsanleandro@yahoo.es"
           >Jose San Leandro</a>
 * @version $Revision$
 */
public abstract class ProcedureRepositoryTemplate
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
        + " * Description: Contains all procedures belonging to {0} repository.\n"
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
     * The project imports.
     */
    public static final String DEFAULT_PROJECT_IMPORTS_JAVADOC =
          "/*\n"
        + " * Importing project-specific classes.\n"
        + " */\n";

    /**
     * The project imports.
     */
    public static final String PROJECT_IMPORTS = "\n";

    /**
     * The ACM-SL imports.
     */
    public static final String ACMSL_IMPORTS =
          "/*\n"
        + " * Importing some ACM-SL classes.\n"
        + " */\n"
        + "import org.acmsl.queryj.QueryJException;\n\n";

    /**
     * The JDK imports.
     */
    public static final String JDK_IMPORTS =
          "/*\n"
        + " * Importing some JDK classes.\n"
        + " */\n"
        + "import java.math.BigDecimal;\n"
        + "import java.sql.CallableStatement;\n"
        + "import java.sql.Connection;\n"
        + "import java.sql.ResultSet;\n"
        + "import java.sql.SQLException;\n\n"
        + "import java.sql.Timestamp;\n"
        + "import java.sql.Types;\n"
        + "import java.util.Calendar;\n\n";
    
    /**
     * The default class Javadoc.
     */
    public static final String DEFAULT_JAVADOC =
          "/**\n"
        + " * Contains all procedures belonging to {0} repository.\n" // repository
        + " * @author <a href=\"http://maven.acm-sl.org/queryj\">QueryJ</a>\n"
        + " * @version $" + "Revision: $\n"
        + " */\n";

    /**
     * The class definition.
     */
    public static final String CLASS_DEFINITION =
         "public abstract class {0}\n"; // repository

    /**
     * The class start.
     */
    public static final String DEFAULT_CLASS_START = "{\n";

    /**
     * The default procedure Javadoc.
     */
    public static final String DEFAULT_PROCEDURE_JAVADOC =
          "    /**\n"
        + "     * {0}\n" // procedure comment.
        + "     * @param connection the JDBC connection.\n"
        + "{1}" // parameters javadoc
        + "     * @return the associated value object.\n"
        + "     */\n";

    /**
     * The procedure's parameter javadoc.
     */
    public static final String DEFAULT_PROCEDURE_PARAMETER_JAVADOC =
        "     * @param {0} {1}\n"; // parameter name - parameter comment.

    /**
     * The procedure body.
     */
    public static final String DEFAULT_PROCEDURE_BODY =
          "    public {0} {1}(\n"
         // procedure value object - procedure java name
        + "            Connection connection"
        + "{2})\n"
         // - procedure parameters declaration
        + "        throws  QueryJException\n"
        + "    '{'\n"
        + "        {0} result = {3};\n\n"
        + "        try\n"
        + "        '{'\n"
        + "            // Retrieve data source\n"
        + "            Connection t_Connection = connection;//getConnection();\n\n"
        + "            CallableStatement t_CallableStatement =\n"
        + "                t_Connection.prepareCall(\n"
        + "{4});\n\n" // procedure sentence
        + "{5}\n" // out parameters registration
        + "{6}\n" // in parameters specification
        + "            t_CallableStatement.execute();\n\n"
        + "            ResultSet t_rsResult = t_CallableStatement.getResultSet();\n\n"
        + "            if  (t_rsResult != null)\n"
        + "            '{'\n"
        + "{7}" // out parameters retrieval
        + "                result = t_rsResult.{10};\n"
        + "{8}\n" // value object construction
        + "                t_rsResult.close();\n"
        + "            '}'\n"
        + "        '}'\n"
        + "        catch  (SQLException sqlException)\n"
        + "        '{'\n"
        + "            throw\n"
        + "                new QueryJException(\n"
        + "                    \"Error executing {11} procedure\",\n"
        + "                    sqlException);\n"
        + "        '}'\n\n"
        + "        return result;\n"
        + "    '}'\n\n";

    /**
     * The parameter declaration.
     */
    public static final String DEFAULT_PROCEDURE_PARAMETER_DECLARATION =
        "\n            {0} {1}";

    /**
     * The procedure sentence.
     */
    public static final String DEFAULT_PROCEDURE_SENTENCE =
        "                    \"'{'? = {0}({1})'}'\"";
        // procedure name - procedure parameter bindings

    /**
     * OUT parameter registration.
     */
    public static final String DEFAULT_OUT_PARAMETER_REGISTRATION =
          "                t_CallableStatement.registerOutParameter(\n"
        + "                    {0},\n"
        + "                    Types.{1});\n";
    
    /**
     * IN parameter specification.
     */
    public static final String DEFAULT_IN_PARAMETER_SPECIFICATION =
        "            t_CallableStatement.{0};\n";
        // parameter type - parameter index - Java parameter name

    /**
     * OUT parameter retrieval.
     */
    public static final String DEFAULT_OUT_PARAMETER_RETRIEVAL =
          "                {0} t_{1} = t_rsResult.get{3}({2});\n";
          // parameter type - parameter name - parameter index - object parameter type

    /**
     * The value object construction.
     */
    public static final String DEFAULT_VALUE_OBJECT_CONSTRUCTION =
          "                {0} t_{1} = t_rsResult.get{2}({3});\n";
          // parameter type - Java parameter name - parameter name

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
     * The procedure Javadoc.
     */
    private String m__strProcedureJavadoc;

    /**
     * The procedure parameter javadoc.
     */
    private String m__strProcedureParameterJavadoc;

    /**
     * The procedure body.
     */
    private String m__strProcedureBody;

    /**
     * The procedure parameter declaration.
     */
    private String m__strProcedureParameterDeclaration;

    /**
     * The procedure sentence.
     */
    private String m__strProcedureSentence;

    /**
     * OUT parameter registration.
     */
    private String m__strOutParameterRegistration;

    /**
     * IN parameter specification.
     */
    private String m__strInParameterSpecification;

    /**
     * OUT parameter retrieval.
     */
    private String m__strOutParameterRetrieval;

    /**
     * Value object construction.
     */
    private String m__strValueObjectConstruction;

    /**
     * The class end.
     */
    private String m__strClassEnd;

    /**
     * The procedure metadata list.
     */
    private List m__lProceduresMetaData;

    /**
     * The procedure parameters metadata.
     */
    private Map m__mProcedureParametersMetaData;

    /**
     * Builds a ProcedureRepositoryTemplate using given information.
     * @param header the header.
     * @param packageDeclaration the package declaration.
     * @param packageName the package name.
     * @param repository the repository.
     * @param importsJavadoc the project imports javadoc.
     * @param projectImports the project imports.
     * @param acmslImports the ACM-SL imports.
     * @param jdkImports the JDK imports.
     * @param javadoc the class Javadoc.
     * @param classDefinition the class definition.
     * @param classStart the class start.
     * @param procedureJavadoc the procedure Javadoc.
     * @param procedureParameterJavadoc the procedure parameter javadoc.
     * @param procedureBody the procedure body.
     * @param procedureParameterDeclaration the procedure parameter declaration.
     * @param procedureSentence the procedure sentence.
     * @param outParameterRegistration the OUT parameter registration.
     * @param inParameterSpecification the IN parameter specification.
     * @param outParameterRetrieval the OUT parameter retrieval.
     * @param valueObjectConstruction the value object construction.
     * @param classEnd the class end.
     */
    public ProcedureRepositoryTemplate(
        String header,
        String packageDeclaration,
        String packageName,
        String repository,
        String importsJavadoc,
        String projectImports,
        String acmslImports,
        String jdkImports,
        String javadoc,
        String classDefinition,
        String classStart,
        String procedureJavadoc,
        String procedureParameterJavadoc,
        String procedureBody,
        String procedureParameterDeclaration,
        String procedureSentence,
        String outParameterRegistration,
        String inParameterSpecification,
        String outParameterRetrieval,
        String valueObjectConstruction,
        String classEnd)
    {
        inmutableSetHeader(header);
        inmutableSetPackageDeclaration(packageDeclaration);
        inmutableSetPackageName(packageName);
        inmutableSetRepository(repository);
        inmutableSetProjectImportsJavadoc(importsJavadoc);
        inmutableSetProjectImports(projectImports);
        inmutableSetAcmslImports(acmslImports);
        inmutableSetJdkImports(jdkImports);
        inmutableSetJavadoc(javadoc);
        inmutableSetClassDefinition(classDefinition);
        inmutableSetClassStart(classStart);
        inmutableSetProcedureJavadoc(procedureJavadoc);
        inmutableSetProcedureParameterJavadoc(procedureParameterJavadoc);
        inmutableSetProcedureBody(procedureBody);
        inmutableSetProcedureParameterDeclaration(procedureParameterDeclaration);
        inmutableSetProcedureSentence(procedureSentence);
        inmutableSetOutParameterRegistration(outParameterRegistration);
        inmutableSetInParameterSpecification(inParameterSpecification);
        inmutableSetOutParameterRetrieval(outParameterRetrieval);
        inmutableSetValueObjectConstruction(valueObjectConstruction);
        inmutableSetClassEnd(classEnd);
        inmutableSetProceduresMetaData(new ArrayList());
        inmutableSetProcedureParametersMetaData(new HashMap());
    }

    /**
     * Builds a ProcedureRepositoryTemplate using given information.
     * @param packageName the package name.
     * @param repository the repository.
     */
    public ProcedureRepositoryTemplate(
        String packageName,
        String repository)
    {
        this(
            DEFAULT_HEADER,
            PACKAGE_DECLARATION,
            packageName,
            repository,
            DEFAULT_PROJECT_IMPORTS_JAVADOC,
            PROJECT_IMPORTS,
            ACMSL_IMPORTS,
            JDK_IMPORTS,
            DEFAULT_JAVADOC,
            CLASS_DEFINITION,
            DEFAULT_CLASS_START,
            DEFAULT_PROCEDURE_JAVADOC,
            DEFAULT_PROCEDURE_PARAMETER_JAVADOC,
            DEFAULT_PROCEDURE_BODY,
            DEFAULT_PROCEDURE_PARAMETER_DECLARATION,
            DEFAULT_PROCEDURE_SENTENCE,
            DEFAULT_OUT_PARAMETER_REGISTRATION,
            DEFAULT_IN_PARAMETER_SPECIFICATION,
            DEFAULT_OUT_PARAMETER_RETRIEVAL,
            DEFAULT_VALUE_OBJECT_CONSTRUCTION,
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
     * @param importJavadoc the new project imports Javadoc.
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
     * Specifies the procedure Javadoc.
     * @param procedureJavadoc the new procedure Javadoc.
     */
    private void inmutableSetProcedureJavadoc(String procedureJavadoc)
    {
        m__strProcedureJavadoc = procedureJavadoc;
    }

    /**
     * Specifies the procedure Javadoc.
     * @param procedureJavadoc the new procedure Javadoc.
     */
    protected void setProcedureJavadoc(String procedureJavadoc)
    {
        inmutableSetProcedureJavadoc(procedureJavadoc);
    }

    /**
     * Retrieves the procedure Javadoc.
     * @return such information.
     */
    public String getProcedureJavadoc() 
    {
        return m__strProcedureJavadoc;
    }

    /**
     * Specifies the procedure parameter Javadoc.
     * @param procedureJavadoc the new procedure parameter Javadoc.
     */
    private void inmutableSetProcedureParameterJavadoc(String procedureParameterJavadoc)
    {
        m__strProcedureParameterJavadoc = procedureParameterJavadoc;
    }

    /**
     * Specifies the procedure parameter Javadoc.
     * @param procedureParameterJavadoc the new procedure parameter Javadoc.
     */
    protected void setProcedureParameterJavadoc(String procedureParameterJavadoc)
    {
        inmutableSetProcedureParameterJavadoc(procedureParameterJavadoc);
    }

    /**
     * Retrieves the procedure parameter Javadoc.
     * @return such information.
     */
    public String getProcedureParameterJavadoc() 
    {
        return m__strProcedureParameterJavadoc;
    }

    /**
     * Specifies the procedure body.
     * @param procedureBody the new procedure body.
     */
    private void inmutableSetProcedureBody(String procedureBody)
    {
        m__strProcedureBody = procedureBody;
    }

    /**
     * Specifies the procedure body.
     * @param procedureBody the new procedure body.
     */
    protected void setProcedureBody(String procedureBody)
    {
        inmutableSetProcedureBody(procedureBody);
    }

    /**
     * Retrieves the procedure body.
     * @return such information.
     */
    public String getProcedureBody() 
    {
        return m__strProcedureBody;
    }

    /**
     * Specifies the procedure parameter declaration.
     * @param procedureDeclaration the new procedure parameter declaration.
     */
    private void inmutableSetProcedureParameterDeclaration(String procedureParameterDeclaration)
    {
        m__strProcedureParameterDeclaration = procedureParameterDeclaration;
    }

    /**
     * Specifies the procedure parameter declaration.
     * @param procedureParameterDeclaration the new procedure parameter declaration.
     */
    protected void setProcedureParameterDeclaration(String procedureParameterDeclaration)
    {
        inmutableSetProcedureParameterDeclaration(procedureParameterDeclaration);
    }

    /**
     * Retrieves the procedure parameter declaration.
     * @return such information.
     */
    public String getProcedureParameterDeclaration() 
    {
        return m__strProcedureParameterDeclaration;
    }

    /**
     * Specifies the procedure sentence.
     * @param procedureSentence the new procedure sentence.
     */
    private void inmutableSetProcedureSentence(String procedureSentence)
    {
        m__strProcedureSentence = procedureSentence;
    }

    /**
     * Specifies the procedure sentence.
     * @param procedureSentence the new procedure sentence.
     */
    protected void setProcedureSentence(String procedureSentence)
    {
        inmutableSetProcedureSentence(procedureSentence);
    }

    /**
     * Retrieves the procedure sentence.
     * @return such information.
     */
    public String getProcedureSentence() 
    {
        return m__strProcedureSentence;
    }

    /**
     * Specifies the OUT parameter registration.
     * @param outParameterRegistration such template chunk.
     */
    private void inmutableSetOutParameterRegistration(String outParameterRegistration)
    {
        m__strOutParameterRegistration = outParameterRegistration;
    }

    /**
     * Specifies the OUT parameter registration.
     * @param outParameterRegistration such template chunk.
     */
    protected void setOutParameterRegistration(String outParameterRegistration)
    {
        inmutableSetOutParameterRegistration(outParameterRegistration);
    }

    /**
     * Retrieves the OUT parameter registration.
     * @return such information.
     */
    public String getOutParameterRegistration() 
    {
        return m__strOutParameterRegistration;
    }

    /**
     * Specifies the IN parameter specification.
     * @param inParameterSpecification such template chunk.
     */
    private void inmutableSetInParameterSpecification(String inParameterSpecification)
    {
        m__strInParameterSpecification = inParameterSpecification;
    }

    /**
     * Specifies the IN parameter specification.
     * @param inParameterSpecification such template chunk.
     */
    protected void setInParameterSpecification(String inParameterSpecification)
    {
        inmutableSetInParameterSpecification(inParameterSpecification);
    }

    /**
     * Retrieves the IN parameter specification.
     * @return such information.
     */
    public String getInParameterSpecification() 
    {
        return m__strInParameterSpecification;
    }

    /**
     * Specifies the OUT parameter retrieval.
     * @param outParameterRetrieval such template chunk.
     */
    private void inmutableSetOutParameterRetrieval(String outParameterRetrieval)
    {
        m__strOutParameterRetrieval = outParameterRetrieval;
    }

    /**
     * Specifies the OUT parameter retrieval.
     * @param outParameterRetrieval such template chunk.
     */
    protected void setOutParameterRetrieval(String outParameterRetrieval)
    {
        inmutableSetOutParameterRetrieval(outParameterRetrieval);
    }

    /**
     * Retrieves the OUT parameter retrieval.
     * @return such information.
     */
    public String getOutParameterRetrieval() 
    {
        return m__strOutParameterRetrieval;
    }

    /**
     * Specifies the value object construction.
     * @param valueObjectConstruction such template chunk.
     */
    private void inmutableSetValueObjectConstruction(String valueObjectConstruction)
    {
        m__strValueObjectConstruction = valueObjectConstruction;
    }

    /**
     * Specifies the value object construction.
     * @param valueObjectConstruction such template chunk.
     */
    protected void setValueObjectConstruction(String valueObjectConstruction)
    {
        inmutableSetValueObjectConstruction(valueObjectConstruction);
    }

    /**
     * Retrieves the value object construction.
     * @return such information.
     */
    public String getValueObjectConstruction() 
    {
        return m__strValueObjectConstruction;
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
     * Specifies the procedures metadata.
     * @param proceduresMetaData the procedures metadata.
     */
    private void inmutableSetProceduresMetaData(List proceduresMetaData)
    {
        m__lProceduresMetaData = proceduresMetaData;
    }

    /**
     * Specifies the procedures metadata.
     * @param proceduresMetaData the procedures metaData.
     */
    protected void setProceduresMetaData(List proceduresMetaData)
    {
        inmutableSetProceduresMetaData(proceduresMetaData);
    }

    /**
     * Retrieves the procedures metadata.
     * @return such collection.
     */
    public List getProceduresMetaData()
    {
        return m__lProceduresMetaData;
    }

    /**
     * Adds a new procedure metadata.
     * @param procedureMetaData the new procedure metadata.
     */
    public void addProcedureMetaData(ProcedureMetaData procedureMetaData)
    {
        if  (procedureMetaData != null)
        {
            List t_lProceduresMetaData = getProceduresMetaData();

            if  (t_lProceduresMetaData != null)
            {
                t_lProceduresMetaData.add(procedureMetaData);
            }
        }
    }

    /**
     * Specifies the procedure parameters metadata.
     * @param parametersMetaData the parameters map.
     */
    private void inmutableSetProcedureParametersMetaData(Map parametersMetaData)
    {
        m__mProcedureParametersMetaData = parametersMetaData;
    }

    /**
     * Specifies the procedure parameters metadata.
     * @param parametersMetaData the parameters map.
     */
    protected void setProcedureParametersMetaData(Map parametersMetaData)
    {
        inmutableSetProcedureParametersMetaData(parametersMetaData);
    }

    /**
     * Retrieves the procedure parameters metadata.
     * @return such metadata.
     */
    public Map getProcedureParametersMetaData()
    {
        return m__mProcedureParametersMetaData;
    }

    /**
     * Adds a new procedure metadata.
     * @param procedureMetaData the procedure metadata.
     * @param parametersMetaData the parameters metadata.
     */
    public void addProcedureParametersMetaData(
        ProcedureMetaData procedureMetaData, ProcedureParameterMetaData[] parametersMetaData)
    {
        if  (   (procedureMetaData  != null)
             && (parametersMetaData != null))
        {
            Map t_mParametersMetaData = getProcedureParametersMetaData();

            if  (t_mParametersMetaData != null)
            {
                t_mParametersMetaData.put(buildKey(procedureMetaData), parametersMetaData);
            }
        }
    }

    /**
     * Retrieves the procedure parameters metadata.
     * @param procedureMetaData the procedure metadata.
     * @return the associated parameters metadata.
     */
    public ProcedureParameterMetaData[] getProcedureParametersMetaData(
        ProcedureMetaData procedureMetaData)
    {
        ProcedureParameterMetaData[] result =
            DatabaseMetaDataManager.EMPTY_PROCEDURE_PARAMETER_METADATA_ARRAY;

        if  (procedureMetaData != null)
        {
            Map t_mParametersMetaData = getProcedureParametersMetaData();

            if  (t_mParametersMetaData != null)
            {
                result =
                    (ProcedureParameterMetaData[])
                        t_mParametersMetaData.get(
                            buildKey(procedureMetaData));
            }
        }

        return result;
    }

    /**
     * Builds the key based on the procedure metadata.
     * @param procedureMetaData the procedure metadata.
     * @return the associated key.
     */
    protected Object buildKey(ProcedureMetaData procedureMetaData)
    {
        Object result = procedureMetaData;

        if  (procedureMetaData != null)
        {
            result = "procedure." + procedureMetaData.getName();
        }

        return result;
    }

    /**
     * Retrieves the source code of the generated procedure repository.
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

            t_sbResult.append(getProjectImportsJavadoc());
            t_sbResult.append(getProjectImports());
            t_sbResult.append(getAcmslImports());
            t_sbResult.append(getJdkImports());

            t_Formatter = new MessageFormat(getJavadoc());
            t_sbResult.append(t_Formatter.format(t_aRepository));

            t_Formatter = new MessageFormat(getClassDefinition());
            t_sbResult.append(
                t_Formatter.format(
                    new Object[]
                    {
                          t_StringUtils.normalize(getRepository(), '_')
                        + "ProcedureRepository"
                    }));

            t_sbResult.append(getClassStart());

            MetaDataUtils t_MetaDataUtils = MetaDataUtils.getInstance();

            List t_lProceduresMetaData = getProceduresMetaData();

            if  (   (t_lProceduresMetaData != null)
                 && (t_MetaDataUtils       != null))
            {
                Iterator t_itProceduresMetaData = t_lProceduresMetaData.iterator();

                MessageFormat t_JavadocFormatter =
                    new MessageFormat(getProcedureJavadoc());

                MessageFormat t_ParameterJavadocFormatter =
                    new MessageFormat(getProcedureParameterJavadoc());

                MessageFormat t_ParameterDeclarationFormatter =
                    new MessageFormat(getProcedureParameterDeclaration());

                MessageFormat t_ProcedureSentenceFormatter =
                    new MessageFormat(getProcedureSentence());

                MessageFormat t_OutParameterRegistrationFormatter =
                    new MessageFormat(getOutParameterRegistration());

                MessageFormat t_InParameterSpecificationFormatter =
                    new MessageFormat(getInParameterSpecification());

                MessageFormat t_ValueObjectConstructionFormatter =
                    new MessageFormat(getValueObjectConstruction());

                String t_strComment = null;

                int t_iReturnType = -1;

                while  (t_itProceduresMetaData.hasNext())
                {
                    ProcedureMetaData t_ProcedureMetaData =
                        (ProcedureMetaData) t_itProceduresMetaData.next();

                    ProcedureParameterMetaData[] t_aProcedureParametersMetaData =
                        getProcedureParametersMetaData(t_ProcedureMetaData);

                    if  (   (t_ProcedureMetaData            != null)
                         && (t_aProcedureParametersMetaData != null))
                    {
                        StringBuffer t_sbParametersJavadoc = new StringBuffer();
                        StringBuffer t_sbParametersDeclaration = new StringBuffer();
                        StringBuffer t_sbProcedureSentenceParameters = new StringBuffer();
                        StringBuffer t_sbOutParametersRegistration = new StringBuffer();
                        StringBuffer t_sbInParametersSpecification = new StringBuffer();
                        StringBuffer t_sbOutParametersRetrieval = new StringBuffer();
                        StringBuffer t_sbValueObjectConstruction = new StringBuffer();

                        int t_iInParameterIndex = 1;

                        for  (int t_iIndex = 0;
                                  t_iIndex < t_aProcedureParametersMetaData.length;
                                  t_iIndex++)
                        {
                            if  (t_aProcedureParametersMetaData[t_iIndex] != null)
                            {
                                if  (   (   t_aProcedureParametersMetaData[t_iIndex].getType()
                                         == ProcedureParameterMetaData.IN_PARAMETER)
                                    || (   t_aProcedureParametersMetaData[t_iIndex].getType()
                                         == ProcedureParameterMetaData.IN_OUT_PARAMETER))
                                {
                                    if  (t_iInParameterIndex > 2)
                                    {
                                        t_sbProcedureSentenceParameters.append(",");
                                    }

                                    t_sbProcedureSentenceParameters.append("?");

                                    t_sbInParametersSpecification.append(
                                        t_InParameterSpecificationFormatter.format(
                                            new Object[]
                                            {
                                                t_MetaDataUtils.getSetterMethod(
                                                    t_aProcedureParametersMetaData[t_iIndex].getDataType(),
                                                    t_iInParameterIndex,
                                                    t_aProcedureParametersMetaData[t_iIndex].getName().toLowerCase())
                                            }));
                                }

                                if  (   (t_aProcedureParametersMetaData[t_iIndex].getName() != null)
                                     && (   (   t_aProcedureParametersMetaData[t_iIndex].getType()
                                             == ProcedureParameterMetaData.IN_PARAMETER)
                                         || (   t_aProcedureParametersMetaData[t_iIndex].getType()
                                             == ProcedureParameterMetaData.IN_OUT_PARAMETER)))
                                {
                                    t_strComment =
                                        t_aProcedureParametersMetaData[t_iIndex].getComment();

                                    if  (t_strComment == null)
                                    {
                                        t_strComment = "No documentation available.";
                                    }
                                
                                    t_sbParametersJavadoc.append(
                                        t_ParameterJavadocFormatter.format(
                                            new Object[]
                                            {
                                                t_aProcedureParametersMetaData[t_iIndex].getName().toLowerCase(),
                                                t_strComment
                                            }));                                        
                                }

                                if  (   t_aProcedureParametersMetaData[t_iIndex].getType()
                                     == ProcedureParameterMetaData.RESULT_PARAMETER)
                                {
                                    t_iReturnType =
                                        t_aProcedureParametersMetaData[t_iIndex].getDataType();
                                }

                                t_iInParameterIndex++;
                            }
                        }

                        int t_iInParameterCount = t_iInParameterIndex;

                        int t_iParameterIndex = t_iInParameterCount;

                        for  (int t_iIndex = 0;
                                  t_iIndex < t_aProcedureParametersMetaData.length;
                                  t_iIndex++)
                        {
                            if  (t_aProcedureParametersMetaData[t_iIndex] != null)
                            {
                                if  (   t_aProcedureParametersMetaData[t_iIndex].getType()
                                     == ProcedureParameterMetaData.RESULT_PARAMETER)
                                {
                                    t_iReturnType =
                                        t_aProcedureParametersMetaData[t_iIndex].getType();
                                }

                                if  (   (   t_aProcedureParametersMetaData[t_iIndex].getName()
                                         != null)
                                     && (   (   t_aProcedureParametersMetaData[t_iIndex].getType()
                                             == ProcedureParameterMetaData.OUT_PARAMETER)
                                         || (   t_aProcedureParametersMetaData[t_iIndex].getType()
                                             == ProcedureParameterMetaData.IN_OUT_PARAMETER)))
                                {
                                    t_sbOutParametersRegistration.append(
                                        t_OutParameterRegistrationFormatter.format(
                                            new Object[]
                                            {
                                                new Integer(t_iIndex + 1),
                                                t_MetaDataUtils.getConstantName(
                                                    t_aProcedureParametersMetaData[t_iIndex].getType())
                                            }));

                                    t_sbValueObjectConstruction.append(
                                        t_ValueObjectConstructionFormatter.format(
                                            new Object[]
                                            {
                                                t_MetaDataUtils.getObjectType(
                                                    t_aProcedureParametersMetaData[t_iIndex].getDataType()),
                                                t_aProcedureParametersMetaData[t_iIndex].getName().toLowerCase(),
                                                t_StringUtils.capitalize(
                                                    t_MetaDataUtils.getObjectType(
                                                        t_aProcedureParametersMetaData[t_iIndex].getDataType()),
                                                        '_'),
                                                new Integer(t_iIndex + 1)
                                            }));

                                    t_iParameterIndex++;
                                }
                            }
                        }

                        int t_iOutParameterCount = t_iParameterIndex - t_iInParameterIndex;

                        t_iInParameterIndex = 1;

                        boolean t_bFirstInParameter = true;

                        for  (int t_iIndex = 0;
                                  t_iIndex < t_aProcedureParametersMetaData.length;
                                  t_iIndex++)
                        {
                            if  (t_aProcedureParametersMetaData[t_iIndex] != null)
                            {
                                if  (   (t_aProcedureParametersMetaData[t_iIndex].getName() != null)
                                     && (   (   t_aProcedureParametersMetaData[t_iIndex].getType()
                                             == ProcedureParameterMetaData.IN_PARAMETER)
                                         || (   t_aProcedureParametersMetaData[t_iIndex].getType()
                                             == ProcedureParameterMetaData.IN_OUT_PARAMETER)))
                                {
                                    if  (t_bFirstInParameter)
                                    {
                                        t_sbParametersDeclaration.append(",");
                                        t_bFirstInParameter = false;
                                    }
                                    
                                    t_sbParametersDeclaration.append(
                                        t_ParameterDeclarationFormatter.format(
                                            new Object[]
                                            {
                                                t_MetaDataUtils.getNativeType(
                                                    t_aProcedureParametersMetaData[t_iIndex].getDataType()),
                                                t_aProcedureParametersMetaData[t_iIndex].getName().toLowerCase()
                                                + (  (  t_iInParameterIndex + t_iOutParameterCount
                                                      < t_aProcedureParametersMetaData.length)
                                                   ? ","
                                                   : "")
                                            }));
                                }

                                t_iInParameterIndex++;
                            }
                        }

                        t_strComment = t_ProcedureMetaData.getComment();

                        if  (t_strComment == null)
                        {
                            t_strComment = "No documentation available.";
                        }
                                
                        t_sbResult.append(
                            t_JavadocFormatter.format(
                                new Object[]
                                {
                                    t_strComment,
                                    t_sbParametersJavadoc.toString()}
                                ));

                        String t_strReturnType =
                            t_MetaDataUtils.getProcedureResultType(t_iReturnType);

                        MessageFormat t_ProcedureBodyFormatter =
                            new MessageFormat(getProcedureBody());

                        t_sbResult.append(
                            t_ProcedureBodyFormatter.format(
                                new Object[]
                                {
                                    t_strReturnType,
                                    t_ProcedureMetaData.getName().toLowerCase(),
                                    t_sbParametersDeclaration.toString(),
                                    t_MetaDataUtils.getProcedureDefaultValue(t_iReturnType),
                                    t_ProcedureSentenceFormatter.format(
                                        new Object[]
                                        {
                                            t_ProcedureMetaData.getName(),
                                            t_sbProcedureSentenceParameters.toString()
                                        }),
                                    t_sbOutParametersRegistration.toString(),
                                    t_sbInParametersSpecification.toString(),
                                    t_sbOutParametersRetrieval.toString(),
                                    t_sbValueObjectConstruction.toString(),
                                    t_strReturnType,
                                    t_MetaDataUtils.getGetterMethod(t_iReturnType, 1),
                                    t_ProcedureMetaData.getName()
                                }));
                    }
                }
            }

            t_sbResult.append(getClassEnd());
        }

        return t_sbResult.toString();
    }
}
