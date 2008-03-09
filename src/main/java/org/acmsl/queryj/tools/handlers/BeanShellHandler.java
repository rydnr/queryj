//;-*- mode: java -*-
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
 * Filename: BeanShellHandler.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Conditionally launches a BeanShell to allow the user
 *              interact with the model and perform acceptance tests.
 *
 */
package org.acmsl.queryj.tools.handlers;

/*
 * Importing some BeanShell classes.
 */
import bsh.Interpreter;
import bsh.EvalError;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.tools.AntCommand;
import org.acmsl.queryj.tools.handlers.AbstractAntCommandHandler;

/*
 * Importing some ACM-SL Commons classes.
 */
import org.acmsl.commons.logging.UniqueLogFactory;
import org.acmsl.commons.patterns.Command;

/*
 * Importing some Ant classes.
 */
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.input.DefaultInputHandler;
import org.apache.tools.ant.input.InputRequest;

/*
 * Importing some Commons-Logging classes.
 */
import org.apache.commons.logging.Log;

/*
 * Importing some JDK classes.
 */
import java.util.Map;

/**
 * Conditionally launches a BeanShell to allow the user
 * interact with the model and perform acceptance tests.
 * @author <a href="mailto:chous@acm-sl.org"
           >Jose San Leandro</a>
 */
