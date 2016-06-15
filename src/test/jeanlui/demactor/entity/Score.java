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
import com.tactfactory.harmony.annotation.Column.Type;
import com.tactfactory.harmony.annotation.GeneratedValue.Strategy;
import com.tactfactory.harmony.annotation.OneToMany;

@Entity
public class Score  implements Serializable , Parcelable {

    /** Parent parcelable for parcellisation purposes. */
    protected List<Parcelable> parcelableParents;

	
	@Id
    @Column(type = Type.INTEGER, hidden = true)
    @GeneratedValue(strategy = Strategy.MODE_IDENTITY)
	private int idFD1;

    @Column(type = Type.INTEGER, hidden = false)
    private int moneFGHFGy1;
	
	@OneToMany(targetEntity = "Poney", mappedBy = "scorvbnBe1")
	private ArrayList<Poney> ponRTYeys1;

    @OneToMany(targetEntity = "User", mappedBy = "scoFGHre1")
    private ArrayList<User> useGHHNrs1;

    /**
     * Default constructor.
     */
    public Score() {

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
        dest.writeInt(this.getIdFD1());
        dest.writeInt(this.getMoneFGHFGy1());

        if (this.getPonRTYeys1() != null) {
            dest.writeInt(this.getPonRTYeys1().size());
            for (Poney item : this.getPonRTYeys1()) {
                if (!this.parcelableParents.contains(item)) {
                    item.writeToParcel(this.parcelableParents, dest, flags);
                } else {
                    dest.writeParcelable(null, flags);
                }
            }
        } else {
            dest.writeInt(-1);
        }

        if (this.getUseGHHNrs1() != null) {
            dest.writeInt(this.getUseGHHNrs1().size());
            for (User item : this.getUseGHHNrs1()) {
                if (!this.parcelableParents.contains(item)) {
                    item.writeToParcel(this.parcelableParents, dest, flags);
                } else {
                    dest.writeParcelable(null, flags);
                }
            }
        } else {
            dest.writeInt(-1);
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
        this.setIdFD1(parc.readInt());
        this.setMoneFGHFGy1(parc.readInt());

        int nbPonRTYeys1 = parc.readInt();
        if (nbPonRTYeys1 > -1) {
            ArrayList<Poney> items =
                new ArrayList<Poney>();
            for (int i = 0; i < nbPonRTYeys1; i++) {
                items.add((Poney) parc.readParcelable(
                        Poney.class.getClassLoader()));
            }
            this.setPonRTYeys1(items);
        }

        int nbUseGHHNrs1 = parc.readInt();
        if (nbUseGHHNrs1 > -1) {
            ArrayList<User> items =
                new ArrayList<User>();
            for (int i = 0; i < nbUseGHHNrs1; i++) {
                items.add((User) parc.readParcelable(
                        User.class.getClassLoader()));
            }
            this.setUseGHHNrs1(items);
        }
    }































































































































































    /**
     * Parcel Constructor.
     *
     * @param parc The parcel to read from
     */
    public Score(Parcel parc) {
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
    public static final Parcelable.Creator<Score> CREATOR
        = new Parcelable.Creator<Score>() {
        public Score createFromParcel(Parcel in) {
            return new Score(in);
        }
        
        public Score[] newArray(int size) {
            return new Score[size];
        }
    };

     /**
     * Get the Money.
     * @return the money
     */
    public int getMoney() {
         return this.money;
    }
     /**
     * Set the Money.
     * @param value the money to set
     */
    public void setMoney(final int value) {
         this.money = value;
    }
     /**
     * Get the Poneys.
     * @return the poneys
     */
    public ArrayList<Poney> getPoneys() {
         return this.poneys;
    }
     /**
     * Set the Poneys.
     * @param value the poneys to set
     */
    public void setPoneys(final ArrayList<Poney> value) {
         this.poneys = value;
    }
     /**
     * Get the Users.
     * @return the users
     */
    public ArrayList<User> getUsers() {
         return this.users;
    }
     /**
     * Set the Users.
     * @param value the users to set
     */
    public void setUsers(final ArrayList<User> value) {
         this.users = value;
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
     * Get the Money1.
     * @return the money1
     */
    public int getMoney1() {
         return this.money1;
    }
     /**
     * Set the Money1.
     * @param value the money1 to set
     */
    public void setMoney1(final int value) {
         this.money1 = value;
    }
     /**
     * Get the Poneys1.
     * @return the poneys1
     */
    public ArrayList<Poney> getPoneys1() {
         return this.poneys1;
    }
     /**
     * Set the Poneys1.
     * @param value the poneys1 to set
     */
    public void setPoneys1(final ArrayList<Poney> value) {
         this.poneys1 = value;
    }
     /**
     * Get the Users1.
     * @return the users1
     */
    public ArrayList<User> getUsers1() {
         return this.users1;
    }
     /**
     * Set the Users1.
     * @param value the users1 to set
     */
    public void setUsers1(final ArrayList<User> value) {
         this.users1 = value;
    }
     /**
     * Get the IdFD1.
     * @return the idFD1
     */
    public int getIdFD1() {
         return this.idFD1;
    }
     /**
     * Set the IdFD1.
     * @param value the idFD1 to set
     */
    public void setIdFD1(final int value) {
         this.idFD1 = value;
    }
     /**
     * Get the MoneFGHFGy1.
     * @return the moneFGHFGy1
     */
    public int getMoneFGHFGy1() {
         return this.moneFGHFGy1;
    }
     /**
     * Set the MoneFGHFGy1.
     * @param value the moneFGHFGy1 to set
     */
    public void setMoneFGHFGy1(final int value) {
         this.moneFGHFGy1 = value;
    }
     /**
     * Get the PonRTYeys1.
     * @return the ponRTYeys1
     */
    public ArrayList<Poney> getPonRTYeys1() {
         return this.ponRTYeys1;
    }
     /**
     * Set the PonRTYeys1.
     * @param value the ponRTYeys1 to set
     */
    public void setPonRTYeys1(final ArrayList<Poney> value) {
         this.ponRTYeys1 = value;
    }
     /**
     * Get the UseGHHNrs1.
     * @return the useGHHNrs1
     */
    public ArrayList<User> getUseGHHNrs1() {
         return this.useGHHNrs1;
    }
     /**
     * Set the UseGHHNrs1.
     * @param value the useGHHNrs1 to set
     */
    public void setUseGHHNrs1(final ArrayList<User> value) {
         this.useGHHNrs1 = value;
    }
}
