/*
                        QueryJ Placeholders

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
 * Filename: CustomResultsHandler.java
 *
 * Author: Jose San Leandro Armendariz (chous)
 *
 * Description: Resolves "custom_results" placeholders in
 *              BasePerTableTemplates.
 *
 * Date: 5/31/12
 * Time: 1:55 AM
 *
 */
package org.acmsl.queryj.placeholders;

/*
 * Importing QueryJ Core classes.
 */
import org.acmsl.queryj.customsql.CustomSqlProvider;
import org.acmsl.queryj.customsql.Result;
import org.acmsl.queryj.metadata.DecoratorFactory;
import org.acmsl.queryj.metadata.MetadataManager;
import org.acmsl.queryj.api.PerTableTemplateContext;
import org.acmsl.queryj.api.TemplateUtils;
import org.acmsl.queryj.api.dao.DAOTemplateUtils;
import org.acmsl.queryj.api.exceptions.QueryJBuildException;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/*
 * Importing some JDK classes.
 */
import java.util.List;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * Resolves "custom_results" placeholders in
 * {@link org.acmsl.queryj.api.PerTableTemplate BasePerTableTemplates}.
 * @author <a href="mailto:chous@acm-sl.org">chous</a>
 * @since 3.0
 * Created: 2012/05/31
 */
@ThreadSafe
public class CustomResultsHandler
    extends AbstractTemplateContextFillHandler<PerTableTemplateContext, List<Result<String>>>
{
    /**
     * The serial version id.
     */
    private static final long serialVersionUID = 6281653014442667192L;

    /**
     * Creates a new {@code CustomResultsHandler} to resolve placeholders with information
     * in given {@link org.acmsl.queryj.api.PerTableTemplateContext context}.
     * @param context the context.
     */
    public CustomResultsHandler(@NotNull final PerTableTemplateContext context)
    {
        super(context);
    }

    /**
     * Retrieves the {@link List} of {@link Result}s for given {@link org.acmsl.queryj.api.PerTableTemplateContext context}.
     * @param context the context.
     * @return such value.
     * model.
     */
    @NotNull
    @Override
    protected List<Result<String>> getValue(@NotNull final PerTableTemplateContext context)
        throws QueryJBuildException
    {
        return
            retrieveCustomResults(
                context.getTableName(),
                context.getCustomSqlProvider(),
                context.getMetadataManager(),
                context.getDecoratorFactory(),
                DAOTemplateUtils.getInstance(),
                TemplateUtils.getInstance());
    }

    /**
     * Returns "custom_results".
     * @return such placeholder.
     */
    @NotNull
    @Override
    public String getPlaceHolder()
    {
        return "custom_results";
    }

    /**
     * Retrieves the custom results.
     * @param tableName the table name.
     * @param customSqlProvider the provider.
     * @param metadataManager the database metadata manager.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @param daoTemplateUtils the <code>DAOTemplateUtils</code> instance.
     * @param templateUtils the <code>TemplateUtils</code> instance.
     * @return the custom results.
     * @throws QueryJBuildException if {@code TemplateUtils#retrieveCustomResults()} throws it.
     */
    @NotNull
    protected List<Result<String>> retrieveCustomResults(
        @NotNull final String tableName,
        @NotNull final CustomSqlProvider customSqlProvider,
        @NotNull final MetadataManager metadataManager,
        @NotNull final DecoratorFactory decoratorFactory,
        @NotNull final DAOTemplateUtils daoTemplateUtils,
        @NotNull final TemplateUtils templateUtils)
      throws QueryJBuildException
    {
        return
            templateUtils.retrieveCustomResults(
                tableName,
                customSqlProvider,
                metadataManager,
                decoratorFactory,
                daoTemplateUtils);
    }
}
