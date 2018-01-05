/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my.vaadin.app;

import com.vaadin.data.Binder;
import com.vaadin.ui.Button;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;
import javafx.scene.input.KeyCode;

/**
 *
 * @author Obuhov.Alexey
 */
public class CustomerForm extends FormLayout {
    
    private TextField firstName = new TextField("First name");
    private TextField lastName = new TextField("Last name");
    private TextField email = new TextField("Email");
    private NativeSelect<CustomerStatus> status = new NativeSelect<>("Status");
    private DateField birthdate = new DateField("Birthdate");
    private Button save = new Button("Save");
    private Button delete = new Button("Delete");
    
    private CustomerService service = CustomerService.getInstance();
    private Customer customer;
    private MyUI myUI;
    private Binder<Customer> binder = new Binder<>(Customer.class);

    public CustomerForm(MyUI myUI) {
        
        this.myUI = myUI;
        setSizeUndefined();
        
        HorizontalLayout buttons = new HorizontalLayout(save, delete);
        addComponents(firstName, lastName, email, status, birthdate, buttons);
        
        status.setItems(CustomerStatus.values());
        save.setStyleName(ValoTheme.BUTTON_PRIMARY);
        save.setClickShortcut(KeyCode.ENTER.ordinal());

        binder.bindInstanceFields(this);
        
        save.addClickListener(e -> save());
        delete.addClickListener(e -> delete());
        
        
    }
    
    public void setCustomer(Customer customer) {
        this.customer = customer;
        binder.setBean(customer);
        
        delete.setVisible(customer.isPersisted());
        setVisible(true);
        firstName.selectAll();
    }
    
    private void delete() {
        service.delete(customer);
        myUI.updateList();
        setVisible(false);
    }
    
    private void save() {
        service.save(customer);
        myUI.updateList();
        setVisible(false);
    }
    
    
}
