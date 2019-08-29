package com.heshaowei.myproj.im.server.io;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.heshaowei.myproj.im.server.enums.MessageStates;
import com.heshaowei.myproj.im.server.enums.MessageTypes;
import com.heshaowei.myproj.im.server.model.GroupMessage;
import com.heshaowei.myproj.im.server.model.Message;
import com.heshaowei.myproj.im.server.model.UserMessage;
import com.heshaowei.myproj.im.server.utils.GsonUtil;
import com.heshaowei.myproj.im.server.utils.JwtUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.AttributeKey;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Date;

public class MyChannelHandler extends SimpleChannelInboundHandler<Object> {


    private static final Logger LOGGER = LoggerFactory.getLogger(MyChannelHandler.class);

    private String URI = "websocket";

    private WebSocketServerHandshaker handshaker ;

    private MessageRepository messageRepository;

    public MyChannelHandler(){}

    public MyChannelHandler withURI(String uri){
        this.URI = uri;
        return this;
    }

    public MyChannelHandler repository(MessageRepository messageRepository){
        this.messageRepository = messageRepository;
        return this;
    }

    /**
     * 连接上服务器
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        LOGGER.info("【handlerAdded】====>"+ctx.channel().id());
        GlobalUserUtil.channels.add(ctx.channel());
    }

    /**
     * 断开连接
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        LOGGER.info("【handlerRemoved】====>"+ctx.channel().id());
        GlobalUserUtil.channels.remove(ctx);
    }

    /**
     * 连接异常   需要关闭相关资源
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        LOGGER.error("【系统异常】======>"+cause.toString());
        ctx.close();
        ctx.channel().close();
    }

    /**
     * 活跃的通道  也可以当作用户连接上客户端进行使用
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        LOGGER.info("【channelActive】=====>"+ctx.channel());
    }

    /**
     * 不活跃的通道  就说明用户失去连接
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
    }

    /**
     * 这里只要完成 flush
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    /**
     * 这里是保持服务器与客户端长连接  进行心跳检测 避免连接断开
     * @param ctx
     * @param evt
     * @throws Exception
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if(evt instanceof IdleStateEvent){
            IdleStateEvent stateEvent = (IdleStateEvent) evt;
            PingWebSocketFrame ping = new PingWebSocketFrame();
            switch (stateEvent.state()){
                //读空闲（服务器端）
                case READER_IDLE:
                    LOGGER.info("【"+ctx.channel().remoteAddress()+"】读空闲（服务器端）");
                    ctx.writeAndFlush(ping);
                    break;
                    //写空闲（客户端）
                case WRITER_IDLE:
                    LOGGER.info("【"+ctx.channel().remoteAddress()+"】写空闲（客户端）");
                    ctx.writeAndFlush(ping);
                    break;
                case ALL_IDLE:
                    LOGGER.info("【"+ctx.channel().remoteAddress()+"】读写空闲");
                    break;
            }
        }
    }

    /**
     * websocket消息处理
     * @param ctx
     * @param msg
     */
    private void doHandlerWebSocketFrame(ChannelHandlerContext ctx, WebSocketFrame msg) {
        //判断msg 是哪一种类型  分别做出不同的反应
        if(msg instanceof CloseWebSocketFrame){
            LOGGER.info("【关闭】");
            handshaker.close(ctx.channel(), (CloseWebSocketFrame) msg);
            return ;
        }
        if(msg instanceof PingWebSocketFrame){
            LOGGER.info("【ping】");
            PongWebSocketFrame pong = new PongWebSocketFrame(msg.content().retain());
            ctx.channel().writeAndFlush(pong);
            return ;
        }
        if(msg instanceof PongWebSocketFrame){
            LOGGER.info("【pong】");
//            PingWebSocketFrame ping = new PingWebSocketFrame(msg.content().retain());
//            ctx.channel().writeAndFlush(ping);
            return ;
        }
        if(!(msg instanceof TextWebSocketFrame)){
            LOGGER.info("【不支持二进制】");
            throw new UnsupportedOperationException("不支持二进制");
        }
        //可以对消息进行处理
        String text = ((TextWebSocketFrame) msg).text();
        JsonObject jo = new JsonParser().parse(text).getAsJsonObject();
        String type = jo.get("type").getAsString();
        if(null == type){
            throw new UnsupportedOperationException("不支持的消息格式");
        }
        if(MessageTypes.USER.getValue().equals(type)) {
            UserMessage m = UserMessage.buildFromJson(text);
            Channel channel = GlobalUserUtil.getChannel(m.getTo().getUsername());
            if(null != channel) {
                channel.writeAndFlush(msg.copy()).addListener(new GenericFutureListener<Future<? super Void>>() {
                    @Override
                    public void operationComplete(Future<? super Void> future) throws Exception {
                        if(future.isSuccess()){
                            m.setSendState(MessageStates.SUCCESS);
                        }else if(future.isCancellable()) {
                            m.setSendState(MessageStates.CANCEL);
                        }else {
                            m.setSendState(MessageStates.FAIL);
                        }

                        handlerMessage(m, ctx);
                    }
                });

            }else {
                //对方没有连接上，则直接保存到数据库
                m.setSendState(MessageStates.NOT_RECEIVE);

                handlerMessage(m, ctx);
            }

        }
        if(MessageTypes.GROUP.getValue().equals(type)) {
            GroupMessage gm = GroupMessage.buildFromJson(text);

            handlerMessage(gm, ctx);
        }

        //群发
        /*for (Channel channel : GlobalUserUtil.channels) {
            channel.writeAndFlush(msg.copy());
        }*/

    }

