package ru.geekbrains.server.service;

import ru.geekbrains.domain.Command;

import java.util.ArrayList;

public interface CommandService {

    ArrayList<String> processCommand(Command command);

    String getCommand();

}