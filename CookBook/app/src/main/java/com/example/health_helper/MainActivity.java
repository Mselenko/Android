package com.example.health_helper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private List<Recipe> listRecipes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new ParseRecipes().execute();
    }


    class ParseRecipes extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected Void doInBackground(Void... voids)
        {
            String siteURL = "https://www.bbcgoodfood.com/recipes/category/healthy";
            String imgRecipe, nameRecipe, linkPage;

            try
            {
                Document document = Jsoup.connect(siteURL).get();
                Elements elements = document.select("div[class=view-content]>ul[class=unstyled category-item--list]>li>article");

                for (Element e : elements)
                {
                    imgRecipe = "https:" + e.select("div[class=shadow-bg shadow-bg-height]>img").attr("src");
                    nameRecipe = e.select("h3>a").text();
                    linkPage = "https://www.bbcgoodfood.com/" + e.select("h3>a").attr("href");

                    listRecipes.add(new Recipe(imgRecipe, nameRecipe, linkPage));
                }


            } catch (IOException e)
            {
                e.printStackTrace();
            }
            return null;
        }


        @Override
        protected void onPostExecute(Void aVoid)
        {
            super.onPostExecute(aVoid);

            Intent intent = new Intent(MainActivity.this, CategoryListActivity.class);
            intent.putParcelableArrayListExtra("mainCategory", (ArrayList<? extends Parcelable>) listRecipes);
            startActivity(intent);
            finish();
        }
    }
}
