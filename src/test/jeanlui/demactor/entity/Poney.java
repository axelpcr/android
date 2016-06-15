package com.tactfactory.demact.entity;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.List;
import java.io.Serializable;
import java.util.ArrayList;

import com.tactfactory.harmony.annotation.Column;
import com.tactfactory.harmony.annotation.Column.Type;
import com.tactfactory.harmony.annotation.Entity;
import com.tactfactory.harmony.annotation.GeneratedValue;
import com.tactfactory.harmony.annotation.GeneratedValue.Strategy;
import com.tactfactory.harmony.annotation.Id;
import com.tactfactory.harmony.annotation.ManyToMany;
import com.tactfactory.harmony.annotation.ManyToOne;

@Entity
public class Poney  implements Serializable , Parcelable {

    /** Parent parcelable for parcellisation purposes. */
    protected List<Parcelable> parcelableParents;

	
	@Id
    @Column(type = Type.INTEGER, hidden = true)
    @GeneratedValue(strategy = Strategy.MODE_IDENTITY)
	private int idlioEm1;
	
	@Column(type = Type.STRING)
	private String iomAiome1;
	
	@ManyToMany(mappedBy = "JockeyDF1")
	private ArrayList<Jockey> jockgFhjeys1;

    @ManyToOne(inversedBy = "ponRTYeys1")
    private Score scorvbnBe1;

    /**
     * Default constructor.
     */
    public Poney() {

    }

    /**
     * Get the Id.
     * @return the id
     */
    public int getId() {
         return this.id;
    }


    /**
     * Set the Id.
     * @param value the id to set
     */
    public void setId(final int value) {
         this.id = value;
    }

    /**
     * Get the Name.
     * @return the name
     */
    public String getName() {
         return this.name;
    }


    /**
     * Set the Name.
     * @param value the name to set
     */
    public void setName(final String value) {
         this.name = value;
    }

    /**
     * Get the Jockeys.
     * @return the jockeys
     */
    public ArrayList<Jockey> getJockeys() {
         return this.jockeys;
    }


    /**
     * Set the Jockeys.
     * @param value the jockeys to set
     */
    public void setJockeys(final ArrayList<Jockey> value) {
         this.jockeys = value;
    }
    /**
     * This stub of code is regenerated. DO NOT MODIFY.
     * 
     * @param dest Destination parcel
     * @param flags flags
     */
    public void writeToParcelRegen(Parcel dest, int flags) {
        if (this.parcelableParents == null) {
            this.parcelableParents = new ArrayList<Parcelable>();
        }
        if (!this.parcelableParents.contains(this)) {
            this.parcelableParents.add(this);
        }
        dest.writeInt(this.getIdlioEm1());
        if (this.getIomAiome1() != null) {
            dest.writeInt(1);
            dest.writeString(this.getIomAiome1());
        } else {
            dest.writeInt(0);
        }

        if (this.getJockgFhjeys1() != null) {
            dest.writeInt(this.getJockgFhjeys1().size());
            for (Jockey item : this.getJockgFhjeys1()) {
                if (!this.parcelableParents.contains(item)) {
                    item.writeToParcel(this.parcelableParents, dest, flags);
                } else {
                    dest.writeParcelable(null, flags);
                }
            }
        } else {
            dest.writeInt(-1);
        }
        if (this.getScorvbnBe1() != null
                    && !this.parcelableParents.contains(this.getScorvbnBe1())) {
            this.getScorvbnBe1().writeToParcel(this.parcelableParents, dest, flags);
        } else {
            dest.writeParcelable(null, flags);
        }
    }
    /**
     * Regenerated Parcel Constructor. 
     *
     * This stub of code is regenerated. DO NOT MODIFY THIS METHOD.
     *
     * @param parc The parcel to read from
     */
    public void readFromParcel(Parcel parc) {
        this.setIdlioEm1(parc.readInt());
        int iomAiome1Bool = parc.readInt();
        if (iomAiome1Bool == 1) {
            this.setIomAiome1(parc.readString());
        }

        int nbJockgFhjeys1 = parc.readInt();
        if (nbJockgFhjeys1 > -1) {
            ArrayList<Jockey> items =
                new ArrayList<Jockey>();
            for (int i = 0; i < nbJockgFhjeys1; i++) {
                items.add((Jockey) parc.readParcelable(
                        Jockey.class.getClassLoader()));
            }
            this.setJockgFhjeys1(items);
        }
        this.setScorvbnBe1((Score) parc.readParcelable(Score.class.getClassLoader()));
    }





























































































































