    private void handlerMessage(Message message, ChannelHandlerContext ctx){
        //返回消息给前端
        ctx.channel().writeAndFlush(new TextWebSocketFrame(GsonUtil.get().toJson(message)));
        //保存消息
        if(null != messageRepository) {
            message.setTime(new Date());
            messageRepository.push(message);
        }
    }


    /**
     * wetsocket第一次连接握手
     * @param ctx
     * @param msg
     */
    private void doHandlerHttpRequest(ChannelHandlerContext ctx, HttpRequest msg) {
        // http 解码失败
        if(!msg.getDecoderResult().isSuccess() || (!"websocket".equals(msg.headers().get("Upgrade")))){
            sendHttpResponse(ctx, (FullHttpRequest) msg,new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,HttpResponseStatus.BAD_REQUEST));
        }

        //可以获取msg的uri来判断
        String uri = msg.getUri();
        String[] uris = uri.split("/");
        //验证权限
        String token = uris[2];
        JwtUtils.CheckResult cr = JwtUtils.validate(token);
        if(!cr.isSuccess()){
            LOGGER.error("权限验证失败！"+cr.getErrCode());
            sendHttpResponse(ctx, (FullHttpRequest) msg,new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,HttpResponseStatus.UNAUTHORIZED));
        }
        String username = cr.getClaims().getSubject();
        if(!uris[1].equals(URI)){
            ctx.close();
        }
        ctx.attr(AttributeKey.valueOf("type")).set(uri);
        //可以通过url获取其他参数
        WebSocketServerHandshakerFactory factory = new WebSocketServerHandshakerFactory(
                "ws://"+msg.headers().get("Host")+"/"+URI+"",null,false
        );
        handshaker = factory.newHandshaker(msg);
        if(handshaker == null){
            WebSocketServerHandshakerFactory.sendUnsupportedWebSocketVersionResponse(ctx.channel());
        }
        //进行连接
        handshaker.handshake(ctx.channel(), (FullHttpRequest) msg);
        //握手成功，可以做其他处理
        //保存用户的channel
        GlobalUserUtil.userChannels.put(username, ctx.channel());
    }

    private static void sendHttpResponse(ChannelHandlerContext ctx, FullHttpRequest req, DefaultFullHttpResponse res) {
        // 返回应答给客户端
        if (res.getStatus().code() != 200) {
            ByteBuf buf = Unpooled.copiedBuffer(res.getStatus().toString(), CharsetUtil.UTF_8);
            res.content().writeBytes(buf);
            buf.release();
        }
        // 如果是非Keep-Alive，关闭连接
        ChannelFuture f = ctx.channel().writeAndFlush(res);
        if (!HttpHeaders.isKeepAlive(req) || res.getStatus().code() != 200) {
            f.addListener(ChannelFutureListener.CLOSE);
        }
    }

    /**
     * 收发消息处理
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        if(msg instanceof HttpRequest){
            doHandlerHttpRequest(ctx,(HttpRequest) msg);
        }else if(msg instanceof WebSocketFrame){
            doHandlerWebSocketFrame(ctx,(WebSocketFrame) msg);
        }
    }
}