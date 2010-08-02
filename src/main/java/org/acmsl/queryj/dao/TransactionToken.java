/*
                        QueryJ

    Copyright (C) 2002-2004  Jose San Leandro Armendariz
                        chous@acm-sl.org

    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    version 2 of the License, or any later version.

    This library is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    Lesser General Public License for more details.

    You should have received a copy of the GNU General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA

    Thanks to ACM S.L. for distributing this library under the GPL license.
    Contact info: chous@acm-sl.org
    Postal Address: c/Playa de Lagoa, 1
                    Urb. Valdecabanas
                    Boadilla del monte
                    28660 Madrid
                    Spain

 ******************************************************************************
 *
 * Filename: $RCSfile: $
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Represents a transaction runtime environment. The one that
 *              created it manages the transaction.
 */
package org.acmsl.queryj.dao;

/*
 * Importing Spring classes.
 */
import org.springframework.transaction.TransactionStatus;

/**
 * Represents a transaction runtime environment. The one that created it
 * manages the transaction.
 * @author <a href="mailto:chous@acm-sl.org"
           >Jose San Leandro Armendariz</a>
 */
public interface TransactionToken
    extends  TransactionStatus
{
}
