package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class DynamicDataTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DynamicData.class);
        DynamicData dynamicData1 = new DynamicData();
        dynamicData1.setId(1L);
        DynamicData dynamicData2 = new DynamicData();
        dynamicData2.setId(dynamicData1.getId());
        assertThat(dynamicData1).isEqualTo(dynamicData2);
        dynamicData2.setId(2L);
        assertThat(dynamicData1).isNotEqualTo(dynamicData2);
        dynamicData1.setId(null);
        assertThat(dynamicData1).isNotEqualTo(dynamicData2);
    }
}
