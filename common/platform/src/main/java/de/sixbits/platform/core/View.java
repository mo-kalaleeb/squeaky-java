package de.sixbits.platform.core;

import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import de.sixbits.platform.R;


public class View {

    void loadFromUrl(ImageView iv, String url) {
        if (url.isEmpty()) {
            return;
        }
        Picasso.get()
                .load(url)
                .error(R.drawable.ic_image_load_error)
                .into(iv);
    }

    void loadCircularFromUrl(ImageView iv, String url) {
        if (url.isEmpty()) {
            return;
        }
        Picasso.get()
                .load(url)
                .transform(new CircleTransform())
                .error(R.drawable.ic_image_load_error)
                .into(iv);
    }
}
