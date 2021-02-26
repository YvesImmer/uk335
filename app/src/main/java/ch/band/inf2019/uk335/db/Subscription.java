package ch.band.inf2019.uk335.db;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

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

    public int preis;

    public long categorieid;

    public Subscription(String title, long dayofnextPayment, int preis, long categorieid) {
        this.title = title;
        this.dayofnextPayment = dayofnextPayment;
        this.preis = preis;
        this.categorieid = categorieid;
    }

    public Subscription() {
        title = "";
        dayofnextPayment = 0;
        preis = 0;
        categorieid = 0;
    }
}
