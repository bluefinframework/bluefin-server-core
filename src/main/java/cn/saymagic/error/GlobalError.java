package cn.saymagic.error;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by saymagic on 16/5/21.
 */
class BluefinThrowable {

    public final HttpStatus status;

    public final String type;

    private Throwable throwable = null;

    public BluefinThrowable(HttpStatus status, String des) {
        this.status = status;
        this.type = des;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public BluefinThrowable setThrowable(Throwable throwable) {
        this.throwable = throwable;
        return this;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getType() {
        return type;
    }

    public Map toMap() {
        Map map = new HashMap<>();
        map.put("status", status);
        map.put("type", type);
        if (throwable != null) {
            map.put("reason", throwable.getMessage());
        }
        return map;
    }

    @Override
    public String toString() {
        JSONObject json = new JSONObject();
        try {
            json.put("status", status);
            json.put("type", type);
            if (throwable != null) {
                json.put("reason", throwable.getMessage());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json.toString();
    }

    public BluefinThrowable selfClone() {
        return new BluefinThrowable(status, type);
    }

}

public class GlobalError extends Exception {

    public static final int NO_ERROR = 0;

    public static final int UNKNOW_ERROR = 1;

    public static final int PARAMETER_ERROR = 2;

    public static final int IO_ERROR = 3;

    public static final int MAPPING_FILE_NOT_FOUND = 4;

    public static final int PROGUARD_FILE_NOT_FOUND = 5;

    public static final int APP_FILE_NOT_FOUND = 6;

    public static final int INFO_FILE_NOT_FOUND = 7;

    public static final int PACKAGE_NOT_FOUND = 8;

    public static final int ANDROID_HOME_IS_NOT_SETUP = 9;
    private static final Map<Integer, BluefinThrowable> mapping = new HashMap<Integer, BluefinThrowable>(){{
        put(NO_ERROR, new BluefinThrowable(HttpStatus.OK, "NO_ERROR"));
        put(UNKNOW_ERROR, new BluefinThrowable(HttpStatus.INTERNAL_SERVER_ERROR, "UNKNOW_ERROR"));
        put(PARAMETER_ERROR, new BluefinThrowable(HttpStatus.BAD_REQUEST, "PARAMETER_ERROR"));
        put(IO_ERROR, new BluefinThrowable(HttpStatus.INTERNAL_SERVER_ERROR, "IO_ERROR"));
        put(MAPPING_FILE_NOT_FOUND, new BluefinThrowable(HttpStatus.NOT_FOUND, "MAPPING_FILE_NOT_FOUND"));
        put(PROGUARD_FILE_NOT_FOUND, new BluefinThrowable(HttpStatus.NOT_FOUND, "PROGUARD_FILE_NOT_FOUND"));
        put(APP_FILE_NOT_FOUND, new BluefinThrowable(HttpStatus.NOT_FOUND, "APP_FILE_NOT_FOUND"));
        put(PACKAGE_NOT_FOUND, new BluefinThrowable(HttpStatus.NOT_FOUND, "PACKAGE_NOT_FOUND"));
        put(ANDROID_HOME_IS_NOT_SETUP, new BluefinThrowable(HttpStatus.SERVICE_UNAVAILABLE, "ANDROID_HOME_IS_NOT_SETUP"));
    }};

    private HttpStatus HTTP_STATUS;

    private int ERROR_TYPE = 0;

    private Throwable EXT_THROWABLE = null;

    public GlobalError (int type){
        this(type, null);
    }

    public GlobalError (int type, Throwable throwable){
        super(throwable);
        if (!mapping.containsKey(type)) {
            this.ERROR_TYPE = UNKNOW_ERROR;
        }
        this.ERROR_TYPE = type;
        this.EXT_THROWABLE = throwable;
    }

    public int getHttpStatus() {
        return mapping.get(ERROR_TYPE).getStatus().value();
    }

    public int getErrorType() {
        return ERROR_TYPE;
    }

    public Map toMap() {
        return mapping.get(ERROR_TYPE)
                .selfClone()
                .setThrowable(EXT_THROWABLE)
                .toMap();
    }

    @Override
    public String toString() {
      return mapping.get(ERROR_TYPE)
              .selfClone()
              .setThrowable(EXT_THROWABLE)
              .toString();
    }

    @Override
    public String getMessage() {
        return toString();
    }
}