public class BeanShellHandler
    extends  AbstractAntCommandHandler
{
    /**
     * The initial messages.
     */
    public static final String[] INITIAL_MESSAGES =
        {
            "Important notice:",
            "-All QueryJ packages are automatically imported.",
            "-Type 'quit' when done."
        };

    /**
     * The available packages.
     */
    public static final String[] AVAILABLE_PACKAGES =
        {
            "org.acmsl.queryj",
            "org.acmsl.queryj.dao",
            "org.acmsl.queryj.tools",
            "org.acmsl.queryj.tools.antlr",
            "org.acmsl.queryj.tools.customsql",
            "org.acmsl.queryj.tools.customsql.handlers",
            "org.acmsl.queryj.tools.customsql.xml",
            "org.acmsl.queryj.tools.handlers",
            "org.acmsl.queryj.tools.handlers.oracle",
            "org.acmsl.queryj.tools.templates",
            "org.acmsl.queryj.tools.templates.dao",
            "org.acmsl.queryj.tools.templates.dao.handlers",
            "org.acmsl.queryj.tools.templates.dao.mock",
            "org.acmsl.queryj.tools.templates.dao.mock.handlers",
            "org.acmsl.queryj.tools.templates.dao.xml",
            "org.acmsl.queryj.tools.templates.dao.xml.handlers",
            "org.acmsl.queryj.tools.templates.functions",
            "org.acmsl.queryj.tools.templates.functions.numeric",
            "org.acmsl.queryj.tools.templates.functions.numeric.handlers",
            "org.acmsl.queryj.tools.templates.functions.numeric.mysql",
            "org.acmsl.queryj.tools.templates.functions.numeric.oracle",
            "org.acmsl.queryj.tools.templates.functions.system",
            "org.acmsl.queryj.tools.templates.functions.system.handlers",
            "org.acmsl.queryj.tools.templates.functions.system.mysql",
            "org.acmsl.queryj.tools.templates.functions.system.oracle",
            "org.acmsl.queryj.tools.templates.functions.text",
            "org.acmsl.queryj.tools.templates.functions.text.handlers",
            "org.acmsl.queryj.tools.templates.functions.text.mysql",
            "org.acmsl.queryj.tools.templates.functions.text.oracle",
            "org.acmsl.queryj.tools.templates.functions.time",
            "org.acmsl.queryj.tools.templates.functions.time.handlers",
            "org.acmsl.queryj.tools.templates.functions.time.mysql",
            "org.acmsl.queryj.tools.templates.functions.time.oracle",
            "org.acmsl.queryj.tools.templates.handlers",
            "org.acmsl.queryj.tools.templates.valueobject",
            "org.acmsl.queryj.tools.templates.valueobject.handlers"
        };

    /**
     * The QueryJ prompt.
     */
    public static final String PROMPT = "(queryj-bsh)";

    /**
     * Creates a BeanShellHandler.
     */
    public BeanShellHandler() {};

    /**
     * Handles given command.
     * @param command the command to handle.
     * @return <code>true</code> if the chain should be stopped.
     */
    public boolean handle(final Command command)
    {
        boolean result = false;

        if  (command instanceof AntCommand) 
        {
            AntCommand t_AntCommand = (AntCommand) command;
            
            result = handle(t_AntCommand);
        }
        
        return result;
    }

    /**
     * Handles given command.
     * @param command the command to handle.
     * @return <code>true</code> if the chain should be stopped.
     * @precondition command != null
     */
    public boolean handle(final AntCommand command)
    {
        return handle(command.getAttributeMap());
    }

    /**
     * Handles given information.
     * @param parameters the parameters.
     * @return <code>true</code> if the chain should be stopped.
     * @precondition parameters != null
     */
    protected boolean handle(final Map parameters)
    {
        Log t_Log = UniqueLogFactory.getLog(BeanShellHandler.class);
                
        Boolean t_bShell =
            (Boolean) parameters.get(ParameterValidationHandler.SHELL);

        if  (Boolean.TRUE.equals(t_bShell))
        {
            try
            {
                Interpreter t_Interpreter = setUpInterpreter();

                int t_iCount =
                    INITIAL_MESSAGES != null ? INITIAL_MESSAGES.length : 0;

                StringBuffer t_sbInitialMessage = new StringBuffer();

                for  (int t_iIndex = 0; t_iIndex < t_iCount; t_iIndex++)
                {
                    t_sbInitialMessage.append(INITIAL_MESSAGES[t_iIndex] + "\n");
                }

                t_Log.info(t_sbInitialMessage.toString());

                InputRequest t_Request =
                    new BshInputRequest(PROMPT, t_Interpreter);

                new DefaultInputHandler().handleInput(t_Request);
            }
            catch  (final EvalError evalError)
            {
                if  (t_Log != null)
                {
                    t_Log.error(
                        "Cannot launch BeanShell.",
                        evalError);
                }
            }
            catch  (final BuildException buildException)
            {
                if  (t_Log != null)
                {
                    t_Log.error(
                        "Cannot launch BeanShell.",
                        buildException);
                }
            }
        }

        return false;
    }

    /**
     * Sets up a new interpreter.
     * @return the configured Interpreter instance.
     * @throws  EvalError if the interpreter cannot be configured.
     */
    protected Interpreter setUpInterpreter()
        throws  EvalError
    {
        Interpreter result = new Interpreter();

        result.set("bsh.prompt", "(queryj) %");
        result.set("bsh.interactive", false);
        result.set("bsh.eval", true);

        int t_iCount =
            AVAILABLE_PACKAGES != null ? AVAILABLE_PACKAGES.length : 0;

        for  (int t_iIndex = 0; t_iIndex < t_iCount; t_iIndex++)
        {
            result.eval("import " + AVAILABLE_PACKAGES[t_iIndex]);
        }

        return result;
    }

    /**
     * Retrieves the relative weight of this handler.
     * @param parameters the parameters.
     * @return a value between <code>MIN_WEIGHT</code>
     * and <code>MAX_WEIGHT</code>.
     */
    public double getRelativeWeight(final Map parameters)
    {
        return MIN_WEIGHT;
    }

    /**
     * Manages the input from Ant.
     * @author <a href="mailto:jose.sanleandro@ventura24.es">Jose San Leandro</a>
     */
    protected static class BshInputRequest
        extends  InputRequest
    {
        /**
         * The BeanShell interpreter.
         */
        private Interpreter m__Interpreter;

        /**
         * Creates a <code>BshInputRequest</code> for given interpreter.
         * @param interpreter such instance.
         */
        public BshInputRequest(
            final String prompt, final Interpreter interpreter)
        {
            super(prompt);
            immutableSetInterpreter(interpreter);
        }

        /**
         * Specifies the interpreter.
         * @param interpreter such instance.
         */
        protected final void immutableSetInterpreter(
            final Interpreter interpreter)
        {
            m__Interpreter = interpreter;
        }

        /**
         * Specifies the interpreter.
         * @param interpreter such instance.
         */
        protected void setInterpreter(final Interpreter interpreter)
        {
            immutableSetInterpreter(interpreter);
        }

        /**
         * Retrieves the interpreter.
         * @return such instance.
         */
        public Interpreter getInterpreter()
        {
            return m__Interpreter;
        }

        /**
         * Receives given input.
         * @param input such input.
         */
        public void setInput(final String input)
        {
            setInput(
                input,
                getInterpreter(),
                UniqueLogFactory.getLog(BeanShellHandler.class));
                
        }

        /**
         * Receives given input.
         * @param input such input.
         * @param interpreter the BeanShell interpreter.
         * @param log the log.
         */
        protected void setInput(
            final String input,
            final Interpreter interpreter,
            final Log log)
          throws  BuildException
        {
            try
            {
                super.setInput(input);

                if  (isInputValid())
                {
                    log("" + interpreter.eval(input), log);
                }
            }
            catch  (final EvalError evalError)
            {
                log(evalError.getMessage(), log);
            }
        }

        /**
         * Logs given message.
         * @param message the message.
         * @param log the <code>Log</code> instance.
         */
        protected void log(final String message, final Log log)
        {
            if  (log != null)
            {
                log.info(message);
            }
            else
            {
                System.out.println(message);
            }
        }

        /**
         * Checks whether the input is valid.
         * @return <code>true</code> in such case.
         */
        public boolean isInputValid()
        {
            boolean result = true;

            String t_strInput = getInput();

            if  (   (t_strInput == null)
                 || (t_strInput.trim().length() == 0)
                 || (t_strInput.equalsIgnoreCase("quit")))
            {
                result = false;
            }

            return result;
        }
    }
}
