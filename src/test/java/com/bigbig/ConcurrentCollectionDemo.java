package com.bigbig;


import com.sun.org.apache.xalan.internal.xsltc.runtime.Hashtable;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentCollectionDemo {
    public static void main(String[] args) {
        Collections.synchronizedMap(new HashMap<>());
        Collections.synchronizedList(new LinkedList<>());
        ConcurrentHashMap map = new ConcurrentHashMap();
        Hashtable t= new Hashtable();
    }
}