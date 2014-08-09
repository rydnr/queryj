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
 * Filename: AbstractEngine.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: 
 *
 * Date: 2014/01/24
 * Time: 07:12
 *
 */
package org.acmsl.queryj.metadata.engines;

/*
 * Importing JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 *
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2014/01/24 07:12
 */
@ThreadSafe
public abstract class AbstractEngine<T>
    implements Engine<T>
{
    /**
     * The name.
     */
    private T m__strName;

    /**
     * The version.
     */
    private T m__strVersion;

    /**
     * Whether it requires custom LOB handling.
     */
    private boolean m__bCustomLobHandling;

    /**
     * Whether it supports sequences.
     */
    private boolean m__bSupportsSequences;

    /**
     * Creates a new instance.
     * @param name the name.
     * @param version the version.
     * @param lobHandling whether it requires special lob handling.
     * @param supportsSequences whether it supports sequences.
     */
    protected AbstractEngine(
        @NotNull final T name, @NotNull final T version, final boolean lobHandling, final boolean supportsSequences)
    {
        immutableSetName(name);
        immutableSetVersion(version);
        immutableSetLobHandling(lobHandling);
        immutableSetSupportsSequences(supportsSequences);
    }

    /**
     * Specifies the name.
     * @param name the name.
     */
    protected final void immutableSetName(@NotNull final T name)
    {
        this.m__strName = name;
    }

    /**
     * Specifies the name.
     * @param name the name.
     */
    @SuppressWarnings("unused")
    protected void setName(@NotNull final T name)
    {
        immutableSetName(name);
    }

    /**
     * Retrieves the name.
     * @return such name.
     */
    @NotNull
    @Override
    public T getName()
    {
        return m__strName;
    }

    /**
     * Specifies the version.
     * @param version the version.
     */
    protected final void immutableSetVersion(@NotNull final T version)
    {
        this.m__strVersion = version;
    }

    /**
     * Specifies the version.
     * @param version the version.
     */
    @SuppressWarnings("unused")
    protected void setVersion(@NotNull final T version)
    {
        immutableSetVersion(version);
    }

    /**
     * Retrieves the version.
     * @return such version.
     */
    @NotNull
    @Override
    public T getVersion()
    {
        return m__strVersion;
    }

    /**
     * Specifies if it requires custom LOB handling or not.
     * @param flag such information.
     */
    protected final void immutableSetLobHandling(final boolean flag)
    {
        this.m__bCustomLobHandling = flag;
    }

    /**
     * Specifies if it requires custom LOB handling or not.
     * @param flag such information.
     */
    @SuppressWarnings("unused")
    protected void setLobHandling(final boolean flag)
    {
        immutableSetLobHandling(flag);
    }

    /**
     * Indicates if it requires custom LOB handling or not.
     * @return such information.
     */
    @Override
    public boolean requiresCustomLobHandling()
    {
        return this.m__bCustomLobHandling;
    }

    /**
     * Specifies if it supports sequences or not.
     * @param flag such information.
     */
    protected final void immutableSetSupportsSequences(final boolean flag)
    {
        this.m__bSupportsSequences = flag;
    }

    /**
     * Specifies if it supports sequences or not.
     * @param flag such information.
     */
    @SuppressWarnings("unused")
    protected void setSupportsSequences(final boolean flag)
    {
        immutableSetSupportsSequences(flag);
    }

    /**
     * Indicates if it supports sequences or not.
     * @return such information.
     */
    @Override
    public boolean supportsSequences()
    {
        return this.m__bSupportsSequences;
    }
}
