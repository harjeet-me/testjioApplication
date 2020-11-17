import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TestjioApplicationTestModule } from '../../../test.module';
import { DiscrimDetailComponent } from 'app/entities/discrim/discrim-detail.component';
import { Discrim } from 'app/shared/model/discrim.model';

describe('Component Tests', () => {
  describe('Discrim Management Detail Component', () => {
    let comp: DiscrimDetailComponent;
    let fixture: ComponentFixture<DiscrimDetailComponent>;
    const route = ({ data: of({ discrim: new Discrim(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TestjioApplicationTestModule],
        declarations: [DiscrimDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(DiscrimDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DiscrimDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load discrim on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.discrim).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
