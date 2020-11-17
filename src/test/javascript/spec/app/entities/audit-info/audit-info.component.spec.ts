import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { TestjioApplicationTestModule } from '../../../test.module';
import { AuditInfoComponent } from 'app/entities/audit-info/audit-info.component';
import { AuditInfoService } from 'app/entities/audit-info/audit-info.service';
import { AuditInfo } from 'app/shared/model/audit-info.model';

describe('Component Tests', () => {
  describe('AuditInfo Management Component', () => {
    let comp: AuditInfoComponent;
    let fixture: ComponentFixture<AuditInfoComponent>;
    let service: AuditInfoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TestjioApplicationTestModule],
        declarations: [AuditInfoComponent],
      })
        .overrideTemplate(AuditInfoComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AuditInfoComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AuditInfoService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new AuditInfo(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.auditInfos && comp.auditInfos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
