import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IDynamicDataEnvelope } from 'app/shared/model/dynamic-data-envelope.model';

type EntityResponseType = HttpResponse<IDynamicDataEnvelope>;
type EntityArrayResponseType = HttpResponse<IDynamicDataEnvelope[]>;

@Injectable({ providedIn: 'root' })
export class DynamicDataEnvelopeService {
  public resourceUrl = SERVER_API_URL + 'api/dynamic-data-envelopes';

  constructor(protected http: HttpClient) {}

  create(dynamicDataEnvelope: IDynamicDataEnvelope): Observable<EntityResponseType> {
    return this.http.post<IDynamicDataEnvelope>(this.resourceUrl, dynamicDataEnvelope, { observe: 'response' });
  }

  update(dynamicDataEnvelope: IDynamicDataEnvelope): Observable<EntityResponseType> {
    return this.http.put<IDynamicDataEnvelope>(this.resourceUrl, dynamicDataEnvelope, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDynamicDataEnvelope>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDynamicDataEnvelope[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
