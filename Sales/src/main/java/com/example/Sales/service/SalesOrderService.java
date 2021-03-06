package com.example.Sales.service;

import com.example.Sales.exception.OrdersNotFoundException;
import com.example.Sales.model.CreateOrder;
import com.example.Sales.model.OrderLineItem;
import com.example.Sales.model.SalesOrder;
import com.example.Sales.repository.OrderLineItemRepository;
import com.example.Sales.repository.SalesOrderRepository;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.persistence.AssociationOverride;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class SalesOrderService {

    @Autowired
    private SalesOrderRepository salesOrderRepository;

    @Autowired
    private OrderLineItemService orderLineItemService;

//    @Autowired
//    private OrderLineItemRepository orderLineItemRepository;

//    @Autowired
//    private OrderLineItem orderLineItem;
//
//    @Autowired
//    private ItemServiceProxy itemServiceProxy;


//    Updating Create Order [i/p = desc, date, email,item list] [o/p = orderId]
//    public Long addNewOrder(String description, Date date, String email, List<String> itemList)
    public Long addNewOrder(CreateOrder order, Double price){
        SalesOrder salesOrder = new SalesOrder(order.getDate(),order.getEmail(),order.getDescription(),price);
        System.out.println(salesOrder);
        SalesOrder Order=salesOrderRepository.save(salesOrder);
        //OrderLineItem orderLineItem=new OrderLineItem(order.getItems().get(0),salesOrder.getId());
        orderLineItemService.addItems(order.getItems(),salesOrder.getId());
        return salesOrder.getId();
    }

    //2. Get Order by emailId [i/p= emailId][o/p = orders in SalesOrder, OrderLineItem by OrderId]

    public List<SalesOrder> getOrderByEmail(String email)
    {
        List<SalesOrder> salesOrder = null;
        salesOrder = this.salesOrderRepository.findByEmail(email);
        if (salesOrder.isEmpty()){
            throw new OrdersNotFoundException("Orders for "+ email + "not found");
        }
        return salesOrder;
    }
//    public List<SalesOrder> getOrderByEmail(String email){
//        List<SalesOrder> foundOrders = salesOrderRepository.findByEmail(email);
//        if (!foundOrders.isEmpty()){
//            return foundOrders;
//        }
//        else{
//            System.out.println("Matching orders not found");
//            return null;
//        }
//    }

}
