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
    private List m__lForeignKeys;

    /**
     * The parent foreign key.
     */
    private ForeignKey m__ParentForeignKey;

    /**
     * The child's attributes.
     */
    private List m__lChildAttributes;

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
        final Table table,
        final String name,
        final List<Attribute> primaryKey,
        final List<Attribute> attributes,
        final List<ForeignKey> foreignKeys,
        final Table parentTable,
        final boolean isStatic,
        final boolean voDecorated,
        final MetadataManager metadataManager,
        final DecoratorFactory decoratorFactory)
    {
        super(
            name, table.getComment(), primaryKey, attributes, foreignKeys, parentTable, isStatic, voDecorated);

        immutableSetTable(table);
        immutableSetMetadataManager(metadataManager);
        immutableSetDecoratorFactory(decoratorFactory);
    }

    /**
     * Creates a <code>AbstractTableDecorator</code> instance.
     * @param table the table name.
     * @param primaryKey the primary key.
     * @param attributes the attributes.
     * @param isStatic whether the table is static.
     * @param voDecorated whether the value-object for the table is
     * decorated.
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @param childAttributes the child attributes.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @precondition decoratorFactory != null
     */
    public AbstractTableDecorator(
        final String table,
        final List<Attribute> primaryKey,
        final List<Attribute> attributes,
        final boolean isStatic,
        final boolean voDecorated,
        final MetadataManager metadataManager,
        final List<Attribute> childAttributes,
        final DecoratorFactory decoratorFactory)
    {
        this(
            null,
            table,
            primaryKey,
            attributes,
            new ArrayList<ForeignKey>(0),
            null,
            isStatic,
            voDecorated,
            metadataManager,
            decoratorFactory);

        immutableSetChildAttributes(childAttributes);
    }

    /**
     * Creates a <code>AbstractTableDecorator</code> instance.
     * <code>Table</code> to decorate.
     * @param table the table.
     * @param primaryKey the primary key.
     * @param isStatic whether the table is static.
     * @param voDecorated whether the value-object for the table is
     * decorated.
     * @param metadataManager the metadata manager.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @precondition table != null
     * @precondition primaryKey != null
     * @precondition metadataManager != null
     * @precondition decoratorFactory != null
     */
    protected AbstractTableDecorator(
        final String table,
        final List primaryKey,
        final boolean isStatic,
        final boolean voDecorated,
        final MetadataManager metadataManager,
        final DecoratorFactory decoratorFactory)
    {
        this(
            null,
            table,
            primaryKey,
            new ArrayList<Attribute>(0),
            new ArrayList<ForeignKey>(0),
            null,
            isStatic,
            voDecorated,
            metadataManager,
            decoratorFactory);
    }

    /**
     * Creates a <code>AbstractTableDecorator</code> instance.
     * <code>Table</code> to decorate.
     * @param table the table.
     * @param primaryKey the primary key.
     * @param isStatic whether the table is static.
     * @param voDecorated whether the value-object for the table is
     * decorated.
     * @param metadataManager the metadata manager.
     * @param childAttributes the child attributes.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @precondition table != null
     * @precondition primaryKey != null
     * @precondition metadataManager != null
     * @precondition decoratorFactory != null
     */
    protected AbstractTableDecorator(
        final String table,
        final List<Attribute> primaryKey,
        final boolean isStatic,
        final boolean voDecorated,
        final MetadataManager metadataManager,
        final List childAttributes,
        final DecoratorFactory decoratorFactory)
    {
        this(
            null,
            table,
            primaryKey,
            new ArrayList<Attribute>(0),
            new ArrayList<ForeignKey>(0),
            null,
            isStatic,
            voDecorated,
            metadataManager,
            decoratorFactory);
        immutableSetChildAttributes(childAttributes);
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
        final List childAttributes)
    {
        m__lChildAttributes = childAttributes;
    }

    /**
     * Specifies the child attributes.
     * @param childAttributes the child attributes.
     */
    @SuppressWarnings("unchecked")
    protected void setChildAttributes(
        final List childAttributes)
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

            if  (result != null)
            {
                setForeignKeys(result);
            }
        }

        return result;
    }

    /**
     * Retrieves the foreign keys.
     * @param name the table name.
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @return such foreign keys.
     * @precondition name != null
     * @precondition metadataManager != null
     */
    protected List<ForeignKey> retrieveForeignKeys(
        final String name,
        final MetadataManager metadataManager)
    {
        return
            retrieveForeignKeys(
                name,
                metadataManager,
                metadataManager.getMetadataTypeManager());
    }

    /**
     * Retrieves the foreign keys.
     * @param name the table name.
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @param metadataTypeManager the <code>MetadataTypeManager</code> instance.
     * @return such foreign keys.
     * @precondition name != null
     * @precondition metadataManager != null
     * @precondition metadataTypeManager != null
     */
    protected List<ForeignKey> retrieveForeignKeys(
        final String name,
        final MetadataManager metadataManager,
        final MetadataTypeManager metadataTypeManager)
    {
        List<ForeignKey> result = null;

        String[] t_astrReferredTables = metadataManager.getReferredTables(name);

        int t_iCount =
            (t_astrReferredTables != null) ? t_astrReferredTables.length : 0;

        String t_strReferredTable;

        for  (int t_iIndex = 0; t_iIndex < t_iCount; t_iIndex++)
        {
            t_strReferredTable = t_astrReferredTables[t_iIndex];

            String[][] t_aastrForeignKeys =
                metadataManager.getForeignKeys(name, t_strReferredTable);

            if  (t_aastrForeignKeys != null)
            {
                result =
                    convertToForeignKeyList(
                        name,
                        t_strReferredTable,
                        t_aastrForeignKeys,
                        metadataManager,
                        metadataTypeManager);
            }
        }

        return result;
    }

    /**
     * Converts given foreign key information into a <code>ForeignKey</code> list.
     * @param sourceTable the source table.
     * @param targetTable the target table.
     * @param fks the foreign key information.
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @param metadataTypeManager the <code>MetadataTypeManager</code> instance.
     * @return the list of <code>ForeignKey</code> instance.
     * @precondition sourceTable != null
     * @precondition targetTable != null
     * @precondition fks != null
     * @precondition metadataManager != null
     * @precondition metadataTypeManager != null
     */
    protected List<ForeignKey> convertToForeignKeyList(
        final String sourceTable,
        final String targetTable,
        final String[][] fks,
        final MetadataManager metadataManager,
        final MetadataTypeManager metadataTypeManager)
    {
        List result = new ArrayList();

        int t_iCount = (fks != null) ? fks.length : 0;

        boolean t_bAllowNull = false;

        List t_lAttributes = null;

        String[] t_astrCurrentFk;

        for  (int t_iIndex = 0; t_iIndex < t_iCount; t_iIndex++)
        {
            t_astrCurrentFk = fks[t_iIndex];

            t_bAllowNull = metadataManager.allowsNull(sourceTable, t_astrCurrentFk);

            t_lAttributes = new ArrayList();

            int t_iAttrCount = (t_astrCurrentFk != null) ? t_astrCurrentFk.length : 0;

            for  (int t_iAttrIndex = 0; t_iAttrIndex < t_iAttrCount; t_iAttrIndex++)
            {
                t_lAttributes.add(
                    buildAttributeDecorator(
                        sourceTable,
                        t_astrCurrentFk[t_iAttrIndex],
                        metadataManager,
                        metadataTypeManager));
            }

            result.add(
                buildForeignKeyDecorator(
                    sourceTable,
                    t_lAttributes,
                    targetTable,
                    t_bAllowNull,
                    metadataManager));
                    
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
     * @precondition table != null
     * @precondition name != null
     * @precondition metadataManager != null
     */
    protected AttributeDecorator buildAttributeDecorator(
        final String table,
        final String name,
        final MetadataManager metadataManager,
        final MetadataTypeManager metadataTypeManager)
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
     * @return such decorator.
     * @precondition sourceTable != null
     * @precondition attributes != null
     * @precondition targetTable != null
     * @precondition metadataManager != null
     */
    protected ForeignKeyDecorator buildForeignKeyDecorator(
        final String sourceTable,
        final List attributes,
        final String targetTable,
        final boolean allowNull,
        final MetadataManager metadataManager)
    {
        return
            new ForeignKeyDecorator(
                sourceTable,
                attributes,
                targetTable,
                allowNull);
    }

    /**
     * Specifies the parent foreign-key.
     * @param foreignKey such fk.
     */
    protected final void immutableSetParentForeignKey(
        final ForeignKey foreignKey)
    {
        m__ParentForeignKey = foreignKey;
    }

    /**
     * Specifies the parent foreign-key.
     * @param foreignKey such fk.
     */
    protected void setParentForeignKey(
        final ForeignKey foreignKey)
    {
        immutableSetParentForeignKey(foreignKey);
    }

    /**
     * Retrieves the parent foreign-key.
     * @return such foreign key.
     */
    protected final ForeignKey immutableGetParentForeignKey()
    {
        return m__ParentForeignKey;
    }

    /**
     * Retrieves the parent foreign-key.
     * @return such foreign key.
     */
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
     * @precondition parent != null
     * @precondition foreignKeys != null
     */
    protected ForeignKey retrieveParentForeignKey(
        final Table parent, final List foreignKeys)
    {
        ForeignKey result = null;

        int t_iCount = (foreignKeys != null) ? foreignKeys.size() : 0;

        for  (int t_iIndex = 0; t_iIndex < t_iCount; t_iIndex++)
        {
            result = (ForeignKey) foreignKeys.get(t_iIndex);

            if  (   (result != null)
                 && (parent.getName().equalsIgnoreCase(
                         result.getTargetTableName()))
                 && (attributeListMatch(
                         getPrimaryKey(), result.getAttributes())))
            {
                break;
            }

            result = null;
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
        final Attribute first, final Attribute second)
    {
        boolean result =
            (   (first != null)
             && (second != null));

        if  (result)
        {
            String t_strFirstName = first.getName();

            result =
                (   (first.getType() == second.getType())
                 && (t_strFirstName != null)
                 && (t_strFirstName.equalsIgnoreCase(second.getName())));
        }

        return result;
    }

    /**
     * Retrieves the name, in upper case.
     * @return such value.
     */
    public String getNameUppercased()
    {
        return uppercase(getName());
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
    public String getSingularNameUppercased()
    {
        return uppercase(getSingular(lowercase(getName())));
    }

    /**
     * Retrieves the singular table's name, lower-cased.
     * @return such information.
     */
    public String getSingularNameLowercased()
    {
        return lowercase(getSingularNameUppercased());
    }

    /**
     * Retrieves the non-read-only attributes.
     * @return such attributes.
     */
    public List getNonReadOnlyAttributes()
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
     * @precondition name != null
     * @precondition attributes != null
     * @precondition metadataManager != null
     * @precondition decoratorFactory != null
     * @precondition tableDecoratorHelper != null
     */
    protected List getNonReadOnlyAttributes(
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
     * @param tableDecoratorHelper the <code>TableDecoratorHelper</code>
     * instance.
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
     * Retrieves the non-parent attributes, plus the primary key.
     * @return such attributes.
     */
    public List getNonParentPlusPkAttributes()
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
     * @precondition name != null
     * @precondition attributes != null
     * @precondition metadataManager != null
     * @precondition decoratorFactory != null
     * @precondition tableDecoratorHelper != null
     */
    protected List getNonParentPlusPkAttributes(
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
            TableDecorator t_ParentDecorator = null;

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
    @NotNull
    public List getAttributes()
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
        final boolean attributesShouldBeCleanedUp)
    {
        List result = super.getAttributes();

        if  (   (result == null)
             || (result.size() == 0))
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
                        decoratorFactory.decoratePrimaryKey(
                            t_strParentTable,
                            metadataManager),
                        t_lAttributes,
                        metadataManager.isTableStatic(
                            t_strParentTable),
                        metadataManager.isTableDecorated(
                            t_strParentTable),
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
     * @param parentTableName the parent table nameq.
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
     * @param parentTable the parent table name.
     * @param primaryKey the primary key.
     * @param attributes the attributes.
     * @param isStatic whether the table contains static values or not.
     * @param voDecorated whether the value-object is decorated.
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
        final List primaryKey,
        final List attributes,
        final boolean isStatic,
        final boolean voDecorated,
        final MetadataManager metadataManager,
        final DecoratorFactory decoratorFactory);
    
    /**
     * Creates a table decorator.
     * @param parentTable the parent table name.
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

    /**
     * Retrieves the list of non-parent, non-externally-managed
     * attributes.
     * @return such list.
     */
    public List getNonParentNonManagedExternallyAttributes()
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
    protected List getNonParentNonManagedExternallyAttributes(
        final List nonParentAttributes)
    {
        List result = new ArrayList();

        int t_iCount = (nonParentAttributes != null) ? nonParentAttributes.size() : 0;

        Attribute t_Attribute;

        for  (int t_iIndex = 0; t_iIndex < t_iCount; t_iIndex++)
        {
            t_Attribute = (Attribute) nonParentAttributes.get(t_iIndex);

            if  (   (t_Attribute != null)
                 && (!t_Attribute.getManagedExternally()))
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
    public List getNonParentNonManagedExternallyPlusPkAttributes()
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
    protected List getNonParentNonManagedExternallyPlusPkAttributes(
        final List nonParentAttributes,
        final List primaryKey)
    {
        List result = new ArrayList();

        int t_iCount = (nonParentAttributes != null) ? nonParentAttributes.size() : 0;

        Attribute t_Attribute;

        for  (int t_iIndex = 0; t_iIndex < t_iCount; t_iIndex++)
        {
            t_Attribute = (Attribute) nonParentAttributes.get(t_iIndex);

            if  (   (t_Attribute != null)
                 && (   (!t_Attribute.getManagedExternally())
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
    public List getAllParentAndNonParentAttributes()
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
     * @precondition metadataManager != null
     * @precondition decoratorFactory != null
     */
    protected List getAllParentAndNonParentAttributes(
        final Table parent,
        final List nonParentAttributes,
        final MetadataManager metadataManager,
        final DecoratorFactory decoratorFactory)
    {
        List result = new ArrayList();

        if  (parent != null)
        {
            TableDecorator t_ParentDecorator = null;

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

            result.addAll(t_ParentDecorator.getAllAttributes());
        }

        if  (nonParentAttributes != null)
        {
            result.addAll(nonParentAttributes);
        }

        return result;
    }

    /**
     * Retrieves the list of parent's all attributes and the non-parent
     * non-managed-externally own attributes.
     * @return such list.
     */
    public List getAllParentAndNonParentNonManagedExternallyAttributes()
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
    protected List getAllParentAndNonParentNonManagedExternallyAttributes(
        final Table parent,
        final List nonParentNonManagedExternallyAttributes,
        final MetadataManager metadataManager,
        final DecoratorFactory decoratorFactory)
    {
        List result = new ArrayList();

        if  (parent != null)
        {
            TableDecorator t_ParentDecorator = null;

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
            
            result.addAll(t_ParentDecorator.getAllNonManagedExternallyAttributes());
        }

        if  (nonParentNonManagedExternallyAttributes != null)
        {
            result.addAll(nonParentNonManagedExternallyAttributes);
        }

        return result;
    }

    /**
     * Retrieves all attributes, including the parent's, but not the externally-managed.
     * @return such attributes.
     */
    public List getAllNonManagedExternallyAttributes()
    {
        return getAllNonManagedExternallyAttributes(getAllAttributes());
    }

    /**
     * Retrieves all attributes, including the parent's, but not the externally-managed.
     * @param allAttributes the attributes.
     * @return such attributes.
     */
    protected List getAllNonManagedExternallyAttributes(final List allAttributes)
    {
        List result = new ArrayList();

        int t_iCount = (allAttributes != null) ? allAttributes.size() : 0;

        Attribute t_Attribute;

        for  (int t_iIndex = 0; t_iIndex < t_iCount; t_iIndex++)
        {
            t_Attribute = (Attribute) allAttributes.get(t_iIndex);

            if  (   (t_Attribute != null)
                 && (!t_Attribute.getManagedExternally()))
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
    public List getAllNonManagedExternallyPlusPkAttributes()
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
    protected List getAllNonManagedExternallyPlusPkAttributes(
        final List allAttributes, final List primaryKey)
    {
        List result = new ArrayList();

        int t_iCount = (allAttributes != null) ? allAttributes.size() : 0;

        Attribute t_Attribute;

        for  (int t_iIndex = 0; t_iIndex < t_iCount; t_iIndex++)
        {
            t_Attribute = (Attribute) allAttributes.get(t_iIndex);

            if  (   (t_Attribute != null)
                 && (   (!t_Attribute.getManagedExternally())
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
    public List getAllNonManagedExternallyNonReadOnlyPlusPkAttributes()
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
    protected List getAllNonManagedExternallyNonReadOnlyPlusPkAttributes(
        final List allAttributes, final List primaryKey)
    {
        List result = new ArrayList();

        int t_iCount = (allAttributes != null) ? allAttributes.size() : 0;

        Attribute t_Attribute;

        for  (int t_iIndex = 0; t_iIndex < t_iCount; t_iIndex++)
        {
            t_Attribute = (Attribute) allAttributes.get(t_iIndex);

            if  (   (t_Attribute != null)
                 && (   (   (!t_Attribute.getManagedExternally())
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
    protected boolean isPartOf(final List list, final Attribute attribute)
    {
        boolean result = false;

        int t_iCount = (list != null) ? list.size() : 0;

        Attribute t_Attribute;

        for  (int t_iIndex = 0; t_iIndex < t_iCount; t_iIndex++)
        {
            t_Attribute = (Attribute) list.get(t_iIndex);

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
    public List getAllParentAndNonParentNonManagedExternallyNonReadOnlyAttributes()
    {
        List result =
            getAllParentAndNonParentNonManagedExternallyAttributes();

        return removeReadOnly(result, TableDecoratorHelper.getInstance());
    }

    /**
     * Removes the read-only attributes from given list.
     * @param attributes the attributes.
     * @param tableDecoratorHelper the <code>TableDecoratorHelper</code> instance.
     * @return the list without the read-only attributes.
     * @precondition attributes != null
     * @precondition tableDecoratorHelper != null
     */
    protected List removeReadOnly(
        final List attributes, final TableDecoratorHelper tableDecoratorHelper)
    {
        return tableDecoratorHelper.removeReadOnly(attributes);
    }

    /**
     * Retrieves the list of parent's all attributes and the non-parent,
     * non-read-only own attributes.
     * @return such list.
     */
    public List getAllParentAndNonParentNonReadOnlyAttributes()
    {
        List result = getAllParentAndNonParentAttributes();

        return removeReadOnly(result, TableDecoratorHelper.getInstance());
    }

    /**
     * Retrieves the read-only attributes.
     * @return such information.
     */
    public List getAllParentAndNonParentReadOnlyAttributes()
    {
        return
            getAllParentAndNonParentReadOnlyAttributes(
                TableDecoratorHelper.getInstance());
    }

    /**
     * Retrieves the read-only attributes.
     * @param tableDecoratorHelper the <code>TableDecoratorHelper</code> instance.
     * @return such information.
     * @precondition tableDecoratorHelper != null
     */
    protected List getAllParentAndNonParentReadOnlyAttributes(
        final TableDecoratorHelper tableDecoratorHelper)
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
    public List getAllParentAndNonParentNonManagedExternallyNonReadOnlyPlusPkAttributes()
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
     * @precondition tableDecoratorHelper != null
     */
    protected List getAllParentAndNonParentNonManagedExternallyNonReadOnlyPlusPkAttributes(
        final Table parent,
        final List nonParentNonManagedExternallyPlusPkAttributes,
        final MetadataManager metadataManager,
        final DecoratorFactory decoratorFactory,
        final TableDecoratorHelper tableDecoratorHelper)
    {
        List result = new ArrayList();

        if  (parent != null)
        {
            TableDecorator t_ParentDecorator = null;

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
            
            result.addAll(
                t_ParentDecorator.getAllNonManagedExternallyNonReadOnlyPlusPkAttributes());
        }

        if  (nonParentNonManagedExternallyPlusPkAttributes != null)
        {
            result.addAll(
                tableDecoratorHelper.removeReadOnly(
                    nonParentNonManagedExternallyPlusPkAttributes));
        }

        return result;
    }

    /**
     * Retrieves all parent tables.
     * @return such tables.
     */
    public List getAllParentTables()
    {
        return
            getAllParentTables(
                getName(), getMetadataManager(), getDecoratorFactory());
    }

    /**
     * Retrieves all parent tables.
     * @param tableName the table name.
     * @param metadataManager the metadata manager.
     * @param decoratorFactory the decorator factory.
     * @precondition tableName != null
     * @precondition metadataManager != null
     * @precondition decoratorFactory != null
     * @return such tables.
     */
    protected List getAllParentTables(
        final String tableName,
        final MetadataManager metadataManager,
        final DecoratorFactory decoratorFactory)
    {
        List result = new ArrayList();

        String parentName;

        String currentName = metadataManager.getParentTable(tableName);

        while  (currentName != null)
        {
            result.add(
                decoratorFactory.createTableDecorator(currentName, metadataManager));

            currentName = metadataManager.getParentTable(currentName);
        }

        return result;
    }
}
