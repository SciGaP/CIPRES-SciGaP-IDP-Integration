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


import org.apache.axis2.AxisFault;
import org.apache.axis2.context.ConfigurationContext;
import org.apache.axis2.context.ConfigurationContextFactory;
import org.wso2.carbon.identity.oauth2.stub.OAuth2TokenValidationServiceStub;
import org.wso2.carbon.identity.oauth2.stub.dto.OAuth2TokenValidationRequestDTO;
import org.wso2.carbon.identity.oauth2.stub.dto.OAuth2TokenValidationRequestDTO_OAuth2AccessToken;
import org.wso2.carbon.identity.oauth2.stub.dto.OAuth2TokenValidationResponseDTO;
import org.wso2.carbon.utils.CarbonUtils;

import java.rmi.RemoteException;

public class OAuthTokenVerificationSample {

    public static final String BEARER_TOKEN_TYPE = "bearer";

    private OAuth2TokenValidationServiceStub stub;

    public OAuthTokenVerificationSample() throws AxisFault {
        ConfigurationContext configContext = ConfigurationContextFactory
                .createConfigurationContextFromFileSystem(null, null);
        String serviceURL = Constants.IDP_URL + "/services/" + "OAuth2TokenValidationService";
        stub = new OAuth2TokenValidationServiceStub(configContext, serviceURL);
        CarbonUtils.setBasicAccessSecurityHeaders(Constants.IDP_CIPRES_ADMIN_USERNAME + '@' + Constants.IDP_TENANT_ID,
                Constants.IDP_CIPRES_ADMIN_PASSWORD,
                true, stub._getServiceClient());
    }

    public OAuth2TokenValidationResponseDTO validateAccessToken(String accessToken) throws RemoteException {
            OAuth2TokenValidationRequestDTO oauthReq = new OAuth2TokenValidationRequestDTO();
            OAuth2TokenValidationRequestDTO_OAuth2AccessToken token =
                    new OAuth2TokenValidationRequestDTO_OAuth2AccessToken();
            token.setIdentifier(accessToken);
            token.setTokenType(BEARER_TOKEN_TYPE);
            oauthReq.setAccessToken(token);
            return stub.validate(oauthReq);
    }
}