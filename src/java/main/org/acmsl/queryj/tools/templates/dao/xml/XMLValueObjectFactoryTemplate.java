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
 * Description: Is able to generate XML-specific value object factories.
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
package org.acmsl.queryj.tools.templates.dao.xml;

/*
 * Importing project-specific classes.
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

/**
 * Is able to generate value object factories according to
 * database metadata.
 * @author <a href="mailto:jsanleandro@yahoo.es"
           >Jose San Leandro</a>
 * @version $Revision$
 */
public abstract class XMLValueObjectFactoryTemplate
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
        + " * Description: Is able to create {0} value objects according to\n"
        + " *              the information stored in the persistence domain.\n"
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
    public static final String DEFAULT_PROJECT_IMPORTS =
          "/*\n"
        + " * Importing some project classes.\n"
        + " */\n"
        + "import {0}.{1}ValueObject;\n" // package - Table
        + "import {0}.{1}ValueObjectFactory;\n\n"; // package - Table

    /**
     * The ACM-SL imports.
     */
    public static final String DEFAULT_ACMSL_IMPORTS =
          "/*\n"
        + " * Importing some ACM-SL classes.\n"
        + " */\n"
        + "import org.acmsl.commons.utils.ConversionUtils;\n\n";

    /**
     * The JDK imports.
     */
    public static final String DEFAULT_JDK_IMPORTS =
          "/*\n"
        + " * Importing some JDK classes.\n"
        + " */\n"
        + "import java.lang.ref.WeakReference;\n"
        + "import java.math.BigDecimal;\n"
        + "import java.util.Calendar;\n"
        + "import java.util.Date;\n\n";

    /**
     * The extra imports.
     */
    public static final String DEFAULT_EXTRA_IMPORTS =
          "/*\n"
        + " * Importing some additional classes.\n"
        + " */\n"
        + "import org.apache.commons.digester.Digester;\n"
        + "import org.apache.commons.digester.ObjectCreationFactory;\n"
        + "import org.xml.sax.Attributes;\n"
        + "import org.xml.sax.SAXException;\n\n";

    /**
     * The default class Javadoc.
     */
    public static final String DEFAULT_JAVADOC =
          "/**\n"
        + " * Adds support for creating {0} value objects from\n" // table
        + " * SAX attributes (as required by Digester-based parsing).\n"
        + " * @author <a href=\"http://maven.acm-sl.org/queryj\">QueryJ</a>\n"
        + " * @version $" + "Revision: $\n"
        + " */\n";

    /**
     * The class definition.
     */
    public static final String CLASS_DEFINITION =
          "public class XML{0}ValueObjectFactory\n"
        + "    extends {0}ValueObjectFactory\n"
        + "    implements ObjectCreationFactory\n"; // table

    /**
     * The class start.
     */
    public static final String DEFAULT_CLASS_START =
          "{\n"
        + "    /**\n"
        + "     * Singleton implemented as a weak reference.\n"
        + "     */\n"
        + "    private static WeakReference singleton;\n\n"
        + "    /**\n"
        + "     * Temporary Digester reference.\n"
        + "     */\n"
        + "    private Digester m__Digester;\n\n";

    /**
     * The class singleton logic.
     */
    public static final String DEFAULT_SINGLETON_BODY =
          "    /**\n"
        + "     * Protected constructor to avoid accidental instantiation.\n"
        + "     */\n"
        + "    protected XML{0}ValueObjectFactory() '{' '}';\n\n" // table
        + "    /**\n"
        + "     * Specifies a new weak reference.\n"
        + "     * @param factory the factory instance to use.\n"
        + "     */\n"
        + "    protected static void setReference(final XML{0}ValueObjectFactory factory)\n" // table
        + "    '{'\n"
        + "        singleton = new WeakReference(factory);\n"
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
        + "     * Retrieves a XML{0}ValueObjectFactory instance.\n"
        + "     * @return such instance.\n"
        + "     */\n"
        + "    public static XML{0}ValueObjectFactory getInstance()\n"
        + "    '{'\n"
        + "        XML{0}ValueObjectFactory result = null;\n\n"
        + "        WeakReference reference = getReference();\n\n"
        + "        if  (reference != null) \n"
        + "        '{'\n"
        + "            result = (XML{0}ValueObjectFactory) reference.get();\n"
        + "        '}'\n\n"
        + "        if  (result == null) \n"
        + "        '{'\n"
        + "            result = new XML{0}ValueObjectFactory();\n\n"
        + "            setReference(result);\n"
        + "        '}'\n\n"
        + "        return result;\n"
        + "    '}'\n\n";

    /**
     * The extra methods.
     */
    public static final String DEFAULT_EXTRA_METHODS =
          "    /**\n"
        + "     * Specifies a new Digester instance.\n"
        + "     * @param digester such instance.\n"
        + "     */\n"
        + "    public void setDigester(final Digester digester)\n"
        + "    {\n"
        + "        m__Digester = digester;\n"
        + "    }\n\n"
        + "    /**\n"
        + "     * Retrieves the Digester instance.\n"
        + "     * @return such instance.\n"
        + "     */\n"
        + "    public Digester getDigester()\n"
        + "    {\n"
        + "        return m__Digester;\n"
        + "    }\n\n";

    /**
     * The factory method.
     */
    public static final String DEFAULT_FACTORY_METHOD =
          "    /**\n"
        + "     * Creates a {0} value object from given SAX attributes.\n"
        + "     * @param attributes the attributes.\n"
        + "     * @return the {0} information.\n"
        + "     * @precondition attributes != null\n"
        + "     */\n"
        + "    public Object createObject(final Attributes attributes)\n"
        + "    '{'\n"
        + "        return createObject(attributes, ConversionUtils.getInstance());\n"
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Creates a {0} value object from given SAX attributes.\n"
        + "     * @param attributes the attributes.\n"
        + "     * @param conversionUtils the ConversionUtils instance.\n"
        + "     * @return the {0} information.\n"
        + "     * @throws SAXException if the attributes are not valid.\n"
        + "     * @precondition attributes != null\n"
        + "     * @precondition conversionUtils != null\n"
        + "     */\n"
        + "    public Object createObject(\n"
        + "        final Attributes attributes, final ConversionUtils conversionUtils)\n"
        + "      throws SAXException\n"
        + "    '{'\n"
        + "        {0}ValueObject result = null;\n"
        + "{1}\n" // attribute build
        + "        result =\n"
        + "            create{0}("
        + "{2});\n\n"  // factory method value object build.
        + "        return result;\n"
        + "    '}'\n\n";

    /**
     * The default attribute build.
     */
    public static final String DEFAULT_FACTORY_METHOD_ATTRIBUTE_BUILD =
          "\n        {0} {1} =\n" // field type, field name
        + "            conversionUtils.to{2}(attributes.getValue(\"{3}\"));\n";
        // field type, capitalized field type, uncapitalized field name,

    /**
     * The default factory method value object build.
     */
    public static final String DEFAULT_FACTORY_METHOD_VALUE_OBJECT_BUILD =
        "\n                {1}"; // Field - field;

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
     * The table template.
     */
    private TableTemplate m__TableTemplate;

    /**
     * The metadata manager.
     */
    private DatabaseMetaDataManager m__MetaDataManager;

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
     * The extra methods.
     */
    private String m__strExtraMethods;

    /**
     * The class end.
     */
    private String m__strClassEnd;

    /**
     * Builds a XMLValueObjectFactoryTemplate using given information.
     * @param header the header.
     * @param packageDeclaration the package declaration.
     * @param packageName the package name.
     * @param tableTemplate the table template.
     * @param metaDataManager the metadata manager.
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
     * @param extraMethods the extra methods.
     * @param classEnd the class end.
     */
    public XMLValueObjectFactoryTemplate(
        final String                  header,
        final String                  packageDeclaration,
        final String                  packageName,
        final TableTemplate           tableTemplate,
        final DatabaseMetaDataManager metaDataManager,
        final String                  projectImports,
        final String                  acmSlImports,
        final String                  jdkImports,
        final String                  extraImports,
        final String                  javadoc,
        final String                  classDefinition,
        final String                  classStart,
        final String                  singletonBody,
        final String                  factoryMethod,
        final String                  factoryMethodAttributeBuild,
        final String                  factoryMethodValueObjectBuild,
        final String                  extraMethods,
        final String                  classEnd)
    {
        immutableSetHeader(header);
        immutableSetPackageDeclaration(packageDeclaration);
        immutableSetPackageName(packageName);
        immutableSetTableTemplate(tableTemplate);
        immutableSetMetaDataManager(metaDataManager);
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
        immutableSetExtraMethods(extraMethods);
        immutableSetClassEnd(classEnd);
    }

    /**
     * Builds a XMLValueObjectFactoryTemplate using given information.
     * @param packageName the package name.
     * @param tableTemplate the table template.
     * @param metaDataManager the metadata manager.
     */
    public XMLValueObjectFactoryTemplate(
        final String                  packageName,
        final TableTemplate           tableTemplate,
        final DatabaseMetaDataManager metaDataManager)
    {
        this(
            DEFAULT_HEADER,
            PACKAGE_DECLARATION,
            packageName,
            tableTemplate,
            metaDataManager,
            DEFAULT_PROJECT_IMPORTS,
            DEFAULT_ACMSL_IMPORTS,
            DEFAULT_JDK_IMPORTS,
            DEFAULT_PROJECT_IMPORTS,
            DEFAULT_JAVADOC,
            CLASS_DEFINITION,
            DEFAULT_CLASS_START,
            DEFAULT_SINGLETON_BODY,
            DEFAULT_FACTORY_METHOD,
            DEFAULT_FACTORY_METHOD_ATTRIBUTE_BUILD,
            DEFAULT_FACTORY_METHOD_VALUE_OBJECT_BUILD,
            DEFAULT_EXTRA_METHODS,
            DEFAULT_CLASS_END);
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
     * @param metaDataManager the new metadata manager.
     */
    private void immutableSetMetaDataManager(
        DatabaseMetaDataManager metaDataManager)
    {
        m__MetaDataManager = metaDataManager;
    }

    /**
     * Specifies the metadata manager.
     * @param metaDataManager the new metadata manager.
     */
    protected void setMetaDataManager(
        DatabaseMetaDataManager metaDataManager)
    {
        immutableSetMetaDataManager(metaDataManager);
    }

    /**
     * Retrieves the metadata manager.
     * @return such information.
     */
    public DatabaseMetaDataManager getMetaDataManager()
    {
        return m__MetaDataManager;
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
     * Retrieves the source code of the generated field tableName.
     * @return such source code.
     */
    public String toString()
    {
        StringBuffer t_sbResult = new StringBuffer();

        StringUtils t_StringUtils = StringUtils.getInstance();

        EnglishGrammarUtils t_EnglishGrammarUtils =
            EnglishGrammarUtils.getInstance();

        TableTemplate t_TableTemplate = getTableTemplate();

        DatabaseMetaDataManager t_MetaDataManager = getMetaDataManager();

        MetaDataUtils t_MetaDataUtils = MetaDataUtils.getInstance();

        if  (   (t_StringUtils     != null)
             && (t_TableTemplate   != null)
             && (t_MetaDataUtils   != null)
             && (t_MetaDataManager != null))
        {
            MessageFormat t_Formatter = new MessageFormat(getHeader());
            t_sbResult.append(
                t_Formatter.format(
                    new Object[]
                    {
                        t_TableTemplate.getTableName()
                    }));

            t_Formatter = new MessageFormat(getPackageDeclaration());
            t_sbResult.append(
                t_Formatter.format(
                    new Object[]
                    {
                        getPackageName()
                    }));

            t_Formatter = new MessageFormat(getProjectImports());
            t_sbResult.append(
                t_Formatter.format(
                    new Object[]
                    {
                        getPackageName(),
                        t_StringUtils.capitalize(
                            t_EnglishGrammarUtils.getSingular(
                                t_TableTemplate.getTableName().toLowerCase()),
                            '_')
                    }));

            t_sbResult.append(getAcmSlImports());
            t_sbResult.append(getJdkImports());
            t_sbResult.append(getExtraImports());

            t_Formatter = new MessageFormat(getJavadoc());
            t_sbResult.append(
                t_Formatter.format(
                    new Object[]
                    {
                        t_TableTemplate.getTableName()
                    }));

            t_Formatter = new MessageFormat(getClassDefinition());
            t_sbResult.append(
                t_Formatter.format(
                    new Object[]
                    {
                        t_StringUtils.capitalize(
                            t_EnglishGrammarUtils.getSingular(
                            t_TableTemplate.getTableName().toLowerCase()),
                            '_')
                    }));

            t_sbResult.append(getClassStart());

            MessageFormat t_SingletonBodyFormatter =
                new MessageFormat(getSingletonBody());

            t_sbResult.append(
                t_SingletonBodyFormatter.format(
                    new Object[]
                    {
                        t_StringUtils.capitalize(
                            t_EnglishGrammarUtils.getSingular(
                                t_TableTemplate.getTableName().toLowerCase()),
                            '_')
                    }));

            List t_lFields = t_TableTemplate.getFields();

            MessageFormat t_FactoryMethodFormatter =
                    new MessageFormat(getFactoryMethod());

            if  (t_lFields != null)
            {
                Iterator t_itFields = t_lFields.iterator();

                StringBuffer t_sbFactoryMethodAttributeBuild = new StringBuffer();

                MessageFormat t_FactoryMethodAttributeBuildFormatter =
                    new MessageFormat(getFactoryMethodAttributeBuild());

                StringBuffer t_sbFactoryMethodValueObjectBuild = new StringBuffer();

                MessageFormat t_FactoryMethodValueObjectBuildFormatter =
                    new MessageFormat(getFactoryMethodValueObjectBuild());

                while  (t_itFields.hasNext()) 
                {
                    String t_strField = (String) t_itFields.next();

                    String t_strFieldType =
                        t_MetaDataUtils.getNativeType(
                            t_MetaDataManager.getColumnType(
                                t_TableTemplate.getTableName(),
                                t_strField));

                    t_sbFactoryMethodAttributeBuild.append(
                        t_FactoryMethodAttributeBuildFormatter.format(
                            new Object[]
                            {
                                t_strFieldType,
                                t_strField.toLowerCase(),
                                t_StringUtils.capitalize(
                                    t_strFieldType.toLowerCase(), '_'),
                                t_StringUtils.unCapitalize(
                                    t_strField.toLowerCase(), "-")
                            }));

                    t_sbFactoryMethodValueObjectBuild.append(
                        t_FactoryMethodValueObjectBuildFormatter.format(
                            new Object[]
                            {
                                t_StringUtils.capitalize(
                                    t_strField.toLowerCase(),
                                    '_'),
                                t_strField.toLowerCase()
                            }));
                }

                t_sbResult.append(
                    t_FactoryMethodFormatter.format(
                        new Object[]
                        {
                            t_StringUtils.capitalize(
                                t_EnglishGrammarUtils.getSingular(
                                    t_TableTemplate.getTableName()
                                        .toLowerCase()),
                                '_'),
                            t_sbFactoryMethodAttributeBuild.toString(),
                            t_sbFactoryMethodValueObjectBuild.toString()
                        }));
            }

            t_sbResult.append(getExtraMethods());

            t_sbResult.append(getClassEnd());
        }

        return t_sbResult.toString();
    }
}
