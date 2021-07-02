package ru.geekbrains.client.factory;


import ru.geekbrains.client.service.NetworkService;
import ru.geekbrains.client.service.impl.IONetworkService;

public class Factory {

    public static NetworkService getNetworkService() {
        return IONetworkService.getInstance();
    }

}