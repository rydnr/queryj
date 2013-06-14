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
 * Filename: DefaultTemplateChainProvider.java
 *
 * Author: Jose San Leandro Armendariz (chous)
 *
 * Description: 
 *
 * Date: 6/4/13
 * Time: 4:40 PM
 *
 */
package org.acmsl.queryj.templates;

/*
 * Importing project classes.
 */
import org.acmsl.queryj.api.handlers.fillhandlers.FillHandler;
import org.acmsl.queryj.templates.dao.handlers.AttributesStatementSetterTemplateHandlerBundle;
import org.acmsl.queryj.templates.dao.handlers.BaseAbstractDAOTemplateHandlerBundle;
import org.acmsl.queryj.templates.dao.handlers.BaseDAOFactoryTemplateHandlerBundle;
import org.acmsl.queryj.templates.dao.handlers.BaseDAOTemplateHandlerBundle;
import org.acmsl.queryj.templates.dao.handlers.BasePreparedStatementCreatorTemplateHandlerBundle;
import org.acmsl.queryj.templates.dao.handlers.BaseRepositoryDAOFactoryTemplateHandlerBundle;
import org.acmsl.queryj.templates.dao.handlers.BaseRepositoryDAOTemplateHandlerBundle;
import org.acmsl.queryj.templates.dao.handlers.BaseResultSetExtractorTemplateHandlerBundle;
import org.acmsl.queryj.templates.dao.handlers.ConfigurationPropertiesTemplateHandlerBundle;
import org.acmsl.queryj.templates.dao.handlers.CustomResultSetExtractorTemplateHandlerBundle;
import org.acmsl.queryj.templates.dao.handlers.DAOChooserTemplateHandlerBundle;
import org.acmsl.queryj.templates.dao.handlers.DAOFactoryTemplateHandlerBundle;
import org.acmsl.queryj.templates.dao.handlers.DAOListenerImplTemplateHandlerBundle;
import org.acmsl.queryj.templates.dao.handlers.DAOListenerTemplateHandlerBundle;
import org.acmsl.queryj.templates.dao.handlers.DAOTemplateHandlerBundle;
import org.acmsl.queryj.templates.dao.handlers.DataAccessContextLocalTemplateHandlerBundle;
import org.acmsl.queryj.templates.dao.handlers.DataAccessManagerTemplateHandlerBundle;
import org.acmsl.queryj.templates.dao.handlers.FkStatementSetterTemplateHandlerBundle;
import org.acmsl.queryj.templates.dao.handlers.JdbcTemplateTemplateHandlerBundle;
import org.acmsl.queryj.templates.dao.handlers.JndiUtilsTemplateHandlerBundle;
import org.acmsl.queryj.templates.dao.handlers.PkStatementSetterTemplateHandlerBundle;
import org.acmsl.queryj.templates.dao.handlers.RepositoryDAOFactoryTemplateHandlerBundle;
import org.acmsl.queryj.templates.dao.handlers.RepositoryDAOTemplateHandlerBundle;
import org.acmsl.queryj.templates.dao.handlers.ResultSetExtractorTemplateHandlerBundle;
import org.acmsl.queryj.templates.dao.handlers.StatisticsProviderTemplateHandlerBundle;
import org.acmsl.queryj.templates.dao.handlers.ThreadLocalBagTemplateHandlerBundle;
import org.acmsl.queryj.templates.other.handlers.CucumberFeatureTemplateHandlerBundle;
import org.acmsl.queryj.templates.valueobject.handlers.BaseValueObjectTemplateHandlerBundle;
import org.acmsl.queryj.templates.valueobject.handlers.CustomBaseValueObjectTemplateHandlerBundle;
import org.acmsl.queryj.templates.valueobject.handlers.CustomValueObjectFactoryTemplateHandlerBundle;
import org.acmsl.queryj.templates.valueobject.handlers.CustomValueObjectImplTemplateHandlerBundle;
import org.acmsl.queryj.templates.valueobject.handlers.CustomValueObjectTemplateHandlerBundle;
import org.acmsl.queryj.templates.valueobject.handlers.ValueObjectFactoryTemplateHandlerBundle;
import org.acmsl.queryj.templates.valueobject.handlers.ValueObjectImplTemplateHandlerBundle;
import org.acmsl.queryj.templates.valueobject.handlers.ValueObjectTemplateHandlerBundle;

