package com.jpmorgan.jpmorganproject.Util

object ValidationUtil {
    /**
     * Check if the searched city is valid
     */
    fun validateSearchedKeyword (
        keyword : String
    ) : Boolean {
        return keyword.matches("[a-zA-z]+".toRegex())
    }
}