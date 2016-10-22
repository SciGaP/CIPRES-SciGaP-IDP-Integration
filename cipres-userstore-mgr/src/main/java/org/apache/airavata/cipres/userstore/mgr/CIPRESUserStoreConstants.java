package org.apache.airavata.cipres.userstore.mgr;/*
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

import org.wso2.carbon.user.api.Property;
import org.wso2.carbon.user.core.UserStoreConfigConstants;
import org.wso2.carbon.user.core.jdbc.JDBCRealmConstants;


import java.util.ArrayList;

public class CIPRESUserStoreConstants {


    //Properties for Read Active Directory User Store Manager
    public static final ArrayList<Property> CUSTOM_UM_MANDATORY_PROPERTIES = new ArrayList<Property>();
    public static final ArrayList<Property> CUSTOM_UM_OPTIONAL_PROPERTIES = new ArrayList<Property>();
    public static final ArrayList<Property> CUSTOM_UM_ADVANCED_PROPERTIES = new ArrayList<Property>();


    static {

        setMandatoryProperty(JDBCRealmConstants.DRIVER_NAME, "Driver Name", "", "Full qualified driver name");
        setMandatoryProperty(JDBCRealmConstants.URL,"Connection URL", "", "URL of the user store database");
        setMandatoryProperty(JDBCRealmConstants.USER_NAME, "User Name","", "Username for the database");
        setMandatoryProperty(JDBCRealmConstants.PASSWORD, "Password","", "Password for the database");

        setProperty(UserStoreConfigConstants.disabled,"Disabled", "false", UserStoreConfigConstants.disabledDescription);

        setProperty("ReadOnly","Read Only", "true", "Indicates whether the user store of this realm operates in the user read only mode or not");
        setProperty(UserStoreConfigConstants.SCIMEnabled,"SCIM Enabled", "false", UserStoreConfigConstants.SCIMEnabledDescription);


        //Advanced Properties (No descriptions added for each property)
        setAdvancedProperty("SelectUserSQL","Select User SQL", "SELECT * FROM users WHERE USERNAME=?", "");
        setAdvancedProperty("UserFilterSQL","User Filter SQL", "SELECT USERNAME FROM users WHERE USERNAME LIKE ?  ORDER BY USERNAME", "");
        setAdvancedProperty("GetUserPropertiesForProfileSQL", "User Profile Select SQL", "SELECT EMAIL, FIRST_NAME, LAST_NAME FROM users WHERE USERNAME=?", "");

    }


    private static void setProperty(String name, String displayName, String value, String description) {
        Property property = new Property(name, value, displayName + "#" +description, null);
        CUSTOM_UM_OPTIONAL_PROPERTIES.add(property);

    }

    private static void setMandatoryProperty(String name, String displayName, String value, String description) {
        Property property = new Property(name, value, displayName + "#" +description, null);
        CUSTOM_UM_MANDATORY_PROPERTIES.add(property);

    }

    private static void setAdvancedProperty(String name, String displayName, String value, String description) {
        Property property = new Property(name, value, displayName + "#" +description, null);
        CUSTOM_UM_ADVANCED_PROPERTIES.add(property);

    }

}