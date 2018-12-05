package com.spiderindia.departmentsofhighway.Adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.spiderindia.departmentsofhighway.Fragmentss.AddBridgeFragment;
import com.spiderindia.departmentsofhighway.Fragmentss.ConditionFormFiveFrgmnt;
import com.spiderindia.departmentsofhighway.Fragmentss.ConditionFormFourFrgmnt;
import com.spiderindia.departmentsofhighway.Fragmentss.ConditionFormOneFrgmnt;
import com.spiderindia.departmentsofhighway.Fragmentss.ConditionFormSixFrgmnt;
import com.spiderindia.departmentsofhighway.Fragmentss.ConditionFormThreeFrgmnt;
import com.spiderindia.departmentsofhighway.Fragmentss.ConditionFormTwoFrgmnt;
import com.spiderindia.departmentsofhighway.Fragmentss.InventaryFormFourFrgmnt;
import com.spiderindia.departmentsofhighway.Fragmentss.InventaryFormOneFrgmnt;
import com.spiderindia.departmentsofhighway.Fragmentss.InventaryFormThreeFrgmnt;
import com.spiderindia.departmentsofhighway.Fragmentss.InventaryFormTwoFrgmnt;
import com.spiderindia.departmentsofhighway.Fragmentss.MaintenanceFormOneFrgmnt;

/**
 * Created by pyr on 08-Jun-18.
 */

public class ViewPagerForHome extends FragmentPagerAdapter {

    AddBridgeFragment addBrdgFragment;
    InventaryFormOneFrgmnt Invntry1Frag;
    InventaryFormTwoFrgmnt Invntry2Frag;
    InventaryFormThreeFrgmnt Invntry3Frag;
    InventaryFormFourFrgmnt Invntry4Frag;
    ConditionFormOneFrgmnt condtn1Frag;
    ConditionFormTwoFrgmnt condtn2Frag;
    ConditionFormThreeFrgmnt condtn3Frag;
    ConditionFormFourFrgmnt condtn4Frag;
    ConditionFormFiveFrgmnt condtn5Frag;
    ConditionFormSixFrgmnt condtn6Frag;
    MaintenanceFormOneFrgmnt maintance1Frag;
    Context context;
    int tabCount;
    //add bridge - 1
    //inventry - 4
    //condition - 6
    //maintainance -1
    public ViewPagerForHome(Context context, FragmentManager fm) {
        super(fm);
        this.tabCount=12;
        context=context;
        addBrdgFragment = new AddBridgeFragment();
        Invntry1Frag = new InventaryFormOneFrgmnt();
        Invntry2Frag = new InventaryFormTwoFrgmnt();
        Invntry3Frag = new InventaryFormThreeFrgmnt();
        Invntry4Frag = new InventaryFormFourFrgmnt();
        condtn1Frag = new ConditionFormOneFrgmnt();
        condtn2Frag = new ConditionFormTwoFrgmnt();
        condtn3Frag = new ConditionFormThreeFrgmnt();
        condtn4Frag = new ConditionFormFourFrgmnt();
        condtn5Frag = new ConditionFormFiveFrgmnt();
        condtn6Frag = new ConditionFormSixFrgmnt();
        maintance1Frag = new MaintenanceFormOneFrgmnt();

    }

    @Override
    public Fragment getItem(int position)
    {
        Fragment fragment = null;
        switch(position)
        {
            case 0:
                fragment = addBrdgFragment;
                break;
            case 1:
                fragment = Invntry1Frag;
                break;
            case 2:
                fragment = Invntry2Frag;
                break;
            case 3:
                fragment = Invntry3Frag;
                break;
            case 4:
                fragment = Invntry4Frag;
                break;
            case 5:
                fragment = condtn1Frag;
                break;
            case 6:
                fragment = condtn2Frag;
                break;
            case 7:
                fragment = condtn3Frag;
                break;
            case 8:
                fragment = condtn4Frag;
                break;
            case 9:
                fragment = condtn5Frag;
                break;
            case 10:
                fragment = condtn6Frag;
                break;
            case 11:
                fragment = maintance1Frag;
                break;
            default:
                // return null;
        }
        return fragment;
    }
    @Override
    public int getCount() {
        return tabCount;
    }
}
