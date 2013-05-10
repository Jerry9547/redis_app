package com.redis_server.web.action;

import java.util.List;

import net.sf.json.JSONObject;

import com.redis_server.entity.UserInf;


public class UserInfAction extends BaseAction {

	private static final long serialVersionUID = -457712169019468515L;
	
	private Integer uid;
	private String sessionId;
	
	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getUserInf(){
		try {
			UserInf userInf=userInfService.findById(uid);
			if (userInf==null) {
				this.writeJson("UID Error", true);
			}
			else {
				JSONObject js=new JSONObject();
				js.put("user", userInf);
				this.writeCorrectJson(js.toString(), true);
			}
			return NONE;
		} catch (Exception e) {
			this.writeErrorJson(this.EXCEPTIONCODE_ERROR, true);
			return NONE;
		}
	}
	
	public String getUserId(){
		try {
			Integer _uuid =userInfService.getUidBySessionId(sessionId);
			if (_uuid!=null&&_uuid>0) {
				JSONObject js=new JSONObject();
				js.put("uuid", _uuid);
				this.writeCorrectJson(js.toString(), true);
			}
			else{
				this.writeJson("SID Error",true);
			}
			return NONE;
		} catch (Exception e) {
			this.writeErrorJson(this.EXCEPTIONCODE_ERROR, true);
			return NONE;
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public String getUserList(){
		try {
			List<UserInf> userList=userInfService.findAll();
			JSONObject js=new JSONObject();
			js.put("users", userList);
			this.writeCorrectJson(js.toString(), true);
			return NONE;
		} catch (Exception e) {
			e.printStackTrace();
			this.writeErrorJson(this.EXCEPTIONCODE_ERROR, true);
			return NONE;
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public String getUserListSharded(){
		try {
			List<UserInf> userList=userInfService.findAlldoSharded();
			JSONObject js=new JSONObject();
			js.put("users", userList);
			this.writeCorrectJson(js.toString(), true);
			return NONE;
		} catch (Exception e) {
			this.writeErrorJson(this.EXCEPTIONCODE_ERROR, true);
			return NONE;
		}
		
	}
	

}
