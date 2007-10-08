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
import org.acmsl.queryj.tools.metadata.vo.Table;

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
    public String getNameUppercased();
    
    /**
     * Retrieves the capitalized name.
     * @return such name.
     */
    public String getNameCapitalized();

    /**
     * Retrieves the name, in lower case.
     * @return such value.
     */
    public String getNameLowercased();

    /**
     * Retrieves the table name, uncapitalized.
     * @return such value.
     */
    public String getUncapitalizedName();

    /**
     * Retrieves the value-object name associated to the table name.
     * @return such name.
     */
    public String getVoName();

    /**
     * Retrieves the table's name in lower-case, once normalized.
     * @return such information.
     */
    public String getNameNormalizedLowercased();

    /**
     * Retrieves the table's name in lower-case, once normalized.
     * @return such information.
     */
    public String getSingularNameNormalizedLowercased();

    /**
     * Retrieves the table's name in lower-case, once normalized.
     * @return such information.
     */
    public String getNameNormalized();

    /**
     * Retrieves the table's name once normalized.
     * @return such information.
     */
    public String getSingularNameCapitalized();

    /**
     * Retrieves the singular table's name, upper-cased.
     * @return such information.
     */
    public String getSingularNameUppercased();

    /**
     * Retrieves the singular table's name, lower-cased.
     * @return such information.
     */
    public String getSingularNameLowercased();

    /**
     * Retrieves all attributes, including the parent's.
     * @return such attributes.
     */
    public List getAllAttributes();

    /**
     * Retrieves all parent tables.
     * @return such tables.
     */
    public List getAllParentTables();

    /**
     * Retrieves all attributes, including the parent's, but not the externally-managed.
     * @return such attributes.
     */
    public List getAllNonManagedExternallyAttributes();

    /**
     * Retrieves all attributes, including the parent's, but not the externally-managed,
     * plus primary key.
     * @return such attributes.
     */
    public List getAllNonManagedExternallyPlusPkAttributes();

    /**
     * Retrieves all attributes, including the parent's, but not the externally-managed,
     * or read-only, plus primary key.
     * @return such attributes.
     */
    public List getAllNonManagedExternallyNonReadOnlyPlusPkAttributes();

    /**
     * Retrieves the non-parent attributes.
     * @return such attributes.
     */
    public List getNonParentAttributes();

    /**
     * Retrieves the non-read-only attributes.
     * @return such attributes.
     */
    public List getNonReadOnlyAttributes();

    /**
     * Retrieves the list of non-parent, non-externally-managed
     * attributes.
     * @return such list.
     */
    public List getNonParentNonManagedExternallyAttributes();

    /**
     * Retrieves the list of parent's all attributes and the non-parent own attributes.
     * @return such list.
     */
    public List getAllParentAndNonParentAttributes();

    /**
     * Retrieves the list of parent's all attributes and the non-parent
     * non-managed-externally, non-read-only own attributes.
     * @return such list.
     */
    public List getAllParentAndNonParentNonManagedExternallyNonReadOnlyAttributes();

    /**
     * Retrieves the list of parent's all attributes and the non-parent
     * non-managed-externally, non-read-only own attributes, plus the primary key.
     * @return such list.
     */
    public List getAllParentAndNonParentNonManagedExternallyNonReadOnlyPlusPkAttributes();

    /**
     * Retrieves the list of parent's all attributes and the non-parent,
     * non-read-only own attributes.
     * @return such list.
     */
    public List getAllParentAndNonParentNonReadOnlyAttributes();

    /**
     * Retrieves the parent's and non parent's read-only attributes.
     * @return such information.
     */
    public List getAllParentAndNonParentReadOnlyAttributes();
}
