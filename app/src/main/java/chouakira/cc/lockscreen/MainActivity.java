package chouakira.cc.lockscreen;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;

public class MainActivity extends Activity {

    DevicePolicyManager mDPM;
    ComponentName comName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDPM = (DevicePolicyManager)getSystemService(DEVICE_POLICY_SERVICE);
        comName = new ComponentName(this, MyAdminReceiver.class);

        // https://developer.android.com/guide/topics/admin/device-admin.html
        if(mDPM.isAdminActive(comName)) {
            mDPM.lockNow();
        } else {
            Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
            intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, comName);
            intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,
                    this.getString(R.string.message));
            startActivity(intent);
        }

        // https://yanlu.me/android-m6-0-permission-chasm/
//        if(!Settings.System.canWrite(this)){
//            Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS,
//                    Uri.parse("package:" + getPackageName()));
//            startActivityForResult(intent, 1);
//        }
//        Settings.System.putInt(this.getContentResolver(), Settings.System.SCREEN_OFF_TIMEOUT, 1000);

        finish();
    }

}
