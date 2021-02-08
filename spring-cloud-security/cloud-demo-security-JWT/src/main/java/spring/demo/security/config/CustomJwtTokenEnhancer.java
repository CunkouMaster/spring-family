package spring.demo.security.config;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.util.HashMap;
import java.util.Map;

/**
 * @author huazai
 * @date 2021/2/8 17:12
 */
public class CustomJwtTokenEnhancer implements TokenEnhancer {

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken oAuth2AccessToken, OAuth2Authentication authentication) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("jwt-key-aaa", "jwt-value-AAA");
        map.put("jwt-key-bbb", "jwt-value-BBB");
        map.put("jwt-key-ccc", "jwt-value-CCC");

        DefaultOAuth2AccessToken defaultOAuth2AccessToken = (DefaultOAuth2AccessToken)oAuth2AccessToken;
        defaultOAuth2AccessToken.setAdditionalInformation(map);

        return defaultOAuth2AccessToken;
    }
}
