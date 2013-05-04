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
 * Filename: BaseDAOTest.java
 *
 * Author: Jose San Leandro Armendariz (chous)
 *
 * Description: Cucumber test for DAO.feature
 *
 * Date: 2013/05/04
 * Time: 10:06 AM
 *
 */
package cucumber.templates.dao;

/*
 * Importing Cucumber classes.
 */
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.DataTable;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

/*
 * Importing QueryJ classes.
 */
import org.acmsl.queryj.metadata.DecoratorFactory;
import org.acmsl.queryj.templates.BasePerTableTemplateContext;
import org.acmsl.queryj.templates.BasePerTableTemplateGenerator;
import org.acmsl.queryj.templates.dao.BaseDAOTemplate;
import org.acmsl.queryj.templates.dao.BaseDAOTemplateGenerator;

/*
 * Importing JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/*
 * Importing JUnit classes.
 */
//import org.junit.Assert;

/**
 * Cucumber test for BaseDAO.feature.
 * @author <a href="queryj@acm-sl.org">Jose San Leandro</a>
 * @since 03/2013
 */
public class BaseDAOTest
    extends AbstractDAOTest<BaseDAOTemplate>
{
    /**
     * Creates a new test instance.
     */
    public BaseDAOTest()
    {
    }

    /**
     * Retrieves the template generator from given template name.
     * @return such generator.
     */
    @Override
    protected BasePerTableTemplateGenerator<BaseDAOTemplate, BasePerTableTemplateContext> retrieveTemplateGenerator()
    {
        return new BaseDAOTemplateGenerator(false, 1);
    }

    /**
     * Retrieves the template from given template name.
     * @param context the {@link BasePerTableTemplateContext} instance.
     * @return such template.
     */
    @Override
    protected BaseDAOTemplate retrieveTemplate(@NotNull final BasePerTableTemplateContext context)
    {
        return new BaseDAOTemplate(context);
    }

    /**
     * Retrieves the {@link org.acmsl.queryj.metadata.DecoratorFactory} instance using given generator.
     * @param generator the generator to use.
     * @return the decorator factory.
     */
    @Override
    protected DecoratorFactory retrieveDecoratorFactory(
        BasePerTableTemplateGenerator<BaseDAOTemplate, BasePerTableTemplateContext> generator)
    {
        // TODO: Fix this.
        return ((BaseDAOTemplateGenerator) generator).getDecoratorFactory();
    }

    /**
     * Generates a file with the information from the feature.
     * @param engine the engine.
     */
    @SuppressWarnings("unused")
    @Override
    @When("I generate with BaseDAO.stg for (.*)")
    public void generateFile(@NotNull final String engine)
    {
        generateFile(engine, getTables(), getOutputFiles());
    }
}
