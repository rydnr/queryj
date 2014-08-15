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
 * Filename: TestableAbstractTemplate.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: A testable version of AbstractTemplate.
 *
 * Date: 2014/06/25
 * Time: 16:31
 *
 */
package org.acmsl.queryj.test;

/*
 * Importing QueryJ Core classes.
 */
import org.acmsl.queryj.api.AbstractTemplate;
import org.acmsl.queryj.api.FillTemplateChain;
import org.acmsl.queryj.api.TemplateContext;
import org.acmsl.queryj.api.exceptions.InvalidPerTableTemplateException;
import org.acmsl.queryj.api.exceptions.InvalidTemplateException;
import org.acmsl.queryj.api.handlers.fillhandlers.FillHandler;

/*
 * Importing StringTemplate classes.
 */
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;

/*
 * Importing EasyMock classes.
 */
import org.easymock.EasyMock;

/*
 * Importing JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/*
 * Importing JDK classes.
 */
import java.util.ArrayList;
import java.util.List;

/**
 * A testable version of {@link AbstractTemplate}.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2014/06/25 16:31
 */
@SuppressWarnings("unused")
@ThreadSafe
public class TestableAbstractTemplate<C extends TemplateContext>
    extends AbstractTemplate<C>
{
    /**
     * The serial version id.
     */
    private static final long serialVersionUID = 5087244130368075606L;

    /**
     * An empty array list.
     */
    @NotNull
    public final List<FillTemplateChain<? extends FillHandler<?>>> emptyList = new ArrayList<>(0);

    /**
     * Creates a new instance.
     * @param templateContext the template context.
     */
    public TestableAbstractTemplate(@NotNull final C templateContext)
    {
        super(templateContext, "org.acmsl.queryj.placeholders", true);
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
                "name", "tableName", "repository", new RuntimeException("test"));
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
              "{ \"emptyList\": " + emptyList
            + ", \"class\": \"TestableAbstractTemplate\""
            + ", \"package\": \"org.acmsl.queryj.test\" }";
    }
}
