package de.tse.example.sparkhibernatebeanrepository.client.gui;

import de.tse.example.sparkhibernatebeanrepository.api.command.CreateDataCommand;
import de.tse.example.sparkhibernatebeanrepository.api.command.DeleteDataCommand;
import de.tse.example.sparkhibernatebeanrepository.api.command.GetDataCommand;
import de.tse.example.sparkhibernatebeanrepository.api.to.CreateInputTO;
import de.tse.example.sparkhibernatebeanrepository.api.to.FilterTO;
import de.tse.example.sparkhibernatebeanrepository.api.to.InputInfoListTO;
import de.tse.example.sparkhibernatebeanrepository.api.to.InputInfoTO;
import de.tse.example.sparkhibernatebeanrepository.client.CommandService;
import de.tse.example.sparkhibernatebeanrepository.client.base.FxmlController;
import de.tse.example.sparkhibernatebeanrepository.client.base.GuiExecutor;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class MainController extends FxmlController implements Initializable {

    private final GuiExecutor guiExecutor;
    private final CommandService commandService;

    @FXML private TextField inputField;
    @FXML private TextField searchField;
    @FXML private TableView<InputInfoTO> table;
    @FXML private TableColumn<InputInfoTO, String> dataColumn;
    @FXML private TableColumn<InputInfoTO, LocalDateTime> createdColumn;

    private final StringProperty inputData = new SimpleStringProperty();
    private final StringProperty searchValue = new SimpleStringProperty();
    private final ObservableList<InputInfoTO> infos = FXCollections.observableArrayList();

    public MainController(final GuiExecutor guiExecutor, final CommandService commandService) {
        this.guiExecutor = guiExecutor;
        this.commandService = commandService;
    }

    @Override protected String getFxml() {
        return "MainController.fxml";
    }

    @Override public void initialize(final URL location, final ResourceBundle resources) {
        inputData.bindBidirectional(inputField.textProperty());
        searchValue.bindBidirectional(searchField.textProperty());

        dataColumn.setCellValueFactory(param -> param.getValue().dataProperty());
        createdColumn.setCellValueFactory(param -> param.getValue().createdProperty());

        table.setItems(infos);

        dataColumn.prefWidthProperty().bind(
                table.widthProperty()
                        .subtract(table.getPrefWidth())
                        .subtract(createdColumn.getPrefWidth())
        );
    }

    @FXML public void createData() {
        final String text = inputData.get();

        final CreateInputTO input = new CreateInputTO();
        input.setInput(text);

        guiExecutor.execute(
                () -> commandService.execute(new CreateDataCommand(input)),
                (InputInfoTO createdInput) -> {
                    infos.add(0, createdInput);
                    inputData.set(null);
                });
    }

    @FXML public void deleteData() {
        final InputInfoTO itemToDelete = table.getSelectionModel().getSelectedItem();

        if (itemToDelete != null) {

            guiExecutor.execute(
                    () -> commandService.execute(new DeleteDataCommand(itemToDelete.getId())),
                    () -> infos.remove(itemToDelete));
        }
    }

    @FXML public void searchData() {
        final String searchText = searchValue.get();

        final FilterTO filter = new FilterTO();
        filter.setSearchValue(searchText);

        guiExecutor.execute(
                () -> commandService.execute(new GetDataCommand(filter)),
                (InputInfoListTO searchResult) -> {
                    infos.clear();
                    infos.addAll(searchResult.getInputInfos());
                }
        );
    }

    @FXML public void clearSearch() {
        searchField.setText("");
        loadAllData();
    }

    public void init() {
        loadAllData();
    }

    private void loadAllData() {
        final FilterTO filter = new FilterTO();

        guiExecutor.execute(
                () -> commandService.execute(new GetDataCommand(filter)),
                (InputInfoListTO inputInfos) -> {
                    infos.clear();
                    infos.addAll(inputInfos.getInputInfos());
                });
    }
}
