package com.hananelsaid.hp.thn2h.ui.ChooseReciver.ChooseGroup

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.hananelsaid.hp.thn2h.R
import com.hananelsaid.hp.thn2h.ui.ChooseReciver.ChooseContacts.ChooseContactsFragment

private val TAB_TITLES = arrayOf(
    R.string.tab_text_1,
    R.string.tab_text_2
)

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class SectionsPagerAdapter(private val context: Context, fm: FragmentManager) :
    FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        // getItem is called to instantiate the fragment for the given page.
        // Return a ChooseGroupFragment (defined as a static inner class below).
        // return ChooseGroupFragment.newInstance(position + 1)

        if (position == 0)
            return ChooseGroupFragment.newInstance()
        else
            return ChooseContactsFragment.newInstance()


}

override fun getPageTitle(position: Int): CharSequence? {
    return context.resources.getString(TAB_TITLES[position])
}

override fun getCount(): Int {
    // Show 2 total pages.
    return 2
}
}