package ru.geekbrains.server.core.handler;


import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import ru.geekbrains.domain.Command;
import ru.geekbrains.server.factory.Factory;
import ru.geekbrains.server.service.CommandDictionaryService;

import java.util.ArrayList;

public class CommandInboundHandler extends SimpleChannelInboundHandler<Command> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Command command) {
        CommandDictionaryService dictionaryService = Factory.getCommandDirectoryService();
        System.out.println(command);
        ArrayList<String> commandResult = dictionaryService.processCommand(command);

        ctx.writeAndFlush(commandResult);
    }

}
