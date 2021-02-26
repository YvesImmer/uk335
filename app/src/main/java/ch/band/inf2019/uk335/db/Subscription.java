package ch.band.inf2019.uk335.db;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "subscription_table",
        foreignKeys = {@ForeignKey(
                entity = Categorie.class,
                parentColumns = "id",
                childColumns = "categorieid"//,
                //onDelete = CASCADE

        )})
public class Subscription {
    @PrimaryKey(autoGenerate = true)
    public int subsciriptionid;

    public String title;

    public long dayofnextPayment;

    public int price;

    public int frequency;

    public long categorieid;

    public Subscription(String title, long dayofnextPayment, int price, long categorieid,int frequency) {
        this.title = title;
        this.dayofnextPayment = dayofnextPayment;
        this.price = price;
        this.categorieid = categorieid;
        this.frequency = frequency;
    }

    public Subscription(int Id) {
        title = "";
        dayofnextPayment = 0;
        price = 0;
        categorieid = Id;
        frequency = 0;
    }
}
