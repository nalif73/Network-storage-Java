package ru.geekbrains.server.service.impl.command;

import ru.geekbrains.domain.Command;
import ru.geekbrains.server.service.CommandService;

import java.io.File;
import java.util.ArrayList;

public class ViewFilesInDirCommand implements CommandService {

    @Override
    public ArrayList<String> processCommand(Command command) {
        final int requirementCountCommandArgs = 1;

        if (command.getArgs().length != requirementCountCommandArgs) {
            throw new IllegalArgumentException("Command \"" + getCommand() + "\" is not correct");
        }

        return process(command.getArgs()[0]);
    }

    private ArrayList<String> process(String dirPath) {
       ArrayList<String> listOfDir = new ArrayList<>();

        File directory = new File(dirPath);

        if (!directory.exists()) {
            listOfDir.add("Directory is not exists");
            return listOfDir;
        }


        for (File childFile : directory.listFiles()) {
//            String typeFile = getTypeFile(childFile);
//            builder.append(childFile.getName()).append(" | ").append(typeFile).append(System.lineSeparator());
            listOfDir.add(childFile.getName() + " | " + getTypeFile(childFile));


        }

        return listOfDir;

    }

    private String getTypeFile(File childFile) {
        return childFile.isDirectory() ? "DIR" : "FILE";
    }

    @Override
    public String getCommand() {
        return "ls";
    }
}