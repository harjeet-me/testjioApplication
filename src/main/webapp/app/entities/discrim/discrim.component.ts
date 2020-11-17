import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IDiscrim } from 'app/shared/model/discrim.model';
import { DiscrimService } from './discrim.service';
import { DiscrimDeleteDialogComponent } from './discrim-delete-dialog.component';

@Component({
  selector: 'jhi-discrim',
  templateUrl: './discrim.component.html',
})
export class DiscrimComponent implements OnInit, OnDestroy {
  discrims?: IDiscrim[];
  eventSubscriber?: Subscription;

  constructor(protected discrimService: DiscrimService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.discrimService.query().subscribe((res: HttpResponse<IDiscrim[]>) => (this.discrims = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInDiscrims();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IDiscrim): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInDiscrims(): void {
    this.eventSubscriber = this.eventManager.subscribe('discrimListModification', () => this.loadAll());
  }

  delete(discrim: IDiscrim): void {
    const modalRef = this.modalService.open(DiscrimDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.discrim = discrim;
  }
}
