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
 * Description: Contains the subtemplates required to create abstract base
 *              JDBC DAO.
 *
 */
package org.acmsl.queryj.tools.templates.dao;

/*
 * Importing some project-specific classes.
 */
import org.acmsl.queryj.tools.metadata.DecoratorFactory;
import org.acmsl.queryj.tools.templates.AbstractTemplate;
import org.acmsl.queryj.tools.templates.TableTemplate;

/**
 * Contains the subtemplates required to create abstract base JDBC DAO.
 * @author <a href="mailto:chous@acm-sl.org"
 *         >Jose San Leandro</a>
 */
public abstract class AbstractJdbcDAOTemplate
    extends  AbstractTemplate
{
    /**
     * The header.
     */
    private String m__strHeader;

    /**
     * The package declaration.
     */
    private String m__strPackageDeclaration;

    /**
     * The package name.
     */
    private String m__strPackageName;

    /**
     * The ACM-SL import statements.
     */
    private String m__strAcmslImports;

    /**
     * The JDK import statements.
     */
    private String m__strJdkImports;

    /**
     * The JDK extension import statements.
     */
    private String m__strJdkExtensionImports;

    /**
     * The Logging import statements.
     */
    private String m__strLoggingImports;

    /**
     * The class Javadoc.
     */
    private String m__strJavadoc;

    /**
     * The class definition.
     */
    private String m__strClassDefinition;

    /**
     * The class start.
     */
    private String m__strClassStart;

    /**
     * The class constructor.
     */
    private String m__strClassConstructor;

    /**
     * The attribute accessors.
     */
    private String m__strAttributeAccessors;

    /**
     * The connection retrieval methods.
     */
    private String m__strConnectionRetrievalMethods;

    /**
     * The commit methods.
     */
    private String m__strCommitMethods;

    /**
     * The rollback methods.
     */
    private String m__strRollbackMethods;

    /**
     * The connection closing methods.
     */
    private String m__strConnectionClosingMethods;

    /**
     * The transaction token factory methods.
     */
    private String m__strTransactionTokenFactoryMethods;

    /**
     * The class end.
     */
    private String m__strClassEnd;

    /**
     * Builds an <code>AbstractJdbcDAOTemplate</code> using given information.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @param header the header.
     * @param packageDeclaration the package declaration.
     * @param packageName the package name.
     * @param acmslImports the ACM-SL imports.
     * @param jdkImports the JDK imports.
     * @param jdkExtensionImports the JDK extension imports.
     * @param loggingImports the commons-logging imports.
     * @param javadoc the class Javadoc.
     * @param classDefinition the class definition.
     * @param classStart the class start.
     * @param classConstructor the class constructor.
     * @param attributeAccessors the attribute accessors.
     * @param connectionRetrievalMethods the connection retrieval methods.
     * @param commitMethods the commit methods.
     * @param rollbackMethods the rollback methods.
     * @param connectionClosingMethods the connection closing methods.
     * @param transactionTokenFactoryMethods the transaction token factory
     * methods.
     * @param classEnd the class end.
     */
    protected AbstractJdbcDAOTemplate(
        final DecoratorFactory decoratorFactory,
        final String header,
        final String packageDeclaration,
        final String packageName,
        final String acmslImports,
        final String jdkImports,
        final String jdkExtensionImports,
        final String loggingImports,
        final String javadoc,
        final String classDefinition,
        final String classStart,
        final String classConstructor,
        final String attributeAccessors,
        final String connectionRetrievalMethods,
        final String commitMethods,
        final String rollbackMethods,
        final String connectionClosingMethods,
        final String transactionTokenFactoryMethods,
        final String classEnd)
    {
        super(decoratorFactory);
        immutableSetHeader(header);
        immutableSetPackageDeclaration(packageDeclaration);
        immutableSetPackageName(packageName);
        immutableSetAcmslImports(acmslImports);
        immutableSetJdkImports(jdkImports);
        immutableSetJdkExtensionImports(jdkExtensionImports);
        immutableSetLoggingImports(loggingImports);
        immutableSetJavadoc(javadoc);
        immutableSetClassDefinition(classDefinition);
        immutableSetClassStart(classStart);
        immutableSetClassConstructor(classConstructor);
        immutableSetAttributeAccessors(attributeAccessors);
        immutableSetConnectionRetrievalMethods(
            connectionRetrievalMethods);
        immutableSetCommitMethods(commitMethods);
        immutableSetRollbackMethods(rollbackMethods);
        immutableSetConnectionClosingMethods(connectionClosingMethods);
        immutableSetTransactionTokenFactoryMethods(
            transactionTokenFactoryMethods);
        immutableSetClassEnd(classEnd);
    }

    /**
     * Specifies the header.
     * @param header the new header.
     */
    private void immutableSetHeader(final String header)
    {
        m__strHeader = header;
    }

    /**
     * Specifies the header.
     * @param header the new header.
     */
    protected void setHeader(final String header)
    {
        immutableSetHeader(header);
    }

    /**
     * Retrieves the header.
     * @return such information.
     */
    public String getHeader() 
    {
        return m__strHeader;
    }

    /**
     * Specifies the package declaration.
     * @param packageDeclaration the new package declaration.
     */
    private void immutableSetPackageDeclaration(final String packageDeclaration)
    {
        m__strPackageDeclaration = packageDeclaration;
    }

    /**
     * Specifies the package declaration.
     * @param packageDeclaration the new package declaration.
     */
    protected void setPackageDeclaration(final String packageDeclaration)
    {
        immutableSetPackageDeclaration(packageDeclaration);
    }

    /**
     * Retrieves the package declaration.
     * @return such information.
     */
    public String getPackageDeclaration() 
    {
        return m__strPackageDeclaration;
    }

    /**
     * Specifies the package name.
     * @param packageName the new package name.
     */
    private void immutableSetPackageName(final String packageName)
    {
        m__strPackageName = packageName;
    }

    /**
     * Specifies the package name.
     * @param packageName the new package name.
     */
    protected void setPackageName(final String packageName)
    {
        immutableSetPackageName(packageName);
    }

    /**
     * Retrieves the package name.
     * @return such information.
     */
    public String getPackageName() 
    {
        return m__strPackageName;
    }

    /**
     * Specifies the ACM-SL imports.
     * @param acmslImports the new ACM-SL imports.
     */
    private void immutableSetAcmslImports(final String acmslImports)
    {
        m__strAcmslImports = acmslImports;
    }

    /**
     * Specifies the ACM-SL imports.
     * @param acmslImports the new ACM-SL imports.
     */
    protected void setAcmslImports(final String acmslImports)
    {
        immutableSetAcmslImports(acmslImports);
    }

    /**
     * Retrieves the ACM-SL imports.
     * @return such information.
     */
    public String getAcmslImports() 
    {
        return m__strAcmslImports;
    }

    /**
     * Specifies the JDK imports.
     * @param jdkImports the new JDK imports.
     */
    private void immutableSetJdkImports(final String jdkImports)
    {
        m__strJdkImports = jdkImports;
    }

    /**
     * Specifies the JDK imports.
     * @param jdkImports the new JDK imports.
     */
    protected void setJdkImports(final String jdkImports)
    {
        immutableSetJdkImports(jdkImports);
    }

    /**
     * Retrieves the JDK imports.
     * @return such information.
     */
    public String getJdkImports() 
    {
        return m__strJdkImports;
    }

    /**
     * Specifies the JDK extension imports.
     * @param jdkExtensionImports the new JDK extension imports.
     */
    private void immutableSetJdkExtensionImports(final String jdkExtensionImports)
    {
        m__strJdkExtensionImports = jdkExtensionImports;
    }

    /**
     * Specifies the JDK extension imports.
     * @param jdkExtensionImports the new JDK extension imports.
     */
    protected void setJdkExtensionImports(final String jdkExtensionImports)
    {
        immutableSetJdkExtensionImports(jdkExtensionImports);
    }

    /**
     * Retrieves the JDK extension imports.
     * @return such information.
     */
    public String getJdkExtensionImports() 
    {
        return m__strJdkExtensionImports;
    }


    /**
     * Specifies the logging imports.
     * @param loggingImports the new logging imports.
     */
    private void immutableSetLoggingImports(final String loggingImports)
    {
        m__strLoggingImports = loggingImports;
    }

    /**
     * Specifies the logging imports.
     * @param loggingImports the new logging imports.
     */
    protected void setLoggingImports(final String loggingImports)
    {
        immutableSetLoggingImports(loggingImports);
    }

    /**
     * Retrieves the logging imports.
     * @return such information.
     */
    public String getLoggingImports() 
    {
        return m__strLoggingImports;
    }

    /**
     * Specifies the javadoc.
     * @param javadoc the new javadoc.
     */
    private void immutableSetJavadoc(final String javadoc)
    {
        m__strJavadoc = javadoc;
    }

    /**
     * Specifies the javadoc.
     * @param javadoc the new javadoc.
     */
    protected void setJavadoc(final String javadoc)
    {
        immutableSetJavadoc(javadoc);
    }

    /**
     * Retrieves the javadoc.
     * @return such information.
     */
    public String getJavadoc() 
    {
        return m__strJavadoc;
    }

    /**
     * Specifies the class definition.
     * @param classDefinition the new class definition.
     */
    private void immutableSetClassDefinition(final String classDefinition)
    {
        m__strClassDefinition = classDefinition;
    }

    /**
     * Specifies the class definition.
     * @param classDefinition the new class definition.
     */
    protected void setClassDefinition(final String classDefinition)
    {
        immutableSetClassDefinition(classDefinition);
    }

    /**
     * Retrieves the class definition.
     * @return such information.
     */
    public String getClassDefinition() 
    {
        return m__strClassDefinition;
    }

    /**
     * Specifies the class start.
     * @param classStart the new class start.
     */
    private void immutableSetClassStart(final String classStart)
    {
        m__strClassStart = classStart;
    }

    /**
     * Specifies the class start.
     * @param classStart the new class start.
     */
    protected void setClassStart(final String classStart)
    {
        immutableSetClassStart(classStart);
    }

    /**
     * Retrieves the class start.
     * @return such information.
     */
    public String getClassStart() 
    {
        return m__strClassStart;
    }

    /**
     * Specifies the class constructor
     * @param constructor such source code.
     */
    private void immutableSetClassConstructor(final String constructor)
    {
        m__strClassConstructor = constructor;
    }

    /**
     * Specifies the class constructor
     * @param constructor such source code.
     */
    protected void setClassConstructor(final String constructor)
    {
        immutableSetClassConstructor(constructor);
    }

    /**
     * Retrieves the class constructor.
     * @return such source code.
     */
    public String getClassConstructor()
    {
        return m__strClassConstructor;
    }

    /**
     * Specifies the attribute accessors.
     * @param attributeAccessors the attribute accessors.
     */
    private void immutableSetAttributeAccessors(final String attributeAccessors)
    {
        m__strAttributeAccessors = attributeAccessors;
    }

    /**
     * Specifies the attribute accessors.
     * @param attributeAccessors the attribute accessors.
     */
    protected void setAttributeAccessors(final String attributeAccessors)
    {
        immutableSetAttributeAccessors(attributeAccessors);
    }

    /**
     * Retrieves the attribute accessors.
     * @return such methods.
     */
    public String getAttributeAccessors()
    {
        return m__strAttributeAccessors;
    }

    /**
     * Specifies the connection retrieval methods,
     * @param connectionRetrievalMethods the connection retrieval
     * methods.
     */
    private void immutableSetConnectionRetrievalMethods(
        String connectionRetrievalMethods)
    {
        m__strConnectionRetrievalMethods = connectionRetrievalMethods;
    }

    /**
     * Specifies the connection retrieval methods,
     * @param connectionRetrievalMethods the connection retrieval
     * methods.
     */
    protected void setConnectionRetrievalMethods(
        String connectionRetrievalMethods)
    {
        immutableSetConnectionRetrievalMethods(
            connectionRetrievalMethods);
    }

    /**
     * Retrieves the connection retrieval methods.
     * @return such methods.
     */
    public String getConnectionRetrievalMethods()
    {
        return m__strConnectionRetrievalMethods;
    }

    /**
     * Specifies the commit methods.
     * @param commitMethods the commit methods.
     */
    private void immutableSetCommitMethods(final String commitMethods)
    {
        m__strCommitMethods = commitMethods;
    }

    /**
     * Specifies the commit methods.
     * @param commitMethods the commit methods.
     */
    protected void setCommitMethods(final String commitMethods)
    {
        immutableSetCommitMethods(commitMethods);
    }

    /**
     * Retrieves the commit methods.
     * @return such methods.
     */
    public String getCommitMethods()
    {
        return m__strCommitMethods;
    }

    /**
     * Specifies the rollback methods.
     * @param rollbackMethods the rollback methods.
     */
    private void immutableSetRollbackMethods(final String rollbackMethods)
    {
        m__strRollbackMethods = rollbackMethods;
    }

    /**
     * Specifies the rollback methods.
     * @param rollbackMethods the rollback methods.
     */
    protected void setRollbackMethods(final String rollbackMethods)
    {
        immutableSetRollbackMethods(rollbackMethods);
    }

    /**
     * Retrieves the rollback methods.
     * @return such methods.
     */
    public String getRollbackMethods()
    {
        return m__strRollbackMethods;
    }

    /**
     * Specifies the connection closing methods,
     * @param connectionClosingMethods the connection closing
     * methods.
     */
    private void immutableSetConnectionClosingMethods(
        String connectionClosingMethods)
    {
        m__strConnectionClosingMethods = connectionClosingMethods;
    }

    /**
     * Specifies the connection closing methods,
     * @param connectionClosingMethods the connection closing
     * methods.
     */
    protected void setConnectionClosingMethods(
        String connectionClosingMethods)
    {
        immutableSetConnectionClosingMethods(
            connectionClosingMethods);
    }

    /**
     * Retrieves the connection closing methods.
     * @return such methods.
     */
    public String getConnectionClosingMethods()
    {
        return m__strConnectionClosingMethods;
    }

    /**
     * Specifies the transaction token factory methods.
     * @param transactionTokenFactoryMethods such methods.
     */
    private void immutableSetTransactionTokenFactoryMethods(
        String transactionTokenFactoryMethods)
    {
        m__strTransactionTokenFactoryMethods = transactionTokenFactoryMethods;
    }

    /**
     * Specifies the transaction token factory methods.
     * @param transactionTokenFactoryMethods such methods.
     */
    protected void setTransactionTokenFactoryMethods(
        String transactionTokenFactoryMethods)
    {
        immutableSetTransactionTokenFactoryMethods(
            transactionTokenFactoryMethods);
    }

    /**
     * Retrieves the transaction token factory methods.
     * @return such methods.
     */
    public String getTransactionTokenFactoryMethods()
    {
        return m__strTransactionTokenFactoryMethods;
    }

    /**
     * Specifies the class end.
     * @param classEnd the new class end.
     */
    private void immutableSetClassEnd(final String classEnd)
    {
        m__strClassEnd = classEnd;
    }

    /**
     * Specifies the class end.
     * @param classEnd the new class end.
     */
    protected void setClassEnd(final String classEnd)
    {
        immutableSetClassEnd(classEnd);
    }

    /**
     * Retrieves the class end.
     * @return such information.
     */
    public String getClassEnd() 
    {
        return m__strClassEnd;
    }

    /**
     * Builds the header for logging purposes.
     * @return such header.
     */
    protected String buildHeader()
    {
        return"Generating JdbcDAO.";
    }
}
