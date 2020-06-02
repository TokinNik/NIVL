package com.tokovoj.nivltest.UI;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;

import com.tokovoj.nivltest.Data.Item;
import com.tokovoj.nivltest.Network.Connection.MediaType;
import com.example.nivltest.R;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static android.view.Gravity.CENTER;


public class ObserveFragment extends Fragment
{
    public static final String TAG = "OBSERVE_FRAMENT";
    private MediaPlayer mediaPlayer = new MediaPlayer();
    private View view;
    private OnFragmentInteractionListener mListener;
    private Item observeNivlItem;

    ObserveFragment(Item nivlItem)
    {
        this.observeNivlItem = nivlItem;
    }

    private void updateUI()
    {
        view.findViewById(R.id.observe_scrollView).scrollTo(0,0);
        if (observeNivlItem != null)
        {
            ((TextView)view.findViewById(R.id.observe_title_textView)).setText(observeNivlItem.getData().get(0).getTitle());
            ((TextView)view.findViewById(R.id.observe_date_textView)).setText(observeNivlItem.getData().get(0).getDate_created().substring(0,10));
            ((TextView)view.findViewById(R.id.observe_type_textView)).setText(observeNivlItem.getData().get(0).getMedia_type());
            ((TextView)view.findViewById(R.id.observe_center_textView)).setText(new StringBuilder().append("Center: ").append(observeNivlItem.getData().get(0).getCenter()).toString());
            ((TextView)view.findViewById(R.id.observe_nasa_id_textView)).setText(new StringBuilder().append("NASA id: ").append(observeNivlItem.getData().get(0).getNasa_id()).toString());
            ((TextView)view.findViewById(R.id.observe_content_textView)).setText(observeNivlItem.getData().get(0).getDescription());

            switch(observeNivlItem.getData().get(0).getMedia_type())
            {
                case "image":
                    mListener.getNivlAssets((observeNivlItem.getHref()), MediaType.IMAGE);
                break;
                case "video":
                    mListener.getNivlAssets((observeNivlItem.getHref()), MediaType.VIDEO);
                    break;
                case "audio":
                    mListener.getNivlAssets((observeNivlItem.getHref()), MediaType.AUDIO);
                    break;
            }
        }
        else
        {
            ((TextView)view.findViewById(R.id.observe_title_textView)).setText(R.string.data_not_load);
            ((TextView)view.findViewById(R.id.observe_content_textView)).setText(R.string.data_not_load);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_obsertve, container, false);
        updateUI();
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context)
    {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener)
        {
            mListener = (OnFragmentInteractionListener) context;
        } else
        {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach()
    {
        super.onDetach();
        mListener = null;
        mediaPlayer.stop();
        mediaPlayer.release();
        mediaPlayer = null;
    }

    void setNivlImage(String href)
    {
        Picasso.with(view.getContext())
                .load("https" + href.substring(4))//bad link from qery
                .error(R.drawable.nasa_logo)
                .into(((ImageView)view.findViewById(R.id.observe_imageView)));
        view.findViewById(R.id.observe_videoView).setVisibility(View.GONE);
        view.findViewById(R.id.observe_progressBar).setVisibility(View.GONE);
    }

    void setNivlVideo(final String href)
    {
        final VideoView videoView =  view.findViewById(R.id.observe_videoView);

        MediaController mediaController = new MediaController(view.getContext());
        mediaController.setAnchorView(videoView);
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener()
        {
            @Override
            public void onPrepared(MediaPlayer mp)
            {
                videoView.setVisibility(View.VISIBLE);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(new LinearLayout.LayoutParams((int)(400*view.getContext().getResources().getDisplayMetrics().density), ViewGroup.LayoutParams.MATCH_PARENT));
                params.gravity = CENTER;
                videoView.setLayoutParams(params);
                view.findViewById(R.id.observe_progressBar).setVisibility(View.GONE);
            }
        });
        videoView.setOnErrorListener(new MediaPlayer.OnErrorListener()
        {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra)
            {
                return false;
            }
        });
        videoView.setMediaController(mediaController);
        videoView.setVideoURI(Uri.parse(href));
        videoView.start();
    }

    void setNivlAudio(String href)
    {
        try
        {
            mediaPlayer.setDataSource(href);
            mediaPlayer.prepareAsync();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        mediaPlayer.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener()
        {
            @Override
            public void onBufferingUpdate(MediaPlayer mp, int percent)
            {
                if(percent > 0)
                    ((SeekBar)view.findViewById(R.id.player_progressBar)).setSecondaryProgress(mp.getDuration()/percent);
            }
        });
        mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener()
        {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra)
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle(R.string.audio_not_found_error)
                        .setMessage(R.string.audio_not_found_message)
                        .setCancelable(true)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                view.findViewById(R.id.observe_progressBar).setVisibility(View.GONE);
                                dialog.cancel();
                            }
                        });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

                return false;
            }
        });
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener()
        {
            @Override
            public void onPrepared(MediaPlayer mp)
            {
                mediaPlayer.start();
                view.findViewById(R.id.observe_progressBar).setVisibility(View.GONE);
                view.findViewById(R.id.observe_videoView).setVisibility(View.GONE);
                view.findViewById(R.id.player_linearLayout).setVisibility(View.VISIBLE);
                ((TextView)view.findViewById(R.id.player_progress_textView)).setText(convertTime(mediaPlayer.getCurrentPosition()));
                ((TextView)view.findViewById(R.id.player_duration_textView)).setText(convertTime(mediaPlayer.getDuration()));
                ((SeekBar)view.findViewById(R.id.player_progressBar)).setMax(mp.getDuration()/1000);
                ((SeekBar)view.findViewById(R.id.player_progressBar)).setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
                    {
                        if(fromUser)
                        {
                            mediaPlayer.seekTo(progress*1000);
                            ((TextView)view.findViewById(R.id.player_progress_textView)).setText(convertTime(mediaPlayer.getCurrentPosition()));
                        }
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar)
                    {}

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar)
                    {}
                });

                startPlayerProgress();
            }
        });
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener()
        {
            @Override
            public void onCompletion(MediaPlayer mp)
            {
                ((ImageButton)view.findViewById(R.id.player_imageButton)).setImageResource(android.R.drawable.ic_media_play);
            }
        });
        view.findViewById(R.id.player_imageButton).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(mediaPlayer.isPlaying())
                {
                    mediaPlayer.pause();
                    ((ImageButton)v).setImageResource(android.R.drawable.ic_media_play);
                }
                else
                {
                    mediaPlayer.start();
                    ((ImageButton)v).setImageResource(android.R.drawable.ic_media_pause);
                }
            }
        });
        view.findViewById(R.id.player_forward_imageButton).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(mediaPlayer.isPlaying())
                {
                    int shift = mediaPlayer.getCurrentPosition() + 30000;
                    if (shift < mediaPlayer.getDuration())
                    {
                        mediaPlayer.seekTo(shift);
                    }
                    else
                    {
                        mediaPlayer.seekTo(mediaPlayer.getDuration());
                    }
                    ((SeekBar) view.findViewById(R.id.player_progressBar)).setProgress(mediaPlayer.getCurrentPosition()/1000);
                    ((TextView)view.findViewById(R.id.player_progress_textView)).setText(convertTime(mediaPlayer.getCurrentPosition()));
                }
            }
        });
        view.findViewById(R.id.player_back_imageButton).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(mediaPlayer.isPlaying())
                {
                    int shift = mediaPlayer.getCurrentPosition() - 30000;
                    if (shift > 0)
                    {
                        mediaPlayer.seekTo(shift);
                    }
                    else
                    {
                        mediaPlayer.seekTo(0);
                    }
                    ((SeekBar) view.findViewById(R.id.player_progressBar)).setProgress(mediaPlayer.getCurrentPosition() / 1000);
                    ((TextView)view.findViewById(R.id.player_progress_textView)).setText(convertTime(mediaPlayer.getCurrentPosition()));
                }
            }
        });
    }

    private void startPlayerProgress()
    {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                if(mediaPlayer == null)
                {
                    System.out.println("NULL NULL NULL NULL");

                }
                else
                {
                    ((SeekBar)view.findViewById(R.id.player_progressBar)).setProgress(mediaPlayer.getCurrentPosition()/1000);
                    ((TextView) view.findViewById(R.id.player_progress_textView)).setText(convertTime(mediaPlayer.getCurrentPosition()));
                    handler.postDelayed(this, 1000);
                }
            }
        },1000);
    }

    private String convertTime(int millis)
    {
        long minutes = TimeUnit.MILLISECONDS.toMinutes(millis);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(millis) - minutes * 60;
        return new StringBuilder().append(minutes).append(":").append(seconds).toString();
    }

    public interface OnFragmentInteractionListener
    {
        void getNivlAssets(String href, MediaType media_type);
    }
}
