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
 * @author <a href="mailto:jsanleandro@yahoo.es"
 *         >Jose San Leandro</a>
 * @version $Revision$
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
    public String toString()
    {
        return
            toString(
                ProcedureRepositoryUtils.getInstance(),
                MetaDataUtils.getInstance(),
                StringUtils.getInstance());
    }

    /**
     * Retrieves the source code of the generated procedure repository.
     * @param metaDataUtils the <code>MetaDataUtils</code> instance.
     * @param stringUtils the <code>StringUtils</code> instance.
     * @return such source code.
     * @precondition metaDataUtils != null
     * @precondition stringUtils != null
     */
    protected String toString(
        final ProcedureRepositoryUtils procedureRepositoryUtils,
        final MetaDataUtils metaDataUtils,
        final StringUtils stringUtils)
    {
        StringBuffer t_sbResult = new StringBuffer();

        Object[] t_aRepository =
            new Object[]{
                stringUtils.normalize(getRepository(), '_')};

        Object[] t_aPackageName = new Object[]{getPackageName()};

        MessageFormat t_Formatter = new MessageFormat(getHeader());
        t_sbResult.append(t_Formatter.format(t_aRepository));

        t_Formatter = new MessageFormat(getPackageDeclaration());
        t_sbResult.append(t_Formatter.format(t_aPackageName));

        t_sbResult.append(getProjectImportsJavadoc());
        t_sbResult.append(getProjectImports());
        t_sbResult.append(getAcmslImports());
        t_sbResult.append(getJdkImports());

        t_Formatter = new MessageFormat(getJavadoc());
        t_sbResult.append(t_Formatter.format(t_aRepository));

        t_Formatter = new MessageFormat(getClassDefinition());
        t_sbResult.append(
            t_Formatter.format(
                new Object[]
                {
                      procedureRepositoryUtils
                          .retrieveProcedureRepositoryClassName(
                              getRepository())
                }));

        t_sbResult.append(getClassStart());

        List t_lProceduresMetaData = getProceduresMetaData();

        if  (t_lProceduresMetaData != null)
        {
            Iterator t_itProceduresMetaData = t_lProceduresMetaData.iterator();

            MessageFormat t_JavadocFormatter =
                new MessageFormat(getProcedureJavadoc());

            MessageFormat t_ParameterJavadocFormatter =
                new MessageFormat(getProcedureParameterJavadoc());

            MessageFormat t_ParameterDeclarationFormatter =
                new MessageFormat(getProcedureParameterDeclaration());

            MessageFormat t_ProcedureSentenceFormatter =
                new MessageFormat(getProcedureSentence());

            MessageFormat t_OutParameterRegistrationFormatter =
                new MessageFormat(getOutParameterRegistration());

            MessageFormat t_InParameterSpecificationFormatter =
                new MessageFormat(getInParameterSpecification());

            MessageFormat t_ValueObjectConstructionFormatter =
                new MessageFormat(getValueObjectConstruction());

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
                        new MessageFormat(getProcedureBody());

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
        }

        t_sbResult.append(getClassEnd());

        return t_sbResult.toString();
    }
}
