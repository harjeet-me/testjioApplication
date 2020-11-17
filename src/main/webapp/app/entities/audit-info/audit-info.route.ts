import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IAuditInfo, AuditInfo } from 'app/shared/model/audit-info.model';
import { AuditInfoService } from './audit-info.service';
import { AuditInfoComponent } from './audit-info.component';
import { AuditInfoDetailComponent } from './audit-info-detail.component';
import { AuditInfoUpdateComponent } from './audit-info-update.component';

@Injectable({ providedIn: 'root' })
export class AuditInfoResolve implements Resolve<IAuditInfo> {
  constructor(private service: AuditInfoService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAuditInfo> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((auditInfo: HttpResponse<AuditInfo>) => {
          if (auditInfo.body) {
            return of(auditInfo.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new AuditInfo());
  }
}

export const auditInfoRoute: Routes = [
  {
    path: '',
    component: AuditInfoComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'testjioApplicationApp.auditInfo.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AuditInfoDetailComponent,
    resolve: {
      auditInfo: AuditInfoResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'testjioApplicationApp.auditInfo.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AuditInfoUpdateComponent,
    resolve: {
      auditInfo: AuditInfoResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'testjioApplicationApp.auditInfo.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AuditInfoUpdateComponent,
    resolve: {
      auditInfo: AuditInfoResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'testjioApplicationApp.auditInfo.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
