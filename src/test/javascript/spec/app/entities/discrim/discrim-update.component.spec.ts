import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { TestjioApplicationTestModule } from '../../../test.module';
import { DiscrimUpdateComponent } from 'app/entities/discrim/discrim-update.component';
import { DiscrimService } from 'app/entities/discrim/discrim.service';
import { Discrim } from 'app/shared/model/discrim.model';

describe('Component Tests', () => {
  describe('Discrim Management Update Component', () => {
    let comp: DiscrimUpdateComponent;
    let fixture: ComponentFixture<DiscrimUpdateComponent>;
    let service: DiscrimService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TestjioApplicationTestModule],
        declarations: [DiscrimUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(DiscrimUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DiscrimUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DiscrimService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Discrim(123);
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
        const entity = new Discrim();
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
