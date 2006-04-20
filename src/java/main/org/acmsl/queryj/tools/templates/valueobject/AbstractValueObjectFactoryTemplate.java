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
 * Description: Contains the required subtemplates used to generate value
 *              object factories according to database metadata.
 *
 */
package org.acmsl.queryj.tools.templates.valueobject;

/*
 * Importing some project-specific classes.
 */
import org.acmsl.queryj.tools.metadata.DecoratorFactory;
import org.acmsl.queryj.tools.metadata.MetadataManager;
import org.acmsl.queryj.tools.templates.AbstractTemplate;
import org.acmsl.queryj.tools.templates.TableTemplate;

/**
 * Contains the required subtemplates used to generate value object factories
 * according to database metadata.
 * @author <a href="mailto:chous@acm-sl.org"
 *         >Jose San Leandro</a>
 */
public abstract class AbstractValueObjectFactoryTemplate
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
     * The factory method.
     */
    private String m__strFactoryMethod;

    /**
     * The factory method field javadoc.
     */
    private String m__strFactoryMethodFieldJavadoc;

    /**
     * The factory method field definition.
     */
    private String m__strFactoryMethodFieldDefinition;

    /**
     * The factory method value object build.
     */
    private String m__strFactoryMethodValueObjectBuild;

    /**
     * The factory alias method.
     */
    private String m__strFactoryAliasMethod;

    /**
     * The class end.
     */
    private String m__strClassEnd;

    /**
     * Builds an AbstractValueObjectFactoryTemplate using given information.
     * @param header the header.
     * @param packageDeclaration the package declaration.
     * @param packageName the package name.
     * @param tableTemplate the table template.
     * @param metadataManager the metadata manager.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @param projectImports the project imports.
     * @param jdkImports the JDK imports.
     * @param javadoc the class Javadoc.
     * @param classDefinition the class definition.
     * @param classStart the class start.
     * @param singletonBody the singleton body.
     * @param factoryMethod the factory method.
     * @param factoryMethodFieldJavadoc the factory method field Javadoc.
     * @param factoryMethodFieldDefinition the constructor field definition.
     * @param factoryMethodValueObjectBuild the factory method value object build.
     * @param factoryAliasMethod the factory alias method.
     * @param classEnd the class end.
     */
    public AbstractValueObjectFactoryTemplate(
        final String header,
        final String packageDeclaration,
        final String packageName,
        final TableTemplate tableTemplate,
        final MetadataManager metadataManager,
        final DecoratorFactory decoratorFactory,
        final String projectImports,
        final String jdkImports,
        final String javadoc,
        final String classDefinition,
        final String classStart,
        final String singletonBody,
        final String factoryMethod,
        final String factoryMethodFieldJavadoc,
        final String factoryMethodFieldDefinition,
        final String factoryMethodValueObjectBuild,
        final String factoryAliasMethod,
        final String classEnd)
    {
        super(decoratorFactory);
        immutableSetHeader(header);
        immutableSetPackageDeclaration(packageDeclaration);
        immutableSetPackageName(packageName);
        immutableSetTableTemplate(tableTemplate);
        immutableSetMetadataManager(metadataManager);
        immutableSetProjectImports(projectImports);
        immutableSetJdkImports(jdkImports);
        immutableSetJavadoc(javadoc);
        immutableSetClassDefinition(classDefinition);
        immutableSetClassStart(classStart);
        immutableSetSingletonBody(singletonBody);
        immutableSetFactoryMethod(factoryMethod);
        immutableSetFactoryMethodFieldJavadoc(factoryMethodFieldJavadoc);
        immutableSetFactoryMethodFieldDefinition(factoryMethodFieldDefinition);
        immutableSetFactoryMethodValueObjectBuild(factoryMethodValueObjectBuild);
        immutableSetFactoryAliasMethod(factoryAliasMethod);
        immutableSetClassEnd(classEnd);
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
        final MetadataManager metadataManager)
    {
        m__MetadataManager = metadataManager;
    }

    /**
     * Specifies the metadata manager.
     * @param metadataManager the new metadata manager.
     */
    protected void setMetadataManager(
        final MetadataManager metadataManager)
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
     * Specifies the ACM-SL imports.
     * @param acmslImports the new ACM-SL imports.
     */
    private void immutableSetProjectImports(final String acmslImports)
    {
        m__strProjectImports = acmslImports;
    }

    /**
     * Specifies the ACM-SL imports.
     * @param acmslImports the new ACM-SL imports.
     */
    protected void setProjectImports(final String acmslImports)
    {
        immutableSetProjectImports(acmslImports);
    }

    /**
     * Retrieves the ACM-SL imports.
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
     * Specifies the factory method field Javadoc.
     * @param fieldJavadoc the new factory method field Javadoc.
     */
    private void immutableSetFactoryMethodFieldJavadoc(final String fieldJavadoc)
    {
        m__strFactoryMethodFieldJavadoc = fieldJavadoc;
    }

    /**
     * Specifies the factory method field Javadoc.
     * @param fieldJavadoc the new factory method field Javadoc.
     */
    protected void setFactoryMethodFieldJavadoc(final String fieldJavadoc)
    {
        immutableSetFactoryMethodFieldJavadoc(fieldJavadoc);
    }

    /**
     * Retrieves the factory method field Javadoc.
     * @return such information.
     */
    public String getFactoryMethodFieldJavadoc() 
    {
        return m__strFactoryMethodFieldJavadoc;
    }

    /**
     * Specifies the factory method field definition.
     * @param fieldDefinition the new factory method field definition.
     */
    private void immutableSetFactoryMethodFieldDefinition(final String fieldDefinition)
    {
        m__strFactoryMethodFieldDefinition = fieldDefinition;
    }

    /**
     * Specifies the factory method field definition.
     * @param fieldDefinition the new factory method field definition.
     */
    protected void setFactoryMethodFieldDefinition(final String fieldDefinition)
    {
        immutableSetFactoryMethodFieldDefinition(fieldDefinition);
    }

    /**
     * Retrieves the factory method field definition.
     * @return such information.
     */
    public String getFactoryMethodFieldDefinition() 
    {
        return m__strFactoryMethodFieldDefinition;
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
     * Specifies the factory alias method.
     * @param factoryAliasMethod such source code.
     */
    private void immutableSetFactoryAliasMethod(final String factoryAliasMethod)
    {
        m__strFactoryAliasMethod = factoryAliasMethod;
    }

    /**
     * Specifies the factory alias method.
     * @param factoryAliasMethod such source code.
     */
    protected void setFactoryAliasMethod(final String factoryAliasMethod)
    {
        immutableSetFactoryAliasMethod(factoryAliasMethod);
    }

    /**
     * Retrieves the factory alias method.
     * @return such source code.
     */
    public String getFactoryAliasMethod()
    {
        return m__strFactoryAliasMethod;
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
              "Generating ValueObjectFactory for "
            + tableTemplate.getTableName() + ".";
    }
}
