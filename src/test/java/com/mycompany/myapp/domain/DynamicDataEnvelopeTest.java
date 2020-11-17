package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class DynamicDataEnvelopeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DynamicDataEnvelope.class);
        DynamicDataEnvelope dynamicDataEnvelope1 = new DynamicDataEnvelope();
        dynamicDataEnvelope1.setId(1L);
        DynamicDataEnvelope dynamicDataEnvelope2 = new DynamicDataEnvelope();
        dynamicDataEnvelope2.setId(dynamicDataEnvelope1.getId());
        assertThat(dynamicDataEnvelope1).isEqualTo(dynamicDataEnvelope2);
        dynamicDataEnvelope2.setId(2L);
        assertThat(dynamicDataEnvelope1).isNotEqualTo(dynamicDataEnvelope2);
        dynamicDataEnvelope1.setId(null);
        assertThat(dynamicDataEnvelope1).isNotEqualTo(dynamicDataEnvelope2);
    }
}
