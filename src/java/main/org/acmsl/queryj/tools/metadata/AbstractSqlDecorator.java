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
    Contact info: jose.sanleandro@acm-sl.com
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
 * Description: Decorates <sql> elements in custom-sql models.
 *
 */
package org.acmsl.queryj.tools.metadata;

/*
 * Importing project-specific classes.
 */
import org.acmsl.queryj.tools.customsql.CustomSqlProvider;
import org.acmsl.queryj.tools.customsql.ParameterElement;
import org.acmsl.queryj.tools.customsql.ParameterRefElement;
import org.acmsl.queryj.tools.customsql.ResultElement;
import org.acmsl.queryj.tools.customsql.ResultRefElement;
import org.acmsl.queryj.tools.customsql.Sql;
import org.acmsl.queryj.tools.customsql.SqlElement;
import org.acmsl.queryj.tools.metadata.CachingParameterDecorator;
import org.acmsl.queryj.tools.metadata.MetadataTypeManager;
import org.acmsl.queryj.tools.metadata.ParameterDecorator;

/*
 * Importing JDK classes.
 */
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/*
 * Importing Apache Commons-Logging classes.
 */
import org.apache.commons.logging.LogFactory;

/**
 * Decorates &lt;sql&gt; elements in <i>custom-sql</i> models.
 * @author <a href="mailto:chous@acm-sl.org"
 *         >Jose San Leandro</a>
 */
