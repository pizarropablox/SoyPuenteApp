package com.duoc.soypuenteapp

import android.speech.tts.TextToSpeech
import org.junit.Test
import org.mockito.Mockito.*
import java.util.*

class TextToSpeechTest {

    @Test
    fun testTextToSpeech() {
        // Crear un mock de TextToSpeech
        val ttsMock = mock(TextToSpeech::class.java)

        // Simular la configuración correcta del idioma
        `when`(ttsMock.setLanguage(Locale("es", "ES"))).thenReturn(TextToSpeech.LANG_COUNTRY_AVAILABLE)

        // Verificar que se está reproduciendo el texto
        ttsMock.speak("Hola mundo", TextToSpeech.QUEUE_FLUSH, null, null)

        verify(ttsMock, times(1)).speak("Hola mundo", TextToSpeech.QUEUE_FLUSH, null, null)
    }
}
