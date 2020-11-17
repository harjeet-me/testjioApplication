import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IDiscrim } from 'app/shared/model/discrim.model';

type EntityResponseType = HttpResponse<IDiscrim>;
type EntityArrayResponseType = HttpResponse<IDiscrim[]>;

@Injectable({ providedIn: 'root' })
export class DiscrimService {
  public resourceUrl = SERVER_API_URL + 'api/discrims';

  constructor(protected http: HttpClient) {}

  create(discrim: IDiscrim): Observable<EntityResponseType> {
    return this.http.post<IDiscrim>(this.resourceUrl, discrim, { observe: 'response' });
  }

  update(discrim: IDiscrim): Observable<EntityResponseType> {
    return this.http.put<IDiscrim>(this.resourceUrl, discrim, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDiscrim>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDiscrim[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
