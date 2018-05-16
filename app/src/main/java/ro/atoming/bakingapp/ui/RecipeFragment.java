package ro.atoming.bakingapp.ui;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import ro.atoming.bakingapp.R;
import ro.atoming.bakingapp.adapters.RecipeStepsAdapter;
import ro.atoming.bakingapp.models.Recipe;
import ro.atoming.bakingapp.models.RecipeStep;

/**
 * Created by Bogdan on 5/7/2018.
 */

public class RecipeFragment extends Fragment implements RecipeStepsAdapter.OnStepListener{

    public RecipeFragment(){}
    private List<RecipeStep> mStepList;
    private Recipe mRecipe;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.detail_fragment_steps,container,false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerview);

        Intent intent = getActivity().getIntent();

        Bundle bundle = intent.getBundleExtra("recipeBundle");
        if(bundle!=null){
            mRecipe = bundle.getParcelable("recipe");
        }
        mStepList = mRecipe.getRecipeSteps();
        RecipeStepsAdapter stepsAdapter = new RecipeStepsAdapter(mStepList,getActivity(),this);
        recyclerView.setAdapter(stepsAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
    }

    @Override
    public void onStepClick(int clickedItem) {

    }
}
