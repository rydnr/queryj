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

 ******************************************************************************
 *
 * Filename: JdbcTableDAO.java
 *
 * Author: Jose San Leandro Armendariz (chous)
 *
 * Description: A TableDAO implementation using JdbcMetadataManager.
 *
 * Date: 6/12/12
 * Time: 10:54 AM
 *
 */
package org.acmsl.queryj.tools.metadata.engines;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/*
* Importing JDK classes.
 */
import java.util.List;

/**
 * A {@link org.acmsl.queryj.tools.metadata.TableDAO} implementation using {@link JdbcMetadataManager}.
 * @author <a href="mailto:chous@acm-sl.org">chous</a>
 * @version 2012/06/12
 */
public class JdbcTableDAO
    extends MetadataManagerTableDAO<JdbcMetadataManager>
{
    /**
     * Creates a {@link JdbcTableDAO} using given manager.
     * @param manager the {@link JdbcMetadataManager} instance.
     */
    public JdbcTableDAO(@NotNull final JdbcMetadataManager manager)
    {
        super(manager);
    }

    /**
     * Retrieves all tables.
     * @return such information.
     */
    @NotNull
    @Override
    public List<String> findAllTableNames()
    {
        return getMetadataManager().getTableNames();
    }
}
