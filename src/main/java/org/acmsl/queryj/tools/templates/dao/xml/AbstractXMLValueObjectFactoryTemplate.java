//;-*- mode: java -*-
/*
                        QueryJ

    Copyright (C) 2002-today  Jose San Leandro Armendariz
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
    Contact info: jose.sanleandro@acm-sl.com
 * Author: Jose San Leandro Armendariz
 *
 * Description: Is able to generate XML-specific value object factories.
 *
 */
package org.acmsl.queryj.tools.templates.dao.xml;

/*
 * Importing project-specific classes.
 */
import org.acmsl.queryj.tools.metadata.DecoratorFactory;
import org.acmsl.queryj.tools.metadata.MetadataManager;
import org.acmsl.queryj.tools.templates.AbstractTemplate;
import org.acmsl.queryj.tools.templates.TableTemplate;

/*
 * Importing StringTemplate classes.
 */
import org.antlr.stringtemplate.StringTemplateGroup;

/*
 * Importing JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Is able to generate value object factories according to
 * database metadata.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public abstract class AbstractXMLValueObjectFactoryTemplate
    extends  AbstractTemplate
{
    /**
     * The package declaration.
     */
    private String m__strPackageDeclaration;

    /**
     * The package name.
     */
    private String m__strPackageName;

    /**
     * The value object package name.
     */
    private String m__strValueObjectPackageName;

    /**
     * The table template.
     */
    private TableTemplate m__TableTemplate;

    /**
     * The metadata manager.
     */
    private MetadataManager m__MetadataManager;

    /**
     * The project import statements.
     */
    private String m__strProjectImports;

    /**
     * The ACM-SL import statements.
     */
    private String m__strAcmSlImports;

    /**
     * The JDK import statements.
     */
    private String m__strJdkImports;

    /**
     * The extra import statements.
     */
    private String m__strExtraImports;

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
     * The factory method.
     */
    private String m__strFactoryMethod;

    /**
     * The factory method attribute build.
     */
    private String m__strFactoryMethodAttributeBuild;

    /**
     * The factory method value object build.
     */
    private String m__strFactoryMethodValueObjectBuild;

    /**
     * The conversion method suffix for nullable values.
     */
    private String m__strConversionMethodSuffixForNullableValues;

    /**
     * The extra methods.
     */
    private String m__strExtraMethods;

    /**
     * The class end.
     */
    private String m__strClassEnd;

    /**
     * Builds an <code>AbstractXMLValueObjectFactoryTemplate</code>
     * using given information.
     * @param packageDeclaration the package declaration.
     * @param packageName the package name.
     * @param valueObjectPackageName the value object package name.
     * @param tableTemplate the table template.
     * @param metadataManager the metadata manager.
     * @param header the header.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @param projectImports the project imports.
     * @param acmSlImports the ACM-SL imports.
     * @param jdkImports the JDK imports.
     * @param extraImports the extra imports.
     * @param javadoc the class Javadoc.
     * @param classDefinition the class definition.
     * @param classStart the class start.
     * @param singletonBody the singleton body.
     * @param factoryMethod the factory method.
     * @param factoryMethodAttributeBuild the factory method attribute build.
     * @param factoryMethodValueObjectBuild the factory method value object build.
     * @param conversionMethodSuffixForNullableValues the conversion method
     * suffix for nullable values.
     * @param extraMethods the extra methods.
     * @param classEnd the class end.
     */
    public AbstractXMLValueObjectFactoryTemplate(
        final String packageDeclaration,
        final String packageName,
        final String valueObjectPackageName,
        final TableTemplate tableTemplate,
        final MetadataManager metadataManager,
        final String header,
        final DecoratorFactory decoratorFactory,
        final String projectImports,
        final String acmSlImports,
        final String jdkImports,
        final String extraImports,
        final String javadoc,
        final String classDefinition,
        final String classStart,
        final String singletonBody,
        final String factoryMethod,
        final String factoryMethodAttributeBuild,
        final String factoryMethodValueObjectBuild,
        final String conversionMethodSuffixForNullableValues,
        final String extraMethods,
        final String classEnd)
    {
        super(header, decoratorFactory);
        immutableSetPackageDeclaration(packageDeclaration);
        immutableSetPackageName(packageName);
        immutableSetValueObjectPackageName(valueObjectPackageName);
        immutableSetTableTemplate(tableTemplate);
        immutableSetMetadataManager(metadataManager);
        immutableSetProjectImports(projectImports);
        immutableSetAcmSlImports(acmSlImports);
        immutableSetJdkImports(jdkImports);
        immutableSetExtraImports(extraImports);
        immutableSetJavadoc(javadoc);
        immutableSetClassDefinition(classDefinition);
        immutableSetClassStart(classStart);
        immutableSetSingletonBody(singletonBody);
        immutableSetFactoryMethod(factoryMethod);
        immutableSetFactoryMethodAttributeBuild(factoryMethodAttributeBuild);
        immutableSetFactoryMethodValueObjectBuild(factoryMethodValueObjectBuild);
        immutableSetConversionMethodSuffixForNullableValues(
            conversionMethodSuffixForNullableValues);
        immutableSetExtraMethods(extraMethods);
        immutableSetClassEnd(classEnd);
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
     * Specifies the value object package name.
     * @param packageName the new package name.
     */
    private void immutableSetValueObjectPackageName(final String packageName)
    {
        m__strValueObjectPackageName = packageName;
    }

    /**
     * Specifies the value object package name.
     * @param packageName the new package name.
     */
    protected void setValueObjectPackageName(final String packageName)
    {
        immutableSetValueObjectPackageName(packageName);
    }

    /**
     * Retrieves the value object package name.
     * @return such information.
     */
    public String getValueObjectPackageName() 
    {
        return m__strValueObjectPackageName;
    }

    /**
     * Specifies the table template.
     * @param tableTemplate the new table template.
     */
    private void immutableSetTableTemplate(TableTemplate tableTemplate)
    {
        m__TableTemplate = tableTemplate;
    }

    /**
     * Specifies the table template.
     * @param tableTemplate the new table template.
     */
    protected void setTableTemplate(TableTemplate tableTemplate)
    {
        immutableSetTableTemplate(tableTemplate);
    }

    /**
     * Retrieves the table template.
     * @return such information.
     */
    public TableTemplate getTableTemplate()
    {
        return m__TableTemplate;
    }

    /**
     * Specifies the metadata manager.
     * @param metadataManager the new metadata manager.
     */
    private void immutableSetMetadataManager(
        MetadataManager metadataManager)
    {
        m__MetadataManager = metadataManager;
    }

    /**
     * Specifies the metadata manager.
     * @param metadataManager the new metadata manager.
     */
    protected void setMetadataManager(
        MetadataManager metadataManager)
    {
        immutableSetMetadataManager(metadataManager);
    }

    /**
     * Retrieves the metadata manager.
     * @return such information.
     */
    public MetadataManager getMetadataManager()
    {
        return m__MetadataManager;
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
     * @param acmslImports the new project imports.
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
    private void immutableSetAcmSlImports(final String acmslImports)
    {
        m__strAcmSlImports = acmslImports;
    }

    /**
     * Specifies the ACM-SL imports.
     * @param acmslImports the new ACM-SL imports.
     */
    protected void setAcmSlImports(final String acmslImports)
    {
        immutableSetAcmSlImports(acmslImports);
    }

    /**
     * Retrieves the ACM-SL imports.
     * @return such information.
     */
    public String getAcmSlImports() 
    {
        return m__strAcmSlImports;
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
     * Specifies the extra imports.
     * @param extraImports the new extra imports.
     */
    private void immutableSetExtraImports(final String extraImports)
    {
        m__strExtraImports = extraImports;
    }

    /**
     * Specifies the extra imports.
     * @param acmslImports the new extra imports.
     */
    protected void setExtraImports(final String extraImports)
    {
        immutableSetExtraImports(extraImports);
    }

    /**
     * Retrieves the extra imports.
     * @return such information.
     */
    public String getExtraImports() 
    {
        return m__strExtraImports;
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
     * Specifies the singleton body.
     * @param singletonBody the singleton body.
     */
    private void immutableSetSingletonBody(final String singletonBody)
    {
        m__strSingletonBody = singletonBody;
    }

    /**
     * Specifies the singleton body.
     * @param singletonBody the singleton body.
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
     * Specifies the factory method.
     * @param factoryMethod such source code.
     */
    private void immutableSetFactoryMethod(final String factoryMethod)
    {
        m__strFactoryMethod = factoryMethod;
    }

    /**
     * Specifies the factory method.
     * @param factoryMethod such source code.
     */
    protected void setFactoryMethod(final String factoryMethod)
    {
        immutableSetFactoryMethod(factoryMethod);
    }

    /**
     * Retrieves the factory method.
     * @return such source code.
     */
    public String getFactoryMethod()
    {
        return m__strFactoryMethod;
    }

    /**
     * Specifies the factory method attribute build.
     * @param attributeBuild the new factory method attribute build.
     */
    private void immutableSetFactoryMethodAttributeBuild(
        String attributeBuild)
    {
        m__strFactoryMethodAttributeBuild = attributeBuild;
    }

    /**
     * Specifies the factory method attribute build.
     * @param attributeBuild the new factory method attribute build.
     */
    protected void setFactoryMethodAttributeBuild(final String attributeBuild)
    {
        immutableSetFactoryMethodAttributeBuild(attributeBuild);
    }

    /**
     * Retrieves the factory method attribute build.
     * @return such information.
     */
    public String getFactoryMethodAttributeBuild() 
    {
        return m__strFactoryMethodAttributeBuild;
    }

    /**
     * Specifies the factory method value object build.
     * @param valueValueObjectBuild the new factory method value object build.
     */
    private void immutableSetFactoryMethodValueObjectBuild(
        String valueObjectBuild)
    {
        m__strFactoryMethodValueObjectBuild = valueObjectBuild;
    }

    /**
     * Specifies the factory method value object build.
     * @param valueValueObjectBuild the new factory method value object build.
     */
    protected void setFactoryMethodValueObjectBuild(final String valueObjectBuild)
    {
        immutableSetFactoryMethodValueObjectBuild(valueObjectBuild);
    }

    /**
     * Retrieves the factory method value object build.
     * @return such information.
     */
    public String getFactoryMethodValueObjectBuild() 
    {
        return m__strFactoryMethodValueObjectBuild;
    }

    /**
     * Specifies the conversion method suffix for nullable values.
     * @param suffix such value.
     */
    protected final void immutableSetConversionMethodSuffixForNullableValues(
        final String suffix)
    {
        m__strConversionMethodSuffixForNullableValues = suffix;
    }

    /**
     * Specifies the conversion method suffix for nullable values.
     * @param suffix such value.
     */
    protected void setConversionMethodSuffixForNullableValues(
        final String suffix)
    {
        immutableSetConversionMethodSuffixForNullableValues(suffix);
    }

    /**
     * Retrieves the conversion method suffix for nullable values.
     * @return such suffix.
     */
    public String getConversionMethodSuffixForNullableValues()
    {
        return m__strConversionMethodSuffixForNullableValues;
    }

    /**
     * Specifies the extra methods.
     * @param extraMethods the new extra methods.
     */
    private void immutableSetExtraMethods(final String extraMethods)
    {
        m__strExtraMethods = extraMethods;
    }

    /**
     * Specifies the extra methods.
     * @param acmslMethods the new extra methods.
     */
    protected void setExtraMethods(final String extraMethods)
    {
        immutableSetExtraMethods(extraMethods);
    }

    /**
     * Retrieves the extra methods.
     * @return such information.
     */
    public String getExtraMethods() 
    {
        return m__strExtraMethods;
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
    @NotNull
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
    @NotNull
    protected String buildHeader(@NotNull final TableTemplate tableTemplate)
    {
        return
              "Generating XML DAO Factory for "
            + tableTemplate.getTableName() + ".";
    }

    /**
     * Retrieves the string template group.
     *
     * @return such instance.
     */
    @Override
    @Nullable
    public StringTemplateGroup retrieveGroup()
    {
        return null;
    }
}
