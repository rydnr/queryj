/*
                        queryj

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
    Contact info: jose.sanleandro@acm-sl.com

 ******************************************************************************
 *
 * Filename: NettyServerChannelHandler.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: 
 *
 * Date: 2014/06/28
 * Time: 20:47
 *
 */
package org.acmsl.queryj.debugging.netty;

/*
 * Importing JetBrains annotations.
 */
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;
import org.acmsl.queryj.tools.debugging.TemplateDebuggingListener;
import org.jetbrains.annotations.NotNull;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 *
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2014/06/28 20:47
 */
@ThreadSafe
@Sharable
public class NettyServerChannelHandler
    extends ChannelHandlerAdapter
{
    /**
     * The listener.
     */
    private TemplateDebuggingListener m__Listener;

    /**
     * Creates a new instance.
     * @param listener the {@link TemplateDebuggingListener listener}.
     */
    public NettyServerChannelHandler(@NotNull final TemplateDebuggingListener listener)
    {
        //TODO
        this.m__Listener = listener;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void channelRead(@NotNull final ChannelHandlerContext ctx, @NotNull final Object msg)
    {
        try
        {
            @NotNull final ByteBuf buffer = (ByteBuf) msg;

            @NotNull final byte[] aux = new byte[buffer.readableBytes()];

            for (int index = 0; index < aux.length; index++)
            {
                aux[index] = buffer.readByte();
            }

//            System.out.println("Received " + new String(aux, CharsetUtil.US_ASCII));

            processCommand(new String(aux, CharsetUtil.US_ASCII), this.m__Listener);
        }
        finally
        {
            ReferenceCountUtil.release(msg);
        }

        final ChannelFuture future = ctx.writeAndFlush("ACK");
        future.addListener(
            channelFuture -> {
                System.out.println("Closing context");
                ctx.close();
            });

        /*
        try
        {
            ctx.channel().closeFuture().sync();
        }
        catch (@NotNull final InterruptedException ex)
        {

        }*/
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void channelReadComplete(final ChannelHandlerContext ctx) throws Exception
    {
        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void exceptionCaught(final ChannelHandlerContext ctx, final Throwable cause)
        throws Exception
    {
        cause.printStackTrace();
        ctx.close();
    }

    /**
     * Processes given command.
     * @param command the command.
     * @param listener the {@link TemplateDebuggingListener listener}.
     */
    public void processCommand(@NotNull final String command, @NotNull final TemplateDebuggingListener listener)
    {
        if (command.equals("reload"))
        {
            listener.reloadRequested();
        }
    }
}
