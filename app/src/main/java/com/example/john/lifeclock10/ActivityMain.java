package com.example.john.lifeclock10;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zhy.m.permission.MPermissions;
import com.zhy.m.permission.PermissionDenied;
import com.zhy.m.permission.PermissionGrant;

import static com.example.john.lifeclock10.UtilsFinalArguments.REQUEST_USUAL;

public class ActivityMain extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    //基本的UI部件
    private Toolbar toolbar;
    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;
    private TextView toolbarTitle;

    //碎片管理FragmentManager
    private FragmentManager mFragmentManager = null;
    private FragmentTransaction mFragmentTransaction = null;
    private FragmentMain fragmentMain = null;
    private FragmentSettings fragmentSettings = null;

    private TextView drawerUserNameTxv;  //侧边栏上部姓名
    private ImageView drawerUserIcon;  //侧边栏上部头像

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setImmersiveMode();

        initView();
        initPermission();
        initClickEvent();
        initData();
    }

    private void initView() {
        //Toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        //隐藏Toolbar默认居左显示的Title
        if (toolbar != null) {
            toolbar.setTitle("");
            toolbar.setBackgroundResource(R.drawable.background_toolbar);
        } else {
            Toast.makeText(this, "toolbar == null!", Toast.LENGTH_SHORT).show();
        }
        toolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);

        //DrawerLayout
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        //NavigationView
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            navigationView.setNavigationItemSelectedListener(this);
        } else {
            Toast.makeText(this, "navigationView == null!", Toast.LENGTH_SHORT).show();
        }

        //初始化mFragmentManager
        mFragmentManager = getSupportFragmentManager();

        //nav_header
        View headerLayout = navigationView.getHeaderView(0);   //没有这一行时，drawerUserNameTxv的findviewById的结果是null
        drawerUserNameTxv = (TextView) headerLayout.findViewById(R.id.txt_name);
        drawerUserIcon = (ImageView) headerLayout.findViewById(R.id.imageView);
        drawerUserIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //do something
            }
        });
    }

    private void initPermission() {
        //获取定位权限
        //MPermissions.requestPermissions(ActivityMain.this, REQUEST_ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION);
        //获取准确信息
        //MPermissions.requestPermissions(ActivityMain.this, REQUEST_ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION);

    }

    private void initClickEvent() {

    }

    private void initData() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        //设置菜单项可被选中并设置为选中状态
        //item.setCheckable(true);
        //item.setChecked(true);

        switch (id){
            case R.id.nav_camera:
                hideAllFragment(mFragmentTransaction);
                if(fragmentMain == null){
                    fragmentMain = new FragmentMain();
                    mFragmentTransaction.add(R.id.main_fragment_layout,fragmentMain);
                }else{
                    mFragmentTransaction.show(fragmentMain);
                }
                //修改标题栏标题
                toolbarTitle = (TextView) findViewById(R.id.toolbar_title);
                toolbarTitle.setText("Title");
                break;
            case R.id.nav_gallery:
                hideAllFragment(mFragmentTransaction);
                if (fragmentSettings == null){
                    fragmentSettings = new FragmentSettings();
                    mFragmentTransaction.add(R.id.main_fragment_layout,fragmentSettings);
                }else {
                    mFragmentTransaction.show(fragmentSettings);
                }
                //修改标题栏标题
                toolbarTitle = (TextView) findViewById(R.id.toolbar_title);
                toolbarTitle.setText(R.string.action_settings);
                break;
            default:
                break;
        }
        mFragmentTransaction.commit();
        //点击某一项菜单项取消选中状态并关闭drawer
        //item.setCheckable(false);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * 实现连续两次点击退出APP
     */
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if ((System.currentTimeMillis() - UtilsFinalArguments.clickTime) > 2000) {
                Toast.makeText(this, "再按一次后退键退出程序", Toast.LENGTH_SHORT).show();
                UtilsFinalArguments.clickTime = System.currentTimeMillis();
            } else {
                ActivityCollector.finishAll();
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // 是否触发按键为back键
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            onBackPressed();
            return true;
        } else { // 如果不是back键正常响应
            return super.onKeyDown(keyCode, event);
        }
    }

    //用于申请权限
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
    {
        MPermissions.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    @PermissionGrant(REQUEST_USUAL)
    public void requestSdcardSuccess()
    {
        Toast.makeText(this, "GRANT ACCESS PERMISSION!", Toast.LENGTH_SHORT).show();
    }

    @PermissionDenied(REQUEST_USUAL)
    public void requestSdcardFailed()
    {
        Toast.makeText(this, "DENY ACCESS PERMISSION!", Toast.LENGTH_SHORT).show();
    }

    /**
     * 隐藏所有Fragment
     * @param fragmentTransaction
     */
    private void hideAllFragment(FragmentTransaction fragmentTransaction){
        /**
         * 4
         */
        if (fragmentMain != null){
            fragmentTransaction.hide(fragmentMain);
        }
        if (fragmentSettings != null){
            fragmentTransaction.hide(fragmentSettings);
        }
    }

    /**
     * 对于不同版本的安卓系统实现沉浸式体验
     */
    private void setImmersiveMode() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //5.0 全透明实现
            //getWindow.setStatusBarColor(Color.TRANSPARENT)
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            //调用静态方法设置沉浸式状态栏
            UtilsLibrary.setStateBarColor(this);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            //4.4 全透明状态栏
            //底部导航栏透明
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            //顶部状态栏透明
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

            ViewGroup contentLayout = (ViewGroup) this.findViewById(android.R.id.content);
            View contentChild = contentLayout.getChildAt(0);
            contentChild.setFitsSystemWindows(false);  // 这里设置成侵占状态栏，反而不会侵占状态栏，太坑
            UtilsLibrary.setupStatusBarView(this, contentLayout, Color.parseColor("#4A90E2"));
            //隐藏底部导航栏
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
            //int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN;  此时将同时隐藏状态栏
            decorView.setSystemUiVisibility(uiOptions);
        }
    }

    /**
     * 添加了actionStart()方法后——最佳启动Activity
     * @param context
     */
    public static void actionStart(Context context){
        Intent intent = new Intent(context,ActivityMain.class);
        context.startActivity(intent);
    }
}
