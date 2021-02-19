package ch.band.inf2019.uk335.model;

import android.hardware.camera2.CameraConstrainedHighSpeedCaptureSession;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

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
    public long subsciriptionid;

    public String title;

    public long zahltag;

    public int preis;

    public long categorieid;

    public Subscription(String title, long zahltag, int preis, long categorieid) {
        this.title = title;
        this.zahltag = zahltag;
        this.preis = preis;
        this.categorieid = categorieid;
    }
}
