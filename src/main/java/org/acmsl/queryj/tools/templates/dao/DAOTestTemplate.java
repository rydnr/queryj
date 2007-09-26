/*
                        QueryJ

    Copyright (C) 2002-2007  Jose San Leandro Armendariz
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
 * Filename: DAOTestTemplate.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Template for creating JUnit tests to ensure generated DAOs
 *              are working fine and the connection correctly managed.
 */
package org.acmsl.queryj.tools.templates.dao;

/*
 * Importing some project-specific classes.
 */
import org.acmsl.queryj.tools.customsql.CustomSqlProvider;
import org.acmsl.queryj.tools.metadata.DecoratorFactory;
import org.acmsl.queryj.tools.metadata.MetadataManager;
import org.acmsl.queryj.tools.templates.BasePerTableTemplate;

/*
 * Importing StringTemplate classes.
 */
import org.antlr.stringtemplate.StringTemplateGroup;

/*
 * Importing some JDK classes.
 */
import java.util.Map;

/**
 * Template for creating JUnit tests to ensure generated DAOs
 * are working fine and the connection correctly managed.
 * @author <a href="mailto:chous@acm-sl.org"
           >Jose San Leandro</a>
 */
public class DAOTestTemplate
    extends  BasePerTableTemplate
{
    /**
     * The jdbc driver class name.
     */
    private String m__strJdbcDriver;

    /**
     * The JDBC url.
     */
    private String m__strJdbcUrl;

    /**
     * The JDBC username.
     */
    private String m__strJdbcUser;

    /**
     * The JDBC password.
     */
    private String m__strJdbcPassword;

    /**
     * Builds a <code>DAOTestTemplate</code> using given information.
     * @param tableName the table name.
     * @param metadataManager the database metadata manager.
     * @param customSqlProvider the CustomSqlProvider instance.
     * @param header the header.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @param packageName the package name.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param quote the identifier quote string.
     * @param basePackageName the base package name.
     * @param repositoryName the repository name.
     * @param jmx whether to support JMX.
     * @param jndiLocation the location of the datasource in JNDI.
     * @param jdbcDriver the JDBC driver.
     * @param jdbcUrl the JDBC url.
     * @param jdbcUser the JDBC username.
     * @param jdbcPassword the JDBC password.
     */
    public DAOTestTemplate(
        final String tableName,
        final MetadataManager metadataManager,
        final CustomSqlProvider customSqlProvider,
        final String header,
        final DecoratorFactory decoratorFactory,
        final String packageName,
        final String engineName,
        final String engineVersion,
        final String quote,
        final String basePackageName,
        final String repositoryName,
        final boolean jmx,
        final String jndiLocation,
        final String jdbcDriver,
        final String jdbcUrl,
        final String jdbcUser,
        final String jdbcPassword)
    {
        super(
            tableName,
            metadataManager,
            customSqlProvider,
            header,
            decoratorFactory,
            packageName,
            engineName,
            engineVersion,
            quote,
            basePackageName,
            repositoryName,
            jmx,
            jndiLocation);

        immutableSetJdbcDriver(jdbcDriver);
        immutableSetJdbcUrl(jdbcUrl);
        immutableSetJdbcUser(jdbcUser);
        immutableSetJdbcPassword(jdbcPassword);
    }

    /**
     * Specifies the JDBC driver.
     * @param driver such information.
     */
    protected final void immutableSetJdbcDriver(final String driver)
    {
        m__strJdbcDriver = driver;
    }

    /**
     * Specifies the JDBC driver.
     * @param driver such information.
     */
    protected void setJdbcDriver(final String driver)
    {
        immutableSetJdbcDriver(driver);
    }

    /**
     * Retrieves the JDBC driver.
     * @return such information.
     */
    public String getJdbcDriver()
    {
        return m__strJdbcDriver;
    }

    /**
     * Specifies the JDBC url.
     * @param url such information.
     */
    protected final void immutableSetJdbcUrl(final String url)
    {
        m__strJdbcUrl = url;
    }

    /**
     * Specifies the JDBC url.
     * @param url such information.
     */
    protected void setJdbcUrl(final String url)
    {
        immutableSetJdbcUrl(url);
    }

    /**
     * Retrieves the JDBC url.
     * @return such information.
     */
    public String getJdbcUrl()
    {
        return m__strJdbcUrl;
    }

    /**
     * Specifies the JDBC user.
     * @param user such information.
     */
    protected final void immutableSetJdbcUser(final String user)
    {
        m__strJdbcUser = user;
    }

    /**
     * Specifies the JDBC user.
     * @param user such information.
     */
    protected void setJdbcUser(final String user)
    {
        immutableSetJdbcUser(user);
    }

    /**
     * Retrieves the JDBC user.
     * @return such information.
     */
    public String getJdbcUser()
    {
        return m__strJdbcUser;
    }

    /**
     * Specifies the JDBC password.
     * @param password such information.
     */
    protected final void immutableSetJdbcPassword(final String password)
    {
        m__strJdbcPassword = password;
    }

    /**
     * Specifies the JDBC password.
     * @param password such information.
     */
    protected void setJdbcPassword(final String password)
    {
        immutableSetJdbcPassword(password);
    }

    /**
     * Retrieves the JDBC password.
     * @return such information.
     */
    public String getJdbcPassword()
    {
        return m__strJdbcPassword;
    }

    /**
     * Builds a key to store the template cache.
     * @return such key.
     */
    protected Object buildTemplateCacheKey()
    {
        return "//DAOTestTemplate//";
    }

    /**
     * Retrieves the string template group.
     * @return such instance.
     */
    protected StringTemplateGroup retrieveGroup()
    {
        return retrieveGroup("/org/acmsl/queryj/dao/DAOTest.stg");
    }

    /**
     * Retrieves the template name.
     * @return such information.
     */
    public String getTemplateName()
    {
        return "DAOTest";
    }

    /**
     * Fills the common parameters.
     * @param input the input.
     * @param tableName the table name.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param jmx whether to support JMX.
     * @param metadataManager the database metadata manager.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @precondition input != null
     * @precondition tableName != null
     * @precondition engineName != null
     * @precondition engineVersion != null
     * @precondition metadataManager != null
     * @precondition decoratorFactory != null
     */
    protected void fillCommonParameters(
        final Map input,
        final String tableName,
        final String engineName,
        final String engineVersion,
        final boolean jmx,
        final MetadataManager metadataManager,
        final DecoratorFactory decoratorFactory)
    {
        fillCommonParameters(
            input,
            tableName,
            engineName,
            engineVersion,
            jmx,
            metadataManager,
            decoratorFactory,
            getJdbcDriver(),
            getJdbcUrl(),
            getJdbcUser(),
            getJdbcPassword());
    }

    /**
     * Fills the common parameters.
     * @param input the input.
     * @param tableName the table name.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param jmx whether to support JMX.
     * @param metadataManager the database metadata manager.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @param jdbcDriver the JDBC driver.
     * @param jdbcUrl the JDBC url.
     * @param jdbcUser the JDBC user.
     * @param jdbcPassword the JDBC password.
     * @precondition input != null
     * @precondition tableName != null
     * @precondition engineName != null
     * @precondition engineVersion != null
     * @precondition metadataManager != null
     * @precondition decoratorFactory != null
     */
    protected void fillCommonParameters(
        final Map input,
        final String tableName,
        final String engineName,
        final String engineVersion,
        final boolean jmx,
        final MetadataManager metadataManager,
        final DecoratorFactory decoratorFactory,
        final String jdbcDriver,
        final String jdbcUrl,
        final String jdbcUser,
        final String jdbcPassword)
    {
        super.fillCommonParameters(
            input,
            tableName,
            engineName,
            engineVersion,
            jmx,
            metadataManager,
            decoratorFactory);

        input.put("jdbc_driver", jdbcDriver);
        input.put("jdbc_url", jdbcUrl);
        input.put("jdbc_user", jdbcUser);
        input.put("jdbc_password", jdbcPassword);
    }
}
