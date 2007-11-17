//;-*- mode: java -*-
/*
                        QueryJ

    Copyright (C) 2002-2007  Jose San Leandro Armendariz
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
 * Filename: AntCommandHandler.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Inside a Chain Of Responsibility, these are the chain links.
 *              This means they perform specific actions when receiving the
 *              command.
 *
 */
package org.acmsl.queryj.tools.handlers;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.tools.AntCommand;

/*
 * Importing some ACM-SL classes.
 */
import org.acmsl.commons.patterns.CommandHandler;

/*
 * Importing some Ant classes.
 */
import org.apache.tools.ant.BuildException;

/*
 * Importing some JDK classes.
 */
import java.util.Map;

/**
 * Inside a Chain Of Responsibility, these are the chain links.
 * This means they perform specific actions when receiving the command.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro</a>
 */
public interface AntCommandHandler
    extends  CommandHandler
{
    /**
     * The maximum weight.
     */
    public static final double MAX_WEIGHT = 1.0d;

    /**
     * The minimum weight.
     */
    public static final double MIN_WEIGHT = 0.0d;

    /**
     * The default weight.
     */
    public static final double DEFAULT_WEIGHT =
        (MAX_WEIGHT + MIN_WEIGHT) / 2.0d;

    /**
     * Handles given command.
     * @param command the command.
     * @return <code>true</code> to avoid further processing of such command
     * by different handlers.
     * @throws BuildException if the build process cannot be performed.
     */
    public boolean handle(final AntCommand command)
        throws  BuildException;

    /**
     * Retrieves the relative weight of this handler.
     * @param command the command.
     * @return a value between <code>MIN_WEIGHT</code>
     * and <code>MAX_WEIGHT</code>.
     * @return a value between <code>MIN_WEIGHT</code> and
     * <code>MAX_WEIGHT</code>.
     */
    public double getRelativeWeight(final AntCommand command);
}
