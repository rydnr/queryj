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
 * Author: Jose San Leandro Armendariz
 *
 * Description: Is able to generate procedure repositories according to
 *              database metadata.
 *
 */
package org.acmsl.queryj.tools.templates;

/*
 * Importing some project-specific classes.
 */
import org.acmsl.queryj.tools.metadata.DecoratorFactory;
import org.acmsl.queryj.tools.metadata.MetadataTypeManager;
import org.acmsl.queryj.tools.metadata.ProcedureMetadata;
import org.acmsl.queryj.tools.metadata.ProcedureParameterMetadata;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public abstract class AbstractProcedureRepositoryTemplate
    extends  AbstractTemplate
{
    /**
     * An empty array of <code>ProcedureParameterMetadata</code> instances.
     */
    protected final ProcedureParameterMetadata[]
        EMPTY_PROCEDURE_PARAMETER_METADATA_ARRAY =
            new ProcedureParameterMetadata[0];
    
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
     * The metadata type manager instance.
     */
    private MetadataTypeManager m__MetadataTypeManager;
    
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
    private List m__lProceduresMetadata;

    /**
     * The procedure parameters metadata.
     */
    private Map m__mProcedureParametersMetadata;

    /**
     * Builds an <code>AbstractProcedureRepositoryTemplate</code> using
     * given information.
     * @param header the header.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @param packageDeclaration the package declaration.
     * @param packageName the package name.
     * @param repository the repository.
     * @param metadataTypeManager the metadata type manager instance.
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
        final DecoratorFactory decoratorFactory,
        final String packageDeclaration,
        final String packageName,
        final String repository,
        final MetadataTypeManager metadataTypeManager,
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
        final String classEnd)
    {
        super(header, decoratorFactory);
        immutableSetPackageDeclaration(packageDeclaration);
        immutableSetPackageName(packageName);
        immutableSetRepository(repository);
        immutableSetMetadataTypeManager(metadataTypeManager);
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
        immutableSetProceduresMetadata(new ArrayList());
        immutableSetProcedureParametersMetadata(new HashMap());
    }


    /**
     * Specifies the metadata type manager to use.
     * @param metadataTypeManager such instance.
     */
    protected final void immutableSetMetadataTypeManager(
        final MetadataTypeManager metadataTypeManager)
    {
        m__MetadataTypeManager = metadataTypeManager;
    }

    /**
     * Specifies the metadata type manager to use.
     * @param metadataTypeManager such instance.
     */
    protected void setMetadataTypeManager(
        final MetadataTypeManager metadataTypeManager)
    {
        immutableSetMetadataTypeManager(metadataTypeManager);
    }

    /**
     * Retrieves the metadata type manager to use.
     * @return such instance.
     */
    protected MetadataTypeManager getMetadataTypeManager()
    {
        return m__MetadataTypeManager;
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
     * @param proceduresMetadata the procedures metadata.
     */
    private void immutableSetProceduresMetadata(final List proceduresMetadata)
    {
        m__lProceduresMetadata = proceduresMetadata;
    }

    /**
     * Specifies the procedures metadata.
     * @param proceduresMetadata the procedures metadata.
     */
    protected void setProceduresMetadata(final List proceduresMetadata)
    {
        immutableSetProceduresMetadata(proceduresMetadata);
    }

    /**
     * Retrieves the procedures metadata.
     * @return such collection.
     */
    public List getProceduresMetadata()
    {
        return m__lProceduresMetadata;
    }

    /**
     * Adds a new procedure metadata.
     * @param procedureMetadata the new procedure metadata.
     */
    public void addProcedureMetadata(@Nullable final ProcedureMetadata procedureMetadata)
    {
        if  (procedureMetadata != null)
        {
            List t_lProceduresMetadata = getProceduresMetadata();

            if  (t_lProceduresMetadata != null)
            {
                t_lProceduresMetadata.add(procedureMetadata);
            }
        }
    }

    /**
     * Specifies the procedure parameters metadata.
     * @param parametersMetadata the parameters map.
     */
    private void immutableSetProcedureParametersMetadata(
        final Map parametersMetadata)
    {
        m__mProcedureParametersMetadata = parametersMetadata;
    }

    /**
     * Specifies the procedure parameters metadata.
     * @param parametersMetadata the parameters map.
     */
    protected void setProcedureParametersMetadata(final Map parametersMetadata)
    {
        immutableSetProcedureParametersMetadata(parametersMetadata);
    }

    /**
     * Retrieves the procedure parameters metadata.
     * @return such metadata.
     */
    public Map getProcedureParametersMetadata()
    {
        return m__mProcedureParametersMetadata;
    }

    /**
     * Adds a new procedure metadata.
     * @param procedureMetadata the procedure metadata.
     * @param parametersMetadata the parameters metadata.
     */
    public void addProcedureParametersMetadata(
        @Nullable final ProcedureMetadata procedureMetadata,
        @Nullable final ProcedureParameterMetadata[] parametersMetadata)
    {
        if  (   (procedureMetadata  != null)
             && (parametersMetadata != null))
        {
            Map t_mParametersMetadata = getProcedureParametersMetadata();

            if  (t_mParametersMetadata != null)
            {
                t_mParametersMetadata.put(
                    buildKey(procedureMetadata), parametersMetadata);
            }
        }
    }

    /**
     * Retrieves the procedure parameters metadata.
     * @param procedureMetadata the procedure metadata.
     * @return the associated parameters metadata.
     */
    @NotNull
    public ProcedureParameterMetadata[] getProcedureParametersMetadata(
        @Nullable final ProcedureMetadata procedureMetadata)
    {
        @NotNull ProcedureParameterMetadata[] result =
            EMPTY_PROCEDURE_PARAMETER_METADATA_ARRAY;

        if  (procedureMetadata != null)
        {
            Map t_mParametersMetadata = getProcedureParametersMetadata();

            if  (t_mParametersMetadata != null)
            {
                result =
                    (ProcedureParameterMetadata[])
                        t_mParametersMetadata.get(
                            buildKey(procedureMetadata));
            }
        }

        return result;
    }

    /**
     * Builds the key based on the procedure metadata.
     * @param procedureMetadata the procedure metadata.
     * @return the associated key.
     */
    @Nullable
    protected Object buildKey(@Nullable final ProcedureMetadata procedureMetadata)
    {
        @Nullable Object result = procedureMetadata;

        if  (procedureMetadata != null)
        {
            result = "procedure." + procedureMetadata.getName();
        }

        return result;
    }

    /**
     * Checks whether the procedure repository generated would be empty.
     * @return <code>true</code> in such case.
     */
    public boolean isEmpty()
    {
        return isEmpty(getProceduresMetadata());
    }

    /**
     * Checks whether the procedure repository generated would be empty.
     * @param proceduresMetadata the metadata associated to the procedures.
     * @return <code>true</code> in such case.
     */
    protected boolean isEmpty(@Nullable final List proceduresMetadata)
    {
        return
            (   (proceduresMetadata == null)
             || (proceduresMetadata.isEmpty()));
    }
}
