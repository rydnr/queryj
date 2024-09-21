// Generated from PerComment.g4 by ANTLR 4.1

/*
                        QueryJ-Core

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
    Contact info: chous@acm-sl.org
    Postal Address: c/Playa de Lagoa, 1
                    Urb. Valdecabanas
                    Boadilla del monte
                    28660 Madrid
                    Spain

 ******************************************************************************
 *
 * Generated from PerComment.g4 by ANTLR.
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: ANTLR parser for PerComment.g4
 *
 */
package org.acmsl.queryj.tools.antlr;

/*
 * Importing ANTLR classes.
 */
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;

/*
 * Importing Apache Commons Logging classes.
 */
import org.apache.commons.logging.LogFactory;

/*
 * Importing JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/*
 * Importing checkthread.org classes.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * This class provides an empty implementation of {@link PerCommentListener},
 * which can be extended to create a listener which only needs to handle a subset
 * of the available methods.
 */
@ThreadSafe
public class PerCommentLoggingListener
    extends PerCommentBaseListener
{
    /**
     * The original text to parse.
     */
    private String m__strComment;

    /**
     * Creates a new listener to log all parsing events.
     * @param comment the comment to parse.
     */
    public PerCommentLoggingListener(@NotNull final String comment)
    {
        immutableSetComment(comment);
        LogFactory.getLog(PerCommentLoggingListener.class).debug("Parsing [" + comment + ']');
    }

    /**
     * Specifies the comment.
     * @param comment the comment.
     */
    protected final void immutableSetComment(@NotNull final String comment)
    {
        this.m__strComment = comment;
    }

    /**
     * Specifies the comment.
     * @param comment the comment.
     */
    @SuppressWarnings("unused")
    protected void setComment(@NotNull final String comment)
    {
        immutableSetComment(comment);
    }

    /**
     * Retrieves the comment.
     * @return the comment.
     */
    @NotNull
    public String getComment()
    {
        return this.m__strComment;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void enterTabStatic(@NotNull final PerCommentParser.TabStaticContext ctx)
    {
        LogFactory.getLog(PerCommentLoggingListener.class).debug("Entering tabStatic: " + ctx.getText());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void exitTabStatic(@NotNull final PerCommentParser.TabStaticContext ctx)
    {
        LogFactory.getLog(PerCommentLoggingListener.class).debug("Exiting tabStatic: " + ctx.getText());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void enterColIsarefs(@NotNull final PerCommentParser.ColIsarefsContext ctx)
    {
        LogFactory.getLog(PerCommentLoggingListener.class).debug("Entering colIsarefs: " + ctx.getText());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void exitColIsarefs(@NotNull final PerCommentParser.ColIsarefsContext ctx)
    {
        LogFactory.getLog(PerCommentLoggingListener.class).debug("Exiting colIsarefs: " + ctx.getText());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void enterColAnnotation(@NotNull final PerCommentParser.ColAnnotationContext ctx)
    {
        LogFactory.getLog(PerCommentLoggingListener.class).debug("Entering colAnnotation: " + ctx.getText());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void exitColAnnotation(@NotNull final PerCommentParser.ColAnnotationContext ctx)
    {
        LogFactory.getLog(PerCommentLoggingListener.class).debug("Exiting colAnnotatiotn: " + ctx.getText());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void enterTabIsa(@NotNull final PerCommentParser.TabIsaContext ctx)
    {
        LogFactory.getLog(PerCommentLoggingListener.class).debug("Entering tabIsa: " + ctx.getText());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void exitTabIsa(@NotNull final PerCommentParser.TabIsaContext ctx)
    {
        LogFactory.getLog(PerCommentLoggingListener.class).debug("Exiting tabIsa: " + ctx.getText());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void enterColBool(@NotNull final PerCommentParser.ColBoolContext ctx)
    {
        LogFactory.getLog(PerCommentLoggingListener.class).debug("Entering colBool: " + ctx.getText());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void exitColBool(@NotNull final PerCommentParser.ColBoolContext ctx)
    {
        LogFactory.getLog(PerCommentLoggingListener.class).debug("Exiting colBool: " + ctx.getText());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void enterColReadonly(@NotNull final PerCommentParser.ColReadonlyContext ctx)
    {
        LogFactory.getLog(PerCommentLoggingListener.class).debug("Entering colReadonly: " + ctx.getText());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void exitColReadonly(@NotNull final PerCommentParser.ColReadonlyContext ctx)
    {
        LogFactory.getLog(PerCommentLoggingListener.class).debug("Exiting colReadonly: " + ctx.getText());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void enterTabRelationship(@NotNull final PerCommentParser.TabRelationshipContext ctx)
    {
        LogFactory.getLog(PerCommentLoggingListener.class).debug("Entering tabRelationship: " + ctx.getText());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void exitTabRelationship(@NotNull final PerCommentParser.TabRelationshipContext ctx)
    {
        LogFactory.getLog(PerCommentLoggingListener.class).debug("Exiting tabRelationship: " + ctx.getText());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void enterColumnComment(@NotNull final PerCommentParser.ColumnCommentContext ctx)
    {
        LogFactory.getLog(PerCommentLoggingListener.class).debug("Entering columnComment: " + ctx.getText());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void exitColumnComment(@NotNull final PerCommentParser.ColumnCommentContext ctx)
    {
        LogFactory.getLog(PerCommentLoggingListener.class).debug("Exiting columnComment: " + ctx.getText());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void enterColOraseq(@NotNull final PerCommentParser.ColOraseqContext ctx)
    {
        LogFactory.getLog(PerCommentLoggingListener.class).debug("Entering colOraseq: " + ctx.getText());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void exitColOraseq(@NotNull final PerCommentParser.ColOraseqContext ctx)
    {
        LogFactory.getLog(PerCommentLoggingListener.class).debug("Exiting colOraseq: " + ctx.getText());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void enterIdent(@NotNull final PerCommentParser.IdentContext ctx)
    {
        LogFactory.getLog(PerCommentLoggingListener.class).debug("Entering ident: " + ctx.getText());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void exitIdent(@NotNull final PerCommentParser.IdentContext ctx)
    {
        LogFactory.getLog(PerCommentLoggingListener.class).debug("Exiting ident: " + ctx.getText());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void enterTabAnnotation(@NotNull final PerCommentParser.TabAnnotationContext ctx)
    {
        LogFactory.getLog(PerCommentLoggingListener.class).debug("Entering tabAnnotation: " + ctx.getText());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void exitTabAnnotation(@NotNull final PerCommentParser.TabAnnotationContext ctx)
    {
        LogFactory.getLog(PerCommentLoggingListener.class).debug("Exiting tabAnnotation: " + ctx.getText());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void enterTableComment(@NotNull final PerCommentParser.TableCommentContext ctx)
    {
        LogFactory.getLog(PerCommentLoggingListener.class).debug("Entering tableComment: " + ctx.getText());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void exitTableComment(@NotNull final PerCommentParser.TableCommentContext ctx)
    {
        LogFactory.getLog(PerCommentLoggingListener.class).debug("Exiting tableComment: " + ctx.getText());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void enterTabIsatype(@NotNull final PerCommentParser.TabIsatypeContext ctx)
    {
        LogFactory.getLog(PerCommentLoggingListener.class).debug("Entering tabIsatype: " + ctx.getText());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void exitTabIsatype(@NotNull final PerCommentParser.TabIsatypeContext ctx)
    {
        LogFactory.getLog(PerCommentLoggingListener.class).debug("Exiting tabIsatype: " + ctx.getText());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void enterTabDecorator(@NotNull final PerCommentParser.TabDecoratorContext ctx)
    {
        LogFactory.getLog(PerCommentLoggingListener.class).debug("Entering tabDecorator: " + ctx.getText());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void exitTabDecorator(@NotNull final PerCommentParser.TabDecoratorContext ctx)
    {
        LogFactory.getLog(PerCommentLoggingListener.class).debug("Exiting tabDecorator: " + ctx.getText());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void enterEveryRule(@NotNull final ParserRuleContext ctx)
    {
        LogFactory.getLog(PerCommentLoggingListener.class).debug("Entering everyRule: " + ctx.getText());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void exitEveryRule(@NotNull final ParserRuleContext ctx)
    {
        LogFactory.getLog(PerCommentLoggingListener.class).debug("Exiting everyRule: " + ctx.getText());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visitTerminal(@NotNull final TerminalNode node)
    {
        LogFactory.getLog(PerCommentLoggingListener.class).debug("Visiting terminal: " + node.getText());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visitErrorNode(@NotNull final ErrorNode node)
    {
        LogFactory.getLog(PerCommentLoggingListener.class).debug("Visiting error node: " + node.getText() + " | " + node.toStringTree());
    }

    @NotNull
    @Override
    public String toString()
    {
        return
              "{ \"comment\": \"" + m__strComment + '"'
            + ", \"class\": \"" + PerCommentLoggingListener.class.getSimpleName() + '"'
            + ", \"package\": \"" + PerCommentLoggingListener.class.getPackage().getName() + '"'
            + " }";
    }
}