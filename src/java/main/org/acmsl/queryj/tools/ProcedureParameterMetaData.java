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
 * Description: Represents procedure parameter metadata.
 *
 */
package org.acmsl.queryj.tools;

/*
 * Importing project-specific classes.
 */
import org.acmsl.queryj.tools.ProcedureMetaData;

/**
 * Represents procedure parameter metadata.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro</a>
 */
public class ProcedureParameterMetaData
    extends  ProcedureMetaData
{
    /**
     * Indicates that the parameter definition is unknown.
     */
    public static final int UNKNOWN_PARAMETER = 0;

    /**
     * Indicates that the parameter is defined as IN.
     */
    public static final int IN_PARAMETER = 1;

    /**
     * Indicates that the parameter is defined as IN/OUT.
     */
    public static final int IN_OUT_PARAMETER = 2;

    /**
     * Indicates that the parameter is defined as OUT.
     */
    public static final int OUT_PARAMETER = 3;

    /**
     * Indicates that the parameter is defined as the result of the procedure.
     */
    public static final int RESULT_PARAMETER = 5;

    /**
     * The data type.
     */
    private int m__iDataType;

    /**
     * The parameter length.
     */
    private int m__iLength;

    /**
     * The nullable setting.
     */
    private int m__iNullable;

    /**
     * Builds a ProcedureParameterMetaData using given information.
     * @param name the name.
     * @param type the type.
     * @param comment the comment.
     * @param dataType the data type.
     * @param length the length.
     * @param nullable the nullable flag.
     */
    public ProcedureParameterMetaData(
        final String name,
        final int    type,
        final String comment,
        final int    dataType,
        final int    length,
        final int    nullable)
    {
        super(name, type, comment);
        immutableSetDataType(dataType);
        immutableSetLength(length);
        immutableSetNullable(nullable);

        org.apache.commons.logging.LogFactory.getLog(getClass()).info(
              "Parameter (name, type, comment, dataType, length, nullable) =\n"
            + name + ", " + type + ", " + comment + ", " + dataType
            + ", " + length + ", " + nullable);
    }

    /**
     * Specifies the data type.
     * @param dataType the data type.
     */
    private void immutableSetDataType(final int dataType)
    {
        m__iDataType = dataType;
    }

    /**
     * Specifies the data type.
     * @param dataType the data type.
     */
    protected void setDataType(final int dataType)
    {
        immutableSetDataType(dataType);
    }

    /**
     * Retrieves the data type.
     * @return such type.
     */
    public int getDataType()
    {
        return m__iDataType;
    }

    /**
     * Specifies the length.
     * @param length the length.
     */
    private void immutableSetLength(final int length)
    {
        m__iLength = length;
    }

    /**
     * Specifies the length.
     * @param length the length.
     */
    protected void setLength(final int length)
    {
        immutableSetLength(length);
    }

    /**
     * Retrieves the length.
     * @return such length.
     */
    public int getLength()
    {
        return m__iLength;
    }

    /**
     * Specifies the nullable setting.
     * @param nullable the nullable setting.
     */
    private void immutableSetNullable(final int nullable)
    {
        m__iNullable = nullable;
    }

    /**
     * Specifies the nullable setting.
     * @param nullable the nullable setting.
     */
    protected void setNullable(final int nullable)
    {
        immutableSetNullable(nullable);
    }

    /**
     * Retrieves the nullable setting.
     * @return such setting.
     */
    public int getNullable()
    {
        return m__iNullable;
    }
}
