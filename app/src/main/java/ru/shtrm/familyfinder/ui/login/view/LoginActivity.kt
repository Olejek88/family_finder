package ru.shtrm.familyfinder.ui.login.view

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login.*
import ru.shtrm.familyfinder.R
import ru.shtrm.familyfinder.data.database.FamilyRealm
import ru.shtrm.familyfinder.ui.base.view.BaseActivity
import ru.shtrm.familyfinder.ui.login.interactor.LoginMVPInteractor
import ru.shtrm.familyfinder.ui.login.presenter.LoginMVPPresenter
import ru.shtrm.familyfinder.ui.main.view.MainActivity
import ru.shtrm.familyfinder.ui.register.view.RegisterActivity
import ru.shtrm.familyfinder.util.AppConstants
import javax.inject.Inject


class LoginActivity : BaseActivity(), LoginMVPView {

    @Inject
    internal lateinit var presenter: LoginMVPPresenter<LoginMVPView, LoginMVPInteractor>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkPermission()
        FamilyRealm.init(this,"family.realm")
        val str: String? = presenter.getUserName()
        if (presenter.checkUserLogin()) {
            openMainActivity()
        }
        setContentView(R.layout.activity_login)
        presenter.onAttach(this)
        setOnClickListeners()
    }

    override fun onDestroy() {
        presenter.onDetach()
        super.onDestroy()
    }

    override fun onFragmentDetached(tag: String) {
    }

    override fun onFragmentAttached() {
    }

    override fun showValidationMessage(errorCode: Int) {
        when (errorCode) {
            AppConstants.EMPTY_EMAIL_ERROR -> Toast.makeText(this, getString(R.string.empty_email_error_message), Toast.LENGTH_LONG).show()
            AppConstants.INVALID_EMAIL_ERROR -> Toast.makeText(this, getString(R.string.invalid_email_error_message), Toast.LENGTH_LONG).show()
            AppConstants.EMPTY_PASSWORD_ERROR -> Toast.makeText(this, getString(R.string.empty_password_error_message), Toast.LENGTH_LONG).show()
            AppConstants.LOGIN_FAILURE -> Toast.makeText(this, getString(R.string.login_failure), Toast.LENGTH_LONG).show()
        }
    }

    override fun openMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun openRegisterActivity() {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun setOnClickListeners() {
        btnServerLogin.setOnClickListener { presenter.onServerLoginClicked(et_email.text.toString(), et_password.text.toString(), this) }
        btnServerRegisterLink.setOnClickListener { presenter.onServerRegisterClicked() }
    }

    private fun checkPermission() {
        val EXTERNAL_STORAGE_ACCESS = 102
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (applicationContext.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION), EXTERNAL_STORAGE_ACCESS)
                return
            }
        }
    }
}