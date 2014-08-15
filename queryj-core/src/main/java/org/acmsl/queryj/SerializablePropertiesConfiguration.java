/*
                        QueryJ Core

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
 * Filename: SerializablePropertiesConfiguration.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Makes PropertiesConfiguration Serializable.
 *
 * Date: 2014/04/30
 * Time: 12:54
 *
 */
package org.acmsl.queryj;

/*
 * Importing Apache Commons Configuration classes.
 */
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

/*
 * Importing JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/*
 * Importing JDK classes.
 */
import java.io.File;
import java.io.Serializable;
import java.net.URL;

/**
 * Makes {@link PropertiesConfiguration} {@link Serializable}.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2014/04/30 12:54
 */
@SuppressWarnings("unused")
@ThreadSafe
public class SerializablePropertiesConfiguration
    extends PropertiesConfiguration
    implements Serializable
{
    /**
     * The serial version id.
     */
    private static final long serialVersionUID = 79790522189184391L;

    /**
     * {@inheritDoc}
     */
    public SerializablePropertiesConfiguration()
    {
    }

    /**
     * {@inheritDoc}
     */
    public SerializablePropertiesConfiguration(@NotNull final String fileName)
        throws ConfigurationException
    {
        super(fileName);
    }

    /**
     * {@inheritDoc}
     */
    public SerializablePropertiesConfiguration(@NotNull final File file)
        throws ConfigurationException
    {
        super(file);
    }

    /**
     * {@inheritDoc}
     */
    public SerializablePropertiesConfiguration(@NotNull final URL url)
        throws ConfigurationException
    {
        super(url);
    }
}
