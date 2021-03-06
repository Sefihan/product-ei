/*
 * Copyright (c) 2005-2015, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.wso2.carbon.esb.mediator.test.call;

import org.apache.axiom.om.OMElement;
import org.apache.axis2.AxisFault;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.wso2.esb.integration.common.utils.ESBIntegrationTest;
import org.wso2.esb.integration.common.utils.clients.SimpleHttpClient;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * Tests for calling the direct endpoint with blocking external calls
 */
public class CallMediatorBlockingDirectEndpointTestCase extends ESBIntegrationTest {

    @BeforeClass(alwaysRun = true)
    public void setEnvironment() throws Exception {
        super.init();
        verifyProxyServiceExistence("callMediatorBlockingProxy");
    }

    @Test(groups = { "wso2.esb" },
          description = "Call the direct endpoint with blocking external calls")
    public void callMediatorBlockingDirectEndpointTest() throws AxisFault {
        OMElement response = axis2Client
                .sendSimpleStockQuoteRequest(getProxyServiceURLHttp("callMediatorBlockingProxy"), null, "WSO2");
        boolean responseContainsWSO2 = response.getFirstElement().toString().contains("WSO2 Company");
        assertTrue(responseContainsWSO2);
    }

    @Test(groups = { "wso2.esb" },
          description = "Invoke a backend that returns a 204, empty response")
    public void callMediatorBlockingNoContentResponseTest() throws IOException {
        SimpleHttpClient client = new SimpleHttpClient();
        Map headers = new HashMap(1);
        headers.put("Content-Type", "application/json");
        org.apache.http.HttpResponse response = client.doGet(getApiInvocationURL
                        ("CallMediatorNoContentTestAPI"), headers);
        assertEquals(response.getStatusLine().getStatusCode(), 204, "Empty response not received");
    }

    @AfterClass(alwaysRun = true)
    public void destroy() throws Exception {
        super.cleanup();
    }

}
