package ru.shtrm.familyfinder.ui.profile.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_profile.view.*
import ru.shtrm.familyfinder.R
import ru.shtrm.familyfinder.data.database.AuthorizedUser
import ru.shtrm.familyfinder.ui.base.view.BaseFragment
import ru.shtrm.familyfinder.ui.profile.interactor.ProfileMVPInterator
import ru.shtrm.familyfinder.ui.profile.presenter.ProfileMVPPresenter
import javax.inject.Inject

class ProfileFragment : BaseFragment(), ProfileFragmentMVPView {

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
        val authUser = AuthorizedUser.instance;
        view.email_text.text = authUser.login
        view.user_text_name.text = authUser.username
        presenter.onAttach(this)
    }

    override fun onDestroyView() {
        presenter.onDetach()
        super.onDestroyView()
    }

    //override fun dismissDialog() = super.dismissDialog(TAG)
    //internal fun show(fragmentManager: FragmentManager) = super.show(fragmentManager, TAG)

}