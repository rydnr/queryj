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
 * Description: Models <sql> elements in custom-sql models.
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
package org.acmsl.queryj.tools.customsql;

/*
 * Importing project-specific classes.
 */
import org.acmsl.queryj.tools.customsql.AbstractIdElement;
import org.acmsl.queryj.tools.customsql.ParameterRefElement;
import org.acmsl.queryj.tools.customsql.ResultRefElement;

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
 * @author <a href="mailto:jsanleandro@yahoo.es"
 *         >Jose San Leandro</a>
 * @version $Revision$
 */
public class SqlElement
    extends  AbstractIdElement
{
    /**
     * The <b>select</b> value for <i>type</i> attribute.
     */
    public static final String SELECT = "select";

    /**
     * The <b>insert</b> value for <i>type</i> attribute.
     */
    public static final String INSERT = "insert";

    /**
     * The <b>update</b> value for <i>type</i> attribute.
     */
    public static final String UPDATE = "update";

    /**
     * The <b>delete</b> value for <i>type</i> attribute.
     */
    public static final String DELETE = "delete";

    /**
     * The <b>select-for-update</b> value for <i>type</i> attribute.
     */
    public static final String SELECT_FOR_UPDATE = "select-for-update";

    /**
     * The <b>mysql</b> value for <i>type</i> attribute.
     */
    public static final String MYSQL = "mysql";

    /**
     * The <b>oracle</b> value for <i>type</i> attribute.
     */
    public static final String ORACLE = "oracle";

    /**
     * The <b>postgres</b> value for <i>type</i> attribute.
     */
    public static final String POSTGRES = "postgres";

    /**
     * The <b>odbc</b> value for <i>type</i> attribute.
     */
    public static final String ODBC = "odbc";

    /**
     * The <b>all-jdbc</b> value for <i>type</i> attribute.
     */
    public static final String ALL_JDBC = "all-jdbc";

    /**
     * The <b>mock</b> value for <i>type</i> attribute.
     */
    public static final String MOCK = "mock";

    /**
     * The <b>xml</b> value for <i>type</i> attribute.
     */
    public static final String XML = "xml";

    /**
     * The <b>all</b> value for <i>type</i> attribute.
     */
    public static final String ALL = "all";

    /**
     * The <i>dao</i> attribute.
     */
    public String m__strDAO;

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
     * The <i>value</i> element.
     */
    private String m__strValue;

    /**
     * The <i>parameter-ref</i> elements.
     */
    private Collection m__cParameterRefs;

    /**
     * The <i>result-ref</i> element.
     */
    private ResultRefElement m__ResultRef;

    /**
     * Creates a ParameterElement with given information.
     * @param id the <i>id</i> attribute.
     * @param dao the <i>dao</i> attribute.
     * @param name the <i>name</i> attribute.
     * @param type the <i>type</i> attribute.
     * @param implementation the <i>implementation</i> attribute.
     */
    public SqlElement(
        final String id,
        final String dao,
        final String name,
        final String type,
        final String implementation)
    {
        super(id);
        immutableSetDAO(dao);
        immutableSetName(name);
        immutableSetType(type);
        immutableSetImplementation(implementation);
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
        m__strDAO = dao;
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
        m__strName = name;
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
        m__strType = type;
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
        m__strImplementation = implementation;
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
     * Specifies the &lt;value&gt; element.
     * @param value such value.
     */
    public void setValue(final String value)
    {
        m__strValue = value;
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
    protected void setParameterRefs(final Collection collection)
    {
        m__cParameterRefs = collection;
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
    public void add(final ParameterRefElement parameterRef)
    {
        add(parameterRef, getParameterRefs());
    }

    /**
     * Adds a new &lt;parameter-ref&gt; element.
     * @param parameterRef such element.
     * @param parameterRefs thhe &ltparameter-ref&gt; elements.
     */
    protected synchronized void add(
        final ParameterRefElement parameterRef, final Collection parameterRefs)
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
    public void setResultRef(final ResultRefElement resultRef)
    {
        m__ResultRef = resultRef;
    }

    /**
     * Retrieves the &lt;result-ref&gt; element.
     * @return such element.
     */
    public ResultRefElement getResultRef()
    {
        return m__ResultRef;
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
                getDao(),
                getName(),
                getType(),
                getImplementation(),
                getValue(),
                getParameterRefs(),
                getResultRef());
    }

    /**
     * Provides a text information about this instance.
     * @param id the <i>id</i> attribute.
     * @param dao the <i>dao</i> attribute.
     * @param name the <i>name</i> attribute.
     * @param type the <i>type</i> attribute.
     * @param implementation the <i>implementation</i> attribute.
     * @param value the <i>value</i> element.
     * @param parameterRefs the <i>parameter-ref</i> elements.
     * @param resultRef the <i>result-ref</i> element.
     * @return such information.
     */
    protected String toString(
        final String id,
        final String dao,
        final String name,
        final String type,
        final String implementation,
        final String value,
        final Collection parameterRefs,
        final ResultRefElement resultRef)
    {
        return
              getClass().getName()
            + "[" + "id=" + id + "]"
            + "[" + "dao=" + dao + "]"
            + "[" + "name=" + name + "]"
            + "[" + "type=" + type + "]"
            + "[" + "value=" + value + "]"
            + "[" + "implementation=" + implementation + "]"
            + "[" + "parameter-refs=" + parameterRefs + "]"
            + "[" + "result-ref=" + resultRef + "]";
    }
}
