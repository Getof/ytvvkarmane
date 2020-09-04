package ru.getof.ytvvkarmane.MainFragments;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.UUID;

import ru.getof.ytvvkarmane.Adapters.MessagesAdapter;
import ru.getof.ytvvkarmane.Components.Messages;
import ru.getof.ytvvkarmane.R;
import ru.getof.ytvvkarmane.Utils.AuthUser;

import static android.app.Activity.RESULT_OK;

public class FragmentChat extends Fragment {

    private static final int REQUEST_CODE_PICK_VIDEO = 2;
    private View view;
    private RecyclerView rvChat;
    private FloatingActionButton flAddVideo;
    private RelativeLayout ltChat;
    private LinearLayout ltNoChat;
    private StorageReference mStorageRef;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference messagesRef = db.collection("Messages");
    private DocumentReference userRef;
    private MessagesAdapter messagesAdapter;
    private boolean firstTime = true;
    private LinearLayoutManager layoutManager;
    private Dialog dialog;
    private ArrayList<Messages> mediaObjectList = new ArrayList<>();
    private Uri videoUri, imageUri;

    public interface ListenerChatFragment{
        void PressVideoChat(String urlVideo);
    }

    private ListenerChatFragment chatListener;

    public static FragmentChat newInstance() {
        FragmentChat fragmentChat = new FragmentChat();
        return fragmentChat;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (messagesAdapter != null) messagesAdapter.notifyDataSetChanged();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (messagesAdapter != null) messagesAdapter.notifyDataSetChanged();
    }

    private void setUpRecyclerView() {
        messagesRef.orderBy("dateMessage", Query.Direction.DESCENDING)
                .limit(5)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        mediaObjectList.clear();
                        mediaObjectList.addAll(queryDocumentSnapshots.toObjects(Messages.class));
                        loadList(mediaObjectList);
                    }
                });
    }

    private void loadList(ArrayList<Messages> arrayList){
        for (int i = arrayList.size()-1; i >=0; i--){
            if (arrayList.get(i).getConfirmMess() == 0){
                arrayList.remove(i);
            }
        }
        layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setReverseLayout(true);
        rvChat.setLayoutManager(layoutManager);
        messagesAdapter = new MessagesAdapter(getActivity(),arrayList);
        rvChat.setAdapter(messagesAdapter);
        if (messagesAdapter != null) {
            messagesAdapter.setOnItemClickListener(new MessagesAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View itemView, int position) {
                    chatListener.PressVideoChat(mediaObjectList.get(position).getUrlVideo());
                }
            });
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_chat_go, container, false);
        ltChat = view.findViewById(R.id.layout_chat);
        ltNoChat = view.findViewById(R.id.layout_no_chat);
        rvChat = view.findViewById(R.id.rv_chat);
        flAddVideo = view.findViewById(R.id.floatingAddVideo);
        mStorageRef = FirebaseStorage.getInstance().getReference();
        setUpRecyclerView();

        if (AuthUser.getInstance().getUser()) {
            ltNoChat.setVisibility(View.GONE);
            ltChat.setVisibility(View.VISIBLE);
        }

        flAddVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("video/*");
                startActivityForResult(intent, REQUEST_CODE_PICK_VIDEO);
            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        //View view = getLayoutInflater().inflate(R.layout.progress);
        builder.setView(R.layout.loader);
        dialog = builder.create();



        return view;
    }

    @Override
    public void onDestroyView() {
        layoutManager = null;
        messagesAdapter = null;
        super.onDestroyView();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_PICK_VIDEO && resultCode == RESULT_OK){
            setDialog(true);
            Uri uri = data.getData();
            MediaMetadataRetriever mMMR = new MediaMetadataRetriever();
            mMMR.setDataSource(getActivity(), uri);
            Bitmap bMap = mMMR.getFrameAtTime();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bMap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] dataImg = baos.toByteArray();

            StorageReference filePath = mStorageRef.child("video_mess").child(uri.getLastPathSegment());
            filePath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    filePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            videoUri = uri;
                            String uidImg = UUID.randomUUID().toString();
                            UploadTask uploadTask = mStorageRef.child("thumb_mess").child(uidImg).putBytes(dataImg);
                            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    mStorageRef.child("thumb_mess/"+uidImg).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            imageUri = uri;
                                            setDialog(false);
                                            sendMessageChat(videoUri, imageUri);
                                        }
                                    });
                                }
                            });
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });
        }
    }

    private void sendMessageChat(Uri video, Uri thumb) {
        final String[] usName = new String[1];
        FirebaseUser us = FirebaseAuth.getInstance().getCurrentUser();
        if (us != null) {
             userRef = db.collection("users").document(us.getUid());
             userRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                 @Override
                 public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                     if (task.isSuccessful()){
                         DocumentSnapshot document = task.getResult();
                         usName[0] = document.getString("Name");
                         messagesRef.add(new Messages(new Date(System.currentTimeMillis()),
                                 usName[0],
                                 video.toString(),
                                 "",
                                 0,
                                 thumb.toString()));
                     }
                 }
             });
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof ListenerChatFragment) {
            chatListener = (ListenerChatFragment) context;
        } else {
            throw new RuntimeException(context.toString());
        }
    }

    public void updateFrag(){
        if (AuthUser.getInstance().getUser()) {
            ltNoChat.setVisibility(View.GONE);
            ltChat.setVisibility(View.VISIBLE);
        } else {
            ltNoChat.setVisibility(View.VISIBLE);
            ltChat.setVisibility(View.GONE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        updateFrag();
    }

    private void setDialog(boolean show){
        if (show) {
            dialog.show();
        } else {
            dialog.dismiss();
        }
    }

}
