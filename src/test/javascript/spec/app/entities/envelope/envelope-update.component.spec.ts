import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { TestjioApplicationTestModule } from '../../../test.module';
import { EnvelopeUpdateComponent } from 'app/entities/envelope/envelope-update.component';
import { EnvelopeService } from 'app/entities/envelope/envelope.service';
import { Envelope } from 'app/shared/model/envelope.model';

describe('Component Tests', () => {
  describe('Envelope Management Update Component', () => {
    let comp: EnvelopeUpdateComponent;
    let fixture: ComponentFixture<EnvelopeUpdateComponent>;
    let service: EnvelopeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TestjioApplicationTestModule],
        declarations: [EnvelopeUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(EnvelopeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EnvelopeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EnvelopeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Envelope(123);
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
        const entity = new Envelope();
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
