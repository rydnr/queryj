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
 * Description: Contains the default subtemplates for building DAO interfaces.
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
package org.acmsl.queryj.tools.templates.dao;

/*
 * Importing some project-specific classes.
 */
import org.acmsl.queryj.tools.DatabaseMetaDataManager;
import org.acmsl.queryj.tools.MetaDataUtils;
import org.acmsl.queryj.tools.templates.TableTemplate;

/*
 * Importing some ACM-SL classes.
 */
import org.acmsl.commons.utils.EnglishGrammarUtils;
import org.acmsl.commons.utils.StringUtils;

/*
 * Importing some JDK classes.
 */
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/*
 * Importing Apache Commons Logging classes.
 */
import org.apache.commons.logging.LogFactory;

/**
 * Contains the default subtemplates for building DAO interfaces.
 * @author <a href="mailto:jsanleandro@yahoo.es"
           >Jose San Leandro</a>
 * @version $Revision$
 */
public interface BaseDAOTemplateDefaults
{
    /**
     * The default header.
     */
    public static final String DEFAULT_HEADER =
          "/*\n"
        + "                        QueryJ\n"
        + "\n"
        + "    Copyright (C) 2002  Jose San Leandro Armendariz\n"
        + "                        jsanleandro@yahoo.es\n"
        + "                        chousz@yahoo.com\n"
        + "\n"
        + "    This library is free software; you can redistribute it and/or\n"
        + "    modify it under the terms of the GNU General Public\n"
        + "    License as published by the Free Software Foundation; either\n"
        + "    version 2 of the License, or any later "
        + "version.\n"
        + "\n"
        + "    This library is distributed in the hope that it will be "
        + "useful,\n"
        + "    but WITHOUT ANY WARRANTY; without even the implied warranty "
        + "of\n"
        + "    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the "
        + "GNU\n"
        + "    General Public License for more details.\n"
        + "\n"
        + "    You should have received a copy of the GNU General Public\n"
        + "    License along with this library; if not, write to the Free "
        + "Software\n"
        + "    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  "
        + "02111-1307  USA\n"
        + "\n"
        + "    Thanks to ACM S.L. for distributing this library under the GPL "
        + "license.\n"
        + "    Contact info: jsanleandro@yahoo.es\n"
        + "    Postal Address: c/Playa de Lagoa, 1\n"
        + "                    Urb. Valdecabanas\n"
        + "                    Boadilla del monte\n"
        + "                    28660 Madrid\n"
        + "                    Spain\n"
        + "\n"
        + " *****************************************************************"
        + "*************\n"
        + " *\n"
        + " * Filename: $" + "RCSfile: $\n"
        + " *\n"
        + " * Author: QueryJ\n"
        + " *\n"
        + " * Description: DAO abstract layer responsible of retrieving\n"
        + " *              \"{0}\" structures from persistence layers.\n"
         // Table name
        + " *\n"
        + " * Last modified by: $" + "Author: $ at $" + "Date: $\n"
        + " *\n"
        + " * File version: $" + "Revision: $\n"
        + " *\n"
        + " * Project version: $" + "Name: $\n"
        + " *\n"
        + " * $" + "Id: $\n"
        + " *\n"
        + " */\n";

    /**
     * The package declaration.
     */
    public static final String PACKAGE_DECLARATION = "package {0};\n\n"; // package

    /**
     * The ACM-SL imports.
     */
    public static final String ACMSL_IMPORTS =
          "/*\n"
        + " * Importing QueryJ classes.\n"
        + " */\n"
        + "import org.acmsl.queryj.dao.TransactionToken;\n\n"
        + "/*\n"
        + " * Importing ACM-SL Commons classes.\n"
        + " */\n"
        + "import org.acmsl.commons.patterns.dao.DAO;\n"
        + "import org.acmsl.commons.patterns.dao.DataAccessException;\n\n";

    /**
     * The JDK imports.
     */
    public static final String JDK_IMPORTS =
          "/*\n"
        + " * Importing some JDK classes.\n"
        + " */\n"
        + "import java.math.BigDecimal;\n"
        + "import java.util.Calendar;\n"
        + "import java.util.Date;\n\n";

    /**
     * The default class Javadoc.
     */
    public static final String DEFAULT_JAVADOC =
          "/**\n"
        + " * DAO abstract class responsible of requesting services\n"
        + " * to manage \"{0}\" structures from persistence layers.\n"
         // Table name
        + " * @author <a href=\"http://maven.acm-sl.org/queryj\">QueryJ</a>\n"
        + " * @version $" + "Revision: $\n"
        + " */\n";

    /**
     * The class definition.
     */
    public static final String CLASS_DEFINITION =
          "public interface {0}DAO\n"
        + "    extends  DAO\n";
        // table name

    /**
     * The class start.
     */
    public static final String DEFAULT_CLASS_START =
        "{\n";

