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
import org.acmsl.queryj.metadata.vo.AbstractForeignKey;
import org.acmsl.queryj.metadata.vo.Attribute;
import org.acmsl.queryj.metadata.vo.ForeignKey;

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
 * Decorates {@link ForeignKey} instances to provide required alternate
 * representations of the information stored therein.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public abstract class AbstractForeignKeyDecorator
    extends AbstractForeignKey
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
     * Creates an <code>AbstractForeignKeyDecorator</code> with the
     * <code>ForeignKey</code> information to decorate.
     * @param foreignKey the foreign key.
     * @param metadataManager the {@link MetadataManager} instance.
     * @param decoratorFactory the {@link DecoratorFactory} implementation.
     */
    @SuppressWarnings("unused")
    public AbstractForeignKeyDecorator(
        @NotNull final ForeignKey foreignKey,
        @NotNull final MetadataManager metadataManager,
        @NotNull final DecoratorFactory decoratorFactory)
    {
        this(
            foreignKey.getSourceTableName(),
            foreignKey.getAttributes(),
            foreignKey.getTargetTableName(),
            foreignKey.isNullable(),
            metadataManager,
            decoratorFactory);

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
     */
    protected AbstractForeignKeyDecorator(
        @NotNull final String sourceTableName,
        @NotNull final List<Attribute> attributes,
        @NotNull final String targetTableName,
        final boolean allowsNull,
        @NotNull final MetadataManager metadataManager,
        @NotNull final DecoratorFactory decoratorFactory)
    {
        super(sourceTableName, attributes, targetTableName, allowsNull);
        immutableSetMetadataManager(metadataManager);
        immutableSetDecoratorFactory(decoratorFactory);
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
    public DecoratorFactory getDecoratorFactory()
    {
        return m__DecoratorFactory;
    }

    /**
     * Specifies the foreign key to decorate.
     * @param foreignKey the foreign key.
     */
    protected final void immutableSetForeignKey(final ForeignKey foreignKey)
    {
        m__ForeignKey = foreignKey;
    }

    /**
     * Specifies the foreign key to decorate.
     * @param foreignKey the foreign key.
     */
    @SuppressWarnings("unused")
    protected void setForeignKey(final ForeignKey foreignKey)
    {
        immutableSetForeignKey(foreignKey);
    }

    /**
     * Retrieves the decorated foreign key.
     * @return such foreign key.
     */
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
     * Retrieves the source table name, uncapitalized.
     * @return such value.
     */
    @NotNull
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
    @NotNull
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
    @NotNull
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
     */
    @NotNull
    protected String toVo(
        @NotNull final String tableName,
        @NotNull final EnglishGrammarUtils englishGrammarUtils,
        @NotNull final DecorationUtils decorationUtils)
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
     */
    @NotNull
    protected String uncapitalize(
        @NotNull final String value, @NotNull final DecorationUtils decorationUtils)
    {
        return decorationUtils.uncapitalize(value.toLowerCase());
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
        return decorationUtils.capitalize(value);
    }

    /**
     * Provides a text representation of the information
     * contained in this instance.
     * @return such information.
     */
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
