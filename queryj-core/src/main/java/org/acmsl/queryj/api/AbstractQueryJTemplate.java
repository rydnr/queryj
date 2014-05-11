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
 * Filename: AbstractTemplate.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Represents generic templates.
 *
 */
package org.acmsl.queryj.api;

/*
 * Importing QueryJ Core classes.
 */
import org.acmsl.queryj.Literals;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing some JDK classes.
 */
import java.io.Serializable;
import java.util.Map;

/**
 * Represents generic templates.
 * @param <C> the template context.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 * @since 2.0
 */
public abstract class AbstractQueryJTemplate<C extends QueryJTemplateContext>
    extends AbstractTemplate<C>
    implements  QueryJSTTemplate<C>,
                DefaultThemeConstants,
                Serializable
{
    /**
     * The serial version id.
     */
    private static final long serialVersionUID = -8374041537939183395L;

    /**
     * The cached processed header.
     */
    private String m__strCachedProcessedHeader;

    /**
     * Builds a {@link AbstractQueryJTemplate} with given context.
     * @param context the context.
     */
    protected AbstractQueryJTemplate(@NotNull final C context)
    {
        super(context);
    }

    /**
     * Specifies the cached processed header.
     * @param header such value.
     */
    protected final void immutableSetCachedProcessedHeader(@Nullable final String header)
    {
        m__strCachedProcessedHeader = header;
    }
    
    /**
     * Specifies the cached processed header.
     * @param header such value.
     */
    @SuppressWarnings("unused")
    protected void setCachedProcessedHeader(@Nullable final String header)
    {
        immutableSetCachedProcessedHeader(header);
    }
    
    /**
     * Retrieves the cached processed header.
     * @return such value.
     */
    @Nullable
    protected String getCachedProcessedHeader()
    {
        return m__strCachedProcessedHeader;
    }

    /**
     * Retrieves the processed header.
     * @param input the input.
     * @return such information.
     */
    @Nullable
    @SuppressWarnings("unused")
    public String getProcessedHeader(@NotNull final Map<String, String> input)
    {
        @Nullable final String result = getCachedProcessedHeader();
        
        if  (result == null)
        {
//            result = processHeader(input, getHeader(getTemplateContext()));
        }
        
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @NotNull
    public String toString()
    {
        return
              "{ \"class\": \"" + AbstractQueryJTemplate.class.getSimpleName() + '"'
            + ", \"cachedProcessedHeader\": \"" + m__strCachedProcessedHeader + '"'
            + Literals.JSON_PARENT_ATTR + super.toString()
            + ", \"package\": \"org.acmsl.queryj.api\" }";
    }
}
