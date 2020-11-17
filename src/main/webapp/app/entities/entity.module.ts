import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'envelope',
        loadChildren: () => import('./envelope/envelope.module').then(m => m.TestjioApplicationEnvelopeModule),
      },
      {
        path: 'file-system',
        loadChildren: () => import('./file-system/file-system.module').then(m => m.TestjioApplicationFileSystemModule),
      },
      {
        path: 'conversation',
        loadChildren: () => import('./conversation/conversation.module').then(m => m.TestjioApplicationConversationModule),
      },
      {
        path: 'dynamic-data-envelope',
        loadChildren: () =>
          import('./dynamic-data-envelope/dynamic-data-envelope.module').then(m => m.TestjioApplicationDynamicDataEnvelopeModule),
      },
      {
        path: 'dynamic-data',
        loadChildren: () => import('./dynamic-data/dynamic-data.module').then(m => m.TestjioApplicationDynamicDataModule),
      },
      {
        path: 'audit-info',
        loadChildren: () => import('./audit-info/audit-info.module').then(m => m.TestjioApplicationAuditInfoModule),
      },
      {
        path: 'discrim',
        loadChildren: () => import('./discrim/discrim.module').then(m => m.TestjioApplicationDiscrimModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class TestjioApplicationEntityModule {}
