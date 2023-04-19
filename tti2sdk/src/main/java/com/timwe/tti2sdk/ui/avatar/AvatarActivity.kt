package com.timwe.tti2sdk.ui.avatar

import android.app.Dialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.FileProvider
import androidx.core.graphics.applyCanvas
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import app.rive.runtime.kotlin.core.Fit
import app.rive.runtime.kotlin.core.Rive
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.tabs.TabLayoutMediator
import com.timwe.tti2sdk.R
import com.timwe.tti2sdk.data.entity.Avatar
import com.timwe.tti2sdk.databinding.ActivityAvatarBinding
import com.timwe.tti2sdk.ui.FragmentId
import com.timwe.tti2sdk.ui.Navigation
import com.timwe.tti2sdk.ui.avatar.fragments.HeadFragment
import com.timwe.tti2sdk.ui.avatar.fragments.HeadFragment.Companion.BOTTOM_CLOTHES
import com.timwe.tti2sdk.ui.avatar.fragments.HeadFragment.Companion.BOTTOM_CLOTHES_COLOR
import com.timwe.tti2sdk.ui.avatar.fragments.HeadFragment.Companion.GENDER
import com.timwe.tti2sdk.ui.avatar.fragments.HeadFragment.Companion.HEAD_EYE_BROWS
import com.timwe.tti2sdk.ui.avatar.fragments.HeadFragment.Companion.HEAD_EYE_COLOR
import com.timwe.tti2sdk.ui.avatar.fragments.HeadFragment.Companion.HEAD_HAIR
import com.timwe.tti2sdk.ui.avatar.fragments.HeadFragment.Companion.HEAD_HAIR_COLOR
import com.timwe.tti2sdk.ui.avatar.fragments.HeadFragment.Companion.HEAD_SKIN_COLOR
import com.timwe.tti2sdk.ui.avatar.fragments.HeadFragment.Companion.PROFILE_NAME
import com.timwe.tti2sdk.ui.avatar.fragments.HeadFragment.Companion.RIDES
import com.timwe.tti2sdk.ui.avatar.fragments.HeadFragment.Companion.RIDES_COLOR
import com.timwe.tti2sdk.ui.avatar.fragments.HeadFragment.Companion.SHOES
import com.timwe.tti2sdk.ui.avatar.fragments.HeadFragment.Companion.SHOES_COLOR
import com.timwe.tti2sdk.ui.avatar.fragments.HeadFragment.Companion.TOP_CLOTHES
import com.timwe.tti2sdk.ui.avatar.fragments.HeadFragment.Companion.TOP_CLOTHES_COLOR
import com.timwe.tti2sdk.ui.dialog.DialogError
import com.timwe.tti2sdk.ui.home.HomeActivity
import com.timwe.utils.onDebouncedListener
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


class AvatarActivity: AppCompatActivity() {

