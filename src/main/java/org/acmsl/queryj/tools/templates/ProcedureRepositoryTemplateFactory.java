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
 * Filename: ProcedureRepositoryTemplateFactory.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Is able to create procedure repository templates.
 *
 */
package org.acmsl.queryj.tools.templates;

/*
 * Importing some ACM-SL classes.
 */
import org.acmsl.queryj.tools.metadata.MetadataTypeManager;
import org.acmsl.queryj.tools.templates.ProcedureRepositoryTemplate;

/*
 * Importing some ACM-SL Commons classes.
 */
import org.acmsl.commons.patterns.Factory;
import org.jetbrains.annotations.NotNull;

/**
 * Is able to create procedure repository templates.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public interface ProcedureRepositoryTemplateFactory
    extends  Factory
{
    /**
     * Generates a procedure repository template.
     * @param packageName the package name.
     * @param repository the repository.
     * @param metadataTypeManager the metadata type manager instance.
     * @param header the header.
     * @return such template.
     */
    @NotNull
    public ProcedureRepositoryTemplate createProcedureRepositoryTemplate(
        final String packageName,
        final String repository,
        final MetadataTypeManager metadataTypeManager,
        final String header);
}
