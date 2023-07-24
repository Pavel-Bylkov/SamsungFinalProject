package com.example.samsungfinal.eventdetail;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.samsungfinal.R;
import com.example.samsungfinal.eventdetail.EventDetail;

import java.io.InputStream;
import java.util.List;

public class EventImagesAdapter extends BaseAdapter {
    Context context;
    private final LayoutInflater mLayoutInflater;
    private List<EventDetail.Image> arrayImages;

    public EventImagesAdapter(Context ctx, List<EventDetail.Image> arr) {
        context = ctx;
        mLayoutInflater = LayoutInflater.from(ctx);
        setArrayMyData(arr);
    }

    public List<EventDetail.Image> getArrayMyData() {
        return arrayImages;
    }

    public void setArrayMyData(List<EventDetail.Image> arrayMyData) {
        this.arrayImages = arrayMyData;
    }

    public int getCount() {
        return arrayImages.size();
    }

    public Object getItem(int position) {

        return arrayImages.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null)
            convertView = mLayoutInflater.inflate(R.layout.adapter_item_images, null);

        EventDetail.Image md = arrayImages.get(position);

        if (md.image != null)
            new DownloadImageTask((ImageView) convertView.findViewById(R.id.event_item_imageView))
                    .execute(md.image);

        return convertView;
    }

    static class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}