/*
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 *
*/
package org.apache.airavata.cipres.userstore.mgr.samples;

import org.apache.oltu.oauth2.client.URLConnectionClient;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.response.OAuthResourceResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

public class OAuthTokenRetrievalSample {

    public AuthResponse getOAuthToken(String username, String password) throws Exception {
            username = username + "@" + Constants.IDP_TENANT_ID;

            OAuthClientRequest request = OAuthClientRequest.tokenLocation(Constants.IDP_URL + "/oauth2/token").
                    setClientId(Constants.IDP_CLIENT_KEY).setClientSecret(Constants.IDP_CLIENT_SECRET).
                    setGrantType(GrantType.PASSWORD).
                    setUsername(username).
                    setPassword(password).
                    buildBodyMessage();


            URLConnectionClient ucc = new URLConnectionClient();

            org.apache.oltu.oauth2.client.OAuthClient oAuthClient = new org.apache.oltu.oauth2.client.OAuthClient(ucc);
            OAuthResourceResponse resp = oAuthClient.resource(request, OAuth.HttpMethod.POST, OAuthResourceResponse.class);

            //converting JSON to object
            ObjectMapper mapper = new ObjectMapper();
            AuthResponse authResponse;

            try {
                authResponse = mapper.readValue(resp.getBody(), AuthResponse.class);
            }catch (Exception ex){
                throw new Exception(resp.getBody());
            }

            String accessToken = authResponse.getAccess_token();
            if (accessToken != null && !accessToken.isEmpty()) {
                System.out.println("User "+username+" authenticated successfully");
                return authResponse;
            }
        return null;
    }

    public AuthResponse getRefreshedOAuthToken(String refreshToken) throws OAuthSystemException, OAuthProblemException,
            IOException {
        OAuthClientRequest request = OAuthClientRequest.tokenLocation(Constants.IDP_URL + "/oauth2/token").
                setClientId(Constants.IDP_CLIENT_KEY).
                setClientSecret(Constants.IDP_CLIENT_SECRET).
                setGrantType(GrantType.REFRESH_TOKEN).
                setRefreshToken(refreshToken).
                buildBodyMessage();

        URLConnectionClient ucc = new URLConnectionClient();

        org.apache.oltu.oauth2.client.OAuthClient oAuthClient = new org.apache.oltu.oauth2.client.OAuthClient(ucc);
        OAuthResourceResponse resp = oAuthClient.resource(request, OAuth.HttpMethod.POST, OAuthResourceResponse.class);

        //converting JSON to object
        ObjectMapper mapper = new ObjectMapper();
        AuthResponse authResponse = mapper.readValue(resp.getBody(), AuthResponse.class);
        System.out.println("Fetched new refreshed OAuth token");
        return authResponse;
    }
}