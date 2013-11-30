/*
                        QueryJ

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
 * Filename: FillTemplateChainWrapper.java
 *
 * Author: Jose San Leandro Armendariz (chous)
 *
 * Description: 
 *
 * Date: 6/11/13
 * Time: 6:31 PM
 *
 */
package org.acmsl.queryj.placeholders;

/*
 * Importing QueryJ-core classes.
 */
import org.acmsl.queryj.api.FillTemplateChain;
import org.acmsl.queryj.api.QueryJTemplateContext;
import org.acmsl.queryj.api.handlers.fillhandlers.FillHandler;
import org.acmsl.queryj.api.placeholders.AbstractFillTemplateChainWrapper;

/*
 * Importing JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.NotThreadSafe;

/*
 * Importing JDK classes.
 */
import java.util.ArrayList;
import java.util.List;

/**
 * Wraps a given chain to add generic, stateless placeholders.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2013/06/11
 */
@NotThreadSafe
public class FillTemplateChainWrapper<C extends QueryJTemplateContext>
    extends AbstractFillTemplateChainWrapper<C>
{
    /**
     * Creates a new wrapper for given chain.
     * @param chain the chain to wrap, in order to provide stateless placeholder
     * providers as well.
     */
    public FillTemplateChainWrapper(@NotNull final FillTemplateChain<C> chain)
    {
        super(chain);
    }

    /**
     * Retrieves the list of generic placeholder handlers.
     * @return such list.
     */
    @Override
    @NotNull
    @SuppressWarnings("unchecked")
    protected List<FillHandler<?>> getHandlers(@NotNull final C context)
    {
        @NotNull final List<FillHandler<?>> result = new ArrayList<FillHandler<?>>(22);

        result.add(new AreTimestampsAllowedHandler(context));
        result.add(new ClassNameHandler<C>(context));
        result.add(new CopyrightYearsHandler());
        result.add(new CurrentYearHandler());
        result.add(new DAOChooserPropertiesFileNameHandler(context));
        result.add(new DAOSubpackageNameHandler(context));
        result.add(new DatabaseEngineNameHandler(context));
        result.add(new DatabaseEngineVersionHandler(context));
        result.add(new FileNameHandler(context));
        result.add(new HeaderHandler(context));
        result.add(new IsRepositoryDAOHandler(context));
        result.add(new JndiLocationFillHandler(context));
        result.add(new LobHandlingFlavorHandler(context));
        result.add(new LobHandlingRepositoryCheckHandler(context));
        result.add(new PackageNameHandler(context));
        result.add(new ProjectPackageHandler(context));
        result.add(new RepositoryNameHandler(context));
        result.add(new SerialVersionUIDHandler(context));
        result.add(new TimestampHandler());
        result.add(new TemplateNameHandler(context));
        result.add(new UseCheckthreadAnnotationsHandler(context));
        result.add(new UseNotNullAnnotationsHandler(context));

        return result;
    }

    @NotNull
    @Override
    public String toString()
    {
        return
              "{ \"class\": \"" + FillTemplateChainWrapper.class.getName() + "\""
            + ", \"parent\": \"" + super.toString() +  "\" }";
    }
}
