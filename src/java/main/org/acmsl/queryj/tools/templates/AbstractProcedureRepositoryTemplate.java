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
 * Description: Is able to generate procedure repositories according to
 *              database metadata.
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
package org.acmsl.queryj.tools.templates;

/*
 * Importing some project-specific classes.
 */
import org.acmsl.queryj.tools.DatabaseMetaDataManager;
import org.acmsl.queryj.tools.ProcedureMetaData;
import org.acmsl.queryj.tools.ProcedureParameterMetaData;

/*
 * Importing some Ant classes.
 */
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;

/*
 * Importing some JDK classes.
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Is able to generate procedure repositories according to
 * database metadata.
 * @author <a href="mailto:jsanleandro@yahoo.es"
 *         >Jose San Leandro</a>
 * @version $Revision$
 */
public abstract class AbstractProcedureRepositoryTemplate
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
     * The repository.
     */
    private String m__strRepository;

    /**
     * The project import Javadoc.
     */
    private String m__strProjectImportsJavadoc;

    /**
     * The project import statements.
     */
    private String m__strProjectImports;

    /**
     * The ACM-SL import statements.
     */
    private String m__strAcmslImports;

    /**
     * The JDK import statements.
     */
    private String m__strJdkImports;

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
     * The procedure Javadoc.
     */
    private String m__strProcedureJavadoc;

    /**
     * The procedure parameter javadoc.
     */
    private String m__strProcedureParameterJavadoc;

    /**
     * The procedure body.
     */
    private String m__strProcedureBody;

    /**
     * The procedure parameter declaration.
     */
    private String m__strProcedureParameterDeclaration;

    /**
     * The procedure sentence.
     */
    private String m__strProcedureSentence;

    /**
     * OUT parameter registration.
     */
    private String m__strOutParameterRegistration;

    /**
     * IN parameter specification.
     */
    private String m__strInParameterSpecification;

    /**
     * OUT parameter retrieval.
     */
    private String m__strOutParameterRetrieval;

    /**
     * Value object construction.
     */
    private String m__strValueObjectConstruction;

    /**
     * The class end.
     */
    private String m__strClassEnd;

    /**
     * The procedure metadata list.
     */
    private List m__lProceduresMetaData;

    /**
     * The procedure parameters metadata.
     */
    private Map m__mProcedureParametersMetaData;

    /**
     * Builds an <code>AbstractProcedureRepositoryTemplate</code> using
     * given information.
     * @param header the header.
     * @param packageDeclaration the package declaration.
     * @param packageName the package name.
     * @param repository the repository.
     * @param importsJavadoc the project imports javadoc.
     * @param projectImports the project imports.
     * @param acmslImports the ACM-SL imports.
     * @param jdkImports the JDK imports.
     * @param javadoc the class Javadoc.
     * @param classDefinition the class definition.
     * @param classStart the class start.
     * @param procedureJavadoc the procedure Javadoc.
     * @param procedureParameterJavadoc the procedure parameter javadoc.
     * @param procedureBody the procedure body.
     * @param procedureParameterDeclaration the procedure parameter declaration.
     * @param procedureSentence the procedure sentence.
     * @param outParameterRegistration the OUT parameter registration.
     * @param inParameterSpecification the IN parameter specification.
     * @param outParameterRetrieval the OUT parameter retrieval.
     * @param valueObjectConstruction the value object construction.
     * @param classEnd the class end.
     */
    public AbstractProcedureRepositoryTemplate(
        final String header,
        final String packageDeclaration,
        final String packageName,
        final String repository,
        final String importsJavadoc,
        final String projectImports,
        final String acmslImports,
        final String jdkImports,
        final String javadoc,
        final String classDefinition,
        final String classStart,
        final String procedureJavadoc,
        final String procedureParameterJavadoc,
        final String procedureBody,
        final String procedureParameterDeclaration,
        final String procedureSentence,
        final String outParameterRegistration,
        final String inParameterSpecification,
        final String outParameterRetrieval,
        final String valueObjectConstruction,
        final String classEnd,
        final Project project,
        final Task task)
    {
        super(project, task);
        immutableSetHeader(header);
        immutableSetPackageDeclaration(packageDeclaration);
        immutableSetPackageName(packageName);
        immutableSetRepository(repository);
        immutableSetProjectImportsJavadoc(importsJavadoc);
        immutableSetProjectImports(projectImports);
        immutableSetAcmslImports(acmslImports);
        immutableSetJdkImports(jdkImports);
        immutableSetJavadoc(javadoc);
        immutableSetClassDefinition(classDefinition);
        immutableSetClassStart(classStart);
        immutableSetProcedureJavadoc(procedureJavadoc);
        immutableSetProcedureParameterJavadoc(procedureParameterJavadoc);
        immutableSetProcedureBody(procedureBody);
        immutableSetProcedureParameterDeclaration(
            procedureParameterDeclaration);
        immutableSetProcedureSentence(procedureSentence);
        immutableSetOutParameterRegistration(outParameterRegistration);
        immutableSetInParameterSpecification(inParameterSpecification);
        immutableSetOutParameterRetrieval(outParameterRetrieval);
        immutableSetValueObjectConstruction(valueObjectConstruction);
        immutableSetClassEnd(classEnd);
        immutableSetProceduresMetaData(new ArrayList());
        immutableSetProcedureParametersMetaData(new HashMap());
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
     * Specifies the repository.
     * @param repository the new repository.
     */
    private void immutableSetRepository(final String repository)
    {
        m__strRepository = repository;
    }

    /**
     * Specifies the repository.
     * @param repository the new repository.
     */
    protected void setRepository(final String repository)
    {
        immutableSetRepository(repository);
    }

    /**
     * Retrieves the repository.
     * @return such information.
     */
    public String getRepository() 
    {
        return m__strRepository;
    }

    /**
     * Specifies the project imports Javadoc.
     * @param projectImports the new project imports Javadoc.
     */
    private void immutableSetProjectImportsJavadoc(final String importJavadoc)
    {
        m__strProjectImportsJavadoc = importJavadoc;
    }

    /**
     * Specifies the project imports Javadoc.
     * @param importJavadoc the new project imports Javadoc.
     */
    protected void setProjectImportsJavadoc(final String importJavadoc)
    {
        immutableSetProjectImportsJavadoc(importJavadoc);
    }

    /**
     * Retrieves the project imports Javadoc.
     * @return such information.
     */
    public String getProjectImportsJavadoc() 
    {
        return m__strProjectImportsJavadoc;
    }

    /**
     * Specifies the project imports.
     * @param projectImports the new project imports.
     */
    private void immutableSetProjectImports(final String projectImports)
    {
        m__strProjectImports = projectImports;
    }

    /**
     * Specifies the project imports.
     * @param projectImports the new project imports.
     */
    protected void setProjectImports(final String projectImports)
    {
        immutableSetProjectImports(projectImports);
    }

    /**
     * Retrieves the project imports.
     * @return such information.
     */
    public String getProjectImports() 
    {
        return m__strProjectImports;
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
     * Specifies the procedure Javadoc.
     * @param procedureJavadoc the new procedure Javadoc.
     */
    private void immutableSetProcedureJavadoc(final String procedureJavadoc)
    {
        m__strProcedureJavadoc = procedureJavadoc;
    }

    /**
     * Specifies the procedure Javadoc.
     * @param procedureJavadoc the new procedure Javadoc.
     */
    protected void setProcedureJavadoc(final String procedureJavadoc)
    {
        immutableSetProcedureJavadoc(procedureJavadoc);
    }

    /**
     * Retrieves the procedure Javadoc.
     * @return such information.
     */
    public String getProcedureJavadoc() 
    {
        return m__strProcedureJavadoc;
    }

    /**
     * Specifies the procedure parameter Javadoc.
     * @param procedureJavadoc the new procedure parameter Javadoc.
     */
    private void immutableSetProcedureParameterJavadoc(
        final String procedureParameterJavadoc)
    {
        m__strProcedureParameterJavadoc = procedureParameterJavadoc;
    }

    /**
     * Specifies the procedure parameter Javadoc.
     * @param procedureParameterJavadoc the new procedure parameter Javadoc.
     */
    protected void setProcedureParameterJavadoc(
        final String procedureParameterJavadoc)
    {
        immutableSetProcedureParameterJavadoc(procedureParameterJavadoc);
    }

    /**
     * Retrieves the procedure parameter Javadoc.
     * @return such information.
     */
    public String getProcedureParameterJavadoc() 
    {
        return m__strProcedureParameterJavadoc;
    }

    /**
     * Specifies the procedure body.
     * @param procedureBody the new procedure body.
     */
    private void immutableSetProcedureBody(final String procedureBody)
    {
        m__strProcedureBody = procedureBody;
    }

    /**
     * Specifies the procedure body.
     * @param procedureBody the new procedure body.
     */
    protected void setProcedureBody(final String procedureBody)
    {
        immutableSetProcedureBody(procedureBody);
    }

    /**
     * Retrieves the procedure body.
     * @return such information.
     */
    public String getProcedureBody() 
    {
        return m__strProcedureBody;
    }

    /**
     * Specifies the procedure parameter declaration.
     * @param procedureDeclaration the new procedure parameter declaration.
     */
    private void immutableSetProcedureParameterDeclaration(
        final String procedureParameterDeclaration)
    {
        m__strProcedureParameterDeclaration = procedureParameterDeclaration;
    }

    /**
     * Specifies the procedure parameter declaration.
     * @param procedureParameterDeclaration the new procedure parameter
     * declaration.
     */
    protected void setProcedureParameterDeclaration(
        final String procedureParameterDeclaration)
    {
        immutableSetProcedureParameterDeclaration(
            procedureParameterDeclaration);
    }

    /**
     * Retrieves the procedure parameter declaration.
     * @return such information.
     */
    public String getProcedureParameterDeclaration() 
    {
        return m__strProcedureParameterDeclaration;
    }

    /**
     * Specifies the procedure sentence.
     * @param procedureSentence the new procedure sentence.
     */
    private void immutableSetProcedureSentence(final String procedureSentence)
    {
        m__strProcedureSentence = procedureSentence;
    }

    /**
     * Specifies the procedure sentence.
     * @param procedureSentence the new procedure sentence.
     */
    protected void setProcedureSentence(final String procedureSentence)
    {
        immutableSetProcedureSentence(procedureSentence);
    }

    /**
     * Retrieves the procedure sentence.
     * @return such information.
     */
    public String getProcedureSentence() 
    {
        return m__strProcedureSentence;
    }

    /**
     * Specifies the OUT parameter registration.
     * @param outParameterRegistration such template chunk.
     */
    private void immutableSetOutParameterRegistration(
        final String outParameterRegistration)
    {
        m__strOutParameterRegistration = outParameterRegistration;
    }

    /**
     * Specifies the OUT parameter registration.
     * @param outParameterRegistration such template chunk.
     */
    protected void setOutParameterRegistration(
        final String outParameterRegistration)
    {
        immutableSetOutParameterRegistration(outParameterRegistration);
    }

    /**
     * Retrieves the OUT parameter registration.
     * @return such information.
     */
    public String getOutParameterRegistration() 
    {
        return m__strOutParameterRegistration;
    }

    /**
     * Specifies the IN parameter specification.
     * @param inParameterSpecification such template chunk.
     */
    private void immutableSetInParameterSpecification(
        final String inParameterSpecification)
    {
        m__strInParameterSpecification = inParameterSpecification;
    }

    /**
     * Specifies the IN parameter specification.
     * @param inParameterSpecification such template chunk.
     */
    protected void setInParameterSpecification(
        final String inParameterSpecification)
    {
        immutableSetInParameterSpecification(inParameterSpecification);
    }

    /**
     * Retrieves the IN parameter specification.
     * @return such information.
     */
    public String getInParameterSpecification() 
    {
        return m__strInParameterSpecification;
    }

    /**
     * Specifies the OUT parameter retrieval.
     * @param outParameterRetrieval such template chunk.
     */
    private void immutableSetOutParameterRetrieval(
        final String outParameterRetrieval)
    {
        m__strOutParameterRetrieval = outParameterRetrieval;
    }

    /**
     * Specifies the OUT parameter retrieval.
     * @param outParameterRetrieval such template chunk.
     */
    protected void setOutParameterRetrieval(final String outParameterRetrieval)
    {
        immutableSetOutParameterRetrieval(outParameterRetrieval);
    }

    /**
     * Retrieves the OUT parameter retrieval.
     * @return such information.
     */
    public String getOutParameterRetrieval() 
    {
        return m__strOutParameterRetrieval;
    }

    /**
     * Specifies the value object construction.
     * @param valueObjectConstruction such template chunk.
     */
    private void immutableSetValueObjectConstruction(
        final String valueObjectConstruction)
    {
        m__strValueObjectConstruction = valueObjectConstruction;
    }

    /**
     * Specifies the value object construction.
     * @param valueObjectConstruction such template chunk.
     */
    protected void setValueObjectConstruction(
        final String valueObjectConstruction)
    {
        immutableSetValueObjectConstruction(valueObjectConstruction);
    }

    /**
     * Retrieves the value object construction.
     * @return such information.
     */
    public String getValueObjectConstruction() 
    {
        return m__strValueObjectConstruction;
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
     * Specifies the procedures metadata.
     * @param proceduresMetaData the procedures metadata.
     */
    private void immutableSetProceduresMetaData(final List proceduresMetaData)
    {
        m__lProceduresMetaData = proceduresMetaData;
    }

    /**
     * Specifies the procedures metadata.
     * @param proceduresMetaData the procedures metaData.
     */
    protected void setProceduresMetaData(final List proceduresMetaData)
    {
        immutableSetProceduresMetaData(proceduresMetaData);
    }

    /**
     * Retrieves the procedures metadata.
     * @return such collection.
     */
    public List getProceduresMetaData()
    {
        return m__lProceduresMetaData;
    }

    /**
     * Adds a new procedure metadata.
     * @param procedureMetaData the new procedure metadata.
     */
    public void addProcedureMetaData(final ProcedureMetaData procedureMetaData)
    {
        if  (procedureMetaData != null)
        {
            List t_lProceduresMetaData = getProceduresMetaData();

            if  (t_lProceduresMetaData != null)
            {
                t_lProceduresMetaData.add(procedureMetaData);
            }
        }
    }

    /**
     * Specifies the procedure parameters metadata.
     * @param parametersMetaData the parameters map.
     */
    private void immutableSetProcedureParametersMetaData(
        final Map parametersMetaData)
    {
        m__mProcedureParametersMetaData = parametersMetaData;
    }

    /**
     * Specifies the procedure parameters metadata.
     * @param parametersMetaData the parameters map.
     */
    protected void setProcedureParametersMetaData(final Map parametersMetaData)
    {
        immutableSetProcedureParametersMetaData(parametersMetaData);
    }

    /**
     * Retrieves the procedure parameters metadata.
     * @return such metadata.
     */
    public Map getProcedureParametersMetaData()
    {
        return m__mProcedureParametersMetaData;
    }

    /**
     * Adds a new procedure metadata.
     * @param procedureMetaData the procedure metadata.
     * @param parametersMetaData the parameters metadata.
     */
    public void addProcedureParametersMetaData(
        final ProcedureMetaData procedureMetaData,
        final ProcedureParameterMetaData[] parametersMetaData)
    {
        if  (   (procedureMetaData  != null)
             && (parametersMetaData != null))
        {
            Map t_mParametersMetaData = getProcedureParametersMetaData();

            if  (t_mParametersMetaData != null)
            {
                t_mParametersMetaData.put(buildKey(procedureMetaData), parametersMetaData);
            }
        }
    }

    /**
     * Retrieves the procedure parameters metadata.
     * @param procedureMetaData the procedure metadata.
     * @return the associated parameters metadata.
     */
    public ProcedureParameterMetaData[] getProcedureParametersMetaData(
        final ProcedureMetaData procedureMetaData)
    {
        ProcedureParameterMetaData[] result =
            DatabaseMetaDataManager.EMPTY_PROCEDURE_PARAMETER_METADATA_ARRAY;

        if  (procedureMetaData != null)
        {
            Map t_mParametersMetaData = getProcedureParametersMetaData();

            if  (t_mParametersMetaData != null)
            {
                result =
                    (ProcedureParameterMetaData[])
                        t_mParametersMetaData.get(
                            buildKey(procedureMetaData));
            }
        }

        return result;
    }

    /**
     * Builds the key based on the procedure metadata.
     * @param procedureMetaData the procedure metadata.
     * @return the associated key.
     */
    protected Object buildKey(final ProcedureMetaData procedureMetaData)
    {
        Object result = procedureMetaData;

        if  (procedureMetaData != null)
        {
            result = "procedure." + procedureMetaData.getName();
        }

        return result;
    }

    /**
     * Checks whether the procedure repository generated would be empty.
     * @return <code>true</code> in such case.
     */
    public boolean isEmpty()
    {
        return isEmpty(getProceduresMetaData());
    }

    /**
     * Checks whether the procedure repository generated would be empty.
     * @param proceduresMetaData the metadata associated to the procedures.
     * @return <code>true</code> in such case.
     */
    protected boolean isEmpty(final List proceduresMetaData)
    {
        return
            (   (proceduresMetaData == null)
             || (proceduresMetaData.isEmpty()));
    }
}
