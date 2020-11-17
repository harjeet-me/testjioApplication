import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { TestjioApplicationTestModule } from '../../../test.module';
import { DynamicDataEnvelopeComponent } from 'app/entities/dynamic-data-envelope/dynamic-data-envelope.component';
import { DynamicDataEnvelopeService } from 'app/entities/dynamic-data-envelope/dynamic-data-envelope.service';
import { DynamicDataEnvelope } from 'app/shared/model/dynamic-data-envelope.model';

describe('Component Tests', () => {
  describe('DynamicDataEnvelope Management Component', () => {
    let comp: DynamicDataEnvelopeComponent;
    let fixture: ComponentFixture<DynamicDataEnvelopeComponent>;
    let service: DynamicDataEnvelopeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TestjioApplicationTestModule],
        declarations: [DynamicDataEnvelopeComponent],
      })
        .overrideTemplate(DynamicDataEnvelopeComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DynamicDataEnvelopeComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DynamicDataEnvelopeService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new DynamicDataEnvelope(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.dynamicDataEnvelopes && comp.dynamicDataEnvelopes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