    private lateinit var binding: ActivityAvatarBinding
    private val viewModel: AvatarViewModel by viewModel()
    private val avatarView by lazy(LazyThreadSafetyMode.NONE) { binding.avatar }
    var builder : Dialog? = null
    var builderShare : Dialog? = null
    var bottomSheetDialogInitial: BottomSheetDialog? = null
    var bottomSheetDialogEnd: BottomSheetDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAvatarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRive()
        setupListeners()
    }

    private fun setupBootomSheetInit() {
        bottomSheetDialogInitial = BottomSheetDialog(this)
        bottomSheetDialogInitial?.setCancelable(true)
        bottomSheetDialogInitial?.window?.setBackgroundDrawable(AppCompatResources.getDrawable(this, android.R.color.transparent))
        bottomSheetDialogInitial?.window?.findViewById<FrameLayout>(R.id.design_bottom_sheet)?.background = AppCompatResources.getDrawable(this, R.drawable.background_cardinator)
        bottomSheetDialogInitial?.setContentView(R.layout.bottom_sheet_layout_first_mission)
        bottomSheetDialogInitial?.show()
        bottomSheetDialogInitial?.findViewById<ImageView>(R.id.boosterOneIconProsTextToSheet)?.onDebouncedListener {
            bottomSheetDialogInitial?.dismiss()
        }
    }

    private fun setupBootomSheetEnd() {
        bottomSheetDialogEnd = BottomSheetDialog(this)
        bottomSheetDialogEnd?.setCancelable(true)
        bottomSheetDialogEnd?.window?.setBackgroundDrawable(AppCompatResources.getDrawable(this, android.R.color.transparent))
        bottomSheetDialogEnd?.window?.findViewById<FrameLayout>(R.id.design_bottom_sheet)?.background = AppCompatResources.getDrawable(this, R.drawable.background_cardinator)
        bottomSheetDialogEnd?.setContentView(R.layout.bottom_sheet_layout_end_mission)
        bottomSheetDialogEnd?.show()
        bottomSheetDialogEnd?.findViewById<ImageView>(R.id.boosterOneIconProsTextToSheet)?.onDebouncedListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
            bottomSheetDialogEnd?.dismiss()
        }
        bottomSheetDialogEnd?.findViewById<ImageView>(R.id.daily_two_icon_pros_text)?.onDebouncedListener {
            bottomSheetDialogEnd?.dismiss()
        }
        bottomSheetDialogEnd?.setOnCancelListener {
            val intent = Intent(this, HomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
        }
        bottomSheetDialogEnd?.setOnDismissListener {
            val intent = Intent(this, HomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        setupObservers()
    }

    private fun setupRive() {
        Rive.init(this)
    }

    fun setTabSelected(position: Int){
        val layout = when(position){
            0 -> R.layout.layout_tab_selected_head
            1 -> R.layout.layout_tab_selected_clothes
            2 -> R.layout.layout_tab_selected_shoes
            3 -> R.layout.layout_tab_selected_ride
            else -> 0
        }
        binding.tabLayout.getTabAt(position)?.setCustomView(layout)
    }

    fun setTabUnSelected(position: Int){
        val layout = when(position){
            0 -> R.layout.layout_tab_unselected_head
            1 -> R.layout.layout_tab_unselected_clothes
            2 -> R.layout.layout_tab_unselected_shoes
            3 -> R.layout.layout_tab_unselected_ride
            else -> 0
        }
        binding.tabLayout.getTabAt(position)?.setCustomView(layout)
    }

    private fun setAllTabs() = with(binding){
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            Log.i("meus tabs", "${tab} + ${position}")
        }.attach()
        tabLayout.getTabAt(0)?.setCustomView(R.layout.layout_tab_selected_head)
        tabLayout.getTabAt(1)?.setCustomView(R.layout.layout_tab_unselected_clothes)
        tabLayout.getTabAt(2)?.setCustomView(R.layout.layout_tab_unselected_shoes)
        tabLayout.getTabAt(3)?.setCustomView(R.layout.layout_tab_unselected_ride)
        tabLayout.getTabAt(0)?.select()
    }

    val adapter = AdapterTabFragment(this@AvatarActivity)
    private fun setupView(avatar: Avatar) = with(binding){

        val bundle = Bundle()
        bundle.putSerializable(HeadFragment.AVATAR, avatar)

        if(viewPager.adapter != null){
            adapter.replaceFragment(
                Navigation.getFragmentFromFragmentId(
                    FragmentId.FRAG_HEAD,
                    bundle
                ), TAB_HEAD
            )
            adapter.replaceFragment(Navigation.getFragmentFromFragmentId(FragmentId.FRAG_CLOTHES, bundle), TAB_CLOTHES)
            adapter.replaceFragment(Navigation.getFragmentFromFragmentId(FragmentId.FRAG_SHOES, bundle), TAB_SHOES)
            adapter.replaceFragment(Navigation.getFragmentFromFragmentId(FragmentId.FRAG_RIDE, bundle), TAB_RIDE)
            adapter.notifyItemRangeChanged(0, 3)
            setAllTabs()

            return
        }

        var  mFirstPageCalled = true
        adapter.addFragment(Navigation.getFragmentFromFragmentId(FragmentId.FRAG_HEAD, bundle))
        adapter.addFragment(Navigation.getFragmentFromFragmentId(FragmentId.FRAG_CLOTHES, bundle))
        adapter.addFragment(Navigation.getFragmentFromFragmentId(FragmentId.FRAG_SHOES, bundle))
        adapter.addFragment(Navigation.getFragmentFromFragmentId(FragmentId.FRAG_RIDE, bundle))
        viewPager.adapter = adapter
        viewPager.currentItem = 0
        setAllTabs()

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){

            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
                Log.i("onPageScrollStateChanged", "${state}")
            }

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                Log.i("onPageSelected", "${position}")
                if(mFirstPageCalled){
                    mFirstPageCalled = false

                }else{

                    setTabSelected(position)

                    val myPossitonsUnselected: ArrayList<Int> = arrayListOf(TAB_HEAD, TAB_CLOTHES, TAB_SHOES, TAB_RIDE)
                    myPossitonsUnselected.remove(position)
                    for (item in myPossitonsUnselected){
                        setTabUnSelected(item)
                    }

                }

            }

        })

    }

    private fun setupListeners(){
        binding.btnBackAvatar.onDebouncedListener {
            if(viewModel.checkAvatarEdited()){
                viewModel.cancelAll()
                onBackPressedDispatcher.onBackPressed()
            }else{
                dialogShow()
            }
        }
        binding.btnShareAvatar.onDebouncedListener {
            dialogShare()
        }
        binding.btnSaveAvatar.onDebouncedListener {
            saveAvatarEdited()
        }
        binding.btnRandomize.onDebouncedListener{
            viewModel.getAvatarStructure()
            viewModel.getAvatar(random = true)
        }
        viewModel.getAvatarStructure()
        viewModel.getAvatar()

    }

    /*
    * Set rive image
    *
    * */
    fun setAvatar(inputValueKey: String, inputValue: String){
        avatarView.setNumberState(
            stateMachineName = "Avatar",
            inputName = inputValueKey,
            value = inputValue.toFloat()
        )
    }

    private fun setupObservers() {
        viewModel.avatarStructure.observe(this, Observer { bytes ->
            //avatarView.setRiveBytes(
            //  autoplay = true,
            //  bytes = bytes,
            //  fit = Fit.FILL
            // )

            avatarView.setRiveResource(
                autoplay = true,
                resId = R.raw.avatar_male_design_v6,
                fit = Fit.FILL
            )

        })

        viewModel.avatar.observe(this, Observer { it ->
            setupView(avatar = it)
            mountAvatarImage(avatar = it)
            viewModel.getFistAccessAvatar()
        })

        viewModel.isFirstAccessAvatar.observe(this, Observer {
            if(!it){
                setupBootomSheetInit()
            }
        })

        viewModel.isFirstAccessAvatarSecondDialog.observe(this, Observer {
            if(!it){
                setupBootomSheetEnd()
            }
        })

        viewModel.userandavatar.observe(this, Observer { it ->
            viewModel.equalsAvatar()
            builder?.cancel()
        })

        viewModel.error.observe(this, Observer { it ->
            DialogError(
                this@AvatarActivity,
                it.errorCode!!,
                object : DialogError.ClickListenerDialogError{
                    override fun reloadClickListener() {
                        viewModel.getAvatarStructure()
                        viewModel.getAvatar()
                    }
                }
            )
        })

        viewModel.loading.observe(this, Observer { it ->
            if(it){
                binding.loadingBox.visibility = View.VISIBLE

            }else{
                binding.loadingBox.visibility = View.GONE
                binding.progressBarAvatar.container.visibility = View.GONE
            }
        })
    }

    fun mountAvatarImage(avatar: Avatar){

        setAvatarEdited(key = PROFILE_NAME, avatar.profileName)
        setAvatarEdited(key = GENDER, value = avatar.gender.id.toString())
        setAvatarEdited(key = HEAD_SKIN_COLOR, value = avatar.skinColor.id.toString())
        setAvatarEdited(key = HEAD_HAIR, value = avatar.hair.id.toString())
        setAvatarEdited(key = HEAD_HAIR_COLOR, value = avatar.hairColor.id.toString())
        setAvatarEdited(key = HEAD_EYE_COLOR, value = avatar.eyeColor.id.toString())
        setAvatarEdited(key = HEAD_EYE_BROWS, value = avatar.eyeBrows.id.toString())
        setAvatarEdited(key = TOP_CLOTHES, value = avatar.topClothes.id.toString())
        setAvatarEdited(key = TOP_CLOTHES_COLOR, value = avatar.topClothesColor.id.toString())
        setAvatarEdited(key = BOTTOM_CLOTHES, value = avatar.bottomClothes.id.toString())
        setAvatarEdited(key = BOTTOM_CLOTHES_COLOR, value = avatar.bottomClothesColor.id.toString())
        setAvatarEdited(key = SHOES, value = avatar.shoes.id.toString())
        setAvatarEdited(key = SHOES_COLOR, value = avatar.shoesColor.id.toString())
        setAvatarEdited(key = RIDES, value = avatar.rides.id.toString())
        setAvatarEdited(key = RIDES_COLOR, value = avatar.ridesColor.id.toString())

        setAvatar(
            inputValueKey = viewModel.getInitialPosition(avatar.headCustomizations, HEAD_SKIN_COLOR),
            inputValue = avatar.skinColor.riveInputValue)

        setAvatar(
            inputValueKey = viewModel.getInitialPosition(avatar.headCustomizations, HEAD_HAIR),
            inputValue = avatar.hair.riveInputValue)

        setAvatar(
            inputValueKey = viewModel.getInitialPosition(avatar.headCustomizations, HEAD_HAIR_COLOR),
            inputValue = avatar.hairColor.riveInputValue)

        setAvatar(
            inputValueKey = viewModel.getInitialPosition(avatar.headCustomizations, HEAD_EYE_COLOR),
            inputValue = avatar.eyeColor.riveInputValue)

        setAvatar(
            inputValueKey = viewModel.getInitialPosition(avatar.headCustomizations, HEAD_EYE_BROWS),
            inputValue = avatar.eyeBrows.riveInputValue)

        setAvatar(
            inputValueKey = viewModel.getInitialPosition(avatar.clothesCustomizations, TOP_CLOTHES),
            inputValue = avatar.topClothes.riveInputValue)

        setAvatar(
            inputValueKey = viewModel.getInitialPosition(avatar.clothesCustomizations, TOP_CLOTHES_COLOR),
            inputValue = avatar.topClothesColor.riveInputValue)

        setAvatar(
            inputValueKey = viewModel.getInitialPosition(avatar.clothesCustomizations, BOTTOM_CLOTHES),
            inputValue = avatar.bottomClothes.riveInputValue)

        setAvatar(
            inputValueKey = viewModel.getInitialPosition(avatar.clothesCustomizations, BOTTOM_CLOTHES_COLOR),
            inputValue = avatar.bottomClothesColor.riveInputValue)

        setAvatar(
            inputValueKey = viewModel.getInitialPosition(avatar.shoesCustomizations, SHOES),
            inputValue = avatar.shoes.riveInputValue)

        setAvatar(
            inputValueKey = viewModel.getInitialPosition(avatar.shoesCustomizations, SHOES_COLOR),
            inputValue = avatar.shoesColor.riveInputValue)

        setAvatar(
            inputValueKey = viewModel.getInitialPosition(avatar.ridesCustomizations, RIDES),
            inputValue = avatar.rides.riveInputValue)

        setAvatar(
            inputValueKey = viewModel.getInitialPosition(avatar.ridesCustomizations, RIDES_COLOR),
            inputValue = avatar.ridesColor.riveInputValue)

    }

    /*
    *
    * Set avatar for save
    * Avatar used um post api
    *
    * */
    fun setAvatarEdited(key: String, value: String){
        viewModel.setEditedAvatar(key = key, value = value)
    }

    fun saveAvatarEdited(){
        viewModel.postCreateOrUpdateUser()
    }

    fun dialogShow(){
        if (builder == null){
            builder = Dialog(this)
        }
        val inflater = layoutInflater
        val dialogLayout = inflater.inflate(R.layout.dialog_confirm_changes_avatar, null)
        val btnDoNotSave  = dialogLayout.findViewById<AppCompatButton>(R.id.btnDoNotSave)
        val btnKeppChanges  = dialogLayout.findViewById<AppCompatButton>(R.id.btnKeepChanges)
        btnDoNotSave.setOnClickListener{
            builder?.cancel()
            onBackPressedDispatcher.onBackPressed()
        }
        btnKeppChanges.setOnClickListener{
            saveAvatarEdited()
            builder?.cancel()
        }
        builder?.setContentView(dialogLayout)
        builder?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        builder?.setCancelable(true)
        builder?.show()
    }

    fun dialogShare(){
        if (builderShare == null){
            builderShare = Dialog(this)
        }
        val inflater = layoutInflater
        val dialogLayoutShare = inflater.inflate(R.layout.dialog_share_avatar, null)
        val btnShareProfile = dialogLayoutShare.findViewById<AppCompatButton>(R.id.btnShareProfile)
        val btnCancelProfile = dialogLayoutShare.findViewById<AppCompatButton>(R.id.btnCancelShare)
        val imageViewAvatar = dialogLayoutShare.findViewById<ImageView>(R.id.imageViewShare)
        val progress = dialogLayoutShare.findViewById<ProgressBar>(R.id.progressShare)
        progress.visibility = View.VISIBLE

        val bitmap = avatarView.bitmap
        val uri: Uri?
        if(isExternalStorageWritable()){
            uri = saveImageExternal(bitmap!!)
        }else{
            uri = saveImage(bitmap!!)
        }

        Glide.with(this)
            .load(bitmap)
            .priority(Priority.HIGH)
            .listener(object : RequestListener<Drawable> {

                override fun onLoadFailed(e: GlideException?, model: Any?,
                                          target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                    progress?.visibility = View.GONE
                    return false
                }

                override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?,
                                             dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                    progress?.visibility = View.GONE
                    return false
                }
            })
            .into(imageViewAvatar!!)

        btnShareProfile.setOnClickListener{
            shareImage(uri = uri!!)
            builderShare?.cancel()
        }
        btnCancelProfile.setOnClickListener{
            builderShare?.cancel()
        }
        builderShare?.setContentView(dialogLayoutShare)
        builderShare?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        builderShare?.setCancelable(true)
        builderShare?.show()
    }

    private fun saveImageExternal(image: Bitmap): Uri? {
        var uri: Uri? = null
        try {
            val file = File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "my_avatar.png")
            val stream = FileOutputStream(file)
            image.compress(Bitmap.CompressFormat.PNG, 90, stream)
            stream.close()
            uri = Uri.fromFile(file)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return uri
    }

    private fun saveImage(image: Bitmap): Uri? {
        val imagesFolder = File(cacheDir, "images")
        var uri: Uri? = null
        try {
            imagesFolder.mkdirs()
            val file = File(imagesFolder, "my_avatar.png")
            val stream = FileOutputStream(file)
            image.compress(Bitmap.CompressFormat.PNG, 90, stream)
            stream.flush()
            stream.close()
            uri = FileProvider.getUriForFile(this, "${applicationContext.packageName}.fileprovider", file)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return uri
    }

    fun isExternalStorageWritable(): Boolean {
        val state = Environment.getExternalStorageState()
        return Environment.MEDIA_MOUNTED == state
    }

    fun shareImage(uri: Uri){
        try {
            val intent = Intent().apply {
                this.action = Intent.ACTION_SEND
                this.putExtra(Intent.EXTRA_STREAM, uri)
                this.type = "image/png"
            }
            startActivity(Intent.createChooser(intent, resources.getText(R.string.share)))
        }catch (e: java.lang.Exception){
            e.printStackTrace()
        }
    }

    inner class AdapterTabFragment(activity: FragmentActivity?) : FragmentStateAdapter(activity!!) {
        private val mFragmentList: MutableList<Fragment> = ArrayList()

        fun addFragment(fragment: Fragment) {
            mFragmentList.add(fragment)
        }

        fun replaceFragment(fragment: Fragment, position: Int) {
            mFragmentList.removeAt(position)
            mFragmentList.add(position, fragment)
        }

        fun getFragment(position: Int): Fragment {
            return mFragmentList[position]
        }

        override fun getItemCount(): Int {
            return mFragmentList.size
        }

        override fun createFragment(position: Int): Fragment {
            return mFragmentList[position]
        }

    }

    override fun onBackPressed() {
        viewModel.cancelAll()
        onBackPressedDispatcher.onBackPressed()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    companion object {
        private const val TAB_HEAD    = 0
        private const val TAB_CLOTHES = 1
        private const val TAB_SHOES   = 2
        private const val TAB_RIDE    = 3
    }
}