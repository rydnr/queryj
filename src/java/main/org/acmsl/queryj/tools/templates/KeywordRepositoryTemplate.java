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
public abstract class KeywordRepositoryTemplate
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
        + " * Description: Contains all defined field creation keywords for\n"
        + " *              {0} repository.\n"
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
        + " * Importing some ACM-SL classes.\n"
        + " */\n"
        + "{0}"
        + "import org.acmsl.queryj.QueryJException;\n\n";

    /**
     * The ACM-SL import template.
     */
    public static final String DEFAULT_ACMSL_IMPORT_TEMPLATE =
        "import org.acmsl.queryj.{0};\n";
    
    /**
     * The JDK imports.
     */
    protected static final String DEFAULT_JDK_IMPORTS =
          "/*\n"
        + " * Importing some JDK classes.\n"
        + " */\n"
        + "import java.lang.ref.WeakReference;\n\n";

    /**
     * The default class Javadoc.
     */
    public static final String DEFAULT_JAVADOC =
          "/**\n"
        + " * Contains all defined field creation keywords for\n"
        + " * {0} repository.\n" // repository
        + " * @author <a href=\"http://queryj.sourceforge.net\">QueryJ</a>\n"
        + " * @version $" + "Revision: $\n"
        + " */\n";

    /**
     * The class definition.
     */
    public static final String CLASS_DEFINITION =
         "public abstract class {0}\n"; // class name

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
     * The class singleton logic.
     */
    protected static final String DEFAULT_SINGLETON_BODY =
          "    /**\n"
        + "     * Specifies a new weak reference.\n"
        + "     * @param repository the instance to use.\n"
        + "     */\n"
        + "    protected static void setReference({0} repository)\n"
         // class name
        + "    '{'\n"
        + "        singleton = new WeakReference(repository);\n"
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Retrieves the weak reference.\n"
        + "     * @return such reference.\n"
        + "     */\n"
        + "    protected static WeakReference getReference()\n"
        + "    '{'\n"
        + "        return singleton;\n"
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Retrieves a {0} instance.\n"
         // class name
        + "     * @return such instance.\n"
        + "     */\n"
        + "    public static {0} getInstance()\n"
        + "    '{'\n"
        + "        {0} result = null;\n\n"
         // class name 
       + "        WeakReference reference = getReference();\n\n"
        + "        if  (reference != null) \n"
        + "        '{'\n"
        + "            result = ({0}) reference.get();\n"
         // class name
        + "        '}'\n\n"
        + "        if  (result == null) \n"
        + "        '{'\n"
        + "            result = new {0}() '{' '}';\n\n"
         // class name
        + "            setReference(result);\n"
        + "        '}'\n\n"
        + "        return result;\n"
        + "    '}'\n\n";

    /**
     * The default keyword retrieval method Javadoc.
     */
    public static final String DEFAULT_KEYWORD_RETRIEVAL_METHOD_JAVADOC =
          "    /**\n"
        + "     * Represents {0} database-interpreted keyword\n" // procedure comment.
        + "     * @return the associated keyword field.\n"
        + "     */\n";

    /**
     * The keyword retrieval method body.
     */
    public static final String DEFAULT_KEYWORD_RETRIEVAL_METHOD_BODY =
          "    public {0} {1}()\n"
        + "    '{'\n"
        + "        return new {0}(\"{2}\", null) '{' '}';\n"
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
     * The ACM-SL import statements.
     */
    private String m__strAcmslImports;

    /**
     * The ACM-SL import statement template.
     */
    private String m__strAcmslImportTemplate;

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
     * The singleton body.
     */
    private String m__strSingletonBody;

    /**
     * The keyword retrieval method Javadoc.
     */
    private String m__strKeywordRetrievalMethodJavadoc;

    /**
     * The keyword retrieval method body.
     */
    private String m__strKeywordRetrievalMethodBody;

    /**
     * The class end.
     */
    private String m__strClassEnd;

    /**
     * The keywords metadata list.
     */
    private List m__lKeywordsMetaData;

    /**
     * The keywords metadata.
     */
    private Map m__mKeywordsMetaData;

    /**
     * Builds a KeywordRepositoryTemplate using given information.
     * @param header the header.
     * @param packageDeclaration the package declaration.
     * @param packageName the package name.
     * @param repository the repository.
     * @param acmslImports the ACM-SL imports.
     * @param acmslImportTemplate the ACM-SL import template.
     * @param jdkImports the JDK imports.
     * @param javadoc the class Javadoc.
     * @param classDefinition the class definition.
     * @param classStart the class start.
     * @param singletonBody the singleton body.
     * @param keywordRetrievalMethodJavadoc the keyword retrieval method Javadoc.
     * @param keywordRetrievalMethodBody the the keyword retrieval method body.
     * @param classEnd the class end.
     */
    public KeywordRepositoryTemplate(
        String header,
        String packageDeclaration,
        String packageName,
        String repository,
        String acmslImports,
        String acmslImportTemplate,
        String jdkImports,
        String javadoc,
        String classDefinition,
        String classStart,
        String singletonBody,
        String keywordRetrievalMethodJavadoc,
        String keywordRetrievalMethodBody,
        String classEnd)
    {
        inmutableSetHeader(header);
        inmutableSetPackageDeclaration(packageDeclaration);
        inmutableSetPackageName(packageName);
        inmutableSetRepository(repository);
        inmutableSetAcmslImports(acmslImports);
        inmutableSetAcmslImportTemplate(acmslImportTemplate);
        inmutableSetJdkImports(jdkImports);
        inmutableSetJavadoc(javadoc);
        inmutableSetClassDefinition(classDefinition);
        inmutableSetClassStart(classStart);
        inmutableSetSingletonBody(singletonBody);
        inmutableSetKeywordRetrievalMethodJavadoc(keywordRetrievalMethodJavadoc);
        inmutableSetKeywordRetrievalMethodBody(keywordRetrievalMethodBody);
        inmutableSetClassEnd(classEnd);
        inmutableSetKeywordsMetaDataList(new ArrayList());
        inmutableSetKeywordsMetaData(new HashMap());
    }

    /**
     * Builds a KeywordRepositoryTemplate using given information.
     * @param packageName the package name.
     * @param repository the repository.
     */
    public KeywordRepositoryTemplate(
        String packageName,
        String repository)
    {
        this(
            DEFAULT_HEADER,
            PACKAGE_DECLARATION,
            packageName,
            repository,
            ACMSL_IMPORTS,
            DEFAULT_ACMSL_IMPORT_TEMPLATE,
            DEFAULT_JDK_IMPORTS,
            DEFAULT_JAVADOC,
            CLASS_DEFINITION,
            DEFAULT_CLASS_START,
            DEFAULT_SINGLETON_BODY,
            DEFAULT_KEYWORD_RETRIEVAL_METHOD_JAVADOC,
            DEFAULT_KEYWORD_RETRIEVAL_METHOD_BODY,
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
     * Specifies the ACM-SL import template.
     * @param acmslImportTemplate the new ACM-SL import template.
     */
    private void inmutableSetAcmslImportTemplate(String acmslImportTemplate)
    {
        m__strAcmslImportTemplate = acmslImportTemplate;
    }

    /**
     * Specifies the ACM-SL import template.
     * @param acmslImportTemplate the new ACM-SL import template.
     */
    protected void setAcmslImportTemplate(String acmslImportTemplate)
    {
        inmutableSetAcmslImportTemplate(acmslImportTemplate);
    }

    /**
     * Retrieves the ACM-SL import template.
     * @return such information.
     */
    public String getAcmslImportTemplate()
    {
        return m__strAcmslImportTemplate;
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
    protected String getJdkImports() 
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
     * Specifies the keyword retrieval method Javadoc.
     * @param keywordRetrievalMethodJavadoc the new keyword retrieval method
     * Javadoc.
     */
    private void inmutableSetKeywordRetrievalMethodJavadoc(
        String keywordRetrievalMethodJavadoc)
    {
        m__strKeywordRetrievalMethodJavadoc = keywordRetrievalMethodJavadoc;
    }

    /**
     * Specifies the keyword retrieval method Javadoc.
     * @param keywordRetrievalMethodJavadoc the new keyword retrieval method
     * Javadoc.
     */
    protected void setKeywordRetrievalMethodJavadoc(
        String keywordRetrievalMethodJavadoc)
    {
        inmutableSetKeywordRetrievalMethodJavadoc(keywordRetrievalMethodJavadoc);
    }

    /**
     * Retrieves the keyword retrieval method Javadoc.
     * @return such information.
     */
    public String getKeywordRetrievalMethodJavadoc() 
    {
        return m__strKeywordRetrievalMethodJavadoc;
    }

    /**
     * Specifies the keyword retrieval method body.
     * @param keywordRetrievalMethodBody the new keyword retrieval method body.
     */
    private void inmutableSetKeywordRetrievalMethodBody(
        String keywordRetrievalMethodBody)
    {
        m__strKeywordRetrievalMethodBody = keywordRetrievalMethodBody;
    }

    /**
     * Specifies the keyword retrieval method body.
     * @param keywordRetrievalMethodBody the new keyword retrieval method body.
     */
    protected void setKeywordRetrievalMethodBody(String keywordRetrievalMethodBody)
    {
        inmutableSetKeywordRetrievalMethodBody(keywordRetrievalMethodBody);
    }

    /**
     * Retrieves the keyword retrieval method body.
     * @return such information.
     */
    public String getKeywordRetrievalMethodBody()
    {
        return m__strKeywordRetrievalMethodBody;
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
     * Specifies the keywords metadata.
     * @param keywordsMetaData the keywords metadata.
     */
    private void inmutableSetKeywordsMetaDataList(List keywordsMetaData)
    {
        m__lKeywordsMetaData = keywordsMetaData;
    }

    /**
     * Specifies the keywords metadata.
     * @param keywordsMetaData the keywords metaData.
     */
    protected void setKeywordsMetaDataList(List keywordsMetaData)
    {
        inmutableSetKeywordsMetaDataList(keywordsMetaData);
    }

    /**
     * Retrieves the keywords metadata.
     * @return such collection.
     */
    public List getKeywordsMetaDataList()
    {
        return m__lKeywordsMetaData;
    }

    /**
     * Adds a new keyword metadata.
     * @param keyword the keyword.
     * @param fieldType the field type.
     */
    public void addKeyword(String keyword, String fieldType)
    {
        if  (   (keyword   != null)
             && (fieldType != null))
        {
            List t_lKeywordsMetaData = getKeywordsMetaDataList();

            if  (t_lKeywordsMetaData != null)
            {
                t_lKeywordsMetaData.add(keyword);
            }

            Map t_mKeywordsMetaData = getKeywordsMetaData();

            if  (t_mKeywordsMetaData != null)
            {
                t_mKeywordsMetaData.put(buildKey(keyword), fieldType);
            }
        }
    }

    /**
     * Retrieves the field type of given keyword.
     * @param keyword the keyword.
     * @return the field type.
     */
    protected String getKeywordFieldType(String keyword)
    {
        String result = "";

        if  (keyword != null)
        {
            Map t_mKeywordsMetaData = getKeywordsMetaData();

            if  (t_mKeywordsMetaData != null)
            {
                result =
                      ""
                    + t_mKeywordsMetaData.get(buildKey(keyword));
            }
        }

        return result;
    }

    /**
     * Specifies the keyword parameters metadata.
     * @param keywordsMetaData the keywords map.
     */
    private void inmutableSetKeywordsMetaData(Map keywordsMetaData)
    {
        m__mKeywordsMetaData = keywordsMetaData;
    }

    /**
     * Specifies the keywords metadata.
     * @param keywordsMetaData the keywords map.
     */
    protected void setKeywordsMetaData(Map keywordsMetaData)
    {
        inmutableSetKeywordsMetaData(keywordsMetaData);
    }

    /**
     * Retrieves the keyword metadata.
     * @return such metadata.
     */
    protected Map getKeywordsMetaData()
    {
        return m__mKeywordsMetaData;
    }

    /**
     * Builds the key based on the keyword.
     * @param keyword the keyword.
     * @return the associated key.
     */
    protected Object buildKey(String keyword)
    {
        Object result = keyword;

        if  (keyword != null)
        {
            result = "keyword." + keyword;
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

            StringBuffer t_sbAcmslImports = new StringBuffer();

            StringBuffer t_sbKeywordRetrievalMethods = new StringBuffer();

            List t_lKeywordsMetaData = getKeywordsMetaDataList();

            if  (t_lKeywordsMetaData != null)
            {
                Iterator t_itKeywordsMetaData = t_lKeywordsMetaData.iterator();

                MessageFormat t_AcmslImportTemplateFormatter =
                    new MessageFormat(getAcmslImportTemplate());

                MessageFormat t_KeywordRetrievalMethodJavadocFormatter =
                    new MessageFormat(getKeywordRetrievalMethodJavadoc());

                MessageFormat t_KeywordRetrievalMethodBodyFormatter =
                    new MessageFormat(getKeywordRetrievalMethodBody());

                while  (t_itKeywordsMetaData.hasNext())
                {
                    String t_strKeyword =
                        "" + t_itKeywordsMetaData.next();

                    String t_strFieldType =
                        "" + getKeywordFieldType(t_strKeyword);

                    t_sbAcmslImports.append(
                        t_AcmslImportTemplateFormatter.format(
                            new Object[]
                            {
                                t_strFieldType
                            }));

                    t_sbKeywordRetrievalMethods.append(
                        t_KeywordRetrievalMethodJavadocFormatter.format(
                            new Object[]
                            {
                                t_strKeyword
                            }));

                    t_sbKeywordRetrievalMethods.append(
                        t_KeywordRetrievalMethodBodyFormatter.format(
                            new Object[]
                            {
                                t_strFieldType,
                                t_StringUtils.normalize(t_strKeyword, '_'),
                                t_strKeyword
                            }));
                }
            }

            t_Formatter = new MessageFormat(getAcmslImports());
            t_sbResult.append(
                t_Formatter.format(
                    new Object[]
                    {
                        t_sbAcmslImports
                    }));

            t_sbResult.append(getJdkImports());

            t_Formatter = new MessageFormat(getJavadoc());
            t_sbResult.append(t_Formatter.format(t_aRepository));

            t_Formatter = new MessageFormat(getClassDefinition());
            t_sbResult.append(
                t_Formatter.format(
                    new Object[]
                    {
                          t_StringUtils.normalize(getRepository(), '_')
                        + "KeywordRepository"
                    }));

            t_sbResult.append(getClassStart());

            t_Formatter = new MessageFormat(getSingletonBody());
            t_sbResult.append(
                t_Formatter.format(
                    new Object[]
                    {
                          t_StringUtils.normalize(getRepository(), '_')
                        + "KeywordRepository"
                    }));

            t_sbResult.append(t_sbKeywordRetrievalMethods);

            t_sbResult.append(getClassEnd());
        }

        return t_sbResult.toString();
    }
}
