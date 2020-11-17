import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { TestjioApplicationTestModule } from '../../../test.module';
import { AuditInfoUpdateComponent } from 'app/entities/audit-info/audit-info-update.component';
import { AuditInfoService } from 'app/entities/audit-info/audit-info.service';
import { AuditInfo } from 'app/shared/model/audit-info.model';

describe('Component Tests', () => {
  describe('AuditInfo Management Update Component', () => {
    let comp: AuditInfoUpdateComponent;
    let fixture: ComponentFixture<AuditInfoUpdateComponent>;
    let service: AuditInfoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TestjioApplicationTestModule],
        declarations: [AuditInfoUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(AuditInfoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AuditInfoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AuditInfoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new AuditInfo(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new AuditInfo();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