/*
 * Importing queryj-core classes.
 */
import org.acmsl.queryj.QueryJCommand;
import org.acmsl.queryj.api.handlers.TemplateHandler;
import org.acmsl.queryj.tools.TemplateChainProvider;

/*
 * Importing queryj-placeholders classes.
 */

/*
 * Importing JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/*
 * Importing JDK classes.
 */
import java.util.ArrayList;
import java.util.List;

/**
 * Implements {@link TemplateChainProvider} to generate
 * the default templates.
 * @author <a href="mailto:chous@acm-sl.org>Jose San Leandro</a>
 * @since 2013/06/04
 */
@SuppressWarnings("unused")
public class DefaultTemplateChainProvider
    implements TemplateChainProvider<TemplateHandler<QueryJCommand>>
{
    /**
     * Retrieves the custom chain.
     * @return such chain.
     */
    @NotNull
    @Override
    public List<TemplateHandler<QueryJCommand>> getHandlers()
    {
        @NotNull
        final List<TemplateHandler<QueryJCommand>> result = new ArrayList<TemplateHandler<QueryJCommand>>();

        result.add(new AttributesStatementSetterTemplateHandlerBundle());
        result.add(new BaseAbstractDAOTemplateHandlerBundle());
        result.add(new BaseDAOFactoryTemplateHandlerBundle());
        result.add(new BaseDAOTemplateHandlerBundle());
        result.add(new BasePreparedStatementCreatorTemplateHandlerBundle());
        result.add(new BaseRepositoryDAOFactoryTemplateHandlerBundle());
        result.add(new BaseRepositoryDAOTemplateHandlerBundle());
        result.add(new BaseResultSetExtractorTemplateHandlerBundle());
        result.add(new BaseValueObjectTemplateHandlerBundle());
        result.add(new ConfigurationPropertiesTemplateHandlerBundle());
        result.add(new CucumberFeatureTemplateHandlerBundle());
        result.add(new CustomBaseValueObjectTemplateHandlerBundle());
        result.add(new CustomResultSetExtractorTemplateHandlerBundle());
        result.add(new CustomValueObjectFactoryTemplateHandlerBundle());
        result.add(new CustomValueObjectImplTemplateHandlerBundle());
        result.add(new CustomValueObjectTemplateHandlerBundle());
        result.add(new DAOChooserTemplateHandlerBundle());
        result.add(new DAOFactoryTemplateHandlerBundle());
        result.add(new DAOListenerImplTemplateHandlerBundle());
        result.add(new DAOListenerTemplateHandlerBundle());
        result.add(new DAOTemplateHandlerBundle());
        result.add(new DataAccessContextLocalTemplateHandlerBundle());
        result.add(new DataAccessManagerTemplateHandlerBundle());
        result.add(new FkStatementSetterTemplateHandlerBundle());
        result.add(new JdbcTemplateTemplateHandlerBundle());
        result.add(new JndiUtilsTemplateHandlerBundle());
        result.add(new PkStatementSetterTemplateHandlerBundle());
        result.add(new RepositoryDAOFactoryTemplateHandlerBundle());
        result.add(new RepositoryDAOTemplateHandlerBundle());
        result.add(new ResultSetExtractorTemplateHandlerBundle());
        result.add(new StatisticsProviderTemplateHandlerBundle());
        result.add(new ThreadLocalBagTemplateHandlerBundle());
        result.add(new ValueObjectFactoryTemplateHandlerBundle());
        result.add(new ValueObjectImplTemplateHandlerBundle());
        result.add(new ValueObjectTemplateHandlerBundle());

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    public List<FillHandler> getFillHandlers()
    {
        @NotNull
        final List<FillHandler> result = new ArrayList<FillHandler>();

//        result.add(new CopyrightYearsHandler());

        return result;
    }
}
