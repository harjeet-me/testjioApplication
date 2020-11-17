import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IDynamicData } from 'app/shared/model/dynamic-data.model';
import { DynamicDataService } from './dynamic-data.service';
import { DynamicDataDeleteDialogComponent } from './dynamic-data-delete-dialog.component';

@Component({
  selector: 'jhi-dynamic-data',
  templateUrl: './dynamic-data.component.html',
})
export class DynamicDataComponent implements OnInit, OnDestroy {
  dynamicData?: IDynamicData[];
  eventSubscriber?: Subscription;

  constructor(
    protected dynamicDataService: DynamicDataService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.dynamicDataService.query().subscribe((res: HttpResponse<IDynamicData[]>) => (this.dynamicData = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInDynamicData();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IDynamicData): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInDynamicData(): void {
    this.eventSubscriber = this.eventManager.subscribe('dynamicDataListModification', () => this.loadAll());
  }

  delete(dynamicData: IDynamicData): void {
    const modalRef = this.modalService.open(DynamicDataDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.dynamicData = dynamicData;
  }
}
