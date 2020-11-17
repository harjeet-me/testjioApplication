import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDiscrim } from 'app/shared/model/discrim.model';

@Component({
  selector: 'jhi-discrim-detail',
  templateUrl: './discrim-detail.component.html',
})
export class DiscrimDetailComponent implements OnInit {
  discrim: IDiscrim | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ discrim }) => (this.discrim = discrim));
  }

  previousState(): void {
    window.history.back();
  }
}
