import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IFileSystem } from 'app/shared/model/file-system.model';

type EntityResponseType = HttpResponse<IFileSystem>;
type EntityArrayResponseType = HttpResponse<IFileSystem[]>;

@Injectable({ providedIn: 'root' })
export class FileSystemService {
  public resourceUrl = SERVER_API_URL + 'api/file-systems';

  constructor(protected http: HttpClient) {}

  create(fileSystem: IFileSystem): Observable<EntityResponseType> {
    return this.http.post<IFileSystem>(this.resourceUrl, fileSystem, { observe: 'response' });
  }

  update(fileSystem: IFileSystem): Observable<EntityResponseType> {
    return this.http.put<IFileSystem>(this.resourceUrl, fileSystem, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IFileSystem>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IFileSystem[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
