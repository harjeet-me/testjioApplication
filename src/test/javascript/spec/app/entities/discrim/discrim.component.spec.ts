import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { TestjioApplicationTestModule } from '../../../test.module';
import { DiscrimComponent } from 'app/entities/discrim/discrim.component';
import { DiscrimService } from 'app/entities/discrim/discrim.service';
import { Discrim } from 'app/shared/model/discrim.model';

describe('Component Tests', () => {
  describe('Discrim Management Component', () => {
    let comp: DiscrimComponent;
    let fixture: ComponentFixture<DiscrimComponent>;
    let service: DiscrimService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TestjioApplicationTestModule],
        declarations: [DiscrimComponent],
      })
        .overrideTemplate(DiscrimComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DiscrimComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DiscrimService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Discrim(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.discrims && comp.discrims[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
