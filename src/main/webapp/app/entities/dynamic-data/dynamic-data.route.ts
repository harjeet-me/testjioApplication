import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IDynamicData, DynamicData } from 'app/shared/model/dynamic-data.model';
import { DynamicDataService } from './dynamic-data.service';
import { DynamicDataComponent } from './dynamic-data.component';
import { DynamicDataDetailComponent } from './dynamic-data-detail.component';
import { DynamicDataUpdateComponent } from './dynamic-data-update.component';

@Injectable({ providedIn: 'root' })
export class DynamicDataResolve implements Resolve<IDynamicData> {
  constructor(private service: DynamicDataService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDynamicData> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((dynamicData: HttpResponse<DynamicData>) => {
          if (dynamicData.body) {
            return of(dynamicData.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new DynamicData());
  }
}

export const dynamicDataRoute: Routes = [
  {
    path: '',
    component: DynamicDataComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'testjioApplicationApp.dynamicData.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DynamicDataDetailComponent,
    resolve: {
      dynamicData: DynamicDataResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'testjioApplicationApp.dynamicData.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DynamicDataUpdateComponent,
    resolve: {
      dynamicData: DynamicDataResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'testjioApplicationApp.dynamicData.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DynamicDataUpdateComponent,
    resolve: {
      dynamicData: DynamicDataResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'testjioApplicationApp.dynamicData.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
