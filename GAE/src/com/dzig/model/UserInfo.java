package com.dzig.model;

import org.json.JSONException;
import org.json.JSONObject;

import com.dzig.api.AuthFilter;
import com.google.appengine.api.users.User;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
public class UserInfo implements Convertable{

	@Id private String id;
	private String nickname;
	private String email;
	private String avatar;

	public UserInfo(){};
	
	public UserInfo(String id, String nickname, String email) {
		this.id = id;
		this.nickname = nickname;
		this.email = email;
	}

	public static UserInfo create(User user){
		//TODO: update user in storage
		if (AuthFilter.DEBUG_TURN_OFF_AUTH) return new UserInfo("100500", "Dudi", "Muldenberg");
		return new UserInfo(user.getUserId(), user.getNickname(), user.getEmail());
	}
	
	@Override
	public JSONObject toJson() throws JSONException {
		JSONObject json = new JSONObject();
		json.put("id", String.valueOf(id));
		json.put("email", String.valueOf(email));
		json.put("nickname", String.valueOf(nickname));
		json.put("avatar", String.valueOf(avatar));
		return json;
	}

}
