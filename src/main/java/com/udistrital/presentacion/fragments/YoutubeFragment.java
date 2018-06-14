package com.udistrital.presentacion.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.udistrital.R;
import com.udistrital.Utils.ConfiguracionYoutube;

import java.io.Serializable;


/**
 * Created by erika on 1/03/18.
 */

public class YoutubeFragment extends Fragment implements Serializable {





   /* public static YoutubeFragment newInstance(String []arguments){
        YoutubeFragment f = new YoutubeFragment();
        Bundle bundle= new Bundle();
        bundle.putSerializable("Array", arguments);
        f.setArguments(bundle);
        return f;
    }*/

    public static YoutubeFragment newInstance(String arguments){
        YoutubeFragment f = new YoutubeFragment();
        Bundle bundle= new Bundle();
        bundle.putString("video", arguments);
        f.setArguments(bundle);
        return f;
    }



    public YoutubeFragment() {


    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final String idvideo= getArguments().getString("video");

        View rootView = inflater.inflate(R.layout.fragment_youtube, container, false);

        YouTubePlayerSupportFragment youTubePlayerFragment = YouTubePlayerSupportFragment.newInstance();

        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.youtube_layout, youTubePlayerFragment).commit();

        youTubePlayerFragment.initialize(ConfiguracionYoutube.CLAVE_API, new YouTubePlayer.OnInitializedListener() {

            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider arg0, YouTubePlayer YPlayer, boolean b) {
                if (!b) {

                    YPlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT);
                    //YPlayer.setFullscreen(true);
                    YPlayer.loadVideo(idvideo);
                    //YPlayer.play();

                }
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider arg0, YouTubeInitializationResult error) {

                String errorMessage = error.toString();
                Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_LONG).show();
                Log.d("errorMessage:", errorMessage);
            }
        });





        return rootView;
    }
}
