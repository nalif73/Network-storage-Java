package ru.geekbrains.server;

import ru.geekbrains.server.factory.Factory;
//import ru.geekbrains.server.sqlite.JDBC;

public class Main {

    public static void main(String[] args) {

        Factory.getServerService().startServer();
    }

}
