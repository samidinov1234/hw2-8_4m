package com.example.zametka_1_4m.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.zametka_1_4m.R
import com.example.zametka_1_4m.databinding.ItemOnboardBinding

class OnBoardingAdapter(private val onClick: () -> Unit) :
    RecyclerView.Adapter<OnBoardingAdapter.OnBoardingViewHolder>() {
    private val list = arrayListOf(
        OnBoardModel(R.raw.anim_1, "Очень удобный функционал"),
        OnBoardModel(R.raw.anim_2, "Быстрый, качественный продукт"),
        OnBoardModel(R.raw.anim_4, "Куча функций и интересных фишек"),
    )


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnBoardingViewHolder {
        return OnBoardingViewHolder(
            ItemOnboardBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: OnBoardingViewHolder, position: Int) {
        holder.bind(list[position])
    }

    inner class OnBoardingViewHolder(private val binding: ItemOnboardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(onBoarding: OnBoardModel) = with(binding) {
            tvTitle.text = onBoarding.title

            onBoarding.anim?.let {
                lottieAnim.setAnimation(onBoarding.anim)
                lottieAnim.playAnimation()
            }

            nextTxt.setOnClickListener {
                onClick()
            }
        }
    }
}
