package com.example.eco_explore3.ui.home;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.eco_explore3.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.label.ImageLabel;
import com.google.mlkit.vision.label.ImageLabeler;
import com.google.mlkit.vision.label.ImageLabeling;
import com.google.mlkit.vision.label.defaults.ImageLabelerOptions;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class HomeFragment extends Fragment {

    private ImageView innerImage;
    private TextView resultTv;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_PICK_IMAGE = 2;
    private static final int REQUEST_CAMERA_PERMISSION = 2;
    private ImageLabeler labeler;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        innerImage = root.findViewById(R.id.imageView2);
        resultTv = root.findViewById(R.id.textView);
        FloatingActionButton fab = root.findViewById(R.id.fab);

        // Initialize Image Labeler
        labeler = ImageLabeling.getClient(ImageLabelerOptions.DEFAULT_OPTIONS);

        // Check and request camera permission
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(),
                    new String[]{Manifest.permission.CAMERA},
                    REQUEST_CAMERA_PERMISSION);
        }

        innerImage.setOnClickListener(v -> showImageOptions());

        fab.setOnClickListener(v -> {
            // Clear ImageView and TextView
            innerImage.setImageBitmap(null);
            resultTv.setText("Lets Explore!");
        });
        return root;
    }

    private void showImageOptions() {
        PopupMenu popupMenu = new PopupMenu(requireContext(), innerImage);
        popupMenu.getMenuInflater().inflate(R.menu.menu_image_options, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_choose_from_device:
                        openGallery();
                        return true;
                    case R.id.action_take_photo:
                        openCamera();
                        return true;
                    default:
                        return false;
                }
            }
        });

        popupMenu.show();
    }

    private void openCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(requireActivity().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }

    }

    private void openGallery() {
        Intent pickPhotoIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickPhotoIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(pickPhotoIntent, REQUEST_PICK_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_PICK_IMAGE) {
                if (data != null && data.getData() != null) {
                    Uri selectedImageUri = data.getData();
                    try {
                        Bitmap imageBitmap = MediaStore.Images.Media.getBitmap(requireContext().getContentResolver(), selectedImageUri);
                        if (imageBitmap != null) {
                            doInference(imageBitmap);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(requireContext(), "Failed to load image", Toast.LENGTH_SHORT).show();
                    }
                }
            } else if (requestCode == REQUEST_IMAGE_CAPTURE) {
                if (data != null && data.getExtras() != null) {
                    Bitmap imageBitmap = (Bitmap) data.getExtras().get("data");
                    if (imageBitmap != null) {
                        doInference(imageBitmap);
                    }
                }
            }
        }
    }



    private final List<String> selectedLabels = Arrays.asList(
            "Aquarium", "Bird", "Twig", "Petal", "Plant", "Fish", "Pomacentridae", "Shetland sheepdog", "Bullfighting",
            "Gerbil", "Sky", "Porcelain", "Bear", "Flower", "Snorkeling", "Swamp", "Rodeo", "Basset hound", "Larva",
            "Prairie", "Sphynx", "Farm", "Seal", "Vegetable", "Butterfly", "Wing", "Bench", "Dog", "Bull", "Fur", "Pet",
            "Dog", "Garden", "Wool", "Turtle", "Crocodile", "Duck", "Fishing", "Rein", "Shikoku", "Penguin", "Shell",
            "Horn", "Insect", "Hair", "Herd", "Horse", "Forest", "Cat","Plush", "Waterfowl",
            "Cavalier", "Trunk", "Stuffed toy", "Cairn terrier", "Reef", "Flora", "Fruit", "Dalmatian", "Waterfowl",
            "Cave", "Cattle", "Jungle"
    );

    private void doInference(Bitmap input) {
        innerImage.setImageBitmap(input);
        InputImage image = InputImage.fromBitmap(input, 0);
        resultTv.setText(""); // Clear previous labels

        labeler.process(image)
                .addOnSuccessListener(new OnSuccessListener<List<ImageLabel>>() {
                    @Override
                    public void onSuccess(List<ImageLabel> labels) {
                        // Task completed successfully
                        StringBuilder labelText = new StringBuilder();
                        boolean found = false;
                        for (ImageLabel label : labels) {
                            String text = label.getText();
                            float confidence = label.getConfidence();
                            if (selectedLabels.contains(text)) {
                                int confidencePercentage = (int) (confidence * 100);
                                if (confidencePercentage > 85) {
                                    labelText.append(text).append(" ").append(confidencePercentage).append("%\n");
                                    found = true;
                                }
                            }
                        }
                        if (found) {
                            // Update resultTv with detected labels
                            resultTv.setText(labelText.toString());
                        } else {
                            // No matching labels found
                            resultTv.setText("Cannot identify species");
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Task failed with an exception
                        Toast.makeText(requireContext(), "Failed to label image", Toast.LENGTH_SHORT).show();
                    }
                });
    }

}
