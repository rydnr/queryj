/*
                        QueryJ Core

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
 * Filename: SqlElement.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Models <sql> elements in custom-sql models.
 *
 */
package org.acmsl.queryj.customsql;

/*
 * Importing Jetbrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing JDK classes.
 */
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * Models &lt;sql&gt; elements in <i>custom-sql</i> models, which
 * satisfy the following DTD extract (to describe the model even in
 * non-xml implementations):
 *  &lt;!ELEMENT sql (parameter-ref)+&gt;
 *  &lt;!ATTLIST sql
 *    id ID #REQUIRED
 *    class CDATA #IMPLIED
 *    matches (single | multiple) #REQUIRED&gt;
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 * @param <T> the type.
 */
@ThreadSafe
public class SqlElement<T>
    extends  AbstractIdElement<T>
    implements Sql<T>
{
    /**
     * The serial version id.
     */
    private static final long serialVersionUID = -7884918833943291505L;

    /**
     * The <i>dao</i> attribute.
     */
    private T m__strDAO;

    /**
     * The <i>repositoryScope</i> attribute.
     */
    private T m__strRepositoryScope;

    /**
     * The <i>name</i> attribute.
     */
    private T m__strName;

    /**
     * The <i>type</i> attribute.
     */
    private T m__strType;

    /**
     * The cardinality.
     */
    private SqlCardinality cardinality;

    /**
     * The <i>implementation</i> attribute.
     */
    private T m__strImplementation;

    /**
     * The <i>validate</i> attribute.
     */
    private boolean m__bValidate = true;

    /**
     * The <i>dynamic</i> attribute.
     */
    private boolean m__bDynamic = false;

    /**
     * The <i>description</i> attribute.
     */
    private T m__strDescription;

    /**
     * The <i>value</i> element.
     */
    private T m__strValue;

    /**
     * The <i>parameter-ref</i> elements.
     */
    private List<ParameterRef> m__lParameterRefs = new ArrayList<>();

    /**
     * The <i>result-ref</i> element.
     */
    private ResultRef m__ResultRef;

    /**
     * The <i>connection-flags-ref</i> element.
     */
    private ConnectionFlagsRef m__ConnectionFlagsRef;

    /**
     * The <i>statement-flags-ref</i> element.
     */
    private StatementFlagsRef m__StatementFlagsRef;

    /**
     * The <i>resultset-flags-ref</i> element.
     */
    private ResultSetFlagsRef m__ResultSetFlagsRef;

    /**
     * Creates a <code>SqlElement</code> with given information.
     * @param id the <i>id</i> attribute.
     * @param dao the <i>dao</i> attribute.
     * @param name the <i>name</i> attribute.
     * @param type the <i>type</i> attribute.
     * @param cardinality the cardinality of the query.
     * @param implementation the <i>implementation</i> attribute.
     * @param validate the <i>validate</i> attribute.
     * @param dynamic the <i>dynamic</i> attribute.
     * @param description the <i>description</i>.
     */
    @SuppressWarnings("unused")
    public SqlElement(
        @NotNull final T id,
        @Nullable final T dao,
        @NotNull final T name,
        @NotNull final T type,
        @NotNull final SqlCardinality cardinality,
        @Nullable final T implementation,
        final boolean validate,
        final boolean dynamic,
        @NotNull final T description)
    {
        this(id, dao, null, name, type, cardinality, implementation, validate, dynamic, description);
    }

    /**
     * Creates a <code>SqlElement</code> with given information.
     * @param id the <i>id</i> attribute.
     * @param name the <i>name</i> attribute.
     * @param type the <i>type</i> attribute.
     * @param cardinality the cardinality of the query.
     * @param implementation the <i>implementation</i> attribute.
     * @param validate the <i>validate</i> attribute.
     * @param dynamic the <i>dynamic</i> attribute.
     * @param repositoryScope the <i>repositoryScope</i> attribute.
     * @param description the <i>description</i>.
     */
    @SuppressWarnings("unused")
    public SqlElement(
        @NotNull final T id,
        @NotNull final T name,
        @NotNull final T type,
        @NotNull final SqlCardinality cardinality,
        @Nullable final T implementation,
        final boolean validate,
        final boolean dynamic,
        @NotNull final T repositoryScope,
        @NotNull final T description)
    {
        this(id, null, repositoryScope, name, type, cardinality, implementation, validate, dynamic, description);
    }

    /**
     * Creates a <code>SqlElement</code> with given information.
     * @param id the <i>id</i> attribute.
     * @param dao the <i>dao</i> attribute.
     * @param repositoryScope the <i>repositoryScope</i> attribute.
     * @param name the <i>name</i> attribute.
     * @param type the <i>type</i> attribute.
     * @param cardinality the cardinality of the query.
     * @param implementation the <i>implementation</i> attribute.
     * @param validate the <i>validate</i> attribute.
     * @param dynamic the <i>dynamic</i> attribute.
     * @param description the <i>description</i>.
     */
    public SqlElement(
        @NotNull final T id,
        @Nullable final T dao,
        @Nullable final T repositoryScope,
        @NotNull final T name,
        @NotNull final T type,
        @NotNull final SqlCardinality cardinality,
        @Nullable final T implementation,
        final boolean validate,
        final boolean dynamic,
        @NotNull final T description)
    {
        super(id);
        if (dao != null)
        {
            immutableSetDAO(dao);
        }
        if (repositoryScope != null)
        {
            immutableSetRepositoryScope(repositoryScope);
        }
        immutableSetName(name);
        immutableSetType(type);
        immutableSetCardinality(cardinality);
        if (implementation != null)
        {
            immutableSetImplementation(implementation);
        }
        immutableSetValidate(validate);
        immutableSetDynamic(dynamic);
        immutableSetDescription(description);
    }

    /**
     * Specifies the <i>dao</i> attribute.
     * @param dao such value.
     */
    protected final void immutableSetDAO(@NotNull final T dao)
    {
        m__strDAO = dao;
    }

    /**
     * Specifies the <i>dao</i> attribute.
     * @param dao such value.
     */
    @SuppressWarnings("unused")
    protected void setDao(@NotNull final T dao)
    {
        immutableSetDAO(dao);
    }

    /**
     * Retrieves the <i>dao</i> attribute.
     * @return such value.
     */
    @Override
    @Nullable
    public T getDao()
    {
        return m__strDAO;
    }

    /**
     * Specifies the <i>repositoryScope</i> attribute.
     * @param repositoryScope such attribute.
     */
    protected final void immutableSetRepositoryScope(
        @NotNull final T repositoryScope)
    {
        m__strRepositoryScope = repositoryScope;
    }
    
    /**
     * Specifies the <i>repositoryScope</i> attribute.
     * @param repositoryScope such attribute.
     */
    @SuppressWarnings("unused")
    protected void setRepositoryScope(@NotNull final T repositoryScope)
    {
        immutableSetRepositoryScope(repositoryScope);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    @Nullable
    public T getRepositoryScope()
    {
        return m__strRepositoryScope;
    }
    
    /**
     * Specifies the <i>name</i> attribute.
     * @param name such value.
     */
    protected final void immutableSetName(@NotNull final T name)
    {
        m__strName = name;
    }

    /**
     * Specifies the <i>name</i> attribute.
     * @param name such value.
     */
    @SuppressWarnings("unused")
    protected void setName(@NotNull final T name)
    {
        immutableSetName(name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @NotNull
    public T getName()
    {
        return m__strName;
    }

    /**
     * Specifies the <i>type</i> attribute.
     * @param type such value.
     */
    protected final void immutableSetType(@NotNull final T type)
    {
        m__strType = type;
    }

    /**
     * Specifies the <i>type</i> attribute.
     * @param type such value.
     */
    @SuppressWarnings("unused")
    protected void setType(@NotNull final T type)
    {
        immutableSetType(type);
    }

    /**
     * Retrieves the <i>type</i> attribute.
     * @return such value.
     */
    @Override
    @NotNull
    public T getType()
    {
        return m__strType;
    }

    /**
     * Specifies the cardinality.
     * @param value such information.
     */
    protected final void immutableSetCardinality(@NotNull final SqlCardinality value)
    {
        this.cardinality = value;
    }

    /**
     * Specifies the cardinality.
     * @param cardinality such information.
     */
    @SuppressWarnings("unused")
    protected void setCardinality(@NotNull final SqlCardinality cardinality)
    {
        immutableSetCardinality(cardinality);
    }

    /**
     * Retrieves the cardinality.
     * @return such information.
     */
    @Override
    @NotNull
    public SqlCardinality getCardinality()
    {
        return this.cardinality;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isMultiple()
    {
        return getCardinality().equals(SqlCardinality.MULTIPLE);
    }

    /**
     * Specifies the <i>implementation</i> attribute.
     * @param implementation such value.
     */
    protected final void immutableSetImplementation(@NotNull final T implementation)
    {
        m__strImplementation = implementation;
    }

    /**
     * Specifies the <i>implementation</i> attribute.
     * @param implementation such value.
     */
    @SuppressWarnings("unused")
    protected void setImplementation(@NotNull final T implementation)
    {
        immutableSetImplementation(implementation);
    }

    /**
     * Retrieves the <i>implementation</i> attribute.
     * @return such value.
     */
    @Override
    @Nullable
    public T getImplementation()
    {
        return m__strImplementation;
    }

    /**
     * Specifies the <i>validate</i> attribute.
     * @param validate such flag.
     */
    protected final void immutableSetValidate(final boolean validate)
    {
        m__bValidate = validate;
    }

    /**
     * Specifies the <i>validate</i> attribute.
     * @param validate such flag.
     */
    @SuppressWarnings("unused")
    protected void setValidate(final boolean validate)
    {
        immutableSetValidate(validate);
    }

    /**
     * Specifies the <i>validate</i> attribute.
     * @param validate such flag.
     */
    @SuppressWarnings("unused")
    protected void isValidate(final boolean validate)
    {
        immutableSetValidate(validate);
    }

    /**
     * Retrieves the <i>validate</i> attribute.
     * @return such information.
     */
    @Override
    public boolean getValidate()
    {
        return m__bValidate;
    }

    /**
     * Retrieves the <i>validate</i> attribute.
     * @return such information.
     */
    @Override
    public boolean isValidate()
    {
        return getValidate();
    }

    /**
     * Specifies whether the query is dynamic or not.
     * @param dynamic whether the query is dynamic.
     */
    protected final void immutableSetDynamic(final boolean dynamic)
    {
        m__bDynamic = dynamic;
    }

    /**
     * Specifies whether the query is dynamic or not.
     * @param dynamic whether the query is dynamic.
     */
    @SuppressWarnings("unused")
    protected void setDynamic(final boolean dynamic)
    {
        immutableSetDynamic(dynamic);
    }

    /**
     * Retrieves whether the query is dynamic or not.
     * @return such information.
     */
    @Override
    public boolean isDynamic()
    {
        return m__bDynamic;
    }

    /**
     * Specifies the <i>description</i> attribute.
     * @param description such value.
     */
    protected final void immutableSetDescription(@NotNull final T description)
    {
        m__strDescription = description;
    }

    /**
     * Specifies the <i>description</i> attribute.
     * @param description such value.
     */
    @SuppressWarnings("unused")
    public void setDescription(@NotNull final T description)
    {
        immutableSetDescription(description);
    }

    /**
     * Retrieves the <i>description</i> attribute.
     * @return such value.
     */
    @Override
    @NotNull
    public T getDescription()
    {
        return m__strDescription;
    }

    /**
     * Specifies the &lt;value&gt; element.
     * @param value such value.
     */
    protected final void immutableSetValue(@NotNull final T value)
    {
        m__strValue = value;
    }

    /**
     * Specifies the &lt;value&gt; element.
     * @param value such value.
     */
    @SuppressWarnings("unused")
    public void setValue(@NotNull final T value)
    {
        immutableSetValue(value);
    }

    /**
     * Retrieves the &lt;value&gt; element.
     * @return such value.
     */
    @Override
    @Nullable
    public T getValue()
    {
        return m__strValue;
    }

    /**
     * Specifies the &lt;parameter-ref&gt; elements.
     * @param list such elements.
     */
    protected final void immutableSetParameterRefs(
        @NotNull final List<ParameterRef> list)
    {
        m__lParameterRefs = list;
    }

    /**
     * Specifies the &lt;parameter-ref&gt; elements.
     * @param list such elements.
     */
    @SuppressWarnings("unused")
    protected void setParameterRefs(@NotNull final List<ParameterRef> list)
    {
        immutableSetParameterRefs(list);
    }

    /**
     * Retrieves the &lt;parameter-ref&gt; elements.
     * @return such elements.
     */
    @Override
    @NotNull
    public List<ParameterRef> getParameterRefs()
    {
        return m__lParameterRefs;
    }

    /**
     * Adds a new &lt;parameter-ref&gt; element.
     * @param parameterRef such element.
     */
    @Override
    public void add(@NotNull final ParameterRef parameterRef)
    {
        add(parameterRef, getParameterRefs());
    }

    /**
     * Adds a new &lt;parameter-ref&gt; element.
     * @param parameterRef such element.
     * @param parameterRefs the &lt;parameter-ref&gt; elements.
     */
    protected synchronized void add(
        @NotNull final ParameterRef parameterRef, @NotNull final List<ParameterRef> parameterRefs)
    {
        parameterRefs.add(parameterRef);
    }

    /**
     * Specifies the &lt;result-ref&gt; element.
     * @param resultRef such element.
     */
    protected final void immutableSetResultRef(@NotNull final ResultRef resultRef)
    {
        m__ResultRef = resultRef;
    }

    /**
     * Specifies the &lt;result-ref&gt; element.
     * @param resultRef such element.
     */
    @SuppressWarnings("unused")
    public void setResultRef(@NotNull final ResultRef resultRef)
    {
        immutableSetResultRef(resultRef);
    }

    /**
     * Retrieves the &lt;result-ref&gt; element.
     * @return such element.
     */
    @Override
    @Nullable
    public ResultRef getResultRef()
    {
        return m__ResultRef;
    }

    /**
     * Specifies the &lt;connection-flags-ref&gt; element.
     * @param connectionflagsRef such element.
     */
    protected final void immutableSetConnectionFlagsRef(
        @NotNull final ConnectionFlagsRef connectionflagsRef)
    {
        m__ConnectionFlagsRef = connectionflagsRef;
    }

    /**
     * Specifies the &lt;connection-flags-ref&gt; element.
     * @param connectionflagsRef such element.
     */
    @SuppressWarnings("unused")
    public void setConnectionFlagsRef(@NotNull final ConnectionFlagsRef connectionflagsRef)
    {
        immutableSetConnectionFlagsRef(connectionflagsRef);
    }

    /**
     * Retrieves the &lt;connection-flags-ref&gt; element.
     * @return such element.
     */
    @Nullable
    public ConnectionFlagsRef getConnectionFlagsRef()
    {
        return m__ConnectionFlagsRef;
    }

    /**
     * Specifies the &lt;statement-flags-ref&gt; element.
     * @param statementflagsRef such element.
     */
    protected final void immutableSetStatementFlagsRef(
        @NotNull final StatementFlagsRef statementflagsRef)
    {
        m__StatementFlagsRef = statementflagsRef;
    }

    /**
     * Specifies the &lt;statement-flags-ref&gt; element.
     * @param statementflagsRef such element.
     */
    @SuppressWarnings("unused")
    public void setStatementFlagsRef(@NotNull final StatementFlagsRef statementflagsRef)
    {
        immutableSetStatementFlagsRef(statementflagsRef);
    }

    /**
     * Retrieves the &lt;statement-flags-ref&gt; element.
     * @return such element.
     */
    @Nullable
    public StatementFlagsRef getStatementFlagsRef()
    {
        return m__StatementFlagsRef;
    }

    /**
     * Specifies the &lt;resultset-flags-ref&gt; element.
     * @param resultsetflagsRef such element.
     */
    protected final void immutableSetResultSetFlagsRef(
        @NotNull final ResultSetFlagsRef resultsetflagsRef)
    {
        m__ResultSetFlagsRef = resultsetflagsRef;
    }

    /**
     * Specifies the &lt;resultset-flags-ref&gt; element.
     * @param resultsetflagsRef such element.
     */
    @SuppressWarnings("unused")
    public void setResultSetFlagsRef(@NotNull final ResultSetFlagsRef resultsetflagsRef)
    {
        immutableSetResultSetFlagsRef(resultsetflagsRef);
    }

    /**
     * Retrieves the &lt;resultset-flags-ref&gt; element.
     * @return such element.
     */
    @Override
    @Nullable
    public ResultSetFlagsRef getResultSetFlagsRef()
    {
        return m__ResultSetFlagsRef;
    }

    /**
     * Provides a text information about this instance.
     * @return such information.
     */
    @Override
    @NotNull
    public String toString()
    {
        return
            toString(
                getId(),
                getDescription(),
                getDao(),
                getRepositoryScope(),
                getName(),
                getType(),
                getCardinality(),
                getImplementation(),
                getValue(),
                isValidate(),
                isDynamic(),
                getParameterRefs(),
                getResultRef(),
                getConnectionFlagsRef(),
                getStatementFlagsRef(),
                getResultSetFlagsRef());
    }

    /**
     * Provides a text information about this instance.
     * @param id the <i>id</i> attribute.
     * @param description the <i>description</i> attribute.
     * @param dao the <i>dao</i> attribute.
     * @param repositoryScope the <i>repositoryScope</i> attribute.
     * @param name the <i>name</i> attribute.
     * @param type the <i>type</i> attribute.
     * @param cardinality the <i>cardinality</i> attribute.
     * @param implementation the <i>implementation</i> attribute.
     * @param value the <i>value</i> element.
     * @param validate the <i>validate</i> attribute.
     * @param dynamic the <i>dynamic</i> attribute.
     * @param parameterRefs the <i>parameter-ref</i> elements.
     * @param resultRef the <i>result-ref</i> element.
     * @param connectionFlagsRef the <i>connection-flags-ref</i> element.
     * @param statementFlagsRef the <i>statement-flags-ref</i> element.
     * @param resultSetFlagsRef the <i>resultset-flags-ref</i> element.
     * @return such information.
     */
    @NotNull
    protected String toString(
        final T id,
        final T description,
        final T dao,
        final T repositoryScope,
        final T name,
        final T type,
        @NotNull final SqlCardinality cardinality,
        final T implementation,
        final T value,
        final boolean validate,
        final boolean dynamic,
        final Collection<ParameterRef> parameterRefs,
        final ResultRef resultRef,
        final ConnectionFlagsRef connectionFlagsRef,
        final StatementFlagsRef statementFlagsRef,
        final ResultSetFlagsRef resultSetFlagsRef)
    {
        return
              getClass().getName()
            + "[" + "id=" + id + "]"
            + "[" + "description=" + description + "]"
            + "[" + "dao=" + dao + "]"
            + "[" + "repositoryScope=" + repositoryScope + "]"
            + "[" + "name=" + name + "]"
            + "[" + "cardinality=" + cardinality + "]"
            + "[" + "type=" + type + "]"
            + "[" + "value=" + value + "]"
            + "[" + "validate=" + validate + "]"
            + "[" + "dynamic=" + dynamic + "]"
            + "[" + "implementation=" + implementation + "]"
            + "[" + "parameter-refs=" + parameterRefs + "]"
            + "[" + "result-ref=" + resultRef + "]"
            + "[" + "connection-flags-ref=" + connectionFlagsRef + "]"
            + "[" + "statement-flags-ref=" + statementFlagsRef + "]"
            + "[" + "resultset-flags-ref=" + resultSetFlagsRef + "]";
    }

    /**
     * Compares this object with the specified object for order.  Returns a
     * negative integer, zero, or a positive integer as this object is less
     * than, equal to, or greater than the specified object.
     * <p>The implementor must ensure <tt>sgn(x.compareTo(y)) ==
     * -sgn(y.compareTo(x))</tt> for all <tt>x</tt> and <tt>y</tt>.  (This
     * implies that <tt>x.compareTo(y)</tt> must throw an exception iff
     * <tt>y.compareTo(x)</tt> throws an exception.)
     * <p>The implementor must also ensure that the relation is transitive:
     * <tt>(x.compareTo(y)&gt;0 &amp;&amp; y.compareTo(z)&gt;0)</tt> implies
     * <tt>x.compareTo(z)&gt;0</tt>.
     * <p>Finally, the implementor must ensure that <tt>x.compareTo(y)==0</tt>
     * implies that <tt>sgn(x.compareTo(z)) == sgn(y.compareTo(z))</tt>, for
     * all <tt>z</tt>.
     * <p>It is strongly recommended, but <i>not</i> strictly required that
     * <tt>(x.compareTo(y)==0) == (x.equals(y))</tt>.  Generally speaking, any
     * class that implements the <tt>Comparable</tt> interface and violates
     * this condition should clearly indicate this fact.  The recommended
     * language is "Note: this class has a natural ordering that is
     * inconsistent with equals."
     * <p>In the foregoing description, the notation
     * <tt>sgn(</tt><i>expression</i><tt>)</tt> designates the mathematical
     * <i>signum</i> function, which is defined to return one of <tt>-1</tt>,
     * <tt>0</tt>, or <tt>1</tt> according to whether the value of
     * <i>expression</i> is negative, zero or positive.
     *
     * @param o the object to be compared.
     * @return a negative integer, zero, or a positive integer as this object
     *         is less than, equal to, or greater than the specified object.
     *                            from being compared to this object.
     */
    @Override
    public int compareTo(final Sql<T> o)
    {
        return compareThem(this, o);
    }

    /**
     * Compares given {@link Sql} instances.
     * @param first the first.
     * @param second the second.
     * @return a positive number if the first is considered 'greater' than
     * the second; 0 if they're equal; a negative number otherwise.
     */
    protected int compareThem(@NotNull final Sql<T> first, @NotNull final Sql<T> second)
    {
        return ("" + first.getId()).compareTo(("" + second.getId()));
    }
}
