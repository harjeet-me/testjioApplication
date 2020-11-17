import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { TestjioApplicationTestModule } from '../../../test.module';
import { DynamicDataUpdateComponent } from 'app/entities/dynamic-data/dynamic-data-update.component';
import { DynamicDataService } from 'app/entities/dynamic-data/dynamic-data.service';
import { DynamicData } from 'app/shared/model/dynamic-data.model';

describe('Component Tests', () => {
  describe('DynamicData Management Update Component', () => {
    let comp: DynamicDataUpdateComponent;
    let fixture: ComponentFixture<DynamicDataUpdateComponent>;
    let service: DynamicDataService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TestjioApplicationTestModule],
        declarations: [DynamicDataUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(DynamicDataUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DynamicDataUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DynamicDataService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new DynamicData(123);
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
        const entity = new DynamicData();
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
