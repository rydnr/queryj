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
 * Description: Contains the elements required to create the
 *              PkStatementSetter sources.
 *
 */
package org.acmsl.queryj.tools.templates.dao;

/*
 * Importing some project-specific classes.
 */
import org.acmsl.queryj.tools.metadata.MetadataManager;
import org.acmsl.queryj.tools.templates.AbstractTemplate;
import org.acmsl.queryj.tools.templates.TableTemplate;

/**
 * Contains the elements required to create the PkStatementSetter sources.
 * @author <a href="mailto:chous@acm-sl.org"
 *         >Jose San Leandro</a>
 */
public abstract class AbstractPkStatementSetterTemplate
    extends  AbstractTemplate
{
    /**
     * The table template.
     */
    private TableTemplate m__TableTemplate;

    /**
     * The database metadata manager.
     */
    private MetadataManager m__MetadataManager;

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
     * The base package name.
     */
    private String m__strBasePackageName;

    /**
     * The repository name.
     */
    private String m__strRepositoryName;

    /**
     * The project imports.
     */
    private String m__strProjectImports;

    /**
     * The ACM-SL import statements.
     */
    private String m__strAcmslImports;

    /**
     * The additional import statements.
     */
    private String m__strAdditionalImports;

    /**
     * The JDK import statements.
     */
    private String m__strJdkImports;

    /**
     * The JDK extension import statements.
     */
    private String m__strJdkExtensionImports;

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
     * The class constructor.
     */
    private String m__strClassConstructor;

    /**
     * The parameter setter call.
     */
    private String m__strParameterSetterCall;

    /**
     * The parameter accessor.
     */
    private String m__strParameterAccessor;

    /**
     * The setValues method.
     */
    private String m__strSetValuesMethod;

    /**
     * The parameter getter call.
     */
    private String m__strParameterGetterCall;

    /**
     * The parameter javadoc.
     */
    private String m__strParameterJavadoc;

    /**
     * The parameter declaration.
     */
    private String m__strParameterDeclaration;

    /**
     * The parameter specification.
     */
    private String m__strParameterSpecification;

    /**
     * The class end.
     */
    private String m__strClassEnd;

    /**
     * Builds an <code>AbstractPkStatementSetterTemplate</code> using
     * given information.
     * @param tableTemplate the table template.
     * @param metadataManager the database metadata manager.
     * @param header the header.
     * @param packageDeclaration the package declaration.
     * @param packageName the package name.
     * @param basePackageName the base package name.
     * @param repositoryName the repository name.
     * @param projectImports the project imports.
     * @param acmslImports the ACM-SL imports.
     * @param additionalImports the additional imports.
     * @param jdkImports the JDK imports.
     * @param jdkExtensionImports the JDK extension imports.
     * @param loggingImports the commons-logging imports.
     * @param javadoc the class Javadoc.
     * @param classDefinition the class definition.
     * @param classStart the class start.
     * @param classConstructor the class constructor.
     * @param parameterSetterCall the parameter setter call.
     * @param parameterAccessor the parameter accessor.
     * @param setValuesMethod the setValues method.
     * @param parameterGetterCall the parameter getter call.
     * @param parameterJavadoc the parameter javadoc.
     * @param parameterDeclaration the parameter declaration.
     * @param parameterSpecification the parameter specification.
     * @param classEnd the class end.
     */
    protected AbstractPkStatementSetterTemplate(
        final TableTemplate tableTemplate,
        final MetadataManager metadataManager,
        final String header,
        final String packageDeclaration,
        final String packageName,
        final String basePackageName,
        final String repositoryName,
        final String projectImports,
        final String additionalImports,
        final String acmslImports,
        final String jdkImports,
        final String jdkExtensionImports,
        final String loggingImports,
        final String javadoc,
        final String classDefinition,
        final String classStart,
        final String classConstructor,
        final String parameterSetterCall,
        final String parameterAccessor,
        final String setValuesMethod,
        final String parameterGetterCall,
        final String parameterJavadoc,
        final String parameterDeclaration,
        final String parameterSpecification,
        final String classEnd)
    {
        immutableSetTableTemplate(
            tableTemplate);

        immutableSetMetadataManager(
            metadataManager);

        immutableSetHeader(
            header);

        immutableSetPackageDeclaration(
            packageDeclaration);

        immutableSetPackageName(
            packageName);

        immutableSetBasePackageName(
            basePackageName);

        immutableSetRepositoryName(
            repositoryName);

        immutableSetProjectImports(
            projectImports);

        immutableSetAcmslImports(
            acmslImports);

        immutableSetAdditionalImports(
            additionalImports);

        immutableSetJdkImports(
            jdkImports);

        immutableSetJdkExtensionImports(
            jdkExtensionImports);

        immutableSetLoggingImports(
            loggingImports);

        immutableSetJavadoc(
            javadoc);

        immutableSetClassDefinition(
            classDefinition);

        immutableSetClassStart(
            classStart);

        immutableSetClassConstructor(classConstructor);

        immutableSetParameterSetterCall(parameterSetterCall);

        immutableSetParameterAccessor(parameterAccessor);

        immutableSetSetValuesMethod(setValuesMethod);

        immutableSetParameterGetterCall(parameterGetterCall);

        immutableSetParameterJavadoc(parameterJavadoc);

        immutableSetParameterDeclaration(parameterDeclaration);

        immutableSetParameterSpecification(
            parameterSpecification);

        immutableSetClassEnd(
            classEnd);
    }

    /**
     * Specifies the table template.
     * @param tableTemplate the table template.
     */
    private void immutableSetTableTemplate(final TableTemplate tableTemplate)
    {
        m__TableTemplate = tableTemplate;
    }

    /**
     * Specifies the table template.
     * @param tableTemplate the table template.
     */
    protected void setTableTemplate(final TableTemplate tableTemplate)
    {
        immutableSetTableTemplate(tableTemplate);
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
     * @param metadataManager the metadata manager.
     */
    private void immutableSetMetadataManager(
        final MetadataManager metadataManager)
    {
        m__MetadataManager = metadataManager;
    }

    /**
     * Specifies the metadata manager.
     * @param metadataManager the metadata manager.
     */
    protected void setMetadataManager(
        final MetadataManager metadataManager)
    {
        immutableSetMetadataManager(metadataManager);
    }

    /**
     * Retrieves the metadata manager.
     * @return such manager.
     */
    public MetadataManager getMetadataManager()
    {
        return m__MetadataManager;
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
    private void immutableSetPackageDeclaration(
        final String packageDeclaration)
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
     * Specifies the base package name.
     * @param basePackageName the new base package name.
     */
    private void immutableSetBasePackageName(final String basePackageName)
    {
        m__strBasePackageName = basePackageName;
    }

    /**
     * Specifies the base package name.
     * @param basePackageName the new base package name.
     */
    protected void setBasePackageName(final String basePackageName)
    {
        immutableSetBasePackageName(basePackageName);
    }

    /**
     * Retrieves the base package name.
     * @return such information.
     */
    public String getBasePackageName() 
    {
        return m__strBasePackageName;
    }

    /**
     * Specifies the repository name.
     * @param repositoryName the new repository name.
     */
    private void immutableSetRepositoryName(final String repositoryName)
    {
        m__strRepositoryName = repositoryName;
    }

    /**
     * Specifies the repository name.
     * @param repositoryName the new repository name.
     */
    protected void setRepositoryName(final String repositoryName)
    {
        immutableSetRepositoryName(repositoryName);
    }

    /**
     * Retrieves the repository name.
     * @return such information.
     */
    public String getRepositoryName()
    {
        return m__strRepositoryName;
    }

    /**
     * Specifies the project imports.
     * @param projectImports the new project imports.
     */
    private void immutableSetProjectImports(final String projectImports)
    {
        m__strProjectImports = projectImports;
    }

    /**
     * Specifies the project imports.
     * @param projectImports the new project imports.
     */
    protected void setProjectImports(final String projectImports)
    {
        immutableSetProjectImports(projectImports);
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
    private void immutableSetAcmslImports(final String acmslImports)
    {
        m__strAcmslImports = acmslImports;
    }

    /**
     * Specifies the ACM-SL imports.
     * @param acmslImports the new ACM-SL imports.
     */
    protected void setAcmslImports(final String acmslImports)
    {
        immutableSetAcmslImports(acmslImports);
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
     * Specifies the additional imports.
     * @param additionalImports the new additional imports.
     */
    private void immutableSetAdditionalImports(final String additionalImports)
    {
        m__strAdditionalImports = additionalImports;
    }

    /**
     * Specifies the additional imports.
     * @param additionalImports the new additional imports.
     */
    protected void setAdditionalImports(final String additionalImports)
    {
        immutableSetAdditionalImports(additionalImports);
    }

    /**
     * Retrieves the additional imports.
     * @return such information.
     */
    public String getAdditionalImports() 
    {
        return m__strAdditionalImports;
    }

    /**
     * Specifies the JDK imports.
     * @param jdkImports the new JDK imports.
     */
    private void immutableSetJdkImports(final String jdkImports)
    {
        m__strJdkImports = jdkImports;
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
        return m__strJdkImports;
    }

    /**
     * Specifies the JDK extension imports.
     * @param jdkExtensionImports the new JDK extension imports.
     */
    private void immutableSetJdkExtensionImports(
        final String jdkExtensionImports)
    {
        m__strJdkExtensionImports = jdkExtensionImports;
    }

    /**
     * Specifies the JDK extension imports.
     * @param jdkExtensionImports the new JDK extension imports.
     */
    protected void setJdkExtensionImports(final String jdkExtensionImports)
    {
        immutableSetJdkExtensionImports(jdkExtensionImports);
    }

    /**
     * Retrieves the JDK extension imports.
     * @return such information.
     */
    public String getJdkExtensionImports() 
    {
        return m__strJdkExtensionImports;
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
     * Specifies the class constructor.
     * @param template such template.
     */
    private void immutableSetClassConstructor(final String template)
    {
        m__strClassConstructor = template;
    }

    /**
     * Specifies the class constructor.
     * @param template such template.
     */
    protected void setClassConstructor(final String template)
    {
        immutableSetClassConstructor(template);
    }

    /**
     * Retrieves the class constructor.
     * @return such template.
     */
    public String getClassConstructor()
    {
        return m__strClassConstructor;
    }

    /**
     * Specifies the parameter setter call template.
     * @param template such template.
     */
    private void immutableSetParameterSetterCall(final String template)
    {
        m__strParameterSetterCall = template;
    }

    /**
     * Specifies the parameter setter call template.
     * @param template such template.
     */
    protected void setParameterSetterCall(final String template)
    {
        immutableSetParameterSetterCall(template);
    }

    /**
     * Retrieves the parameter setter call template.
     * @return such template.
     */
    public String getParameterSetterCall()
    {
        return m__strParameterSetterCall;
    }

    /**
     * Specifies the parameter accessor template.
     * @param template such template.
     */
    private void immutableSetParameterAccessor(final String template)
    {
        m__strParameterAccessor = template;
    }

    /**
     * Specifies the parameter accessor template.
     * @param template such template.
     */
    protected void setParameterAccessor(final String template)
    {
        immutableSetParameterAccessor(template);
    }

    /**
     * Retrieves the parameter accessor template.
     * @return such template.
     */
    public String getParameterAccessor()
    {
        return m__strParameterAccessor;
    }

    /**
     * Specifies the setValues method subtemplate.
     * @param method such method.
     */
    private void immutableSetSetValuesMethod(final String method)
    {
        m__strSetValuesMethod = method;
    }

    /**
     * Specifies the setValues method subtemplate.
     * @param method such method.
     */
    protected void setSetValuesMethod(final String method)
    {
        immutableSetSetValuesMethod(method);
    }

    /**
     * Retrieves the setValues method subtemplate.
     * @return such subtemplate.
     */
    public String getSetValuesMethod()
    {
        return m__strSetValuesMethod;
    }

    /**
     * Specifies the parameter getter call template.
     * @param template such template.
     */
    private void immutableSetParameterGetterCall(final String template)
    {
        m__strParameterGetterCall = template;
    }

    /**
     * Specifies the parameter getter call template.
     * @param template such template.
     */
    protected void setParameterGetterCall(final String template)
    {
        immutableSetParameterGetterCall(template);
    }

    /**
     * Retrieves the parameter getter call template.
     * @return such template.
     */
    public String getParameterGetterCall()
    {
        return m__strParameterGetterCall;
    }

    /**
     * Specifies the parameter javadoc template.
     * @param template such template.
     */
    private void immutableSetParameterJavadoc(final String template)
    {
        m__strParameterJavadoc = template;
    }

    /**
     * Specifies the parameter javadoc template.
     * @param template such template.
     */
    protected void setParameterJavadoc(final String template)
    {
        immutableSetParameterJavadoc(template);
    }

    /**
     * Retrieves the parameter javadoc template.
     * @return such template.
     */
    public String getParameterJavadoc()
    {
        return m__strParameterJavadoc;
    }

    /**
     * Specifies the parameter declaration template.
     * @param template such template.
     */
    private void immutableSetParameterDeclaration(final String template)
    {
        m__strParameterDeclaration = template;
    }

    /**
     * Specifies the parameter declaration template.
     * @param template such template.
     */
    protected void setParameterDeclaration(final String template)
    {
        immutableSetParameterDeclaration(template);
    }

    /**
     * Retrieves the parameter declaration template.
     * @return such template.
     */
    public String getParameterDeclaration()
    {
        return m__strParameterDeclaration;
    }

    /**
     * Specifies the parameter specification template.
     * @param template such template.
     */
    private void immutableSetParameterSpecification(final String template)
    {
        m__strParameterSpecification = template;
    }

    /**
     * Specifies the parameter specification template.
     * @param template such template.
     */
    protected void setParameterSpecification(final String template)
    {
        immutableSetParameterSpecification(template);
    }

    /**
     * Retrieves the parameter specification template.
     * @return such template.
     */
    public String getParameterSpecification()
    {
        return m__strParameterSpecification;
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
     * Builds the header for logging purposes.
     * @return such header.
     */
    protected String buildHeader()
    {
        return buildHeader(getTableTemplate());
    }

    /**
     * Builds the header for logging purposes.
     * @param tableTemplate the table template.
     * @return such header.
     * @precondition tableTemplate != null
     */
    protected String buildHeader(final TableTemplate tableTemplate)
    {
        return
              "Generating PkStatementSetter for "
            + tableTemplate.getTableName() + ".";
    }
}
