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
import org.acmsl.queryj.metadata.SqlDAO;
import org.acmsl.queryj.metadata.SqlParameterDAO;
import org.acmsl.queryj.metadata.SqlPropertyDAO;
import org.acmsl.queryj.metadata.SqlResultDAO;
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
import java.util.HashSet;
import java.util.List;

/*
 * Importing Digester classes.
 */
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

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * Is able to read the contents contained in QueryJ's sql.xml files.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
@ThreadSafe
public class SqlXmlParserImpl
    implements  CustomSqlProvider,
                SqlXmlParser
{
    public static final long serialVersionUID = 4622172516611441363L;

    /**
     * The input stream.
     */
    private transient InputStream m__isInput;

    /**
     * The class loader.
     */
    private transient ClassLoader m__ClassLoader;

    /**
     * The queries.
     */
    private List<Sql> m__lQueries = new ArrayList<Sql>();

    /**
     * The results.
     */
    private List<Result> m__lResults = new ArrayList<Result>();

    /**
     * The parameters.
     */
    private List<Parameter> m__lParameters = new ArrayList<Parameter>();

    /**
     * The parameter refs.
     */
    private List<ParameterRef> m__lParameterRefs = new ArrayList<ParameterRef>();

    /**
     * The properties.
     */
    private List<Property> m__lProperties = new ArrayList<Property>();

    /**
     * The property refs.
     */
    private List<PropertyRef> m__lPropertyRefs = new ArrayList<PropertyRef>();

    /**
     * The connection flags.
     */
    private List<ConnectionFlags> m__lConnectionFlags = new ArrayList<ConnectionFlags>();

    /**
     * The statement flags.
     */
    private List<StatementFlags> m__lStatementFlags = new ArrayList<StatementFlags>();

    /**
     * The result set flags.
     */
    private List<ResultSetFlags> m__lResultSetFlags = new ArrayList<ResultSetFlags>();

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
        return new SqlXmlParserParameterDAO(this);
    }

    /**
     * Retrieves the {@link SqlPropertyDAO} instance.
     * @return such instance.
     */
    @NotNull
    @Override
    public SqlPropertyDAO getSqlPropertyDAO()
    {
        return new SqlXmlParserPropertyDAO(this);
    }

    /**
     * Processes given collection.
     * @param collection the collection to process.
     */
    protected void processCollection(@NotNull final List<? extends IdentifiableElement> collection)
    {
        for (@Nullable IdentifiableElement t_Item : collection)
        {
            if (t_Item instanceof Sql)
            {
                addQuery((Sql) t_Item);
            }
            else if (t_Item instanceof Result)
            {
                addResult((Result) t_Item);
            }
            else if (t_Item instanceof Property)
            {
                addProperty((Property) t_Item);
            }
            else if (t_Item instanceof PropertyRef)
            {
                addPropertyRef((PropertyRef) t_Item);
            }
            else if (t_Item instanceof Parameter)
            {
                addParameter((Parameter) t_Item);
            }
            else if (t_Item instanceof ParameterRef)
            {
                addParameterRef((ParameterRef) t_Item);
            }
            else if (t_Item instanceof ConnectionFlags)
            {
                addConnectionFlags((ConnectionFlags) t_Item);
            }
            else if (t_Item instanceof StatementFlags)
            {
                addStatementFlags((StatementFlags) t_Item);
            }
            else if (t_Item instanceof ResultSetFlags)
            {
                addResultSetFlags((ResultSetFlags) t_Item);
            }
            else
            {
                UniqueLogFactory.getLog(SqlXmlParserImpl.class).fatal(
                    "Unexpected element : " + t_Item);
            }
        }
    }

    /**
     * Adds given query, if not already annotated.
     * @param sql the query.
     */
    protected void addQuery(@NotNull final Sql sql)
    {
        this.add(sql, getQueries());
    }

    /**
     * Adds given result, if not already annotated.
     * @param result the result.
     */
    protected void addResult(@NotNull final Result result)
    {
        add(result, getResults());
    }

    /**
     * Adds given parameter, if not already annotated.
     * @param parameter the parameter.
     */
    protected void addParameter(@NotNull final Parameter parameter)
    {
        add(parameter, getParameters());
    }

    /**
     * Adds given parameter reference, if not already annotated.
     * @param parameterRef the parameter reference.
     */
    protected void addParameterRef(@NotNull final ParameterRef parameterRef)
    {
        add(parameterRef, getParameterRefs());
    }

    /**
     * Adds given property, if not already annotated.
     * @param property the property.
     */
    protected void addProperty(@NotNull final Property property)
    {
        add(property, getProperties());
    }

    /**
     * Adds given property reference, if not already annotated.
     * @param propertyRef the property reference.
     */
    protected void addPropertyRef(@NotNull final PropertyRef propertyRef)
    {
        add(propertyRef, getPropertyRefs());
    }

    /**
     * Adds given connection flags, if not already annotated.
     * @param connectionFlags the connection flags reference.
     */
    protected void addConnectionFlags(@NotNull final ConnectionFlags connectionFlags)
    {
        add(connectionFlags, getConnectionFlagList());
    }

    /**
     * Adds given statement flags, if not already annotated.
     * @param statementFlags the statement flags reference.
     */
    protected void addStatementFlags(@NotNull final StatementFlags statementFlags)
    {
        add(statementFlags, getStatementFlagList());
    }

    /**
     * Adds given result-set flags, if not already annotated.
     * @param resultSetFlags the result-set flags reference.
     */
    protected void addResultSetFlags(@NotNull final ResultSetFlags resultSetFlags)
    {
        add(resultSetFlags, getResultSetFlagList());
    }

    /**
     * Adds given item, if not already annotated.
     * @param item the item.
     * @param items the item list.
     */
    protected <C> void add(@NotNull final C item, @NotNull final List<C> items)
    {
        if (!items.contains(item))
        {
            items.add(item);
        }
    }

    /**
     * Specifies the queries.
     * @param queries such information.
     */
    protected final void immutableSetQueries(@NotNull final List<Sql> queries)
    {
        m__lQueries = queries;
    }

    /**
     * Specifies the queries.
     * @param queries such information.
     */
    protected void setQueries(@NotNull final List<Sql> queries)
    {
        immutableSetQueries(queries);
    }

    /**
     * Retrieves the sql.xml {@link org.acmsl.queryj.metadata.vo.Table} collection.
     * return such collection.
     */
    @NotNull
    @Override
    public List<Sql> getQueries()
    {
        return m__lQueries;
    }

    /**
     * Specifies the {@link Result}s.
     * @param results the results.
     */
    protected final void immutableSetResults(@NotNull final List<Result> results)
    {
        this.m__lResults = results;
    }

    /**
     * Specifies the {@link Result}s.
     * @param results the results.
     */
    protected void setResults(@NotNull final List<Result> results)
    {
        immutableSetResults(results);
    }

    /**
     * Retrieves the sql.xml {@link org.acmsl.queryj.customsql.Result} collection.
     * return such collection.
     */
    @NotNull
    @Override
    public List<Result> getResults()
    {
        return m__lResults;
    }

    /**
     * Specifies the {@link Parameter}s.
     * @param parameters such information.
     */
    protected final void immutableSetParameters(@NotNull final List<Parameter> parameters)
    {
        this.m__lParameters = parameters;
    }

    /**
     * Specifies the {@link Parameter}s.
     * @param parameters such information.
     */
    protected void setParameters(@NotNull final List<Parameter> parameters)
    {
        immutableSetParameters(parameters);
    }

    /**
     * Retrieves the sql.xml {@link org.acmsl.queryj.customsql.Parameter} collection.
     * return such collection.
     */
    @NotNull
    @Override
    public List<Parameter> getParameters()
    {
        return m__lParameters;
    }

    /**
     * Retrieves the sql.xml {@link org.acmsl.queryj.customsql.Parameter} collection.
     * return such collection.
     */
    @NotNull
    @Override
    public List<ParameterRef> getParameterRefs()
    {
        return m__lParameterRefs;
    }

    /**
     * Specifies the {@link Property properties}.
     * @param properties the properties.
     */
    protected final void immutableSetProperties(@NotNull final List<Property> properties)
    {
        this.m__lProperties = properties;
    }

    /**
     * Specifies the {@link Property properties}.
     * @param properties the properties.
     */
    protected void setProperties(@NotNull final List<Property> properties)
    {
        immutableSetProperties(properties);
    }

    /**
     * Retrieves the sql.xml {@link org.acmsl.queryj.customsql.Property} collection.
     * return such collection.
     */
    @NotNull
    @Override
    public List<Property> getProperties()
    {
        return m__lProperties;
    }

    /**
     * Retrieves the sql.xml {@link org.acmsl.queryj.customsql.PropertyRef} collection.
     * return such collection.
     */
    @NotNull
    @Override
    public List<PropertyRef> getPropertyRefs()
    {
        return m__lPropertyRefs;
    }

    /**
     * Specifies the list of {@link ConnectionFlags}.
     * @param list such list.
     */
    protected final void immutableSetConnectionFlagList(@NotNull final List<ConnectionFlags> list)
    {
        this.m__lConnectionFlags = list;
    }

    /**
     * Specifies the list of {@link ConnectionFlags}.
     * @param list such list.
     */
    protected void setConnectionFlagList(@NotNull final List<ConnectionFlags> list)
    {
        immutableSetConnectionFlagList(list);
    }

    /**
     * Retrieves the sql.xml {@link org.acmsl.queryj.customsql.ConnectionFlags} collection.
     * return such collection.
     */
    @NotNull
    @Override
    public List<ConnectionFlags> getConnectionFlagList()
    {
        return m__lConnectionFlags;
    }

    /**
     * Specifies the list of {@link StatementFlags}.
     * @param list such list.
     */
    protected final void immutableSetStatementFlagList(@NotNull final List<StatementFlags> list)
    {
        this.m__lStatementFlags = list;
    }

    /**
     * Specifies the list of {@link StatementFlags}.
     * @param list such list.
     */
    protected void setStatementFlagList(@NotNull final List<StatementFlags> list)
    {
        immutableSetStatementFlagList(list);
    }

    /**
     * Retrieves the sql.xml {@link org.acmsl.queryj.customsql.StatementFlags} collection.
     * return such collection.
     */
    @NotNull
    @Override
    public List<StatementFlags> getStatementFlagList()
    {
        return m__lStatementFlags;
    }

    /**
     * Specifies the list of {@link ResultSetFlags}.
     * @param list such list.
     */
    protected final void immutableSetResultSetFlagList(@NotNull final List<ResultSetFlags> list)
    {
        this.m__lResultSetFlags = list;
    }

    /**
     * Specifies the list of {@link ResultSetFlags}.
     * @param list such list.
     */
    protected void setResultSetFlagList(@NotNull final List<ResultSetFlags> list)
    {
        immutableSetResultSetFlagList(list);
    }

    /**
     * Retrieves the sql.xml {@link org.acmsl.queryj.customsql.ResultSetFlags} collection.
     * return such collection.
     */
    @NotNull
    @Override
    public List<ResultSetFlags> getResultSetFlagList()
    {
        return m__lResultSetFlags;
    }

    /**
     * Specifies the class loader (to give it to Digester).
     * @param classLoader the class loader.
     */
    protected final void immutableSetClassLoader(@NotNull final ClassLoader classLoader)
    {
        m__ClassLoader = classLoader;
    }

    /**
     * Specifies the class loader (to give it to Digester).
     * @param classLoader the class loader.
     */
    @SuppressWarnings("unused")
    public void setClassLoader(@NotNull final ClassLoader classLoader)
    {
        immutableSetClassLoader(classLoader);
    }

    /**
     * Retrieves the class loader.
     * @return such instance.
     */
    @Nullable
    public ClassLoader getClassLoader()
    {
        return m__ClassLoader;
    }

    /**
     * Specifies the input stream.
     * @param input the input stream.
     */
    protected final void immutableSetInput(@NotNull final InputStream input)
    {
        m__isInput = input;
    }

    /**
     * Specifies the input stream.
     * @param input the input stream.
     */
    @SuppressWarnings("unused")
    protected void setInput(@NotNull final InputStream input)
    {
        immutableSetInput(input);
    }

    /**
     * Retrieves the input stream.
     * @return such stream.
     */
    @Nullable
    protected InputStream getInput()
    {
        return m__isInput;
    }

    /**
     * Loads the information from the XML resource.
     * @throws QueryJBuildException if the information cannot be read.
     */
    protected void load()
      throws  QueryJBuildException
    {
        load(configureDigester(getClassLoader()), getInput());
    }

    /**
     * Loads the information from the XML resource.
     * @param digester the Digester instance.
     * @param input the input stream.
     * @throws QueryJBuildException if the information cannot be read.
     */
    protected synchronized void load(
        @Nullable final Digester digester, @Nullable final InputStream input)
      throws  QueryJBuildException
    {
        if  (digester != null)
        {
            @NotNull List<? extends IdentifiableElement> collection = new ArrayList<IdentifiableElement>();

            digester.push(collection);

            try
            {
                if  (input != null)
                {
                    digester.parse(input);

                    processCollection(collection);
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
     * Parses the sql.xml associated to this instance.
     * @throws QueryJBuildException if the information cannot be read.
     */
    public void parse()
        throws  QueryJBuildException
    {
        @Nullable List<Sql> queries = getQueries();

        if  (queries.size() == 0)
        {
            load();

            postProcess();
        }
    }

    /**
     * Post processes the parsed contents.
     */
    protected void postProcess()
    {
        setQueries(postProcess(getQueries()));
        setResults(postProcess(getResults()));
        setParameters(postProcess(getParameters()));
        setProperties(postProcess(getProperties()));
        setConnectionFlagList(postProcess(getConnectionFlagList()));
        setStatementFlagList(postProcess(getStatementFlagList()));
        setResultSetFlagList(postProcess(getResultSetFlagList()));
    }

    /**
     * Post-processes given items.
     * @param items the items.
     * @return the processed items.
     */
    @NotNull
    protected <T extends IdentifiableElement> List<T> postProcess(@NotNull final List<T> items)
    {
        List<T> result;

        HashSet<T> aux = new HashSet<T>(items.size());
        aux.addAll(items);

        result = new ArrayList<T>(aux.size());
        result.addAll(aux);

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
        addResult(id, result, getResults());
    }

    /**
     * Adds a new result.
     * @param id the result id.
     * @param result the <code>Result</code> instance.
     */
    protected void addResult(
        @NotNull final String id, @NotNull final Result result, @NotNull final List<Result> results)
    {
        if (results.contains(result))
        {
            results.add(result);
        }
        else
        {
            UniqueLogFactory.getLog(SqlXmlParserImpl.class).warn(
                id + " is defined more than once");
        }
    }

    /**
     * Adds a new property.
     * @param id the property id.
     * @param columnName the column name.
     * @param index the property index.
     * @param type the property type.
     * @param nullable whether it allows null or not.
     */
    @Override
    public void addProperty(
        @NotNull final String id,
        @NotNull final String columnName,
        final int index,
        @NotNull final String type,
        final boolean nullable)
    {
        getProperties().add(new PropertyElement(id, columnName, index, type, nullable));
    }

    /**
     * Retrieves the {@link SqlConnectionFlagsDAO} instance.
     * @return such instance.
     */
    @NotNull
    @Override
    public SqlConnectionFlagsDAO getSqlConnectionFlagsDAO()
    {
        return new SqlXmlParserConnectionFlagsDAO(this);
    }

    /**
     * Retrieves the {@link SqlStatementFlagsDAO} instance.
     * @return such instance.
     */
    @NotNull
    @Override
    public SqlStatementFlagsDAO getSqlStatementFlagsDAO()
    {
        return new SqlXmlParserStatementFlagsDAO(this);
    }

    /**
     * Retrieves the {@link SqlResultSetFlagsDAO} instance.
     * @return such instance.
     */
    @NotNull
    @Override
    public SqlResultSetFlagsDAO getSqlResultSetFlagsDAO()
    {
        return new SqlXmlParserResultSetFlagsDAO(this);
    }
}

