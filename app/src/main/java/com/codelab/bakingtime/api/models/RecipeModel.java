package com.codelab.bakingtime.api.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;


public class RecipeModel implements Parcelable {

    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("image")
    private String image;
    @SerializedName("ingredients")
    private ArrayList<IngredientsModel> ingredients;
    @SerializedName("steps")
    private ArrayList<StepsModel> steps;

    private boolean isSelected;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public ArrayList<IngredientsModel> getIngredients() {
        return ingredients;
    }

    public ArrayList<StepsModel> getSteps() {
        return steps;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(id);
        dest.writeString(name);
        dest.writeString(image);
        dest.writeList(ingredients);
        dest.writeList(steps);
    }

    protected RecipeModel(Parcel in) {
        id = in.readInt();
        name = in.readString();
        image = in.readString();
        ingredients = in.readArrayList(null);
        steps = in.readArrayList(null);
    }

    public static final Creator<RecipeModel> CREATOR = new Creator<RecipeModel>() {
        @Override
        public RecipeModel createFromParcel(Parcel source) {
            return new RecipeModel(source);
        }

        @Override
        public RecipeModel[] newArray(int size) {
            return new RecipeModel[size];
        }
    };

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
