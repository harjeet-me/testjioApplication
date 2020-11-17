import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TestjioApplicationSharedModule } from 'app/shared/shared.module';
import { DiscrimComponent } from './discrim.component';
import { DiscrimDetailComponent } from './discrim-detail.component';
import { DiscrimUpdateComponent } from './discrim-update.component';
import { DiscrimDeleteDialogComponent } from './discrim-delete-dialog.component';
import { discrimRoute } from './discrim.route';

@NgModule({
  imports: [TestjioApplicationSharedModule, RouterModule.forChild(discrimRoute)],
  declarations: [DiscrimComponent, DiscrimDetailComponent, DiscrimUpdateComponent, DiscrimDeleteDialogComponent],
  entryComponents: [DiscrimDeleteDialogComponent],
})
export class TestjioApplicationDiscrimModule {}
