/*
                        QueryJ

    Copyright (C) 2002  Jose San Leandro Armendáriz
                        jsanleandro@yahoo.es
                        chousz@yahoo.com

    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    version 2.1 of the License, or (at your option) any later version.

    This library is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    Lesser General Public License for more details.

    You should have received a copy of the GNU General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA

    Thanks to ACM S.L. for distributing this library under the GPL license.
    Contact info: jsanleandro@yahoo.es
    Postal Address: c/Playa de Lagoa, 1
                    Urb. Valdecabañas
                    Boadilla del monte
                    28660 Madrid
                    Spain

 ******************************************************************************
 *
 * Filename: $RCSfile$
 *
 * Author: Jose San Leandro Armendáriz
 *
 * Description: Represents a transaction runtime environment. The one that
 *              created it manages the transaction.
 *
 * Last modified by: $Author$ at $Date$
 *
 * File version: $Revision$
 *
 * Project version: $Name$
 *
 * $Id$
 *
 */
package org.acmsl.queryj.dao;

/**
 * Represents a transaction runtime environment. The one that created it
 * manages the transaction.
 * @author <a href="mailto:jsanleandro@yahoo.es"
           >Jose San Leandro Armendáriz</a>
 * @version $Revision$
 */
public interface TransactionToken
{
    /**
     * Checks if there's any active transaction.
     * @return true if a transaction is active.
     */
    public boolean isTransactionAlive();

    /**
     * Gets notified whenever the transaction starts.
     */
    public void beginTransaction();

    /**
    * Releases the transaction.
    */
    public void release();

    /**
     * Takes into account that the transaction is over.
     */
    public void endTransaction();

    /**
     * Sets the need for a rollback of the whole transaction.
     * @param flag to indicate to rollback the transaction.
     */
    public void setRollbackPending(boolean flag);

    /**
     * Checks the need for a rollback of the whole transaction.
     * @return such information.
     */
    public boolean isRollbackPending();
}
