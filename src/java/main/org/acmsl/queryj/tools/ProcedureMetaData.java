/*
                        QueryJ

    Copyright (C) 2002  Jose San Leandro Armendáriz
                        jsanleandro@yahoo.es
                        chousz@yahoo.com

    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    version 2.1 of the License, or (at your option) any later version.

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
                    Urb. Valdecabañas
                    Boadilla del monte
                    28660 Madrid
                    Spain

 ******************************************************************************
 *
 * Filename: $RCSfile$
 *
 * Author: Jose San Leandro Armendáriz
 *
 * Description: Represents procedure metadata.
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
package org.acmsl.queryj.tools;

/**
 * Represents procedure metadata.
 * @author <a href="mailto:jsanleandro@yahoo.es">Jose San Leandro</a>
 * @version $Revision$
 */
public class ProcedureMetaData
{
    /**
     * Indicates that a procedure's result is unknown.
     */
    public static final int PROCEDURE_RESULT_UNKNOWN = 1;

    /**
     * Indicates that a procedure doesn't return anything.
     */
    public static final int PROCEDURE_NO_RESULT = 2;

    /**
     * Indicates that a procedure returns something.
     */
    public static final int PROCEDURE_RETURNS_RESULT = 3;

    /**
     * The name.
     */
    private String m__strName;

    /**
     * The type.
     */
    private int m__iType;

    /**
     * The comment.
     */
    private String m__strComment;

    /**
     * Builds a ProcedureMetaData using given information.
     * @param name the procedure name.
     * @param type the type.
     * @param comment the procedure comment.
     */
    public ProcedureMetaData(String name, int type, String comment)
    {
        inmutableSetName(name);
        inmutableSetType(type);
        inmutableSetComment(comment);
    }

    /**
     * Specifies the name.
     * @param name the name.
     */
    private void inmutableSetName(String name)
    {
        m__strName = name;
    }

    /**
     * Specifies the name.
     * @param name the name.
     */
    protected void setName(String name)
    {
        inmutableSetName(name);
    }

    /**
     * Retrieves the name.
     * @return such name.
     */
    public String getName()
    {
        return m__strName;
    }

    /**
     * Specifies the type.
     * @param type the type.
     */
    private void inmutableSetType(int type)
    {
        m__iType = type;
    }

    /**
     * Specifies the type.
     * @param type the type.
     */
    protected void setType(int type)
    {
        inmutableSetType(type);
    }

    /**
     * Retrieves the type.
     * @return such type.
     */
    public int getType()
    {
        return m__iType;
    }

    /**
     * Specifies the comment.
     * @param comment the comment.
     */
    private void inmutableSetComment(String comment)
    {
        m__strComment = comment;
    }

    /**
     * Specifies the comment.
     * @param comment the comment.
     */
    protected void setComment(String comment)
    {
        inmutableSetComment(comment);
    }

    /**
     * Retrieves the comment.
     * @return such comment.
     */
    public String getComment()
    {
        return m__strComment;
    }

    /**
     * Represents procedure parameter metadata.
     * @author <a href="mailto:jsanleandro@yahoo.es">Jose San Leandro</a>
     * @version $Revision$
     */
    private static class ProcedureParameterMetaData
        extends  ProcedureMetaData
    {
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
            String name,
            int    type,
            String comment,
            int    dataType,
            int    length,
            int    nullable)
        {
            super(name, type, comment);
            inmutableSetDataType(dataType);
            inmutableSetLength(length);
            inmutableSetNullable(nullable);
        }

        /**
         * Specifies the data type.
         * @param dataType the data type.
         */
        private void inmutableSetDataType(int dataType)
        {
            m__iDataType = dataType;
        }

        /**
         * Specifies the data type.
         * @param dataType the data type.
         */
        protected void setDataType(int dataType)
        {
            inmutableSetDataType(dataType);
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
        private void inmutableSetLength(int length)
        {
            m__iLength = length;
        }

        /**
         * Specifies the length.
         * @param length the length.
         */
        protected void setLength(int length)
        {
            inmutableSetLength(length);
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
        private void inmutableSetNullable(int nullable)
        {
            m__iNullable = nullable;
        }

        /**
         * Specifies the nullable setting.
         * @param nullable the nullable setting.
         */
        protected void setNullable(int nullable)
        {
            inmutableSetNullable(nullable);
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
}
