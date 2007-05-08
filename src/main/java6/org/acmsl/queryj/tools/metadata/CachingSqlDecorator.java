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
    Contact info: jose.sanleandro@acm-sl.com
    Postal Address: c/Playa de Lagoa, 1
                    Urb. Valdecabanas
                    Boadilla del monte
                    28660 Madrid
                    Spain

 ******************************************************************************
 *
 * Filename: CachingSqlDecorator.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Adds a simple caching mechanism while decorating <sql>
 *              instances.
 *
 */
package org.acmsl.queryj.tools.metadata;

/*
 * Importing project-specific classes.
 */
import org.acmsl.queryj.tools.customsql.CustomSqlProvider;
import org.acmsl.queryj.tools.customsql.Result;
import org.acmsl.queryj.tools.customsql.Sql;
import org.acmsl.queryj.tools.metadata.MetadataManager;
import org.acmsl.queryj.tools.metadata.MetadataTypeManager;
import org.acmsl.queryj.tools.metadata.SqlDecorator;

/*
 * Importing JDK classes.
 */
import java.util.Collection;

/**
 * Adds a simple caching mechanism while decorating <code>sql</code>
 * instances.
 * @author <a href="mailto:chous@acm-sl.org"
 *         >Jose San Leandro</a>
 */
public class CachingSqlDecorator
    extends  AbstractSqlDecorator
{
    /**
     * A cached empty String array.
     */
    public static final String[] EMPTY_STRING_ARRAY = new String[0];

    /**
     * The cached splitted quoted value.
     */
    private String[] m__astrCachedSplittedQuotedValue;

    /**
     * The cached id formatted as constant.
     */
    private String m__strCachedIdAsConstant;

    /**
     * The cached capitalized id.
     */
    private String m__strCachedIdCapitalized;

    /**
     * The cached uncapitalized name.
     */
    private String m__strCachedNameUncapitalized;

    /**
     * The cached parameters.
     */
    private Collection m__cCachedParameters;

    /**
     * The cached result.
     */
    private Result m__CachedResult;
    
    /**
     * The cached result class.
     */
    private String m__strCachedResultClass;

    /**
     * The cached result id as constant.
     */
    private String m__strCachedResultIdAsConstant;

    /**
     * The cached result id capitalized.
     */
    private String m__strCachedResultIdCapitalized;

    /**
     * Creates a <code>CachingSqlDecorator</code> with given information.
     * @param sql the <code>Sql</code> to decorate.
     * @param customSqlProvider the <code>CustomSqlProvider</code>, required
     * to decorate referred parameters.
     * @param metadataManager the metadata manager.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @precondition sql != null
     * @precondition customSqlProvider != null
     * @precondition metadataManager != null
     */
    public CachingSqlDecorator(
        final Sql sql,
        final CustomSqlProvider customSqlProvider,
        final MetadataManager metadataManager,
        final DecoratorFactory decoratorFactory)
    {
        super(sql, customSqlProvider, metadataManager, decoratorFactory);
    }

    /**
     * Specifies the cached splitted quoted value.
     * @param value the value to cache.
     */
    protected final void immutableSetCachedSplittedQuotedValue(
        final String[] value)
    {
        m__astrCachedSplittedQuotedValue = value;
    }

    /**
     * Specifies the cached splitted quoted value.
     * @param value the value to cache.
     */
    protected void setCachedSplittedQuotedValue(final String[] value)
    {
        immutableSetCachedSplittedQuotedValue(value);
    }

    /**
     * Retrieves the cached splitted quoted value.
     * @return such value.
     */
    protected final String[] immutableGetCachedSplittedQuotedValue()
    {
        return m__astrCachedSplittedQuotedValue;
    }

    /**
     * Retrieves the cached splitted quoted value.
     * @return such value.
     */
    public String[] getCachedSplittedQuotedValue()
    {
        return clone(immutableGetCachedSplittedQuotedValue());
    }

    /**
     * Retrieves the value, in multiple lines.
     * @return such output.
     */
    public String[] getSplittedQuotedValue()
    {
        String[] result = getCachedSplittedQuotedValue();

        if  (   (result == null)
             || (result.length == 0))
        {
            result = super.getSplittedQuotedValue();
            setCachedSplittedQuotedValue(result);
        }

        return result;
    }

    /**
     * Specifies the cached id formatted as constant.
     * @param value the value to cache.
     */
    protected final void immutableSetCachedIdAsConstant(
        final String value)
    {
        m__strCachedIdAsConstant = value;
    }

    /**
     * Specifies the cached id formatted as constant.
     * @param value the value to cache.
     */
    protected void setCachedIdAsConstant(final String value)
    {
        immutableSetCachedIdAsConstant(value);
    }

    /**
     * Retrieves the cached id formatted as constant.
     * @return such value.
     */
    public String getCachedIdAsConstant()
    {
        return m__strCachedIdAsConstant;
    }

    /**
     * Retrieves the id formatted as constant.
     * @return such information.
     */
    public String getIdAsConstant()
    {
        String result = getCachedIdAsConstant();

        if  (result == null)
        {
            result = super.getIdAsConstant();
            setCachedIdAsConstant(result);
        }

        return result;
    }

    /**
     * Specifies the cached capitalized id.
     * @param value the value to cache.
     */
    protected final void immutableSetCachedIdCapitalized(
        final String value)
    {
        m__strCachedIdCapitalized = value;
    }

    /**
     * Specifies the cached capitalized id.
     * @param value the value to cache.
     */
    protected void setCachedIdCapitalized(final String value)
    {
        immutableSetCachedIdCapitalized(value);
    }

    /**
     * Retrieves the cached capitalized id.
     * @return such value.
     */
    public String getCachedIdCapitalized()
    {
        return m__strCachedIdCapitalized;
    }

    /**
     * Retrieves the id capitalized.
     * @return such information.
     */
    public String getIdCapitalized()
    {
        String result = getCachedIdCapitalized();

        if  (result == null)
        {
            result = super.getIdCapitalized();
            setCachedIdCapitalized(result);
        }

        return result;
    }

    /**
     * Specifies the cached uncapitalized name.
     * @param value the value to cache.
     */
    protected final void immutableSetCachedNameUncapitalized(
        final String value)
    {
        m__strCachedNameUncapitalized = value;
    }

    /**
     * Specifies the cached uncapitalized name.
     * @param value the value to cache.
     */
    protected void setCachedNameUncapitalized(final String value)
    {
        immutableSetCachedNameUncapitalized(value);
    }

    /**
     * Retrieves the cached uncapitalized name.
     * @return such value.
     */
    public String getCachedNameUncapitalized()
    {
        return m__strCachedNameUncapitalized;
    }

    /**
     * Retrieves the name, (un)capitalized.
     * @return such information.
     */
    public String getNameUncapitalized()
    {
        String result = getCachedNameUncapitalized();

        if  (result == null)
        {
            result = super.getNameUncapitalized();
            setCachedNameUncapitalized(result);
        }

        return result;
    }

    /**
     * Specifies the cached parameters.
     * @param value the value to cache.
     */
    protected final void immutableSetCachedParameters(
        final Collection value)
    {
        m__cCachedParameters = value;
    }

    /**
     * Specifies the cached parameters.
     * @param value the value to cache.
     */
    protected void setCachedParameters(final Collection value)
    {
        immutableSetCachedParameters(value);
    }

    /**
     * Retrieves the cached parameters.
     * @return such value.
     */
    public Collection getCachedParameters()
    {
        return m__cCachedParameters;
    }

    /**
     * Retrieves the parameters.
     * @return such information.
     */
    public Collection getParameters()
    {
        Collection result = getCachedParameters();

        if  (result == null)
        {
            result = super.getParameters();
            setCachedParameters(result);
        }

        return result;
    }

    /**
     * Specifies the cached result.
     * @param value such value.
     */
    protected final void immutableSetCachedResult(final Result result)
    {
        m__CachedResult = result;
    }

    /**
     * Specifies the cached result.
     * @param value such value.
     */
    protected void setCachedResult(final Result result)
    {
        immutableSetCachedResult(result);
    }
    
    /**
     * Retrieves the cached result.
     * @return such result.
     */
    protected Result getCachedResult()
    {
        return m__CachedResult;
    }

    /**
     * Retrieves the sql result.
     * @return such information.
     */
    public Result getResult()
    {
        Result result = getCachedResult();
        
        if  (result == null)
        {
            result = super.getResult();
            setCachedResult(result);
        }
        
        return result;
    }
    
    /**
     * Specifies the cached result class.
     * @param value the value to cache.
     */
    protected final void immutableSetCachedResultClass(
        final String value)
    {
        m__strCachedResultClass = value;
    }

    /**
     * Specifies the cached result class.
     * @param value the value to cache.
     */
    protected void setCachedResultClass(final String value)
    {
        immutableSetCachedResultClass(value);
    }

    /**
     * Retrieves the cached result class.
     * @return such value.
     */
    public String getCachedResultClass()
    {
        return m__strCachedResultClass;
    }

    /**
     * Retrieves the result class.
     * @return such information.
     */
    public String getResultClass()
    {
        String result = getCachedResultClass();

        if  (result == null)
        {
            result = super.getResultClass();
            setCachedResultClass(result);
        }

        return result;
    }

    /**
     * Specifies the cached result id as constant.
     * @param value the value to cache.
     */
    protected final void immutableSetCachedResultIdAsConstant(
        final String value)
    {
        m__strCachedResultIdAsConstant = value;
    }

    /**
     * Specifies the cached result id as constant.
     * @param value the value to cache.
     */
    protected void setCachedResultIdAsConstant(final String value)
    {
        immutableSetCachedResultIdAsConstant(value);
    }

    /**
     * Retrieves the cached result id as constant.
     * @return such value.
     */
    public String getCachedResultIdAsConstant()
    {
        return m__strCachedResultIdAsConstant;
    }

    /**
     * Retrieves the result id as constant.
     * @return such information.
     */
    public String getResultIdAsConstant()
    {
        String result = getCachedResultIdAsConstant();

        if  (result == null)
        {
            result = super.getResultIdAsConstant();
            setCachedResultIdAsConstant(result);
        }

        return result;
    }

    /**
     * Specifies the cached result id capitalized.
     * @param value the value to cache.
     */
    protected final void immutableSetCachedResultIdCapitalized(
        final String value)
    {
        m__strCachedResultIdCapitalized = value;
    }

    /**
     * Specifies the cached result id capitalized.
     * @param value the value to cache.
     */
    protected void setCachedResultIdCapitalized(final String value)
    {
        immutableSetCachedResultIdCapitalized(value);
    }

    /**
     * Retrieves the cached result id capitalized.
     * @return such value.
     */
    public String getCachedResultIdCapitalized()
    {
        return m__strCachedResultIdCapitalized;
    }

    /**
     * Retrieves the result id capitalized.
     * @return such information.
     */
    public String getResultIdCapitalized()
    {
        String result = getCachedResultIdCapitalized();

        if  (result == null)
        {
            result = super.getResultIdCapitalized();
            setCachedResultIdCapitalized(result);
        }

        return result;
    }

    /**
     * Clones given String array.
     * @param array the array to clone.
     * @return the cloned array.
     * @precondition array != null
     */
    protected String[] clone(final String[] array)
    {
        String[] result = EMPTY_STRING_ARRAY;

        int t_iCount = (array != null) ? array.length : 0;

        if  (t_iCount > 0)
        {
            result = new String[t_iCount];

            for  (int t_iIndex = 0; t_iIndex < t_iCount; t_iIndex++)
            {
                result[t_iIndex] = array[t_iIndex];
            }
        }

        return result;
    }
}
