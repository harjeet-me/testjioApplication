import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDynamicData } from 'app/shared/model/dynamic-data.model';
import { DynamicDataService } from './dynamic-data.service';

@Component({
  templateUrl: './dynamic-data-delete-dialog.component.html',
})
export class DynamicDataDeleteDialogComponent {
  dynamicData?: IDynamicData;

  constructor(
    protected dynamicDataService: DynamicDataService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.dynamicDataService.delete(id).subscribe(() => {
      this.eventManager.broadcast('dynamicDataListModification');
      this.activeModal.close();
    });
  }
}
