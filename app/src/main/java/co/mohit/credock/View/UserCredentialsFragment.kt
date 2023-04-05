package co.mohit.credock.View

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.mohit.credock.Controller.RecyclerAdapterCredCollection
import co.mohit.credock.Controller.SingleCredCardViewModel
import co.mohit.credock.R
import co.mohit.credock.databinding.FragmentUserCredentialsBinding


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class UserCredentialsFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null

    private lateinit var userCredentialsBinding:FragmentUserCredentialsBinding
    private lateinit var recyclerViewCredCard:RecyclerView
    private lateinit var recyclerAdapterCredCollection: RecyclerAdapterCredCollection
    private lateinit var credCardsCollectionList:ArrayList<SingleCredCardViewModel>

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            UserCredentialsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        initializeList()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        userCredentialsBinding = FragmentUserCredentialsBinding.inflate(inflater,container,false)
        // Inflate the layout for this fragment
        return userCredentialsBinding.root
    }

    override fun onStart() {
        super.onStart()
        recyclerViewCredCard = userCredentialsBinding.rcvUserCredCollection
        recyclerViewCredCard.layoutManager = GridLayoutManager(this.context,2)
        initializeRecyclerAdapterForCredCards()
    }

    fun initializeRecyclerAdapterForCredCards()
    {
        recyclerAdapterCredCollection = RecyclerAdapterCredCollection(this.requireContext(),credCardsCollectionList)
        userCredentialsBinding.rcvUserCredCollection.adapter = recyclerAdapterCredCollection
    }

    fun initializeList()
    {
        credCardsCollectionList = ArrayList()
        credCardsCollectionList.add(SingleCredCardViewModel("BK101/1","SBI"))
        credCardsCollectionList.add(SingleCredCardViewModel("MA102/2","Gmail"))
        credCardsCollectionList.add(SingleCredCardViewModel("INS103/4","Niva Bupa"))
    }


}