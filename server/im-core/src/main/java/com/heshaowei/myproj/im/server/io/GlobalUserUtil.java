package com.heshaowei.myproj.im.server.io;

import com.google.common.collect.Maps;
import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.Map;

public class GlobalUserUtil {

    //保存全局的  连接上服务器的客户
    public static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor
            .INSTANCE);

    //保存用户对应的channel
    public static Map<String, Channel> userChannels = Maps.newHashMap();

    public static Channel getChannel(String username){
        Channel channel = userChannels.get(username);
        if(null != channel && channels.contains(channel)) {
            return channel;
        }else {
            return null;
        }
    }
}