import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { TestjioApplicationTestModule } from '../../../test.module';
import { ConversationDetailComponent } from 'app/entities/conversation/conversation-detail.component';
import { Conversation } from 'app/shared/model/conversation.model';

describe('Component Tests', () => {
  describe('Conversation Management Detail Component', () => {
    let comp: ConversationDetailComponent;
    let fixture: ComponentFixture<ConversationDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ conversation: new Conversation(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TestjioApplicationTestModule],
        declarations: [ConversationDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ConversationDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ConversationDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load conversation on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.conversation).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });

    describe('byteSize', () => {
      it('Should call byteSize from JhiDataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'byteSize');
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.byteSize(fakeBase64);

        // THEN
        expect(dataUtils.byteSize).toBeCalledWith(fakeBase64);
      });
    });

    describe('openFile', () => {
      it('Should call openFile from JhiDataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'openFile');
        const fakeContentType = 'fake content type';
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.openFile(fakeContentType, fakeBase64);

        // THEN
        expect(dataUtils.openFile).toBeCalledWith(fakeContentType, fakeBase64);
      });
    });
  });
});
