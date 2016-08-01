package cn.saymagic.entity;

import org.json.JSONObject;

/**
 * Created by saymagic on 16/6/8.
 */
public class WebLog {

    private String sessionId;

    private String request;

    private Object response;

    private long cost;

    public WebLog(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public Object getResponse() {
        return response;
    }

    public void setResponse(Object response) {
        this.response = response;
    }

    public long getCost() {
        return cost;
    }

    public void setCost(long cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        JSONObject object = new JSONObject();
        object.put("request", getRequest());
        object.put("response", getResponse());
        object.put("sessionId", getSessionId());
        return object.toString();
    }
}