    /**
     * Parcel Constructor.
     *
     * @param parc The parcel to read from
     */
    public Poney(Parcel parc) {
        // You can chose not to use harmony's generated parcel.
        // To do this, remove this line.
        this.readFromParcel(parc);

        // You can  implement your own parcel mechanics here.

    }

    /* This method is not regenerated. You can implement your own parcel mechanics here. */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        // You can chose not to use harmony's generated parcel.
        // To do this, remove this line.
        this.writeToParcelRegen(dest, flags);
        // You can  implement your own parcel mechanics here.
    }

    /**
     * Use this method to write this entity to a parcel from another entity.
     * (Useful for relations)
     *
     * @param parent The entity being parcelled that need to parcel this one
     * @param dest The destination parcel
     * @param flags The flags
     */
    public synchronized void writeToParcel(List<Parcelable> parents, Parcel dest, int flags) {
        this.parcelableParents = new ArrayList<Parcelable>(parents);
        dest.writeParcelable(this, flags);
        this.parcelableParents = null;
    }

    @Override
    public int describeContents() {
        // This should return 0 
        // or CONTENTS_FILE_DESCRIPTOR if your entity is a FileDescriptor.
        return 0;
    }

    /**
     * Parcelable creator.
     */
    public static final Parcelable.Creator<Poney> CREATOR
        = new Parcelable.Creator<Poney>() {
        public Poney createFromParcel(Parcel in) {
            return new Poney(in);
        }
        
        public Poney[] newArray(int size) {
            return new Poney[size];
        }
    };

     /**
     * Get the Score.
     * @return the score
     */
    public Score getScore() {
         return this.score;
    }
     /**
     * Set the Score.
     * @param value the score to set
     */
    public void setScore(final Score value) {
         this.score = value;
    }
     /**
     * Get the Id1.
     * @return the id1
     */
    public int getId1() {
         return this.id1;
    }
     /**
     * Set the Id1.
     * @param value the id1 to set
     */
    public void setId1(final int value) {
         this.id1 = value;
    }
     /**
     * Get the Name1.
     * @return the name1
     */
    public String getName1() {
         return this.name1;
    }
     /**
     * Set the Name1.
     * @param value the name1 to set
     */
    public void setName1(final String value) {
         this.name1 = value;
    }
     /**
     * Get the Jockeys1.
     * @return the jockeys1
     */
    public ArrayList<Jockey> getJockeys1() {
         return this.jockeys1;
    }
     /**
     * Set the Jockeys1.
     * @param value the jockeys1 to set
     */
    public void setJockeys1(final ArrayList<Jockey> value) {
         this.jockeys1 = value;
    }
     /**
     * Get the Score1.
     * @return the score1
     */
    public Score getScore1() {
         return this.score1;
    }
     /**
     * Set the Score1.
     * @param value the score1 to set
     */
    public void setScore1(final Score value) {
         this.score1 = value;
    }
     /**
     * Get the IdlioEm1.
     * @return the idlioEm1
     */
    public int getIdlioEm1() {
         return this.idlioEm1;
    }
     /**
     * Set the IdlioEm1.
     * @param value the idlioEm1 to set
     */
    public void setIdlioEm1(final int value) {
         this.idlioEm1 = value;
    }
     /**
     * Get the IomAiome1.
     * @return the iomAiome1
     */
    public String getIomAiome1() {
         return this.iomAiome1;
    }
     /**
     * Set the IomAiome1.
     * @param value the iomAiome1 to set
     */
    public void setIomAiome1(final String value) {
         this.iomAiome1 = value;
    }
     /**
     * Get the JockgFhjeys1.
     * @return the jockgFhjeys1
     */
    public ArrayList<Jockey> getJockgFhjeys1() {
         return this.jockgFhjeys1;
    }
     /**
     * Set the JockgFhjeys1.
     * @param value the jockgFhjeys1 to set
     */
    public void setJockgFhjeys1(final ArrayList<Jockey> value) {
         this.jockgFhjeys1 = value;
    }
     /**
     * Get the ScorvbnBe1.
     * @return the scorvbnBe1
     */
    public Score getScorvbnBe1() {
         return this.scorvbnBe1;
    }
     /**
     * Set the ScorvbnBe1.
     * @param value the scorvbnBe1 to set
     */
    public void setScorvbnBe1(final Score value) {
         this.scorvbnBe1 = value;
    }
}
