import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { TestjioApplicationTestModule } from '../../../test.module';
import { EnvelopeComponent } from 'app/entities/envelope/envelope.component';
import { EnvelopeService } from 'app/entities/envelope/envelope.service';
import { Envelope } from 'app/shared/model/envelope.model';

describe('Component Tests', () => {
  describe('Envelope Management Component', () => {
    let comp: EnvelopeComponent;
    let fixture: ComponentFixture<EnvelopeComponent>;
    let service: EnvelopeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TestjioApplicationTestModule],
        declarations: [EnvelopeComponent],
      })
        .overrideTemplate(EnvelopeComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EnvelopeComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EnvelopeService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Envelope(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.envelopes && comp.envelopes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
