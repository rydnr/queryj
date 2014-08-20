/*
                        QueryJ Template Debugging

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
 * Filename: NettyServerDebuggingService.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Netty-based TCP/IP server which drives the template debugging
 *              process.
 *
 * Date: 2014/06/26
 * Time: 18:43
 *
 */
package org.acmsl.queryj.debugging.netty;

/*
 * Importing QueryJ Core classes.
 */
import org.acmsl.queryj.api.TemplateContext;
import org.acmsl.queryj.api.exceptions.DevelopmentModeException;
import org.acmsl.queryj.debugging.TemplateDebuggingCommand;
import org.acmsl.queryj.debugging.TemplateDebuggingListener;
import org.acmsl.queryj.debugging.TemplateDebuggingService;

/*
 * Importing Netty classes.
 */
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/*
 * Importing Apache Commons Logging classes.
 */
import org.apache.commons.logging.LogFactory;

/*
 * Importing StringTemplate classes.
 */
import org.stringtemplate.v4.ST;

/*
 * Importing JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/*
 * Importing JDK classes.
 */
import java.io.IOException;

/**
 * Netty-based TCP/IP server which drives the
 * template debugging process.
 * @param <C> the template context.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2014/06/26 18:43
 */
@ThreadSafe
public class NettyServerDebuggingService<C extends TemplateContext>
    implements TemplateDebuggingService<C>,
               TemplateDebuggingListener
{
    /**
     * The system property for debugging templates.
     */
    public static final String QUERYJ_TEMPLATE_DEBUG_PORT = "queryj.template.debug.port";

    /**
     * The server bootstrap.
     */
    private ServerBootstrap m__ServerBootstrap;

    /**
     * The event loop group.
     */
    private EventLoopGroup m__EventLoopGroup;

    /**
     * The channel future.
     */
    private ChannelFuture m__ChannelFuture;

    /**
     * The last command received.
     */
    private TemplateDebuggingCommand m__Command;

    /**
     * Specifies the {@link ServerBootstrap}.
     * @param bootstrap the bootstrap.
     */
    protected final void immutableSetServerBootstrap(@NotNull final ServerBootstrap bootstrap)
    {
        this.m__ServerBootstrap = bootstrap;
    }

    /**
     * Specifies the {@link ServerBootstrap}.
     * @param bootstrap the bootstrap.
     */
    @SuppressWarnings("unused")
    protected void setServerBootstrap(@NotNull final ServerBootstrap bootstrap)
    {
        immutableSetServerBootstrap(bootstrap);
    }

    /**
     * Retrieves the {@link ServerBootstrap}.
     * @return such bootstrap.
     */
    @SuppressWarnings("unused")
    @Nullable
    protected ServerBootstrap getServerBootstrap()
    {
        return this.m__ServerBootstrap;
    }

    /**
     * Specifies the event loop group.
     * @param group such {@link EventLoopGroup}.
     */
    protected final void immutableSetEventLoopGroup(@NotNull final EventLoopGroup group)
    {
        this.m__EventLoopGroup = group;
    }

    /**
     * Specifies the event loop group.
     * @param group such {@link EventLoopGroup}.
     */
    protected void setEventLoopGroup(@NotNull final EventLoopGroup group)
    {
        immutableSetEventLoopGroup(group);
    }

    /**
     * Retrieves the event loop group.
     * @return such {@link EventLoopGroup}.
     */
    protected EventLoopGroup getEventLoopGroup()
    {
        return m__EventLoopGroup;
    }

    /**
     * Specifies the channel future.
     * @param future such {@link ChannelFuture}.
     */
    protected final void immutableSetChannelFuture(@NotNull final ChannelFuture future)
    {
        this.m__ChannelFuture = future;
    }

    /**
     * Specifies the channel future.
     * @param future such {@link ChannelFuture}.
     */
    @SuppressWarnings("unused")
    protected void setChannelFuture(@NotNull final ChannelFuture future)
    {
        immutableSetChannelFuture(future);
    }

    /**
     * Retrieves the channel future.
     * @return such {@link ChannelFuture}.
     */
    @SuppressWarnings("unused")
    protected ChannelFuture getChannelFuture()
    {
        return this.m__ChannelFuture;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @NotNull
    public TemplateDebuggingCommand debugTemplate(
        @NotNull final ST template, @NotNull final C context, @NotNull final String output)
        throws DevelopmentModeException
    {
        try
        {
            final ChannelFuture future = launchServer();

            future.sync();
        }
        catch (@NotNull final InterruptedException | IOException interruption)
        {
            throw new DevelopmentModeException(template.groupThatCreatedThisInstance);
        }

        return this.m__Command;
    }

    /**
     * Launches the server.
     * @return the {@link ChannelFuture}.
     * @throws InterruptedException if the server gets interrupted.
     * @throws IOException if the socket cannot be bound.
     */
    public ChannelFuture launchServer()
        throws InterruptedException,
                IOException
    {
        final int t_iPort;

        @Nullable final String t_strPort = System.getProperty(QUERYJ_TEMPLATE_DEBUG_PORT);

        if (t_strPort != null)
        {
            t_iPort = Integer.valueOf(t_strPort);
        }
        else
        {
            t_iPort = 0;
        }

        return launchServer(t_iPort);
    }

    /**
     * Launches the server.
     * @param port the port.
     * @return the {@link ChannelFuture}.
     * @throws InterruptedException if the server gets interrupted.
     * @throws IOException if the socket cannot be bound.
     */
    public ChannelFuture launchServer(final int port)
        throws InterruptedException,
        IOException
    {

        return launchServer(port, new NettyServerChannelHandler(this));
    }

    /**
     * Launches the server.
     * @param port the port.
     * @param handler the {@link ChannelHandlerAdapter handler} to handle incoming connections.
     * @return the {@link ChannelFuture}.
     * @throws InterruptedException if the server gets interrupted.
     * @throws IOException if the socket cannot be bound.
     */
    @NotNull
    protected ChannelFuture launchServer(final int port, @NotNull final ChannelHandlerAdapter handler)
        throws InterruptedException,
               IOException
    {
        @NotNull final ChannelFuture result;

        @Nullable ChannelFuture aux = null;

        @NotNull final EventLoopGroup bossGroup = new NioEventLoopGroup();
        setEventLoopGroup(bossGroup);
        @NotNull final EventLoopGroup workerGroup = new NioEventLoopGroup();
        try
        {
            @NotNull final ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>()
                { // (4)
                    /**
                     * {@inheritDoc}
                     */
                    @Override
                    public void initChannel(@NotNull final SocketChannel ch)
                        throws Exception
                    {
                        ch.pipeline().addLast(handler);
                    }
                })
                .option(ChannelOption.SO_BACKLOG, 128)          // (5)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10000)
                .childOption(ChannelOption.SO_KEEPALIVE, true); // (6)

            // Bind and start to accept incoming connections.
            aux = b.bind(port).sync(); // (7)

            // Wait until the server socket is closed.
            // In this example, this does not happen, but you can do that to gracefully
            // shut down your server.
//            result.channel().closeFuture().sync();
        }
        catch (@NotNull final Throwable throwable)
        {
            LogFactory.getLog(NettyServerDebuggingService.class).fatal(
                "Cannot run the template debugging server", throwable);
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }

        if (aux == null)
        {
            throw new RuntimeException("Cannot run server");
        }
        else
        {
            result = aux;
        }

        return result;
    }

    /**
     * Stops the server.
     * @throws InterruptedException if the server cannot be stopped.
     */
    @SuppressWarnings("unused")
    public void stopServer()
        throws InterruptedException
    {
        stopServer(getEventLoopGroup());
    }

    /**
     * Stops the server.
     * @param group the {@link EventLoopGroup group}.
     * @throws InterruptedException if the server cannot be stopped.
     */
    protected void stopServer(@NotNull final EventLoopGroup group)
        throws InterruptedException
    {
        group.shutdownGracefully().sync();
    }

    /**
     * Gets notified whenever a "reload" operation has been
     * requested.
     */
    @Override
    public void reloadRequested()
    {
        this.m__Command = TemplateDebuggingCommand.RELOAD;
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    public String toString()
    {
        return
              "{ \"lastCommand\": \"" + m__Command + '"'
            + ", \"channelFuture\": \"" + m__ChannelFuture.hashCode() + '"'
            + ", \"serverBootstrap\": \"" + m__ServerBootstrap.hashCode() + '"'
            + ", \"eventLoopGroup\": \"" + m__EventLoopGroup.hashCode() + '"'
            + ", \"class\": \"" + NettyServerDebuggingService.class.getSimpleName() + '"'
            + ", \"package\": \"" + NettyServerDebuggingService.class.getPackage() + "\" }";
    }
}
