package ru.shtrm.familyfinder.ui.family.view

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.realm.Realm
import kotlinx.android.synthetic.main.fragment_family.view.*
import ru.shtrm.familyfinder.R
import ru.shtrm.familyfinder.data.database.repository.user.User
import ru.shtrm.familyfinder.ui.base.view.BaseFragment
import ru.shtrm.familyfinder.ui.family.FamilyAdapter
import ru.shtrm.familyfinder.ui.family.interactor.FamilyMVPInterator
import ru.shtrm.familyfinder.ui.family.presenter.FamilyMVPPresenter
import javax.inject.Inject


class FamilyFragment : BaseFragment(), FamilyFragmentMVPView {

    companion object {
        internal val TAG = "FamilyFragment"

        fun newInstance(): FamilyFragment {
            return FamilyFragment()
        }

    }

    @Inject
    internal lateinit var presenter: FamilyMVPPresenter<FamilyFragmentMVPView, FamilyMVPInterator>

    private val TAG = "FamilyFragment"

    override fun setUp() {}

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_family, container, false)
        initView(view.context, view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.onAttach(this)
    }

    override fun onDestroyView() {
        presenter.onDetach()
        super.onDestroyView()
    }

    private fun initView(context: Context, view: View) {
        val adapter: FamilyAdapter?
        val recyclerView: RecyclerView = view.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)
        val realm = Realm.getDefaultInstance()
        val users = realm.where(User::class.java).findAll()
        adapter = FamilyAdapter(context, users)
        recyclerView.adapter = adapter
    }
}