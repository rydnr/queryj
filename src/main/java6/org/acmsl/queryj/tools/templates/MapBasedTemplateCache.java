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

 *****************************************************************************
 *
 * Filename: TemplateCache.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Simple Map-based template cache.
 *
 */
package org.acmsl.queryj.tools.templates;

/*
 * Importing some JDK classes.
 */
import java.util.Map;

/**
 * Simple Map-based template cache.
 * @author <a href="mailto:chous@acm-sl.org"
 *         >Jose San Leandro</a>
 */
public class MapBasedTemplateCache
    implements TemplateCache
{
    /**
     * The map backend.
     */
    private Map m__mCache;

    /**
     * Creates a <code>MapBasedTemplateCache</code> instance.
     * @param map the map.
     */
    public MapBasedTemplateCache(final Map map)
    {
        immutableSetMap(map);
    }

    /**
     * Specifies the map.
     * @param map the map.
     */
    protected final void immutableSetMap(final Map map)
    {
        m__mCache = map;
    }

    /**
     * Specifies the map.
     * @param map the map.
     */
    protected void setMap(final Map map)
    {
        immutableSetMap(map);
    }

    /**
     * Retrieves the map.
     * @return such map.
     */
    protected Map getMap()
    {
        return m__mCache;
    }

    /**
     * Caches given template attribute.
     * @param key the attribute  key.
     * @param attribute the attribute to cache.
     * @precondition key != null
     * @precondition attribute != null
     */
    public void put(final Object key, final Object attribute)
    {
        put(key, attribute, getMap());
    }

    /**
     * Caches given template attribute.
     * @param key the attribute  key.
     * @param attribute the attribute to cache.
     * @param map the map.
     * @precondition key != null
     * @precondition attribute != null
     * @precondition map != null
     */
    protected void put(
        final Object key, final Object attribute, final Map map)
    {
        if  (   (key != null)
             && (attribute != null))
        {
            map.put(key, attribute);
        }
    }

    /**
     * Retrieves a cached template attribute.
     * @param key the attribute key.
     * @return such attribute.
     * @precondition key != null
     */
    public Object get(final Object key)
    {
        return get(key, getMap());
    }

    /**
     * Retrieves a cached template attribute.
     * @param key the attribute key.
     * @return such attribute.
     * @precondition key != null
     */
    protected Object get(final Object key, final Map map)
    {
        Object result = null;

        if  (key != null)
        {
            result = map.get(key);
        }

        return result;
    }
}
