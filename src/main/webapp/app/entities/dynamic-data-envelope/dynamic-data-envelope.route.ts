import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IDynamicDataEnvelope, DynamicDataEnvelope } from 'app/shared/model/dynamic-data-envelope.model';
import { DynamicDataEnvelopeService } from './dynamic-data-envelope.service';
import { DynamicDataEnvelopeComponent } from './dynamic-data-envelope.component';
import { DynamicDataEnvelopeDetailComponent } from './dynamic-data-envelope-detail.component';
import { DynamicDataEnvelopeUpdateComponent } from './dynamic-data-envelope-update.component';

@Injectable({ providedIn: 'root' })
export class DynamicDataEnvelopeResolve implements Resolve<IDynamicDataEnvelope> {
  constructor(private service: DynamicDataEnvelopeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDynamicDataEnvelope> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((dynamicDataEnvelope: HttpResponse<DynamicDataEnvelope>) => {
          if (dynamicDataEnvelope.body) {
            return of(dynamicDataEnvelope.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new DynamicDataEnvelope());
  }
}

export const dynamicDataEnvelopeRoute: Routes = [
  {
    path: '',
    component: DynamicDataEnvelopeComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'testjioApplicationApp.dynamicDataEnvelope.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DynamicDataEnvelopeDetailComponent,
    resolve: {
      dynamicDataEnvelope: DynamicDataEnvelopeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'testjioApplicationApp.dynamicDataEnvelope.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DynamicDataEnvelopeUpdateComponent,
    resolve: {
      dynamicDataEnvelope: DynamicDataEnvelopeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'testjioApplicationApp.dynamicDataEnvelope.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DynamicDataEnvelopeUpdateComponent,
    resolve: {
      dynamicDataEnvelope: DynamicDataEnvelopeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'testjioApplicationApp.dynamicDataEnvelope.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
