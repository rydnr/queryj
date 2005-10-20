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
 * Description: Is able to generate procedure repositories according to
 *              database metadata.
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
 * @author <a href="mailto:chous@acm-sl.org"
 *         >Jose San Leandro</a>
 */
public abstract class AbstractKeywordRepositoryTemplate
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
     * Builds an <code>AbstractKeywordRepositoryTemplate</code> using
     * given information.
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
    public AbstractKeywordRepositoryTemplate(
        final String header,
        final String packageDeclaration,
        final String packageName,
        final String repository,
        final String acmslImports,
        final String acmslImportTemplate,
        final String jdkImports,
        final String javadoc,
        final String classDefinition,
        final String classStart,
        final String singletonBody,
        final String keywordRetrievalMethodJavadoc,
        final String keywordRetrievalMethodBody,
        final String classEnd)
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
     * Specifies the header.
     * @param header the new header.
     */
    private void inmutableSetHeader(final String header)
    {
        m__strHeader = header;
    }

    /**
     * Specifies the header.
     * @param header the new header.
     */
    protected void setHeader(final String header)
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
    private void inmutableSetPackageDeclaration(final String packageDeclaration)
    {
        m__strPackageDeclaration = packageDeclaration;
    }

    /**
     * Specifies the package declaration.
     * @param packageDeclaration the new package declaration.
     */
    protected void setPackageDeclaration(final String packageDeclaration)
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
    private void inmutableSetPackageName(final String packageName)
    {
        m__strPackageName = packageName;
    }

    /**
     * Specifies the package name.
     * @param packageName the new package name.
     */
    protected void setPackageName(final String packageName)
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
    private void inmutableSetRepository(final String repository)
    {
        m__strRepository = repository;
    }

    /**
     * Specifies the repository.
     * @param repository the new repository.
     */
    protected void setRepository(final String repository)
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
    private void inmutableSetAcmslImports(final String acmslImports)
    {
        m__strAcmslImports = acmslImports;
    }

    /**
     * Specifies the ACM-SL imports.
     * @param acmslImports the new ACM-SL imports.
     */
    protected void setAcmslImports(final String acmslImports)
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
    private void inmutableSetAcmslImportTemplate(final String acmslImportTemplate)
    {
        m__strAcmslImportTemplate = acmslImportTemplate;
    }

    /**
     * Specifies the ACM-SL import template.
     * @param acmslImportTemplate the new ACM-SL import template.
     */
    protected void setAcmslImportTemplate(final String acmslImportTemplate)
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
    private void inmutableSetJdkImports(final String jdkImports)
    {
        m__strJdkImports = jdkImports;
    }

    /**
     * Specifies the JDK imports.
     * @param jdkImports the new JDK imports.
     */
    protected void setJdkImports(final String jdkImports)
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
    private void inmutableSetJavadoc(final String javadoc)
    {
        m__strJavadoc = javadoc;
    }

    /**
     * Specifies the javadoc.
     * @param javadoc the new javadoc.
     */
    protected void setJavadoc(final String javadoc)
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
    private void inmutableSetClassDefinition(final String classDefinition)
    {
        m__strClassDefinition = classDefinition;
    }

    /**
     * Specifies the class definition.
     * @param classDefinition the new class definition.
     */
    protected void setClassDefinition(final String classDefinition)
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
    private void inmutableSetClassStart(final String classStart)
    {
        m__strClassStart = classStart;
    }

    /**
     * Specifies the class start.
     * @param classStart the new class start.
     */
    protected void setClassStart(final String classStart)
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
    private void inmutableSetSingletonBody(final String singletonBody)
    {
        m__strSingletonBody = singletonBody;
    }

    /**
     * Specifies the singleton body.
     * @param singletonBody the new singleton body.
     */
    protected void setSingletonBody(final String singletonBody)
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
    protected void setKeywordRetrievalMethodBody(final String keywordRetrievalMethodBody)
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
    private void inmutableSetClassEnd(final String classEnd)
    {
        m__strClassEnd = classEnd;
    }

    /**
     * Specifies the class end.
     * @param classEnd the new class end.
     */
    protected void setClassEnd(final String classEnd)
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
    public void addKeyword(final String keyword, String fieldType)
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
    protected String getKeywordFieldType(final String keyword)
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
    protected Object buildKey(final String keyword)
    {
        Object result = keyword;

        if  (keyword != null)
        {
            result = "keyword." + keyword;
        }

        return result;
    }


    /**
     * Builds the header for logging purposes.
     * @return such header.
     */
    protected String buildHeader()
    {
        return "Generating KeywordRepository.";
    }
}
