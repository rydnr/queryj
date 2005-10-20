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
 * Description: Is able to generate procedure repositories according to
 *              database metadata.
 *
 */
package org.acmsl.queryj.tools.templates;

/*
 * Importing some project-specific classes.
 */
import org.acmsl.queryj.tools.DatabaseMetaDataManager;
import org.acmsl.queryj.tools.MetaDataUtils;
import org.acmsl.queryj.tools.ProcedureMetaData;
import org.acmsl.queryj.tools.ProcedureParameterMetaData;

/*
 * Importing some ACM-SL classes.
 */
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

/**
 * Is able to generate procedure repositories according to
 * database metadata.
 * @author <a href="mailto:chous@acm-sl.org"
 *         >Jose San Leandro</a>
 */
public class ProcedureRepositoryTemplate
    extends  AbstractProcedureRepositoryTemplate
    implements  ProcedureRepositoryTemplateDefaults
{
    /**
     * Builds a <code>ProcedureRepositoryTemplate</code> using given
     * information.
     * @param packageName the package name.
     * @param repository the repository.
     */
    public ProcedureRepositoryTemplate(
        final String packageName, final String repository)
    {
        super(
            DEFAULT_HEADER,
            PACKAGE_DECLARATION,
            packageName,
            repository,
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
     * Retrieves the source code of the generated procedure repository.
     * @return such source code.
     */
    protected String generateOutput()
    {
        return
            generateOutput(
                getHeader(),
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
                getProceduresMetaData(),
                ProcedureRepositoryTemplateUtils.getInstance(),
                MetaDataUtils.getInstance(),
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
     * @param proceduresMetaData the procedures' meta data.
     * @param procedureRepositoryTemplateUtils the
     * <code>ProcedureRepositoryTemplateUtils</code> instance.
     * @param metaDataUtils the <code>MetaDataUtils</code> instance.
     * @param stringUtils the <code>StringUtils</code> instance.
     * @return such source code.
     * @precondition proceduresMetaData != null
     * @precondition procedureRepositoryTemplateUtils != null
     * @precondition metaDataUtils != null
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
        final List proceduresMetaData,
        final ProcedureRepositoryTemplateUtils procedureRepositoryTemplateUtils,
        final MetaDataUtils metaDataUtils,
        final StringUtils stringUtils)
    {
        StringBuffer t_sbResult = new StringBuffer();

        Object[] t_aRepository =
            new Object[]
            {
                stringUtils.normalize(repository, '_')
            };

        Object[] t_aPackageName = new Object[]{packageName};

        MessageFormat t_Formatter = new MessageFormat(header);
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

        Iterator t_itProceduresMetaData = proceduresMetaData.iterator();

        MessageFormat t_JavadocFormatter =
            new MessageFormat(procedureJavadoc);

        MessageFormat t_ParameterJavadocFormatter =
            new MessageFormat(procedureParameterJavadoc);

        MessageFormat t_ParameterDeclarationFormatter =
            new MessageFormat(procedureParameterDeclaration);

        MessageFormat t_ProcedureSentenceFormatter =
            new MessageFormat(procedureSentence);

        MessageFormat t_OutParameterRegistrationFormatter =
            new MessageFormat(outParameterRegistration);

        MessageFormat t_InParameterSpecificationFormatter =
            new MessageFormat(inParameterSpecification);

        MessageFormat t_ValueObjectConstructionFormatter =
            new MessageFormat(valueObjectConstruction);

        String t_strComment = null;

        int t_iReturnType = -1;

        while  (t_itProceduresMetaData.hasNext())
        {
            ProcedureMetaData t_ProcedureMetaData =
                (ProcedureMetaData) t_itProceduresMetaData.next();

            ProcedureParameterMetaData[] t_aProcedureParametersMetaData =
                getProcedureParametersMetaData(t_ProcedureMetaData);

            if  (   (t_ProcedureMetaData            != null)
                 && (t_aProcedureParametersMetaData != null))
            {
                StringBuffer t_sbParametersJavadoc = new StringBuffer();
                StringBuffer t_sbParametersDeclaration = new StringBuffer();
                StringBuffer t_sbProcedureSentenceParameters = new StringBuffer();
                StringBuffer t_sbOutParametersRegistration = new StringBuffer();
                StringBuffer t_sbInParametersSpecification = new StringBuffer();
                StringBuffer t_sbOutParametersRetrieval = new StringBuffer();
                StringBuffer t_sbValueObjectConstruction = new StringBuffer();

                int t_iInParameterIndex = 1;

                for  (int t_iIndex = 0;
                          t_iIndex < t_aProcedureParametersMetaData.length;
                          t_iIndex++)
                {
                    if  (t_aProcedureParametersMetaData[t_iIndex] != null)
                    {
                        if  (   (   t_aProcedureParametersMetaData[
                                        t_iIndex].getType()
                                 == ProcedureParameterMetaData
                                        .IN_PARAMETER)
                             || (   t_aProcedureParametersMetaData[
                                        t_iIndex].getType()
                                 == ProcedureParameterMetaData
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
                                        metaDataUtils.getSetterMethod(
                                            t_aProcedureParametersMetaData[
                                                t_iIndex].getDataType(),
                                            t_iInParameterIndex,
                                            t_aProcedureParametersMetaData[
                                                t_iIndex].getName().toLowerCase())
                                    }));
                        }

                        if  (   (t_aProcedureParametersMetaData[
                                     t_iIndex].getName() != null)
                             && (   (   t_aProcedureParametersMetaData[
                                            t_iIndex].getType()
                                     == ProcedureParameterMetaData
                                            .IN_PARAMETER)
                                 || (   t_aProcedureParametersMetaData[
                                            t_iIndex].getType()
                                     == ProcedureParameterMetaData
                                            .IN_OUT_PARAMETER)))
                        {
                            t_strComment =
                                t_aProcedureParametersMetaData[
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
                                        t_aProcedureParametersMetaData[
                                            t_iIndex]
                                                .getName().toLowerCase(),
                                        t_strComment
                                    }));                                        
                        }

                        if  (   t_aProcedureParametersMetaData[
                                    t_iIndex].getType()
                             == ProcedureParameterMetaData
                                    .RESULT_PARAMETER)
                        {
                            t_iReturnType =
                                t_aProcedureParametersMetaData[
                                    t_iIndex].getDataType();
                        }

                        t_iInParameterIndex++;
                    }
                }

                int t_iInParameterCount = t_iInParameterIndex;

                int t_iParameterIndex = t_iInParameterCount;

                for  (int t_iIndex = 0;
                          t_iIndex < t_aProcedureParametersMetaData.length;
                          t_iIndex++)
                {
                    if  (t_aProcedureParametersMetaData[t_iIndex] != null)
                    {
                        if  (   t_aProcedureParametersMetaData[t_iIndex]
                                    .getType()
                             == ProcedureParameterMetaData
                                    .RESULT_PARAMETER)
                        {
                            t_iReturnType =
                                t_aProcedureParametersMetaData[t_iIndex]
                                    .getType();
                        }

                        if  (   (   t_aProcedureParametersMetaData[
                                        t_iIndex].getName()
                                 != null)
                             && (   (   t_aProcedureParametersMetaData[
                                            t_iIndex].getType()
                                     == ProcedureParameterMetaData
                                           .OUT_PARAMETER)
                                 || (   t_aProcedureParametersMetaData[
                                            t_iIndex].getType()
                                     == ProcedureParameterMetaData
                                            .IN_OUT_PARAMETER)))
                        {
                            t_sbOutParametersRegistration.append(
                                t_OutParameterRegistrationFormatter.format(
                                    new Object[]
                                    {
                                        new Integer(t_iIndex + 1),
                                        metaDataUtils.getConstantName(
                                            t_aProcedureParametersMetaData[
                                                t_iIndex].getType())
                                    }));

                            t_sbValueObjectConstruction.append(
                                t_ValueObjectConstructionFormatter.format(
                                    new Object[]
                                    {
                                        metaDataUtils.getObjectType(
                                            t_aProcedureParametersMetaData[
                                                t_iIndex].getDataType()),
                                        t_aProcedureParametersMetaData[
                                            t_iIndex].getName().toLowerCase(),
                                        stringUtils.capitalize(
                                            metaDataUtils.getObjectType(
                                                t_aProcedureParametersMetaData[
                                                    t_iIndex].getDataType()),
                                            '_'),
                                        new Integer(t_iIndex + 1)
                                    }));

                            t_iParameterIndex++;
                        }
                    }
                }

                int t_iOutParameterCount = t_iParameterIndex - t_iInParameterIndex;

                t_iInParameterIndex = 1;

                boolean t_bFirstInParameter = true;

                for  (int t_iIndex = 0;
                          t_iIndex < t_aProcedureParametersMetaData.length;
                          t_iIndex++)
                {
                    if  (t_aProcedureParametersMetaData[t_iIndex] != null)
                    {
                        if  (   (t_aProcedureParametersMetaData[t_iIndex]
                                     .getName() != null)
                             && (   (   t_aProcedureParametersMetaData[
                                            t_iIndex].getType()
                                     == ProcedureParameterMetaData
                                           .IN_PARAMETER)
                                 || (   t_aProcedureParametersMetaData[
                                            t_iIndex].getType()
                                     == ProcedureParameterMetaData
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
                                        metaDataUtils.getNativeType(
                                            t_aProcedureParametersMetaData[
                                                t_iIndex].getDataType()),
                                        t_aProcedureParametersMetaData[
                                            t_iIndex]
                                        .getName().toLowerCase()
                                        + (  (  t_iInParameterIndex
                                                + t_iOutParameterCount
                                                < t_aProcedureParametersMetaData
                                                .length)
                                             ? ","
                                             : "")
                                    }));
                        }

                        t_iInParameterIndex++;
                    }
                }

                t_strComment = t_ProcedureMetaData.getComment();

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

                String t_strReturnType =
                    metaDataUtils.getProcedureResultType(t_iReturnType);

                MessageFormat t_ProcedureBodyFormatter =
                    new MessageFormat(procedureBody);

                t_sbResult.append(
                    t_ProcedureBodyFormatter.format(
                        new Object[]
                        {
                            t_strReturnType,
                            t_ProcedureMetaData.getName().toLowerCase(),
                            t_sbParametersDeclaration.toString(),
                            metaDataUtils.getProcedureDefaultValue(
                                t_iReturnType),
                            t_ProcedureSentenceFormatter.format(
                                new Object[]
                                {
                                    t_ProcedureMetaData.getName(),
                                    t_sbProcedureSentenceParameters
                                    .toString()
                                }),
                            t_sbOutParametersRegistration.toString(),
                            t_sbInParametersSpecification.toString(),
                            t_sbOutParametersRetrieval.toString(),
                            t_sbValueObjectConstruction.toString(),
                            t_strReturnType,
                            metaDataUtils.getGetterMethod(
                                t_iReturnType, 1),
                            t_ProcedureMetaData.getName()
                        }));
            }
        }

        t_sbResult.append(classEnd);

        return t_sbResult.toString();
    }
}
