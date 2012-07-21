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
 * Importing project classes.
 */
import org.acmsl.commons.utils.EnglishGrammarUtils;
import org.acmsl.queryj.SingularPluralFormConverter;
import org.acmsl.queryj.customsql.CustomSqlProvider;
import org.acmsl.queryj.metadata.vo.AbstractForeignKey;
import org.acmsl.queryj.metadata.vo.Attribute;
import org.acmsl.queryj.metadata.vo.ForeignKey;
import org.acmsl.queryj.metadata.vo.Table;

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
    extends AbstractForeignKey
    implements ForeignKeyDecorator
{
    /**
     * The decorated instance.
     */
    private ForeignKey m__ForeignKey;

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
    @SuppressWarnings("unused")
    public AbstractForeignKeyDecorator(
        @NotNull final ForeignKey foreignKey,
        @NotNull final MetadataManager metadataManager,
        @NotNull final DecoratorFactory decoratorFactory,
        @NotNull final CustomSqlProvider customSqlProvider)
    {
        this(
            foreignKey.getSourceTableName(),
            foreignKey.getAttributes(),
            foreignKey.getTargetTableName(),
            foreignKey.isNullable(),
            metadataManager,
            decoratorFactory,
            customSqlProvider);

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
    protected AbstractForeignKeyDecorator(
        @NotNull final String sourceTableName,
        @NotNull final List<Attribute> attributes,
        @NotNull final String targetTableName,
        final boolean allowsNull,
        @NotNull final MetadataManager metadataManager,
        @NotNull final DecoratorFactory decoratorFactory,
        @NotNull final CustomSqlProvider customSqlProvider)
    {
        super(sourceTableName, attributes, targetTableName, allowsNull);
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
    protected final void immutableSetForeignKey(@NotNull final ForeignKey foreignKey)
    {
        m__ForeignKey = foreignKey;
    }

    /**
     * Specifies the foreign key to decorate.
     * @param foreignKey the foreign key.
     */
    @SuppressWarnings("unused")
    protected void setForeignKey(@NotNull final ForeignKey foreignKey)
    {
        immutableSetForeignKey(foreignKey);
    }

    /**
     * Retrieves the decorated foreign key.
     * @return such foreign key.
     */
    @NotNull
    public ForeignKey getForeignKey()
    {
        return m__ForeignKey;
    }

    /**
     * Retrieves the attributes.
     * @return such information.
     */
    @NotNull
    @Override
    public List<Attribute> getAttributes()
    {
        return decorateAttributes(super.getAttributes(), getMetadataManager(), getDecoratorFactory());
    }

    /**
     * Decorates given attributes.
     * @param attributes the attributes to decorate.
     * @param metadataManager the {@link MetadataManager} instance.
     * @param decoratorFactory the {@link DecoratorFactory} implementation.
     * @return such information.
     */
    @NotNull
    protected List<Attribute> decorateAttributes(
        @NotNull final List<Attribute> attributes,
        @NotNull final MetadataManager metadataManager,
        @NotNull final DecoratorFactory decoratorFactory)
    {
        @NotNull List<Attribute> result = new ArrayList<Attribute>(attributes.size());

        for (@Nullable Attribute t_Attribute : attributes)
        {
            if (t_Attribute != null)
            {
                result.add(
                    decoratorFactory.createDecorator(
                        t_Attribute,
                        metadataManager));
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
    public Table getSource()
    {
        return getTable(getSourceTableName(), getMetadataManager());
    }

    /**
     * Retrieves the target table.
     * @return such information.
     */
    @SuppressWarnings("unused")
    @Nullable
    public Table getTarget()
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
    public Table getTable(@NotNull final String sourceTableName, @NotNull final MetadataManager metadataManager)
    {
        return getTable(sourceTableName, metadataManager.getTableDAO());
    }

    /**
     * Retrieves the source table.
     * @param sourceTableName the name of the source table.
     * @param tableDAO the {@link TableDAO} instance.
     * @return such information.
     */
    @Nullable
    public Table getTable(@NotNull final String sourceTableName, @NotNull final TableDAO tableDAO)
    {
        Table result = tableDAO.findByName(sourceTableName);

        if (result != null)
        {
            result = decorate(result);
        }

        return result;
    }

    /**
     * Retrieves the Value-Object-formatted name of the source table.
     * @return such information.
     */
    @SuppressWarnings("unused")
    @NotNull
    public String getSourceVoName()
    {
        return toVoName(getSourceTableName());
    }

    /**
     * Retrieves the Value-Object-formatted name of the target table.
     * @return such information.
     */
    @SuppressWarnings("unused")
    @NotNull
    public String getTargetVoName()
    {
        return toVoName(getTargetTableName());
    }

    /**
     * Converts given name into a ValueObject-formatted one.
     * @param tableName the table name.
     * @return the formatted name.
     */
    @NotNull
    protected String toVoName(@NotNull final String tableName)
    {
        return toVoName(tableName, DecorationUtils.getInstance());
    }

    /**
     * Retrieves the table's name once normalized.
     * @param name the table name.
     * @param decorationUtils the {@link DecorationUtils} instance.
     * @return such information.
     */
    @NotNull
    protected String toVoName(
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
     * Checks whether this foreign key allows null or not.
     * @return such condition.
     */
    public boolean isNullable()
    {
        return isNullable(getAttributes());
    }

    /**
     * Checks whether this foreign key allows null or not.
     * @return such condition.
     */
    protected boolean isNullable(@NotNull final List<Attribute> attributes)
    {
        boolean result = true;

        for (@Nullable Attribute t_Attribute : attributes)
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
    protected Table decorate(@NotNull final Table table)
    {
        return new CachingTableDecorator(table, getMetadataManager(), getDecoratorFactory(), getCustomSqlProvider());
    }

    /**
     * Retrieves the name of the foreign key, in upper case.
     * @return such information.
     */
    @SuppressWarnings("unused")
    @NotNull
    public String getNameUppercased()
    {
        return getNameUppercased(buildName(getForeignKey()), DecorationUtils.getInstance());
    }

    /**
     * Retrieves the name of the foreign key, in upper case.
     * @param name the {@link ForeignKey}'s name.
     * @param decorationUtils the {@link DecorationUtils} instance.
     * @return such information.
     */
    @NotNull
    protected String getNameUppercased(
        @NotNull final String name, @NotNull final DecorationUtils decorationUtils)
    {
        return decorationUtils.upperCase(name);
    }

    /**
     * Builds a name using information from the foreign key.
     * @param foreignKey the {@link ForeignKey} instance.
     * @return the artificial name.
     */
    @NotNull
    protected String buildName(@NotNull final ForeignKey foreignKey)
    {
        @Nullable String result = foreignKey.getFkName();

        if (result == null)
        {
            StringBuilder t_sbName = new StringBuilder(foreignKey.getSourceTableName());

            t_sbName.append("_");

            for (@Nullable Attribute t_Attribute : foreignKey.getAttributes())
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
     * Retrieves the name of the foreign key, capitalized.
     * @return such information.
     */
    @SuppressWarnings("unused")
    @NotNull
    public String getNameCapitalized()
    {
        return getNameCapitalized(buildName(getForeignKey()), DecorationUtils.getInstance());
    }

    /**
     * Retrieves the name of the foreign key, capitalized.
     * @param name the {@link ForeignKey}'s name.
     * @param decorationUtils the {@link DecorationUtils} instance.
     * @return such information.
     */
    @NotNull
    protected String getNameCapitalized(
        @NotNull final String name, @NotNull final DecorationUtils decorationUtils)
    {
        return decorationUtils.capitalize(name);
    }

    /**
     * Retrieves the name of the foreign key, in lower case.
     * @return such information.
     */
    @SuppressWarnings("unused")
    @NotNull
    public String getNameLowercased()
    {
        return getNameLowercased(buildName(getForeignKey()), DecorationUtils.getInstance());
    }

    /**
     * Retrieves the name of the foreign key, in lower case.
     * @param name the {@link ForeignKey}'s name.
     * @param decorationUtils the {@link DecorationUtils} instance.
     * @return such information.
     */
    @NotNull
    protected String getNameLowercased(
        @NotNull final String name, @NotNull final DecorationUtils decorationUtils)
    {
        return decorationUtils.lowerCase(name);
    }

    /**
     * Retrieves the name of the foreign key.
     * @return such information.
     */
    @Override
    @NotNull
    public String getFkName()
    {
        return getFkName(getForeignKey());
    }

    /**
     * Retrieves the name of the foreign key.
     * @param foreignKey the {@link ForeignKey} instance.
     * @return such information.
     */
    @NotNull
    protected String getFkName(@NotNull final ForeignKey foreignKey)
    {
        String result = foreignKey.getFkName();

        if (result == null)
        {
            result = getSourceVoName() + "_" + getTargetVoName() + foreignKey.hashCode();
        }

        return result;
    }

    /**
     * Provides a text representation of the information
     * contained in this instance.
     * @return such information.
     */
    @Override
    @NotNull
    public String toString()
    {
        return "" + getForeignKey();
    }

    /**
     * Provides a text representation of the information
     * contained in given instance.
     * @param foreignKey the decorated foreign key.
     * @return such information.
     * @precondition foreignKey != null
     */
    @NotNull
    protected String toString(@NotNull final ForeignKey foreignKey)
    {
        return "" + foreignKey;
    }

    /**
     * Retrieves the hash code associated to this instance.
     * @return such information.
     */
    public int hashCode()
    {
        return hashCode(getForeignKey());
    }

    /**
     * Retrieves the hash code associated to given instance.
     * @param foreignKey the decorated foreign key.
     * @return such information.
     */
    protected int hashCode(@NotNull final ForeignKey foreignKey)
    {
        return foreignKey.hashCode();
    }

    /**
     * Checks whether given object is semantically equal to this instance.
     * @param object the object to compare to.
     * @return the result of such comparison.
     */
    public boolean equals(final Object object)
    {
        boolean result = false;

        if (object instanceof ForeignKey)
        {
            result = equals(getForeignKey(), (ForeignKey) object);
        }

        return result;
    }

    /**
     * Checks whether given object is semantically equal to given instance.
     * @param foreignKey the decorated foreign key.
     * @param object the object to compare to.
     * @return the result of such comparison.
     */
    protected boolean equals(@NotNull final ForeignKey foreignKey, final ForeignKey object)
    {
        return foreignKey.equals(object);
    }    

    /**
     * Compares given object with this instance.
     * @param object the object to compare to.
     * @return the result of such comparison.
     * @throws ClassCastException if the type of the specified
     * object prevents it from being compared to this Object.
     */
    public int compareTo(@Nullable final ForeignKey object)
        throws  ClassCastException
    {
        return compareTo(getForeignKey(), object);
    }

    /**
     * Compares given object with given instance.
     * @param foreignKey the decorated foreign key.
     * @param object the object to compare to.
     * @return the result of such comparison.
     * @throws ClassCastException if the type of the specified
     * object prevents it from being compared to this Object.
     * @precondition foreignKey != null
     */
    protected int compareTo(@NotNull final ForeignKey foreignKey, @Nullable final ForeignKey object)
        throws  ClassCastException
    {
        return foreignKey.compareTo(object);
    }
}
