package app.kth.com.groupie.otherProfile;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import app.kth.com.groupie.R;
import app.kth.com.groupie.data.structure.Profile;
import de.hdodenhof.circleimageview.CircleImageView;

public class OtherProfieActivity extends AppCompatActivity {

    Profile profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_profie);

        profile = (Profile) getIntent().getSerializableExtra("profile");
        initTextviews();

    }


    private void initTextviews(){
        TextView firstname = (TextView) findViewById(R.id.profilename_textview);
        firstname.setText(profile.getFirstName());

        TextView lastname = (TextView) findViewById(R.id.lastname_textview);
        lastname.setText(profile.getLastName());

        TextView school = (TextView) findViewById(R.id.profileschool_textview);
        school.setText(profile.getSchool());

        TextView bio = (TextView) findViewById(R.id.profilebio_textview);
        bio.setText(profile.getBio());

        TextView field = (TextView) findViewById(R.id.profilefield_textview);
        field.setText(profile.getFieldOfStudy());

        if(profile.getProfilePicture() != null){
            new DownloadImageTask((CircleImageView) findViewById(R.id.profilepic_imageview))
                    .execute(profile.getProfilePicture());
        }

        ImageView report = (ImageView) findViewById(R.id.reportuser_imageview);
        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(getApplicationContext(), "User Reported", Toast.LENGTH_LONG);
                toast.show();
            }
        });
    }


    // show The Image in a ImageView
    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        CircleImageView bmImage;

        public DownloadImageTask(CircleImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}