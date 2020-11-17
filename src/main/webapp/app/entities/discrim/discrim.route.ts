import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IDiscrim, Discrim } from 'app/shared/model/discrim.model';
import { DiscrimService } from './discrim.service';
import { DiscrimComponent } from './discrim.component';
import { DiscrimDetailComponent } from './discrim-detail.component';
import { DiscrimUpdateComponent } from './discrim-update.component';

@Injectable({ providedIn: 'root' })
export class DiscrimResolve implements Resolve<IDiscrim> {
  constructor(private service: DiscrimService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDiscrim> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((discrim: HttpResponse<Discrim>) => {
          if (discrim.body) {
            return of(discrim.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Discrim());
  }
}

export const discrimRoute: Routes = [
  {
    path: '',
    component: DiscrimComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'testjioApplicationApp.discrim.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DiscrimDetailComponent,
    resolve: {
      discrim: DiscrimResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'testjioApplicationApp.discrim.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DiscrimUpdateComponent,
    resolve: {
      discrim: DiscrimResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'testjioApplicationApp.discrim.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DiscrimUpdateComponent,
    resolve: {
      discrim: DiscrimResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'testjioApplicationApp.discrim.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
