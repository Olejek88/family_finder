package ru.shtrm.familyfinder.ui.register.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_register.*
import ru.shtrm.familyfinder.R
import ru.shtrm.familyfinder.ui.base.view.BaseActivity
import ru.shtrm.familyfinder.ui.login.view.LoginActivity
import ru.shtrm.familyfinder.ui.main.view.MainActivity
import ru.shtrm.familyfinder.ui.register.interactor.RegisterMVPInteractor
import ru.shtrm.familyfinder.ui.register.presenter.RegisterMVPPresenter
import ru.shtrm.familyfinder.util.AppConstants
import javax.inject.Inject

class RegisterActivity : BaseActivity(), RegisterMVPView {

    @Inject
    internal lateinit var presenter: RegisterMVPPresenter<RegisterMVPView, RegisterMVPInteractor>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
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
            AppConstants.EMPTY_NAME_ERROR -> Toast.makeText(this, getString(R.string.name_error_message), Toast.LENGTH_LONG).show()
        }
    }

    override fun openMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun openLoginActivity() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun setOnClickListeners() {
        btnServerLoginLink.setOnClickListener { presenter.onServerLoginClicked() }
        btnServerRegister.setOnClickListener { presenter.onServerRegisterClicked(etr_email.text.toString(), etr_password.text.toString(), etr_username.text.toString()) }
    }
}