package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class AuditInfoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AuditInfo.class);
        AuditInfo auditInfo1 = new AuditInfo();
        auditInfo1.setId(1L);
        AuditInfo auditInfo2 = new AuditInfo();
        auditInfo2.setId(auditInfo1.getId());
        assertThat(auditInfo1).isEqualTo(auditInfo2);
        auditInfo2.setId(2L);
        assertThat(auditInfo1).isNotEqualTo(auditInfo2);
        auditInfo1.setId(null);
        assertThat(auditInfo1).isNotEqualTo(auditInfo2);
    }
}
