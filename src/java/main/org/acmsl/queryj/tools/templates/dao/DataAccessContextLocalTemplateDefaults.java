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
 * Filename: $RCSfile$
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Defines the default subtemplates used to generate
 *              the dataAccesContext-local file for declaring
 *              the transactional behaviour.
 *
 */
package org.acmsl.queryj.tools.templates.dao;

/**
 * Defines the default subtemplates used to generate the dataAccesContext-local file
 * for declaring the transactional behaviour.
 * @author <a href="mailto:chous@acm-sl.org"
 *         >Jose San Leandro</a>
 */
public interface DataAccessContextLocalTemplateDefaults
{
    /**
     * The default header.
     */
    public static final String DEFAULT_HEADER =
          "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
        + "<!DOCTYPE beans PUBLIC \"-//SPRING//DTD BEAN//EN\" \"http://www.springframework.org/dtd/spring-beans.dtd\">\n"
        + "<!--\n"
        + "                       QueryJ\n"
        + "\n"
        + "   Copyright (C) 2002-2005  Jose San Leandro Armendariz\n"
        + "                       chous@acm-sl.org\n"
        + "                       chousz@yahoo.com\n"
        + "\n"
        + "   This library is free software; you can redistribute it and/or\n"
        + "   modify it under the terms of the GNU General Public\n"
        + "   License as published by the Free Software Foundation; either\n"
        + "   version 2 of the License, or any later "
        + "ersion.\n"
        + "\n"
        + "   This library is distributed in the hope that it will be "
        + "useful,\n"
        + "   but WITHOUT ANY WARRANTY; without even the implied warranty "
        + "of\n"
        + "   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the "
        + "GNU\n"
        + "   General Public License for more details.\n"
        + "\n"
        + "   You should have received a copy of the GNU General Public\n"
        + "   License along with this library; if not, write to the Free "
        + "Software\n"
        + "   Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  "
        + "02111-1307  USA\n"
        + "\n"
        + "   Thanks to ACM S.L. for distributing this library under the GPL "
        + "license.\n"
        + "   Contact info: chous@acm-sl.org\n"
        + "\n\n"
        + "  Filename: $" + "RCSfile: $\n"
        + "\n"
        + "  Author: QueryJ\n"
        + "\n"
        + "  Description: Specifies the transactional behaviour of the DAO layer.\n"
        + "\n"
        + "  Last modified by: $" + "Author: $ at $" + "Date: $\n"
        + "\n"
        + "  File version: $" + "Revision: $\n"
        + " \n"
        + "  Project version: $" + "Name: $\n"
        + "\n"
        + " $" + "Id: $\n"
        + "-->\n";

    /**
     * The resource definitions.
     * @param 0 the JNDI location.
     * @param 1 the DAO definitions.
     */
    public static final String DEFAULT_RESOURCE_DEFINITION =
          "<beans>\n"
        + "  <!-- ========================= RESOURCE DEFINITIONS ========================= -->\n"
        + "  <!-- Main JNDI DataSource for J2EE environments -->\n"
        + "  <bean id=\"dataSource\" class=\"org.springframework.jndi.JndiObjectFactoryBean\">\n"
        + "    <property name=\"jndiName\"><value>{0}</value></property>\n"
        + "  </bean>\n\n"
        + "  <bean\n"
        + "    id=\"transactionManager\"\n"
        + "    class=\"org.springframework.jdbc.datasource.DataSourceTransactionManager\">\n"
        + "    <property name=\"dataSource\"><ref local=\"dataSource\"/></property>\n"
        + "  </bean>\n"
        + "{1}"
        + "</beans>\n";

    /**
     * The dao definitions.
     * @param 0 capitalized value object name.
     * @param 1 the dao package.
     * @param 2 the capitalized engine name.
     */
    public static final String DEFAULT_DAO_DEFINITION =
          "  <!-- DAO declaration for {0} -->\n"
        + "  <bean id=\"{0}DAO\" class=\"{1}.{2}{0}DAO\">\n"
        + "    <property name=\"dataSource\"><ref local=\"dataSource\"/></property>\n"
        + "  </bean>\n\n"
        + "  <!-- Transactional declaration for {0} -->\n"
        + "  <bean id=\"{0}\" class=\"org.springframework.transaction.interceptor.TransactionProxyFactoryBean\">\n"
        + "    <property name=\"transactionManager\"><ref local=\"transactionManager\"/></property>\n"
        + "    <property name=\"target\"><ref bean=\"{0}DAO\"/></property>\n"
        + "    <property name=\"transactionAttributes\">\n"
        + "      <props>\n"
        + "        <!-- See 17.6.2.7, table 14, of EJB Specification 2.1 Final Release -->\n"
        + "        <prop key=\"*\">PROPAGATION_REQUIRED</prop>\n"
        + "      </props>\n"
        + "    </property>\n"
        + "  </bean>\n\n\n";
}
