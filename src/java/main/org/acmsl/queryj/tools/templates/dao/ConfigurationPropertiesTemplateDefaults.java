/*
                        QueryJ

    Copyright (C) 2002  Jose San Leandro Armendariz
                        jsanleandro@yahoo.es
                        chousz@yahoo.com

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
    Contact info: jsanleandro@yahoo.es
    Postal Address: c/Playa de Lagoa, 1
                    Urb. Valdecabanas
                    Boadilla del monte
                    28660 Madrid
                    Spain

 ******************************************************************************
 *
 * Filename: $RCSfile$
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Defines the default subtemplates used to generate
 *              the configuration file for configuring
 *              DAOChooser.
 *
 */
package org.acmsl.queryj.tools.templates.dao;

/**
 * Defines the default subtemplates used to generate the configuration file
 * for configuring <code>DAOChooser</code> singleton.
 * @author <a href="mailto:jsanleandro@yahoo.es"
 *         >Jose San Leandro</a>
 */
public interface ConfigurationPropertiesTemplateDefaults
{
    /**
     * The default header.
     */
    public static final String DEFAULT_HEADER =
          "#\n"
        + "#                       QueryJ\n"
        + "#\n"
        + "#   Copyright (C) 2002  Jose San Leandro Armendariz\n"
        + "#                       jsanleandro@yahoo.es\n"
        + "#                       chousz@yahoo.com\n"
        + "#\n"
        + "#   This library is free software; you can redistribute it and/or\n"
        + "#   modify it under the terms of the GNU General Public\n"
        + "#   License as published by the Free Software Foundation; either\n"
        + "#   version 2 of the License, or any later "
        + "version.\n"
        + "#\n"
        + "#   This library is distributed in the hope that it will be "
        + "useful,\n"
        + "#   but WITHOUT ANY WARRANTY; without even the implied warranty "
        + "of\n"
        + "#   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the "
        + "GNU\n"
        + "#   General Public License for more details.\n"
        + "#\n"
        + "#   You should have received a copy of the GNU General Public\n"
        + "#   License along with this library; if not, write to the Free "
        + "Software\n"
        + "#   Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  "
        + "02111-1307  USA\n"
        + "#\n"
        + "#   Thanks to ACM S.L. for distributing this library under the GPL "
        + "license.\n"
        + "#   Contact info: jsanleandro@yahoo.es\n"
        + "#   Postal Address: c/Playa de Lagoa, 1\n"
        + "#                   Urb. Valdecabanas\n"
        + "#                   Boadilla del monte\n"
        + "#                   28660 Madrid\n"
        + "#                   Spain\n"
        + "#\n"
        + "#################################################################"
        + "#############\n"
        + "#\n"
        + "#  Filename: $" + "RCSfile: $\n"
        + "#\n"
        + "#  Author: QueryJ\n"
        + "#\n"
        + "#  Description: Specifies which DAO implementations are used.\n"
        + "#\n"
        + "#  Last modified by: $" + "Author: $ at $" + "Date: $\n"
        + "#\n"
        + "#  File version: $" + "Revision: $\n"
        + "# \n"
        + "#  Project version: $" + "Name: $\n"
        + "#\n"
        + "# $" + "Id: $\n"
        + "#";

    /**
     * The DAO factory settings.
     */
    public static final String DEFAULT_DAO_FACTORY_SETTING =
          "\n\n# {0} DAO implementation.\n"
        + "{1}.{2}.dao={3}.{4}{0}DAOFactory\n"
        + "#{1}.{2}.dao={5}.Mock{0}DAOFactory\n"
        + "#{1}.{2}.dao={6}.XML{0}DAOFactory\n";
}
