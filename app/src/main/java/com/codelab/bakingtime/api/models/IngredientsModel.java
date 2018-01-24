package com.codelab.bakingtime.api.models;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class IngredientsModel implements Parcelable {

    @SerializedName("quantity")
    private float quantity;
    @SerializedName("measure")
    private String measure;
    @SerializedName("ingredient")
    private String ingredient;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeFloat(quantity);
        dest.writeString(measure);
        dest.writeString(ingredient);
    }

    protected IngredientsModel(Parcel in) {
        quantity = in.readFloat();
        measure = in.readString();
        ingredient = in.readString();
    }

    public static final Creator<IngredientsModel> CREATOR = new Creator<IngredientsModel>() {
        @Override
        public IngredientsModel createFromParcel(Parcel source) {
            return new IngredientsModel(source);
        }

        @Override
        public IngredientsModel[] newArray(int size) {
            return new IngredientsModel[size];
        }
    };

    public float getQuantity() {
        return quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public String getIngredient() {
        return ingredient;
    }
}