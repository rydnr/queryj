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
 * Filename: JavaClassSource.java
 *
 * Author: Jose San Leandro Armendariz (chous)
 *
 * Description: 
 *
 * Date: 3/28/13
 * Time: 1:46 PM
 *
 */
package org.acmsl.queryj.tools.antlr;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class JavaClassSource
    implements JavaSource
{
    /**
     * The name.
     */
    private String m__strName;

    /**
     * The superclass.
     */
    private String m__strSuperClass;

    /**
     * The interfaces.
     */
    private List<String> m__lInterfaces;

    /**
     * Creates a class with given name.
     * @param name the name.
     */
    public JavaClassSource(@NotNull final String name)
    {
        immutableSetName(name);
        immutableSetInterfaces(new ArrayList<String>());
    }

    /**
     * Specifies the name.
     * @param name the name.
     */
    protected final void immutableSetName(@NotNull final String name)
    {
        this.m__strName = name;
    }

    /**
     * Specifies the name.
     * @param name the name.
     */
    protected void setName(@NotNull final String name)
    {
        immutableSetName(name);
    }

    /**
     * Retrieves the name.
     *
     * @return such information.
     */
    @Override
    @NotNull
    public String getName()
    {
        return m__strName;
    }

    /**
     * Specifies the superclass.
     * @param superclass the superclass.
     */
    protected final void immutableSetSuperClass(@NotNull final String superclass)
    {
        m__strSuperClass = superclass;
    }

    /**
     * Specifies the superclass.
     * @param superclass the superclass.
     */
    @SuppressWarnings("unused")
    public void setSuperClass(@NotNull final String superclass)
    {
        immutableSetSuperClass(superclass);
    }

    /**
     * Retrieves the superclass, if any.
     *
     * @return such information.
     */
    @Override
    public String getSuperClass()
    {
        return m__strSuperClass;
    }

    /**
     * Specifies the interfaces.
     * @param interfaces such information.
     */
    protected final void immutableSetInterfaces(@NotNull final List<String> interfaces)
    {
        m__lInterfaces = interfaces;
    }

    /**
     * Specifies the interfaces.
     * @param interfaces such information.
     */
    @SuppressWarnings("unused")
    public void setInterfaces(@NotNull final List<String> interfaces)
    {
        immutableSetInterfaces(interfaces);
    }

    /**
     * Retrieves the interfaces, if any.
     *
     * @return such information.
     */
    @Override
    public List<String> getInterfaces()
    {
        return m__lInterfaces;
    }

    /**
     * Retrieves the constructors.
     *
     * @return such information.
     */
    @Override
    public Constructor<?>[] getConstructors()
    {
        return new Constructor<?>[0];  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves whether this source represents an interface or not.
     *
     * @return <code>true</code> in such case.
     */
    @Override
    public boolean isInterface()
    {
        return false;
    }

    /**
     * Checks whether this source represents an annotation or not.
     *
     * @return <code>true</code> in such case.
     */
    @Override
    public boolean isAnnotation()
    {
        return false;
    }

    /**
     * Returns the Java language modifiers for this class or interface, encoded in an integer.
     * The modifiers consist of the Java Virtual Machine's constants for
     * public, protected, private, final, static, abstract and interface; they should be decoded using the methods of
     * class {@link java.lang.reflect.Modifier} .
     * Retrieves the modifiers.
     *
     * @return a value to be interpreted using {@link java.lang.reflect.Modifier}'s static methods.
     */
    @Override
    public int getModifiers()
    {
        return 0;
    }

    /**
     * Retrieves the fields.
     *
     * @return such information.
     */
    @Override
    public Field[] getFields()
    {
        return new Field[0];
    }

    /**
     * Retrieves the methods.
     *
     * @return such information.
     */
    @Override
    public Method[] getMethods()
    {
        return new Method[0];
    }
}
