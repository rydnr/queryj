//;-*- mode: java -*-
/*
                        QueryJ

    Copyright (C) 2002-today  Jose San Leandro Armendariz
                              chous@acm-sl.org

    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Protected
    License as published by the Free Software Foundation; either
    version 2 of the License, or any later version.

    This library is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General Protected License for more details.

    You should have received a copy of the GNU General Protected
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA

    Thanks to ACM S.L. for distributing this library under the GPL license.
    Contact info: jose.sanleandro@acm-sl.com

 ******************************************************************************
 *
 * Filename: FunctionsTemplate.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Contains all common behaviour of function templates.
 *
 */
package org.acmsl.queryj.tools.templates.functions;

/*
 * Importing some project-specific classes.
 */
import org.acmsl.queryj.tools.metadata.DecoratorFactory;
import org.acmsl.queryj.tools.templates.AbstractTemplate;

/*
 * Importing some ACM-SL classes.
 */
import org.acmsl.commons.logging.UniqueLogFactory;
import org.acmsl.commons.utils.StringUtils;

/*
 * Importing some Apache Commons-Logging classes.
 */
import org.apache.commons.logging.Log;

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
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Contains all common behaviour of function templates.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public abstract class FunctionsTemplate
    extends  AbstractTemplate
    implements  FunctionsTemplateDefaults
{
    /**
     * Cached empty string array.
     */
    protected static final String[] EMPTY_STRING_ARRAY =
        new String[0];
    /**
     * The function class description.
     */
    private String m__strClassDescription;

    /**
     * The function class prefix.
     */
    private String m__strClassPrefix;

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
     * The function list.
     */
    private List m__lFunctions;

    /**
     * The function - field type mapping.
     */
    private static Map m__mMappings;

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
     * The singleton body.
     */
    private String m__strSingletonBody;

    /**
     * The class constructor.
     */
    private String m__strClassConstructor;

    /**
     * The inner class.
     */
    private String m__strInnerClass;

    /**
     * The class end.
     */
    private String m__strClassEnd;

    /**
     * Builds a <code>FunctionsTemplate</code> using given information.
     * @param header the header.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @param classDescription the class description.
     * @param classPrefix the class prefix.
     * @param packageDeclaration the package declaration.
     * @param packageName the package name.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param quote the identifier quote string.
     * @param acmslImports the ACM-SL imports.
     * @param jdkImports the JDK imports.
     * @param javadoc the class Javadoc.
     * @param classDefinition the class definition.
     * @param classStart the class start.
     * @param singletonBody the singleton body.
     * @param classConstructor the class constructor.
     * @param innerClass the inner class.
     * @param classEnd the class end.
     */
    protected FunctionsTemplate(
        final String header,
        final DecoratorFactory decoratorFactory,
        final String classDescription,
        final String classPrefix,
        final String packageDeclaration,
        final String packageName,
        final String engineName,
        final String engineVersion,
        final String quote,
        final String acmslImports,
        final String jdkImports,
        final String javadoc,
        final String classDefinition,
        final String classStart,
        final String singletonBody,
        final String classConstructor,
        final String innerClass,
        final String classEnd)
    {
        super(header, decoratorFactory);
        immutableSetClassDescription(classDescription);
        immutableSetClassPrefix(classPrefix);
        immutableSetPackageDeclaration(packageDeclaration);
        immutableSetPackageName(packageName);
        immutableSetEngineName(engineName);
        immutableSetEngineVersion(engineVersion);
        immutableSetQuote(quote);
        immutableSetAcmslImports(acmslImports);
        immutableSetJdkImports(jdkImports);
        immutableSetJavadoc(javadoc);
        immutableSetClassDefinition(classDefinition);
        immutableSetClassStart(classStart);
        immutableSetSingletonBody(singletonBody);
        immutableSetClassConstructor(classConstructor);
        immutableSetInnerClass(innerClass);
        immutableSetClassEnd(classEnd);
        immutableSetFunctions(new ArrayList());
    }

    /**
     * Builds a FunctionsTemplate using given information.
     * @param header the header.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @param classDescription the class description.
     * @param classPrefix the class prefix.
     * @param packageName the package name.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param quote the identifier quote string.
     */
    protected FunctionsTemplate(
        final String header,
        final DecoratorFactory decoratorFactory,
        final String classDescription,
        final String classPrefix,
        final String packageName,
        final String engineName,
        final String engineVersion,
        final String quote)
    {
        this(
//            (header != null) ? header : DEFAULT_HEADER,
            DEFAULT_HEADER,
            decoratorFactory,
            classDescription,
            classPrefix,
            PACKAGE_DECLARATION,
            packageName,
            engineName,
            engineVersion,
            quote,
            ACMSL_IMPORTS,
            JDK_IMPORTS,
            DEFAULT_JAVADOC,
            CLASS_DEFINITION,
            DEFAULT_CLASS_START,
            SINGLETON_BODY,
            CLASS_CONSTRUCTOR,
            INNER_CLASS,
            DEFAULT_CLASS_END);
    }

    /**
     * Specifies the class description.
     * @param description the description.
     */
    private void immutableSetClassDescription(final String description)
    {
        m__strClassDescription = description;
    }

    /**
     * Specifies the class description.
     * @param description the description.
     */
    protected void setClassDescription(final String description)
    {
        immutableSetClassDescription(description);
    }

    /**
     * Retrieves the class description.
     * @return such description.
     */
    protected String getClassDescription()
    {
        return m__strClassDescription;
    }

    /**
     * Specifies the class prefix.
     * @param prefix the prefix.
     */
    private void immutableSetClassPrefix(final String prefix)
    {
        m__strClassPrefix = prefix;
    }

    /**
     * Specifies the class prefix.
     * @param prefix the prefix.
     */
    protected void setClassPrefix(final String prefix)
    {
        immutableSetClassPrefix(prefix);
    }

    /**
     * Retrieves the class prefix.
     * @return such prefix.
     */
    protected String getClassPrefix()
    {
        return m__strClassPrefix;
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
    protected String getPackageDeclaration() 
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
     * Specifies the engine name.
     * @param engineName the new engine name.
     */
    private void immutableSetEngineName(final String engineName)
    {
        m__strEngineName = engineName;
    }

    /**
     * Specifies the engine name.
     * @param engineName the new engine name.
     */
    protected void setEngineName(final String engineName)
    {
        immutableSetEngineName(engineName);
    }

    /**
     * Retrieves the engine name.
     * @return such information.
     */
    protected String getEngineName() 
    {
        return m__strEngineName;
    }

    /**
     * Specifies the engine version.
     * @param engineVersion the new engine version.
     */
    private void immutableSetEngineVersion(final String engineVersion)
    {
        m__strEngineVersion = engineVersion;
    }

    /**
     * Specifies the engine version.
     * @param engineVersion the new engine version.
     */
    protected void setEngineVersion(final String engineVersion)
    {
        immutableSetEngineVersion(engineVersion);
    }

    /**
     * Retrieves the engine version.
     * @return such information.
     */
    protected String getEngineVersion()
    {
        return m__strEngineVersion;
    }

    /**
     * Specifies the identifier quote string.
     * @param quote such identifier.
     */
    private void immutableSetQuote(final String quote)
    {
        m__strQuote = quote;
    }

    /**
     * Specifies the identifier quote string.
     * @param quote such identifier.
     */
    protected void setQuote(final String quote)
    {
        immutableSetQuote(quote);
    }

    /**
     * Retrieves the identifier quote string.
     * @return such identifier.
     */
    protected String getQuote()
    {
        return m__strQuote;
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
    protected String getAcmslImports() 
    {
        return m__strAcmslImports;
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
    protected String getJdkImports() 
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
    protected String getJavadoc() 
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
    protected String getClassDefinition() 
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
    protected String getClassStart() 
    {
        return m__strClassStart;
    }

    /**
     * Specifies the singleton body.
     * @param singletonBody the new singleton body.
     */
    private void immutableSetSingletonBody(final String singletonBody)
    {
        m__strSingletonBody = singletonBody;
    }

    /**
     * Specifies the singleton body.
     * @param singletonBody the new singleton body.
     */
    protected void setSingletonBody(final String singletonBody)
    {
        immutableSetSingletonBody(singletonBody);
    }

    /**
     * Retrieves the singleton body.
     * @return such information.
     */
    protected String getSingletonBody()
    {
        return m__strSingletonBody;
    }

    /**
     * Specifies the class constructor
     * @param constructor such source code.
     */
    private void immutableSetClassConstructor(final String constructor)
    {
        m__strClassConstructor = constructor;
    }

    /**
     * Specifies the class constructor
     * @param constructor such source code.
     */
    protected void setClassConstructor(final String constructor)
    {
        immutableSetClassConstructor(constructor);
    }

    /**
     * Retrieves the class constructor.
     * @return such source code.
     */
    protected String getClassConstructor()
    {
        return m__strClassConstructor;
    }

    /**
     * Specifies the inner class.
     * @param innerClass the new inner class.
     */
    private void immutableSetInnerClass(final String innerClass)
    {
        m__strInnerClass = innerClass;
    }

    /**
     * Specifies the inner class.
     * @param innerClass the new inner class.
     */
    protected void setInnerClass(final String innerClass)
    {
        immutableSetInnerClass(innerClass);
    }

    /**
     * Retrieves the inner classes.
     * @return such information.
     */
    protected String getInnerClass() 
    {
        return m__strInnerClass;
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
    protected String getClassEnd() 
    {
        return m__strClassEnd;
    }

    /**
     * Specifies the functions.
     * @param functions the function collection.
     */
    private void immutableSetFunctions(final List functions)
    {
        m__lFunctions = functions;
    }

    /**
     * Specifies the functions.
     * @param functions the function collection.
     */
    protected void setFunctions(final List functions)
    {
        immutableSetFunctions(functions);
    }

    /**
     * Retrieves the functions.
     * @return such collection.
     */
    protected List getFunctions()
    {
        return m__lFunctions;
    }

    /**
     * Adds a new function.
     * @param function the new function.
     */
    public void addFunction(@Nullable final String function)
    {
        if  (function != null) 
        {
            List t_lFunctions = getFunctions();

            if  (t_lFunctions != null) 
            {
                t_lFunctions.add(function.trim());
            }
        }
    }

    /**
     * Retrieves the mapping for given function.
     * @param function the function.
     * @return the field type.
     */
    @Nullable
    protected abstract String getMapping(final String function);

    /**
     * Retrieves the special mapping for given function.
     * @param function the function.
     * @return the field type.
     */
    @Nullable
    protected abstract String getSpecialMapping(final String function);

    /**
     * Retrieves the special mapping's return type for given function.
     * @param function the function.
     * @return the field type.
     */
    @Nullable
    protected abstract String getSpecialMappingReturnType(final String function);

    /**
     * Retrieves the default function method.
     * @return such method template.
     */
    protected abstract String getDefaultFunctionMethod();

    /**
     * Retrieves the capitalized words.
     * @return such words.
     */
    protected abstract String[] getCapitalizedWords();

    /**
     * Retrieves the field types.
     * @return such array.
     */
    protected abstract String[] getFieldTypes();

    /**
     * Creates a key for function mappings.
     * @param function the function.
     * @param templateClass the template class.
     * @return the key.
     */
    @NotNull
    protected static String buildKey(
        final String function, @Nullable final Class templateClass)
    {
        return
              (  (templateClass != null)
               ? templateClass.getName() + "."
               : "queryj.")
            + function;
    }

    /**
     * Creates a key for special mappings.
     * @param function the function.
     * @param templateClass the template class.
     * @return the special key.
     */
    @NotNull
    protected static String buildSpecialKey(
        final String function, @Nullable final Class templateClass)
    {
        return
              (  (templateClass != null)
               ? templateClass.getName() + ".special."
               : "queryj.special.")
            + function;
    }

    /**
     * Creates a key for special mappings' return type.
     * @param function the function.
     * @param templateClass the template class.
     * @return the special key.
     */
    @NotNull
    protected static String buildSpecialKeyReturnType(
        final String function, @Nullable final Class templateClass)
    {
        return
              (  (templateClass != null)
               ? templateClass.getName() + ".special.return."
               : "queryj.special.return.")
            + function;
    }


    /**
     * Specifies the mappings.
     * @param mappings the mappings.
     */
    protected static void setMappings(final Map mappings)
    {
        m__mMappings = mappings;
    }

    /**
     * Retrieves the mappings.
     * @return such map.
     */
    protected static final Map immutableGetMappings()
    {
        return m__mMappings;
    }
    
    /**
     * Retrieves the mappings.
     * @return such map.
     */
    public Map getMappings()
    {
        Map result = immutableGetMappings();

        if  (result == null)
        {
            result = new HashMap();
        }

        if  (!isFilledIn(result))
        {
            synchronized (result)
            {
                result = fillUpMappings(result);
                result = fillUpSpecialMappings(result);
                result = fillUpSpecialMappingsReturnTypes(result);
                setMappings(result);
            }
        }

        return result;
    }

    /**
     * Checks whether given map is already filled with specific
     * template mappings.
     * @param mapping the mapping table.
     * @return <code>true</code> if the map contains the specific
     * mapping entries for this template.
     */
    protected abstract boolean isFilledIn(Map mappings);

    /**
     * Checks whether a concrete mapping can be omitted without generating a
     * warning message.
     * @param mapping the concrete mapping.
     * @return <code>true</code> to generate the warning message.
     */
    protected boolean generateWarning(final String mapping)
    {
        return true;
    }

    /**
     * Retrieves the mapping for given function.
     * @param function the function.
     * @param templateClass the template class.
     * @return the field type.
     */
    @Nullable
    protected String getMapping(@Nullable final String function, @Nullable Class templateClass)
    {
        @Nullable String result = null;

        if  (   (function      != null)
             && (templateClass != null))
        {
            result =
                getMapping(
                    getMappings(),
                    buildKey(function, templateClass));
        }

        return result;
    }

    /**
     * Retrieves the mapping for given special function.
     * @param function the function.
     * @return the field type.
     */
    @Nullable
    public String getSpecialMapping(@Nullable final String function, @Nullable Class templateClass)
    {
        @Nullable String result = null;

        if  (   (function      != null)
             && (templateClass != null))
        {
            result =
                getMapping(
                    getMappings(),
                    buildSpecialKey(function, templateClass));
        }
        
        return result;
    }

    /**
     * Retrieves the mapping for given key.
     * @param mappings the mappings.
     * @param key the key.
     * @return the field type.
     */
    @Nullable
    protected String getMapping(@Nullable Map mappings, @Nullable String key)
    {
        @Nullable String result = null;

        if  (   (mappings != null)
             && (key      != null))
        {
            result = (String) mappings.get(key);
        }

        return result;
    }

    /**
     * Builds the mappings.
     * @param mappings the initial mapping collection.
     * @return the updated collection.
     */
    protected abstract Map fillUpMappings(Map mappings);

    /**
     * Creates the special function mappings.
     * @param mapping the initial mapping.
     * @return the updated mapping.
     */
    protected abstract Map fillUpSpecialMappings(Map mappings);

    /**
     * Creates the special function mappings' return types.
     * @param mapping the initial mapping.
     * @return the updated mapping.
     */
    protected abstract Map fillUpSpecialMappingsReturnTypes(Map mappings);

    /**
     * Retrieves the special mapping's return type for given function.
     * @param function the function.
     * @param templateClass the template class.
     * @return the field type.
     */
    @Nullable
    public String getSpecialMappingReturnType(
        @Nullable String function, Class templateClass)
    {
        @Nullable String result = null;

        if  (function != null)
        {
            result =
                getMapping(
                    getMappings(),
                    buildSpecialKeyReturnType(
                        function,
                        templateClass));
        }
        
        return result;
    }

    /**
     * Outputs the inner classes.
     * @return such classes.
     */
    protected String innerClassesToString()
    {
        @NotNull StringBuffer t_sbResult = new StringBuffer();

        String t_strInnerClass = getInnerClass();

        if  (t_strInnerClass != null) 
        {
            String[] t_astrFieldTypes = getFieldTypes();

            if  (t_astrFieldTypes != null) 
            {
                @Nullable MessageFormat t_InnerClassFormatter = null;

                for  (int t_iFieldIndex = 0;
                          t_iFieldIndex < t_astrFieldTypes.length;
                          t_iFieldIndex++)
                {
                    t_InnerClassFormatter =
                        new MessageFormat(t_strInnerClass);
                
                    t_sbResult.append(
                        t_InnerClassFormatter.format(
                            new Object[]
                            {
                                t_astrFieldTypes[t_iFieldIndex],
                                getQuote()
                            }));
                }
            }
        }

        return t_sbResult.toString();
    }

    /**
     * Logs a warning message.
     * @param message the message.
     */
    protected void logWarn(final String message)
    {
        Log t_Log = UniqueLogFactory.getLog(FunctionsTemplate.class);

        if  (t_Log != null)
        {
            t_Log.warn(message);
        }
    }

    /**
     * Logs a debug message.
     * @param message the message.
     */
    protected void logDebug(final String message)
    {
        Log t_Log = UniqueLogFactory.getLog(FunctionsTemplate.class);

        if  (t_Log != null)
        {
            t_Log.debug(message);
        }
    }

    /**
     * Builds the header for logging purposes.
     * @return such header.
     */
    @NotNull
    protected String buildHeader()
    {
        return buildHeader(getClassDescription());
    }

    /**
     * Builds the header for logging purposes.
     * @param type the type.
     * @return such header.
     * @precondition type != null
     */
    @NotNull
    protected String buildHeader(final String type)
    {
        return "Generating " + type + " functions template.";
    }

    /**
     * Retrieves the source code of the generated field tableName.
     * @param header the header.
     * @return such source code.
     */
    protected String generateOutput(final String header)
    {
        @NotNull StringBuffer t_sbResult = new StringBuffer();

        StringUtils t_StringUtils = StringUtils.getInstance();

        if  (t_StringUtils != null) 
        {
            @NotNull Object t_aEngine =
                new Object[]
                {
                    getEngineName(),
                    getEngineVersion(),
                    getClassDescription()
                };

            @NotNull Object[] t_aPackageName = new Object[]{getPackageName()};

            @NotNull Object[] t_aClassPrefix = new Object[]{getClassPrefix()};

            @Nullable MessageFormat t_Formatter = null;

            String t_strHeader = header;

            if  (t_strHeader != null) 
            {
                t_Formatter = new MessageFormat(getHeader());
                t_sbResult.append(t_Formatter.format(t_aEngine));
            }

            String t_strPackageDeclaration = getPackageDeclaration();

            if  (t_strPackageDeclaration != null) 
            {
                t_Formatter = new MessageFormat(getPackageDeclaration());
                t_sbResult.append(t_Formatter.format(t_aPackageName));
            }

            String t_strAcmslImports = getAcmslImports();

            if  (t_strAcmslImports != null) 
            {
                t_sbResult.append(t_strAcmslImports);
            }

            String t_strJdkImports = getJdkImports();

            if  (t_strJdkImports != null) 
            {
                t_sbResult.append(t_strJdkImports);
            }

            String t_strJavadoc = getJavadoc();

            if  (t_strJavadoc != null) 
            {
                t_Formatter = new MessageFormat(t_strJavadoc);
                t_sbResult.append(t_Formatter.format(t_aEngine));
            }

            String t_strClassDefinition = getClassDefinition();

            if  (t_strClassDefinition != null) 
            {
                t_Formatter = new MessageFormat(t_strClassDefinition);
                t_sbResult.append(t_Formatter.format(t_aClassPrefix));
            }

            String t_strClassStart = getClassStart();

            if  (t_strClassStart != null) 
            {
                t_sbResult.append(t_strClassStart);
            }

            String t_strSingletonBody = getSingletonBody();

            if  (t_strSingletonBody != null) 
            {
                t_Formatter = new MessageFormat(t_strSingletonBody);
                t_sbResult.append(t_Formatter.format(t_aClassPrefix));
            }

            String t_strFunctionMethod = getDefaultFunctionMethod();

            if  (t_strFunctionMethod != null) 
            {
                List t_lFunctions = getFunctions();

                if  (t_lFunctions != null)
                {
                    Iterator t_itFunctions = t_lFunctions.iterator();

                    @NotNull MessageFormat t_MethodFormatter =
                        new MessageFormat(t_strFunctionMethod);

                    while  (t_itFunctions.hasNext()) 
                    {
                        @NotNull String t_strFunction = (String) t_itFunctions.next();

                        @Nullable String t_strMapping = getMapping(t_strFunction);

                        if  (t_strMapping == null) 
                        {
                            t_strMapping = getSpecialMapping(t_strFunction);

                            if  (t_strMapping == null)
                            {
                                if  (generateWarning(null))
                                {
                                    logDebug(
                                        "No mapping for " + t_strFunction);
                                }

                                continue;
                            }
                            else 
                            {
                                t_MethodFormatter =
                                    new MessageFormat(t_strMapping);

                                t_strMapping =
                                    getSpecialMappingReturnType(t_strFunction);
                            }
                        }

                        t_sbResult.append(
                            t_MethodFormatter.format(
                                new Object[]
                                {
                                    getEngineName(),
                                    t_strFunction,
                                    t_strMapping,
                                    t_StringUtils.toJavaMethod(
                                        t_strFunction.toLowerCase(),
                                        '_',
                                        getCapitalizedWords()),
                                    t_strFunction
                                }));
                    }
                }
            }

            t_sbResult.append(innerClassesToString());

            String t_strClassEnd = getClassEnd();

            if  (t_strClassEnd != null) 
            {
                t_sbResult.append(t_strClassEnd);
            }
        }

        return t_sbResult.toString();
    }
}
