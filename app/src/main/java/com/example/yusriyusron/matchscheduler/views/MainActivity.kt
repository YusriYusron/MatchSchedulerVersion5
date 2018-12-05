package com.example.yusriyusron.matchscheduler.views

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.widget.LinearLayout
import com.example.yusriyusron.matchscheduler.R
import org.jetbrains.anko.design.tabLayout
import org.jetbrains.anko.linearLayout
import org.jetbrains.anko.matchParent
import org.jetbrains.anko.support.v4.viewPager
import org.jetbrains.anko.wrapContent

class MainActivity : AppCompatActivity() {

    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val linearLayout = linearLayout {
            lparams(width = matchParent, height = matchParent)
            orientation = LinearLayout.VERTICAL
            tabLayout = tabLayout {
                lparams(width = matchParent, height = wrapContent)
            }
            viewPager = viewPager({
                id = R.id.viewPagerLayout
            }).lparams(width = matchParent, height = matchParent)
        }
        viewPager.adapter =
                PagerAdapter(supportFragmentManager)
        tabLayout.setupWithViewPager(viewPager)
    }

    class PagerAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {
        override fun getCount(): Int {
            return 2
        }

        override fun getItem(p0: Int): Fragment {
            if (p0 == 0) {
                val fragment = MatchFragment()
                val bundle = Bundle()
                bundle.putString("TYPE", "last")
                fragment.arguments = bundle
                return fragment
            } else {
                val fragment = MatchFragment()
                val bundle = Bundle()
                bundle.putString("TYPE", "next")
                fragment.arguments = bundle
                return fragment
            }
        }

        override fun getPageTitle(position: Int): CharSequence? {
            if (position == 0) {
                return "Last Match"
            } else {
                return "Next Match"
            }
        }
    }
}
