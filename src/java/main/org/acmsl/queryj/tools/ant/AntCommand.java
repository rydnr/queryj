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
 * Description: Represents workflow commands associated to Ant actions.
 *
 */
package org.acmsl.queryj.tools.ant;

/*
 * Importing project classes.
 */
import org.acmsl.queryj.tools.logging.QueryJAntLog;
import org.acmsl.queryj.tools.logging.QueryJLog;

/*
 * Importing some ACM-SL classes.
 */
import org.acmsl.commons.patterns.Command;

/*
 * Importing some Ant classes.
 */
import org.apache.tools.ant.Project;

/*
 * Importing some JDK classes.
 */
import java.util.HashMap;
import java.util.Map;

/**
 * Represents workflow commands associated to Ant actions.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro</a>
 */
public class AntCommand
    implements  Command
{
    /**
     * The attribute collection.
     */
    private Map m__mAttributes;

    /**
     * The reference to the log instance.
     */
    private QueryJLog m__Log;

    /**
     * Constructs an empty map command.
     */
    public AntCommand()
    {
        immutableSetAttributeMap(new HashMap());
    }

    /**
     * Constructs an empty map command.
     * @param log the log instance.
     */
    public AntCommand(final QueryJLog log)
    {
        this();
        immutableSetLog(log);
    }

    /**
     * Constructs an empty map command.
     * @param project the project, for logging purposes
     * (optional).
     * @param task the task, for logging purposes (optional).
     */
    public AntCommand(final Project project)
    {
        this(new QueryJAntLog(project));
    }

    /**
     * Specifies the attribute map.
     * @param map such map.
     */
    protected final void immutableSetAttributeMap(final Map map)
    {
        m__mAttributes = map;
    }

    /**
     * Specifies the attribute map.
     * @param map such map.
     */
    public void setAttributeMap(final Map map)
    {
        immutableSetAttributeMap(map);
    }

    /**
     * Retrieves the attribute map.
     * @return the map.
     */
    public Map getAttributeMap()
    {
        return m__mAttributes;
    }

    /**
     * Adds a new attribute.
     * @param name the attribute name.
     * @param value the attribute value.
     * @precondition name != null
     * @precondition value != null
     */
    public void setAttribute(final String name, final Object value)
    {
        setAttribute(name, value, getAttributeMap());
    }
    
    /**
     * Adds a new attribute.
     * @param name the attribute name.
     * @param value the attribute value.
     * @param map the actual attribute map.
     * @precondition name != null
     * @precondition value != null
     */
    protected void setAttribute(
        final String name, final Object value, final Map map)
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
     * @precondition name != null
     */
    public Object getAttribute(final String name)
    {
        return getAttribute(name, getAttributeMap());
    }
    
    /**
     * Retrieves the attribute value.
     * @param name the attribute name.
     * @param map the attribute map.
     * @return the value or <code>null</code> if it wasn't found.
     * @precondition name != null
     */
    protected Object getAttribute(final String name, final Map map)
    {
        Object result = null;

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
    public boolean contains(final String name)
    {
        return contains(name, getAttributeMap());
    }
    
    /**
     * Retrieves if a concrete attribute exists.
     * @param name the attribute name.
     * @param map the attribute map.
     * @return <code>true</code> if the attribute is already defined.
     * @precondition name != null
     */
    protected boolean contains(final String name, final Map map)
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
    protected final void immutableSetLog(final QueryJLog log)
    {
        m__Log = log;
    }

    /**
     * Specifies the log instance.
     * @param log the log instance.
     */
    protected void setLog(final QueryJLog log)
    {
        immutableSetLog(log);
    }

    /**
     * Retrieves the log instance.
     * @return such instance.
     */
    public QueryJLog getLog()
    {
        return m__Log;
    }

}
