import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDynamicDataEnvelope } from 'app/shared/model/dynamic-data-envelope.model';

@Component({
  selector: 'jhi-dynamic-data-envelope-detail',
  templateUrl: './dynamic-data-envelope-detail.component.html',
})
export class DynamicDataEnvelopeDetailComponent implements OnInit {
  dynamicDataEnvelope: IDynamicDataEnvelope | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ dynamicDataEnvelope }) => (this.dynamicDataEnvelope = dynamicDataEnvelope));
  }

  previousState(): void {
    window.history.back();
  }
}
