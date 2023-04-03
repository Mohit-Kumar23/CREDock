package co.mohit.credock.View

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.mohit.credock.Controller.IUserIndividualCred
import co.mohit.credock.Controller.RecyclerAdapterIndividualCred
import co.mohit.credock.Controller.SingleCredentialViewModel
import co.mohit.credock.R
import co.mohit.credock.databinding.FragmentIndividualCredentialBinding


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class IndividualCredentialFragment : Fragment(),IUserIndividualCred {

    private var param1: String? = null
    private var param2: String? = null
    private lateinit var individualCredentialBinding:FragmentIndividualCredentialBinding
    private lateinit var credArrList:ArrayList<SingleCredentialViewModel>
    private lateinit var recyclerViewIndCred:RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

        credArrList = ArrayList()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        individualCredentialBinding = FragmentIndividualCredentialBinding.inflate(inflater,container,false)
        return individualCredentialBinding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onStart() {
        super.onStart()
        recyclerViewIndCred = individualCredentialBinding.rcvIndividualCred
        recyclerViewIndCred.layoutManager = LinearLayoutManager(this.context,RecyclerView.VERTICAL,false)
    }

    override fun onResume() {
        super.onResume()
        individualCredentialBinding.btnAddNewItem.setOnClickListener(View.OnClickListener {
            if(credArrList.size > 0)
            {
                var prevCredRow = credArrList.get(credArrList.size-1)
                if(!prevCredRow.isCredentialEmpty())
                {
                    addNewCredentialRowToArrayList()
                }
                else
                {
                    Toast.makeText(this.context,"Please fill above credential first",Toast.LENGTH_LONG).show()
                }
            }
            else
            {
                addNewCredentialRowToArrayList()
            }
        })
    }

    private fun addNewCredentialRowToArrayList() {
        credArrList.add(SingleCredentialViewModel("",""))
        val recyclerAdapterIndividualCred = RecyclerAdapterIndividualCred(this.requireContext(),this,credArrList)
        individualCredentialBinding.rcvIndividualCred.adapter = recyclerAdapterIndividualCred
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            IndividualCredentialFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun appendCredArrayList(position: Int, credTagName: String?, credValue: String?,bRemove:Boolean) {
        if(!bRemove) {
            credArrList[position].credentialTagName = credTagName!!
            credArrList[position].credentialValue = credValue!!
        }
        else
        {
            credArrList.removeAt(position)
        }
    }
}