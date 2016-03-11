package environment.th.com.thenvi.frg;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * Created by Administrator on 2016/3/8.
 */
public class BaseFragment extends Fragment {

    /**
     *  加载新的fragment
     */
    public void loadFragment(Fragment fragment,int fragId, String tagStr){
        FragmentManager fragmentManager= getFragmentManager();
        FragmentTransaction transaction= fragmentManager.beginTransaction();
        transaction.replace(fragId, fragment, tagStr);
        transaction.addToBackStack(null);
        transaction.commit();
        // transaction.commitAllowingStateLoss();
    }


}
