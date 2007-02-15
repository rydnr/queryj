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
 * Filename: AbstractTableDecorator.java
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
import java.util.ArrayList;
import java.util.List;

/**
 * Decorates <code>Table</code> instances to provide required alternate
 * representations of the information stored therein.
 * @author <a href="mailto:chous@acm-sl.org"
 *         >Jose San Leandro</a>
 */
public abstract class AbstractTableDecorator
    extends     AbstractTable
    implements  TableDecorator
{
    /**
     * The metadata type manager.
     */
    private MetadataManager m__MetadataManager;

    /**
     * The decorator factory.
     */
    private DecoratorFactory m__DecoratorFactory;
    
    /**
     * The child's attributes.
     */
    private List m__lChildAttributes;

    /**
     * A flag indicating whether the attributes have been cleaned up.
     */
    private boolean m__bAttributesCleanedUp = false;

    /**
     * Creates an <code>AbstractTableDecorator</code> with the
     * <code>Table</code> to decorate.
     * @param table the table.
     * @param metadataManager the metadata manager.
     * @precondition table != null
     * @precondition metadataManager != null
     */
    public AbstractTableDecorator(
        final Table table, final MetadataManager metadataManager)
    {
        this(
            table.getName(),
            table.getAttributes(),
            table.getParentTable(),
            metadataManager);
    }

    /**
     * Creates an <code>AbstractTableDecorator</code> with the
     * <code>Table</code> to decorate.
     * @param table the table.
     * @param metadataManager the metadata manager.
     * @param decoratorFactory the decorator factory.
     * @precondition table != null
     * @precondition metadataManager != null
     * @precondition decoratorFactory != null
     */
    public AbstractTableDecorator(
        final Table table,
        final MetadataManager metadataManager,
        final DecoratorFactory decoratorFactory)
    {
        this(table, metadataManager);
        immutableSetDecoratorFactory(decoratorFactory);
    }

    /**
     * Creates an <code>AbstractTableDecorator</code> with the following
     * information.
     * @param name the name.
     * @param attributes the attributes.
     * @param parentTable the parent table.
     * @param metadataManager the metadata manager.
     * @precondition name != null
     * @precondition metadataManager != null
     */
    public AbstractTableDecorator(
        final String name,
        final List attributes,
        final Table parentTable,
        final MetadataManager metadataManager)
    {
        super(name, attributes, parentTable);

        immutableSetMetadataManager(metadataManager);
    }

    /**
     * Creates a <code>AbstractTableDecorator</code> instance.
     * @param table the table name.
     * @param attributes the attributes.
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @param childAttributes the child attributes.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @precondition decoratorFactory != null
     */
    public AbstractTableDecorator(
        final String table,
        final List attributes,
        final MetadataManager metadataManager,
        final List childAttributes,
        final DecoratorFactory decoratorFactory)
    {
        this(table, attributes, null, metadataManager);
        immutableSetChildAttributes(childAttributes);
        immutableSetDecoratorFactory(decoratorFactory);
    }

    /**
     * Creates a <code>AbstractTableDecorator</code> instance.
     * <code>Table</code> to decorate.
     * @param table the table.
     * @param metadataManager the metadata manager.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @precondition table != null
     * @precondition metadataManager != null
     * @precondition decoratorFactory != null
     */
    protected AbstractTableDecorator(
        final String table,
        final MetadataManager metadataManager,
        final DecoratorFactory decoratorFactory)
    {
        this(table, null, null, metadataManager);
        immutableSetDecoratorFactory(decoratorFactory);
    }
    
    /**
     * Creates a <code>AbstractTableDecorator</code> instance.
     * <code>Table</code> to decorate.
     * @param table the table.
     * @param metadataManager the metadata manager.
     * @param childAttributes the child attributes.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @precondition table != null
     * @precondition metadataManager != null
     * @precondition decoratorFactory != null
     */
    protected AbstractTableDecorator(
        final String table,
        final MetadataManager metadataManager,
        final List childAttributes,
        final DecoratorFactory decoratorFactory)
    {
        this(table, null, null, metadataManager);
        immutableSetChildAttributes(childAttributes);
        immutableSetDecoratorFactory(decoratorFactory);
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
     * Specifies the decorator factory.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     */
    protected final void immutableSetDecoratorFactory(
        final DecoratorFactory decoratorFactory)
    {
        m__DecoratorFactory = decoratorFactory;
    }
    
    /**
     * Specifies the decorator factory.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     */
    protected void setDecoratorFactory(final DecoratorFactory decoratorFactory)
    {
        immutableSetDecoratorFactory(decoratorFactory);
    }
    
    /**
     * Retrieves the decorator factory.
     * @return such instance.
     */
    protected final DecoratorFactory immutableGetDecoratorFactory()
    {
        return m__DecoratorFactory;
    }
    
    /**
     * Retrieves the decorator factory.
     * @return such instance.
     */
    public DecoratorFactory getDecoratorFactory()
    {
        DecoratorFactory result = immutableGetDecoratorFactory();

        if  (result == null)
        {
            result = CachingDecoratorFactory.getInstance();
            setDecoratorFactory(result);
        }

        return result;
    }
    
    /**
     * Specifies whether the attributes have been cleaned up.
     * @param flag such flag.
     */
    protected final void immutableSetAttributesCleanedUp(final boolean flag)
    {
        m__bAttributesCleanedUp = flag;
    }
    
    /**
     * Specifies whether the attributes have been cleaned up.
     * @param flag such flag.
     */
    protected void setAttributesCleanedUp(final boolean flag)
    {
        immutableSetAttributesCleanedUp(flag);
    }
    
    /**
     * Retrieves whether the attributes have been cleaned up.
     * @return such flag.
     */
    protected boolean getAttributesCleanedUp()
    {
        return m__bAttributesCleanedUp;
    }

    /**
     * Specifies the child attributes.
     * @param childAttributes the child attributes.
     */
    protected final void immutableSetChildAttributes(final List childAttributes)
    {
        m__lChildAttributes = childAttributes;
    }

    /**
     * Specifies the child attributes.
     * @param childAttributes the child attributes.
     */
    protected void setChildAttributes(final List childAttributes)
    {
        immutableSetChildAttributes(childAttributes);
    }

    /**
     * Retrieves the child's attributes.
     * @return such list.
     */
    public List getChildAttributes()
    {
        return m__lChildAttributes;
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
     * Retrieves the non-parent attributes.
     * @return such attributes.
     */
    public List getNonParentAttributes()
    {
        return
            getNonParentAttributes(
                getName(),
                getAttributes(),
                getParentTable(),
                getMetadataManager(),
                getDecoratorFactory(),
                TableDecoratorHelper.getInstance());
    }

    /**
     * Retrieves the non-parent attributes.
     * @param name the table name.
     * @param attributes the attributes.
     * @param parentTable the parent table (or <code>null</code> otherwise).
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @param tableDecoratorHelper the <code>TableDecoratorHelper</code> instance.
     * @return such attributes.
     * @precondition name != null
     * @precondition attributes != null
     * @precondition metadataManager != null
     * @precondition decoratorFactory != null
     * @precondition tableDecoratorHelper != null
     */
    protected List getNonParentAttributes(
        final String name,
        final List attributes,
        final Table parentTable,
        final MetadataManager metadataManager,
        final DecoratorFactory decoratorFactory,
        final TableDecoratorHelper tableDecoratorHelper)
    {
        List result = attributes;

        if  (parentTable != null)
        {
            result =
                tableDecoratorHelper.removeOverridden(
                    decorateAttributes(
                        parentTable.getName(),
                        metadataManager,
                        decoratorFactory),
                    attributes,
                    name,
                    metadataManager);
        }

        return result;
    }

    /**
     * Decorates the attributes.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @return the decorated attributes.
     * @precondition decoratorFactory != null
     */
    protected List decorateAttributes()
    {
        return
            decorateAttributes(
                getName(), getMetadataManager(), getDecoratorFactory());
    }
    
    /**
     * Decorates the attributes.
     * @param name the table name.
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @return the decorated attributes.
     * @precondition name != null
     * @precondition decoratorFactory != null
     * @precondition metadataManager != null
     */
    protected List decorateAttributes(
        final String name,
        final MetadataManager metadataManager,
        final DecoratorFactory decoratorFactory)
    {
        return decoratorFactory.decorateAttributes(name, metadataManager);
    }
    
    /**
     * Retrieves the attributes.
     * @return such information.
     */
    public List getAllAttributes()
    {
        return
            getAllAttributes(
                getName(), getMetadataManager(), getDecoratorFactory());
    }

    /**
     * Retrieves the attributes.
     * @param table the table name.
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @return such information.
     * @precondition table != null
     * @precondition metadataManager != null
     * @precondition decoratorFactory != null
     */
    protected List getAllAttributes(
        final String table,
        final MetadataManager metadataManager,
        final DecoratorFactory decoratorFactory)
    {
        List result = new ArrayList();

        Table t_ParentTable = getParentTable();

        if  (t_ParentTable != null)
        {
            TableDecorator t_ParentDecorator =
                createTableDecorator(
                    t_ParentTable, metadataManager, decoratorFactory);
            
            result.addAll(t_ParentDecorator.getAllAttributes());
        }
        
        result.addAll(
            decorateAttributes(
                table,
                metadataManager,
                decoratorFactory));

        return result;
    }

    /**
     * Retrieves the attributes.
     * @return such information.
     */
    public List getAttributes()
    {
        return
            getAttributes(
                getName(),
                getChildAttributes(),
                getMetadataManager(),
                getDecoratorFactory(),
                getAttributesCleanedUp());
    }

    /**
     * Retrieves the attributes.
     * @param table the table name.
     * @param childAttributes the child's attributes.
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @param attributesCleanedUp whether the child attributes have been removed
     * from the attribute list already.
     * @return such information.
     * @precondition table != null
     * @precondition childAttributes != null
     * @precondition metadataManager != null
     * @precondition decoratorFactory != null
     */
    protected List getAttributes(
        final String table,
        final List childAttributes,
        final MetadataManager metadataManager,
        final DecoratorFactory decoratorFactory,
        final boolean attributesCleanedUp)
    {
        List result = super.getAttributes();

        if  (   (result == null)
             || (result.size() == 0))
        {
            result =
                decorateAttributes(
                    table, metadataManager, decoratorFactory);
        }
        
        if  (   (result != null)
             && (!attributesCleanedUp))
        {
            result =
                removeOverridden(
                    childAttributes,
                    result,
                    table,
                    metadataManager,
                    TableDecoratorHelper.getInstance());
            
            super.setAttributes(result);
            setAttributesCleanedUp(true);
        }

        return result;
    }

    /**
     * Retrieves the parent table.
     * @return such information.
     */
    public Table getParentTable()
    {
        return getParentTable(getMetadataManager(), getDecoratorFactory());
    }
    
    /**
     * Retrieves the parent table.
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @return such information.
     * @precondition metadataManager != null
     * @precondition decoratorFactory != null
     */
    protected Table getParentTable(
        final MetadataManager metadataManager, 
        final DecoratorFactory decoratorFactory)
    {
        Table result = super.getParentTable();

        if  (result == null)
        {
            String t_strParentTable =
                metadataManager.getParentTable(getName());
            
            if  (t_strParentTable != null)
            {
                List t_lAttributes = getAttributes();
                
                super.setParentTable(
                    createTableDecorator(
                        t_strParentTable, 
                        t_lAttributes,
                        metadataManager,
                        decoratorFactory));

                result = super.getParentTable();
            }
        }

        return result;
    }


    /**
     * Removes the duplicated attributes from <code>secondAttributes</code>.
     * @param firstAttributes the child attributes.
     * @param secondAttributes the parent attributes.
     * @param parentTableName the parent table name.
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @return the cleaned-up attributes.
     * @precondition firstAttributes != null
     * @precondition secondAttributes != null
     * @precondition parentTableName != null
     * @precondition metadataManager != null
     */
    public List removeOverridden(
        final List firstAttributes,
        final List secondAttributes,
        final String parentTableName,
        final MetadataManager metadataManager,
        final TableDecoratorHelper tableDecoratorHelper)
    {
        return
            tableDecoratorHelper.removeOverridden(
                firstAttributes,
                secondAttributes,
                parentTableName,
                metadataManager);
    }
        
    /**
     * Sums up parent and child's attributes.
     * @return such collection.
     */
    protected List sumUpParentAndChildAttributes()
    {
        return
            sumUpParentAndChildAttributes(
                getAttributes(),
                getChildAttributes(),
                TableDecoratorHelper.getInstance());
    }

    /**
     * Sums up parent and child's attributes.
     * @param parentTable the parent table.
     * @param attributes the attributes.
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @param tableDecoratorHelper the <code>TableDecoratorHelper</code> instance.
     * @return such collection.
     * @precondition metadataManager != null
     * @precondition decoratorFactory != null
     * @precondition tableDecoratorHelper != null
     */
    protected List sumUpParentAndChildAttributes(
        final String parentTable,
        final List attributes,
        final MetadataManager metadataManager,
        final DecoratorFactory decoratorFactory,
        final TableDecoratorHelper tableDecoratorHelper)
    {
        return
            tableDecoratorHelper.sumUpParentAndChildAttributes(
                parentTable,
                attributes,
                metadataManager,
                decoratorFactory);
    }
    
    /**
     * Sums up parent and child's attributes.
     * @param attributes the attributes.
     * @param childAttributes the child attributes.
     * @param tableDecoratorHelper the <code>TableDecoratorHelper</code> instance.
     * @return such collection.
     * @precondition attributes != null
     * @precondition childAttributes != null
     * @precondition tableDecoratorHelper != null
     */
    protected List sumUpParentAndChildAttributes(
        final List attributes,
        final List childAttributes,
        final TableDecoratorHelper tableDecoratorHelper)
    {
        return
            tableDecoratorHelper.sumUpParentAndChildAttributes(
                attributes, childAttributes);
    }

    /**
     * Creates a table decorator.
     * @param name the table name.
     * @param attributes the attributes.
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @return such decorator.
     * @precondition name != null
     * @precondition attributes != null
     * @precondition metadataManager != null
     * @precondition decoratorFactory != null
     */
    protected abstract TableDecorator createTableDecorator(
        final String parentTable,
        final List attributes,
        final MetadataManager metadataManager,
        final DecoratorFactory decoratorFactory);
    
    /**
     * Creates a table decorator.
     * @param name the table name.
     * @param attributes the attributes.
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @return such decorator.
     * @precondition name != null
     * @precondition attributes != null
     * @precondition metadataManager != null
     * @precondition decoratorFactory != null
     */
    protected abstract TableDecorator createTableDecorator(
        final Table parentTable,
        final MetadataManager metadataManager,
        final DecoratorFactory decoratorFactory);
}
