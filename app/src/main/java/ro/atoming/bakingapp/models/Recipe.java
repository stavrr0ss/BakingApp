package ro.atoming.bakingapp.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bogdan on 5/2/2018.
 */

public class Recipe implements Parcelable{
    //TODO 0 modify the type of Ingredients and RecipeSteps from Lists to Arrays before extracting the JSON
    private int recipeId;
    private String name;
    private List<Ingredient> ingredients;
    private List<RecipeStep> recipeSteps;
    private int servings;

    public Recipe(int recipeId, String name, ArrayList<Ingredient> ingredients, ArrayList<RecipeStep> recipeSteps,
                  int servings) {
        this.recipeId = recipeId;
        this.name = name;
        this.ingredients = ingredients;
        this.recipeSteps = recipeSteps;
        this.servings = servings;
    }

    protected Recipe(Parcel in) {
        recipeId = in.readInt();
        name = in.readString();
        ingredients = in.createTypedArrayList(Ingredient.CREATOR);
        recipeSteps = in.createTypedArrayList(RecipeStep.CREATOR);
        servings = in.readInt();
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public List<RecipeStep> getRecipeSteps() {
        return recipeSteps;
    }

    public void setRecipeSteps(ArrayList<RecipeStep> recipeSteps) {
        this.recipeSteps = recipeSteps;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(recipeId);
        parcel.writeString(name);
        parcel.writeTypedList(ingredients);
        parcel.writeTypedList(recipeSteps);
        parcel.writeInt(servings);
    }
}
