import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IAuditInfo } from 'app/shared/model/audit-info.model';

type EntityResponseType = HttpResponse<IAuditInfo>;
type EntityArrayResponseType = HttpResponse<IAuditInfo[]>;

@Injectable({ providedIn: 'root' })
export class AuditInfoService {
  public resourceUrl = SERVER_API_URL + 'api/audit-infos';

  constructor(protected http: HttpClient) {}

  create(auditInfo: IAuditInfo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(auditInfo);
    return this.http
      .post<IAuditInfo>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(auditInfo: IAuditInfo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(auditInfo);
    return this.http
      .put<IAuditInfo>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IAuditInfo>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IAuditInfo[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(auditInfo: IAuditInfo): IAuditInfo {
    const copy: IAuditInfo = Object.assign({}, auditInfo, {
      createdDate: auditInfo.createdDate && auditInfo.createdDate.isValid() ? auditInfo.createdDate.toJSON() : undefined,
      lastModifiedDate:
        auditInfo.lastModifiedDate && auditInfo.lastModifiedDate.isValid() ? auditInfo.lastModifiedDate.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.createdDate = res.body.createdDate ? moment(res.body.createdDate) : undefined;
      res.body.lastModifiedDate = res.body.lastModifiedDate ? moment(res.body.lastModifiedDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((auditInfo: IAuditInfo) => {
        auditInfo.createdDate = auditInfo.createdDate ? moment(auditInfo.createdDate) : undefined;
        auditInfo.lastModifiedDate = auditInfo.lastModifiedDate ? moment(auditInfo.lastModifiedDate) : undefined;
      });
    }
    return res;
  }
}
