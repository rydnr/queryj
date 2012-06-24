//;-*- mode: java -*-
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
 * Filename: QueryJCommand.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Represents QueryJ workflow commands.
 *
 */
package org.acmsl.queryj;

/*
 * Importing project classes.
 */
import org.acmsl.queryj.tools.logging.QueryJLog;

/*
 * Importing some ACM-SL classes.
 */
import org.acmsl.commons.patterns.Command;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing some JDK classes.
 */
import java.util.HashMap;
import java.util.Map;

/**
 * Represents QueryJ workflow commands.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public class QueryJCommand
    implements  Command
{
    /**
     * The attribute collection.
     */
    private Map<String,?> m__mAttributes;

    /**
     * The reference to the log instance.
     */
    private QueryJLog m__Log;

    /**
     * Constructs an empty map command.
     */
    @SuppressWarnings("unchecked")
    public QueryJCommand()
    {
        immutableSetAttributeMap(new HashMap());
    }

    /**
     * Constructs an empty map command.
     * @param log the log instance.
     */
    public QueryJCommand(@NotNull final QueryJLog log)
    {
        this();
        immutableSetLog(log);
    }

    /**
     * Specifies the attribute map.
     * @param map such map.
     */
    protected final void immutableSetAttributeMap(final Map<String,?> map)
    {
        m__mAttributes = map;
    }

    /**
     * Specifies the attribute map.
     * @param map such map.
     */
    @SuppressWarnings("unused")
    public void setAttributeMap(final Map<String,?> map)
    {
        immutableSetAttributeMap(map);
    }

    /**
     * Retrieves the attribute map.
     * @return the map.
     */
    public Map<String,?> getAttributeMap()
    {
        return m__mAttributes;
    }

    /**
     * Adds a new attribute.
     * @param name the attribute name.
     * @param value the attribute value.
     */
    public void setAttribute(@NotNull final String name, @NotNull final Object value)
    {
        setAttribute(name, value, getAttributeMap());
    }
    
    /**
     * Adds a new attribute.
     * @param name the attribute name.
     * @param value the attribute value.
     * @param map the actual attribute map.
     */
    @SuppressWarnings("unchecked")
    protected void setAttribute(
        @NotNull final String name, @NotNull final Object value, @Nullable final Map map)
    {
        if  (map != null) 
        {
            map.put(name, value);
        }
    }

    /**
     * Retrieves the attribute value.
     * @param name the attribute name.
     * @return the value or <code>null</code> if it wasn't found.
     */
    @SuppressWarnings("unused")
    @Nullable
    public Object getAttribute(@NotNull final String name)
    {
        return getAttribute(name, getAttributeMap());
    }
    
    /**
     * Retrieves the attribute value.
     * @param name the attribute name.
     * @param map the attribute map.
     * @return the value or <code>null</code> if it wasn't found.
     */
    @Nullable
    protected Object getAttribute(final String name, @Nullable final Map<String,?> map)
    {
        @Nullable Object result = null;

        if  (map != null) 
        {
            result = map.get(name);
        }
        
        return result;
    }

    /**
     * Retrieves if a concrete attribute exists.
     * @param name the attribute name.
     * @return <code>true</code> if the attribute is already defined.
     * @precondition name != null
     */
    public boolean contains(@NotNull final String name)
    {
        return contains(name, getAttributeMap());
    }
    
    /**
     * Retrieves if a concrete attribute exists.
     * @param name the attribute name.
     * @param map the attribute map.
     * @return <code>true</code> if the attribute is already defined.
     */
    protected boolean contains(@NotNull final String name, @Nullable final Map<String,?> map)
    {
        boolean result = false;

        if  (map != null) 
        {
            result = map.containsKey(name);
        }

        return result;
    }

    /**
     * Specifies the log instance.
     * @param log the log instance.
     */
    protected final void immutableSetLog(@NotNull final QueryJLog log)
    {
        m__Log = log;
    }

    /**
     * Specifies the log instance.
     * @param log the log instance.
     */
    @SuppressWarnings("unused")
    protected void setLog(@NotNull final QueryJLog log)
    {
        immutableSetLog(log);
    }

    /**
     * Retrieves the log instance.
     * @return such instance.
     */
    @Nullable
    public QueryJLog getLog()
    {
        return m__Log;
    }
}
