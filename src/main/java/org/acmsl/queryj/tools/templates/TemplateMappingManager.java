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

 ******************************************************************************
 *
 * Filename: TemplateMappingManager.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Manages the association between database engines and template
 *              factory classes.
 *
 */
package org.acmsl.queryj.tools.templates;

/*
 * Importing some ACM-SL classes.
 */
import org.acmsl.queryj.tools.QueryJBuildException;
import org.acmsl.queryj.tools.templates.functions.numeric
    .NumericFunctionsTemplateGenerator;

import org.acmsl.queryj.tools.templates.functions.numeric
    .NumericFunctionsTestTemplateGenerator;

import org.acmsl.queryj.tools.templates.functions.numeric.mysql
    .MySQLNumericFunctionsTemplateGenerator;

import org.acmsl.queryj.tools.templates.functions.numeric.mysql
    .MySQLNumericFunctionsTestTemplateGenerator;

import org.acmsl.queryj.tools.templates.functions.numeric.oracle
    .OracleNumericFunctionsTemplateGenerator;

import org.acmsl.queryj.tools.templates.functions.numeric.oracle
    .OracleNumericFunctionsTestTemplateGenerator;

import org.acmsl.queryj.tools.templates.functions.system.mysql
    .MySQLSystemFunctionsTemplateGenerator;

import org.acmsl.queryj.tools.templates.functions.system.mysql
    .MySQLSystemFunctionsTestTemplateGenerator;

import org.acmsl.queryj.tools.templates.functions.system.oracle
    .OracleSystemFunctionsTemplateGenerator;

import org.acmsl.queryj.tools.templates.functions.system.oracle
    .OracleSystemFunctionsTestTemplateGenerator;

import org.acmsl.queryj.tools.templates.functions.system
    .SystemFunctionsTemplateGenerator;

import org.acmsl.queryj.tools.templates.functions.system
    .SystemFunctionsTestTemplateGenerator;

import org.acmsl.queryj.tools.templates.functions.text.mysql
    .MySQLTextFunctionsTemplateGenerator;

import org.acmsl.queryj.tools.templates.functions.text.mysql
    .MySQLTextFunctionsTestTemplateGenerator;

import org.acmsl.queryj.tools.templates.functions.text.oracle
    .OracleTextFunctionsTemplateGenerator;

import org.acmsl.queryj.tools.templates.functions.text.oracle
    .OracleTextFunctionsTestTemplateGenerator;

import org.acmsl.queryj.tools.templates.functions.text
    .TextFunctionsTemplateGenerator;

import org.acmsl.queryj.tools.templates.functions.text
    .TextFunctionsTestTemplateGenerator;

import org.acmsl.queryj.tools.templates.functions.time.mysql
    .MySQLTimeFunctionsTemplateGenerator;

import org.acmsl.queryj.tools.templates.functions.time.mysql
    .MySQLTimeFunctionsTestTemplateGenerator;

import org.acmsl.queryj.tools.templates.functions.time.oracle
    .OracleTimeFunctionsTemplateGenerator;

import org.acmsl.queryj.tools.templates.functions.time.oracle
    .OracleTimeFunctionsTestTemplateGenerator;

import org.acmsl.queryj.tools.templates.functions.time
    .TimeFunctionsTemplateGenerator;

import org.acmsl.queryj.tools.templates.functions.time
    .TimeFunctionsTestTemplateGenerator;

import org.acmsl.queryj.tools.templates.TemplateFactory;

/*
 * Importing some ACM-SL Commons classes.
 */
import org.acmsl.commons.patterns.Manager;
import org.acmsl.commons.patterns.Singleton;

/*
 * Importing some JDK classes.
 */
import java.util.HashMap;
import java.util.Map;

/*
 * Importing Jakarta Commons Logging classes.
 */
