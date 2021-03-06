package ru.shtrm.familyfinder.ui.family.view

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_family.view.*
import ru.shtrm.familyfinder.R
import ru.shtrm.familyfinder.ui.base.view.BaseFragment
import ru.shtrm.familyfinder.ui.family.FamilyAdapter
import ru.shtrm.familyfinder.ui.family.interactor.FamilyMVPInterator
import ru.shtrm.familyfinder.ui.family.presenter.FamilyMVPPresenter
import javax.inject.Inject


class FamilyFragment : BaseFragment(), FamilyFragmentMVPView {
    private var mContext: Context? = null

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
        Log.d("Family","onCreateView")
        initView(view.context, view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("Family","onViewCreated")
        presenter.onAttach(this)
    }

    override fun onDestroyView() {
        presenter.onDetach()
        super.onDestroyView()
    }

    private fun initView(context: Context, view: View) {
        mContext = context
        val recyclerView: RecyclerView = view.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)
        val users = presenter.getUsers()
        if(users!=null) {
            users.addChangeListener { results ->
                recyclerView.swapAdapter(FamilyAdapter(context, results), false);
            }
            recyclerView.adapter = FamilyAdapter(context, users)
        }
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
    }
}