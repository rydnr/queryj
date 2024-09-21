/*
                        QueryJ Maven

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
 * Filename: CommonsLoggingMavenLogAdapterTest.java
 *
 * Author: Jose San Leandro Armendariz.
 *
 * Description: Tests the adapter to use Maven Log within Log4J
 */
package org.acmsl.queryj.tools.maven;

/*
 * Importing some ACM-SL classes.
 */

/*
 * Importing some Maven classes.
 */
import org.apache.maven.plugin.logging.Log;

/*
 * Importing some Jetbrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/*
 * Importing EasyMock classes.
 */
import org.easymock.EasyMock;

/*
 * Importing PowerMock classes.
 */
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/*
 * Importing JUnit classes.
 */
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Executes QueryJ.
 */
@SuppressWarnings("unused")
@RunWith(PowerMockRunner.class)
@PrepareForTest(Log.class)
public class CommonsLoggingMavenLogAdapterTest
{
    @Test
    public void commons_logging_outputs_to_maven_log()
    {
        @NotNull final Log t_Log = EasyMock.createNiceMock(Log.class);

        @NotNull final String t_strLogMessage = "logging something";

        t_Log.info(t_strLogMessage);
        EasyMock.expectLastCall();
        EasyMock.replay(t_Log);

        @NotNull final CommonsLoggingMavenLogAdapter instance = new CommonsLoggingMavenLogAdapter(t_Log);
        instance.info(t_strLogMessage);

        EasyMock.verify(t_Log);
    }
}