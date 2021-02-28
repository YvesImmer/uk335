package ch.band.inf2019.uk335.db;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "subscription_table",
        foreignKeys = {@ForeignKey(
                entity = Categorie.class,
                parentColumns = "id",
                childColumns = "categorieid",
                onDelete = CASCADE

        )})
public class Subscription {
    @PrimaryKey(autoGenerate = true)
    public int subsciriptionid;

    public String title;

    public long dayofnextPayment;

    public int price;
    /**
     * 0 for never
     * 1 for mothly
     * 2 for yearly
     */
    public int frequency;

    public int categorieid;

    public Subscription(String title, long dayofnextPayment, int price, int categorieid,int frequency) {
        this.title = title;
        this.dayofnextPayment = dayofnextPayment;
        this.price = price;
        this.categorieid = categorieid;
        this.frequency = frequency;
    }

    public Subscription(int categorieId) {
        this("",0,0,categorieId,0);

    }

    /**
     * Updates the Day of next Payment so its in the future if the subsciption is in the Futur
     * @return a Subscription with a updated Day of next Payment
     */
    public Subscription updateDayOfNextPayment(){
        if(frequency<0){
            long today = new Date().getTime();
            Calendar c = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
            c.setTimeInMillis(dayofnextPayment);
            while (dayofnextPayment < today){
                switch (frequency){
                    case 1:c.add(Calendar.MONTH, 1);break;
                    case 2:c.add(Calendar.YEAR,1);break;
                    default:return this;
                }

                dayofnextPayment = c.getTimeInMillis();
            }
        }

        return this;
    }
}
