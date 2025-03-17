package com.uilover.project2272.Helper;

import android.content.Context;
import android.widget.Toast;


import com.uilover.project2272.Domain.ItemsModel;

import java.util.ArrayList;

public class ManagmentCart {

    private Context context;
    private TinyDB tinyDB;

    public ManagmentCart(Context context) {
        this.context = context;
        this.tinyDB = new TinyDB(context);
    }

    public void insertItem(ItemsModel item) {
        ArrayList<ItemsModel> listItem = getListCart();
        boolean existAlready = false;
        int n = 0;
        for (int y = 0; y < listItem.size(); y++) {
            if (listItem.get(y).getTitle().equals(item.getTitle())) {
                existAlready = true;
                n = y;
                break;
            }
        }
        if (existAlready) {
            listItem.get(n).setNumberinCart(item.getNumberinCart());
        } else {
            listItem.add(item);
        }
        tinyDB.putListObject("CartList", listItem);
        Toast.makeText(context, "Added to your Cart", Toast.LENGTH_SHORT).show();
    }

    public ArrayList<ItemsModel> getListCart() {
        return tinyDB.getListObject("CartList");
    }

    public void minusItem(ArrayList<ItemsModel> listItem, int position, ChangeNumberItemsListener changeNumberItemsListener) {
        if (listItem.get(position).getNumberinCart() == 1) {
            listItem.remove(position);
        } else {
            listItem.get(position).setNumberinCart(listItem.get(position).getNumberinCart() - 1);
        }
        tinyDB.putListObject("CartList", listItem);
        changeNumberItemsListener.changed();
    }

    public void plusItem(ArrayList<ItemsModel> listItem, int position, ChangeNumberItemsListener changeNumberItemsListener) {
        listItem.get(position).setNumberinCart(listItem.get(position).getNumberinCart() + 1);
        tinyDB.putListObject("CartList", listItem);
        changeNumberItemsListener.changed();
    }

    public Double getTotalFee() {
        ArrayList<ItemsModel> listItem2 = getListCart();
        double fee = 0;
        for (int i = 0; i < listItem2.size(); i++) {
            fee = fee + (listItem2.get(i).getPrice() * listItem2.get(i).getNumberinCart());
        }
        return fee;
    }
}
