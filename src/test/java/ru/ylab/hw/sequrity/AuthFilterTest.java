//package ru.ylab.hw.sequrity;
//
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import java.util.Collections;
//
//import static org.mockito.Mockito.*;
//
//class AuthFilterTest {
//
//    @Mock
//    private HttpServletRequest request;
//
//    @Mock
//    private HttpServletResponse response;
//
//    @Mock
//    private FilterChain chain;
//
//    private AuthFilter authFilter;
//
//    @BeforeEach
//    public void setUp() {
//        MockitoAnnotations.openMocks(this);
//        authFilter = new AuthFilter();
//    }
//
//    @Test
//    public void testDoFilterValidToken() throws Exception {
//        String token = "Bearer " + JwtUtil.generateToken("testUser", Collections.singletonList("USER_ROLE"));
//
//        when(request.getHeader("Authorization")).thenReturn(token);
//
//        authFilter.doFilter(request, response, chain);
//
//        verify(chain, times(1)).doFilter(request, response);
//    }
//
//    @Test
//    public void testDoFilterInvalidToken() throws Exception {
//        when(request.getHeader("Authorization")).thenReturn("Bearer invalidToken");
//
//        authFilter.doFilter(request, response, chain);
//
//        verify(response, times(1)).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//        verify(chain, never()).doFilter(request, response);
//    }
//}