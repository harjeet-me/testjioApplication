import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { ConversationService } from 'app/entities/conversation/conversation.service';
import { IConversation, Conversation } from 'app/shared/model/conversation.model';
import { CONVERSATIONTYPE } from 'app/shared/model/enumerations/conversationtype.model';

describe('Service Tests', () => {
  describe('Conversation Service', () => {
    let injector: TestBed;
    let service: ConversationService;
    let httpMock: HttpTestingController;
    let elemDefault: IConversation;
    let expectedResult: IConversation | IConversation[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(ConversationService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Conversation(0, 'AAAAAAA', CONVERSATIONTYPE.EMAIL, 'image/png', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', currentDate);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            sentDateTime: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Conversation', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            sentDateTime: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            sentDateTime: currentDate,
          },
          returnedFromService
        );

        service.create(new Conversation()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Conversation', () => {
        const returnedFromService = Object.assign(
          {
            subject: 'BBBBBB',
            type: 'BBBBBB',
            attachment: 'BBBBBB',
            attachmentName: 'BBBBBB',
            status: 'BBBBBB',
            sentDateTime: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            sentDateTime: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Conversation', () => {
        const returnedFromService = Object.assign(
          {
            subject: 'BBBBBB',
            type: 'BBBBBB',
            attachment: 'BBBBBB',
            attachmentName: 'BBBBBB',
            status: 'BBBBBB',
            sentDateTime: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            sentDateTime: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Conversation', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
