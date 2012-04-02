//;-*- mode: java -*-
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
 * Filename: DAOTestTemplateFactory.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Is able to create DAO test templates.
 *
 */
package org.acmsl.queryj.tools.templates.dao;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.tools.metadata.MetadataManager;
import org.acmsl.queryj.tools.templates.dao.DAOTestTemplate;
import org.acmsl.queryj.tools.templates.TableTemplate;

/*
 * Importing some ACM-SL Commons classes.
 */
import org.acmsl.commons.patterns.Factory;
import org.jetbrains.annotations.NotNull;

/**
 * Is able to create DAO test templates.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public interface DAOTestTemplateFactory
    extends  Factory
{
    /**
     * Generates a DAO test template.
     * @param tableTemplate the table template.
     * @param metadataManager the metadata manager.
     * @param packageName the package name.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param quote the identifier quote string.
     * @param daoPackageName the DAO's package name.
     * @param valueObjectPackageName the value object's package name.
     * @param jdbcDriver the JDBC driver.
     * @param jdbcUrl the JDBC URL.
     * @param jdbcUsername the JDBC username.
     * @param jdbcPassword the JDBC password.
     * @param header the header.
     * @return a template.
     */
    @NotNull
    public DAOTestTemplate createDAOTestTemplate(
        final TableTemplate tableTemplate,
        final MetadataManager metadataManager,
        final String packageName,
        final String engineName,
        final String engineVersion,
        final String quote,
        final String daoPackageName,
        final String valueObjectPackageName,
        final String jdbcDriver,
        final String jdbcUrl,
        final String jdbcUsername,
        final String jdbcPassword,
        final String header);
} 
