package com.tan.advandroid.myapplication;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tan.advandroid.myapplication.network.Network;

/**
 * Created by asus on 29.03.2015.
 */
public class MyCursorAdapter  extends CursorAdapter {
    private static ViewHolder viewHolder;
    private final LayoutInflater inflater;
    private MyAsyncTask myAsyncTask;

    public MyCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        viewHolder = new ViewHolder();
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        View itemLayout = inflater.inflate(R.layout.list_item, viewGroup, false);
        return itemLayout;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        viewHolder.title = (TextView) view.findViewById(R.id.title);
        viewHolder.url = (ImageView) view.findViewById(R.id.icon);
        viewHolder.title.setText(cursor.getString(cursor.getColumnIndex(MyContentProvider.TECH_TITLE)));
        view.setTag(viewHolder);
        myAsyncTask = new MyAsyncTask();
        myAsyncTask.execute(String.valueOf(cursor.getString(cursor.getColumnIndex(MyContentProvider.TECH_IMAGE))));

    }

    public class MyAsyncTask extends AsyncTask<String, Integer, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... string) {
            Network network = new Network();
            Bitmap bitmap = network.downloadImage(string[0]);
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
         viewHolder.url.setImageBitmap(bitmap);
        }

    }

    @Override
    public int getCount() {
        return getCursor() == null ? 0 : super.getCount();
    }

    private static class ViewHolder {
        TextView title;
        ImageView url;
    }
}