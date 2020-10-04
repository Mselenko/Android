package com.example.health_helper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CategoryListActivity extends AppCompatActivity
{

    private RecyclerView recyclerView;
    ArrayList<Recipe>  mainCategory;
    ArrayList<Recipe>  recipeList;
    TextView category_recipe_title;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_list);

        recyclerView = findViewById(R.id.recycler_view);
        category_recipe_title = findViewById(R.id.category_recipe_title);
        mainCategory = getIntent().getParcelableArrayListExtra("mainCategory");
        recipeList = getIntent().getParcelableArrayListExtra("recipeList");

        GridLayoutManager manager;

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
            manager = new GridLayoutManager(this,3);
        }else
            {
            manager = new GridLayoutManager(this,2);
        }

        recyclerView.setLayoutManager(manager);
        RecycleViewAdapter adapter;
        if(mainCategory != null)
        {
            adapter = new RecycleViewAdapter(mainCategory);
        }else
            {
            adapter = new RecycleViewAdapter(recipeList);
        }
        recyclerView.setAdapter(adapter);
    }

    public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.RecipeViewHolder>
    {

        List<Recipe> listRecipes;

        public RecycleViewAdapter(List<Recipe> listRecipes){
            this.listRecipes = listRecipes;
        }

        private final View.OnClickListener itemOnClick = new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                int itemPosition = recyclerView.getChildLayoutPosition(v);

                Intent intentPageRecipe = new Intent(CategoryListActivity.this, RecipeListActivity.class);

                intentPageRecipe.putExtra("category", listRecipes.get(itemPosition).getPageURL());
                startActivity(intentPageRecipe);
            }
        };


        private final View.OnClickListener recipeOnClickListener = new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                int itemPosition = recyclerView.getChildLayoutPosition(v);
                Intent intentPageRecipe = new Intent(CategoryListActivity.this, FullRecipeActivity.class);
                intentPageRecipe.putExtra("pageUrl",recipeList.get(itemPosition).getPageURL());
                intentPageRecipe.putExtra("title",recipeList.get(itemPosition).getRecipeName());
                intentPageRecipe.putExtra("image",recipeList.get(itemPosition).getImageURL());
                startActivity(intentPageRecipe);
            }
        };

        @NonNull
        @Override
        public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
        {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_list, parent, false);
            RecipeViewHolder recipeHolder = new RecipeViewHolder(view);

            if(mainCategory != null)
            {
                view.setOnClickListener(itemOnClick);
                category_recipe_title.setText("Category List");
             }else
                 {
                view.setOnClickListener(recipeOnClickListener);
                category_recipe_title.setText("Recipes List");
            }
            return recipeHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position)
        {
            holder.title.setText(listRecipes.get(position).getRecipeName());
            holder.url.setText(listRecipes.get(position).getPageURL());
            Picasso.get().load(listRecipes.get(position).getImageURL()).fit().centerCrop().into(holder.image);
        }

        @Override
        public int getItemCount()
        {
            return listRecipes.size();
        }

        public class RecipeViewHolder extends RecyclerView.ViewHolder
        {
            CardView cardView;
            ImageView image;
            TextView url;
            TextView title;

            public RecipeViewHolder(@NonNull View itemView)
            {
                super(itemView);
                cardView = itemView.findViewById(R.id.card_view);
                image = itemView.findViewById(R.id.recipe_image);
                url = itemView.findViewById(R.id.recipe_link);
                title = itemView.findViewById(R.id.recipe_title);
            }
        }
    }
}
