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
import lk.ijse.pos.bo.BoFactory;
import lk.ijse.pos.bo.custom.impl.CustomerBoImpl;
import lk.ijse.pos.dto.CustomerDto;
import lk.ijse.pos.util.IdGenerator;
import lk.ijse.pos.view.tm.CustomerTM;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

import static javafx.scene.control.ButtonType.*;

public class CustomerManagerForm {

    public AnchorPane customerContainer;
    public TextField txtName;
    public TextField txtSalary;
    public Button btnSaveUpdate;
    public TextField txtAddress;
    public TextField txtSearch;
    public TableView<CustomerTM> tblCustomer;
    public TableColumn colId;
    public TableColumn colName;
    public TableColumn colSalary;
    public TableColumn colAddress;
    public TableColumn colOption;
    private String id;
    public final CustomerBoImpl customerBo= BoFactory.getInstance().getBo(BoFactory.BoType.CUSTOMER);

    public void initialize(){
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        colOption.setCellValueFactory(new PropertyValueFactory<>("delete"));
        loadAllCustomers("");

        txtSearch.textProperty().addListener((observable, oldValue, newValue) ->{
            loadAllCustomers(newValue);
        });

        tblCustomer.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue != null){
                setData(newValue);
            }
        });
    }

    private void setData(CustomerTM customerTM) {
        btnSaveUpdate.setText("Update Customer");
        id=customerTM.getId();
        txtName.setText(customerTM.getName());
        txtAddress.setText(customerTM.getAddress());
        txtSalary.setText(String.valueOf(customerTM.getSalary()));
    }

    private void loadAllCustomers(String text) {
        ObservableList<CustomerTM> dataLoadArray= FXCollections.observableArrayList();
        try {
            ArrayList<CustomerDto> customerDtoArrayList =customerBo.searchCustomer(text);
            for (CustomerDto customerDto : customerDtoArrayList){
                Button deleteBtn= new Button("Delete");
                CustomerTM customerTM=new CustomerTM(customerDto.getId(), customerDto.getName(),
                        customerDto.getAddress(), customerDto.getSalary(), deleteBtn
                );
                dataLoadArray.add(customerTM);
                deleteBtn.setOnAction(event -> {
                    try {
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                                "Are you sure whether do you want to delete this customer ?",YES,NO);
                        alert.showAndWait();
                        if(alert.getResult()==YES){
                            if(customerBo.deleteCustomer(customerTM.getId())){
                                new Alert(Alert.AlertType.INFORMATION,"Successfully deleted...",
                                        CANCEL).show();
                                loadAllCustomers(txtSearch.getText());
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
            tblCustomer.setItems(dataLoadArray);
        } catch (SQLException | ClassNotFoundException exception) {
            new Alert(Alert.AlertType.WARNING,exception.getMessage(), CANCEL);
        }
    }

    public void backToHomeOnAction(ActionEvent actionEvent) {
        try {
            setUI("Dashboard","Dashboard");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void newCustomerOnAction(ActionEvent actionEvent) {
        btnSaveUpdate.setText("Save Customer");
        clearData();
    }

    private void clearData() {
        txtAddress.clear();
        txtName.clear();
        txtSalary.clear();
    }

    public void saveCustomerOnAction(ActionEvent actionEvent) {
        try {
            if(btnSaveUpdate.getText().equalsIgnoreCase("Save Customer")){
                if(customerBo.saveCustomer(new CustomerDto(
                        IdGenerator.getId(),
                        txtName.getText(),
                        txtAddress.getText(),
                        Double.parseDouble(txtSalary.getText())
                ))){
                    new Alert(Alert.AlertType.INFORMATION,"Successfully saved...",
                            CANCEL).show();
                    loadAllCustomers("");
                    clearData();
                }else{
                    new Alert(Alert.AlertType.WARNING,"Something went wrong, please try again",
                            ButtonType.OK).show();
                }
                btnSaveUpdate.setText("Update Customer");
            }else{
                if(id==null)return;
                if(customerBo.updateCustomer(new CustomerDto(
                        id,txtName.getText(),txtAddress.getText(),
                        Double.parseDouble(txtSalary.getText())
                ))){
                    new Alert(Alert.AlertType.INFORMATION,"Successfully updated...",
                            CANCEL).show();
                    loadAllCustomers("");
                    clearData();
                }else{
                    new Alert(Alert.AlertType.WARNING,"Something went wrong, please try again",
                            ButtonType.OK).show();
                }
            }
            loadAllCustomers("");
        } catch (SQLException | ClassNotFoundException exception) {
            new Alert(Alert.AlertType.INFORMATION,exception.getMessage(), CANCEL).show();
        }
    }

    public void setUI(String fileName,String title) throws IOException {
        Stage stage= (Stage) customerContainer.getScene().getWindow();
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
