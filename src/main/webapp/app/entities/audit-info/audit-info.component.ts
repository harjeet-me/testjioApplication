import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IAuditInfo } from 'app/shared/model/audit-info.model';
import { AuditInfoService } from './audit-info.service';
import { AuditInfoDeleteDialogComponent } from './audit-info-delete-dialog.component';

@Component({
  selector: 'jhi-audit-info',
  templateUrl: './audit-info.component.html',
})
export class AuditInfoComponent implements OnInit, OnDestroy {
  auditInfos?: IAuditInfo[];
  eventSubscriber?: Subscription;

  constructor(protected auditInfoService: AuditInfoService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.auditInfoService.query().subscribe((res: HttpResponse<IAuditInfo[]>) => (this.auditInfos = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInAuditInfos();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IAuditInfo): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInAuditInfos(): void {
    this.eventSubscriber = this.eventManager.subscribe('auditInfoListModification', () => this.loadAll());
  }

  delete(auditInfo: IAuditInfo): void {
    const modalRef = this.modalService.open(AuditInfoDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.auditInfo = auditInfo;
  }
}
