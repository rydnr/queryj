//;-*- mode: java -*-
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
 * Filename: SqlElement.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Models <sql> elements in custom-sql models.
 *
 */
package org.acmsl.queryj.tools.customsql;

/*
 * Importing project-specific classes.
 */
import org.acmsl.queryj.tools.customsql.AbstractIdElement;
import org.acmsl.queryj.tools.customsql.ParameterRefElement;
import org.acmsl.queryj.tools.customsql.ResultRefElement;
import org.acmsl.queryj.tools.customsql.Sql;

/*
 * Importing JDK classes.
 */
import java.util.ArrayList;
import java.util.Collection;

/**
 * Models &lt;sql&gt; elements in <i>custom-sql</i> models, which
 * satisfy the following DTD extract (to describe the model even in
 * non-xml implementations):
 *  <!ELEMENT sql (parameter-ref)+>
 *  <!ATTLIST sql
 *    id ID #REQUIRED
 *    class CDATA #IMPLIED
 *    matches (single | multiple) #REQUIRED>
 * @author <a href="mailto:chous@acm-sl.org"
 *         >Jose San Leandro</a>
 */
public class SqlElement
    extends  AbstractIdElement
    implements Sql
{
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
     * The <i>description</i> attribute.
     */
    private String m__strDescription;

    /**
     * The <i>value</i> element.
     */
    private String m__strValue;

    /**
     * The <i>dynamic</i> attribute.
     */
    private boolean m__bDynamic = false;
    
    /**
     * The <i>parameter-ref</i> elements.
     */
    private Collection m__cParameterRefs;

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
     * @param implementation the <i>implementation</i> attribute.
     * @param validate the <i>validate</i> attribute.
     * @param dynamic whether the sql is dynamic.
     * @precondition id != null
     * @precondition dao != null
     * @precondition name != null
     * @precondition type != null
     * @precondition implementation != null
     */
    public SqlElement(
        final String id,
        final String dao,
        final String name,
        final String type,
        final String implementation,
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
     * @param dynamic whether the sql is dynamic.
     * @param repositoryScope the <i>repositoryScope</i> attribute.
     * @precondition id != null
     * @precondition name != null
     * @precondition type != null
     * @precondition implementation != null
     * @precondition repositoryScope != null
     */
    public SqlElement(
        final String id,
        final String name,
        final String type,
        final String implementation,
        final boolean validate,
        final String repositoryScope,
        final boolean dynamic)
    {
        this(
            id,
            null,
            repositoryScope,
            name,
            type,
            implementation,
            validate,
            dynamic);
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
     * @param dynamic whether the sql is dynamic.
     * @precondition id != null
     * @precondition (dao != null) || (repositoryScope != null)
     * @precondition name != null
     * @precondition type != null
     * @precondition implementation != null
     */
    protected SqlElement(
        final String id,
        final String dao,
        final String repositoryScope,
        final String name,
        final String type,
        final String implementation,
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
    protected void setDao(final String dao)
    {
        immutableSetDAO(dao);
    }

    /**
     * Retrieves the <i>dao</i> attribute.
     * @return such value.
     */
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
    protected void setRepositoryScope(final String repositoryScope)
    {
        immutableSetRepositoryScope(repositoryScope);
    }
    
    /**
     * Retrieves the <i>repositoryScope</i> attribute.
     * @return such information.
     */
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
    protected void setName(final String name)
    {
        immutableSetName(name);
    }

    /**
     * Retrieves the <i>name</i> attribute.
     * @return such value.
     */
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
    protected void setType(final String type)
    {
        immutableSetType(type);
    }

    /**
     * Retrieves the <i>type</i> attribute.
     * @return such value.
     */
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
    protected void setImplementation(final String implementation)
    {
        immutableSetImplementation(implementation);
    }

    /**
     * Retrieves the <i>implementation</i> attribute.
     * @return such value.
     */
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
    protected void setValidate(final boolean validate)
    {
        immutableSetValidate(validate);
    }

    /**
     * Specifies the <i>validate</i> attribute.
     * @param validate such flag.
     */
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
     * Specifies the <i>dynamic</i> attribute.
     * @param dynamic such flag.
     */
    protected final void immutableSetDynamic(final boolean dynamic)
    {
        m__bDynamic = dynamic;
    }

    /**
     * Specifies the <i>dynamic</i> attribute.
     * @param dynamic such flag.
     */
    protected void setDynamic(final boolean dynamic)
    {
        immutableSetDynamic(dynamic);
    }

    /**
     * Specifies the <i>dynamic</i> attribute.
     * @param dynamic such flag.
     */
    protected void isDynamic(final boolean dynamic)
    {
        immutableSetDynamic(dynamic);
    }

    /**
     * Retrieves the <i>dynamic</i> attribute.
     * @return such information.
     */
    public boolean getDynamic()
    {
        return m__bDynamic;
    }

    /**
     * Retrieves the <i>dynamic</i> attribute.
     * @return such information.
     */
    public boolean isDynamic()
    {
        return getDynamic();
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
    public void setValue(final String value)
    {
        immutableSetValue(value);
    }

    /**
     * Retrieves the &lt;value&gt; element.
     * @return such value.
     */
    public String getValue()
    {
        return m__strValue;
    }

    /**
     * Specifies the &lt;parameter-ref&gt; elements.
     * @param parameterRefs such elements.
     */
    protected final void immutableSetParameterRefs(
        final Collection collection)
    {
        m__cParameterRefs = collection;
    }

    /**
     * Specifies the &lt;parameter-ref&gt; elements.
     * @param parameterRefs such elements.
     */
    protected void setParameterRefs(final Collection collection)
    {
        immutableSetParameterRefs(collection);
    }

    /**
     * Retrieves the &lt;parameter-ref&gt; elements.
     * @return such elements.
     */
    public Collection getParameterRefs()
    {
        return m__cParameterRefs;
    }

    /**
     * Adds a new &lt;parameter-ref&gt; element.
     * @param parameterRef such element.
     */
    public void add(final ParameterRef parameterRef)
    {
        add(parameterRef, getParameterRefs());
    }

    /**
     * Adds a new &lt;parameter-ref&gt; element.
     * @param parameterRef such element.
     * @param parameterRefs thhe &ltparameter-ref&gt; elements.
     */
    protected synchronized void add(
        final ParameterRef parameterRef, final Collection parameterRefs)
    {
        Collection t_cParameterRefs = parameterRefs;

        if  (t_cParameterRefs == null)
        {
            t_cParameterRefs = new ArrayList();
            setParameterRefs(t_cParameterRefs);
        }

        t_cParameterRefs.add(parameterRef);
    }

    /**
     * Specifies the &lt;result-ref&gt; element.
     * @param resultRef such element.
     */
    protected final void immutableSetResultRef(
        final ResultRef resultRef)
    {
        m__ResultRef = resultRef;
    }

    /**
     * Specifies the &lt;result-ref&gt; element.
     * @param resultRef such element.
     */
    public void setResultRef(final ResultRef resultRef)
    {
        immutableSetResultRef(resultRef);
    }

    /**
     * Retrieves the &lt;result-ref&gt; element.
     * @return such element.
     */
    public ResultRef getResultRef()
    {
        return m__ResultRef;
    }

    /**
     * Specifies the &lt;connection-flags-ref&gt; element.
     * @param connectionflagsRef such element.
     */
    protected final void immutableSetConnectionFlagsRef(
        final ConnectionFlagsRef connectionflagsRef)
    {
        m__ConnectionFlagsRef = connectionflagsRef;
    }

    /**
     * Specifies the &lt;connection-flags-ref&gt; element.
     * @param connectionflagsRef such element.
     */
    public void setConnectionFlagsRef(final ConnectionFlagsRef connectionflagsRef)
    {
        immutableSetConnectionFlagsRef(connectionflagsRef);
    }

    /**
     * Retrieves the &lt;connection-flags-ref&gt; element.
     * @return such element.
     */
    public ConnectionFlagsRef getConnectionFlagsRef()
    {
        return m__ConnectionFlagsRef;
    }

    /**
     * Specifies the &lt;statement-flags-ref&gt; element.
     * @param statementflagsRef such element.
     */
    protected final void immutableSetStatementFlagsRef(
        final StatementFlagsRef statementflagsRef)
    {
        m__StatementFlagsRef = statementflagsRef;
    }

    /**
     * Specifies the &lt;statement-flags-ref&gt; element.
     * @param statementflagsRef such element.
     */
    public void setStatementFlagsRef(final StatementFlagsRef statementflagsRef)
    {
        immutableSetStatementFlagsRef(statementflagsRef);
    }

    /**
     * Retrieves the &lt;statement-flags-ref&gt; element.
     * @return such element.
     */
    public StatementFlagsRef getStatementFlagsRef()
    {
        return m__StatementFlagsRef;
    }

    /**
     * Specifies the &lt;resultset-flags-ref&gt; element.
     * @param resultsetflagsRef such element.
     */
    protected final void immutableSetResultSetFlagsRef(
        final ResultSetFlagsRef resultsetflagsRef)
    {
        m__ResultSetFlagsRef = resultsetflagsRef;
    }

    /**
     * Specifies the &lt;resultset-flags-ref&gt; element.
     * @param resultsetflagsRef such element.
     */
    public void setResultSetFlagsRef(final ResultSetFlagsRef resultsetflagsRef)
    {
        immutableSetResultSetFlagsRef(resultsetflagsRef);
    }

    /**
     * Retrieves the &lt;resultset-flags-ref&gt; element.
     * @return such element.
     */
    public ResultSetFlagsRef getResultSetFlagsRef()
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
