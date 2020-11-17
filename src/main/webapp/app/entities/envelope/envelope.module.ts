import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TestjioApplicationSharedModule } from 'app/shared/shared.module';
import { EnvelopeComponent } from './envelope.component';
import { EnvelopeDetailComponent } from './envelope-detail.component';
import { EnvelopeUpdateComponent } from './envelope-update.component';
import { EnvelopeDeleteDialogComponent } from './envelope-delete-dialog.component';
import { envelopeRoute } from './envelope.route';

@NgModule({
  imports: [TestjioApplicationSharedModule, RouterModule.forChild(envelopeRoute)],
  declarations: [EnvelopeComponent, EnvelopeDetailComponent, EnvelopeUpdateComponent, EnvelopeDeleteDialogComponent],
  entryComponents: [EnvelopeDeleteDialogComponent],
})
export class TestjioApplicationEnvelopeModule {}
