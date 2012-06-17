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
import org.acmsl.queryj.tools.metadata.vo.Attribute;
import org.acmsl.queryj.tools.metadata.vo.ForeignKey;
import org.acmsl.queryj.tools.metadata.vo.LazyAttribute;
import org.acmsl.queryj.tools.metadata.vo.Table;

/*
 * Importing some ACM-SL Commons classes.
 */
import org.acmsl.commons.utils.EnglishGrammarUtils;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
     * The decorated table.
     */
    private Table m__Table;

    /**
     * The metadata type manager.
     */
    private MetadataManager m__MetadataManager;

    /**
     * The decorator factory.
     */
    private DecoratorFactory m__DecoratorFactory;
    
    /**
     * The foreign keys.
     */
    @SuppressWarnings("unused")
    private List<ForeignKey> m__lForeignKeys;

    /**
     * The parent foreign key.
     */
    private ForeignKey m__ParentForeignKey;

    /**
     * The child's attributes.
     */
    private List<Attribute> m__lChildAttributes;

    /**
     * A flag indicating whether the attributes should be cleaned up.
     */
    private boolean m__bAttributesShouldBeCleanedUp = true;

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
        this(
            table,
            table.getName(),
            table.getPrimaryKey(),
            table.getAttributes(),
            table.getForeignKeys(),
            table.getParentTable(),
            table.isStatic(),
            table.isVoDecorated(),
            metadataManager,
            decoratorFactory);
    }

    /**
     * Creates an <code>AbstractTableDecorator</code> with the following
     * information.
     * @param name the name.
     * @param primaryKey the primary key.
     * @param attributes the attributes.
     * @param foreignKeys the foreign keys.
     * @param parentTable the parent table.
     * @param isStatic whether the table is static.
     * @param voDecorated whether the value-object should be decorated.
     * @param metadataManager the metadata manager.
     * @param decoratorFactory the decorator factory.
     */
    public AbstractTableDecorator(
        @NotNull final Table table,
        @NotNull final String name,
        @NotNull final List<Attribute> primaryKey,
        @NotNull final List<Attribute> attributes,
        @NotNull final List<ForeignKey> foreignKeys,
        @Nullable final Table parentTable,
        final boolean isStatic,
        final boolean voDecorated,
        @NotNull final MetadataManager metadataManager,
        @NotNull final DecoratorFactory decoratorFactory)
    {
        super(
            name, table.getComment(), primaryKey, attributes, foreignKeys, parentTable, isStatic, voDecorated);

        immutableSetTable(table);
        immutableSetMetadataManager(metadataManager);
        immutableSetDecoratorFactory(decoratorFactory);
    }

    /**
     * Specifies the table to decorate.
     * @param table such table.
     */
    protected final void immutableSetTable(@NotNull final Table table)
    {
        this.m__Table = table;
    }

    /**
     * Specifies the table to decorate.
     * @param table such table.
     */
    @SuppressWarnings("unused")
    protected void setTable(@NotNull final Table table)
    {
        immutableSetTable(table);
    }

    /**
     * Retrieves the decorated table.
     * @return such table.
     */
    protected final Table immutableGetTable()
    {
        return this.m__Table;
    }

    /**
     * Retrieves the decorated table.
     * @return such table.
     */
    public Table getTable()
    {
        return immutableGetTable();
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
     * Specifies whether the attributes should be cleaned up.
     * @param flag such flag.
     */
    protected final void immutableSetAttributesShouldBeCleanedUp(final boolean flag)
    {
        m__bAttributesShouldBeCleanedUp = flag;
    }
    
    /**
     * Specifies whether the attributes have been cleaned up.
     * @param flag such flag.
     */
    @SuppressWarnings("unused")
    protected void setAttributesShouldBeCleanedUp(final boolean flag)
    {
        immutableSetAttributesShouldBeCleanedUp(flag);
    }
    
    /**
     * Retrieves whether the attributes have been cleaned up.
     * @return such flag.
     */
    protected boolean getAttributesShouldBeCleanedUp()
    {
        return m__bAttributesShouldBeCleanedUp;
    }

    /**
     * Specifies the child attributes.
     * @param childAttributes the child attributes.
     */
    protected final void immutableSetChildAttributes(
        final List<Attribute> childAttributes)
    {
        m__lChildAttributes = childAttributes;
    }

    /**
     * Specifies the child attributes.
     * @param childAttributes the child attributes.
     */
    @SuppressWarnings("unused")
    protected void setChildAttributes(
        @NotNull final List<Attribute> childAttributes)
    {
        immutableSetChildAttributes(childAttributes);
    }

    /**
     * Retrieves the child's attributes.
     * @return such list.
     */
    public List<Attribute> getChildAttributes()
    {
        return m__lChildAttributes;
    }

    /**
     * Retrieves the foreign keys.
     * @return such list.
     */
    @NotNull
    @Override
    public List<ForeignKey> getForeignKeys()
    {
        List<ForeignKey> result = immutableGetForeignKeys();

        if  (result == null)
        {
            result =
                retrieveForeignKeys(
                    getName(),
                    getMetadataManager());

            setForeignKeys(result);
        }

        return result;
    }

    /**
     * Retrieves the foreign keys.
     * @param name the table name.
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @return such foreign keys.
     */
    @NotNull
    protected List<ForeignKey> retrieveForeignKeys(
        @NotNull final String name,
        @NotNull final MetadataManager metadataManager)
    {
        @Nullable List<ForeignKey> result;

        @Nullable final Table t_Table = metadataManager.getTableDAO().findByName(name);

        if (t_Table != null)
        {
            result = t_Table.getForeignKeys();
        }
        else
        {
            result = new ArrayList<ForeignKey>(0);
        }

        return result;
    }

    /**
     * Builds an attribute decorator with given information.
     * @param table the table name.
     * @param name the attribute name.
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @param metadataTypeManager the <code>MetadataTypeManager</code> instance.
     * @return such decorator.
     */
    @SuppressWarnings("unused")
    @NotNull
    protected AttributeDecorator buildAttributeDecorator(
        @NotNull final String table,
        @NotNull final String name,
        @NotNull final MetadataManager metadataManager,
        @NotNull final MetadataTypeManager metadataTypeManager)
    {
        return
            new CachingAttributeDecorator(
                new LazyAttribute(
                    table,
                    name,
                    metadataManager,
                    metadataTypeManager),
                metadataManager);
    }

    /**
     * Builds a foreign-key decorator with given information.
     * @param sourceTable the source table name.
     * @param attributes the attributes.
     * @param targetTable the target table name.
     * @param allowNull whether the foreign-key allows null.
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @param decoratorFactory the {@link DecoratorFactory} implementation.
     * @return such decorator.
     */
    @SuppressWarnings("unused")
    @NotNull
    protected ForeignKeyDecorator buildForeignKeyDecorator(
        @NotNull final String sourceTable,
        @NotNull final List<Attribute> attributes,
        @NotNull final String targetTable,
        final boolean allowNull,
        @NotNull final MetadataManager metadataManager,
        @NotNull final DecoratorFactory decoratorFactory)
    {
        return
            new ForeignKeyDecorator(
                sourceTable,
                attributes,
                targetTable,
                allowNull,
                metadataManager,
                decoratorFactory);
    }

    /**
     * Specifies the parent foreign-key.
     * @param foreignKey such fk.
     */
    protected final void immutableSetParentForeignKey(
        @NotNull final ForeignKey foreignKey)
    {
        m__ParentForeignKey = foreignKey;
    }

    /**
     * Specifies the parent foreign-key.
     * @param foreignKey such fk.
     */
    protected void setParentForeignKey(
        @NotNull final ForeignKey foreignKey)
    {
        immutableSetParentForeignKey(foreignKey);
    }

    /**
     * Retrieves the parent foreign-key.
     * @return such foreign key.
     */
    @Nullable
    protected final ForeignKey immutableGetParentForeignKey()
    {
        return m__ParentForeignKey;
    }

    /**
     * Retrieves the parent foreign-key.
     * @return such foreign key.
     */
    @SuppressWarnings("unused")
    @Nullable
    public ForeignKey getParentForeignKey()
    {
        ForeignKey result = immutableGetParentForeignKey();

        if  (result == null)
        {
            Table t_Parent = getParentTable();

            if  (t_Parent != null)
            {
                result =
                    retrieveParentForeignKey(t_Parent, getForeignKeys());

                if  (result != null)
                {
                    setParentForeignKey(result);
                }
            }
        }

        return result;
    }

    /**
     * Retrieves the parent foreign key.
     * @param parent the parent table.
     * @param foreignKeys the foreign keys.
     * @return such foreign key.
     */
    @Nullable
    protected ForeignKey retrieveParentForeignKey(
        @NotNull final Table parent, @NotNull final List<ForeignKey> foreignKeys)
    {
        @Nullable ForeignKey result = null;

        for (@Nullable ForeignKey t_ForeignKey : foreignKeys)
        {
            if (   (t_ForeignKey != null)
                 && (parent.getName().equalsIgnoreCase(
                         t_ForeignKey.getTargetTableName()))
                 && (attributeListMatch(
                getPrimaryKey(), t_ForeignKey.getAttributes())))
            {
                result = t_ForeignKey;
                break;
            }
        }

        return result;
    }

    /**
     * Checks whether given attribute lists match.
     * @param first the first attribute list.
     * @param second the second attribute list.
     * @return <code>true</code> in such case.
     */
    protected boolean attributeListMatch(final List first, final List second)
    {
        boolean result =
            (   (first != null)
             && (second != null));

        if  (result)
        {
            int t_iCount = first.size();
            result = (t_iCount == second.size());

            if  (result)
            {
                for  (int t_iIndex = 0; t_iIndex < t_iCount; t_iIndex++)
                {
                    result =
                        attributesMatch(
                            (Attribute) first.get(t_iIndex),
                            (Attribute) second.get(t_iIndex));

                    if  (!result)
                    {
                        break;
                    }
                }
            }
        }

        return result;
    }

    /**
     * Checks whether given attributes match.
     * @param first the first attribute.
     * @param second the second attribute.
     * @return <code>true</code> in such case.
     */
    protected boolean attributesMatch(
        @Nullable final Attribute first, @Nullable final Attribute second)
    {
        boolean result =
            (   (first != null)
             && (second != null));

        if  (result)
        {
            @NotNull String t_strFirstName = first.getName();

            result =
                (   (first.getType().equals(second.getType()))
                 && (t_strFirstName.equalsIgnoreCase(second.getName())));
        }

        return result;
    }

    /**
     * Retrieves the name, in upper case.
     * @return such value.
     */
    @NotNull
    public String getNameUppercased()
    {
        return uppercase(getName());
    }
    
    /**
     * Retrieves the capitalized name.
     * @return such name.
     */
    @NotNull
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
        return
            capitalize(value, DecorationUtils.getInstance());
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
        return decorationUtils.capitalize(lowercase(value));
    }
    
    /**
     * Converts given value to upper-case.
     * @param value the value.
     * @return the alternate version of the value.
     * @precondition value != null
     */
    protected String uppercase(final String value)
    {
        return uppercase(value, DecorationUtils.getInstance());
    }
    
    /**
     * Converts given value to upper-case.
     * @param value the value.
     * @param decorationUtils the <code>DecorationUtils</code> instance.
     * @return the alternate version of the value.
     * @precondition value != null
     * @precondition decorationUtils != null
     */
    protected String uppercase(
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
    @NotNull
    public String getNameLowercased()
    {
        return lowercase(getName());
    }
    
    /**
     * Converts given value to lower-case.
     * @param value the value.
     * @return the alternate version of the value.
     * @precondition value != null
     */
    protected String lowercase(final String value)
    {
        return lowercase(value, DecorationUtils.getInstance());
    }

    /**
     * Converts given value to lower-case.
     * @param value the value.
     * @param decorationUtils the <code>DecorationUtils</code> instance.
     * @return the alternate version of the value.
     * @precondition value != null
     * @precondition decorationUtils != null
     */
    protected String lowercase(
        final String value, final DecorationUtils decorationUtils)
    {
        return decorationUtils.lowerCase(value);
    }

    /**
     * Retrieves the table name, uncapitalized.
     * @return such value.
     */
    @NotNull
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
    @NotNull
    public String getVoName()
    {
        return getSingularNameCapitalized();
    }

    /**
     * Retrieves the table's name in lower-case, once normalized.
     * @return such information.
     */
    @NotNull
    public String getNameNormalizedLowercased()
    {
        return normalizeLowercase(getName());
    }

    /**
     * Retrieves the table's name in lower-case, once normalized.
     * @return such information.
     */
    @NotNull
    public String getSingularNameNormalizedLowercased()
    {
        return normalizeLowercase(getSingular(getName()));
    }

    /**
     * Retrieves the table's name in lower-case, once normalized.
     * @return such information.
     */
    @NotNull
    public String getNameNormalized()
    {
        return normalize(getName());
    }

    /**
     * Retrieves the table's name once normalized.
     * @return such information.
     */
    @NotNull
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
     * @param singularPluralFormConverter the
     * <code>SingularPluralFormConverter</code> instance.
     * @return the singular.
     * @precondition word != null
     * @precondition singularPluralFormConverter != null
     */
    protected String getSingular(
        final String word,
        final EnglishGrammarUtils singularPluralFormConverter)
    {
        return singularPluralFormConverter.getSingular(word);
    }

    /**
     * Retrieves the singular table's name, upper-cased.
     * @return such information.
     */
    @NotNull
    public String getSingularNameUppercased()
    {
        return uppercase(getSingular(lowercase(getName())));
    }

    /**
     * Retrieves the singular table's name, lower-cased.
     * @return such information.
     */
    @NotNull
    public String getSingularNameLowercased()
    {
        return lowercase(getSingularNameUppercased());
    }

    /**
     * Retrieves the non-read-only attributes.
     * @return such attributes.
     */
    @NotNull
    public List<Attribute> getNonReadOnlyAttributes()
    {
        return
            getNonReadOnlyAttributes(
                getName(),
                getAttributes(),
                getParentTable(),
                getMetadataManager(),
                getDecoratorFactory(),
                TableDecoratorHelper.getInstance());
    }

    /**
     * Retrieves the non-read-only attributes.
     * @param name the table name.
     * @param attributes the attributes.
     * @param parentTable the parent table (or <code>null</code> otherwise).
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @param tableDecoratorHelper the <code>TableDecoratorHelper</code>
     * instance.
     * @return such attributes.
     */
    @SuppressWarnings("unused")
    @NotNull
    protected List<Attribute> getNonReadOnlyAttributes(
        final String name,
        final List<Attribute> attributes,
        final Table parentTable,
        final MetadataManager metadataManager,
        final DecoratorFactory decoratorFactory,
        final TableDecoratorHelper tableDecoratorHelper)
    {
        List<Attribute> result = attributes;

        if  (parentTable != null)
        {
            result =
                tableDecoratorHelper.removeReadOnly(
                    tableDecoratorHelper.sumUpParentAndChildAttributes(
                        parentTable.getName(),
                        decorateAttributes(),
                        metadataManager,
                        decoratorFactory));
        }

        return result;
    }

    /**
     * Retrieves the non-parent attributes.
     * @return such attributes.
     */
    @NotNull
    public List<Attribute> getNonParentAttributes()
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
     * @param tableDecoratorHelper the <code>TableDecoratorHelper</code>
     * instance.
     * @return such attributes.
     */
    @NotNull
    protected List<Attribute> getNonParentAttributes(
        @NotNull final String name,
        @NotNull final List<Attribute> attributes,
        @Nullable final Table parentTable,
        @NotNull final MetadataManager metadataManager,
        @NotNull final DecoratorFactory decoratorFactory,
        @NotNull final TableDecoratorHelper tableDecoratorHelper)
    {
        List<Attribute> result = attributes;

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
     * Retrieves the non-parent attributes, plus the primary key.
     * @return such attributes.
     */
    @NotNull
    public List<Attribute> getNonParentPlusPkAttributes()
    {
        return
            getNonParentPlusPkAttributes(
                getName(),
                getAllAttributes(),
                getParentTable(),
                getMetadataManager(),
                getDecoratorFactory(),
                TableDecoratorHelper.getInstance());
    }

    /**
     * Retrieves the non-parent attributes, plus the primary key.
     * @param name the table name.
     * @param attributes the attributes.
     * @param parentTable the parent table (or <code>null</code> otherwise).
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @param tableDecoratorHelper the <code>TableDecoratorHelper</code>
     * instance.
     * @return such attributes.
     */
    @NotNull
    protected List<Attribute> getNonParentPlusPkAttributes(
        @NotNull final String name,
        @NotNull final List<Attribute> attributes,
        @Nullable final Table parentTable,
        @NotNull final MetadataManager metadataManager,
        @NotNull final DecoratorFactory decoratorFactory,
        @NotNull final TableDecoratorHelper tableDecoratorHelper)
    {
        List<Attribute> result = attributes;

        if  (parentTable != null)
        {
            result =
                tableDecoratorHelper.removeOverriddenPlusPk(
                    decorateAttributes(
                        parentTable.getName(),
                        metadataManager,
                        decoratorFactory),
                    attributes,
                    getPrimaryKey(),
                    name,
                    metadataManager);
        }

        return result;
    }

    /**
     * Decorates the attributes.
     * @return the decorated attributes.
     */
    protected List<Attribute> decorateAttributes()
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
     */
    protected List<Attribute> decorateAttributes(
        @NotNull final String name,
        @NotNull final MetadataManager metadataManager,
        @NotNull final DecoratorFactory decoratorFactory)
    {
        return decoratorFactory.decorateAttributes(name, metadataManager);
    }
    
    /**
     * Retrieves the attributes.
     * @return such information.
     */
    @NotNull
    public List<Attribute> getAllAttributes()
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
     */
    protected List<Attribute> getAllAttributes(
        @NotNull final String table,
        @NotNull final MetadataManager metadataManager,
        @NotNull final DecoratorFactory decoratorFactory)
    {
        List<Attribute> result = new ArrayList<Attribute>();

        Table t_ParentTable = getParentTable();

        if  (t_ParentTable != null)
        {
            TableDecorator t_ParentDecorator;

            if  (t_ParentTable instanceof TableDecorator)
            {
                t_ParentDecorator = (TableDecorator) t_ParentTable;
            }
            else
            {
                t_ParentDecorator =
                    createTableDecorator(
                        t_ParentTable, metadataManager, decoratorFactory);
            }

            if (t_ParentDecorator != null)
            {
                result.addAll(t_ParentDecorator.getAllAttributes());
            }
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
    @NotNull
    public List<Attribute> getAttributes()
    {
        return
            getAttributes(
                getName(),
                getChildAttributes(),
                getMetadataManager(),
                getDecoratorFactory(),
                getAttributesShouldBeCleanedUp());
    }

    /**
     * Retrieves the attributes.
     * @param table the table name.
     * @param childAttributes the child's attributes.
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @param attributesShouldBeCleanedUp whether the child attributes should be removed
     * from the attribute list.
     * @return such information.
     */
    protected List<Attribute> getAttributes(
        @NotNull final String table,
        @NotNull final List<Attribute> childAttributes,
        @NotNull final MetadataManager metadataManager,
        @NotNull final DecoratorFactory decoratorFactory,
        final boolean attributesShouldBeCleanedUp)
    {
        List<Attribute> result = super.getAttributes();

        if  (result.size() == 0)
        {
            result =
                decorateAttributes(
                    table, metadataManager, decoratorFactory);
        
            if  (   (result != null)
                 && (attributesShouldBeCleanedUp))
            {
                result =
                    removeOverridden(
                        childAttributes,
                        result,
                        table,
                        metadataManager,
                        TableDecoratorHelper.getInstance());

                super.setAttributes(result);
            }
        }

        return result;
    }

    /**
     * Retrieves the parent table.
     * @return such information.
     */
    @Nullable
    public Table getParentTable()
    {
        return getParentTable(getName(), getMetadataManager());
    }
    
    /**
     * Retrieves the parent table.
     * @param name the table name.
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @return such information.
     */
    @Nullable
    protected Table getParentTable(@NotNull final String name, @NotNull final MetadataManager metadataManager)
    {
        Table result = super.getParentTable();

        if  (result == null)
        {
            @Nullable Table t_Table = metadataManager.getTableDAO().findByName(name);

            if (t_Table != null)
            {
                result = t_Table.getParentTable();
            }
        }

        return result;
    }


    /**
     * Removes the duplicated attributes from <code>secondAttributes</code>.
     * @param firstAttributes the child attributes.
     * @param secondAttributes the parent attributes.
     * @param parentTableName the parent table nameq.
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @return the cleaned-up attributes.
     */
    public List<Attribute> removeOverridden(
        @NotNull final List<Attribute> firstAttributes,
        @NotNull final List<Attribute> secondAttributes,
        @Nullable final String parentTableName,
        @NotNull final MetadataManager metadataManager,
        @NotNull final TableDecoratorHelper tableDecoratorHelper)
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
    @SuppressWarnings("unused")
    @NotNull
    protected List<Attribute> sumUpParentAndChildAttributes()
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
     */
    @SuppressWarnings("unused")
    @NotNull
    protected List<Attribute> sumUpParentAndChildAttributes(
        @NotNull final String parentTable,
        @NotNull final List<Attribute> attributes,
        @NotNull final MetadataManager metadataManager,
        @NotNull final DecoratorFactory decoratorFactory,
        @NotNull final TableDecoratorHelper tableDecoratorHelper)
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
     */
    @NotNull
    protected List<Attribute> sumUpParentAndChildAttributes(
        @NotNull final List<Attribute> attributes,
        @NotNull final List<Attribute> childAttributes,
        @NotNull final TableDecoratorHelper tableDecoratorHelper)
    {
        return
            tableDecoratorHelper.sumUpParentAndChildAttributes(
                attributes, childAttributes);
    }

    /**
     * Creates a table decorator.
     * @param parentTable the parent table name.
     * @param primaryKey the primary key.
     * @param attributes the attributes.
     * @param isStatic whether the table contains static values or not.
     * @param voDecorated whether the value-object is decorated.
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @return such decorator.
     */
    @SuppressWarnings("unused")
    protected abstract TableDecorator createTableDecorator(
        @Nullable final String parentTable,
        @NotNull final List<Attribute> primaryKey,
        @NotNull final List<Attribute> attributes,
        final boolean isStatic,
        final boolean voDecorated,
        @NotNull final MetadataManager metadataManager,
        @NotNull final DecoratorFactory decoratorFactory);
    
    /**
     * Creates a table decorator.
     * @param parentTable the parent table name.
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @return such decorator.
     */
    @Nullable
    protected abstract TableDecorator createTableDecorator(
        @NotNull final Table parentTable,
        @NotNull final MetadataManager metadataManager,
        @NotNull final DecoratorFactory decoratorFactory);

    /**
     * Retrieves the list of non-parent, non-externally-managed
     * attributes.
     * @return such list.
     */
    @NotNull
    public List<Attribute> getNonParentNonManagedExternallyAttributes()
    {
        return
            getNonParentNonManagedExternallyAttributes(
                getNonParentAttributes());
    }

    /**
     * Retrieves the list of non-parent, non-externally-managed
     * attributes.
     * @param nonParentAttributes the non-parent attributes.
     * @return such list.
     */
    @NotNull
    protected List<Attribute> getNonParentNonManagedExternallyAttributes(
        @NotNull final List<Attribute> nonParentAttributes)
    {
        List<Attribute> result = new ArrayList<Attribute>();

        for (@Nullable Attribute t_Attribute : nonParentAttributes)
        {
            if  (   (t_Attribute != null)
                 && (!t_Attribute.isManagedExternally()))
            {
                result.add(t_Attribute);
            }
        }

        return result;
    }

    /**
     * Retrieves the list of non-parent, non-externally-managed
     * attributes.
     * @return such list.
     */
    @NotNull
    public List<Attribute> getNonParentNonManagedExternallyPlusPkAttributes()
    {
        return
            getNonParentNonManagedExternallyPlusPkAttributes(
                getNonParentPlusPkAttributes(),
                getPrimaryKey());
    }

    /**
     * Retrieves the list of non-parent, non-externally-managed
     * attributes.
     * @param nonParentAttributes the non-parent attributes.
     * @param primaryKey the primary key.
     * @return such list.
     */
    @NotNull
    protected List<Attribute> getNonParentNonManagedExternallyPlusPkAttributes(
        @NotNull final List<Attribute> nonParentAttributes,
        @NotNull final List<Attribute> primaryKey)
    {
        List<Attribute> result = new ArrayList<Attribute>();

        for (@Nullable Attribute t_Attribute : nonParentAttributes)
        {
            if  (   (t_Attribute != null)
                 && (   (!t_Attribute.isManagedExternally())
                     || (isPartOf(primaryKey, t_Attribute))))
            {
                result.add(t_Attribute);
            }
        }

        return result;
    }

    /**
     * Retrieves the list of parent's all attributes and the non-parent own attributes.
     * @return such list.
     */
    @NotNull
    public List<Attribute> getAllParentAndNonParentAttributes()
    {
        return
            getAllParentAndNonParentAttributes(
                getParentTable(),
                getNonParentAttributes(),
                getMetadataManager(),
                getDecoratorFactory());
    }

    /**
     * Retrieves the list of parent's all attributes and the non-parent own attributes.
     * @param parent the parent table, if any.
     * @param nonParentAttributes the non-parent attributes.
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @return such list.
     */
    @NotNull
    protected List<Attribute> getAllParentAndNonParentAttributes(
        @Nullable final Table parent,
        @NotNull final List<Attribute> nonParentAttributes,
        @NotNull final MetadataManager metadataManager,
        @NotNull final DecoratorFactory decoratorFactory)
    {
        @NotNull final List<Attribute> result = new ArrayList<Attribute>();

        if  (parent != null)
        {
            TableDecorator t_ParentDecorator;

            if  (parent instanceof TableDecorator)
            {
                t_ParentDecorator = (TableDecorator) parent;
            }
            else
            {
                t_ParentDecorator =
                    createTableDecorator(
                        parent, metadataManager, decoratorFactory);
            }

            if (t_ParentDecorator != null)
            {
                result.addAll(t_ParentDecorator.getAllAttributes());
            }
        }

        result.addAll(nonParentAttributes);

        return result;
    }

    /**
     * Retrieves the list of parent's all attributes and the non-parent
     * non-managed-externally own attributes.
     * @return such list.
     */
    @NotNull
    public List<Attribute> getAllParentAndNonParentNonManagedExternallyAttributes()
    {
        return
            getAllParentAndNonParentNonManagedExternallyAttributes(
                getParentTable(),
                getNonParentNonManagedExternallyAttributes(),
                getMetadataManager(),
                getDecoratorFactory());
    }

    /**
     * Retrieves the list of parent's all attributes and the non-parent
     * non-managed-externally own attributes.
     * @param parent the parent.
     * @param nonParentNonManagedExternallyAttributes the non-parent, non-managed-externally
     * own attributes.
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @return such list.
     */
    @NotNull
    protected List<Attribute> getAllParentAndNonParentNonManagedExternallyAttributes(
        @Nullable final Table parent,
        @NotNull final List<Attribute> nonParentNonManagedExternallyAttributes,
        @NotNull final MetadataManager metadataManager,
        @NotNull final DecoratorFactory decoratorFactory)
    {
        @NotNull final List<Attribute> result = new ArrayList<Attribute>();

        if  (parent != null)
        {
            TableDecorator t_ParentDecorator;

            if  (parent instanceof TableDecorator)
            {
                t_ParentDecorator = (TableDecorator) parent;
            }
            else
            {
                t_ParentDecorator =
                    createTableDecorator(
                        parent, metadataManager, decoratorFactory);
            }

            if (t_ParentDecorator != null)
            {
                result.addAll(t_ParentDecorator.getAllNonManagedExternallyAttributes());
            }
        }

        result.addAll(nonParentNonManagedExternallyAttributes);

        return result;
    }

    /**
     * Retrieves all attributes, including the parent's, but not the externally-managed.
     * @return such attributes.
     */
    @NotNull
    public List<Attribute> getAllNonManagedExternallyAttributes()
    {
        return getAllNonManagedExternallyAttributes(getAllAttributes());
    }

    /**
     * Retrieves all attributes, including the parent's, but not the externally-managed.
     * @param allAttributes the attributes.
     * @return such attributes.
     */
    @NotNull
    protected List<Attribute> getAllNonManagedExternallyAttributes(@NotNull final List<Attribute> allAttributes)
    {
        @NotNull final List<Attribute> result = new ArrayList<Attribute>();

        for (@Nullable Attribute t_Attribute : allAttributes)
        {
            if  (   (t_Attribute != null)
                 && (!t_Attribute.isManagedExternally()))
            {
                result.add(t_Attribute);
            }
        }

        return result;
    }

    /**
     * Retrieves all attributes, including the parent's, but not the externally-managed,
     * plus the primary key.
     * @return such attributes.
     */
    @NotNull
    public List<Attribute> getAllNonManagedExternallyPlusPkAttributes()
    {
        return getAllNonManagedExternallyPlusPkAttributes(getAllAttributes(), getPrimaryKey());
    }

    /**
     * Retrieves all attributes, including the parent's, but not the externally-managed,
     * plus the primary key.
     * @param allAttributes the attributes.
     * @param primaryKey the primary key.
     * @return such attributes.
     */
    @NotNull
    protected List<Attribute> getAllNonManagedExternallyPlusPkAttributes(
        @NotNull final List<Attribute> allAttributes, @NotNull final List<Attribute> primaryKey)
    {
        @NotNull final List<Attribute> result = new ArrayList<Attribute>();

        for (@Nullable Attribute t_Attribute : allAttributes)
        {
            if  (   (t_Attribute != null)
                 && (   (!t_Attribute.isManagedExternally())
                     || (isPartOf(primaryKey, t_Attribute))))
            {
                result.add(t_Attribute);
            }
        }

        return result;
    }

    /**
     * Retrieves all attributes, including the parent's, but not the externally-managed,
     * plus the primary key.
     * @return such attributes.
     */
    @NotNull
    public List<Attribute> getAllNonManagedExternallyNonReadOnlyPlusPkAttributes()
    {
        return getAllNonManagedExternallyNonReadOnlyPlusPkAttributes(getAllAttributes(), getPrimaryKey());
    }

    /**
     * Retrieves all attributes, including the parent's, but not the externally-managed,
     * or read-only, plus the primary key.
     * @param allAttributes the attributes.
     * @param primaryKey the primary key.
     * @return such attributes.
     */
    @NotNull
    protected List<Attribute> getAllNonManagedExternallyNonReadOnlyPlusPkAttributes(
        @NotNull final List<Attribute> allAttributes, @NotNull final List<Attribute> primaryKey)
    {
        @NotNull final List<Attribute> result = new ArrayList<Attribute>();

        for (@Nullable Attribute t_Attribute : allAttributes)
        {
            if  (   (t_Attribute != null)
                 && (   (   (!t_Attribute.isManagedExternally())
                         && (!t_Attribute.isReadOnly()))
                     || (isPartOf(primaryKey, t_Attribute))))
            {
                result.add(t_Attribute);
            }
        }

        return result;
    }

    /**
     * Checks whether given attribute belongs to a concrete attribute list.
     * @param list the list.
     * @param attribute the attribute.
     * @return <tt>true</tt> in such case.
     */
    protected boolean isPartOf(@NotNull final List<Attribute> list, @NotNull final Attribute attribute)
    {
        boolean result = false;

        for (@Nullable Attribute t_Attribute : list)
        {
            if  (attributesMatch(t_Attribute, attribute))
            {
                result = true;
                break;
            }
        }

        return result;
    }

    /**
     * Retrieves the list of parent's all attributes and the non-parent
     * non-managed-externally, non-read-only own attributes.
     * @return such list.
     */
    @NotNull
    public List<Attribute> getAllParentAndNonParentNonManagedExternallyNonReadOnlyAttributes()
    {
        @NotNull List<Attribute> result =
            getAllParentAndNonParentNonManagedExternallyAttributes();

        return removeReadOnly(result, TableDecoratorHelper.getInstance());
    }

    /**
     * Removes the read-only attributes from given list.
     * @param attributes the attributes.
     * @param tableDecoratorHelper the <code>TableDecoratorHelper</code> instance.
     * @return the list without the read-only attributes.
     */
    @NotNull
    protected List<Attribute> removeReadOnly(
        @NotNull final List<Attribute> attributes,
        @NotNull final TableDecoratorHelper tableDecoratorHelper)
    {
        return tableDecoratorHelper.removeReadOnly(attributes);
    }

    /**
     * Retrieves the list of parent's all attributes and the non-parent,
     * non-read-only own attributes.
     * @return such list.
     */
    @NotNull
    public List<Attribute> getAllParentAndNonParentNonReadOnlyAttributes()
    {
        @NotNull final List<Attribute> result = getAllParentAndNonParentAttributes();

        return removeReadOnly(result, TableDecoratorHelper.getInstance());
    }

    /**
     * Retrieves the read-only attributes.
     * @return such information.
     */
    @NotNull
    public List<Attribute> getAllParentAndNonParentReadOnlyAttributes()
    {
        return
            getAllParentAndNonParentReadOnlyAttributes(
                TableDecoratorHelper.getInstance());
    }

    /**
     * Retrieves the read-only attributes.
     * @param tableDecoratorHelper the <code>TableDecoratorHelper</code> instance.
     * @return such information.
     */
    @NotNull
    protected List<Attribute> getAllParentAndNonParentReadOnlyAttributes(
        @NotNull final TableDecoratorHelper tableDecoratorHelper)
    {
        return
            tableDecoratorHelper.removeNonReadOnlyAttributes(
                getAllParentAndNonParentAttributes());
    }

    /**
     * Retrieves the list of parent's all attributes and the non-parent
     * non-managed-externally, non-read-only own attributes, including the primary key.
     * @return such list.
     */
    @NotNull
    public List<Attribute> getAllParentAndNonParentNonManagedExternallyNonReadOnlyPlusPkAttributes()
    {
        return
            getAllParentAndNonParentNonManagedExternallyNonReadOnlyPlusPkAttributes(
                getParentTable(),
                getNonParentNonManagedExternallyPlusPkAttributes(),
                getMetadataManager(),
                getDecoratorFactory(),
                TableDecoratorHelper.getInstance());
    }

    /**
     * Retrieves the list of parent's all attributes and the non-parent
     * non-managed-externally , non-read-only, own attributes, plus the primary key.
     * @param parent the parent.
     * @param nonParentNonManagedExternallyPlusPkAttributes the non-parent, non-managed-externally
     * own attributes, plus the primary key.
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @param tableDecoratorHelper the <code>TableDecoratorHelper</code> instance.
     * @return such list.
     */
    @NotNull
    protected List<Attribute> getAllParentAndNonParentNonManagedExternallyNonReadOnlyPlusPkAttributes(
        @Nullable final Table parent,
        @NotNull final List<Attribute> nonParentNonManagedExternallyPlusPkAttributes,
        @NotNull final MetadataManager metadataManager,
        @NotNull final DecoratorFactory decoratorFactory,
        @NotNull final TableDecoratorHelper tableDecoratorHelper)
    {
        @NotNull final List<Attribute> result = new ArrayList<Attribute>();

        if  (parent != null)
        {
            TableDecorator t_ParentDecorator;

            if  (parent instanceof TableDecorator)
            {
                t_ParentDecorator = (TableDecorator) parent;
            }
            else
            {
                t_ParentDecorator =
                    createTableDecorator(
                        parent, metadataManager, decoratorFactory);
            }

            if (t_ParentDecorator != null)
            {
                result.addAll(
                    t_ParentDecorator.getAllNonManagedExternallyNonReadOnlyPlusPkAttributes());
            }
        }

        result.addAll(
            tableDecoratorHelper.removeReadOnly(
                nonParentNonManagedExternallyPlusPkAttributes));

        return result;
    }

    /**
     * Retrieves all parent tables.
     * @return such tables.
     */
    @NotNull
    public List<Table> getAllParentTables()
    {
        return getAllParentTables(getName(), getMetadataManager());
    }

    /**
     * Retrieves all parent tables.
     * @param tableName the table name.
     * @param metadataManager the metadata manager.
     * @return such tables.
     */
    @NotNull
    protected List<Table> getAllParentTables(
        @NotNull final String tableName, @NotNull final MetadataManager metadataManager)
    {
        @NotNull final List<Table> result = new ArrayList<Table>(0);

        @Nullable Table t_Table = metadataManager.getTableDAO().findByName(tableName);

        if (t_Table != null)
        {
            @Nullable Table t_Parent;

            do
            {
                t_Parent = t_Table.getParentTable();

                if (t_Parent != null)
                {
                    result.add(t_Parent);

                    t_Parent = t_Parent.getParentTable();
                }
            }
            while (t_Parent != null);
        }

        return result;
    }
}
