package com.coopersimpson.androidnews.data.network

import org.junit.Assert
import org.junit.Test

class ApiMappingsTest {
    // Country code mappings
    @Test
    fun `countryCode - Australia`() {
        Assert.assertEquals("au", ApiMappings.countryCode("Australia"))
    }

    @Test
    fun `countryCode - Any`() {
        Assert.assertEquals(null, ApiMappings.countryCode("Any"))
    }

    @Test
    fun `countryCode - fallback`() {
        Assert.assertEquals(null, ApiMappings.countryCode("sadfas42"))
    }

    // Language code mappings
    @Test
    fun `languageCode - English`() {
        Assert.assertEquals("en", ApiMappings.languageCode("English"))
    }

    @Test
    fun `languageCode - fallback`() {
        Assert.assertEquals(null, ApiMappings.languageCode("asdfar234"))
    }

    // Category mappings
    @Test
    fun `categoryCode - Business`() {
        Assert.assertEquals("business", ApiMappings.categoryCode("Business"))
    }

    @Test
    fun `categoryCode - All`() {
        Assert.assertEquals(null, ApiMappings.categoryCode("All"))
    }

    @Test
    fun `categoryCode - fallback`() {
        Assert.assertEquals(null, ApiMappings.categoryCode("sadfasdf"))
    }

    // Country emoji mappings
    @Test
    fun `countryEmoji - united states of america`() {
        Assert.assertEquals("🇺🇸", ApiMappings.countryEmoji("united states of america"))
    }

    @Test
    fun `countryEmoji - world`() {
        Assert.assertEquals("🌍", ApiMappings.countryEmoji("world"))
    }

    @Test
    fun `countryEmoji - fallback`() {
        Assert.assertEquals("🌍", ApiMappings.countryEmoji("asdf23225"))
    }
}