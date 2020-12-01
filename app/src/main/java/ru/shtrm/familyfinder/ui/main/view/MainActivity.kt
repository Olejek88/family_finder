package ru.shtrm.familyfinder.ui.main.view

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
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
import ru.shtrm.familyfinder.ui.feed.view.FeedActivity
import ru.shtrm.familyfinder.ui.login.view.LoginActivity
import ru.shtrm.familyfinder.ui.main.interactor.MainMVPInteractor
import ru.shtrm.familyfinder.ui.main.presenter.MainMVPPresenter
import ru.shtrm.familyfinder.ui.rate.view.RateUsDialog
import ru.shtrm.familyfinder.util.extension.addFragment
import ru.shtrm.familyfinder.util.extension.removeFragment
import javax.inject.Inject

class MainActivity : BaseActivity(), MainMVPView, NavigationView.OnNavigationItemSelectedListener,
        HasSupportFragmentInjector {

    @Inject
    internal lateinit var presenter: MainMVPPresenter<MainMVPView, MainMVPInteractor>
    @Inject
    internal lateinit var fragmentDispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpDrawerMenu()
        presenter.onAttach(this)
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
                presenter.onDrawerOptionLogoutClick()
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
        lockDrawer()
        //supportFragmentManager.addFragment(R.id.frame_container, AboutFragment.newInstance(), AboutFragment.TAG)
    }

    override fun openMapFragment() {
        lockDrawer()
        //supportFragmentManager.addFragment(R.id.frame_container, AboutFragment.newInstance(), AboutFragment.TAG)
    }

    override fun openFamilyFragment() {
        lockDrawer()
        //supportFragmentManager.addFragment(R.id.frame_container, AboutFragment.newInstance(), AboutFragment.TAG)
    }

    override fun openAboutFragment() {
        lockDrawer()
        supportFragmentManager.addFragment(R.id.frame_container, AboutFragment.newInstance(), AboutFragment.TAG)
    }

    override fun openFeedActivity() {
        val intent = Intent(this, FeedActivity::class.java)
        startActivity(intent)
    }

    override fun openRateUsDialog() = RateUsDialog.newInstance().let {
        it?.show(supportFragmentManager)
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
}
