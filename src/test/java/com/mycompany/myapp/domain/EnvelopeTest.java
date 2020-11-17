package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class EnvelopeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Envelope.class);
        Envelope envelope1 = new Envelope();
        envelope1.setId(1L);
        Envelope envelope2 = new Envelope();
        envelope2.setId(envelope1.getId());
        assertThat(envelope1).isEqualTo(envelope2);
        envelope2.setId(2L);
        assertThat(envelope1).isNotEqualTo(envelope2);
        envelope1.setId(null);
        assertThat(envelope1).isNotEqualTo(envelope2);
    }
}
