/*
                        QueryJ Test

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
 * Filename: BeanElementFactory.java
 *
 * Author: Jose San Leandro Armendariz (chous)
 *
 * Description: Creates BeanElements.
 *
 * Date: 5/25/13
 * Time: 8:25 PM
 *
 */
package org.acmsl.queryj.test.xml;

/*
 * Importing some Digester classes.
 */
import org.apache.commons.digester.Digester;
import org.apache.commons.digester.ObjectCreationFactory;

/*
 * Importing some Jetbrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing SAX classes.
 */
import org.xml.sax.Attributes;

/**
 * Creates {@link BeanElement}s.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2013/05/25
 */
public class BeanElementFactory
    implements ObjectCreationFactory
{
    /**
     * The digester instance.
     */
    private Digester m__Digester;

    /**
     * <p>Factory method called by {@link org.apache.commons.digester.FactoryCreateRule} to supply an
     * object based on the element's attributes.
     *
     * @param attributes the element's attributes
     */
    @NotNull
    @Override
    public Object createObject(@NotNull final Attributes attributes)
        throws Exception
    {
        final @Nullable String t_strId = attributes.getValue("id");
        final @Nullable String t_strClass = attributes.getValue("class");

        return new BeanElement(t_strId, t_strClass);
    }

    /**
     * <p>Returns the {@link org.apache.commons.digester.Digester} that was set by the
     * {@link org.apache.commons.digester.FactoryCreateRule} upon initialization.
     */
    @Override
    @Nullable
    public Digester getDigester()
    {
        return m__Digester;
    }

    /**
     * <p>Set the {@link org.apache.commons.digester.Digester} to allow the implementation to do logging,
     * classloading based on the Digester's classloader, etc.
     *
     * @param digester parent Digester object.
     */
    @Override
    public void setDigester(@NotNull final Digester digester)
    {
        this.m__Digester = digester;
    }

    @Override
    public String toString()
    {
        return
            "BeanElementFactory{ digester=" + getDigester() + " }";
    }
}

