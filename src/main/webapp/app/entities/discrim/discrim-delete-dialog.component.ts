import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDiscrim } from 'app/shared/model/discrim.model';
import { DiscrimService } from './discrim.service';

@Component({
  templateUrl: './discrim-delete-dialog.component.html',
})
export class DiscrimDeleteDialogComponent {
  discrim?: IDiscrim;

  constructor(protected discrimService: DiscrimService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.discrimService.delete(id).subscribe(() => {
      this.eventManager.broadcast('discrimListModification');
      this.activeModal.close();
    });
  }
}
