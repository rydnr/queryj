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
 * Filename: ProcedureMetadata.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Represents procedure metadata.
 *
 */
package org.acmsl.queryj.tools.metadata;

/**
 * Represents procedure metadata.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro</a>
 */
public class ProcedureMetadata
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
    public ProcedureMetadata(
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
}
