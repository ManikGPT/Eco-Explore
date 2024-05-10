package com.example.eco_explore3.ui.help;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eco_explore3.R;
import com.example.eco_explore3.databinding.FragmentHelpBinding;

import java.util.ArrayList;
import java.util.List;

public class HelpFragment extends Fragment {

    private RecyclerView recyclerViewFAQ;
    private FAQAdapter faqAdapter;
    private List<FAQItem> faqList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_help, container, false);
        recyclerViewFAQ = root.findViewById(R.id.recyclerViewFAQ);
        faqList = new ArrayList<>();
        populateFAQList(); // Populate FAQ list with data
        faqAdapter = new FAQAdapter(faqList);
        recyclerViewFAQ.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewFAQ.setAdapter(faqAdapter);
        return root;
    }

    private void populateFAQList() {
        // Add FAQ items to the list
        faqList.add(new FAQItem("How do I take a photo?", "You can take a photo by clicking on the camera icon in the bottom-right corner of the screen."));
        faqList.add(new FAQItem("How do I choose an image from my device?", "You can choose an image from your device by clicking on the gallery icon in the bottom-right corner of the screen and selecting the desired image."));
        faqList.add(new FAQItem("How do I edit my profile?", "To edit your profile, navigate to the 'Edit Profile' section from the navigation drawer and follow the instructions provided."));
        faqList.add(new FAQItem("How do I change app settings?", "You can change app settings by navigating to the 'Settings' section from the navigation drawer and adjusting the settings according to your preferences."));
        faqList.add(new FAQItem("Where can I find information about the app?", "You can find information about the app, such as version details and credits, in the 'About' section from the navigation drawer."));
        faqList.add(new FAQItem("How do I contact support?", "For any support or assistance, you can navigate to the 'Contact' section from the navigation drawer and follow the instructions provided."));
        faqList.add(new FAQItem("How do I log out of the app?", "To log out of the app, simply click on the 'Logout' option in the navigation drawer."));

    }
}
