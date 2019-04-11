package com.bigbig.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="orders",schema = "dbo")
public class Order {

    private String orderid;
    private Float amount = 0f;
    private Set<OrderItem> items = new HashSet<OrderItem>();

    @Id
    @Column(length = 12)
    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    @Column(nullable = false)
    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    //@OneToMany(mappedBy = "order", orphanRemoval = true, cascade = CascadeType.ALL)
    @OneToMany(cascade = { CascadeType.REFRESH, CascadeType.PERSIST,CascadeType.MERGE, CascadeType.REMOVE },mappedBy ="order") //这里配置关系，并且确定关系维护端和被维护端。mappBy表示关系被维护端，只有关系端有权去更新外键。这里还有注意OneToMany默认的加载方式是赖加载。当看到设置关系中最后一个单词是Many，那么该加载默认为懒加载
    public Set<OrderItem> getItems() {
        return items;
    }

    public void setItems(Set<OrderItem> items) {
        this.items = items;
    }

    /**
     *该方法用于向order中加order项
     **/
    public void addOrderItem(OrderItem orderItem){
        orderItem.setOrder(this);//用关系维护端来维护关系
        this.items.add(orderItem);
    }

 }