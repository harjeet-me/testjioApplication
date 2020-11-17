import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDynamicDataEnvelope } from 'app/shared/model/dynamic-data-envelope.model';
import { DynamicDataEnvelopeService } from './dynamic-data-envelope.service';

@Component({
  templateUrl: './dynamic-data-envelope-delete-dialog.component.html',
})
export class DynamicDataEnvelopeDeleteDialogComponent {
  dynamicDataEnvelope?: IDynamicDataEnvelope;

  constructor(
    protected dynamicDataEnvelopeService: DynamicDataEnvelopeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.dynamicDataEnvelopeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('dynamicDataEnvelopeListModification');
      this.activeModal.close();
    });
  }
}
