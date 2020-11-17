import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { DynamicDataService } from 'app/entities/dynamic-data/dynamic-data.service';
import { IDynamicData, DynamicData } from 'app/shared/model/dynamic-data.model';
import { DataType } from 'app/shared/model/enumerations/data-type.model';

describe('Service Tests', () => {
  describe('DynamicData Service', () => {
    let injector: TestBed;
    let service: DynamicDataService;
    let httpMock: HttpTestingController;
    let elemDefault: IDynamicData;
    let expectedResult: IDynamicData | IDynamicData[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(DynamicDataService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new DynamicData(0, 'AAAAAAA', 'AAAAAAA', DataType.DString);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a DynamicData', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new DynamicData()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a DynamicData', () => {
        const returnedFromService = Object.assign(
          {
            dataKey: 'BBBBBB',
            dataValue: 'BBBBBB',
            valueDataType: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of DynamicData', () => {
        const returnedFromService = Object.assign(
          {
            dataKey: 'BBBBBB',
            dataValue: 'BBBBBB',
            valueDataType: 'BBBBBB',
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

      it('should delete a DynamicData', () => {
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
