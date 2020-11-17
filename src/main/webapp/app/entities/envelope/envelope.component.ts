import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IEnvelope } from 'app/shared/model/envelope.model';
import { EnvelopeService } from './envelope.service';
import { EnvelopeDeleteDialogComponent } from './envelope-delete-dialog.component';

@Component({
  selector: 'jhi-envelope',
  templateUrl: './envelope.component.html',
})
export class EnvelopeComponent implements OnInit, OnDestroy {
  envelopes?: IEnvelope[];
  eventSubscriber?: Subscription;

  constructor(protected envelopeService: EnvelopeService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.envelopeService.query().subscribe((res: HttpResponse<IEnvelope[]>) => (this.envelopes = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInEnvelopes();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IEnvelope): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInEnvelopes(): void {
    this.eventSubscriber = this.eventManager.subscribe('envelopeListModification', () => this.loadAll());
  }

  delete(envelope: IEnvelope): void {
    const modalRef = this.modalService.open(EnvelopeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.envelope = envelope;
  }
}
