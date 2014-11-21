package com.cocoahero.android.geojson;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;

public class FeatureCollection extends GeoJSONObject {

    // ------------------------------------------------------------------------
    // Constants
    // ------------------------------------------------------------------------

    public static final String JSON_FEATURES = "features";

    private static final String JSON_BOUNDING_BOX = "bbox";

    // ------------------------------------------------------------------------
    // Instance Variables
    // ------------------------------------------------------------------------

    private final List<Feature> mFeatures = new ArrayList<Feature>();

    private BoundingBox mBoundingBox;

    // ------------------------------------------------------------------------
    // Constructors
    // ------------------------------------------------------------------------

    public FeatureCollection() {
        // Default Constructor
    }

    public FeatureCollection(JSONObject json) {
        super(json);

        JSONArray features = json.optJSONArray(JSON_FEATURES);
        if (features != null) {
            for (int i = 0; i < features.length(); i++) {
                JSONObject featureJSON = features.optJSONObject(i);
                if (featureJSON != null) {
                    this.mFeatures.add(new Feature(featureJSON));
                }
            }
        }

        JSONArray bbox = json.optJSONArray(JSON_BOUNDING_BOX);
        if (bbox != null) {
            this.mBoundingBox = new BoundingBox(bbox);
        }
    }

    // ------------------------------------------------------------------------
    // Parcelable Interface
    // ------------------------------------------------------------------------

    public static final Parcelable.Creator<FeatureCollection> CREATOR = new Creator<FeatureCollection>() {
        @Override
        public FeatureCollection createFromParcel(Parcel in) {
            return (FeatureCollection) readParcel(in);
        }

        @Override
        public FeatureCollection[] newArray(int size) {
            return new FeatureCollection[size];
        }
    };

    // ------------------------------------------------------------------------
    // Public Methods
    // ------------------------------------------------------------------------

    public void addFeature(Feature feature) {
        this.mFeatures.add(feature);
    }

    public void removeFeature(Feature feature) {
        this.mFeatures.remove(feature);
    }

    public List<Feature> getFeatures() {
        return this.mFeatures;
    }

    public void setFeatures(List<Feature> features) {
        this.mFeatures.clear();
        if (features != null) {
            this.mFeatures.addAll(features);
        }
    }

    public BoundingBox getBoundingBox() {
        return this.mBoundingBox;
    }

    public void setBoundingBox(BoundingBox bbox) {
        this.mBoundingBox = bbox;
    }

    @Override
    public String getType() {
        return GeoJSON.TYPE_FEATURE_COLLECTION;
    }

    @Override
    public JSONObject toJSON() throws JSONException {
        JSONObject json = super.toJSON();

        JSONArray features = new JSONArray();
        for (Feature feature : this.mFeatures) {
            features.put(feature.toJSON());
        }

        json.put(JSON_FEATURES, features);

        if (this.mBoundingBox != null) {
            json.put(JSON_BOUNDING_BOX, mBoundingBox.toJSON());
        }

        return json;
    }

}
