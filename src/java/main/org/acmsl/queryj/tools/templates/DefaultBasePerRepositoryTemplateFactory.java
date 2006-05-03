//;-*- mode: java -*-
/*
                        QueryJ

    Copyright (C) 2002-2005  Jose San Leandro Armendariz
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
 * Description: Represents entities able to create per-repository templates.
 *
 */
package org.acmsl.queryj.tools.templates;

/*
 * Importing some ACM-SL classes.
 */
import org.acmsl.queryj.QueryJException;
import org.acmsl.queryj.tools.metadata.MetadataManager;
import org.acmsl.queryj.tools.templates.BasePerRepositoryTemplate;

/*
 * Importing some JDK classes.
 */
import java.util.Collection;

/**
 * Represents entities able to create per-repository templates.
 * @author <a href="mailto:chous@acm-sl.org"
 *         >Jose San Leandro</a>
 */
public interface DefaultBasePerRepositoryTemplateFactory
    extends  BasePerRepositoryTemplateFactory
{
    /**
     * Generates a <i>per-repository</i> template.
     * @param metadataManager the metadata manager.
     * @param packageName the package name.
     * @param basePackageName the base package name.
     * @param engineName the engine name.
     * @param repositoryName the name of the repository.
     * @param tables the tables.
     * @param header the header.
     * @return a template.
     * @throws QueryJException if the input values are invalid.
     */
    public BasePerRepositoryTemplate createTemplate(
        final MetadataManager metadataManager,
        final String packageName,
        final String basePackageName,
        final String repositoryName,
        final String engineName,
        final Collection tables,
        final String header)
      throws  QueryJException;
}