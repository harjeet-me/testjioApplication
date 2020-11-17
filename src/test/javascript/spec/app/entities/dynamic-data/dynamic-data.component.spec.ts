import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { TestjioApplicationTestModule } from '../../../test.module';
import { DynamicDataComponent } from 'app/entities/dynamic-data/dynamic-data.component';
import { DynamicDataService } from 'app/entities/dynamic-data/dynamic-data.service';
import { DynamicData } from 'app/shared/model/dynamic-data.model';

describe('Component Tests', () => {
  describe('DynamicData Management Component', () => {
    let comp: DynamicDataComponent;
    let fixture: ComponentFixture<DynamicDataComponent>;
    let service: DynamicDataService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TestjioApplicationTestModule],
        declarations: [DynamicDataComponent],
      })
        .overrideTemplate(DynamicDataComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DynamicDataComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DynamicDataService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new DynamicData(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.dynamicData && comp.dynamicData[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
