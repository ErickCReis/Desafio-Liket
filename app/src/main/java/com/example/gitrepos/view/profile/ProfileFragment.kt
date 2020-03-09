package com.example.gitrepos.view.profile


import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.gitrepos.R
import com.example.gitrepos.model.profile.Profile
import com.example.gitrepos.model.profile.ProfilesDatabase
import kotlinx.android.synthetic.main.fragment_details.*
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment : Fragment(), ProfileView {

    private lateinit var presenterProfile: ProfilePresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenterProfile = getPresenter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        swipe_profile.setOnRefreshListener {
            swipe_profile.isRefreshing = false
            presenterProfile.getData()
        }

        presenterProfile.showData()

        profile_toolbar.title = "Perfil"
        profile_toolbar.setNavigationOnClickListener {
            requireActivity().onBackPressed()
        }
        activity!!.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)


        update_name_button.setOnClickListener {
            val nome = profile_name_edit.text.toString()

            if(nome != "") {
                val profile = ProfilesDatabase.getDatabase(requireContext()).profilesDao().getProfile()
                profile.name = nome
                ProfilesDatabase.getDatabase(requireContext()).profilesDao().update(profile)
                activity!!.onBackPressed()
            }
        }

        profile_link_button.setOnClickListener {
            val url = ProfilesDatabase.getDatabase(requireContext()).profilesDao().getProfile().link
            val link = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(link)
        }

        photo.setOnClickListener {
            findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToMyDialogFragment())
        }
    }

    override fun onResume() {
        super.onResume()
        presenterProfile.showData()
    }



    override fun loadProfile(profile: Profile) {
        profile_name.text = profile.name

        if(profile.avatar == null) {
            Glide.with(requireContext())
                .asBitmap()
                .load(profile.avatarUrl)
                .circleCrop()
                .placeholder(R.drawable.ic_radio_button)
                .error(R.drawable.ic_error_black)
                .into(profile_image)
        } else {
            val bitmap = BitmapFactory.decodeByteArray(
                profile.avatar, 0,
                profile.avatar!!.size
            )
            profile_image.setImageBitmap(bitmap)
        }

    }

    private fun getPresenter(): ProfilePresenter{
        val database = ProfilesDatabase.getDatabase(requireContext())
        return ProfilePresenterImpl(this, database)
    }
}
