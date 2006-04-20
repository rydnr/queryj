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
 * Filename: $RCSfile: $
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
import org.acmsl.queryj.tools.metadata.DecoratorFactory;
import org.acmsl.queryj.tools.templates.AbstractKeywordRepositoryTemplate;
import org.acmsl.queryj.tools.templates.KeywordRepositoryTemplateDefaults;

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
           >Jose San Leandro</a>
 */
public class KeywordRepositoryTemplate
    extends  AbstractKeywordRepositoryTemplate
    implements  KeywordRepositoryTemplateDefaults
{
    /**
     * Builds a KeywordRepositoryTemplate using given information.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @param packageName the package name.
     * @param repository the repository.
     */
    public KeywordRepositoryTemplate(
        final DecoratorFactory decoratorFactory,
        final String packageName,
        final String repository)
    {
        super(
            decoratorFactory,
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
     * Retrieves the source code of the generated procedure repository.
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
                getAcmslImports(),
                getAcmslImportTemplate(),
                getJdkImports(),
                getJavadoc(),
                getClassDefinition(),
                getClassStart(),
                getSingletonBody(),
                getKeywordRetrievalMethodJavadoc(),
                getKeywordRetrievalMethodBody(),
                getClassEnd(),
                getKeywordsMetaDataList(),
                StringUtils.getInstance());
    }

    /**
     * Retrieves the source code of the generated procedure repository.
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
     * @param keywordsMetaData the keywords meta data.
     * @param stringUtils the <code>StringUtils</code> instance.
     * @return such source code.
     * @precondition stringUtils != null
     */
    protected String generateOutput(
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
        final String classEnd,
        final List keywordsMetaData,
        final StringUtils stringUtils)
    {
        StringBuffer t_sbResult = new StringBuffer();

        String t_strRepository = stringUtils.capitalize(repository);

        Object[] t_aRepository =
            new Object[]
            {
                t_strRepository
            };

        Object[] t_aPackageName = new Object[]{packageName};

        MessageFormat t_Formatter = new MessageFormat(header);
        t_sbResult.append(t_Formatter.format(t_aRepository));

        t_Formatter = new MessageFormat(packageDeclaration);
        t_sbResult.append(t_Formatter.format(t_aPackageName));

        StringBuffer t_sbAcmslImports = new StringBuffer();

        StringBuffer t_sbKeywordRetrievalMethods = new StringBuffer();

        List t_lKeywordsMetaData = getKeywordsMetaDataList();

        if  (t_lKeywordsMetaData != null)
        {
            Iterator t_itKeywordsMetaData = t_lKeywordsMetaData.iterator();

            MessageFormat t_AcmslImportTemplateFormatter =
                new MessageFormat(acmslImportTemplate);

            MessageFormat t_KeywordRetrievalMethodJavadocFormatter =
                new MessageFormat(keywordRetrievalMethodJavadoc);

            MessageFormat t_KeywordRetrievalMethodBodyFormatter =
                new MessageFormat(keywordRetrievalMethodBody);

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
                            stringUtils.capitalize(t_strKeyword),
                            t_strKeyword
                        }));
            }
        }

        t_Formatter = new MessageFormat(acmslImports);
        t_sbResult.append(
            t_Formatter.format(
                new Object[]
                {
                    t_sbAcmslImports
                }));

        t_sbResult.append(jdkImports);

        t_Formatter = new MessageFormat(javadoc);
        t_sbResult.append(t_Formatter.format(t_aRepository));

        t_Formatter = new MessageFormat(classDefinition);
        t_sbResult.append(
            t_Formatter.format(
                new Object[]
                {
                    t_strRepository + "KeywordRepository"
                }));

        t_sbResult.append(classStart);

        t_Formatter = new MessageFormat(singletonBody);
        t_sbResult.append(
            t_Formatter.format(
                new Object[]
                {
                    t_strRepository + "KeywordRepository"
                }));

        t_sbResult.append(t_sbKeywordRetrievalMethods);

        t_sbResult.append(classEnd);

        return t_sbResult.toString();
    }
}
