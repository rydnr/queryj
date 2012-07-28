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
package org.acmsl.queryj.metadata;

/*
 * Importing project classes.
 */
import org.acmsl.queryj.customsql.CustomResultUtils;
import org.acmsl.queryj.customsql.CustomSqlProvider;
import org.acmsl.queryj.customsql.Result;
import org.acmsl.queryj.customsql.ResultElement;
import org.acmsl.queryj.customsql.ResultRef;
import org.acmsl.queryj.customsql.Sql;
import org.acmsl.queryj.metadata.vo.AbstractTable;
import org.acmsl.queryj.metadata.vo.Attribute;
import org.acmsl.queryj.metadata.vo.ForeignKey;
import org.acmsl.queryj.metadata.vo.LazyAttribute;
import org.acmsl.queryj.metadata.vo.Row;
import org.acmsl.queryj.metadata.vo.Table;
import org.acmsl.queryj.SingularPluralFormConverter;
import org.acmsl.queryj.templates.dao.DAOTemplateUtils;

/*
 * Importing some ACM-SL Commons classes.
 */
import org.acmsl.commons.logging.UniqueLogFactory;
import org.acmsl.commons.utils.EnglishGrammarUtils;

/*
 * Importing some Apache Commons Logging classes.
 */
import org.apache.commons.logging.Log;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing some JDK classes.
 */
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
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
     * The custom sql provider.
     */
    private CustomSqlProvider m__CustomSqlProvider;

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
     * @param table the {@link Table table}.
     * @param metadataManager the {@link MetadataManager metadata manager}.
     * @param decoratorFactory the {@link DecoratorFactory decorator factory}.
     * @param customSqlProvider the {@link CustomSqlProvider custom-sql provider}.
     */
    @SuppressWarnings("unused")
    public AbstractTableDecorator(
        @NotNull final Table table,
        @NotNull final MetadataManager metadataManager,
        @NotNull final DecoratorFactory decoratorFactory,
        @NotNull final CustomSqlProvider customSqlProvider)
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
            decoratorFactory,
            customSqlProvider);
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
     * @param metadataManager the {@link MetadataManager metadata manager}.
     * @param decoratorFactory the {@link DecoratorFactory decorator factory}.
     * @param customSqlProvider the {@link CustomSqlProvider custom-sql provider}.
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
        @NotNull final DecoratorFactory decoratorFactory,
        @NotNull final CustomSqlProvider customSqlProvider)
    {
        super(
            name, table.getComment(), primaryKey, attributes, foreignKeys, parentTable, isStatic, voDecorated);

        immutableSetTable(table);
        immutableSetMetadataManager(metadataManager);
        immutableSetDecoratorFactory(decoratorFactory);
        immutableSetCustomSqlProvider(customSqlProvider);
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
    @NotNull
    protected final Table immutableGetTable()
    {
        return this.m__Table;
    }

    /**
     * Retrieves the decorated table.
     * @return such table.
     */
    @NotNull
    public Table getTable()
    {
        return immutableGetTable();
    }

    /**
     * Specifies the metadata manager.
     * @param metadataManager such instance.
     */
    protected final void immutableSetMetadataManager(
        @NotNull final MetadataManager metadataManager)
    {
        m__MetadataManager = metadataManager;
    }

    /**
     * Specifies the metadata manager.
     * @param metadataManager such instance.
     */
    protected void setMetadataManager(
        @NotNull final MetadataManager metadataManager)
    {
        immutableSetMetadataManager(metadataManager);
    }

    /**
     * Retrieves the metadata manager.
     * @return such instance.
     */
    @NotNull
    protected MetadataManager getMetadataManager()
    {
        return m__MetadataManager;
    }

    /**
     * Specifies the decorator factory.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     */
    protected final void immutableSetDecoratorFactory(
        @NotNull final DecoratorFactory decoratorFactory)
    {
        m__DecoratorFactory = decoratorFactory;
    }
    
    /**
     * Specifies the decorator factory.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     */
    protected void setDecoratorFactory(@NotNull final DecoratorFactory decoratorFactory)
    {
        immutableSetDecoratorFactory(decoratorFactory);
    }
    
    /**
     * Retrieves the decorator factory.
     * @return such instance.
     */
    @Nullable
    protected final DecoratorFactory immutableGetDecoratorFactory()
    {
        return m__DecoratorFactory;
    }
    
    /**
     * Retrieves the decorator factory.
     * @return such instance.
     */
    @NotNull
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
     * Specifies the {@link CustomSqlProvider}.
     * @param provider the {@link CustomSqlProvider provider}.
     */
    protected final void immutableSetCustomSqlProvider(@NotNull final CustomSqlProvider provider)
    {
        m__CustomSqlProvider = provider;
    }

    /**
     * Specifies the {@link CustomSqlProvider}.
     * @param provider the {@link CustomSqlProvider provider}.
     */
    @SuppressWarnings("unused")
    protected void setCustomSqlProvider(@NotNull final CustomSqlProvider provider)
    {
        immutableSetCustomSqlProvider(provider);
    }

    /**
     * Retrieves the {@link CustomSqlProvider}.
     * @return such instance.
     */
    @NotNull
    public CustomSqlProvider getCustomSqlProvider()
    {
        return m__CustomSqlProvider;
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
    @Nullable
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
        return getForeignKeys(getName(), getMetadataManager(), getDecoratorFactory(), getCustomSqlProvider());
    }

    /**
     * Retrieves the foreign keys.
     * @param name the table name.
     * @param metadataManager the {@link MetadataManager} instance.
     * @param decoratorFactory the {@link DecoratorFactory} instance.
     * @param customSqlProvider the {@link CustomSqlProvider} instnace.
     * @return such list.
     */
    @NotNull
    protected List<ForeignKey> getForeignKeys(
        @NotNull final String name,
        @NotNull final MetadataManager metadataManager,
        @NotNull final DecoratorFactory decoratorFactory,
        @NotNull final CustomSqlProvider customSqlProvider)
    {
        List<ForeignKey> result = immutableGetForeignKeys();

        if  (   (result == null)
             || (!areDecorated(result)))
        {
            result =
                decorate(
                    retrieveForeignKeys(
                        name,
                        metadataManager),
                    metadataManager,
                    decoratorFactory,
                    customSqlProvider);

            setForeignKeys(result);
        }

        try
        {
            Collections.sort(result);
        }
        catch (@NotNull final Throwable error)
        {
            UniqueLogFactory.getLog(AbstractTableDecorator.class).error(
                error);
        }

        return result;
    }

    /**
     * Checks whether given foreign keys are decorated already or not.
     * @param foreignKeys the {@link ForeignKey} list.
     * @return <code>true</code> in such case.
     */
    protected boolean areDecorated(@NotNull final List<ForeignKey> foreignKeys)
    {
        boolean result = false;

        for (@Nullable ForeignKey t_ForeignKey : foreignKeys)
        {
            if (t_ForeignKey instanceof ForeignKeyDecorator)
            {
                result = true;
                break;
            }
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
     * @param customSqlProvider the {@link CustomSqlProvider} instance.
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
        @NotNull final DecoratorFactory decoratorFactory,
        @NotNull final CustomSqlProvider customSqlProvider)
    {
        return
            new CachingForeignKeyDecorator(
                sourceTable,
                attributes,
                targetTable,
                allowNull,
                metadataManager,
                decoratorFactory,
                customSqlProvider);
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
     */
    @NotNull
    protected String capitalize(@NotNull final String value)
    {
        return
            capitalize(value, DecorationUtils.getInstance());
    }
    
    /**
     * Capitalizes given value.
     * @param value the value.
     * @param decorationUtils the <code>DecorationUtils</code> instance.
     * @return the alternate version of the value.
     */
    @NotNull
    protected String capitalize(
        @NotNull final String value, @NotNull final DecorationUtils decorationUtils)
    {
        return decorationUtils.capitalize(lowercase(value));
    }
    
    /**
     * Converts given value to upper-case.
     * @param value the value.
     * @return the alternate version of the value.
     */
    @NotNull
    protected String uppercase(@NotNull final String value)
    {
        return uppercase(value, DecorationUtils.getInstance());
    }
    
    /**
     * Converts given value to upper-case.
     * @param value the value.
     * @param decorationUtils the <code>DecorationUtils</code> instance.
     * @return the alternate version of the value.
     */
    @NotNull
    protected String uppercase(
        @NotNull final String value, @NotNull final DecorationUtils decorationUtils)
    {
        return decorationUtils.upperCase(value);
    }
    
    /**
     * Normalizes given value to lower-case.
     * @param value the value.
     * @return the alternate version of the value.
     */
    @NotNull
    protected String normalizeLowercase(@NotNull final String value)
    {
        return normalizeLowercase(value, DecorationUtils.getInstance());
    }
    
    /**
     * Normalizes given value to lower-case.
     * @param value the value.
     * @param decorationUtils the <code>DecorationUtils</code> instance.
     * @return the alternate version of the value.
     */
    @NotNull
    protected String normalizeLowercase(
        @NotNull final String value, @NotNull final DecorationUtils decorationUtils)
    {
        return decorationUtils.normalizeLowercase(value);
    }
    
    /**
     * Normalizes given value.
     * @param value the value.
     * @return the alternate version of the value.
     */
    @NotNull
    protected String normalize(@NotNull final String value)
    {
        return normalize(value, DecorationUtils.getInstance());
    }
    
    /**
     * Normalizes given value.
     * @param value the value.
     * @param decorationUtils the <code>DecorationUtils</code> instance.
     * @return the alternate version of the value.
     */
    @NotNull
    protected String normalize(
        @NotNull final String value, @NotNull final DecorationUtils decorationUtils)
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
     */
    @NotNull
    protected String lowercase(@NotNull final String value)
    {
        return lowercase(value, DecorationUtils.getInstance());
    }

    /**
     * Converts given value to lower-case.
     * @param value the value.
     * @param decorationUtils the <code>DecorationUtils</code> instance.
     * @return the alternate version of the value.
     */
    @NotNull
    protected String lowercase(
        @NotNull final String value, @NotNull final DecorationUtils decorationUtils)
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
        return uncapitalize(getName(), DecorationUtils.getInstance());
    }

    /**
     * Uncapitalizes given value.
     * @param value the value.
     * @param decorationUtils the <code>DecorationUtils</code> instance.
     * @return the modified version of the value.
     */
    @NotNull
    protected String uncapitalize(
        @NotNull final String value, @NotNull final DecorationUtils decorationUtils)
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
     * Retrieves the value-object name associated to the table name, uncapitalized.
     * @return such name.
     */
    @NotNull
    public String getVoNameUncapitalized()
    {
        return uncapitalize(getVoName(), DecorationUtils.getInstance());
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
        return getSingularNameCapitalized(getName(), DecorationUtils.getInstance());
    }

    /**
     * Retrieves the table's name once normalized.
     * @param name the table name.
     * @param decorationUtils the {@link DecorationUtils} instance.
     * @return such information.
     */
    @NotNull
    protected String getSingularNameCapitalized(
        @NotNull final String name, @NotNull final DecorationUtils decorationUtils)
    {
        return decorationUtils.capitalize(decorationUtils.getSingular(name));
    }

    /**
     * Retrieves the singular of given word.
     * @param word the word.
     * @return the singular.
     */
    @NotNull
    protected String getSingular(@NotNull final String word)
    {
        return getSingular(word, SingularPluralFormConverter.getInstance());
    }

    /**
     * Retrieves the singular of given word.
     * @param word the word.
     * @param singularPluralFormConverter the
     * <code>SingularPluralFormConverter</code> instance.
     * @return the singular.
     */
    @NotNull
    protected String getSingular(
        @NotNull final String word,
        @NotNull final EnglishGrammarUtils singularPluralFormConverter)
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
        @NotNull final String name,
        @NotNull final List<Attribute> attributes,
        @Nullable final Table parentTable,
        @NotNull final MetadataManager metadataManager,
        @NotNull final DecoratorFactory decoratorFactory,
        @NotNull final TableDecoratorHelper tableDecoratorHelper)
    {
        @NotNull List<Attribute> result = attributes;

        if  (parentTable != null)
        {
            result =
                tableDecoratorHelper.removeReadOnly(
                    decorateAttributes());
        }

        Collections.sort(result);

        return result;
    }

    /**
     * Retrieves the non-read-only attributes, including parent's.
     * @return such attributes.
     */
    @SuppressWarnings("unused")
    @NotNull
    public List<Attribute> getAllNonReadOnlyAttributes()
    {
        return
            getAllNonReadOnlyAttributes(
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
    protected List<Attribute> getAllNonReadOnlyAttributes(
        @NotNull final String name,
        @NotNull final List<Attribute> attributes,
        @Nullable final Table parentTable,
        @NotNull final MetadataManager metadataManager,
        @NotNull final DecoratorFactory decoratorFactory,
        @NotNull final TableDecoratorHelper tableDecoratorHelper)
    {
        @NotNull List<Attribute> result = attributes;

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

        Collections.sort(result);

        return result;
    }

    /**
     * Retrieves the non-read-only attributes.
     * @return such attributes.
     */
    @NotNull
    public List<Attribute> getAllNonReadOnlyButExternallyManagedAttributes()
    {
        return
            getAllNonReadOnlyButExternallyManagedAttributes(
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
    protected List<Attribute> getAllNonReadOnlyButExternallyManagedAttributes(
        @NotNull final String name,
        @NotNull final List<Attribute> attributes,
        @Nullable final Table parentTable,
        @NotNull final MetadataManager metadataManager,
        @NotNull final DecoratorFactory decoratorFactory,
        @NotNull final TableDecoratorHelper tableDecoratorHelper)
    {
        List<Attribute> result =
            getNonReadOnlyAttributes(
                name, attributes, parentTable, metadataManager, decoratorFactory, tableDecoratorHelper);

        result = tableDecoratorHelper.removeExternallyManaged(result);

        Collections.sort(result);

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
        @NotNull List<Attribute> result = attributes;

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

        Collections.sort(result);

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
        @NotNull List<Attribute> result = attributes;

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

        Collections.sort(result);

        return result;
    }

    /**
     * Decorates the attributes.
     * @return the decorated attributes.
     */
    @NotNull
    protected List<Attribute> decorateAttributes()
    {
        return
            decorateAttributes(
                getName(), getMetadataManager(), getDecoratorFactory());
    }
    
    /**
     * Decorates the attributes.
     * @param attributes the attributes.
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @return the decorated attributes.
     */
    @NotNull
    protected List<Attribute> decorateAttributes(
        @NotNull final List<Attribute> attributes,
        @NotNull final MetadataManager metadataManager,
        @NotNull final DecoratorFactory decoratorFactory)
    {
        return decoratorFactory.decorateAttributes(attributes, metadataManager);
    }

    /**
     * Decorates the attributes.
     * @param name the table name.
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @return the decorated attributes.
     */
    @NotNull
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
                getName(), getMetadataManager(), getDecoratorFactory(), getCustomSqlProvider());
    }

    /**
     * Retrieves the attributes.
     * @param table the table name.
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @param customSqlProvider the {@link CustomSqlProvider} instance.
     * @return such information.
     */
    @NotNull
    protected List<Attribute> getAllAttributes(
        @NotNull final String table,
        @NotNull final MetadataManager metadataManager,
        @NotNull final DecoratorFactory decoratorFactory,
        @NotNull final CustomSqlProvider customSqlProvider)
    {
        @NotNull final List<Attribute> result = new ArrayList<Attribute>();

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
                        t_ParentTable, metadataManager, decoratorFactory, customSqlProvider);
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

        Collections.sort(result);

        return result;
    }

    /**
     * Retrieves the child attributes.
     * @return such list.
     */
    @NotNull
    protected List<Attribute> retrieveChildAttributes()
    {
        @Nullable List<Attribute> result = getChildAttributes();

        if (result == null)
        {
            result = new ArrayList<Attribute>(0);
        }

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
                retrieveChildAttributes(),
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
    @NotNull
    protected List<Attribute> getAttributes(
        @NotNull final String table,
        @NotNull final List<Attribute> childAttributes,
        @NotNull final MetadataManager metadataManager,
        @NotNull final DecoratorFactory decoratorFactory,
        final boolean attributesShouldBeCleanedUp)
    {
        @NotNull List<Attribute> result = super.getAttributes();

        if (!alreadyDecorated(result))
        {
            result =
                decorateAttributes(
                    result, metadataManager, decoratorFactory);
        
            if  (attributesShouldBeCleanedUp)
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

        Collections.sort(result);

        return result;
    }

    /**
     * Retrieves whether the attributes are already decorated or not.
     * @param attributes the {@link Attribute} list.
     * @return <code>true</code> in such case.
     */
    protected boolean alreadyDecorated(@NotNull final List<Attribute> attributes)
    {
        boolean result = false;

        for (@Nullable Attribute t_Attribute : attributes)
        {
            if (t_Attribute instanceof AttributeDecorator)
            {
                result = true;
                break;
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
    @NotNull
    public List<Attribute> removeOverridden(
        @NotNull final List<Attribute> firstAttributes,
        @NotNull final List<Attribute> secondAttributes,
        @Nullable final String parentTableName,
        @NotNull final MetadataManager metadataManager,
        @NotNull final TableDecoratorHelper tableDecoratorHelper)
    {
        List<Attribute> result =
            tableDecoratorHelper.removeOverridden(
                firstAttributes,
                secondAttributes,
                parentTableName,
                metadataManager);

        Collections.sort(result);

        return result;
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
                retrieveChildAttributes(),
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
     * @param customSqlProvider the {@link CustomSqlProvider} instance.
     * @return such decorator.
     */
    @SuppressWarnings("unused")
    @Nullable
    protected abstract TableDecorator createTableDecorator(
        @Nullable final String parentTable,
        @NotNull final List<Attribute> primaryKey,
        @NotNull final List<Attribute> attributes,
        final boolean isStatic,
        final boolean voDecorated,
        @NotNull final MetadataManager metadataManager,
        @NotNull final DecoratorFactory decoratorFactory,
        @NotNull final CustomSqlProvider customSqlProvider);
    
    /**
     * Creates a table decorator.
     * @param parentTable the parent table name.
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @param customSqlProvider the {@link CustomSqlProvider} instance.
     * @return such decorator.
     */
    @Nullable
    protected abstract TableDecorator createTableDecorator(
        @NotNull final Table parentTable,
        @NotNull final MetadataManager metadataManager,
        @NotNull final DecoratorFactory decoratorFactory,
        @NotNull final CustomSqlProvider customSqlProvider);

    /**
     * Retrieves the list of non-parent, non-externally-managed
     * attributes.
     * @return such list.
     */
    @NotNull
    public List<Attribute> getNonParentNonExternallyManagedAttributes()
    {
        return
            getNonParentNonExternallyManagedAttributes(
                getNonParentAttributes());
    }

    /**
     * Retrieves the list of non-parent, non-externally-managed
     * attributes.
     * @param nonParentAttributes the non-parent attributes.
     * @return such list.
     */
    @NotNull
    protected List<Attribute> getNonParentNonExternallyManagedAttributes(
        @NotNull final List<Attribute> nonParentAttributes)
    {
        List<Attribute> result = new ArrayList<Attribute>();

        for (@Nullable Attribute t_Attribute : nonParentAttributes)
        {
            if  (   (t_Attribute != null)
                 && (!t_Attribute.isExternallyManaged()))
            {
                result.add(t_Attribute);
            }
        }

        Collections.sort(result);

        return result;
    }

    /**
     * Retrieves the list of non-parent, non-externally-managed
     * attributes.
     * @return such list.
     */
    @NotNull
    public List<Attribute> getNonParentNonExternallyManagedPlusPkAttributes()
    {
        return
            getNonParentNonExternallyManagedPlusPkAttributes(
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
    protected List<Attribute> getNonParentNonExternallyManagedPlusPkAttributes(
        @NotNull final List<Attribute> nonParentAttributes,
        @NotNull final List<Attribute> primaryKey)
    {
        @NotNull final List<Attribute> result = new ArrayList<Attribute>();

        for (@Nullable Attribute t_Attribute : nonParentAttributes)
        {
            if  (   (t_Attribute != null)
                 && (   (!t_Attribute.isExternallyManaged())
                     || (isPartOf(primaryKey, t_Attribute))))
            {
                result.add(t_Attribute);
            }
        }

        Collections.sort(result);

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
                getDecoratorFactory(),
                getCustomSqlProvider());
    }

    /**
     * Retrieves the list of parent's all attributes and the non-parent own attributes.
     * @param parent the parent table, if any.
     * @param nonParentAttributes the non-parent attributes.
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @param customSqlProvider the {@link CustomSqlProvider} instance.
     * @return such list.
     */
    @NotNull
    protected List<Attribute> getAllParentAndNonParentAttributes(
        @Nullable final Table parent,
        @NotNull final List<Attribute> nonParentAttributes,
        @NotNull final MetadataManager metadataManager,
        @NotNull final DecoratorFactory decoratorFactory,
        @NotNull final CustomSqlProvider customSqlProvider)
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
                        parent, metadataManager, decoratorFactory, customSqlProvider);
            }

            if (t_ParentDecorator != null)
            {
                result.addAll(t_ParentDecorator.getAllAttributes());
            }
        }

        result.addAll(nonParentAttributes);

        Collections.sort(result);

        return result;
    }

    /**
     * Retrieves the list of parent's all attributes and the non-parent
     * non-managed-externally own attributes.
     * @return such list.
     */
    @NotNull
    public List<Attribute> getAllParentAndNonParentNonExternallyManagedAttributes()
    {
        return
            getAllParentAndNonParentNonExternallyManagedAttributes(
                getParentTable(),
                getNonParentNonExternallyManagedAttributes(),
                getMetadataManager(),
                getDecoratorFactory(),
                getCustomSqlProvider());
    }

    /**
     * Retrieves the list of parent's all attributes and the non-parent
     * non-managed-externally own attributes.
     * @param parent the parent.
     * @param nonParentNonExternallyManagedAttributes the non-parent, non-managed-externally
     * own attributes.
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @param customSqlProvider the {@link CustomSqlProvider} instance.
     * @return such list.
     */
    @NotNull
    protected List<Attribute> getAllParentAndNonParentNonExternallyManagedAttributes(
        @Nullable final Table parent,
        @NotNull final List<Attribute> nonParentNonExternallyManagedAttributes,
        @NotNull final MetadataManager metadataManager,
        @NotNull final DecoratorFactory decoratorFactory,
        @NotNull final CustomSqlProvider customSqlProvider)
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
                        parent, metadataManager, decoratorFactory, customSqlProvider);
            }

            if (t_ParentDecorator != null)
            {
                result.addAll(t_ParentDecorator.getAllNonExternallyManagedAttributes());
            }
        }

        result.addAll(nonParentNonExternallyManagedAttributes);

        Collections.sort(result);

        return result;
    }

    /**
     * Retrieves all attributes, including the parent's, but not the externally-managed.
     * @return such attributes.
     */
    @NotNull
    public List<Attribute> getAllNonExternallyManagedAttributes()
    {
        return getAllNonExternallyManagedAttributes(getAllAttributes());
    }

    /**
     * Retrieves all attributes, including the parent's, but not the externally-managed.
     * @param allAttributes the attributes.
     * @return such attributes.
     */
    @NotNull
    protected List<Attribute> getAllNonExternallyManagedAttributes(@NotNull final List<Attribute> allAttributes)
    {
        @NotNull final List<Attribute> result = new ArrayList<Attribute>();

        for (@Nullable Attribute t_Attribute : allAttributes)
        {
            if  (   (t_Attribute != null)
                 && (!t_Attribute.isExternallyManaged()))
            {
                result.add(t_Attribute);
            }
        }

        Collections.sort(result);

        return result;
    }

    /**
     * Retrieves all attributes, including the parent's, but not the externally-managed,
     * plus the primary key.
     * @return such attributes.
     */
    @NotNull
    public List<Attribute> getAllNonExternallyManagedPlusPkAttributes()
    {
        return getAllNonExternallyManagedPlusPkAttributes(getAllAttributes(), getPrimaryKey());
    }

    /**
     * Retrieves all attributes, including the parent's, but not the externally-managed,
     * plus the primary key.
     * @param allAttributes the attributes.
     * @param primaryKey the primary key.
     * @return such attributes.
     */
    @NotNull
    protected List<Attribute> getAllNonExternallyManagedPlusPkAttributes(
        @NotNull final List<Attribute> allAttributes, @NotNull final List<Attribute> primaryKey)
    {
        @NotNull final List<Attribute> result = new ArrayList<Attribute>();

        for (@Nullable Attribute t_Attribute : allAttributes)
        {
            if  (   (t_Attribute != null)
                 && (   (!t_Attribute.isExternallyManaged())
                     || (isPartOf(primaryKey, t_Attribute))))
            {
                result.add(t_Attribute);
            }
        }

        Collections.sort(result);

        return result;
    }

    /**
     * Retrieves all attributes, including the parent's, but not the externally-managed,
     * plus the primary key.
     * @return such attributes.
     */
    @NotNull
    public List<Attribute> getAllNonExternallyManagedNonReadOnlyPlusPkAttributes()
    {
        return getAllNonExternallyManagedNonReadOnlyPlusPkAttributes(getAllAttributes(), getPrimaryKey());
    }

    /**
     * Retrieves all attributes, including the parent's, but not the externally-managed,
     * or read-only, plus the primary key.
     * @param allAttributes the attributes.
     * @param primaryKey the primary key.
     * @return such attributes.
     */
    @NotNull
    protected List<Attribute> getAllNonExternallyManagedNonReadOnlyPlusPkAttributes(
        @NotNull final List<Attribute> allAttributes, @NotNull final List<Attribute> primaryKey)
    {
        @NotNull final List<Attribute> result = new ArrayList<Attribute>();

        for (@Nullable Attribute t_Attribute : allAttributes)
        {
            if  (   (t_Attribute != null)
                 && (   (   (!t_Attribute.isExternallyManaged())
                         && (!t_Attribute.isReadOnly()))
                     || (isPartOf(primaryKey, t_Attribute))))
            {
                result.add(t_Attribute);
            }
        }

        Collections.sort(result);

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
    public List<Attribute> getAllParentAndNonParentNonExternallyManagedNonReadOnlyAttributes()
    {
        @NotNull List<Attribute> result =
            getAllParentAndNonParentNonExternallyManagedAttributes();

        result = removeReadOnly(result, TableDecoratorHelper.getInstance());

        Collections.sort(result);

        return result;
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
        @NotNull List<Attribute> result = getAllParentAndNonParentAttributes();

        result = removeReadOnly(result, TableDecoratorHelper.getInstance());

        Collections.sort(result);

        return result;
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
        List<Attribute> result =
            tableDecoratorHelper.removeNonReadOnlyAttributes(
                getAllParentAndNonParentAttributes());

        Collections.sort(result);

        return result;
    }

    /**
     * Retrieves the list of parent's all attributes and the non-parent
     * non-managed-externally, non-read-only own attributes, including the primary key.
     * @return such list.
     */
    @NotNull
    public List<Attribute> getAllParentAndNonParentNonExternallyManagedNonReadOnlyPlusPkAttributes()
    {
        return
            getAllParentAndNonParentNonExternallyManagedNonReadOnlyPlusPkAttributes(
                getParentTable(),
                getNonParentNonExternallyManagedPlusPkAttributes(),
                getMetadataManager(),
                getDecoratorFactory(),
                getCustomSqlProvider(),
                TableDecoratorHelper.getInstance());
    }

    /**
     * Retrieves the list of parent's all attributes and the non-parent
     * non-managed-externally , non-read-only, own attributes, plus the primary key.
     * @param parent the parent.
     * @param nonParentNonExternallyManagedPlusPkAttributes the non-parent, non-managed-externally
     * own attributes, plus the primary key.
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @param customSqlProvider the {@link CustomSqlProvider} instance.
     * @param tableDecoratorHelper the <code>TableDecoratorHelper</code> instance.
     * @return such list.
     */
    @NotNull
    protected List<Attribute> getAllParentAndNonParentNonExternallyManagedNonReadOnlyPlusPkAttributes(
        @Nullable final Table parent,
        @NotNull final List<Attribute> nonParentNonExternallyManagedPlusPkAttributes,
        @NotNull final MetadataManager metadataManager,
        @NotNull final DecoratorFactory decoratorFactory,
        @NotNull final CustomSqlProvider customSqlProvider,
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
                        parent, metadataManager, decoratorFactory, customSqlProvider);
            }

            if (t_ParentDecorator != null)
            {
                result.addAll(
                    t_ParentDecorator.getAllNonExternallyManagedNonReadOnlyPlusPkAttributes());
            }
        }

        result.addAll(
            tableDecoratorHelper.removeReadOnly(
                nonParentNonExternallyManagedPlusPkAttributes));

        Collections.sort(result);

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

    /**
     * Retrieves the static content.
     * @return such information.
     */
    @Override
    @NotNull
    public List<Row> getStaticContent()
    {
        List<Row> result = null;

        try
        {
            result =
                retrieveStaticContent(
                    getTable().getName(),
                    getMetadataManager(),
                    getDecoratorFactory(),
                    DAOTemplateUtils.getInstance());
        }
        catch (@NotNull final SQLException invalidQuery)
        {
            @Nullable Log t_Log = UniqueLogFactory.getLog(AbstractTableDecorator.class);

            if (t_Log != null)
            {
                t_Log.error(
                    "Cannot retrieve contents in table " + getTable().getName(),
                    invalidQuery);
            }
        }

        if (result == null)
        {
            result = new ArrayList<Row>(0);
        }

        return result;
    }
    /**
     * Retrieves the static values of given table.
     * @param tableName the table name.
     * @param metadataManager the {@link MetadataManager} instance.
     * @param decoratorFactory the decorator factory.
     * @param daoTemplateUtils the {@link DAOTemplateUtils} instance.
     * @return such information.
     * @throws SQLException if the operation fails.
     */
    @NotNull
    protected List<Row> retrieveStaticContent(
        @NotNull final String tableName,
        @NotNull final MetadataManager metadataManager,
        @NotNull final DecoratorFactory decoratorFactory,
        @NotNull final DAOTemplateUtils daoTemplateUtils)
        throws SQLException
    {
        List<Row> result =
            daoTemplateUtils.queryContents(
                tableName, metadataManager, decoratorFactory);

        if (result == null)
        {
            result = new ArrayList<Row>(0);
        }

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @NotNull
    public List<Sql> getDynamicQueries()
    {
        return getDynamicQueries(getTable(), getCustomSqlProvider());
    }

    /**
     * Retrieves the dynamic queries.
     * @param table the table.
     * @param customSqlProvider the {@link CustomSqlProvider} instance.
     * @return the list of dynamic queries.
     */
    @NotNull
    protected List<Sql> getDynamicQueries(
        @NotNull final Table table, @NotNull final CustomSqlProvider customSqlProvider)
    {
        return getDynamicQueries(table.getName(), customSqlProvider.getSqlDAO());
    }

    /**
     * Retrieves the dynamic queries.
     * @param tableName the table name.
     * @param sqlDAO the {@link SqlDAO} instance.
     * @return the list of dynamic queries.
     */
    @NotNull
    protected List<Sql> getDynamicQueries(
        @NotNull final String tableName, @NotNull final SqlDAO sqlDAO)
    {
        List<Sql> result = sqlDAO.findDynamic(tableName);

        Collections.sort(result);

        return result;
    }

    /**
     * Retrieves all non-primary-key attributes.
     * @return such list.
     */
    @NotNull
    public List<Attribute> getNonPrimaryKeyAttributes()
    {
        return filterAttributes(getAttributes(), getPrimaryKey());
    }

    /**
     * Retrieves the read-only attributes.
     * @return such list.
     */
    @NotNull
    public List<Attribute> getReadOnlyAttributes()
    {
        return filterReadOnlyAttributes(getAttributes());
    }

    /**
     * Retrieves the read-only attributes from given list.
     * @param attributes the attribute list.
     * @return the read-only subset.
     */
    @NotNull
    protected List<Attribute> filterReadOnlyAttributes(@NotNull final List<Attribute> attributes)
    {
        @NotNull final List<Attribute> result =  new ArrayList<Attribute>(0);

        for (@Nullable Attribute t_Attribute : attributes)
        {
            if (   (t_Attribute != null)
                && (t_Attribute.isReadOnly()))
            {
                result.add(t_Attribute);
            }
        }

        return result;
    }

    /**
     * Retrieves all non-primary-key, non-read-only attributes.
     * @return such list.
     */
    @SuppressWarnings("unused")
    @NotNull
    public List<Attribute> getNonPrimaryKeyNonReadOnlyAttributes()
    {
        return filterAttributes(filterAttributes(getAttributes(), getPrimaryKey()), getReadOnlyAttributes());
    }

    /**
     * Filters certain attributes.
     * @param attributes the attributes.
     * @param toExclude the attributes to exclude.
     * @return such list.
     */
    @NotNull
    protected List<Attribute> filterAttributes(
        @NotNull final List<Attribute> attributes, @NotNull final List<Attribute> toExclude)
    {
        @NotNull List<Attribute> result = new ArrayList<Attribute>(attributes);

        result.removeAll(toExclude);

        Collections.sort(result);

        return result;
    }

    /**
     * Retrieves the custom selects.
     * @return such list of {@link Sql} elements.
     */
    @SuppressWarnings("unused")
    @NotNull
    public List<Sql> getCustomSelects()
    {
        return decorate(getCustomSelects(getTable(), getCustomSqlProvider()));
    }

    /**
     * Retrieves the custom selects.
     * @param table the table.
     * @param customSqlProvider the {@link CustomSqlProvider} instance.
     * @return such list of {@link Sql} elements.
     */
    @NotNull
    protected List<Sql> getCustomSelects(@NotNull final Table table, @NotNull final CustomSqlProvider customSqlProvider)
    {
        return getCustomSelects(table, customSqlProvider.getSqlDAO());
    }

    /**
     * Retrieves the custom selects.
     * @param table the table.
     * @param sqlDAO the {@link SqlDAO} instance.
     * @return such list of {@link Sql} elements.
     */
    @NotNull
    protected List<Sql> getCustomSelects(@NotNull final Table table, @NotNull final SqlDAO sqlDAO)
    {
        List<Sql> result = sqlDAO.findSelects(table.getName());

        Collections.sort(result);

        return result;
    }

    /**
     * Retrieves the custom select-for-update queries.
     * @return such list of {@link Sql} elements.
     */
    @SuppressWarnings("unused")
    @NotNull
    public List<Sql> getCustomSelectsForUpdate()
    {
        return decorate(getCustomSelectsForUpdate(getTable(), getCustomSqlProvider()));
    }

    /**
     * Retrieves the custom select-for-update queries.
     * @param table the table.
     * @param customSqlProvider the {@link CustomSqlProvider} instance.
     * @return such list of {@link Sql} elements.
     */
    @NotNull
    protected List<Sql> getCustomSelectsForUpdate(
        @NotNull final Table table, @NotNull final CustomSqlProvider customSqlProvider)
    {
        return getCustomSelectsForUpdate(table, customSqlProvider.getSqlDAO());
    }

    /**
     * Retrieves the custom select-for-update queries.
     * @param table the table.
     * @param sqlDAO the {@link SqlDAO} instance.
     * @return such list of {@link Sql} elements.
     */
    @NotNull
    protected List<Sql> getCustomSelectsForUpdate(@NotNull final Table table, @NotNull final SqlDAO sqlDAO)
    {
        List<Sql> result = sqlDAO.findSelectsForUpdate(table.getName());

        Collections.sort(result);

        return result;
    }

    /**
     * Decorates given {@link Sql queries.}
     * @param queries the queries to decorate.
     * @return the decorated queries.
     */
    @NotNull
    protected List<Sql> decorate(@NotNull final List<Sql> queries)
    {
        return decorate(queries, getCustomSqlProvider(), getMetadataManager());
    }

    /**
     * Decorates given {@link Sql queries.}
     * @param queries the queries to decorate.
     * @param customSqlProvider the {@link CustomSqlProvider} instance.
     * @param metadataManager the {@link MetadataManager} instance.
     * @return the decorated queries.
     */
    @NotNull
    protected List<Sql> decorate(
        @NotNull final List<Sql> queries,
        @NotNull final CustomSqlProvider customSqlProvider,
        @NotNull final MetadataManager metadataManager)
    {
        @NotNull List<Sql> result = new ArrayList<Sql>(queries.size());

        for (@Nullable Sql t_Sql : queries)
        {
            if (t_Sql != null)
            {
                result.add(decorate(t_Sql, customSqlProvider, metadataManager));
            }
        }

        return result;
    }

    /**
     * Decorates given {@link Sql query}.
     * @param query the query to decorate.
     * @param customSqlProvider the {@link CustomSqlProvider} instance.
     * @param metadataManager the {@link MetadataManager} instance.
     * @return the decorated query.
     */
    @NotNull
    protected Sql decorate(
        @NotNull final Sql query,
        @NotNull final CustomSqlProvider customSqlProvider,
        @NotNull final MetadataManager metadataManager)
    {
        return new CachingSqlDecorator(query, customSqlProvider, metadataManager);
    }

    /**
     * Retrieves the custom updates or inserts.
     * @return such information.
     */
    @NotNull
    public List<Sql> getCustomUpdatesOrInserts()
    {
        return decorate(getCustomUpdatesOrInserts(getTable(), getCustomSqlProvider()));
    }

    /**
     * Retrieves the custom updates or inserts.
     * @return such information.
     */
    @NotNull
    protected List<Sql> getCustomUpdatesOrInserts(
        @NotNull final Table table, @NotNull final CustomSqlProvider customSqlProvider)
    {
        return getCustomUpdatesOrInserts(table.getName(), customSqlProvider.getSqlDAO());
    }

    /**
     * Retrieves the custom updates or inserts.
     * @param tableName the table name.
     * @param sqlDAO the {@link SqlDAO} instance.
     * @return such information.
     */
    @NotNull
    protected List<Sql> getCustomUpdatesOrInserts(
        @NotNull final String tableName, @NotNull final SqlDAO sqlDAO)
    {
        @NotNull final List<Sql> result = new ArrayList<Sql>(sqlDAO.findInserts(tableName));

        result.addAll(sqlDAO.findUpdates(tableName));

        Collections.sort(result);

        return result;
    }

    /**
     * Retrieves the custom results.
     * @return such list of {@link ResultDecorator} elements.
     */
    @SuppressWarnings("unused")
    @NotNull
    public List<ResultDecorator> getCustomResults()
    {
        return getCustomResults(getTable(), getCustomSqlProvider(), getMetadataManager(), getDecoratorFactory());
    }


    /**
     * Retrieves the custom results.
     * @param table the {@link Table} instance.
     * @param customSqlProvider the {@link CustomSqlProvider} instance.
     * @param metadataManager the {@link MetadataManager} instance.
     * @param decoratorFactory the {@link DecoratorFactory} instance.
     * @return such list of {@link Result} elements.
     */
    @NotNull
    protected List<ResultDecorator> getCustomResults(
        @NotNull final Table table,
        @NotNull final CustomSqlProvider customSqlProvider,
        @NotNull final MetadataManager metadataManager,
        @NotNull final DecoratorFactory decoratorFactory)
    {
        return
            getCustomResults(
                table.getName(),
                customSqlProvider.getSqlDAO(),
                customSqlProvider.getSqlResultDAO(),
                customSqlProvider,
                metadataManager,
                decoratorFactory);
    }

    /**
     * Retrieves the custom results.
     * @param tableName the table name.
     * @param sqlDAO the {@link SqlDAO} instance.
     * @param resultDAO the {@link SqlResultDAO} instance.
     * @param customSqlProvider the {@link CustomSqlProvider} instance.
     * @param metadataManager the {@link MetadataManager} instance.
     * @param decoratorFactory the {@link DecoratorFactory} instance.
     * @return such list of {@link Result} elements.
     */
    @NotNull
    protected List<ResultDecorator> getCustomResults(
        @NotNull final String tableName,
        @NotNull final SqlDAO sqlDAO,
        @NotNull final SqlResultDAO resultDAO,
        @NotNull final CustomSqlProvider customSqlProvider,
        @NotNull final MetadataManager metadataManager,
        @NotNull final DecoratorFactory decoratorFactory)
    {
        List<ResultDecorator> result;

        List<Result> aux = new ArrayList<Result>(2);

        @Nullable Result singleResult = resultDAO.findSingleMatch(tableName);

        if (singleResult != null)
        {
            aux.add(singleResult);
        }

        @Nullable Result multipleResult = resultDAO.findMultipleMatch(tableName);

        if (   (multipleResult == null)
            && (singleResult != null))
        {
            multipleResult =
                new ResultElement(
                    singleResult.getId().replaceFirst("single\\.", "multiple\\."),
                    singleResult.getClassValue(),
                    Result.MULTIPLE);

            aux.add(multipleResult);
        }
        else if (   (multipleResult != null)
                 && (singleResult == null))
        {
            singleResult =
                new ResultElement(
                    multipleResult.getId().replaceFirst("multiple\\.", "single\\."),
                    multipleResult.getClassValue(),
                    Result.MULTIPLE);

            aux.add(singleResult);
        }

        @Nullable Result customResult;

        List<Sql> sqlList = sqlDAO.findSelects(tableName);

        for (Sql t_Sql : sqlList)
        {
            customResult = resultDAO.findBySqlId(t_Sql.getId());

            if (   (customResult != null)
                && (!aux.contains(customResult)))
            {
                aux.add(customResult);
            }
        }

        sqlList = sqlDAO.findSelectsForUpdate(tableName);

        for (Sql t_Sql : sqlList)
        {
            customResult = resultDAO.findBySqlId(t_Sql.getId());

            if (   (customResult != null)
                && (!aux.contains(customResult)))
            {
                aux.add(customResult);
            }
        }

        result = new ArrayList<ResultDecorator>(aux.size());

        for (@NotNull Result t_Result : aux)
        {
            result.add(decorate(t_Result, customSqlProvider, metadataManager, decoratorFactory));
        }

        Collections.sort(result);

        return result;
    }

    /**
     * Decorates given result.
     * @param customResult the {@link Result} element.
     * @param customSqlProvider the {@link CustomSqlProvider} instance.
     * @param metadataManager the {@link MetadataManager} instance.
     * @param decoratorFactory the {@link DecoratorFactory} instance.
     * @return the decorated version.
     */
    @NotNull
    protected ResultDecorator decorate(
        @NotNull final Result customResult,
        @NotNull final CustomSqlProvider customSqlProvider,
        @NotNull final MetadataManager metadataManager,
        @NotNull final DecoratorFactory decoratorFactory)
    {
        return new CachingResultDecorator(customResult, customSqlProvider, metadataManager, decoratorFactory);
    }

    /**
     * Decorates given foreign keys.
     * @param foreignKeys the {@link ForeignKey} list.
     * @param metadataManager the {@link MetadataManager} instance.
     * @param decoratorFactory the {@link DecoratorFactory} instance.
     * @param customSqlProvider the {@link CustomSqlProvider} instance.
     * @return the decorated version.
     */
    @NotNull
    protected List<ForeignKey> decorate(
        @NotNull final List<ForeignKey> foreignKeys,
        @NotNull final MetadataManager metadataManager,
        @NotNull final DecoratorFactory decoratorFactory,
        @NotNull final CustomSqlProvider customSqlProvider)
    {
        @NotNull final List<ForeignKey> result = new ArrayList<ForeignKey>(foreignKeys.size());

        for (@Nullable ForeignKey t_ForeignKey : foreignKeys)
        {
            if (t_ForeignKey != null)
            {
                result.add(decorate(t_ForeignKey, metadataManager, decoratorFactory, customSqlProvider));
            }
        }

        return result;
    }

    /**
     * Decorates given foreign key.
     * @param foreignKey the {@link ForeignKey}.
     * @param metadataManager the {@link MetadataManager} instance.
     * @param decoratorFactory the {@link DecoratorFactory} instance.
     * @param customSqlProvider the {@link CustomSqlProvider} instance.
     * @return the decorated version.
     */
    @NotNull
    protected ForeignKeyDecorator decorate(
        @NotNull final ForeignKey foreignKey,
        @NotNull final MetadataManager metadataManager,
        @NotNull final DecoratorFactory decoratorFactory,
        @NotNull final CustomSqlProvider customSqlProvider)
    {
        return new CachingForeignKeyDecorator(foreignKey, metadataManager, decoratorFactory, customSqlProvider);
    }

    /**
     * Retrieves the externally-managed attributes.
     * @return such information.
     */
    @SuppressWarnings("unused")
    @NotNull
    public List<AttributeDecorator> getAllExternallyManagedAttributes()
    {
        return getAllExternallyManagedAttributes(getAllParentAndNonParentAttributes(), getMetadataManager());
    }


    /**
     * Retrieves the externally-managed attributes.
     * @param attributes the attributes (including parent's).
     * @param metadataManager the {@link MetadataManager} instance.
     * @return such information.
     */
    @NotNull
    protected List<AttributeDecorator> getAllExternallyManagedAttributes(
        @NotNull final List<Attribute> attributes, @NotNull final MetadataManager metadataManager)
    {
        List<AttributeDecorator> result = new ArrayList<AttributeDecorator>();

        for (@Nullable Attribute t_Attribute : attributes)
        {
            if (   (t_Attribute != null)
                && (t_Attribute.isExternallyManaged()))
            {
                result.add(decorate(t_Attribute, metadataManager));
            }
        }

        Collections.sort(result);

        return result;
    }

    /**
     * Decorates given attribute.
     * @param attribute the attribute to decorate.
     * @param metadataManager the {@link MetadataManager} instance.
     * @return the decorated attribute.
     */
    @NotNull
    protected AttributeDecorator decorate(
        @NotNull final Attribute attribute, @NotNull final MetadataManager metadataManager)
    {
        return new CachingAttributeDecorator(attribute, metadataManager);
    }

    /**
     * Retrieves the list of different results defined for this table (using the referring custom-selects).
     * @return such list.
     */
    @SuppressWarnings("unused")
    @NotNull
    public List<Result> getDifferentCustomResults()
    {
        return getDifferentCustomResults(getTable(), getCustomSqlProvider());
    }

    /**
     * Retrieves the list of different results defined for this table (using the referring custom-selects).
     * @return such list.
     */
    @NotNull
    protected List<Result> getDifferentCustomResults(
        @NotNull final Table table, @NotNull final CustomSqlProvider customSqlProvider)
    {
        return
            getDifferentCustomResults(
                table.getName(),
                getVoName(),
                customSqlProvider.getSqlDAO(),
                customSqlProvider.getSqlResultDAO(),
                getMetadataManager(),
                customSqlProvider,
                getDecoratorFactory(),
                CustomResultUtils.getInstance());
    }

    /**
     * Retrieves the list of different results defined for this table (using the referring custom-selects).
     * @param table the {@link Table} instance.
     * @param voName the ValueObject name.
     * @param sqlDAO the {@link SqlDAO} instance.
     * @param resultDAO the {@link SqlResultDAO} instance.
     * @param metadataManager the {@link MetadataManager} instance.
     * @param customSqlProvider the {@link CustomSqlProvider} instance.
     * @param decoratorFactory the {@link DecoratorFactory} implementation.
     * @param customResultUtils the {@link CustomResultUtils} instance.
     * @return such list.
     */
    @NotNull
    protected List<Result> getDifferentCustomResults(
        @NotNull final String table,
        @NotNull final String voName,
        @NotNull final SqlDAO sqlDAO,
        @NotNull final SqlResultDAO resultDAO,
        @NotNull final MetadataManager metadataManager,
        @NotNull final CustomSqlProvider customSqlProvider,
        @NotNull final DecoratorFactory decoratorFactory,
        @NotNull final CustomResultUtils customResultUtils)
    {
        List<Result> result = new ArrayList<Result>();

        @NotNull final List<Sql> t_lSql = sqlDAO.findSelects(table);
        t_lSql.addAll(sqlDAO.findSelectsForUpdate(table));

        for (@Nullable Sql t_Sql : t_lSql)
        {
            if (t_Sql != null)
            {
                @Nullable final ResultRef t_ResultRef = t_Sql.getResultRef();

                if (t_ResultRef != null)
                {
                    @Nullable final Result t_Result = resultDAO.findByPrimaryKey(t_ResultRef.getId());
                    if (   (t_Result != null)
                        && (!matches(t_Result.getClassValue(), voName))
                        && (!result.contains(t_Result)))
                    {
                        result.add(
                            customResultUtils.decorate(
                                t_Result, metadataManager, customSqlProvider, decoratorFactory));
                    }
                }
            }
        }

        Collections.sort(result);

        return result;
    }

    /**
     * Checks whether given ValueObject classes match.
     * @param classValue the class value.
     * @param voName the ValueObject name.
     * @return <code>true</code> in such case.
     */
    protected boolean matches(@Nullable final String classValue, @NotNull final String voName)
    {
        boolean result = false;

        if (classValue != null)
        {
            String[] t_astrParts = classValue.split("\\.");

            if (t_astrParts.length > 0)
            {
                result = voName.equals(t_astrParts[t_astrParts.length - 1]);
            }
        }

        return result;
    }
}
