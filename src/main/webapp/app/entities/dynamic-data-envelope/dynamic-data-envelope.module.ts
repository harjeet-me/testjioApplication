import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TestjioApplicationSharedModule } from 'app/shared/shared.module';
import { DynamicDataEnvelopeComponent } from './dynamic-data-envelope.component';
import { DynamicDataEnvelopeDetailComponent } from './dynamic-data-envelope-detail.component';
import { DynamicDataEnvelopeUpdateComponent } from './dynamic-data-envelope-update.component';
import { DynamicDataEnvelopeDeleteDialogComponent } from './dynamic-data-envelope-delete-dialog.component';
import { dynamicDataEnvelopeRoute } from './dynamic-data-envelope.route';

@NgModule({
  imports: [TestjioApplicationSharedModule, RouterModule.forChild(dynamicDataEnvelopeRoute)],
  declarations: [
    DynamicDataEnvelopeComponent,
    DynamicDataEnvelopeDetailComponent,
    DynamicDataEnvelopeUpdateComponent,
    DynamicDataEnvelopeDeleteDialogComponent,
  ],
  entryComponents: [DynamicDataEnvelopeDeleteDialogComponent],
})
export class TestjioApplicationDynamicDataEnvelopeModule {}
