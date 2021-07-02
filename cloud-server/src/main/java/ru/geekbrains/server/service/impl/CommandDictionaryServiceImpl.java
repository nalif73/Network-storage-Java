package ru.geekbrains.server.service.impl;

import ru.geekbrains.domain.Command;
import ru.geekbrains.server.factory.Factory;
import ru.geekbrains.server.service.CommandDictionaryService;
import ru.geekbrains.server.service.CommandService;

import java.util.*;

public class CommandDictionaryServiceImpl implements CommandDictionaryService {

    private final Map<String, CommandService> commandDictionary;

    public CommandDictionaryServiceImpl() {
        commandDictionary = Collections.unmodifiableMap(getCommonDictionary());
    }

    private Map<String, CommandService> getCommonDictionary() {
        List<CommandService> commandServices = Factory.getCommandServices();

        Map<String, CommandService> commandDictionary = new HashMap<>();
        for (CommandService commandService : commandServices) {
            commandDictionary.put(commandService.getCommand(), commandService);
        }

        return commandDictionary;
    }

    @Override
    public ArrayList<String> processCommand(Command command) {
        if (commandDictionary.containsKey(command.getCommandName())) {
            return commandDictionary.get(command.getCommandName()).processCommand(command);
        }

        return new ArrayList<String>(Arrays.asList("Error command"));
    }


}