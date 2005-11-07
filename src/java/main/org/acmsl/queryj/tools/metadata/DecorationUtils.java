/*
                        QueryJ

    Copyright (C) 2002-2005  Jose San Leandro Armend&aacute;riz
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
                    Urb. Valdecaba&ntilde;as
                    Boadilla del monte
                    28660 Madrid
                    Spain

 ******************************************************************************
 *
 * Filename: $RCSfile$
 *
 * Author: Jose San Leandro Armend&aacute;riz
 *
 * Description: Provides some useful decoration methods.
 *
 */
package org.acmsl.queryj.tools.metadata;

/*
 * Importing ACM-SL Commons classes.
 */
import org.acmsl.commons.patterns.Utils;
import org.acmsl.commons.utils.StringUtils;

/*
 * Importing some JDK classes.
 */
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.StringTokenizer;

/**
 * Provides some useful decoration methods.
 * @author <a href="mailto:chous@acm-sl.org"
 *         >Jose San Leandro Armend&aacute;riz</a>
 */
public class DecorationUtils
    implements  Utils
{
    /**
     * An empty cached String array.
     */
    public static final String[] EMPTY_STRING_ARRAY = new String[0];

    /**
     * Singleton implemented as a weak reference.
     */
    private static WeakReference singleton;

    /**
     * Protected constructor to avoid accidental instantiation.
     */
    protected DecorationUtils() {};

    /**
     * Specifies a new weak reference.
     * @param utils the utils instance to use.
     */
    protected static void setReference(final DecorationUtils utils)
    {
        singleton = new WeakReference(utils);
    }

    /**
     * Retrieves the weak reference.
     * @return such reference.
     */
    protected static WeakReference getReference()
    {
        return singleton;
    }

    /**
     * Retrieves a <code>DecorationUtils</code> instance.
     * @return such instance.
     */
    public static DecorationUtils getInstance()
    {
        DecorationUtils result = null;

        WeakReference reference = getReference();

        if  (reference != null) 
        {
            result = (DecorationUtils) reference.get();
        }

        if  (result == null) 
        {
            result = new DecorationUtils();

            setReference(result);
        }

        return result;
    }

    /**
     * Converts given value to upper-case.
     * @param value the value.
     * @return the alternate version of the value.
     * @precondition value != null
     */
    public String upperCase(final String value)
    {
        return value.toUpperCase();
    }
    
    /**
     * Converts given value to lower-case.
     * @param value the value.
     * @return the alternate version of the value.
     * @precondition value != null
     */
    public String lowerCase(final String value)
    {
        return value.toLowerCase();
    }

    /**
     * Uncapitalizes given value.
     * @param value the value.
     * @return the modified version of the value.
     * @precondition value != null
     */
    public String uncapitalize(final String value)
    {
        return uncapitalize(value, StringUtils.getInstance());
        
    }

    /**
     * Uncapitalizes given value.
     * @param value the value.
     * @param stringUtils the <code>StringUtils</code> instance.
     * @return the modified version of the value.
     * @precondition value != null
     * @precondition stringUtils != null
     */
    protected String uncapitalize(
        final String value, final StringUtils stringUtils)
    {
        return stringUtils.unCapitalizeStart(stringUtils.capitalize(value));
    }

    /**
     * Capitalizes given value.
     * @param value the value.
     * @return the modified version of the value.
     * @precondition value != null
     */
    public String capitalize(final String value)
    {
        return capitalize(value, StringUtils.getInstance());
    }

    /**
     * Capitalizes given value.
     * @param value the value.
     * @param stringUtils the <code>StringUtils</code> instance.
     * @return the modified version of the value.
     * @precondition value != null
     * @precondition stringUtils != null
     */
    protected String capitalize(
        final String value, final StringUtils stringUtils)
    {
        return stringUtils.capitalize(value);
    }

    /**
     * Normalizes given value.
     * @param value the value.
     * @return the normalized value.
     * @precondition value != null
     */
    public String normalize(final String value)
    {
        return normalizeUppercase(value).toLowerCase();
    }
    
    /**
     * Normalizes given value, in upper case.
     * @param value the value.
     * @return the normalized value.
     * @precondition value != null
     */
    public String normalizeUppercase(final String value)
    {
        return normalizeUppercase(value, StringUtils.getInstance());
    }
    
    /**
     * Normalizes given value, in upper case.
     * @param value the value.
     * @param stringUtils the <code>StringUtils</code> instance.
     * @return the normalized value.
     * @precondition value != null
     * @precondition stringUtils != null
     */
    protected String normalizeUppercase(
        final String value, final StringUtils stringUtils)
    {
        return stringUtils.normalize(value, '_');
    }

    /**
     * Splits given value into multiple lines.
     * @param value the value.
     * @return such output.
     * @precondition value != null
     */
    public String[] split(final String value)
    {
        return
            split(
                value,
                new String[] { System.getProperty("line.separator"), "\n" });
    }
    
    /**
     * Splits given value into multiple lines.
     * @param value the value.
     * @param separators an ordered list of separators. The first non-null
     * will be the one used.
     * @return such output.
     * @precondition value != null
     * @precondition separators != null
     */
    public String[] split(final String value, final String[] separators)
    {
        Collection t_cResult = new ArrayList();

        int t_iLength = (separators != null) ? separators.length : 0;
        
        String t_strSeparator = null;
        
        for  (int t_iIndex = 0; t_iIndex < t_iLength; t_iIndex++)
        {
            t_strSeparator = separators[t_iIndex];

            if  (t_strSeparator != null)
            {
                break;
            }
        }

        if  (t_strSeparator != null)
        {
            StringTokenizer t_Tokenizer =
                new StringTokenizer(value.trim(), t_strSeparator, false);

            while  (t_Tokenizer.hasMoreTokens())
            {
                t_cResult.add(t_Tokenizer.nextToken());
            }
        }
        
        return (String[]) t_cResult.toArray(EMPTY_STRING_ARRAY);
    }

    /**
     * Surrounds given values using given separator.
     * @param values the values.
     * @param separator the separator.
     * @return the quoted values.
     * @precondition values != null
     * @precondition separator != null
     */
    public String[] surround(final String[] values, final String separator)
    {
        return surround(values, separator, separator);
    }

    /**
     * Surrounds given values using given separators.
     * @param values the values.
     * @param leftSeparator the left-side separator.
     * @param rightSeparator the right-side separator.
     * @return the quoted values.
     * @precondition values != null
     * @precondition separator != null
     */
    public String[] surround(
        final String[] values,
        final String leftSeparator,
        final String rightSeparator)
    {
        String[] result = EMPTY_STRING_ARRAY;
        
        int t_iLength = (values != null) ? values.length : 0;
        
        result = new String[t_iLength];
        
        for  (int t_iIndex = 0; t_iIndex < t_iLength; t_iIndex++)
        {
            result[t_iIndex] = values[t_iIndex];
            
            if  (result[t_iIndex] != null)
            {
                result[t_iIndex] =
                    leftSeparator + result[t_iIndex] + rightSeparator;
            }
        }
        
        return result;
    }

    /**
     * Trims given values.
     * @param values the values.
     * @return the trimmed lines.
     * @precondition values != null
     */
    public String[] trim(final String[] values)
    {
        Collection t_cResult = new ArrayList();
        
        int t_iLength = (values != null) ? values.length : 0;

        String t_strCurrentLine = null;
        
        for  (int t_iIndex = 0; t_iIndex < t_iLength; t_iIndex++)
        {
            t_strCurrentLine = values[t_iIndex];
            
            if  (t_strCurrentLine != null)
            {
                t_strCurrentLine = t_strCurrentLine.trim();
            }
            
            if  (t_strCurrentLine.length() > 0)
            {
                t_cResult.add(t_strCurrentLine);
            }
        }
        
        return (String[]) t_cResult.toArray(EMPTY_STRING_ARRAY);
    }
}
