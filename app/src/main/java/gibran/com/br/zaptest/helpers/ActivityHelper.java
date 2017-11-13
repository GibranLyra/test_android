package gibran.com.br.zaptest.helpers;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import org.joda.time.DateTime;

/**
 * Created by gibranlyra on 24/08/17.
 */

public class ActivityHelper {

    public static void addFragmentToActivity(@NonNull FragmentManager fragmentManager,
                                             @NonNull Fragment fragment, int frameId) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(frameId, fragment);
        transaction.commitAllowingStateLoss();
    }

    public static String getFormatedDate(String date) {
        DateTime dateTime = new DateTime(date);
        String formatedDate = dateTime.toString("dd/MM/yyyy - HH:mm");
        return formatedDate;
    }

}
