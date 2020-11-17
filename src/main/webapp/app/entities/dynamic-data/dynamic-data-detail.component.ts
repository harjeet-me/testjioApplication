import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDynamicData } from 'app/shared/model/dynamic-data.model';

@Component({
  selector: 'jhi-dynamic-data-detail',
  templateUrl: './dynamic-data-detail.component.html',
})
export class DynamicDataDetailComponent implements OnInit {
  dynamicData: IDynamicData | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ dynamicData }) => (this.dynamicData = dynamicData));
  }

  previousState(): void {
    window.history.back();
  }
}
