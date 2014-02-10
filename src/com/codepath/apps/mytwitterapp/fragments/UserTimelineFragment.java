package com.codepath.apps.mytwitterapp.fragments;

import org.json.JSONArray;

import android.os.Bundle;

import com.codepath.apps.mytwitterapp.MyTwitterApp;
import com.codepath.apps.mytwitterapp.models.Tweet;
import com.codepath.apps.mytwitterapp.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;

public class UserTimelineFragment extends TweetsListFragment {

	//String name;
	User user;
	Long uid;
	
	//private OnItemSelectedListener listener;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		Bundle args = getArguments();
		this.user = (User) args.getSerializable("user");
		//Log.d("DEBUG", this.user.getScreenName().toString());

		updateUserTimeline(-1);
	}
	
	public void updateUserTimeline(long maxId) {
		MyTwitterApp.getRestClient().getAdditionalUserTimeline(
				user.getScreenName(),
				maxId,
				new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONArray jsonTweets) {
				getAdapter().addAll(Tweet.fromJson(jsonTweets));
			}
		});
	}
	
	@Override
	protected void customLoadMoreDataFromApi(int totalItemsCount) {
		// TODO Auto-generated method stub
		
		if (totalItemsCount != 1) {
			Tweet theTweetData = (Tweet) lvTweets
					.getItemAtPosition(totalItemsCount - 1);
			
			updateUserTimeline(theTweetData.getUid() - 1);
				
		}
	}
	
	@Override
	protected void fetchTimelineAsync(int i) {
		// TODO Auto-generated method stub
		updateUserTimeline(-1);
	}

	public static UserTimelineFragment newInstance(int i, String name) {
		// TODO Auto-generated method stub
		//return null;
		UserTimelineFragment utf = new UserTimelineFragment();
		Bundle b = new Bundle();
		b.putInt("myInt", i);
		b.putString("myString", name);
		utf.setArguments(b);
		return utf;
	}
}
