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
package pl.wavesoftware.wfirma.api.core.model.invoices;

import com.openpojo.reflection.PojoClass;
import com.openpojo.validation.PojoValidator;
import java.util.Collection;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import pl.wavesoftware.wfirma.api.core.model.utils.PojoValidationFactory;

/**
 *
 * @author Krzysztof Suszyński <krzysztof.suszynski@wavesoftware.pl>
 */
@RunWith(Parameterized.class)
public class InvoicesTest {

    private final PojoClass pojoClass;

    private final PojoValidator pojoValidator = PojoValidationFactory.createPojoValidator();

    @Parameterized.Parameters(name = "{0}")
    public static Collection<Object[]> data() {
        return PojoValidationFactory.createPojoClassList(
                InvoicesApi.class,
                Invoices.class,
                NormalInvoice.class,
                ProformaInvoice.class,
                InvoiceContents.class,
                InvoiceContent.class
        );
    }

    public InvoicesTest(String label, PojoClass pojoClass) {
        this.pojoClass = pojoClass;
    }

    @Test
    public void testPojoStructureAndBehavior() {
        assertThat(pojoClass).isNotNull();
        pojoValidator.runValidation(pojoClass);
    }

}
