package ro.atoming.bakingapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ro.atoming.bakingapp.R;
import ro.atoming.bakingapp.models.RecipeStep;
import ro.atoming.bakingapp.ui.DetailStepActivity;

/**
 * Created by Bogdan on 5/7/2018.
 */

public class RecipeStepsAdapter extends RecyclerView.Adapter<RecipeStepsAdapter.StepViewHolder> {

    private List<RecipeStep> mStepList;
    private Context mContext;
    private OnStepListener mStepListener;

    public interface OnStepListener {
        void onStepClick(int clickedItem);
    }
    @NonNull
    @Override
    public StepViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.steps_list_item,parent,false);
        return new StepViewHolder(view,this);
    }

    @Override
    public void onBindViewHolder(@NonNull StepViewHolder holder, int position) {
        RecipeStep currentStep = mStepList.get(position);
        holder.mStepDescription.setText(currentStep.getShortDescription());
        currentStep.getDescription();
        currentStep.getStepId();
        currentStep.getVideoUrl();
    }

    @Override
    public int getItemCount() {
        if (mStepList == null){
            return 0;
        }
        return mStepList.size();
    }

    public RecipeStepsAdapter(List<RecipeStep> stepList,Context context,OnStepListener stepListener){
        this.mStepList = stepList;
        this.mContext = context;
        this.mStepListener = stepListener;
    }

    public class StepViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView mStepDescription;
        RecipeStepsAdapter mAdapter;


        public StepViewHolder(View itemView, RecipeStepsAdapter stepsAdapter) {
            super(itemView);
            mStepDescription = itemView.findViewById(R.id.step_description);
            mAdapter = stepsAdapter;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int clickedItem = getAdapterPosition();
            RecipeStep currentStep = mStepList.get(clickedItem);
            Bundle bundle = new Bundle();
            bundle.putParcelable("step",currentStep);
            Intent stepIntent = new Intent(mContext, DetailStepActivity.class);
            stepIntent.putExtra("currentStep",bundle);
            mContext.startActivity(stepIntent);
            mStepListener.onStepClick(clickedItem);
        }
    }
}
