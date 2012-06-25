/*
                        QueryJ

    Copyright (C) 2002-today Jose San Leandro Armendariz
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
package org.acmsl.queryj.metadata;

/*
 * Importing project classes.
 */
import org.acmsl.queryj.customsql.Sql;
import org.acmsl.queryj.metadata.vo.Attribute;
import org.acmsl.queryj.metadata.vo.Table;
import org.jetbrains.annotations.NotNull;

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
public interface TableDecorator
    extends  Table
{
    /**
     * Retrieves the name, in upper case.
     * @return such value.
     */
    @NotNull
    String getNameUppercased();
    
    /**
     * Retrieves the capitalized name.
     * @return such name.
     */
    @NotNull
    String getNameCapitalized();

    /**
     * Retrieves the name, in lower case.
     * @return such value.
     */
    @NotNull
    String getNameLowercased();

    /**
     * Retrieves the table name, uncapitalized.
     * @return such value.
     */
    @NotNull
    String getUncapitalizedName();

    /**
     * Retrieves the value-object name associated to the table name.
     * @return such name.
     */
    @NotNull
    String getVoName();

    /**
     * Retrieves the table's name in lower-case, once normalized.
     * @return such information.
     */
    @NotNull
    String getNameNormalizedLowercased();

    /**
     * Retrieves the table's name in lower-case, once normalized.
     * @return such information.
     */
    @NotNull
    String getSingularNameNormalizedLowercased();

    /**
     * Retrieves the table's name in lower-case, once normalized.
     * @return such information.
     */
    @NotNull
    String getNameNormalized();

    /**
     * Retrieves the table's name once normalized.
     * @return such information.
     */
    @NotNull
    String getSingularNameCapitalized();

    /**
     * Retrieves the singular table's name, upper-cased.
     * @return such information.
     */
    @NotNull
    String getSingularNameUppercased();

    /**
     * Retrieves the singular table's name, lower-cased.
     * @return such information.
     */
    @SuppressWarnings("unused")
    @NotNull
    String getSingularNameLowercased();

    /**
     * Retrieves all attributes, including the parent's.
     * @return such attributes.
     */
    @NotNull
    List<Attribute> getAllAttributes();

    /**
     * Retrieves all parent tables.
     * @return such tables.
     */
    @NotNull
    List<Table> getAllParentTables();

    /**
     * Retrieves all attributes, including the parent's, but not the externally-managed.
     * @return such attributes.
     */
    @NotNull
    List<Attribute> getAllNonManagedExternallyAttributes();

    /**
     * Retrieves all attributes, including the parent's, but not the externally-managed,
     * plus primary key.
     * @return such attributes.
     */
    @NotNull
    List<Attribute> getAllNonManagedExternallyPlusPkAttributes();

    /**
     * Retrieves all attributes, including the parent's, but not the externally-managed,
     * or read-only, plus primary key.
     * @return such attributes.
     */
    @NotNull
    List<Attribute> getAllNonManagedExternallyNonReadOnlyPlusPkAttributes();

    /**
     * Retrieves the non-parent attributes.
     * @return such attributes.
     */
    @NotNull
    List<Attribute> getNonParentAttributes();

    /**
     * Retrieves the non-read-only attributes.
     * @return such attributes.
     */
    @NotNull
    List<Attribute> getNonReadOnlyAttributes();

    /**
     * Retrieves the list of non-parent, non-externally-managed
     * attributes.
     * @return such list.
     */
    @NotNull
    List<Attribute> getNonParentNonManagedExternallyAttributes();

    /**
     * Retrieves the list of parent's all attributes and the non-parent own attributes.
     * @return such list.
     */
    @NotNull
    List<Attribute> getAllParentAndNonParentAttributes();

    /**
     * Retrieves the list of parent's all attributes and the non-parent
     * non-managed-externally, non-read-only own attributes.
     * @return such list.
     */
    @NotNull
    List<Attribute> getAllParentAndNonParentNonManagedExternallyNonReadOnlyAttributes();

    /**
     * Retrieves the list of parent's all attributes and the non-parent
     * non-managed-externally, non-read-only own attributes, plus the primary key.
     * @return such list.
     */
    @NotNull
    List<Attribute> getAllParentAndNonParentNonManagedExternallyNonReadOnlyPlusPkAttributes();

    /**
     * Retrieves the list of parent's all attributes and the non-parent,
     * non-read-only own attributes.
     * @return such list.
     */
    @NotNull
    List<Attribute> getAllParentAndNonParentNonReadOnlyAttributes();

    /**
     * Retrieves the parent's and non parent's read-only attributes.
     * @return such information.
     */
    @NotNull
    List<Attribute> getAllParentAndNonParentReadOnlyAttributes();

    /**
     * Retrieves the list of dynamic queries.
     * @return such list.
     */
    @SuppressWarnings("unused")
    @NotNull
    List<Sql> getDynamicQueries();
}