import org.apache.commons.logging.LogFactory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Manages the association between database engines and template
 * factory classes.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public class TemplateMappingManager
    implements  Manager,
                Singleton
{
    /**
     * The default template type.
     */
    protected static final String DEFAULT_TEMPLATE = "_^DEFAULT.-";

    /**
     * The default test template type.
     */
    protected static final String DEFAULT_TEST_TEMPLATE = "_^DEFAULT.-.TEST^_";

    /**
     * The Time functions template type.
     */
    public static final String TIME_FUNCTIONS_TEMPLATE = "time";

    /**
     * The Time functions test template type.
     */
    public static final String TIME_FUNCTIONS_TEST_TEMPLATE = "time.test";

    /**
     * The Text functions template type.
     */
    public static final String TEXT_FUNCTIONS_TEMPLATE = "text";

    /**
     * The Text functions test template type.
     */
    public static final String TEXT_FUNCTIONS_TEST_TEMPLATE = "text.test";

    /**
     * The Numeric functions template type.
     */
    public static final String NUMERIC_FUNCTIONS_TEMPLATE = "numeric";

    /**
     * The Numeric functions test template type.
     */
    public static final String NUMERIC_FUNCTIONS_TEST_TEMPLATE =
        "numeric.test";

    /**
     * The System functions template type.
     */
    public static final String SYSTEM_FUNCTIONS_TEMPLATE = "system";

    /**
     * The System functions test template type.
     */
    public static final String SYSTEM_FUNCTIONS_TEST_TEMPLATE = "system.test";

    /**
     * The table repository template attribute name.
     */
    public static final String TABLE_REPOSITORY_TEMPLATE = "table.repository";

    /**
     * The data access manager template attribute name.
     */
    public static final String DATA_ACCESS_MANAGER_TEMPLATE =
        "data.access.manager.template";

    /**
     * The DAO template type.
     */
    public static final String DAO_TEMPLATE_PREFIX = "dao.";

    /**
     * The DAO factory templates attribute name.
     */
    public static final String DAO_FACTORY_TEMPLATES = "DAO.factory.templates";

    /**
     * The DAO templates attribute name.
     */
    public static final String DAO_TEMPLATES = "DAO.templates";

    /**
     * The DAO names attribute name.
     */
    public static final String DAO_NAMES = "DAO.names";

    /**
     * The DAO factory names attribute name.
     */
    public static final String DAO_FACTORY_NAMES = "DAO.factory.names";

    /**
     * The DAO factory template type.
     */
    public static final String DAO_FACTORY_TEMPLATE_PREFIX = "dao.factory.";

    /**
     * The base DAO template type.
     */
    public static final String BASE_DAO_TEMPLATE_PREFIX = "base.dao.";

    /**
     * The base DAO templates attribute name.
     */
    public static final String BASE_DAO_TEMPLATES = "base.DAO.templates";

    /**
     * The base DAO factory templates attribute name.
     */
    public static final String BASE_DAO_FACTORY_TEMPLATES =
        "base.DAO.factory.templates";

    /**
     * The base DAO factory template prefix.
     */
    public static final String BASE_DAO_FACTORY_TEMPLATE_PREFIX =
        "base.dao.factory.";

    /**
     * The JDBC DAO template type.
     */
    public static final String JDBC_DAO_TEMPLATE = "jdbc.dao.template";

    /**
     * The JDBC DAO template factory type.
     */
    public static final String JDBC_DAO_TEMPLATE_FACTORY =
        "jdbc.dao.template.factory";

    /**
     * The QueryPreparedStatementCreator template type.
     */
    public static final String QUERY_PREPARED_STATEMENT_CREATOR_TEMPLATE =
        "query.prepared.statement.creator.template";

    /**
     * The ResultSetExtractor template type.
     */
    public static final String RESULTSET_EXTRACTOR_TEMPLATES =
        "resultset.extractor.templates";

    /**
     * The BaseResultSetExtractor template type.
     */
    public static final String BASE_RESULTSET_EXTRACTOR_TEMPLATE =
        "base.resultset.extractor.template";

    /**
     * The DAO listener template attribute name.
     */
    public static final String DAO_LISTENER_TEMPLATE = "dao.listener.template";

    /**
     * The DAO listener implementation template attribute name.
     */
    public static final String DAO_LISTENER_IMPL_TEMPLATE = "dao.listener.impl.template";

    /**
     * The CustomResultSetExtractor template type.
     */
    public static final String CUSTOM_RESULTSET_EXTRACTOR_TEMPLATES =
        "custom.resultset.extractor.templates";

    /**
     * The AttributesStatementSetter template type.
     */
    public static final String ATTRIBUTES_STATEMENT_SETTER_TEMPLATES =
        "attributes.statement.setter.templates";

    /**
     * The PkStatementSetter template type.
     */
    public static final String PK_STATEMENT_SETTER_TEMPLATES =
        "pk.statement.setter.templates";

    /**
     * The FkStatementSetter template type.
     */
    public static final String FK_STATEMENT_SETTER_TEMPLATES =
        "fk.statement.setter.templates";

    /**
     * The CustomValueObject template type.
     */
    public static final String CUSTOM_VALUE_OBJECT_TEMPLATES =
        "custom.value.object.templates";

    /**
     * The CustomBaseValueObject template type.
     */
    public static final String CUSTOM_BASE_VALUE_OBJECT_TEMPLATES =
        "custom.base.value.object.templates";

    /**
     * The CustomValueObjectImpl template type.
     */
    public static final String CUSTOM_VALUE_OBJECT_IMPL_TEMPLATES =
        "custom.value.object.impl.templates";

    /**
     * The CustomValueObjectFactory template type.
     */
    public static final String CUSTOM_VALUE_OBJECT_FACTORY_TEMPLATES =
        "custom.value.object.factory.templates";

    /**
     * The Mock DAO template prefix.
     */
    public static final String MOCK_DAO_TEMPLATE_PREFIX = "mock.dao.";

    /**
     * The Mock DAO templates attribute name.
     */
    public static final String MOCK_DAO_TEMPLATES = "mock.DAO.templates";

    /**
     * The Mock DAO test template prefix.
     */
    public static final String MOCK_DAO_TEST_TEMPLATE_PREFIX = "mock.dao.test.";

    /**
     * The Mock DAO test templates attribute name.
     */
    public static final String MOCK_DAO_TEST_TEMPLATES = "mock.DAO.test.templates";

    /**
     * The XML DAO template prefix.
     */
    public static final String XML_DAO_TEMPLATE_PREFIX = "xml.dao.";

    /**
     * The XML DAO templates attribute name.
     */
    public static final String XML_DAO_TEMPLATES = "xml.DAO.templates";

    /**
     * The XML DAO test template prefix.
     */
    public static final String XML_DAO_TEST_TEMPLATE_PREFIX = "xml.dao.test.";

    /**
     * The XML DAO test templates attribute name.
     */
    public static final String XML_DAO_TEST_TEMPLATES = "xml.DAO.test.templates";

    /**
     * The value object templates attribute name.
     */
    public static final String VALUE_OBJECT_TEMPLATES = "value-object.templates";

    /**
     * The value objects' names attribute name.
     */
    public static final String VALUE_OBJECT_NAMES = "value-object.names";

    /**
     * The value object factory templates attribute name.
     */
    public static final String VALUE_OBJECT_FACTORY_TEMPLATES = "value-object.factory.templates";

    /**
     * The value object factories' names attribute name.
     */
    public static final String VALUE_OBJECT_FACTORY_NAMES = "value-object.factory.names";

    /**
     * The value object template prefix.
     */
    public static final String VALUE_OBJECT_TEMPLATE_PREFIX = "value-object.";

    /**
     * The value object factory template prefix.
     */
    public static final String VALUE_OBJECT_FACTORY_TEMPLATE_PREFIX =
        "value-object.factory.";

    /**
     * The base value object templates attribute name.
     */
    public static final String BASE_VALUE_OBJECT_TEMPLATES =
        "base-value-object.templates";

    /**
     * The value object impl templates attribute name.
     */
    public static final String VALUE_OBJECT_IMPL_TEMPLATES =
        "value-object-impl.templates";

    /**
     * The base value object template prefix.
     */
    public static final String BASE_VALUE_OBJECT_TEMPLATE_PREFIX = "base-value-object.";

    /**
     * The value object impl template prefix.
     */
    public static final String VALUE_OBJECT_IMPL_TEMPLATE_PREFIX = "value-object-impl.";

    /**
     * The DAO test template prefix.
     */
    public static final String DAO_TEST_TEMPLATE_PREFIX = "dao.test.";

    /**
     * The test templates attribute name.
     */
    public static final String TEST_TEMPLATES = "test.templates";

    /**
     * The DAO test templates attribute name.
     */
    public static final String DAO_TEST_TEMPLATES = "DAO.test.templates";

    /**
     * The DAO test names attribute name.
     */
    public static final String DAO_TEST_NAMES = "DAO.test.names";

    /**
     * The DAOChooser template attribute name.
     */
    public static final String DAO_CHOOSER_TEMPLATE =
        "dao.chooser.template";

    /**
     * The configuration properties template attribute name.
     */
    public static final String CONFIGURATION_PROPERTIES_TEMPLATE =
        "configuration.properties.template";

    /**
     * The Mock DAO factory templates attribute name.
     */
    public static final String MOCK_DAO_FACTORY_TEMPLATES =
        "mock.DAO.factory.templates";

    /**
     * The Mock DAO factory template prefix.
     */
    public static final String MOCK_DAO_FACTORY_TEMPLATE_PREFIX =
        "mock.dao.factory.";

    /**
     * The XML value object template prefix.
     */
    public static final String XML_VALUE_OBJECT_TEMPLATE_PREFIX =
        "xml.value-object.";

    /**
     * The XML value object factory template prefix.
     */
    public static final String XML_VALUE_OBJECT_FACTORY_TEMPLATE_PREFIX =
        "xml.value-object.factory.";

    /**
     * The XML value object factory templates attribute name.
     */
    public static final String XML_VALUE_OBJECT_FACTORY_TEMPLATES =
        "xml.value-object.factory.templates";

    /**
     * The XML DAO factory templates attribute name.
     */
    public static final String XML_DAO_FACTORY_TEMPLATES =
        "xml.DAO.factory.templates";

    /**
     * The XML DAO factory template prefix.
     */
    public static final String XML_DAO_FACTORY_TEMPLATE_PREFIX =
        "xml.dao.factory.";

    /**
     * The dataAccessContextLocal template attribute name.
     */
    public static final String DATAACCESSCONTEXTLOCAL_TEMPLATE =
        "dataAccessContext-local.xml.template";

    /**
     * The keyword repository template attribute name.
     */
    public static final String KEYWORD_REPOSITORY_TEMPLATE =
        "keyword.repository.template";

    /**
     * The repository DAO template attribute name.
     */
    public static final String REPOSITORY_DAO_TEMPLATE = "repository.dao";

    /**
     * The repository DAO factory template attribute name.
     */
    public static final String REPOSITORY_DAO_FACTORY_TEMPLATE =
        "repository.dao.factory";

    /**
     * The repository DAO template attribute name.
     */
    public static final String BASE_REPOSITORY_DAO_TEMPLATE =
        "base.repository.dao";

    /**
     * The repository DAO factory template attribute name.
     */
    public static final String BASE_REPOSITORY_DAO_FACTORY_TEMPLATE =
        "base.repository.dao.factory";

    /**
     * The base abstract DAO templates attribute name.
     */
    public static final String BASE_ABSTRACT_DAO_TEMPLATES =
        "base.abstract.DAO.templates";

    /**
     * The BaseResultSetExtractor template type.
     */
    public static final String BASE_RESULTSET_EXTRACTOR_TEMPLATE =
        "base.resultset.extractor.template";

    /**
     * The engine-template mapping.
     */
    private Map m__mMapping;

    /**
     * Singleton implemented to avoid the double-checked locking.
     */
    private static class TemplateMappingManagerSingletonContainer
    {
        /**
         * The actual singleton.
         */
        public static final TemplateMappingManager SINGLETON =
            new TemplateMappingManager();
    }

    /**
     * Protected constructor to avoid accidental instantiation.
     */
    protected TemplateMappingManager()
    {
        registerTemplates();
    }

    /**
     * Retrieves a <code>TemplateMappingManager</code> instance.
     * @return such instance.
     */
    @NotNull
    public static TemplateMappingManager getInstance()
    {
        return TemplateMappingManagerSingletonContainer.SINGLETON;
    }

    /**
     * Specifies the mapping between engines and templates.
     * @param mapping the mapping.
     */
    private void inmutableSetMapping(final Map mapping)
    {
        m__mMapping = mapping;
    }

    /**
     * Specifies the mapping between engines and templates.
     * @param mapping the mapping.
     */
    protected void setMapping(final Map mapping)
    {
        inmutableSetMapping(mapping);
    }

    /**
     * Retrieves the mapping between engines and templates.
     * @return such mapping.
     */
    protected Map getMapping()
    {
        return m__mMapping;
    }

    /**
     * Adds a new default mapping.
     * @param type the template type.
     * @param templateFactoryClass the template factory.
     */
    public void addDefaultTemplateFactoryClass(
        final String type, final String templateFactoryClass)
    {
        addTemplateFactoryClass(
            type, DEFAULT_TEMPLATE, templateFactoryClass);
    }

    /**
     * Adds a new mapping.
     * @param type the template type.
     * @param engineName the engine name.
     * @param templateFactoryClass the template factory.
     */
    public void addTemplateFactoryClass(
        final String type,
        final String engineName,
        final String templateFactoryClass)
    {
        addTemplateFactoryClass(type, engineName, null, templateFactoryClass);
    }

    /**
     * Adds a new mapping.
     * @param type the template type.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param templateFactoryClass the template factory.
     */
    public void addTemplateFactoryClass(
        @Nullable final String type,
        @Nullable final String engineName,
        final String engineVersion,
        @Nullable final String templateFactoryClass)
    {
        if  (   (type                 != null)
             && (engineName           != null)
             && (templateFactoryClass != null))
        {
            Map t_mMapping = getMapping();

            if  (t_mMapping == null)
            {
                t_mMapping = new HashMap();
                setMapping(t_mMapping);
            }

            @NotNull Object t_Key =
                buildKey(type, engineName, engineVersion);

            if  (t_Key != null)
            {
                if  (!templateFactoryClass.equals(t_mMapping.get(t_Key)))
                {
                    LogFactory.getLog(TemplateMappingManager.class).debug(
                          "Overwritting previous template factory ("
                        + t_mMapping.get(t_Key)
                        + "->" + templateFactoryClass + ")");
                }

                t_mMapping.put(t_Key, templateFactoryClass);
            }
        }
    }

    /**
     * Retrieves the default template factory class.
     * @param type the template type.
     * @return the template factory class name.
     */
    @Nullable
    public String getDefaultTemplateFactoryClass(final String type)
    {
        return getTemplateFactoryClass(type, DEFAULT_TEMPLATE, null);
    }

    /**
     * Retrieves the template factory class.
     * @param type the template type.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @return the template factory class name.
     */
    @Nullable
    public String getTemplateFactoryClass(
        @Nullable final String type,
        @Nullable final String engineName,
        final String engineVersion)
    {
        @Nullable String result = null;

        @Nullable Object t_Key = null;

        if  (   (type       != null)
             && (engineName != null))
        {
            Map t_Mapping = getMapping();

            if  (t_Mapping != null)
            {
                t_Key = buildKey(type, engineName, engineVersion);

                Object t_Result = t_Mapping.get(t_Key);

                if  (t_Result == null)
                {
                    t_Key = buildKey(type, engineName);

                    t_Result =
                        t_Mapping.get(t_Key);
                }

                if  (t_Result == null)
                {
                    t_Key = buildKey(type);

                    t_Result =
                        t_Mapping.get(t_Key);
                }

                if  (t_Result != null)
                {
                    result = t_Result.toString();
                }
            }
        }

        return result;
    }

    /**
     * Retrieves the template factory instance.
     * @param type the template type.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @return the template factory class name.
     * @throws QueryJBuildException if the factory class is invalid.
     */
    @Nullable
    public TemplateFactory getTemplateFactory(
        final String type, final String engineName, final String engineVersion)
      throws  QueryJBuildException
    {
        @Nullable TemplateFactory result = null;

        @Nullable String t_strClassName =
            getTemplateFactoryClass(
                type, engineName, engineVersion);

        if  (t_strClassName != null)
        {
            try
            {
                Class t_Class = Class.forName(t_strClassName);

                if  (t_Class != null)
                {
                    Object t_Result = t_Class.newInstance();

                    if  (t_Result instanceof TemplateFactory)
                    {
                        result = (TemplateFactory) t_Result;
                    }
                }
                else
                {
                    throw
                        new QueryJBuildException(
                            type + ".template.factory.class.not.available");
                }
            }
            catch  (@NotNull final ClassNotFoundException classNotFoundException)
            {
                throw
                    new QueryJBuildException(
                        type + ".template.factory.not.found",
                        classNotFoundException);
            }
            catch  (@NotNull final InstantiationException instantiationException)
            {
                throw
                    new QueryJBuildException(
                        "invalid." + type + ".template.factory",
                        instantiationException);
            }
            catch  (@NotNull final IllegalAccessException illegalAccessException)
            {
                throw
                    new QueryJBuildException(
                        "access.to." + type + ".template.factory.not.allowed",
                        illegalAccessException);
            }
        }

        return result;
    }


    /**
     * Builds a key by default for given type.
     * @param type the template type.
     * @return the key.
     */
    @NotNull
    protected final String buildKey(final String type)
    {
        return buildKey(type, DEFAULT_TEMPLATE);
    }

    /**
     * Builds a key using the engine information.
     * @param type the template type.
     * @param engineName the engine name.
     * @return the key.
     */
    @NotNull
    private String buildKey(@Nullable final String type, @Nullable final String engineName)
    {
        @NotNull String result = "";

        if  (   (type       != null)
             && (engineName != null))
        {
            result = type + "." + engineName.toLowerCase();
        }

        return result;
    }

    /**
     * Builds a key using the engine information.
     * @param type the template type.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @return the key.
     */
    @NotNull
    private String buildKey(
        final String type, final String engineName, @Nullable final String engineVersion)
    {
        @NotNull String result = buildKey(type, engineName);

        if  (engineVersion != null)
        {
            result += "-" + engineVersion.toLowerCase();
        }

        return result;
    }

    /**
     * Registers the template factories.
     */
    protected void registerTemplates()
    {
        /*
         * Defaults removed since database functions are always
         * dependent on the database: capitalized words,
         * return types...
        registerDefaultTemplates();
         */
        registerOracleTemplates();
        registerMySQLTemplates();
        registerAccessTemplates();
    }

    /**
     * Registers default template factories.
     */
    protected void registerDefaultTemplates()
    {
        registerDefaultTimeFunctionsTemplates();
        registerDefaultNumericFunctionsTemplates();
        registerDefaultTextFunctionsTemplates();
        registerDefaultSystemFunctionsTemplates();
    }

    /**
     * Registers default time functions templates.
     */
    protected void registerDefaultTimeFunctionsTemplates()
    {
        addDefaultTemplateFactoryClass(
            TIME_FUNCTIONS_TEMPLATE,
            TimeFunctionsTemplateGenerator.class.getName());

        addDefaultTemplateFactoryClass(
            TIME_FUNCTIONS_TEST_TEMPLATE,
            TimeFunctionsTestTemplateGenerator.class.getName());
    }

    /**
     * Registers default numeric functions templates.
     */
    protected void registerDefaultNumericFunctionsTemplates()
    {
        addDefaultTemplateFactoryClass(
            NUMERIC_FUNCTIONS_TEMPLATE,
            NumericFunctionsTemplateGenerator.class.getName());

        addDefaultTemplateFactoryClass(
            NUMERIC_FUNCTIONS_TEST_TEMPLATE,
            NumericFunctionsTestTemplateGenerator.class.getName());
    }

    /**
     * Registers default text functions templates.
     */
    protected void registerDefaultTextFunctionsTemplates()
    {
        addDefaultTemplateFactoryClass(
            TEXT_FUNCTIONS_TEMPLATE,
            TextFunctionsTemplateGenerator.class.getName());

        addDefaultTemplateFactoryClass(
            TEXT_FUNCTIONS_TEST_TEMPLATE,
            TextFunctionsTestTemplateGenerator.class.getName());
    }

    /**
     * Registers default system functions templates.
     */
    protected void registerDefaultSystemFunctionsTemplates()
    {
        addDefaultTemplateFactoryClass(
            SYSTEM_FUNCTIONS_TEMPLATE,
            SystemFunctionsTemplateGenerator.class.getName());

        addDefaultTemplateFactoryClass(
            SYSTEM_FUNCTIONS_TEST_TEMPLATE,
            SystemFunctionsTestTemplateGenerator.class.getName());
    }

    /**
     * Registers Oracle template factories.
     */
    protected void registerOracleTemplates()
    {
        registerOracleTimeFunctionsTemplates();
        registerOracleNumericFunctionsTemplates();
        registerOracleTextFunctionsTemplates();
        registerOracleSystemFunctionsTemplates();
    }

    /**
     * Registers Oracle time functions templates.
     */
    protected void registerOracleTimeFunctionsTemplates()
    {
        addTemplateFactoryClass(
            TIME_FUNCTIONS_TEMPLATE,
            "oracle",
            OracleTimeFunctionsTemplateGenerator.class.getName());

        addTemplateFactoryClass(
            TIME_FUNCTIONS_TEMPLATE,
            "oracle",
              "Oracle8i Enterprise Edition Release 8.1.7.0.1 - "
            + "Production\n JServer Release 8.1.7.0.1 - Production",
            OracleTimeFunctionsTemplateGenerator.class.getName());

        addTemplateFactoryClass(
            TIME_FUNCTIONS_TEST_TEMPLATE,
            "oracle",
            OracleTimeFunctionsTestTemplateGenerator.class.getName());

        addTemplateFactoryClass(
            TIME_FUNCTIONS_TEST_TEMPLATE,
            "oracle",
              "Oracle8i Enterprise Edition Release 8.1.7.0.1 - "
            + "Production\n JServer Release 8.1.7.0.1 - Production",
            OracleTimeFunctionsTestTemplateGenerator.class.getName());
    }

    /**
     * Registers Oracle numeric functions templates.
     */
    protected void registerOracleNumericFunctionsTemplates()
    {
        addTemplateFactoryClass(
            NUMERIC_FUNCTIONS_TEMPLATE,
            "oracle",
            OracleNumericFunctionsTemplateGenerator.class.getName());

        addTemplateFactoryClass(
            NUMERIC_FUNCTIONS_TEMPLATE,
            "oracle",
              "Oracle8i Enterprise Edition Release 8.1.7.0.1 - "
            + "Production\n JServer Release 8.1.7.0.1 - Production",
            OracleNumericFunctionsTemplateGenerator.class.getName());

        addTemplateFactoryClass(
            NUMERIC_FUNCTIONS_TEST_TEMPLATE,
            "oracle",
            OracleNumericFunctionsTestTemplateGenerator.class.getName());

        addTemplateFactoryClass(
            NUMERIC_FUNCTIONS_TEST_TEMPLATE,
            "oracle",
              "Oracle8i Enterprise Edition Release 8.1.7.0.1 - "
            + "Production\n JServer Release 8.1.7.0.1 - Production",
            OracleNumericFunctionsTestTemplateGenerator.class.getName());
    }

    /**
     * Registers Oracle text functions templates.
     */
    protected void registerOracleTextFunctionsTemplates()
    {
        addTemplateFactoryClass(
            TEXT_FUNCTIONS_TEMPLATE,
            "oracle",
            OracleTextFunctionsTemplateGenerator.class.getName());

        addTemplateFactoryClass(
            TEXT_FUNCTIONS_TEMPLATE,
            "oracle",
              "Oracle8i Enterprise Edition Release 8.1.7.0.1 - "
            + "Production\n JServer Release 8.1.7.0.1 - Production",
            OracleTextFunctionsTemplateGenerator.class.getName());

        addTemplateFactoryClass(
            TEXT_FUNCTIONS_TEST_TEMPLATE,
            "oracle",
            OracleTextFunctionsTestTemplateGenerator.class.getName());

        addTemplateFactoryClass(
            TEXT_FUNCTIONS_TEST_TEMPLATE,
            "oracle",
              "Oracle8i Enterprise Edition Release 8.1.7.0.1 - "
            + "Production\n JServer Release 8.1.7.0.1 - Production",
            OracleTextFunctionsTestTemplateGenerator.class.getName());
    }

    /**
     * Registers Oracle system functions templates.
     */
    protected void registerOracleSystemFunctionsTemplates()
    {
        addTemplateFactoryClass(
            SYSTEM_FUNCTIONS_TEMPLATE,
            "oracle",
            OracleSystemFunctionsTemplateGenerator.class.getName());

        addTemplateFactoryClass(
            SYSTEM_FUNCTIONS_TEMPLATE,
            "oracle",
              "Oracle8i Enterprise Edition Release 8.1.7.0.1 - "
            + "Production\n JServer Release 8.1.7.0.1 - Production",
            OracleSystemFunctionsTemplateGenerator.class.getName());

        addTemplateFactoryClass(
            SYSTEM_FUNCTIONS_TEST_TEMPLATE,
            "oracle",
            OracleSystemFunctionsTestTemplateGenerator.class.getName());

        addTemplateFactoryClass(
            SYSTEM_FUNCTIONS_TEST_TEMPLATE,
            "oracle",
              "Oracle8i Enterprise Edition Release 8.1.7.0.1 - "
            + "Production\n JServer Release 8.1.7.0.1 - Production",
            OracleSystemFunctionsTestTemplateGenerator.class.getName());
    }

    /**
     * Registers MySQL template factories.
     */
    protected void registerMySQLTemplates()
    {
        registerMySQLTimeFunctionsTemplates();
        registerMySQLNumericFunctionsTemplates();
        registerMySQLTextFunctionsTemplates();
        registerMySQLSystemFunctionsTemplates();
    }

    /**
     * Registers MySQL time functions templates.
     */
    protected void registerMySQLTimeFunctionsTemplates()
    {
        addTemplateFactoryClass(
            TIME_FUNCTIONS_TEMPLATE,
            "mysql",
            MySQLTimeFunctionsTemplateGenerator.class.getName());

        addTemplateFactoryClass(
            TIME_FUNCTIONS_TEMPLATE,
            "mysql",
            "3.3.23",
            //"4.0.16",
            MySQLTimeFunctionsTemplateGenerator.class.getName());

        addTemplateFactoryClass(
            TIME_FUNCTIONS_TEST_TEMPLATE,
            "mysql",
            MySQLTimeFunctionsTestTemplateGenerator.class.getName());

        addTemplateFactoryClass(
            TIME_FUNCTIONS_TEST_TEMPLATE,
            "mysql",
            "3.3.23",
            MySQLTimeFunctionsTestTemplateGenerator.class.getName());
    }

    /**
     * Registers MySQL numeric functions templates.
     */
    protected void registerMySQLNumericFunctionsTemplates()
    {
        addTemplateFactoryClass(
            NUMERIC_FUNCTIONS_TEMPLATE,
            "mysql",
            MySQLNumericFunctionsTemplateGenerator.class.getName());

        addTemplateFactoryClass(
            NUMERIC_FUNCTIONS_TEMPLATE,
            "mysql",
            "3.3.23",
            MySQLNumericFunctionsTemplateGenerator.class.getName());

        addTemplateFactoryClass(
            NUMERIC_FUNCTIONS_TEST_TEMPLATE,
            "mysql",
            MySQLNumericFunctionsTestTemplateGenerator.class.getName());

        addTemplateFactoryClass(
            NUMERIC_FUNCTIONS_TEST_TEMPLATE,
            "mysql",
            "3.3.23",
            MySQLNumericFunctionsTestTemplateGenerator.class.getName());
    }

    /**
     * Registers MySQL text functions templates.
     */
    protected void registerMySQLTextFunctionsTemplates()
    {
        addTemplateFactoryClass(
            TEXT_FUNCTIONS_TEMPLATE,
            "mysql",
            MySQLTextFunctionsTemplateGenerator.class.getName());

        addTemplateFactoryClass(
            TEXT_FUNCTIONS_TEMPLATE,
            "mysql",
            "3.3.23",
            MySQLTextFunctionsTemplateGenerator.class.getName());

        addTemplateFactoryClass(
            TEXT_FUNCTIONS_TEST_TEMPLATE,
            "mysql",
            MySQLTextFunctionsTestTemplateGenerator.class.getName());

        addTemplateFactoryClass(
            TEXT_FUNCTIONS_TEST_TEMPLATE,
            "mysql",
            "3.3.23",
            MySQLTextFunctionsTestTemplateGenerator.class.getName());
    }

    /**
     * Registers MySQL system functions templates.
     */
    protected void registerMySQLSystemFunctionsTemplates()
    {
        addTemplateFactoryClass(
            SYSTEM_FUNCTIONS_TEMPLATE,
            "mysql",
            MySQLSystemFunctionsTemplateGenerator.class.getName());

        addTemplateFactoryClass(
            SYSTEM_FUNCTIONS_TEMPLATE,
            "mysql",
            "3.3.23",
            MySQLSystemFunctionsTemplateGenerator.class.getName());

        addTemplateFactoryClass(
            SYSTEM_FUNCTIONS_TEST_TEMPLATE,
            "mysql",
            MySQLSystemFunctionsTestTemplateGenerator.class.getName());

        addTemplateFactoryClass(
            SYSTEM_FUNCTIONS_TEST_TEMPLATE,
            "mysql",
            "3.3.23",
            MySQLSystemFunctionsTestTemplateGenerator.class.getName());
    }

    /**
     * Registers Access template factories.
     */
    protected void registerAccessTemplates()
    {
        //registerAccessTimeFunctionsTemplates();
    }

    /**
     * Registers Access time functions templates.
     *
    protected void registerAccessTimeFunctionsTemplates()
    {
        addTemplateFactoryClass(
            TIME_FUNCTIONS_TEMPLATE,
            "access",
            AccessTimeFunctionsTemplateGenerator.class.getName());

        addTemplateFactoryClass(
            TIME_FUNCTIONS_TEMPLATE,
            "access",
            "04.00.0000",
            AccessTimeFunctionsTemplateGenerator.class.getName());

        addTemplateFactoryClass(
            TIME_FUNCTIONS_TEST_TEMPLATE,
            "access",
            AccessTimeFunctionsTestTemplateGenerator.class.getName());

        addTemplateFactoryClass(
            TIME_FUNCTIONS_TEST_TEMPLATE,
            "access",
            "04.00.0000",
            AccessTimeFunctionsTestTemplateGenerator.class.getName());
    }
    */
}
