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
 * Description: Represents workflow commands associated to Ant actions.
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
package org.acmsl.queryj.tools;

/*
 * Importing some ACM-SL classes.
 */
import org.acmsl.commons.patterns.Command;
import org.acmsl.commons.version.Version;
import org.acmsl.commons.version.VersionFactory;

/*
 * Importing some Ant classes.
 */
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;

/*
 * Importing some JDK classes.
 */
import java.util.HashMap;
import java.util.Map;

/**
 * Represents workflow commands associated to Ant actions.
 * @author <a href="mailto:jsanleandro@yahoo.es">Jose San Leandro</a>
 * @version $Revision$ at $Date$
 */
public class AntCommand
    implements  Command
{
    /**
     * The attribute collection.
     */
    private Map m__mAttributes;

    /**
     * The reference to the project, for logging purposes.
     */
    private Project m__Project;

    /**
     * The reference to the task, for logging purposes.
     */
    private Task m__Task;

    /**
     * Constructs an empty map command.
     */
    public AntCommand()
    {
        inmutableSetAttributeMap(new HashMap());
    }

    /**
     * Constructs an empty map command.
     * @param project the project, for logging purposes
     * (optional).
     * @param task the task, for logging purposes (optional).
     */
    public AntCommand(Project project, Task task)
    {
        this();
        inmutableSetProject(project);
        inmutableSetTask(task);
    }

    /**
     * Specifies the attribute map.
     * @param map such map.
     */
    private void inmutableSetAttributeMap(Map map)
    {
        m__mAttributes = map;
    }

    /**
     * Specifies the attribute map.
     * @param map such map.
     */
    public void setAttributeMap(Map map)
    {
        inmutableSetAttributeMap(map);
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
     */
    public void setAttribute(String name, Object value)
    {
        if  (   (name  != null)
             && (value != null))
        {
            Map t_mAttributes = getAttributeMap();

            if  (t_mAttributes != null) 
            {
                t_mAttributes.put(name, value);
            }            
        }
    }

    /**
     * Retrieves the attribute value.
     * @param name the attribute name.
     * @return the value or <code>null</code> if it wasn't found.
     */
    public Object getAttribute(String name)
    {
        Object result = null;

        if  (name != null) 
        {
            Map t_mAttributes = getAttributeMap();

            if  (t_mAttributes != null) 
            {
                result = t_mAttributes.get(name);
            }
        }
        
        return result;
    }

    /**
     * Retrieves if a concrete attribute exists.
     * @param name the attribute name.
     * @return <code>true</code> if the attribute is already defined.
     */
    public boolean contains(String name)
    {
        boolean result = false;

        if  (name != null) 
        {
            Map t_mAttributes = getAttributeMap();

            if  (t_mAttributes != null) 
            {
                result = t_mAttributes.containsKey(name);
            }
        }

        return result;
    }

    /**
     * Specifies the project.
     * @param project the project.
     */
    private void inmutableSetProject(Project project)
    {
        m__Project = project;
    }

    /**
     * Specifies the project.
     * @param project the project.
     */
    protected void setProject(Project project)
    {
        inmutableSetProject(project);
    }

    /**
     * Retrieves the project.
     * @return such instance.
     */
    public Project getProject()
    {
        return m__Project;
    }

    /**
     * Specifies the task.
     * @param task the task.
     */
    private void inmutableSetTask(Task task)
    {
        m__Task = task;
    }

    /**
     * Specifies the task.
     * @param task the task.
     */
    protected void setTask(Task task)
    {
        inmutableSetTask(task);
    }

    /**
     * Retrieves the task.
     * @return such instance.
     */
    public Task getTask()
    {
        return m__Task;
    }

    /**
     * Concrete version object updated everytime it's checked-in in a
     * CVS repository.
     */
    public static final Version VERSION =
        VersionFactory.createVersion("$Revision$");

    /**
     * Retrieves the current version of this object.
     * @return the version object with such information.
     */
    public Version getVersion()
    {
        return VERSION;
    }

    /**
     * Retrieves the current version of this class. It's defined because
     * this is a utility class that cannot be instantiated.
     * @return the object with class version information.
     */
    public static Version getClassVersion()
    {
        return VERSION;
    }
}
