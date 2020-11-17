import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { TestjioApplicationTestModule } from '../../../test.module';
import { DynamicDataEnvelopeUpdateComponent } from 'app/entities/dynamic-data-envelope/dynamic-data-envelope-update.component';
import { DynamicDataEnvelopeService } from 'app/entities/dynamic-data-envelope/dynamic-data-envelope.service';
import { DynamicDataEnvelope } from 'app/shared/model/dynamic-data-envelope.model';

describe('Component Tests', () => {
  describe('DynamicDataEnvelope Management Update Component', () => {
    let comp: DynamicDataEnvelopeUpdateComponent;
    let fixture: ComponentFixture<DynamicDataEnvelopeUpdateComponent>;
    let service: DynamicDataEnvelopeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TestjioApplicationTestModule],
        declarations: [DynamicDataEnvelopeUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(DynamicDataEnvelopeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DynamicDataEnvelopeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DynamicDataEnvelopeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new DynamicDataEnvelope(123);
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
        const entity = new DynamicDataEnvelope();
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
