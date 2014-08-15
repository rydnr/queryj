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
 * Filename: JavaSource.java
 *
 * Author: Jose San Leandro Armendariz (chous)
 *
 * Description: Represents parsed Java sources. Defines a subset of Class,
 *              but it cannot be used since it's final and most methods native.
 *
 * Date: 3/28/13
 * Time: 10:48 AM
 *
 */
package org.acmsl.queryj.tools.antlr;

/**
 * Importing NotNull annotations.
 */
import org.jetbrains.annotations.NotNull;

/**
 * Importing JDK classes.
 */
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Represents parsed Java sources. Defines a subset of {@link Class}, but it cannot be used
 * since it's final and most methods native.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 2013/03
 */
public interface JavaSource
{
    /**
     * Retrieves whether this source represents an interface or not.
     * @return <code>true</code> in such case.
     */
    @SuppressWarnings("unused")
    public boolean isInterface();

    /**
     * Checks whether this source represents an annotation or not.
     * @return <code>true</code> in such case.
     */
    @SuppressWarnings("unused")
    public boolean isAnnotation();

    /**
     * Retrieves the name.
     * @return such information.
     */
    @NotNull
    public java.lang.String getName();

    /**
     * Returns the Java language modifiers for this class or interface, encoded in an integer.
     * The modifiers consist of the Java Virtual Machine's constants for
     * public, protected, private, final, static, abstract and interface; they should be decoded using the methods of
     * class {@link java.lang.reflect.Modifier} .
     * Retrieves the modifiers.
     * @return a value to be interpreted using {@link java.lang.reflect.Modifier}'s static methods.
     */
    @SuppressWarnings("unused")
    public int getModifiers();

    /**
     * Retrieves the fields.
     * @return such information.
     */
    public Field[] getFields();

    /**
     * Retrieves the methods.
     * @return such information.
     */
    @SuppressWarnings("unused")
    public Method[] getMethods();
}
