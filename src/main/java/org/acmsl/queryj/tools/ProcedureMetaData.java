/*
                        QueryJ

    Copyright (C) 2002-2007  Jose San Leandro Armendariz
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
 * Filename: ProcedureMetaData.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Represents procedure metadata.
 *
 */
package org.acmsl.queryj.tools;

/**
 * Represents procedure metadata.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro</a>
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
    public ProcedureMetaData(
        final String name, final int type, final String comment)
    {
        immutableSetName(name);
        immutableSetType(type);
        immutableSetComment(comment);
    }

    /**
     * Specifies the name.
     * @param name the name.
     */
    private void immutableSetName(final String name)
    {
        m__strName = name;
    }

    /**
     * Specifies the name.
     * @param name the name.
     */
    protected void setName(final String name)
    {
        immutableSetName(name);
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
    private void immutableSetType(final int type)
    {
        m__iType = type;
    }

    /**
     * Specifies the type.
     * @param type the type.
     */
    protected void setType(final int type)
    {
        immutableSetType(type);
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
    private void immutableSetComment(final String comment)
    {
        m__strComment = comment;
    }

    /**
     * Specifies the comment.
     * @param comment the comment.
     */
    protected void setComment(final String comment)
    {
        immutableSetComment(comment);
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
     * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro</a>
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
}
