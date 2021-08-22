package com.example.project1.ui.chat;



import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.project1.MainActivity;
import com.example.project1.PostDetail;
import com.example.project1.R;
import com.example.project1.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ChatFragment extends Fragment {

    private DatabaseReference dbUser;
    public ArrayList<User> allUser = new ArrayList<>();
    private UserAdapter adapter;
    String sender;

    public View onCreateView(@NonNull LayoutInflater inflater,
                                ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.activity_chat, container, false);

        dbUser = FirebaseDatabase.getInstance().getReference();
        MainActivity parentActivity = (MainActivity)getActivity();
        sender = parentActivity.getUserName();

        Query query = dbUser.child("User").orderByChild("userName");
        query.addListenerForSingleValueEvent(valueEventListener);

        adapter = new UserAdapter(getContext(), allUser);
        SearchView userSearch = root.findViewById(R.id.userSearchView);
        ListView userList = root.findViewById(R.id.userListView);
        userList.setAdapter(adapter);

        adapter.notifyDataSetChanged();
        adapter = new UserAdapter(getContext(), allUser);
        userList.setAdapter(adapter);

        userSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                ArrayList<User> result = findUser(getAllUser(), s);
                adapter = new UserAdapter(getContext(), result);
                userList.setAdapter(adapter);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        return root;
    }


    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            if (snapshot.exists()) {
                for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                    User user = userSnapshot.getValue(User.class);
                    allUser.add(user);
                }
                adapter.notifyDataSetChanged();
            } else {
                String message =  "No data here.";
                Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {
            Toast.makeText(getContext(), "DatabaseError, please try again later", Toast.LENGTH_LONG ).show();

        }
    };

    /**
     * The method use to get all user store in the firebase
     * @return
     */
    public ArrayList getAllUser() {
        return this.allUser;
    }


    /**
     * The method will retrieve all user whose name is match with the given key
     * @param all -- arrayList which contain all user in the firebase
     * @param name -- key for finding matching user
     * @return -- arrayList which contain all matched user
     */
    public ArrayList<User>findUser(ArrayList<User>all, String name) {
        ArrayList<User> availableUser = new ArrayList<>();
        for (User user : all) {
            if (user.getUserName().contains(name)) {
                availableUser.add(user);
            }
        }
        return availableUser;
    }


    /**
     * Adapter class for user
     */
    private class UserAdapter extends BaseAdapter {

        private ArrayList<User> userView;
        private LayoutInflater inflater;

        public UserAdapter(Context context, ArrayList<User> userArrayList) {
            this.inflater = LayoutInflater.from(context);
            this.userView = userArrayList;
        }

        @Override
        public int getCount() {
            return userView.size();
        }

        @Override
        public Object getItem(int i) {
            return userView.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int position, View view, ViewGroup viewGroup) {
            ViewHolder myView;


            if (view == null) {
                view = inflater.inflate(R.layout.user_item, null);
                myView = new ViewHolder();
                myView.userName = (TextView)view.findViewById(R.id.user_name);
                myView.userImage = (ImageView)view.findViewById(R.id.profile);
                myView.userLayout = (RelativeLayout)view.findViewById(R.id.userListLayout);
                view.setTag(myView);

            } else {
                myView = (ViewHolder)view.getTag();
            }

            // write user information
            myView.userName.setText(userView.get(position).getUserName());
            myView.userLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getContext(), Chat.class);
                    intent.putExtra("receiver", myView.userName.getText());
                    intent.putExtra("sender", sender);
                    getContext().startActivity(intent);
                }
            });
            return view;
        }
    }

    /**
     * Class for the user holder
     */
    private class ViewHolder {
        private RelativeLayout userLayout;
        private ImageView userImage;
        private TextView userName;
    }


}


