package ru.geekbrains.client.service;

import ru.geekbrains.domain.Command;

import java.util.ArrayList;

public interface NetworkService {

    void sendCommand(Command command);

    ArrayList<String> readCommandResult();

    void closeConnection();

}