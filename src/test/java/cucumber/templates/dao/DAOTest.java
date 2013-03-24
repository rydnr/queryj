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
 * Filename: DAOTest.java
 *
 * Author: Jose San Leandro Armendariz (chous)
 *
 * Description: Cucumber test for DAO.feature
 *
 * Date: 3/23/13
 * Time: 10:20 AM
 *
 */
package cucumber.templates.dao;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.DataTable;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;

import org.acmsl.queryj.metadata.engines.JdbcMetadataTypeManager;
import org.acmsl.queryj.metadata.vo.Attribute;
import org.acmsl.queryj.metadata.vo.AttributeValueObject;
import org.acmsl.queryj.metadata.vo.ForeignKey;
import org.acmsl.queryj.metadata.vo.Table;
import org.acmsl.queryj.metadata.vo.TableValueObject;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DAOTest
{
    private List<Table> m__lTables;

    public DAOTest()
    {
        immutableSetTables(new ArrayList<Table>());
    }

    /**
     * Specifies the tables.
     * @param tables the tables.
     */
    protected final void immutableSetTables(@NotNull final List<Table> tables)
    {
        m__lTables = tables;
    }


    /**
     * Specifies the tables.
     * @param tables the tables.
     */
    @SuppressWarnings("unused")
    protected void setTables(@NotNull final List<Table> tables)
    {
        immutableSetTables(tables);
    }

    /**
     * Retrieves the tables.
     * @return such information.
     */
    @NotNull
    protected List<Table> getTables()
    {
        return m__lTables;
    }

    /**
     * Defines the input tables based on the information provided by the
     * feature.
     * @param tableInfo the information about the tables.
     */
    @Given("the following tables:")
    public void defineInputTables(@NotNull final DataTable tableInfo)
    {
        @NotNull final List<Map<String, String>> tableEntries = tableInfo.asMaps();

        @NotNull final List<Table> tables = getTables();

        for (@NotNull final Map<String, String> tableEntry: tableEntries)
        {
            tables.add(convertToTable(tableEntry));
        }
    }

    @And("the following columns:")
    public void defineInputColumns(@NotNull final DataTable columnInfo)
    {
        for (@NotNull final Table table : getTables())
        {
            List<Attribute> attributes = table.getAttributes();

            Attribute attribute;

            int index = 1;

            String length;
            String precision;
            int precisionValue;
            String booleanInfo;
            String booleanTrue;
            String booleanFalse;
            String booleanNull;

            for (@NotNull final Map<String, String> columnEntry: columnInfo.asMaps())
            {
                length = columnEntry.get("length");
                precision = columnEntry.get("precision");
                precisionValue = 0;

                booleanInfo = columnEntry.get("boolean");
                booleanTrue = null;
                booleanFalse = null;
                booleanNull = null;

                if (booleanInfo != null)
                {
                    String[] fields = booleanInfo.split(",");
                    booleanTrue = fields[0];

                    if (fields.length > 1)
                    {
                        booleanFalse = fields[1];
                    }

                    if (fields.length > 2)
                    {
                        booleanNull = fields[2];
                    }
                }

                if (precision != null)
                {
                    precisionValue = Integer.parseInt(precision);
                }
                attribute =
                    new AttributeValueObject(
                        columnEntry.get("column"),
                        retrieveAttributeTypeId(columnEntry.get("type"), precisionValue),
                        columnEntry.get("type"),
                        columnEntry.get("table"),
                        "test comment",
                        index++,
                        Integer.parseInt(length),
                        Integer.parseInt(precision),
                        columnEntry.get("keyword"),
                        columnEntry.get("query"),
                        Boolean.valueOf(columnEntry.get("allows null")),
                        columnEntry.get("value"),
                        Boolean.valueOf(columnEntry.get("readonly")),
                        booleanInfo != null,
                        booleanTrue,
                        booleanFalse,
                        booleanNull);

                attributes.add(attribute);
            }
        }
    }

    /**
     * Defines the foreign keys via cucumber features.
     * @param fkInfo such information.
     */
    @SuppressWarnings("unused")
    @And("the following foreign keys:")
    public void defineForeignKeys(@NotNull final DataTable fkInfo)
    {
        // TODO
        String a;
    }

    /**
     * Generates a file with the information from the feature.
     * @param template the template.
     */
    @SuppressWarnings("unused")
    @When("I generate with (.*)\\.stg")
    public void generateFile(@NotNull final String template)
    {
        // TODO
        String a;
    }

    @SuppressWarnings("unused")
    @Then("the generated (.*) compiles successfully")
    public void checkGeneratedFileCompiles(@NotNull final String file)
    {
        // TODO
        String a;
        Assert.assertEquals("The name of the generated file does not match", file, "");
    }

    /**
     * Retrieves the id of the attribute type.
     * @param type the type.
     * @param precision the precision.
     * @return the concrete type in {@link java.sql.Types}.
     */
    protected int retrieveAttributeTypeId(@NotNull final String type, final int precision)
    {
        return new JdbcMetadataTypeManager().getJavaType(type, precision);
    }

    /**
     * Converts given table information to a {@link Table}.
     * @param tableEntry the table information.
     * @return the {@link Table} instance.
     */
    @NotNull
    protected Table convertToTable(@NotNull final Map<String, String> tableEntry)
    {
        Table result;

        result =
            new TableValueObject(
                tableEntry.get("table"),
                tableEntry.get("comment"),
                new ArrayList<Attribute>(),
                new ArrayList<Attribute>(),
                new ArrayList<ForeignKey>(),
                null, //tableEntry.get("parent table"),
                tableEntry.get("static") != null,
                tableEntry.get("decorated") != null);

        return result;
    }
}
