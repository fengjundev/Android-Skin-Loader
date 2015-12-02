package cn.feng.skin.manager.util;

import android.util.Log;

/**
 * Log Utils for debug
 * 
 * @author fengjun
 */
public class L {

	private static final boolean DEBUG  = true;
	private static final String  TAG    = "SkinLoader";
	private static final String  LINE    = "________________________________________________________";
	
    private L() {
        throw new AssertionError();
    }
    
    public static void i(String msg){
    	if(DEBUG){
    		Log.i(TAG, LINE);
    		Log.i(TAG, msg);
    	}
    }
    
    public static void d(String msg){
    	if(DEBUG){
    		Log.d(TAG, LINE);
    		Log.d(TAG, msg);
    	}
    }
    
    public static void w(String msg){
    	if(DEBUG){
    		Log.w(TAG, LINE);
    		Log.w(TAG, msg);
    	}
    }
    
    public static void e(String msg){
    	if(DEBUG){
    		Log.e(TAG, LINE);
    		Log.e(TAG, msg);
    	}
    }
    
    public static void i(String tag, String msg){
    	if(DEBUG){
    		Log.i(tag, LINE);
    		Log.i(tag, msg);
    	}
    }
    
    public static void d(String tag, String msg){
    	if(DEBUG){
    		Log.d(tag, LINE);
    		Log.d(tag, msg);
    	}
    }
    
    public static void w(String tag, String msg){
    	if(DEBUG){
    		Log.w(tag, LINE);
    		Log.w(tag, msg);
    	}
    }
    
    public static void e(String tag, String msg){
    	if(DEBUG){
    		Log.e(tag, LINE);
    		Log.e(tag, msg);
    	}
    }
}
