import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TestjioApplicationTestModule } from '../../../test.module';
import { AuditInfoDetailComponent } from 'app/entities/audit-info/audit-info-detail.component';
import { AuditInfo } from 'app/shared/model/audit-info.model';

describe('Component Tests', () => {
  describe('AuditInfo Management Detail Component', () => {
    let comp: AuditInfoDetailComponent;
    let fixture: ComponentFixture<AuditInfoDetailComponent>;
    const route = ({ data: of({ auditInfo: new AuditInfo(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TestjioApplicationTestModule],
        declarations: [AuditInfoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(AuditInfoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AuditInfoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load auditInfo on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.auditInfo).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
