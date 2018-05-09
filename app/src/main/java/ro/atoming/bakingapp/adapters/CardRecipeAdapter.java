package ro.atoming.bakingapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ro.atoming.bakingapp.R;
import ro.atoming.bakingapp.models.Recipe;
import ro.atoming.bakingapp.models.RecipeStep;
import ro.atoming.bakingapp.ui.DetailActivity;

/**
 * Created by Bogdan on 5/4/2018.
 */

public class CardRecipeAdapter extends RecyclerView.Adapter<CardRecipeAdapter.CardViewHolder> {

    List<Recipe> mRecipeList;
    Context mContext;
    private CardClickListener mCardClickListener ;
    private static final String LOG_TAG = CardRecipeAdapter.class.getSimpleName();


    public CardRecipeAdapter(List<Recipe> recipes, Context context, CardClickListener cardClickListener){
        mRecipeList = recipes;
        mContext = context;
        mCardClickListener = cardClickListener;
    }
    public interface CardClickListener{
        void onCardClick(int clickedItem);
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_item_layout,parent,false);
        view.setFocusable(true);
        return new CardViewHolder(view,this);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        Recipe recipe = mRecipeList.get(position);
        holder.recipeName.setText(recipe.getName());
        List<RecipeStep> recipeSteps = recipe.getRecipeSteps();
        String mRecipeStep = recipeSteps.toString();
        Log.d(LOG_TAG, "THIS ARE THE STEPS " + mRecipeStep);
        holder.servings.setText(String.valueOf(recipe.getServings()));
    }

    @Override
    public int getItemCount() {
        if (mRecipeList == null)
            return 0;
        return mRecipeList.size();
    }

    public void setData(List<Recipe> recipeData){
        mRecipeList = recipeData;
        notifyDataSetChanged();
    }

    public class CardViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        CardView cardView ;
        TextView recipeName;
        TextView servings;
        CardRecipeAdapter mAdapter;

        public CardViewHolder(View itemView, CardRecipeAdapter cardAdapter) {
            super(itemView);
            cardView = (CardView)itemView.findViewById(R.id.cardview);
            recipeName = (TextView)itemView.findViewById(R.id.recipe_name);
            servings = (TextView)itemView.findViewById(R.id.servings);
            mAdapter = cardAdapter;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int clickedItem = getAdapterPosition();
            Recipe currentRecipe = mRecipeList.get(clickedItem);
            Bundle bundle = new Bundle();
            bundle.putParcelable("recipe",currentRecipe);
            Intent intent = new Intent(mContext,DetailActivity.class);
            intent.putExtra("recipeBundle",bundle);
            //RecipeFragment fragment = new RecipeFragment();
            //fragment.setArguments(bundle);
            Log.v(LOG_TAG, "Detail Activity started " + intent.toString());
            mContext.startActivity(intent);
            mCardClickListener.onCardClick(clickedItem);
        }
    }
}
