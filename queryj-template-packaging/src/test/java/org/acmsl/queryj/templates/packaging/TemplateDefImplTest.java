/*
                        QueryJ Template Packaging

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
 * Filename: TemplateDefImplTest.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Tests for TemplateDefImpl.
 *
 * Date: 2014/05/26
 * Time: 07:32
 *
 */
package org.acmsl.queryj.templates.packaging;

/*
 * Importing JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/*
 * Importing JUnit/EasyMock classes.
 */
import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/*
 * Importing JDK classes.
 */
import java.io.File;
import java.util.HashMap;

/**
 * Tests for {@link TemplateDefImpl}.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2014/05/26 07:32
 */
@RunWith(JUnit4.class)
public class TemplateDefImplTest
{
    /**
     * Checks whether getDefName is based on the filename.
     */
    @Test
    public void getDefName_is_based_on_the_filename()
    {
        @NotNull final File file = EasyMock.createNiceMock(File.class);
        EasyMock.expect(file.getAbsolutePath())
            .andReturn(File.separator + "tmp" + File.separator + "templatedefs" + File.separator + "name.stg.def")
            .anyTimes();

        EasyMock.replay(file);

        @NotNull final TemplateDef<String> instance =
            new TemplateDefImpl(
                "name2.stg",
                TemplateDefType.PER_CUSTOM_RESULT,
                TemplateDefOutput.JAVA,
                "<result.id.normalized>Sample.java",
                "com.foo.bar",
                file,
                new HashMap<String, String>(0),
                false,
                false);

        Assert.assertEquals("name", instance.getDefName());

        EasyMock.verify(file);
    }
}
