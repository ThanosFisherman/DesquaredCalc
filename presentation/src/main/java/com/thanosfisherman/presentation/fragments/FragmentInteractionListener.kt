package com.thanosfisherman.presentation.fragments

interface FragmentInteractionListener {
    fun messageFromChildToParent(vararg values: String)
}