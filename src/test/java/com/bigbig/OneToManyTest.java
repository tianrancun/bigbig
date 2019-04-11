package com.bigbig;

import com.bigbig.entity.Order;
import com.bigbig.entity.OrderItem;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class OneToManyTest {

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }
    @Test public void addOrder(){
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("test");
        EntityManager em = factory.createEntityManager();
        em.getTransaction().begin(); // start transaction

        Order order = new Order();
        order.setAmount(34f);
        order.setOrderid("00001");

        //order中包含的OrderItem项OrderItem1，OrderItem2
        OrderItem orderItem1 = new OrderItem();
        orderItem1.setProductName("书");
        orderItem1.setSellPrice(22f);
        order.addOrderItem(orderItem1); //add orderitem in order

        OrderItem orderItem2 = new OrderItem();
        orderItem2.setProductName("篮球");
        orderItem2.setSellPrice(100f);
        order.addOrderItem(orderItem2);

        em.persist(order); //persist order object
        em.getTransaction().commit(); //commit transaction
        em.close();
        factory.close();
    }
}