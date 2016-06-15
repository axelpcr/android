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
import com.tactfactory.harmony.annotation.ManyToMany;
import com.tactfactory.harmony.annotation.OneToMany;
import com.tactfactory.harmony.annotation.Column.Type;
import com.tactfactory.harmony.annotation.GeneratedValue.Strategy;
import com.tactfactory.harmony.annotation.OneToOne;

@Entity
public class Jockey  implements Serializable , Parcelable {

    /** Parent parcelable for parcellisation purposes. */
    protected List<Parcelable> parcelableParents;

	
	@Id
    @Column(type = Type.INTEGER, hidden = true)
    @GeneratedValue(strategy = Strategy.MODE_IDENTITY)
	private int fbgDFbdf;
	
	@Column(type = Type.STRING)
	private String dfdfgdDDfgdfg;
	
	@Column(type = Type.STRING)
	private String dfgdfgdfgdfFg;
	
	@ManyToMany(mappedBy = "PoneyDFd1")
	private ArrayList<Poney> dzerzerBCze;

    @OneToOne(targetEntity = "User")
    private User iuytrezBa;

    /**
     * Default constructor.
     */
    public Jockey() {

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
        dest.writeInt(this.getFbgDFbdf());
        if (this.getDfdfgdDDfgdfg() != null) {
            dest.writeInt(1);
            dest.writeString(this.getDfdfgdDDfgdfg());
        } else {
            dest.writeInt(0);
        }
        if (this.getDfgdfgdfgdfFg() != null) {
            dest.writeInt(1);
            dest.writeString(this.getDfgdfgdfgdfFg());
        } else {
            dest.writeInt(0);
        }

        if (this.getDzerzerBCze() != null) {
            dest.writeInt(this.getDzerzerBCze().size());
            for (Poney item : this.getDzerzerBCze()) {
                if (!this.parcelableParents.contains(item)) {
                    item.writeToParcel(this.parcelableParents, dest, flags);
                } else {
                    dest.writeParcelable(null, flags);
                }
            }
        } else {
            dest.writeInt(-1);
        }
        if (this.getIuytrezBa() != null
                    && !this.parcelableParents.contains(this.getIuytrezBa())) {
            this.getIuytrezBa().writeToParcel(this.parcelableParents, dest, flags);
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
        this.setFbgDFbdf(parc.readInt());
        int dfdfgdDDfgdfgBool = parc.readInt();
        if (dfdfgdDDfgdfgBool == 1) {
            this.setDfdfgdDDfgdfg(parc.readString());
        }
        int dfgdfgdfgdfFgBool = parc.readInt();
        if (dfgdfgdfgdfFgBool == 1) {
            this.setDfgdfgdfgdfFg(parc.readString());
        }

        int nbDzerzerBCze = parc.readInt();
        if (nbDzerzerBCze > -1) {
            ArrayList<Poney> items =
                new ArrayList<Poney>();
            for (int i = 0; i < nbDzerzerBCze; i++) {
                items.add((Poney) parc.readParcelable(
                        Poney.class.getClassLoader()));
            }
            this.setDzerzerBCze(items);
        }
        this.setIuytrezBa((User) parc.readParcelable(User.class.getClassLoader()));
    }




























































































































































    /**
     * Parcel Constructor.
     *
     * @param parc The parcel to read from
     */
    public Jockey(Parcel parc) {
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
    public static final Parcelable.Creator<Jockey> CREATOR
        = new Parcelable.Creator<Jockey>() {
        public Jockey createFromParcel(Parcel in) {
            return new Jockey(in);
        }
        
        public Jockey[] newArray(int size) {
            return new Jockey[size];
        }
    };

     /**
     * Get the User.
     * @return the user
     */
    public User getUser() {
         return this.user;
    }
     /**
     * Set the User.
     * @param value the user to set
     */
    public void setUser(final User value) {
         this.user = value;
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
     * Get the User1.
     * @return the user1
     */
    public User getUser1() {
         return this.user1;
    }
     /**
     * Set the User1.
     * @param value the user1 to set
     */
    public void setUser1(final User value) {
         this.user1 = value;
    }
     /**
     * Get the FbgDFbdf.
     * @return the fbgDFbdf
     */
    public int getFbgDFbdf() {
         return this.fbgDFbdf;
    }
     /**
     * Set the FbgDFbdf.
     * @param value the fbgDFbdf to set
     */
    public void setFbgDFbdf(final int value) {
         this.fbgDFbdf = value;
    }
     /**
     * Get the DfdfgdDDfgdfg.
     * @return the dfdfgdDDfgdfg
     */
    public String getDfdfgdDDfgdfg() {
         return this.dfdfgdDDfgdfg;
    }
     /**
     * Set the DfdfgdDDfgdfg.
     * @param value the dfdfgdDDfgdfg to set
     */
    public void setDfdfgdDDfgdfg(final String value) {
         this.dfdfgdDDfgdfg = value;
    }
     /**
     * Get the DfgdfgdfgdfFg.
     * @return the dfgdfgdfgdfFg
     */
    public String getDfgdfgdfgdfFg() {
         return this.dfgdfgdfgdfFg;
    }
     /**
     * Set the DfgdfgdfgdfFg.
     * @param value the dfgdfgdfgdfFg to set
     */
    public void setDfgdfgdfgdfFg(final String value) {
         this.dfgdfgdfgdfFg = value;
    }
     /**
     * Get the DzerzerBCze.
     * @return the dzerzerBCze
     */
    public ArrayList<Poney> getDzerzerBCze() {
         return this.dzerzerBCze;
    }
     /**
     * Set the DzerzerBCze.
     * @param value the dzerzerBCze to set
     */
    public void setDzerzerBCze(final ArrayList<Poney> value) {
         this.dzerzerBCze = value;
    }
     /**
     * Get the IuytrezBa.
     * @return the iuytrezBa
     */
    public User getIuytrezBa() {
         return this.iuytrezBa;
    }
     /**
     * Set the IuytrezBa.
     * @param value the iuytrezBa to set
     */
    public void setIuytrezBa(final User value) {
         this.iuytrezBa = value;
    }
}
