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
 * Filename: TemplateDefTypeTest.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Tests for TemplateDefType.
 *
 * Date: 2014/04/15
 * Time: 07:33
 *
 */
package org.acmsl.queryj.templates.packaging;

/*
 * Importing JUnit classes.
 */
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * Tests for {@link TemplateDefType}.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2014/04/15 07:33
 */
@RunWith(JUnit4.class)
public class TemplateDefTypeTest
{
    /**
     * Tests isPerTable() for all types.
     */
    @Test
    public void isPerTable_is_correct_for_all_types()
    {
        Assert.assertTrue(TemplateDefType.PER_TABLE.isPerTable());
        Assert.assertFalse(TemplateDefType.PER_REPOSITORY.isPerTable());
        Assert.assertFalse(TemplateDefType.PER_CUSTOM_SQL.isPerTable());
        Assert.assertFalse(TemplateDefType.PER_FOREIGN_KEY.isPerTable());
        Assert.assertFalse(TemplateDefType.PER_CUSTOM_RESULT.isPerTable());
    }

    /**
     * Tests isPerRepository() for all types.
     */
    @Test
    public void isPerRepository_is_correct_for_all_types()
    {
        Assert.assertFalse(TemplateDefType.PER_TABLE.isPerRepository());
        Assert.assertTrue(TemplateDefType.PER_REPOSITORY.isPerRepository());
        Assert.assertFalse(TemplateDefType.PER_CUSTOM_SQL.isPerRepository());
        Assert.assertFalse(TemplateDefType.PER_FOREIGN_KEY.isPerRepository());
        Assert.assertFalse(TemplateDefType.PER_CUSTOM_RESULT.isPerRepository());
    }

    /**
     * Tests isPerSql() for all types.
     */
    @Test
    public void isPerSql_is_correct_for_all_types()
    {
        Assert.assertFalse(TemplateDefType.PER_TABLE.isPerSql());
        Assert.assertFalse(TemplateDefType.PER_REPOSITORY.isPerSql());
        Assert.assertTrue(TemplateDefType.PER_CUSTOM_SQL.isPerSql());
        Assert.assertFalse(TemplateDefType.PER_FOREIGN_KEY.isPerSql());
        Assert.assertFalse(TemplateDefType.PER_CUSTOM_RESULT.isPerSql());
    }

    /**
     * Tests isPerForeignKey() for all types.
     */
    @Test
    public void isPerForeignKey_is_correct_for_all_types()
    {
        Assert.assertFalse(TemplateDefType.PER_TABLE.isPerForeignKey());
        Assert.assertFalse(TemplateDefType.PER_REPOSITORY.isPerForeignKey());
        Assert.assertFalse(TemplateDefType.PER_CUSTOM_SQL.isPerForeignKey());
        Assert.assertTrue(TemplateDefType.PER_FOREIGN_KEY.isPerForeignKey());
        Assert.assertFalse(TemplateDefType.PER_CUSTOM_RESULT.isPerForeignKey());
    }

    /**
     * Tests isPerCustomResult() for all types.
     */
    @Test
    public void isPerCustomResult_is_correct_for_all_types()
    {
        Assert.assertFalse(TemplateDefType.PER_TABLE.isPerCustomResult());
        Assert.assertFalse(TemplateDefType.PER_REPOSITORY.isPerCustomResult());
        Assert.assertFalse(TemplateDefType.PER_CUSTOM_SQL.isPerCustomResult());
        Assert.assertFalse(TemplateDefType.PER_FOREIGN_KEY.isPerCustomResult());
        Assert.assertTrue(TemplateDefType.PER_CUSTOM_RESULT.isPerCustomResult());
    }
}
