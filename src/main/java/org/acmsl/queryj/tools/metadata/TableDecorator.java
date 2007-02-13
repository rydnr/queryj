//;-*- mode: java -*-
/*
                        QueryJ

    Copyright (C) 2002-2007  Jose San Leandro Armendariz
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
 * Filename: TableDecorator.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Decorates 'Table' instances to provide required
 *              alternate representations of the information stored therein.
 *
 */
package org.acmsl.queryj.tools.metadata;

/*
 * Importing project classes.
 */
import org.acmsl.queryj.tools.SingularPluralFormConverter;
import org.acmsl.queryj.tools.metadata.vo.AbstractTable;
import org.acmsl.queryj.tools.metadata.vo.Table;
import org.acmsl.queryj.tools.metadata.DecorationUtils;

/*
 * Importing some ACM-SL Commons classes.
 */
import org.acmsl.commons.utils.EnglishGrammarUtils;
import org.acmsl.commons.utils.StringUtils;

/*
 * Importing some JDK classes.
 */
import java.util.List;

/**
 * Decorates <code>Table</code> instances to provide required alternate
 * representations of the information stored therein.
 * @author <a href="mailto:chous@acm-sl.org"
 *         >Jose San Leandro</a>
 */
public class TableDecorator
    extends AbstractTable
{
    /**
     * The metadata type manager.
     */
    private MetadataManager m__MetadataManager;

    /**
     * Creates an <code>TableDecorator</code> with the
     * <code>Table</code> to decorate.
     * @param table the table.
     * @param metadataManager the metadata manager.
     * @precondition table != null
     * @precondition metadataManager != null
     */
    public TableDecorator(
        final Table table, final MetadataManager metadataManager)
    {
        this(
            table.getName(),
            table.getAttributes(),
            table.getParentTable(),
            metadataManager);
    }

    /**
     * Creates an <code>TableDecorator</code> with the following
     * information.
     * @param name the name.
     * @param attributes the attributes.
     * @param parentTable the parent table.
     * @param metadataManager the metadata manager.
     * @precondition name != null
     * @precondition metadataManager != null
     */
    public TableDecorator(
        final String name,
        final List attributes,
        final Table parentTable,
        final MetadataManager metadataManager)
    {
        super(name, attributes, parentTable);

        immutableSetMetadataManager(metadataManager);
    }

    /**
     * Specifies the metadata manager.
     * @param metadataManager such instance.
     */
    protected final void immutableSetMetadataManager(
        final MetadataManager metadataManager)
    {
        m__MetadataManager = metadataManager;
    }

    /**
     * Specifies the metadata manager.
     * @param metadataManager such instance.
     */
    protected void setMetadataManager(
        final MetadataManager metadataManager)
    {
        immutableSetMetadataManager(metadataManager);
    }

    /**
     * Retrieves the metadata manager.
     * @return such instance.
     */
    protected MetadataManager getMetadataManager()
    {
        return m__MetadataManager;
    }

    /**
     * Retrieves the name, in upper case.
     * @return such value.
     */
    public String getNameUppercased()
    {
        return upperCase(getName());
    }
    
    /**
     * Retrieves the capitalized name.
     * @return such name.
     */
    public String getNameCapitalized()
    {
        return capitalize(getName());
    }

    /**
     * Capitalizes given value.
     * @param value the value.
     * @return the alternate version of the value.
     * @precondition value != null
     */
    protected String capitalize(final String value)
    {
        return capitalize(value, DecorationUtils.getInstance());
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
        return decorationUtils.capitalize(lowerCase(value));
    }
    
    /**
     * Converts given value to upper-case.
     * @param value the value.
     * @return the alternate version of the value.
     * @precondition value != null
     */
    protected String upperCase(final String value)
    {
        return upperCase(value, DecorationUtils.getInstance());
    }
    
    /**
     * Converts given value to upper-case.
     * @param value the value.
     * @param decorationUtils the <code>DecorationUtils</code> instance.
     * @return the alternate version of the value.
     * @precondition value != null
     * @precondition decorationUtils != null
     */
    protected String upperCase(
        final String value, final DecorationUtils decorationUtils)
    {
        return decorationUtils.upperCase(value);
    }
    
    /**
     * Normalizes given value to lower-case.
     * @param value the value.
     * @return the alternate version of the value.
     * @precondition value != null
     */
    protected String normalizeLowercase(final String value)
    {
        return normalizeLowercase(value, DecorationUtils.getInstance());
    }
    
    /**
     * Normalizes given value to lower-case.
     * @param value the value.
     * @param decorationUtils the <code>DecorationUtils</code> instance.
     * @return the alternate version of the value.
     * @precondition value != null
     * @precondition decorationUtils != null
     */
    protected String normalizeLowercase(
        final String value, final DecorationUtils decorationUtils)
    {
        return decorationUtils.normalizeLowercase(value);
    }
    
    /**
     * Normalizes given value.
     * @param value the value.
     * @return the alternate version of the value.
     * @precondition value != null
     */
    protected String normalize(final String value)
    {
        return normalize(value, DecorationUtils.getInstance());
    }
    
    /**
     * Normalizes given value.
     * @param value the value.
     * @param decorationUtils the <code>DecorationUtils</code> instance.
     * @return the alternate version of the value.
     * @precondition value != null
     * @precondition decorationUtils != null
     */
    protected String normalize(
        final String value, final DecorationUtils decorationUtils)
    {
        return decorationUtils.normalize(value);
    }
    
    /**
     * Retrieves the name, in lower case.
     * @return such value.
     */
    public String getNameLowercased()
    {
        return lowerCase(getName());
    }
    
    /**
     * Converts given value to lower-case.
     * @param value the value.
     * @return the alternate version of the value.
     * @precondition value != null
     */
    protected String lowerCase(final String value)
    {
        return lowerCase(value, DecorationUtils.getInstance());
    }

    /**
     * Converts given value to lower-case.
     * @param value the value.
     * @param decorationUtils the <code>DecorationUtils</code> instance.
     * @return the alternate version of the value.
     * @precondition value != null
     * @precondition decorationUtils != null
     */
    protected String lowerCase(
        final String value, final DecorationUtils decorationUtils)
    {
        return decorationUtils.lowerCase(value);
    }

    /**
     * Retrieves the table name, uncapitalized.
     * @return such value.
     */
    public String getUncapitalizedName()
    {
        return uncapitalize(getName());
    }

    /**
     * Uncapitalizes given value.
     * @param value the value.
     * @return the modified version of the value.
     * @precondition value != null
     */
    protected String uncapitalize(final String value)
    {
        return uncapitalize(value, DecorationUtils.getInstance());
    }

    /**
     * Uncapitalizes given value.
     * @param value the value.
     * @param decorationUtils the <code>DecorationUtils</code> instance.
     * @return the modified version of the value.
     * @precondition value != null
     * @precondition decorationUtils != null
     */
    protected String uncapitalize(
        final String value, final DecorationUtils decorationUtils)
    {
        return decorationUtils.uncapitalize(value);
    }

    /**
     * Retrieves the value-object name associated to the table name.
     * @return such name.
     */
    public String getVoName()
    {
        return getSingularNameCapitalized();
    }

    /**
     * Retrieves the table's name in lower-case, once normalized.
     * @return such information.
     */
    public String getNameNormalizedLowercased()
    {
        return normalizeLowercase(getName());
    }

    /**
     * Retrieves the table's name in lower-case, once normalized.
     * @return such information.
     */
    public String getSingularNameNormalizedLowercased()
    {
        return normalizeLowercase(getSingular(getName()));
    }

    /**
     * Retrieves the table's name in lower-case, once normalized.
     * @return such information.
     */
    public String getNameNormalized()
    {
        return normalize(getName());
    }

    /**
     * Retrieves the table's name once normalized.
     * @return such information.
     */
    public String getSingularNameCapitalized()
    {
        return capitalize(getSingular(lowercase(getName())));
    }

    /**
     * Retrieves the singular of given word.
     * @param word the word.
     * @return the singular.
     * @precondition word != null
     */
    protected String getSingular(final String word)
    {
        return getSingular(word, SingularPluralFormConverter.getInstance());
    }

    /**
     * Retrieves the singular of given word.
     * @param word the word.
     * @param singularPluralFormConverter the <code>SingularPluralFormConverter</code> instance.
     * @return the singular.
     * @precondition word != null
     * @precondition singularPluralFormConverter != null
     */
    protected String getSingular(
        final String word, final EnglishGrammarUtils singularPluralFormConverter)
    {
        return singularPluralFormConverter.getSingular(word);
    }

    /**
     * Converts given value to lower case.
     * @param value the value.
     * @return the lower-cased value.
     * @precondition value != null
     */
    protected String lowercase(final String value)
    {
        return value.toLowerCase();
    }

    /**
     * Retrieves the singular table's name, upper-cased.
     * @return such information.
     */
    public String getSingularNameUppercased()
    {
        return upperCase(getSingular(lowerCase(getName())));
    }

    /**
     * Retrieves the singular table's name, lower-cased.
     * @return such information.
     */
    public String getSingularNameLowercased()
    {
        return lowerCase(getSingularNameUppercased());
    }


    /**
     * Retrieves the parent table.
     * @return such table.
     */
    public Table getParentTable()
    {
        Table result = super.getParentTable();

        if  (result == null)
        {
            result = getParentTable(getName(), getMetadataManager());
            super.setParentTable(result);
        }

        return result;
    }

    /**
     * Retrieves the parent table.
     * @param table the table name.
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @return such table, or <code>null</code> if it doesn't exist.
     * @precondition table != null
     * @precondition metadataManager != null
     */
    protected Table getParentTable(
        final String name, final MetadataManager metadataManager)
    {
        Table result = null;

        String t_strParentTable = metadataManager.getParentTable(name);

        if  (t_strParentTable != null)
        {
            result =
                new LazyTableDecorator(
                    t_strParentTable, metadataManager, getDecoratorFactory());
        }

        return result;
    }

    /**
     * Retrieves the standard decorator instance. Inherit and override for a different
     * one.
     * @return the decorator factory used by default.
     */
    protected DecoratorFactory getDecoratorFactory()
    {
        return CachingDecoratorFactory.getInstance();
    }

    /**
     * Retrieves the non-parent attributes.
     * @return such attributes.
     */
    public List getNonParentAttributes()
    {
        return getNonParentAttributes(getAttributes(), TableDecoratorHelper.getInstance());
    }

    /**
     * Retrieves the non-parent attributes.
     * @param attributes the attributes.
     * @param tableDecoratorHelper the <code>TableDecoratorHelper</code> instance.
     * @return such attributes.
     * @precondition attributes != null
     * @precondition tableDecoratorHelper != null
     */
    protected List getNonParentAttributes(
        final List attributes, final TableDecoratorHelper tableDecoratorHelper)
    {
        List result = attributes;

        Table t_ParentTable = getParentTable();

        if  (t_ParentTable != null)
        {
            result =
                tableDecoratorHelper.removeOverridden(
                    t_ParentTable.getAttributes(), attributes, getMetadataManager());
        }

        return getAttributes();
    }
}
