import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TestjioApplicationTestModule } from '../../../test.module';
import { DynamicDataEnvelopeDetailComponent } from 'app/entities/dynamic-data-envelope/dynamic-data-envelope-detail.component';
import { DynamicDataEnvelope } from 'app/shared/model/dynamic-data-envelope.model';

describe('Component Tests', () => {
  describe('DynamicDataEnvelope Management Detail Component', () => {
    let comp: DynamicDataEnvelopeDetailComponent;
    let fixture: ComponentFixture<DynamicDataEnvelopeDetailComponent>;
    const route = ({ data: of({ dynamicDataEnvelope: new DynamicDataEnvelope(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TestjioApplicationTestModule],
        declarations: [DynamicDataEnvelopeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(DynamicDataEnvelopeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DynamicDataEnvelopeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load dynamicDataEnvelope on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.dynamicDataEnvelope).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
