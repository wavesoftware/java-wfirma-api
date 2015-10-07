/*
 * Copyright (c) 2014 Krzysztof Suszyński <krzysztof.suszynski@wavesoftware.pl>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package pl.wavesoftware.wfirma.api.simple.mapper;

import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.wavesoftware.wfirma.api.ApiContext;
import pl.wavesoftware.wfirma.api.core.mapper.ResponseListener;
import pl.wavesoftware.wfirma.api.core.mapper.xml.JaxbMarshaller;
import pl.wavesoftware.wfirma.api.core.model.*;
import pl.wavesoftware.wfirma.api.core.model.companies.Companies;
import pl.wavesoftware.wfirma.api.core.model.companies.CompaniesGetRequest;
import pl.wavesoftware.wfirma.api.core.model.contractors.Contractor;
import pl.wavesoftware.wfirma.api.core.model.contractors.Contractors;
import pl.wavesoftware.wfirma.api.core.model.contractors.ContractorsApi;
import pl.wavesoftware.wfirma.api.core.model.logic.*;
import pl.wavesoftware.wfirma.api.core.model.requests.AddRequest;
import pl.wavesoftware.wfirma.api.core.model.requests.DeleteRequest;
import pl.wavesoftware.wfirma.api.core.model.requests.FindRequest;
import pl.wavesoftware.wfirma.api.simple.model.SimpleCredentials;

import java.io.InputStream;
import java.net.URI;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static pl.wavesoftware.wfirma.api.unittests.XsdValidatorAssert.assertThat;

/**
 *
 * @author Krzysztof Suszyński <krzysztof.suszynski@wavesoftware.pl>
 */
public class SimpleGatewayIT {

    private String expResultAuth;

    private String correctLogin;

    private String correctPassword;

    private static final String EXAMPLE_NIP = "5272516453";

    private static final URI GATEWAY_ADDRESS = URI.create(Gateway.GATEWAY_ADDRESS);

    @Before
    public void before() {
        correctLogin = System.getenv("WFIRMA_LOGIN");
        correctPassword = System.getenv("WFIRMA_PASSWORD");
        Logger logger = LoggerFactory.getLogger(this.getClass());
        boolean loginCond = (correctLogin == null || "".equals(correctLogin.trim()));
        boolean passCond = (correctPassword == null || "".equals(correctPassword.trim()));
        if (loginCond) {
            logger.warn("Please set environment variable `WFIRMA_LOGIN`"
                + " before running this intergration test, skipping!");
        }
        Assume.assumeFalse(loginCond);
        if (passCond) {
            logger.warn("Please set environment variable `WFIRMA_PASSWORD`"
                + " before running this intergration test, skipping!");
        }
        Assume.assumeFalse(passCond);

        expResultAuth = "<\\?xml version=\"1\\.0\" encoding=\"UTF-8\"\\?>\\s*"
            + "<domain>\\s*"
            + "<status>\\s*"
            + "<code>AUTH</code>\\s*"
            + "</status>\\s*"
            + "</domain>\\s*";
    }

    private SimpleGateway instance(String login, String password) {
        SimpleCredentials creds = new SimpleCredentials(login, password);
        ApiContext ctx = new ApiContext(creds);
        GatewayFactory factory = ctx.getGatewayFactory();
        factory.setAlternativeAddress(GATEWAY_ADDRESS);
        return SimpleGateway.class.cast(factory.produce());
    }

    @Test
    public void testFetch() throws WFirmaException {
        SimpleGateway instance = instance(correctLogin, correctPassword);

        Request<Companies> get = CompaniesGetRequest.create();
        String result = instance.get(get);
        assertThat(result).isNotNull();
        assertThat(result).isWellFormedXml();
        assertThat(result).isValidByXsd(getTestFetchXsd());
    }

