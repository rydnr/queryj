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
 * Filename: SqlXmlParserImpl.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Is able to read the contents contained in QueryJ's sql.xml files.
 *
 */
package org.acmsl.queryj.customsql.xml;

/*
 * Importing project-specific classes.
 */
import org.acmsl.queryj.customsql.*;
import org.acmsl.queryj.metadata.SqlPropertyDAO;
import org.acmsl.queryj.tools.QueryJBuildException;

/*
 * Importing some ACM-SL Commons classes.
 */
import org.acmsl.commons.logging.UniqueLogFactory;

/*
 * Importing some JDK classes.
 */
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;

/*
 * Importing Digester classes.
 */
import org.acmsl.queryj.metadata.SqlDAO;
import org.acmsl.queryj.metadata.SqlParameterDAO;
import org.acmsl.queryj.metadata.SqlResultDAO;
import org.apache.commons.digester.Digester;

/*
 * Importing Jakarta Commons Logging classes
 */
import org.apache.commons.logging.Log;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Is able to read the contents contained in QueryJ's sql.xml files.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public class SqlXmlParserImpl
    implements  CustomSqlProvider,
                SqlXmlParser
{
    public static final long serialVersionUID = 4622172516611441363L;
    /**
     * The complete sql.xml contents.
     */
    private Collection m__cSqlXmlContents;

    /**
     * The key-value pairs.
     */
    private Map m__mSqlXmlContents;

    /**
     * The input stream.
     */
    private transient InputStream m__isInput;

    /**
     * The class loader.
     */
    private transient ClassLoader m__ClassLoader;

    /**
     * Creates a SqlXmlParserImpl with given input stream.
     * @param input the input stream.
     */
    public SqlXmlParserImpl(@NotNull final InputStream input)
    {
        immutableSetInput(input);
    }

    /**
     * Retrieves the {@link SqlDAO} instance.
     * @return such instance.
     */
    @NotNull
    @Override
    public SqlDAO getSqlDAO()
    {
        return new SqlXmlParserSqlDAO(this);
    }

    /**
     * Retrieves the {@link SqlResultDAO} instance.
     * @return such instance.
     */
    @NotNull
    @Override
    public SqlResultDAO getSqlResultDAO()
    {
        return new SqlXmlParserResultDAO(this);
    }

    /**
     * Retrieves the {@link SqlParameterDAO} instance.
     * @return such instance.
     */
    @NotNull
    @Override
    public SqlParameterDAO getSqlParameterDAO()
    {
        return new CustomSqlProviderSqlParameterDAO(this);
    }

    /**
     * Retrieves the {@link SqlPropertyDAO} instance.
     * @return such instance.
     */
    @NotNull
    @Override
    public SqlPropertyDAO getSqlPropertyDAO()
    {
        return new CustomSqlProviderSqlPropertyDAO(this);
    }

    /**
     * Specifies the map with sql.xml contents.
     * @param map the new map.
     */
    protected void setMap(final Map map)
    {
        m__mSqlXmlContents = map;
    }

    /**
     * Retrieves the map with sql.xml contents.
     * return such map.
     */
    public Map getMap()
    {
        return m__mSqlXmlContents;
    }

    /**
     * Specifies the sql.xml element collection.
     * @param collection the new collection.
     */
    protected void setCollection(final Collection collection)
    {
        m__cSqlXmlContents = collection;
    }

    /**
     * Retrieves the sql.xml element collection.
     * return such collection.
     */
    @SuppressWarnings("unchecked")
    @NotNull
    public Collection getCollection()
    {
        return m__cSqlXmlContents;
    }

    /**
     * Specifies the class loader (to give it to Digester).
     * @param classLoader the class loader.
     */
    @SuppressWarnings("unused")
    public void setClassLoader(final ClassLoader classLoader)
    {
        m__ClassLoader = classLoader;
    }

    /**
     * Retrieves the class loader.
     * @return such instance.
     */
    public ClassLoader getClassLoader()
    {
        return m__ClassLoader;
    }

    /**
     * Specifies the input stream.
     * @param input the input stream.
     */
    private void immutableSetInput(final InputStream input)
    {
        m__isInput = input;
    }

    /**
     * Specifies the input stream.
     * @param input the input stream.
     */
    @SuppressWarnings("unused")
    protected void setInput(final InputStream input)
    {
        immutableSetInput(input);
    }

    /**
     * Retrieves the input stream.
     * @return such stream.
     */
    protected InputStream getInput()
    {
        return m__isInput;
    }

    /**
     * Loads the information from the XML resource.
     * @return the custom SQL information.
     * @throws QueryJBuildException if the information cannot be read.
     */
    @Nullable
    protected Map load()
      throws  QueryJBuildException
    {
        return load(configureDigester(getClassLoader()), getInput());
    }

    /**
     * Loads the information from the XML resource.
     * @param digester the Digester instance.
     * @param input the input stream.
     * @return the information.
     * @throws QueryJBuildException if the information cannot be read.
     */
    @Nullable
    protected synchronized Map load(
        @Nullable final Digester digester, @Nullable final InputStream input)
      throws  QueryJBuildException
    {
        @Nullable Map result = null;

        if  (digester != null)
        {
            @NotNull Collection collection = new ArrayList();

            digester.push(collection);

            try
            {
                if  (input != null)
                {
                    digester.parse(input);

                    result = new Hashtable();
                    processSqlXml(collection, result);
                    setCollection(collection);
                    setMap(result);
                }
            }
            catch  (@NotNull final RuntimeException exception)
            {
                throw exception;
            }
            catch  (@NotNull final Exception exception)
            {
                try
                {
                    Log t_Log = UniqueLogFactory.getLog(SqlXmlParser.class);

                    if  (t_Log != null)
                    {
                        t_Log.error(
                            "Cannot read sql.xml information.",
                            exception);
                    }
                }
                catch  (@NotNull final Throwable throwable)
                {
                    // class-loading problem.
                }

                throw
                    new QueryJBuildException(
                        "cannot.read.custom.sql.xml",
                        exception);
            }
        }

        return result;
    }

    /**
     * Creates and configures a digester instance.
     * @param classLoader <i>optional</i> the class loader.
     * @return a configured digester instance.
     */
    @NotNull
    protected Digester configureDigester(@Nullable final ClassLoader classLoader)
    {
        @NotNull Digester result = new Digester();

        if  (classLoader != null)
        {
            result.setClassLoader(classLoader);
        }

        // <sql-list>

        //   <sql>
        result.addFactoryCreate(
            "sql-list/sql",
            new SqlElementFactory());

        //     <description>
        result.addRule(
            "sql-list/sql/description",
            new UntrimmedCallMethodRule("setDescription", 0));
        //     </description>

        //     <value>
        result.addRule(
            "sql-list/sql/value",
            new UntrimmedCallMethodRule("setValue", 0));
        //     </value>

        //     <parameter-ref>
        result.addFactoryCreate(
            "sql-list/sql/parameter-ref",
            new ParameterRefElementFactory());
        result.addSetNext("sql-list/sql/parameter-ref", "add");
        //     </parameter-ref>

        //     <result-ref>
        result.addFactoryCreate(
            "sql-list/sql/result-ref",
            new ResultRefElementFactory());
        result.addSetNext(
            "sql-list/sql/result-ref", "setResultRef");
        //     </result-ref>

        //     <connection-flags-ref>
        result.addFactoryCreate(
            "sql-list/sql/connection-flags-ref",
            new ConnectionFlagsRefElementFactory());
        result.addSetNext(
            "sql-list/sql/connection-flags-ref", "setConnectionFlagsRef");
        //     </connection-flags-ref>

        //     <statement-flags-ref>
        result.addFactoryCreate(
            "sql-list/sql/statement-flags-ref",
            new StatementFlagsRefElementFactory());
        result.addSetNext(
            "sql-list/sql/statement-flags-ref", "setStatementFlagsRef");
        //     </statement-flags-ref>

        //     <resultset-flags-ref>
        result.addFactoryCreate(
            "sql-list/sql/resultset-flags-ref",
            new ResultSetFlagsRefElementFactory());
        result.addSetNext(
            "sql-list/sql/resultset-flags-ref", "setResultSetFlagsRef");
        //     </resultset-flags-ref>

        result.addSetNext("sql-list/sql", "add");

        //   </sql>

        //   <parameter-list>

        /*
        result.addObjectCreate(
            "sql-list/parameter-list",
            "java.util.ArrayList");
        */

        //     <parameter>
        result.addFactoryCreate(
            "sql-list/parameter-list/parameter",
            new ParameterElementFactory());
        result.addSetRoot(
            "sql-list/parameter-list/parameter", "add");
        //     </parameter>
        //   </parameter-list>

        //   <result-list>
        //     <result>
        result.addFactoryCreate(
            "sql-list/result-list/result",
            new ResultElementFactory());
        result.addSetRoot("sql-list/result-list/result", "add");

        //       <property-ref>
        result.addFactoryCreate(
            "sql-list/result-list/result/property-ref",
            new PropertyRefElementFactory());
        result.addSetNext(
            "sql-list/result-list/result/property-ref", "add");
        //       </property-ref>
        //     </result>
        //   </result-list>

        //   <property-list>
        //     <property>
        result.addFactoryCreate(
            "sql-list/property-list/property",
            new PropertyElementFactory());
        result.addSetRoot(
            "sql-list/property-list/property", "add");
        //     </property>
        //   </property-list>

        //   <flag-list>
        //     <connection-flags>
        result.addFactoryCreate(
            "sql-list/flag-list/connection-flags",
            new ConnectionFlagsElementFactory());
        result.addSetRoot(
            "sql-list/flag-list/connection-flags", "add");
        //     </connection-flags>
        //     <statement-flags>
        result.addFactoryCreate(
            "sql-list/flag-list/statement-flags",
            new StatementFlagsElementFactory());
        result.addSetRoot(
            "sql-list/flag-list/statement-flags", "add");
        //     </statement-flags>
        //     <resultset-flags>
        result.addFactoryCreate(
            "sql-list/flag-list/resultset-flags",
            new ResultSetFlagsElementFactory());
        result.addSetRoot(
            "sql-list/flag-list/resultset-flags", "add");
        //     </resultset-flags>
        //   </flag-list>

        // <sql-list>

        return result;
    }

    /**
     * Processes given sql.xml information.
     * @param collection the sql.xml element collection.
     * @param map the sql.xml map.
     */
    @SuppressWarnings("unchecked")
    protected synchronized void processSqlXml(
        @NotNull final Collection collection, @NotNull final Map map)
    {
        Iterator t_Iterator = collection.iterator();

        if  (t_Iterator != null)
        {
            while  (t_Iterator.hasNext())
            {
                Object t_Object = t_Iterator.next();

                if  (t_Object != null)
                {
                    if  (t_Object instanceof Sql)
                    {
                        map.put(
                            buildSqlKey(t_Object), t_Object);
                    }
                    else if  (t_Object instanceof Parameter)
                    {
                        map.put(
                            buildParameterKey(
                                (AbstractIdElement) t_Object),
                            t_Object);
                    }
                    else if  (t_Object instanceof Result)
                    {
                        map.put(
                            buildResultKey(
                                (AbstractIdElement) t_Object),
                            t_Object);
                    }
                    else if  (t_Object instanceof Property)
                    {
                        map.put(
                            buildPropertyKey(
                                (AbstractIdElement) t_Object),
                            t_Object);
                    }
                    else if  (t_Object instanceof ConnectionFlagsElement)
                    {
                        map.put(
                            buildConnectionFlagsKey(
                                (AbstractIdElement) t_Object),
                            t_Object);
                    }
                    else if  (t_Object instanceof StatementFlagsElement)
                    {
                        map.put(
                            buildStatementFlagsKey(
                                (AbstractIdElement) t_Object),
                            t_Object);
                    }
                    else if  (t_Object instanceof ResultSetFlagsElement)
                    {
                        map.put(
                            buildResultSetFlagsKey(
                                (AbstractIdElement) t_Object),
                            t_Object);
                    }
                }
            }
        }
    }

    /**
     * Parses the sql.xml associated to this instance.
     * @throws QueryJBuildException if the information cannot be read.
     */
    public void parse()
        throws  QueryJBuildException
    {
        @Nullable Map t_mSqlXmlMap = getMap();

        if  (t_mSqlXmlMap == null)
        {
            t_mSqlXmlMap = load();
            setMap(t_mSqlXmlMap);
        }
    }

    /**
     * Builds a key for &lt;sql&gt; elements.
     * @param sqlElement such element.
     * @return the key.
     */
    @NotNull
    protected Object buildSqlKey(final Object sqlElement)
    {
        return "\\/sql\\::" + sqlElement;
    }

    /**
     * Builds a key for &lt;parameter&gt; elements.
     * @param parameterElement such element.
     * @return the key.
     */
    @NotNull
    protected Object buildParameterKey(
        @NotNull final AbstractIdElement parameterElement)
    {
        return buildParameterKey(parameterElement.getId());
    }

    /**
     * Builds a key for &lt;parameter&gt; elements, via their id.
     * @param parameterElementId such element id.
     * @return the key.
     */
    @NotNull
    protected Object buildParameterKey(final String parameterElementId)
    {
        return "\\/parameter\\::" + parameterElementId;
    }

    /**
     * Builds a key for &lt;result&gt; elements.
     * @param resultElement such element.
     * @return the key.
     */
    @NotNull
    protected Object buildResultKey(@NotNull final AbstractIdElement resultElement)
    {
        return buildResultKey(resultElement.getId());
    }

    /**
     * Builds a key for &lt;result&gt; elements, via their id.
     * @param resultElementId such element id.
     * @return the key.
     */
    @NotNull
    protected Object buildResultKey(final String resultElementId)
    {
        return "\\/result\\::" + resultElementId;
    }

    /**
     * Builds a key for &lt;property&gt; elements.
     * @param propertyElement such element.
     * @return the key.
     */
    @NotNull
    protected Object buildPropertyKey(@NotNull final AbstractIdElement propertyElement)
    {
        return buildPropertyKey(propertyElement.getId());
    }

    /**
     * Builds a key for &lt;property&gt; elements, via their id.
     * @param propertyElementId such element id.
     * @return the key.
     */
    @NotNull
    protected Object buildPropertyKey(final String propertyElementId)
    {
        return "\\/property\\::" + propertyElementId;
    }

    /**
     * Builds a key for &lt;connection-flags&gt; elements.
     * @param connectionFlagsElement such element.
     * @return the key.
     */
    @NotNull
    protected Object buildConnectionFlagsKey(
        @NotNull final AbstractIdElement connectionFlagsElement)
    {
        return buildConnectionFlagsKey(connectionFlagsElement.getId());
    }

    /**
     * Builds a key for &lt;connection-flags&gt; elements, via their id.
     * @param connectionFlagsElementId such element id.
     * @return the key.
     */
    @NotNull
    protected Object buildConnectionFlagsKey(final String connectionFlagsElementId)
    {
        return "\\/connection|flags\\::" + connectionFlagsElementId;
    }

    /**
     * Builds a key for &lt;statement-flags&gt; elements.
     * @param statementFlagsElement such element.
     * @return the key.
     */
    @NotNull
    protected Object buildStatementFlagsKey(
        @NotNull final AbstractIdElement statementFlagsElement)
    {
        return buildStatementFlagsKey(statementFlagsElement.getId());
    }

    /**
     * Builds a key for &lt;statement-flags&gt; elements, via their id.
     * @param statementFlagsElementId such element id.
     * @return the key.
     */
    @NotNull
    protected Object buildStatementFlagsKey(final String statementFlagsElementId)
    {
        return "\\/statement|flags\\::" + statementFlagsElementId;
    }

    /**
     * Builds a key for &lt;resultset-flags&gt; elements.
     * @param resultSetFlagsElement such element.
     * @return the key.
     */
    @NotNull
    protected Object buildResultSetFlagsKey(
        @NotNull final AbstractIdElement resultSetFlagsElement)
    {
        return buildResultSetFlagsKey(resultSetFlagsElement.getId());
    }

    /**
     * Builds a key for &lt;resultset-flags&gt; elements, via their id.
     * @param resultSetFlagsElementId such element id.
     * @return the key.
     */
    @NotNull
    protected Object buildResultSetFlagsKey(final String resultSetFlagsElementId)
    {
        return "\\/resultset|flags\\::" + resultSetFlagsElementId;
    }

    /**
     * Resolves the parameter reference.
     * @param reference such reference.
     * @return the referenced parameter.
     */
    @Override
    @Nullable
    public Parameter resolveParameterReference(
        @NotNull final String reference)
    {
        return resolveParameterReference(reference, getMap());
    }

    /**
     * Resolves the parameter reference.
     * @param reference such reference.
     * @param map the map.
     * @return the referenced parameter.
     */
    @Nullable
    protected Parameter resolveParameterReference(
        @NotNull final String reference,
        @Nullable final Map map)
    {
        @Nullable ParameterElement result = null;

        if  (map != null)
        {
            result =
                (ParameterElement)
                    map.get(buildParameterKey(reference));
        }

        return result;
    }

    /**
     * Resolves the result reference.
     * @param reference such reference.
     * @return the referenced result.
     */
    @Override
    @Nullable
    public Result resolveResultReference(
        @NotNull final String reference)
    {
        return resolveResultReference(reference, getMap());
    }

    /**
     * Resolves the result reference.
     * @param reference such reference.
     * @param map the map.
     * @return the referenced result.
     */
    @Nullable
    protected Result resolveResultReference(
        @NotNull final String reference,
        @Nullable final Map map)
    {
        @Nullable Result result = null;

        if  (map != null)
        {
            result =
                (Result)
                    map.get(buildResultKey(reference));
        }

        return result;
    }

    /**
     * Resolves the property reference.
     * @param reference the reference.
     * @return the referenced property.
     */
    @Override
    @Nullable
    public PropertyElement resolvePropertyReference(
        @NotNull final String reference)
    {
        return resolvePropertyReference(reference, getMap());
    }

    /**
     * Resolves the property reference.
     * @param id the property id.
     * @param map the map.
     * @return the referenced property.
     */
    @Nullable
    protected PropertyElement resolvePropertyReference(
        @NotNull final String id,
        @Nullable final Map map)
    {
        @Nullable PropertyElement result = null;

        if  (map != null)
        {
            result = (PropertyElement) map.get(buildPropertyKey(id));
        }

        return result;
    }

    /**
     * Resolves the connection-flags reference.
     * @param reference such reference.
     * @return the referenced connection flags.
     */
    @Override
    @Nullable
    public ConnectionFlagsElement resolveConnectionFlagsReference(
        @NotNull final String reference)
    {
        return resolveConnectionFlagsReference(reference, getMap());
    }

    /**
     * Resolves given connection-flags reference.
     * @param reference such reference.
     * @param map the map.
     * @return the referenced connection flags.
     */
    @Nullable
    protected ConnectionFlagsElement resolveConnectionFlagsReference(
        @NotNull final String reference,
        @Nullable final Map map)
    {
        @Nullable ConnectionFlagsElement result = null;

        if  (map != null)
        {
            result =
                (ConnectionFlagsElement)
                    map.get(buildConnectionFlagsKey(reference));
        }

        return result;
    }

    /**
     * Resolves the statement-flags reference.
     * @param reference such reference.
     * @return the referenced statement flags.
     */
    @Override
    @Nullable
    public StatementFlagsElement resolveStatementFlagsReference(
        @NotNull final String reference)
    {
        return resolveStatementFlagsReference(reference, getMap());
    }

    /**
     * Resolves given statement-flags reference.
     * @param reference such reference.
     * @param map the map.
     * @return the referenced statement flags.
     */
    @Nullable
    protected StatementFlagsElement resolveStatementFlagsReference(
        @NotNull final String reference,
        @Nullable final Map map)
    {
        @Nullable StatementFlagsElement result = null;

        if  (map != null)
        {
            result =
                (StatementFlagsElement)
                    map.get(buildStatementFlagsKey(reference));
        }

        return result;
    }

    /**
     * Resolves the resultset-flags reference.
     * @param reference such reference.
     * @return the referenced resultset flags.
     */
    @Override
    @Nullable
    public ResultSetFlagsElement resolveResultSetFlagsReference(
        @NotNull final String reference)
    {
        return resolveResultSetFlagsReference(reference, getMap());
    }

    /**
     * Resolves given resultset-flags reference.
     * @param reference such reference.
     * @param map the map.
     * @return the referenced resultset flags.
     */
    @Nullable
    protected ResultSetFlagsElement resolveResultSetFlagsReference(
        @NotNull final String reference,
        @Nullable final Map map)
    {
        @Nullable ResultSetFlagsElement result = null;

        if  (map != null)
        {
            result =
                (ResultSetFlagsElement)
                    map.get(buildResultSetFlagsKey(reference));
        }

        return result;
    }

    /**
     * Adds a new result.
     * @param id the result id.
     * @param result the <code>Result</code> instance.
     */
    @Override
    public void addResult(@NotNull final String id, @NotNull final Result result)
    {
        // TODO
    }

    /**
     * Adds a new property.
     * @param id the property id.
     * @param name the property name.
     * @param type the property type.
     */
    @Override
    public void addProperty(
        @NotNull final String id, @NotNull final String name, @NotNull final String type)
    {
        // TODO
    }
}
