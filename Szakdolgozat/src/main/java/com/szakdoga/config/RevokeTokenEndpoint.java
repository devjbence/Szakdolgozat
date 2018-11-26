package com.szakdoga.config;

import javax.annotation.Resource;
//import javax.servlet.http.HttpServletRequest;

import org.springframework.security.oauth2.provider.endpoint.FrameworkEndpoint;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

//https://www.baeldung.com/spring-security-oauth-revoke-tokens
@FrameworkEndpoint
public class RevokeTokenEndpoint {

    @Resource(name="tokenServices")
    ConsumerTokenServices tokenServices;
         
    @RequestMapping(method = RequestMethod.DELETE, value = "/tokens/revoke/{tokenId}")
    @ResponseBody
    public String revokeToken(@PathVariable String tokenId) {
        tokenServices.revokeToken(tokenId);
        return "Token has been deleted successfully.";
    }

}
