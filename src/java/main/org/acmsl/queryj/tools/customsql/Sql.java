/*
                        QueryJ

    Copyright (C) 2002-2005  Jose San Leandro Armendariz
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
 * Filename: $RCSfile: $
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
import org.acmsl.queryj.tools.customsql.ParameterRefElement;
import org.acmsl.queryj.tools.customsql.ResultRefElement;

/*
 * Importing JDK classes.
 */
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
public interface Sql
    extends  IdentifiableElement
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
     * Retrieves the <i>description</i> attribute.
     * @return such value.
     */
    public String getDescription();

    /**
     * Retrieves the <i>dao</i> attribute.
     * @return such value.
     */
    public String getDao();

    /**
     * Retrieves the <i>repositoryScope</i> attribute.
     * @return such value.
     */
    public String getRepositoryScope();

    /**
     * Retrieves the <i>name</i> attribute.
     * @return such value.
     */
    public String getName();

    /**
     * Retrieves the <i>type</i> attribute.
     * @return such value.
     */
    public String getType();

    /**
     * Retrieves the <i>implementation</i> attribute.
     * @return such value.
     */
    public String getImplementation();

    /**
     * Retrieves the <i>validate</i> attribute.
     * @return such information.
     */
    public boolean getValidate();

    /**
     * Retrieves the <i>validate</i> attribute.
     * @return such information.
     */
    public boolean isValidate();

    /**
     * Retrieves the &lt;value&gt; element.
     * @return such value.
     */
    public String getValue();

    /**
     * Retrieves the &lt;parameter-ref&gt; elements.
     * @return such elements.
     */
    public Collection getParameterRefs();

    /**
     * Adds a new &lt;parameter-ref&gt; element.
     * @param parameterRef such element.
     */
    public void add(final ParameterRefElement parameterRef);

    /**
     * Retrieves the &lt;result-ref&gt; element.
     * @return such element.
     */
    public ResultRefElement getResultRef();

    /**
     * Retrieves the &lt;connection-flags-ref&gt; element.
     * @return such element.
     */
    public ConnectionFlagsRefElement getConnectionFlagsRef();

    /**
     * Retrieves the &lt;statement-flags-ref&gt; element.
     * @return such element.
     */
    public StatementFlagsRefElement getStatementFlagsRef();

    /**
     * Retrieves the &lt;resultset-flags-ref&gt; element.
     * @return such element.
     */
    public ResultSetFlagsRefElement getResultSetFlagsRef();
}
