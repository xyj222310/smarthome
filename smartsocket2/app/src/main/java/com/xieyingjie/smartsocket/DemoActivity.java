package com.xieyingjie.smartsocket;

import android.animation.Animator;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.animation.OvershootInterpolator;

import com.xieyingjie.smartsocket.navigation.AHBottomNavigation;
import com.xieyingjie.smartsocket.navigation.AHBottomNavigationAdapter;
import com.xieyingjie.smartsocket.navigation.AHBottomNavigationItem;
import com.xieyingjie.smartsocket.navigation.AHBottomNavigationViewPager;

import java.util.ArrayList;

public class DemoActivity extends AppCompatActivity {
	private FloatingActionButton floatingActionButton;
	private DemoFragment currentFragment;
	private DemoViewPagerAdapter adapter;
	private AHBottomNavigationAdapter navigationAdapter;
	private ArrayList<AHBottomNavigationItem> bottomNavigationItems = new ArrayList<>();
	private boolean useMenuResource = true;
	private int[] tabColors;

	// UI
	private AHBottomNavigationViewPager viewPager;
	private AHBottomNavigation bottomNavigation;
	private Toolbar toolbar =null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		supportRequestWindowFeature(Window.FEATURE_NO_TITLE) ; //去掉原来的actionbar,5.0之后都用  toolbar
		setContentView(R.layout.activity_main);
		initUI();
	}
	/**
	 * Init UI
	 */
	private void initUI() {
		toolbar=  (Toolbar)findViewById(R.id.toolbar);//自定义顶部工具栏
		toolbar.setSubtitleTextColor(getResources().getColor(android.R.color.holo_red_light));
		toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
		toolbar.inflateMenu(R.menu.toolbar_menu);

		floatingActionButton = (FloatingActionButton)findViewById(R.id.floating_action_button); //自定义 浮动button
		bottomNavigation = (AHBottomNavigation) findViewById(R.id.bottom_navigation);  //自定义底部导航对象
		viewPager = (AHBottomNavigationViewPager) findViewById(R.id.view_pager);//  自定义viewpager
		viewPager.setPagingEnabled(true);
		if (useMenuResource) { //默认为true
			tabColors = getApplicationContext().getResources().getIntArray(R.array.tab_colors);
			navigationAdapter = new AHBottomNavigationAdapter(this, R.menu.bottom_navigation_menu_3);
			navigationAdapter.setupWithBottomNavigation(bottomNavigation, tabColors);
		} else {
			AHBottomNavigationItem item1 = new AHBottomNavigationItem(R.string.tab_1, R.drawable.ic_apps_black_24dp, R.color.color_tab_1);
			AHBottomNavigationItem item2 = new AHBottomNavigationItem(R.string.tab_2, R.mipmap.ic_maps_local_bar, R.color.color_tab_2);
			AHBottomNavigationItem item3 = new AHBottomNavigationItem(R.string.tab_3, R.mipmap.ic_maps_local_user, R.color.color_tab_3);
			bottomNavigationItems.add(item1);  // 添加 三个底部导航
			bottomNavigationItems.add(item2);
			bottomNavigationItems.add(item3);
			bottomNavigation.addItems(bottomNavigationItems);
		}

		bottomNavigation.setAccentColor(Color.parseColor("#F63D2B"));
		bottomNavigation.setInactiveColor(Color.parseColor("#747474"));

		bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
			@Override
			public boolean onTabSelected(int position, boolean wasSelected) {// 底部导航tab监听
				if (currentFragment == null) {
					currentFragment = adapter.getCurrentFragment();
				}
				if (wasSelected) {
					currentFragment.refresh();
					return true;
				}
				if (currentFragment != null) {
					currentFragment.willBeHidden();
				}
				viewPager.setCurrentItem(position, false);
				currentFragment = adapter.getCurrentFragment();
				currentFragment.willBeDisplayed();

				bottomNavigation.setNotification("", 1);
				floatingActionButton.setAlpha(0f);
				floatingActionButton.setScaleX(0f);
				floatingActionButton.setScaleY(0f);
				floatingActionButton.animate()
						.alpha(1)
						.scaleX(1)
						.scaleY(1)
						.setDuration(300)
						.setInterpolator(new OvershootInterpolator())
						.setListener(new Animator.AnimatorListener() {
							@Override
							public void onAnimationStart(Animator animation) {

							}

							@Override
							public void onAnimationEnd(Animator animation) {
								floatingActionButton.animate()
										.setInterpolator(new LinearOutSlowInInterpolator())
										.start();
							}

							@Override
							public void onAnimationCancel(Animator animation) {

							}

							@Override
							public void onAnimationRepeat(Animator animation) {

							}
						})
						.start();
				if(position==0){
					toolbar.setTitle("设备");
					floatingActionButton.setVisibility(View.INVISIBLE);
				}
				else if (position == 1) { // 第二页浮动按钮.
					floatingActionButton.setVisibility(View.VISIBLE);
					toolbar.setTitle("资讯");
				} else {
					if (floatingActionButton.getVisibility() == View.VISIBLE) { // animation动画，我不懂
						floatingActionButton.animate()
								.alpha(0)
								.scaleX(0)
								.scaleY(0)
								.setDuration(300)
								.setInterpolator(new LinearOutSlowInInterpolator())
								.setListener(new Animator.AnimatorListener() {
									@Override
									public void onAnimationStart(Animator animation) {

									}

									@Override
									public void onAnimationEnd(Animator animation) {
										floatingActionButton.setVisibility(View.GONE);
									}

									@Override
									public void onAnimationCancel(Animator animation) {
										floatingActionButton.setVisibility(View.GONE);
									}

									@Override
									public void onAnimationRepeat(Animator animation) {

									}
								})
								.start();
					}
					toolbar.setTitle("更多");
				}
				return true;
			}
		});

		bottomNavigation.setOnNavigationPositionListener(new AHBottomNavigation.OnNavigationPositionListener() {
			@Override public void onPositionChange(int y) {
				Log.d("DemoActivity", "BottomNavigation Position: " + y);
			}
		});
		viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
			}
			@Override
			public void onPageSelected(int position) {
				currentFragment = adapter.getCurrentFragment();
				viewPager.setCurrentItem(position, false);
				currentFragment.willBeDisplayed();
				bottomNavigation.setCurrentItem(position);
			}
			@Override
			public void onPageScrollStateChanged(int state) {
			}
		});
		viewPager.setOffscreenPageLimit(3);
		adapter = new DemoViewPagerAdapter(getSupportFragmentManager());
		viewPager.setAdapter(adapter);
		currentFragment = adapter.getCurrentFragment();
		final Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				bottomNavigation.setNotification("16", 1);
//				Snackbar.make(bottomNavigation, "Snackbar with bottom navigation",
//						Snackbar.LENGTH_SHORT).show();
			}
		}, 3000);
	}
	/**
	 * Return the number of items in the bottom navigation
	 */
	public int getBottomNavigationNbItems() {
		return bottomNavigation.getItemsCount();
	}

}
