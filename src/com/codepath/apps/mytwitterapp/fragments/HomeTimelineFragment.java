package com.codepath.apps.mytwitterapp.fragments;

import java.util.ArrayList;

import org.json.JSONArray;

import android.os.Bundle;
import android.util.Log;

import com.activeandroid.ActiveAndroid;
import com.codepath.apps.mytwitterapp.MyTwitterApp;
import com.codepath.apps.mytwitterapp.TweetsAdapter;
import com.codepath.apps.mytwitterapp.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

public class HomeTimelineFragment extends TweetsListFragment {

	//FragmentActivity listener;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		// TODO Auto-generated method stub
		// if (!TimelineActivity.isInternetAvailable(this)) {
		/*
		 * myList = Tweet.getAll(); adapter = new
		 * TweetsAdapter(getBaseContext(), myList);
		 * lvTweets.setAdapter(adapter);
		 */

		// } else {
		MyTwitterApp.getRestClient().getHomeTimeline(
				new JsonHttpResponseHandler() {
					@Override
					public void onSuccess(JSONArray jsonTweets) {
						// tweets = Tweet.fromJson(jsonTweets);
						
						Log.d("DEBUG", "adapter count: " + getAdapter().getCount());
						//if (getAdapter().getCount() == 0)
						getAdapter().clear();
						getAdapter().addAll(Tweet.fromJson(jsonTweets));
						
						//Log.d("DEBUG", "count: " + getAdapter().getCount());
						
						ActiveAndroid.beginTransaction();
						try {
							for (Tweet tweetInstance : Tweet.fromJson(jsonTweets)) {
								tweetInstance.getUser().save();
								tweetInstance.save();
							}
							ActiveAndroid.setTransactionSuccessful();
						} finally {
							ActiveAndroid.endTransaction();
						}
						// super.onSuccess(arg0);
					}
				});

		// }
	}
	
	@Override
	protected void customLoadMoreDataFromApi(int totalItemsCount) {
		// TODO Auto-generated method stub
		
		//Log.d("DEBUG", "count 2: " + totalItemsCount);
		//Log.d("DEBUG", theTweetData.toString());

		if (totalItemsCount != 1) {
			Tweet theTweetData = (Tweet) lvTweets
					.getItemAtPosition(totalItemsCount - 1);
			
			MyTwitterApp.getRestClient().getAdditionalHomeTimeline(
				theTweetData.getUid() - 1, new JsonHttpResponseHandler() {

					public void onSuccess(JSONArray json) {
						Log.d("DEBUG", json.toString());
						//tweets = Tweet.fromJson(json);
						getAdapter().addAll(Tweet.fromJson(json));
						//adapter.notifyDataSetChanged();

						ActiveAndroid.beginTransaction();
						try {
							for (Tweet tweetInstance : Tweet.fromJson(json)) {
								tweetInstance.getUser().save();
								tweetInstance.save();
							}
							ActiveAndroid.setTransactionSuccessful();
						} finally {
							ActiveAndroid.endTransaction();
						}
					}

					public void onFailure(Throwable e) {
						Log.d("DEBUG", "Fetch timeline error: " + e.toString());
					}
				});
		}
	}
	
	@Override
	public void fetchTimelineAsync(int i) {
		MyTwitterApp.getRestClient().getHomeTimeline(
				new JsonHttpResponseHandler() {
					@Override
					public void onSuccess(JSONArray jsonTweets) {
						Log.d("DEBUG", jsonTweets.toString());
						ArrayList<Tweet> tweets = Tweet.fromJson(jsonTweets);

						adapter = new TweetsAdapter(getActivity(), tweets);
						lvTweets.setAdapter(adapter);
						lvTweets.onRefreshComplete();
						ActiveAndroid.beginTransaction();
						try {
							for (Tweet tweetInstance : Tweet.fromJson(jsonTweets)) {
								tweetInstance.getUser().save();
								tweetInstance.save();
							}
							ActiveAndroid.setTransactionSuccessful();
						} finally {
							ActiveAndroid.endTransaction();
						}
						// super.onSuccess(jsonTweets);
					}

				});
	}

	/*
	 * public static boolean isInternetAvailable(Context context) {
	 * ConnectivityManager cm = (ConnectivityManager) context
	 * .getSystemService(Context.CONNECTIVITY_SERVICE);
	 * 
	 * NetworkInfo activeNetwork = cm.getActiveNetworkInfo(); return
	 * activeNetwork != null && activeNetwork.isConnectedOrConnecting(); }
	 

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		this.listener = (FragmentActivity) activity;
	}*/
}
