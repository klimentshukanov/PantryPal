package mk.ukim.finki.pantrypal.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class FoodItem {

    String itemName;
    LocalDate expirationDate;

    String uid;

    public FoodItem() {
    }

    public FoodItem(String itemName, LocalDate expirationDate, String uid) {
        this.itemName = itemName;
        this.expirationDate = expirationDate;
        this.uid = uid;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
