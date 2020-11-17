import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IConversation } from 'app/shared/model/conversation.model';

type EntityResponseType = HttpResponse<IConversation>;
type EntityArrayResponseType = HttpResponse<IConversation[]>;

@Injectable({ providedIn: 'root' })
export class ConversationService {
  public resourceUrl = SERVER_API_URL + 'api/conversations';

  constructor(protected http: HttpClient) {}

  create(conversation: IConversation): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(conversation);
    return this.http
      .post<IConversation>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(conversation: IConversation): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(conversation);
    return this.http
      .put<IConversation>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IConversation>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IConversation[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(conversation: IConversation): IConversation {
    const copy: IConversation = Object.assign({}, conversation, {
      sentDateTime: conversation.sentDateTime && conversation.sentDateTime.isValid() ? conversation.sentDateTime.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.sentDateTime = res.body.sentDateTime ? moment(res.body.sentDateTime) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((conversation: IConversation) => {
        conversation.sentDateTime = conversation.sentDateTime ? moment(conversation.sentDateTime) : undefined;
      });
    }
    return res;
  }
}
