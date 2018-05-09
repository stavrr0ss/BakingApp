package ro.atoming.bakingapp.utils;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import ro.atoming.bakingapp.models.Ingredient;
import ro.atoming.bakingapp.models.Recipe;
import ro.atoming.bakingapp.models.RecipeStep;

/**
 * Created by Bogdan on 5/2/2018.
 */

public class Networking {

    public static final String LOG_TAG = Networking.class.getSimpleName();
    public static final String RECIPE_ID = "id";
    public static final String RECIPE_NAME = "name";
    public static final String RECIPE_INGREDIENTS = "ingredients";
    public static final String RECIPE_STEPS = "steps";
    public static final String RECIPE_SERVINGS = "servings";
    public static final String INGREDIENT_QUANTITY = "quantity";
    public static final String INGREDIENT_MEASURE = "measure";
    public static final String INGREDIENT_NAME = "ingredient";
    public static final String STEP_ID = "id";
    public static final String STEP_SHORT_DESCRIPTION = "shortDescription";
    public static final String STEP_DESCRIPTION = "description";
    public static final String STEP_VIDEO = "videoURL";

    public static List<Recipe> searchRecipes(String requestUrl) {
        URL url = buildUrl(requestUrl);
        String jsonResponse = null;
        try{
            jsonResponse = makeHttpRequest(url);
        }catch (IOException e){
            Log.e(LOG_TAG,"Problem with HTTP response !",e);
        }
        List<Recipe> recipeList = extractJsonResponse(jsonResponse);
        return recipeList;
    }

    private static URL buildUrl(String stringUrl) {
        URL returnUrl = null;
        try {
            returnUrl = new URL(stringUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return returnUrl;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        HttpURLConnection httpURLConnection = null;
        InputStream inputStream = null;
        String jsonResponse = "";
        if (url == null) {
            return jsonResponse;
        }
        try {
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.connect();
            if (httpURLConnection.getResponseCode() == 200) {
                inputStream = httpURLConnection.getInputStream();
                Scanner scanner = new Scanner(inputStream);
                scanner.useDelimiter("\\A");
                if (scanner.hasNext()) {
                    jsonResponse = scanner.next();
                    return jsonResponse;
                } else {
                    return null;
                }
            } else {
                Log.v(LOG_TAG, "HTTP response is " + httpURLConnection.getResponseCode());
            }
        } finally {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static List<Recipe> extractJsonResponse(String jsonResponse) {
        int recipeId = 0;
        String recipeName = "";
        ArrayList<Ingredient> ingredientList = new ArrayList<>();
        ArrayList<RecipeStep> recipeSteps = new ArrayList<>();
        int servings = 0;
        int quantity = 0;
        String measure = "";
        String ingredient = "";
        int stepId = 0;
        String shortDescription = "";
        String description = "";
        String videoUrl = "";
        List<Recipe> recipeList = new ArrayList<>();
        try {
            JSONArray recipeArray = new JSONArray(jsonResponse);
            for (int i = 0; i < recipeArray.length(); i++) {
                JSONObject jsonRecipe = recipeArray.getJSONObject(i);
                if (jsonRecipe.has(RECIPE_ID)) {
                    recipeId = jsonRecipe.getInt(RECIPE_ID);
                    Log.v(LOG_TAG, "RECIPE ID -" + recipeId);
                }
                if (jsonRecipe.has(RECIPE_NAME)) {
                    recipeName = jsonRecipe.getString(RECIPE_NAME);
                    Log.v(LOG_TAG, "RECIPE NAME -" + recipeName);
                }
                //TODO 1 create separate methods for Ingredients Json
                if (jsonRecipe.has(RECIPE_INGREDIENTS)) {
                    JSONArray ingredientsArray = jsonRecipe.getJSONArray(RECIPE_INGREDIENTS);
                    for (int j = 0; j < ingredientsArray.length(); j++) {
                        JSONObject currentIngredient = ingredientsArray.getJSONObject(j);
                        if (currentIngredient.has(INGREDIENT_QUANTITY)) {
                            quantity = currentIngredient.getInt(INGREDIENT_QUANTITY);
                        }
                        if (currentIngredient.has(INGREDIENT_MEASURE)) {
                            measure = currentIngredient.getString(INGREDIENT_MEASURE);
                        }
                        if (currentIngredient.has(INGREDIENT_NAME)) {
                            ingredient = currentIngredient.getString(INGREDIENT_NAME);
                        }
                        Ingredient ingredients = new Ingredient(quantity, measure, ingredient);
                        ingredientList.add(ingredients);
                    }
                }
                //TODO 2 create separate method to extract Recipe Steps Json
                if (jsonRecipe.has(RECIPE_STEPS)) {
                    JSONArray recipeStepsArray = jsonRecipe.getJSONArray(RECIPE_STEPS);
                    for (int k = 0; k < recipeStepsArray.length(); k++) {
                        JSONObject jsonRecipeStep = recipeStepsArray.getJSONObject(k);
                        if (jsonRecipeStep.has(STEP_ID)) {
                            stepId = jsonRecipeStep.getInt(STEP_ID);
                        }
                        if (jsonRecipeStep.has(STEP_SHORT_DESCRIPTION)) {
                            shortDescription = jsonRecipeStep.getString(STEP_SHORT_DESCRIPTION);
                        }
                        if (jsonRecipeStep.has(STEP_DESCRIPTION)) {
                            description = jsonRecipeStep.getString(STEP_DESCRIPTION);
                        }
                        if (jsonRecipeStep.has(STEP_VIDEO)) {
                            videoUrl = jsonRecipeStep.getString(STEP_VIDEO);
                        }
                        RecipeStep currentStep = new RecipeStep(stepId, shortDescription, description, videoUrl);
                        recipeSteps.add(currentStep);
                    }
                }
                if (jsonRecipe.has(RECIPE_SERVINGS)) {
                    servings = jsonRecipe.getInt(RECIPE_SERVINGS);
                }
                Recipe currentRecipe = new Recipe(recipeId, recipeName, ingredientList, recipeSteps, servings);
                recipeList.add(currentRecipe);
            }
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Problem parsing JSON response !");
        }
        return recipeList;
    }

}
