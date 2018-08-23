package harshadavolvocode.com.volvo_weatherforecast_harshada.activity;


import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class splashActivityTest {

    @Rule
    public ActivityTestRule<splashActivity> mActivityTestRule = new ActivityTestRule<>(splashActivity.class);

    @Test
    public void splashActivityTest() {
    }

}
