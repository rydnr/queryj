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
 * Filename: $RCSfile$
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Decorates <result> elements in custom-sql models.
 *
 */
package org.acmsl.queryj.tools.metadata;

/*
 * Importing project-specific classes.
 */
import org.acmsl.queryj.tools.customsql.CustomSqlProvider;
import org.acmsl.queryj.tools.customsql.PropertyElement;
import org.acmsl.queryj.tools.customsql.PropertyRefElement;
import org.acmsl.queryj.tools.customsql.ResultElement;
import org.acmsl.queryj.tools.metadata.DecorationUtils;

/*
 * Importing JDK classes.
 */
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/*
 * Importing Apache Commons Logging classes.
 */
import org.apache.commons.logging.LogFactory;

/**
 * Decorates &lt;result&gt; elements in <i>custom-sql</i> models.
 * @author <a href="mailto:chous@acm-sl.org"
 *         >Jose San Leandro</a>
 */
public class ResultDecorator
    extends  ResultElement
{
    /**
     * The result element.
     */
    private ResultElement m__Result;

    /**
     * The custom sql provider.
     * @todo remove this.
     */
    private CustomSqlProvider m__CustomSqlProvider;
    
    /**
     * Creates a <code>ResultElementDecorator</code> with given instance.
     * @param result the result element.
     * @param customSqlProvider the <code>CustomSqlProvider</code>, required
     * to decorate referred parameters.
     * @precondition result != null
     * @precondition customSqlProvider != null
     */
    public ResultDecorator(
        final ResultElement result, final CustomSqlProvider customSqlProvider)
    {
        super(
            result.getId(),
            result.getClassValue(),
            result.getMatches());
        immutableSetResult(result);
        immutableSetPropertyRefs(result.getPropertyRefs());
        immutableSetCustomSqlProvider(customSqlProvider);
    }

    /**
     * Specifies the result.
     * @param result the result.
     */
    protected final void immutableSetResult(final ResultElement result)
    {
        m__Result = result;
    }

    /**
     * Specifies the result.
     * @param result the result.
     */
    protected void setResult(final ResultElement result)
    {
        immutableSetResult(result);
    }

    /**
     * Retrieves the result.
     * @return such element.
     */
    public ResultElement getResult()
    {
        return m__Result;
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
     * Retrieves the id, normalized.
     * @return such information.
     */
    public String getIdNormalized()
    {
        return normalize(getId(), DecorationUtils.getInstance());
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
     * Normalizes given value.
     * @param value the value.
     * @param decorationUtils the <code>DecorationUtils</code> instance.
     * @return such information.
     * @precondition value != null
     * @precondition decorationUtils != null
     */
    protected String normalize(
        final String value, final DecorationUtils decorationUtils)
    {
        return decorationUtils.normalize(value);
    }
    
    /**
     * Retrieves the id, normalized and upper-cased.
     * @return such information.
     */
    public String getIdNormalizedUppercased()
    {
        return normalizeUppercase(getId(), DecorationUtils.getInstance());
    }
    
    /**
     * Normalizes given value, in upper-case.
     * @param value the value.
     * @param decorationUtils the <code>DecorationUtils</code> instance.
     * @return such information.
     * @precondition value != null
     * @precondition decorationUtils != null
     */
    protected String normalizeUppercase(
        final String value, final DecorationUtils decorationUtils)
    {
        return decorationUtils.normalizeUppercase(value);
    }
    
    /**
     * Retrieves the properties.
     * @return such information.
     */
    public Collection getProperties()
    {
        return getProperties(getPropertyRefs(), getCustomSqlProvider());
    }
    
    /**
     * Retrieves the properties.
     * @todo fix reference to customSqlProvider.
     * @param propertyRefs the property references.
     * @param customSqlProvider the <code>CustomSqlProvider</code>.
     * @return such information.
     * @precondition customSqlProvider != null
     */
    public Collection getProperties(
        final Collection propertyRefs,
        final CustomSqlProvider customSqlProvider)
    {
        Collection result = new ArrayList();

        if  (propertyRefs != null)
        {
            Iterator t_PropertyRefIterator = propertyRefs.iterator();
            
            if  (t_PropertyRefIterator != null)
            {
                PropertyRefElement t_PropertyRef = null;
                PropertyElement t_Property = null;
                
                while  (t_PropertyRefIterator.hasNext())
                {
                    t_PropertyRef =
                        (PropertyRefElement) t_PropertyRefIterator.next();

                    if  (t_PropertyRef != null)
                    {
                        t_Property =
                            customSqlProvider.resolveReference(t_PropertyRef);

                        result.add(t_Property);
                            //new PropertyDecorator(t_Property));
                    }
                    else
                    {
                        try
                        {
                            // todo throw something.
                            LogFactory.getLog("custom-sql").warn(
                                  "Referenced property not found:"
                                + t_PropertyRef.getId());
                        }
                        catch  (final Throwable throwable)
                        {
                            // class-loading problem.
                        }
                    }
                }
            }
        }

        return result;
    }

    /**
     * Retrieves the hashcode.
     * @return such value.
     */
    public int hashCode()
    {
        return hashCode(getResult());
    }

    /**
     * Retrieves the hashcode.
     * @param result the result element.
     * @return such value.
     * @precondition result != null
     */
    protected int hashCode(final ResultElement result)
    {
        return result.hashCode();
    }

    /**
     * Checks whether given instance is semantically equivalent to this one.
     * @param instance the instance to compare with.
     * @return the result of the check.
     */
    public boolean equals(final Object instance)
    {
        return equals(instance, getResult());
    }

    /**
     * Checks whether given instance is semantically equivalent to this one.
     * @param instance the instance to compare with.
     * @param wrappedResult the wrapped result.
     * @return the result of the check.
     * @precondition wrappedResult != null
     */
    protected boolean equals(
        final Object instance, final ResultElement wrappedResult)
    {
        boolean result = false;

        if  (   (instance != null)
             && (instance instanceof ResultElement))
        {
            result = instance.equals(wrappedResult);
        }
        
        return result;
    }
}
