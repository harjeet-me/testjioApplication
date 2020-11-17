import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TestjioApplicationTestModule } from '../../../test.module';
import { EnvelopeDetailComponent } from 'app/entities/envelope/envelope-detail.component';
import { Envelope } from 'app/shared/model/envelope.model';

describe('Component Tests', () => {
  describe('Envelope Management Detail Component', () => {
    let comp: EnvelopeDetailComponent;
    let fixture: ComponentFixture<EnvelopeDetailComponent>;
    const route = ({ data: of({ envelope: new Envelope(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TestjioApplicationTestModule],
        declarations: [EnvelopeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(EnvelopeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EnvelopeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load envelope on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.envelope).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
