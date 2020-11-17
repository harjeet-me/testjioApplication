import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IDynamicData } from 'app/shared/model/dynamic-data.model';

type EntityResponseType = HttpResponse<IDynamicData>;
type EntityArrayResponseType = HttpResponse<IDynamicData[]>;

@Injectable({ providedIn: 'root' })
export class DynamicDataService {
  public resourceUrl = SERVER_API_URL + 'api/dynamic-data';

  constructor(protected http: HttpClient) {}

  create(dynamicData: IDynamicData): Observable<EntityResponseType> {
    return this.http.post<IDynamicData>(this.resourceUrl, dynamicData, { observe: 'response' });
  }

  update(dynamicData: IDynamicData): Observable<EntityResponseType> {
    return this.http.put<IDynamicData>(this.resourceUrl, dynamicData, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDynamicData>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDynamicData[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
