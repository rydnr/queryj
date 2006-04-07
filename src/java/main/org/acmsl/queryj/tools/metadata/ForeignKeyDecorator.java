//;-*- mode: java -*-
/*
                        QueryJ

    Copyright (C) 2002-2005  Jose San Leandro Armendariz
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
    Postal Address: c/Playa de Lagoa, 1
                    Urb. Valdecabanas
                    Boadilla del monte
                    28660 Madrid
                    Spain

 *****************************************************************************
 *
 * Filename: $RCSfile$
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Decorates 'ForeignKey' instances to provide required
 *              alternate representations of the information stored therein.
 *
 */
package org.acmsl.queryj.tools.metadata;

/*
 * Importing project classes.
 */
import org.acmsl.queryj.tools.metadata.vo.AbstractForeignKey;
import org.acmsl.queryj.tools.metadata.vo.ForeignKey;
import org.acmsl.queryj.tools.metadata.DecorationUtils;

/*
 * Importing some ACM-SL Commons classes.
 */
import org.acmsl.commons.utils.EnglishGrammarUtils;

/*
 * Importing some JDK classes.
 */
import java.util.Collection;

/**
 * Decorates <code>ForeignKey</code> instances to provide required alternate
 * representations of the information stored therein.
 * @author <a href="mailto:chous@acm-sl.org"
 *         >Jose San Leandro</a>
 */
public class ForeignKeyDecorator
    extends AbstractForeignKey
{
    /**
     * Creates a <code>ForeignKeyDecorator</code> with the
     * <code>ForeignKey</code> information to decorate.
     * @param foreignKey the foreign key.
     * @precondition foreignKey != null
     */
    public ForeignKeyDecorator(final ForeignKey foreignKey)
    {
        this(
            foreignKey.getSourceTableName(),
            foreignKey.getAttributes(),
            foreignKey.getTargetTableName(),
            foreignKey.getAllowsNull());
    }

    /**
     * Creates a <code>ForeignKeyDecorator</code> with the following
     * information.
     * @param sourceTableName the source table name.
     * @param attributes the attributes.
     * @param targetTableName the target table name.
     * @param allowsNull whether the foreign key allows null values.
     * @precondition sourceTableName != null
     * @precondition attributes != null
     * @precondition targetTableName != null
     */
    public ForeignKeyDecorator(
        final String sourceTableName,
        final Collection attributes,
        final String targetTableName,
        final boolean allowsNull)
    {
        super(sourceTableName, attributes, targetTableName, allowsNull);
    }

    /**
     * Retrieves the source table name, uncapitalized.
     * @return such value.
     */
    public String getSourceTableNameUncapitalized()
    {
        return
            uncapitalize(
                getSourceTableName(), DecorationUtils.getInstance());
    }

    /**
     * Retrieves the source value-object name.
     * @return such value.
     */
    public String getSourceVoName()
    {
        return
            toVo(
                getSourceTableName(),
                EnglishGrammarUtils.getInstance(),
                DecorationUtils.getInstance());
    }

    /**
     * Retrieves the target value-object name.
     * @return such value.
     */
    public String getTargetVoName()
    {
        return
            toVo(
                getTargetTableName(),
                EnglishGrammarUtils.getInstance(),
                DecorationUtils.getInstance());
    }

    /**
     * Converts given table name to its value-object version.
     * @param tableName the table name.
     * @param englishGrammarUtils the <code>EnglishGrammarUtils</code>
     * instance.
     * @param decorationUtils the <code>DecorationUtils</code> instance.
     * @return the value-object name.
     * @precondition tableName != null
     * @precondition englishGrammarUtils != null
     * @precondition decorationUtils != null
     */
    protected String toVo(
        final String tableName,
        final EnglishGrammarUtils englishGrammarUtils,
        final DecorationUtils decorationUtils)
    {
        return
            capitalize(
                englishGrammarUtils.getSingular(tableName.toLowerCase()),
                decorationUtils);
    }

    /**
     * Uncapitalizes given value.
     * @param value the value.
     * @param decorationUtils the <code>DecorationUtils</code> instance.
     * @return the alternate version of the value.
     * @precondition value != null
     * @precondition decorationUtils != null
     */
    protected String uncapitalize(
        final String value, final DecorationUtils decorationUtils)
    {
        return decorationUtils.uncapitalize(value.toLowerCase());
    }

    /**
     * Capitalizes given value.
     * @param value the value.
     * @param decorationUtils the <code>DecorationUtils</code> instance.
     * @return the alternate version of the value.
     * @precondition value != null
     * @precondition decorationUtils != null
     */
    protected String capitalize(
        final String value, final DecorationUtils decorationUtils)
    {
        return decorationUtils.capitalize(value);
    }
}
