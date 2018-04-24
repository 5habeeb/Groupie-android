package app.kth.com.groupie.parent;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.functions.FirebaseFunctions;

import java.util.ArrayList;

import app.kth.com.groupie.R;
import app.kth.com.groupie.data.Group;

public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.GroupViewHolder> {
    private ArrayList<Group> groupArrayList = new ArrayList<>();
    private Context context;
    private static int NUM_GROUPS_TO_LOAD = 10;
    private FirebaseFunctions mFunction;

    public GroupAdapter(final DatabaseReference databaseReference, Context context) {
        this.context = context;

        Query nearestGroupMeetingQuery = databaseReference.orderByChild("meetingDateTimeStamp").limitToLast(NUM_GROUPS_TO_LOAD);

        nearestGroupMeetingQuery.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Group group = dataSnapshot.getValue(Group.class);


                if (group.getIsPublic()) {
                    group.setGroupId(dataSnapshot.getKey());
                    groupArrayList.add(group);
                    Log.d("TAG", "GROUP KEY: " + group.getGroupId());
                }



                notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public class GroupViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout parent = (RelativeLayout) itemView.findViewById(R.id.group_card_view);
        ImageView subjectImage = (ImageView) itemView.findViewById(R.id.subject_imageview);
        TextView subject = (TextView) itemView.findViewById(R.id.group_subject_textview);
        TextView topic = (TextView) itemView.findViewById(R.id.group_topic_textview);
        TextView location = (TextView) itemView.findViewById(R.id.group_location_textview);
        TextView description = (TextView) itemView.findViewById(R.id.group_description_textview);
        Button joinGroupBtn = (Button) itemView.findViewById(R.id.join_group_btn);

        // fixed textViews
        TextView topicTitle = (TextView) itemView.findViewById(R.id.topic_textview_fixed);
        TextView locationTitle = (TextView) itemView.findViewById(R.id.location_textview_fixed);
        TextView descriptionTitle = (TextView) itemView.findViewById(R.id.description_textview_fixed);



        public GroupViewHolder(View itemView) {
            super(itemView);
        }
    }



    @NonNull
    @Override
    public GroupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_group, parent, false);

        return new GroupViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull GroupViewHolder holder, int position) {
        final Group group = groupArrayList.get(position);

        setFields(group, holder);
        setSubjectImage(group, holder);
        setJoinGroupButton(group, holder);
    }

    @Override
    public int getItemCount() {
        return groupArrayList.size();
    }

    
    private void setFields(Group group, GroupViewHolder holder) {
        holder.subject.setText(group.getSubject());

        // Empty fields are hidden
        if(group.getTopic() == null){
            holder.topic.setText("");
            holder.topicTitle.setVisibility(View.GONE);
        }
        else {
            holder.topic.setText(group.getTopic());
            holder.topicTitle.setVisibility(View.VISIBLE);

        }

        if(group.getLocation() == null){
            holder.location.setText("");
            holder.locationTitle.setVisibility(View.GONE);
        }
        else {
            holder.location.setText(group.getTopic());
            holder.locationTitle.setVisibility(View.VISIBLE);
        }

        if(group.getDescription()== null){
            holder.description.setText("");
            holder.descriptionTitle.setVisibility(View.GONE);
        }
        else
        {
            holder.description.setText(group.getTopic());
            holder.descriptionTitle.setVisibility(View.VISIBLE);
        }
    }

    private void setSubjectImage(Group group, GroupViewHolder holder) {
        // show right image based on the subject
        switch (group.getSubject()){
            case "Language" :
                //replace by the right image
                holder.subjectImage.setBackgroundResource(R.drawable.logo);
                break;

            case "Programming" :
                holder.subjectImage.setBackgroundResource(R.drawable.logo);
                break;

            case "Maths" :
                holder.subjectImage.setBackgroundResource(R.drawable.logo);
                break;

            case "Business and Economics" :
                holder.subjectImage.setBackgroundResource(R.drawable.logo);
                break;

            case "Engineering" :
                holder.subjectImage.setBackgroundResource(R.drawable.logo);
                break;

            case "Natural Science" :
                holder.subjectImage.setBackgroundResource(R.drawable.logo);
                break;

            case "Law and Political Science" :
                holder.subjectImage.setBackgroundResource(R.drawable.logo);
                break;

            case "Art and Music" :
                holder.subjectImage.setBackgroundResource(R.drawable.logo);
                break;

            case "Other" :
                holder.subjectImage.setBackgroundResource(R.drawable.logo);
                break;

            default :
                holder.subjectImage.setBackgroundResource(R.drawable.logo);
                break;
        }
    }

    private void setJoinGroupButton (final Group group, GroupViewHolder holder) {
        // Join group button
        mFunction = FirebaseFunctions.getInstance();

        holder.joinGroupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("TAG" , "Join Group!");
                //join the group via Cloud functions
                // go somewhere in the app
                String groupId = group.getGroupId();
                mFunction.getHttpsCallable("dbGroupsJoin").call(groupId);
            }
        });
    }
}
