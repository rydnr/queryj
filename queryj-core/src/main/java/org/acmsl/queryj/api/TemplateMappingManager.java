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
package org.acmsl.queryj.api;

/*
 * Importing some ACM-SL Commons classes.
 */
import org.acmsl.commons.patterns.Manager;
import org.acmsl.commons.patterns.Singleton;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * Manages the association between database engines and template
 * factory classes.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
@ThreadSafe
public class TemplateMappingManager
    implements  Manager,
                Singleton
{
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
     * The DAO factory templates attribute name.
     */
    public static final String DAO_FACTORY_TEMPLATES = "DAO.factory.templates";

    /**
     * The DAO templates attribute name.
     */
    public static final String DAO_TEMPLATES = "DAO.templates";

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
     * The JDBC DAO template type.
     */
    @SuppressWarnings("unused")
    public static final String JDBC_DAO_TEMPLATE = "jdbc.dao.template";

    /**
     * The ResultSetExtractor template type.
     */
    public static final String RESULTSET_EXTRACTOR_TEMPLATES =
        "resultset.extractor.templates";

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
     * The value object templates attribute name.
     */
    public static final String VALUE_OBJECT_TEMPLATES = "value-object.templates";

    /**
     * The value object factory templates attribute name.
     */
    public static final String VALUE_OBJECT_FACTORY_TEMPLATES = "value-object.factory.templates";

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
     * The DAOChooser template attribute name.
     */
    public static final String DAO_CHOOSER_TEMPLATE =
        "dao.chooser.template";

    /**
     * The JndiUtils template attribute name.
     */
    public static final String JNDI_UTILS_TEMPLATE =
        "jndi.utils.template";

    /**
     * The configuration properties template attribute name.
     */
    public static final String CONFIGURATION_PROPERTIES_TEMPLATE =
        "configuration.properties.template";

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
     * The JdbcTemplate template type.
     */
    public static final String JDBC_TEMPLATE_TEMPLATE =
        "jdbc.template.template";

    /**
     * The BasePreparedStatementCreator template type.
     */
    public static final String BASE_PREPARED_STATEMENT_CREATOR_TEMPLATE =
        "base.prepared.statement.creator.template";

    /**
     * The ThreadLocalBag template type.
     */
    public static final String THREAD_LOCAL_BAG_TEMPLATE =
        "thread.local.bag.template";

    /**
     * The StatisticsProvider template type.
     */
    public static final String STATISTICS_PROVIDER_TEMPLATE =
        "statistics.provider.template";

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
    protected TemplateMappingManager() {}

    /**
     * Retrieves a <code>TemplateMappingManager</code> instance.
     * @return such instance.
     */
    @NotNull
    public static TemplateMappingManager getInstance()
    {
        return TemplateMappingManagerSingletonContainer.SINGLETON;
    }
}
