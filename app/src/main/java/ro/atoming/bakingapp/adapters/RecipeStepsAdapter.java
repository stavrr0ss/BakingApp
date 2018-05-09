package ro.atoming.bakingapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ro.atoming.bakingapp.R;
import ro.atoming.bakingapp.models.RecipeStep;

/**
 * Created by Bogdan on 5/7/2018.
 */

public class RecipeStepsAdapter extends RecyclerView.Adapter<RecipeStepsAdapter.StepViewHolder> {

    private List<RecipeStep> stepList;
    private Context mContext;

    @NonNull
    @Override
    public StepViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.steps_list_item,parent,false);
        return new StepViewHolder(view,this);
    }

    @Override
    public void onBindViewHolder(@NonNull StepViewHolder holder, int position) {
        RecipeStep currentStep = stepList.get(position);
        holder.mStepDescription.setText(currentStep.getShortDescription());
        currentStep.getDescription();
        currentStep.getStepId();
        currentStep.getVideoUrl();
    }

    @Override
    public int getItemCount() {
        if (stepList == null){
            return 0;
        }
        return stepList.size();
    }

    public RecipeStepsAdapter(List<RecipeStep> stepList,Context context){
        this.stepList = stepList;
        this.mContext = context;
    }

    public class StepViewHolder extends RecyclerView.ViewHolder{
        TextView mStepDescription;
        RecipeStepsAdapter mAdapter;

        public StepViewHolder(View itemView, RecipeStepsAdapter stepsAdapter) {
            super(itemView);
            mStepDescription = itemView.findViewById(R.id.step_description);
            mAdapter = stepsAdapter;
            //itemView.setOnClickListener(this);
        }
    }
}
