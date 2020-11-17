import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TestjioApplicationTestModule } from '../../../test.module';
import { DynamicDataDetailComponent } from 'app/entities/dynamic-data/dynamic-data-detail.component';
import { DynamicData } from 'app/shared/model/dynamic-data.model';

describe('Component Tests', () => {
  describe('DynamicData Management Detail Component', () => {
    let comp: DynamicDataDetailComponent;
    let fixture: ComponentFixture<DynamicDataDetailComponent>;
    const route = ({ data: of({ dynamicData: new DynamicData(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TestjioApplicationTestModule],
        declarations: [DynamicDataDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(DynamicDataDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DynamicDataDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load dynamicData on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.dynamicData).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
