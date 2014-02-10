package com.codepath.apps.mytwitterapp.models;

import java.io.Serializable;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "User")
public class User extends Model implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -996313931775605751L;

	@Column(name = "name", index = true)
	private String name;
	
	@Column(name = "uid", index = true)
	private long uid;
	
	@Column(name = "screen_name", index = true)
	private String screenName;
	
	@Column(name = "profile_image_url", index = true)
	private String profileImageUrl;
	
	@Column(name = "num_tweets", index = true)
	private int numTweets;
	
	@Column(name = "followers_count", index = true)
	private int followersCount;
	
	@Column(name = "friends_count", index = true)
	private int friendsCount;
	
	@Column(name = "description", index = true)
	private String description;
	
	public User() {
		super();
	}

    public User(String name, long uid, String screenName,
			String profileImageUrl, int numTweets, int followersCount,
			int friendsCount) {
		super();
		this.name = name;
		this.uid = uid;
		this.screenName = screenName;
		this.profileImageUrl = profileImageUrl;
		this.numTweets = numTweets;
		this.followersCount = followersCount;
		this.friendsCount = friendsCount;
	}

    public List<Tweet> tweets() {
    	return getMany(Tweet.class, "Tweet");
    }

	public String getName() {
        return name;
    }

    public long getUid() {
        return uid;
    }

    public String getScreenName() {
        return screenName;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public int getNumTweets() {
        return numTweets;
    }

    public int getFollowersCount() {
        return followersCount;
    }

    public int getFriendsCount() {
        return friendsCount;
    }
    
    public String getTagline() {
    	return description;
    }

    public static User fromJson(JSONObject json) {
        User u = new User();
        try {
        	u.name = json.getString("name");
        	u.uid = json.getLong("id");
        	u.screenName = json.getString("screen_name");
        	u.profileImageUrl = json.getString("profile_image_url");
        	u.numTweets = json.getInt("statuses_count");
        	u.followersCount = json.getInt("followers_count");
        	u.friendsCount = json.getInt("friends_count");
        	u.description = json.getString("description");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return u;
    }


}