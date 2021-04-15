package com.thousand.bosch.global.extension

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.thousand.bosch.R


internal fun FragmentManager.removeFragment(tag: String) {
    this.findFragmentByTag(tag)?.let {
        this.beginTransaction()
                .disallowAddToBackStack()
                .remove(it)
                .commitNow()
    }
}

internal fun FragmentManager.addFragment(containerViewId: Int,
                                         fragment: Fragment,
                                         tag: String) {
    this.beginTransaction()
            .disallowAddToBackStack()
            .add(containerViewId, fragment, tag)
            .commit()
}

internal fun FragmentManager.clearAndReplaceFragment(
    containerViewId: Int,
    fragment: Fragment,
    tag: String,
    animateEnter: Int? = null,
    animateExit: Int? = null
) {
    this.clearBackStack()

    val tr = this.beginTransaction()

    if (animateEnter != null && animateExit != null) {
        tr.setCustomAnimations(animateEnter, animateExit)
    }

    tr.disallowAddToBackStack()
        .replace(containerViewId, fragment, tag)
        .commitAllowingStateLoss()
}

internal fun FragmentManager.clearBackStack() {
    if (this.backStackEntryCount > 0) {
        this.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }
}

internal fun FragmentManager.replaceFragment(containerViewId: Int,
                                         fragment: Fragment,
                                         tag: String) {
    this.beginTransaction()
            .disallowAddToBackStack()
            .replace(R.id.Container, fragment, tag)
        .commitAllowingStateLoss()
}

internal fun FragmentManager.replaceFragmentWithBackStack(containerViewId: Int,
                                         fragment: Fragment,
                                         tag: String) {
    this.beginTransaction()
        .add(R.id.Container, fragment, tag)
        .addToBackStack(tag)
        .commit()
}

internal fun FragmentManager.addFragmentWithBackStack(containerViewId: Int,
                                                          fragment: Fragment,
                                                          tag: String) {
    this.beginTransaction()
            .add(R.id.Container, fragment, tag)
            .addToBackStack(tag)
            .commit()
}
