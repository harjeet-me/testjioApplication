import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { DynamicDataEnvelopeService } from 'app/entities/dynamic-data-envelope/dynamic-data-envelope.service';
import { IDynamicDataEnvelope, DynamicDataEnvelope } from 'app/shared/model/dynamic-data-envelope.model';

describe('Service Tests', () => {
  describe('DynamicDataEnvelope Service', () => {
    let injector: TestBed;
    let service: DynamicDataEnvelopeService;
    let httpMock: HttpTestingController;
    let elemDefault: IDynamicDataEnvelope;
    let expectedResult: IDynamicDataEnvelope | IDynamicDataEnvelope[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(DynamicDataEnvelopeService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new DynamicDataEnvelope(0, 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a DynamicDataEnvelope', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new DynamicDataEnvelope()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a DynamicDataEnvelope', () => {
        const returnedFromService = Object.assign(
          {
            desc: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of DynamicDataEnvelope', () => {
        const returnedFromService = Object.assign(
          {
            desc: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a DynamicDataEnvelope', () => {
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
