package com.example.nivltest.UI;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nivltest.AppModel;
import com.example.nivltest.Mediator.Mediator;
import com.example.nivltest.Net.MediaType;
import com.example.nivltest.Net.NivlData;
import com.example.nivltest.Net.Net;
import com.example.nivltest.R;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements AppModel.UI, ObserveFragment.OnFragmentInteractionListener
{
    public static final  String TAG = "MAIN_ACTIVITY";
    private static final int START_PAGE = 1;
    private static final int MAX_ITEMS_COUNT = 99;
    private AppModel.Mediator mediator;

    private RecyclerView recyclerView;
    private AlertDialog loadDialog;
    private TextView pageText;
    private int currentPage;
    private int pageCount;
    List<NivlData.Collection.Item> list = new ArrayList<>();

    private FragmentManager fragmentManager;
    private OnListInteractionListener listener = new OnListInteractionListener() {
        @Override
        public void OnListInteraction(int position) {
            setObserveFragment(list.get(position));
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Net net = new Net();
        mediator = new Mediator(net);
        mediator.attachUI(this);

        fragmentManager = getSupportFragmentManager();

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.loading)
                .setMessage(R.string.loading_message)
                .setCancelable(false)
                .setView(new ProgressBar(this));
        loadDialog = builder.create();
        loadDialog.hide();

        currentPage = START_PAGE;

        pageText = findViewById(R.id.page_textView);

        ImageButton nextButton = findViewById(R.id.next_imageButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentPage < pageCount)
                {
                    currentPage++;
                    if(searhNivlData(currentPage))
                    {
                        loadDialog.show();
                        pageText.setText(new StringBuilder().append(currentPage).append("/").append(pageCount).toString());
                    }
                }
            }
        });

        ImageButton prewButton = findViewById(R.id.prew_imageButton);
        prewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentPage > 1)
                {
                    currentPage--;
                    if(searhNivlData(currentPage))
                    {
                        loadDialog.show();
                        pageText.setText(new StringBuilder().append(currentPage).append("/").append(pageCount).toString());
                    }
                }
            }
        });

        Button searchButton = findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(searhNivlData(START_PAGE))
                {
                    loadDialog.show();
                    currentPage = START_PAGE;
                }
            }
        });
    }

    @Override
    protected void onResume()
    {
        super.onResume();
    }

    private boolean searhNivlData(int page)
    {
        String q = String.valueOf(((EditText)findViewById(R.id.search_editText)).getText());
        if(!q.equals(""))
        {
            StringBuilder builder = new StringBuilder();
            if(((CheckBox)findViewById(R.id.image_checkBox)).isChecked())
                builder.append("image,");
            if(((CheckBox)findViewById(R.id.video_checkBox)).isChecked())
                builder.append("video,");
            if(((CheckBox)findViewById(R.id.audio_checkBox)).isChecked())
                builder.append("audio,");

            if (builder.length() == 0)
            {
                Toast.makeText(this, R.string.select_categories, Toast.LENGTH_SHORT).show();
                return false;
            }
            else
            {
                builder.deleteCharAt(builder.length()-1);
                mediator.searchNivlData(q, page, builder.toString());
                loadDialog.show();
                return true;
            }
        }
        return false;
    }

    private void setObserveFragment(NivlData.Collection.Item nivlItem)
    {
        recyclerView.setVisibility(View.GONE);
        getSupportActionBar().hide();
        findViewById(R.id.search_layout).setVisibility(View.GONE);
        findViewById(R.id.page_lauout).setVisibility(View.GONE);

        fragmentManager.beginTransaction()
                .add(R.id.container, new ObserveFragment(nivlItem), ObserveFragment.TAG)
                .commit();
    }

    @Override
    public void onItemsUpdate(NivlData nivlData)
    {
        list = nivlData.getCollection().getItems();
        int totalHits = nivlData.getCollection().getMetadata().getTotal_hits();
        pageCount = totalHits/MAX_ITEMS_COUNT;
        if(pageCount == 0) pageCount++;
        pageText.setText(new StringBuilder().append(currentPage).append("/").append(pageCount).toString());
        recyclerView.setAdapter(new RecyclerViewAdapter(list, listener));
        loadDialog.hide();
    }

    @Override
    public void setErrorMessage(int code)
    {
        switch (code)
        {
            case 204:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(R.string.no_result_error)
                        .setMessage(R.string.no_result_message)
                        .setCancelable(true)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                dialog.cancel();
                                loadDialog.hide();
                            }
                        });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                break;
            case 400:
                Toast.makeText(this, R.string.download_error,  Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void setConnectionLostMessage()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.connection_error)
                .setMessage(R.string.connection_error_message)
                .setCancelable(true)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                       dialog.cancel();
                       loadDialog.hide();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    public void setNivlAssets(String href, MediaType mediaType)
    {
        switch (mediaType)
        {
            case IMAGE:
                ((ObserveFragment)fragmentManager.findFragmentById(R.id.container)).setNivlImage(href);
                break;
            case VIDEO:
                ((ObserveFragment)fragmentManager.findFragmentById(R.id.container)).setNivlVideo(href);
                break;
            case AUDIO:
                ((ObserveFragment)fragmentManager.findFragmentById(R.id.container)).setNivlAudio(href);
                break;
        }
    }

    @Override
    public void onBackPressed()
    {
        if (recyclerView.getVisibility() == View.GONE)
        {
            recyclerView.setVisibility(View.VISIBLE);
            getSupportActionBar().show();
            findViewById(R.id.search_layout).setVisibility(View.VISIBLE);
            findViewById(R.id.page_lauout).setVisibility(View.VISIBLE);

            fragmentManager.beginTransaction()
                    .remove(fragmentManager.findFragmentByTag(ObserveFragment.TAG))
                    .commit();
        }
        else
        {
            super.onBackPressed();
        }
    }

    @Override
    public void getNivlAssets(String href, MediaType media_type)
    {
        mediator.getNivlAssets(href, media_type);
    }

    interface OnListInteractionListener
    {
        void OnListInteraction(int position);
    }

    @Override
    protected void onDestroy()
    {
        loadDialog.dismiss();
        mediator.detachUI();
        super.onDestroy();
    }
}
