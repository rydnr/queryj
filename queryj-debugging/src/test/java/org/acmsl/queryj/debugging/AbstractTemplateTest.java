/*
                        QueryJ Debugging

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
 * Filename: AbstractTemplateTest.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Tests for AbstractTemplate.
 *
 * Date: 2014/06/25
 * Time: 15:29
 *
 */
package org.acmsl.queryj.debugging;

/*
 * Importing QueryJ Core classes.
 */
import org.acmsl.queryj.api.AbstractTemplate;
import org.acmsl.queryj.api.AbstractTemplateGenerator;
import org.acmsl.queryj.api.FillTemplateChain;
import org.acmsl.queryj.api.TemplateContext;
import org.acmsl.queryj.api.exceptions.InvalidPerTableTemplateException;
import org.acmsl.queryj.api.exceptions.InvalidTemplateException;
import org.acmsl.queryj.api.handlers.fillhandlers.FillHandler;
import org.acmsl.queryj.metadata.DecoratorFactory;

/*
 * Importing QueryJ Debugging classes.
 */
import org.acmsl.queryj.debugging.AbstractTemplateGeneratorTest.DoNothingDebuggingService;

/*
 * Importing QueryJ Test classes.
 */
import org.acmsl.queryj.test.TestableAbstractTemplate;

/*
 * Importing ACM S.L. Java Commons classes.
 */
import org.acmsl.commons.utils.io.FileUtils;

/*
 * Importing StringTemplate classes.
 */
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;

/*
 * Importing JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Tests for {@link AbstractTemplate}.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2014/06/25 15:29
 */
@RunWith(JUnit4.class)
public class AbstractTemplateTest
{
    /**
     * Checks generateOutput() calls TemplateDebuggingService.debug() in
     * a debugging session.
     * throws Exception if the test fails.
     */
    @Test
    public void generateOutput_calls_TemplateDebuggingService_when_debugging()
    throws  Exception
    {
        @NotNull final TemplateContext templateContext = EasyMock.createNiceMock(TemplateContext.class);

        EasyMock.expect(templateContext.isDebugEnabled()).andReturn(true);
        EasyMock.expect(templateContext.getFileName()).andReturn("foo.java").anyTimes();
        EasyMock.replay(templateContext);

        @NotNull final MyTestableAbstractTemplate<TemplateContext> template =
            new MyTestableAbstractTemplate<>(templateContext);

        @NotNull final DoNothingDebuggingService<TemplateContext> templateDebuggingService =
            new DoNothingDebuggingService<>();

        @SuppressWarnings("unchecked")
        @NotNull final AbstractTemplateGenerator<MyTestableAbstractTemplate<TemplateContext>, TemplateContext> generator =
            new AbstractTemplateGenerator(false, 1)
            {
                /**
                 * {@inheritDoc}
                 */
                @NotNull
                @Override
                public DecoratorFactory getDecoratorFactory()
                {
                    return EasyMock.createNiceMock(DecoratorFactory.class);
                }

                /**
                 * {@inheritDoc}
                 */
                @Nullable
                @Override
                protected String retrieveHash(
                    @NotNull final String fileName,
                    @NotNull final File outputDir,
                    @NotNull final File rootFolder,
                    @NotNull final Charset charset,
                    @NotNull final FileUtils fileUtils)
                {
                    return "";
                }
            };

        @NotNull final File outputDir = EasyMock.createNiceMock(File.class);
        EasyMock.expect(outputDir.getAbsolutePath()).andReturn("/tmp").anyTimes();
        EasyMock.expect(outputDir.exists()).andReturn(true).anyTimes();
        EasyMock.replay(outputDir);
        @NotNull final File rootFolder = EasyMock.createNiceMock(File.class);
        EasyMock.expect(rootFolder.getAbsolutePath()).andReturn("/tmp").anyTimes();
        EasyMock.expect(rootFolder.exists()).andReturn(true).anyTimes();
        EasyMock.replay(rootFolder);

        generator.write(template, outputDir, rootFolder, Charset.defaultCharset());
//        Assert.assertTrue(templateDebuggingService.called);
    }


    /**
     * Convenience class to test whether STInspectorDebuggingService actual
     * calls to ST.inspect().
     * @param <C> the template context.
     */
    public static class MyTestableAbstractTemplate<C extends TemplateContext>
        extends TestableAbstractTemplate<C>
    {
        /**
         * The serial version id.
         */
        private static final long serialVersionUID = 8071044949637581121L;

        /**
         * An empty array list.
         */
        @NotNull
        public final List<FillTemplateChain<? extends FillHandler<?>>> emptyList = new ArrayList<>(0);

        /**
         * Creates a new instance.
         */
        public MyTestableAbstractTemplate(@NotNull final C templateContext)
        {
            super(templateContext);
        }

        /**
         * The mocked template.
         */
        public ST template;

        /**
         * {@inheritDoc}
         */
        @Nullable
        @Override
        public ST retrieveTemplate(@Nullable final STGroup group)
        {
            this.template = EasyMock.createNiceMock(ST.class);

            EasyMock.expect(template.render()).andReturn("[result]").anyTimes();
            EasyMock.expect(template.inspect()).andReturn(null);
            EasyMock.replay(template);

            return template;
        }

        /**
         * {@inheritDoc}
         */
        @NotNull
        @Override
        public InvalidTemplateException buildInvalidTemplateException(
            @NotNull final TemplateContext context,
            @NotNull final ST template,
            @NotNull final Throwable actualException)
        {
            return
                new InvalidPerTableTemplateException(
                    "name", "tableName", "repository", EasyMock.createNiceMock(Throwable.class));
        }

        /**
         * {@inheritDoc}
         */
        @Nullable
        @Override
        public STGroup retrieveGroup()
        {
            @NotNull final STGroup result = EasyMock.createNiceMock(STGroup.class);

            @NotNull final ST template = EasyMock.createNiceMock(ST.class);

            EasyMock.expect(result.getInstanceOf(TEMPLATE_NAME)).andReturn(template);
            EasyMock.expect(template.add(CONTEXT, emptyList)).andReturn(template);

            EasyMock.replay(result);
            EasyMock.replay(template);

            return result;
        }

        /**
         * {@inheritDoc}
         * @return
         */
        @NotNull
        @Override
        public String getTemplateName()
        {
            return "test";
        }

        /**
         * {@inheritDoc}
         */
        @Override
        @NotNull
        public List<FillTemplateChain<? extends FillHandler<?>>> buildFillTemplateChains(
            @NotNull final TemplateContext context)
        {
            return emptyList;
        }

        /**
         * {@inheritDoc}
         */
        @NotNull
        @Override
        public String toString()
        {
            return
                  "{ \"template\": " + template
                + ", \"emptyList\": " + emptyList
                + ", \"class\": \"MyTestableAbstractTemplate\""
                + ", \"package\": \"org.acmsl.queryj.debugging\" }";
        }
    }

}