public abstract class AbstractSqlDecorator
    extends  SqlElement
    implements  SqlDecorator
{
    /**
     * The wrapped sql element.
     */
    private Sql m__Sql;

    /**
     * The custom sql provider.
     * @todo remove this.
     */
    private CustomSqlProvider m__CustomSqlProvider;

    /**
     * The metadata manager.
     */
    private MetadataManager m__MetadataManager;

    /**
     * Creates an <code>AbstractSqlDecorator</code> with given information.
     * @param sql the <code>Sql</code> to decorate.
     * @param customSqlProvider the <code>CustomSqlProvider</code>, required
     * to decorate referred parameters.
     * @param metadataTypeManager the metadata type manager.
     * @precondition sql != null
     * @precondition customSqlProvider != null
     * @precondition metadataTypeManager != null
     */
    public AbstractSqlDecorator(
        final Sql sql,
        final CustomSqlProvider customSqlProvider,
        final MetadataManager metadataManager)
    {
        super(
            sql.getId(),
            sql.getDao(),
            sql.getName(),
            sql.getType(),
            sql.getImplementation(),
            sql.getValidate());
        immutableSetDescription(sql.getDescription());
        immutableSetValue(sql.getValue());
        immutableSetParameterRefs(sql.getParameterRefs());
        immutableSetResultRef(sql.getResultRef());
        immutableSetConnectionFlagsRef(sql.getConnectionFlagsRef());
        immutableSetStatementFlagsRef(sql.getStatementFlagsRef());
        immutableSetResultSetFlagsRef(sql.getResultSetFlagsRef());
        immutableSetSql(sql);
        immutableSetCustomSqlProvider(customSqlProvider);
        immutableSetMetadataManager(metadataManager);
    }

    /**
     * Specifies the wrapped <i>sql</i> element.
     * @param sql such instance.
     */
    protected final void immutableSetSql(final Sql sql)
    {
        m__Sql = sql;
    }

    /**
     * Specifies the wrapped <i>sql</i> element.
     * @param sql such instance.
     */
    protected void setSql(final Sql sql)
    {
        immutableSetSql(sql);
    }

    /**
     * Retrieves the wrapped <i>sql</i> element.
     * @return such instance.
     */
    public Sql getSql()
    {
        return m__Sql;
    }

    /**
     * Specifies the custom SQL provider.
     * @param customSqlProvider such provider.
     */
    protected final void immutableSetCustomSqlProvider(
        final CustomSqlProvider customSqlProvider)
    {
        m__CustomSqlProvider = customSqlProvider;
    }

    /**
     * Specifies the custom SQL provider.
     * @param customSqlProvider such provider.
     */
    protected void setCustomSqlProvider(
        final CustomSqlProvider customSqlProvider)
    {
        immutableSetCustomSqlProvider(customSqlProvider);
    }

    /**
     * Retrieves the custom SQL provider.
     * @return such provider.
     */
    protected CustomSqlProvider getCustomSqlProvider()
    {
        return m__CustomSqlProvider;
    }

    /**
     * Specifies the metadata manager.
     * @param metadataManager such manager.
     */
    protected final void immutableSetMetadataManager(
        final MetadataManager metadataManager)
    {
        m__MetadataManager = metadataManager;
    }

    /**
     * Specifies the metadata  manager.
     * @param metadataManager such manager.
     */
    protected void setMetadataManager(
        final MetadataManager metadataManager)
    {
        immutableSetMetadataManager(metadataManager);
    }

    /**
     * Retrieves the metadata  manager.
     * @return such manager.
     */
    protected MetadataManager getMetadataManager()
    {
        return m__MetadataManager;
    }

    /**
     * Retrieves the metadata type manager.
     * @return such manager.
     */
    protected MetadataTypeManager getMetadataTypeManager()
    {
        return getMetadataTypeManager(getMetadataManager());
    }

    /**
     * Retrieves the metadata type manager.
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @return such manager.
     * @precondition metadataManager != null
     */
    protected MetadataTypeManager getMetadataTypeManager(
        final MetadataManager metadataManager)
    {
        return metadataManager.getMetadataTypeManager();
    }

    /**
     * Retrieves the value, in multiple lines.
     * @return such output.
     */
    public String[] getSplittedQuotedValue()
    {
        return splitAndQuote(getValue(), DecorationUtils.getInstance());
    }

    /**
     * Splits given value into several lines, quoting each one.
     * @param value the value.
     * @param decorationUtils the <code>DecorationUtils</code> instance.
     * @return the splitted value.
     * @precondition value != null
     * @precondition decorationUtils != null
     */
    protected String[] splitAndQuote(
        final String value, final DecorationUtils decorationUtils)
    {
        return
            decorationUtils.surround(
                decorationUtils.trim(
                    decorationUtils.split(
                        decorationUtils.escape(value, '\"'))),
                "\"",
                " \"");
    }

    /**
     * Retrieves the id formatted as a constant.
     * @return such information.
     */
    public String getIdAsConstant()
    {
        return uppercase(normalize(getId(), DecorationUtils.getInstance()));
    }

    /**
     * Normalizes given value.
     * @param value the value.
     * @param decorationUtils the <code>DecorationUtils</code> instance.
     * @return the value, after being processed.
     * @precondition value != null
     * @precondition decorationUtils != null
     */
    protected String normalize(
        final String value, final DecorationUtils decorationUtils)
    {
        return decorationUtils.normalize(value);
    }

    /**
     * Retrieves the id capitalized.
     * @return such information.
     */
    public String getIdCapitalized()
    {
        return capitalize(getId(), DecorationUtils.getInstance());
    }

    /**
     * Transforms given value to upper case.
     * @param value the value.
     * @return <code>value.toUpperCase()</code>.
     * @precondition value != null
     */
    protected String uppercase(final String value)
    {
        return value.toUpperCase();
    }

    /**
     * Retrieves the name, (un)capitalized.
     * @return such information.
     */
    public String getNameUncapitalized()
    {
        return uncapitalize(getName(), DecorationUtils.getInstance());
    }

    /**
     * (Un)capitalizes given value.
     * @param value the value.
     * @param decorationUtils the <code>DecorationUtils</code> instance.
     * @return the value, after being processed.
     * @precondition value != null
     * @precondition decorationUtils != null
     */
    protected String uncapitalize(
        final String value, final DecorationUtils decorationUtils)
    {
        return decorationUtils.uncapitalize(value);
    }

    /**
     * Capitalizes given value.
     * @param value the value.
     * @param decorationUtils the <code>DecorationUtils</code> instance.
     * @return the value, after being processed.
     * @precondition value != null
     * @precondition decorationUtils != null
     */
    protected String capitalize(
        final String value, final DecorationUtils decorationUtils)
    {
        return decorationUtils.capitalize(value);
    }

    /**
     * Retrieves the parameters.
     * @return such information.
     */
    public Collection getParameters()
    {
        return
            getParameters(
                getParameterRefs(),
                getCustomSqlProvider(),
                getMetadataTypeManager());
    }

    /**
     * Retrieves the parameters.
     * @todo fix reference to customSqlProvider.
     * @param parameterRefs the parameter references.
     * @param customSqlProvider the <code>CustomSqlProvider</code>.
     * @param metadataTypeManager the metadata type manager.
     * @return such information.
     * @precondition customSqlProvider != null
     * @precondition metadataTypeManager != null
     */
    protected Collection getParameters(
        final Collection parameterRefs,
        final CustomSqlProvider customSqlProvider,
        final MetadataTypeManager metadataTypeManager)
    {
        Collection result = new ArrayList();

        if  (parameterRefs != null)
        {
            Iterator t_ParameterRefIterator = parameterRefs.iterator();

            if  (t_ParameterRefIterator != null)
            {
                ParameterRefElement t_ParameterRef = null;
                ParameterElement t_Parameter = null;

                while  (t_ParameterRefIterator.hasNext())
                {
                    t_ParameterRef =
                        (ParameterRefElement) t_ParameterRefIterator.next();

                    if  (t_ParameterRef != null)
                    {
                        t_Parameter =
                            customSqlProvider.resolveReference(
                                t_ParameterRef);

                        if  (t_Parameter != null)
                        {
                            result.add(
                                new CachingParameterDecorator(
                                    t_Parameter, metadataTypeManager));
                        }
                        else
                        {
                            try
                            {
                                // todo throw something.
                                LogFactory.getLog(SqlDecorator.class).warn(
                                    "Referenced parameter not found:"
                                    + t_ParameterRef.getId());
                            }
                            catch  (final Throwable throwable)
                            {
                                // class-loading problem.
                            }
                        }
                    }
                }
            }
        }

        return result;
    }

    /**
     * Retrieves the result class.
     * @return such information.
     */
    public String getResultClass()
    {
        return getResultClass(getResultRef(), getCustomSqlProvider());
    }

    /**
     * Retrieves the result class.
     * @param resultRef the result ref.
     * @param customSqlProvider the custom sql provider.
     * @return such information.
     * @precondition customSqlProvider != null
     */
    protected String getResultClass(
        final ResultRefElement resultRef,
        final CustomSqlProvider customSqlProvider)
    {
        String result = null;

        if  (resultRef != null)
        {
            ResultElement t_Result =
                customSqlProvider.resolveReference(resultRef);

            if  (t_Result != null)
            {
                if  (ResultElement.MULTIPLE.equalsIgnoreCase(
                         t_Result.getMatches()))
                {
                    result = MULTIPLE_RESULT_CLASS;
                }
                else
                {
                    result = t_Result.getClassValue();
                }
            }
            else
            {
                try
                {
                    // todo throw something.
                    LogFactory.getLog("custom-sql").warn(
                          "Referenced result not found:"
                        + resultRef.getId());
                }
                catch  (final Throwable throwable)
                {
                    // class-loading problem.
                }
            }
        }

        return result;
    }

    /**
     * Retrieves the result id as constant.
     * @return such information.
     */
    public String getResultIdAsConstant()
    {
        return
            getResultIdAsConstant(
                getResultRef(),
                getCustomSqlProvider(),
                DecorationUtils.getInstance());
    }

    /**
     * Retrieves the result id as constant.
     * @param resultRef the result ref.
     * @param customSqlProvider the custom sql provider.
     * @param decorationUtils the <code>DecorationUtils</code> instance.
     * @return such information.
     * @precondition customSqlProvider != null
     * @precondition decorationUtils != null
     */
    protected String getResultIdAsConstant(
        final ResultRefElement resultRef,
        final CustomSqlProvider customSqlProvider,
        final DecorationUtils decorationUtils)
    {
        String result = null;

        if  (resultRef != null)
        {
            ResultElement t_Result =
                customSqlProvider.resolveReference(resultRef);

            if  (t_Result != null)
            {
                result =
                    uppercase(normalize(t_Result.getId(), decorationUtils));
            }
            else
            {
                try
                {
                    // todo throw something.
                    LogFactory.getLog("custom-sql").warn(
                          "Referenced result not found:"
                        + resultRef.getId());
                }
                catch  (final Throwable throwable)
                {
                    // class-loading problem.
                }
            }
        }

        return result;
    }
}
