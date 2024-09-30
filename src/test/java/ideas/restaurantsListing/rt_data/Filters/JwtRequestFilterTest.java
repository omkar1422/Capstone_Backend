package ideas.restaurantsListing.rt_data.Filters;

import ideas.restaurantsListing.rt_data.Exception.jwt.TokenExpiredException;
import ideas.restaurantsListing.rt_data.Service.CustomerService;
import ideas.restaurantsListing.rt_data.Util.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.servlet.FilterChain;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.PrintWriter;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JwtRequestFilterTest {

    @InjectMocks
    private JwtRequestFilter jwtRequestFilter;

    @Mock
    private CustomerService customerService;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @Mock
    private UserDetails userDetails;

    private static final String TOKEN = "Bearer valid.token.here";
    private static final String EMAIL = "test@example.com";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void doFilterInternal_ShouldSetAuthentication_WhenTokenIsValid() throws Exception {
        // Arrange
        when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn(TOKEN);
        when(jwtUtil.extractUsername("valid.token.here")).thenReturn(EMAIL);
        when(customerService.loadUserByUsername(EMAIL)).thenReturn(userDetails);
        when(jwtUtil.validateToken("valid.token.here", userDetails)).thenReturn(true);

        jwtRequestFilter.doFilterInternal(request, response, filterChain);

        ArgumentCaptor<HttpServletRequest> reqCaptor = ArgumentCaptor.forClass(HttpServletRequest.class);
        ArgumentCaptor<HttpServletResponse> resCaptor = ArgumentCaptor.forClass(HttpServletResponse.class);
        verify(filterChain).doFilter(reqCaptor.capture(), resCaptor.capture());
        assertNotNull(reqCaptor.getValue());
        assertNotNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    void doFilterInternal_ShouldReturnForbidden_WhenTokenIsExpired() throws Exception {

        when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn(TOKEN);
        when(jwtUtil.extractUsername("valid.token.here")).thenThrow(new TokenExpiredException("Token expired"));

        PrintWriter writer = mock(PrintWriter.class);
        when(response.getWriter()).thenReturn(writer);

        jwtRequestFilter.doFilterInternal(request, response, filterChain);

        verify(response).setStatus(HttpServletResponse.SC_FORBIDDEN);
        verify(writer).write("Token expired: Token expired"); // Verify that writer's write method is called
        verify(filterChain, never()).doFilter(any(), any());
    }

    @Test
    void doFilterInternal_ShouldReturnBadRequest_WhenTokenIsInvalid() throws Exception {
        // Arrange
        when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn(TOKEN);
        when(jwtUtil.extractUsername("valid.token.here")).thenThrow(new RuntimeException("Invalid token"));

        PrintWriter writer = mock(PrintWriter.class);
        when(response.getWriter()).thenReturn(writer);

        jwtRequestFilter.doFilterInternal(request, response, filterChain);

        verify(response).setStatus(HttpServletResponse.SC_BAD_REQUEST);
        verify(writer).write("Invalid token: Invalid token"); // Verify that writer's write method is called
        verify(filterChain, never()).doFilter(any(), any());
    }

    @Test
    void doFilterInternal_ShouldAllowOptionsRequest() throws Exception {

        when(request.getMethod()).thenReturn("OPTIONS");

        jwtRequestFilter.doFilterInternal(request, response, filterChain);

        verify(response).setStatus(HttpServletResponse.SC_OK);
        verify(filterChain, never()).doFilter(any(), any());
    }
}
