import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IDynamicDataEnvelope } from 'app/shared/model/dynamic-data-envelope.model';
import { DynamicDataEnvelopeService } from './dynamic-data-envelope.service';
import { DynamicDataEnvelopeDeleteDialogComponent } from './dynamic-data-envelope-delete-dialog.component';

@Component({
  selector: 'jhi-dynamic-data-envelope',
  templateUrl: './dynamic-data-envelope.component.html',
})
export class DynamicDataEnvelopeComponent implements OnInit, OnDestroy {
  dynamicDataEnvelopes?: IDynamicDataEnvelope[];
  eventSubscriber?: Subscription;

  constructor(
    protected dynamicDataEnvelopeService: DynamicDataEnvelopeService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.dynamicDataEnvelopeService
      .query()
      .subscribe((res: HttpResponse<IDynamicDataEnvelope[]>) => (this.dynamicDataEnvelopes = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInDynamicDataEnvelopes();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IDynamicDataEnvelope): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInDynamicDataEnvelopes(): void {
    this.eventSubscriber = this.eventManager.subscribe('dynamicDataEnvelopeListModification', () => this.loadAll());
  }

  delete(dynamicDataEnvelope: IDynamicDataEnvelope): void {
    const modalRef = this.modalService.open(DynamicDataEnvelopeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.dynamicDataEnvelope = dynamicDataEnvelope;
  }
}
