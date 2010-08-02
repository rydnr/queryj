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
 * Description: Traces method calls.
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
package aspects.org.acmsl.queryj;

/*
 * Importing project classes.
 */
import org.acmsl.queryj.Condition;
import org.acmsl.queryj.SelectQuery;
import org.acmsl.queryj.tools.OracleMetaDataManager;

/*
 * Importing JDK classes.
 */
import java.util.HashMap;
import java.util.Map;

/*
 * Importing Jakarta Commons classes.
 */
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Traces method calls.
 * @author <a href=
  "http://www.samspublishing.com/catalog/product.asp?product_id={681277AC-6106-4C7D-B71A-59D04871EE89}"
   >Ivan Kiselev (from Aspect-Oriented Programming with AspectJ)</a>
 * @version $Revision$
 */
public aspect Tracer
{
    /**
     * The stack depth.
     */
    private static Map m__mStackDepths = new HashMap();

    /**
     * Specifies the stack depths.
     * @param map the new map.
     */
    protected void setStackDepths(Map map)
    {
        m__mStackDepths = map;
    }

    /**
     * Retrieves the stack depths map.
     * @return such map.
     */
    public Map getStackDepths()
    {
        return m__mStackDepths;
    }

    /**
     * The tracing pointcut.
     */
    pointcut tracePoint(Object obj):
           execution(* *.*(..))
        && this(obj)
        && !within(Tracer)
        && !within(Log)
        && !within(java.io.PrintStream)
        && !execution(String *.toString())
        && !execution(String *.getPrefix())
        && !execution(String *.getSuffix())
        && !execution(* *.getLeftSideField())
        && !execution(* *.getRightSideField())
        && !execution(* *.getLeftSideValue())
        && !execution(* *.getRightSideValue())
        && !execution(* *.getOperator())
        && !execution(* *.getSymbol())
        && !execution(* *.getTable())
        && !execution(* *.getTableAlias())
        && !execution(* *.getTableAliasName())
        && !execution(* *.getName())
        && !execution(* *.getTableAliasReference());

    /**
     * Before reaching tracing pointcut.
     */
    before(Object obj) : tracePoint(obj)
    {
        Map t_mStackDepths = getStackDepths();

        if  (t_mStackDepths == null)
        {
            t_mStackDepths = new HashMap();
            setStackDepths(t_mStackDepths);
        }

        Integer t_Depth = (Integer) t_mStackDepths.get(Thread.currentThread());

        if  (t_Depth == null)
        {
            t_Depth = new Integer(0);
        }

        //LogFactory.getLog("tracer-in").info(
        System.out.println(
            indent(t_Depth.intValue()) + " >> "  + thisJoinPointStaticPart.getSignature());

        t_mStackDepths.put(Thread.currentThread(), new Integer(t_Depth.intValue() + 1));
    }

    /**
     * After reaching tracing pointcut.
     */
    after(Object obj) : tracePoint(obj)
    {
        Map t_mStackDepths = getStackDepths();

        if  (t_mStackDepths == null)
        {
            t_mStackDepths = new HashMap();
            setStackDepths(t_mStackDepths);
        }

        Integer t_Depth = (Integer) t_mStackDepths.get(Thread.currentThread());

        if  (t_Depth == null)
        {
            t_Depth = new Integer(0);
        }

        t_Depth = new Integer(t_Depth.intValue() - 1);

        if  (t_Depth.intValue() == 0)
        {
            t_mStackDepths.remove(Thread.currentThread());
        }
        else
        {
            t_mStackDepths.put(Thread.currentThread(), t_Depth);
        }

        //LogFactory.getLog("tracer-out").info(
        System.out.println(
            indent(t_Depth.intValue()) + " << " + thisJoinPointStaticPart.getSignature());
    }

    /**
     * Performs the indentation.
     * @param num the amount to indent.
     * @return such indentation.
     */
    protected static StringBuffer indent(int num)
    {
        StringBuffer result = new StringBuffer();

        for  (int t_iIndex = 0;
                  t_iIndex < num;
                  t_iIndex++)
        {
            result.append(' ');
        }

        result.append(Integer.toString(num));
        result.append(" [");
        result.append(Thread.currentThread().hashCode());
        result.append("]");

        return result;
    }
}
