import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { TestjioApplicationTestModule } from '../../../test.module';
import { ConversationComponent } from 'app/entities/conversation/conversation.component';
import { ConversationService } from 'app/entities/conversation/conversation.service';
import { Conversation } from 'app/shared/model/conversation.model';

describe('Component Tests', () => {
  describe('Conversation Management Component', () => {
    let comp: ConversationComponent;
    let fixture: ComponentFixture<ConversationComponent>;
    let service: ConversationService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TestjioApplicationTestModule],
        declarations: [ConversationComponent],
      })
        .overrideTemplate(ConversationComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ConversationComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ConversationService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Conversation(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.conversations && comp.conversations[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
