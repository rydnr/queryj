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
 * Filename: DatabaseMetaDataLoggingHandler.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Is responsible of resolving SQL type names to the
 *              java.sql.Types constants.
 *
 */
package org.acmsl.queryj.tools;

/*
 * Importing some ACM-SL Commons classes.
 */
import org.acmsl.commons.patterns.Singleton;

/*
 * Importing some JDK classes.
 */
import java.sql.Types;

/**
 * Is responsible of resolving SQL type names to the
 * <code>java.sql.Types</code> constants.
 * @author <a href="mailto:chous@acm-sl.org"
 *         >Jose San Leandro</a>
 */
public class SqlTypeResolver
    implements  Singleton
{
    /**
     * Singleton implemented to avoid the double-checked locking.
     */
    private static class SqlTypeResolverSingletonContainer
    {
        /**
         * The actual singleton.
         */
        public static final SqlTypeResolver SINGLETON =
            new SqlTypeResolver();
    }

    /**
     * Retrieves a <code>SqlTypeResolver</code> instance.
     * @return such instance.
     */
    public static SqlTypeResolver getInstance()
    {
        return SqlTypeResolverSingletonContainer.SINGLETON;
    }

    /**
     * Resolves given name as a <code>java.sql.Types</code> constant.
     * @param name the type name.
     * @return such constant.
     * @precondition name != null
     */
    public int resolve(final String name)
    {
        int result = Types.OTHER;

        if  ("BIT".equalsIgnoreCase(name))
        {
            result = Types.BIT;
        }
        else if  ("TINYINT".equalsIgnoreCase(name))
        {
            result = Types.TINYINT;
        }
        else if  ("SMALLINT".equalsIgnoreCase(name))
        {
	    result = Types.SMALLINT;
        }
        else if  ("INTEGER".equalsIgnoreCase(name))
        {
 	    result = Types.INTEGER;
        }
        else if  ("BIGINT".equalsIgnoreCase(name))
        {
 	    result = Types.BIGINT;
        }
        else if  ("FLOAT".equalsIgnoreCase(name))
        {
 	    result = Types.FLOAT;
        }
        else if  ("REAL".equalsIgnoreCase(name))
        {
 	    result = Types.REAL;
        }
        else if  ("DOUBLE".equalsIgnoreCase(name))
        {
 	    result = Types.DOUBLE;
        }
        else if  ("NUMERIC".equalsIgnoreCase(name))
        {
 	    result = Types.NUMERIC;
        }
        else if  ("DECIMAL".equalsIgnoreCase(name))
        {
	    result = Types.DECIMAL;
        }
        else if  ("CHAR".equalsIgnoreCase(name))
        {
	    result = Types.CHAR;
        }
        else if  ("VARCHAR".equalsIgnoreCase(name))
        {
 	    result = Types.VARCHAR;
        }
        else if  ("LONGVARCHAR".equalsIgnoreCase(name))
        {
 	    result = Types.LONGVARCHAR;
        }
        else if  ("DATE".equalsIgnoreCase(name))
        {
 	    result = Types.DATE;
        }
        else if  ("TIME".equalsIgnoreCase(name))
        {
 	    result = Types.TIME;
        }
        else if  ("TIMESTAMP".equalsIgnoreCase(name))
        {
 	    result = Types.TIMESTAMP;
        }
        else if  ("BINARY".equalsIgnoreCase(name))
        {
	    result = Types.BINARY;
        }
        else if  ("VARBINARY".equalsIgnoreCase(name))
        {
 	    result = Types.VARBINARY;
        }
        else if  ("LONGVARBINARY".equalsIgnoreCase(name))
        {
 	    result = Types.LONGVARBINARY;
        }
        else if  ("NULL".equalsIgnoreCase(name))
        {
	    result = Types.NULL;
        }
        else if  ("OTHER".equalsIgnoreCase(name))
        {
	    result = Types.OTHER;
        }
        else if  ("JAVA_OBJECT".equalsIgnoreCase(name))
        {
            result = Types.JAVA_OBJECT;
        }
        else if  ("DISTINCT".equalsIgnoreCase(name))
        {
            result = Types.DISTINCT;
        }
        else if  ("STRUCT".equalsIgnoreCase(name))
        {
            result = Types.STRUCT;
        }
        else if  ("ARRAY".equalsIgnoreCase(name))
        {
            result = Types.ARRAY;
        }
        else if  ("BLOB".equalsIgnoreCase(name))
        {
            result = Types.BLOB;
        }
        else if  ("CLOB".equalsIgnoreCase(name))
        {
            result = Types.CLOB;
        }
        else if  ("REF".equalsIgnoreCase(name))
        {
            result = Types.REF;
        }
        else if  ("DATALINK".equalsIgnoreCase(name))
        {
            result = Types.DATALINK;
        }
        else if  ("BOOLEAN".equalsIgnoreCase(name))
        {
            result = Types.BOOLEAN;
        }
    //------------------------- JDBC 4.0 -----------------------------------
        else if  ("ROWID".equalsIgnoreCase(name))
        {
            result = -8;//Types.ROWID;
        }
        else if  ("NCHAR".equalsIgnoreCase(name))
        {
            result = -15; //Types.NCHAR;
        }
        else if  ("NVARCHAR".equalsIgnoreCase(name))
        {
            result = -9; //Types.NVARCHAR;
        }
        else if  ("LONGNVARCHAR".equalsIgnoreCase(name))
        {
            result = -16; //Types.LONGNVARCHAR;
        }
        else if  ("NCLOB".equalsIgnoreCase(name))
        {
            result = 2011; //Types.NCLOB;
        }
        else if  ("SQLXML".equalsIgnoreCase(name))
        {
            result = 2009; //Types.SQLXML;
        }

        return result;
    }

    /**
     * Resolves given <code>java.sql.Types</code> constant to its name.
     * @param type the type id.
     * @return the type name.
     */
    public String resolve(final int type)
    {
        String result = "OTHER";

        switch  (type)
        {
            case Types.BIT:
                result = "BIT";
                break;
                
            case Types.TINYINT:
                result = "TINYINT";
                break;
                
            case Types.SMALLINT:
                result = "SMALLINT";
                break;
                
            case Types.INTEGER:
                result = "INTEGER";
                break;
                
            case Types.BIGINT:
                result = "BIGINT";
                break;
                
            case Types.FLOAT:
                result = "FLOAT";
                break;
                
            case Types.REAL:
                result = "REAL";
                break;
                
            case Types.DOUBLE:
                result = "DOUBLE";
                break;
                
            case Types.NUMERIC:
                result = "NUMERIC";
                break;
                
            case Types.DECIMAL:
                result = "DECIMAL";
                break;
                
            case Types.CHAR:
                result = "CHAR";
                break;
                
            case Types.VARCHAR:
                result = "VARCHAR";
                break;
                
            case Types.LONGVARCHAR:
                result = "LONGVARCHAR";
                break;
                
            case Types.DATE:
                result = "DATE";
                break;
                
            case Types.TIME:
                result = "TIME";
                break;
                
            case Types.TIMESTAMP:
                result = "TIMESTAMP";
                break;
                
            case Types.BINARY:
                result = "BINARY";
                break;
                
            case Types.VARBINARY:
                result = "VARBINARY";
                break;
                
            case Types.LONGVARBINARY:
                result = "LONGVARBINARY";
                break;
                
            case Types.NULL:
                result = "NULL";
                break;
                
            case Types.OTHER:
                result = "OTHER";
                break;
                
            case Types.JAVA_OBJECT:
                result = "JAVA_OBJECT";
                break;
                
            case Types.DISTINCT:
                result = "DISTINCT";
                break;
                
            case Types.STRUCT:
                result = "STRUCT";
                break;
                
            case Types.ARRAY:
                result = "ARRAY";
                break;
                
            case Types.BLOB:
                result = "BLOB";
                break;
                
            case Types.CLOB:
                result = "CLOB";
                break;
                
            case Types.REF:
                result = "REF";
                break;
                
            case Types.DATALINK:
                result = "DATALINK";
                break;
                
            case Types.BOOLEAN:
                result = "BOOLEAN";
                break;
                
    //------------------------- JDBC 4.0 -----------------------------------
            case -8: //Types.ROWID:
                result = "ROWID";
                break;
                
            case -15: //Types.NCHAR:
                result = "NCHAR";
                break;
                
            case -9: //Types.NVARCHAR:
                result = "NVARCHAR";
                break;
                
            case -16: //Types.LONGNVARCHAR:
                result = "LONGNVARCHAR";
                break;
                
            case 2011: //Types.NCLOB:
                result = "NCLOB";
                break;
                
            case 2009: //Types.SQLXML:
                result = "SQLXML";
                break;
                
            default:
                result = "OTHER";
                break;
        }

        return result;
    }
}
