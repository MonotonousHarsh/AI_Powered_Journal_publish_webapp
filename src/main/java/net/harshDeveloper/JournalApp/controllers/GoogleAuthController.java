//package net.harshDeveloper.JournalApp.controllers;
//
//
//import lombok.extern.slf4j.Slf4j;
//import net.harshDeveloper.JournalApp.services.UserDetailServiceImpl;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.*;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.client.RestClientException;
//import org.springframework.web.client.RestTemplate;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@Slf4j
//@RestController
//@RequestMapping("/auth/google")
//public class GoogleAuthController {
//
//    @Autowired
//    private UserDetailServiceImpl userDetailServiceImpl;
//
//    @Autowired
//    private UserDetails userDetails;
//
//
//    @Value("Spring.Security.oauth2.client.registration.google.client-id")
//    private String clientId;
//
//    @Value("Spring.Security.oauth2.client.registration.google.client-secret")
//    private String clientSecret;
//
//    public RestTemplate restTemplate;
//
//    @GetMapping("/callback")
//    public ResponseEntity<?> handleGoogleCallback(@RequestParam String code)  {
//
//        try {
//            //exchange authorization code for tokens
//
//            String tokenEndpoint = "https://oauth2.googleapis.com/token";
//            Map<String, String> params = new HashMap<>();
//            params.put("code", code);
//            params.put("client_id", clientId);
//            params.put("client_secret", clientSecret);
//            params.put("redirect_uri", "https://developers.google.com/oauthplayground");
//            params.put("grant_type", "authorization_code");
//
//            HttpHeaders headers = new HttpHeaders();
//            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//
//
//            HttpEntity<Map<String, String>> request = new HttpEntity<>(params, headers);
//
//            ResponseEntity<Map> tokenResponse = restTemplate.postForEntity(tokenEndpoint, request, Map.class);
//            String idToken = (String) tokenResponse.getBody().get("id_token");
//            String userInfoUrl = "https://oauth2.googleapis.com/tokeninfo?id_token=" + idToken;
//            ResponseEntity<Map> userInfoResponse = restTemplate.getForEntity(userInfoUrl, Map.class);
//            if (userInfoResponse.getStatusCode() == HttpStatus.OK) {
//                Map<String, Object> userinfo = userInfoResponse.getBody();
//                String email = (String) userinfo.get("email");
//                UserDetails userDetails = userDetailServiceImpl.loadUserByUsername(email);
//
//            }
//            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//            return ResponseEntity.status(HttpStatus.OK).build();
//        }
//        catch (Exception e) {
//            log.error("exception occured while  handle google aUth", e);
//           // return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//        return ResponseEntity.status(HttpStatus.OK).build();
//    }
//
//
//
//}
