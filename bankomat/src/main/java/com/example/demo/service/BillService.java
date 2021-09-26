package com.example.demo.service;

import com.example.demo.model.Bill;

import java.util.*;

public class BillService {

    private static Map<Integer, Bill> store = new TreeMap(Comparator.reverseOrder());

    public static void receive(final Bill bill) {
        Integer total = bill.getTotal();
        Integer key = bill.getNominal();
        Bill storedBill = store.get(key);
        if (storedBill == null) {
            Bill newBill = new Bill(key, total);
            store.put(key, newBill);
        } else {
            int storedTotal = storedBill.getTotal();
            int newTotal = total + storedTotal;
            Bill newBill = new Bill(key, newTotal);
            store.put(key, newBill);
        }
    }

    public static Map<Integer, Integer> give(final int sum) throws Exception {
        Map<Integer, Integer> onHand = new HashMap<>();
        int restSum = sum;

        for (Map.Entry<Integer, Bill> entry : store.entrySet()) {
            Integer key = entry.getKey();
            Bill bill = entry.getValue();
            if ((key > restSum) || bill.getTotal()==0){
                continue;
            }
            int result = returnTotal(key, bill, restSum);
            if (restSum > 0) {
                onHand.put(bill.getTotal(), result);
                restSum = restSum - result;
            } else if (result == sum) {
                return onHand;
            } else {
                throw new Exception("Запрошенные купюры не соответствуют доступному номиналу");
            }
        }
        return onHand;
    }

    private static int returnTotal(final int nom, final Bill bill, final int sum) {
        int targetBillCount = sum / nom;
        int total = bill.getTotal();
        int sumForGive = nom * total;
        if (total < targetBillCount) {
            int restTotal = targetBillCount - total;
            store.put(nom, new Bill(nom, restTotal));
            return sumForGive;
        } else {
            store.put(nom, new Bill(nom, 0));
            return sumForGive;
        }
    }


    public static void main(String[] args) throws Exception {
        Bill bill1 = new Bill(1000, 5);
        Bill bill2 = new Bill(500, 1);
        receive(bill1);
        receive(bill2);
        Map<Integer, Integer> result = give(5500);
        System.out.println(result.toString());
    }

}
