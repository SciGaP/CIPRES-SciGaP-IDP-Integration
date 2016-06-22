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


import org.wso2.carbon.identity.oauth2.stub.dto.OAuth2TokenValidationResponseDTO;

public class Main {
    public static void main(String[] args) throws Exception {

        String username = "";
        String password = "";

        OAuthTokenRetrievalSample tokenRetriever = new OAuthTokenRetrievalSample();
        System.out.println("Getting OAuth Access Token for User : " + username);
        AuthResponse authResponse = tokenRetriever.getOAuthToken(username, password);
        System.out.println("OAuth token : " + authResponse.getAccess_token());
        System.out.println("OAuth token expires in : " + authResponse.getExpires_in());
        System.out.println("Refresh token : " + authResponse.getRefresh_token());

        System.out.println("Getting OAuth Access Token from Refresh Token");
        authResponse = tokenRetriever.getRefreshedOAuthToken(authResponse.getRefresh_token());
        System.out.println("New OAuth token : " + authResponse.getAccess_token());

        OAuthTokenVerificationSample tokenVerifier = new OAuthTokenVerificationSample();
        System.out.println("Verifying OAuth Access Token");
        OAuth2TokenValidationResponseDTO validationResp = tokenVerifier.validateAccessToken(authResponse.getAccess_token());

        //Validated username is in the CIPRES.ORG/<username>@prod.cipres format
        String validatedUsername = validationResp.getAuthorizedUser();
        System.out.println("Authorised user (Full Format): " + validatedUsername);
        String cipresUsername = validatedUsername.replaceAll("CIPRES.ORG/", "");
        cipresUsername = cipresUsername.replaceAll("@" + Constants.IDP_TENANT_ID, "");
        System.out.println("Cipres Username : " + cipresUsername);
    }
}