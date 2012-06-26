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
 * Filename: SqlElement.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Models <sql> elements in custom-sql models.
 *
 */
package org.acmsl.queryj.customsql;

/*
 * Importing project-specific classes.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing JDK classes.
 */
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Models &lt;sql&gt; elements in <i>custom-sql</i> models, which
 * satisfy the following DTD extract (to describe the model even in
 * non-xml implementations):
 *  <!ELEMENT sql (parameter-ref)+>
 *  <!ATTLIST sql
 *    id ID #REQUIRED
 *    class CDATA #IMPLIED
 *    matches (single | multiple) #REQUIRED>
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public class SqlElement
    extends  AbstractIdElement
    implements Sql
{
    private static final long serialVersionUID = -7884918833943291505L;
    /**
     * The <i>dao</i> attribute.
     */
    private String m__strDAO;

    /**
     * The <i>repositoryScope</i> attribute.
     */
    private String m__strRepositoryScope;

    /**
     * The <i>name</i> attribute.
     */
    private String m__strName;

    /**
     * The <i>type</i> attribute.
     */
    private String m__strType;

    /**
     * The <i>implementation</i> attribute.
     */
    private String m__strImplementation;

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
    private String m__strDescription;

    /**
     * The <i>value</i> element.
     */
    private String m__strValue;

    /**
     * The <i>parameter-ref</i> elements.
     */
    private List<ParameterRefElement> m__lParameterRefs;

    /**
     * The <i>result-ref</i> element.
     */
    private ResultRef m__ResultRef;

    /**
     * The <i>connection-flags-ref</i> element.
     */
    private ConnectionFlagsRefElement m__ConnectionFlagsRef;

    /**
     * The <i>statement-flags-ref</i> element.
     */
    private StatementFlagsRefElement m__StatementFlagsRef;

    /**
     * The <i>resultset-flags-ref</i> element.
     */
    private ResultSetFlagsRefElement m__ResultSetFlagsRef;

    /**
     * Creates a <code>SqlElement</code> with given information.
     * @param id the <i>id</i> attribute.
     * @param dao the <i>dao</i> attribute.
     * @param name the <i>name</i> attribute.
     * @param type the <i>type</i> attribute.
     * @param implementation the <i>implementation</i> attribute.
     * @param validate the <i>validate</i> attribute.
     * @param dynamic the <i>dynamic</i> attribute.
     */
    @SuppressWarnings("unused")
    public SqlElement(
        @NotNull final String id,
        @NotNull final String dao,
        @NotNull final String name,
        @NotNull final String type,
        @NotNull final String implementation,
        final boolean validate,
        final boolean dynamic)
    {
        this(id, dao, null, name, type, implementation, validate, dynamic);
    }

    /**
     * Creates a <code>SqlElement</code> with given information.
     * @param id the <i>id</i> attribute.
     * @param name the <i>name</i> attribute.
     * @param type the <i>type</i> attribute.
     * @param implementation the <i>implementation</i> attribute.
     * @param validate the <i>validate</i> attribute.
     * @param dynamic the <i>dynamic</i> attribute.
     * @param repositoryScope the <i>repositoryScope</i> attribute.
     */
    @SuppressWarnings("unused")
    public SqlElement(
        @NotNull final String id,
        @NotNull final String name,
        @NotNull final String type,
        @NotNull final String implementation,
        final boolean validate,
        final boolean dynamic,
        @NotNull final String repositoryScope)
    {
        this(
            id, null, repositoryScope, name, type, implementation, validate, dynamic);
    }

    /**
     * Creates a <code>SqlElement</code> with given information.
     * @param id the <i>id</i> attribute.
     * @param dao the <i>dao</i> attribute.
     * @param repositoryScope the <i>repositoryScope</i> attribute.
     * @param name the <i>name</i> attribute.
     * @param type the <i>type</i> attribute.
     * @param implementation the <i>implementation</i> attribute.
     * @param validate the <i>validate</i> attribute.
     * @param dynamic the <i>dynamic</i> attribute.
     */
    protected SqlElement(
        @NotNull final String id,
        @Nullable final String dao,
        @Nullable final String repositoryScope,
        @NotNull final String name,
        @NotNull final String type,
        @NotNull final String implementation,
        final boolean validate,
        final boolean dynamic)
    {
        super(id);
        immutableSetDAO(dao);
        immutableSetRepositoryScope(repositoryScope);
        immutableSetName(name);
        immutableSetType(type);
        immutableSetImplementation(implementation);
        immutableSetValidate(validate);
        immutableSetDynamic(dynamic);
    }

    /**
     * Retrieves the <i>description</i> attribute.
     * @return such value.
     */
    @NotNull
    public String getDescription()
    {
        return m__strDescription;
    }

    /**
     * Specifies the <i>dao</i> attribute.
     * @param dao such value.
     */
    protected final void immutableSetDAO(final String dao)
    {
        m__strDAO = dao;
    }

    /**
     * Specifies the <i>dao</i> attribute.
     * @param dao such value.
     */
    @SuppressWarnings("unused")
    protected void setDao(final String dao)
    {
        immutableSetDAO(dao);
    }

    /**
     * Retrieves the <i>dao</i> attribute.
     * @return such value.
     */
    @NotNull
    public String getDao()
    {
        return m__strDAO;
    }

    /**
     * Specifies the <i>repositoryScope</i> attribute.
     * @param repositoryScope such attribute.
     */
    protected final void immutableSetRepositoryScope(
        final String repositoryScope)
    {
        m__strRepositoryScope = repositoryScope;
    }
    
    /**
     * Specifies the <i>repositoryScope</i> attribute.
     * @param repositoryScope such attribute.
     */
    @SuppressWarnings("unused")
    protected void setRepositoryScope(final String repositoryScope)
    {
        immutableSetRepositoryScope(repositoryScope);
    }
    
    /**
     * Retrieves the <i>repositoryScope</i> attribute.
     * @return such information.
     */
    @NotNull
    public String getRepositoryScope()
    {
        return m__strRepositoryScope;
    }
    
    /**
     * Specifies the <i>name</i> attribute.
     * @param name such value.
     */
    protected final void immutableSetName(final String name)
    {
        m__strName = name;
    }

    /**
     * Specifies the <i>name</i> attribute.
     * @param name such value.
     */
    @SuppressWarnings("unused")
    protected void setName(final String name)
    {
        immutableSetName(name);
    }

    /**
     * Retrieves the <i>name</i> attribute.
     * @return such value.
     */
    @NotNull
    public String getName()
    {
        return m__strName;
    }

    /**
     * Specifies the <i>type</i> attribute.
     * @param type such value.
     */
    protected final void immutableSetType(final String type)
    {
        m__strType = type;
    }

    /**
     * Specifies the <i>type</i> attribute.
     * @param type such value.
     */
    @SuppressWarnings("unused")
    protected void setType(final String type)
    {
        immutableSetType(type);
    }

    /**
     * Retrieves the <i>type</i> attribute.
     * @return such value.
     */
    @NotNull
    public String getType()
    {
        return m__strType;
    }

    /**
     * Specifies the <i>implementation</i> attribute.
     * @param implementation such value.
     */
    protected final void immutableSetImplementation(final String implementation)
    {
        m__strImplementation = implementation;
    }

    /**
     * Specifies the <i>implementation</i> attribute.
     * @param implementation such value.
     */
    @SuppressWarnings("unused")
    protected void setImplementation(final String implementation)
    {
        immutableSetImplementation(implementation);
    }

    /**
     * Retrieves the <i>implementation</i> attribute.
     * @return such value.
     */
    @NotNull
    public String getImplementation()
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
    public boolean getValidate()
    {
        return m__bValidate;
    }

    /**
     * Retrieves the <i>validate</i> attribute.
     * @return such information.
     */
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
    public boolean isDynamic()
    {
        return m__bDynamic;
    }

    /**
     * Specifies the <i>description</i> attribute.
     * @param description such value.
     */
    protected final void immutableSetDescription(final String description)
    {
        m__strDescription = description;
    }

    /**
     * Specifies the <i>description</i> attribute.
     * @param description such value.
     */
    @SuppressWarnings("unused")
    public void setDescription(final String description)
    {
        immutableSetDescription(description);
    }

    /**
     * Specifies the &lt;value&gt; element.
     * @param value such value.
     */
    protected final void immutableSetValue(final String value)
    {
        m__strValue = value;
    }

    /**
     * Specifies the &lt;value&gt; element.
     * @param value such value.
     */
    @SuppressWarnings("unused")
    public void setValue(final String value)
    {
        immutableSetValue(value);
    }

    /**
     * Retrieves the &lt;value&gt; element.
     * @return such value.
     */
    @NotNull
    public String getValue()
    {
        return m__strValue;
    }

    /**
     * Specifies the &lt;parameter-ref&gt; elements.
     * @param list such elements.
     */
    protected final void immutableSetParameterRefs(
        final List<ParameterRefElement> list)
    {
        m__lParameterRefs = list;
    }

    /**
     * Specifies the &lt;parameter-ref&gt; elements.
     * @param list such elements.
     */
    protected void setParameterRefs(@NotNull final List<ParameterRefElement> list)
    {
        immutableSetParameterRefs(list);
    }

    /**
     * Retrieves the &lt;parameter-ref&gt; elements.
     * @return such elements.
     */
    @NotNull
    public List<ParameterRefElement> getParameterRefs()
    {
        return m__lParameterRefs;
    }

    /**
     * Adds a new &lt;parameter-ref&gt; element.
     * @param parameterRef such element.
     */
    public void add(@NotNull final ParameterRefElement parameterRef)
    {
        add(parameterRef, getParameterRefs());
    }

    /**
     * Adds a new &lt;parameter-ref&gt; element.
     * @param parameterRef such element.
     * @param parameterRefs thhe &ltparameter-ref&gt; elements.
     */
    protected synchronized void add(
        @NotNull final ParameterRefElement parameterRef, @Nullable final List<ParameterRefElement> parameterRefs)
    {
        List<ParameterRefElement> t_lParameterRefs = parameterRefs;

        if  (t_lParameterRefs == null)
        {
            t_lParameterRefs = new ArrayList<ParameterRefElement>();
            setParameterRefs(t_lParameterRefs);
        }

        t_lParameterRefs.add(parameterRef);
    }

    /**
     * Specifies the &lt;result-ref&gt; element.
     * @param resultRef such element.
     */
    protected final void immutableSetResultRef(
        @NotNull final ResultRef resultRef)
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
    @NotNull
    public ResultRef getResultRef()
    {
        return m__ResultRef;
    }

    /**
     * Specifies the &lt;connection-flags-ref&gt; element.
     * @param connectionflagsRef such element.
     */
    protected final void immutableSetConnectionFlagsRef(
        final ConnectionFlagsRefElement connectionflagsRef)
    {
        m__ConnectionFlagsRef = connectionflagsRef;
    }

    /**
     * Specifies the &lt;connection-flags-ref&gt; element.
     * @param connectionflagsRef such element.
     */
    @SuppressWarnings("unused")
    public void setConnectionFlagsRef(final ConnectionFlagsRefElement connectionflagsRef)
    {
        immutableSetConnectionFlagsRef(connectionflagsRef);
    }

    /**
     * Retrieves the &lt;connection-flags-ref&gt; element.
     * @return such element.
     */
    @NotNull
    public ConnectionFlagsRefElement getConnectionFlagsRef()
    {
        return m__ConnectionFlagsRef;
    }

    /**
     * Specifies the &lt;statement-flags-ref&gt; element.
     * @param statementflagsRef such element.
     */
    protected final void immutableSetStatementFlagsRef(
        final StatementFlagsRefElement statementflagsRef)
    {
        m__StatementFlagsRef = statementflagsRef;
    }

    /**
     * Specifies the &lt;statement-flags-ref&gt; element.
     * @param statementflagsRef such element.
     */
    @SuppressWarnings("unused")
    public void setStatementFlagsRef(final StatementFlagsRefElement statementflagsRef)
    {
        immutableSetStatementFlagsRef(statementflagsRef);
    }

    /**
     * Retrieves the &lt;statement-flags-ref&gt; element.
     * @return such element.
     */
    @NotNull
    public StatementFlagsRefElement getStatementFlagsRef()
    {
        return m__StatementFlagsRef;
    }

    /**
     * Specifies the &lt;resultset-flags-ref&gt; element.
     * @param resultsetflagsRef such element.
     */
    protected final void immutableSetResultSetFlagsRef(
        final ResultSetFlagsRefElement resultsetflagsRef)
    {
        m__ResultSetFlagsRef = resultsetflagsRef;
    }

    /**
     * Specifies the &lt;resultset-flags-ref&gt; element.
     * @param resultsetflagsRef such element.
     */
    @SuppressWarnings("unused")
    public void setResultSetFlagsRef(final ResultSetFlagsRefElement resultsetflagsRef)
    {
        immutableSetResultSetFlagsRef(resultsetflagsRef);
    }

    /**
     * Retrieves the &lt;resultset-flags-ref&gt; element.
     * @return such element.
     */
    public ResultSetFlagsRefElement getResultSetFlagsRef()
    {
        return m__ResultSetFlagsRef;
    }

    /**
     * Provides a text information about this instance.
     * @return such information.
     */
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
                getImplementation(),
                getValue(),
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
     * @param implementation the <i>implementation</i> attribute.
     * @param value the <i>value</i> element.
     * @param parameterRefs the <i>parameter-ref</i> elements.
     * @param resultRef the <i>result-ref</i> element.
     * @param connectionFlagsRef the <i>connection-flags-ref</i> element.
     * @param statementFlagsRef the <i>statement-flags-ref</i> element.
     * @param resultSetFlagsRef the <i>resultset-flags-ref</i> element.
     * @return such information.
     */
    @NotNull
    protected String toString(
        final String id,
        final String description,
        final String dao,
        final String repositoryScope,
        final String name,
        final String type,
        final String implementation,
        final String value,
        final Collection parameterRefs,
        final ResultRef resultRef,
        final ConnectionFlagsRefElement connectionFlagsRef,
        final StatementFlagsRefElement statementFlagsRef,
        final ResultSetFlagsRefElement resultSetFlagsRef)
    {
        return
              getClass().getName()
            + "[" + "id=" + id + "]"
            + "[" + "description=" + description + "]"
            + "[" + "dao=" + dao + "]"
            + "[" + "repositoryScope=" + repositoryScope + "]"
            + "[" + "name=" + name + "]"
            + "[" + "type=" + type + "]"
            + "[" + "value=" + value + "]"
            + "[" + "implementation=" + implementation + "]"
            + "[" + "parameter-refs=" + parameterRefs + "]"
            + "[" + "result-ref=" + resultRef + "]"
            + "[" + "connection-flags-ref=" + connectionFlagsRef + "]"
            + "[" + "statement-flags-ref=" + statementFlagsRef + "]"
            + "[" + "resultset-flags-ref=" + resultSetFlagsRef + "]";
    }
}
