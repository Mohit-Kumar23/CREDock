package co.mohit.credock.Controller

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import co.mohit.credock.R
import co.mohit.credock.View.IndividualCredentialFragment

class RecyclerAdapterIndividualCred(val context: Context,val frag:Fragment,var credArrayList:ArrayList<SingleCredentialViewModel>)
    :RecyclerView.Adapter<ViewHolderIndividualCred>() {

    private lateinit var callBackListener:IUserIndividualCred

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderIndividualCred {
        callBackListener =  frag as IUserIndividualCred
        return ViewHolderIndividualCred(LayoutInflater.from(context).inflate(R.layout.custom_credential_input_layout,parent,false))
    }

    override fun getItemCount(): Int {
        return credArrayList.size
    }

    override fun onBindViewHolder(holder: ViewHolderIndividualCred, position: Int) {
            holder.inp_credTagName.setText(credArrayList[position].credentialTagName.toCharArray(),0,credArrayList[position].credentialTagName.length)
            holder.inp_credValue.setText(credArrayList[position].credentialValue.toCharArray(),0,credArrayList[position].credentialValue.length)

            holder.doneBtn.setOnClickListener(View.OnClickListener {
                if(!holder.inp_credTagName.text.isNullOrEmpty() and !holder.inp_credValue.text.isNullOrEmpty())
                {
                    Toast.makeText(context,"Credential Added",Toast.LENGTH_SHORT).show()
                    callBackListener.appendCredArrayList(position,holder.inp_credTagName.text.toString(),holder.inp_credValue.text.toString(),false)
                }
            })
            holder.removeBtn.setOnClickListener(View.OnClickListener {
                callBackListener.appendCredArrayList(position,null,null,true)
            })
    }
}