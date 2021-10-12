package cz.johnyapps.adastraone_task.activities;

import cz.johnyapps.adastraone_task.R;

public class MainActivity extends BottomNavigationActivity {
    @Override
    public int[] getFragmentIds() {
        return new int[]{R.id.randomActivityFragment, R.id.allActivitiesFragment};
    }
}