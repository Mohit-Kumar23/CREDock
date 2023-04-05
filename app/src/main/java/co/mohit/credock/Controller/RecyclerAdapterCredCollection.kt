package co.mohit.credock.Controller

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.mohit.credock.R

class RecyclerAdapterCredCollection(var context: Context, var credsCardList:ArrayList<SingleCredCardViewModel>)
    :RecyclerView.Adapter<ViewHolderSingleCredCard>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderSingleCredCard {

        return ViewHolderSingleCredCard(LayoutInflater.from(context).inflate(R.layout.custom_credential_cardview,parent,false))
    }

    override fun getItemCount(): Int {
        return credsCardList.size
    }

    override fun onBindViewHolder(holder: ViewHolderSingleCredCard, position: Int) {
        holder.tv_credId.text = credsCardList[position].credID
        holder.tv_platformName.text = credsCardList[position].platformName

        holder.imgBtn_deleteCred.setOnClickListener(View.OnClickListener {

        })
    }
}