import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TestjioApplicationSharedModule } from 'app/shared/shared.module';
import { FileSystemComponent } from './file-system.component';
import { FileSystemDetailComponent } from './file-system-detail.component';
import { FileSystemUpdateComponent } from './file-system-update.component';
import { FileSystemDeleteDialogComponent } from './file-system-delete-dialog.component';
import { fileSystemRoute } from './file-system.route';

@NgModule({
  imports: [TestjioApplicationSharedModule, RouterModule.forChild(fileSystemRoute)],
  declarations: [FileSystemComponent, FileSystemDetailComponent, FileSystemUpdateComponent, FileSystemDeleteDialogComponent],
  entryComponents: [FileSystemDeleteDialogComponent],
})
export class TestjioApplicationFileSystemModule {}
