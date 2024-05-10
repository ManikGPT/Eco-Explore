package com.example.eco_explore3.ui.explore;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.eco_explore3.R;

import java.util.ArrayList;
import java.util.List;

public class ExploreFragment extends Fragment {

    private TextView textViewConservationStats;
    private TextView textViewPhotoGallery;
    private RecyclerView photoRecyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_explore, container, false);

        textViewConservationStats = root.findViewById(R.id.textViewConservationStats);
        textViewPhotoGallery = root.findViewById(R.id.textViewPhotoGallery);
        photoRecyclerView = root.findViewById(R.id.photoRecyclerView);

        // Display Wildlife Conservation Stats
        displayConservationStats();

        // Set up Nature Photo Gallery
        setUpPhotoGallery();

        return root;
    }

    private void displayConservationStats() {
        String conservationStats = "Wildlife Conservation Stats of India\n\n" +
                "Number of National Parks: 104\n" +
                "Number of Wildlife Sanctuaries: 500\n\n" +
                "Number of Endangered Species (Animals): 300+\n" +
                "Number of Endangered Species (Plants): 140+\n\n" +
                "Number of Conservation Projects: 295";

        textViewConservationStats.setText(conservationStats);
    }

    private void setUpPhotoGallery() {
        // Sample list of photo resources (drawable IDs)
        List<Integer> photoList = new ArrayList<>();
        photoList.add(R.drawable.photo1);
        photoList.add(R.drawable.photo2);
        photoList.add(R.drawable.photo3);
        photoList.add(R.drawable.photo4);
        photoList.add(R.drawable.photo5);
        photoList.add(R.drawable.image2);
        photoList.add(R.drawable.photo7);
        photoList.add(R.drawable.photo8);
        photoList.add(R.drawable.photo9);
        photoList.add(R.drawable.image4);
        photoList.add(R.drawable.photo11);
        photoList.add(R.drawable.photo12);
        photoList.add(R.drawable.photo13);
        photoList.add(R.drawable.photo14);
        photoList.add(R.drawable.photo15);
        photoList.add(R.drawable.photo16);
        photoList.add(R.drawable.photo17);
        photoList.add(R.drawable.photo18);
        photoList.add(R.drawable.photo19);
        photoList.add(R.drawable.photo20);
        photoList.add(R.drawable.photo21);
        photoList.add(R.drawable.photo22);
        photoList.add(R.drawable.photo23);
        photoList.add(R.drawable.photo24);
        photoList.add(R.drawable.photo25);
        photoList.add(R.drawable.photo26);
        photoList.add(R.drawable.photo27);
        photoList.add(R.drawable.photo28);
        photoList.add(R.drawable.photo29);
        photoList.add(R.drawable.photo30);


        // Create and set up the RecyclerView adapter
        PhotoGalleryAdapter adapter = new PhotoGalleryAdapter(photoList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        photoRecyclerView.setLayoutManager(layoutManager);
        photoRecyclerView.setAdapter(adapter);
    }

}

