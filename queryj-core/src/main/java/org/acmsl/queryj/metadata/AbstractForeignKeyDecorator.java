/*
                        QueryJ Core

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

 *****************************************************************************
 *
 * Filename: AbstractForeignKeyDecorator.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Decorates 'ForeignKey' instances to provide required
 *              alternate representations of the information stored therein.
 *
 */
package org.acmsl.queryj.metadata;

/*
 * Importing QueryJ Core classes.
 */
import org.acmsl.queryj.customsql.CustomSqlProvider;
import org.acmsl.queryj.metadata.vo.AbstractForeignKey;
import org.acmsl.queryj.metadata.vo.Attribute;
import org.acmsl.queryj.metadata.vo.ForeignKey;
import org.acmsl.queryj.metadata.vo.Table;

/*
 * Importing some Apache Commons-Lang.
 */
import org.apache.commons.lang.builder.CompareToBuilder;

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
 * Decorates {@link ForeignKey} instances to provide required alternate
 * representations of the information stored therein.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public abstract class AbstractForeignKeyDecorator
    extends AbstractForeignKey<DecoratedString>
    implements ForeignKeyDecorator
{
    /**
     * The decorated instance.
     */
    private ForeignKey<String> m__ForeignKey;

    /**
     * The {@link MetadataManager} instance.
     */
    private MetadataManager m__MetadataManager;

    /**
     * The {@link DecoratorFactory} instance.
     */
    private DecoratorFactory m__DecoratorFactory;

    /**
     * The {@link CustomSqlProvider} instance.
     */
    private CustomSqlProvider m__CustomSqlProvider;

    /**
     * Creates an <code>AbstractForeignKeyDecorator</code> with the
     * <code>ForeignKey</code> information to decorate.
     * @param foreignKey the foreign key.
     * @param metadataManager the {@link MetadataManager} instance.
     * @param decoratorFactory the {@link DecoratorFactory} implementation.
     * @param customSqlProvider the {@link CustomSqlProvider} instance.
     */
    public AbstractForeignKeyDecorator(
        @NotNull final ForeignKey<String> foreignKey,
        @NotNull final MetadataManager metadataManager,
        @NotNull final DecoratorFactory decoratorFactory,
        @NotNull final CustomSqlProvider customSqlProvider)
    {
        super(
            new DecoratedString(foreignKey.getSourceTableName()),
            new ArrayList<Attribute<DecoratedString>>(0),
            new DecoratedString(foreignKey.getTargetTableName()),
            foreignKey.isNullable());
        immutableSetAttributes(
            AbstractForeignKeyDecorator.<String, DecoratedString>decorateAttributes(
                foreignKey.getAttributes(), metadataManager, decoratorFactory));
        immutableSetMetadataManager(metadataManager);
        immutableSetDecoratorFactory(decoratorFactory);
        immutableSetCustomSqlProvider(customSqlProvider);

        immutableSetForeignKey(foreignKey);
    }

    /**
     * Creates an <code>AbstractForeignKeyDecorator</code> with the following
     * information.
     * @param sourceTableName the source table name.
     * @param attributes the attributes.
     * @param targetTableName the target table name.
     * @param allowsNull whether the foreign key allows null values.
     * @param metadataManager the {@link MetadataManager} instance.
     * @param decoratorFactory the {@link DecoratorFactory} implementation.
     * @param customSqlProvider the {@link CustomSqlProvider} instance.
     */
    @SuppressWarnings("unused")
    protected AbstractForeignKeyDecorator(
        @NotNull final String sourceTableName,
        @NotNull final List<Attribute<String>> attributes,
        @NotNull final String targetTableName,
        final boolean allowsNull,
        @NotNull final MetadataManager metadataManager,
        @NotNull final DecoratorFactory decoratorFactory,
        @NotNull final CustomSqlProvider customSqlProvider)
    {
        super(
            new DecoratedString(sourceTableName),
            decoratorFactory.decorateAttributes(attributes, metadataManager),
            new DecoratedString(targetTableName),
            allowsNull);
        immutableSetMetadataManager(metadataManager);
        immutableSetDecoratorFactory(decoratorFactory);
        immutableSetCustomSqlProvider(customSqlProvider);
    }

    /**
     * Specifies the {@link MetadataManager} instance.
     * @param metadataManager such instance.
     */
    protected final void immutableSetMetadataManager(@NotNull final MetadataManager metadataManager)
    {
        m__MetadataManager = metadataManager;
    }

    /**
     * Specifies the {@link MetadataManager} instance.
     * @param metadataManager such instance.
     */
    @SuppressWarnings("unused")
    protected void setMetadataManager(@NotNull final MetadataManager metadataManager)
    {
        immutableSetMetadataManager(metadataManager);
    }

    /**
     * Retrieves the {@link MetadataManager} instance.
     * @return such instance.
     */
    @NotNull
    public MetadataManager getMetadataManager()
    {
        return m__MetadataManager;
    }

    /**
     * Specifies the {@link DecoratorFactory} instance.
     * @param decoratorFactory such instance.
     */
    protected final void immutableSetDecoratorFactory(@NotNull final DecoratorFactory decoratorFactory)
    {
        m__DecoratorFactory = decoratorFactory;
    }

    /**
     * Specifies the {@link DecoratorFactory} instance.
     * @param decoratorFactory such instance.
     */
    @SuppressWarnings("unused")
    protected void setDecoratorFactory(@NotNull final DecoratorFactory decoratorFactory)
    {
        immutableSetDecoratorFactory(decoratorFactory);
    }

    /**
     * Retrieves the {@link DecoratorFactory} instance.
     * @return such instance.
     */
    @NotNull
    public DecoratorFactory getDecoratorFactory()
    {
        return m__DecoratorFactory;
    }

    /**
     * Specifies the {@link CustomSqlProvider} instance.
     * @param customSqlProvider such instance.
     */
    protected final void immutableSetCustomSqlProvider(@NotNull final CustomSqlProvider customSqlProvider)
    {
        m__CustomSqlProvider = customSqlProvider;
    }

    /**
     * Specifies the {@link CustomSqlProvider} instance.
     * @param customSqlProvider such instance.
     */
    @SuppressWarnings("unused")
    protected void setCustomSqlProvider(@NotNull final CustomSqlProvider customSqlProvider)
    {
        immutableSetCustomSqlProvider(customSqlProvider);
    }

    /**
     * Retrieves the {@link CustomSqlProvider} instance.
     * @return such instance.
     */
    @NotNull
    public CustomSqlProvider getCustomSqlProvider()
    {
        return m__CustomSqlProvider;
    }

    /**
     * Specifies the foreign key to decorate.
     * @param foreignKey the foreign key.
     */
    protected final void immutableSetForeignKey(@NotNull final ForeignKey<String> foreignKey)
    {
        m__ForeignKey = foreignKey;
    }

    /**
     * Specifies the foreign key to decorate.
     * @param foreignKey the foreign key.
     */
    @SuppressWarnings("unused")
    protected void setForeignKey(@NotNull final ForeignKey<String> foreignKey)
    {
        immutableSetForeignKey(foreignKey);
    }

    /**
     * Retrieves the decorated foreign key.
     * @return such foreign key.
     */
    @NotNull
    public ForeignKey<String> getForeignKey()
    {
        return m__ForeignKey;
    }

    /**
     * Retrieves the attributes.
     * @return such information.
     */
    @NotNull
    @Override
    public List<Attribute<DecoratedString>> getAttributes()
    {
        return decorateAttributes(super.getAttributes(), getMetadataManager(), getDecoratorFactory());
    }

    /**
     * Decorates given attributes.
     * @param <V> the type of the attributes.
     * @param <K> the type of the decorated attributes.
     * @param attributes the attributes to decorate.
     * @param metadataManager the {@link MetadataManager} instance.
     * @param decoratorFactory the {@link DecoratorFactory} implementation.
     * @return such information.
     */
    @NotNull
    protected static <V, K> List<Attribute<K>> decorateAttributes(
        @NotNull final List<Attribute<V>> attributes,
        @NotNull final MetadataManager metadataManager,
        @NotNull final DecoratorFactory decoratorFactory)
    {
        @NotNull final List<Attribute<K>> result = new ArrayList<>(attributes.size());

        for (@Nullable final Attribute<V> t_Attribute : attributes)
        {
            if (t_Attribute != null)
            {
                result.add(decoratorFactory.<V, K>createDecorator(t_Attribute, metadataManager));
            }
        }

        return result;
    }

    /**
     * Retrieves the source table.
     * @return such information.
     */
    @SuppressWarnings("unused")
    @Nullable
    public Table<DecoratedString, Attribute<DecoratedString>, ListDecorator<Attribute<DecoratedString>>> getSource()
    {
        return getTable(getSourceTableName().getValue(), getMetadataManager());
    }

    /**
     * Retrieves the target table.
     * @return such information.
     */
    @SuppressWarnings("unused")
    @Nullable
    public Table<DecoratedString, Attribute<DecoratedString>, ListDecorator<Attribute<DecoratedString>>> getTarget()
    {
        return getTable(getTargetTableName(), getMetadataManager());
    }

    /**
     * Retrieves the source table.
     * @param sourceTableName the name of the source table.
     * @param metadataManager the {@link MetadataManager} instance.
     * @return such information.
     */
    @Nullable
    public Table<DecoratedString, Attribute<DecoratedString>, ListDecorator<Attribute<DecoratedString>>> getTable(
        @NotNull final String sourceTableName, @NotNull final MetadataManager metadataManager)
    {
        return getTable(sourceTableName, metadataManager.getTableDAO());
    }

    /**
     * Retrieves the source table.
     * @param sourceTableName the name of the source table.
     * @param metadataManager the {@link MetadataManager} instance.
     * @return such information.
     */
    @Nullable
    public Table<DecoratedString, Attribute<DecoratedString>, ListDecorator<Attribute<DecoratedString>>> getTable(
        @NotNull final DecoratedString sourceTableName, @NotNull final MetadataManager metadataManager)
    {
        return getTable(sourceTableName.getValue(), metadataManager.getTableDAO());
    }

    /**
     * Retrieves the source table.
     * @param sourceTableName the name of the source table.
     * @param tableDAO the {@link TableDAO} instance.
     * @return such information.
     */
    @Nullable
    public Table<DecoratedString, Attribute<DecoratedString>, ListDecorator<Attribute<DecoratedString>>> getTable(
        @NotNull final String sourceTableName, @NotNull final TableDAO tableDAO)
    {
        final Table<DecoratedString, Attribute<DecoratedString>, ListDecorator<Attribute<DecoratedString>>> result;

        final Table<String, Attribute<String>, List<Attribute<String>>> aux =
            tableDAO.findByName(sourceTableName);

        if (aux != null)
        {
            result = decorate(aux);
        }
        else
        {
            result = null;
        }

        return result;
    }

    /**
     * Checks whether this foreign key allows null or not.
     * @return such condition.
     */
    public boolean isNullable()
    {
        return isNullable(getAttributes());
    }

    /**
     * Checks whether this foreign key allows null or not.
     * @param attributes the attributes.
     * @param <V> the attribute type.
     * @return such condition.
     */
    protected <V> boolean isNullable(@NotNull final List<Attribute<V>> attributes)
    {
        boolean result = true;

        for (@Nullable final Attribute<V> t_Attribute : attributes)
        {
            if (   (t_Attribute != null)
                && (!t_Attribute.isNullable()))
            {
                result = false;
                break;
            }
        }

        return result;
    }

    /**
     * Decorates given table.
     * @param table the table.
     * @return the decorated table.
     */
    @NotNull
    protected Table<DecoratedString, Attribute<DecoratedString>, ListDecorator<Attribute<DecoratedString>>> decorate(
        @NotNull final Table<String, Attribute<String>, List<Attribute<String>>> table)
    {
        return new CachingTableDecorator(table, getMetadataManager(), getDecoratorFactory(), getCustomSqlProvider());
    }

    /**
     * Builds a name using information from the foreign key.
     * @param foreignKey the {@link ForeignKey} instance.
     * @return the artificial name.
     */
    @SuppressWarnings("unused")
    @NotNull
    protected String buildName(@NotNull final ForeignKey<String> foreignKey)
    {
        @Nullable String result = foreignKey.getFkName();

        if (result == null)
        {
            final StringBuilder t_sbName = new StringBuilder(foreignKey.getSourceTableName());

            t_sbName.append("_");

            for (@Nullable final Attribute<String> t_Attribute : foreignKey.getAttributes())
            {
                if (t_Attribute == null)
                {
                    t_sbName.append("null");
                }
                else
                {
                    t_sbName.append(t_Attribute.getName());
                }

                t_sbName.append("_");
            }

            t_sbName.append(foreignKey.getTargetTableName());

            result = t_sbName.toString();
        }

        return result;
    }

    /**
     * Retrieves the name of the foreign key.
     * @return such information.
     */
    @Override
    @NotNull
    public DecoratedString getFkName()
    {
        return getFkName(getForeignKey());
    }

    /**
     * Retrieves the name of the foreign key.
     * @param foreignKey the {@link ForeignKey} instance.
     * @return such information.
     */
    @NotNull
    protected DecoratedString getFkName(@NotNull final ForeignKey<String> foreignKey)
    {
        @NotNull final DecoratedString result;

        @Nullable final String aux = foreignKey.getFkName();

        if (aux == null)
        {
            final StringBuilder t_sbAux = new StringBuilder(getSourceTableName().getVoName().getValue());

            t_sbAux.append("_");
            t_sbAux.append(getTargetTableName().getVoName().getValue());

            for (@Nullable final Attribute<DecoratedString> t_Attribute : getAttributes())
            {
                if (t_Attribute != null)
                {
                    t_sbAux.append("_");
                    t_sbAux.append(t_Attribute.getName());
                }
            }

            result = new DecoratedString(t_sbAux.toString());
        }
        else
        {
            result = new DecoratedString(aux);
        }

        return result;
    }

    /**
     * Retrieves the list of attribute types.
     * @return such list.
     */
    @NotNull
    @SuppressWarnings("unused")
    public List<DecoratedString> getAttributeTypes()
    {
        return
            getAttributeTypes(
                getAttributes(),
                getMetadataManager().getMetadataTypeManager(),
                TableDecoratorHelper.getInstance());
    }

    /**
     * Retrieves the list of attribute types.
     * @param attributes the attributes.
     * @param metadataTypeManager the {@link MetadataTypeManager} instance.
     * @param tableDecoratorHelper the {@link TableDecoratorHelper} instance.
     * @return such list.
     */
    @NotNull
    protected List<DecoratedString> getAttributeTypes(
        @NotNull final List<Attribute<DecoratedString>> attributes,
        @NotNull final MetadataTypeManager metadataTypeManager,
        @NotNull final TableDecoratorHelper tableDecoratorHelper)
    {
        return tableDecoratorHelper.getAttributeTypes(attributes, metadataTypeManager);
    }

    /**
     * Checks whether any attribute is a clob.
     * @return {@code true} in such case.
     */
    @SuppressWarnings("unused")
    public boolean getContainsClobs()
    {
        return containClobs(getAttributes(), getMetadataManager().getMetadataTypeManager(), TableDecoratorHelper.getInstance());
    }

    /**
     * Checks whether any attribute is a clob.
     * @param attributes the {@link Attribute}s.
     * @param metadataTypeManager the {@link MetadataTypeManager} instance.
     * @param tableDecoratorHelper the {@link TableDecoratorHelper} instance.
     * @return {@code true} in such case.
     */
    protected boolean containClobs(
        @NotNull final List<Attribute<DecoratedString>> attributes,
        @NotNull final MetadataTypeManager metadataTypeManager,
        @NotNull final TableDecoratorHelper tableDecoratorHelper)
    {
        return tableDecoratorHelper.containClobs(attributes, metadataTypeManager);
    }

    /**
     * Provides a text representation of the information
     * contained in given instance.
     * @return such information.
     */
    @NotNull
    @Override
    public String toString()
    {
        return
              "{ \"class\": \"" + AbstractForeignKeyDecorator .class.getName() + '"'
            + ", \"customSqlProvider\": " + m__CustomSqlProvider.hashCode()
            + ", \"foreignKey\": " + m__ForeignKey
            + ", \"metadataManager\": " + m__MetadataManager.hashCode()
            + ", \"decoratorFactory\": " + m__DecoratorFactory.hashCode()
            + "\" }";
    }

    /**
     * Retrieves the hash code associated to this instance.
     * @return such information.
     */
    @Override
    public int hashCode()
    {
        return hashCode(getForeignKey());
    }

    /**
     * Retrieves the hash code associated to given instance.
     * @param foreignKey the decorated foreign key.
     * @return such information.
     */
    protected int hashCode(@NotNull final ForeignKey<String> foreignKey)
    {
        return foreignKey.hashCode();
    }

    /**
     * Checks whether given object is semantically equal to this instance.
     * @param object the object to compare to.
     * @return the result of such comparison.
     */
    @Override
    @SuppressWarnings("unchecked")
    public boolean equals(@Nullable final Object object)
    {
        boolean result = false;

        if (object instanceof ForeignKey)
        {
            result = equals(getForeignKey(), (ForeignKey<String>) object);
        }

        return result;
    }

    /**
     * Checks whether given object is semantically equal to given instance.
     * @param foreignKey the decorated foreign key.
     * @param object the object to compare to.
     * @return the result of such comparison.
     */
    protected boolean equals(@NotNull final ForeignKey<String> foreignKey, final ForeignKey<String> object)
    {
        return foreignKey.equals(object);
    }    

    /**
     * Compares given object with this instance.
     * @param object the object to compare to.
     * @return the result of such comparison.
     */
    @Override
    public int compareTo(@Nullable final ForeignKey<DecoratedString> object)
        throws  ClassCastException
    {
        return compareTo(getForeignKey(), object);
    }

    /**
     * Compares given object with given instance.
     * @param foreignKey the decorated foreign key.
     * @param object the object to compare to.
     * @return the result of such comparison.
     * throws ClassCastException if the objects are not compatible.
     */
    protected int compareTo(
        @NotNull final ForeignKey<String> foreignKey, @Nullable final ForeignKey<DecoratedString> object)
        throws  ClassCastException
    {
        final int result;

        if (object != null)
        {
            result = compareFks(foreignKey, object);
        }
        else
        {
            result = 1;
        }

        return result;
    }
    /**
     * Compares given {@link ForeignKey foreign keys}.
     * @param first the first.
     * @param second the second.
     * @return a positive number if the first is considered 'greater' than the second;
     * 0 if they are equal; a negative number otherwise.
     */
    @SuppressWarnings("unchecked")
    protected int compareFks(@NotNull final ForeignKey<String> first, @NotNull final ForeignKey<DecoratedString> second)
    {
        final int result;

        @NotNull final CompareToBuilder comparator = new CompareToBuilder();

        comparator.append(
            first.getSourceTableName(),
            second.getSourceTableName().getValue());

        comparator.append(
            first.getTargetTableName(),
            second.getTargetTableName().getValue());

        comparator.append(
            first.isNullable(),
            second.isNullable());

        final List<Attribute<String>> t_lFirstAttributes = first.getAttributes();
        final List<Attribute<DecoratedString>> t_lSecondAttributes = second.getAttributes();

        final Attribute<String>[] t_aFirstAttributes = (Attribute<String>[]) new Attribute<?>[t_lFirstAttributes.size()];
        t_lFirstAttributes.toArray(t_aFirstAttributes);
        final Attribute<DecoratedString>[] t_aSecondAttributes =
            (Attribute<DecoratedString>[]) new Attribute<?>[t_lSecondAttributes.size()];
        t_lSecondAttributes.toArray(t_aSecondAttributes);

        for (int t_iIndex = 0; t_iIndex < t_aFirstAttributes.length; t_iIndex++)
        {
            if (t_iIndex < t_aSecondAttributes.length)
            {
                comparator.append(t_aFirstAttributes[t_iIndex], t_aSecondAttributes[t_iIndex]);
            }
        }

        result = comparator.toComparison();

        return result;
    }
}
