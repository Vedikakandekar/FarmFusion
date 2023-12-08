package com.sem3.farmfusion;

import static android.app.Activity.RESULT_OK;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;

import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CreatePostFragment extends Fragment {

  public CreatePostFragment() {
        // Required empty public constructor
    }

private static final int PICK_IMAGE_REQUEST=1;

    private ImageView imgview;
  TextInputEditText et_crop_name , et_content;
    Button btn_upload_img;
    Button btn_create_post;
    TextView TV_fragHeading;
  private Uri uri_crop_img;
  String ImgdownloadUri;

    String here;

    private StorageReference storageReference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        Bundle args = getArguments();
        if (args != null) {
           here = args.getString("here");
        }

        View v =  inflater.inflate(R.layout.fragment_create_post, container, false);
        btn_upload_img = v.findViewById(R.id.btn_upload_crop_img);
        btn_create_post = v.findViewById(R.id.btn_add_post);
        et_crop_name = v.findViewById(R.id.ET_crop_name) ;
        et_content = v.findViewById(R.id.ET_post_Description);
        imgview = v.findViewById(R.id.imgview);
        TV_fragHeading = v.findViewById(R.id.TV_fragHeading);

        storageReference = FirebaseStorage.getInstance().getReference("uploads");


        if(here.equals("createPost"))
        {
            btn_create_post.setText("Create Post");
            btn_upload_img.setVisibility(View.VISIBLE);
            et_crop_name.setVisibility(View.VISIBLE);
            et_content.setVisibility(View.VISIBLE);
            TV_fragHeading.setText("Create New Post");
            btn_upload_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openFileChooser();
                }
            });

            btn_create_post.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String cropnm,content;
                    cropnm = et_crop_name.getText().toString();
                    content = et_content.getText().toString();

                    if(!cropnm.isEmpty() && !content.isEmpty())
                    {
                        createPost(cropnm,content);

                    }
                    else
                    {
                        Toast.makeText(getContext(), "Crop name and Post content can't be Empty !!!", Toast.LENGTH_SHORT).show();
                    }

                }
            });

        }
        else if(here.equals("createReply"))
        {
            btn_create_post.setText("Send Reply");
            TV_fragHeading.setText("Reply");
            et_content.setHint("Reply ");
            et_content.setVisibility(View.VISIBLE);

            btn_create_post.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String content;
                    content = et_content.getText().toString();
                    if(content!= null && !content.isEmpty())
                    {
                        sendReply(content);
                    }
                    else
                    {
                        Toast.makeText(getContext(), "Reply content can't be Empty !!!", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }


        return v;
    }

    private void sendReply(String content) {

    }

    private void openFileChooser()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData()!=null)
        {
            uri_crop_img = data.getData();
            imgview.setVisibility(View.VISIBLE);
            imgview.setImageURI(uri_crop_img);
        }

    }

    private void createPost(String cropnm,String content)
    {
        Long timestamp1 = System.currentTimeMillis()   ;
       storageReference.child(timestamp1+"."+getFileExtension(uri_crop_img))
        .putFile(uri_crop_img)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                       // Toast.makeText(getActivity().getApplicationContext(), "Uploaded Image Successfully", Toast.LENGTH_SHORT).show();

                        ImgdownloadUri=taskSnapshot.getMetadata().getReference().getDownloadUrl().toString();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("UserData", Context.MODE_PRIVATE);

        String timestamp = String.valueOf(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date currentDate = new Date();
        String date = sdf.format(currentDate);
        Posts post = new Posts(cropnm,content,sharedPreferences.getString("userEmail",""),ImgdownloadUri,date);

        DatabaseReference dbposts = DAO.getPostsDatabaseReference();
        dbposts.child(timestamp).setValue(post).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                et_crop_name.setText("");
                et_content.setText("");
                imgview.setVisibility(View.GONE);
                Toast.makeText(getContext(), "Post Created Successfully"+ImgdownloadUri, Toast.LENGTH_SHORT).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String getFileExtension(Uri uri)
    {
        ContentResolver cR = getContext().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }


}