package kg.attractor.xfood.mockito.service;

import kg.attractor.xfood.service.impl.OAuthServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class OAuthServiceTest {

    @InjectMocks
    private OAuthServiceImpl oAuthService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void testGenerateCodeVerifier() {
        String codeVerifier = oAuthService.generateCodeVerifier();
        assertNotNull(codeVerifier);
        assertEquals(43, codeVerifier.length());
    }

    @Test
    void testGenerateCodeChallenge() throws Exception {
        String codeVerifier = "testCodeVerifier";
        String expectedChallenge = Base64.getUrlEncoder().withoutPadding()
                .encodeToString(MessageDigest.getInstance("SHA-256").digest(codeVerifier.getBytes(StandardCharsets.US_ASCII)));

        String codeChallenge = oAuthService.generateCodeChallenge(codeVerifier);
        assertEquals(expectedChallenge, codeChallenge);
    }
}