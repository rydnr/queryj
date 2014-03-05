/*
                        QueryJ-Core

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
 * Filename: EngineDecorator.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: 
 *
 * Date: 2014/01/24
 * Time: 07:24
 *
 */
package org.acmsl.queryj.metadata.engines;

/*
 * Importing QueryJ-Core classes.
 */
import org.acmsl.queryj.metadata.DecoratedString;

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
 * Created: 2014/01/24 07:24
 */
@ThreadSafe
public class EngineDecorator
    extends AbstractEngine<DecoratedString>
{
    /**
     * The serial version id.
     */
    private static final long serialVersionUID = -1162196549794131467L;

    /**
     * The wrapped engine.
     */
    private Engine<String> m__Engine;

    /**
     * Creates a new decorator.
     * @param engine the engine.
     */
    public EngineDecorator(@NotNull final Engine<String> engine)
    {
        super(
            new DecoratedString(engine.getName()),
            new DecoratedString(engine.getVersion()),
            engine.requiresCustomLobHandling(),
            engine.supportsSequences());
        immutableSetEngine(engine);
    }

    /**
     * Specifies the engine.
     * @param engine such engine.
     */
    protected final void immutableSetEngine(@NotNull final Engine<String> engine)
    {
        this.m__Engine = engine;
    }

    /**
     * Specifies the engine.
     * @param engine such engine.
     */
    @SuppressWarnings("unused")
    protected void setEngine(@NotNull final Engine<String> engine)
    {
        immutableSetEngine(engine);
    }

    /**
     * Retrieves the engine.
     * @return such engine.
     */
    @SuppressWarnings("unused")
    public Engine<String> getEngine()
    {
        return this.m__Engine;
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    public String toString()
    {
        return
              "{ \"engine\": " + m__Engine
            + " , \"class\": \"" + EngineDecorator.class.getName() + "\" }";
    }
}
