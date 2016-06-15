package com.tactfactory.demact.entity;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.List;
import java.io.Serializable;
import java.util.ArrayList;

import com.tactfactory.harmony.annotation.Column;
import com.tactfactory.harmony.annotation.Entity;
import com.tactfactory.harmony.annotation.GeneratedValue;
import com.tactfactory.harmony.annotation.Id;
import com.tactfactory.harmony.annotation.OneToOne;
import com.tactfactory.harmony.annotation.Column.Type;
import com.tactfactory.harmony.annotation.GeneratedValue.Strategy;
import com.tactfactory.harmony.annotation.ManyToOne;

@Entity
public class User  implements Serializable , Parcelable {

    /** Parent parcelable for parcellisation purposes. */
    protected List<Parcelable> parcelableParents;

	
	@Id
    @Column(type = Type.INTEGER, hidden = true)
    @GeneratedValue(strategy = Strategy.MODE_IDENTITY)
	private int id1HNY;
	
	@Column(type = Type.STRING)
	private String naFGHme1;
	
	@Column(type = Type.STRING)
	private String surnFGHame1;
	
	@ManyToOne(inversedBy = "useGHHNrs1")
	private Score scoFGHre1;

    @OneToOne(targetEntity = "Jockey")
    private Jockey jocFGHkey1;

    /**
     * Default constructor.
     */
    public User() {

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
     * Get the Surname.
     * @return the surname
     */
    public String getSurname() {
         return this.surname;
    }


    /**
     * Set the Surname.
     * @param value the surname to set
     */
    public void setSurname(final String value) {
         this.surname = value;
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
        dest.writeInt(this.getId1HNY());
        if (this.getNaFGHme1() != null) {
            dest.writeInt(1);
            dest.writeString(this.getNaFGHme1());
        } else {
            dest.writeInt(0);
        }
        if (this.getSurnFGHame1() != null) {
            dest.writeInt(1);
            dest.writeString(this.getSurnFGHame1());
        } else {
            dest.writeInt(0);
        }
        if (this.getScoFGHre1() != null
                    && !this.parcelableParents.contains(this.getScoFGHre1())) {
            this.getScoFGHre1().writeToParcel(this.parcelableParents, dest, flags);
        } else {
            dest.writeParcelable(null, flags);
        }
        if (this.getJocFGHkey1() != null
                    && !this.parcelableParents.contains(this.getJocFGHkey1())) {
            this.getJocFGHkey1().writeToParcel(this.parcelableParents, dest, flags);
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
        this.setId1HNY(parc.readInt());
        int naFGHme1Bool = parc.readInt();
        if (naFGHme1Bool == 1) {
            this.setNaFGHme1(parc.readString());
        }
        int surnFGHame1Bool = parc.readInt();
        if (surnFGHame1Bool == 1) {
            this.setSurnFGHame1(parc.readString());
        }
        this.setScoFGHre1((Score) parc.readParcelable(Score.class.getClassLoader()));
        this.setJocFGHkey1((Jockey) parc.readParcelable(Jockey.class.getClassLoader()));
    }






























































































































































    /**
     * Parcel Constructor.
     *
     * @param parc The parcel to read from
     */
    public User(Parcel parc) {
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
    public static final Parcelable.Creator<User> CREATOR
        = new Parcelable.Creator<User>() {
        public User createFromParcel(Parcel in) {
            return new User(in);
        }
        
        public User[] newArray(int size) {
            return new User[size];
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
     * Get the Jockey.
     * @return the jockey
     */
    public Jockey getJockey() {
         return this.jockey;
    }
     /**
     * Set the Jockey.
     * @param value the jockey to set
     */
    public void setJockey(final Jockey value) {
         this.jockey = value;
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
     * Get the Surname1.
     * @return the surname1
     */
    public String getSurname1() {
         return this.surname1;
    }
     /**
     * Set the Surname1.
     * @param value the surname1 to set
     */
    public void setSurname1(final String value) {
         this.surname1 = value;
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
     * Get the Jockey1.
     * @return the jockey1
     */
    public Jockey getJockey1() {
         return this.jockey1;
    }
     /**
     * Set the Jockey1.
     * @param value the jockey1 to set
     */
    public void setJockey1(final Jockey value) {
         this.jockey1 = value;
    }
     /**
     * Get the Id1HNY.
     * @return the id1HNY
     */
    public int getId1HNY() {
         return this.id1HNY;
    }
     /**
     * Set the Id1HNY.
     * @param value the id1HNY to set
     */
    public void setId1HNY(final int value) {
         this.id1HNY = value;
    }
     /**
     * Get the NaFGHme1.
     * @return the naFGHme1
     */
    public String getNaFGHme1() {
         return this.naFGHme1;
    }
     /**
     * Set the NaFGHme1.
     * @param value the naFGHme1 to set
     */
    public void setNaFGHme1(final String value) {
         this.naFGHme1 = value;
    }
     /**
     * Get the SurnFGHame1.
     * @return the surnFGHame1
     */
    public String getSurnFGHame1() {
         return this.surnFGHame1;
    }
     /**
     * Set the SurnFGHame1.
     * @param value the surnFGHame1 to set
     */
    public void setSurnFGHame1(final String value) {
         this.surnFGHame1 = value;
    }
     /**
     * Get the ScoFGHre1.
     * @return the scoFGHre1
     */
    public Score getScoFGHre1() {
         return this.scoFGHre1;
    }
     /**
     * Set the ScoFGHre1.
     * @param value the scoFGHre1 to set
     */
    public void setScoFGHre1(final Score value) {
         this.scoFGHre1 = value;
    }
     /**
     * Get the JocFGHkey1.
     * @return the jocFGHkey1
     */
    public Jockey getJocFGHkey1() {
         return this.jocFGHkey1;
    }
     /**
     * Set the JocFGHkey1.
     * @param value the jocFGHkey1 to set
     */
    public void setJocFGHkey1(final Jockey value) {
         this.jocFGHkey1 = value;
    }
}
