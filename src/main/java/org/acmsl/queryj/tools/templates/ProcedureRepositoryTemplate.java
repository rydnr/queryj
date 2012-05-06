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
 * Filename: ProcedureRepositoryTemplate.java
 *
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

/*
 * Importing some ACM-SL classes.
 */
import org.acmsl.commons.utils.StringUtils;
import org.antlr.stringtemplate.StringTemplateGroup;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing some JDK classes.
 */
import java.text.MessageFormat;
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
public class ProcedureRepositoryTemplate
    extends  AbstractProcedureRepositoryTemplate
    implements  ProcedureRepositoryTemplateDefaults
{
    private static final long serialVersionUID = 1538609668496295602L;

    /**
     * Builds a <code>ProcedureRepositoryTemplate</code> using given
     * information.
     * @param header the header.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @param packageName the package name.
     * @param repository the repository.
     * @param metadataTypeManager the metadata type manager.
     * @precondition decoratorFactory != null
     * @precondition metadataTypeManager != null
     */
    public ProcedureRepositoryTemplate(
        @Nullable final String header,
        final DecoratorFactory decoratorFactory,
        final String packageName,
        final String repository,
        final MetadataTypeManager metadataTypeManager)
    {
        super(
            (header != null) ? header : DEFAULT_HEADER,
            decoratorFactory,
            PACKAGE_DECLARATION,
            packageName,
            repository,
            metadataTypeManager,
            DEFAULT_PROJECT_IMPORTS_JAVADOC,
            PROJECT_IMPORTS,
            ACMSL_IMPORTS,
            JDK_IMPORTS,
            DEFAULT_JAVADOC,
            CLASS_DEFINITION,
            DEFAULT_CLASS_START,
            DEFAULT_PROCEDURE_JAVADOC,
            DEFAULT_PROCEDURE_PARAMETER_JAVADOC,
            DEFAULT_PROCEDURE_BODY,
            DEFAULT_PROCEDURE_PARAMETER_DECLARATION,
            DEFAULT_PROCEDURE_SENTENCE,
            DEFAULT_OUT_PARAMETER_REGISTRATION,
            DEFAULT_IN_PARAMETER_SPECIFICATION,
            DEFAULT_OUT_PARAMETER_RETRIEVAL,
            DEFAULT_VALUE_OBJECT_CONSTRUCTION,
            DEFAULT_CLASS_END);
    }


    /**
     * Retrieves the string template group.
     *
     * @return such instance.
     */
    @Override
    @Nullable
    public StringTemplateGroup retrieveGroup()
    {
        return null;
    }

    /**
     * Retrieves the source code of the generated procedure repository.
     * @param header the header.
     * @return such source code.
     */
    @Override
    protected String generateOutput(final String header)
    {
        return
            generateOutput(
                header,
                getPackageDeclaration(),
                getPackageName(),
                getRepository(),
                getProjectImportsJavadoc(),
                getProjectImports(),
                getAcmslImports(),
                getJdkImports(),
                getJavadoc(),
                getClassDefinition(),
                getClassStart(),
                getProcedureJavadoc(),
                getProcedureParameterJavadoc(),
                getProcedureBody(),
                getProcedureParameterDeclaration(),
                getProcedureSentence(),
                getOutParameterRegistration(),
                getInParameterSpecification(),
                getOutParameterRetrieval(),
                getValueObjectConstruction(),
                getClassEnd(),
                getProceduresMetadata(),
                ProcedureRepositoryTemplateUtils.getInstance(),
                getMetadataTypeManager(),
                StringUtils.getInstance());
    }

    /**
     * Retrieves the source code of the generated procedure repository.
     * @param header the header.
     * @param packageDeclaration the package declaration.
     * @param packageName the package name.
     * @param repository the repository.
     * @param projectImportsJavadoc the project imports javadoc.
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
     * @param proceduresMetadata the procedures' meta data.
     * @param procedureRepositoryTemplateUtils the
     * <code>ProcedureRepositoryTemplateUtils</code> instance.
     * @param metadataTypeManager the metadata type manager.
     * @param stringUtils the <code>StringUtils</code> instance.
     * @return such source code.
     * @precondition proceduresMetaData != null
     * @precondition procedureRepositoryTemplateUtils != null
     * @precondition metadataTypeManager != null
     * @precondition stringUtils != null
     */
    protected String generateOutput(
        final String header,
        final String packageDeclaration,
        final String packageName,
        final String repository,
        final String projectImportsJavadoc,
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
        @NotNull final List proceduresMetadata,
        @NotNull final ProcedureRepositoryTemplateUtils procedureRepositoryTemplateUtils,
        @NotNull final MetadataTypeManager metadataTypeManager,
        @NotNull final StringUtils stringUtils)
    {
        @NotNull StringBuffer t_sbResult = new StringBuffer();

        @NotNull Object[] t_aRepository =
            new Object[]
            {
                stringUtils.normalize(repository, '_')
            };

        @NotNull Object[] t_aPackageName = new Object[]{packageName};

        @NotNull MessageFormat t_Formatter = new MessageFormat(header);
        t_sbResult.append(t_Formatter.format(t_aRepository));

        t_Formatter = new MessageFormat(packageDeclaration);
        t_sbResult.append(t_Formatter.format(t_aPackageName));

        t_sbResult.append(projectImportsJavadoc);
        t_sbResult.append(projectImports);
        t_sbResult.append(acmslImports);
        t_sbResult.append(jdkImports);

        t_Formatter = new MessageFormat(javadoc);
        t_sbResult.append(t_Formatter.format(t_aRepository));

        t_Formatter = new MessageFormat(classDefinition);
        t_sbResult.append(
            t_Formatter.format(
                new Object[]
                {
                      procedureRepositoryTemplateUtils
                          .retrieveProcedureRepositoryClassName(
                              repository)
                }));

        t_sbResult.append(classStart);

        Iterator t_itProceduresMetadata = proceduresMetadata.iterator();

        @NotNull MessageFormat t_JavadocFormatter =
            new MessageFormat(procedureJavadoc);

        @NotNull MessageFormat t_ParameterJavadocFormatter =
            new MessageFormat(procedureParameterJavadoc);

        @NotNull MessageFormat t_ParameterDeclarationFormatter =
            new MessageFormat(procedureParameterDeclaration);

        @NotNull MessageFormat t_ProcedureSentenceFormatter =
            new MessageFormat(procedureSentence);

        @NotNull MessageFormat t_OutParameterRegistrationFormatter =
            new MessageFormat(outParameterRegistration);

        @NotNull MessageFormat t_InParameterSpecificationFormatter =
            new MessageFormat(inParameterSpecification);

        @NotNull MessageFormat t_ValueObjectConstructionFormatter =
            new MessageFormat(valueObjectConstruction);

        @Nullable String t_strComment = null;

        int t_iReturnType = -1;

        while  (t_itProceduresMetadata.hasNext())
        {
            @NotNull ProcedureMetadata t_ProcedureMetadata =
                (ProcedureMetadata) t_itProceduresMetadata.next();

            @NotNull ProcedureParameterMetadata[] t_aProcedureParametersMetadata =
                getProcedureParametersMetadata(t_ProcedureMetadata);

            if  (   (t_ProcedureMetadata            != null)
                 && (t_aProcedureParametersMetadata != null))
            {
                @NotNull StringBuffer t_sbParametersJavadoc = new StringBuffer();
                @NotNull StringBuffer t_sbParametersDeclaration = new StringBuffer();
                @NotNull StringBuffer t_sbProcedureSentenceParameters = new StringBuffer();
                @NotNull StringBuffer t_sbOutParametersRegistration = new StringBuffer();
                @NotNull StringBuffer t_sbInParametersSpecification = new StringBuffer();
                @NotNull StringBuffer t_sbOutParametersRetrieval = new StringBuffer();
                @NotNull StringBuffer t_sbValueObjectConstruction = new StringBuffer();

                int t_iInParameterIndex = 1;

                for  (int t_iIndex = 0;
                          t_iIndex < t_aProcedureParametersMetadata.length;
                          t_iIndex++)
                {
                    if  (t_aProcedureParametersMetadata[t_iIndex] != null)
                    {
                        if  (   (   t_aProcedureParametersMetadata[
                                        t_iIndex].getType()
                                 == ProcedureParameterMetadata
                                        .IN_PARAMETER)
                             || (   t_aProcedureParametersMetadata[
                                        t_iIndex].getType()
                                 == ProcedureParameterMetadata
                                        .IN_OUT_PARAMETER))
                        {
                            if  (t_iInParameterIndex > 2)
                            {
                                t_sbProcedureSentenceParameters.append(",");
                            }

                            t_sbProcedureSentenceParameters.append("?");

                            t_sbInParametersSpecification.append(
                                t_InParameterSpecificationFormatter.format(
                                    new Object[]
                                    {
                                        metadataTypeManager.getSetterMethod(
                                            t_aProcedureParametersMetadata[
                                                t_iIndex].getDataType(),
                                            t_iInParameterIndex,
                                            t_aProcedureParametersMetadata[
                                                t_iIndex].getName().toLowerCase())
                                    }));
                        }

                        if  (   (t_aProcedureParametersMetadata[
                                     t_iIndex].getName() != null)
                             && (   (   t_aProcedureParametersMetadata[
                                            t_iIndex].getType()
                                     == ProcedureParameterMetadata
                                            .IN_PARAMETER)
                                 || (   t_aProcedureParametersMetadata[
                                            t_iIndex].getType()
                                     == ProcedureParameterMetadata
                                            .IN_OUT_PARAMETER)))
                        {
                            t_strComment =
                                t_aProcedureParametersMetadata[
                                    t_iIndex].getComment();

                            if  (t_strComment == null)
                            {
                                t_strComment =
                                    "No documentation available.";
                            }
                                
                            t_sbParametersJavadoc.append(
                                t_ParameterJavadocFormatter.format(
                                    new Object[]
                                    {
                                        t_aProcedureParametersMetadata[
                                            t_iIndex]
                                                .getName().toLowerCase(),
                                        t_strComment
                                    }));                                        
                        }

                        if  (   t_aProcedureParametersMetadata[
                                    t_iIndex].getType()
                             == ProcedureParameterMetadata
                                    .RESULT_PARAMETER)
                        {
                            t_iReturnType =
                                t_aProcedureParametersMetadata[
                                    t_iIndex].getDataType();
                        }

                        t_iInParameterIndex++;
                    }
                }

                int t_iInParameterCount = t_iInParameterIndex;

                int t_iParameterIndex = t_iInParameterCount;

                for  (int t_iIndex = 0;
                          t_iIndex < t_aProcedureParametersMetadata.length;
                          t_iIndex++)
                {
                    if  (t_aProcedureParametersMetadata[t_iIndex] != null)
                    {
                        if  (   t_aProcedureParametersMetadata[t_iIndex]
                                    .getType()
                             == ProcedureParameterMetadata
                                    .RESULT_PARAMETER)
                        {
                            t_iReturnType =
                                t_aProcedureParametersMetadata[t_iIndex]
                                    .getType();
                        }

                        if  (   (   t_aProcedureParametersMetadata[
                                        t_iIndex].getName()
                                 != null)
                             && (   (   t_aProcedureParametersMetadata[
                                            t_iIndex].getType()
                                     == ProcedureParameterMetadata
                                           .OUT_PARAMETER)
                                 || (   t_aProcedureParametersMetadata[
                                            t_iIndex].getType()
                                     == ProcedureParameterMetadata
                                            .IN_OUT_PARAMETER)))
                        {
                            t_sbOutParametersRegistration.append(
                                t_OutParameterRegistrationFormatter.format(
                                    new Object[]
                                    {
                                        Integer.valueOf(t_iIndex + 1),
                                        metadataTypeManager.getConstantName(
                                            t_aProcedureParametersMetadata[
                                                t_iIndex].getType())
                                    }));

                            t_sbValueObjectConstruction.append(
                                t_ValueObjectConstructionFormatter.format(
                                    new Object[]
                                    {
                                        metadataTypeManager.getObjectType(
                                            t_aProcedureParametersMetadata[
                                                t_iIndex].getDataType(), false),
                                        t_aProcedureParametersMetadata[
                                            t_iIndex].getName().toLowerCase(),
                                        stringUtils.capitalize(
                                            metadataTypeManager.getObjectType(
                                                t_aProcedureParametersMetadata[
                                                    t_iIndex].getDataType(), false),
                                            '_'),
                                        Integer.valueOf(t_iIndex + 1)
                                    }));

                            t_iParameterIndex++;
                        }
                    }
                }

                int t_iOutParameterCount = t_iParameterIndex - t_iInParameterIndex;

                t_iInParameterIndex = 1;

                boolean t_bFirstInParameter = true;

                for  (int t_iIndex = 0;
                          t_iIndex < t_aProcedureParametersMetadata.length;
                          t_iIndex++)
                {
                    if  (t_aProcedureParametersMetadata[t_iIndex] != null)
                    {
                        if  (   (t_aProcedureParametersMetadata[t_iIndex]
                                     .getName() != null)
                             && (   (   t_aProcedureParametersMetadata[
                                            t_iIndex].getType()
                                     == ProcedureParameterMetadata
                                           .IN_PARAMETER)
                                 || (   t_aProcedureParametersMetadata[
                                            t_iIndex].getType()
                                     == ProcedureParameterMetadata
                                            .IN_OUT_PARAMETER)))
                        {
                            if  (t_bFirstInParameter)
                            {
                                t_sbParametersDeclaration.append(",");
                                t_bFirstInParameter = false;
                            }
                                    
                            t_sbParametersDeclaration.append(
                                t_ParameterDeclarationFormatter.format(
                                    new Object[]
                                    {
                                        metadataTypeManager.getNativeType(
                                            t_aProcedureParametersMetadata[
                                                t_iIndex].getDataType()),
                                        t_aProcedureParametersMetadata[
                                            t_iIndex]
                                        .getName().toLowerCase()
                                        + (  (  t_iInParameterIndex
                                                + t_iOutParameterCount
                                                < t_aProcedureParametersMetadata
                                                .length)
                                             ? ","
                                             : "")
                                    }));
                        }

                        t_iInParameterIndex++;
                    }
                }

                t_strComment = t_ProcedureMetadata.getComment();

                if  (t_strComment == null)
                {
                    t_strComment = "No documentation available.";
                }
                                
                t_sbResult.append(
                    t_JavadocFormatter.format(
                        new Object[]
                        {
                            t_strComment,
                            t_sbParametersJavadoc.toString()}
                        ));

                @Nullable String t_strReturnType =
                    metadataTypeManager.getProcedureResultType(t_iReturnType, false);

                @NotNull MessageFormat t_ProcedureBodyFormatter =
                    new MessageFormat(procedureBody);

                t_sbResult.append(
                    t_ProcedureBodyFormatter.format(
                        new Object[]
                        {
                            t_strReturnType,
                            t_ProcedureMetadata.getName().toLowerCase(),
                            t_sbParametersDeclaration.toString(),
                            metadataTypeManager.getProcedureDefaultValue(
                                t_iReturnType, false),
                            t_ProcedureSentenceFormatter.format(
                                new Object[]
                                {
                                    t_ProcedureMetadata.getName(),
                                    t_sbProcedureSentenceParameters
                                    .toString()
                                }),
                            t_sbOutParametersRegistration.toString(),
                            t_sbInParametersSpecification.toString(),
                            t_sbOutParametersRetrieval.toString(),
                            t_sbValueObjectConstruction.toString(),
                            t_strReturnType,
                            metadataTypeManager.getGetterMethod(
                                t_iReturnType, 1),
                            t_ProcedureMetadata.getName()
                        }));
            }
        }

        t_sbResult.append(classEnd);

        return t_sbResult.toString();
    }
}
