//;-*- mode: java -*-
/*
                        QueryJ

    Copyright (C) 2002-2006  Jose San Leandro Armendariz
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
 * Description: Provides some methods commonly-reused when working with
 *              ST templates.
 */
package org.acmsl.queryj.tools.templates;

/*
 * Importing ACM-SL Commons classes.
 */
import org.acmsl.commons.patterns.Utils;

/*
 * Importing StringTemplate classes.
 */
import org.antlr.stringtemplate.language.AngleBracketTemplateLexer;
import org.antlr.stringtemplate.StringTemplateGroup;

/*
 * Importing some JDK classes.
 */
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.util.WeakHashMap;
import java.util.Map;

/**
 * Provides some methods commonly-used when working with ST templates.
 * @author <a href="mailto:chous@acm-sl.org"
 *         >Jose San Leandro</a>
 */
public class STUtils
    implements Utils
{
    /**
     * The cached template groups.
     */
    private static final Map ST_GROUPS = new WeakHashMap();

    /**
     * Singleton implemented as a weak reference.
     */
    private static WeakReference singleton;

    /**
     * Protected constructor to avoid accidental instantiation.
     */
    protected STUtils() {};

    /**
     * Specifies a new weak reference.
     * @param utils the utils instance to use.
     */
    protected static void setReference(final STUtils utils)
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
     * Retrieves a <code>STUtils</code> instance.
     * @return such instance.
     */
    public static STUtils getInstance()
    {
        STUtils result = null;

        WeakReference reference = getReference();

        if  (reference != null) 
        {
            result = (STUtils) reference.get();
        }

        if  (result == null) 
        {
            result = new STUtils();

            setReference(result);
        }

        return result;
    }
    
    /**
     * Retrieves the string template group.
     * @param path the path.
     * @param theme the theme.
     * @return such instance.
     * @precondition path != null
     * @precondition theme != null
     */
    public StringTemplateGroup retrieveGroup(
        final String path, final String theme)
    {
        StringTemplateGroup result = (StringTemplateGroup) ST_GROUPS.get(path);
        
        if  (result == null)
        {
            StringTemplateGroup t_Theme = retrieveGroup(theme);
            result = retrieveUncachedGroup(path);

            if  (result != null)
            {
                result.setSuperGroup(t_Theme);
                ST_GROUPS.put(path, result);
            }
        }
        
        return result;
    }
            
    /**
     * Retrieves the string template group.
     * @param path the path.
     * @param theme the theme.
     * @return such instance.
     * @precondition path != null
     * @precondition theme != null
     */
    protected StringTemplateGroup retrieveGroup(
        final String path)
    {
        StringTemplateGroup result = (StringTemplateGroup) ST_GROUPS.get(path);
        
        if  (result == null)
        {
            result = retrieveUncachedGroup(path);
            ST_GROUPS.put(path, result);
        }
        
        return result;
    }
            
    /**
     * Retrieves the string template group.
     * @param path the path.
     * @param theme the theme.
     * @return such instance.
     * @precondition path != null
     * @precondition theme != null
     */
    protected StringTemplateGroup retrieveUncachedGroup(
        final String path)
    {
        StringTemplateGroup result = null;

        InputStream t_Input = STUtils.class.getResourceAsStream(path);

        result =
            new StringTemplateGroup(
                new InputStreamReader(t_Input),
                AngleBracketTemplateLexer.class);

        return result;
    }

}
