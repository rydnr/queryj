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
    Contact info: chous@acm-sl.org
    Postal Address: c/Playa de Lagoa, 1
                    Urb. Valdecabanas
                    Boadilla del monte
                    28660 Madrid
                    Spain

 ******************************************************************************
 *
 * Filename: ConfigurationPropertiesTemplateFactory.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Represents entities able to create configuration properties.
 *
 */
package org.acmsl.queryj.tools.templates.dao;

/*
 * Importing some ACM-SL classes.
 */
import org.acmsl.queryj.QueryJException;
import org.acmsl.queryj.tools.templates.dao.ConfigurationPropertiesTemplate;

/*
 * Importing Ant classes.
 */
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;

/**
 * Represents entities able to create configuration properties.
 * @author <a href="mailto:chous@acm-sl.org"
 *         >Jose San Leandro</a>
 */
public interface ConfigurationPropertiesTemplateFactory
{
    /**
     * Generates a configuration property template.
     * @param repository the repository name.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param basePackageName the base package name.
     * @param project the project, for logging purposes.
     * @param task the task, for logging purposes.
     * @return a template.
     * @throws QueryJException if the input values are invalid.
     */
    public ConfigurationPropertiesTemplate createConfigurationPropertiesTemplate(
        final String repository,
        final String engineName,
        final String engineVersion,
        final String basePackageName,
        final Project project,
        final Task task)
      throws  QueryJException;
}
