package ro.atoming.bakingapp.ui;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import ro.atoming.bakingapp.R;
import ro.atoming.bakingapp.adapters.CardRecipeAdapter;
import ro.atoming.bakingapp.models.Recipe;
import ro.atoming.bakingapp.utils.Networking;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Recipe>>,CardRecipeAdapter.CardClickListener{

    private RecyclerView mRecyclerView;
    public static final String LOG_TAG = MainActivity.class.getSimpleName();
    private CardRecipeAdapter mAdapter;
    private List<Recipe> mRecipeList;
    public static final int LOADER_ID = 101;
    public static final String BASE_URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = findViewById(R.id.recyclerview);
        mAdapter = new CardRecipeAdapter(mRecipeList,this, this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        if (isConnected()){
            LoaderManager loaderManager = getSupportLoaderManager();
            loaderManager.initLoader(LOADER_ID,null,this);
        }
    }

    private boolean isConnected() {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    @NonNull
    @Override
    public Loader<List<Recipe>> onCreateLoader(int id, @Nullable Bundle args) {
        return new RecipeLoader(this);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<Recipe>> loader, List<Recipe> data) {
        if (data !=null & !data.isEmpty()){
            mAdapter.setData(data);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<Recipe>> loader) {
            mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCardClick(int clickedItem) {

        //Recipe currentRecipe = mRecipeList.get(clickedItem);
        //Intent intent = new Intent(MainActivity.this,DetailActivity.class);
        //intent.putExtra("recipe",currentRecipe);
        //Log.v(LOG_TAG, "Detail Activity started " + intent.toString());
        //startActivity(intent);
    }

    private static class RecipeLoader extends AsyncTaskLoader<List<Recipe>>{

        public RecipeLoader(@NonNull Context context) {
            super(context);
        }

        @Override
        protected void onStartLoading() {
            super.onStartLoading();
            forceLoad();
        }

        @Nullable
        @Override
        public List<Recipe> loadInBackground() {
            List<Recipe> recipeList= Networking.searchRecipes(BASE_URL);
            return recipeList;
        }
    }
}
