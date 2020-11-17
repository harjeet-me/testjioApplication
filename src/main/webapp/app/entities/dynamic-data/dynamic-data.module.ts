import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TestjioApplicationSharedModule } from 'app/shared/shared.module';
import { DynamicDataComponent } from './dynamic-data.component';
import { DynamicDataDetailComponent } from './dynamic-data-detail.component';
import { DynamicDataUpdateComponent } from './dynamic-data-update.component';
import { DynamicDataDeleteDialogComponent } from './dynamic-data-delete-dialog.component';
import { dynamicDataRoute } from './dynamic-data.route';

@NgModule({
  imports: [TestjioApplicationSharedModule, RouterModule.forChild(dynamicDataRoute)],
  declarations: [DynamicDataComponent, DynamicDataDetailComponent, DynamicDataUpdateComponent, DynamicDataDeleteDialogComponent],
  entryComponents: [DynamicDataDeleteDialogComponent],
})
export class TestjioApplicationDynamicDataModule {}
