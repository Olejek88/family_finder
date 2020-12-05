package ru.shtrm.familyfinder.ui.main.view

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.view.MenuItem
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_navigation.*
import kotlinx.android.synthetic.main.nav_header_navigation.view.*
import ru.shtrm.familyfinder.R
import ru.shtrm.familyfinder.ui.about.view.AboutFragment
import ru.shtrm.familyfinder.ui.base.view.BaseActivity
import ru.shtrm.familyfinder.ui.family.view.FamilyFragment
import ru.shtrm.familyfinder.ui.login.view.LoginActivity
import ru.shtrm.familyfinder.ui.main.interactor.MainMVPInteractor
import ru.shtrm.familyfinder.ui.main.presenter.MainMVPPresenter
import ru.shtrm.familyfinder.ui.map.view.MapFragment
import ru.shtrm.familyfinder.ui.profile.view.ProfileFragment
import ru.shtrm.familyfinder.util.extension.addFragment
import ru.shtrm.familyfinder.util.extension.removeFragment
import javax.inject.Inject

class MainActivity : BaseActivity(), MainMVPView, NavigationView.OnNavigationItemSelectedListener,
        HasSupportFragmentInjector {
    private var aboutFragment: AboutFragment? = null
    private var mapFragment: MapFragment? = null
    private var familyFragment: FamilyFragment? = null
    private var profileFragment: ProfileFragment? = null
    private var selectedNavItem = 0
    private val KEY_NAV_ITEM = "CURRENT_NAV_ITEM"

    @Inject
    internal lateinit var presenter: MainMVPPresenter<MainMVPView, MainMVPInteractor>
    @Inject
    internal lateinit var fragmentDispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpDrawerMenu()
        setUpBottomBar()
        checkpermission()

        if (savedInstanceState != null) {
            mapFragment = supportFragmentManager.getFragment(savedInstanceState, "MapFragment") as MapFragment
            familyFragment = supportFragmentManager.getFragment(savedInstanceState, "FamilyFragment") as FamilyFragment
            profileFragment = supportFragmentManager.getFragment(savedInstanceState, "ProfileFragment") as ProfileFragment
            aboutFragment = supportFragmentManager.getFragment(savedInstanceState, "AboutFragment") as AboutFragment
            selectedNavItem = savedInstanceState.getInt(KEY_NAV_ITEM)
        } else {
            mapFragment = supportFragmentManager.findFragmentById(R.id.frame_container) as MapFragment?
            if (mapFragment == null) {
                mapFragment = MapFragment.newInstance()
            }
            familyFragment = supportFragmentManager.findFragmentById(R.id.frame_container) as FamilyFragment?
            if (familyFragment == null) {
                familyFragment = FamilyFragment.newInstance()
            }
            profileFragment = supportFragmentManager.findFragmentById(R.id.frame_container) as ProfileFragment?
            if (profileFragment == null) {
                profileFragment = ProfileFragment.newInstance()
            }
            aboutFragment = supportFragmentManager.findFragmentById(R.id.frame_container) as AboutFragment?
            if (aboutFragment == null) {
                aboutFragment = AboutFragment.newInstance()
            }
        }
        supportFragmentManager.addFragment(R.id.frame_container, FamilyFragment.newInstance(), FamilyFragment.TAG)
        presenter.onAttach(this)
    }

    private fun checkpermission() {
        val EXTERNAL_STORAGE_ACCESS = 102
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (applicationContext.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.ACCESS_FINE_LOCATION), EXTERNAL_STORAGE_ACCESS)
                return
            }
        }
    }
    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        }
        val fragment = supportFragmentManager.findFragmentByTag(AboutFragment.TAG)
        fragment?.let { onFragmentDetached(AboutFragment.TAG) } ?: super.onBackPressed()
    }

    override fun onDestroy() {
        presenter.onDetach()
        super.onDestroy()
    }

    override fun onFragmentDetached(tag: String) {
        supportFragmentManager?.removeFragment(tag = tag)
        unlockDrawer()
    }

    override fun onFragmentAttached() {
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_profile -> {
                presenter.onDrawerOptionProfileClick()
            }
            R.id.nav_map -> {
                presenter.onDrawerOptionMapClick()
            }
            R.id.nav_family -> {
                presenter.onDrawerOptionFamilyClick()
            }
            R.id.nav_about -> {
                presenter.onDrawerOptionAboutClick()
            }
            R.id.nav_logout -> {
                presenter.onDrawerOptionLogoutClick(this)
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun lockDrawer() = drawerLayout?.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)

    override fun unlockDrawer() = drawerLayout?.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)

    override fun inflateUserDetails(userDetails: Pair<String?, String?>) {
        navView.getHeaderView(0).nav_name.text = userDetails.first
        navView.getHeaderView(0).nav_email.text = userDetails.second
    }

    override fun openLoginActivity() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun openProfileFragment() {
        //supportFragmentManager.addFragment(R.id.frame_container, profileFragment, ProfileFragment.TAG)
        changeFragment("profileFragment")
        toolbar.title = resources.getString(R.string.profile)
    }

    override fun openMapFragment() {
        supportFragmentManager.addFragment(R.id.frame_container, MapFragment.newInstance(), MapFragment.TAG)
    }

    override fun openFamilyFragment() {
        supportFragmentManager.addFragment(R.id.frame_container, FamilyFragment.newInstance(), FamilyFragment.TAG)
    }

    override fun openAboutFragment() {
        supportFragmentManager.addFragment(R.id.frame_container, AboutFragment.newInstance(), AboutFragment.TAG)
    }

    override fun supportFragmentInjector() = fragmentDispatchingAndroidInjector

    private fun setUpDrawerMenu() {
        setSupportActionBar(toolbar)
        val toggle = ActionBarDrawerToggle(
                this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        navView.setNavigationItemSelectedListener(this)

    }

    private fun setUpBottomBar() {
        bottomBar.setOnTabSelectListener({ tabId ->
            val tr = supportFragmentManager.beginTransaction()
            when (tabId) {
                R.id.menu_map -> {
                    presenter.onDrawerOptionMapClick()
                }
                R.id.menu_family -> {
                    presenter.onDrawerOptionFamilyClick()
                }
                R.id.menu_profile -> {
                    presenter.onDrawerOptionProfileClick()
                }
            }
            tr.commit()
        })
    }

    internal fun changeFragment(selectedFragment: String) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.hide(aboutFragment)
        fragmentTransaction.hide(familyFragment)
        fragmentTransaction.hide(mapFragment)
        fragmentTransaction.hide(profileFragment)

        if (selectedFragment === "aboutFragment")
            fragmentTransaction.show(aboutFragment)
        if (selectedFragment === "familyFragment")
            fragmentTransaction.show(familyFragment)
        if (selectedFragment === "mapFragment")
            fragmentTransaction.show(mapFragment)
        if (selectedFragment === "profileFragment")
            fragmentTransaction.show(profileFragment)
        fragmentTransaction.commit()
    }
}
