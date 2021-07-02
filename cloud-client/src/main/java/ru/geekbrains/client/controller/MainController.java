package ru.geekbrains.client.controller;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import ru.geekbrains.client.factory.Factory;
import ru.geekbrains.client.service.NetworkService;
import ru.geekbrains.domain.Command;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import ru.geekbrains.domain.FileProperties;

import java.io.IOException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class MainController implements Initializable {
    @FXML
    TableView<FileProperties> localFilesTable, cloudFilesTable;
    @FXML
    ComboBox<String> localDisksBox;
    @FXML
    TextField localPathField, cloudPathField;
    @FXML
    Button localBtnPathUp, cloudBtnPathUp;

    public NetworkService networkService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        networkService = Factory.getNetworkService();

        CreateTableColumn(localFilesTable);
        updateList(Paths.get("."), localFilesTable);

        CreateTableColumn(cloudFilesTable);
        updateList(Paths.get("Cloud"), cloudFilesTable);

        localDisksBox.getItems().clear();
        for (Path p : FileSystems.getDefault().getRootDirectories()) {
            localDisksBox.getItems().add(p.toString());
        }
        localDisksBox.getSelectionModel().select(0);

  //      createCommandResultHandler();
    }

    private void updateListCloud(Path cloud, TableView<FileProperties> cloudFilesTable) {


    }

    public void FilesTableClick(MouseEvent mouseEvent) {
        TableView<FileProperties> element = (TableView<FileProperties>) mouseEvent.getSource();
        Path path;
        if (mouseEvent.getClickCount() == 2) {
            if (element.equals(localFilesTable)) {
               path  = Paths.get(localPathField.getText()).resolve(element.getSelectionModel().getSelectedItem().getFilename());
            } else {
                 path = Paths.get(cloudPathField.getText()).resolve(element.getSelectionModel().getSelectedItem().getFilename());
            }
            if (Files.isDirectory(path)) {
                updateList(path, element);
            }
        }
    }

    private void CreateTableColumn(TableView<FileProperties> FilesTable) {
        TableColumn<FileProperties, String> localFileTypeColumn = new TableColumn<>();
        localFileTypeColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getType().getName()));
        localFileTypeColumn.setPrefWidth(24);

        TableColumn<FileProperties, String> localFilenameColumn = new TableColumn<>("Имя");
        localFilenameColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getFilename()));
        localFilenameColumn.setPrefWidth(240);

        TableColumn<FileProperties, Long> localFileSizeColumn = new TableColumn<>("Размер");
        localFileSizeColumn.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getSize()));
        localFileSizeColumn.setCellFactory(column -> {
            return new TableCell<FileProperties, Long>() {
                @Override
                protected void updateItem(Long item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null || empty) {
                        setText(null);
                        setStyle("");
                    } else {
                        String text = String.format("%,d байт", item);
                        if (item == -1L) {
                            text = "[DIR]";
                        }
                        setText(text);
                    }
                }
            };
        });
        localFileSizeColumn.setPrefWidth(100);

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        TableColumn<FileProperties, String> localFileDateColumn = new TableColumn<>("Дата изменения");
        localFileDateColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getLastModified().format(dtf)));
        localFileDateColumn.setPrefWidth(120);

        FilesTable.getColumns().addAll(localFileTypeColumn, localFilenameColumn, localFileSizeColumn, localFileDateColumn);
        FilesTable.getSortOrder().add(localFileTypeColumn);
    }

    private void updateList(Path path, TableView<FileProperties> FilesTable) {
        try {
            if (FilesTable.equals(localFilesTable)) {
                localPathField.setText(path.normalize().toAbsolutePath().toString());
            } else
                cloudPathField.setText(path.normalize().toAbsolutePath().toString());

            FilesTable.getItems().clear();
            FilesTable.getItems().addAll(Files.list(path).map(FileProperties::new).collect(Collectors.toList()));
            FilesTable.sort();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Не удалось обновить список файлов", ButtonType.OK);
            alert.showAndWait();
        }
    }


    public void shutdown() {
        networkService.closeConnection();
    }


    public void btnPathUpAction(ActionEvent actionEvent) {
        Button element = (Button) actionEvent.getSource();
        Path upperPath;
        if (element.equals(localBtnPathUp)) {
            upperPath = Paths.get(localPathField.getText()).getParent();
            if (upperPath != null) {
                updateList(upperPath, localFilesTable);
            }
        } else {
            upperPath = Paths.get(cloudPathField.getText()).getParent();
            if (upperPath != null && !Paths.get(cloudPathField.getText()).getFileName().toString().equals("Cloud")) {
                updateList(upperPath, cloudFilesTable);
            }
        }
    }


    public void selectDiskActionLocal(ActionEvent actionEvent) {
        //  ComboBox<String> element = (ComboBox<String>) actionEvent.getSource();
        updateList(Paths.get(localDisksBox.getSelectionModel().getSelectedItem()), localFilesTable);
    }

    private void createCommandResultHandler() {
        new Thread(() -> {
            while (true) {

//                ArrayList<String> resultCommand = networkService.readCommandResult();
//               Platform.runLater(() -> commandResultClient.getItems().clear());
//                for (String item : resultCommand) {
//                    Platform.runLater(() -> commandResultClient.getItems().add(commandResultClient.getItems().size(), item));
//                    //Platform.runLater(() -> commandResultTextArea.appendText(resultCommand + System.lineSeparator()));
//                }
            }
        }).start();
    }

    private void sendCommandUpload(ActionEvent actionEvent) {
//        String[] textCommand = commandTextField.getText().trim().split("\\s");
//        if (textCommand.length > 1) {
//            String[] commandArgs = Arrays.copyOfRange(textCommand, 1, textCommand.length);
//            networkService.sendCommand(new Command(textCommand[0], commandArgs));
//            commandTextField.clear();
//        }
    }

    private void sendCommandDownload(ActionEvent actionEvent) {
    }


}