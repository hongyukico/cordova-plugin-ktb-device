package com.ktb.plugin;

import android.content.Context;
import android.provider.Settings;
import android.view.WindowManager;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.TimeZone;

import static com.ktb.plugin.util.IntenetUtil.getNetworkByType;

/**
 * 项目名称：android
 * 类描述：
 * 创建人：HONGYU.LIU
 * 创建时间：2016/9/23 15:23
 * 修改人：HONGYU.LIU
 * 修改时间：2016/9/23 15:23
 * 修改备注：
 */
public class DeviceInfoPlugin extends CordovaPlugin {
    public static final String TAG = "DeviceInfoPlugin";
    public static String platform;                            // Device OS
    public static String uuid;                                // Device UUID

    private static final String ANDROID_PLATFORM = "Android";
    private static final String AMAZON_PLATFORM = "amazon-fireos";
    private static final String AMAZON_DEVICE = "Amazon";

    private Context context;

    /**
     * Constructor.
     */
    public DeviceInfoPlugin() {
    }

    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);
        getUuid();
        context = cordova.getActivity();
    }


    /**
     * Get the device's Universally Unique Identifier (UUID).
     *
     * @return
     */
    public String getUuid() {
        String uuid = Settings.Secure.getString(this.cordova.getActivity().getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
        return uuid;
    }

    public String getModel() {
        String model = android.os.Build.MODEL;
        return model;
    }

    public String getProductName() {
        String productname = android.os.Build.PRODUCT;
        return productname;
    }

    public String getManufacturer() {
        String manufacturer = android.os.Build.MANUFACTURER;
        return manufacturer;
    }

    public String getSerialNumber() {
        String serial = android.os.Build.SERIAL;
        return serial;
    }

    /**
     * Get the OS version.
     *
     * @return
     */
    public String getOSVersion() {
        String osversion = android.os.Build.VERSION.RELEASE;
        return osversion;
    }

    public String getSDKVersion() {
        @SuppressWarnings("deprecation")
        String sdkversion = android.os.Build.VERSION.SDK;
        return sdkversion;
    }

    public String getTimeZoneID() {
        TimeZone tz = TimeZone.getDefault();
        return (tz.getID());
    }

    /**
     * Function to check if the device is manufactured by Amazon
     *
     * @return
     */
    public boolean isAmazonDevice() {
        if (android.os.Build.MANUFACTURER.equals(AMAZON_DEVICE)) {
            return true;
        }
        return false;
    }

    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if ("getDeviceInfo".equals(action)) {
            JSONObject r = new JSONObject();
            r.put("uuid", uuid);
            r.put("appVersion", this.getOSVersion());
            r.put("systemType", this.getPlatform());
            r.put("connectionType", this.getConnectionType());
            r.put("screenWidth", getScreenWidth(context));
            r.put("screenHeight", getScreenHeight(context));
            r.put("model", this.getModel());
            r.put("manufacturer", this.getManufacturer());
            r.put("isVirtual", this.isVirtual());
            r.put("serial", this.getSerialNumber());
            String pageParam = "";
            if (cordova.getActivity().getIntent().hasExtra("pageParam"))
                pageParam = cordova.getActivity().getIntent().getStringExtra("pageParam");
            r.put("pageParam", pageParam);
            callbackContext.success(r.toString());
        } else {
            return false;
        }
        return true;
    }

    public boolean isVirtual() {
        return android.os.Build.FINGERPRINT.contains("generic") ||
                android.os.Build.PRODUCT.contains("sdk");
    }

    /**
     * Get the OS name.
     *
     * @return
     */
    public String getPlatform() {
        String platform;
        if (isAmazonDevice()) {
            platform = AMAZON_PLATFORM;
        } else {
            platform = ANDROID_PLATFORM;
        }
        return platform;
    }

    public String getConnectionType() {
        String connectionType = getNetworkByType(context);
        return connectionType;
    }


    public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        return width;
    }

    public static int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        int height = wm.getDefaultDisplay().getHeight();
        return height;
    }
}
