import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TestjioApplicationSharedModule } from 'app/shared/shared.module';
import { AuditInfoComponent } from './audit-info.component';
import { AuditInfoDetailComponent } from './audit-info-detail.component';
import { AuditInfoUpdateComponent } from './audit-info-update.component';
import { AuditInfoDeleteDialogComponent } from './audit-info-delete-dialog.component';
import { auditInfoRoute } from './audit-info.route';

@NgModule({
  imports: [TestjioApplicationSharedModule, RouterModule.forChild(auditInfoRoute)],
  declarations: [AuditInfoComponent, AuditInfoDetailComponent, AuditInfoUpdateComponent, AuditInfoDeleteDialogComponent],
  entryComponents: [AuditInfoDeleteDialogComponent],
})
export class TestjioApplicationAuditInfoModule {}
