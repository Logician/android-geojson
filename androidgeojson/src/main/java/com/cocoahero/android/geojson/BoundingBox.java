package com.cocoahero.android.geojson;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;

public class BoundingBox implements Parcelable {

    // ------------------------------------------------------------------------
    // Instance Variables
    // ------------------------------------------------------------------------

    private final double[] mStorage;

    // ------------------------------------------------------------------------
    // Constructors
    // ------------------------------------------------------------------------

    public BoundingBox(JSONArray coordinates) {
        mStorage = new double[coordinates.length()];
        for (int i = 0; i < coordinates.length(); i++) {
            this.mStorage[i] = coordinates.optDouble(i, 0);
        }
    }

    public BoundingBox(double[] array) {
        mStorage = array;
    }

    private BoundingBox(Parcel parcel) {
        this(parcel.createDoubleArray());
    }

    // ------------------------------------------------------------------------
    // Parcelable Interface
    // ------------------------------------------------------------------------

    public static final Parcelable.Creator<BoundingBox> CREATOR = new Parcelable.Creator<BoundingBox>() {
        @Override
        public BoundingBox createFromParcel(Parcel in) {
            return new BoundingBox(in);
        }

        @Override
        public BoundingBox[] newArray(int size) {
            return new BoundingBox[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDoubleArray(this.mStorage);
    }

    // ------------------------------------------------------------------------
    // Public Methods
    // ------------------------------------------------------------------------

    public JSONArray toJSON() throws JSONException {
        JSONArray coordinates = new JSONArray();

        for (int i = 0; i < mStorage.length; i++) {
            coordinates.put(mStorage[i]);
        }

        return coordinates;
    }

    public double[] toArray() {
        return this.mStorage;
    }

}
