import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEnvelope } from 'app/shared/model/envelope.model';
import { EnvelopeService } from './envelope.service';

@Component({
  templateUrl: './envelope-delete-dialog.component.html',
})
export class EnvelopeDeleteDialogComponent {
  envelope?: IEnvelope;

  constructor(protected envelopeService: EnvelopeService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.envelopeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('envelopeListModification');
      this.activeModal.close();
    });
  }
}