    @Test
    public void testPost() throws Exception {
        AddRequest<Contractors> addRequest = createAddRequest();
        FindRequest<Contractors> findRequest = createFindRequest();
        DeleteRequest<Contractors> deleteRequest;

        SimpleGateway instance = instance(correctLogin, correctPassword);

        String result;
        result = instance.post(findRequest);
        ContractorsApi api;
        api = JaxbMarshaller.create(ContractorsApi.class).unmarshal(result);
        if (!api.getContractors().getContractor().isEmpty()) {
            for (Contractor contractor : api.getContractors().getContractor()) {
                deleteRequest = createDeleteRequest(contractor);
                instance.get(deleteRequest);
            }
        }
        result = instance.post(findRequest);
        assertThat(result).doesNotContain(EXAMPLE_NIP);
        result = instance.post(addRequest);
        assertThat(result).isNotEmpty();
        result = instance.post(findRequest);
        assertThat(result).isWellFormedXml();
        assertThat(result).isValidByXsd(getTestPostXsd());
        assertThat(result).contains(EXAMPLE_NIP);
        api = JaxbMarshaller.create(ContractorsApi.class).unmarshal(result);
        deleteRequest = createDeleteRequest(api.getContractors().getContractor().iterator().next());
        result = instance.get(deleteRequest);
        assertThat(result).isNotEmpty();
        assertThat(result).containsSequence("Kontrahent: ", " został usunięty.");
        result = instance.post(findRequest);
        assertThat(result).isWellFormedXml();
        assertThat(result).isValidByXsd(getTestPostXsd());
        assertThat(result).doesNotContain(EXAMPLE_NIP);
    }

    @Test
    public void testFetchWithAuthFail() throws WFirmaException, BackingStoreException {
        Preferences prefs = Preferences.userRoot();
        String key = this.getClass().getName() + "::testFetchWithAuthFail";
        long lastRun = prefs.getLong(key, 0);
        long current = System.currentTimeMillis();
        boolean cond = current - lastRun > 5 * 60 * 1000;
        if (!cond) {
            LoggerFactory.getLogger(this.getClass()).warn(
                "Invalid login testing on WFirma API is possible no more often then 5min interval, skipping test!");
        }
        Assume.assumeTrue(cond);
        SimpleGateway instance = instance("non-existing-login-2@example.org", "invalid-password");
        final StringBuilder responseBuilder = new StringBuilder();
        ResponseListener listener = new ResponseListener() {

            @Override
            public void responseRecived(String recived) {
                responseBuilder.append(recived);
            }
        };
        instance.addListener(listener);
        try {
            Request<Companies> get = CompaniesGetRequest.create();
            instance.get(get);
            fail("Expected to throw a WFirmaSercurityException for invalid auth");
        } catch (WFirmaSecurityException ex) {
            assertEquals("Auth failed for user: `non-existing-login-2@example.org`", ex.getLocalizedMessage());
        } finally {
            instance.removeListener(listener);
        }
        assertThat(responseBuilder.toString()).matches(expResultAuth);
        prefs.putLong(key, current);
        prefs.flush();
    }

    private FindRequest<Contractors> createFindRequest() {
        Parameters params = new Parameters();
        Conditions conds = params.getConditions();
        And and = new And();
        Condition cond = new Condition();
        cond.setField("nip");
        cond.setOperator(LogicalOperator.EQ);
        cond.setValue(EXAMPLE_NIP);
        and.getCondition().add(cond);
        conds.getAnd().add(and);
        return FindRequest.create(Contractors.class, params);
    }

    private AddRequest<Contractors> createAddRequest() {
        ContractorsApi api = new ContractorsApi();
        Contractors contractors = new Contractors(api);
        api.setContractors(contractors);
        Contractor contractor = new Contractor();
        contractors.getContractor().add(contractor);
        contractor.setNip(EXAMPLE_NIP);
        contractor.setName("Wave Software");
        return AddRequest.create(contractors);
    }

    private DeleteRequest<Contractors> createDeleteRequest(Contractor contractor) {
        return DeleteRequest.create(Contractors.class, contractor.getId());
    }

    private InputStream getTestPostXsd() {
        return checkNotNull(this.getClass().getResourceAsStream("testPost.xsd"));
    }

    private InputStream getTestFetchXsd() {
        return checkNotNull(this.getClass().getResourceAsStream("testFetch.xsd"));
    }

}
