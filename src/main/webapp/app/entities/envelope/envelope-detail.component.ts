import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEnvelope } from 'app/shared/model/envelope.model';

@Component({
  selector: 'jhi-envelope-detail',
  templateUrl: './envelope-detail.component.html',
})
export class EnvelopeDetailComponent implements OnInit {
  envelope: IEnvelope | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ envelope }) => (this.envelope = envelope));
  }

  previousState(): void {
    window.history.back();
  }
}
