package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class DiscrimTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Discrim.class);
        Discrim discrim1 = new Discrim();
        discrim1.setId(1L);
        Discrim discrim2 = new Discrim();
        discrim2.setId(discrim1.getId());
        assertThat(discrim1).isEqualTo(discrim2);
        discrim2.setId(2L);
        assertThat(discrim1).isNotEqualTo(discrim2);
        discrim1.setId(null);
        assertThat(discrim1).isNotEqualTo(discrim2);
    }
}
