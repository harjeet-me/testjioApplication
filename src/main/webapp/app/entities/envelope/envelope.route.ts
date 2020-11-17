import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IEnvelope, Envelope } from 'app/shared/model/envelope.model';
import { EnvelopeService } from './envelope.service';
import { EnvelopeComponent } from './envelope.component';
import { EnvelopeDetailComponent } from './envelope-detail.component';
import { EnvelopeUpdateComponent } from './envelope-update.component';

@Injectable({ providedIn: 'root' })
export class EnvelopeResolve implements Resolve<IEnvelope> {
  constructor(private service: EnvelopeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEnvelope> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((envelope: HttpResponse<Envelope>) => {
          if (envelope.body) {
            return of(envelope.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Envelope());
  }
}

export const envelopeRoute: Routes = [
  {
    path: '',
    component: EnvelopeComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'testjioApplicationApp.envelope.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: EnvelopeDetailComponent,
    resolve: {
      envelope: EnvelopeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'testjioApplicationApp.envelope.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: EnvelopeUpdateComponent,
    resolve: {
      envelope: EnvelopeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'testjioApplicationApp.envelope.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: EnvelopeUpdateComponent,
    resolve: {
      envelope: EnvelopeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'testjioApplicationApp.envelope.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
