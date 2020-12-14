package ru.shtrm.familyfinder.ui.profile.view

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.support.annotation.NonNull
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.realm.Realm
import kotlinx.android.synthetic.main.fragment_profile.view.*
import ru.shtrm.familyfinder.R
import ru.shtrm.familyfinder.data.database.AuthorizedUser
import ru.shtrm.familyfinder.data.database.repository.user.User
import ru.shtrm.familyfinder.ui.base.view.BaseFragment
import ru.shtrm.familyfinder.ui.profile.interactor.ProfileMVPInterator
import ru.shtrm.familyfinder.ui.profile.presenter.ProfileMVPPresenter
import ru.shtrm.familyfinder.util.FileUtils
import javax.inject.Inject

class ProfileFragment : BaseFragment(), ProfileFragmentMVPView {
    private val CAMERA_REQUEST = 103
    private val ACTIVITY_PHOTO = 101
    private var mainContext: Context? = null

    companion object {
        internal val TAG = "ProfileFragment"

        fun newInstance(): ProfileFragment {
            return ProfileFragment()
        }
    }

    @Inject
    internal lateinit var presenter: ProfileMVPPresenter<ProfileFragmentMVPView, ProfileMVPInterator>

    private val TAG = "ProfileFragment"

    override fun setUp() {}

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_profile, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainContext = this.context
        val authUser = AuthorizedUser.instance
        view.email_text.setText(authUser.login)
        view.user_text_name.setText(authUser.username)

        val path = FileUtils.getPicturesDirectory(this.context!!)
        val avatar = authUser.image
        if (avatar!=null && avatar != "") {
            view.user_image.setImageBitmap(FileUtils.getBitmapByPath(path, avatar))
        }
        view.user_image.setOnClickListener { checkPermissionCamera(this.context!!) }

        view.user_text_name.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                authUser.username = s.toString()
                val realm = Realm.getDefaultInstance()
                realm.executeTransaction { realmB ->
                    val user = realmB.where(User::class.java).equalTo("login", authUser.login).findFirst()
                    if (user != null) {
                        user.username = s.toString()
                        presenter.sendUserRequest(user)
                    }
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        presenter.onAttach(this)
    }

    override fun onDestroyView() {
        presenter.onDetach()
        super.onDestroyView()
    }

    private fun checkPermissionCamera(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (context.checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(arrayOf(Manifest.permission.CAMERA), CAMERA_REQUEST)
                return
            } else {
                val intent = Intent(Intent.ACTION_GET_CONTENT)
                intent.type = "image/*"
                startActivityForResult(intent, ACTIVITY_PHOTO)
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, @NonNull permissions: Array<String>, @NonNull grantResults: IntArray) {
        if (requestCode == CAMERA_REQUEST) {
            if (permissions[0] == Manifest.permission.CAMERA && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                try {
                    val intent = Intent(Intent.ACTION_GET_CONTENT)
                    intent.type = "image/*"
                    startActivityForResult(intent, ACTIVITY_PHOTO)
                } catch (e: ActivityNotFoundException) {
                    e.printStackTrace()
                }
            }
        }
    }

    /**
     * Handle the scanning result.
     * @param requestCode The request code.
     * @param resultCode The result code.
     * @param data The result.
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            ACTIVITY_PHOTO -> if (resultCode == RESULT_OK) {
                val userBitmap: Bitmap? = presenter.storeImage (this.context!!,data)
                if (userBitmap!=null)
                    view!!.user_image.setImageBitmap(userBitmap)
            }
        }
    }
}