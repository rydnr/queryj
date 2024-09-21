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
 * Filename: MetadataTypeUtils.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Provides some methods commonly-reused when working with
 *              types of metadata.
 */
package org.acmsl.queryj.metadata;

/*
 * Importing ACM-SL Java Commons classes.
 */
import org.acmsl.commons.patterns.Singleton;
import org.acmsl.commons.patterns.Utils;
import org.acmsl.queryj.Literals;

/*
 * Importing NotNull annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * Provides some methods commonly-used when working with types of metadata.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
@ThreadSafe
public class MetadataTypeUtils
    implements  Singleton,
                Utils
{
    /**
     * Singleton implemented to avoid the double-checked locking.
     */
    private static class MetadataTypeUtilsSingletonContainer
    {
        /**
         * The actual singleton.
         */
        public static final MetadataTypeUtils SINGLETON =
            new MetadataTypeUtils();
    }

    /**
     * Protected constructor to avoid accidental instantiation.
     */
    protected MetadataTypeUtils() {};

    /**
     * Retrieves a <code>MetadataTypeUtils</code> instance.
     * @return such instance.
     */
    @NotNull
    public static MetadataTypeUtils getInstance()
    {
        return MetadataTypeUtilsSingletonContainer.SINGLETON;
    }

    /**
     * Retrieves the wrapper class associated to given Java type.
     * @param type the Java type.
     * @return such class name (not fully-qualified).
     */
    @NotNull
    public String getWrapperClass(@NotNull final String type)
    {
        String result = type;

        if  (   (Literals.BOOLEAN.equals(type))
             || (Literals.BOOLEAN.equals(type))
             || ("java.lang.Boolean".equals(type)))
        {
            result = Literals.BOOLEAN;
        }
        else if  (   ("byte".equals(type))
                  || ("Byte".equals(type))
                  || ("java.lang.Byte".equals(type)))
        {
            result = "Byte";
        }
        else if  (   ("short".equals(type))
                  || ("Short".equals(type))
                  || ("java.lang.Short".equals(type)))
        {
            result = "Short";
        }
        else if  (   ("char".equals(type))
                  || ("Character".equals(type))
                  || ("java.lang.Character".equals(type)))
        {
            result = "Character";
        }
        else if  (   ("int".equals(type))
                  || (Literals.INTEGER.equals(type))
                  || ("java.lang.Long".equals(type)))
        {
            result = Literals.INTEGER;
        }
        else if  (   ("long".equals(type))
                  || ("Long".equals(type))
                  || ("java.lang.Long".equals(type)))
        {
            result = "Long";
        }
        else if  (   (Literals.FLOAT.equals(type))
                  || (Literals.FLOAT_C.equals(type))
                  || ("java.lang.Float".equals(type)))
        {
            result = Literals.FLOAT_C;
        }
        else if  (   (Literals.DOUBLE.equals(type))
                  || (Literals.DOUBLE_C.equals(type))
                  || ("java.lang.Double".equals(type)))
        {
            result = Literals.DOUBLE_C;
        }

        return result;
    }


    /**
     * Provides a text representation of the information
     * contained in this instance.
     * @return such information.
     */
    @Override
    @NotNull
    public String toString()
    {
        return
            new org.apache.commons.lang.builder.ToStringBuilder(this)
                .appendSuper(super.toString())
                .toString();
    }

    /**
     * Retrieves the hash code associated to this instance.
     * @return such information.
     */
    @Override
    public int hashCode()
    {
        return
            new org.apache.commons.lang.builder.HashCodeBuilder(-2052006153, 987877993)
                .appendSuper(super.hashCode())
                .toHashCode();
    }

    /**
     * Checks whether given object is semantically equal to this instance.
     * @param object the object to compare to.
     * @return the result of such comparison.
     */
    @Override
    public boolean equals(@Nullable final Object object)
    {
        return
            new org.apache.commons.lang.builder.EqualsBuilder()
                .appendSuper(super.equals(object))
            .isEquals();
    }
}
