import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAuditInfo } from 'app/shared/model/audit-info.model';

@Component({
  selector: 'jhi-audit-info-detail',
  templateUrl: './audit-info-detail.component.html',
})
export class AuditInfoDetailComponent implements OnInit {
  auditInfo: IAuditInfo | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ auditInfo }) => (this.auditInfo = auditInfo));
  }

  previousState(): void {
    window.history.back();
  }
}
