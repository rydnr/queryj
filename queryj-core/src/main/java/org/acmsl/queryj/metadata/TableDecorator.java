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
import org.acmsl.queryj.customsql.Result;
import org.acmsl.queryj.customsql.Sql;
import org.acmsl.queryj.metadata.vo.Attribute;
import org.acmsl.queryj.metadata.vo.Row;
import org.acmsl.queryj.metadata.vo.Table;

/*
 * Importing some JetBrains annotations.
 */
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
    extends  Table<DecoratedString, Attribute<DecoratedString>, ListDecorator<Attribute<DecoratedString>>>
{
    /**
     * Retrieves the read-only attributes.
     * @return such attributes.
     */
    @NotNull
    ListDecorator<Attribute<DecoratedString>> getReadOnlyAttributes();

    /**
     * Retrieves all parent tables.
     * @return such tables.
     */
    @NotNull
    List<Table<DecoratedString, Attribute<DecoratedString>, ListDecorator<Attribute<DecoratedString>>>> getAllParentTables();

    /**
     * Retrieves all attributes, including the parent's, but not the externally-managed.
     * @return such attributes.
     */
    @NotNull
    ListDecorator<Attribute<DecoratedString>> getExternallyManagedAttributes();

    /**
     * Retrieves the list of dynamic queries.
     * @return such list.
     */
    @SuppressWarnings("unused")
    @NotNull
    List<Sql<DecoratedString>> getDynamicQueries();

    /**
     * Retrieves the static contents, if any.
     * @return the list of static contents.
     */
    @NotNull
    List<Row<DecoratedString>> getStaticContent();

    /**
     * Retrieves the list of different results defined for this table (using the referring custom-selects).
     * @return such list.
     */
    @NotNull
    List<Result<DecoratedString>> getDifferentCustomResults();

    /**
     * Retrieves the ordered list of the fully-qualified attribute types.
     * @return such list.
     */
    @NotNull
    List<DecoratedString> getAttributeTypes();
}
