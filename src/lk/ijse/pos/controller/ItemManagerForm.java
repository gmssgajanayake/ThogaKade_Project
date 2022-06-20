package lk.ijse.pos.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.pos.dao.DatabaseAccessCode;
import lk.ijse.pos.dto.ItemDTO;
import lk.ijse.pos.util.CodeGenerator;
import lk.ijse.pos.view.tm.ItemTM;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

import static javafx.scene.control.ButtonType.*;
import static javafx.scene.control.ButtonType.YES;

public class ItemManagerForm {

    public TextField txtDescription;
    public TextField txtUnitPrice;
    public TextField txtQuantityOnHand;
    public Button btnSaveUpdate;
    public TextField txtSearch;
    public TableColumn colOption;
    public AnchorPane ItemContainer;
    public TableView<ItemTM> tblItem;
    public TableColumn colCode;
    public TableColumn colDescription;
    public TableColumn colUnitPrice;
    public TableColumn colQtyOnHand;
    private String code;

    public void initialize(){
        colCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colQtyOnHand.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));
        colOption.setCellValueFactory(new PropertyValueFactory<>("btnDelete"));
        loadAllItems("");

        txtSearch.textProperty().addListener((observable, oldValue, newValue) ->{
            loadAllItems(newValue);
        });

        tblItem.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue != null){
                setData(newValue);
            }
        });
    }

    private void setData(ItemTM itemTM) {
        btnSaveUpdate.setText("Update Customer");
        code=itemTM.getCode();
        txtDescription.setText(itemTM.getDescription());
        txtUnitPrice.setText(String.valueOf(itemTM.getUnitPrice()));
        txtQuantityOnHand.setText(String.valueOf(itemTM.getQtyOnHand()));
    }

    public void backToHomeOnAction(ActionEvent actionEvent) {
        try {
            setUI("Dashboard","Dashboard");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void newItemOnAction(ActionEvent actionEvent) {
        btnSaveUpdate.setText("Save Item");
        clearData();
    }

    private void clearData() {
        txtDescription.clear();
        txtQuantityOnHand.clear();
        txtUnitPrice.clear();
    }

    public void saveItemOnAction(ActionEvent actionEvent) {
        try {
            if(btnSaveUpdate.getText().equalsIgnoreCase("Save Item")){
                if(new DatabaseAccessCode().saveItem(new ItemDTO(
                        CodeGenerator.getCode(),
                        txtDescription.getText(),
                        Double.parseDouble(txtUnitPrice.getText()),
                        Double.parseDouble(txtQuantityOnHand.getText())
                ))){
                    new Alert(Alert.AlertType.INFORMATION,"Successfully saved...",
                            CANCEL).show();
                    loadAllItems("");
                    clearData();
                }else{
                    new Alert(Alert.AlertType.WARNING,"Something went wrong, please try again",
                            ButtonType.OK).show();
                }
                btnSaveUpdate.setText("Update Item");
            }else{
                if(code==null)return;
                if(new DatabaseAccessCode().updateItem(new ItemDTO(
                        code,txtDescription.getText(),Double.parseDouble(txtUnitPrice.getText()),
                        Double.parseDouble(txtQuantityOnHand.getText())
                ))){
                    new Alert(Alert.AlertType.INFORMATION,"Successfully updated...",
                            CANCEL).show();
                    loadAllItems("");
                    clearData();
                }else{
                    new Alert(Alert.AlertType.WARNING,"Something went wrong, please try again",
                            ButtonType.OK).show();
                }
            }
            loadAllItems("");
        } catch (SQLException | ClassNotFoundException exception) {
            new Alert(Alert.AlertType.INFORMATION,exception.getMessage(), CANCEL).show();
        }
    }

    private void loadAllItems(String text) {
        ObservableList<ItemTM> dataLoadArray= FXCollections.observableArrayList();
        try {
            ArrayList<ItemDTO> itemDTOArrayList =new DatabaseAccessCode().searchItem(text);
            for (ItemDTO itemDTO : itemDTOArrayList){
                Button deleteBtn= new Button("Delete");
                ItemTM itemTM=new ItemTM(itemDTO.getCode(), itemDTO.getDescription(),
                        itemDTO.getUnitPrice(), itemDTO.getQtyOnHand(),deleteBtn
                );
                dataLoadArray.add(itemTM);
                deleteBtn.setOnAction(event -> {
                    try {
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                                "Are you sure whether do you want to delete this item ?",YES,NO);
                        alert.showAndWait();
                        if(alert.getResult()==YES){
                            if(new DatabaseAccessCode().deleteItem(itemTM.getCode())){
                                new Alert(Alert.AlertType.INFORMATION,"Successfully deleted...",
                                        CANCEL).show();
                                loadAllItems(txtSearch.getText());
                                clearData();
                            }else{
                                new Alert(Alert.AlertType.WARNING,"Something went wrong, please try again",
                                        ButtonType.OK).show();
                            }
                        }
                    } catch (SQLException | ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
            tblItem.setItems(dataLoadArray);
        } catch (SQLException | ClassNotFoundException exception) {
            new Alert(Alert.AlertType.WARNING,exception.getMessage(), CANCEL);
        }
    }

    public void setUI(String fileName,String title) throws IOException {
        Stage stage= (Stage) ItemContainer.getScene().getWindow();
        stage.setScene(
                new Scene(
                        FXMLLoader.load(
                                Objects.requireNonNull(getClass().getResource("../view/" + fileName + ".fxml"))
                        )
                )
        );
        stage.setTitle(title);
        stage.centerOnScreen();
    }
}