    /**
     * The find by primary key method.
     */
    public static final String DEFAULT_FIND_BY_PRIMARY_KEY_METHOD =
          "    /**\n"
        + "     * Loads {0} information from the persistence layer filtering\n"
         // table name
        + "     * by its primary keys."
        + "{1}\n"
         // FIND_BY_PRIMARY_KEY_PK_JAVADOC
        + "     * @param transactionToken needed to use an open connection and\n"
        + "     * see previously uncommited inserts/updates/deletes.\n"
        + "     * @return the information extracted from the persistence layer.\n"
        + "     * @throws DataAccessException if the access to the information fails.\n"
        + "     */\n"
        + "    public {2}ValueObject findByPrimaryKey(\n"
         // java table name
        + "{3}"
         // FIND_BY_PRIMARY_KEY_PK_DECLARATION
        + "        final TransactionToken transactionToken)\n"
        + "      throws DataAccessException;\n\n";

    /**
     * The find-by-primary-key method's primary keys javadoc.
     */
    public static final String DEFAULT_FIND_BY_PRIMARY_KEY_PK_JAVADOC =
        "\n     * @param {0} the {1} value to filter.";
         // java pk - pk

    /**
     * The find-by-primary-key method's primary keys declaration.
     */
    public static final String DEFAULT_FIND_BY_PRIMARY_KEY_PK_DECLARATION =
        "        final {0} {1},\n";
         // pk type - java pk

    /**
     * The store method.
     */
    public static final String DEFAULT_INSERT_METHOD =
          "    /**\n"
        + "     * Persists {0} information."
         // table name
        + "{1}"
         // (optional) pk javadoc
        + "{2}\n"
         // insert parameters javadoc
        + "     * @param transactionToken the transaction boundary.\n"
        + "     * @throws DataAccessException if the access to the information fails.\n"
        + "     */\n"
        + "    public void insert(\n"
        + "{3}"
         // (optional) pk declaration
        + "{4}"
         // insert parameters declaration
        + "        final TransactionToken transactionToken)\n"
        + "      throws DataAccessException;\n\n";

    /**
     * The insert parameters javadoc.
     */
    public static final String DEFAULT_INSERT_PARAMETERS_JAVADOC =
        "\n     * @param {0} the {1} information.";
    // field name - field Name

    /**
     * The insert parameters declaration.
     */
    public static final String DEFAULT_INSERT_PARAMETERS_DECLARATION =
        "        final {0} {1},\n";
    // field type - field name

    /**
     * The update method.
     */
    public static final String DEFAULT_UPDATE_METHOD =
          "    /**\n"
        + "     * Updates {0} information\n"
        + "     * in the persistence layer."
         // table name
        + "{1}"
         // (optional) pk javadoc
        + "{2}\n"
         // update parameters javadoc
        + "     * @param transactionToken the transaction boundary.\n"
        + "     * @throws DataAccessException if the access to the information fails.\n"
        + "     */\n"
        + "    public void update(\n"
        + "{3}"
         // (optional) pk declaration
        + "{4}\n"
         // update parameters declaration
        + "        final TransactionToken transactionToken)\n"
        + "      throws DataAccessException;\n\n";

    /**
     * The update parameters javadoc.
     */
    public static final String DEFAULT_UPDATE_PARAMETERS_JAVADOC =
        "\n     * @param {0} the {1} information.";
    // field name - field Name

    /**
     * The update parameters declaration.
     */
    public static final String DEFAULT_UPDATE_PARAMETERS_DECLARATION =
        "        final {0} {1},";
    // field type - field name

    /**
     * The delete method.
     */
    public static final String DEFAULT_DELETE_METHOD =
          "    /**\n"
        + "     * Deletes {0} information from the persistence layer filtering\n"
         // table name
        + "     * by its primary keys."
        + "{1}\n"
         // DELETE_PK_JAVADOC
        + "     * @param transactionToken needed to use an open connection and\n"
        + "     * see previously uncommited inserts/updates/deletes.\n"
        + "     * @return <code>true</code> if the information has been deleted\n"
        + "     * successfully.\n"
        + "     * @throws DataAccessException if the access to the information fails.\n"
        + "     */\n"
        + "    public boolean delete(\n"
         // java table name
        + "{2}"
         // DELETE_PK_DECLARATION
        + "        final TransactionToken  transactionToken)\n"
        + "      throws DataAccessException;\n";

    /**
     * The delete method's primary keys javadoc.
     */
    public static final String DEFAULT_DELETE_PK_JAVADOC =
        "\n     * @param {0} the {1} value to filter.";
         // java pk - pk

    /**
     * The delete method's primary keys declaration.
     */
    public static final String DEFAULT_DELETE_PK_DECLARATION =
        "        final {0} {1},\n";
         // pk type - java pk

    /**
     * The default class end.
     */
    public static final String DEFAULT_CLASS_END = "}\n";
}
