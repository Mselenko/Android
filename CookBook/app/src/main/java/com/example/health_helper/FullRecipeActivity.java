package com.example.health_helper;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;


public class FullRecipeActivity extends AppCompatActivity
{

    private String URLlink;
    private TextView recipeName;
    private ImageView imageRecipe;
    private TextView ingredients;
    private TextView steps;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_recipe);

        URLlink = getIntent().getStringExtra("pageUrl");

        recipeName = findViewById(R.id.recipeName);
        imageRecipe = findViewById(R.id.imageRecipe);
        ingredients = findViewById(R.id.ingredientsTextView);
        steps = findViewById(R.id.stepsTextView);

        recipeName.setText(getIntent().getStringExtra("title"));
        Picasso.get().load(getIntent().getStringExtra("image")).fit().centerCrop().into(imageRecipe);

        new FullRecipeLoad().execute();
    }


    class FullRecipeLoad extends AsyncTask<Void, Void, Void>
    {
    String ingr, stps;


        @Override
        protected Void doInBackground(Void... voids)
        {
            try {
                Document document = Jsoup.connect(URLlink).get();

                Elements section1 = document.select("div[class=responsive-tabs__panes]>section[id=recipe-ingredients]>div[class=ingredients-list]>div[class=ingredients-list__content]>ul[class=ingredients-list__group]>li");
                Elements section2 = document.select("div[class=responsive-tabs__panes]>section[id=recipe-method]>div[class=method]>ol>li");

                int a = 1;
                for (Element e : section1)
                {
                    ingr += a + ". " + e.attr("content") + "\n\n";
                    a++;
                }

                int i = 1;
                for (Element e : section2)
                {
                    stps += (i + ".  " + e.select("p").text() + "\n\n");
                    i++;
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

            ingredients.setText(ingr.replace("null", ""));
            steps.setText(stps.replace("null", ""));
        }
    }

}
