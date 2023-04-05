package co.mohit.credock.Controller

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.custom_credential_cardview.view.*

class ViewHolderSingleCredCard(itemView: View): RecyclerView.ViewHolder(itemView)
{
    val tv_platformName = itemView.tv_credentialPlatform
    val tv_credId = itemView.tv_credenrialID
    val imgBtn_deleteCred = itemView.imgBtn_deleteCredential
}