package com.jpmorgan.jpmorganproject.Util

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class ValidationUtilTest {

    @Test
    fun `empty username returns true`() {
        val result = ValidationUtil.validateSearchedKeyword("")
        assertThat(result).isFalse()
    }

    @Test
    fun `only contains lowercase letters`() {
        val result = ValidationUtil.validateSearchedKeyword("asdlfkjeiw")
        assertThat(result).isTrue()
    }

    @Test
    fun `only contains uppercase letters`() {
        val result = ValidationUtil.validateSearchedKeyword("AAASDFIOCF")
        assertThat(result).isTrue()
    }

    @Test
    fun `contains both lowercase and uppercase letters`() {
        val result = ValidationUtil.validateSearchedKeyword("ASDfffvlf")
        assertThat(result).isTrue()
    }

    @Test
    fun `contains digit` () {
        val result = ValidationUtil.validateSearchedKeyword("ASDfffv34lf")
        assertThat(result).isFalse()
    }

    @Test
    fun `contains digit and special` () {
        val result = ValidationUtil.validateSearchedKeyword("ASD!@`fffv34lf")
        assertThat(result).isFalse()
    }
}