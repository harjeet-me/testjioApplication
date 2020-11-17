import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAuditInfo } from 'app/shared/model/audit-info.model';
import { AuditInfoService } from './audit-info.service';

@Component({
  templateUrl: './audit-info-delete-dialog.component.html',
})
export class AuditInfoDeleteDialogComponent {
  auditInfo?: IAuditInfo;

  constructor(protected auditInfoService: AuditInfoService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.auditInfoService.delete(id).subscribe(() => {
      this.eventManager.broadcast('auditInfoListModification');
      this.activeModal.close();
    });
  }
}
