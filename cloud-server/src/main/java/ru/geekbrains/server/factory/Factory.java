package ru.geekbrains.server.factory;


import ru.geekbrains.server.service.CommandDictionaryService;
import ru.geekbrains.server.service.CommandService;
import ru.geekbrains.server.service.ServerService;
import ru.geekbrains.server.service.impl.CommandDictionaryServiceImpl;
import ru.geekbrains.server.service.impl.NettyServerService;
import ru.geekbrains.server.service.impl.command.ViewFilesInDirCommand;

import java.util.Arrays;
import java.util.List;

public class Factory {

    public static ServerService getServerService() {
        return new NettyServerService();
    }

    public static CommandDictionaryService getCommandDirectoryService() {
        return new CommandDictionaryServiceImpl();
    }

    public static List<CommandService> getCommandServices() {
        return Arrays.asList( new ViewFilesInDirCommand());
    }


}
