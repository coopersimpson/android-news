package com.coopersimpson.androidnews;

import com.coopersimpson.androidnews.data.network.ApiMappings;
import org.junit.Assert.assertEquals
import org.junit.Test;

public class ApiMappingTest {
    // Country code mappings
    @Test
    fun `countryCode - Australia`() {
        assertEquals("au", ApiMappings.countryCode("Australia"))
    }

    @Test
    fun `countryCode - Any`() {
        assertEquals(null, ApiMappings.countryCode("Any"))
    }

    @Test
    fun `countryCode - fallback`() {
        assertEquals(null, ApiMappings.countryCode("sadfas42"))
    }

    // Language code mappings
    @Test
    fun `languageCode - English`() {
        assertEquals("en", ApiMappings.languageCode("English"))
    }

    @Test
    fun `languageCode - fallback`() {
        assertEquals(null, ApiMappings.languageCode("asdfar234"))
    }

    // Category mappings
    @Test
    fun `categoryCode - Business`() {
        assertEquals("business", ApiMappings.categoryCode("Business"))
    }

    @Test
    fun `categoryCode - All`() {
        assertEquals(null, ApiMappings.categoryCode("All"))
    }

    @Test
    fun `categoryCode - fallback`() {
        assertEquals(null, ApiMappings.categoryCode("sadfasdf"))
    }

    // Country emoji mappings
    @Test
    fun `countryEmoji - united states of america`() {
        assertEquals("🇺🇸", ApiMappings.countryEmoji("united states of america"))
    }

    @Test
    fun `countryEmoji - world`() {
        assertEquals("🌍", ApiMappings.countryEmoji("world"))
    }

    @Test
    fun `countryEmoji - fallback`() {
        assertEquals("🌍", ApiMappings.countryEmoji("asdf23225"))
    }
}